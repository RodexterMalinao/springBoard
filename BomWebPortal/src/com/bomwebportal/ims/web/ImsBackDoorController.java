package com.bomwebportal.ims.web;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.dto.ui.CcLtsImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.service.ImsOrderService;
import com.google.gson.Gson;

public class ImsBackDoorController extends SimpleFormController{
	
	private Gson gson = new Gson();
	private ImsOrderService orderService;

	public void setOrderService(ImsOrderService orderService) {
		this.orderService = orderService;
	}

	public ImsOrderService getOrderService() {
		return orderService;
	}		

	private String retUrl;
	private String termUrl;
	private String acqUrl;
	private String nowRetUrl;

	public String getAcqUrl() {
		return acqUrl;
	}

	public void setAcqUrl(String acqUrl) {
		this.acqUrl = acqUrl;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getTermUrl() {
		return termUrl;
	}

	public void setTermUrl(String termUrl) {
		this.termUrl = termUrl;
	}

	protected Object formBackingObject(HttpServletRequest request) throws ServletException{
		logger.info("ImsBackDoorController formBackingObject");
		CcLtsImsOrderEnquiryUI enquiry = new CcLtsImsOrderEnquiryUI();
		return enquiry;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
	throws Exception {
		CcLtsImsOrderEnquiryUI enquiry = (CcLtsImsOrderEnquiryUI) command;
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		logger.info("ImsBackDoorController:" + gson.toJson(enquiry));
		String custNum="";
		String link="";
		if("RET".equals(enquiry.getBackDoorFunction())){
			link = this.getRetUrl()+enquiry.getSerNum()+"&user="+user.getUsername()+"&SSO_BYPASS=1&FSA_LOCK_BYPASS=1&initLoad=1";
		}else if("TERM".equals(enquiry.getBackDoorFunction())){
			link = this.getTermUrl()+enquiry.getSerNum()+"&user="+user.getUsername()+"&SSO_BYPASS=1&FSA_LOCK_BYPASS=1&initLoad=1";
		}else if("NTVRET".equals(enquiry.getBackDoorFunction())){
			link = this.getNowRetUrl()+enquiry.getSerNum()+"&user="+user.getUsername()+"&SSO_BYPASS=1";
		}else if("NTV".equals(enquiry.getBackDoorFunction())){
			link = "welcome.html";			
		}
		else{
			List<ImsInstallationUI> cust =  orderService.getImsCustomer(enquiry.getDocType(), enquiry.getDocNum());
			if(cust.size()>0){
				custNum = cust.get(0).getCustNo();
			}
			link = this.getAcqUrl()+custNum;
		}		
		logger.info("link:" + link);
		return new ModelAndView(new RedirectView(link));										
	}

	public String getNowRetUrl() {
		return nowRetUrl;
	}

	public void setNowRetUrl(String nowRetUrl) {
		this.nowRetUrl = nowRetUrl;
	}
}
