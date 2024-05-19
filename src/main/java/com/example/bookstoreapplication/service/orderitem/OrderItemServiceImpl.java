package com.example.bookstoreapplication.service.orderitem;

import com.example.bookstoreapplication.dto.orderitem.OrderItemResponseDto;
import com.example.bookstoreapplication.exception.EntityNotFoundException;
import com.example.bookstoreapplication.mapper.OrderItemMapper;
import com.example.bookstoreapplication.model.Order;
import com.example.bookstoreapplication.model.OrderItem;
import com.example.bookstoreapplication.repository.OrderItemRepository;
import com.example.bookstoreapplication.repository.order.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId) {
        return orderItemMapper.toResponseDtoList(orderItemRepository.findAllByOrderId(orderId));
    }

    @Override
    public OrderItemResponseDto getOrderItemFromSpecificOrder(Long orderId, Long orderItemId) {
        Order order = orderRepository.findOrderById(orderId);
        OrderItem item = order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getId()
                        .equals(orderItemId)).findFirst().orElseThrow(
                        () -> new EntityNotFoundException("failed to get order item by id="
                                + orderItemId));
        return orderItemMapper.toResponseDto(item);
    }
}
