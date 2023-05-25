package com.shared.printer.management.controller;


import com.shared.printer.management.MQTT.MsgGateway;
import com.shared.printer.management.entity.goods;
import com.shared.printer.management.entity.order;
import com.shared.printer.management.mapper.orderMapper;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController // 声明这个是一个控制器
public class excleController {

    @Autowired
    private orderMapper OrderMapper;

    @ApiOperation(value="将订单列表表导出成Excel", notes="返回文件流")
    @PostMapping("/exportgoods")
    //返回文件流
    public byte[] exportGoods() {
        List<order> orders = OrderMapper.findAll();
        //创建Excel
        String sheetName = "订单列表";
        String[] title = {"下单人ID", "打印机ID", "订单状态", "下单时间", "打印数量", "打印颜色", "打印纸张", "打印价格"};
        String[][] values = new String[orders.size()][8];
        for (int i = 0; i < orders.size(); i++) {
            values[i][0] = orders.get(i).getOrdererID().toString();
            values[i][1] = orders.get(i).getPrintid().toString();
            values[i][2] = orders.get(i).getState();
            values[i][3] = orders.get(i).getPrinttime();
            values[i][4] = orders.get(i).getPrintnumber().toString();
            values[i][5] = orders.get(i).getPrintcolor().toString();
            values[i][6] = orders.get(i).getPrintpaper().toString();
            values[i][7] = orders.get(i).getPrintprice().toString();
        }
        //使用POI创建Excel 不使用工具类直接在这里写
        SXSSFWorkbook wb = new SXSSFWorkbook();
        //创建sheet
        wb.createSheet(sheetName);
        //创建标题头 title
        wb.getSheet(sheetName).createRow(0);
        for (int i = 0; i < title.length; i++) {
            wb.getSheet(sheetName).getRow(0).createCell(i).setCellValue(title[i]);
        }
        //创建内容 values
        for (int i = 0; i < values.length; i++) {
            wb.getSheet(sheetName).createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                wb.getSheet(sheetName).getRow(i + 1).createCell(j).setCellValue(values[i][j]);
            }
        }
        //设置标题头样式
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = wb.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        for (int i = 0; i < title.length; i++) {
            wb.getSheet(sheetName).getRow(0).getCell(i).setCellStyle(cellStyle);
        }
//        //将EXCEL文件保存到本地
//        try {
//            //写入文件
//            FileOutputStream fout = new FileOutputStream(filePath + fileName);
//            wb.write(fout);
//            fout.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return filePath + fileName;
        //将wb转化为文件 并转化为字节流返回
        byte[] bytes = null;
        //创建输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //将EXCEL文件写入输出流
        try {
            wb.write(byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //关闭输出流
        try {
            byteArrayOutputStream.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes; //返回文件流
    }
}
