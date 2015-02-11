package com.it.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.it.dao.IUserDao;
import com.it.model.UserEntity;

@Transactional(propagation=Propagation.REQUIRED)
@Service(value="userService")
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IUserDao userDao;

	public List<UserEntity> query() {
		return (List<UserEntity>) userDao.findAll();
	}

	public void update(UserEntity user) {
		// TODO Auto-generated method stub
		
	}
	
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}
