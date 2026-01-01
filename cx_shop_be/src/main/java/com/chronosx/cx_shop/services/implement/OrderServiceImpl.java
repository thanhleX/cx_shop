package com.chronosx.cx_shop.services.implement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chronosx.cx_shop.dtos.CartItemDto;
import com.chronosx.cx_shop.dtos.OrderDto;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.models.*;
import com.chronosx.cx_shop.repositories.OrderDetailRepository;
import com.chronosx.cx_shop.repositories.OrderRepository;
import com.chronosx.cx_shop.repositories.ProductRepository;
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
    ProductRepository productRepository;
    OrderDetailRepository orderDetailRepository;

    ModelMapper modelMapper;

    @Override
    @Transactional
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
        order.setTotalMoney(orderDto.getTotalMoney());
        orderRepository.save(order);

        // Tạo danh sách các đối tượng OrderDetail từ cartItems
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDto cartItemDto : orderDto.getCartItems()) {
            // Tạo một đối tượng OrderDetail từ CartItemDTO
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            // Lấy thông tin sản phẩm từ cartItemDTO
            Long productId = cartItemDto.getProductId();
            int quantity = cartItemDto.getQuantity();

            Product product = productRepository
                    .findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("cannot find product with id = " + productId));

            orderDetail.setProduct(product);
            orderDetail.setNumberOfProducts(quantity);
            orderDetail.setPrice(product.getPrice());
            orderDetail.setTotalMoney(product.getPrice() * quantity);
            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);

        modelMapper.typeMap(Order.class, OrderResponse.class).addMappings(mapper -> {
            // Ánh xạ Order.user.id sang OrderResponse.userId
            mapper.map(src -> src.getUser().getId(), OrderResponse::setUserId);
        });

        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrderById(Long id) throws DataNotFoundException {
        Order selectedOrder = orderRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("cannot find order with id = " + id));

        modelMapper.typeMap(Order.class, OrderResponse.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getId(), OrderResponse::setUserId);
        });

        return modelMapper.map(selectedOrder, OrderResponse.class);
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

    @Override
    public Page<Order> getOrdersByKeyword(String keyword, Pageable pageable) {
        return orderRepository.findByKeyword(keyword, pageable);
    }
}
