package com.example.order.controller;

import com.example.order.pojo.OrderDo;
import com.example.order.pojo.OrderDto;
import com.example.order.pojo.ResponseDto;
import com.example.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{memberId}")
    @Operation(summary = "Place an order")
    public ResponseDto<OrderDo> createOrder(@PathVariable Long memberId, @RequestBody OrderDto orderDto) {
        return orderService.createOrder(memberId, orderDto);
    }

    @GetMapping
    @Operation(summary = "Get orders by conditions")
    public ResponseDto<List<OrderDo>> findOrders(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) String orderUuid,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) LocalDate purchaseDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.searchOrders(memberId, orderUuid, productName, purchaseDate, page, size);
    }
}