package com.casadocodigo.productapi.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.casadocodigo.productapi.dto.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "product")
@Table(schema = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Float preco;
    private String descricao;
    private String productIdentifier;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    public static Product convert(ProductDTO dto) {
        return Product.builder()
            .nome(dto.getNome())
            .preco(dto.getPreco())
            .descricao(dto.getDescricao())
            .productIdentifier(dto.getProductIdentifier())
            .category(
                Objects.isNull(dto.getCategory())
                    ? null
                    : Category.convert(dto.getCategory())
            )
            .build();
    }
}
