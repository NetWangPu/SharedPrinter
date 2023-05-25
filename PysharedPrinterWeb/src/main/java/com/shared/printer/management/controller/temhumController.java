package com.shared.printer.management.controller;


import com.shared.printer.management.entity.TemHum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController // 声明这个是一个控制器
public class temhumController {

    @Autowired
    private com.shared.printer.management.mapper.temhumMapper temhumMapper;

    @ApiOperation(value = "查询所有温湿度", notes = "查询所有温湿度")
    @GetMapping("/alltemhum")
    public List<TemHum> alltemhum() {
        List<TemHum> temHums = temhumMapper.findAll();
        //数量超过100条，按时间排序，只返回最新的100条
        if (temHums.size() > 100) {
            temHums.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
            temHums = temHums.subList(0, 100);
        }
        return temHums;
    }

    @ApiOperation(value = "添加温湿度", notes = "添加温湿度")
    @GetMapping("/addtemhum")
    public int addtemhum(String tem, String hum) {
        TemHum temHum = new TemHum();
        temHum.setHum(hum);
        temHum.setTem(tem);
        temHum.setDateTime(LocalDateTime.now().toString());
        temhumMapper.addTemHum(temHum);
        return 1;
    }
}
