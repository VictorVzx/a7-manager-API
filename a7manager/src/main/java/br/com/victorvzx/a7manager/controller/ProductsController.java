package br.com.victorvzx.a7manager.controller;

import br.com.victorvzx.a7manager.model.ProductsModel;
import br.com.victorvzx.a7manager.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsRepository repository;


    @PostMapping
    public ResponseEntity<ProductsModel> post(@RequestBody ProductsModel produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
    }

    @GetMapping
    public List<ProductsModel> getAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsModel> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsModel> put(@PathVariable Long id, @RequestBody ProductsModel produto) {
        return repository.findById(id)
                .map(registro -> {
                    // Atualiza os campos baseados na sua Model
                    registro.setProduct_name(produto.getProduct_name());
                    registro.setDescription(produto.getDescription());
                    registro.setPrice(produto.getPrice());
                    registro.setQuantity(produto.getQuantity());

                    ProductsModel atualizado = repository.save(registro);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(registro -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/imagem")
    public ResponseEntity<ProductsModel> updateImage(@PathVariable Long id, @RequestBody String novaUrl) {
        return repository.findById(id)
                .map(produto -> {
                    produto.setImage_url(novaUrl);
                    return ResponseEntity.ok(repository.save(produto));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
