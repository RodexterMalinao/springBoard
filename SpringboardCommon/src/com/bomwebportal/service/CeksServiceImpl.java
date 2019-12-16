package com.bomwebportal.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.CeksDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.ceks.CeksEncUtil;
import com.bomwebportal.util.ceks.CeksServerUtils;
import com.pccw.util.spring.SpringApplicationContext;

@Transactional(readOnly=true)
public class CeksServiceImpl implements CeksService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String appEnv;	
	private String ceksTestUrl;	
	private String ceksTestString;	
	private String ceksUrl;	
	private String appId;
	
	public String initCeks(String pUsername) {
		
		logger.info("PaymentServiceImpl initialCeksAccess() ceksTestUrl = " + ceksTestUrl);

		if (CeksServerUtils.testCeksCatureReady(ceksTestUrl, ceksTestString)) {
			logger.info("test ceks capture link OK");
		}else{
			logger.info("ceks cature not avaliable");
			return "N";
		}
		
		String sCeksUrl = null;
		
        try {
        	
			logger.info("PaymentServiceImpl initialCeksAccess() appId = " + appId);
			logger.info("PaymentServiceImpl initialCeksAccess() username = " + pUsername);
			
			String contextId = SpringApplicationContext.getBean(CeksDAO.class).initialCeksAccess(pUsername, appId);
			
			logger.info("PaymentServiceImpl initialCeksAccess() contextId = " + contextId);
			logger.debug("uEnc:" + CeksEncUtil.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId));
			
			sCeksUrl = ceksUrl+"?V1=" + appId + "&V2=" + CeksEncUtil.encAES(BomWebPortalConstant.CEKS_ENC_KEY, pUsername)+
								"&V3="+CeksEncUtil.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId);
			logger.debug("sCeksUrl = " + sCeksUrl);
			
			return sCeksUrl;
			
		} catch (Exception e) {
			logger.error("Exception caught in initialCeksAccess()", e);
			throw new AppRuntimeException(e);
		}
	}

	public String getAppEnv() {
		return this.appEnv;
	}

	public void setAppEnv(String pAppEnv) {
		this.appEnv = pAppEnv;
	}

	public String getCeksTestUrl() {
		return this.ceksTestUrl;
	}

	public void setCeksTestUrl(String pCeksTestUrl) {
		this.ceksTestUrl = pCeksTestUrl;
	}

	public String getCeksTestString() {
		return this.ceksTestString;
	}

	public void setCeksTestString(String pCeksTestString) {
		this.ceksTestString = pCeksTestString;
	}

	public String getCeksUrl() {
		return this.ceksUrl;
	}

	public void setCeksUrl(String pCeksUrl) {
		this.ceksUrl = pCeksUrl;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String pAppId) {
		this.appId = pAppId;
	}
}