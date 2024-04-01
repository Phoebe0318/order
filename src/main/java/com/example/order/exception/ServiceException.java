package com.example.order.exception;

import java.io.Serial;

public class ServiceException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -4513514459698548384L;

    public ServiceException(String message) {
        super(message);
    }
}
