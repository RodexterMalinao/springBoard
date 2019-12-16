package com.bomwebportal.sbo.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bomwebportal.sbo.dto.SboMobOrderDTO;
import com.bomwebportal.sbo.dto.form.SboOrderSearchForm;
import com.bomwebportal.sbo.service.SboOrderService;

public class SboMobOrderSearchController extends AbstractController {
	protected final Log logger = LogFactory.getLog(getClass());

	
	private SboOrderService sboOrderService;
	
	
	public SboOrderService getSboOrderService() {
		return sboOrderService;
	}

	public void setSboOrderService(SboOrderService sboOrderService) {
		this.sboOrderService = sboOrderService;
	}
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		res.setHeader("Pragma", "no-cache");
		res.setHeader("Expires", "0");
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		
		ModelAndView mav = new ModelAndView("sbomobordersearch");

		SboOrderSearchForm orderSearch = (SboOrderSearchForm)req.getSession().getAttribute("sboordersearch");
		
		

		List<SboMobOrderDTO> list = null;
		/*
		if (orderSearch != null) {
			
			list = sboOrderService.findSboMobOrder(
					orderSearch.getOrderId(),
					orderSearch.getIdDocType(),
					orderSearch.getIdDocNum(),
					orderSearch.getServiceNumber(),
					orderSearch.getContactEmail());

			
			PagedListHolder pl = new PagedListHolder(list);
			pl.setPageSize(10);
			req.getSession().setAttribute("SboMobOrderSearch.result", pl);
			if (list.size() > 0) {
				mav.addObject("sboMobOrderList", pl);
			}
		} else {
			PagedListHolder pl = (PagedListHolder)req.getSession().getAttribute("SboMobOrderSearch.result");
			if (pl != null) {
				mav.addObject("sboMobOrderList", pl);

			}
		}
		return mav;
		*/
		
		if (orderSearch != null && orderSearch.isMobSearch()) {
			list = sboOrderService.findSboMobOrder(
					orderSearch.getOrderId(),
					orderSearch.getIdDocType(),
					orderSearch.getIdDocNum(),
					orderSearch.getServiceNumber(),
					orderSearch.getContactEmail(),
					orderSearch.getContactNum());
			
			if (list.size() > 0) {
				mav.addObject("sboMobOrderList", list);
			}
		}
		return mav;
	}

}
