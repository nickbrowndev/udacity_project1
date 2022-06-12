package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//@ControllerAdvice
public class ErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    public ErrorHandler() {
    }

    //@ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception) {
        log.error("An error has occurred.", exception);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("errorCause", "my cause");
        return modelAndView;
    }

}

