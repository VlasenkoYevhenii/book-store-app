package com.example.bookstoreapplication.service.shoppingcart;

import com.example.bookstoreapplication.dto.cartitem.CartItemRequestDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemResponseDto;
import com.example.bookstoreapplication.dto.shoppingcart.ShoppingCartRequestDto;
import com.example.bookstoreapplication.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.bookstoreapplication.exception.EntityNotFoundException;
import com.example.bookstoreapplication.mapper.ShoppingCartMapper;
import com.example.bookstoreapplication.model.CartItem;
import com.example.bookstoreapplication.model.ShoppingCart;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.repository.cartitem.CartItemRepository;
import com.example.bookstoreapplication.repository.shoppingcart.ShoppingCartRepository;
import com.example.bookstoreapplication.service.cartitem.CartItemService;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static final String FIND_CART_ITEM_EXCEPTION = "Can't find cartItem by id ";
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemService cartItemService;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartResponseDto createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartMapper
                .toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addBookToShoppingCart(User user,
                                                         CartItemRequestDto cartItemRequestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(user.getId());
        CartItemResponseDto responseDto
                = cartItemService.createCartItem(shoppingCart, cartItemRequestDto);
        CartItem cartItem = findById(responseDto.getId());
        addCartItemToShoppingCart(shoppingCart, cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getShoppingCartDto(Long userId) {
        return shoppingCartMapper
                .toDto(shoppingCartRepository.findShoppingCartByUserId(userId));
    }

    @Override
    public ShoppingCartResponseDto clearShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setCartItems(new HashSet<>());
        return shoppingCartMapper
                .toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto updateQuantityById(User user,
                                                      Long cartItemId,
                                                      ShoppingCartRequestDto requestDto) {
        CartItem cartItem = findById(cartItemId);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemService.save(cartItem);
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(user.getId());
        shoppingCart.setCartItems(updateCartItems(cartItemId, shoppingCart, cartItem));
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    private Set<CartItem> updateCartItems(Long cartItemId, ShoppingCart shoppingCart,
                                          CartItem cartItem) {
        Set<CartItem> cartItemSet = new HashSet<>();
        for (CartItem c : shoppingCart.getCartItems()) {
            if (c.getId().equals(cartItemId)) {
                cartItemSet.add(cartItem);
            } else {
                cartItemSet.add(c);
            }
        }
        return cartItemSet;
    }

    private void addCartItemToShoppingCart(ShoppingCart shoppingCart, CartItem cartItem) {
        shoppingCart.getCartItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    private CartItem findById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException(FIND_CART_ITEM_EXCEPTION + cartItemId));
    }
}
