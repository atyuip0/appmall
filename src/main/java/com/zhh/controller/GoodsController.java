package com.zhh.controller;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.ObjectCodec;
import com.zhh.dto.BaseResp;
import com.zhh.repository.CategoryRepository;
import com.zhh.repository.GoodsRepository;
import com.zhh.repository.entity.Banner;
import com.zhh.repository.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Service;

import java.util.*;

@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(value = "/shop/goods/detail" )
    public Object goods(@RequestParam("id") Long id) {
        ServiceLoader serviceLoader = ServiceLoader.load(JsonFactory.class);
        Iterator iterable = serviceLoader.iterator();
        while (iterable.hasNext()){
            System.out.println(iterable.next().getClass().toString());
        }
        ServiceLoader serviceLoader_ = ServiceLoader.load(ObjectCodec.class);
        Iterator iterable_ = serviceLoader_.iterator();
        while (iterable_.hasNext()){
            System.out.println(iterable_.next().getClass().toString());
        }
        Service.providers(JsonFactory.class);
        Goods goods = goodsRepository.findById(id).get();
        Map<String,Object> map = new HashMap<>(3);
        map.put("basicInfo",goods);
        map.put("properties","");
        map.put("pics",goods.getPics().split(";"));
        map.put("category",categoryRepository.findById(goods.getCategoryid()).get());
        return BaseResp.SUCCESSRESP.setData(map);
    }

}
