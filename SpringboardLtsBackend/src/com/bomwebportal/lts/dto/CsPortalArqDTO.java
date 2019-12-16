package com.bomwebportal.lts.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;

public class CsPortalArqDTO extends CsPortalResponseDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6444147400086772045L;
	private String apiTy;
	private String reply; // Reply Code returned from API
	private String clnVer; // Client Version, must be empty string in this moment
	private String sysId; // The System Id assigned to your application
	private String sysPwd; // The Password assigned to your System Id
	private String userId; // It must be empty string
	private String psnTy; // It must be empty string
	/**
	 * @return the apiTy
	 */
	public String getApiTy() {
		return apiTy;
	}
	/**
	 * @param apiTy the apiTy to set
	 */
	public void setApiTy(String apiTy) {
		this.apiTy = apiTy;
	}
	/**
	 * @return the reply
	 */
	public String getReply() {
		return reply;
	}
	/**
	 * @param reply the reply to set
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}
	/**
	 * @return the clnVer
	 */
	public String getClnVer() {
		return clnVer;
	}
	/**
	 * @param clnVer the clnVer to set
	 */
	public void setClnVer(String clnVer) {
		this.clnVer = clnVer;
	}
	/**
	 * @return the sysId
	 */
	public String getSysId() {
		return sysId;
	}
	/**
	 * @param sysId the sysId to set
	 */
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	/**
	 * @return the sysPwd
	 */
	public String getSysPwd() {
		return sysPwd;
	}
	/**
	 * @param sysPwd the sysPwd to set
	 */
	public void setSysPwd(String sysPwd) {
		this.sysPwd = sysPwd;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the psnTy
	 */
	public String getPsnTy() {
		return psnTy;
	}
	/**
	 * @param psnTy the psnTy to set
	 */
	public void setPsnTy(String psnTy) {
		this.psnTy = psnTy;
	}
	
	public boolean isSystemBusy() {
		return StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_GATE_BUSY, this.getReply())
		|| StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_BUSY, this.getReply());
	}
	
	public boolean isReturnError() {
		return !StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS, this.getReply())
		|| !StringUtils.equals(CsPortalResponseDTO.RETURN_SUCCESS, this.getRtnCd());
	}

}
