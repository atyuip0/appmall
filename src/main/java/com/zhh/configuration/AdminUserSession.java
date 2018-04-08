package com.zhh.configuration;

import com.zhh.repository.entity.AdminUser;
import com.zhh.repository.entity.User;

public class AdminUserSession {

    private AdminUser adminUser;

    private String token;

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public String getToken() {
        return token;
    }

    public AdminUserSession setToken(String token) {
        this.token = token;
        return this;
    }
}
