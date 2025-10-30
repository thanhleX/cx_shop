package com.chronosx.cx_shop.services.implement;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chronosx.cx_shop.dtos.OrderDto;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.models.Order;
import com.chronosx.cx_shop.models.OrderStatus;
import com.chronosx.cx_shop.models.User;
import com.chronosx.cx_shop.repositories.OrderRepository;
import com.chronosx.cx_shop.repositories.UserRepository;
import com.chronosx.cx_shop.responses.OrderResponse;
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
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDto orderDto) throws DataNotFoundException {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        User existingUser = userRepository
                .findById(orderDto.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + id));

        modelMapper.typeMap(OrderDto.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));

        modelMapper.map(orderDto, order);
        order.setUser(existingUser);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setIsActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> getAllOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
