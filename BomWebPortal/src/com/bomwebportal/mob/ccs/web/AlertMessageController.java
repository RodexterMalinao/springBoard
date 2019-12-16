package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.service.MobCcsOrderSearchService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class AlertMessageController extends SimpleFormController {
	
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
	
protected Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		Object ui = new Object();
		
		return ui;
		
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		
		List<OrderDTO> orderList = null;
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		List<CodeLkupDTO> sales = entityCodeMap.get("MOB_SALES_FALLOUT_CD");
		List<String> codeIdList = new ArrayList<String>();
		
		for (CodeLkupDTO code : sales) {
			codeIdList.add(code.getCodeId());
		}
		
		orderList = mobCcsOrderSearchService.findOrderEnquiry(null, null, 
				"ALL", "ALERT", null, salesUserDto.getChannelCd(), 
				salesUserDto.getUsername(), "ALL", salesUserDto.getCategory(), 
				salesUserDto.getAreaCd(), salesUserDto.getShopCd(), null, null);
		if (orderList != null) {
			for (OrderDTO order : orderList) {
				if (codeIdList.contains(order.getReasonCd())) {
					order.setSalesFalloutFlag(true);
				}
			}
		}
		
		Map referenceData = new HashMap<String, List<String>>();
		
		referenceData.put("orderList", orderList);
		
		return referenceData;
	}
}
