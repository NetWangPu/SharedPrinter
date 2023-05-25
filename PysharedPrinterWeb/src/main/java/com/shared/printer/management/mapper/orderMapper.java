package com.shared.printer.management.mapper;


import com.shared.printer.management.entity.order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface orderMapper {

    //查询所有订单
    @Select("select * from table_order") //这里是sql语句 用注解的方式
    public List<order> findAll() ;

    //添加订单
    @Select("insert into table_order (id,OrdererID,printid,state,Printtime,Printnumber,Printcolor,Printpaper,Printfile,Printprice) " +
            "values (#{id},#{OrdererID},#{printid},#{state},#{Printtime},#{Printnumber},#{Printcolor},#{Printpaper},#{Printfile},#{Printprice})")
    public void addOrder(order order);

    //根据id查询订单
    @Select("select * from table_order where id = #{id}")
    public order findById(Integer id);

    //查询用户所有订单 id
    @Select("select * from table_order where OrdererID = #{OrdererID}")
    public List<order> findByOrdererId(Integer OrdererID);

    //根据printid查询订单 列表
    @Select("select * from table_order where printid = #{printid}")
    public List<order> findByPrintid(Integer printid);

    //根据OrdererID查询订单
    @Select("select * from table_order where OrdererID = #{OrdererID}")
    public List<order> findByOrdererID(Integer OrdererID);

    //根据id更改订单状态
    @Select("update table_order set state = #{state} where id = #{id}")
    public void updateState(Integer id,String state);

    //根据id更改订单打印时间
    @Select("update table_order set Printtime = #{Printtime} where id = #{id}")
    public void updatePrinttime(Integer id,String Printtime);

}
