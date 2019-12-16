package com.bomwebportal.lts.service.bom;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.ServiceProfileInventoryDTO;
import com.bomwebportal.lts.util.LtsProfileConstant;
import com.bomwebportal.lts.wsClientLts.BomWsClient;

public class ServiceProfileLtsDrgServiceImpl extends ServiceProfileLtsServiceImpl implements ServiceProfileLtsDrgService {

	private BomWsClient bomWsClient = null;
	
	
	public ServiceDetailProfileLtsDTO getServiceProfile(String pSrvNum, String pSrvType, String pUser) {

		String reformattedSrvNum = null;
		
		if (StringUtils.equalsIgnoreCase(pSrvType, LtsProfileConstant.SERVICE_TYPE_TEL)) {
			reformattedSrvNum = StringUtils.leftPad(pSrvNum, 12, '0');
		}
		try {
			ServiceDetailProfileLtsDTO srvProfile = this.serviceProfileLtsDao.getServiceProfile(reformattedSrvNum, pSrvType);
			
			if (srvProfile == null ) {
				return null;
			}
			this.getServiceInventoryProfile(srvProfile);
			this.getInventoryStatusDesc(srvProfile);
			srvProfile.setCcSrvMemNum(this.getServiceMemNum(srvProfile.getCcSrvId(), srvProfile.getSrvNum()));
			srvProfile.setSrvGrp(this.serviceProfileLtsDao.getEyeGrp(srvProfile.getCcSrvId()));
			
			if (StringUtils.isNotEmpty(srvProfile.getDuplexNum())) {
				srvProfile.setDuplexCcSrvMemNum(this.getServiceMemNum(srvProfile.getCcSrvId(), srvProfile.getDuplexNum()));
			}
			PendingOrdStatusDetailDTO status = this.getPendingOrder(srvProfile.getSrvNum(), pSrvType);
			
			if (status != null) {
				srvProfile.setPendingOcid(status.getOcid());
				srvProfile.setPendingOcSrd(status.getSrd());
				srvProfile.setPendingOrdType(status.getOrderType());
			}
			return srvProfile;
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getServiceProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public ServiceProfileInventoryDTO getServiceInventory(String pSrvNum, String pSrvType) {
		return this.bomWsClient.getServiceInvenotry(pSrvNum, pSrvType);
	}
	
	private void getServiceInventoryProfile(ServiceDetailProfileLtsDTO pSrvDtl) {
		
		ServiceProfileInventoryDTO srvInventory = this.bomWsClient.getServiceInvenotry(pSrvDtl.getSrvNum(), pSrvDtl.getSrvType());
		pSrvDtl.setTwoNBuildInd(StringUtils.equals("ONP", StringUtils.substring(srvInventory.getDnExchangeId(), 3)) && !StringUtils.equals("G", srvInventory.getNetworkInd()));
		pSrvDtl.setInventStatus(srvInventory.getInventStatus());
		pSrvDtl.setDnExchFrozen(srvInventory.isFrozenExchInd());
		pSrvDtl.setSharedBsn(srvInventory.isSharedBsn());
		
		if (StringUtils.isNotEmpty(pSrvDtl.getDuplexNum())) {
			srvInventory = this.bomWsClient.getServiceInvenotry(pSrvDtl.getDuplexNum(), pSrvDtl.getSrvType());
			pSrvDtl.setDuplexTwoNBuildInd(StringUtils.equals("ONP", StringUtils.substring(srvInventory.getDnExchangeId(), 3)) && !StringUtils.equals("G", srvInventory.getNetworkInd()));
			pSrvDtl.setDuplexDnExchFrozen(srvInventory.isFrozenExchInd());
		}
	}
	
	private String getInventoryStatusDesc(ServiceDetailProfileLtsDTO pSrvProfile) {
		
		if (StringUtils.isEmpty(pSrvProfile.getInventStatus())) {
			return null;
		}
		pSrvProfile.setInventStsDesc((String)this.inventoryStatusCodeLkupCacheService.get(pSrvProfile.getInventStatus()));
		return pSrvProfile.getInventStsDesc();
	}
	
	private String getServiceMemNum(String pCcSrvId, String pSrvNum) {
		
		try {
			return this.serviceProfileLtsDao.getServiceMemNum(pCcSrvId, pSrvNum);
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getServiceMemNum()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public BomWsClient getBomWsClient() {
		return bomWsClient;
	}

	public void setBomWsClient(BomWsClient bomWsClient) {
		this.bomWsClient = bomWsClient;
	}
}
