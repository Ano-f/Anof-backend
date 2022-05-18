package com.shinsunsu.anofspring.exception.user;

public class PasswordErrorException extends IllegalArgumentException {
    public PasswordErrorException(String msg) { super(msg); }
}
