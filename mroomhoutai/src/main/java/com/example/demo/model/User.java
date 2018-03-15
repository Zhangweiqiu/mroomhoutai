package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private Integer u_id;
	
	private String u_name;
	
	private String u_identity;

	private String u_card;
	
	private String u_cardph;
	
	private String u_phone;
	
	private Integer u_showmodal;
	
	private String u_password;
	
	private String u_state;
	
	private String u_deposit;
	public String getU_deposit() {
		return u_deposit;
	}

	public void setU_deposit(String u_deposit) {
		this.u_deposit = u_deposit;
	}

	public User() {
		super();
	}

	public Integer getU_id() {
		return u_id;
	}

	public void setU_id(Integer u_id) {
		this.u_id = u_id;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_identity() {
		return u_identity;
	}

	public void setU_identity(String u_identity) {
		this.u_identity = u_identity;
	}

	public String getU_card() {
		return u_card;
	}

	public void setU_card(String u_card) {
		this.u_card = u_card;
	}

	public String getU_cardph() {
		return u_cardph;
	}

	public void setU_cardph(String u_cardph) {
		this.u_cardph = u_cardph;
	}

	public String getU_phone() {
		return u_phone;
	}

	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}



	public Integer getU_showmodal() {
		return u_showmodal;
	}

	public void setU_showmodal(Integer u_showmodal) {
		this.u_showmodal = u_showmodal;
	}

	public String getU_password() {
		return u_password;
	}

	public void setU_password(String u_password) {
		this.u_password = u_password;
	}

	public String getU_state() {
		return u_state;
	}

	public void setU_state(String u_state) {
		this.u_state = u_state;
	}


}
