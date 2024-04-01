package com.example.order.service;

import com.example.order.pojo.Member;
import com.example.order.pojo.Order;
import com.example.order.pojo.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong orderIdSequence = new AtomicLong(1);

    @Autowired
    private MemberService memberService;

    public ResponseDto<Order> placeOrder(Order order) {
        Long orderId = orderIdSequence.getAndIncrement();
        order.setOrderId(orderId);
        orders.put(orderId, order);

        ResponseDto<Order> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        responseDto.setMessage("Success");
        responseDto.setData(order);

        return responseDto;
    }

    public ResponseDto<List<Order>> getOrdersByCriteria(UUID orderUuid, String productName, LocalDateTime purchaseDate,
                                                        int page, int pageSize) {
        List<Order> matchingOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if ((orderUuid == null || order.getOrderUuid().equals(orderUuid)) &&
                    (productName == null || order.getProductName().equals(productName)) &&
                    (purchaseDate == null || order.getPurchaseDate().equals(purchaseDate))) {
                matchingOrders.add(order);
            }
        }

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, matchingOrders.size());
        List<Order> paginatedOrders = matchingOrders.subList(startIndex, endIndex);

        ResponseDto<List<Order>> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        responseDto.setMessage("Success");
        responseDto.setData(paginatedOrders);
        return responseDto;
    }

    public ResponseDto<List<Member>> getMemberOrderStatistics(int n) {
        Map<Long, Long> memberOrderCounts = orders.values().stream()
                .collect(Collectors.groupingBy(Order::getMemberId, Collectors.counting()));

        List<Member> memberStatistics = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : memberOrderCounts.entrySet()) {
            if (entry.getValue() > n) {
                Long memberId = entry.getKey();
                Member member = memberService.getMembers().get(memberId);
                if (member != null) {
                    memberStatistics.add(member);
                }
            }
        }

        ResponseDto<List<Member>> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        responseDto.setMessage("Success");
        responseDto.setData(memberStatistics);
        return responseDto;
    }
}
