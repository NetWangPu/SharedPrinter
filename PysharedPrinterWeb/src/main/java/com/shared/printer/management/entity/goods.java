package com.shared.printer.management.entity;


public class goods {

    //物品id
    public Integer id;
    //物品名称
    public String time;
    //物品
    public String goods;
    //颜色
    public String color;
    //尺寸
    public String goodSize;
    //包装有无
    public String isPacked;
    //状态1

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGoodSize() {
        return goodSize;
    }

    public void setGoodSize(String goodSize) {
        this.goodSize = goodSize;
    }

    public String getIsPacked() {
        return isPacked;
    }

    public void setIsPacked(String isPacked) {
        this.isPacked = isPacked;
    }


    @Override
    public String toString() {
        return "goods{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", goods='" + goods + '\'' +
                ", color='" + color + '\'' +
                ", goodSize='" + goodSize + '\'' +
                ", isPacked='" + isPacked + '\'' +
                '}';
    }
}
