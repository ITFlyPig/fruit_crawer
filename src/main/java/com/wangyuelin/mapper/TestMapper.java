package com.wangyuelin.mapper;

import com.wangyuelin.model.User;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface TestMapper {

	void save(User user);
	boolean update(User user);
	boolean delete(int id);
	User findById(int id);
	List<User> findAll();

	@Select(" select * from user where name=#{name}")
	User findByName(String name);
}
