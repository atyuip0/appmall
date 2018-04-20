package com.zhh.repository.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="t_goods")
public class Goods extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Transient
    private String logisticsStr;

    @Transient
    private String marketpriceStr;

    @Transient
    private String priceStr;

    @Transient
    private String purchaserpriceStr;

    private String description;

    private LocalDateTime pushTime;

    @OneToOne
    @JoinColumn(name="categoryid",insertable = false,updatable = false)
    private Category category;

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

    public String getPriceStr() {
        if(StringUtils.isNotBlank(this.priceStr)){
            return this.priceStr;
        }
        return BigDecimal.valueOf(getPrice(),2).toString();
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getMarketpriceStr() {
        if(StringUtils.isNotBlank(this.marketpriceStr)){
            return this.marketpriceStr;
        }
        return BigDecimal.valueOf(getMarketprice(),2).toString();
    }

    public void setMarketpriceStr(String marketpriceStr) {
        this.marketpriceStr = marketpriceStr;
    }

    public LocalDateTime getPushTime() {
        return pushTime;
    }

    public void setPushTime(LocalDateTime pushTime) {
        this.pushTime = pushTime;
    }

    public String getLogisticsStr() {
        if(StringUtils.isNotBlank(this.logisticsStr)){
            return this.logisticsStr;
        }
        return BigDecimal.valueOf(getLogistics(),2).toString();
    }

    public void setLogisticsStr(String logisticsStr) {
        this.logisticsStr = logisticsStr;
    }

    public String getPurchaserpriceStr() {
        if(StringUtils.isNotBlank(this.purchaserpriceStr)){
            return this.purchaserpriceStr;
        }
        return BigDecimal.valueOf(getPurchaserprice(),2).toString();
    }

    public void setPurchaserpriceStr(String purchaserpriceStr) {
        this.purchaserpriceStr = purchaserpriceStr;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}