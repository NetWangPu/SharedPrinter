package com.shared.printer.management.mapper;

import com.shared.printer.management.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface userMapper {


    //查询所有用户
    @Select("select * from table_user") //这里是sql语句 用注解的方式
    public List<User> findAll() ;

    //根据id查询用户
    @Select("select * from table_user where id = #{id}")
    public User findById(Integer id);

    //添加用户
    @Select("insert into table_user (id,username,password,balance) values (#{id},#{username},#{password},#{balance})")
    public void addUser(User user);

    //删除用户
    @Select("delete from table_user where id = #{id}")
    public void deleteUser(Integer id);

    //修改用户密码
    @Select("update table_user set password = #{password} where id = #{id}")
    public void updateUserPassword(Integer id,String password);

    //用户注销 删除用户
    @Select("delete from table_user where id = #{id}")
    public void logout(Integer id);

    //用户登录 根据用户名和密码查询用户
    @Select("select * from table_user where username = #{username} and password = #{password}")
    public User login(String username,String password);

    //根据用户名查询用户
    @Select("select * from table_user where username = #{username}")
    public List<User> findUserByUsername(String username);

    //用户充值 根据id累加余额
    @Select("update table_user set balance = balance + #{balance} where id = #{id}")
    public void recharge(Integer id,Double balance);

    //用户消费 根据id减少余额
    @Select("update table_user set balance = balance - #{balance} where id = #{id}")
    public void consume(Integer id,Double balance);
}
