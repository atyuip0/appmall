package com.zhh.configuration;

import com.zhh.dto.BaseResp;
import com.zhh.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class AdminWebSecurityConfig implements WebMvcConfigurer {

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大KB,MB
//        factory.setMaxFileSize("2MB");
//        //设置总上传数据总大小
//        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        addInterceptor.excludePathPatterns(
                "/v2.0/login**",
                "/v2.0/logout**",
                "/v2.0/img/**",
                "/v2.0/uploadImg.ajax**");
        // 拦截配置
        addInterceptor.addPathPatterns("/v2.0/**").order(-1);
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            if ("OPTIONS".equals(request.getMethod())){
                return true;
            }
            String token = TokenUtil.getToken(request);
            AdminUserSession adminUserSession = AdminUserSessionMap.getAdminUserSession(token);
            if(adminUserSession==null){
                return false;
            }
            return true;
        }
    }
}
