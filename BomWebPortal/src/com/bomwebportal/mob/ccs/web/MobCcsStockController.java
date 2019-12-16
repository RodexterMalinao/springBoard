package com.bomwebportal.mob.ccs.web;

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

public class MobCcsStockController extends SimpleFormController {
	
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

		logger.info("MobCcsStockController@formBackingObject called");
		
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		if (user == null) {
			user = new BomSalesUserDTO();
		}
		int channelId = user.getChannelId();
		request.getSession().setAttribute("channelId", channelId);
		
		StockDTO sessionStock = (StockDTO) MobCcsSessionUtil
				.getSession(request, "stock");//get session from hashMap session
		
		if (sessionStock == null) {
			
			logger.info("MobCcsStockModelController@formBackingObject called , sessionStock is null");
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
					
					logger.debug("Stock - searching with type & model: " + sessionStock.getType());
					logger.debug("Stock - searching with type & model: " + sessionStock.getModel());
					
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
					
					
					List<StockResultDTO> tempList 
					= stockService.getStockResultDTOByItemSerial(sessionStock.getItemSerial().trim());
				
					request.setAttribute("resultList", tempList);
				
					sessionStock.setStockResult(tempList);

					request.setAttribute("sk", 3);
					sessionStock.setSk(3);
				
				} else if (StringUtils.isNotBlank(sessionStock.getOrderId())) {
					
					// search by order id
					logger.debug("Stock - searching with order id: " + sessionStock.getOrderId());
					
					List<StockResultDTO> tempList 
					= stockService.getStockResultDTOByOrderId(sessionStock.getOrderId().trim());
				
					request.setAttribute("resultList", tempList);
				
					sessionStock.setStockResult(tempList);
					
					request.setAttribute("sk", 4);
					sessionStock.setSk(4);
				}
				

				if (tempMiddleList != null && tempMiddleList.size() > 0) {
					sessionStock.setSelectedNum("0");
					sessionStock.setStatus("02");
				}
				
				sessionStock.setActionType("SEARCH1");
				request.setAttribute("actionType", "SEARCH1");
				
				MobCcsSessionUtil.setSession(request, "stock", sessionStock);

			} else if ("SEARCH2".equalsIgnoreCase(sessionStock.getActionType())) {
				
				request.setAttribute("tempResultList", sessionStock.getTempResult());
				
				if (sessionStock.getSelectedNum() != null) {
					
					List<StockCatalogDTO> tempMiddleList = sessionStock.getTempResult();
					
					String selectedItemCode = tempMiddleList.get(Integer.parseInt(sessionStock.getSelectedNum())).getScItemCode();
				
					sessionStock.setItemCode(selectedItemCode);
					
					List<StockResultDTO> tempList;
					
					tempList = stockService.getStockResultDTO(sessionStock);
					
					request.setAttribute("resultList", tempList);
					/*for (StockResultDTO test : tempList) {
						System.out.println(test.getImei());
					}*/
					sessionStock.setStockResult(tempList);
					sessionStock.setActionType("SEARCH2");
					request.setAttribute("actionType", "SEARCH2");
					
					MobCcsSessionUtil.setSession(request, "stock", sessionStock);
				}
			}
			
		}

		return sessionStock;

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobCcsStockController onSubmit called");
		
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
			return new ModelAndView(new RedirectView("mobccsstockin.html"));
		} else {
			return new ModelAndView(new RedirectView("mobccsstock.html"));
		}

	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobccsstockController ReferenceData called");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		StockDTO dto 
			= (StockDTO) MobCcsSessionUtil.getSession(request, "stock");
		
		CodeLkupDTO all = new CodeLkupDTO();
		all.setCodeDesc("ALL");
		all.setCodeId("ALL");
		
		/*MaintParmLkupDTO allStatus = new MaintParmLkupDTO();
		allStatus.setParmType("ALL");
		allStatus.setParmValue("ALL");*/

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
		List<CodeLkupDTO> statusList = new ArrayList<CodeLkupDTO>();
		// status
		statusList = stockService.getStockMainStatusList("CFM");
		statusList.add(0, all);
		referenceData.put("statusList", statusList);
		
		// stockPool
		referenceData.put("stockPoolList", this.stockService.getMaintParmValue(user.getChannelCd(), FunctionCd.STOCK_QUANTITY_ENQUIRY, ParmType.STOCK_POOL));
		
		return referenceData;
	}
	
}
