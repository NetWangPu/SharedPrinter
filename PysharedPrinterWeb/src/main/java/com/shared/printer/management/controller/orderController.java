package com.shared.printer.management.controller;


import com.shared.printer.management.MQTT.MsgGateway;
import com.shared.printer.management.entity.User;
import com.shared.printer.management.entity.order;
import com.shared.printer.management.mapper.orderMapper;
import com.shared.printer.management.mapper.userMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController // 声明这个是一个控制器
public class orderController {

    @Autowired
    private orderMapper OrderMapper;

    @Autowired
    private userMapper UserMapper;

    @Autowired
    private MsgGateway msgGateway;

    @Value("${mqtt.out.topic:mqtt_out_topic}")
    private String[] outTopic;


    //判断环境 是windows还是linux 使用不同的路径
    private String uploadPath = System.getProperty("os.name").toLowerCase().startsWith("win") ? "D:/upload/" : "/upload/";

    //Swagger描述
    // http://localhost:8080/swagger-ui.html
     @ApiOperation(value = "查询所有订单", notes = "查询所有订单")
     @GetMapping("/allorder")
     public List<order> allorder() {
         return OrderMapper.findAll();
     }

     //添加订单
    @ApiOperation(value = "添加订单", notes = "添加订单")
    @PostMapping("/addorder")
    public int addOrder(int ordererid,int printid,int PrintNum,int Printcolor,
                        int Printpaper,String printfile,Double printprice) {
         //获取时间
        order order = new order();
        order.setOrdererID(ordererid);
        order.setPrintid(printid);
        order.setState("未打印");
        //获取时间
        order.setPrinttime(LocalDateTime.now().toString());
        order.setPrintnumber(PrintNum);
        order.setPrintcolor(Printcolor);
        order.setPrintpaper(Printpaper);
        order.setPrintfile(printfile);
        order.setPrintprice(printprice);
        //根据id查询用户 检查余额是否足够
        User user =  UserMapper.findById(ordererid);
        if(user.getBalance()<printprice){
            return 0;
        }
        //扣除余额
        UserMapper.consume(ordererid,printprice);
        //添加订单
        OrderMapper.addOrder(order);
        // TODO: 2023/5/23 下发指令
        String msg = "{\"printerid\":\""+printid+"\",\"id\":\""+ordererid+"\",\"Printnumber\":\""+PrintNum+"\",\"Printcolor\":\""+Printcolor+"\",\"Printfile\":\""+printfile+"\"}";
        //{"printerid":"1","id":"1","Printnumber":"1","Printcolor":"1","Printfile":"1PrintcontentPrinttimePrintstatusPrintresult"}
        //        msgGateway.sendWithTopic(outTopic[0],"DEVICE"+"OPEN");
        msgGateway.sendWithTopic(outTopic[0],msg);
        return 1;
    }

    //根据id查询订单
    @ApiOperation(value = "查询订单", notes = "根据id查询订单")
    @GetMapping("/order")
    public List<order> order(Integer id) {
        return OrderMapper.findByOrdererId(id);
    }

    //根据id更改订单状态
    @ApiOperation(value = "更改订单状态", notes = "更改订单状态")
    @GetMapping("/updateorder")
    public int updateOrderState(int id,String state) {
        OrderMapper.updateState(id,state);
        if (OrderMapper.findById(id).getState().equals(state)) {
            return 1;
        } else {
            return 0;
        }
    }

    //下载文件 根据文件名下载文件
    @ApiOperation(value = "下载文件", notes = "根据文件名下载文件")
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

    //上传文件 保存文件并返回下载链接
    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //设置文件存储路径
        String filePath = uploadPath;
        //设置文件名 为当前时间戳 + 8位随机数 + 后缀名
        fileName = System.currentTimeMillis() + (int) (Math.random() * 100000000) + suffixName;
        System.out.println("上传文件"+fileName);
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
    //删除文件
    @ApiOperation(value = "删除文件根据文件名称", notes = "删除文件")
    @GetMapping("/deletefile")
    @ResponseBody
    public int deleteFile(String fileName) {
        //获取文件路径
        String filePath = uploadPath;
        //获取文件
        File file = new File(filePath + fileName);
        //判断文件是否存在
        if (file.exists()) {
            //删除文件
            file.delete();
            return 1;
        } else {
            return 0;
        }
    }

}
