package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DanDao;
import com.example.demo.model.Dan;


@Service
@Transactional
@Component  
public class DanService {
	@Autowired
	DanDao dandao;

	public Map<String, Object> findByBalname(String d_balname, Integer page, Integer rows) {
		Map<String,Object> map = new HashMap<>();
		Pageable pageable = new PageRequest(page-1,rows);
		List<Dan> list = dandao.findByBalname(d_balname);
		map.put("total",1); //总条数
	    map.put("pageNumber",1); //10 20 ...
	    map.put("pageSize", 10); //总页数
	    map.put("rows",list); //内容
	    return map;
	}

	public Map<String, Object> findByState(String d_balname, String d_state, Integer page, Integer rows) {
		Map<String,Object> map = new HashMap<>();
		Pageable pageable = new PageRequest(page-1,rows);
		List<Dan> list = dandao.findByState(d_balname,d_state);
		map.put("total",1); //总条数
	    map.put("pageNumber",1); //10 20 ...
	    map.put("pageSize", 10); //总页数
	    map.put("rows",list); //内容
	    return map;
	}

	public Map<String, Object> findSearch(String d_balname, String d_state, String d_pay_data, String d_pay_data1,
			Integer page, Integer rows) {
//		System.out.println("3333333");
//		System.out.println(d_pay_data);
//		System.out.println(d_pay_data1);

		Pageable pageable = new PageRequest(page-1,rows);
		Page<Dan> result = dandao.findAll(new Specification<Dan>() {
//			Predicate p3;
//			Predicate p6;
//			Predicate p8;
//			Predicate p9;
			@Override
			public Predicate toPredicate(Root<Dan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(d_balname)) {
//					System.out.println("888888888");
	                list.add(cb.equal(root.get("borrow_name").as(String.class), d_balname));
	                list.add(cb.equal(root.get("lend_name").as(String.class), d_balname));
//					Predicate p1 = cb.equal(root.get("borrow_name").as(String.class), d_balname);
//					Predicate p2 = cb.equal(root.get("lend_name").as(String.class), d_balname);
//					p3 = cb.or(p1,p2);
	            }

	            if (StringUtils.isNotBlank(d_state)) {
//	            	System.out.println("777777777");
	                list.add(cb.equal(root.get("dan_state").as(String.class), d_state));
//	            	Predicate p4 = cb.equal(root.get("dan_state").as(String.class), d_state);
//	            	p6 = cb.and(p4,p3);
	            }

	            if (StringUtils.isNotBlank(d_pay_data)&&StringUtils.isBlank(d_pay_data1)) {
//	            	System.out.println("22222222");
	            	list.add(cb.equal(root.get("pay_data").as(String.class), d_pay_data));
//	            	Predicate p7 = cb.equal(root.get("pay_data").as(Dan.class), d_pay_data);
//	            	p8 = cb.and(p6,p7);
	            }

	            if (StringUtils.isNotBlank(d_pay_data)&&StringUtils.isNotBlank(d_pay_data1)) {
//	            	System.out.println("111111");
	            	list.add(cb.lessThanOrEqualTo(root.get("pay_data").as(String.class), d_pay_data1));
	            	list.add(cb.greaterThanOrEqualTo(root.get("pay_data").as(String.class), d_pay_data));
//	            	Predicate p7 = cb.lessThanOrEqualTo(root.get("pay_data").as(String.class), d_pay_data1);
//	            	p9 = cb.and(p8,p7);
	            }
//	            System.out.println("111111");
	            if(StringUtils.isNotBlank(d_balname)) {
	            	int i = list.size();
	            	if(i == 2)
	            		return cb.or(list.get(0),list.get(1));
	            	if(i == 3)
	            		return cb.and(list.get(2),cb.or(list.get(0),list.get(1)));
	            	if(i == 4)
	            		return cb.and(list.get(3),cb.and(list.get(2),cb.or(list.get(1),list.get(0))));
	            	if(i == 5)
	            		return cb.and(list.get(4),cb.and(list.get(3),cb.and(list.get(2),cb.or(list.get(1),list.get(0)))));
	            }
	            	Predicate[] p = new Predicate[list.size()];
	                return cb.and(list.toArray(p));
//                  Predicate[] p = new Predicate[list.size()];
//                return cb.and(list.get(3),cb.and(list.get(2),cb.or(list.get(1),list.get(0))));
	        }
			}, pageable);
//		System.out.println("5555555");
//		System.out.println(result.size());
		Map<String,Object> map = new HashMap<>();
		
		map.put("total",result.getTotalElements()); //总条数
	    map.put("pageNumber", result.getSize()); //10 20 ...
	    map.put("pageSize", result.getTotalPages()); //总页数
	    map.put("rows",result.getContent()); //内容
	    
