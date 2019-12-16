package com.bomwebportal.lts.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.LtsOrderSearchDTO;
import com.bomwebportal.lts.service.LtsOrderSearchService;
import com.bomwebportal.sbo.dto.form.SboOrderSearchForm;

public class SboLtsOrderSearchController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private LtsOrderSearchService ltsOrderSearchService;
	
	public LtsOrderSearchService getLtsOrderSearchService() {
		return ltsOrderSearchService;
	}
	public void setLtsOrderSearchService(LtsOrderSearchService ltsOrderSearchService) {
		this.ltsOrderSearchService = ltsOrderSearchService;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		SboOrderSearchForm orderSearch = (SboOrderSearchForm)request.getSession().getAttribute("sboordersearch");
		ModelAndView mav = new ModelAndView("sboltsordersearch");

		if (orderSearch != null) {
			if (StringUtils.isBlank(orderSearch.getServiceNumberType())
					|| StringUtils.equals("TEL", orderSearch.getServiceNumberType())) {
				List<LtsOrderSearchDTO> ltsSearchResultList = searchLtsOnlineOrder(orderSearch, bomSalesUserDTO.getUsername());
				mav.addObject("ltsOrderSearchResult", ltsSearchResultList);
			}
		}
		
		return mav;
	}
	
	private List<LtsOrderSearchDTO> searchLtsOnlineOrder(
			SboOrderSearchForm searchUI, String userId) {
		return ltsOrderSearchService.searchLtsOnlineOrder(
				searchUI.getOrderId(), searchUI.getIdDocType(),
				searchUI.getIdDocNum(), StringUtils.isNotBlank(searchUI.getServiceNumber()) ? StringUtils.leftPad(searchUI.getServiceNumber(), 12, "0") : searchUI.getServiceNumber(),
				searchUI.getContactEmail(), userId);
	}
	
}
