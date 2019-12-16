package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;

public interface OfferActionService {

	public abstract void getLtsOfferActions(ServiceDetailProfileLtsDTO pService);

	public abstract void getImsOfferActions(FsaServiceDetailOutputDTO pFsa, String pExistEyeType);

	public abstract void getLtsEquipmentAction(ServiceDetailProfileLtsDTO pService);
	
	public abstract void getLtsTvAction(ServiceDetailProfileLtsDTO pService);
	
	public abstract void getLtsChannelAction(ServiceDetailProfileLtsDTO pService);
	
	public abstract void getLtsRestrictedOfferAction(ServiceDetailProfileLtsDTO pService);
}