package com.bomwebportal.lts.service.acq;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.Service0060DetailLtsDTO;

public interface LtsAcq0060DetailService {
	
	public void save0060DetailService(SbOrderDTO pSbOrder, Service0060DetailLtsDTO service0060DetailLtsDTO, String pUser);
	
	public void terminate0060DetailService(SbOrderDTO pSbOrder, String srvNum, String srvType, String datCd, String pUser);

}
