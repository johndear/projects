package com.it.service;

import java.util.List;

import com.it.model.UserEntity;

public interface IUserService {

	List<UserEntity> query();
	
	void update(UserEntity user);
	
	void delete(String id);
	
	
}
