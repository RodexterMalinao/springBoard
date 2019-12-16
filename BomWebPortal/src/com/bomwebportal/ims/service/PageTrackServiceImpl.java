package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.ims.dao.PageTrackDAO;
import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;


@Transactional(readOnly=true)
public class PageTrackServiceImpl implements PageTrackService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private PageTrackDAO pageTrackDAO;	

	public PageTrackDAO getPageTrackDAO() {
		return pageTrackDAO;
	}

	public void setPageTrackDAO(PageTrackDAO pageTrackDAO) {
		this.pageTrackDAO = pageTrackDAO;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public int insertPageTrack(String ipAddress, String staffId, String pageId, String func){
		
		logger.info("insertPageTrack is called");
		
		try{
			return pageTrackDAO.insertPageTrack(ipAddress, staffId, pageId, func);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}
	
	public void doPageTrackCustSearch(String ipAddress, String staffId, String pageId, String func, CustomerInformationDTO customerInformationDTO){
		try{
			pageTrackDAO.doPageTrackCustSearch(ipAddress, staffId, pageId, func, customerInformationDTO);
		}catch(DAOException de) {
			logger.error("Exception caught in doPageTrackCustSearch()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public void doPageTrackRolloutSearch(String ipAddress, String staffId, String pageId, String func, String address){
		try{
			pageTrackDAO.doPageTrackRolloutSearch(ipAddress, staffId, pageId, func, address);
		}catch(DAOException de) {
			logger.error("Exception caught in doPageTrackRolloutSearch()", de);
			throw new AppRuntimeException(de);
		}
	}
	
}
