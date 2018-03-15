package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AdminService;

@RestController
public class AdminController {
	@Autowired
	AdminService adminService;
	
	@RequestMapping("/login")
	public Map<String,Object> login(String aname,String password) {
		return adminService.login(aname, password);
	}
	
	@RequestMapping("/updatatext")
	public boolean updatatext(String aname,String atext) {
		return adminService.updatatext(aname, atext);
	}
}
