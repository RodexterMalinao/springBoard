package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MrtReserveDAO;
import com.bomwebportal.mob.ccs.dto.MrtReserveDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTReserveUI;

@Transactional(readOnly=true)
public class MrtReserveServiceImpl implements MrtReserveService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MrtReserveDAO mrtReserveDao;
	
	public void setMrtReserveDao(MrtReserveDAO mrtReserveDao) {
		this.mrtReserveDao = mrtReserveDao;
	}
	public MrtReserveDAO getMrtReserveDao() {
		return mrtReserveDao;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public int insertMrtReserve(MrtReserveDTO dto){
		
		try{
			logger.info("insertMrtReserve() is called in MrtReserveServiceImpl");
			return mrtReserveDao.insertMrtReserve(dto);
		}catch (DAOException de) {
			logger.error("Exception caught in insertMrtReserve()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<MRTReserveUI> getReserveMRTList(String staffId) {
		try {
			logger.info("getReserveMRTList() is called in MrtReserveServiceImpl");
			return mrtReserveDao.getReserveMRTList(staffId);
		} catch (DAOException de) {
			logger.error("Exception caught in getReserveMRTList()", de);
			throw new AppRuntimeException(de);
		}
	}
}
