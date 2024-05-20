package com.example.bookstoreapplication.service.order;

import com.example.bookstoreapplication.dto.order.OrderRequestShippingAddressDto;
import com.example.bookstoreapplication.dto.order.OrderRequestStatusUpdateDto;
import com.example.bookstoreapplication.dto.order.OrderResponseDto;
import com.example.bookstoreapplication.exception.EntityNotFoundException;
import com.example.bookstoreapplication.mapper.OrderItemMapper;
import com.example.bookstoreapplication.mapper.OrderMapper;
import com.example.bookstoreapplication.model.Order;
import com.example.bookstoreapplication.model.OrderItem;
import com.example.bookstoreapplication.model.ShoppingCart;
import com.example.bookstoreapplication.model.Status;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.repository.order.OrderRepository;
import com.example.bookstoreapplication.repository.shoppingcart.ShoppingCartRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public OrderResponseDto placeOrder(User user, OrderRequestShippingAddressDto dto) {
        Long userId = user.getId();
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userId);
        Order order = createOrder(user, dto.getShippingAddress(), shoppingCart);
        orderRepository.save(order);
        order.setOrderItems(populateWithOrderItems(shoppingCart));
        order.setTotal(calculateTotalPrice(shoppingCart));
        return orderMapper.toOrderResponseDto(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDto> getAllOrdersByUser(User user) {
        List<Order> orders = orderRepository.findByUserId(user.getId());
        if (orders.isEmpty()) {
            throw new EntityNotFoundException("No orders found for user " + user.getId());
        }
        return orderMapper.toResponseDtoList(orders);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderRequestStatusUpdateDto dto) {
        Order order = orderRepository.findByOrderId(orderId).orElseThrow(
                () -> new EntityNotFoundException("Failed to find order by id = " + orderId));
        order.setStatus(Status.valueOf(dto.getStatus()));
        orderRepository.save(order);
    }

    private Order createOrder(User user, String shippingAddress, ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.PENDING);
        order.setTotal(calculateTotalPrice(shoppingCart));
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(shippingAddress);
        return order;
    }

    private BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(item -> item.getBook()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderItem> populateWithOrderItems(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(orderItemMapper::toOrderItemFromCartItem)
                .collect(Collectors.toSet());
    }
}
