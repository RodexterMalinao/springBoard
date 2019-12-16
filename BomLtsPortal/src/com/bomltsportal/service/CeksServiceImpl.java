package com.bomltsportal.service;

import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomltsportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomltsportal.dao.CeksDAO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.ServerUtils;
import com.bomltsportal.util.uENC;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.BomWebPortalConstant;



@Transactional(readOnly=true)
public class CeksServiceImpl implements CeksService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String appEnv;	
	private String ceksTestUrl;	
	private String ceksTestString;	
	private String ceksUrl;	
	private String ceksLink1ZH;
	private String ceksLink1EN;
	private String ceksAppId;
	private String ceksAppIdNoPayment;
	private String ceksLink2ZH;
	private String ceksLink2EN;
	private String ceksAppId2;

	private String ceksDelAppId;
	private String ceksDelAppId2;
	private String ceksDelAppIdNoPayment;
	
	private String appIdPrefix;	
	private String appId;
	
	private CeksDAO ceksDao;
	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	
//	private CodeLkupCacheServiceImpl ceksServiceNameLkupService;
//	
//	public CodeLkupCacheServiceImpl getCeksServiceNameLkupService() {
//		return ceksServiceNameLkupService;
//	}
//
//	public void setCeksServiceNameLkupService(
//			CodeLkupCacheServiceImpl ceksServiceNameLkupService) {
//		this.ceksServiceNameLkupService = ceksServiceNameLkupService;
//	}

	public String initCeks(String srvType, String locale, String conflictName, String resourceShortage, String wipInd,	String onpInd, String prepayInd, String amt, String refNo, String order_id, String environment) {
		
		if (!this.IsCeksReady()){
			return "N";
		}
		
		
		String sCeksUrl = null;
		String v7 = Integer.toString( Integer.parseInt(amt) *100);
		String v8 = refNo;
//		String v9 = "Service fee";
		
		
        try {
        	String ax4 = ceksDao.getCeksTitle(srvType, locale);
        	String v9 = ceksDao.getCeksFeeDesc(locale);
        	
        	//Hardcore
        	String username = "SBONLINE";
        	
        	if ( "Y".equalsIgnoreCase(resourceShortage) //|| "Y".equalsIgnoreCase(conflictName)
        			|| "Y".equalsIgnoreCase(wipInd) || "Y".equalsIgnoreCase(onpInd)
        			|| "N".equalsIgnoreCase(prepayInd))
			{
				if (BomLtsConstant.LOCALE_ENG.equals(locale)) {
					ceksUrl = ceksLink1EN;
				}
				else {
					ceksUrl = ceksLink1ZH;
				}
				amt = "1"; // hold $1 for no prepayment item
				appId = StringUtils.equals(srvType, BomLtsConstant.SERVICE_TYPE_EYE)?ceksAppIdNoPayment:ceksDelAppIdNoPayment;
				//ceksDao.insertCeksTempRecord(order_id, appId, refNo, null);
			}
			else
			{
				if (BomLtsConstant.LOCALE_ENG.equals(locale)) {
					ceksUrl = ceksLink2EN;	
				}
				else {
					ceksUrl = ceksLink2ZH;
				}
				
				if ("Y".equalsIgnoreCase(environment)) {
					appId = StringUtils.equals(srvType, BomLtsConstant.SERVICE_TYPE_EYE)?ceksAppId2:ceksDelAppId2;
				} else {
					appId = StringUtils.equals(srvType, BomLtsConstant.SERVICE_TYPE_EYE)?ceksAppId:ceksDelAppId;
				}
				ceksDao.insertCeksTempRecord(order_id, appId, refNo, amt);
			}
        	
    		String ax3 = uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, Integer.toString( Integer.parseInt(amt) *100));

    		logger.info("PaymentServiceImpl initialCeksAccess() appId = " + appId);
			logger.info("PaymentServiceImpl initialCeksAccess() username = " + username);
			
			String contextId = ceksDao.initialCeksAccess(username, appId);
			//String contextId = "SBO0001";
			
			logger.info("PaymentServiceImpl initialCeksAccess() contextId = " + contextId);
			logger.debug("uEnc:"+uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId));
			
			if ( "Y".equals(resourceShortage)// || "Y".equalsIgnoreCase(conflictName)
					|| "Y".equalsIgnoreCase(wipInd) || "Y".equalsIgnoreCase(onpInd))
			{
				sCeksUrl = ceksUrl
				+"?V1=" + appId
				+"&V2=" + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, username)
				+"&V3=" + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId)
