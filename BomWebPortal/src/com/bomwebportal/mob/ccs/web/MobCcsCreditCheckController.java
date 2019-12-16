package com.bomwebportal.mob.ccs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.wsclient.CustCreditCheckClient;

import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryResultDetailsV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryResultStatusDescTextV10CT;

public class MobCcsCreditCheckController implements Controller{
	
	 protected final Log logger = LogFactory.getLog(getClass());
	
	 private CustCreditCheckClient custCreditCheckClient;
	 private MobCcsApprovalLogService mobCcsApprovalLogService;
	 
	 public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}

	public void setMobCcsApprovalLogService(
			MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}

	public CustCreditCheckClient getCustCreditCheckClient() {
		return custCreditCheckClient;
	}

	public void setCustCreditCheckClient(CustCreditCheckClient custCreditCheckClient) {
		this.custCreditCheckClient = custCreditCheckClient;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 		
		 String idDocType = (String)request.getParameter("idDocType");
		 String idDocNum = (String)request.getParameter("idDocNum");
		 String orderId = (String)request.getParameter("orderId");
		 
		 JSONArray jsonArray = new JSONArray();
		 
		 boolean approvedSrvLineExceed = false;
		 Boolean temp = (Boolean) request.getSession().getAttribute("approvedSrvLineExceed");
		 
		 if (temp != null) {
			 approvedSrvLineExceed = temp;
		 }
		 //To check whether service line exceed has been approved previously 
		 //else will ask user re approve
		 if (approvedSrvLineExceed) {
			 jsonArray.add("{\"creditStatus\":\"" + "all condition passed" + "\"}");
		 } else {
			 try {
					QueryResultDetailsV10CT result = custCreditCheckClient.checkCustomerCredit(idDocType, idDocNum);
					if (result != null && result.getStatus() != null) {
						logger.debug("creditCheck status: " + result.getStatus() + ", desc: " + result.getStatusDesc());
						String status = result.getStatus().toString();
						if ("0".equals(status) || "4".equals(status)) {
							jsonArray.add("{\"creditStatus\":\"" + "all condition passed" + "\"}");
						} else if ("1".equals(status) || "5".equals(status) || "7".equals(status)) {
							jsonArray.add("{\"creditStatus\":\"" + "blacklisted" + "\"}");
						} else if ("2".equals(status) || "6".equals(status) || "8".equals(status)) {
							jsonArray.add("{\"creditStatus\":\"" + "overdue failed" + "\"}");
						} else if ("3".equals(status)) {
							
							QueryResultStatusDescTextV10CT creditStatusDesc = result.getStatusDesc();
							
							if (StringUtils.isBlank(orderId) || !mobCcsApprovalLogService.isApproval(orderId, "AU02")) {
								String statusDesc = creditStatusDesc.toString();
								if (StringUtils.isNotBlank(statusDesc)) {
									int start = statusDesc.lastIndexOf("(");
									int end = statusDesc.lastIndexOf(")");
									
									jsonArray.add("{\"creditStatus\":\"" + "MRT count failed"
											 + "\",\"mrtCount\":\"" + statusDesc.substring(start + 1, end)
											 + "\"}");
								}
								
							} else if (mobCcsApprovalLogService.isApproval(orderId, "AU02")){
								jsonArray.add("{\"creditStatus\":\"" + "all condition passed" + "\"}");
							}
						} else {
							jsonArray.add("{\"creditStatus\":\"" + "no match" + "\"}");
						}
					} else {
						jsonArray.add("{\"creditStatus\":\"" + "no match" + "\"}");
					}
				} catch (Exception e) {
					logger.error("Exception caught in calling MobCcsCreditCheckController()", e);
				}
		 }
		 	
			
		 	logger.info("jsonArray : " + jsonArray);
			
			return new ModelAndView("ajax_creditCheck", "jsonArray", jsonArray);
	}
	
}
