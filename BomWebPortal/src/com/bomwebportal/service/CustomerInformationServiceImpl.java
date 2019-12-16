package com.bomwebportal.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.ServiceLineDTO;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.AddrLkupDAO;
import com.bomwebportal.dao.BomCustomerValidationDAO;
import com.bomwebportal.dao.ConstantLkupDAO;
import com.bomwebportal.dao.CustomerOrderHistoryDAO;
import com.bomwebportal.dto.AddressDTO;
import com.bomwebportal.dto.BomCustomerVerificationDTO;
import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.dto.CustomerOrderHistoryDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.PageTrackDAO;

@Transactional(readOnly=true)
public class CustomerInformationServiceImpl implements CustomerInformationService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private BomCustomerValidationDAO bomCustomerValidationDAO;
	private CustomerOrderHistoryDAO customerOrderHistoryDao;
	private AddrLkupDAO addrLkupDAO;
	private PageTrackDAO pageTrackDaoCustSearch;
	
	public BomCustomerValidationDAO getBomCustomerValidationDAO() {
		return bomCustomerValidationDAO;
	}
	public void setBomCustomerValidationDAO(
			BomCustomerValidationDAO bomCustomerValidationDAO) {
		this.bomCustomerValidationDAO = bomCustomerValidationDAO;
	}
	public CustomerOrderHistoryDAO getCustomerOrderHistoryDao() {
		return customerOrderHistoryDao;
	}
	public void setCustomerOrderHistoryDao(
			CustomerOrderHistoryDAO customerOrderHistoryDao) {
		this.customerOrderHistoryDao = customerOrderHistoryDao;
	}
	public AddrLkupDAO getAddrLkupDAO() {
		return addrLkupDAO;
	}
	public void setAddrLkupDAO(AddrLkupDAO addrLkupDAO) {
		this.addrLkupDAO = addrLkupDAO;
	}
	
	public void setPageTrackDaoCustSearch(PageTrackDAO pageTrackDaoCustSearch) {
		this.pageTrackDaoCustSearch = pageTrackDaoCustSearch;
	}
	public PageTrackDAO getPageTrackDaoCustSearch() {
		return pageTrackDaoCustSearch;
	}
	public List<CustomerOrderHistoryDTO> getCustomerOrderHistoryDTOALL(String idDocNum, String serviceNum, String idDocType, String loginId, String serviceType){
		try {
			return customerOrderHistoryDao.getCustomerOrderHistoryDTOALL(idDocNum, serviceNum, idDocType, loginId, serviceType);

		} catch (DAOException de) {
			logger.error("Exception caught in getCustomerProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<CustomerOrderHistoryDTO> getCustomerOrderHistoryDTOALL_retail_only(String idDocNum, String serviceNum, String idDocType, String loginId, String serviceType){
		try {
			return customerOrderHistoryDao.getCustomerOrderHistoryDTOALL_retail_only(idDocNum, serviceNum, idDocType, loginId, serviceType);

		} catch (DAOException de) {
			logger.error("Exception caught in getCustomerProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getStreetCatgDescFromStCatgCd(String stCatgCd){
		try {
			return addrLkupDAO.getStreetCatgDescFromStCatgCd(stCatgCd);

		} catch (DAOException de) {
			logger.error("Exception caught in getStreetCatgDescFromStCatgCd()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getSectDescFromSectCd(String sectCd){
		try {
			return addrLkupDAO.getSectDescFromSectCd(sectCd);

		} catch (DAOException de) {
			logger.error("Exception caught in getSectDescFromSectCd()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getDistDscFromDistrNum(String distrNum){
		try {
			return addrLkupDAO.getDistDscFromDistrNum(distrNum);

		} catch (DAOException de) {
			logger.error("Exception caught in getDistDscFromDistrNum()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getAreaDescFromDistrNum(String distrNum){
		try {
			return addrLkupDAO.getAreaDescFromDistrNum(distrNum);

		} catch (DAOException de) {
			logger.error("Exception caught in getAreaDescFromDistrNum()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getAreaCdFromDistrNum(String distrNum){
		try {
			return addrLkupDAO.getAreaCdFromDistrNum(distrNum);

		} catch (DAOException de) {
			logger.error("Exception caught in getAreaCdFromDistrNum()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public BomCustomerVerificationDTO getBomCustomerVerificationResult(String pDocType, String pDocNum, String pFirstName, String pLastName){
		if(bomCustomerValidationDAO != null){
			return bomCustomerValidationDAO.validateUserWithBom(pDocType, pDocNum, pFirstName, pLastName);
		}
		else{
			return null;
		}
	}
	
	public BomCustomerVerificationDTO getLtsCustomerVerificationResult(String pDocType, String pDocNum, String pFirstName, String pLastName){
		if(bomCustomerValidationDAO != null){
			return bomCustomerValidationDAO.validateUserWithBomLtsProfile(pDocType, pDocNum, pFirstName, pLastName);
		}
		else{
			return null;
		}
	}
	
	public BomCustomerVerificationDTO getImsCustomerVerificationResult(String pDocType, String pDocNum, String pFirstName, String pLastName){
		if(bomCustomerValidationDAO != null){
			return bomCustomerValidationDAO.validateUserWithBomImsProfile(pDocType, pDocNum, pFirstName, pLastName);
		}
		else{
			return null;
		}
	}
	
	public void doPageTrackCustSearch(String ipAddress, String staffId, String pageId, String func, CustomerInformationDTO customerInformationDTO){
		try{
			pageTrackDaoCustSearch.doPageTrackCustSearch(ipAddress, staffId, pageId, func, customerInformationDTO);
		}catch(DAOException de) {
			logger.error("Exception caught in doPageTrack()", de);
			throw new AppRuntimeException(de);
		}
	}
}

