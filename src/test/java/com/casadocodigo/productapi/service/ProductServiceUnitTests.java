package com.casadocodigo.productapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.casadocodigo.productapi.Repository.ProductRepository;
import com.casadocodigo.productapi.dto.CategoryDTO;
import com.casadocodigo.productapi.dto.ProductDTO;
import com.casadocodigo.productapi.model.Category;
import com.casadocodigo.productapi.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(buildProduct(1L, "prod-1", 1L)));

        List<ProductDTO> result = productService.getAll();

        assertEquals(1, result.size());
        assertEquals("prod-1", result.get(0).getProductIdentifier());
    }

    @Test
    void shouldReturnProductsByCategoryUsingQueryMethod() {
        when(productRepository.getProductByCategory(2L)).thenReturn(List.of(buildProduct(2L, "prod-2", 2L)));

        List<ProductDTO> result = productService.getProductByCategoryId(2L);

        assertEquals(1, result.size());
        assertEquals(Long.valueOf(2L), result.get(0).getCategory().getId());
    }

    @Test
    void shouldReturnProductsByCategoryUsingDerivedMethod() {
        when(productRepository.findByCategoryId(3L)).thenReturn(List.of(buildProduct(3L, "prod-3", 3L)));

        List<ProductDTO> result = productService.findProductByCategoryId(3L);

        assertEquals(1, result.size());
        assertEquals("prod-3", result.get(0).getProductIdentifier());
    }

    @Test
    void shouldFindByProductIdentifier() {
        when(productRepository.findByProductIdentifier("abc")).thenReturn(Optional.of(buildProduct(4L, "abc", 1L)));

        ProductDTO result = productService.findByProductIdentifier("abc");

        assertEquals("abc", result.getProductIdentifier());
        assertEquals(new BigDecimal("199.90"), result.getPreco());
    }

    @Test
    void shouldThrowNotFoundWhenProductIdentifierDoesNotExist() {
        when(productRepository.findByProductIdentifier("missing")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> productService.findByProductIdentifier("missing")
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void shouldSaveProduct() {
        ProductDTO request = ProductDTO.builder()
            .productIdentifier("new-prod")
            .nome("Mouse")
            .descricao("Mouse sem fio")
            .preco(new BigDecimal("79.90"))
            .category(CategoryDTO.builder().id(1L).nome("Eletronico").build())
            .build();

        when(productRepository.save(org.mockito.ArgumentMatchers.any(Product.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        ProductDTO saved = productService.save(request);

        assertEquals("new-prod", saved.getProductIdentifier());
        assertEquals(new BigDecimal("79.90"), saved.getPreco());
    }

    @Test
    void shouldDeleteProductWhenExists() {
        Product product = buildProduct(5L, "prod-del", 1L);
        when(productRepository.findById(5L)).thenReturn(Optional.of(product));

        productService.delete(5L);

        verify(productRepository).delete(product);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingProduct() {
        when(productRepository.findById(10L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> productService.delete(10L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    private Product buildProduct(Long id, String productIdentifier, Long categoryId) {
        return Product.builder()
            .id(id)
            .productIdentifier(productIdentifier)
            .nome("Produto")
            .descricao("Descricao")
            .preco(new BigDecimal("199.90"))
            .category(Category.builder().id(categoryId).nome("Categoria").build())
            .build();
    }
}
