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
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDetailDTO;
import com.bomwebportal.mob.ccs.dto.ui.StockManualAssgnUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.StockManualAssgnService;
import com.bomwebportal.mob.ccs.service.StockManualDetailService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;

public class MobDsStockManualAssgnController extends SimpleFormController {
	
	private StockManualAssgnService stockManualAssgnService;
	private StockManualDetailService stockManualDetailService;
	private CodeLkupService codeLkupService;

	public void setStockManualAssgnService(StockManualAssgnService stockManualAssgnService) {
		this.stockManualAssgnService = stockManualAssgnService;
	}
	public StockManualAssgnService getStockManualAssgnService() {
		return stockManualAssgnService;
	}

	public StockManualDetailService getStockManualDetailService() {
		return stockManualDetailService;
	}
	public void setStockManualDetailService(
			StockManualDetailService stockManualDetailService) {
		this.stockManualDetailService = stockManualDetailService;
	}
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		StockManualAssgnUI ui = new StockManualAssgnUI();
		
		String itemCode = request.getParameter("itemCode");
		
		request.setAttribute("itemCode", itemCode);
		ui.setItemCode(itemCode);
		
		String locFlag = request.getParameter("locFlag");
		ui.setLocation(locFlag);
		
		String isSave = request.getParameter("save");
		request.setAttribute("save", isSave);
		
		String source = request.getParameter("source");
		ui.setSource(source);
		
		return ui;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		StockManualAssgnUI sessionStockManualAssgnUI = (StockManualAssgnUI) command;
		MobCcsSessionUtil.setSession(request, "sessionStockManualAssgnUI", sessionStockManualAssgnUI);
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		String action = ServletRequestUtils.getRequiredStringParameter(request, "action");
		String actionType = (String) request.getParameter("actionType");
		String orderId = request.getParameter("orderId");
		String itemCode = sessionStockManualAssgnUI.getItemCode();
		if (StringUtils.contains(itemCode, ',')) {
			int commapos = itemCode.indexOf(",");
			itemCode = itemCode.substring(commapos+1).trim();
		}
		sessionStockManualAssgnUI.setItemCode(itemCode);
		
		if ("ASSIGN".equalsIgnoreCase(actionType)) {
			
			List<StockManualDetailDTO> list = new ArrayList<StockManualDetailDTO>();
			list = stockManualDetailService.getStockManualDetail(orderId);
			
			StockManualAssgnUI inDto = new StockManualAssgnUI();
			inDto.setItemCode(sessionStockManualAssgnUI.getItemCode());
			inDto.setItemSerial(sessionStockManualAssgnUI.getSelItemSerial());
			inDto.setStatus(sessionStockManualAssgnUI.getStatus());
			inDto.setOrderId(orderId);
			inDto.setUsername(user.getUsername());
			
			// INSERT
			int dsResult = 0;
			
			StockManualAssgnUI oDto = stockManualAssgnService.getAssignedItemSerialStatus(orderId, request.getParameter("itemCode"));
			if (oDto != null) {
				oDto.setOrderId(orderId);
				oDto.setItemCode(request.getParameter("itemCode"));
			} else {
				oDto = new StockManualAssgnUI();
				oDto.setOrderId(orderId);
				oDto.setItemCode(request.getParameter("itemCode"));
			}
			
			if (oDto.getItemSerial() != null) {
				//deassign old item
				StockManualAssgnUI tempDto = new StockManualAssgnUI();
				tempDto.setOrderId(oDto.getOrderId());
				tempDto.setItemCode(oDto.getItemCode());
				dsResult = stockManualAssgnService.manualDSStockAssgn(tempDto, oDto, orderId, user.getUsername(), action);
				oDto.setSelItemSerial(oDto.getItemSerial());
				oDto.setItemSerial(null);
				tempDto = null;
			}
			//Assign Item
			if (dsResult >= 0) {
				dsResult = stockManualAssgnService.manualDSStockAssgn(inDto, oDto, orderId, user.getUsername(), action);
			}
			
			if (dsResult > 0) {
				request.setAttribute("save", "Y");
				request.getSession().setAttribute("save", "Y");
				if (user.getChannelId() == 11) {
					return new ModelAndView(new RedirectView("mobdsstockmanualdetail.html?orderId="+sessionStockManualAssgnUI.getOrderId()));
				}
			} else {
				request.setAttribute("save", "N");
				return new ModelAndView(
						new RedirectView("mobdsstockmanualassgn.html?itemCode="+sessionStockManualAssgnUI.getItemCode()
								+"&orderId="+sessionStockManualAssgnUI.getOrderId()+"&action="+action+"&save=N&source="+sessionStockManualAssgnUI.getSource()));
			}
		}
		return new ModelAndView(new RedirectView("mobdsstockmanualassgn.html?itemCode="+sessionStockManualAssgnUI.getItemCode()
								+"&orderId="+sessionStockManualAssgnUI.getOrderId()+"&action="+action+"&save=Y&source="+sessionStockManualAssgnUI.getSource()));
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobDsStockManualAssgnController ReferenceData called");

		Map referenceData = new HashMap<String, List<String>>();

		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		String itemCode = ServletRequestUtils.getRequiredStringParameter(request, "itemCode");
		List<CodeLkupDTO> itemCodeList = stockManualAssgnService.getItemColorlist(itemCode);
		if (itemCodeList == null || itemCodeList.isEmpty()) {
			request.getSession().setAttribute("hasOtherColor", false);
		} else {
			request.getSession().setAttribute("hasOtherColor", true);
			referenceData.put("itemCodeList", itemCodeList);
		}
		
		String source= (String) request.getParameter("source");
		referenceData.put("source", source);

		String action = ServletRequestUtils.getRequiredStringParameter(request, "action");
		referenceData.put("action", action);
		
		return referenceData;
	}
	
}
