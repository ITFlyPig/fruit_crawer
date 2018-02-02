package com.wangyuelin.crawer.mapper;

import com.wangyuelin.crawer.model.Fruit;
import com.wangyuelin.crawer.model.MonthFruitBean;
import com.wangyuelin.crawer.model.Tag;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Map;

@MapperScan
public interface FruitMapper {
	void saveFruitBaseInfo(Fruit fruit);
	void updateFruitBaseInfo(Fruit fruit);
	Fruit getByName(String name);

	@Select("UPDATE fruit SET des = #{0} WHERE name = #{1}")
	void updateFruitDesc(String des, String name);


	@Select("INSERT INTO fruit_tag(tag, content, belong_fruit) VALUES (#{tag}, #{tagContent}, #{fruit})")
	void saveTag(Map<String, String> paramMap);

	@Select("SELECT fruit_tag.tag AS tag, fruit_tag.content AS childSText FROM fruit_tag WHERE fruit_tag.tag = #{tag}")
	Tag getTagByName(String tag);

	@Select("UPDATE fruit_tag SET content = #{tagContent} WHERE tag=#{tag}")
	void updateTagByName(Map<String, String> params);

}
