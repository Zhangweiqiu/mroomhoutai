package com.example.demo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Cp;
import com.example.demo.model.Yunying;

public interface CpDao extends CrudRepository<Cp,Integer>{
	List<Cp> findByCellphone(String phone);
}
