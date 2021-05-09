package com.zut.interactivetools.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public ExceptionResponse handle(MyException e){
        return e.print();
    }
}
