package com.bomwebportal.ims.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.GetCOrderDTO;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.COrderService;
import com.bomwebportal.service.ConstantLkupService;


public class ImsCheckCOrderController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private COrderService cOrderService;
    private ConstantLkupService constantLkupService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("in ImsCheckCOrderController");
		
		ImsInstallationUI cust = new ImsInstallationUI();
		
    	cust = (ImsInstallationUI)request.getSession().getAttribute("imsCustomer");
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		String isNowTV = "N";
		String createCOrderInd = "N";
		String sharedFSA = "";
		
		String disableWqInd = "N";
		
		if("Y".equalsIgnoreCase(sessionOrder.getMobileOfferInd()))
		{
			disableWqInd = constantLkupService.getDisableWQInd();
		}
		
		if(sessionOrder.getProcessVim()!=null && "P".equals(sessionOrder.getProcessVim())){
			 isNowTV="Y";
		}else{
			 isNowTV="N";
		}
		
		GetCOrderDTO cOrderDTO = new GetCOrderDTO();
		
		String custNo = "";
		
		if(cust!=null){
			custNo = cust.getCustNo();
		}else{
			custNo = sessionOrder.getCustomer().getCustNo();
		}
		
		// do not share FSA if pre-install order or mobile offer or csl shop or pre-use order
		if("Y".equalsIgnoreCase(sessionOrder.getPreInstallInd())||("Y".equalsIgnoreCase(sessionOrder.getMobileOfferInd())&&"Y".equalsIgnoreCase(disableWqInd))
				||"Y".equalsIgnoreCase(sessionOrder.getCslShopCustInd())||"Y".equalsIgnoreCase(sessionOrder.getPreUseInd())){
			cOrderDTO.setO_create_c_order("N");
			cOrderDTO.setO_related_fsa(null);
			cOrderDTO.setO_reason(null);
		}else{
			cOrderDTO = cOrderService.getCOrder("Y", isNowTV, sessionOrder.getInstallAddress().getAddrInventory().getTechnology(), custNo, sessionOrder.getInstallAddress().getSerbdyno(), sessionOrder.getInstallAddress().getUnitNo(), sessionOrder.getInstallAddress().getFloorNo());
		}
		
		sessionOrder.setcOrderDTO(cOrderDTO);
		sessionOrder.setRide_on_FSA_Ind(cOrderDTO.getO_create_c_order());
		sessionOrder.setRelated_FSA(cOrderDTO.getO_related_fsa());
		sessionOrder.setRide_on_FSA_reason_Cd(cOrderDTO.getO_reason());
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, sessionOrder);	
		
		createCOrderInd = cOrderDTO.getO_create_c_order();
		String multiFSAInd = "N";
		if ("Y".equalsIgnoreCase(createCOrderInd)){
			sharedFSA = cOrderDTO.getO_related_fsa();
			String nAcctNum = cOrderService.getNAcctByFSA(sharedFSA);
			if(nAcctNum!=null){
				multiFSAInd = cOrderService.checkMultipleFSAUnderAcct(nAcctNum);
			}
		}
		
		JSONArray jsonArray = new JSONArray();

		
		logger.info("isNowTV: "+isNowTV);
		logger.info("technology: "+sessionOrder.getInstallAddress().getAddrInventory().getTechnology());
		logger.info("custno: "+sessionOrder.getCustomer().getCustNo());
		logger.info("serbdyno: "+sessionOrder.getInstallAddress().getSerbdyno());
		logger.info("unitno: "+sessionOrder.getInstallAddress().getUnitNo());
		logger.info("floorno: "+sessionOrder.getInstallAddress().getFloorNo());
		
		jsonArray.add(
				
				"{\"createCOrderInd\":\"" +createCOrderInd + 
				"\",\"relatedFSA\":\"" + sharedFSA +
				"\",\"multiFSAInd\":\"" + multiFSAInd +
				"\"}");
		
		logger.info(jsonArray);
		
		return new ModelAndView("ajax_imscheckcorder", "jsonArray", jsonArray);
				
		
	}
	
	public void setcOrderService(COrderService cOrderService) {
		this.cOrderService = cOrderService;
	}

	public COrderService getcOrderService() {
		return cOrderService;
	}

	public void setConstantLkupService(ConstantLkupService constantLkupService) {
		this.constantLkupService = constantLkupService;
	}

	public ConstantLkupService getConstantLkupService() {
		return constantLkupService;
	}

}

