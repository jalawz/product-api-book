package com.casadocodigo.productapi.dto;

import javax.validation.constraints.NotNull;

import com.casadocodigo.productapi.model.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    @NotNull
    private Long id;
    private String nome;

    public static CategoryDTO convert(Category category) {
        return CategoryDTO.builder()
            .id(category.getId())
            .nome(category.getNome())
            .build();
    }

}
