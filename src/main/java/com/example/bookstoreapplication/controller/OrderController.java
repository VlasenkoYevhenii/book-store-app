package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.order.OrderRequestShippingAddressDto;
import com.example.bookstoreapplication.dto.order.OrderRequestStatusUpdateDto;
import com.example.bookstoreapplication.dto.order.OrderResponseDto;
import com.example.bookstoreapplication.dto.orderitem.OrderItemResponseDto;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.service.order.OrderService;
import com.example.bookstoreapplication.service.orderitem.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders and Order items")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping
    @Operation(summary = "Place order", description = "Place a new order")
    public OrderResponseDto placeOrder(@RequestBody @Valid OrderRequestShippingAddressDto dto,
                                       Authentication auth) {
        User user = (User) auth.getPrincipal();
        return orderService.placeOrder(user.getId(), dto);
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Get all orders for current user")
    public List<OrderResponseDto> getAllOrders(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return orderService.getAllOrdersByUserId(user.getId());
    }

    @PatchMapping("/{orderId}")
    public void updateOrderStatus(@PathVariable Long orderId,
                                  @RequestBody @Valid OrderRequestStatusUpdateDto dto) {
        orderService.updateOrderStatus(orderId, dto);
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItemResponseDto> getOrderItems(@PathVariable Long orderId) {
        return orderItemService.getOrderItemsByOrderId(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getOrderItemFromSpecificOrder(@PathVariable Long orderId,
                                                              @PathVariable Long itemId) {
        return orderItemService.getOrderItemFromSpecificOrder(orderId, itemId);
    }
}
