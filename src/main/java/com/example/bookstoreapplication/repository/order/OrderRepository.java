package com.example.bookstoreapplication.repository.order;

import com.example.bookstoreapplication.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Long userId);

    @Query(value = "SELECT * FROM orders WHERE id = :orderId", nativeQuery = true)
    Optional<Order> findByOrderId(@Param("orderId") Long orderId);
}
