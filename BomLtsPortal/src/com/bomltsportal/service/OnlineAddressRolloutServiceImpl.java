package com.bomltsportal.service;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.dao.AddressDetailDAO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.service.AddressRolloutService;

public class OnlineAddressRolloutServiceImpl implements OnlineAddressRolloutService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private AddressDetailDAO addressDetailDao = null;
	private AddressRolloutService addressRolloutService;


	public boolean isBlacklistListLtsAddress(String pFlat, String pFloor, String pServiceBoundary) {
		
		try {
			return this.addressDetailDao.isBlacklistListLtsAddress(pFlat, pFloor, pServiceBoundary);
		} catch (DAOException de) {
			logger.error("Fail in AddressRolloutService.isBlacklistListLtsAddress()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String[] getConsumerEyeSrvBdryByBuildCoordinate(String pFlat, String pFloor, String pBuildxy, String pSrvType) {
		
		try {
			return this.addressDetailDao.getServiceBoundaryByBuildCoordinate(pFlat, pFloor, pBuildxy, pSrvType);
			
//			if (ArrayUtils.isEmpty(srvBdrys)) {
//				return null;
//			}
//			return this.addressDetailDao.filterSrvBdryForEyeConsumer(srvBdrys);
		} catch (DAOException de) {
			logger.error("Fail in AddressRolloutService.getServiceBoundaryByBuildCoordinate()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public AddressRolloutDTO getAddressRollout(String[] srvBdrys, String flat, String floor, String srvType) {
		if (ArrayUtils.isEmpty(srvBdrys)) {
			return null;
		}
		AddressRolloutDTO addressRollout = null;
		AddressRolloutDTO shortageAddressRollout = null;
		AddressRolloutDTO ponVillaShortageRollout = null;
		for (String srvBdry : srvBdrys) {
			addressRollout = addressRolloutService.getAddressRollout(srvBdry, flat, floor);
			
			if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_DEL, srvType)) {
				return addressRollout;
			}
			
			if (!addressRollout.isEyeCoverage()) {
				continue;
			}
			
			if (isResourceShortage(addressRollout)) {
				shortageAddressRollout = addressRollout;
				continue;
			}
			
			if (ArrayUtils.isEmpty(addressRollout.getTechnology()) && addressRollout.isPonVilla()) {
				ponVillaShortageRollout = addressRollout;
				continue;
			}
			
			return addressRollout;
		}
		return shortageAddressRollout != null ? shortageAddressRollout : ponVillaShortageRollout;
	}

	private boolean isResourceShortage(AddressRolloutDTO addressRollout) {
		return StringUtils.isNotBlank(addressRollout.getPcdResourceShortage());
	}
	
	public AddressDetailDAO getAddressDetailDao() {
		return addressDetailDao;
	}

	public void setAddressDetailDao(AddressDetailDAO addressDetailDao) {
		this.addressDetailDao = addressDetailDao;
	}

	public AddressRolloutService getAddressRolloutService() {
		return addressRolloutService;
	}

	public void setAddressRolloutService(AddressRolloutService addressRolloutService) {
		this.addressRolloutService = addressRolloutService;
	}
}
