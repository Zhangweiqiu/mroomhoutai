package com.example.demo.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;


@Service
public class UserService {
	@Autowired
	UserDao userDao;
	
	public Map<String,Object> userlist(String admin,Integer page,Integer rows){

		Map<String,Object> map = new HashMap<>();
		if(admin == null||"".equals(admin)) {
			map.put("total", 0);
			map.put("pageNumber", 0);
			map.put("pageSize", 0);
			map.put("rows", null);
		}else {
//			Pageable pageable = new PageRequest(page-1,rows);
			List<User> list = userDao.weiUser("已支付，未审核");
			map.put("total",list.size()); //总条数
		    map.put("pageNumber",rows); //10 20 ...
		    map.put("pageSize",page); //当前页数
		    List<User> li = new LinkedList<User>();
		    if(rows*page >= list.size()) {
		    	if(list.size()-rows*(page-1) > 0) {
			    	 for(int i = 0; i < list.size()-rows*(page-1); i++) {
					    	li.add(list.get(rows*(page-1)+i));
					    }
		    	}else {
		    		for(int i = 0; i < list.size(); i++) {
				    	li.add(list.get(rows*(page-1)+i));
				    }
		    	}
		    }else {
		    	for(int i = 0; i < rows; i++) {
			    	li.add(list.get(rows*(page-1)+i));
			    }
				    
		    }
		    map.put("rows",li); //内容

		}
	    return map;
	}

	public boolean editUser(User user) {
		User u = userDao.findOne(user.getU_id());
		if("开通权限".equals(user.getU_state())) {
			u.setU_state("已支付，已审核");
			u.setU_deposit(user.getU_deposit());
		}
		else {
			u.setU_state("已支付，未审核");
			u.setU_deposit("0.00");
		}
		if(userDao.save(u)!= null)
			return true;
		return false;
	}
	

	public Map<String, Object> findByName(String u_name, Integer page, Integer rows) {
		Map<String,Object> map = new HashMap<>();
		if(u_name == null || "".equals(u_name)) {
			Pageable pageable = new PageRequest(page-1,rows);
			Page<User> list = userDao.findAll(pageable);
			map.put("total",list.getTotalElements()); //总条数
		    map.put("pageNumber", list.getSize()); //10 20 ...
		    map.put("pageSize", list.getTotalPages()); //总页数
		    map.put("rows",list.getContent()); //内容
		}else {
		    map.put("total",1);
		    map.put("pageNumber", 1);
		    map.put("pageSize", 10);
		    map.put("rows",userDao.findByName(u_name));
		}		
		return map;
	}

	public Map<String, Object> userlists(String admin, Integer page, Integer rows) {
		Map<String,Object> map = new HashMap<>();
		if(admin == null||"".equals(admin)) {
			map.put("total", 0);
			map.put("pageNumber", 0);
			map.put("pageSize", 0);
			map.put("rows", null);
		}else {
//			Pageable pageable = new PageRequest(page-1,rows);
			List<User> list = userDao.weiUser("已支付，已审核");
			map.put("total",list.size()); //总条数
		    map.put("pageNumber",rows); //10 20 ...
		    map.put("pageSize",page); //当前页数
		    List<User> li = new LinkedList<User>();
		    if(rows*page >= list.size()) {
		    	if(list.size()-rows*(page-1) > 0) {
			    	 for(int i = 0; i < list.size()-rows*(page-1); i++) {
					    	li.add(list.get(rows*(page-1)+i));
					    }
		    	}else {
		    		for(int i = 0; i < list.size(); i++) {
				    	li.add(list.get(rows*(page-1)+i));
				    }
		    	}
		    }else {
		    	for(int i = 0; i < rows; i++) {
			    	li.add(list.get(rows*(page-1)+i));
			    }
				    
		    }
		    map.put("rows",li); //内容
		}
	    return map;
	}
}
