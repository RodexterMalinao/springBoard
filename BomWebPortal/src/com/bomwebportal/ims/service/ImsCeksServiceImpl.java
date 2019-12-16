package com.bomwebportal.ims.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.dao.CeksDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.ServerUtils;
import com.bomwebportal.util.uENC;

@Transactional(readOnly=true)
public class ImsCeksServiceImpl implements ImsCeksService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String appEnv;	
	private String ceksTestUrl;	
	private String ceksTestString;	
	private String ceksUrl;	
	private String appIdPrefix;	
	private String appId;
	
	private CeksDAO ceksDao;
	private BomPropertyPlaceholderConfigurer propertyConfigurer;

	public String initCeks(String username) {
		
		logger.info("PaymentServiceImpl initialCeksAccess() ceksTestUrl = " + ceksTestUrl);

		if(ServerUtils.testCeksCatureReady(ceksTestUrl, ceksTestString)){
			logger.info("test ceks capture link OK");
		}else{
			logger.info("ceks cature not avaliable");
			return "N";
		}
		
		String sCeksUrl = null;
		
        try {
        	
			logger.info("PaymentServiceImpl initialCeksAccess() appId = " + appId);
			logger.info("PaymentServiceImpl initialCeksAccess() username = " + username);
			
			String contextId = ceksDao.initialCeksAccess(username, appId);
			
			logger.info("PaymentServiceImpl initialCeksAccess() contextId = " + contextId);
			logger.debug("uEnc:"+uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId));
			
			sCeksUrl = ceksUrl+"?V1="+appId+"&V2="+uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, username)+
								"&V3="+uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId);
			logger.debug("sCeksUrl = " + sCeksUrl);
			
			return sCeksUrl;
			
		} catch (Exception e) {
			logger.error("Exception caught in initialCeksAccess()", e);
			throw new AppRuntimeException(e);
		}
	}

	public void loadingCeksProperties() {
		try {
			
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			ceksTestUrl = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.CEKS_TEST_URL);
			ceksTestString = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.CEKS_TEST_STRING);
			ceksUrl = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+"ims.ceks.url");
			appIdPrefix = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+"ims.ceks.appIdPrefix");
			appId = StringUtils.upperCase(appIdPrefix + appEnv);
			
			logger.debug("appEnv = " + appEnv);
			logger.debug("ceksTestUrl = " + ceksTestUrl);
			logger.debug("ceksTestString = " + ceksTestString);
			logger.debug("ceksUrl = " + ceksUrl);
			logger.debug("appIdPrefix = " + appIdPrefix);
			logger.debug("appId = " + appId);
			
		} catch (Exception e) {
			logger.error("Exception caught in loadingCeksProperties()", e);
			throw new AppRuntimeException(e);
		}
	}

	public CeksDAO getCeksDao() {
		return ceksDao;
	}

	public void setCeksDao(CeksDAO ceksDao) {
		this.ceksDao = ceksDao;
	}

	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
		loadingCeksProperties();
	}

}
