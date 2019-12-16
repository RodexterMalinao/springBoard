package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MrtInvertoryOrderDAO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryOrderDTO;

@Transactional(readOnly=true)
public class MrtInventoryOrderServiceImpl implements MrtInventoryOrderService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MrtInvertoryOrderDAO mrtInvertoryOrderDAO;
	
	public MrtInvertoryOrderDAO getMrtInvertoryOrderDAO() {
		return mrtInvertoryOrderDAO;
	}

	public void setMrtInvertoryOrderDAO(MrtInvertoryOrderDAO mrtInvertoryOrderDAO) {
		this.mrtInvertoryOrderDAO = mrtInvertoryOrderDAO;
	}

    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public MrtInventoryOrderDTO getMrtInventoryOrderDTO(String msisdn, Date stockInDate) {
		try {
			logger.info("getMrtInventoryOrderDTO() is called in MrtInventoryOrderServiceImpl");
			return this.mrtInvertoryOrderDAO.getMrtInventoryOrderDTO(msisdn, stockInDate);
	
		} catch (DAOException de) {
			logger.error("Exception caught in getMrtInventoryOrderDTO()", de);
			throw new AppRuntimeException(de);
		}
	}


    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<MrtInventoryOrderDTO> getMrtInventoryOrderDTOALL(String msisdn) {
		try {
			logger.info("getMrtInventoryOrderDTOALL() is called in MrtInventoryOrderServiceImpl");
			return this.mrtInvertoryOrderDAO.getMrtInventoryOrderDTOALL(msisdn);
	
		} catch (DAOException de) {
			logger.error("Exception caught in getMrtInventoryOrderDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

}
