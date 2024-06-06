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
    private final ShoppingCartRepository cartRepository;
    private final ShoppingCartMapper cartMapper;
    private final CartItemService itemService;
    private final CartItemRepository itemRepository;

    @Override
    public ShoppingCartResponseDto createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return cartMapper
                .toResponseDto(cartRepository.save(shoppingCart));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addBookToShoppingCart(User user,
                                                         CartItemRequestDto cartItemRequestDto) {
        ShoppingCart shoppingCart = cartRepository.findShoppingCartByUserId(user.getId());
        CartItemResponseDto responseDto
                = itemService.createCartItem(shoppingCart, cartItemRequestDto);
        CartItem cartItem = findById(responseDto.getId());
        addCartItemToShoppingCart(shoppingCart, cartItem);
        return cartMapper.toResponseDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getShoppingCartDto(Long userId) {
        return cartMapper
                .toResponseDto(cartRepository.findShoppingCartByUserId(userId));
    }

    @Override
    public ShoppingCartResponseDto clearShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setCartItems(new HashSet<>());
        return cartMapper
                .toResponseDto(cartRepository.save(shoppingCart));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto updateQuantityById(User user,
                                                      Long cartItemId,
                                                      ShoppingCartRequestDto requestDto) {
        CartItem cartItem = findById(cartItemId);
        cartItem.setQuantity(requestDto.getQuantity());
        itemService.save(cartItem);
        cartRepository.findShoppingCartByUserId(user.getId()).getCartItems().add(cartItem);

        return cartMapper.toResponseDto(cartRepository.findShoppingCartByUserId(user.getId()));
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
        cartRepository.save(shoppingCart);
    }

    private CartItem findById(Long cartItemId) {
        return itemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException(FIND_CART_ITEM_EXCEPTION + cartItemId));
    }
}
