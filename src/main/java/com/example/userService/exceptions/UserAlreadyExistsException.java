package com.example.userService.exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String msg){
        //? super
        super(msg);
    }
}
