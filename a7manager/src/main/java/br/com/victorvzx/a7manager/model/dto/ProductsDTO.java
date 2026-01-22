package br.com.victorvzx.a7manager.model.dto;

public record ProductsDTO(
        Long id,
        String product_name,
        String description,
        String price,
        String quantity
) {
}
