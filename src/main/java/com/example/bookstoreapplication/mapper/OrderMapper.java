package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.order.OrderResponseDto;
import com.example.bookstoreapplication.model.Order;
import com.example.bookstoreapplication.model.Status;
import com.example.bookstoreapplication.model.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "userId", source = "user", qualifiedByName = "getUserIdFromUser")
    @Mapping(target = "orderItems", source = "orderItems")
    @Mapping(target = "orderDate", source = "orderDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "status", source = "status", qualifiedByName = "getStatusStringName")
    OrderResponseDto toOrderResponseDto(Order order);

    @Mapping(target = "userId", source = "user", qualifiedByName = "getUserIdFromUser")
    @Mapping(target = "orderItems", source = "orderItems")
    @Mapping(target = "orderDate", source = "orderDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "status", source = "status", qualifiedByName = "getStatusStringName")
    List<OrderResponseDto> toResponseDtoList(List<Order> orders);

    @Named("getUserIdFromUser")
    default Long getUserIdFromUser(User user) {
        return user.getId();
    }

    @Named("getStatusStringName")
    default String getStatusStringName(Status status) {
        return status.name();
    }
}
