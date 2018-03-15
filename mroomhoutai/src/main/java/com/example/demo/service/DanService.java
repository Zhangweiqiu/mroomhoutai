package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.stereotype.Service;

import com.example.demo.dao.DanDao;
import com.example.demo.model.Dan;


@Service
@Transactional
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
	public void jietiaoOpen(String borrow_name,String lend_name,String borrow_date,String pay_data,String money) throws ParseException {
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
		
		
		int random=(int)(Math.random()*1000000);
		String danid = "20171127"+Integer.toString(random);
		List<Dan> d = dandao.findByDanID(danid);
		while(d.size() != 0) {
			random=(int)(Math.random()*1000000);
			danid = "20171127"+Integer.toString(random);
			d = dandao.findByDanID(danid);
		}
		dan.setDan_id(danid);
		dandao.save(dan);
		System.out.println("2321y89ey");
	}

}
