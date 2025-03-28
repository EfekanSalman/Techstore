package com.efekansalman.Techstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efekansalman.Techstore.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findBycategoryName(String categoryName);
}
