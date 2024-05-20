package com.example.bookstoreapplication.service.order;

import com.example.bookstoreapplication.dto.order.OrderRequestShippingAddressDto;
import com.example.bookstoreapplication.dto.order.OrderRequestStatusUpdateDto;
import com.example.bookstoreapplication.dto.order.OrderResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(Long userId, OrderRequestShippingAddressDto dto);

    List<OrderResponseDto> getAllOrdersByUserId(Long userId);

    void updateOrderStatus(Long orderId, OrderRequestStatusUpdateDto dto);
}
