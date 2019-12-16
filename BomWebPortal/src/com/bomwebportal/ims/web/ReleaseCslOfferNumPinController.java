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

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.service.MobilePINService;
import com.bomwebportal.service.MobilePINServiceImpl.MobileOffer;
import com.google.gson.Gson;


public class ReleaseCslOfferNumPinController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	Gson gson = new Gson();
	private MobilePINService mobilePINService;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.getSession().setAttribute("MrtReserveException", null); 
		
		logger.info("ReleaseMobilePIN");
		
		
		MobileOffer imsMobileOfferUi = new MobileOffer();
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);	
		
		List<MobileOffer> imsMobileOfferUiList = (List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList");
		
		logger.info("ReleaseMobilePIN*** (List<MobileOffer>) request.getSession().getAttribute"+"(imsMobileOfferUiList):"+ gson.toJson((List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList")));
		//logger.info(gson.toJson("sessionOrder.getCreateBy():"+sessionOrder.getCreateBy()));
		//logger.info(gson.toJson("sessionOrder.getOrderId():"+sessionOrder.getOrderId()));
		
			
		if (imsMobileOfferUiList != null){
			
			for(int i= 0; i<imsMobileOfferUiList.size(); i++){
			
				if(sessionOrder != null && !("").equals(sessionOrder)){
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y");
						logger.error("Exception caught in releaseMobilePIN()", e);
					}
					logger.info("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					
				}else{
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin);
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y");
						logger.error("Exception caught in releaseMobilePIN()", e);
					}
					logger.info("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					
				}
			
				logger.info(gson.toJson(imsMobileOfferUi));
			}
			request.getSession().setAttribute("imsMobileOfferUiList", null);
		}else if(sessionOrder.getSubscribedCslOffers() != null){
			
			SubscribedCslOfferDTO[] csldto = sessionOrder.getSubscribedCslOffers(); 
			
			if(sessionOrder != null && !("").equals(sessionOrder)){
				for(SubscribedCslOfferDTO i:csldto){
					if(i.getVas_parm_a()!= null && !("").equals(i.getVas_parm_a()) && i.getVas_parm_b()!= null && !("").equals(i.getVas_parm_b())){
						try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(i.getVas_parm_a(), i.getVas_parm_b(), sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
						}catch(Exception e){
							request.getSession().setAttribute("MrtReserveException", "Y");
							logger.error("Exception caught in releaseMobilePIN()", e);
						}
						logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);								
					}
				}	
			}else{
				for(SubscribedCslOfferDTO i:csldto){
					if(i.getVas_parm_a()!= null && !("").equals(i.getVas_parm_a()) && i.getVas_parm_b()!= null && !("").equals(i.getVas_parm_b())){
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(i.getVas_parm_a(), i.getVas_parm_b());
						}catch(Exception e){
							request.getSession().setAttribute("MrtReserveException", "Y");
							logger.error("Exception caught in releaseMobilePIN()", e);
						}
						logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);
						}
					}
			}
			
		}
		
		
		return new ModelAndView("ajax_releasemobilepin");
	}

	public MobilePINService getMobilePINService() {
		return mobilePINService;
	}

	public void setMobilePINService(MobilePINService mobilePINService) {
		this.mobilePINService = mobilePINService;
	}
	
	
}
