package com.zhh.repository.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zhh.constants.OrderStatusEnum;
import com.zhh.dto.BaseResp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_order")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long amount;

    private Long amountGoods;

    private Long amountLogistics;

    @Transient
    private String amountStr;

    @Transient
    private String amountGoodsStr;

    @Transient
    private String amountLogisticsStr;

    private Long amountPay;

    private Integer goodsNumber;

    private String logisticsname;

    private String expressno;

    private String linkman;

    private String address;

    private String mobile;

    private String code;

    private String remark;

    private Integer status;

    @Transient
    private String statusStr;

    @OneToMany(mappedBy="order")
    private List<OrderGoods> orderGoodsList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmountGoods() {
        return amountGoods;
    }

    public void setAmountGoods(Long amountGoods) {
        this.amountGoods = amountGoods;
    }

    public Long getAmountLogistics() {
        return amountLogistics;
    }

    public void setAmountLogistics(Long amountLogistics) {
        this.amountLogistics = amountLogistics;
    }

    public Long getAmountPay() {
        return amountPay;
    }

    public void setAmountPay(Long amountPay) {
        this.amountPay = amountPay;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getLogisticsname() {
        return logisticsname;
    }

    public void setLogisticsname(String logisticsname) {
        this.logisticsname = logisticsname == null ? null : logisticsname.trim();
    }

    public String getExpressno() {
        return expressno;
    }

    public void setExpressno(String expressno) {
        this.expressno = expressno == null ? null : expressno.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrderGoods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoods> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    @Transient
    public String getAmountStr() {
        return BigDecimal.valueOf(getAmount(),2).toString();
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    @Transient
    public String getAmountGoodsStr() {
        return BigDecimal.valueOf(getAmountGoods(),2).toString();
    }

    public void setAmountGoodsStr(String amountGoodsStr) {
        this.amountGoodsStr = amountGoodsStr;
    }

    @Transient
    public String getAmountLogisticsStr() {
        return BigDecimal.valueOf(getAmountLogistics(),2).toString();
    }

    public void setAmountLogisticsStr(String amountLogisticsStr) {
        this.amountLogisticsStr = amountLogisticsStr;
    }

    @Transient
    public String getStatusStr() {
        return OrderStatusEnum.getName(getStatus());
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}