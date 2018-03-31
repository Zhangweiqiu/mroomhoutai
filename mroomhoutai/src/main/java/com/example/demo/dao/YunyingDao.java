package com.example.demo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Yunying;

public interface YunyingDao extends CrudRepository<Yunying,Integer>{
	List<Yunying> findByCollectToken(String collectToken);
	
	List<Yunying> findByCellphone(String phone);
	
}
