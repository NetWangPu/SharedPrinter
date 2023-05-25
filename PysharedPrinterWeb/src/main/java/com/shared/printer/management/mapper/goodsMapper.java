package com.shared.printer.management.mapper;

import com.shared.printer.management.entity.goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface goodsMapper {

    //查询所有物品
    @Select("select * from table_goods") //这里是sql语句 用注解的方式
    public List<goods> findAll() ;

    //增加物品
    @Select("insert into table_goods (id,time,goods,color,goodSize,isPacked) values (#{id},#{time},#{goods},#{color},#{goodSize},#{isPacked})")
    public void addGoods(goods goods);

    //根据时间段查询物品
    @Select("select * from table_goods where time between #{time1} and #{time2}")
    public List<goods> findGoodsByTime(String time1,String time2);

    //删除物品
    @Select("delete from table_goods where id = #{id}")
    public void deleteGoods(String id);
}
