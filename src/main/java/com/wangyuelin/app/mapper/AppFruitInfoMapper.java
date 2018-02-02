package com.wangyuelin.app.mapper;

import com.wangyuelin.app.model.FruitBean;
import com.wangyuelin.app.model.FruitTag;
import com.wangyuelin.app.model.MonthBean;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface AppFruitInfoMapper {

    @Select("SELECT fruit.des as des, fruit.icon as icon, fruit.`name` as name, fruit.func as func FROM fruit;")
    List<FruitBean> getAllFruitInfo();

    @Select("SELECT month_fruit.month_num as monthNum, month_fruit.fruits as fruitStr FROM month_fruit;")
    List<MonthBean> getAllFruit();

    @Select("SELECT fruit_tag.tag AS tag, fruit_tag.content AS content from fruit_tag WHERE belong_fruit = #{fruit}")
    List<FruitTag> getTagsByFruit(String fruit);
}
