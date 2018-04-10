package com.zhh.controller.admin;


import com.zhh.configuration.AdminUserSession;
import com.zhh.configuration.AdminUserSessionMap;
import com.zhh.dto.admin.AdminLoginReq;
import com.zhh.dto.BaseResp;
import com.zhh.repository.AdminUserRepository;
import com.zhh.repository.entity.AdminUser;
import com.zhh.utils.IpUtil;
import com.zhh.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginAdminController extends BaseAdminCtl {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @PostMapping(value = "/login")
    public Object login(@RequestBody AdminLoginReq adminLoginReq,
                        HttpServletRequest request) {
        AdminUser adminUser = adminUserRepository.findByUserNameAndPwdIs(adminLoginReq.getUsername(),adminLoginReq.getPassword());
        if (adminUser==null){
            return BaseResp.ERRORRESP;
        }
        adminUserRepository.updateLastLoginById(IpUtil.getIpAddr(request), LocalDateTime.now(),adminUser.getId());
        String token = AdminUserSessionMap.genAdminUserSession(adminUser, UUIDUtil.genUUID());
        Map map = new HashMap(1);
        map.put("token",token);
        map.put("roles",new String[]{"admin"});
        map.put("introduction","管理员");
        map.put("name","admin");
        return BaseResp.SUCCESSRESP.setData(map);
    }

    @PostMapping(value = "/logout")
    public Object logout(HttpServletRequest request) {
        String token = request.getHeader("X-Token");
        AdminUserSessionMap.rmAdminUserSession(token);
        return BaseResp.SUCCESSRESP;
    }

    @GetMapping(value = "/info")
    public Object info(HttpServletRequest request,String token) {
        AdminUserSession adminUserSession = AdminUserSessionMap.getAdminUserSession(token);
        if(adminUserSession==null){
            return BaseResp.ERRORRESP;
        }
        Map map = new HashMap(1);
        map.put("token",token);
        map.put("roles",new String[]{"admin"});
        map.put("introduction","管理员");
        map.put("name","admin");
        return BaseResp.SUCCESSRESP.setData(map);
    }
}
