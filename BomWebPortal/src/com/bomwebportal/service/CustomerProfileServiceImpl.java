package com.bomwebportal.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.CustomerProfileDAO;
import com.bomwebportal.dao.HktPremierAddrDAO;
import com.bomwebportal.dao.PublicHousingAddressDAO;
import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.ActualUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MobBillMediaDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.BomAddressDtlDTO;
import com.bomwebportal.mob.dao.bom.BomAddressDtlDAO;
import com.bomwebportal.mob.dao.bom.BomOrderDAO;

@Transactional(readOnly=true)
public class CustomerProfileServiceImpl implements CustomerProfileService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustomerProfileDAO customerProfileDao;
	private PublicHousingAddressDAO publicHousingAddressDao;
	private HktPremierAddrDAO hktPremierAddrDAO;
	private BomAddressDtlDAO bomAddressDtlDAO;
	private BomOrderDAO bomOrderDAO;
	public CustomerProfileDAO getCustomerProfileDao() {
		return customerProfileDao;
	}

	public void setCustomerProfileDao(CustomerProfileDAO customerProfileDao) {
		this.customerProfileDao = customerProfileDao;
	}

	public PublicHousingAddressDAO getPublicHousingAddressDao() {
	    return publicHousingAddressDao;
	}

	public void setPublicHousingAddressDao(
		PublicHousingAddressDAO publicHousingAddressDao) {
	    this.publicHousingAddressDao = publicHousingAddressDao;
	}
	
	public HktPremierAddrDAO getHktPremierAddrDAO() {
		return hktPremierAddrDAO;
	}

	public void setHktPremierAddrDAO(HktPremierAddrDAO hktPremierAddrDAO) {
		this.hktPremierAddrDAO = hktPremierAddrDAO;
	}

	public BomAddressDtlDAO getBomAddressDtlDAO() {
		return bomAddressDtlDAO;
	}

	public void setBomAddressDtlDAO(BomAddressDtlDAO bomAddressDtlDAO) {
		this.bomAddressDtlDAO = bomAddressDtlDAO;
	}
	

	public BomOrderDAO getBomOrderDAO() {
		return bomOrderDAO;
	}

	public void setBomOrderDAO(BomOrderDAO bomOrderDAO) {
		this.bomOrderDAO = bomOrderDAO;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public void insertBomCustomerProfile(CustomerProfileDTO dto){
		
		try{
			logger.info("insertOrderPayment() is called in CustomerProfileServiceImpl");
			customerProfileDao.insertBomOrderContact(dto);
			List<String> selectedBillMedia = dto.getSelectedBillMedia();//Paper bill Athena 20130925 (Start)
			if(selectedBillMedia!=null){
				for (int i=0;i<selectedBillMedia.size();i++){
					MobBillMediaDTO mobBillMediaDTO = new MobBillMediaDTO();
					mobBillMediaDTO.setBillMediaCode((selectedBillMedia.get(i)));
					mobBillMediaDTO.setCreateBy("");
					mobBillMediaDTO.setLastUpdBy("");
					mobBillMediaDTO.setOrderId(dto.getOrderId());
					customerProfileDao.insertBomWebBillMedia(mobBillMediaDTO);
				}
			}
			//Paper bill Athena 20130925 (End)
		}catch (DAOException de) {
			logger.error("Exception caught in insertOrderPayment()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public void deleteBomCustomerProfile(String orderId){
		
		try{
			logger.info("deleteBomCustomerProfile() is called in CustomerProfileServiceImpl");
			customerProfileDao.deleteBomOrderContact(orderId);
			customerProfileDao.deleteBomWebBillMedia(orderId); //Paper bill Athena 20130925

		}catch (DAOException de) {
			logger.error("Exception caught in deleteBomCustomerProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	public CustomerProfileDTO getCustomerProfile (String orderId ){
		try {
			CustomerProfileDTO dto = customerProfileDao.getCustomerProfile(orderId);
			if (dto!=null){
				if (!dto.isSameAsCust()){
					dto.setActualUserDTO(customerProfileDao.getActualUser(orderId));
				} else {
					dto.setActualUserDTO(new ActualUserDTO());
				}
			}
			return dto;

		} catch (DAOException de) {
			logger.error("Exception caught in getCustomerProfile()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public CustomerProfileDTO getCustomerProfileAll(String orderId){
		try {
			CustomerProfileDTO dto = customerProfileDao.getCustomerProfileAll(orderId);
			if (StringUtils.isNotEmpty(dto.getSameAsCustInd()) && !dto.isSameAsCust()){
				dto.setActualUserDTO(customerProfileDao.getActualUser(orderId));
			} else {
				dto.setActualUserDTO(new ActualUserDTO());
			}
			return dto;

		} catch (DAOException de) {
			logger.error("Exception caught in getCustomerProfileAll()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public CustomerProfileDTO getCurrentCustomerProfile (String orderId ){
		try {
			CustomerProfileDTO dto = customerProfileDao.getCurrentCustomerProfile(orderId);
			if (dto!=null){
				if (!dto.isSameAsCust()){
					dto.setActualUserDTO(customerProfileDao.getActualUser(orderId));
				} else {
					dto.setActualUserDTO(new ActualUserDTO());
				}
			}
			return dto;

		} catch (DAOException de) {
			logger.error("Exception caught in getCustomerProfile()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public boolean isPublicHousing(String serviceBoundaryNum){
	    try {
	    	return publicHousingAddressDao.isPublicHousing(serviceBoundaryNum);
	    } catch (DAOException de) {
        	logger.error(de);
        	throw new AppRuntimeException(de);
	    }
	}

	public boolean isHktPremierCustomer(CustomerProfileDTO customerProfileDto) {
		if (logger.isDebugEnabled()) {
			logger.debug("isHktPremierCustomer() is called in HktPremierServiceImpl");
		}
		return false;
	}

	public boolean isHktPremierAddr(String serviceBoundaryNum) {
		if (logger.isDebugEnabled()) {
			logger.debug("isHktPremierAddress() is called in HktPremierServiceImpl");
			logger.debug("serviceBoundaryNum: " + serviceBoundaryNum);
		}
		
		if (StringUtils.isBlank(serviceBoundaryNum)) {
			return false;
		}
		try {
			String result = this.hktPremierAddrDAO.checkPremierAddr(StringUtils.trim(serviceBoundaryNum));
			if (logger.isDebugEnabled()) {
				logger.debug("result: " + result);
			}
			for (String value : new String[] { "PM", "RM" }) {
				if (value.equals(result)) {
					return true;
				}
			}
			return false;
		} catch (DAOException de) {
			logger.error("DAOException caught in isHktPremierAddr()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public CustomerProfileDTO getCosCustomerProfile (String orderId ){
		try {
			return customerProfileDao.getCosCustomerProfile(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getCosCustomerProfile()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public BomAddressDtlDTO getBomAddrDtl(String idDocType, String idDocNum) {
		try {
			return bomAddressDtlDAO.getBomAddrDtl(idDocType, idDocNum);

		} catch (DAOException de) {
			logger.error("Exception caught in getBomAddrDtl()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean isStudentPlan(String orderId ){
		try {
			return customerProfileDao.isStudentPlan(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in isStudentPlan()", de);
			throw new AppRuntimeException(de);
		}

	}
	

	public String getCareCashStartDate(String codeType ){
		try {
			return customerProfileDao.getCareCashStartDate(codeType);

		} catch (DAOException de) {
			logger.error("Exception caught in getCareCashStartDate()", de);
			throw new AppRuntimeException(de);
		}

	}
	

	
	public AccountDTO getAccountInfo(String srvNum, String idDocType,
			String idDocNum, String brand) {
		try {
			logger.debug(srvNum);
			logger.debug(idDocType);
			logger.debug(idDocNum);
			logger.debug(brand);
			return bomOrderDAO.getAccountInfo(srvNum, idDocType, idDocNum, brand);

		} catch (DAOException de) {
			logger.error("Exception caught in getAccountInfo()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AccountDTO> getBarredFraud(String idDocType,String idDocNum){
		try {
			return bomOrderDAO.getBarredFraud(idDocType, idDocNum);

		} catch (DAOException de) {
			logger.error("Exception caught in getBarredFraud()", de);
			throw new AppRuntimeException(de);
		}
		
	}

}

