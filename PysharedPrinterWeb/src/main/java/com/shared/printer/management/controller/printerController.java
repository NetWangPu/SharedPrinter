package com.shared.printer.management.controller;

import com.shared.printer.management.entity.printer;
import com.shared.printer.management.mapper.printerMapper;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // 声明这个是一个控制器
public class printerController {

    @Autowired
    private printerMapper printerMapperq;

    //Swagger描述
    // http://localhost:8080/swagger-ui.html

    @ApiOperation(value = "查询所有打印机", notes = "查询所有打印机")
    @GetMapping("/allprinter")
    public List<printer> allprinter() {
        return printerMapperq.findAll();
    }

    @ApiOperation(value = "查询打印机", notes = "根据id查询打印机")
    @GetMapping("/printer")
    public printer getPrinterById(Integer id) {
        return printerMapperq.findById(id);
    }

    @ApiOperation(value = "添加打印机", notes = "添加打印机")
    @GetMapping("/addprinter")
    public int addPrinter(String position){
        printer printer = new printer();
        printer.setPosition(position);
        printer.setNumberofpaper(0);
        printer.setState("维修");
        printerMapperq.addPrinter(printer);
        return 1;
    }

    @ApiOperation(value = "删除打印机", notes = "删除打印机")
    @GetMapping("/deleteprinter")
    public int deletePrinter(Integer id){
        printerMapperq.deletePrinter(id);
        return 1;
    }

    //加纸
    @ApiOperation(value = "加纸", notes = "加纸")
    @GetMapping("/addpaper")
    public int addPaper(Integer id ,Integer numberofpaper){
        printer printer = printerMapperq.findById(id);
        printer.setNumberofpaper(printer.getNumberofpaper()+numberofpaper);
        printerMapperq.updateNumberofpaper(printer.getId(),printer.getNumberofpaper());
        return 1;
    }

    //更改状态
    @ApiOperation(value = "更改状态", notes = "更改状态")
    @GetMapping("/updatestate")
    public int updateState(Integer id ,String state){
        printer printer = printerMapperq.findById(id);
        if (state.equals("1")){
            state = "在线";
        }else if (state.equals("0")){
            state = "维修";
        }
        printer.setState(state);
        printerMapperq.updateState(printer.getId(),printer.getState());
        return 1;
    }
}

