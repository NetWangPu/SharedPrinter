package com.shared.printer.management.mapper;

import com.shared.printer.management.entity.printer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface printerMapper {

    //查询所有打印机
    @Select("select * from table_printer") //这里是sql语句 用注解的方式
    public List<printer> findAll() ;

    //根据id查询打印机
    @Select("select * from table_printer where id = #{id}")
    public printer findById(Integer id);

    //根据id更改打印机状态
    @Select("update table_printer set state = #{state} where id = #{id}")
    public void updateState(Integer id,String state);

    //根据id更改打印机纸张数量
    @Select("update table_printer set Numberofpaper = #{Numberofpaper} where id = #{id}")
    public void updateNumberofpaper(Integer id,Integer Numberofpaper);

    //添加打印机
    @Select("insert into table_printer (id,position,state,Numberofpaper) values (#{id},#{position},#{state},#{Numberofpaper})")
    public void addPrinter(printer printer);

    //删除打印机
    @Select("delete from table_printer where id = #{id}")
    public void deletePrinter(Integer id);
}
