package com.zhh.dto.wx;

public class GetOpenId {

    private String openid;
    private String session_key;
    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public GetOpenId setOpenid(String openid) {
        this.openid = openid;
        return this;
    }

    public String getSession_key() {
        return session_key;
    }

    public GetOpenId setSession_key(String session_key) {
        this.session_key = session_key;
        return this;
    }

    public String getUnionid() {
        return unionid;
    }

    public GetOpenId setUnionid(String unionid) {
        this.unionid = unionid;
        return this;
    }
}
