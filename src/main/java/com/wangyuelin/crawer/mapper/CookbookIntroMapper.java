package com.wangyuelin.crawer.mapper;

import com.wangyuelin.crawer.model.CookBookBean;
import com.wangyuelin.crawer.model.CookBookDetailBean;
import com.wangyuelin.crawer.model.MaterialBean;
import com.wangyuelin.crawer.model.StepBean;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface CookbookIntroMapper {

    @Select("SELECT cookbook.img AS img, cookbook.intro AS intro, cookbook.name AS name," +
            "cookbook.detail_url AS detailUrl FROM cookbook WHERE cookbook.name = #{cookbookName} LIMIT 1")
    CookBookBean getCookbookIntroByName(String cookbookName);

    @Select("INSERT INTO cookbook(img, intro, name, detail_url) VALUES (#{img}, #{intro}, #{name}, #{detailUrl})")
    void saveCookBook(CookBookBean cookBookBean);

    @Select("DELETE FROM cookbook WHERE name=#{cookbookName}")
    void deleteCookbook(String cookbookName);

    @Select("UPDATE cookbook_detail SET material = #{0} WHERE name=#{1}")
    void updateCookbookDetailMaterial(String value, String name);


    @Select("SELECT IFNULL(SUM(id), 0) AS id FROM cookbook_detail WHERE name=#{cookbookName}")
    int getCookbookDetailNum(String cookbookName);

    @Select("DELETE FROM cookbook_detail WHERE name=#{name}")
    void deleteCookbookDetail(String name);

    @Select("INSERT INTO cookbook_detail(name, material) VALUES(#{0}, #{1})")
    void saveCookbookDetailMaterial(String name, String materials);

    @Select("SELECT IFNULL(SUM(id), 0) AS id FROM step WHERE belong_cookbook = #{belongCookbook}")
    int getStepSum(String belongCookbook);

    @Select("DELETE FROM step WHERE belong_cookbook = #{belongCookbook} ")
    void deleteStep(String belongCookbook);

    @Select("INSERT INTO step(name, img, step.`index`, tip, sort, belong_cookbook) VALUES(#{name}, #{img}, #{index}, #{tip}, #{sort}, #{belongCookbook})")
    void saveStep(StepBean stepBean);

    @Select("SELECT IFNULL(SUM(id), 0) AS id FROM material WHERE name = #{materialName}")
    int getMaterialSum(String materialName);

    @Select("DELETE FROM material WHERE name = #{materialName}")
    void deleteMaterial(String materialName);

    @Select("INSERT INTO material(name, num, img, type) VALUES(#{name}, #{num}, #{img}, #{type})")
    void saveMaterial(MaterialBean materialBean);


    @Select("SELECT fruits FROM month_fruit;")
    List<String> getFruits();


}
