package com.bomwebportal.mob.ds.service;

import java.util.List;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ds.dao.MobDsFalloutLogDAO;
import com.bomwebportal.mob.ds.dto.MobDsFalloutLogDTO;

@Transactional(readOnly=true)
public class MobDsFalloutLogServiceImpl implements MobDsFalloutLogService {

	protected final Log logger = LogFactory.getLog(getClass());
	private MobDsFalloutLogDAO mobDsFalloutLogDAO;
	
	
	public MobDsFalloutLogDAO getMobDsFalloutLogDAO() {
		return mobDsFalloutLogDAO;
	}

	public void setMobDsFalloutLogDAO(MobDsFalloutLogDAO mobDsFalloutLogDAO) {
		this.mobDsFalloutLogDAO = mobDsFalloutLogDAO;
	}
	

	public List<MobDsFalloutLogDTO> getFalloutLogDTOALLByCaseNo(String caseNo, String orderId) {
		// TODO Auto-generated method stub
		
		try {
			logger.info("getFalloutLogDTOALLByCaseNo() is called in MobDsFalloutLogServiceImpl");
			return this.mobDsFalloutLogDAO.getFalloutLogDTOALLByCaseNo(caseNo, orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getFalloutLogDTOALLByCaseNo()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertFalloutLogDTO(MobDsFalloutLogDTO dto) {
		// TODO Auto-generated method stub
		try {
			logger.info("insertFalloutLogDTO() is called in MobDsFalloutLogServiceImpl");
			return this.mobDsFalloutLogDAO.insertFalloutLogDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertFalloutLogDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

}
