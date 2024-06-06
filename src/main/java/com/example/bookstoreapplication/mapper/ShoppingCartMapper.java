package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.cartitem.CartItemResponseDto;
import com.example.bookstoreapplication.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.bookstoreapplication.model.CartItem;
import com.example.bookstoreapplication.model.ShoppingCart;
import com.example.bookstoreapplication.model.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user", qualifiedByName = "getUserIdFromUser")
    @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "getCartItemsDtos")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    @Named("getCartItemsDtos")
    default Set<CartItemResponseDto> getCartItemsDtoSet(Set<CartItem> cartItemSet) {
        return cartItemSet.stream()
                .map(this::toCartItemResponseDto)
                .collect(Collectors.toSet());
    }

    @Named("getUserIdFromUser")
    default Long getUserIdFromUser(User user) {
        return user.getId();
    }

    CartItemResponseDto toCartItemResponseDto(CartItem cartItem);
}
