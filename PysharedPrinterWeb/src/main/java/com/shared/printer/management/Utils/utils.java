package com.shared.printer.management.Utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.util.Iterator;
import java.util.Map;

public class utils {

    //获取时间戳 用于存放到数据库中
    public static String getTimeStamp() {
        long time = System.currentTimeMillis();
        String timeStamp = String.valueOf(time);
        return timeStamp;
    }
}
