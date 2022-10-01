package com.example.demo1.bean;

//写一个打印订单的实体类
public class PrintOrder {
    //订单id
    private int id;
    //订单号
    private String orderNumber;
    //订单类型
    private String orderType;
    //订单状态
    private String orderStatus;
    //打印份数
    private int printCopies;
    //文件下载地址
    private String fileDownloadUrl;

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPrintCopies() {
        return printCopies;
    }

    public void setPrintCopies(int printCopies) {
        this.printCopies = printCopies;
    }

    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }
}
