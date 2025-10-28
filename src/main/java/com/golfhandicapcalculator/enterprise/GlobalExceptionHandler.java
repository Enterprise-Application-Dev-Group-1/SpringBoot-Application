package com.golfhandicapcalculator.enterprise;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
Handles InvalidInputException and displays a friendly error message.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public String handleInvalidInput(InvalidInputException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "golf-handicap"; // the name of your Thymeleaf template
    }
}
