package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dan {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer d_id;
	
	private String dan_id;
	
	private String borrow_name;

	private String lend_name;
	
	private Date borrow_date;
	
	private Date pay_data;
	
	private Date repay_date;
	
	private Integer year_rate;
	
	private String borrow_use;
	
	private Date dan_date;
	
	private String dan_state;
	
	private Integer borrow_id;
	
	public Integer getD_id() {
		return d_id;
	}

	public void setD_id(Integer d_id) {
		this.d_id = d_id;
	}

	public String getDan_id() {
		return dan_id;
	}

	public void setDan_id(String dan_id) {
		this.dan_id = dan_id;
	}

	public String getBorrow_name() {
		return borrow_name;
	}

	public void setBorrow_name(String borrow_name) {
		this.borrow_name = borrow_name;
	}

	public String getLend_name() {
		return lend_name;
	}

	public void setLend_name(String lend_name) {
		this.lend_name = lend_name;
	}

	public Date getBorrow_date() {
		return borrow_date;
	}

	public void setBorrow_date(Date borrow_date) {
		this.borrow_date = borrow_date;
	}

	public Date getPay_data() {
		return pay_data;
	}

	public void setPay_data(Date pay_data) {
		this.pay_data = pay_data;
	}

	public Date getRepay_date() {
		return repay_date;
	}

	public void setRepay_date(Date repay_date) {
		this.repay_date = repay_date;
	}

	public Integer getYear_rate() {
		return year_rate;
	}

	public void setYear_rate(Integer year_rate) {
		this.year_rate = year_rate;
	}

	public String getBorrow_use() {
		return borrow_use;
	}

	public void setBorrow_use(String borrow_use) {
		this.borrow_use = borrow_use;
	}

	public Date getDan_date() {
		return dan_date;
	}

	public void setDan_date(Date dan_date) {
		this.dan_date = dan_date;
	}

	public String getDan_state() {
		return dan_state;
	}

	public void setDan_state(String dan_state) {
		this.dan_state = dan_state;
	}

	public Integer getBorrow_id() {
		return borrow_id;
	}

	public void setBorrow_id(Integer borrow_id) {
		this.borrow_id = borrow_id;
	}

	public Integer getLend_id() {
		return lend_id;
	}

	public void setLend_id(Integer lend_id) {
		this.lend_id = lend_id;
	}

	public Integer getMonery() {
		return monery;
	}

	public void setMonery(Integer monery) {
		this.monery = monery;
	}

	public Integer getJia_id() {
		return jia_id;
	}

	public void setJia_id(Integer jia_id) {
		this.jia_id = jia_id;
	}

	private Integer lend_id;
	
	private Integer monery;
	
	private Integer jia_id;

}
