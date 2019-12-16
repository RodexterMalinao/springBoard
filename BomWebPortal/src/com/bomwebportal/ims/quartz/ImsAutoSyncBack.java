package com.bomwebportal.ims.quartz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.service.ImsAutoSyncBackService;
import com.bomwebportal.util.BomWebPortalConstant;

public class ImsAutoSyncBack {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	private static boolean isProcessing;
	private ImsAutoSyncBackService service;
	
	public void autoSyncBomOcDetail(){
		logger.info("autoSyncBomOcDetail is starting");
		String appEnv = "";
		try{
			appEnv = propertyConfigurer.getMergedProperties().getProperty(
					BomWebPortalConstant.APP_ENV);
			//appEnv = "uat";
			if(!"dev".equals(appEnv)){
				if(!isProcessing){
					isProcessing = true;
					
					processSyncOc();
					
					isProcessing = false;
					logger.info("autoSyncBomOcDetail completed");
				}else{
					logger.info("Last run still executing... job skipped");
				}
			}else{
				logger.info("dev env skipping the auto process...");
			}						
		}catch(Exception e){
			isProcessing = false;
			logger.error("Exception caught in autoSyncBomOcDetail()", e);
			throw new AppRuntimeException(e);
		}				
	}
	
	private void processSyncOc(){
		
		List<OrderImsDTO> orderlist = service.getOcPendingOrder();
		if(orderlist!=null){
			
			OrderImsDTO bomorder;
			List<OrderImsDTO> bomorderlist; 
			
			for(int i=0; i< orderlist.size(); i++){
				
				bomorderlist = service.getBomOcDetail(orderlist.get(i).getOrderId());
				if(bomorderlist != null && bomorderlist.size()>0){
					bomorder = bomorderlist.get(0);
					if(bomorder.getOcId()!=null){
						logger.info("OC id "+bomorder.getOcId()+" generated");
						service.updateOrderOcDetail(bomorder);
						
						//partial work queue
						if("B".equals(orderlist.get(i).getProcessVim())){
							try{
								service.createVimBundleChannelRequest(orderlist.get(i).getOrderId());
							}catch(Exception e){
								e.printStackTrace();
							}
						}												
					}
				}								
			}
			
		}else{
			logger.info("No IMS order waiting for OC detail sync");
		}
		
	}

	public ImsAutoSyncBackService getService() {
		return service;
	}

	public void setService(ImsAutoSyncBackService service) {
		this.service = service;
	}

	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}

}
