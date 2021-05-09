package com.zut.interactivetools.exception;

public class MyException extends RuntimeException {
    private int code;  //异常状态码
    private String message;  //异常信息
    private String descinfo;   //描述

    /**
     * @param code     状态
     * @param message  信息
     * @param descinfo 错误,描述!
     */
    public MyException(int code, String message, String descinfo) {
        this.code = code;
        this.message = message;
        this.descinfo = descinfo;
    }

    public MyException() {
    }

    public String getDescinfo() {
        return descinfo;
    }

    public void setDescinfo(String descinfo) {
        this.descinfo = descinfo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public ExceptionResponse print(){
        return new ExceptionResponse(code,message,descinfo);
    }
}
