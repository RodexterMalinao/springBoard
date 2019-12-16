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

public class MobCcsStockModelDetailsUpdateController extends SimpleFormController {
	
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
		
		String itemCode = request.getParameter("itemCode");
		
		logger.info("MobCcsStockModelDetailsUpdateController@formBackingObject called");
		
		List<StockModelDetailsDTO> tempDto = stockModelDetailsService.getStockCatalogListByItemCode(itemCode);
		
		if (tempDto != null && tempDto.size() > 0) {
			MobCcsSessionUtil.setSession(request, "stockModelDetailsUpdate", tempDto.get(0));
			request.setAttribute("typeDesc", tempDto.get(0).getType());
		}

		logger.info("MobCcsStockModelDetailsUpdateController@formBackingObject called");
		
		StockModelDetailsDTO sessionStockModelDetailsUpdate = (StockModelDetailsDTO) MobCcsSessionUtil
				.getSession(request, "stockModelDetailsUpdate");//get session from hashMap session
		
		if (sessionStockModelDetailsUpdate == null) {
			
			logger.info("MobCcsStockModelController@formBackingObject called , sessionStockModel is null");
			sessionStockModelDetailsUpdate = new StockModelDetailsDTO();
			
		} else {
			
			if (sessionStockModelDetailsUpdate.getType() != null 
					&& !"NONE".equalsIgnoreCase(sessionStockModelDetailsUpdate.getType())
					&& "UPDATE".equalsIgnoreCase(sessionStockModelDetailsUpdate.getActionType())) {
				
				sessionStockModelDetailsUpdate.setModel(sessionStockModelDetailsUpdate.getModel().toUpperCase());
				BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
				sessionStockModelDetailsUpdate.setUsername(user.getUsername());
				
				int isSuccess = 
				stockModelDetailsService.updateModelDetails(sessionStockModelDetailsUpdate);
				
				if (isSuccess == 1) {
					request.setAttribute("actionType", "SUCCESS");
					sessionStockModelDetailsUpdate.setActionType("SUCCESS");
					logger.debug("set action type to success");
				} else {
					request.setAttribute("actionType", "UNSUCCESS");
					sessionStockModelDetailsUpdate.setActionType("UNSUCCESS");
					logger.debug("set action type to unsuccess");
				}
				
				MobCcsSessionUtil.setSession(request, "stockModelDetailsUpdate", sessionStockModelDetailsUpdate);
			}
			
		}

		return sessionStockModelDetailsUpdate;

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobCcsStockModelDetailsUpdateController onSubmit called");
		
		StockModelDetailsDTO dto = (StockModelDetailsDTO) command;
		MobCcsSessionUtil.setSession(request, "stockModelDetailsUpdate", dto);// save session to hashMap session
		
		if ("UPDATE".equalsIgnoreCase(dto.getActionType())) {
			return new ModelAndView(new RedirectView("mobccsstockmodeldetailsupdate.html"));
		} else {
			return new ModelAndView(new RedirectView("mobccsstockmodeldetailsupdate.html?itemCode=" + dto.getItemCode()));
		}

	}

	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsStockModelDetailsUpdateController ReferenceData called");
		
		StockModelDetailsDTO dto 
			= (StockModelDetailsDTO) MobCcsSessionUtil.getSession(request, "stockModelDetailsUpdate");

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
