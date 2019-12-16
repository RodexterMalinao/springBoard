package com.bomwebportal.ims.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.GetSourceCodeDAO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.RemarkDTO;
import com.bomwebportal.ims.dto.ui.ImsPaymentUI;

public class GetSourceCodeServiceImpl implements GetSourceCodeService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private GetSourceCodeDAO dao;


	public List<Map<String, String>> getSourceCode() throws DAOException{
		logger.info("GetSourceCodeServiceImpl old called");		
		try{
			return dao.getSourceCode();
		}
		catch (Exception e) {
			logger.error("Exception caught in getSourceCode()", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}

	public List<Map<String, String>> getSourceCode(String channel) throws DAOException{
		// TODO Auto-generated method stub
		logger.info("GetSourceCodeServiceImpl called");
//		List<String> list = new ArrayList<String>();
//		
//		// Hardcode data
//		if ( "CSS".equals(appMethod) )
//		{
//			list.add("A");
//			list.add("B");
//			list.add("C");
//		}
//		else if ( "CFO".equals(appMethod) )
//		{
//			list.add("1");
//			list.add("2");
//			list.add("3");
//		}
//		else if ( "HPM".equals(appMethod) )
//		{
//			list.add("I");
//			list.add("II");
//			list.add("III");
//		}
//
//		
//		return list;
		
		try{
			return dao.getSourceCode(channel);
		}
		catch (Exception e) {
			logger.error("Exception caught in getSourceCode()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		
	}

	public void setDao(GetSourceCodeDAO dao) {
		this.dao = dao;
	}

	public GetSourceCodeDAO getDao() {
		return dao;
	}
	
	
	
	
	//kinman20140428	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getDeflaultAppMethod(String deflaultSourceCode){
		String deflaultAppMethod = null;
		
		try{
			deflaultAppMethod = dao.getDeflaultAppMethod(deflaultSourceCode);
			
			return deflaultAppMethod;
		}catch (Exception e){
			logger.error("Exception caught in getDeflaultAppMethod()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	//kinman20140530	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getDeflaultAppMethodRetry(String deflaultSourceCode){
		String deflaultAppMethod = null;
		
		try{
			deflaultAppMethod = dao.getDeflaultAppMethodRetry(deflaultSourceCode);
			
			return deflaultAppMethod;
		}catch (Exception e){
			logger.error("Exception caught in getDeflaultAppMethodRetry()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<RemarkDTO> getL2JobCode() {
		List<RemarkDTO> cList = new ArrayList<RemarkDTO>();
		try{	
			cList = dao.getL2JobCode();
			return cList;
		} catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}
	
	public void getDirectSalesAppMethod(ImsPaymentUI payment, String srcCd) {
		OrderImsDTO o = null;
		try {
			dao.getDirectSalesAppMethod(payment, srcCd);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
    
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getRetailAppMethod(String SourceCode) {
			String retailAppMethod = null;
		try{
			retailAppMethod = dao.getRetailAppMethod(SourceCode);
			
			return retailAppMethod;
		}catch (Exception e){
			logger.error("Exception caught in getRetailAppMethod()", e);
			throw new AppRuntimeException(e);
		}
	}

}
