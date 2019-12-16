package com.bomwebportal.lts.service.acq;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.Service0060DetailLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.ServiceActionBase;

public class LtsAcq0060DetailServiceImpl implements LtsAcq0060DetailService {	

	private ServiceActionBase service0060DetailLtsService;

    public void save0060DetailService(SbOrderDTO pSbOrder, Service0060DetailLtsDTO service0060DetailLtsDTO, String pUser) {
    	service0060DetailLtsService.doSave(service0060DetailLtsDTO, pUser, pSbOrder.getOrderId());		
	}

	public void terminate0060DetailService(SbOrderDTO pSbOrder,
			String srvNum, String srvType, String datCd, String pUser) {
		Service0060DetailLtsDTO service0060DetailLtsDTO = new Service0060DetailLtsDTO();
		service0060DetailLtsDTO.setSrvNum(srvNum);
		service0060DetailLtsDTO.setTypeOfSrv(srvType);
		service0060DetailLtsDTO.setDatCd(datCd);				
		service0060DetailLtsDTO.setIoInd(LtsConstant.IO_IND_OUT);
		this.save0060DetailService(pSbOrder, service0060DetailLtsDTO, pUser);
	}	
	
	/**
	 * @return the service0060DetailLtsService
	 */
	public ServiceActionBase getService0060DetailLtsService() {
		return service0060DetailLtsService;
	}

	/**
	 * @param service0060DetailLtsService the service0060DetailLtsService to set
	 */
	public void setService0060DetailLtsService(
			ServiceActionBase service0060DetailLtsService) {
		this.service0060DetailLtsService = service0060DetailLtsService;
	}

}
