package com.monousoooo.example.util;

import com.monousoooo.example.constant.CommonConstants;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public static <T> Response<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> Response<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> Response<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> Response<T> failed() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> Response<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> Response<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> Response<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    public static <T> Response<T> restResult(T data, int code, String msg) {
        Response<T> res = new Response<>();
        res.setCode(code);
        res.setData(data);
        res.setMsg(msg);
        return res;
    }

}