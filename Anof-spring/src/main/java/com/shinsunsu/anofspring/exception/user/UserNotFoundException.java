package com.shinsunsu.anofspring.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg) {super(msg);}
}
