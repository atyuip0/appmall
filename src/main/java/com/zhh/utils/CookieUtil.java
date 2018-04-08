package com.zhh.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    private static final int COOKIE_MAX_AGE = 2592000;
    public static final int COOKIE_LIVE_TIME_ONE_HOUR = 3600;

    public CookieUtil() {
    }

    public static void removeCookie(HttpServletResponse response, Cookie cookie) {
        if (cookie != null) {
            cookie.setPath("/");
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

    }

    public static void removeCookie(HttpServletResponse response, Cookie cookie, String domain) {
        if (cookie != null) {
            cookie.setPath("/");
            cookie.setValue("");
            cookie.setMaxAge(0);
            cookie.setDomain(domain);
            response.addCookie(cookie);
        }

    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && name != null && name.length() != 0) {
            Cookie cookie = null;

            for(int i = 0; i < cookies.length; ++i) {
                if (cookies[i].getName().equals(name)) {
                    cookie = cookies[i];
                }
            }

            return cookie;
        } else {
            return null;
        }
    }

    public static void setCookie(HttpServletResponse response, String name, String value, String domain) {
        setCookie(response, name, value, domain, 2592000);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, String domain, int maxAge) {
        if (value == null) {
            value = "";
        }

        Cookie cookie = new Cookie(name, value);
        if (maxAge != 0) {
            cookie.setMaxAge(maxAge);
        } else {
            cookie.setMaxAge(2592000);
        }

        cookie.setPath("/");
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }

        response.addCookie(cookie);
    }
}
