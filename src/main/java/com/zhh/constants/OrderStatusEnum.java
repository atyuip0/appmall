package com.zhh.constants;

public enum OrderStatusEnum {

    START(0,"待付款"),
    CANCEL(1,"订单已取消"),
    PAYED(2,"已付款"),
    SENDED(3,"已发货"),
    RECEIVED(4,"交易完成")
    ;

    private int status;

    private String desc;

    OrderStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getName(Integer status){

        if (status == null){
            return "其他";
        }
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()){
            if(status.equals(orderStatusEnum.getStatus())){
                return orderStatusEnum.getDesc();
            }
        }
        return "其他";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
