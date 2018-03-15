package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Administrator;


public interface AdminDao extends CrudRepository<Administrator,Integer>{

	Administrator findByAname(String aname);

}
