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
import com.bomwebportal.ims.service.ImsOrderDetailService;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.web.CustomerInformationController.CustomerPremierInfoDTO;
import com.bomwebportal.wsclient.CustProfileClient;
import com.bomwebportal.wsclient.CustomerSearchClient;

public class ImsOrderPreviewController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());
	

	private String retentionRecallUrl;
	private String retentionViewUrl;
	private String terminationRecallUrl;
	private String terminationViewUrl;

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

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String orderId = (String) request.getParameter("orderId");
		String locale = (String) request.getParameter("locale");
		String recall = (String) request.getParameter("recall");
		ModelAndView myview = new ModelAndView();
		
		logger.info("orderId:"+orderId+"  locale:"+locale+" recall:"+recall+ " orderId.substring(4, 6):"+orderId.substring(4, 6));
		
		
		String nextview = "";
		
		if(orderId!=null && !orderId.equals("")){
			if("PR".equals(orderId.substring(4, 6))||"PU".equals(orderId.substring(4, 6))){
				if("Y".equalsIgnoreCase(recall)){
					nextview = retentionRecallUrl+"="+orderId;
				}else{
					nextview = retentionViewUrl+"="+orderId;
				}
			}else if("T".equals(orderId.substring(5, 6))){
				if("Y".equalsIgnoreCase(recall)){
					nextview = terminationRecallUrl+"="+orderId;
				}else{
					nextview = terminationViewUrl+"="+orderId;
				}
			}else if(orderId.indexOf("OSBO")!=-1){
					nextview = "imsolenquiryerror.html?orderId="+orderId;
			}else{
				if("Y".equalsIgnoreCase(recall)){
					nextview = "imsorderdetail.html?orderId="+orderId+"&status=abc";//recall, need order status?
				}else{
					nextview = "imsorderdetail.html?orderId="+orderId;
				}
			}
		}
		logger.info("nextview:"+nextview);
		myview.setView(new RedirectView(nextview));
		return myview;
	}
	




}

