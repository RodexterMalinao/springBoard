package com.bomwebportal.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

public class OrderSummaryController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	/**ims steven added 20140620**/
	private String retentionRecallUrl;
	private String retentionViewUrl;
	private String terminationRecallUrl;
	private String terminationViewUrl;
	private ImsOrderAmendService imsOrderAmendservice;
	private String ntvDomain;
	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}
	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}
	public String getRetentionRecallUrl() {
		return retentionRecallUrl;
	}
	public void setRetentionRecallUrl(String retentionRecallUrl) {
		this.retentionRecallUrl = retentionRecallUrl;
	}
	public String getRetentionViewUrl() {
		return retentionViewUrl;
	}
	public void setRetentionViewUrl(String retentionViewUrl) {
		this.retentionViewUrl = retentionViewUrl;
	}
	public String getTerminationRecallUrl() {
		return terminationRecallUrl;
	}
	public void setTerminationRecallUrl(String terminationRecallUrl) {
		this.terminationRecallUrl = terminationRecallUrl;
	}
	public String getTerminationViewUrl() {
		return terminationViewUrl;
	}
	public void setTerminationViewUrl(String terminationViewUrl) {
		this.terminationViewUrl = terminationViewUrl;
	}
	/**ims steven added 20140620**/

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
//		logger.info("OrderSummaryController is called");

		List<String> log = new ArrayList<String>();
		log.add("retentionRecallUrl:"+retentionRecallUrl);
		log.add("retentionViewUrl:"+retentionViewUrl);
		log.add("terminationRecallUrl:"+terminationRecallUrl);
		log.add("terminationViewUrl:"+terminationViewUrl);
		logger.info(new Gson().toJson(log));
