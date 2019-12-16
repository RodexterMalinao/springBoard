package com.bomwebportal.lts.service.bom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.AddressDetailLtsDAO;
import com.bomwebportal.lts.dto.AddressDetailDTO;

public class AddressDetailLtsServiceImpl implements AddressDetailLtsService {

	private final Log logger = LogFactory.getLog(getClass());
	
	private AddressDetailLtsDAO addressDetailLtsDao;
	
	
	public boolean isBlacklistAddress(String pSrvBdy, String pFlat, String pFloor) {
		
		try {
			return this.addressDetailLtsDao.isBlacklistAddress(pSrvBdy, pFlat, pFloor);
		} catch (DAOException de) {
			logger.error("Fail in AddressDetailLtsService.isBlacklistAddress()", de);
			throw new AppRuntimeException(de);
		}
	}

	
	public AddressDetailDTO getAddressDetail(String sb) {
		try {
			return this.addressDetailLtsDao.getAddressDetail(sb);
		} catch (DAOException de) {
			logger.error("Fail in AddressDetailLtsService.getAddressDetail()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getAddressBuildXy(String pSrvBdy) {
		try {
			return this.addressDetailLtsDao.getAddressBuildXy(pSrvBdy);
		} catch (DAOException de) {
			logger.error("Fail in AddressDetailLtsService.getAddressBuildXy()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public AddressDetailLtsDAO getAddressDetailLtsDao() {
		return addressDetailLtsDao;
	}

	public void setAddressDetailLtsDao(AddressDetailLtsDAO addressDetailLtsDao) {
		this.addressDetailLtsDao = addressDetailLtsDao;
	}
}
