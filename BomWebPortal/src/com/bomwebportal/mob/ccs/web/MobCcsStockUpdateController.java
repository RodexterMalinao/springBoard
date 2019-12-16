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
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.StockService;
import com.bomwebportal.mob.ccs.service.StockService.FunctionCd;
import com.bomwebportal.mob.ccs.service.StockService.ParmType;
import com.bomwebportal.mob.ccs.service.StockUpdateService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.lowagie.text.Utilities;

public class MobCcsStockUpdateController extends SimpleFormController {
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
		String stockPoolType = request.getParameter("stockPoolType");
		
		logger.info("MobCcsStockUpdateController@formBackingObject called");
		
		List<StockUpdateDTO> tempDto = new ArrayList<StockUpdateDTO>();
		
		if (StringUtils.isNotEmpty(serialNumber) && StringUtils.isNotEmpty(stockPoolType)) {
			if (serialNumber.startsWith("#") || stockPoolType.equalsIgnoreCase("ONLINE")) {
				tempDto = stockUpdateService.getStockUpdateDTObyImei(serialNumber);
			} else {
				tempDto = stockUpdateService.getStockUpdateDTObyImeiReal(serialNumber);
			}
		}

		if (tempDto != null && tempDto.size() > 0) {
			MobCcsSessionUtil.setSession(request, "stockUpdate", tempDto.get(0));
		}
		
		StockUpdateDTO sessionStockUpdate = (StockUpdateDTO) MobCcsSessionUtil.getSession(request, "stockUpdate");//get session from hashMap session
		if (sessionStockUpdate == null) {
			logger.info("MobCcsStockUpdateController@formBackingObject called , sessionStockUpdate is null");
			sessionStockUpdate = new StockUpdateDTO();
		} else {
			
			if ("UPDATE".equalsIgnoreCase(sessionStockUpdate.getActionType())) {
				
				StockUpdateDTO temp = (StockUpdateDTO)MobCcsSessionUtil.getSession(request, "stockUpdate");
				if (temp != null && temp.getImei() != null ){
					tempDto = stockUpdateService.getStockUpdateDTObyImei(temp.getImei());//virtual
				} else {
					tempDto = stockUpdateService.getStockUpdateDTObyImeiReal(temp.getItemSerialReal());//Real
				}
				if (temp != null && tempDto!=null) {
					StockUpdateDTO updateDto = new StockUpdateDTO();
					
				/*	if ((!tempDto.get(0).getStockPool().equalsIgnoreCase(temp.getStockPool())) || (!tempDto.get(0).getLocation().equalsIgnoreCase(temp.getLocation()))){
						updateDto.setAction("1");
					}
					
					if (!tempDto.get(0).getStatus().equalsIgnoreCase(temp.getStatus())){
						updateDto.setAction("2");
					}
					
					
					if ("SBS".equalsIgnoreCase(temp.getStockPool())){
						updateDto.setAction("3");
					}*/
					
					String insertLocation = temp.getLocation();
					String insertStatus = temp.getStatus();
					String insertImei = temp.getImei();
					String insertItemCode = temp.getItemCode();
					String insertRemarks = temp.getRemarks();
					
					
					
					updateDto.setLocation(insertLocation);
					updateDto.setStatus(insertStatus);
					updateDto.setImei(insertImei);
					updateDto.setType(temp.getType());
					updateDto.setItemCode(insertItemCode);
					updateDto.setRemarks(insertRemarks.toUpperCase());
					if (temp.getImei() != null) {//virtual
						updateDto.setOldStockPool(tempDto.get(0).getStockPool());
						updateDto.setStockPool(temp.getStockPool());
					} else {//real
						updateDto.setOldStockPool(temp.getStockPool());
						updateDto.setStockPool(null);
					}
					updateDto.setOldStatus(tempDto.get(0).getStatus());
					updateDto.setItemSerialReal(temp.getItemSerialReal());
					updateDto.setRemarks(temp.getRemarks());
					
					String resultStatus = "";
					resultStatus = stockUpdateService.updateStockInventory(updateDto, user.getUsername());
					String[] resultStatusArray = resultStatus.split("&");
					

					if (resultStatusArray[0].equals("0")) {
						request.setAttribute("actionType", "SUCCESS");
					} else {
						request.setAttribute("actionType", "UNSUCCESS");
						request.setAttribute("errorMsg", resultStatusArray[2]);
					}
				}
				MobCcsSessionUtil.setSession(request, "stockUpdate", sessionStockUpdate);
			}
		}
		return sessionStockUpdate;

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		return new ModelAndView(new RedirectView("mobccsstockupdate.html"));
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		
		logger.info("MobccsstockmodelController ReferenceData called");
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
		
