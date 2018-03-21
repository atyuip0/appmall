package com.zhh.repository.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="t_goods")
public class Goods extends BaseEntity {

    private Long goodsid;

    private Long categoryid;

    private String goodsname;

    private String subtitle;

    private Integer goodstype;

    private Long price;

    private Long marketprice;

    private Long purchaserprice;

    private Integer paixu;

    private Integer state;

    private Integer storedcount;

    private Integer outcount;

    private String spec;

    private String pics;

    private String pic;

    private String manufactory;

    private String reseller;

    private String producingarea;

    private String remark;

    private Integer logisticsId;

    private Long logistics;

    private String marketpriceStr;

    private String priceStr;

    private String description;

    @Id
    public Long getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Long goodsid) {
        this.goodsid = goodsid;
    }

    public Long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(Integer goodstype) {
        this.goodstype = goodstype;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(Long marketprice) {
        this.marketprice = marketprice;
    }

    public Long getPurchaserprice() {
        return purchaserprice;
    }

    public void setPurchaserprice(Long purchaserprice) {
        this.purchaserprice = purchaserprice;
    }

    public Integer getPaixu() {
        return paixu;
    }

    public void setPaixu(Integer paixu) {
        this.paixu = paixu;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getStoredcount() {
        return storedcount;
    }

    public void setStoredcount(Integer storedcount) {
        this.storedcount = storedcount;
    }

    public Integer getOutcount() {
        return outcount;
    }

    public void setOutcount(Integer outcount) {
        this.outcount = outcount;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getManufactory() {
        return manufactory;
    }

    public void setManufactory(String manufactory) {
        this.manufactory = manufactory;
    }

    public String getReseller() {
        return reseller;
    }

    public void setReseller(String reseller) {
        this.reseller = reseller;
    }

    public String getProducingarea() {
        return producingarea;
    }

    public void setProducingarea(String producingarea) {
        this.producingarea = producingarea;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Long getLogistics() {
        return logistics;
    }

    public void setLogistics(Long logistics) {
        this.logistics = logistics;
    }

    @Transient
    public String getPriceStr() {
        return BigDecimal.valueOf(getPrice(),2).toString();
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    @Transient
    public String getMarketpriceStr() {
        return BigDecimal.valueOf(getMarketprice(),2).toString();
    }

    public void setMarketpriceStr(String marketpriceStr) {
        this.marketpriceStr = marketpriceStr;
    }
}