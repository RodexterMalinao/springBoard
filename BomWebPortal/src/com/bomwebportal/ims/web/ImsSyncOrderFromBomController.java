package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsAutoSyncWQDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.service.ImsAutoSyncBackService;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.google.gson.Gson;

public class ImsSyncOrderFromBomController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private static boolean isProcessing;
	private Gson gson = new Gson();
	
	private ImsAutoSyncBackService service;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.debug("ImsSyncOrderFromBom request recieved");
		
		JSONArray jsonArray = new JSONArray();		
		
		if(!isProcessing){			
			try{
				isProcessing = true;
				processSyncOc();	
				processSyncCCWQ();			
				logger.info("ImsSyncOrderFromBom request completed");				
				jsonArray.add(
						"{\"return\":\"" + "success" +				
						"\"}");				
			}catch(Exception e){
				throw new ServletException(e.getMessage());				
			}finally{
				isProcessing = false;
			}			
		}else{
			jsonArray.add(
					"{\"return\":\"" + "previous request is processing" +				
					"\"}");
		}
				
		return new ModelAndView("ajax_view", "jsonArray", jsonArray);
		
	}
	
	//steven added 20131202 start
	private void processSyncCCWQ(){
		List<ImsAutoSyncWQDTO> errorDtoList = new ArrayList<ImsAutoSyncWQDTO>();
		List<String> fsSuccessOrderIdListForLog = new ArrayList<String>();
		logger.debug("processSyncCCWQ is called");
		List<ImsAutoSyncWQDTO> pendingSbAutoWqList = imsOrderAmendService.getPendingFromSpringBoard();
		List<String> wqNatureIds = imsOrderAmendService.getWqNatureWhichNeedSyncBackFromBom();
		if(pendingSbAutoWqList!=null){
			for(ImsAutoSyncWQDTO sbWq:pendingSbAutoWqList){
				Boolean isAuto = false;
				for(String wqNatureId: wqNatureIds){
					if(sbWq.getWqNatureID().equals(wqNatureId)){
						isAuto=true;
					}
				}//for loop wqNatureIds	
				if(isAuto){
					//auto flow start
					ImsAutoSyncWQDTO pendingBomDto = imsOrderAmendService.getPendingFromBOM(sbWq);
					if(pendingBomDto!=null){
						logger.debug("processing sbid:"+sbWq.getSbid()+"\tWqWpAssgnId:"+sbWq.getWqWpAssgnId());
						if(imsOrderAmendService.updateBomwebAmendCategory(sbWq.getSbid(), sbWq.getWqNatureID())){
							if(imsOrderAmendService.setWQStatusOutdated(sbWq.getWqWpAssgnId())){
								if(imsOrderAmendService.insertNewWQStatus(sbWq.getWqWpAssgnId())){
									logger.debug("processSyncCCWQ Done sbid:"+sbWq.getSbid()+"\tWqWpAssgnId:"+sbWq.getWqWpAssgnId());
								}
							}
						}
					}else{
						errorDtoList.add(sbWq);
					}
					//auto flow end
				}else{						
					if(imsOrderAmendService.updateBomwebAmendCategoryFS(sbWq.getSbid())){//f&s flow start
						fsSuccessOrderIdListForLog.add(sbWq.getSbid());
					}//f&s flow end						
				}
			}//for loop pendingSbAutoWqList
		}else{
			logger.info("No pending WQ sync");
		}
		logger.debug("errorDtoList:"+new Gson().toJson(errorDtoList));
		logger.info("fsSuccessOrderIdListForLog: " + gson.toJson(fsSuccessOrderIdListForLog));
	}
	//steven added 20131202 end
	
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
	
	//steven added 20131202 start
	public ImsOrderAmendService imsOrderAmendService;

	public ImsOrderAmendService getImsOrderAmendService() {
		return imsOrderAmendService;
	}

	public void setImsOrderAmendService(ImsOrderAmendService imsOrderAmendService) {
		this.imsOrderAmendService = imsOrderAmendService;
	}
	//steven added 20131202 end

}