	    return map;
	
	}
	public Map<String,Object> jietiaoOpen(String borrow_name,String lend_name,String borrow_date,String pay_data,String money,String year_rate) throws ParseException {
		Dan dan = new Dan();
		java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
		Date date1 =  formatter.parse(borrow_date);
		Date date2 = formatter.parse(pay_data);
		dan.setBorrow_name(borrow_name);
		dan.setLend_name(lend_name);
		dan.setBorrow_date(date1);
		dan.setPay_data(date2);	
		int mon = Integer.parseInt(money);
		dan.setMonery(mon);
		
		String date3_str=formatter.format(new Date());
		Date date3 = formatter.parse(date3_str);			
		long l=date2.getTime()-date1.getTime();
		long l2=date2.getTime()-date3.getTime();
		int lendId=(int) (l/(1000*3600*24));
		int borrowId=(int) (l2/(1000*3600*24));
		dan.setLend_id(lendId);
		dan.setBorrow_id(borrowId);
		dan.setDan_date(new Date());
		
		
		int random=(int)(Math.random()*1000000);
		String danid = "20181127"+Integer.toString(random);
		List<Dan> d = dandao.findByDanID(danid);
		while(d.size() != 0) {
			random=(int)(Math.random()*1000000);
			danid = "20181127"+Integer.toString(random);
			d = dandao.findByDanID(danid);
		}
		dan.setDan_id(danid);
		dan.setDan_state("待确认");
		dan.setYear_rate(Integer.parseInt(year_rate));
		dan.setOpen_dan(lend_name);
		Map<String,Object> map = new HashMap<>();
		if(dandao.save(dan) != null) {
			map.put("state", true);
			map.put("danid", dan.getDan_id());
			map.put("url", "idz/detail.html");
		}
		else {
			map.put("state", false);
			map.put("url", "idz/jietiaoOpen.html");
		}
		return map;
	}
	
	public Map<String,Object> searchDan(String danId){
		Map<String,Object> map = new HashMap<>();
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");  
		List<Dan> dan = dandao.findByDanID(danId);
		map.put("borrowName", dan.get(0).getBorrow_name());
		map.put("lendName", dan.get(0).getLend_name());
		map.put("borrowdate", sf.format(dan.get(0).getBorrow_date()));
		map.put("payDate", sf.format(dan.get(0).getPay_data()));
		map.put("money", dan.get(0).getMonery());
		map.put("yearRate", dan.get(0).getYear_rate());
		map.put("danState", dan.get(0).getDan_state());
		map.put("danDate", dan.get(0).getDan_date());
		System.out.println(dan.get(0).getBorrow_name());
		map.put("url", "jietiaoSearch.html");
		return map;
	}

	
	public Map<String,Object> deleteDan(String danId){
		Map<String,Object> map = new HashMap<>();
		dandao.deleteDan(danId);
		map.put("state", true);
		return map;
	}
	
	public Map<String,Object> hk(String danId){
		Map<String,Object> map = new HashMap<>();
		
		dandao.hk(new Date(),danId);
		map.put("state", true);
		return map;
	}
	
	public Map<String,Object> yanqi(String pay_date,String money,String dan_id){
		Map<String,Object> map = new HashMap<>();
		List<Dan> danlist = dandao.findByDanID(dan_id);
		Dan dan = danlist.get(0);
		int mon;
		if(money ==null || "".equals(money)) {
			mon = dan.getMonery();
		}else {
			mon = Integer.parseInt(money);
		}
		dandao.yanqi(mon, dan.getPay_data(), money, new Date(), pay_date, dan_id);
		map.put("danid", dan_id);
		map.put("url","jietiaoSearch.html");
		map.put("state", true);
		return map;
	}

	public Map<String,Object> danlist(String aname,Integer page,Integer rows){
		Map<String,Object> map = new HashMap<>();
		System.out.println(page);
		System.out.println(aname);
		List<Dan> danlist =  dandao.selectDan();
		System.out.println(danlist.size());
		map.put("total",danlist.size()); //总条数
	    map.put("pageNumber",rows); //10 20 ...
	    map.put("pageSize",page); //当前页数
	    List<Dan> li = new  LinkedList<Dan>();
	    if(rows*page >= danlist.size()) {
	    	if(danlist.size()-rows*(page-1) > 0) {
		    	 for(int i = 0; i < danlist.size()-rows*(page-1); i++) {
				    	li.add(danlist.get(rows*(page-1)+i));
				    }
	    	}else {
	    		for(int i = 0; i < danlist.size(); i++) {
			    	li.add(danlist.get(rows*(page-1)+i));
			    }
	    	}
	    }else {
	    	for(int i = 0; i < rows; i++) {
		    	li.add(danlist.get(rows*(page-1)+i));
		    }
			    
	    }
		map.put("rows",li); //内容
		return map;
	
	}
	

    @Scheduled(cron="0 0 2 * * ?")  
    public void testTasks() throws ParseException {
    	List<Dan> danlist = dandao.All();
    	
    	for(Dan dan:danlist) {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    		String date3_str=format.format(new Date());
    		String data4_str = format.format(dan.getPay_data());
    		if(!"".equals(data4_str)&&data4_str!=null) {
    			Date date3 = format.parse(date3_str);
    			Date date2 = format.parse(data4_str);
    			long l2=date2.getTime()-date3.getTime();
    			int borrowId=(int) (l2/(1000*3600*24));
    			dandao.updatetime(dan.getDan_id(), borrowId);
    		}
    	}
    	System.out.println(danlist.size());
    }    
}
