package com.zhh.controller.admin;

import com.zhh.configuration.AdminUserSessionMap;
import com.zhh.dto.BaseResp;
import com.zhh.dto.admin.GoodsQueryReq;
import com.zhh.repository.GoodsRepository;
import com.zhh.repository.entity.AdminUser;
import com.zhh.repository.entity.Goods;
import com.zhh.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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
    public Object add(@RequestBody Goods goods, HttpServletRequest request) {
        AdminUser adminUser = AdminUserSessionMap.getAdminUserSession(request).getAdminUser();
        goods.setPrice(new Money(goods.getPriceStr()).getCent());
        goods.setMarketprice(new Money(goods.getMarketpriceStr()).getCent());
        goods.setPurchaserprice(new Money(goods.getPurchaserpriceStr()).getCent());
        goods.setLogistics(new Money(goods.getLogisticsStr()).getCent());
        goods.setGoodstype(0);
        goods.setOutcount(0);
        goods.setPaixu(0);
        goods.setState(1);
        goods.setReseller("");
        goods.setSpec("");
        goods.setPushTime(LocalDateTime.now());
        goods.setAddBy(adminUser.getUserName());
        goods.setAddTime(LocalDateTime.now());
        goodsRepository.save(goods);
        return BaseResp.SUCCESSRESP;
    }

    @GetMapping("/goods/get")
    public Object update(@RequestParam Long goodsid) {
        Goods goods = goodsRepository.findById(goodsid).get();
        return BaseResp.SUCCESSRESP.setData(goods);
    }

    @PostMapping("/goods/update")
    public Object update(@RequestBody GoodsQueryReq goodsQueryReq) {
        Page goodsList;
        goodsList = goodsRepository.findAll(Pageable.unpaged());
        return BaseResp.SUCCESSRESP.setData(goodsList);
    }

}
