package com.zhh.controller.admin;

import com.zhh.dto.BaseResp;
import com.zhh.dto.admin.GoodsQueryReq;
import com.zhh.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController("AdminGoodsController")
public class GoodsController extends BaseAdminCtl {

    @Autowired
    private GoodsRepository goodsRepository;

    @PostMapping("/goods/list")
    public Object goodsList(@RequestBody GoodsQueryReq goodsQueryReq) {
        Page goodsList = goodsRepository.findAll(PageRequest.of((goodsQueryReq.getPage()-1),goodsQueryReq.getLimit()));
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
