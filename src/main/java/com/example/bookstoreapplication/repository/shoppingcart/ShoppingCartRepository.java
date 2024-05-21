package com.example.bookstoreapplication.repository.shoppingcart;

import com.example.bookstoreapplication.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(value = "ShoppingCart.withUserAndCartItemsAndBooks")
    ShoppingCart findShoppingCartByUserId(Long userId);
}
