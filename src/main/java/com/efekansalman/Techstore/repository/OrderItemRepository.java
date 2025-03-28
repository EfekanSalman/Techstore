package com.efekansalman.Techstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efekansalman.Techstore.Entity.Order;

public interface OrderItemRepository extends JpaRepository<Order, Long> {
}
