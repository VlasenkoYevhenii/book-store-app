package com.example.bookstoreapplication.service.orderitem;

import com.example.bookstoreapplication.dto.orderitem.OrderItemResponseDto;
import java.util.List;

public interface OrderItemService {

    List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId);

    OrderItemResponseDto getOrderItemFromSpecificOrder(Long orderId, Long orderItemId);
}
