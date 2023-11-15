package com.petproject.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlerController {

    /**
     * Used for displaying the custom page of error "404 Not Found".
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError() {
        return "error404";
    }
}

