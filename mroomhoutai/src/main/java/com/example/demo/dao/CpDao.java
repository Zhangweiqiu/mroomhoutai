package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Cp;

public interface CpDao extends CrudRepository<Cp,Integer>{
	Cp findByCellphone(String phone);
}