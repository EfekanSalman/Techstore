package com.efekansalman.Techstore.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {
	private Long id;
	private String name;
	private BigDecimal price;
	private int stock;
	private String categoryName;	
}
