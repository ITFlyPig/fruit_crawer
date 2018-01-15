package com.wangyuelin.crawer.mapper;

import com.wangyuelin.crawer.model.Fruit;
import com.wangyuelin.crawer.model.MonthFruitBean;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface FruitMapper {
	void saveFruitBaseInfo(Fruit fruit);
	void updateFruitBaseInfo(Fruit fruit);
	Fruit getByName(String name);
	void updateFruitDesc(String des, String name);

}
