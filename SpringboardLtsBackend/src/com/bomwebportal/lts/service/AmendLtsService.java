package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDTO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDetailDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;


public interface AmendLtsService {
	
	public abstract String getNextBatchSeq(String orderId) throws DAOException;
	
	public abstract String getNextItemSeq(String orderId, String dtlId, String batchSeq) throws DAOException;
	
	public abstract void updateApprovalStatus(String orderId, String pUser, String status) throws DAOException;

	public void saveAmendmentAuditLog(OrderLtsAmendDTO orderLtsAmendDTO, String pOrderId, String pDtlId, String batchSeq, String pUser);
	
	public void saveAmendmentDetailAuditLog(OrderLtsAmendDetailDTO orderLtsAmendDetailDTO, String pOrderId, String pDtlId, String batchSeq, String itemSeq, String itemSubSeq, String pUser);
	
	public List<OrderLtsAmendDetailDTO> retrieveApprovedAmendDetailAuditLog(String pOrderId, String pDtlId);
	
	public OrderLtsAmendDTO retrieveApprovedAmendAuditLog(String pOrderId, String pDtlId);
	
	public String completeAmendment(String orderId) throws DAOException;
	
}
