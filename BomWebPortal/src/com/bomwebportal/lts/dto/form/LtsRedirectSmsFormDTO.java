package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class LtsRedirectSmsFormDTO implements Serializable {
	/*
	 * https://win.pccw-hkt.com/openapi/ecapi?username=s_eyemkt&password=
	 * eyemkt12&msgType=1&charSet=BIG5&number=85291418073&msg=TEST
	 * 
	 * Sample URL of BOM sending SMS
	 */
	String username;
	String password;
	String msgType;
	String charSet;
	String number;
	String msg;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getCharSet() {
		return charSet;
	}
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		String ret = ReflectionToStringBuilder.toString(this);
		return ret;
	}

}
