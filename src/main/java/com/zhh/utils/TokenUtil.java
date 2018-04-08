package com.zhh.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {

    public final static String Admin_Token = "Admin-Token";

    public static String getToken(HttpServletRequest request){
        String token = request.getHeader("X-Token");
        token = StringUtils.isBlank(token) ? request.getParameter("token") : token;
        CookieUtil.getCookie(request,Admin_Token);
        token = StringUtils.isBlank(token) ? CookieUtil.getCookieValue(request,Admin_Token) : token;
        return token;
    }

}
