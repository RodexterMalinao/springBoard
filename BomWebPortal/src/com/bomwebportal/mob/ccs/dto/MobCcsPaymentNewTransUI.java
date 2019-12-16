package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MobCcsPaymentNewTransUI implements Serializable{
	
	private String order_id;
	
	private String mrt;	
	private String cc_type;
	private String pay_method;	
	private String cc_num;
	private Double ad_amount;	
	private String cc_hold_name;
	private Double up_amount;		
	private String cc_exp_date;
	private Double out_amout;
	
	private String ref_no;
	private String transDateStr;
	private Date transDate;	
	private String pay_comb;
	private List<CodeLkupDTO> payMethodList;
	private Double pay_amount;	
	private String lastUpdateBy;
	
	private String msg;
	private String actionType;
	
	public String getMrt() {
		return mrt;
	}
	public void setMrt(String mrt) {
		this.mrt = mrt;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getCc_type() {
		return cc_type;
	}
	public void setCc_type(String cc_type) {
		this.cc_type = cc_type;
	}
	public String getCc_num() {
		return cc_num;
	}
	public void setCc_num(String cc_num) {
		this.cc_num = cc_num;
	}
	public String getCc_hold_name() {
		return cc_hold_name;
	}
	public void setCc_hold_name(String cc_hold_name) {
		this.cc_hold_name = cc_hold_name;
	}
	public String getCc_exp_date() {
		return cc_exp_date;
	}
	public void setCc_exp_date(String cc_exp_date) {
		this.cc_exp_date = cc_exp_date;
	}
	public String getPay_comb() {
		return pay_comb;
	}
	public void setPay_comb(String pay_comb) {
		this.pay_comb = pay_comb;
	}	
	public List<CodeLkupDTO> getPayMethodList() {
		return payMethodList;
	}
	public void setPayMethodList(List<CodeLkupDTO> payMethodList) {
		this.payMethodList = payMethodList;
	}
	public Double getAd_amount() {
		return ad_amount;
	}
	public void setAd_amount(Double ad_amount) {
		this.ad_amount = ad_amount;
	}
	public Double getUp_amount() {
		return up_amount;
	}
	public void setUp_amount(Double up_amount) {
		this.up_amount = up_amount;
	}
	public Double getOut_amout() {
		return out_amout;
	}
	public void setOut_amout(Double out_amout) {
		this.out_amout = out_amout;
	}
	public String getRef_no() {
		return ref_no;
	}
	public void setRef_no(String ref_no) {
		this.ref_no = ref_no;
	}
	public String getTransDateStr() {
		return transDateStr;
	}
	public void setTransDateStr(String transDateStr) {
		this.transDateStr = transDateStr;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
	public Double getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(Double pay_amount) {
		this.pay_amount = pay_amount;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	
}
