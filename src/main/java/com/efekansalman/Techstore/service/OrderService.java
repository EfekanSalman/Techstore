package com.efekansalman.Techstore.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.efekansalman.Techstore.Entity.Order;
import com.efekansalman.Techstore.Entity.OrderItem;
import com.efekansalman.Techstore.Entity.Product;
import com.efekansalman.Techstore.dto.OrderDTO;
import com.efekansalman.Techstore.dto.OrderItemDTO;
import com.efekansalman.Techstore.repository.OrderRepository;
import com.efekansalman.Techstore.repository.ProductRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	
	public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}
	
	public OrderDTO createOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setOrderDate(LocalDateTime.now());
		order.setStatus("PENDING");
		
		List<OrderItem> items = new ArrayList<>();
		for (OrderItemDTO itemDTO : orderDTO.getItems()) {
			Optional<Product> productOpt = productRepository.findById(itemDTO.getProductId());
			if (productOpt.isEmpty()) {
				throw new IllegalArgumentException("Product not found: " + itemDTO.getProductId());
			}
			Product product = productOpt.get();
			if (product.getStock() < itemDTO.getQuantity()) {
				throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
			}
			
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setQuantity(itemDTO.getQuantity());
			items.add(orderItem);
			
			product.setStock(product.getStock() - itemDTO.getQuantity());
			productRepository.save(product);
			
		}
		
		order.setItems(items);
		Order savedOrder = orderRepository.save(order);
		return mapToDTO(savedOrder);
	}
	
	
	private OrderDTO mapToDTO(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.setOrderId(order.getId());
		dto.setOrderDate(order.getOrderDate());
		dto.setStatus(order.getStatus());
		
		List<OrderItemDTO> itemDTOs = new ArrayList<>();
		for (OrderItem item : order.getItems()) {
			OrderItemDTO itemDTO = new OrderItemDTO();
			itemDTO.setProductId(item.getProduct().getId());
			itemDTO.setProductName(item.getProduct().getName());
			itemDTO.setQuantity(item.getQuantity());
			itemDTOs.add(itemDTO);
		}
		dto.setItems(itemDTOs);
		return dto;
	}
}
