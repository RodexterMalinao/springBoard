package com.bomwebportal.ims.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustTagDTO;
import org.openuri.www.CustomerBasicInfoDTO;
import org.openuri.www.CustomerSearchResponse;
import org.openuri.www.SearchingKeyDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.interceptor.ImsCommonInterceptor;
import com.bomwebportal.ims.service.ImsOrderDetailService;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ImsSignOffLogService;
import com.bomwebportal.web.CustomerInformationController.CustomerPremierInfoDTO;
import com.bomwebportal.wsclient.CustProfileClient;
import com.bomwebportal.wsclient.CustomerSearchClient;

public class ImsOrderDetailController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsOrderDetailService orderDetailService;
	private ImsSignOffLogService signOffLogService;

	private CustProfileClient custProfileClient;

	private String ntvDomain;
	
	public CustProfileClient getCustProfileClient() {
		return custProfileClient;
	}

	public void setCustProfileClient(CustProfileClient custProfileClient) {
		this.custProfileClient = custProfileClient;
	}
	
	private CustomerSearchClient custSearchClient;	

	public CustomerSearchClient getCustSearchClient() {
		return custSearchClient;
	}

	public void setCustSearchClient(CustomerSearchClient custSearchClient) {
		this.custSearchClient = custSearchClient;
	}
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String orderId = (String) request.getParameter("orderId");
		String orderStatus = (String) request.getParameter("status");
		String imsOrderEnquiry = (String) request.getParameter("imsOrderEnquiry");
		ModelAndView myview = new ModelAndView();
		String custNum = (String) request.getParameter("custNum");
		String awaitCash = (String) request.getParameter("awaitCash");
		String awaitSignOff = (String) request.getParameter("awaitSignOff");
		String reqType = (String) request.getParameter("reqType");
		String locale = "en";
		if(request.getParameter("locale")!=null && !request.getParameter("locale").isEmpty())
			locale = (String)request.getParameter("locale");
		
		logger.info("orderId:"+orderId+";order status:"+orderStatus+";imsOrderEnquiry:"+imsOrderEnquiry+";custNum:"+custNum);
		
		List<CustomerBasicInfoDTO> cust = orderDetailService.getBomWebCustomerBom(custNum);
		
		if(custNum == null && !(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)) { 
			if(cust == null){
				 cust= orderDetailService.getBomWebCustomer(custNum);//springBorad profile
			}
	
			if(cust!=null){
				request.getSession().setAttribute("custSearchResultListSession", cust);
			}else{
				request.getSession().setAttribute("custSearchResultListSession", null);
			}
		}
		
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, null);
		
		request.getSession().setAttribute("imsCustomer", null);
		request.getSession().setAttribute("imsDocType", null);
		request.getSession().setAttribute("imsIdDocNum", null);
		request.getSession().setAttribute("imsLoginName", null);
		request.getSession().setAttribute("imsOldLoginName", null);
		request.getSession().setAttribute("imsSubmitTag", null);
		request.getSession().setAttribute("imsOrderId", null);	

		request.getSession().setAttribute("selectedIMSVasList", null);
		request.getSession().setAttribute("selectedIMSChannelList", null);
		request.getSession().setAttribute("selectedIMSNowVasList", null);
		request.getSession().setAttribute("IMS_IsCouponBasket", null);
		request.getSession().setAttribute("IMS_ContractPeriod", null);
		request.getSession().setAttribute("IMS_BasketID", null);
		request.getSession().setAttribute("IMSTVType", null);
		request.getSession().setAttribute("type30F6", null);
		
		request.getSession().setAttribute("lob", "IMS");
		request.getSession().setAttribute("isImsShowChangeLang", orderDetailService.isImsShowChangeLang());
		
		String nextview = "";
		
		OrderImsUI order = null;
		
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		if(orderId!=null && !orderId.equals("")){
			order = orderDetailService.getOrder(orderId);
			
			OrderImsUI orderLogging = new OrderImsUI();
			orderLogging.setOrderId(orderId);
			orderLogging.setImsOrderType(order.getImsOrderType());
			orderLogging.setCreateBy(user.getUsername());
			
			if ("NTV-A".equals(order.getOrderType())||order.isOrderTypeNowRet()) {
				nextview = ntvDomain + "ntvorderdetail.html?_al=new&orderId=" + orderId;
			} else {
				if(StringUtils.equals(orderStatus, "06") && StringUtils.equals(awaitCash, "Y")){
					//await cash -> imsdone
					nextview = "imsdone.html?awaitCash=Y";
					order.setLastUpdBy(user.getUsername());
					order.setOrderActionInd("C");
					signOffLogService.signOffOrderLog(orderLogging, "RecallOrder(AC)", null);
				} else if(StringUtils.equals(orderStatus, "11") && StringUtils.equals(awaitSignOff, "Y")){
					//await SignOff -> imsdone
					nextview = "imsdone.html?awaitSignOff=Y";
					order.setLastUpdBy(user.getUsername());
					order.setOrderActionInd("C");
					signOffLogService.signOffOrderLog(orderLogging, "RecallOrder(AS)", null);
				} else if(StringUtils.equals(orderStatus, "02") || StringUtils.equals(orderStatus, "04")){
					//Amend -> imsdsaddressinfo
					nextview = "imsdsaddressinfo.html?language="+("en".equals(locale)?locale:"zh_HK");
					orderDetailService.updateOrderImgByLatestAmend(order); 
					order.setOrderActionInd("W");
					order.setLastUpdBy(user.getUsername());
					OrderImsUI orderClone = orderDetailService.getNewOrderTemplate(user.getShopCd());
					BeanUtils.copyProperties(order, orderClone);
					order.setOrderImg(orderClone);
					signOffLogService.signOffOrderLog(orderLogging, "AmendOrder", null);
				} else if (!StringUtils.isEmpty(orderStatus)) { 	//Recall
					nextview = "imsaddressinfo.html?language="+("en".equals(locale)?locale:"zh_HK");
					order.setOrderActionInd("W");
					order.setLastUpdBy(user.getUsername());
					signOffLogService.signOffOrderLog(orderLogging, "RecallOrder", null);
				}else {
					if (imsOrderEnquiry!=null && !imsOrderEnquiry.equals("")){
						String emailReqResult = request.getParameter("emailReqResult");
						String emailReqResultMsg = request.getParameter("emailReqResultMsg");
						String sentDate = request.getParameter("sentDate");
						
						logger.info("emailReqResult: " + emailReqResult);
						logger.info("emailReqResultMsg: " + emailReqResultMsg);
						logger.info("sentDate: " + sentDate);
						
						myview.addObject("emailReqResult", emailReqResult);
						myview.addObject("emailReqResultMsg", emailReqResultMsg);
						myview.addObject("sentDate", sentDate);
						
						nextview = "imsresendemailhistory.html";
					}else{
						nextview = "imsdone.html?dM=Y";
						if(StringUtils.equals(reqType, "QC")){
							signOffLogService.signOffOrderLog(orderLogging, "QCOrder", null);
						}else{
							signOffLogService.signOffOrderLog(orderLogging, "EnquiryOrder", null);
						}
					}
					order.setOrderActionInd("R");
				}
			}
						
		}else{					
			order = orderDetailService.getNewOrderTemplate(user.getShopCd());
			order.setCreateBy(user.getUsername());
			if ((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)) {
				order.setImsOrderType(ImsCommonInterceptor.IMS_ORDER_TYPE_DS);
				if(                         request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)!=null 
					  && !"".equals((String)request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM))){
					order.setMsisdn((String)request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM));
				}
				//20151216 ims celia ds ntv
				if(request.getParameter("salesType")!=null && StringUtils.isBlank(user.getSalesType()))
					user.setSalesType((String)request.getParameter("salesType"));
				if(request.getParameter("location")!=null && StringUtils.isBlank(user.getLocation()))
					user.setLocation((String)request.getParameter("location"));
				logger.info(user.getSalesType()+"   "+user.getLocation());
			}
			
			nextview = "imsaddressinfo.html?language="+("en".equals(locale)?locale:"zh_HK");
		}
		
		myview.setView(new RedirectView(nextview));
		
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
								
		return myview;
	}

	public ImsOrderDetailService getOrderDetailService() {
		return orderDetailService;
	}

	public void setOrderDetailService(ImsOrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}		
	
	public void setSignOffLogService(ImsSignOffLogService signOffLogService) {
		this.signOffLogService = signOffLogService;
	}
	public ImsSignOffLogService getSignOffLogService() {
		return signOffLogService;
	}

	public String getNtvDomain() {
		return ntvDomain;
	}

	public void setNtvDomain(String ntvDomain) {
		this.ntvDomain = ntvDomain;
	}

	private List<CustomerBasicInfoDTO> getCustomerBasicInfoDTOList(CustomerBasicInfoDTO[] customerBasicInfoDTOs) {
		if (customerBasicInfoDTOs == null || customerBasicInfoDTOs.length == 0) {
			return Collections.emptyList();
		}
		
		List<CustomerBasicInfoDTO> customerBasicInfoDTOList = new ArrayList<CustomerBasicInfoDTO>();
		for (CustomerBasicInfoDTO customerBasicInfoDTO : customerBasicInfoDTOs) {
			CustomerPremierInfoDTO customerPremierInfoDTO = new CustomerPremierInfoDTO();
			BeanUtils.copyProperties(customerBasicInfoDTO, customerPremierInfoDTO);
			try {
				// call remote serivce
				CustTagDTO custTagDTO = null; 
				if (StringUtils.isNotBlank(customerBasicInfoDTO.getIdDocNum())) {
					CustProfileClient.SystemId systemId = CustProfileClient.SystemId.valueOf(customerBasicInfoDTO.getSystemId());
					CustProfileClient.IdDocType idDocType = CustProfileClient.IdDocType.valueOf(customerBasicInfoDTO.getIdDocType());
					custTagDTO = this.custProfileClient.getCustomerTagByIdDoc(systemId, idDocType, customerBasicInfoDTO.getIdDocNum());
				}
				customerPremierInfoDTO.setCustTagDTO(custTagDTO);
			} catch (Exception e) {
				logger.warn("Exception in call remote service", e);
			}
			customerBasicInfoDTOList.add(customerPremierInfoDTO);
		}
		return customerBasicInfoDTOList;
	}
	

	
	public class CustomerPremierInfoDTO extends CustomerBasicInfoDTO {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private CustTagDTO custTagDTO;

		public CustTagDTO getCustTagDTO() {
			return custTagDTO;
		}

		public void setCustTagDTO(CustTagDTO custTagDTO) {
			this.custTagDTO = custTagDTO;
		}
	}
}