//				+"&V7=" + 100
				+"&V8=" + v8
				+"&V9=" + (StringUtils.equals(locale, BomLtsConstant.LOCALE_CHI) ? URLEncoder.encode(v9, "BIG5") : v9)
				+"&AX3=" + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, Integer.toString(100))
				+"&AX4=" + (StringUtils.equals(locale, BomLtsConstant.LOCALE_CHI) ? URLEncoder.encode(ax4, "BIG5") : ax4);
			}
			else
			{
				sCeksUrl = ceksUrl
				+"?V1=" + appId
				+"&V2=" + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, username)
				+"&V3=" + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, contextId)
//				+"&V7=" + v7
				+"&V8=" + v8
				+"&V9=" + (StringUtils.equals(locale, BomLtsConstant.LOCALE_CHI) ? URLEncoder.encode(v9, "BIG5") : v9)
				+"&AX3=" + ax3
				+"&AX4=" + (StringUtils.equals(locale, BomLtsConstant.LOCALE_CHI) ? URLEncoder.encode(ax4, "BIG5") : ax4);
			}
			
			
			logger.info("(Amount before times 100=" + amt + ")");
			logger.info("sCeksUrl = " + sCeksUrl);
			logger.debug("sCeksUrl = " + sCeksUrl);
			return sCeksUrl;
			
		} catch (Exception e) {
			logger.error("Exception caught in initialCeksAccess()", e);
			throw new AppRuntimeException(e);
		}
	}

	public void updateCTR(String order_id, String reference_no, String ret_code, String status, String card_pan, String ret_parm, String exp_date) throws DAOException
	{
		try{
			ceksDao.updateCeksTempRecord(order_id, reference_no, ret_code, status, card_pan, ret_parm, exp_date);
		}catch (DAOException e) {
			// TODO: handle exception
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> checkCTR(String order_id, String status, String ret_code) throws DAOException{
		try{
			return ceksDao.getCeksTempRecord(order_id, status, ret_code);
		}catch (DAOException e) {
			// TODO: handle exception
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void loadingCeksProperties() {
		try {
			
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			ceksTestUrl = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.CEKS_TEST_URL);
			ceksTestString = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.CEKS_TEST_STRING);
			//ceksUrl = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+"ims.ceks.url");
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

	public String getCeksLink1ZH() {
		return ceksLink1ZH;
	}

	public void setCeksLink1ZH(String ceksLink1ZH) {
		this.ceksLink1ZH = ceksLink1ZH;
	}

	public String getCeksLink1EN() {
		return ceksLink1EN;
	}

	public void setCeksLink1EN(String ceksLink1EN) {
		this.ceksLink1EN = ceksLink1EN;
	}

	public String getCeksLink2ZH() {
		return ceksLink2ZH;
	}

	public void setCeksLink2ZH(String ceksLink2ZH) {
		this.ceksLink2ZH = ceksLink2ZH;
	}

	public String getCeksLink2EN() {
		return ceksLink2EN;
	}

	public void setCeksLink2EN(String ceksLink2EN) {
		this.ceksLink2EN = ceksLink2EN;
	}

	public boolean IsCeksReady(){
		if(ServerUtils.testCeksCatureReady(ceksTestUrl, ceksTestString)){
			logger.info("test ceks capture link OK");
			return true;
		}else{
			logger.info("ceks cature not avaliable");
			return false;
		}
	}

	public void setCeksAppId(String ceksAppId) {
		this.ceksAppId = ceksAppId;
	}

	public String getCeksAppId() {
		return ceksAppId;
	}

	public void setCeksAppIdNoPayment(String ceksAppIdNoPayment) {
		this.ceksAppIdNoPayment = ceksAppIdNoPayment;
	}

	public String getCeksAppIdNoPayment() {
		return ceksAppIdNoPayment;
	}

	public void setCeksAppId2(String ceksAppId2) {
		this.ceksAppId2 = ceksAppId2;
	}

	public String getCeksAppId2() {
		return ceksAppId2;
	}

	public String getCeksDelAppId() {
		return ceksDelAppId;
	}

	public void setCeksDelAppId(String ceksDelAppId) {
		this.ceksDelAppId = ceksDelAppId;
	}

	public String getCeksDelAppId2() {
		return ceksDelAppId2;
	}

	public void setCeksDelAppId2(String ceksDelAppId2) {
		this.ceksDelAppId2 = ceksDelAppId2;
	}

	public String getCeksDelAppIdNoPayment() {
		return ceksDelAppIdNoPayment;
	}

	public void setCeksDelAppIdNoPayment(String ceksDelAppIdNoPayment) {
		this.ceksDelAppIdNoPayment = ceksDelAppIdNoPayment;
	}


}
