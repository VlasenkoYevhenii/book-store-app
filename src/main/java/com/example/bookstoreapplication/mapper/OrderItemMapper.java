package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.orderitem.OrderItemResponseDto;
import com.example.bookstoreapplication.model.OrderItem;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {
    OrderItemResponseDto toResponseDto(OrderItem orderItem);

    List<OrderItemResponseDto> toResponseDtoList(List<OrderItem> orderItemList);
}