//		logger.info("retentionRecallUrl:"+retentionRecallUrl);
//		logger.info("retentionViewUrl:"+retentionViewUrl);
//		logger.info("terminationRecallUrl:"+terminationRecallUrl);
//		logger.info("terminationViewUrl:"+terminationViewUrl);
		request.getSession().setAttribute("IMSretentionRecallUrl", retentionRecallUrl);
		request.getSession().setAttribute("IMSretentionViewUrl", retentionViewUrl);
		request.getSession().setAttribute("IMSterminationRecallUrl", terminationRecallUrl);
		request.getSession().setAttribute("IMSterminationViewUrl", terminationViewUrl);
		
		// String selectDate=request.getParameter("dateStr");
		String orderIdStr = request.getParameter("orderIdStr");
		// new parms
		String lob = request.getParameter("lob");
		String orderStatus = request.getParameter("orderStatus");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		String staffId = request.getParameter("staffId");
		
		String selectedShopCode = request.getParameter("selectedShopCode");
		String serviceNum = StringUtils.trimToNull(request.getParameter("serviceNum"));
		String dmsInd = request.getParameter("dmsInd");
		String bomStartDate = request.getParameter("bomStartDate");
		String bomEndDate = request.getParameter("bomEndDate");
		
		String orderType = request.getParameter("orderType");
		request.setAttribute("ntvdomain", this.getNtvDomain());			// ims nowtvsales celia 20150918
		
		ModelAndView myView = new ModelAndView("ordersummary");

		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession()
				.getAttribute("bomsalesuser");

		//String shopCode = salesUserDto.getShopCd();// original
		String shopCode = salesUserDto.getBomShopCd(); // modified by nancy: Premier
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
		String action = request.getParameter("action");
		//System.out.println("xxxxxxxxxxxxxxxxxx action=" + action);
		//if (StringUtils.isNotBlank(lob)) {
		if ("search".equals(action)) {
			List<OrderDTO> orderList = new ArrayList<OrderDTO>();

			if (orderIdStr == null) {
				orderIdStr = "";
			}			
				
			orderList = orderService.getOrderList(shopCode, lob, orderStatus, startDate, endDate, staffId, orderIdStr.trim(),
					serviceNum, dmsInd, bomStartDate, bomEndDate, orderType);
			PagedListHolder pl = new PagedListHolder(orderList);
			pl.setPageSize(10);
			request.getSession().setAttribute("orderList", pl);
			
			myView.addObject("orderList", pl);
			
			setLtsAllowAmendOrder(orderList);

			List<String> roleCodeList = imsOrderAmendservice.getRoleCodeByUserIDLkupCode(salesUserDto.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_WRITE, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION);
			List<String> channelCodeList = imsOrderAmendservice.getChannelCodeListByChannelID(salesUserDto.getChannelId());
			setImsAllowAmendOrder(orderList, roleCodeList, salesUserDto, channelCodeList);
			
		} else if ("next".equals(action)) {
			PagedListHolder pl = (PagedListHolder)request.getSession().getAttribute("orderList");
			if (!pl.isLastPage()) {
				pl.nextPage();
			}
			myView.addObject("orderList", pl);
		} else if ("prev".equals(action)) {
			PagedListHolder pl = (PagedListHolder)request.getSession().getAttribute("orderList");
			if (!pl.isFirstPage()) {
				pl.previousPage();
			}
			myView.addObject("orderList", pl);
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
		myView.addObject("salesUserDto", salesUserDto);
		myView.addObject("serviceNum", serviceNum);
		myView.addObject("dmsInd", dmsInd);
		myView.addObject("bomStartDate", bomStartDate);
		myView.addObject("bomEndDate", bomEndDate);
		myView.addObject("orderType",orderType);
//		logger.info("OrderSummaryController.handleRequest() End");

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
	}
	
	public void setImsAllowAmendOrder(List<OrderDTO> orderList, List<String> roleCodeList, BomSalesUserDTO userDto, List<String> channelCodeListOfChannelId) {
		if (orderList == null || orderList.isEmpty()) {
			return;
		}
		
		List<String> orderIds = new ArrayList<String>();
		

		for (OrderDTO order : orderList) {
			if (!StringUtils.equals("IMS", order.getOrderSumLob())) {
				continue;
			}else{
				orderIds.add(order.getOrderId());
			}
		}
		
		if (orderIds.isEmpty()) {
			return;
		}
		
		List<ImsAlertMsgDTO> imsOrderList = imsOrderAmendservice.getImsAlertMsgList(orderIds);

		Boolean needChannelID = false;
		Boolean needChannelCD = false;
		Boolean needTeam = false;
		Boolean needSales = false;
		
		for(String code : roleCodeList){
			if(code.equalsIgnoreCase("SALES_CD")){
				needSales=true;
			}
			if(code.equalsIgnoreCase("TEAM_CD")){
				needTeam=true;
			}
			if(code.equalsIgnoreCase("CHANNEL_CD")){
				needChannelCD=true;
			}
			if(code.equalsIgnoreCase("CHANNEL_ID")){
				needChannelID=true;
			}
		}
		

		for (OrderDTO order : orderList) {
			if (!StringUtils.equals("IMS", order.getOrderSumLob())) {				
				continue;
			}
			if("IMS".equalsIgnoreCase(order.getOrderSumLob())){
				order.setAllowAmendOrder(false);
			}
			for(ImsAlertMsgDTO dto:imsOrderList){
				if(order.getOrderId().equals(dto.getOrderId())){					
					//open for NOWTVSALES 20150909
//					if(!imsOrderAmendservice.isOpenRetailAmend()){
//						order.setAllowAmendOrder(false);
//						continue;
//					}
//					logger.info("user : Channel:"+userDto.getChannelCd()+
//							" \tteam:"+userDto.getShopCd()+
//							" \tid:"+userDto.getUsername()+
//							" \tid2:"+userDto.getUsername());
//					
//					logger.info("order: Channel:"+dto.getSalesChannel()+
//							" \tTeam:"+dto.getSalesTeam()+
//							" \tid:"+dto.getSalesCd()+
//							" \tid2:"+dto.getCreateBy()+
//							" \torder_id:"+dto.getOrderId());
					
					if(dto.getSalesChannel()==null){
						dto.setSalesChannel("");
					}
					if(dto.getSalesCd()==null){
						dto.setSalesCd("");
					}
					if(dto.getSalesTeam()==null){
						dto.setSalesTeam("");
					}
					if(dto.getCreateBy()==null){
						dto.setCreateBy("");
					}
					
					if(dto.getCreateBy().equalsIgnoreCase(userDto.getUsername())){
							order.setAllowAmendOrder(true);
//							logger.info("Recall / amend, case 5 createBy");
							continue;
					}
					if(needSales){
						if(dto.getSalesCd().equalsIgnoreCase(userDto.getUsername())){
							order.setAllowAmendOrder(true);
//							logger.info("Recall / amend, case 4 salesCd");
							continue;
						}
					}
					if(needTeam){
						if(dto.getSalesTeam().equalsIgnoreCase(userDto.getShopCd())){
							order.setAllowAmendOrder(true);
//							logger.info("Recall / amend, case 3 salesTeam");
							continue;
						}
					}
					if(needChannelCD){
						if(dto.getSalesChannel().equalsIgnoreCase(userDto.getChannelCd())){
							order.setAllowAmendOrder(true);
//							logger.info("Recall / amend, case 2 salesChannel");
							continue;
						}
					}
					if(needChannelID){
						for(String channelCode : channelCodeListOfChannelId){
							if(dto.getSalesChannel().equalsIgnoreCase(channelCode)){
								order.setAllowAmendOrder(true);
//								logger.info("Recall / amend, case 1 salesChannel id");
								continue;
							}
						}
					}

//					logger.info("cannot recall/amend");
					logger.info("user : Channel:"+userDto.getChannelCd()+
							" \tteam:"+userDto.getShopCd()+
							" \tid:"+userDto.getUsername()+
							" \tid2:"+userDto.getUsername());

					logger.info("order: Channel:"+dto.getSalesChannel()+
							" \tTeam:"+dto.getSalesTeam()+
							" \tid:"+dto.getSalesCd()+
							" \tid2:"+dto.getCreateBy()+
							" \torder_id:"+dto.getOrderId());
					order.setAllowAmendOrder(false);
				}
			}
		}
	}
	public void setNtvDomain(String ntvDomain) {
		this.ntvDomain = ntvDomain;
	}
	public String getNtvDomain() {
		return ntvDomain;
	}
	
}
