package com.example.bookstoreapplication.service.cartitem;

import com.example.bookstoreapplication.dto.cartitem.CartItemRequestDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemResponseDto;
import com.example.bookstoreapplication.dto.cartitem.CartItemUpdateDto;
import com.example.bookstoreapplication.mapper.CartItemMapper;
import com.example.bookstoreapplication.model.CartItem;
import com.example.bookstoreapplication.repository.cartitem.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository repository;
    private final CartItemMapper mapper;

    @Override
    public CartItemResponseDto save(CartItemRequestDto cartItemRequestDto) {
        CartItem item = mapper.toCartItem(cartItemRequestDto);
        return mapper.toCartItemResponseDto(repository.save(item)
        );
    }

    @Override
    @Transactional
    public void updateCartItem(Long cartItemId, CartItemUpdateDto updateDto) {
        repository.findById(cartItemId).ifPresent(item -> {
            item.setQuantity(updateDto.quantity());
            repository.save(item);
        });
    }
}
