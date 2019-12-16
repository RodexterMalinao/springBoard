package com.bomwebportal.lts.service.order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dao.WorkQueueOrderSearchDAO;
import com.pccw.util.spring.SpringApplicationContext;

public class WorkQueueEnquiryServiceImpl implements WorkQueueEnquiryService {

	private final Log logger = LogFactory.getLog(getClass());
	

	public String getWorkQueueStatus(String pOrderId, String pBatchId, String pDtlId, String pNature) {
		
		try {
			WorkQueueOrderSearchDAO criteria = SpringApplicationContext.getBean(WorkQueueOrderSearchDAO.class);
			criteria.setSearchKey("sb_id", pOrderId);
			criteria.setSearchKey("wqBatchId", pBatchId);
			criteria.setSearchKey("sbDtlId", pDtlId);
			criteria.setSearchKey("wqNatureId", pNature);
			WorkQueueOrderSearchDAO[] result = (WorkQueueOrderSearchDAO[])criteria.doSelect(null, null);
			
			if (result == null || result.length == 0) {
				return null;
			}
			return result[0].getStatusCd();
		} catch (Exception e) {
			logger.error("Fail in WorkQueueEnquiryService.getWorkQueueStatus()", e);
			throw new AppRuntimeException(e);
		}
	}
}
