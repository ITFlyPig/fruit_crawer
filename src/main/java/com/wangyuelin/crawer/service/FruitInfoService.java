package com.wangyuelin.crawer.service;

import com.wangyuelin.crawer.model.Fruit;
import com.wangyuelin.crawer.model.MonthFruitBean;

import java.util.List;

public interface FruitInfoService {
    void save(List<MonthFruitBean> list);
    void save(MonthFruitBean monthFruitBean);
    MonthFruitBean getByMonth(int month);
    void update(MonthFruitBean bean);
    void saveBase(Fruit fruit);
    void updateFruitDesc(String fruitNmae, String desc);
}
