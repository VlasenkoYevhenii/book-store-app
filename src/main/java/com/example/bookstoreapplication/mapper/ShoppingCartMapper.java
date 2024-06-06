package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.cartitem.CartItemResponseDto;
import com.example.bookstoreapplication.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.bookstoreapplication.model.CartItem;
import com.example.bookstoreapplication.model.ShoppingCart;
import com.example.bookstoreapplication.model.User;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = {CartItemMapper.class})
public interface ShoppingCartMapper {

    @Mapping(target = "userId", source = "user", qualifiedByName = "getUserIdByUser")
    @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "getCartItemsDtos")
    ShoppingCartResponseDto toResponseDto(ShoppingCart shoppingCart);

    @Named(value = "getUserIdByUser")
    default Long getUserIdByUser(User user) {
        return user.getId();
    }

    @Named(value = "getCartItemsDtos")
    Set<CartItemResponseDto> getCartItemsDto(Set<CartItem> cartItems);
}
