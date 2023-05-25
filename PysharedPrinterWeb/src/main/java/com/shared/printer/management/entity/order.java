package com.shared.printer.management.entity;

public class order {

    public Integer id;
    //订单人ID
    public Integer OrdererID;
    //打印机printid
    public Integer printid;
    //订单状态 未打印|打印中|已打印
    public String state;
    //打印时间
    public String  Printtime ;
    //打印份数
    public Integer Printnumber ;
    //打印颜色 1黑白 2彩色
    public Integer Printcolor ;
    //打印纸张数量
    public Integer Printpaper ;
    //打印的文件 这是一个下载链接
    public String  Printfile ;
    //打印价格
    public Double Printprice ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdererID() {
        return OrdererID;
    }

    public void setOrdererID(Integer ordererID) {
        OrdererID = ordererID;
    }

    public Integer getPrintid() {
        return printid;
    }

    public void setPrintid(Integer printid) {
        this.printid = printid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrinttime() {
        return Printtime;
    }

    public void setPrinttime(String printtime) {
        Printtime = printtime;
    }

    public Integer getPrintnumber() {
        return Printnumber;
    }

    public void setPrintnumber(Integer printnumber) {
        Printnumber = printnumber;
    }

    public Integer getPrintcolor() {
        return Printcolor;
    }

    public void setPrintcolor(Integer printcolor) {
        Printcolor = printcolor;
    }

    public Integer getPrintpaper() {
        return Printpaper;
    }

    public void setPrintpaper(Integer printpaper) {
        Printpaper = printpaper;
    }

    public String getPrintfile() {
        return Printfile;
    }

    public void setPrintfile(String printfile) {
        Printfile = printfile;
    }

    public Double getPrintprice() {
        return Printprice;
    }

    public void setPrintprice(Double printprice) {
        Printprice = printprice;
    }

    @Override
    public String toString() {
        return "order{" +
                "id=" + id +
                ", OrdererID=" + OrdererID +
                ", printid=" + printid +
                ", state='" + state + '\'' +
                ", Printtime='" + Printtime + '\'' +
                ", Printnumber=" + Printnumber +
                ", Printcolor=" + Printcolor +
                ", Printpaper=" + Printpaper +
                ", Printfile='" + Printfile + '\'' +
                ", Printprice=" + Printprice +
                '}';
    }
}
