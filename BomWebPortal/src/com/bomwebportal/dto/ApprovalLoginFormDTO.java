/**
 * 
 */
package com.bomwebportal.dto;

// @author MAX.R.MENG LTS
public class ApprovalLoginFormDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4308606254992903658L;
	
	private String username;
	private String password;
	
	private String errMsg;
	
	private String category;
	private boolean approvalInd;
	
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
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isApprovalInd() {
		return approvalInd;
	}
	public void setApprovalInd(boolean approvalInd) {
		this.approvalInd = approvalInd;
	}
}
