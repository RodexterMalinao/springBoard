package com.bomwebportal.mob.ds.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ds.service.MobDsOrderService;
import com.bomwebportal.mob.ds.ui.MobDsOrderSearchUI;
import com.bomwebportal.util.BomWebPortalConstant;

public class MobDsOrderSearchController  extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobDsOrderService mobDsOrderService;

	public MobDsOrderService getMobDsOrderService() {
		return mobDsOrderService;
	}

	public void setMobDsOrderService(
			MobDsOrderService mobDsOrderSearchService) {
		this.mobDsOrderService = mobDsOrderSearchService;
	}
	
	protected ModelAndView onSubmit(
			HttpServletRequest request,	HttpServletResponse response, Object command,	BindException errors)
			throws Exception {
		
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		MobDsOrderSearchUI ui = (MobDsOrderSearchUI) command;
		
		if (ui.getSearchStartDate() == null) {
			ui.setSearchStartDate("");
		}
		
		if (ui.getSearchEndDate() == null) {
			ui.setSearchEndDate("");
		}
		
		if (ui.getSearchOrderId() == null) {
			ui.setSearchOrderId("");
		}
		
		if (ui.getSearchOrderSts() == null) {
			ui.setSearchOrderSts("");
		}
				
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		ModelAndView modelAndView = new ModelAndView("mobdsordersearch", model);
		
		List<OrderDTO> orderList = new ArrayList<OrderDTO> ();
		
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		
//		List<CodeLkupDTO> support = entityCodeMap.get("CCS_SUPPORT");
//		List<CodeLkupDTO> ch = entityCodeMap.get("CCS_CH");
//		List<CodeLkupDTO> sales = entityCodeMap .get("MOB_SALES_FALLOUT_CD");
//		List<String> codeIdList = new ArrayList<String>();
		
		String group = null;
		//if ("DSA".equalsIgnoreCase(salesUserDto.getChannelCd())){
		if ("MOB".equalsIgnoreCase(salesUserDto.getChannelCd())){
			group = "SUPPORT";
		}
		
//		ui.setSearchOrderType("ALL");
		
		if (!(ui.getSearchOrderId().isEmpty() && ui.getSearchStartDate().isEmpty() 
				&& ui.getSearchEndDate().isEmpty() && ui.getSearchOrderSts().equalsIgnoreCase("ALL")
				&& ui.getSearchMrt().isEmpty() && ui.getSearchOrderType().equalsIgnoreCase("ALL") && ui.getSearchOrderNature().equalsIgnoreCase("ALL"))) {
						
			if ((BomWebPortalConstant.BWP_ORDER_REVIEWING.equals(ui.getSearchOrderSts())) 
					&& ui.getSearchStartDate().isEmpty()) {
				
				List<OrderDTO> temp = mobDsOrderService.findOrderReview(salesUserDto.getUsername(), ("" + salesUserDto.getChannelId()), salesUserDto.getChannelCd(), 
						 salesUserDto.getCategory());
				
				for (OrderDTO tempOrder:temp) {
					if (BomWebPortalConstant.BWP_ORDER_REVIEWING.equals(tempOrder.getOrderStatus()) || (tempOrder.getOrderStatus().equals("01") && tempOrder.getCheckPoint().equals("610"))
							/*&& (("N".equals(tempOrder.getSuperAppInd()) && "SUPERVISOR".equals(salesUserDto.getCategory()))
									|| ("N".equals(tempOrder.getOrderAppInd()) && "ORDER SUPPORT".equals(salesUserDto.getCategory())))*/
							) {
						orderList.add(tempOrder);
					}
				}
				
			} else {
				
				orderList = mobDsOrderService.findOrderEnquiry(ui.getSearchStartDate(), ui.getSearchEndDate(), 
						ui.getSearchOrderId(), ui.getSearchOrderSts(), ui.getVariousDateType(), ui.getSearchChannel(), 
						ui.getSearchStaffId(), ui.getSearchOrderType(), salesUserDto.getCategory(), 
						salesUserDto.getAreaCd(), salesUserDto.getShopCd(), group, ui.getSearchMrt(),salesUserDto.getChannelCd(),
						ui.getSearchAoInd(), ui.getSearchAoItemCode(), ui.getSearchOrderNature());
			}
			
		}
				
		
		String index = (String) request.getParameter("index");
		PagedListHolder orderPagedList = null;
		int noOfPages = 1;
		
		if (StringUtils.isNotBlank(index)) {
			orderPagedList = (PagedListHolder) request.getSession().getAttribute("sessionOrderList");
			 if("next".equals(index)){
				 orderPagedList.nextPage();
			 } else if ("previous".equals(index)) {
				 orderPagedList.previousPage();
			 } else {
				 orderPagedList.setPage(Integer.parseInt(index) - 1);
			 }
		} else {
			if (orderList != null) {
				orderPagedList = new PagedListHolder(orderList);
			}
		}
		
		if (orderPagedList != null) {
			
			noOfPages = (int) orderPagedList.getPageCount();
			modelAndView.addObject("currentPage", orderPagedList.getPage() + 1);	
			
			modelAndView.addObject("sessionOrderList", orderPagedList);
			modelAndView.addObject("noOfPages", noOfPages);
			request.getSession().setAttribute("sessionOrderList", orderPagedList);
			
		}
		
		return modelAndView;
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		MobDsOrderSearchUI ui = new MobDsOrderSearchUI();
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		ui.setSearchStaffId(salesUserDto.getUsername());
		ui.setVariousDateType("APP");
		ui.setSearchChannel(salesUserDto.getChannelCd());
		
		return ui;
		
	}
	
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		CodeLkupDTO initValue = new CodeLkupDTO();
		initValue.setCodeId("ALL");
		initValue.setCodeDesc("ALL");
		
		List<CodeLkupDTO> orderStatus = new ArrayList<CodeLkupDTO>(); 
		orderStatus.add(0, initValue);
		
		
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		
		//we take value of source parameter to shorten our code description searching process
		
		for (CodeLkupDTO dto : entityCodeMap.get("DS_ORD_STATUS")) {
				orderStatus.add(dto);
		}
		
				
		
		Map referenceData = new HashMap<String, List<String>>();
		referenceData.put("orderStatus", orderStatus);
		
		return referenceData;
	}	

}
