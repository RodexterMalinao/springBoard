package com.bomwebportal.lts.service.acq;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.acq.LtsDsOrderDAO;
import com.bomwebportal.lts.dto.acq.BomwebDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;

public class LtsDsOrderServiceImpl implements LtsDsOrderService {
	private LtsDsOrderDAO ltsDsOrderDAO;
	
	public BomwebDsOrderInfoDTO getOrderInfo(String pOrderId) {
		// TODO Auto-generated method stub
		
		
		return null;
	}
	
	

	public LtsDsOrderDAO getLtsDsOrderDAO() {
		return ltsDsOrderDAO;
	}

	public void setLtsDsOrderDAO(LtsDsOrderDAO ltsDsOrderDAO) {
		this.ltsDsOrderDAO = ltsDsOrderDAO;
	}

	public void updateDsInfo(String pOrderId, String qcCallTimePeriod, String waiveCd, String pUserId) {		
		if(StringUtils.isNotBlank(pOrderId)){
			try {
				ltsDsOrderDAO.updateDsInfo(qcCallTimePeriod, waiveCd, pOrderId, pUserId);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void updatePrepayInfo(String pOrderId, PrepayLtsDTO pPrepayInfo, String pUserId) {		
		if(StringUtils.isNotBlank(pOrderId) && pPrepayInfo != null){
			try {
				ltsDsOrderDAO.updatePrepayInfo(pOrderId, pPrepayInfo.getPrepayType()
						, pPrepayInfo.getBillType(), pPrepayInfo.getMercahntCode(), pPrepayInfo.getShopCode(), pPrepayInfo.getTranCode(), pUserId, pPrepayInfo.getPaymentStatus(), pPrepayInfo.getPrepayDate());
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<String> isDuplicatedShopCodeTranCode(String pOrderId, String pShopCode, String pTranCode, String pPrepaySettleDate) {		
		if(StringUtils.isNotBlank(pOrderId)){
			try {
				return ltsDsOrderDAO.isDuplicatedShopCodeTranCode(pOrderId, pShopCode, pTranCode, pPrepaySettleDate);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String canReuseShopCodeTranCode(String pAccNo, String pOrderId, String pCancelOrderDate) {		
		if(StringUtils.isNotBlank(pOrderId)){
			try {
				return ltsDsOrderDAO.canReuseShopCodeTranCode(pAccNo, pOrderId, pCancelOrderDate);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
