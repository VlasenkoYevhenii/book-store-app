package com.example.bookstoreapplication.repository.cartitem;

import com.example.bookstoreapplication.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
