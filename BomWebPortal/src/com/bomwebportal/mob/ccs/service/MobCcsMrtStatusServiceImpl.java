package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsMrtStatusDAO;
import com.bomwebportal.mob.ccs.dto.MrtStatusDTO;

@Transactional(readOnly=true)
public class MobCcsMrtStatusServiceImpl implements MobCcsMrtStatusService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	private MobCcsMrtStatusDAO mobCcsMrtStatusDAO;
	
	public MobCcsMrtStatusDAO getMobCcsMrtStatusDAO() {
		return mobCcsMrtStatusDAO;
	}

	public void setMobCcsMrtStatusDAO(MobCcsMrtStatusDAO mobCcsMrtStatusDAO) {
		this.mobCcsMrtStatusDAO = mobCcsMrtStatusDAO;
	}

	public List<MrtStatusDTO> getMrtStatusDTOByMsisdn(String msisdn) {
		try {
			logger.info("getMrtStatusDTOByMsisdn() is called @ MobCcsMrtStatusServiceImpl");
			return this.mobCcsMrtStatusDAO.getMrtStatusDTOByMsisdn(msisdn);
		} catch (DAOException de) {
			logger.error("Exception caught in getMrtStatusDTOByMsisdn()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<MrtStatusDTO> getMrtStatusDTOByMsisdnAndStatus(String msisdn, MsisdnStatus status) {
		try {
			logger.info("getMrtStatusDTOByMsisdnAndStatus() is called @ MobCcsMrtStatusServiceImpl");
			return this.mobCcsMrtStatusDAO.getMrtStatusDTOByMsisdn(msisdn, status.getStatus());
		} catch (DAOException de) {
			logger.error("Exception caught in getMrtStatusDTOByMsisdnAndStatus()", de);
			throw new AppRuntimeException(de);
		}
	}

    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public int deleteMrtStatusDTOByMsisdnAndStatus(String msisdn, MsisdnStatus status) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("msisdn: " + msisdn + ", status: " + status);
    	}
    	if (StringUtils.isBlank(msisdn)) {
    		return -1;
    	}
    	if (status == null) {
    		return -1;
    	}
		try {
			logger.info("deleteMrtStatusDTOByMsisdnAndStatus() is called @ MobCcsMrtStatusServiceImpl");
			return this.mobCcsMrtStatusDAO.deleteMrtStatusDTOByMsisdnAndStatus(msisdn, status.getStatus());
		} catch (DAOException de) {
			logger.error("Exception caught in deleteMrtStatusDTOByMsisdnAndStatus()", de);
			throw new AppRuntimeException(de);
		}
	}

}
