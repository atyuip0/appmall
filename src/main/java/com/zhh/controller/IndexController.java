package com.zhh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhh.dto.BaseResp;
import com.zhh.repository.*;
import com.zhh.repository.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/banner/list")
    public Object bannerList(@RequestParam("key") Integer key) {
        List<Banner> banners = bannerRepository.findByTypeAndStatus(key,0,new Sort(Sort.Direction.DESC, "paixu"));
        return BaseResp.SUCCESSRESP.setData(banners);
    }

    @GetMapping("/shop/goods/category/all")
    public Object categoryList() {
        List<Category> categories = categoryRepository.findByParentIdAndStatus(-1L,0,new Sort(Sort.Direction.DESC, "paixu"));
        return BaseResp.SUCCESSRESP.setData(categories);
    }

    @GetMapping("/shop/goods/list")
    public Object goodsList(@RequestParam("categoryId") Long categoryId,@RequestParam("nameLike") String nameLike) {
        List<Goods> goodsList;
        if(categoryId==null){
            goodsList = goodsRepository.findByStateAndGoodsnameLike(0,String.format("%%%s%%",nameLike),new Sort(Sort.Direction.DESC, "paixu"));
        }else {
            goodsList = goodsRepository.findByCategoryidAndStateAndGoodsnameLike(categoryId,0,String.format("%%%s%%",nameLike),new Sort(Sort.Direction.DESC, "paixu"));
        }
        return BaseResp.SUCCESSRESP.setData(goodsList);
    }

    @GetMapping("/notice/list")
    public Object noticeList(@RequestParam("pageSize") Integer pageSize) {
        Page<Notice> notices = noticeRepository.findByStatus(0,PageRequest.of(0, pageSize,new Sort(Sort.Direction.DESC, "paixu")));
        return BaseResp.SUCCESSRESP.setData(notices.getContent());
    }

}
