package com.example.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberStatisticsDto {
    private Long memberId;
    private String name;
    private String email;
    private int orderCount;
}
