package com.efekansalman.Techstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efekansalman.Techstore.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findByName(String name);
}
