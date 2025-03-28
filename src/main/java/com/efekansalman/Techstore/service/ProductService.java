package com.efekansalman.Techstore.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.efekansalman.Techstore.Entity.Category;
import com.efekansalman.Techstore.Entity.Product;
import com.efekansalman.Techstore.dto.ProductDTO;
import com.efekansalman.Techstore.repository.CategoryRepository;
import com.efekansalman.Techstore.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}
	
	public ProductDTO addProduct(ProductDTO productDTO) {
		if (productDTO.getStock() < 0) {
			throw new IllegalArgumentException("Stock cannot be negative");
		}
		
		Optional<Category> categoryOpt = categoryRepository.findByName(productDTO.getCategoryName());
		if (categoryOpt.isEmpty()) {
			throw new IllegalArgumentException("Category not found: " + productDTO.getCategoryName());
		}
		
		Product product = Product.builder()
				.name(productDTO.getName())
				.price(productDTO.getPrice())
				.stock(productDTO.getStock())
				.category(categoryOpt.get())
				.build();
		
		Product savedProduct = productRepository.save(product);
		return mapToDTO(savedProduct);	
	}
	
	private ProductDTO mapToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());
		dto.setCategoryName(product.getCategory().getName());
		return dto;
	}
}
