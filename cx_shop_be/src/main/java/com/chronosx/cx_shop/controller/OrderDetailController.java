package com.chronosx.cx_shop.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chronosx.cx_shop.components.LocalizationUtils;
import com.chronosx.cx_shop.dtos.OrderDetailDto;
import com.chronosx.cx_shop.dtos.responses.OrderDetailResponse;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.models.OrderDetail;
import com.chronosx.cx_shop.services.OrderDetailService;
import com.chronosx.cx_shop.utils.MessageKeys;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailController {

    OrderDetailService orderDetailService;

    LocalizationUtils localizationUtils;

    @PostMapping
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDto orderDetailDto) {
        try {
            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDto);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(newOrderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // lay ra 1 order_detail theo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailById(@Valid @PathVariable("id") Long id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
        return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
    }

    // lay ra danh sach cac order_details cua 1 order theo order id
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailsByOrderId(@Valid @PathVariable("orderId") Long orderId) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetails(orderId);
        List<OrderDetailResponse> orderDetailResponses =
                orderDetails.stream().map(OrderDetailResponse::fromOrderDetail).toList();
        return ResponseEntity.ok(orderDetailResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable Long id, @RequestBody OrderDetailDto orderDetailDto) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDto);
            return ResponseEntity.ok(orderDetail);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@Valid @PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok()
                .body(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_ORDER_DETAIL_SUCCESSFULLY.getKey(), id));
    }
}
