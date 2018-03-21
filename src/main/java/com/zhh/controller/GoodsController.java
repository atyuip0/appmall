package com.zhh.controller;


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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/shop/goods/detail")
    public Object bannerList(@RequestParam("id") Long id) {
        Goods goods = goodsRepository.findById(id).get();
        Map<String,Object> map = new HashMap<>(3);
        map.put("basicInfo",goods);
        map.put("properties","");
        map.put("pics",goods.getPics().split(";"));
        map.put("category",categoryRepository.findById(goods.getCategoryid()).get());
        return BaseResp.SUCCESSRESP.setData(map);
    }

}
