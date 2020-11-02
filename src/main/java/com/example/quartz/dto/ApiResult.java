package com.example.quartz.dto;


public class ApiResult {

    private int code;

    private String msg;

    private Object data;

    public ApiResult() {
    }

    public ApiResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ApiResult ok(){
        return ok(null);
    }

    public static ApiResult ok(Object data){
        return new ApiResult(200,"REQUEST SUCCESS",data);
    }

    public static ApiResult prompt(String msg){
        return prompt(-1,msg);
    }

    public static ApiResult prompt(int code,String msg){
        return new ApiResult(code,msg,null);
    }
}
