package com.shared.printer.management.entity;

public class printer {

    public Integer id;

    //打印机中的纸张数量
    public Integer Numberofpaper;
    //打印机的位置
    public String position;
    //打印机的状态
    public String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberofpaper() {
        return Numberofpaper;
    }

    public void setNumberofpaper(Integer numberofpaper) {
        Numberofpaper = numberofpaper;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "printer{" +
                "id=" + id +
                ", Numberofpaper=" + Numberofpaper +
                ", position='" + position + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
