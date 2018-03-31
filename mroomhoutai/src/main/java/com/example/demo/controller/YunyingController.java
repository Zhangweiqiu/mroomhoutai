package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.YunyingService;

import net.sf.json.JSONObject;

@RestController
public class YunyingController {
	
	@Autowired
	YunyingService yunyingService;
	
	
	@RequestMapping("/getInfo")
	public String getInfo(String collectToken,String ph) {
		return  yunyingService.getInfo(collectToken,ph);
	}
	
	@RequestMapping("/seeInfo")
	public JSONObject seeInfo(Integer limit,Integer offset,String phone) {
		return yunyingService.seeInfo(limit, offset, phone);
	}
	
	@RequestMapping("/oneuser")
	public  Map<String,Object>  oneuser(String phone) {
		return yunyingService.oneuser(phone);
	} 
	
	@RequestMapping("/alluser")
	public  JSONObject  alluser(Integer limit,Integer offset) {
		return yunyingService.alluser();
	} 
}
