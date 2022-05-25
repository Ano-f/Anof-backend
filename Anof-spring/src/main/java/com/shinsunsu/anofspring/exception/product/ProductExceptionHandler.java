package com.shinsunsu.anofspring.exception.product;

import com.shinsunsu.anofspring.exception.product.ProductException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RestController
public class ProductExceptionHandler {

    @ExceptionHandler(value = ProductException.class)
    public String handleProductException(ProductException e) {
        return e.getMessage();
    }
}
