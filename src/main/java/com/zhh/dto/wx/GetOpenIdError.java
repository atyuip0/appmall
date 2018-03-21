package com.zhh.dto.wx;

public class GetOpenIdError {

    private String errcode;

    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public GetOpenIdError setErrcode(String errcode) {
        this.errcode = errcode;
        return this;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public GetOpenIdError setErrmsg(String errmsg) {
        this.errmsg = errmsg;
        return this;
    }
}
