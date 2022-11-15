package com.casadocodigo.productapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casadocodigo.productapi.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
