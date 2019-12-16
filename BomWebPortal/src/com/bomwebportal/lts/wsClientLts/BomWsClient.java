package com.bomwebportal.lts.wsClientLts;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.srvAccess.ServiceProfileInventoryDTO;

public class BomWsClient {

	private WebServiceTemplate webServiceTemplate;
	private WebServiceTemplate webServiceTemplateSecondary;

	private final Log logger = LogFactory.getLog(getClass());	
		
	public ServiceProfileInventoryDTO getServiceInvenotry(String pSrvNum, String pSrvType) {
				
		ServiceInventoryDTO wsSrvInventory = null;
		
		try {
			wsSrvInventory = this.getServiceInvenotry(this.webServiceTemplate, pSrvNum, pSrvType);
		} catch (Exception ex) {
			logger.error("Error in calling primary WS to Bom: " + ex.getCause());
			
			try {
				wsSrvInventory = this.getServiceInvenotry(this.webServiceTemplateSecondary, pSrvNum, pSrvType);
			} catch (Exception ex1) {
				logger.error("Error in calling secondary WS to Bom: " + ex1.getCause());
				throw new AppRuntimeException(ex1);
			}
		}
		if (wsSrvInventory == null || StringUtils.isEmpty(wsSrvInventory.getDnExchangeId())) {
			return null;
		}
		ServiceProfileInventoryDTO srvIntentory = new ServiceProfileInventoryDTO();
//		BeanUtils.copyProperties(wsSrvInventory, srvIntentory);
		srvIntentory.setDnExchangeId(wsSrvInventory.getDnExchangeId());
		srvIntentory.setFrozenExchInd(wsSrvInventory.isFrozenExchInd().booleanValue());
		srvIntentory.setInventStatus(wsSrvInventory.getInventStatus());
		srvIntentory.setNetworkInd(wsSrvInventory.getNetworkInd());
		srvIntentory.setSharedBsn(wsSrvInventory.isSharedBsn().booleanValue());
		return srvIntentory;
	}
	
	private ServiceInventoryDTO getServiceInvenotry(WebServiceTemplate pWsTemplate, String pSrvNum, String pSrvType) {
		
		GetServiceInventory request = new GetServiceInventory();		
		request.setPSrvNum(pSrvNum);
		request.setPSrvType(pSrvType);
		GetServiceInventoryResponse response = (GetServiceInventoryResponse)pWsTemplate.marshalSendAndReceive(request);
		return response.getReturn();
	}
	
	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}

	public WebServiceTemplate getWebServiceTemplateSecondary() {
		return webServiceTemplateSecondary;
	}

	public void setWebServiceTemplateSecondary(
			WebServiceTemplate webServiceTemplateSecondary) {
		this.webServiceTemplateSecondary = webServiceTemplateSecondary;
	}
}
