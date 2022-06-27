package com.shinsunsu.anofspring.exception.user;

import com.shinsunsu.anofspring.exception.product.ProductException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RestController
public class UserExceptionHandler {

    @ExceptionHandler(value = PasswordErrorException.class)
    public String PasswordErrorException(PasswordErrorException e) {
        return e.getMessage();
    }
}
