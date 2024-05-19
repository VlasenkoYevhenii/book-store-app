package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
