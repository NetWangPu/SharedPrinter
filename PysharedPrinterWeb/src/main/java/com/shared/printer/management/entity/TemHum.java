package com.shared.printer.management.entity;

import java.time.LocalDateTime;

public class TemHum {

    //id
    public Integer id;
    //时间
    public String time;
    //温度
    public String tem;
    //湿度
    public String hum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateTime() {
        return time;
    }

    public void setDateTime(String time) {
        this.time = time;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    @Override
    public String toString() {
        return "TemHum{" +
                "id=" + id +
                ", dateTime='" + time + '\'' +
                ", tem='" + tem + '\'' +
                ", hum='" + hum + '\'' +
                '}';
    }
}
