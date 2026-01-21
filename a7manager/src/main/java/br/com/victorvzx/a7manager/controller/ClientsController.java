package br.com.victorvzx.a7manager.controller;

import br.com.victorvzx.a7manager.model.ClientsModel;
import br.com.victorvzx.a7manager.model.ProductsModel;
import br.com.victorvzx.a7manager.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientsController {
    @Autowired
    private ClientsRepository repository;

    @PostMapping
    public ResponseEntity<ClientsModel> post(@RequestBody ClientsModel cliente) {
        // Em um sistema real, aqui você usaria um BCrypt para criptografar a senha!
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(cliente));
    }

    @GetMapping
    public ResponseEntity<List<ClientsModel>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientsModel> getOne(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
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
