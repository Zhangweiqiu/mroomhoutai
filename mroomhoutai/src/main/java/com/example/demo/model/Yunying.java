package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yunying {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer yid;
	
	private String collectToken;
	
	private String realname;
	
	private String idcard;
	
	private String cellphone;
	
	private String place;
	
	private String othercellphone;
	
	private String inittype;
	
	private String starttime;
	
	private String usetime;
	
	private String calltype;

	public String getUsetime() {
		return usetime;
	}

	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}

	public String getCalltype() {
		return calltype;
	}

	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}

	public Integer getYid() {
		return yid;
	}

	public void setYid(Integer yid) {
		this.yid = yid;
	}

	public String getCollectToken() {
		return collectToken;
	}

	public void setCollectToken(String collectToken) {
		this.collectToken = collectToken;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getOthercellphone() {
		return othercellphone;
	}

	public void setOthercellphone(String othercellphone) {
		this.othercellphone = othercellphone;
	}

	public String getInittype() {
		return inittype;
	}

	public void setInittype(String inittype) {
		this.inittype = inittype;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
}
