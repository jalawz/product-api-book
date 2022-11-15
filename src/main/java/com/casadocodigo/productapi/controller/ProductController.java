package com.casadocodigo.productapi.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.casadocodigo.productapi.dto.ProductDTO;
import com.casadocodigo.productapi.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductByCategoryId(categoryId));
    }

    @GetMapping("/{productIdentifier}")
    public ResponseEntity<ProductDTO> findById(@PathVariable String productIdentifier) {
        return ResponseEntity
            .ok(productService.findByProductIdentifier(productIdentifier));
    }

    @PostMapping
    public ResponseEntity<Void> newProduct(@Valid @RequestBody ProductDTO request) {
        ProductDTO productSaved = productService.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productIdentifier}")
            .buildAndExpand(productSaved.getProductIdentifier()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
}
