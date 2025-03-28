package com.efekansalman.Techstore.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
	private Long productId;
	private String productName;
	private int quantity;
}
