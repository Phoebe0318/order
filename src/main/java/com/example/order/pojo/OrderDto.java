package com.example.order.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(name = "Order DTO")
public class OrderDto {

    private Long productId;

    private int quantity;

    private LocalDateTime purchaseDate;
}