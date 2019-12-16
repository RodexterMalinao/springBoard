package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ImsOrderDocumentDAO;
import com.bomwebportal.ims.dto.ImsAllOrdDocDTO;
import java.util.List;
//created by Tony
public class ImsOrderDocumentServiceImpl implements ImsOrderDocumentService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsOrderDocumentDAO imsOrderDocumentDao;
	
	public void insertImsAllOrdDocDTO(List<ImsAllOrdDocDTO> imsAllOrdDocDtos){
		try{
			for(ImsAllOrdDocDTO imsAllOrdDocDto : imsAllOrdDocDtos){
				imsOrderDocumentDao.insertImsAllOrderDocDTO(imsAllOrdDocDto);
			}
		}catch(DAOException de){
			logger.error("Fail in ImsOrderDocumentService.insertImsAllOrdDocDTO()", de);
			throw new RuntimeException(de.getCustomMessage());
			
		}
	}
	
	public void insertImsEdfRef(String orderId, String edfRef, String user){
		try{
			imsOrderDocumentDao.insertImsEdfRef(orderId, edfRef, user);
		}catch(DAOException de){
			logger.error("Fail in ImsOrderDocumentService.insertImsEdfRef", de);
			throw new RuntimeException(de.getCustomMessage());
			
		}
		
	}
	
	public boolean checkImsEdfRef(String orderId, String dtlId){
		try{
			return imsOrderDocumentDao.checkImsEdfRef(orderId, dtlId);
		}catch(DAOException de){
			logger.error("Fail in ImsOrderDocumentService.checkImsEdfRef", de);
			throw new RuntimeException(de.getCustomMessage());
			
		}
		
	}
	
	public void updateImsEdfRef(String orderId, String dtlId, String edfRef, String user){
		try{
			imsOrderDocumentDao.updateImsEdfRef(orderId, dtlId, edfRef, user);
		}catch(DAOException de){
			logger.error("Fail in ImsOrderDocumentService.updateImsEdfRef", de);
			throw new RuntimeException(de.getCustomMessage());
			
		}
		
	}
	
	public String getImsAllOrderDocFaxSerial(String orderId, String docType){
		try{
			return imsOrderDocumentDao.getImsAllOrderDocFaxSerial(orderId, docType);
		}catch(DAOException de){ 
			logger.error("Fail in ImsOrderDocumentService.getImsAllOrderDocFaxSerial", de);
			throw new RuntimeException(de.getCustomMessage());
			
		}
		
	}

	public void setImsOrderDocumentDao(ImsOrderDocumentDAO imsOrderDocumentDao) {
		this.imsOrderDocumentDao = imsOrderDocumentDao;
	}

	public ImsOrderDocumentDAO getImsOrderDocumentDao() {
		return imsOrderDocumentDao;
	}

	public void updateWqFaxSerial(String orderId, String serial, String user) {
		try {
			imsOrderDocumentDao.updateWqFaxSerial(orderId, serial, user);
		} catch (DAOException e) {
			logger.error("updateWqFaxSerial error:",e);
		}
		
	}

}
