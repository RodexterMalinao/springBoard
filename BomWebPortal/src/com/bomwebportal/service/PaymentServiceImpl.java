package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.dao.CeksDAO;
import com.bomwebportal.dto.IssueBankDTO;
//import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ds.dao.MobDsMrtManagementDAO;
//import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.ServerUtils;
import com.bomwebportal.util.uENC;
import com.bomwebportal.bean.LookupTableBean;

@Transactional(readOnly=true)
public class PaymentServiceImpl implements PaymentService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private CeksDAO ceksDao;
	
	private MobDsMrtManagementDAO mobDsMrtManagementDAO;
	
	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	
	private String appEnv;
	
	private String ceksTestUrl;
	
	private String ceksTestString;
	
	private String ceksUrl;
	
	private String appIdPrefix;
	
	private String appId;
	
	private String appIdPrefixInstant2;
	
	private String appIdInstant2;
	
	public CeksDAO getCeksDao() {
		return ceksDao;
	}

	public void setCeksDao(CeksDAO ceksDao) {
		this.ceksDao = ceksDao;
	}

	public MobDsMrtManagementDAO getMobDsMrtManagementDAO() {
		return mobDsMrtManagementDAO;
	}

	public void setMobDsMrtManagementDAO(MobDsMrtManagementDAO mobDsMrtManagementDAO) {
		this.mobDsMrtManagementDAO = mobDsMrtManagementDAO;
	}
	
	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
		loadingCeksProperties();
	}
	
	public void loadingCeksProperties() {
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			ceksTestUrl = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.CEKS_TEST_URL);
			ceksTestString = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.CEKS_TEST_STRING);
			ceksUrl = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.CEKS_URL);
			appIdPrefix = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.CEKS_APPID_PREFIX);
			appIdPrefixInstant2 = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.CEKS_APPID_PREFIX_INSTANT2);
			appId = StringUtils.upperCase(appIdPrefix + appEnv);
			appIdInstant2 = StringUtils.upperCase(appIdPrefixInstant2 + appEnv);
			
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

	public List<IssueBankDTO> getIssueBankList(){
		return LookupTableBean.getInstance().getIssueBankList();
		/*
		try{
			logger.info("getIssueBankList() is called in PaymentServiceImpl.java");
			return paymentDao.getIssueBankList();
//			return null;
		}catch (DAOException de) {
//		}catch (Exception de) {
			logger.error("Exception caught in getIssueBankList()", de);
			throw new AppRuntimeException(de);
		}*/
		
	}
	
	public String initialCeksAccess(String username, HttpServletRequest request) {
		
		logger.info("PaymentServiceImpl initialCeksAccess() ceksTestUrl = " + ceksTestUrl);

		if(ServerUtils.testCeksCatureReady(ceksTestUrl, ceksTestString)){
			logger.info("test ceks capture link OK");
		}else{
			logger.info("ceks cature not avaliable");
			return "N";
		}
		
		String currentAppId = appId;
		if ("UAT".equalsIgnoreCase(appEnv) && request.getRequestURI().startsWith("/BomWebPortal_CR1/")) {
			currentAppId = appIdInstant2;
		}
		
		String sCeksUrl = null;
		
        try {
        	//String username = paymentDTO.getUsername();
        	
			logger.info("PaymentServiceImpl initialCeksAccess() appId = " + currentAppId);
			logger.info("PaymentServiceImpl initialCeksAccess() username = " + username);
			
			String contextId = ceksDao.initialCeksAccess(username, appId);
//			String contextId = appId + "20110131_0000000003";
			logger.info("PaymentServiceImpl initialCeksAccess() contextId = " + contextId);
			logger.debug("uEnc:"+uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId));
			
			sCeksUrl = ceksUrl+"?V1="+currentAppId+"&V2="+uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, username)+
								"&V3="+uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId);
			logger.debug("sCeksUrl = " + sCeksUrl);
			
			return sCeksUrl;
			
		} catch (Exception e) {
			logger.error("Exception caught in initialCeksAccess()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public String getSalesChannelCd(String staffId, Date appDate) {
		logger.info("getSalesChannelCd() @ PaymentServiceImpl is called.");
		try{
			String channelCd = mobDsMrtManagementDAO.getSalesChannelCd(staffId, appDate);
			logger.debug("Sales ChannelCd = " + channelCd);
			return channelCd;
		} catch (Exception e) {
			logger.error("Exception caught in getSalesChannelCd()", e);
			throw new AppRuntimeException(e);
		}
	}
}
