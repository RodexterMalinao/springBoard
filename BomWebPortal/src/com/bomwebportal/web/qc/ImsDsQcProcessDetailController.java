package com.bomwebportal.web.qc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.ImsDsQcProcessDetailUI;
import com.bomwebportal.ims.web.ImsAppointmentController;
import com.bomwebportal.service.qc.ImsDSQCService;
import com.google.gson.Gson;


public class ImsDsQcProcessDetailController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();
	private ImsDSQCService imsDSQCService;
	private ImsAppointmentController appntController;
	private String ntvDomain;
	
	public String getNtvDomain() {
		return ntvDomain;
	}

	public void setNtvDomain(String ntvDomain) {
		this.ntvDomain = ntvDomain;
	}
	
	public ImsAppointmentController getAppntController() {
		return appntController;
	}

	public void setAppntController(ImsAppointmentController appntController) {
		this.appntController = appntController;
	}

	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}

	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}

	public Object formBackingObject(HttpServletRequest request)
	throws ServletException {
		ImsDsQcProcessDetailUI enquiry = new ImsDsQcProcessDetailUI(); 
		List<String> reasonCQlt = new ArrayList<String>();
		List<String> reasonFLlt = new ArrayList<String>();
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		boolean isActiveQCstaff = false;
		
		request.setAttribute("orderid", request.getParameter("orderId"));
		String orderID=request.getParameter("orderId");
		
		request.getSession().setAttribute("orderType", request.getParameter("orderType"));
		
		request.getSession().setAttribute("ntvDomain", ntvDomain);
		
		try {
			isActiveQCstaff=imsDSQCService.isActiveQCstaff(user.getUsername());
			
			List<ImsDsQcProcessDetailUI> reasonCQList = imsDSQCService.getReasonCQDesc();
			List<ImsDsQcProcessDetailUI> reasonFailList = imsDSQCService.getReasonFailDesc();
			List<ImsDsQcProcessDetailUI> qcProcessInfo  = imsDSQCService.getQCProcessInfo(orderID);
			
			reasonCQlt.add("");
			reasonFLlt.add("");
			
			for (int i=0;i<reasonCQList.size();i++){
				reasonCQlt.add(reasonCQList.get(i).getReasonCQDesc());
			}
			for (int i=0;i<reasonFailList.size();i++){
				reasonFLlt.add(reasonFailList.get(i).getReasonFailDesc());
			}
			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			logger.debug("QC PROCESS get Current DATE : " +dateFormat.format(cal.getTime()));
			//logger.debug("QC PROCESS get Current DATE : " + dateFormat.format(date));
			//logger.debug("QC PROCESS get Order Distict: " +qcProcessInfo.get(0).getOrderDistict());
			
			enquiry.setAssignee(imsDSQCService.getAssignee(orderID));
			enquiry.setAssignDate(imsDSQCService.getAssignDate(orderID));
			//enquiry.setQcDateTime(dateFormat.format(cal.getTime()));
			enquiry.setQcDateTime(dateFormat.format(cal.getTime()));
			enquiry.setPreInstallInd(imsDSQCService.getPreInstallInd(orderID));
			if (qcProcessInfo.size() > 0){
				enquiry.setQcCallTime(qcProcessInfo.get(0).getQcCallTime());
				enquiry.setOrderDistict(qcProcessInfo.get(0).getOrderDistict());
				enquiry.setOrderPlace(qcProcessInfo.get(0).getOrderPlace());
			}
		
		
			List<String> sameCustOrders = imsDSQCService.getSameCustOrder(orderID);
			System.out.println(sameCustOrders.toString());
			if(!sameCustOrders.isEmpty() && sameCustOrders != null){
				enquiry.setSameCustOrder(sameCustOrders.toString().replace("[", "").replace("]", "").replaceAll(", ", "\n"));
			}
			
			request.getSession().setAttribute("reasonCQList", reasonCQlt);
			request.getSession().setAttribute("reasonFailList", reasonFLlt);
			
			//logger.debug("enquiry.getAssignee : " + enquiry.getAssignee());
			request.getSession().setAttribute("enquiry", enquiry);
			request.getSession().setAttribute("isActiveQCstaff", isActiveQCstaff);
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return enquiry;
	}
	
	
	
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		// TODO Auto-generated method stub
		//logger.debug("skjflshdfkjsdfkjsdfbsdhlfLsdf");
		
		return super.processFormSubmission(request, response, command, errors);
	}

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
			
		ImsDsQcProcessDetailUI enquiry = (ImsDsQcProcessDetailUI)command;
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		//logger.debug("skjflshdfkjsdfkjsdfbsdhlfLsdf");
		//logger.debug(new Gson().toJson(errors));
		boolean isOrderAwaitQC = false;
		int chgAwaitQC2AwaitSignoff = 0;
		String orderid= enquiry.getOrderId();
		if (enquiry != null){
			
			enquiry.setLastUpBy(user.getUsername());
			enquiry.setCreateBy(user.getUsername());
		
			if (enquiry.getAction().equalsIgnoreCase("PASS")){
				logger.info("QC process onSubmit PASS called: " + gson.toJson(enquiry));
				
				
				try {
					isOrderAwaitQC = imsDSQCService.isOrderAwaitQC(orderid);
					request.getSession().setAttribute("isOrderAwaitQC", isOrderAwaitQC);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(isOrderAwaitQC && ((String) request.getSession().getAttribute("orderType")).contains("NTV")){
					
					try {
						chgAwaitQC2AwaitSignoff = imsDSQCService.changeAwaitQCOrderStatus(orderid);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				
				
				enquiry.setQcStatus("Q01");
				
				try {
					if (imsDSQCService.checkOrderExist(orderid)){
						logger.info("updateQcProcess PASS START");
						imsDSQCService.updateQcProcess(enquiry);
					}else{
						logger.info("insertQcProcess PASS START");
						imsDSQCService.insertQcProcess(enquiry);
					}
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if (enquiry.getAction().equalsIgnoreCase("CANNOTQC")){
				logger.info("QC process onSubmit CANNOTQC called: " + gson.toJson(enquiry));
				orderid= enquiry.getOrderId();
				enquiry.setQcStatus("Q03");
				
				try {
					logger.info("QC process Cannot QC Reason CODE: " + imsDSQCService.getReasonCode(enquiry.getReasonCQDesc()));
					enquiry.setReasonCQCode(imsDSQCService.getReasonCode(enquiry.getReasonCQDesc()));
					
					if (imsDSQCService.checkOrderExist(orderid)){
						logger.info("updateQcProcess CANNOT QC START");
						imsDSQCService.updateQcProcess(enquiry);
					}else{
						logger.info("insertQcProcess CANNOT QC START");
						imsDSQCService.insertQcProcess(enquiry);
					}
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if (enquiry.getAction().equalsIgnoreCase("FAIL")){
				logger.info("QC process onSubmit CANNOTQC called: " + gson.toJson(enquiry));
				orderid= enquiry.getOrderId();
				enquiry.setQcStatus("Q02");
				
				try {
					logger.info("QC process Failed Reason CODE: " + imsDSQCService.getReasonCode(enquiry.getReasonFailDesc()));
					enquiry.setReasonFailCode(imsDSQCService.getReasonCode(enquiry.getReasonFailDesc()));
					if (imsDSQCService.checkOrderExist(orderid)){
						logger.info("updateQcProcess START");
						imsDSQCService.updateQcProcess(enquiry);
					}else{
						logger.info("insertQcProcess START");
						imsDSQCService.insertQcProcess(enquiry);
					}
					
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else if (enquiry.getAction().equalsIgnoreCase("RNA")){
				logger.info("QC process onSubmit RNA called: " + gson.toJson(enquiry));
				orderid= enquiry.getOrderId();
				enquiry.setQcStatus("Q04");
				
				try {
					if (imsDSQCService.checkOrderExist(orderid)){
						logger.info("updateQcProcess RNA START");
						imsDSQCService.updateQcProcess(enquiry);
					}else{
						logger.info("insertQcProcess RNA START");
						imsDSQCService.insertQcProcess(enquiry);
					}
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if (enquiry.getAction().equalsIgnoreCase("PASSWITHOUTQC")){
				logger.info("QC Proceed order without QC called: " + gson.toJson(enquiry));
				
				
				try {
					isOrderAwaitQC = imsDSQCService.isOrderAwaitQC(orderid);
					request.getSession().setAttribute("isOrderAwaitQC", isOrderAwaitQC);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(isOrderAwaitQC && ((String) request.getSession().getAttribute("orderType")).contains("NTV")){
					
					try {
						chgAwaitQC2AwaitSignoff = imsDSQCService.changeAwaitQCOrderStatus(orderid);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				
				
				enquiry.setQcStatus("Q07");
				
				try {
					if (imsDSQCService.checkOrderExist(orderid)){
						logger.info("updateQcProcess PASSWITHOUTQC START");
						imsDSQCService.updateQcProcess(enquiry);
					}else{
						logger.info("insertQcProcess PASSWITHOUTQC START");
						imsDSQCService.insertQcProcess(enquiry);
					}
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/*
		logger.info("request.getSession().getAttribute(isOrderAwaitQC):"+request.getSession().getAttribute("isOrderAwaitQC"));
		
		boolean isOrderAwaitSignoff = false;
		try {
			isOrderAwaitSignoff = imsDSQCService.isOrderAwaitSignOff(enquiry.getOrderId());		
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("isOrderAwaitSignoff:"+isOrderAwaitSignoff);
		*/
		if(chgAwaitQC2AwaitSignoff == 1){
			request.getSession().setAttribute("_action","ntvawaitsignoff");
			request.getSession().setAttribute("_qcOrderId",orderid);
		}
	 return new ModelAndView(new RedirectView("blank.jsp"));
		
	}
}
