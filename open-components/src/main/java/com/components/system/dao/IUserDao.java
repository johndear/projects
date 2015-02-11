package com.components.system.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository(value="userDao")
public interface IUserDao extends JpaSpecificationExecutor,PagingAndSortingRepository {

}
