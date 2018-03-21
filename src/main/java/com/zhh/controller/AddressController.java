package com.zhh.controller;

import com.zhh.configuration.UserSessionMap;
import com.zhh.dto.BaseResp;
import com.zhh.dto.UserSession;
import com.zhh.repository.AddressRepository;
import com.zhh.repository.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收货地址
 * @author zhang.haihe
 */
@RequestMapping("/user/shipping-address")
@RestController
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/default")
    public Object getDefault(@RequestParam("token") String token) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        Address address = addressRepository.getByUserIdAndIsDefaultIs(111L,true);
        return BaseResp.SUCCESSRESP.setData(address);
    }

    @GetMapping("/add")
    public Object add(@RequestBody Address address) {
        Address address_ = addressRepository.save(address);
        return BaseResp.SUCCESSRESP;
    }

    @GetMapping("/list")
    public Object list() {
        Iterable address_ = addressRepository.findAll();
        return BaseResp.SUCCESSRESP.setData(address_);
    }

}
