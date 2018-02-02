package com.wangyuelin.app.service;

import com.wangyuelin.app.model.FruitBean;
import com.wangyuelin.app.model.FruitRepBean;
import com.wangyuelin.app.model.MonthBean;

import java.util.List;

public interface FruitInfoService {
    List<FruitRepBean> getAllFruitInfo();
    List<MonthBean> getMonths();
}
