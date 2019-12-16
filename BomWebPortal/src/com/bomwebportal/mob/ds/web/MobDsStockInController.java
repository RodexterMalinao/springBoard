package com.bomwebportal.mob.ds.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class MobDsStockInController extends SimpleFormController {

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
		
		logger.info("MobDsStockInController@formBackingObject called");
		
		StockInDTO sessionStockIn = (StockInDTO) MobCcsSessionUtil
				.getSession(request, "stockIn");//get session from hashMap session
		
		StockDTO sessionStock = (StockDTO) MobCcsSessionUtil.getSession(request, "stock");
		
		String sessionStockItemCode = sessionStock.getItemCode();
		
		if (sessionStockIn != null) {
			logger.debug("Action Type =  " + sessionStockIn.getActionType());
		}
		
		if (sessionStockIn == null) {
			
			logger.info("MobDsStockInController@formBackingObject called , stockIn is null");

			sessionStockIn = new StockInDTO();
			
			if (!("".equalsIgnoreCase(sessionStockItemCode) || sessionStockItemCode == null)) {
				List<StockInDTO> temp = stockInService.getStockInDTO(sessionStockItemCode);
				
				if (temp != null && temp.size() > 0) {
					sessionStockIn.setImei(temp.get(0).getImei());
					sessionStockIn.setItemCode(sessionStockItemCode);
					sessionStockIn.setModel(temp.get(0).getModel());
					sessionStockIn.setStatus(temp.get(0).getStatus());
					sessionStockIn.setType(temp.get(0).getType());
					sessionStockIn.setStockPool(temp.get(0).getStockPool());
					MobCcsSessionUtil.setSession(request, "stockIn", sessionStockIn);
				}
			}
	
		} else {
			if ("REFRESH".equalsIgnoreCase(sessionStockIn.getActionType()) || "INSERT".equalsIgnoreCase(sessionStockIn.getActionType())) {
				
				List<StockCatalogDTO> tempList 
					= stockInService.getTypeNModel(sessionStockIn.getItemCode().trim());
				
				if (tempList != null && tempList.size() != 0) {
					
					sessionStockIn.setType(tempList.get(0).getScItemType());
					sessionStockIn.setModel(tempList.get(0).getScItemDesc());
					sessionStockIn.setItemCode(tempList.get(0).getScItemCode());
					sessionStockIn.setMipSimType(tempList.get(0).getMipSimType());
				} else if (tempList == null || tempList.size() == 0){
					sessionStockIn.setType("NONE");
					sessionStockIn.setModel("");
					sessionStockIn.setItemCode("");
					sessionStockIn.setStockPool("");
				}
				
				MobCcsSessionUtil.setSession(request, "stockIn", sessionStockIn);
			}
			
			if ("INSERT".equalsIgnoreCase(sessionStockIn.getActionType())) {
				
				StockInDTO temp = (StockInDTO)MobCcsSessionUtil.getSession(request, "stockIn");
				
				if (temp != null 
						&& temp.getItemCode() != null && !"".equalsIgnoreCase(temp.getItemCode().trim())
						&& temp.getStatus() != null && !"NONE".equalsIgnoreCase(temp.getStatus().trim())
						&& temp.getImei() != null && !"".equalsIgnoreCase(temp.getImei().trim())
						&& temp.getStoreCode() != null && !"".equalsIgnoreCase(temp.getStoreCode().trim())
						&& StringUtils.isNotBlank(temp.getStockPool())
						&& !(temp.getStatus() != null && "RSS".equalsIgnoreCase(temp.getStatus().trim())
							&& (temp.getEventCode() == null || !"".equalsIgnoreCase(temp.getImei().trim())))) {
					boolean isDuplicate = false;
					List<StockInDTO> checkDto = stockInService.checkDuplicateStockIn(temp.getItemCode(), temp.getImei());
					if (checkDto != null && checkDto.size() > 0) {
						isDuplicate = true;
					}
					
					if (!isDuplicate || stockInService.allowRWHStock(temp, checkDto)) {
						
						String insertItemCode = temp.getItemCode();
						String insertType = temp.getType();
						String insertImei = temp.getImei();
						String insertStatus = temp.getStatus();
						String insertRemarks = temp.getRemarks();
						String insertBatchRef = temp.getBatchRef().toUpperCase();
						String insertStaffId = temp.getStaffId();
						
						StockInDTO insertDto = new StockInDTO();
						
						insertDto.setItemCode(insertItemCode);
						sessionStockIn.setItemCode(insertItemCode);
						
						insertDto.setType(insertType);
						sessionStockIn.setType(insertType);
						
						insertDto.setStatus(insertStatus);
						sessionStockIn.setStatus(insertStatus);
						
						insertDto.setImei(insertImei);
						sessionStockIn.setImei(insertImei);
						
						insertDto.setRemarks(insertRemarks);
						sessionStockIn.setRemarks(insertRemarks);
						
						insertDto.setBatchRef(insertBatchRef);
						sessionStockIn.setBatchRef(insertBatchRef);
						
						insertDto.setStockPool(temp.getStockPool());
						sessionStockIn.setStockPool(temp.getStockPool());
						
						insertDto.setStoreCode(temp.getStoreCode());
						sessionStockIn.setStoreCode(temp.getStoreCode());
						
						insertDto.setTeamCode(temp.getTeamCode());
						sessionStockIn.setTeamCode(temp.getTeamCode());
						
						insertDto.setEventCode(temp.getEventCode());
						sessionStockIn.setEventCode(temp.getEventCode());
						
						insertDto.setStaffId(insertStaffId);
						sessionStockIn.setStaffId(insertStaffId);
						
						insertDto.setBookOutDate(temp.getBookOutDate());
						sessionStockIn.setBookOutDate(temp.getBookOutDate());
						
						BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
					
						int isSuccess = -1;
								
						if (isDuplicate) {
							isSuccess =
								stockInService.updateDsRWHStockInventory(checkDto.get(0), insertDto, user.getUsername());
						} else {
							isSuccess =
								stockInService.insertDsStockInventory(insertDto, user.getUsername());
						}
						
						if (isSuccess == 1) {
							request.setAttribute("actionType", "SUCCESS");
							sessionStockIn.setActionType("SUCCESS");
							logger.debug("set action type to success");
							
							sessionStockIn.setImei(null);
						} else {
							request.setAttribute("actionType", "UNSUCCESS");
							sessionStockIn.setActionType("UNSUCCESS");
							logger.debug("set action type to unsuccess");
						}
						MobCcsSessionUtil.setSession(request, "stockIn", sessionStockIn);
					}
				}
			} else if ("CLEAR".equalsIgnoreCase(sessionStockIn.getActionType())) {
				sessionStockIn = new StockInDTO();
				MobCcsSessionUtil.setSession(request, "stockIn", sessionStockIn);
			} else {
				if (!("".equalsIgnoreCase(sessionStockItemCode) || sessionStockItemCode == null)) {
					List<StockInDTO> temp = stockInService.getStockInDTO(sessionStockItemCode);
					if (temp != null && temp.size() > 0) {
						sessionStockIn.setImei(temp.get(0).getImei());
						sessionStockIn.setItemCode(sessionStockItemCode);
						sessionStockIn.setModel(temp.get(0).getModel());
						sessionStockIn.setStatus(temp.get(0).getStatus());
						sessionStockIn.setType(temp.get(0).getType());
						
						MobCcsSessionUtil.setSession(request, "stockIn", sessionStockIn);
					}
				}
			}
		}
		return sessionStockIn;

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobDsStockInController onSubmit called");
		
		StockInDTO dto = (StockInDTO) command;
		MobCcsSessionUtil.setSession(request, "stockIn", dto);// save session to hashMap session
		
		return new ModelAndView(new RedirectView("mobdsstockin.html"));
	}

	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobDsStockInController ReferenceData called");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		Map<String, Object> referenceData = new HashMap<String, Object>();

		// type
		List<CodeLkupDTO> typeList = codeLkupService.getCodeLkupDTOALL("STOCK_TYPE");
		referenceData.put("typeList", typeList);
		
		// status
//		referenceData.put("statusList", this.stockService.getStockMainStatusList("MDV"));
		List<CodeLkupDTO> statusList = new ArrayList<CodeLkupDTO>();
		statusList.add(new CodeLkupDTO("27", codeLkupService.getCodeDesc("STOCK_STS", "27")));
		statusList.add(new CodeLkupDTO("28", codeLkupService.getCodeDesc("STOCK_STS", "28")));
		statusList.add(new CodeLkupDTO("29", codeLkupService.getCodeDesc("STOCK_STS", "29")));
		referenceData.put("statusList", statusList);
		
		// stockPool
		referenceData.put("stockPoolList", this.stockService.getMaintParmValue(user.getChannelCd(), FunctionCd.STOCK_QUANTITY_ENQUIRY, ParmType.STOCK_POOL));
		
		// store and team code
		referenceData.put("storeList", stockService.getUserMultiStoreCode(user.getUsername(), user.getChannelId()));
		referenceData.put("teamListAll", stockService.getUSerMultiStoreTeamCode(user.getUsername(), user.getChannelId()));
		
		// eventCd
		referenceData.put("eventList", this.stockService.getValidEventList());
		
		return referenceData;
	}
	
}
