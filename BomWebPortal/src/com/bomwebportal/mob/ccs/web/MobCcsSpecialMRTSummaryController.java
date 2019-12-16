package com.bomwebportal.mob.ccs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

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

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsSpecialMRTSummaryUI;
import com.bomwebportal.mob.ccs.service.MobCcsSpecialMRTSummaryService;



public class MobCcsSpecialMRTSummaryController extends SimpleFormController{
	
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	MobCcsSpecialMRTSummaryService mobCcsSpecialMRTSummaryService;
	
	public MobCcsSpecialMRTSummaryService getMobCcsSpecialMRTSummaryService() {
		return mobCcsSpecialMRTSummaryService;
	}

	public void setMobCcsSpecialMRTSummaryService(
			MobCcsSpecialMRTSummaryService mobCcsSpecialMRTSummaryService) {
		this.mobCcsSpecialMRTSummaryService = mobCcsSpecialMRTSummaryService;
	}

	protected Object formBackingObject(HttpServletRequest request) throws ServletException{
			
		MobCcsSpecialMRTSummaryUI ui = new MobCcsSpecialMRTSummaryUI();
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		request.setAttribute("user", salesUserDto);
		request.setAttribute("channelCd", salesUserDto.getChannelCd());
		/*System.out.println(salesUserDto.getChannelCd());*/
		return ui;
	}
	
	protected Map referenceData(HttpServletRequest request) {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		List<MaintParmLkupDTO> resultStatusTypes = mobCcsSpecialMRTSummaryService.getResultStatusTypes(salesUserDto.getChannelCd());
		
		//List<String> resultStatusTypes = Arrays.asList("REQUEST", "APPROVED", "REJECTED", "PROCEED", "CANCELLED", "EXPIRED", "ATTACHED", "SUSPENDED");
		
		referenceData.put("resultStatusTypes", resultStatusTypes);
		
		
		List<MaintParmLkupDTO> channelTypes = mobCcsSpecialMRTSummaryService.getChannelTypes();
		
		//List<String> channelTypes = Arrays.asList("IBS","OBS");
		
		referenceData.put("channelTypes", channelTypes);
		
		
		
		return referenceData;
		
	}
	
	
	protected ModelAndView onSubmit(HttpServletRequest request,	HttpServletResponse response, Object command,	BindException errors) throws Exception {
		
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		MobCcsSpecialMRTSummaryUI ui = (MobCcsSpecialMRTSummaryUI) command;
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		request.setAttribute("channelCd", salesUserDto.getChannelCd());
		
		if (ui.getChannel() == null || "ALL".equals(ui.getChannel())) {
			ui.setChannel("");			
		}
		
		if (ui.getMobNum()==null) {
			ui.setMobNum("");			
		}
		
		if (ui.getRequestDateFrom() == null) {
			ui.setRequestDateFrom("");			
		}
		
		if (ui.getRequestDateTo() == null) {
			ui.setRequestDateTo("");
		}
		
		if (ui.getRequestedBy() == null) {
			ui.setRequestedBy("");
		}
		
		if (ui.getResultStatus() == null || "ALL".equals( ui.getResultStatus())) {
			ui.setResultStatus("");
		}
		
		
		
		ModelAndView modelAndView = new ModelAndView("mobccsspecialmrtsummary", model);
		
		
		List <SpecialMrtRequestDTO> specialMRTRequestList = null;
		
		if (salesUserDto.getChannelCd().equals("CPA") ) {
			
			specialMRTRequestList = mobCcsSpecialMRTSummaryService.getSpecialMRTRequests(ui.getResultStatus(), ui.getRequestDateFrom(), 
					ui.getRequestDateTo(), ui.getChannel(), ui.getRequestedBy(), ui.getMobNum());
			
			
		} else {
		
			specialMRTRequestList = mobCcsSpecialMRTSummaryService.getSpecialMRTRequestsByManager(ui.getResultStatus(), ui.getRequestDateFrom()
					, ui.getRequestDateTo(), salesUserDto.getChannelCd(), salesUserDto.getShopCd());
			
			//System.out.println(salesUserDto.getChannelCd());
		
		}
		
		String index = (String) request.getParameter("index");
		PagedListHolder specialMRTRequestPagedList = null;
		int noOfPages = 1;
		
		if (StringUtils.isNotBlank(index)) {
			specialMRTRequestPagedList = (PagedListHolder) request.getSession().getAttribute("sessionSpecialMRTRequestPagedList");
			if("next".equals(index)) {
				specialMRTRequestPagedList.nextPage();
			}else if ("previous".equals(index)){
				specialMRTRequestPagedList.previousPage();
			} else {
				specialMRTRequestPagedList.setPage(Integer.parseInt(index)-1);				
			}
			
		} else {
			//will run when the page is first loaded...
			if (specialMRTRequestList != null) {
				specialMRTRequestPagedList = new PagedListHolder (specialMRTRequestList);
			}
		}
		
		
		//will run every time the form is submitted
		if (specialMRTRequestPagedList != null) {
			
			noOfPages = (int) specialMRTRequestPagedList.getPageCount();
			
			modelAndView.addObject("currentPage", specialMRTRequestPagedList.getPage() + 1);
			modelAndView.addObject("sessionSpecialMRTRequestPagedList", specialMRTRequestPagedList);
			modelAndView.addObject("noOfPages", noOfPages);
			request.getSession().setAttribute("sessionSpecialMRTRequestPagedList", specialMRTRequestPagedList);
			
		}
		
		return modelAndView;
		
	}
	

	
	
}
