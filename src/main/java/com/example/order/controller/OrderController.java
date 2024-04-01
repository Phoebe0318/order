package com.example.order.controller;

import com.example.order.pojo.Member;
import com.example.order.pojo.Order;
import com.example.order.pojo.ResponseDto;
import com.example.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @Operation(summary = "Place an order")
    public ResponseDto<Order> placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @GetMapping("/orders")
    @Operation(summary = "Get all orders")
    public ResponseDto<List<Order>> getOrdersByCriteria(
            @RequestParam(required = false) UUID orderUuid,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) LocalDateTime purchaseDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return orderService.getOrdersByCriteria(orderUuid, productName, purchaseDate, page, pageSize);
    }

    @GetMapping("/order/statistics")
    @Operation(summary = "Get member order statistics")
    public ResponseDto<List<Member>> getMemberOrderStatistics(@RequestParam int n) {
        return orderService.getMemberOrderStatistics(n);
    }
}