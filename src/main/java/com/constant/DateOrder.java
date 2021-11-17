package com.constant;

/**
 * @Description
 * @Author Hexiaoshu
 * @Date 2021/11/8
 * @modify
 */
public enum DateOrder {
    //日期排序
    //desc 最近数据在前
    DESC(true, "降序"),
    ASC(false, "升序");

    private final  Boolean order;
    private final  String message;
    DateOrder(Boolean order, String message) {
        this.order=order;
        this.message = message;
    }


    public Boolean getOrder() {return order;}
    public String getMessage() {return message;}

}
