package com.bomwebportal.lts.service;

import java.util.HashMap;
import java.util.List;

import com.bomwebportal.lts.dto.DocWaiveReasonDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsOrderDocumentService {

	List<DocWaiveReasonDTO> getWaiveReasonList(String docType);
	
	OrderDocDTO getOrderDoc(String docType);
	
	void saveGeneratedForm(List<AllOrdDocLtsDTO> generatedFormList, String userId);
	 
	List<AllOrdDocLtsDTO> getGeneratedFormList(SbOrderDTO sbOrder);
	
	void updateOutdatedInd(String orderId, String docType, boolean isOutdated);
	
	void updateCapturedFaxImageFileName(String pOrderId, String pDocType, String pSeq, String pFileName);
	
	public List<AllOrdDocDTO> getAllOrdDocListByDocTypeOrderId(String pOrderId, String pDocType);	
	
	List<AllOrdDocDTO> getAllOrdDocListByOrderId(String orderId);
	
	List<AllOrdDocDTO> getCollectedOrdDocListByOrderId(String orderId);
	 
	List<AllOrdDocDTO> getCollectedOrdDocListByOrderIdAndDocType(String orderId, String docType); 
	
	List<AllOrdDocDTO> getLatestCollectedOrdDocListByOrderIdAndDocType(String orderId, String docType); 
	
	public void insertAllOrdDocDTO(List<AllOrdDocDTO> allOrdDocDtos);
	
	public void saveAllAllOrdDocAssgn(List<AllOrdDocAssgnLtsDTO> allOrdDocAssgnList, String user, String pOrderId);
	
	public HashMap<String, String> getFaxSerialMap(String orderId);
	
	public List<OrderDocDTO> getOrderDocBySubcItem(String[] subcItemIds);
	
}
