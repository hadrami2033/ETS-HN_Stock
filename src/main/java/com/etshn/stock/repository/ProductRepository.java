package com.etshn.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.etshn.stock.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findByNomContainingAndQuantiteEnStockGreaterThanOrderByDateCreationDesc(String nom, int quantity, Pageable pageable);
	Page<Product> findByNomContainingAndQuantiteEnStockEqualsOrderByDateCreationDesc(String nom, int quantity, Pageable pageable);
	Page<Product> findByNomContainingOrderByDateCreationDesc(String nom, Pageable pageable);
    Product findByNom(String nom);
}
