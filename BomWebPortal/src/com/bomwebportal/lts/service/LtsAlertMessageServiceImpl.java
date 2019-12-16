package com.bomwebportal.lts.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.AlertOrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LtsAlertMessageDAO;
import com.pccw.util.search.SearchResult;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;
import com.pccw.wq.service.WorkQueueService;


public class LtsAlertMessageServiceImpl implements LtsAlertMessageService {
	
	private WorkQueueService workQueueService;
	private LtsAlertMessageDAO ltsAlertMessageDAO;
	protected final Log logger = LogFactory.getLog(getClass());
	
	public String getAlertCount(String pSrvType, String pUser)throws Exception{
		int count = 0;
		SearchResult<WqInquiryResultFormDTO> searchResult = workQueueService.searchSbIdsWithOutstandingWq(pSrvType,pUser);
		count = searchResult.getTotalCount();
		return String.valueOf(count);
	}
	
	private Map<String, WqInquiryResultFormDTO> getSbIdsWithOutstandingWq(String pSrvType, String pUser)throws Exception{ //return sbId and corresponding WqInquiryResultFormDTO
		Map<String, WqInquiryResultFormDTO> resultMap = new HashMap<String,WqInquiryResultFormDTO>();
		SearchResult<WqInquiryResultFormDTO> searchResult = workQueueService.searchSbIdsWithOutstandingWq(pSrvType,pUser);
		if (searchResult != null) {
			for (WqInquiryResultFormDTO eachRec : searchResult.getResult()) {
				resultMap.put(eachRec.getSbId(), eachRec);		
			}
		}
		return resultMap;
	}

	public AlertOrderDTO[] getOrderListWithOutstandingWq(String pSrvType, String pUser)throws Exception{
		AlertOrderDTO[] alertOrderList = null;
		Map<String,WqInquiryResultFormDTO> resultMap = null;
		resultMap = this.getSbIdsWithOutstandingWq(pSrvType, pUser);
		String[] sbIds = {};
		String tmp;
		if (resultMap!=null){
			sbIds = resultMap.keySet().toArray(sbIds);
		}
		try {
			if (resultMap.size()!=0) {
				alertOrderList = ltsAlertMessageDAO.getOrderListWithOutstandingWq(sbIds,pSrvType);
				WqInquiryResultFormDTO wqDTO = null;
				for (AlertOrderDTO eachOrder: alertOrderList){
					wqDTO = resultMap.get(eachOrder.getOrderId());
					if (wqDTO == null) {
						continue;
					}
					tmp = wqDTO.getWqNatureDesc();
					if (tmp != null) {
						tmp = tmp.replaceAll("(\r\n|\n)", "<br />");
					}
					eachOrder.setWqNatureDesc(tmp);

					tmp = wqDTO.getWpDesc();
					if (tmp != null) {
						tmp = tmp.replaceAll("(\r\n|\n)", "<br />");
					}
					eachOrder.setWpDesc(tmp);
				}
				return alertOrderList;
			} else {
				return null;
			}

		} catch (DAOException de) {
			logger.error("Exception caught in getOrderList()", de);
			throw new AppRuntimeException(de);
		}
	}

	public WorkQueueService getWorkQueueService() {
		return this.workQueueService;
	}


	public void setWorkQueueService(WorkQueueService pWorkQueueService) {
		this.workQueueService = pWorkQueueService;
	}

	public LtsAlertMessageDAO getLtsAlertMessageDAO() {
		return this.ltsAlertMessageDAO;
	}

	public void setLtsAlertMessageDAO(LtsAlertMessageDAO pLtsAlertMessageDAO) {
		this.ltsAlertMessageDAO = pLtsAlertMessageDAO;
	}
}