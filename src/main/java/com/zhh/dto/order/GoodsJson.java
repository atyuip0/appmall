package com.zhh.dto.order;

public class GoodsJson {

    private Long goodsId;

    private Integer number;

    private String propertyChildIds;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPropertyChildIds() {
        return propertyChildIds;
    }

    public void setPropertyChildIds(String propertyChildIds) {
        this.propertyChildIds = propertyChildIds;
    }
}
