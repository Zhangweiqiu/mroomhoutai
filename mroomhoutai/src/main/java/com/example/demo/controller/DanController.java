package com.example.demo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DanDao;
import com.example.demo.model.Dan;
import com.example.demo.service.DanService;

@RestController
public class DanController {
	
	@Autowired
	DanService danService;
	
	@Autowired
	DanDao dandao;
	
	@PostMapping(value="/dan/search")
	public Map<String,Object> doSearch(String d_balname,String d_state,String d_pay_data,String d_pay_data1,Integer page,Integer rows){
		return danService.findSearch(d_balname,d_state,d_pay_data,d_pay_data1,page,rows);
	}
	
	@PostMapping(value="/dan/state/{d_balname}/{d_state}")
	public Map<String,Object> doStateSearch(@PathVariable("d_balname") String d_balname,@PathVariable("d_state") String d_state,Integer page,Integer rows){
		return danService.findByState(d_balname,d_state,page,rows);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@PostMapping(value="/dan/download")  
	   public void downstudents(HttpServletRequest request, HttpServletResponse response,@RequestParam("d_balname") String d_balname,@RequestParam("d_state") String d_state,@RequestParam("d_pay_data") String d_pay_data,@RequestParam("d_pay_data1") String d_pay_data1)throws IOException  
	   {  //我这是根据前端传来的起始时间来查询数据库里的数据，如果没有输入变量要求，保留前两个就行  
		   //String fileName = "daninfo"  + ".xls";   
		   //response.setContentType("application/octet-stream");
	       //response.setHeader("Content-disposition", "attachment;filename=" + fileName);
	       // 声明一个工作薄  
	       HSSFWorkbook workbook = new HSSFWorkbook();  
	       // 生成一个表格  
	       HSSFSheet sheet = workbook.createSheet(); 
	       
//	       List<Dan> dataset = dandao.findByState(d_balname, d_state);//查询出来的数据
	       List<Dan> dataset = dandao.findAll(new Specification<Dan>() {
//				Predicate p3;
//				Predicate p6;
//				Predicate p8;
//				Predicate p9;

				@Override
				public Predicate toPredicate(Root<Dan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					if (StringUtils.isNotBlank(d_balname)) {
		                list.add(cb.equal(root.get("borrow_name").as(String.class), d_balname));
		                list.add(cb.equal(root.get("lend_name").as(String.class), d_balname));
//						Predicate p1 = cb.equal(root.get("borrow_name").as(String.class), d_balname);
//						Predicate p2 = cb.equal(root.get("lend_name").as(String.class), d_balname);
//						p3 = cb.or(p1,p2);
		            }

		            if (StringUtils.isNotBlank(d_state)) {
		                list.add(cb.equal(root.get("dan_state").as(String.class), d_state));
//		            	Predicate p4 = cb.equal(root.get("dan_state").as(String.class), d_state);
//		            	p6 = cb.and(p4,p3);
		            }

		            if (StringUtils.isNotBlank(d_pay_data)&&StringUtils.isBlank(d_pay_data1)) {
		            	list.add(cb.equal(root.get("pay_data").as(String.class), d_pay_data));
//		            	Predicate p7 = cb.equal(root.get("pay_data").as(Dan.class), d_pay_data);
//		            	p8 = cb.and(p6,p7);
		            }

		            if (StringUtils.isNotBlank(d_pay_data)&&StringUtils.isNotBlank(d_pay_data1)) {
		            	list.add(cb.lessThanOrEqualTo(root.get("pay_data").as(String.class), d_pay_data1));
		            	list.add(cb.greaterThanOrEqualTo(root.get("pay_data").as(String.class), d_pay_data));
//		            	Predicate p7 = cb.lessThanOrEqualTo(root.get("pay_data").as(String.class), d_pay_data1);
//		            	p9 = cb.and(p8,p7);
		            }
		            if(StringUtils.isNotBlank(d_balname)) {
		            	int i = list.size();
		            	if(i == 3)
		            		return cb.and(list.get(2),cb.or(list.get(0),list.get(1)));
		            	if(i == 4)
		            		return cb.and(list.get(3),cb.and(list.get(2),cb.or(list.get(1),list.get(0))));
		            	if(i == 5)
		            		return cb.and(list.get(4),cb.and(list.get(3),cb.and(list.get(2),cb.or(list.get(1),list.get(0)))));
		            }
		            	Predicate[] p = new Predicate[list.size()];
		                return cb.and(list.toArray(p));
//	                  Predicate[] p = new Predicate[list.size()];
//	                return cb.and(list.get(3),cb.and(list.get(2),cb.or(list.get(1),list.get(0))));
		        }
				});
	       //System.out.println(dataset.size());
	       
	       
	       int rowNum = 1;
	       
	       String[] headers = {"借款人", "出借人", "借款时间", "到期时间","状态","金额","剩余时间"};//导出的Excel头部
	       
	       HSSFRow row = sheet.createRow(0);
	        //在excel表中添加表头
	       //System.out.println(headers.length);
	       for(int i=0;i<headers.length;i++){
	            HSSFCell cell = row.createCell(i);
	            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
	            cell.setCellValue(text);
	        }
	       
	     //在表中存放查询到的数据放入对应的列
	        for (int i = 0; i < dataset.size(); i++) {
	            HSSFRow row1 = sheet.createRow(rowNum);
	            Dan dan = (Dan)dataset.get(i);
	            if(dan.getBorrow_name()==null||dan.getBorrow_name().equals(""))
	            	row1.createCell(0).setCellValue("");
	            else
	            	row1.createCell(0).setCellValue(dan.getBorrow_name());
	            
	            if(dan.getLend_name()==null||dan.getLend_name().equals(""))
	            	row1.createCell(1).setCellValue("");
	            else
	            	row1.createCell(1).setCellValue(dan.getLend_name());
	            
	            if(dan.getBorrow_date()==null||dan.getBorrow_date().equals(""))
	            	row1.createCell(2).setCellValue("");
	            else
	            	row1.createCell(2).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(dan.getBorrow_date()));
	            
	            if(dan.getPay_data()==null||dan.getPay_data().equals(""))
	            	row1.createCell(3).setCellValue("");
	            else
	            	row1.createCell(3).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(dan.getPay_data()));
	            
	            if(dan.getDan_state()==null||dan.getDan_state().equals(""))
	            	row1.createCell(4).setCellValue("");
	            else
	            	row1.createCell(4).setCellValue(dan.getDan_state());
	            
	            if(dan.getMonery()==null||dan.getMonery().equals(""))
	            	row1.createCell(5).setCellValue("");
	            else
	            	row1.createCell(5).setCellValue((double) dan.getMonery());
	            if(dan.getBorrow_id()==null||dan.getBorrow_id().equals(""))
	            	row1.createCell(6).setCellValue("");
	            else
	            	row1.createCell(6).setCellValue((Integer) dan.getBorrow_id());
	            //System.out.println(dan.getBorrow_name());
	            //System.out.println(dan.getBorrow_date());
	            rowNum++;
	        }
	        
//	       ServletOutputStream out = response.getOutputStream();
//	       workbook.write(out);
//	       out.flush();
//	       out.close();
	        OutputStream out = null;    
	        try {        
	            out = response.getOutputStream();
	            String userAgent = request.getHeader("USER-AGENT");  
	            String fileName = "dan.xls";// 文件名  
	            response.setCharacterEncoding("UTF-8"); 
	            response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置contentType为excel格式  
	            //response.setContentType("application/x-msdownload");    
//	            response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
	            response.setHeader("Content-Disposition", "attachment; filename="    
	                                                    + URLEncoder.encode(fileName, "UTF-8"));    
	            workbook.write(out);    
	        } catch (Exception e) {    
	            e.printStackTrace();    
	        } finally {      
	            try {       
	                out.close();      
	            } catch (IOException e) {      
	                e.printStackTrace();    
	            }      
	        }     
	        
	   }  
	
	@RequestMapping("/jietiaoOpen")
	public Map<String,Object> jietiaoOpen(String borrow_name,String lend_name,String borrow_date,String pay_data,String money,String year_rate) throws ParseException {
		return danService.jietiaoOpen(borrow_name, lend_name, borrow_date, pay_data, money,year_rate);
		
	}
	
	@RequestMapping("/searchDan")
	public Map<String,Object> searchDan(String danId){
		System.out.println(danId);
		return danService.searchDan(danId);
	}
	
	@RequestMapping("/deleteDan")
	public Map<String,Object> deleteDan(String danId){
		return danService.deleteDan(danId);
	}
	
	@RequestMapping("/hk")
	public Map<String,Object> hk(String danId){
		return danService.hk(danId);
	}
	
	@RequestMapping("/yanqi")
	public Map<String,Object> yanqi(String pay_date,String money,String dan_id) throws ParseException{
		return danService.yanqi(pay_date,money,dan_id);
	}
	
	@RequestMapping("/danlist")
	public Map<String,Object> danlist(String aname,Integer page,Integer rows){
		System.out.println(page);
		return danService.danlist(aname, page, rows);
	}
}
