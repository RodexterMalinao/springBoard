package com.pccw.springboard.svc.server.dao.slv;

import java.util.List;

import com.pccw.springboard.svc.server.dao.DaoException;
import com.pccw.springboard.svc.server.dto.OrderHistoryDTO;
import com.pccw.springboard.svc.server.dto.slv.SlvCustomerDTO;
import com.pccw.springboard.svc.server.dto.slv.SlvServiceDTO;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.service.WorkQueueService;
import com.smartliving.dao.SmartLivingDAO;

public class SlvSbDaoImpl implements SlvSbDao {

	@Override
	public List<OrderHistoryDTO> getSbOrderHistory(String pSlvCustNum)
			throws DaoException {
		try {
			return SpringApplicationContext.getBean(SmartLivingDAO.class).getSlvOrderSummarySvc(pSlvCustNum);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public long getSlvAlertCnt(String pLoginUserId) throws DaoException {
		try {
			return (long) SpringApplicationContext.getBean(WorkQueueService.class).searchSbIdsWithOutstandingWq("SLV", pLoginUserId).getTotalCount();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<SlvCustomerDTO> getSlvCustomer(String pGrpIdDocType, String pGrpIdDocNum, String pBomParentCustNum,
			String pProfileID, String pContactNumber) throws DaoException {
		try {
			return SpringApplicationContext.getBean(SmartLivingDAO.class).getSlvCustomer(pGrpIdDocType, pGrpIdDocNum,
							pBomParentCustNum, pProfileID, pContactNumber);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<SlvServiceDTO> getSlvServiceSummary(String pSlvCustNum)
			throws DaoException {
		try {
			return SpringApplicationContext.getBean(SmartLivingDAO.class).getSlvServiceSummary(pSlvCustNum);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

}