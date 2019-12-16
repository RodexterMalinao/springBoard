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
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.service.MobilePINService;
import com.bomwebportal.service.MobilePINServiceImpl.MobileOffer;
import com.google.gson.Gson;

public class CheckCslOfferNumPinController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	Gson gson = new Gson();
	private MobilePINService mobilePINService;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);	
		
		MobileOffer imsMobileOfferUi = new MobileOffer();
		List<MobileOffer> imsMobileOfferUiListTmp = new ArrayList<MobileOffer>();
		String number = "";
		String pin = "";
		number = request.getParameter("num");
		pin = request.getParameter("pin");
		logger.info("CheckMobilePIN request of number: "+"["+request.getParameter("num")+"]"+" and pin:"+"["+request.getParameter("pin")+"]");
		
		
		List<MobileOffer> imsMobileOfferUiList = (List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList");
		
		logger.info("BEGIN***** (List<MobileOffer>) request.getSession().getAttribute"+"(imsMobileOfferUiList):"+ gson.toJson((List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList")));
		
		String result = "fail"; 
		
		if (imsMobileOfferUiList != null){
			
			Boolean recordRepeated = false;
			
			for(int i= 0; i<imsMobileOfferUiList.size(); i++){
				
				imsMobileOfferUiListTmp.add(imsMobileOfferUiList.get(i));
				if(number.equals(imsMobileOfferUiList.get(i).mrt) && pin.equals(imsMobileOfferUiList.get(i).pin)){
					recordRepeated = true;
				}	
			}
			
			
			if(recordRepeated){
				result = "success";
			}else{
				if(sessionOrder != null && !("").equals(sessionOrder)){
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.getMobilePIN(number, pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
						result = "exception"; 
						logger.error("Exception caught in getMobilePIN()", e);
					}
				}else{
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.getMobilePIN(number, pin, "NONONLINE");
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
						result = "exception"; 
						logger.error("Exception caught in getMobilePIN()", e);
					}
				}
				
				
				//if status is active
				if(imsMobileOfferUi.status.equals("A") ){
					if(sessionOrder != null && !("").equals(sessionOrder)){
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.reserveMobilePIN(number, pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
						}catch(Exception e){
							request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
							result = "exception"; 
							logger.error("Exception caught in reserveMobilePIN()", e);
						}
					}else{
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.reserveMobilePIN(number, pin);
						}catch(Exception e){
							request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
							result = "exception"; 
							logger.error("Exception caught in reserveMobilePIN()", e);
						}	
					}
						
					if(imsMobileOfferUi.status.equals("R") ){				
						result = "success"; 
						
						//add back new record
						imsMobileOfferUiListTmp.add(imsMobileOfferUi);
						
						request.getSession().setAttribute("imsMobileOfferUiList",imsMobileOfferUiListTmp);
						
					}
				}
			}
			logger.info(gson.toJson(imsMobileOfferUi));
		}else{
			if(sessionOrder != null && !("").equals(sessionOrder)){
				try{
					imsMobileOfferUi = (MobileOffer) mobilePINService.getMobilePIN(number, pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
				}catch(Exception e){
					request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
					result = "exception"; 
					logger.error("Exception caught in getMobilePIN()", e);
				}	
			}else{
				try{
				imsMobileOfferUi = (MobileOffer) mobilePINService.getMobilePIN(number, pin, "NONONLINE");
				}catch(Exception e){
					request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
					result = "exception"; 
					logger.error("Exception caught in getMobilePIN()", e);
				}	
			}
			logger.info(gson.toJson(imsMobileOfferUi));
			//if status is active
			
			if(imsMobileOfferUi.status.equals("A") ){
				if(sessionOrder != null && !("").equals(sessionOrder)){
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.reserveMobilePIN(number, pin);
						
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception
						result = "exception"; 
						logger.error("Exception caught in reserveMobilePIN()", e);
					}
				}else{
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.reserveMobilePIN(number, pin);
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y"); //unknown error found, throw Exception 
						result = "exception"; 
						logger.error("Exception caught in reserveMobilePIN()", e);
					}
				}
			if(imsMobileOfferUi.status.equals("R") ){				
				result = "success"; 
				
				//add new record
				imsMobileOfferUiListTmp.add(imsMobileOfferUi);
				
				request.getSession().setAttribute("imsMobileOfferUiList",imsMobileOfferUiListTmp);
				
			}
			}
			logger.info(gson.toJson(imsMobileOfferUi));
		}
		logger.info("END***** (List<MobileOffer>) request.getSession().getAttribute"+"(imsMobileOfferUiList):"+ gson.toJson((List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList")));
		
			
		JSONArray jsonArray = new JSONArray();
		
		String returntxt = "";
		
		if(imsMobileOfferUi.rtnErrMessage != null){
			 returntxt = imsMobileOfferUi.rtnErrMessage.toString();
		}
	 
		jsonArray.add("{\"message\":\"" + returntxt	
					 	+ "\", \"result\":\"" + result
					 	+ "\"}");

		
		logger.info(jsonArray);
		
		return new ModelAndView("ajax_checkmobilepin", "jsonArray", jsonArray);
	}

	public MobilePINService getMobilePINService() {
		return mobilePINService;
	}

	public void setMobilePINService(MobilePINService mobilePINService) {
		this.mobilePINService = mobilePINService;
	}
	
	
}
