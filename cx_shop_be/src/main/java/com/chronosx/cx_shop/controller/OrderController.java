package com.chronosx.cx_shop.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.chronosx.cx_shop.components.LocalizationUtils;
import com.chronosx.cx_shop.dtos.OrderDto;
import com.chronosx.cx_shop.models.Order;
import com.chronosx.cx_shop.responses.OrderResponse;
import com.chronosx.cx_shop.services.OrderService;
import com.chronosx.cx_shop.utils.MessageKeys;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            OrderResponse orderResponse = orderService.createOrder(orderDto);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrdersByUser(@Valid @PathVariable("user_id") Long userId) {
        try {
            List<Order> orders = orderService.getAllOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable Long id, @Valid @RequestBody OrderDto orderDto) {
        try {
            Order existingOrder = orderService.getOrderById(id);
            OrderResponse orderResponse = OrderResponse.fromOrder(existingOrder);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@Valid @PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(
                localizationUtils.getLocalizedMessage(MessageKeys.DELETE_ORDER_SUCCESSFULLY.getKey(), id));
    }
}
