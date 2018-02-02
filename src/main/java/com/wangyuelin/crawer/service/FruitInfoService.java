package com.wangyuelin.crawer.service;

import com.wangyuelin.crawer.model.*;

import java.util.List;

public interface FruitInfoService {
    void save(List<MonthFruitBean> list);
    void save(MonthFruitBean monthFruitBean);
    MonthFruitBean getByMonth(int month);
    void update(MonthFruitBean bean);
    void saveBase(Fruit fruit);
    void updateFruitDesc(String fruitNmae, String desc);
    void saveTag(String fruit, Tag tag);
    void saveTags(List<Tag> tags, String fruit);

    void saveCookbookIntros(List<CookBookBean> cookbooks);

    void saveSteps(List<StepBean> steps, String cookbook);

    void saveMaterials(List<MaterialBean> materials, String cookbook);

    void updateCookbookDetailMatedial(String value, String name);


    void saveCookbookDetailMaterials(String cookbook, String materials);

    List<String> getFruits();

}
