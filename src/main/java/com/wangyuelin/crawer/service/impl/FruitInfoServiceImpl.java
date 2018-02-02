package com.wangyuelin.crawer.service.impl;

import com.wangyuelin.crawer.mapper.CookbookIntroMapper;
import com.wangyuelin.crawer.mapper.FruitInfoMapper;
import com.wangyuelin.crawer.mapper.FruitMapper;
import com.wangyuelin.crawer.model.*;
import com.wangyuelin.crawer.service.FruitInfoService;
import com.wangyuelin.util.TextUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("fruitInfoService")
@Transactional
public class FruitInfoServiceImpl implements FruitInfoService {
    @Resource(name="fruitInfoMapper")
    private FruitInfoMapper mapper;

    @Resource(name = "fruitMapper")
    private FruitMapper fruitMapper;

    @Resource(name = "cookbookIntroMapper")
    private CookbookIntroMapper cookbookIntroMapper;

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

    /**
     * 保存一个tag
     * @param fruit
     * @param tag
     */
    public void saveTag(String fruit, Tag tag) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fruit", fruit);
        params.put("tag", tag.getTag());
        params.put("tagContent", tag.getChildSText());
        fruitMapper.saveTag(params);
    }

    /**
     * 保存一个tag
     * @param fruit
     * @param tag
     */
    public void updateTag(String fruit, Tag tag) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fruit", fruit);
        params.put("tag", tag.getTag());
        params.put("tagContent", tag.getChildSText());
        fruitMapper.updateTagByName(params);
    }


    /**
     * 保存一个列表
     * @param tags
     * @param fruit
     */
    public void saveTags(List<Tag> tags, String fruit) {
        if (tags == null){
            return;
        }
        System.out.println("开始保存：" + fruit);
        int size = tags.size();
        for (int i = 0; i < size; i++){
            Tag tag = tags.get(i);
            if (fruitMapper.getTagByName(tag.getTag()) == null){
                saveTag(fruit, tag);
            }else {
                updateTag(fruit, tag);
            }
        }
    }

    /**
     * 保存菜谱的简介
     * @param cookbooks
     */
    public void saveCookbookIntros(List<CookBookBean> cookbooks) {
        if (cookbooks == null){
            return;
        }
        int size = cookbooks.size();
        for (int i = 0; i < size; i++){
            CookBookBean bean = cookbooks.get(i);
            if (cookbookIntroMapper.getCookbookIntroByName(bean.getName()) != null){
                cookbookIntroMapper.deleteCookbook(bean.getName());
            }
            cookbookIntroMapper.saveCookBook(bean);
        }

    }

    /**
     * 保存做菜的步骤
     * @param steps
     */
    public void saveSteps(List<StepBean> steps, String cookbook) {
        if (steps == null){
            return;
        }
        System.out.println("开始保存材料");
        int sum = cookbookIntroMapper.getStepSum(cookbook);
        if (sum > 0){
            cookbookIntroMapper.deleteStep(cookbook);
        }
        int size = steps.size();
        for (int i = 0; i < size; i++){
            StepBean stepBean = steps.get(i);
            stepBean.setBelongCookbook(cookbook);
            stepBean.setSort(i + 1);
            cookbookIntroMapper.saveStep(stepBean);
        }
    }

    /**
     * 将材料保存到数据库
     * @param materials
     * @param cookbook
     */
    public void saveMaterials(List<MaterialBean> materials, String cookbook) {
        if(materials == null){
            return;
        }
        int size = materials.size();
        for (int i = 0; i < size; i++){
            MaterialBean bean =materials.get(i);
            if (cookbookIntroMapper.getMaterialSum(bean.getName()) > 0){
                cookbookIntroMapper.deleteMaterial(bean.getName());
            }
            cookbookIntroMapper.saveMaterial(bean);
        }


    }

    /**
     * 更新菜单详情的一个列的值
     * @param value
     * @param name
     */
    public void updateCookbookDetailMatedial(String value, String name) {
        cookbookIntroMapper.updateCookbookDetailMaterial(value, name);

    }

    /**
     * 保存菜谱对应的材料
     * @param cookbook
     * @param materials
     */
    public void saveCookbookDetailMaterials(String cookbook, String materials) {
        if (cookbookIntroMapper.getCookbookDetailNum(cookbook) > 0){
            cookbookIntroMapper.updateCookbookDetailMaterial( materials, cookbook);
        }else {
            cookbookIntroMapper.saveCookbookDetailMaterial(cookbook, materials);
        }

    }

    /**
     * 获取所有存储的水果
     * @param
     * @return
     */
    public List<String> getFruits(){
        List<String> fruits = cookbookIntroMapper.getFruits();
        if (fruits == null){
            return null;
        }
        ArrayList<String> result = new ArrayList<String>();
        int size = fruits.size();
        for (int i = 0; i < size; i++){
            String fruitStr = fruits.get(i);
            if (TextUtil.isEmpty(fruitStr)){
                continue;
            }
            String[] fruitArray = fruitStr.split("，");
            addUnique(result, fruitArray);
        }

        System.out.println("获取到的水果：" + result.toString());
        return result;


    }

    /**
     * 添加
     * @param result
     * @param fruitArray
     */
    private void addUnique(List<String> result, String[] fruitArray){
        if (result == null | fruitArray == null){
            return;
        }
        int size = fruitArray.length;
        for (int i = 0; i < size; i++){
            String fruit = fruitArray[i].replaceAll(" ", "");
            if (result.contains(fruit)){
                continue;
            }else {
                result.add(fruit);
            }
        }
    }


}
