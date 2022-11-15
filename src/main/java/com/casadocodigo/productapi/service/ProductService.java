package com.casadocodigo.productapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.casadocodigo.productapi.Repository.ProductRepository;
import com.casadocodigo.productapi.dto.ProductDTO;
import com.casadocodigo.productapi.model.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream()
            .map(ProductDTO::convert)
            .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByCategoryId(Long categoryId) {
        return productRepository.getProductByCategory(categoryId)
            .stream()
            .map(ProductDTO::convert)
            .collect(Collectors.toList());
    }

    public List<ProductDTO> findProductByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
            .stream()
            .map(ProductDTO::convert)
            .collect(Collectors.toList());
    }

    public ProductDTO findByProductIdentifier(String productIdentifier) {
        return productRepository.findByProductIdentifier(productIdentifier)
            .map(ProductDTO::convert)
            .orElseThrow(() -> 
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    String.format("Product with identifier: %s not found", productIdentifier))
            );
    }

    public ProductDTO save(ProductDTO dto) {
        return ProductDTO.convert(productRepository
            .save(Product.convert(dto)));
    }

    public void delete(Long productId) {
        productRepository.findById(productId)
            .ifPresentOrElse(
                productRepository::delete, 
                () -> {
                    throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        String.format("Product with id: %s not found", productId)
                    );
                }
            );
    }
    
}
