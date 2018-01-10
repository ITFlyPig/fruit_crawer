package com.wangyuelin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.wangyuelin.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangyuelin.model.User;
import com.wangyuelin.service.UserService;


@Service("userService")
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class UserServiceImpl implements UserService {
	
	@Resource(name="testMapper")
	private TestMapper mapper;

	public void save(User user) {

	}

	public boolean update(User user) {
		return false;
	}

	public boolean delete(int id) {
		
		return mapper.delete(id);
	}

	public List<User> findAll() {
		List<User> findAllList = mapper.findAll();
		return findAllList;
	}

	public User findByName(String name) {
		return null;
	}

	public User findById(int id) {

		User user = mapper.findById(id);
		
		return user;
	}

//	public void save(User user) {
//
//		mapper.save(user);
//	}
//
//	public boolean update(User user) {
//
//		return mapper.update(user);
//	}
//
//	public User findByName(String name) {
//		return mapper.findByName(name);
//	}
}
