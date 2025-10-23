package com.chronosx.cx_shop.services.implement;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.chronosx.cx_shop.dtos.OrderDto;
import com.chronosx.cx_shop.dtos.responses.OrderResponse;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.models.Order;
import com.chronosx.cx_shop.models.OrderStatus;
import com.chronosx.cx_shop.models.User;
import com.chronosx.cx_shop.repositories.OrderRepository;
import com.chronosx.cx_shop.repositories.UserRepository;
import com.chronosx.cx_shop.services.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Override
    public OrderResponse createOrder(OrderDto orderDto) throws DataNotFoundException {
        User user = userRepository
                .findById(orderDto.getUserId())
                .orElseThrow(() -> new DataNotFoundException("cannot find user with id = " + orderDto.getUserId()));

        modelMapper.typeMap(OrderDto.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));

        Order order = new Order();

        modelMapper.map(orderDto, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);

        LocalDate shippingDate = orderDto.getShippingDate() == null ? LocalDate.now() : orderDto.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("shipping date cannot be in the past");
        }
        order.setShippingDate(shippingDate);
        order.setIsActive(true);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDto orderDto) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {}

    @Override
    public List<OrderResponse> getAllOrders() {
        return List.of();
    }
}
