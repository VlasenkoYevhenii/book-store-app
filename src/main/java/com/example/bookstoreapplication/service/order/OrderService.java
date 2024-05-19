package com.example.bookstoreapplication.service.order;

import com.example.bookstoreapplication.dto.order.OrderRequestShippingAddressDto;
import com.example.bookstoreapplication.dto.order.OrderRequestStatusUpdateDto;
import com.example.bookstoreapplication.dto.order.OrderResponseDto;
import com.example.bookstoreapplication.model.User;

public interface OrderService {
    OrderResponseDto placeOrder(User user, OrderRequestShippingAddressDto dto);

    OrderResponseDto getAllOrdersByUser(User user);

    void updateOrderStatus(Long orderId, OrderRequestStatusUpdateDto dto);
}
