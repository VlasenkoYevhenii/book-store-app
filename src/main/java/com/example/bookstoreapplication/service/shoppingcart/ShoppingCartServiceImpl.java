package com.example.bookstoreapplication.service.shoppingcart;

import com.example.bookstoreapplication.dto.cartitem.CartItemRequestDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemUpdateDto;
import com.example.bookstoreapplication.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.bookstoreapplication.exception.EntityNotFoundException;
import com.example.bookstoreapplication.mapper.CartItemMapper;
import com.example.bookstoreapplication.mapper.ShoppingCartMapper;
import com.example.bookstoreapplication.model.CartItem;
import com.example.bookstoreapplication.model.ShoppingCart;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.repository.cartitem.CartItemRepository;
import com.example.bookstoreapplication.repository.shoppingcart.ShoppingCartRepository;
import com.example.bookstoreapplication.service.cartitem.CartItemService;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final ShoppingCartMapper cartMapper;
    private final CartItemRepository itemRepository;
    private final CartItemMapper itemMapper;
    private final CartItemService itemService;

    @Override
    public ShoppingCartResponseDto getByUserId(Long userId) {
        return cartMapper.toDto(cartRepository.findShoppingCartByUserId(userId));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addBookToCart(Long userId, CartItemRequestDto requestDto) {
        cartRepository.findShoppingCartByUserId(userId)
                .getCartItems()
                .add(itemMapper.toCartItem(requestDto));
        itemService.save(requestDto);
        return cartMapper.toDto(cartRepository.findShoppingCartByUserId(userId));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto updateBookQuantityById(Long cartItemId, Long userId,
                                                          CartItemUpdateDto updateDto) {
        itemService.updateCartItem(cartItemId, updateDto);
        ShoppingCart shoppingCart = cartRepository.findShoppingCartByUserId(userId);
        cartRepository.save(shoppingCart);
        return cartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = cartRepository.findShoppingCartByUserId(userId);
        CartItem cartItem = itemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found: "
                            + cartItemId));
        shoppingCart.getCartItems().remove(cartItem);
        cartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public void createNewShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        cart.setCartItems(new HashSet<>());
        cartRepository.save(cart);
    }
}
