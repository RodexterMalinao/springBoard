package com.bomwebportal.mob.ccs.service;

import javax.servlet.http.HttpServletRequest;

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
public class MobCcsCeksServiceImpl implements MobCcsCeksService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private CeksDAO ceksDao;
	private BomPropertyPlaceholderConfigurer propertyConfigurer;

	private String appEnv;	
	private String ceksTestUrl;	
	private String ceksTestString;	
	private String ceksUrl;	
	private String appIdPrefix;
	private String appId;
	private String appIdPrefixInstant2;
	private String appIdInstant2;
	private String appCeksModeId; //Add by Herbert 20120710
	
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

	private void loadingCeksProperties() {
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			//Add by Herbert 20120710
			appCeksModeId = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.CEKS_CCS_MODE_ID);
			
			/*Add by herbert 20120103 for UAT2 & PRD2*/
			//Modify by Herbert 20120710
			if ("UAT".equalsIgnoreCase(appEnv)){
				//appEnv = appEnv + "2";
				appEnv = appCeksModeId + appEnv ;
				logger.debug("CCUAT Setting:" + appEnv);
			}
			if ("PRD".equalsIgnoreCase(appEnv)){
				//appEnv = appEnv + "2";
				appEnv = appCeksModeId + appEnv;
				logger.debug("CCPRD2 Setting:" + appEnv);
			}
			
			ceksTestUrl = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.CEKS_TEST_URL);
			ceksTestString = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.CEKS_TEST_STRING);
			ceksUrl = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+"ceks.url");
			appIdPrefix = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+"ceks.appIdPrefix");
			appIdPrefixInstant2 = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.CEKS_APPID_PREFIX_INSTANT2);
			appId = StringUtils.upperCase(appIdPrefix + appEnv);
			appIdInstant2 = StringUtils.upperCase(appIdPrefixInstant2 + appEnv);
			
			if (logger.isDebugEnabled()) {
				logger.debug("appEnv = " + appEnv);
				logger.debug("ceksTestUrl = " + ceksTestUrl);
				logger.debug("ceksTestString = " + ceksTestString);
				logger.debug("ceksUrl = " + ceksUrl);
				logger.debug("appIdPrefix = " + appIdPrefix);
				logger.debug("appId = " + appId);
			}
		} catch (Exception e) {
			logger.error("Exception caught in loadingCeksProperties()", e);
			throw new AppRuntimeException(e);
		}
	}

	public String initCeks(String username, HttpServletRequest request) {
		logger.info("MobCcsCeksServiceImpl initCeks() ceksTestUrl = " + ceksTestUrl);

		if (ServerUtils.testCeksCatureReady(ceksTestUrl, ceksTestString)) {
			if (logger.isInfoEnabled()) logger.info("test ceks capture link OK");
		} else {
			if (logger.isInfoEnabled()) logger.info("ceks cature not avaliable");
			return "N";
		}
		
		String currentAppId = appId;
		if ("CCUAT".equalsIgnoreCase(appEnv) && request.getRequestURI().startsWith("/BomWebPortal_CR1/")) {
			currentAppId = appIdInstant2;
		}
		
        try {
        	if (logger.isInfoEnabled()) {
				logger.info("MobCcsCeksServiceImpl initCeks() appId = " + currentAppId);
				logger.info("MobCcsCeksServiceImpl initCeks() username = " + username);
        	}
			
			String contextId = ceksDao.initialCeksAccess(username, currentAppId);
			
			if (logger.isInfoEnabled()) logger.info("MobCcsCeksServiceImpl initCeks() contextId = " + contextId);
			if (logger.isDebugEnabled()) logger.debug("uEnc:" + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId));
			
			String sCeksUrl = ceksUrl 
					+ "?V1=" + currentAppId 
					+ "&V2=" + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, username)
					+ "&V3=" + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId);
			
			if (logger.isDebugEnabled()) logger.debug("sCeksUrl = " + sCeksUrl);
			
			return sCeksUrl;
		} catch (Exception e) {
			logger.error("Exception caught in initCeks()", e);
			throw new AppRuntimeException(e);
		}
	}

}
