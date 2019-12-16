package com.bomwebportal.ims.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.bomwebportal.ims.service.ImsOrderService;

public class ImsGetPendingOrderController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsOrderService imsOrderService;
	
	private static boolean isProcessing;
	
	private static final int MAX_INPUT = 60*24; // One day
	private static final int MIN_INPUT = 30; // 30 minutes

	public ImsOrderService getImsOrderService() {
		return imsOrderService;
	}

	public void setImsOrderService(ImsOrderService imsOrderService) {
		this.imsOrderService = imsOrderService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		logger.info("ImsGetPendingOrder request recieved");
		
		JSONArray jsonArray = new JSONArray();
	
		if(!isProcessing){
			isProcessing = true;
			String input = request.getParameter("withinMinute");
			logger.info("withinMinute input: " + input);
			 
			try {
				int withinMinute = Integer.parseInt(input); 
				
				if (withinMinute > MAX_INPUT || withinMinute < MIN_INPUT)
					jsonArray.add(
							"{\"return\":\"" + "Invalid input. Valid range is from " + MIN_INPUT + " to " +  MAX_INPUT + " minutes" + 
							"\"}");
				else {
					List<String> orderIdList = imsOrderService.getPendingOrderIdList(withinMinute);
					String ConcatOrderId = StringUtils.join(orderIdList, ",");
					jsonArray.add(
								"{\"return\":\"" + ConcatOrderId +				
								"\"}");
				}
				
		    } catch (NumberFormatException ex) {
		    	
	        	jsonArray.add(
						"{\"return\":\"" + "Invalid input " + input + 
						"\"}");
	        	
	        	
		    } finally{
		    	Thread.sleep(4000);
		    	isProcessing = false;
		    	logger.info("ImsGetPendingOrder request completed");	
		    }
			
		}else{
			jsonArray.add(
					"{\"return\":\"" + "previous request is processing" +				
					"\"}");
		}

		return new ModelAndView("ajax_addrLookup", "jsonArray", jsonArray);
		
	}
	


}
