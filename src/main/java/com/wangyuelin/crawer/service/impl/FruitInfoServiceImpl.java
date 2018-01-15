package com.wangyuelin.crawer.service.impl;

import com.wangyuelin.crawer.mapper.FruitInfoMapper;
import com.wangyuelin.crawer.mapper.FruitMapper;
import com.wangyuelin.crawer.model.Fruit;
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

    @Resource(name = "fruitMapper")
    private FruitMapper fruitMapper;

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
            System.out.println("将要插入的数据：" + bean);
            if (getByMonth(bean.getMonthNum()) != null){
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

    /**
     * 保存基本信息
     * @param fruit
     */
    public void saveBase(Fruit fruit){
        if (fruitMapper.getByName(fruit.getName()) == null){
            fruitMapper.saveFruitBaseInfo(fruit);
        }else {
            fruitMapper.updateFruitBaseInfo(fruit);
        }
    }

    /**
     * 更行水果的简介
     * @param fruitName
     * @param desc
     */
    public void updateFruitDesc(String fruitName, String desc) {
        System.out.println("更新的水果：" + fruitName);
        fruitMapper.updateFruitDesc(desc, fruitName);
    }


}
