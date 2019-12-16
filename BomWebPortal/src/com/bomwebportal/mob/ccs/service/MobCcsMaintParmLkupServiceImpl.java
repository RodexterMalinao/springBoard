package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsMaintParmLkupDAO;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;

@Transactional(readOnly=true)
public class MobCcsMaintParmLkupServiceImpl implements MobCcsMaintParmLkupService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	private MobCcsMaintParmLkupDAO mobCcsMaintParmLkupDAO;

	public MobCcsMaintParmLkupDAO getMobCcsMaintParmLkupDAO() {
		return mobCcsMaintParmLkupDAO;
	}

	public void setMobCcsMaintParmLkupDAO(
			MobCcsMaintParmLkupDAO mobCcsMaintParmLkupDAO) {
		this.mobCcsMaintParmLkupDAO = mobCcsMaintParmLkupDAO;
	}

	public List<MaintParmLkupDTO> getMaintParmLkupDTO(String channelCd, String lob, String functionCd, String parmType) {
		try {
			logger.info("getMaintParmLkupDTO() is called @ MobCcsMaintParmLkupServiceImpl");
			return this.mobCcsMaintParmLkupDAO.getMaintParmLkupDTO(channelCd, lob, functionCd, parmType);
		} catch (DAOException de) {
			logger.error("Exception caught in getMaintParmLkupDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

}
