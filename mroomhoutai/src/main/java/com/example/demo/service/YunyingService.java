package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.CpDao;
import com.example.demo.dao.YunyingDao;
import com.example.demo.model.Cp;
import com.example.demo.model.Yunying;
import com.example.demo.util.HttpUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class YunyingService {
	
	@Resource
	YunyingDao yunyingDao;
	
	@Resource
	CpDao cpDao;
	
	public String getInfo(String collectToken,String ph) {
		JSONObject jsonObject = HttpUtil.httpRequest(HttpUtil.access_token_Url, "GET", null);
		
		JSONObject data = jsonObject.getJSONObject("data");
		
		String access_token = data.getString("access_token");
		
		List<Yunying> yu = yunyingDao.findByCellphone(ph);
		String url = "";
		boolean state = false;
		if(yu.size()> 0) {
			url ="https://data.hulushuju.com/api/data/rawdatas/old/"+yu.get(0).getCollectToken()+"?companyAccount=yunyikj_CRAWLER&accessToken="+access_token;
		}else {
			url ="https://data.hulushuju.com/api/data/rawdatas/old/"+collectToken+"?companyAccount=yunyikj_CRAWLER&accessToken="+access_token;
			state = true;
		}
		JSONObject info =  HttpUtil.httpRequest(url, "GET", null);
		System.out.println(info.get("message"));
		JSONArray phone = info.getJSONArray("data");
		
		JSONObject in = phone.getJSONObject(0);
		JSONArray i = in.getJSONArray("calls"); //通话信息
		JSONObject j = in.getJSONObject("basic"); //获取个人基本信息
		String idcard = j.getString("idcard"); //身份证
		String realname = j.getString("real_name");//真实姓名
		String cellphone = j.getString("cell_phone");//电话号码   
		String regtime = j.getString("reg_time");//注册时间   
		String datasource = in.getString("datasource");//来源
		
		if(state) {
			Cp cp = new Cp();
			cp.setCellphone(cellphone);
			cp.setDatasource(datasource);
			cp.setIdcard(idcard);
			cp.setRealname(realname);
			cp.setRegtime(regtime);
			cpDao.save(cp);
		}
		
		for( int l = 0; l < i.size();l++) {
			JSONObject k = i.getJSONObject(l);
			Yunying yun = new Yunying();
			yun.setInittype(k.getString("init_type"));
			yun.setOthercellphone(k.getString("other_cell_phone"));
			yun.setPlace(k.getString("place"));
			yun.setStarttime(k.getString("start_time"));
			yun.setIdcard(idcard);
			yun.setRealname(realname);
			yun.setCellphone(cellphone);
			yun.setUsetime(k.getString("use_time"));
			yun.setCalltype(k.getString("call_type"));
			if(state) {
				yun.setCollectToken(collectToken);
			}
			yunyingDao.save(yun);
		}
		return "ok";
	}
	
	public JSONObject seeInfo(Integer limit,Integer offset,String phone) {
			List<Yunying> yunyingList = (List<Yunying>) yunyingDao.findByCellphone(phone);
			
			JSONObject json = new JSONObject();
			json.put("total", yunyingList.size());	
			json.put("rows", yunyingList);
			System.out.println(json);
			return json;
	}
	
	public Map<String,Object> oneuser(String phone) {
		Cp cp = cpDao.findByCellphone(phone);
		Map<String,Object> map = new HashMap<>();
		map.put("cp", cp);
		return map;
	}
	
	
	public  JSONObject  alluser() {
		List<Cp> cpList = (List<Cp>) cpDao.findAll();
		JSONObject json = new JSONObject();
		json.put("rows",cpList);
		json.put("total", cpList.size());
		return json;
	} 
}
