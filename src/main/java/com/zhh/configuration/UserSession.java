package com.zhh.configuration;

import com.zhh.repository.entity.User;

public class UserSession {

    private User user;

    private String session_key;

    private String token;

    public User getUser() {
        return user;
    }

    public UserSession setUser(User user) {
        this.user = user;
        return this;
    }

    public String getSession_key() {
        return session_key;
    }

    public UserSession setSession_key(String session_key) {
        this.session_key = session_key;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserSession setToken(String token) {
        this.token = token;
        return this;
    }
}
