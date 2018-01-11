package com.wangyuelin.crawer.service.impl;

import com.wangyuelin.crawer.mapper.FruitInfoMapper;
import com.wangyuelin.crawer.model.MonthFruitBean;
import com.wangyuelin.crawer.service.FruitInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("fruitInfoService")
@Transactional
public class FruitInfoServiceImpl implements FruitInfoService {
    @Resource(name="fruitInfoMapper")
    private FruitInfoMapper mapper;

    /**
     * 插入一个列表
     * @param list
     */
    public void save(List<MonthFruitBean> list) {
        if (list == null){
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++){
            MonthFruitBean bean = list.get(i);
            if (getByMonth(bean.getMonthNum()) == null){
                update(bean);
            }else {
                save(bean);
            }
        }

    }

    /**
     * 插入单条数据
     * @param monthFruitBean
     */
    public void save(MonthFruitBean monthFruitBean) {
        mapper.save(monthFruitBean);
    }

    /**
     *
     * @param month
     * @return
     */
    public MonthFruitBean getByMonth(int month) {
        return mapper.getByMonth(month);
    }

    /**
     * 更新
     * @param bean
     */
    public void update(MonthFruitBean bean) {
        mapper.update(bean);
    }


}
