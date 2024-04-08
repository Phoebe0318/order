package com.example.order.service;

import com.example.order.dao.MemberDao;
import com.example.order.dao.OrderDao;
import com.example.order.pojo.MemberDo;
import com.example.order.pojo.OrderDo;
import com.example.order.pojo.OrderDto;
import com.example.order.pojo.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderDao orderDao;
    private final MemberDao memberDao;

    public ResponseDto<OrderDo> createOrder(Long memberId, OrderDto orderDto) {
        Optional<MemberDo> memberOptional = memberDao.findById(memberId);
        if (memberOptional.isEmpty()) {
            log.info("Member not found with ID: {}", memberId);

            ResponseDto<OrderDo> responseDto = new ResponseDto<>();
            responseDto.setStatus(0);
            responseDto.setMessage("Member not found");
            return responseDto;
        }

        OrderDo orderDo = mapToOrderDo(orderDto);
        orderDo.setMemberId(memberId);
        orderDo.setOrderUuid(UUID.randomUUID().toString());
        OrderDo createdOrder = orderDao.save(orderDo);
        log.info("Order created successfully with ID: {}", createdOrder.getId());

        ResponseDto<OrderDo> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        responseDto.setMessage("Order created successfully");
        responseDto.setData(createdOrder);
        return responseDto;
    }

    private OrderDo mapToOrderDo(OrderDto orderDto) {
        OrderDo orderDo = new OrderDo();
        orderDo.setProductId(orderDto.getProductId());
        orderDo.setQuantity(orderDto.getQuantity());
        orderDo.setPurchaseDate(orderDto.getPurchaseDate());
        return orderDo;
    }

    public ResponseDto<List<OrderDo>> searchOrders(Long memberId, String orderUuid, String productName, LocalDate purchaseDate, int page, int size) {
        List<OrderDo> orders;
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDo> ordersPage = orderDao.findByConditions(memberId, orderUuid, productName, purchaseDate, pageable);
        orders = ordersPage.getContent();

        ResponseDto<List<OrderDo>> responseDto = new ResponseDto<>();
        if (!orders.isEmpty()) {
            responseDto.setStatus(1);
            responseDto.setMessage("Orders fetched successfully");
            responseDto.setData(orders);
        } else {
            responseDto.setStatus(0);
            responseDto.setMessage("No orders found");
        }
        return responseDto;
    }

}
