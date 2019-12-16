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
import com.bomwebportal.mob.ccs.dto.StockResultDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.StockService;
import com.bomwebportal.mob.ccs.service.StockService.FunctionCd;
import com.bomwebportal.mob.ccs.service.StockService.ParmType;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;

public class MobDsStockController extends SimpleFormController {
	
	private StockService stockService;
	private CodeLkupService codeLkupService;

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}
	public StockService getStockService() {
		return stockService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public Object formBackingObject(HttpServletRequest request)
		throws ServletException {

		logger.info("MobDsStockController@formBackingObject called");
		
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		if (user == null) {
			user = new BomSalesUserDTO();
		}
		
		StockDTO sessionStock = (StockDTO) MobCcsSessionUtil
				.getSession(request, "stock");//get session from hashMap session
		
		if (sessionStock == null) {
			
			logger.info("MobDsStockController@formBackingObject called , sessionStock is null");
			sessionStock = new StockDTO();
			
		} else {
			
			if ("SEARCH1".equalsIgnoreCase(sessionStock.getActionType())) {
			
				List<StockCatalogDTO> tempMiddleList = new ArrayList<StockCatalogDTO>();
				
				if (!"NONE".equalsIgnoreCase(sessionStock.getType())) {
					// search by type & model

					if (sessionStock.getModel() != null && !"".equalsIgnoreCase(sessionStock.getModel().trim())) {
						sessionStock.setModel(sessionStock.getModel().trim().toUpperCase());
						if (sessionStock.getModel().indexOf("*") >= 0) {
							sessionStock.setModel(
									StringUtils.replaceChars(sessionStock.getModel(), '*', '%'));
						}
					}
					
					logger.debug("Stock - searching with type: " + sessionStock.getType() + " & model: " + sessionStock.getModel());
					
					tempMiddleList 
						= stockService.getTempMiddleList(sessionStock.getType(), sessionStock.getModel());
				
					request.setAttribute("tempResultList", tempMiddleList);
					sessionStock.setTempResult(tempMiddleList);
					
					request.setAttribute("sk", 1);
					sessionStock.setSk(1);
				} else if (!"".equalsIgnoreCase(sessionStock.getSearchItemCode().trim())){
					// search by item code
					
					logger.debug("Stock - searching with item code: " + sessionStock.getSearchItemCode());
					
					tempMiddleList 
						= stockService.getTempMiddleListByItemCode(sessionStock.getSearchItemCode().trim());
				
					request.setAttribute("tempResultList", tempMiddleList);
					sessionStock.setTempResult(tempMiddleList);
					
					request.setAttribute("sk", 2);
					sessionStock.setSk(2);
					
				} else if (!"".equalsIgnoreCase(sessionStock.getItemSerial().trim())){
					
					// search by item serial
					logger.debug("Stock - searching with item serial: " + sessionStock.getItemSerial());
					
					sessionStock.setSk(3);
					sessionStock.setChannelId(user.getChannelId());
					sessionStock.setCategory(user.getCategory());
					sessionStock.setStaffId(user.getUsername());
					clearStock(sessionStock);
					
					List<StockResultDTO> tempList = new ArrayList<StockResultDTO>();
					
					tempList = stockService.getDSStockResultDTO(sessionStock);
					
					request.setAttribute("resultList", tempList);
				
					sessionStock.setStockResult(tempList);

					request.setAttribute("sk", 3);
					
				} else if (StringUtils.isNotBlank(sessionStock.getOrderId())) {
					
					// search by order id
					logger.debug("Stock - searching with order id: " + sessionStock.getOrderId());
					
					sessionStock.setSk(4);
					sessionStock.setChannelId(user.getChannelId());
					sessionStock.setCategory(user.getCategory());
					sessionStock.setStaffId(user.getUsername());
					clearStock(sessionStock);
					
					List<StockResultDTO> tempList = new ArrayList<StockResultDTO>();
					
					tempList = stockService.getDSStockResultDTO(sessionStock);
					
					request.setAttribute("resultList", tempList);
				
					sessionStock.setStockResult(tempList);
					
					request.setAttribute("sk", 4);
					
				}
				
				sessionStock.setActionType("SEARCH1");
				request.setAttribute("actionType", "SEARCH1");
				
				MobCcsSessionUtil.setSession(request, "stock", sessionStock);

			} else if ("SEARCH2".equalsIgnoreCase(sessionStock.getActionType())) {
				
				request.setAttribute("tempResultList", sessionStock.getTempResult());
				if (sessionStock.getSelectedNumList() != null && 
						sessionStock.getSelectedNumList().size() != 0) {
					
					List<StockCatalogDTO> tempMiddleList = sessionStock.getTempResult();
					
					List<String> selectedItemCodeList = new ArrayList<String>();
					for (String selectedNumTemp : sessionStock.getSelectedNumList()) {
						selectedItemCodeList.add(tempMiddleList.get(Integer.parseInt(selectedNumTemp)).getScItemCode());
					}
					sessionStock.setSelectedItemCodeList(selectedItemCodeList);
					
					List<StockResultDTO> tempList = new ArrayList<StockResultDTO>();
					
					sessionStock.setChannelId(user.getChannelId());
					sessionStock.setCategory(user.getCategory());
					sessionStock.setStaffId(user.getUsername());
					
					if ("".equalsIgnoreCase(sessionStock.getStoreCode())) {
						sessionStock.setTeamCode("");
					}
 					
					tempList = stockService.getDSStockResultDTO(sessionStock);
					
					request.setAttribute("resultList", tempList);
					sessionStock.setStockResult(tempList);
					sessionStock.setActionType("SEARCH2");
					request.setAttribute("actionType", "SEARCH2");
					
					MobCcsSessionUtil.setSession(request, "stock", sessionStock);
				}
				
			} else if ("CLEAR1".equalsIgnoreCase(sessionStock.getActionType()) || "CLEAR2".equalsIgnoreCase(sessionStock.getActionType())) {
				
				clearStock(sessionStock);
				
				if ("CLEAR1".equalsIgnoreCase(sessionStock.getActionType())) {
					sessionStock.setActionType("");
					request.setAttribute("actionType", "");
				} else {
					sessionStock.setActionType("SEARCH1");
					request.setAttribute("actionType", "SEARCH1");
					request.setAttribute("tempResultList", sessionStock.getTempResult());
					request.setAttribute("sk", sessionStock.getSk());
				}
				
				MobCcsSessionUtil.setSession(request, "stock", sessionStock);
			}
		}
		return sessionStock;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobDsStockController onSubmit called");
		
		StockDTO dto = (StockDTO) command;
		MobCcsSessionUtil.setSession(request, "stock", dto);// save session to hashMap session
		
		if ("INSTOCK".equalsIgnoreCase(dto.getActionType())) {
			logger.debug("From stock to stock in page");
			
			if (dto.getSelectedNum() != null) {
				
				List<StockCatalogDTO> tempMiddleList = dto.getTempResult();
				
				String selectedItemCode = tempMiddleList.get(Integer.parseInt(dto.getSelectedNum())).getScItemCode();
				logger.debug("selected item code in stock main page: " + selectedItemCode);
				dto.setItemCode(selectedItemCode);
				
				MobCcsSessionUtil.setSession(request, "stock", dto);
			}
			return new ModelAndView(new RedirectView("mobdsstockin.html"));
		} else {
			return new ModelAndView(new RedirectView("mobdsstock.html"));
		}

	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobccsstockController ReferenceData called");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		StockDTO dto 
			= (StockDTO) MobCcsSessionUtil.getSession(request, "stock");
		
		CodeLkupDTO all = new CodeLkupDTO("ALL", "ALL");
		
		Map referenceData = new HashMap<String, List<String>>();

		// type
		List<CodeLkupDTO> typeList = codeLkupService.getCodeLkupDTOALL("STOCK_TYPE");
		typeList.add(0, all);
		referenceData.put("typeList", typeList);

		// model
		if (dto != null && ("".equals(dto.getActionType()) || dto.getActionType() == null)) {
			List<StockCatalogDTO> modelList = stockService.getModelList();
			referenceData.put("modelList", modelList);
		}
		
		// status
		List<CodeLkupDTO> statusList = new ArrayList<CodeLkupDTO>();
		statusList = stockService.getStockMainStatusList("MDV");
		statusList.add(0, all);
		referenceData.put("statusList", statusList);
		
		// stockPool
		referenceData.put("stockPoolList", this.stockService.getMaintParmValue(user.getChannelCd(), FunctionCd.STOCK_QUANTITY_ENQUIRY, ParmType.STOCK_POOL));
		
		//Store Code and Team Code
		referenceData.put("storeList", stockService.getUserMultiStoreCode(user.getUsername(), user.getChannelId()));
		referenceData.put("teamListAll", stockService.getUSerMultiStoreTeamCode(user.getUsername(), user.getChannelId()));
		
		// event
		referenceData.put("eventList", stockService.getEventList());
		
		return referenceData;
	}
	
	private void clearStock(StockDTO sessionStock) {
		sessionStock.setStockPool("DS");
		sessionStock.setStoreCode(null);
		sessionStock.setTeamCode(null);
		sessionStock.setEventCode(null);
		sessionStock.setStatus(null);
		sessionStock.setSelectedItemCodeList(null);
		sessionStock.setSelectedNumList(null);
	}
	
}
