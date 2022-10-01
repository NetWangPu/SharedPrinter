package com.example.demo1.bean;
//一个订单bean 对应数据库中的order_form表
public class OrderForm {
    //订单id
    private int id;
    //订单编号
    private String orderNumber;
    //订单页码数
    private int orderPage;
    //订单是否完成
    private boolean orderFinish;
    //订单总价
    private double orderPrice;
    //订单创建时间
    private String orderCreateTime;
    //订单完成时间
    private String orderFinishTime;
    //订单是否支付
    private boolean orderPay;
    //订单文件下载地址
    private String orderFileUrl;
    /*
    后期添加单双面打印 黑白彩色打印 等等
     */

    //getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderPage() {
        return orderPage;
    }

    public void setOrderPage(int orderPage) {
        this.orderPage = orderPage;
    }

    public boolean isOrderFinish() {
        return orderFinish;
    }

    public void setOrderFinish(boolean orderFinish) {
        this.orderFinish = orderFinish;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getOrderFinishTime() {
        return orderFinishTime;
    }

    public void setOrderFinishTime(String orderFinishTime) {
        this.orderFinishTime = orderFinishTime;
    }

    public boolean isOrderPay() {
        return orderPay;
    }

    public void setOrderPay(boolean orderPay) {
        this.orderPay = orderPay;
    }

    public String getOrderFileUrl() {
        return orderFileUrl;
    }

    public void setOrderFileUrl(String orderFileUrl) {
        this.orderFileUrl = orderFileUrl;
    }
}
