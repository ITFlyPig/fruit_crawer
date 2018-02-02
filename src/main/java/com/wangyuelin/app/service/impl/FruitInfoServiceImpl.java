package com.wangyuelin.app.service.impl;

import com.wangyuelin.app.mapper.AppFruitInfoMapper;
import com.wangyuelin.app.model.*;
import com.wangyuelin.app.service.FruitInfoService;
import com.wangyuelin.crawer.mapper.CookbookIntroMapper;
import com.wangyuelin.util.TextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("FruitInfoServiceImpl")
@Transactional
public class FruitInfoServiceImpl implements FruitInfoService {
    static Logger logger = Logger.getLogger(FruitInfoServiceImpl.class.getName());

    @Resource(name = "appFruitInfoMapper")
    AppFruitInfoMapper appFruitInfoMapper;

    private List<FruitBean> fruitList;


    /**
     * 查询每个月对应的水果的基本信息
     * @return
     */
    public List<FruitRepBean> getAllFruitInfo() {

        fruitList = appFruitInfoMapper.getAllFruitInfo();
        if (fruitList == null){
            logger.error("获取所有的水果基本信息为空");
            return null;
        }

        //获取每个月对应的成熟的水果
        List<MonthBean> months = getMonthFruit();
        //获取每个水果的信息
        if (months == null){
            logger.error("获取每个月对应的水果为空");
            return null;
        }
        int size = months.size();
        ArrayList<FruitRepBean> repBeans = new ArrayList<FruitRepBean>();
        for (int i = 0; i < size; i++){
            MonthBean item = months.get(i);
            FruitRepBean repBean = getFruitsInfo(item);
            if (repBean == null){
                logger.error("查询月份对应的水果的基本信息为空");
                continue;
            }
            repBeans.add(repBean);
        }


        return repBeans;
    }


    public List<MonthBean> getMonths() {
        return getMonthFruit();
    }

    /**
     * 获取每个月对应的水果
     * @return
     */
    private List<MonthBean> getMonthFruit(){

        List<MonthBean> list = appFruitInfoMapper.getAllFruit();
        Observable.from(list)
                .filter(new Func1<MonthBean, Boolean>() {
                    public Boolean call(MonthBean monthBean) {
                        return !TextUtil.isEmpty(monthBean.getFruitStr());//过滤掉没有水果信息的
                    }
                })
                .map(new Func1<MonthBean, MonthBean>() {
                    public MonthBean call(MonthBean monthBean) {
                        String[] fruitArray = monthBean.getFruitStr().split("，");
                        if (fruitArray != null){
                            monthBean.setFruits(Arrays.asList(fruitArray));
                        }
                        return monthBean;
                    }
                })
                .subscribe(new Subscriber<MonthBean>() {
                    public void onCompleted() {

                    }

                    public void onError(Throwable throwable) {
                        logger.error("处理出错");

                    }

                    public void onNext(MonthBean monthBean) {

                    }
                });


        return list;
    }

    /**
     * 查询每个月对应的水果的基本信息
     * @param monthBean
     * @return
     */
    private FruitRepBean getFruitsInfo(MonthBean monthBean){
        if (monthBean == null || monthBean.getFruits() == null){
            return null;
        }

        List<String> fruitList = monthBean.getFruits();
        FruitRepBean repBean = new FruitRepBean();
        repBean.setMonth(monthBean.getMonth());
        repBean.setMonthNum(monthBean.getMonthNum());
        repBean.setFruits(new ArrayList<FruitBean>());
        int size = fruitList.size();
        for (int i = 0; i < size; i++){
            String fruitStr = fruitList.get(i);
            if (TextUtil.isEmpty(fruitStr)){
                logger.error("查询水果的基本信息时，水果为空");
                continue;
            }

            FruitBean fruitBean = getFruitByName(fruitStr);
            if (fruitBean == null){
                logger.error("查询到的水果基本信息为空");
                continue;
            }
            repBean.getFruits().add(fruitBean);
        }
        return repBean;

    }

    /**
     * 据水果的名称查询到水果的基本信息  todo:查询标签、菜谱
     * @param fruitStr
     * @return
     */
    private FruitBean getFruitByName(String fruitStr){
        if (TextUtil.isEmpty(fruitStr)){
            logger.error("水果的名字为空");
            return null;
        }
        if (fruitList == null){
            logger.error("水果的基本信息为空");
            return null;
        }
        int size = fruitList.size();
        for (int i = 0; i < size; i++){
            FruitBean item = fruitList.get(i);
            if (fruitStr.equalsIgnoreCase(item.getName())){
                //查询标签
                List<FruitTag> tags = appFruitInfoMapper.getTagsByFruit(fruitStr);
                if (tags == null){
                    logger.error("水果：" + fruitStr + " 查询到的标签为空");
                }
                item.setTags(tags);
                return item;
            }

        }


        return null;

    }

}
