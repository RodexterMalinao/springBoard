package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.dto.CancelOrderDTO;
import com.bomwebportal.ims.service.ImsOrderCancelService;
import com.bomwebportal.ims.service.ReleaseLoginIDService;

public class ImsCancelOrderController implements Controller{	
	protected final Log logger = LogFactory.getLog(getClass());
	private static boolean isProcessing;
	
	private ImsOrderCancelService service;
	private ReleaseLoginIDService reLoginSrv;
	
	public ReleaseLoginIDService getReLoginSrv() {
		return reLoginSrv;
	}

	public void setReLoginSrv(ReleaseLoginIDService reLoginSrv) {
		this.reLoginSrv = reLoginSrv;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("imscancelorder request recieved");
		
		JSONArray jsonArray = new JSONArray();		
		
		if(!isProcessing){			
			try{
				isProcessing = true;				
				//orderService.cancelPendingOrder();
				processAutoCancel();
				ReleaseReserveLoginId();
				logger.info("imscancelorder request completed");
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
	
	private void processAutoCancel(){
		
		List<CancelOrderDTO> orderlist = service.getPendingToCancelOrder();
		if(orderlist!=null){
			for(int i=0; i< orderlist.size(); i++){				
				service.cancelOrder(orderlist.get(i));				
			}
		}
	}

	public ImsOrderCancelService getService() {
		return service;
	}

	public void setService(ImsOrderCancelService service) {
		this.service = service;
	}
	
	private void ReleaseReserveLoginId(){
		logger.info("ReleaseReserveLoginId");
		
		this.reLoginSrv.releaseUselessLoginID();
		
		
	}
	
	

}
