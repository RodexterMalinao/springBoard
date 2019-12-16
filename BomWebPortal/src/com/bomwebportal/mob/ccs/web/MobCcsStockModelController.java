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

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockModelDTO;
import com.bomwebportal.mob.ccs.dto.StockModelResultDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.StockModelService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;

public class MobCcsStockModelController extends SimpleFormController {
	
	private StockModelService stockModelService;
	private CodeLkupService codeLkupService;

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	
	public void setStockModelService(StockModelService stockModelService) {
		this.stockModelService = stockModelService;
	}
	public StockModelService getStockModelService() {
		return stockModelService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("MobCcsStockModelController@formBackingObject called");
		
		StockModelDTO sessionStockModel = (StockModelDTO) MobCcsSessionUtil
				.getSession(request, "stockModel");//get session from hashMap session
		
		if (sessionStockModel == null) {
			
			logger.info("MobCcsStockModelController@formBackingObject called , sessionStockModel is null");
			sessionStockModel = new StockModelDTO();
			
		} else {
			
			String type = sessionStockModel.getType();
			String typeUsedToSearch = sessionStockModel.getType();
			String model = sessionStockModel.getModel();
			
			List<CodeLkupDTO> stockTypeList = codeLkupService.getCodeLkupDTOALL("STOCK_TYPE");
			
			if (type != null && stockTypeList != null && stockTypeList.size() > 0) {
				for (int i=0; i<stockTypeList.size(); i++) {
					if (type.equalsIgnoreCase(stockTypeList.get(i).getCodeId())) {
						typeUsedToSearch = stockTypeList.get(i).getCodeDesc();
					}
				}
			}
			
			if (!("NONE".equalsIgnoreCase(type)) && type != null) {

				sessionStockModel.setStockModelResult(stockModelService.getStockModelResultDTO(typeUsedToSearch, model));
				sessionStockModel.setActionType(sessionStockModel.getActionType());

				MobCcsSessionUtil.setSession(request, "stockModel", sessionStockModel);
			}
			
		}

		return sessionStockModel;

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobccsstockmodelController onSubmit called");
		
		StockModelDTO dto = (StockModelDTO) command;
		MobCcsSessionUtil.setSession(request, "stockModel", dto);// save session to hashMap session
		
		if ("REFRESH".equalsIgnoreCase(dto.getActionType())) {
			return new ModelAndView(new RedirectView("mobccsstockmodel.html"));
		} else if ("DIRECT".equalsIgnoreCase(dto.getActionType())) {
			return new ModelAndView(new RedirectView("mobccsstockmodeldetails.html"));
		} else {
			return new ModelAndView(new RedirectView("mobccsstock.html"));
		}
		
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		
		logger.info("MobccsstockmodelController ReferenceData called");
		
		StockModelDTO dto 
			= (StockModelDTO) MobCcsSessionUtil.getSession(request, "stockModel");

		Map referenceData = new HashMap<String, List<String>>();

		// type
		List<CodeLkupDTO> typeList = codeLkupService.getCodeLkupDTOALL("STOCK_TYPE");	
		referenceData.put("typeList", typeList);
	
		if (dto != null) {
			referenceData.put("actionType", dto.getActionType());
			
			String type = dto.getType();
			String model = dto.getModel();
			
			List<StockModelResultDTO> resultList = stockModelService.getStockModelResultDTO(type, model);
			referenceData.put("resultList", resultList);
		}

		return referenceData;
	}


	
}
