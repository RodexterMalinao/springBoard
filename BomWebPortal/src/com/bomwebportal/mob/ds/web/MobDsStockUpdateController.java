package com.bomwebportal.mob.ds.web;

import java.util.ArrayList;
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
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.StockService;
import com.bomwebportal.mob.ccs.service.StockService.FunctionCd;
import com.bomwebportal.mob.ccs.service.StockService.ParmType;
import com.bomwebportal.mob.ccs.service.StockUpdateService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;

public class MobDsStockUpdateController extends SimpleFormController {
	private StockService stockService;
	private StockUpdateService stockUpdateService;
	private CodeLkupService codeLkupService;

	public StockService getStockService() {
		return stockService;
	}
	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}
	public void setStockUpdateService(StockUpdateService stockUpdateService) {
		this.stockUpdateService = stockUpdateService;
	}
	public StockUpdateService getStockUpdateService() {
		return stockUpdateService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String serialNumber = request.getParameter("serialNumber");
		
		logger.info("MobDsStockUpdateController@formBackingObject called");
		
		List<StockUpdateDTO> tempDto = new ArrayList<StockUpdateDTO>();
		tempDto = stockUpdateService.getDSStockUpdateDTObyImei(serialNumber);
		
		if (tempDto != null && tempDto.size() > 0) {
			MobCcsSessionUtil.setSession(request, "stockUpdate", tempDto.get(0));
		}
		
		StockUpdateDTO sessionStockUpdate = (StockUpdateDTO) MobCcsSessionUtil
		.getSession(request, "stockUpdate");//get session from hashMap session
		if (sessionStockUpdate == null) {
			
			logger.info("MobDsStockUpdateController@formBackingObject called , sessionStockUpdate is null");
			sessionStockUpdate = new StockUpdateDTO();
			
		} else {
			
			if ("UPDATE".equalsIgnoreCase(sessionStockUpdate.getActionType())) {
				
				StockUpdateDTO temp = (StockUpdateDTO)MobCcsSessionUtil.getSession(request, "stockUpdate");
				
				if (temp != null) {
					
					String insertStatus = temp.getStatus();
					String insertImei = temp.getImei();
					String insertItemCode = temp.getItemCode();
					String insertRemarks = temp.getRemarks();
					
					StockUpdateDTO updateDto = new StockUpdateDTO();
					
					updateDto.setStatus(insertStatus);
					updateDto.setImei(insertImei);
					updateDto.setItemCode(insertItemCode);
					updateDto.setRemarks(insertRemarks.toUpperCase());
					updateDto.setStockPool(temp.getStockPool());
					
					List<StockUpdateDTO> originalDtoList = stockUpdateService.getDSStockUpdateDTObyImei(temp.getImei());
					StockUpdateDTO originalDto = originalDtoList.get(0);
					
					String insertStaffID = temp.getStaffId();
					String insertEventCode = temp.getEventCode();
					if ("".equals(temp.getEventCode())) {
						insertEventCode = null;
					}
					String insertStoreCode = temp.getStoreCode();
					String insertTeamCode = temp.getTeamCode();

					updateDto.setStaffId(insertStaffID);
					updateDto.setEventCode(insertEventCode);
					updateDto.setStoreCode(insertStoreCode);
					updateDto.setTeamCode(insertTeamCode);
					
					/*
					 * 1. MDV Team Checking    - MDV can transfer their team item within team only
					 * 2. SIS Store Checking   - SIS can transfer same store item within same store
					 * 3. WHT Channel Checking - WHT can transfer item with same SIS/MDV team code
					 * 4. SIS not allow to modify RSS item
					 * --------validator--------
					 * 5. Staff checking
					 * x6. RSS/SOS, SOS/RSS status change checking
					 * 7. staff validation
					 * 8. SOS transfer
					 * 9. RSS event change checking
					 * 		-> RSS / Normal update (stock_pool/add remarks) / No change
					 * 10. RWH checking
					 */
					
					int isSuccess = -1;
					int dsError = 0;
					
					boolean allowUpdate = true;
					
					if ("SUPERVISOR".equalsIgnoreCase(user.getCategory())) {
						List<StockDTO> shopList = stockService.getUSerMultiStoreTeamCode(user.getUsername(), user.getChannelId());
						for (StockDTO u : shopList) {
							if (u.getTeamCode().equalsIgnoreCase(originalDto.getTeamCode())) {
								allowUpdate = true;
								break;
							} else {
								allowUpdate = false;
							}
						}
						
						if (!user.getAreaCd().equalsIgnoreCase(originalDto.getStoreCode())) {
							allowUpdate = false;
						}
						
						if (!allowUpdate) {
							dsError = 1;
							request.setAttribute("dsError", dsError);
						}
					} else if (("MDV".equalsIgnoreCase(user.getChannelCd()) || "SIS".equalsIgnoreCase(user.getChannelCd())) 
							&& !(user.getShopCd().equalsIgnoreCase(originalDto.getTeamCode()))) {
						dsError = 1;
						request.setAttribute("dsError", dsError);
					} else if (!user.getShopCd().equalsIgnoreCase(originalDto.getChannelCd())) {
						dsError = 1;
						request.setAttribute("dsError", dsError);
					} else if (("SIS".equalsIgnoreCase(user.getChannelCd()) || "SIS".equalsIgnoreCase(user.getShopCd())) &&
							"28".equals(originalDto.getStatus())) {
						dsError = 2;
						request.setAttribute("dsError", dsError);
					} 
					
					if (dsError == 0) {
						isSuccess = stockUpdateService.updateDSStockInventory(updateDto, originalDto.getStatus(), user.getUsername());
						if (isSuccess == 1) {
							request.setAttribute("actionType", "SUCCESS");
						} else {
							request.setAttribute("actionType", "UNSUCCESS");
						}
					}
					MobCcsSessionUtil.setSession(request, "stockUpdate", sessionStockUpdate);
				}
			}
		}
		return sessionStockUpdate;

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		return new ModelAndView(new RedirectView("mobdsstockupdate.html"));
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		
		logger.info("MobdsstockmodelController ReferenceData called");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		request.getSession().setAttribute("channelId", user.getChannelId());
		
		StockUpdateDTO sessionStockUpdate = 
				(StockUpdateDTO) MobCcsSessionUtil.getSession(request, "stockUpdate");
		
		String sessionStatus = "";
		
		if (sessionStockUpdate != null && sessionStockUpdate.getStatus() != null) {
			sessionStatus = sessionStockUpdate.getStatus();
		}

		Map referenceData = new HashMap<String, List<String>>();
		
		// type
		List<CodeLkupDTO> typeList = codeLkupService.getCodeLkupDTOALL("STOCK_TYPE");
		referenceData.put("typeList", typeList);
		
		// status
		List<CodeLkupDTO> statusList = new ArrayList<CodeLkupDTO>();
		if ("18".equals(sessionStatus)) {
			statusList.add(new CodeLkupDTO("27", codeLkupService.getCodeDesc("STOCK_STS", "27")));
			statusList.add(new CodeLkupDTO("18", codeLkupService.getCodeDesc("STOCK_STS", "18")));
		} else if ("27".equals(sessionStatus)) {
			statusList.add(new CodeLkupDTO("27", codeLkupService.getCodeDesc("STOCK_STS", "27")));
			statusList.add(new CodeLkupDTO("29", codeLkupService.getCodeDesc("STOCK_STS", "29")));
		} else if ("28".equals(sessionStatus)) {
			statusList.add(new CodeLkupDTO("28", codeLkupService.getCodeDesc("STOCK_STS", "28")));
			statusList.add(new CodeLkupDTO("29", codeLkupService.getCodeDesc("STOCK_STS", "29")));
		} else if ("29".equals(sessionStatus)) {
			statusList.add(new CodeLkupDTO("27", codeLkupService.getCodeDesc("STOCK_STS", "27")));
			statusList.add(new CodeLkupDTO("28", codeLkupService.getCodeDesc("STOCK_STS", "28")));
			statusList.add(new CodeLkupDTO("29", codeLkupService.getCodeDesc("STOCK_STS", "29")));
		} else if (sessionStockUpdate != null && sessionStockUpdate.getStatus() != null) {
			statusList.add(new CodeLkupDTO(sessionStockUpdate.getStatus(), codeLkupService.getCodeDesc("STOCK_STS", sessionStockUpdate.getStatus())));
		}
		referenceData.put("statusList", statusList);

		// stockPool
		referenceData.put("stockPoolList", this.stockService.getMaintParmValue("MOB", FunctionCd.STOCK_QUANTITY_ENQUIRY, ParmType.STOCK_POOL));

		// Event Code
		List<StockDTO> eventList = new ArrayList<StockDTO>();
		if ("28".equals(sessionStockUpdate.getStatus()) || "29".equals(sessionStockUpdate.getStatus())) {
			eventList = this.stockService.getValidEventList();
			if (sessionStockUpdate.getEventCode() != null && !"".equals(sessionStockUpdate.getEventCode())) {
				boolean isValidEvent = stockUpdateService.validateEffEndDate(sessionStockUpdate.getEventCode());
				if (!isValidEvent) {
					StockDTO eventDto = new StockDTO();
					eventDto.setEventCode(sessionStockUpdate.getEventCode());
					eventList.add(eventDto);
				}
			}
		} else if (sessionStockUpdate.getEventCode() != null && !"".equals(sessionStockUpdate.getEventCode())) {
			StockDTO eventDto = new StockDTO();
			eventDto.setEventCode(sessionStockUpdate.getEventCode());
			eventList.add(eventDto);
		}
		referenceData.put("eventCodeList", eventList);
		
		// Store Code and Team Code
		if (sessionStockUpdate != null && sessionStockUpdate.getChannelCd() != null) {
			referenceData.put("storeList", stockService.getStoreList(sessionStockUpdate.getChannelCd()));
			referenceData.put("teamListAll", stockService.getTeamList(sessionStockUpdate.getChannelCd()));
		} else if (user.getChannelId() == 10) {
			referenceData.put("storeList", stockService.getStoreList(user.getChannelCd()));
			referenceData.put("teamListAll", stockService.getTeamList(user.getChannelCd()));
		} else {
			referenceData.put("storeList", stockService.getStoreList(user.getShopCd()));
			referenceData.put("teamListAll", stockService.getTeamList(user.getShopCd()));
		}
		
		return referenceData;
	}
	
}
