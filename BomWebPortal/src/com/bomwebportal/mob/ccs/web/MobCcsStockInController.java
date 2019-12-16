package com.bomwebportal.mob.ccs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockInDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.StockInService;
import com.bomwebportal.mob.ccs.service.StockService;
import com.bomwebportal.mob.ccs.service.StockService.FunctionCd;
import com.bomwebportal.mob.ccs.service.StockService.ParmType;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;

public class MobCcsStockInController extends SimpleFormController {

	private StockService stockService;
	private StockInService stockInService;
	private CodeLkupService codeLkupService;

	public StockService getStockService() {
		return stockService;
	}
	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}
	public StockInService getStockInService() {
		return stockInService;
	}
	public void setStockInService(StockInService stockInService) {
		this.stockInService = stockInService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		logger.info("MobCcsStockInController@formBackingObject called");
		
		StockInDTO sessionStockIn = (StockInDTO) MobCcsSessionUtil.getSession(request, "stockIn");//get session from hashMap session
		
		StockDTO sessionStock = (StockDTO) MobCcsSessionUtil.getSession(request, "stock");
		
		String sessionStockItemCode = sessionStock.getItemCode();
		
		if (sessionStockIn == null) {
			
			logger.info("MobCcsStockInController@formBackingObject called , stockIn is null");

			sessionStockIn = new StockInDTO();
			
			if (StringUtils.isNotBlank(sessionStockItemCode)) {
				List<StockInDTO> temp = stockInService.getStockInDTO(sessionStockItemCode);
				
				if (CollectionUtils.isNotEmpty(temp)) {
					sessionStockIn.setImei(temp.get(0).getImei());
					sessionStockIn.setItemCode(sessionStockItemCode);
					sessionStockIn.setModel(temp.get(0).getModel());
					sessionStockIn.setStatus(temp.get(0).getStatus());
					sessionStockIn.setType(temp.get(0).getType());
					sessionStockIn.setStockPool(temp.get(0).getStockPool());
					MobCcsSessionUtil.setSession(request, "stockIn", sessionStockIn);
				}
			}
		}

		return sessionStockIn;

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobCcsStockInController onSubmit called");
		
		StockInDTO dto = (StockInDTO) command;
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsstockin.html"));
		
		if ("REFRESH".equalsIgnoreCase(dto.getActionType()) || "INSERT".equalsIgnoreCase(dto.getActionType())) {
			
			List<StockCatalogDTO> tempList = stockInService.getTypeNModel(dto.getItemCode().trim());
			
			if (CollectionUtils.isNotEmpty(tempList)) {
				dto.setType(tempList.get(0).getScItemType());
				dto.setModel(tempList.get(0).getScItemDesc());
				dto.setItemCode(tempList.get(0).getScItemCode());
				dto.setMipSimType(tempList.get(0).getMipSimType());
			} else {
				dto.setType("NONE");
				dto.setModel("");
				dto.setItemCode("");
				dto.setStockPool("");
				dto.setMipSimType("");
			}
			
			MobCcsSessionUtil.setSession(request, "stockIn", dto);
		}
		
		if ("INSERT".equalsIgnoreCase(dto.getActionType())) {
			
			StockInDTO temp = (StockInDTO)MobCcsSessionUtil.getSession(request, "stockIn");
			
			if (temp != null 
					&& temp.getItemCode() != null && !"".equalsIgnoreCase(temp.getItemCode().trim())
					&& temp.getLocation() != null && !"NONE".equalsIgnoreCase(temp.getLocation().trim())
					&& temp.getStatus() != null && !"NONE".equalsIgnoreCase(temp.getStatus().trim())
					&& (temp.getImei() != null && !"".equalsIgnoreCase(temp.getImei().trim())
						|| temp.getQuantity() != null && !"".equalsIgnoreCase(temp.getQuantity().trim()))
					&& temp.getBatchRef() != null && !"".equals(temp.getBatchRef().trim())
					&& StringUtils.isNotBlank(temp.getStockPool())) {
				
				String insertItemCode = temp.getItemCode();
				String insertType = temp.getType();
				String insertLocation = temp.getLocation();
				String insertStatus = temp.getStatus();
				String insertImei = temp.getImei();
				String insertRemarks = temp.getRemarks();
				String insertBatchRef = temp.getBatchRef().toUpperCase();
				String insertQuantity = temp.getQuantity();
				
				StockInDTO insertDto = new StockInDTO();
				
				insertDto.setItemCode(insertItemCode);
				dto.setItemCode(insertItemCode);
				
				insertDto.setType(insertType);
				dto.setType(insertType);
				
				insertDto.setLocation(insertLocation);
				dto.setLocation(insertLocation);
				
				insertDto.setStatus(insertStatus);
				dto.setStatus(insertStatus);
				
				insertDto.setImei(insertImei);
				dto.setImei(insertImei);
				
				insertDto.setRemarks(insertRemarks);
				dto.setRemarks(insertRemarks);
				
				insertDto.setBatchRef(insertBatchRef);
				dto.setBatchRef(insertBatchRef);
				
				insertDto.setQuantity(insertQuantity);
				dto.setQuantity(insertQuantity);
				
				insertDto.setStockPool(temp.getStockPool());
				dto.setStockPool(temp.getStockPool());
				
				BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
				
				/*if ("06".equals(insertDto.getType())) {
					String[] result =
							stockInService.insertFreeGiftItem(insertDto);
					
					if (result != null && result.length != 0) {
						String startSerial = result[0];
						String endSerial = result[1];
						String retVal = result[2];
						String errorCode = result[3];
						String errorText = result[4];
						
						if (retVal != null && Integer.parseInt(retVal) == 0) {
							modelAndView.addObject("result", "SUCCESS");
							modelAndView.addObject("startSerial", startSerial);
							modelAndView.addObject("endSerial", endSerial);
							dto.setActionType("SUCCESS");
							logger.debug("set action type to success");
						} else {
							modelAndView.addObject("result", "UNSUCCESS");
							dto.setActionType("UNSUCCESS");
							logger.debug("set action type to unsuccess");
						}
					}
				} else {*/
					String[] result = stockInService.insertStockInventory(insertDto, user.getUsername());
					
					if (result != null && result.length != 0) {
						String retVal = result[0];
						String errorCode = result[1];
						String errorText = result[2];
						
						if (retVal != null && Integer.parseInt(retVal) == 0) {
							if ("06".equals(insertDto.getType())){
								modelAndView.addObject("startSerial", result[3]);
								modelAndView.addObject("endSerial",  result[4]);
							}
							modelAndView.addObject("result", "SUCCESS");
							dto.setActionType("SUCCESS");
							logger.debug("set action type to success");
						} else {
							modelAndView.addObject("result", "UNSUCCESS");
							dto.setActionType("UNSUCCESS");
							logger.debug("set action type to unsuccess");
						}
					}
				/*	if (isSuccess == 1) {
						modelAndView.addObject("result", "SUCCESS");
						dto.setActionType("SUCCESS");
						logger.debug("set action type to success");
					} else {
						modelAndView.addObject("result", "UNSUCCESS");
						dto.setActionType("UNSUCCESS");
						logger.debug("set action type to unsuccess");
					}*/
				//}
			}
		}
		
		MobCcsSessionUtil.setSession(request, "stockIn", dto);// save session to hashMap session
		
		return modelAndView;
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsStockInController ReferenceData called");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		Map referenceData = new HashMap<String, List<String>>();

		// type
		List<CodeLkupDTO> typeList = codeLkupService.getCodeLkupDTOALL("STOCK_TYPE");
		referenceData.put("typeList", typeList);
		
		// location
		List<CodeLkupDTO> locationList = codeLkupService.getCodeLkupDTOALL("STOCK_LOC");
		referenceData.put("locationList", locationList);
		
		// status
		List<CodeLkupDTO> statusList = stockInService.getStatusList();
		referenceData.put("statusList", statusList);

		// stockPool
		referenceData.put("stockPoolList", this.stockService.getMaintParmValue(user.getChannelCd(), FunctionCd.STOCK_QUANTITY_ENQUIRY, ParmType.STOCK_POOL));
		
		return referenceData;
	}
	
}
