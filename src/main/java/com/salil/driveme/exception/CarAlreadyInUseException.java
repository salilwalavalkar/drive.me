package com.salil.driveme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Car already in use by another driver")
public class CarAlreadyInUseException extends Exception
{
    private static final long serialVersionUID = -3387516993224229950L;


    public CarAlreadyInUseException(String message)
    {
        super(message);
    }
}