		// location
		List<CodeLkupDTO> locationList = codeLkupService.getCodeLkupDTOALL("STOCK_LOC");
		referenceData.put("locationList", locationList);
		
		// status
		// 02 FREE --> FREE, RESERVE, DOA, TRANSFER, RETURN
		// 18 RESERVE --> FREE, RESERVE, TRANSFER, DOA, RETURN
		// 19 ASSIGN (NOT ALLOW TO BE UPDATED)
		// 20 SOLD (NOT ALLOW TO BE UPDATED)
		// 21 TRANSFER --> FREE, RESERVE, TRANSFER
		// 22 RETURN --> RETURN
		// 23 DOA --> RETURN, DOA
		// 24 DEASSIGN
		// 26 FAULTY
		List<CodeLkupDTO> statusList = new ArrayList<CodeLkupDTO>();

		if ("02".equals(sessionStatus)) {
			statusList.add(new CodeLkupDTO("02", "FREE"));
			statusList.add(new CodeLkupDTO("18", "RESERVE"));
			statusList.add(new CodeLkupDTO("21", "TRANSFER")); 
			statusList.add(new CodeLkupDTO("22", "RETURN"));
//				statusList.add(new CodeLkupDTO("23", "DOA"));
//			statusList.add(new CodeLkupDTO("26", "FAULTY"));
			
		} else if ("18".equals(sessionStatus)){
			statusList.add(new CodeLkupDTO("02", "FREE"));
			statusList.add(new CodeLkupDTO("18", "RESERVE"));
			statusList.add(new CodeLkupDTO("21", "TRANSFER"));
			statusList.add(new CodeLkupDTO("22", "RETURN"));
//				statusList.add(new CodeLkupDTO("23", "DOA"));
			//statusList.add(new CodeLkupDTO("26", "FAULTY"));
			
		} else if ("19".equals(sessionStatus)){
			statusList.add(new CodeLkupDTO("19", "ASSIGN"));
			
		} else if ("20".equals(sessionStatus)) {
			statusList.add(new CodeLkupDTO("20", "SOLD"));
			
		} else if ("21".equals(sessionStatus)){
			statusList.add(new CodeLkupDTO("02", "FREE"));
//			statusList.add(new CodeLkupDTO("18", "RESERVE"));
			statusList.add(new CodeLkupDTO("21", "TRANSFER"));
			
		} else if ("22".equals(sessionStatus)){
			statusList.add(new CodeLkupDTO("02", "FREE"));
			statusList.add(new CodeLkupDTO("22", "RETURN"));
			
		} else if ("23".equals(sessionStatus)){
//			statusList.add(new CodeLkupDTO("02", "FREE"));
//			statusList.add(new CodeLkupDTO("18", "RESERVE"));
//			statusList.add(new CodeLkupDTO("22", "RETURN"));
			statusList.add(new CodeLkupDTO("23", "DOA"));
			statusList.add(new CodeLkupDTO("26", "FAULTY"));
			
		} else if ("25".equals(sessionStatus)) {
			statusList.add(new CodeLkupDTO("25", "CANCELLING"));
		
		} else if ("26".equals(sessionStatus)) {
			statusList.add(new CodeLkupDTO("02", "FREE"));
			statusList.add(new CodeLkupDTO("22", "RETURN"));
			statusList.add(new CodeLkupDTO("26", "FAULTY"));
		}else if ("35".equals(sessionStatus)){
			statusList.add(new CodeLkupDTO("35", "ALLOCATED"));
		}
		
		referenceData.put("statusList", statusList);

		// stockPool
		referenceData.put("stockPoolList", this.stockService.getMaintParmValue(user.getChannelCd(), FunctionCd.STOCK_QUANTITY_ENQUIRY, ParmType.STOCK_POOL));
		return referenceData;
	}
	
}
