package com.zhh.controller.admin;

import com.zhh.dto.BaseResp;
import com.zhh.dto.GoodsQueryReq;
import com.zhh.repository.GoodsRepository;
import com.zhh.repository.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController("AdminGoodsController")
public class GoodsController extends BaseAdminCtl {

    @Autowired
    private GoodsRepository goodsRepository;

    @PostMapping("/goods/list")
    public Object goodsList(@RequestBody GoodsQueryReq goodsQueryReq) {
        Page goodsList;
        goodsList = goodsRepository.findAll(Pageable.unpaged());
        return BaseResp.SUCCESSRESP.setData(goodsList);
    }

    @PostMapping("/goods/add")
    public Object add(@RequestBody GoodsQueryReq goodsQueryReq) {
        Page goodsList;
        goodsList = goodsRepository.findAll(Pageable.unpaged());
        return BaseResp.SUCCESSRESP.setData(goodsList);
    }

    @PostMapping("/goods/update")
    public Object update(@RequestBody GoodsQueryReq goodsQueryReq) {
        Page goodsList;
        goodsList = goodsRepository.findAll(Pageable.unpaged());
        return BaseResp.SUCCESSRESP.setData(goodsList);
    }

}
