package com.bomwebportal.lts.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.ims.service.ImsSignOffLogService;
import com.bomwebportal.lts.dao.OrderDocumentDAO;
import com.bomwebportal.lts.dto.form.OrderAmendmentFormDTO;
import com.bomwebportal.lts.dto.order.AmendOrderRecDTO;
import com.bomwebportal.lts.service.order.AmendRetrieveService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;

public class OrderAmendController extends SimpleFormController {
	
	private AmendRetrieveService amendRetrieveService;
	private OrderDocumentDAO orderDocumentDao;
		
	protected final Log logger = LogFactory.getLog(getClass());
	
//	ims steven added for ims 20150317
	private ImsOrderAmendService imsOrderAmendservice;
	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}
	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}
//	ims steven added for ims 20150317
	private ImsSignOffLogService signOffLogService;
	public void setSignOffLogService(ImsSignOffLogService signOffLogService) {
		this.signOffLogService = signOffLogService;
	}
	public ImsSignOffLogService getSignOffLogService() {
		return signOffLogService;
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException{

		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
//		ims steven added for iframe 20131121
		request.getSession().setAttribute("ImsAmendJustDone", "N");
		if(orderId.subSequence(4, 5).equals("P") || orderId.subSequence(4, 5).equals("V")){
			request.getSession().setAttribute("ImsFlow", "Y");
			if(imsOrderAmendservice.isOrderStatusOkayForAmend(orderId)){
				request.getSession().setAttribute("ImsShowAmendButton", "Y");
			}else{
				request.getSession().setAttribute("ImsShowAmendButton", "N");
			}
			
			if(orderId.subSequence(4, 5).equals("P")){
				OrderImsUI order = new OrderImsUI();
				order.setOrderId(orderId);
				if(orderId.subSequence(0, 1).equals("D")){
					order.setImsOrderType("DS");
				}else if(orderId.subSequence(0, 1).equals("P")){
					order.setIsPT("Y");
				}else if(orderId.subSequence(0, 1).equals("C")){
					order.setIsCC("Y");
				}
				order.setCreateBy(bomSalesUserDTO.getUsername());
				signOffLogService.signOffOrderLog(order, "AmendOrder", null);
			}
			
		}else{
			request.getSession().setAttribute("ImsFlow", "N");
		}
//		ims steven added for iframe 20131121 end
			
		
		OrderAmendmentFormDTO orderAmendDTO = new OrderAmendmentFormDTO();
		orderAmendDTO.setSbOrderNum(orderId);
		AmendOrderRecDTO[] amendHist = amendRetrieveService.retrieveWQAmendment(orderId,bomSalesUserDTO.getUsername());
		orderAmendDTO.setAmendHistory(amendHist);
		orderAmendDTO.setOutstandingWqCount(countOutstandingWq(amendHist));
		try{
			orderAmendDTO.setAmendFormList(orderDocumentDao.getAllOrdDocListByDocTypeOrderId(orderId, LtsBackendConstant.ORDER_DOC_TYPE_ORDER_AMENDMENT_FORM));
		}catch(Exception e){
			//???
		}
		return orderAmendDTO;
	}
	
	public AmendRetrieveService getAmendRetrieveService() {
		return this.amendRetrieveService;
	}

	public void setAmendRetrieveService(AmendRetrieveService pAmendRetrieveService) {
		this.amendRetrieveService = pAmendRetrieveService;
	}	
	
	public OrderDocumentDAO getOrderDocumentDao() {
		return orderDocumentDao;
	}

	public void setOrderDocumentDao(OrderDocumentDAO orderDocumentDao) {
		this.orderDocumentDao = orderDocumentDao;
	}
	
	private int countOutstandingWq(AmendOrderRecDTO[] pAmendHist){
		int count=0;
		for (AmendOrderRecDTO eachRec : pAmendHist){
			if (eachRec.isEnableStatusChg()){
				count++;
			}
		}
		return count;
	}
}
