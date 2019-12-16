package com.bomwebportal.ims.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.Utility;

/* ims steven created this file*/
/*this file is no longer needed*/
public class ImsOrderAmendHistoryController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
/*
	protected final Log logger = LogFactory.getLog(getClass());

	private ServiceProfileLtsService serviceProfileLtsService;

	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("ImsOrderAmendHistoryController is called");

		// String selectDate=request.getParameter("dateStr");
		// add by Joyce 20111025
		String orderIdStr = request.getParameter("orderIdStr");
		// new parms
		String lob = request.getParameter("lob");
		String orderStatus = request.getParameter("orderStatus");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		String staffId = request.getParameter("staffId");
		
		String selectedShopCode = request.getParameter("selectedShopCode");
		
		ModelAndView myView = new ModelAndView("imsorderamendhistory");

		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession()
				.getAttribute("bomsalesuser");

		String shopCode = salesUserDto.getShopCd();// original
		if (2 == salesUserDto.getChannelId()) {// chennelId=2, mobccs
			shopCode = salesUserDto.getChannelCd();// convert shopCode to		// ChannelCd
		}
		
		String allowSearhAllInd="";
		if("TTW".equals(shopCode) || "ALL".equals(shopCode) ){
			if ( StringUtils.isNotBlank(selectedShopCode)){
				shopCode=selectedShopCode;//only allow TTW shop sales change shop code
			}
			
			allowSearhAllInd=orderService.getAllowSearchAllInd(salesUserDto.getUsername());
			
		}
	
	
		
		
		
		if (StringUtils.isBlank(startDate)) {
			Date date = new Date();
			startDate = Utility.date2String(date, "dd/MM/yyyy");
			if (StringUtils.isBlank(endDate)) {
				endDate = Utility.date2String(date, "dd/MM/yyyy");
			}
		}

		if (StringUtils.isNotBlank(lob)) {
			List<OrderDTO> orderList = new ArrayList<OrderDTO>();

			if (orderIdStr == null) {
				orderIdStr = "";
			}

//			orderList = orderService.getOrderList(shopCode, lob, orderStatus, startDate, endDate, staffId, orderIdStr.trim());
			myView.addObject("orderList", orderList);
			
			setLtsAllowAmendOrder(orderList);
		}

		myView.addObject("shopCode", shopCode);

		myView.addObject("lob", lob);
		myView.addObject("orderStatus", orderStatus);
		myView.addObject("startDate", startDate);
		myView.addObject("endDate", endDate);
		myView.addObject("staffId", staffId);

		myView.addObject("orderIdStr", orderIdStr);
		myView.addObject("selectedShopCode", selectedShopCode);
		myView.addObject("allowSearhAllInd", allowSearhAllInd);
		myView.addObject("salesUserDto", salesUserDto);//add by wilson 20120928, BUILD3.040

		logger.info("ImsOrderAmendHistoryController handleRequest() End");

		return myView;
	}

	// Added by Jayson 2012.2.14 VD
	public void setLtsAllowAmendOrder(List<OrderDTO> orderList) {
		if (orderList == null || orderList.isEmpty()) {
			return;
		}

		for (OrderDTO order : orderList) {
			if (!StringUtils.equals("LTS", order.getOrderSumLob())
					|| StringUtils.isEmpty(order.getOcid())) {
				continue;
			}

			PendingOrdStatusDetailDTO pendingOrdStatusDetail = serviceProfileLtsService
					.getPendingOrder(order.getOrderSumServiceNum(), LtsConstant.SERVICE_TYPE_TEL);

			if (pendingOrdStatusDetail == null) {
				order.setAllowAmendOrder(false);
				continue;
			}

			if (!StringUtils.equals(pendingOrdStatusDetail.getOcid(),
					order.getOcid())) {
				continue;
			}

			if (StringUtils.equals(LtsConstant.DRG_ORDER_STATUS_CANCELLED,
					pendingOrdStatusDetail.getLegacyStatus())
					|| StringUtils.equals(
							LtsConstant.DRG_ORDER_STATUS_COMPLETED,
							pendingOrdStatusDetail.getLegacyStatus())
					|| StringUtils.equals(
							LtsConstant.DRG_ORDER_STATUS_NUMBER_INVESTIGATE,
							pendingOrdStatusDetail.getLegacyStatus())
					|| StringUtils.equals(
							LtsConstant.BOM_ORDER_STATUS_CANCELLED_W_ORDER,
							pendingOrdStatusDetail.getBomStatus())
					|| StringUtils.equals(
							LtsConstant.BOM_ORDER_STATUS_CANCELLED_WO_ORDER,
							pendingOrdStatusDetail.getBomStatus())
					|| StringUtils.equals(
							LtsConstant.BOM_ORDER_STATUS_COMPLETED,
							pendingOrdStatusDetail.getBomStatus())) {
				order.setAllowAmendOrder(false);
			}
		}
	}*/

}
