package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AdminDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Administrator;

@Service
@Transactional
public class AdminService {

	@Autowired
	AdminDao adminDao;
	
	@Autowired
	UserDao userDao;

	public Map<String, Object> login(String aname, String password) {
		Map<String,Object> map = new HashMap<>();
		Administrator admin = adminDao.findByAname(aname);
		if(admin != null) {
			if(admin.getPassword().equals(password)) {
				map.put("state", true);
				map.put("admin", admin);
			}
			else {
				map.put("state", false);
			}
		}
		else
			map.put("state", false);
		return map;
	}

	public boolean updatatext(String aname, String atext) {
		Administrator a = adminDao.findByAname(aname);
		userDao.updataUser(0);
		a.setAtext(atext);
		if(adminDao.save(a) != null)
			return true;
		return false;
	}
}
