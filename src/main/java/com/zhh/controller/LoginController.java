package com.zhh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhh.constants.AppConstants;
import com.zhh.dto.BaseResp;
import com.zhh.configuration.UserSession;
import com.zhh.dto.wx.GetOpenId;
import com.zhh.dto.wx.UserInfoEncrypted;
import com.zhh.http.OkHttpService;
import com.zhh.repository.ParameterRepository;
import com.zhh.repository.UserRepository;
import com.zhh.repository.entity.User;
import com.zhh.utils.MyAES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.zhh.configuration.UserSessionMap.genUserSession;
import static com.zhh.configuration.UserSessionMap.getUserSession;

/**
 * @author zhang.haihe
 */
@RequestMapping("/user")
@RestController
public class LoginController {

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OkHttpService okHttpService;

    @Autowired
    ObjectMapper objectMapper;

    String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&js_code=%s&grant_type=%s";

    @GetMapping("/login")
    public Object login(@RequestParam("code") String code) {
        String appId = parameterRepository.getByParamNameIs(AppConstants.APPID).getParamValue();
        String secret = parameterRepository.getByParamNameIs(AppConstants.APPSECRET).getParamValue();
        String grantType = AppConstants.GRANT_TYPE;
        try {
            String resp = okHttpService.get(String.format(url,appId,secret,code,grantType));
            GetOpenId getOpenId = null;
            if(!StringUtils.isEmpty(resp)){
                getOpenId = objectMapper.readValue(resp, GetOpenId.class);
            }
            if(getOpenId!=null && !StringUtils.isEmpty(getOpenId.getOpenid())){
                User user = userRepository.getByOpenIdIs(getOpenId.getOpenid());
                if (user==null){
                    user = new User();
                    user.setOpenId(getOpenId.getOpenid());
                    user.setStatus(0);
                }
                if(user.getStatus().equals(0)){
                    return new BaseResp("10000","注册");
                }
                String token = genUserSession(user,getOpenId.getSession_key());
                Map map = new HashMap(1);
                map.put("token",token);
                return BaseResp.SUCCESSRESP.setData(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return new BaseResp("10000","注册");
        return BaseResp.ERRORRESP;
    }


    @GetMapping("/register/complex")
    public Object register(@RequestParam("code") String code,@RequestParam("encryptedData") String encryptedData,
                           @RequestParam("iv") String iv) {
        String appId = parameterRepository.getByParamNameIs(AppConstants.APPID).getParamValue();
        String secret = parameterRepository.getByParamNameIs(AppConstants.APPSECRET).getParamValue();
        String grantType = AppConstants.GRANT_TYPE;
        try {
            String resp = okHttpService.get(String.format(url,appId,secret,code,grantType));
            GetOpenId getOpenId = null;
            if(!StringUtils.isEmpty(resp)){
                getOpenId = objectMapper.readValue(resp, GetOpenId.class);
            }
            if(getOpenId!=null && !StringUtils.isEmpty(getOpenId.getOpenid())){
                User user = new User();
                user.setOpenId(getOpenId.getOpenid());
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] encryptedData_ = decoder.decode(encryptedData);
                byte[] sessionKey = decoder.decode(getOpenId.getSession_key());
                byte[] iv_ = decoder.decode(iv);
                String userInfoStr = MyAES.AesCbcDecode(encryptedData_,MyAES.getSecretKey(sessionKey),iv_);
                UserInfoEncrypted userInfoEncrypted = objectMapper.readValue(userInfoStr,UserInfoEncrypted.class);
                if(user.getOpenId().equals(userInfoEncrypted.getOpenId())){
                    user.setNickname(userInfoEncrypted.getNickname());
                    user.setGender(new Integer(userInfoEncrypted.getGender()));
                    user.setCity(userInfoEncrypted.getCity());
                    user.setCountry(userInfoEncrypted.getCountry());
                    user.setAvatarurl(userInfoEncrypted.getAvatarUrl());
                    user.setProvince(userInfoEncrypted.getProvince());
                    userRepository.save(user);
                    String token = genUserSession(user,getOpenId.getSession_key());
                    Map map = new HashMap(1);
                    map.put("token",token);
                    return BaseResp.SUCCESSRESP.setData(map);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BaseResp.ERRORRESP;
    }


    @GetMapping("/check-token")
    public Object checkToken(@RequestParam("token") String token) {
        UserSession userSession = getUserSession(token);
        if(userSession==null) {
            return BaseResp.ERRORRESP;
        } else {
            return BaseResp.SUCCESSRESP;
        }
    }

}
