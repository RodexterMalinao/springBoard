package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class MobCcsPaymentUpfrontDTO implements Serializable{
	private String order_id;
	private String mrt;
	private String cc_type;
	private String cc_num;
	private String cc_hold_name;
	private String cc_exp_date;
	private String pay_method;	
	private Double ad_amount;		
	private Double up_amount;	
	private Double out_amout;
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getMrt() {
		return mrt;
	}
	public void setMrt(String mrt) {
		this.mrt = mrt;
	}
	public String getCc_type() {
		return cc_type;
	}
	public void setCc_type(String cc_type) {
		this.cc_type = cc_type;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
	public String getCc_num() {
		return cc_num;
	}
	public void setCc_num(String cc_num) {
		this.cc_num = cc_num;
	}
	public Double getAd_amount() {
		return ad_amount;
	}
	public void setAd_amount(Double ad_amount) {
		this.ad_amount = ad_amount;
	}
	public String getCc_hold_name() {
		return cc_hold_name;
	}
	public void setCc_hold_name(String cc_hold_name) {
		this.cc_hold_name = cc_hold_name;
	}
	public Double getUp_amount() {
		return up_amount;
	}
	public void setUp_amount(Double up_amount) {
		this.up_amount = up_amount;
	}
	public String getCc_exp_date() {
		return cc_exp_date;
	}
	public void setCc_exp_date(String cc_exp_date) {
		this.cc_exp_date = cc_exp_date;
	}
	public Double getOut_amout() {
		return out_amout;
	}
	public void setOut_amout(Double out_amout) {
		this.out_amout = out_amout;
	}
	
}
