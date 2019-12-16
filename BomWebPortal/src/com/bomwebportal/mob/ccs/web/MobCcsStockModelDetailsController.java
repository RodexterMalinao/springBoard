package com.bomwebportal.mob.ccs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockModelDetailsDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.StockModelDetailsService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;

public class MobCcsStockModelDetailsController extends SimpleFormController {
	
	private StockModelDetailsService stockModelDetailsService;
	private CodeLkupService codeLkupService;
	
	public void setStockModelDetailsService(StockModelDetailsService stockModelDetailsService) {
		this.stockModelDetailsService = stockModelDetailsService;
	}
	public StockModelDetailsService getStockModelDetailsService() {
		return stockModelDetailsService;
	}
	
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		logger.info("MobCcsStockModelDetailsController@formBackingObject called");
		
		StockModelDetailsDTO sessionStockModelDetails = (StockModelDetailsDTO) MobCcsSessionUtil
				.getSession(request, "stockModelDetails");//get session from hashMap session
		
		if (sessionStockModelDetails == null) {
			
			logger.info("MobCcsStockModelController@formBackingObject called , sessionStockModel is null");
			sessionStockModelDetails = new StockModelDetailsDTO();
			
		} else if ("SEARCH".equalsIgnoreCase(sessionStockModelDetails.getActionType())) {
			
			List<StockModelDetailsDTO> tempDto 
				= stockModelDetailsService.getStockCatalogListByItemCode(
						sessionStockModelDetails.getItemCode());
			
			String itemCode = sessionStockModelDetails.getItemCode();
			
			if (tempDto != null && tempDto.size() > 0) {
				sessionStockModelDetails = tempDto.get(0);
			} else {
				sessionStockModelDetails = new StockModelDetailsDTO();
				sessionStockModelDetails.setItemCode(itemCode);
			}
			
		} else {
			
			if (sessionStockModelDetails.getType() != null 
					&& !"NONE".equalsIgnoreCase(sessionStockModelDetails.getType())) {
				
				BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
				sessionStockModelDetails.setUsername(user.getUsername());
				sessionStockModelDetails.setModel(sessionStockModelDetails.getModel().trim().toUpperCase());
				
				List<StockModelDetailsDTO> checkDto 
				= stockModelDetailsService.getStockCatalogListByItemCode(sessionStockModelDetails.getItemCode().trim());

				int isSuccess = -1;
				
				if (checkDto != null && checkDto.size() > 0) {
					isSuccess = stockModelDetailsService.updateModelDetails(sessionStockModelDetails);
				} else {
					isSuccess = stockModelDetailsService.insertModelDetails(sessionStockModelDetails);
				}
				
				if (isSuccess == 1) {
					request.setAttribute("actionType", "SUCCESS");
					sessionStockModelDetails.setActionType("SUCCESS");
					logger.debug("set action type to success");
				} else {
					request.setAttribute("actionType", "UNSUCCESS");
					sessionStockModelDetails.setActionType("UNSUCCESS");
					logger.debug("set action type to unsuccess");
				}
				
				MobCcsSessionUtil.setSession(request, "stockModelDetails", sessionStockModelDetails);
			}
			
		}
		
		if (!("01".equals(sessionStockModelDetails.getType()) || "04".equals(sessionStockModelDetails.getType()))) {
			sessionStockModelDetails.setSimType("NONE");
		}

		return sessionStockModelDetails;

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobCcsStockModelDetailsController onSubmit called");
		
		StockModelDetailsDTO dto = (StockModelDetailsDTO) command;
		MobCcsSessionUtil.setSession(request, "stockModelDetails", dto);// save session to hashMap session
		
		if ("SEARCH".equalsIgnoreCase(dto.getActionType()) || 
				"INSERT".equalsIgnoreCase(dto.getActionType())) {
			return new ModelAndView(new RedirectView("mobccsstockmodeldetails.html"));
		} else {
			return new ModelAndView(new RedirectView("mobccsstock.html"));
		}

	}

	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsStockModelDetailsController ReferenceData called");
		
		StockModelDetailsDTO dto 
			= (StockModelDetailsDTO) MobCcsSessionUtil.getSession(request, "stockModelDetails");

		Map<String, Object> referenceData = new HashMap<String, Object>();

		// type
		List<CodeLkupDTO> typeList = codeLkupService.getCodeLkupDTOALL("STOCK_TYPE");	
		referenceData.put("typeList", typeList);
	
		// type
		List<CodeLkupDTO> assignModeList = codeLkupService.getCodeLkupDTOALL("STOCK_MODE");
		referenceData.put("assignModeList", assignModeList);
		
		if (dto != null) {
			referenceData.put("actionType", dto.getActionType());
		}

		referenceData.put("hsExtraFunctionList", this.codeLkupService.getCodeLkupDTOALL("HS_EXTRA_FUNCTION"));
		
		return referenceData;

	}

}
