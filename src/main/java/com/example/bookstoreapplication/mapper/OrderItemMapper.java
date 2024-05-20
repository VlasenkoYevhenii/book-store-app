package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.orderitem.OrderItemResponseDto;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.CartItem;
import com.example.bookstoreapplication.model.OrderItem;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book", qualifiedByName = "getIdFromBook")
    OrderItemResponseDto toResponseDto(OrderItem orderItem);

    @Mapping(target = "bookId", source = "book", qualifiedByName = "getIdFromBook")
    List<OrderItemResponseDto> toResponseDtoList(List<OrderItem> orderItemList);

    @Mapping(target = "id", ignore = true)
    OrderItem toOrderItemFromCartItem(CartItem cartItem);

    @Named("getIdFromBook")
    default Long getIdFromBook(Book book) {
        return book.getId();
    }
}
