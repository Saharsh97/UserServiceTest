package com.scaler.userservice.exceptions;

public class TokenNotExistsOrAlreadyExpiredException extends Exception {
    public TokenNotExistsOrAlreadyExpiredException(String message){
        super(message);
    }
}
