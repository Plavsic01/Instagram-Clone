package com.plavsic.instagram.user.exception;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Could not find user!");
    }
}
