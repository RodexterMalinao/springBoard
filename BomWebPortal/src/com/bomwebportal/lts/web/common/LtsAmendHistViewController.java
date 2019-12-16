package com.bomwebportal.lts.web.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.order.AmendOrderRecDTO;
import com.bomwebportal.lts.service.order.AmendRetrieveService;
import com.pccw.util.db.OracleSelectHelper;

public class LtsAmendHistViewController extends SimpleFormController {
	
	private final String viewName = "lts/common/ltsamendhistview";
	
	private AmendRetrieveService amendRetrieveService;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String orderId = (String) request.getParameter("orderId");

		ModelAndView mav = new ModelAndView(viewName);
		
		if(StringUtils.isNotBlank(orderId)){
			
			AmendOrderRecDTO[] amendHists = amendRetrieveService.retrieveWQAmendment(orderId, bomSalesUserDTO.getUsername());
			String[] amendNatureIds = OracleSelectHelper.getSqlFirstColumnStrings("BomWebPortalDS", 
					"SELECT wq_nature_id FROM Q_WQ_NATURE WHERE wq_nature_type like 'AMEND%'");
			
			List<AmendOrderRecDTO> amendHistList = new ArrayList<AmendOrderRecDTO>(); 
			if(amendHists != null){
				for(AmendOrderRecDTO amend: amendHists){
					if(ArrayUtils.contains(amendNatureIds, amend.getNatureId())){
						amendHistList.add(amend);
					}
				}
			}
			
			mav.addObject("amendHistList", amendHistList);
			
		}

		return mav;
	}

	public AmendRetrieveService getAmendRetrieveService() {
		return amendRetrieveService;
	}

	public void setAmendRetrieveService(AmendRetrieveService amendRetrieveService) {
		this.amendRetrieveService = amendRetrieveService;
	}
}
