package com.bomwebportal.lts.service;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LtsBomOrderStatusDAO;
import com.bomwebportal.lts.dao.LtsOrderSearchDAO;
import com.bomwebportal.lts.dto.LtsOrderSearchDTO;
import com.bomwebportal.lts.dto.OrderSrvStatusDetailDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.util.uENC;


public class LtsOrderSearchServiceImpl implements LtsOrderSearchService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private LtsOrderSearchDAO ltsOrderSearchDAO;
	private String sboSrdAmendUrl;
	private LtsBomOrderStatusDAO ltsBomOrderStatusDAO;
	
	public LtsOrderSearchDAO getLtsOrderSearchDAO() {
		return ltsOrderSearchDAO;
	}
	
	public void setLtsOrderSearchDAO(LtsOrderSearchDAO ltsOrderSearchDAO) {
		this.ltsOrderSearchDAO = ltsOrderSearchDAO;
	}

	public String getSboSrdAmendUrl() {
		return sboSrdAmendUrl;
	}

	public void setSboSrdAmendUrl(String sboSrdAmendUrl) {
		this.sboSrdAmendUrl = sboSrdAmendUrl;
	}

	public LtsBomOrderStatusDAO getLtsBomOrderStatusDAO() {
		return ltsBomOrderStatusDAO;
	}

	public void setLtsBomOrderStatusDAO(LtsBomOrderStatusDAO ltsBomOrderStatusDAO) {
		this.ltsBomOrderStatusDAO = ltsBomOrderStatusDAO;
	}

	public List<LtsOrderSearchDTO> searchLtsOnlineOrder(String orderId,
			String idDocType, String idDocNum, String srvNum, String email, String userId) {
		
		try {
			List<LtsOrderSearchDTO> resultList = ltsOrderSearchDAO.searchLtsOnlineOrder(orderId, idDocType, idDocNum, srvNum, email);
			setAmendSrdUrl(resultList, userId);
			return resultList;
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}
	

	private void setAmendSrdUrl(List<LtsOrderSearchDTO> ltsOrderSearchList, String userId) {
		if (ltsOrderSearchList == null || ltsOrderSearchList.isEmpty()) {
			return;
		}
		for (LtsOrderSearchDTO ltsOrderSearch : ltsOrderSearchList) {
			String key = uENC.encAES(LtsConstant.URL_PARM_ENC_KEY, ltsOrderSearch.getOrderId());
			ltsOrderSearch.setAmendSrdUrl(new String(sboSrdAmendUrl + key + "&userId=" + userId));
		}
		
	}
	

	public List<LtsOrderSearchDTO> searchLtsCcOrder(String orderId,
			String idDocType, String idDocNum, String srvNum, String email, String userId, String[] channelId, 
			String staffNum, String salesTeam, String[] salesCenters, String salesChannel, String orgStaffId, String startDate, String endDate, String status, 
			String srdStartDate, String srdEndDate){
		
		try {
			List<LtsOrderSearchDTO> resultList = ltsOrderSearchDAO.searchLtsCcOrder(
					orderId, idDocType, idDocNum, srvNum, email, channelId, staffNum, salesTeam, salesCenters, salesChannel, orgStaffId, 
					startDate, endDate, status, srdStartDate, srdEndDate);
//			setAmendSrdUrl(resultList, userId);
			return resultList;
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}

	
	public List<OrderSrvStatusDetailDTO> getPendingOrderSrvStatusList(String pOcid){
		try {
			List<OrderSrvStatusDetailDTO> orderStatus = ltsBomOrderStatusDAO.getPendingOrderSrvStatusList(pOcid);
			return orderStatus;
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}

	public List<String> getDsSalesCenter(String staffId, String channelId){
		try {
			return ltsOrderSearchDAO.getDsSalesCenter(staffId, channelId);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
}
