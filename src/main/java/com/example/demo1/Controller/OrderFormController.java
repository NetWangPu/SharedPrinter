package com.example.demo1.Controller;


import com.example.demo1.Util.ExcelToPdf;
import com.example.demo1.Util.ImageToPdf;
import com.example.demo1.Util.PptToPdf;
import com.example.demo1.Util.WordToPdf;
import com.example.demo1.bean.OrderForm;
import com.example.demo1.bean.PrintOrder;
import com.sun.org.apache.bcel.internal.classfile.SourceFile;
import com.sun.org.apache.bcel.internal.util.ClassPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class OrderFormController {

    //用来记录每日的单号也是订单的id
    private int orderNumber = 0;

    //打印队列
    private PrintOrder[] printQueue = new PrintOrder[100];
    //打印队列的长度
    private int printQueueLength = 0;
    //打印队列的头
    private int printQueueHead = 0;
    //打印队列的尾
    private int printQueueTail = 0;
    //打印队列的最大长度
    private int printQueueMaxLength = 100;
    //打印队列的最小长度
    private int printQueueMinLength = 0;

    //指定文件上传的路径到资源文件夹 linux能工作
    private String uploadPath = "/home/tmp/";

    //返回打印队列里所有的订单
    @GetMapping("/orderForm")
    @ResponseBody
    public List<PrintOrder> orderForm(){
        List<PrintOrder> allofOrderForm = new ArrayList<>();
        //将打印队列里的订单放进ArrayList里
        for (int i = 0; i < printQueueLength; i++) {
            PrintOrder printOrder = printQueue[printQueueHead];
            allofOrderForm.add(printOrder);
        }
        return allofOrderForm;
    }
    //上传文件 保存文件并返回下载链接
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file);
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //设置文件存储路径
        String filePath = uploadPath;
        //设置文件名
        fileName = System.currentTimeMillis() + suffixName;
        //创建文件
        File dest = new File(filePath + fileName);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        //保存文件
        file.transferTo(dest);
        //返回文件名
        return fileName;
    }
    //下载文件 根据文件名下载文件
    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<FileSystemResource> download(@RequestParam("fileName") String fileName) throws FileNotFoundException {
        //获取文件路径
        String filePath = uploadPath;
        //获取文件
        File file = new File(filePath + fileName);
        //返回文件
        return ResponseEntity.ok().body(new FileSystemResource(file));
    }

    //客户端打印文件请求
    @PostMapping("/submitOrder")
    @ResponseBody
    public String submitOrder(@RequestParam("DownloadUrl") String DownloadUrl, @RequestParam("page") int page, @RequestParam("copy") int copy) throws IOException {
        //获取文件下载链接 页数 份数
        String fileDownloadUrl = DownloadUrl;
        int printCopies = copy;
        //创建队列对象
        PrintOrder printOrder = new PrintOrder();
        //设置队列对象属性
        printOrder.setFileDownloadUrl(fileDownloadUrl);
        printOrder.setPrintCopies(printCopies);
        printOrder.setId(orderNumber++);
        //判断打印队列是否已满
        if (printQueueLength == printQueueMaxLength) {
            return "打印队列已满,请稍后再试";
        }
        //放入打印队列
        printQueue[printQueueTail] = printOrder;
        //打印队列长度+1
        printQueueLength++;
        //打印队列头+1
        printQueueTail++;
        return "orderNumber:" + printOrder.getId();
    }

    //处理设备端的打印请求
    @PostMapping("/print")
    @ResponseBody
    public PrintOrder print(@RequestParam("deviceId") String deviceId) throws IOException {
        //获取设备id 这个以后做统计用
        String printDeviceId = deviceId;
        //判断打印队列是否为空
        if (printQueueLength == printQueueMinLength) {
            return null;
        }else {
            //获取打印队列头的打印对象
            PrintOrder printOrder = printQueue[printQueueLength-1];
            //返回给设备端打印对象
            return printOrder;
        }
    }
    //处理设备端的打印完成请求
    @PostMapping("/printFinish")
    @ResponseBody
    public String printFinish(@RequestParam("deviceId") String deviceId, @RequestParam("printOrderId") int printOrderId) throws IOException {
        //获取设备id 这个以后做统计用
        String printDeviceId = deviceId;
        //获取打印对象id
        int printOrderId1 = printOrderId;
        //根据打印对象id删除打印队列中的打印对象
        //删除打印完成的打印对象的文件 以后在写
        for (int i = 0; i < printQueueTail; i++) {
            if (!printQueue[i].equals(null) && (printQueue[i].getId() == printOrderId1)) {
                printQueue[i] = null;
                printQueueLength--;
                //队尾-1
                printQueueTail--;
                return "success";
            }
        }
        return "fail";
    }

}
