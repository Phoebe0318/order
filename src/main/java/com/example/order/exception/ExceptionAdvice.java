package com.example.order.exception;

import com.example.order.pojo.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseDto<String> handleMemberAccountNotFoundException(ServiceException serviceException) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        responseDto.setStatus(-1);
        responseDto.setMessage(serviceException.getMessage());

        return responseDto;
    }

}
