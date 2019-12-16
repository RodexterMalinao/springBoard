package com.bomwebportal.web.qc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ImsDSQCDAO;
import com.bomwebportal.ims.dto.ui.ImsDsQCStaffAdminUI;
import com.bomwebportal.ims.dto.ui.ImsQcAssignUI;
import com.bomwebportal.ims.service.CCSalesInfoService;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.service.qc.ImsDSQCService;


public class ImsDsQCStaffAdminController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());

	private ImsOrderAmendService imsOrderAmendservice;
	private CCSalesInfoService ccsiService;

	private ImsDSQCService imsDSQCService;
	
	public Object formBackingObject(HttpServletRequest request)
	throws ServletException {	
		logger.info("ImsDsQCStaffAdminController formBackingObject is called");
	
		ImsDsQCStaffAdminUI enquiry = new ImsDsQCStaffAdminUI();
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
	
		try {
			
			if(bomSalesUserDTO.getChannelCd().equalsIgnoreCase("QCC")){
				enquiry.setControlStaffRatio(imsDSQCService.getQcStaffRatio("CONTROL").get(0).getControlStaffRatio());
				enquiry.setCleanStaffRatio(imsDSQCService.getQcStaffRatio("CLEAN").get(0).getCleanStaffRatio());
			}else{
				enquiry.setControlStaffRatio(imsDSQCService.getQcNTVStaffRatio("CONTROL").get(0).getControlStaffRatio());
				enquiry.setCleanStaffRatio(imsDSQCService.getQcNTVStaffRatio("CLEAN").get(0).getCleanStaffRatio());
					
			}
			logger.info("DsImsStaffAdminController enquiry.getControlStaffRatio: " +enquiry.getControlStaffRatio() );
			logger.info("DsImsStaffAdminController enquiry.getCleanStaffRatio: " +enquiry.getCleanStaffRatio() );

		
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enquiry;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
		logger.info("ImsDsQCStaffAdminController onSubmit is called");
		ImsDsQCStaffAdminUI enquiry = (ImsDsQCStaffAdminUI)command;
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		logger.info("ImsDsQCStaffAdminController onSubmit is enquiry.getControlStaffRatio():" + enquiry.getControlStaffRatio());
		logger.info("ImsDsQCStaffAdminController onSubmit is enquiry.getCleanStaffRatio():" + enquiry.getCleanStaffRatio());

		
		try {
			
			if(bomSalesUserDTO.getChannelCd().equalsIgnoreCase("QCC")){
				imsDSQCService.updateControlStaffRatio(enquiry);
				imsDSQCService.updateCleanStaffRatio(enquiry);
			}else{
				imsDSQCService.updateControlNTVStaffRatio(enquiry);
				imsDSQCService.updateCleanNTVStaffRatio(enquiry);				
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView(new RedirectView("imsdsqcstaffadmin.html"));
	}

	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}

	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}
	
	public CCSalesInfoService getCcsiService() {
		return ccsiService;
	}

	public void setCcsiService(CCSalesInfoService ccsiService) {
		this.ccsiService = ccsiService;
	}

	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}

	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}
}
