package com.bomwebportal.lts.service.bom;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.BomOrderStatusSynchDAO;
import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;

public class BomOrderStatusSynchServiceImpl implements BomOrderStatusSynchService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private BomOrderStatusSynchDAO bomOrderStatusSynchDAO;

	public BomOrderStatusSynchDAO getBomOrderStatusSynchDAO() {
		return bomOrderStatusSynchDAO;
	}

	public void setBomOrderStatusSynchDAO(
			BomOrderStatusSynchDAO bomOrderStatusSynchDAO) {
		this.bomOrderStatusSynchDAO = bomOrderStatusSynchDAO;
	}
	
	public OrderStatusSynchDTO[] getBomOrderStatus(String pOrderId,
			String pTypeOfSrv, String pSrvNum, String pCCServiceId,
			String pCCServiceMemNum) {
		try {
			return bomOrderStatusSynchDAO.getStatus(pOrderId, pTypeOfSrv, pSrvNum,pCCServiceId, pCCServiceMemNum, null, null, null);
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e);
		}
	}	
	
	public OrderStatusSynchDTO[] getBomOrderStatus(String pOrderId,
			String pTypeOfSrv, String pSrvNum, String pCCServiceId,
			String pCCServiceMemNum, String pOcId, String pGrpId, String pToProd) {
		try {
			return bomOrderStatusSynchDAO.getStatus(pOrderId, pTypeOfSrv, pSrvNum,pCCServiceId, pCCServiceMemNum, pOcId, pGrpId, pToProd);
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e);
		}
	}
	
	public OrderStatusSynchDTO[] getBomOrderStatusByCcServiceId(String pSbOrderId, String pSrvType, String pCcSrvId, String pSrvNum, String pSrvMemNum) {
		
		List<OrderStatusSynchDTO> statusList = new ArrayList<OrderStatusSynchDTO>();
		OrderStatusSynchDTO[] status = this.getBomOrderStatus(pSbOrderId, pSrvType, pSrvNum, pCcSrvId, pSrvMemNum);
	
		for (int i=0; status!=null && i<status.length; ++i) {
			if (StringUtils.equals(status[i].getCcServiceId(), pCcSrvId)) {
				statusList.add(status[i]);
			}
		}
		return statusList.toArray(new OrderStatusSynchDTO[statusList.size()]);
	}
}
