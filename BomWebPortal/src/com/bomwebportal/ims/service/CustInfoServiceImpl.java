package com.bomwebportal.ims.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.CustInfoDAO;
import com.bomwebportal.ims.dto.CustInfoDTO;

@Transactional(readOnly=true)
public class CustInfoServiceImpl implements CustInfoService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustInfoDAO custDao;	

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<CustInfoDTO> searchCustInfo(String iddocno){
		
		logger.info("searchCustInfo is called");
		
		try{			
			return custDao.getCustInfo(iddocno);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
	}

	public CustInfoDAO getCustDao() {
		return custDao;
	}

	public void setCustDao(CustInfoDAO custDao) {
		this.custDao = custDao;
	}
	
	
}
