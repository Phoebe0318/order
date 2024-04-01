package com.example.order.pojo;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private Long orderId;
    private UUID orderUuid;
    private Long memberId;
    private Long productId;
    private int quantity;
    private String productName;
    private LocalDateTime purchaseDate;

    public Order() {
    }

    public Order(UUID orderUuid, Long memberId, Long productId, int quantity, String productName, LocalDateTime purchaseDate) {
        this.orderUuid = orderUuid;
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.purchaseDate = purchaseDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(UUID orderUuid) {
        this.orderUuid = orderUuid;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
