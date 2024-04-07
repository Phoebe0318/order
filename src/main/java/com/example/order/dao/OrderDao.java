package com.example.order.dao;

import com.example.order.pojo.OrderDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;

@Repository
public interface OrderDao extends JpaRepository<OrderDo, Long> {

    @Query(value = "SELECT o.id, o.order_uuid, o.member_id, o.product_id, o.quantity, o.purchase_date " +
            "FROM orders o " +
            "JOIN product p ON o.product_id = p.id " +
            "WHERE (:orderUuid IS NULL OR o.order_uuid = :orderUuid) " +
            "AND (:productName IS NULL OR p.name = :productName) " +
            "AND (:purchaseDate IS NULL OR DATE(o.purchase_date) = :purchaseDate) " +
            "AND (:memberId IS NULL OR o.member_id = :memberId)", nativeQuery = true)
    Page<OrderDo> findByConditions(Long memberId, String orderUuid, String productName, LocalDate purchaseDate, Pageable pageable);

    int countByMemberId(Long memberId);
}