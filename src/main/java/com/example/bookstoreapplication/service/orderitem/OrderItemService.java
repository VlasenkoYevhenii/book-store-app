package com.example.bookstoreapplication.service.orderitem;

import com.example.bookstoreapplication.dto.order.OrderResponseDto;
import java.util.List;

public interface OrderItemService {

    List<OrderResponseDto> getOrderItemsByOrderId(Long orderId);

    OrderResponseDto getOrderItemFromSpecificOrder(Long orderId, Long orderItemId);
}
