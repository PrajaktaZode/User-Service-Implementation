package com.example.userService.exceptions;

public class WrongPasswordException extends  Exception{
    public WrongPasswordException(String msg) {
        super(msg);
    }
}
