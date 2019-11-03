package com.salil.driveme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Selected driver is offline")
public class IncorrectDriverStatusException extends Exception
{

    private static final long serialVersionUID = -3387516993224229952L;


    public IncorrectDriverStatusException(String message)
    {
        super(message);
    }
}
