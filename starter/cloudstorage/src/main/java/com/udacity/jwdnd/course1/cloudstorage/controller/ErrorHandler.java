package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandler {

    public ErrorHandler() {
    }

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest request, Exception exception, Model model) {

        System.out.println("An exception occurred: " + exception.getMessage());
        exception.printStackTrace(System.out);
        model.addAttribute("errorMessage", exception.getMessage());
        if (exception.getCause() != null) {
            model.addAttribute("errorCause", exception.getCause().getMessage());
        }
        return "error";
    }

}

