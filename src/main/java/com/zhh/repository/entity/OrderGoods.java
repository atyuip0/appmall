package com.zhh.repository.entity;

import com.zhh.constants.OrderStatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="t_order_goods")
public class OrderGoods extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;

    private Long userId;

    private Long goodsId;

    private Long amount;

    private Long amountGoods;

    private Long amountLogistics;

    @Transient
    private String amountStr;

    @Transient
    private String amountGoodsStr;

    @Transient
    private String amountLogisticsStr;

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

    @ManyToOne()
    @JoinColumn(name="orderId",insertable = false,updatable = false)
    private Order order;

    @OneToOne
    @JoinColumn(name="goodsId",insertable = false,updatable = false)
    private Goods goods;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
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