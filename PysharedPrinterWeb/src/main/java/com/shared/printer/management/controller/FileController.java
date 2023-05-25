package com.shared.printer.management.controller;


import com.shared.printer.management.entity.FileBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController {

    //获取路径下的所有文件
    //http://localhost:8080/getAllFiles
    //返回值为json格式
    private String loadPath = System.getProperty("os.name").toLowerCase().startsWith("win") ? "D:/upload/share/" : "/upload/share/";

    //获取路径下的所有文件
    //http://localhost:8080/getAllFiles
    //返回值为json格式
    @ApiOperation(value = "获取路径下的所有文件", notes = "获取路径下的所有文件")
    @GetMapping("/getAllFiles")
    public List<FileBean> getAllFiles() {
        List<FileBean> list = new ArrayList<>();
//        扫描文件夹 loadPath 并将文件挨个添加到list中 使用Java中的File类
        java.io.File file = new java.io.File(loadPath); //获取其file对象
        java.io.File[] fs = file.listFiles(); //遍历path下的文件和目录，放在File数组中
//        for (java.io.File f : fs) { //遍历File[]数组
//            if (!f.isDirectory()) { //若非目录(即文件)，则打印
//                FileBean fileBean = new FileBean();
//                fileBean.setName(f.getName());
//                //文件大小转化为MB两位小数
//                fileBean.setSize(String.valueOf(f.length() / 1024.0 / 1024.0).substring(0, 4) + "MB");
//                //文件最后修改时间 转化为年-月-日 时:分:秒
//                fileBean.setTime(String.valueOf(new java.sql.Timestamp(f.lastModified())).substring(0, 19));
//                fileBean.setUploader("admin");
//                list.add(fileBean);
//            }
//        }
        //在linux下使用ls命令获取文件信息
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(Runtime.getRuntime().exec("ls -l " + loadPath).getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                //跳过第一行
                if (line.startsWith("total")) {
                    continue;
                }
                System.out.println(line);
                //将文件信息按空格分割
                String[] str = line.split("\\s+"); // 正则表达式 一个或多个空格 分割
                //判断是否为文件
                if (!str[0].startsWith("d")) {
                    FileBean fileBean = new FileBean();
                    fileBean.setName(str[8]);
                    //文件大小转化为MB两位小数
                    fileBean.setSize(String.valueOf(Integer.parseInt(str[4]) / 1024.0 / 1024.0).substring(0, 4) + "MB");
                    //文件最后修改时间 转化为年-月-日 时:分:秒
                    fileBean.setTime(str[5] + " " + str[6] + " " + str[7]);
                    fileBean.setUploader("admin");
                    list.add(fileBean);
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @ApiOperation(value="根据文件名下载文件", notes="返回文件流")
    @PostMapping("/downloadFile")
    //返回文件流
    public byte[] downloadFile(String fileName) {
        //在loadPath路径下找到文件名为fileName的文件 并返回文件流
        java.io.File file = new java.io.File(loadPath + fileName);
        byte[] bytes = null;
        //判断文件是否存在
        if (file.exists()) {
            try {
                //将文件转化为字节流
                java.io.FileInputStream fis = new java.io.FileInputStream(file);
                bytes = new byte[fis.available()];
                fis.read(bytes);
                fis.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("文件不存在");
        }
        return bytes;
    }

    //上传文件 保存文件并返回下载链接
    @PostMapping("/uploadsharedfile")
    @ResponseBody
    public int upload(@RequestParam("file") MultipartFile file, String filename) throws IOException {
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //设置文件存储路径
        String filePath = loadPath;
        //设置文件名为上传时文件名
        fileName = filename;
        //创建文件
        File dest = new File(filePath + fileName);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        //保存文件
        file.transferTo(dest);
        //返回文件名
        return 1;
    }

    //根据文件名删除文件
    @PostMapping("/deleteFile")
    @ResponseBody
    public int deleteFile(String fileName) {
        //在loadPath路径下找到文件名为fileName的文件 并删除
        java.io.File file = new java.io.File(loadPath + fileName);
        //判断文件是否存在
        if (file.exists()) {
            file.delete();
            return 1;
        }
        else {
            System.out.println("文件不存在");
            return 0;
        }
    }
}
