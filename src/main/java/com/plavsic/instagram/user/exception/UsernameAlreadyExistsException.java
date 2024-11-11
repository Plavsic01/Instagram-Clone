package com.plavsic.instagram.user.exception;


// Use @ResponseStatus if you don't want to handle error in GlobalExceptionHandler (check application.properties)
//@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username " + username + " already exists!");
    }
}
