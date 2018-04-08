package com.zhh.controller;

import com.zhh.configuration.UserSessionMap;
import com.zhh.dto.BaseResp;
import com.zhh.configuration.UserSession;
import com.zhh.repository.AddressRepository;
import com.zhh.repository.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
        Address address = addressRepository.getByUserIdAndIsDefaultIs(userSession.getUser().getId(),true);
        return BaseResp.SUCCESSRESP.setData(address);
    }

    @GetMapping("/{id}")
    public Object get(@RequestParam("token") String token,@PathVariable Long id) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        Address address = addressRepository.findById(id).get();
        if(address.getUserId().equals(userSession.getUser().getId())){
            return BaseResp.SUCCESSRESP.setData(address);
        }
        return BaseResp.ERRORRESP;
    }

    @DeleteMapping("/{id}")
    public Object del(@RequestParam(value = "token" ,required = false) String token,@PathVariable Long id) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        Address address = addressRepository.findById(id).get();
        if(address.getUserId().equals(userSession.getUser().getId())){
            addressRepository.deleteById(id);
            return BaseResp.SUCCESSRESP;
        }
        return BaseResp.ERRORRESP;
    }

    @PostMapping("/add")
    public Object add(@RequestParam("token") String token, @RequestBody Address address) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        address.setUserId(userSession.getUser().getId());
        address.setAddBy(userSession.getUser().getNickname());
        address.setAddTime(LocalDateTime.now());
        Address address_ = addressRepository.save(address);
        return BaseResp.SUCCESSRESP.setData(address_);
    }

    @PostMapping("/update")
    public Object update(@RequestParam(value = "token",required = false) String token, @RequestBody Address address) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        Address address_ = addressRepository.findById(address.getId()).get();
        if(!address_.getUserId().equals(userSession.getUser().getId())){
            return BaseResp.ERRORRESP;
        }
        address.setUserId(userSession.getUser().getId());
        address.setAddBy(address_.getAddBy());
        address.setAddTime(address_.getAddTime());
        address.setUpBy(userSession.getUser().getNickname());
        address.setUpTime(LocalDateTime.now());
        address_ = addressRepository.save(address);
        return BaseResp.SUCCESSRESP.setData(address_);
    }

    @GetMapping("/list")
    public Object list(@RequestParam("token") String token) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        Iterable address_ = addressRepository.findByUserId(userSession.getUser().getId(), Sort.by(Sort.Direction.DESC,"addTime"));
        return BaseResp.SUCCESSRESP.setData(address_);
    }

}
