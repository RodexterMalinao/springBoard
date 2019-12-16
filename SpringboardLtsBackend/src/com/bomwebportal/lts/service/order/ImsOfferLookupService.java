package com.bomwebportal.lts.service.order;

import java.util.List;

import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;

public interface ImsOfferLookupService {

	public abstract List<String> getOfferIdListByProductParam(String pParameter, String pValue);
	
	public abstract ImsOfferDetailDTO[] getImsOffersByService(ServiceDetailOtherLtsDTO pSrvDtl, String pDeviceType, String pWgOfferInd);
	
}