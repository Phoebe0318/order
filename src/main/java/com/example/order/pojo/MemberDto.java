package com.example.order.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Schema(name = "MemberDto", description = "member information")
public class MemberDto {

    @Schema(description = "member name", example = "John")
    private String name;

    @Schema(description = "member email", example = "johndoe@me.com")
    private String email;

    @Schema(description = "member password", example = "password123")
    private String password;
}