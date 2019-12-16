package com.bomwebportal.lts.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;

public class CsPortalResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 240077769064470159L;
		
	public static final String RETURN_SUCCESS = "0";	
	public static final String RETURN_WARNING = "1";
	public static final String RETURN_ERROR = "2";
	public static final String RETURN_FATAL = "3";
	public static final String SUCCESS_MESSAGE = "SUCCESS";	
	public static final String ERROR_MESSAGE = "ERROR";
	
	private String rtnCd;
	private String rtnDesc; 
	
	/**
	 * @return the rtnCd
	 */
	public String getRtnCd() {
		return rtnCd;
	}

	/**
	 * @param rtnCd the rtnCd to set
	 */
	public void setRtnCd(String rtnCd) {
		this.rtnCd = rtnCd;
	}

	/**
	 * @return the rtnDesc
	 */
	public String getRtnDesc() {
		return rtnDesc;
	}

	/**
	 * @param rtnDesc the rtnDesc to set
	 */
	public void setRtnDesc(String rtnDesc) {
		this.rtnDesc = rtnDesc;
	}

	/**
	 * @return the errorDescriptionMap
	 */
	public static String getReplyDesc(String key) {
		if (StringUtils.isNotBlank(key)) {
			String desc = (String)LtsCsPortalBackendConstant.CSP_REPLY_DESC_MAP.get(key);
			if (StringUtils.isNotBlank(desc)) {
				return desc;
			}
		}
		return null;
	}

}
