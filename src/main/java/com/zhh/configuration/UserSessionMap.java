package com.zhh.configuration;

import com.zhh.repository.entity.User;

import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

public class UserSessionMap {

    private static ConcurrentSkipListMap<String,UserSession> userSessionMap = new ConcurrentSkipListMap();

    public static String genUserSession(User user, String session_key){
        String token = UUID.randomUUID().toString().replace("-","").substring(0,32);
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setSession_key(session_key);
        userSession.setToken(token);
        userSessionMap.put(token,userSession);
        return token;
    }

    public static UserSession getUserSession(String token){
//        return userSessionMap.get(token);
        User user = new User();
        user.setId(110L);
        user.setNickname("haha");
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setSession_key("");
        userSession.setToken(token);
        return userSession;
    }
}
