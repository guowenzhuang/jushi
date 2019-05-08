package com.jushi.api.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private boolean flag;
    private String code;
    private String message;
    private  Object data;
    public static Result success(String message){
        return new Result(true, StatusCode.OK.value(),message);
    }
    public static Result success(String message,Object data){
        return new Result(true, StatusCode.OK.value(),message,data);
    }
    public static Result error(String message){
        return new Result(false, StatusCode.ERROR.value(),message);
    }

    public static Result error(String message,Object data){
        return new Result(false, StatusCode.ERROR.value(),message,data);
    }



    public Result(boolean flag, String code, String message, Object
            data) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Result(boolean flag, String code, String message) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}