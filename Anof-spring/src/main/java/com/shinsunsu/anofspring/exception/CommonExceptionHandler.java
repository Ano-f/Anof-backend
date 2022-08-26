package com.shinsunsu.anofspring.exception;

import com.shinsunsu.anofspring.exception.article.ArticleNotFoundException;
import com.shinsunsu.anofspring.exception.mypage.DangerIngredientException;
import com.shinsunsu.anofspring.exception.product.ProductException;
import com.shinsunsu.anofspring.exception.user.PasswordErrorException;
import com.shinsunsu.anofspring.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(PasswordErrorException.class)
    public ResponseEntity<Object> PasswordErrorException(PasswordErrorException e) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("error", e.getClass().getSimpleName());
        map.put("msg", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ProductException.class)
    public ResponseEntity<Object> ProductException(ProductException e) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("error", e.getClass().getSimpleName());
        map.put("msg", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DangerIngredientException.class)
    public ResponseEntity<Object> DangerIngredientException(DangerIngredientException e) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("error", e.getClass().getSimpleName());
        map.put("msg", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> UserNotFoundException(UserNotFoundException e) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("error", e.getClass().getSimpleName());
        map.put("msg", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ArticleNotFoundException.class)
    public ResponseEntity<Object> ArticleNotFoundException(ArticleNotFoundException e) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("error", e.getClass().getSimpleName());
        map.put("msg", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
