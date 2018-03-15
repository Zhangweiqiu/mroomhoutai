package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;


@RestController
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping(value="userlist")
	public Map<String,Object> users(String admin,Integer page,Integer rows){
		return userService.userlist(admin,page,rows);
	}
	
	@PostMapping(value="userlists")
	public Map<String,Object> userlist(String admin,Integer page,Integer rows){
		return userService.userlists(admin,page,rows);
	}
	
	@RequestMapping("editUser")
	public boolean editUser(User user) {
		return userService.editUser(user);
	}
	
	@PostMapping(value="/user/name/{u_name}")
	public Map<String,Object> userById(@PathVariable("u_name") String u_name,Integer page,Integer rows){
		return userService.findByName(u_name,page,rows);
	}
}
