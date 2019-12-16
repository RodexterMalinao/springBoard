package com.bomwebportal.service;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.SearchingKeyDTO;

import com.bomwebportal.dao.LoggingDAO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;

public class LoggingServiceImpl implements LoggingService {

	private final Log logger = LogFactory.getLog(getClass());
//	private LoggingWsClient loggingWsClient;
//	private LogWsClient logWsClient;
	private LoggingDAO loggingDao;

	private static final String SEARCH_FUNC_CUST_NO= "SB-CUSTOMER_NO";
	private static final String SEARCH_FUNC_TEL = "SB-TEL";
	private static final String SEARCH_FUNC_3G = "SB-3G";
	private static final String SEARCH_FUNC_PASSPORT = "SB-PASSPORT";
	private static final String SEARCH_FUNC_HKID = "SB-HKID";
	private static final String SEARCH_FUNC_HKBR = "SB-HKBR";
	private static final String SEARCH_FUNC_CERT = "SB-CERTIFICATE";
	private static final String SEARCH_FUNC_LOGIN_ID = "SB-LOGIN_ID";
	private static final String RECALL_LTS = "SB-ORD-TEL";
	
	private static final String SEARCH_TYPE_CUSTOMER = "C";
	private static final String SEARCH_TYPE_SERVICE = "S";
	private static final String SEARCH_TYPE_DOC = "D";
	private static final String SEARCH_TYPE_LOGIN_ID = "L";
	
	private static Map<String, String> searchTypeLookupMap = new TreeMap<String, String>();
	
	
	static {
		searchFunctionLookupMap();
	}

	private static void searchFunctionLookupMap() {
		searchTypeLookupMap.put(LtsConstant.SERVICE_TYPE_TEL, SEARCH_FUNC_TEL);
		searchTypeLookupMap.put(LtsConstant.SERVICE_TYPE_MRT, SEARCH_FUNC_3G);
		searchTypeLookupMap.put(LtsConstant.DOC_TYPE_PASSPORT, SEARCH_FUNC_PASSPORT);
		searchTypeLookupMap.put(LtsConstant.DOC_TYPE_HKID, SEARCH_FUNC_HKID);
		searchTypeLookupMap.put(LtsConstant.DOC_TYPE_HKBR, SEARCH_FUNC_HKBR);
		searchTypeLookupMap.put(LtsConstant.DOC_TYPE_CERTIFICATE, SEARCH_FUNC_CERT);
		searchTypeLookupMap.put("Passport", SEARCH_FUNC_PASSPORT);
		searchTypeLookupMap.put("BR", SEARCH_FUNC_HKBR);
		searchTypeLookupMap.put("Certificate No", SEARCH_FUNC_CERT);
	}
	
	public void logSearchByCriteria(String pUser, SearchingKeyDTO pSearchingKey) {
		
		if (StringUtils.isNotEmpty(pSearchingKey.getIdDocType())) {
			this.logSearchByDoc(pUser, pSearchingKey.getIdDocType(), pSearchingKey.getIdDocNum());
		} else if (StringUtils.isNotEmpty(pSearchingKey.getDomainType())) {
			this.logSearchByLoginId(pUser, pSearchingKey.getLoginId(), pSearchingKey.getDomainType());
		} else if (StringUtils.isNotEmpty(pSearchingKey.getServiceType())) {
			this.logSearchByService(pUser, pSearchingKey.getServiceType(), pSearchingKey.getServiceNum());
		}
	}
	
	public void logSearchByCustomerNum(String pUser, String pCustNum) {
		try {
			this.loggingDao.logAction(pUser, SEARCH_FUNC_CUST_NO, pCustNum, SEARCH_TYPE_CUSTOMER);
		} catch (Exception e) {
			logger.error("Error in logSearchByCustomerNum\n" + e.getMessage(), e);
		}
	}
	
	public void logSearchByLoginId(String pUser, String pLoginId, String pDomain) {
		try {
			this.loggingDao.logAction(pUser, SEARCH_FUNC_LOGIN_ID, pLoginId + pDomain, SEARCH_TYPE_LOGIN_ID);
		} catch (Exception e) {
			logger.error("Error in logSearchByLoginId\n" + e.getMessage(), e);
		}
	}
	
	public void logSearchByService(String pUser, String pSrvType, String pSrvNum) {
		try {
			this.loggingDao.logAction(pUser, searchTypeLookupMap.get(pSrvType), pSrvNum, SEARCH_TYPE_SERVICE);
		} catch (Exception e) {
			logger.error("Error in logSearchByService\n" + e.getMessage(), e);
		}
	}
	
	public void logSearchByDoc(String pUser, String pDocType, String pDocNum) {
		try {
			this.loggingDao.logAction(pUser, searchTypeLookupMap.get(pDocType), pDocNum, SEARCH_TYPE_DOC);
		} catch (Exception e) {
			logger.error("Error in logSearchByDoc\n" + e.getMessage(), e);
		}
	}
	
	public void logRecallLtsOrder(String pUser, SbOrderDTO pSbOrder) {
		
		ServiceDetailLtsDTO srvDtlLts = LtsSbOrderHelper.getLtsService(pSbOrder);
		
		try {
			this.loggingDao.logAction(pUser, RECALL_LTS, srvDtlLts.getSrvNum(), SEARCH_TYPE_SERVICE);
		} catch (Exception e) {
			logger.error("Error in logSearchByDoc\n" + e.getMessage(), e);
		}
	}

	public LoggingDAO getLoggingDao() {
		return loggingDao;
	}

	public void setLoggingDao(LoggingDAO loggingDao) {
		this.loggingDao = loggingDao;
	}

//	public LogWsClient getLogWsClient() {
//		return logWsClient;
//	}
//
//	public void setLogWsClient(LogWsClient logWsClient) {
//		this.logWsClient = logWsClient;
//	}
//	
//	public LoggingWsClient getLoggingWsClient() {
//		return loggingWsClient;
//	}
//
//	public void setLoggingWsClient(LoggingWsClient loggingWsClient) {
//		this.loggingWsClient = loggingWsClient;
//	}
}
