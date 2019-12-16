package com.pccw.springboard.svc.server.dao.ims;

import java.util.List;

import com.pccw.springboard.svc.server.dao.DaoException;
import com.pccw.springboard.svc.server.dto.OrderHistoryDTO;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.service.WorkQueueService;

public class ImsSbDaoImpl implements ImsSbDao {

	@Override
	public String checkSbPendingOrder(String pParamString) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getImsAlertCnt(String pLoginUserId) throws DaoException {
		try {
			return (long) SpringApplicationContext.getBean(WorkQueueService.class).searchSbIdsWithOutstandingWq("IMS", pLoginUserId).getTotalCount();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<OrderHistoryDTO> getSbOrderHistory(String pIdDocType, String pIdDocNum, String pLoginIdPrefix, String pDomainType)
			throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
}
