package com.bomwebportal.ims.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bomwebportal.ims.dto.ImsCustomerOrderHistoryDTO;
import com.bomwebportal.ims.service.ImsOLOrderSearchService;
import com.bomwebportal.sbo.dto.form.SboOrderSearchForm;

public class SboImsOrderSearchController extends AbstractController{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsOLOrderSearchService imsOlOrderSearchService;
	
	public ImsOLOrderSearchService getImsOlOrderSearchService() {
		return imsOlOrderSearchService;
	}

	public void setImsOlOrderSearchService(
			ImsOLOrderSearchService imsOlOrderSearchService) {
		this.imsOlOrderSearchService = imsOlOrderSearchService;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Expires", "0");
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		
		SboOrderSearchForm orderSearch = (SboOrderSearchForm)req.getSession().getAttribute("sboordersearch");
		String app = req.getParameter("app");
		List<ImsCustomerOrderHistoryDTO> custOrderHistoryList = null;
		ModelAndView mav = new ModelAndView("sboimsordersearch");
		
		if(orderSearch == null)
			return mav;
		
		logger.info("orderSearch.getIdDocNum(): " + orderSearch.getIdDocNum());
		logger.info("orderSearch.getServiceNumber(): " + orderSearch.getServiceNumber());
		logger.info("orderSearch.getIdDocType(): " + orderSearch.getIdDocType());
		logger.info("orderSearch.getLoginIdPrefix(): " + orderSearch.getLoginIdPrefix());
		logger.info("orderSearch.getServiceNumberType(): " + orderSearch.getServiceNumberType());
		logger.info("orderSearch.getContactEmail(): " + orderSearch.getContactEmail());
		logger.info("orderSearch.getOrderId()" + orderSearch.getOrderId());
		logger.info("orderSearch.getContactNum(): " + orderSearch.getContactNum());

		
		custOrderHistoryList = imsOlOrderSearchService.getCustomerOrderHistoryDTOALL(
						orderSearch.getIdDocNum(), orderSearch.getServiceNumber(),
						orderSearch.getIdDocType(), orderSearch.getLoginIdPrefix(),
						orderSearch.getServiceNumberType(), orderSearch.getContactEmail(),
						orderSearch.getOrderId(), (app.equals("PCD") ? null : "TV_I"),
						orderSearch.getContactNum());

		
		logger.info("Record found: " + custOrderHistoryList.size());
		
	
		
		if (custOrderHistoryList.size() > 0) {
			mav.addObject("sboImsOrderList", custOrderHistoryList);
		}
		
		

		return mav;
	}

}
