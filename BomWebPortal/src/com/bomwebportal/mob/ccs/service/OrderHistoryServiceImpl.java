package com.bomwebportal.mob.ccs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.CustomerProfileDAO;
import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.dao.MobileSimInfoDAO;
import com.bomwebportal.dao.VasDetailDAO;
import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.ExclusiveItemDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderHistoryDAO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsFalloutUI;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsUrgentOrderManagementUI;
import com.bomwebportal.dto.ComponentDTO;
import com.pccw.bom.mob.schemas.ProductDTO;

@Transactional(readOnly = true)
public class OrderHistoryServiceImpl implements OrderHistoryService {

	protected final Log logger = LogFactory.getLog(getClass());

	private MobCcsOrderHistoryDAO mobCcsOrderHistoryDAO;


	public MobCcsOrderHistoryDAO getMobCcsOrderHistoryDAO() {
		return mobCcsOrderHistoryDAO;
	}
	public void setMobCcsOrderHistoryDAO(MobCcsOrderHistoryDAO mobCcsOrderHistoryDAO) {
		this.mobCcsOrderHistoryDAO = mobCcsOrderHistoryDAO;
	}


	public OrderDTO getOrder (String orderId , String seqNo)
	{
		try {
			return mobCcsOrderHistoryDAO.getOrder(orderId, seqNo);

		} catch (DAOException de) {
			logger.error("Exception caught in getOrder()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public CustomerProfileDTO getCustomerProfile (String orderId , String seqNo){
		try {
			return mobCcsOrderHistoryDAO.getCustomerProfile(orderId, seqNo);

		} catch (DAOException de) {
			logger.error("Exception caught in getCustomerProfile()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public MnpDTO getMnp(String orderId, String seqNo) {
		try {
			return mobCcsOrderHistoryDAO.getMnp(orderId,seqNo);

		} catch (DAOException de) {
			logger.error("Exception caught in getMnp()", de);
			throw new AppRuntimeException(de);
		}

	}

	public PaymentDTO getPayment(String orderId, String seqNo) {
		try {
			return mobCcsOrderHistoryDAO.getPayment(orderId,seqNo);

		} catch (DAOException de) {
			logger.error("Exception caught in getPayment()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public MobileSimInfoDTO getSim (String orderId , String seqNo){
		try {
			return mobCcsOrderHistoryDAO.getSim(orderId,seqNo);

		} catch (DAOException de) {
			logger.error("Exception caught in getSim()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public SubscriberDTO getBomWebSub(String orderId, String seqNo) {
		try {
			return mobCcsOrderHistoryDAO.getBomWebSub(orderId,seqNo);

		} catch (DAOException de) {
			logger.error("Exception caught in getBomWebSub()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public AccountDTO getAccount (String orderId , String seqNo)
	{
		try {
			return mobCcsOrderHistoryDAO.getAccount(orderId,seqNo);

		} catch (DAOException de) {
			logger.error("Exception caught in getAccount()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public List<ComponentDTO> getComponentList(String orderId, String seqNo)
	{
		try {
			return mobCcsOrderHistoryDAO.getComponentList(orderId,seqNo);

		} catch (DAOException de) {
			logger.error("Exception caught in getComponentList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
    public ArrayList<MobCcsMrtBaseDTO> getMobCcsMrtDTO(String orderId,String seqNo) {
		try {
		    logger.info("getMobCcsMrtDTO() is called in MobCcsMrtServiceImpl");
		    return mobCcsOrderHistoryDAO.getMobCcsMrtDTO(orderId,seqNo);
	
		} catch (DAOException de) {
		    logger.error("Exception caught in getMobCcsMrtDTO()", de);
		    throw new AppRuntimeException(de);
		}
    }
    
	public List<ProductDTO> getBomProductList(String orderId,String seqNo) {
		List<ProductDTO> productDtoList;
		try {

			logger.info("getBomProductList() is called in OrderServiceImpl");
			productDtoList = mobCcsOrderHistoryDAO.getBomProductList(orderId,seqNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomProductList()", de);
			throw new AppRuntimeException(de);
		}
		return productDtoList;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBomWebSim(MobileSimInfoDTO dto, String seqNo) {
		try {

			logger.info("updateBomWebSim() is called in OrderServiceImpl");
			mobCcsOrderHistoryDAO.updateBomWebSim(dto,seqNo);
		} catch (DAOException de) {
			logger.error("Exception caught in updateBomWebSim()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getMultiIMEI(String orderId, String seqNo) {
		try {
			return mobCcsOrderHistoryDAO.getMultiIMEI(orderId,seqNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getMultiIMEI()", de);
			throw new AppRuntimeException(de);
		}	
	}
	
	public List<ComponentDTO> getPassToBomComponentList(String orderId, String seqNo)
	{
		try {
			return mobCcsOrderHistoryDAO.getPassToBomComponentList(orderId,seqNo);

		} catch (DAOException de) {
			logger.error("Exception caught in getPassToBomComponentList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
}
