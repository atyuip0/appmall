package com.zhh.configuration;

import com.zhh.repository.entity.AdminUser;
import com.zhh.repository.entity.User;
import com.zhh.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

public class AdminUserSessionMap {

    private static ConcurrentSkipListMap<String,AdminUserSession> userSessionMap = new ConcurrentSkipListMap();

    public static String genAdminUserSession(AdminUser user, String session_key){
        String token = UUID.randomUUID().toString().replace("-","").substring(0,32);
        AdminUserSession userSession = new AdminUserSession();
        userSession.setAdminUser(user);
        userSession.setToken(token);
        userSessionMap.put(token,userSession);
        return token;
    }

    public static AdminUserSession getAdminUserSession(String token){
        return token==null?null:userSessionMap.get(token);
    }

    public static AdminUserSession getAdminUserSession(HttpServletRequest request){
        String token = TokenUtil.getToken(request);
        return token==null?null:userSessionMap.get(token);
    }

    public static AdminUserSession rmAdminUserSession(String token){
        return token==null?null:userSessionMap.remove(token);
    }
}
