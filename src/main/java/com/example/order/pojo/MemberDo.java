package com.example.order.pojo;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "member")
public class MemberDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}