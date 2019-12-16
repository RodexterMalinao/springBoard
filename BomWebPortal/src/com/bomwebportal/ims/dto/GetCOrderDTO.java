package com.bomwebportal.ims.dto;

import java.io.Serializable;

//steven created this java file, plz find steven if u find anything wrong
public class GetCOrderDTO implements Serializable{
	
	private static final long serialVersionUID = 530327011975513116L;



private String o_create_c_order;
private String o_reason;
private String o_related_fsa;
private String o_tech;
private int o_bw;

//martin
private String desc;

public void setO_create_c_order(String o_create_c_order) {
	this.o_create_c_order = o_create_c_order;
}
public String getO_create_c_order() {
	return o_create_c_order;
}
public void setO_reason(String o_reason) {
	this.o_reason = o_reason;
}
public String getO_reason() {
	return o_reason;
}
public void setO_related_fsa(String o_related_fsa) {
	this.o_related_fsa = o_related_fsa;
}
public String getO_related_fsa() {
	return o_related_fsa;
}
public void setO_tech(String o_tech) {
	this.o_tech = o_tech;
}
public String getO_tech() {
	return o_tech;
}
public void setO_bw(int o_bw) {
	this.o_bw = o_bw;
}
public int getO_bw() {
	return o_bw;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}


}
