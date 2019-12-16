package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class ValidateOfferResultDTO implements Serializable{
	
	private static final long serialVersionUID = 530327011975513116L;




	private int o_result;
	private String o_result_msg;
	private int gnretval;
	private int gnerrcode;
	private String gserrtext;
	
	public void setO_result(int o_result) {
		this.o_result = o_result;
	}
	public int getO_result() {
		return o_result;
	}
	public void setO_result_msg(String o_result_msg) {
		this.o_result_msg = o_result_msg;
	}
	public String getO_result_msg() {
		return o_result_msg;
	}
	public void setGnretval(int gnretval) {
		this.gnretval = gnretval;
	}
	public int getGnretval() {
		return gnretval;
	}
	public void setGnerrcode(int gnerrcode) {
		this.gnerrcode = gnerrcode;
	}
	public int getGnerrcode() {
		return gnerrcode;
	}
	public void setGserrtext(String gserrtext) {
		this.gserrtext = gserrtext;
	}
	public String getGserrtext() {
		return gserrtext;
	}


}
