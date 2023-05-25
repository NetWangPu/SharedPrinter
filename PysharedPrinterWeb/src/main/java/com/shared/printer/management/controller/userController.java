package com.shared.printer.management.controller;

import com.shared.printer.management.entity.User;
import com.shared.printer.management.mapper.userMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // 声明这个是一个控制器
public class userController {
    @Autowired
    private userMapper UserMapper;

    //Swagger描述
    // http://localhost:8080/swagger-ui.html

    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    @GetMapping("/alluser")
    public List<User> alluser() {
        return UserMapper.findAll();
    }

    @ApiOperation(value = "查询用户", notes = "根据id查询用户")
    @GetMapping("/user")
    public User user(Integer id) {
        return UserMapper.findById(id);
    }

    @ApiOperation(value = "添加用户", notes = "添加用户")
    @PostMapping("/adduser")
    public int addUser(String username,String password) {
        //判断用户名是否存在
        if (!UserMapper.findUserByUsername(username).isEmpty()) {
            return 0;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setBalance(0.0);
        UserMapper.addUser(user);
        return UserMapper.findUserByUsername(username).get(0).getId();
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @GetMapping("/deleteuser")
    public int deleteUser(Integer id) {
        UserMapper.logout(id);
        if (UserMapper.findById(id) == null) {
            return 1;
        } else {
            return 0;
        }
    }

    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @GetMapping("/updateuser")
    public int updateUserPassword(Integer id,String password) {
        UserMapper.updateUserPassword(id,password);
        if (UserMapper.findById(id).getPassword().equals(password)) {
            return 1;
        } else {
            return 0;
        }
    }


    @ApiOperation(value ="用户登录", notes = "登陆")
    @PostMapping("/login")
    public int login(String username,String password) {
        User user = UserMapper.login(username,password);
        if (user == null) {
            return -1;
        } else {
            return user.getId();
        }
    }


    //用户余额充值
    @ApiOperation(value = "用户余额充值", notes = "用户余额充值")
    @GetMapping("/recharge")
    public int recharge(Integer id,Double balance) {
        UserMapper.recharge(id,balance);
        if (UserMapper.findById(id).getBalance().equals(balance)) { //判断是否充值成功
            return 1;
        } else {
            return 0;
        }
    }

}
