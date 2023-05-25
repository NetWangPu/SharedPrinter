package com.shared.printer.management.mapper;


import com.shared.printer.management.entity.TemHum;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface temhumMapper {

    //查询温湿度
    @Select("select * from table_temhum") //这里是sql语句 用注解的方式
    public List<TemHum> findAll() ;

    //增加温湿度
    @Insert("insert into table_temhum (id,time,tem,hum) values (#{id},#{time},#{tem},#{hum})")
    public void addTemHum(TemHum temHum);
}
