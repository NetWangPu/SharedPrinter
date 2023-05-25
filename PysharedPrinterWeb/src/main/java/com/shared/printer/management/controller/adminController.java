package com.shared.printer.management.controller;


import com.shared.printer.management.mapper.temhumMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class adminController {

    @Autowired
    temhumMapper userAndDeviceMapper;

    @ApiOperation(value = "管理员登录")
    @PostMapping("/adminLogin")
    public int adminLogin(String username,String password){
        if (username.equals("admin") && password.equals("123456")){
            return 1;
        }
        return 0;
    }
}
