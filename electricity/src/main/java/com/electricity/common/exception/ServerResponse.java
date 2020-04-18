package com.electricity.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.electricity.common.enums.ResponseEnum;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Description: 服务返回封装
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

// 保证序列化json的时候,如果对象为null,则不会转化为json
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ServerResponse<T> implements Serializable {

    private Integer status;
    private String msg;
    private String url;
    private T data;

    private ServerResponse() {
    }

    private ServerResponse(Integer status) {
        this.status = status;
    }

    private ServerResponse(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(Integer status, String msg, T data) {
        this.data = data;
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(Integer status, String msg, String url) {
        this.status = status;
        this.msg = msg;
        this.url = url;
    }

    // 使该对象不在json序列化中
    @JsonIgnore
    public boolean isSuccess() {
        return this.status.equals(ResponseEnum.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createByERROR(T data) {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseEnum.ERROR.getCode(), ResponseEnum.ERROR.getDesc());
    }
    public static <T> ServerResponse<T> createByError(T data) {
        return new ServerResponse<T>(ResponseEnum.ERROR.getCode(), data);
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        return new ServerResponse<T>(ResponseEnum.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(Integer errorCode, String errorMessage) {
        return new ServerResponse<T>(errorCode, errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorInfo(String msg, String url) {
        return new ServerResponse<T>(ResponseEnum.EXCEPTION.getCode(), msg, url);
    }


}
