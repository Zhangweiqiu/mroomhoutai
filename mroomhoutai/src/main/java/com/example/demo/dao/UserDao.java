package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.User;

public interface UserDao extends CrudRepository<User,Integer>{

	Page<User> findAll(Pageable pageable);
	
	@Modifying
	@Query(nativeQuery = true,value = "select * from user where u_state = ?1")
	List<User> weiUser(String u_state);
	
	@Modifying
	@Query(nativeQuery = true,value = "select * from user where u_name = ?1")
	List<User> findByName(String u_name);

	@Modifying
	@Query(nativeQuery = true,value = "update user set u_showmodal = ?1")  
	void updataUser(Integer i);

}
