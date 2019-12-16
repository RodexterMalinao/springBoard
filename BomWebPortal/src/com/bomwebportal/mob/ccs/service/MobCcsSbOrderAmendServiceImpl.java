package com.bomwebportal.mob.ccs.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsSbOrderAmendDAO;
import com.bomwebportal.mob.ccs.dto.SbOrderAmendDTO;

@Transactional(readOnly=true)
public class MobCcsSbOrderAmendServiceImpl implements MobCcsSbOrderAmendService {
	protected final Log logger = LogFactory.getLog(getClass());

	private MobCcsSbOrderAmendDAO mobCcsSbOrderAmendDAO;
	
	public MobCcsSbOrderAmendDAO getMobCcsSbOrderAmendDAO() {
		return mobCcsSbOrderAmendDAO;
	}

	public void setMobCcsSbOrderAmendDAO(MobCcsSbOrderAmendDAO mobCcsSbOrderAmendDAO) {
		this.mobCcsSbOrderAmendDAO = mobCcsSbOrderAmendDAO;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertSbOrderAmendDTO(SbOrderAmendDTO dto) {
		try {
			return this.mobCcsSbOrderAmendDAO.insertSbOrderAmendDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertSbOrderAmendDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
}
