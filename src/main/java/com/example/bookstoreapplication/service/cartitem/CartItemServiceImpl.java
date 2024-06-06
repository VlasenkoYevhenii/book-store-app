package com.example.bookstoreapplication.service.cartitem;

import com.example.bookstoreapplication.dto.cartitem.CartItemRequestDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemResponseDto;
import com.example.bookstoreapplication.exception.EntityNotFoundException;
import com.example.bookstoreapplication.mapper.BookMapper;
import com.example.bookstoreapplication.mapper.CartItemMapper;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.CartItem;
import com.example.bookstoreapplication.model.ShoppingCart;
import com.example.bookstoreapplication.repository.cartitem.CartItemRepository;
import com.example.bookstoreapplication.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Override
    public CartItemResponseDto findById(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()
                -> new EntityNotFoundException("Failed to find CartItem by id=" + cartItemId));
        return cartItemMapper.toCartItemResponseDto(cartItem);
    }

    @Override
    public void deleteById(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    @Override
    public CartItemResponseDto createCartItem(ShoppingCart shoppingCart,
                                              CartItemRequestDto requestDto) {
        CartItem newItem = cartItemMapper.toCartItem(requestDto);
        Long shoppingCartId = shoppingCart.getId();
        Book book = bookMapper.toModelFromResponse(bookService.getById(requestDto.getBookId()));
        newItem.setBook(book);
        newItem.setShoppingCart(shoppingCart);
        CartItem savedItem = cartItemRepository.save(newItem);
        return save(newItem);
    }

    @Override
    public CartItemResponseDto save(CartItem cartItem) {
        return cartItemMapper.toCartItemResponseDto(cartItemRepository.save(cartItem));
    }
}
