package com.wangyuelin.crawer.mapper;

import com.wangyuelin.crawer.model.Fruit;
import com.wangyuelin.crawer.model.MonthFruitBean;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface FruitMapper {
	void saveFruitBaseInfo(Fruit fruit);
	void updateFruitBaseInfo(Fruit fruit);
	Fruit getByName(String name);

	@Select("UPDATE fruit SET des = #{0} WHERE name = #{1}")
	void updateFruitDesc(String des, String name);

}
