package com.mmall.constant;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;


/**
 * Created by lanbingdepingguo on 2017/7/24.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T>  implements Serializable {
    private int status;
    private String message;
    private T data;

    public ServerResponse(int status) {
        this.status = status;
    }

    public ServerResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }


    public ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }
    public ServerResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data= data;
    }
    @JsonIgnore
    public boolean isSuccess(){
        return status==ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse(ResponseCode.SUCCESS.getCode());

    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg);

    }
    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg,data);

    }
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),data);

    }
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse(ResponseCode.FAIL.getCode());

    }


    public static <T> ServerResponse<T> createByErrorMessage(String msg){
        return new ServerResponse(ResponseCode.FAIL.getCode(),msg);

    }
    public static <T> ServerResponse<T> createByErrorCodeMessage(int code,String msg){
        return new ServerResponse(code,msg);

    }





























}
