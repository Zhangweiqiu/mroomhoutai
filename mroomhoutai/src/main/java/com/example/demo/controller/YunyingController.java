package com.example.demo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.YunyingDao;
import com.example.demo.model.Yunying;
import com.example.demo.service.YunyingService;

import net.sf.json.JSONObject;

@RestController
public class YunyingController {
	
	@Autowired
	YunyingService yunyingService;
	
	@Resource
	YunyingDao yunyingDao;
	
	@RequestMapping("/getInfo")
	public String getInfo(String collectToken,String ph) {
		return  yunyingService.getInfo(collectToken,ph);
	}
	
	@RequestMapping("/seeInfo")
	public JSONObject seeInfo(String phone) {
		return yunyingService.seeInfo( phone);
	}
	
	@RequestMapping("/oneuser")
	public JSONObject  oneuser(String phone) {
		return yunyingService.oneuser(phone);
	} 
	
	@RequestMapping("/alluser")
	public  JSONObject  alluser(Integer limit,Integer offset) {
		return yunyingService.alluser();
	} 
	
	@SuppressWarnings("resource")
	@RequestMapping("/exportUs")
	public void exportUser(HttpServletRequest req,HttpServletResponse resp,String phone) throws IOException {
		List<Yunying> yunyingList =yunyingDao.findByCellphone(phone);
		 HSSFWorkbook workbook = new HSSFWorkbook();
	     HSSFSheet sheet = workbook.createSheet("用户信息表");
	     
	     String fileName="用户通话记录";//设置要导出的文件的名字
	     //新增数据行，并且设置单元格数据
	     
	     int rowNum = 1;
	     
	     String[] headers= {"对方手机号","通话地点","主叫/被叫","通话起始时间","通话时长","通话类型"};
		   //headers表示excel表中第一行的表头
	     
	     HSSFRow row = sheet.createRow(0);
	        //在excel表中添加表头
	     
	     for(int i=0;i<headers.length;i++){
	            HSSFCell cell = row.createCell(i);
	            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
	            cell.setCellValue(text);
	     }
	     if(yunyingList.size() > 0) {
		     for(int i=0;i<yunyingList.size();i++) {
		    	
		        	HSSFRow row1 = sheet.createRow(rowNum);
		        	Yunying yun = yunyingList.get(i);
		        	 System.out.println(yun.getOthercellphone());
		        	if(yun.getOthercellphone()!=null && !"".equals(yun.getOthercellphone()))
		        		row1.createCell(0).setCellValue(yun.getOthercellphone());
		        	else
		        		row1.createCell(0).setCellValue("");
		        	if(yun.getPlace()!=null && !"".equals(yun.getPlace())) {
		        		//String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(dan.getBorrow_date());
		        		row1.createCell(1).setCellValue(yun.getPlace());
		        	}
		        	else
		        		row1.createCell(1).setCellValue("");
		        	if(yun.getInittype()!=null && !"".equals(yun.getInittype())) {
		        		//String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(dan.getPay_data());
		        		row1.createCell(2).setCellValue(yun.getInittype());
		        	} 
		        	else
		        		row1.createCell(2).setCellValue("");
		        	if(yun.getStarttime()!=null && !"".equals(yun.getStarttime())) {
		        		row1.createCell(3).setCellValue(yun.getStarttime());
		        	}else
		        		row1.createCell(3).setCellValue("");
		        	if(yun.getUsetime()!=null && !"".equals(yun.getUsetime())) {
		        		row1.createCell(4).setCellValue(yun.getUsetime());
		        	}else {
		        	row1.createCell(4).setCellValue("");
		        	System.out.println("1111");
		        	}
		        	if(yun.getCalltype()!=null && !"".equals(yun.getCalltype())) {
		        		row1.createCell(5).setCellValue(yun.getCalltype());
		        	}else
		        	row1.createCell(5).setCellValue("");
		        }
	     }
	     
	  // 通过Response把数据以Excel格式保存
	        resp.reset();
	        resp.setContentType("application/msexcel;charset=UTF-8");
	        resp.addHeader("Content-Disposition",
	                "attachment;filename=" + new String((fileName + ".xls").getBytes("GBK"), "ISO8859_1"));
	        // 定义输出类型
	        OutputStream out = resp.getOutputStream();
	        workbook.write(out);
	        out.flush();
	        out.close();
	     
	}
}
