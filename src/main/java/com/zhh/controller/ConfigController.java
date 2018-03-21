package com.zhh.controller;

import com.zhh.constants.AppConstants;
import com.zhh.dto.BaseResp;
import com.zhh.repository.ParameterRepository;
import com.zhh.repository.entity.Address;
import com.zhh.repository.entity.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/config")
@RestController
public class ConfigController {

    @Autowired
    private ParameterRepository parameterRepository;

    @GetMapping("/get-value")
    public Object add(@RequestParam("key") String key) {
        Parameter parameter = parameterRepository.getByParamNameIs(key);
        String value = "";
        if(parameter!=null){
            value = parameter.getParamValue();
        }
        Map map = new HashMap(1);
        map.put("value",value);
        return BaseResp.SUCCESSRESP.setData(map);
    }

}
