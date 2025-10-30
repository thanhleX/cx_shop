package com.chronosx.cx_shop.services.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chronosx.cx_shop.dtos.OrderDetailDto;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.models.Order;
import com.chronosx.cx_shop.models.OrderDetail;
import com.chronosx.cx_shop.models.Product;
import com.chronosx.cx_shop.repositories.OrderDetailRepository;
import com.chronosx.cx_shop.repositories.OrderRepository;
import com.chronosx.cx_shop.repositories.ProductRepository;
import com.chronosx.cx_shop.services.OrderDetailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailServiceImpl implements OrderDetailService {

    OrderDetailRepository orderDetailRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDto orderDetailDto) throws DataNotFoundException {
        Order order = orderRepository
                .findById(orderDetailDto.getOrderId())
                .orElseThrow(
                        () -> new DataNotFoundException("cannot find order with id = " + orderDetailDto.getOrderId()));

        Product product = productRepository
                .findById(orderDetailDto.getProductId())
                .orElseThrow(() ->
                        new DataNotFoundException("cannot find product with id = " + orderDetailDto.getProductId()));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(orderDetailDto.getPrice())
                .numberOfProducts(orderDetailDto.getNumberOfProducts())
                .totalMoney(orderDetailDto.getTotalMoney())
                .color(orderDetailDto.getColor())
                .build();

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetailById(Long id) throws DataNotFoundException {
        return orderDetailRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("cannot find order detail with id - " + id));
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDto) throws DataNotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order Detail Not Found"));

        Order existingOrder = orderRepository
                .findById(orderDetailDto.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order Not Found"));

        Product existingProduct = productRepository
                .findById(orderDetailDto.getProductId())
                .orElseThrow(() ->
                        new DataNotFoundException("cannot find product with id = " + orderDetailDto.getProductId()));

        existingOrderDetail.setPrice(orderDetailDto.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDto.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDto.getTotalMoney());
        existingOrderDetail.setColor(orderDetailDto.getColor());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);

        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public List<OrderDetail> getOrderDetails(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
