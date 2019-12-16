package com.bomwebportal.ims.web;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.google.gson.Gson;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.Ims1AMSFSAInfoDTO;
import com.bomwebportal.ims.dto.ui.Ims1amsUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.Ims1AMSEnquiryService;
import com.bomwebportal.lts.util.LtsConstant;


public class  Ims1AMSController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();
	private Ims1AMSEnquiryService ims1AMSEnquiryService;
	
	
	public Object formBackingObject(HttpServletRequest request) 
		throws ServletException{
		
		logger.info("Ims1AMSController.formBackingObject");
		OrderImsUI sessionOrder = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		logger.info(gson.toJson(sessionOrder));
		
		
		
		logger.debug("session Serbdyno: "+ gson.toJson(sessionOrder.getInstallAddress().getSerbdyno()));		
		logger.debug("session Flat: "+ gson.toJson(sessionOrder.getInstallAddress().getFlat()));
		logger.debug("session FloorNo: "+ gson.toJson(sessionOrder.getInstallAddress().getFloorNo()));
		logger.debug("session HiLotNo: "+ gson.toJson(sessionOrder.getInstallAddress().getHiLotNo()));
		
		/*String serbdyno = sessionOrder.getInstallAddress().getSerbdyno();
		String floor = sessionOrder.getInstallAddress().getFloorNo();
		String flatno = sessionOrder.getInstallAddress().getFlat();
		String hiLotNo = sessionOrder.getInstallAddress().getHiLotNo();	
		*/
		
		String serbdyno = request.getParameter("serbdyno");
		String floor = request.getParameter("floor");
		String flatno = request.getParameter("flatno");
		String hiLotNo = request.getParameter("hiLotNo");	
			
		logger.debug("request.getParameter serbdyno: "+serbdyno);
		logger.debug("request.getParameter floor: " +floor);
		logger.debug("request.getParameter flatno: " +flatno);
		logger.debug("request.getParameter hiLotNo: " +hiLotNo);
		List<Ims1AMSFSAInfoDTO> ims1AMSFSAInfoDTOs = new ArrayList<Ims1AMSFSAInfoDTO>();
		
		//ims1AMSFSAInfoDTOs = ims1AMSEnquiryService.getIms1AMSFSAInfoList2(sessionOrder.getInstallAddress().getSerbdyno(),
			//	sessionOrder.getInstallAddress().getFlat(), sessionOrder.getInstallAddress().getFloorNo(), sessionOrder.getInstallAddress().getHiLotNo());
		
		
		ims1AMSFSAInfoDTOs = ims1AMSEnquiryService.getIms1AMSFSAInfoList2(serbdyno, flatno, floor, hiLotNo);
			
		
		logger.debug(gson.toJson(ims1AMSFSAInfoDTOs));
		
		Ims1amsUI ui = new Ims1amsUI();
		
		for(int i=0; i<ims1AMSFSAInfoDTOs.size(); i++){
			if (ims1AMSFSAInfoDTOs.get(i).getFakeLineType()!=null && "Served By ".equalsIgnoreCase(ims1AMSFSAInfoDTOs.get(i).getFakeLineType())){
				ims1AMSFSAInfoDTOs.get(i).setLineType("Served By "+ ims1AMSFSAInfoDTOs.get(i).getLineType());
			}		
		}
		
		logger.debug("final return of the ArrayList<Ims1AMSAddrInfoDTO>(): "+ gson.toJson(ims1AMSFSAInfoDTOs));
		
		ui.setIms1AMSFSAInforecords(ims1AMSFSAInfoDTOs);
		
		return ui;
	}


	public Ims1AMSEnquiryService getIms1AMSEnquiryService() {
		return ims1AMSEnquiryService;
	}


	public void setIms1AMSEnquiryService(Ims1AMSEnquiryService ims1amsEnquiryService) {
		ims1AMSEnquiryService = ims1amsEnquiryService;
	}
	
	
} 