package com.caoguzelmas.secondhandshop.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class UserActivityException extends RuntimeException{

    private static final String defaultMessage = "User is not active!";

    public UserActivityException() {
        super(defaultMessage);
    }
    public UserActivityException(String message) {
        super(message);
    }

}
