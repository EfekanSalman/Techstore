package com.efekansalman.Techstore.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
	private Long orderId;
	private LocalDateTime orderDate;
	private String status;
	private List<OrderItemDTO> items;
}
