package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.OrderDocumentDAO;
import com.bomwebportal.lts.dto.DocWaiveReasonDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.ServiceActionBase;
import com.pccw.util.spring.SpringApplicationContext;

public class LtsOrderDocumentServiceImpl implements LtsOrderDocumentService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private OrderDocumentDAO orderDocumentDao;
	private OrderRetrieveLtsService orderRetrieveLtsService;

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public OrderDocumentDAO getOrderDocumentDao() {
		return orderDocumentDao;
	}

	public void setOrderDocumentDao(OrderDocumentDAO orderDocumentDao) {
		this.orderDocumentDao = orderDocumentDao;
	}
	
	public List<DocWaiveReasonDTO> getWaiveReasonList(String docType) {
		try {
			return this.orderDocumentDao.getWaiveReasonList(docType);
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getWaiveReasonList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public OrderDocDTO getOrderDoc(String docType) {
		try {
			OrderDocDTO orderDoc = this.orderDocumentDao.getOrderDoc(docType);
			if (orderDoc != null) {
				List<DocWaiveReasonDTO> waiveReasonList = this.getWaiveReasonList(docType);
				orderDoc.setWaiveReasonList(waiveReasonList);
			}
			return orderDoc;
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getOrderDoc()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public void saveGeneratedForm(List<AllOrdDocLtsDTO> generatedFormList, String userId) {
		for (AllOrdDocLtsDTO generatedForm : generatedFormList) {
			if (isOrderDocExist(generatedForm.getOrderId(),
					generatedForm.getDocType(), generatedForm.getSeqNum())) {
				continue;
			}
			ServiceActionBase allOrdDocLtsService = SpringApplicationContext.getBean("allOrdDocLtsService");
			allOrdDocLtsService.doSave(generatedForm, userId,
					generatedForm.getOrderId(), generatedForm.getDocType());
		}
	}
	
	
	private boolean isOrderDocExist(String orderId, String docType, String seqNum) {
		
		AllOrdDocLtsDTO[] allOrdDocs = orderRetrieveLtsService.retrieveAllOrdDocs(orderId);
		
		if (ArrayUtils.isEmpty(allOrdDocs)) {
			return false;	
		}
		
		for (AllOrdDocLtsDTO allOrdDoc : allOrdDocs) {
			if (StringUtils.equals(docType, allOrdDoc.getDocType())
					&& StringUtils.equals(seqNum, allOrdDoc.getSeqNum())) {
				return true;
			}
		}
			
		return false;
	}
	
	
	public List<AllOrdDocLtsDTO> getGeneratedFormList(SbOrderDTO sbOrder) {
		List<AllOrdDocLtsDTO> allOrdDocLtsList = new ArrayList<AllOrdDocLtsDTO>();
		
		String coreServiceAFDocType = null;
		String coreServiceAFDocName = null;
		if (LtsBackendConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())
				&& LtsSbHelper.getRenewalDelService(sbOrder.getSrvDtls()) != null) {
			if (LtsBackendConstant.DISTRIBUTE_MODE_ESIGNATURE.equals(sbOrder.getDisMode())) {
				coreServiceAFDocType = LtsBackendConstant.ORDER_DOC_TYPE_DEL_AF_SIGNED;	
			}
			else{
				coreServiceAFDocType = LtsBackendConstant.ORDER_DOC_TYPE_DEL_AF;
			}
			coreServiceAFDocName = LtsBackendConstant.ORDER_DOC_DEL_AF_NAME;
		}
		else {
			if (LtsBackendConstant.DISTRIBUTE_MODE_ESIGNATURE.equals(sbOrder.getDisMode())) {
				coreServiceAFDocType = LtsBackendConstant.ORDER_DOC_TYPE_EYEX_AF_SIGNED;	
			}
			else{
				coreServiceAFDocType = LtsBackendConstant.ORDER_DOC_TYPE_EYEX_AF;
			}
			coreServiceAFDocName = LtsBackendConstant.ORDER_DOC_EYE_AF_NAME;
		}
		
		
		allOrdDocLtsList.add(createAllOrdDocLtsDTO(sbOrder.getOrderId(), coreServiceAFDocType, coreServiceAFDocName));
		
		if (LtsSbHelper.get2ndDelService(sbOrder) != null) {
			allOrdDocLtsList.add(createAllOrdDocLtsDTO(sbOrder.getOrderId(), coreServiceAFDocType, coreServiceAFDocName));	
		}
		return allOrdDocLtsList;
	}
	
	private AllOrdDocLtsDTO createAllOrdDocLtsDTO(String orderId, String docType, String fileName) {
		return createAllOrdDocLtsDTO(orderId, docType, fileName, "1");
	}
	
	private AllOrdDocLtsDTO createAllOrdDocLtsDTO(String orderId, String docType, String fileName, String seqNum) {
		final String FILE_EXT = ".pdf"; 
		
		StringBuilder filePathName = new StringBuilder();
		filePathName.append(orderId).append("_").append(fileName).append(FILE_EXT);
		
		AllOrdDocLtsDTO allOrdDocLts = new AllOrdDocLtsDTO();
		allOrdDocLts.setOrderId(orderId);
		allOrdDocLts.setDocType(docType);
		allOrdDocLts.setFilePathName(filePathName.toString());
		allOrdDocLts.setSeqNum(seqNum);
		return allOrdDocLts;
	}
	
	public void updateOutdatedInd(String orderId, String docType,
			boolean isOutdated) {
		
		
		try {
			this.orderDocumentDao.updateOutdatedInd(orderId, docType, isOutdated);
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.updateOutdatedInd()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public void updateCapturedFaxImageFileName(String pOrderId, String pDocType,
			String pSeq, String pFileName) {		
		try {
			this.orderDocumentDao.updateAllOrdDocFilePath(pOrderId, pDocType, pSeq, pFileName);
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.updateCapturedFaxImageFileName()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<AllOrdDocDTO> getCollectedOrdDocListByOrderId(String orderId){
		try {
			return orderDocumentDao.getCollectedDocListByOrderId(orderId);
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getAllOrdDocListByOrderId()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}
	
	public List<AllOrdDocDTO> getAllOrdDocListByDocTypeOrderId(String pOrderId, String pDocType){
		try {
			return orderDocumentDao.getAllOrdDocListByDocTypeOrderId(pOrderId, pDocType);
			
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getAllOrdDocListByDocTypeOrderId()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}	
	
	public List<AllOrdDocDTO> getCollectedOrdDocListByOrderIdAndDocType(String orderId, String docType){
		try {
			return orderDocumentDao.getCollectedDocListByOrderIdAndDocType(orderId, docType);
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getCollectedOrdDocListByOrderIdAndDocType()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}
	
	public List<AllOrdDocDTO> getLatestCollectedOrdDocListByOrderIdAndDocType(String orderId, String docType){
		String maxSeq = null;
		maxSeq = getMaxCollectedDocSeqByOrderIdAndDocType(orderId, docType);
		try {
			return orderDocumentDao.getLatestCollectedDocListByOrderIdAndDocType(orderId, docType, maxSeq);			
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getLatestCollectedOrdDocListByOrderIdAndDocType()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}
	
	public String getMaxCollectedDocSeqByOrderIdAndDocType(String orderId, String docType){
		try {
			return orderDocumentDao.getMaxCollectedDocSeqByOrderIdAndDocType(orderId, docType);
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getMaxCollectedDocSeqByOrderIdAndDocType()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}
	
	public List<AllOrdDocDTO> getAllOrdDocListByOrderId(String orderId){
		try {
			return orderDocumentDao.getAllOrdDocListByOrderId(orderId);
			
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getAllOrdDocListByOrderId()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}
	
	public void insertAllOrdDocDTO(List<AllOrdDocDTO> allOrdDocDtos){
		try{
			for(AllOrdDocDTO allOrdDocDto : allOrdDocDtos){
				orderDocumentDao.insertAllOrderDocDTO(allOrdDocDto);
			}
		}catch(DAOException de){
			logger.error("Fail in LtsOrderDocumentService.insertAllOrdDocDTO()", de);
			throw new RuntimeException(de.getCustomMessage());
			
		}
	}
	
	public void saveAllAllOrdDocAssgn(List<AllOrdDocAssgnLtsDTO> allOrdDocAssgnList, String user, String pOrderId) {
		if (allOrdDocAssgnList == null || allOrdDocAssgnList.size() == 0) {
			return;
		}
		for(AllOrdDocAssgnLtsDTO allOrdDocAssgn: allOrdDocAssgnList){
			ServiceActionBase allOrdDocAssgnLtsService = SpringApplicationContext.getBean("allOrdDocAssgnLtsService");
			allOrdDocAssgnLtsService.doSave(allOrdDocAssgn, user, pOrderId);
		}
	}
	
	public HashMap<String, String> getFaxSerialMap(String orderId){

		List<AllOrdDocDTO> allOrdDocList = getAllOrdDocListByOrderId(orderId);
		if(allOrdDocList == null){
			return new HashMap<String, String>();
		}
		
		HashMap<String, String> faxSerialMap = new HashMap<String, String>();
		for(AllOrdDocDTO allOrdDoc : allOrdDocList){
			faxSerialMap.put(allOrdDoc.getDocType(), allOrdDoc.getSerial());
		}
		
		return faxSerialMap;
		
	}
	
	
	public List<OrderDocDTO> getOrderDocBySubcItem(String[] subcItemIds) {
		try {
			if (ArrayUtils.isEmpty(subcItemIds)) {
				return null;
			}
//			
//			int[] subcItemIdInts = new int[subcItemIds.length];
//			
//			for(int i=0; i < subcItemIds.length; i++) {
//				subcItemIdInts[i]= Integer.parseInt(subcItemIds[i]);
//		    }
			return orderDocumentDao.getOrderDocBySubcItem(subcItemIds);
			
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getOrderDocBySubcItem()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}
	
}
