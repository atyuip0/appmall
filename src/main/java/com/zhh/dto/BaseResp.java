package com.zhh.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;

import java.util.ServiceLoader;

//@JsonTypeInfo(use = )
public class BaseResp {

    private String code;

    private String msg;

    private Object data;

    public BaseResp(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static BaseResp SUCCESSRESP = new BaseResp("0","操作成功");

    public static BaseResp ERRORRESP = new BaseResp("9","操作失败");

    public String getCode() {
        return code;
    }

    public BaseResp setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResp setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public BaseResp setData(Object data) {
        this.data = data;
        return this;
    }
}
