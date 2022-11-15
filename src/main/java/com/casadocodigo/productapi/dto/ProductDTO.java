package com.casadocodigo.productapi.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.casadocodigo.productapi.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    
    @NotBlank
    private String productIdentifier;
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    private Float preco;
    @NotNull
    private CategoryDTO category;

    public static ProductDTO convert(Product product) {
        return ProductDTO.builder()
            .nome(product.getNome())
            .preco(product.getPreco())
            .productIdentifier(product.getProductIdentifier())
            .descricao(product.getDescricao())
            .category(
                Objects.isNull(product.getCategory()) 
                    ? null 
                    : CategoryDTO.convert(product.getCategory())
            )
            .build();
    }
}
