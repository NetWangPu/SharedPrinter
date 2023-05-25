package com.shared.printer.management.entity;

public class FileBean {

    String name;
    //多少MB
    String size;
    //上传时间
    String time;
    //上传者
    String uploader;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", time='" + time + '\'' +
                ", uploader='" + uploader + '\'' +
                '}';
    }
}
