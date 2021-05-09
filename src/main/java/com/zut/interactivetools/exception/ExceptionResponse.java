package com.zut.interactivetools.exception;

public class ExceptionResponse {
    private Integer code;
    private String msg;
    private String desc;

    public ExceptionResponse() {
    }

    public ExceptionResponse(Integer code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
