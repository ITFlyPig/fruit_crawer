package com.wangyuelin.crawer.mapper;

import com.wangyuelin.crawer.model.MonthFruitBean;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface FruitInfoMapper {
	void save(List<MonthFruitBean> list);
	void save(MonthFruitBean bean);
	MonthFruitBean getByMonth(int month);
	void update(MonthFruitBean bean);
}
