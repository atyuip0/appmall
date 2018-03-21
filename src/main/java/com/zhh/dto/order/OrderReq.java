package com.zhh.dto.order;

public class OrderReq {

    private String token;

    private Boolean calculate;

    private String goodsJsonStr;

    private Integer provinceId;
    private Integer cityId;
    private Integer districtId;
    private String address;
    private String linkMan;
    private String mobile;
    private String code;

    private String remark;

    public Boolean getCalculate() {
        return calculate;
    }

    public void setCalculate(Boolean calculate) {
        this.calculate = calculate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGoodsJsonStr() {
        return goodsJsonStr;
    }

    public void setGoodsJsonStr(String goodsJsonStr) {
        this.goodsJsonStr = goodsJsonStr;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
