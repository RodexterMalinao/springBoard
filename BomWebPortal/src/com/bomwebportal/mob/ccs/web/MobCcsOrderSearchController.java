package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsOrderSearchUI;
import com.bomwebportal.mob.ccs.service.MobCcsOrderSearchService;

public class MobCcsOrderSearchController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	MobCcsOrderSearchService mobCcsOrderSearchService;
	
	/**
	 * @return the mobCcsOrderSearchService
	 */
	public MobCcsOrderSearchService getMobCcsOrderSearchService() {
		return mobCcsOrderSearchService;
	}

	/**
	 * @param mobCcsOrderSearchService the mobCcsOrderSearchService to set
	 */
	public void setMobCcsOrderSearchService(
			MobCcsOrderSearchService mobCcsOrderSearchService) {
		this.mobCcsOrderSearchService = mobCcsOrderSearchService;
	}
	
	protected ModelAndView onSubmit(
			HttpServletRequest request,	HttpServletResponse response, Object command,	BindException errors)
			throws Exception {
		
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		MobCcsOrderSearchUI ui = (MobCcsOrderSearchUI) command;
		
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
		
		ModelAndView modelAndView = new ModelAndView("mobccsordersearch", model);
		
		List<OrderDTO> orderList = null;
		
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();

		List<CodeLkupDTO> support = entityCodeMap.get("CCS_SUPPORT");
		List<CodeLkupDTO> ch = entityCodeMap.get("CCS_CH");
		List<CodeLkupDTO> sales = entityCodeMap.get("MOB_SALES_FALLOUT_CD");
		List<String> codeIdList = new ArrayList<String>();
		
		String group = null;
		
		for (CodeLkupDTO code : support) {
			if (code.getCodeId().equalsIgnoreCase(salesUserDto.getChannelCd())) {
				group = "SUPPORT";
				break;
			}
		}
		
		if (group == null) {
			for (CodeLkupDTO code : ch) {
				if (code.getCodeId().equalsIgnoreCase(salesUserDto.getChannelCd())) {
					group = "CH";
					break;
				}
			}
		}
		
		for (CodeLkupDTO code : sales) {
			codeIdList.add(code.getCodeId());
		}
		
		if (!(ui.getSearchOrderId().isEmpty() && ui.getSearchStartDate().isEmpty() 
				&& ui.getSearchEndDate().isEmpty() && ui.getSearchOrderSts().equalsIgnoreCase("ALL")
				&& ui.getSearchMrt().isEmpty())) {
			
			orderList = mobCcsOrderSearchService.findOrderEnquiry(ui.getSearchStartDate(), ui.getSearchEndDate(), 
					ui.getSearchOrderId(), ui.getSearchOrderSts(), ui.getVariousDateType(), ui.getSearchChannel(), 
					ui.getSearchStaffId(), ui.getSearchOrderType(), salesUserDto.getCategory(), 
					salesUserDto.getAreaCd(), salesUserDto.getShopCd(), group, ui.getSearchMrt());
		}
				
		if (orderList != null) {
			for (OrderDTO order : orderList) {
				if (codeIdList.contains(order.getReasonCd())) {
					order.setSalesFalloutFlag(true);
				}
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
		
		MobCcsOrderSearchUI ui = new MobCcsOrderSearchUI();
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		ui.setSearchStaffId(salesUserDto.getUsername());
		ui.setVariousDateType("APP");
		ui.setSearchChannel(salesUserDto.getChannelCd());
		
		return ui;
		
	}
	
	private boolean isChannelFoundMatch(List<CodeLkupDTO> list, String searchItem) {
		
		for (CodeLkupDTO dto : list) {
			if (dto.getCodeId().equalsIgnoreCase(searchItem)) {
				return true;
			}
		}
		return false;		
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		CodeLkupDTO initValue = new CodeLkupDTO();
		initValue.setCodeId("ALL");
		initValue.setCodeDesc("----ALL----");
		
		List<CodeLkupDTO> orderStatus = new ArrayList<CodeLkupDTO>(); 
		orderStatus.add(0, initValue);
		
//		List<CodeLkupDTO> channel = new ArrayList<CodeLkupDTO>();
		
		List<CodeLkupDTO> orderType = new ArrayList<CodeLkupDTO>();
		orderType.add(0, initValue);

		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		
		//we take value of source parameter to shorten our code description searching process
		
		for (CodeLkupDTO dto : entityCodeMap.get("ORD_STATUS")) {
			if (!"01".equalsIgnoreCase(dto.getCodeId()) && !"99".equalsIgnoreCase(dto.getCodeId())) {
				orderStatus.add(dto);
			}
		}
		
		for (CodeLkupDTO dto : entityCodeMap.get("ORD_CHECK_POINT")) {
			if(!"999".equals(dto.getCodeId())){//skip check+point =999
				orderStatus.add(dto);
			}
		}
		
		for (CodeLkupDTO dto : entityCodeMap.get("ORD_FALLOUT_CODE")) {
			orderStatus.add(dto);
		}
				
		for (CodeLkupDTO dto : entityCodeMap.get("ORD_CCS_TYPE")) {
			orderType.add(dto);
		}	
		//add COS order type
		if(CollectionUtils.isNotEmpty(entityCodeMap.get("ORD_COS_TYPE"))){
			for (CodeLkupDTO dto : entityCodeMap.get("ORD_COS_TYPE")) {
				orderType.add(dto);
			}
		}
		
		// add BRM order type (Michael 10.31.2016)
		CodeLkupDTO brmDTO = new CodeLkupDTO();
		brmDTO.setCodeId("BRM");
		brmDTO.setCodeDesc("BRM-ORDER");
		orderType.add(brmDTO);
		// add TOO order type (Michael 10.31.2016)
		CodeLkupDTO tooDTO = new CodeLkupDTO();
		tooDTO.setCodeId("TOO");
		tooDTO.setCodeDesc("TOO-ORDER");
		orderType.add(tooDTO);
		
		
		
		
		List<CodeLkupDTO> adminChannel = new ArrayList<CodeLkupDTO>();
		List<CodeLkupDTO> supportChannel = new ArrayList<CodeLkupDTO>();
		List<CodeLkupDTO> channel = new ArrayList<CodeLkupDTO>();
		supportChannel.add(initValue);
		adminChannel.add(initValue);
		
		for (CodeLkupDTO dto : entityCodeMap.get("CCS_CH")) {
			adminChannel.add(dto);
		}
		
		for (CodeLkupDTO dto : entityCodeMap.get("CCS_SUPPORT")) {
			supportChannel.add(dto);//remove support channel , add by wilson 20120528
		}
		
		if (isChannelFoundMatch(supportChannel, salesUserDto.getChannelCd())) {
			//channel.addAll(supportChannel);//remove support channel , add by wilson 20120528
			channel.addAll(adminChannel);
		}
		
		Map referenceData = new HashMap<String, List<String>>();
		referenceData.put("orderStatus", orderStatus);
		referenceData.put("channel", channel);
		referenceData.put("orderType", orderType);
		
		return referenceData;
	}	
	
}
