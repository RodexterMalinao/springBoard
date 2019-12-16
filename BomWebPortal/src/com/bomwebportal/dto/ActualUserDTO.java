package com.bomwebportal.dto;

public class ActualUserDTO implements java.io.Serializable {
		
	private static final long serialVersionUID = 7817645258178103589L;
		String orderId;
		String orderType;
		String memberNum;
		String subDocNum;
		String subDocType;
		String subCompanyName;
		String subTitle;
		String subLastName; 
		String subFirstName; 
		String subContactTel;
		String subEmailAddr;
		String subDateOfBirthStr;
		
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getOrderType() {
			return orderType;
		}
		public void setOrderType(String orderType) {
			this.orderType = orderType;
		}
		public String getMemberNum() {
			return memberNum;
		}
		public void setMemberNum(String memberNum) {
			this.memberNum = memberNum;
		}
		public String getSubDocNum() {
			return subDocNum;
		}
		public void setSubDocNum(String subDocNum) {
			this.subDocNum = subDocNum;
		}
		public String getSubDocType() {
			return subDocType;
		}
		public void setSubDocType(String subDocType) {
			this.subDocType = subDocType;
		}
		public String getSubCompanyName() {
			return subCompanyName;
		}
		public void setSubCompanyName(String subCompanyName) {
			this.subCompanyName = subCompanyName;
		}
		public String getSubTitle() {
			return subTitle;
		}
		public void setSubTitle(String subTitle) {
			this.subTitle = subTitle;
		}
		public String getSubLastName() {
			return subLastName;
		}
		public void setSubLastName(String subLastName) {
			this.subLastName = subLastName;
		}
		public String getSubFirstName() {
			return subFirstName;
		}
		public void setSubFirstName(String subFirstName) {
			this.subFirstName = subFirstName;
		}
		public String getSubContactTel() {
			return subContactTel;
		}
		public void setSubContactTel(String subContactTel) {
			this.subContactTel = subContactTel;
		}
		public String getSubEmailAddr() {
			return subEmailAddr;
		}
		public void setSubEmailAddr(String subEmailAddr) {
			this.subEmailAddr = subEmailAddr;
		}
		public String getSubDateOfBirthStr() {
			return subDateOfBirthStr;
		}
		public void setSubDateOfBirthStr(String subDateOfBirthStr) {
			this.subDateOfBirthStr = subDateOfBirthStr;
		}


}
