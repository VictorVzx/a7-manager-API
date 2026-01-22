package br.com.victorvzx.a7manager.controller;

import br.com.victorvzx.a7manager.model.ClientsModel;
import br.com.victorvzx.a7manager.model.dto.ClientsDTO;
import br.com.victorvzx.a7manager.model.dto.LoginDTO;
import br.com.victorvzx.a7manager.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientsController {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ClientsRepository repository;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO data) {
        // 1. Busca o cliente
        Optional<ClientsModel> clienteOptional = repository.findByEmail(data.email());

        // 2. Se não achar o email, já para aqui
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não cadastrado");
        }

        ClientsModel cliente = clienteOptional.get();

        // 3. Se achou, confere a senha
        if (encoder.matches(data.password(), cliente.getPassword())) {
            // Retorna o DTO (Lembra de tirar os parênteses extras aqui!)
            ClientsDTO dto = new ClientsDTO(cliente.getId(), cliente.getName(), cliente.getEmail());
            return ResponseEntity.ok().body(dto);
        }

        // 4. Se a senha estiver errada
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@RequestBody ClientsModel cliente) {
        // 1. Verificação básica: o email já existe?
        if (repository.findByEmail(cliente.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Este e-mail já está cadastrado!");
        }

        // 2. CRIPTOGRAFIA: Nunca salve a senha como ela veio do front!
        String senhaCriptografada = encoder.encode(cliente.getPassword());
        cliente.setPassword(senhaCriptografada);

        // 3. SALVAR: O Repository guarda o hash no banco
        ClientsModel clienteSalvo = repository.save(cliente);

        // 4. RETORNO SEGURO: Usamos o DTO para não devolver a senha no JSON
        ClientsDTO resposta = new ClientsDTO(
                clienteSalvo.getId(),
                clienteSalvo.getName(),
                clienteSalvo.getEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<ClientsDTO>> getAll() {
        List<ClientsDTO> DTOlist = repository.findAll().stream().map(c -> new ClientsDTO(c.getId(), c.getName(), c.getEmail())).toList();
        return ResponseEntity.ok(DTOlist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientsDTO> getOne(@PathVariable Long id) {
        // BUSCA UM: Se achar, converte para DTO
        return repository.findById(id)
                .map(c -> ResponseEntity.ok(new ClientsDTO(c.getId(), c.getName(), c.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}

