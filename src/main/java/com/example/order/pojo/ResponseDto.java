package com.example.order.pojo;

import lombok.Data;

@Data
public class ResponseDto<T> {
    int status;
    T token;
    String message;
    Object data;
}
