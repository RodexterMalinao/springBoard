package com.bomwebportal.web.qc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.dto.ui.ImsQcAssignUI;
import com.bomwebportal.service.qc.ImsDSQCService;



public class ImsQcAssignAjaxController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private final String assign = "assign";
	private final String assignB = "assignBulk";
	
	private ImsDSQCService imsDSQCService; 
	
	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}
	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}
    
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("ImsQcAssignAjaxController handle Request: (start) ");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String rtnValue;
		rtnValue = "hello world";
		
		ImsQcAssignUI imsQcAssignUI; //= new ImsQcAssignUI();
		List<ImsQcAssignUI> imsQcAssignUIList = new ArrayList<ImsQcAssignUI>();
		
		String operationType = request.getParameter("tp");
		
		rtnValue=operationType;
		
		String staffId = (String) request.getParameter("staffId");
		logger.info("ImsQcAssignAjaxController staffId:"+staffId);
		String sysF = request.getParameter("sysf");
		logger.info("ImsQcAssignAjaxController sysf:"+sysF);
		
		try {
			if (operationType.equalsIgnoreCase(assign)){
				String orderIds = request.getParameter("orderIds");
				imsQcAssignUI = new ImsQcAssignUI();
				
				imsQcAssignUI.setOrderIds(orderIds);
				imsQcAssignUI.setAssignee(staffId);
				imsQcAssignUI.setStaffId(staffId);
				imsQcAssignUI.setLastUpBy(user.getOrgStaffId());
				imsQcAssignUI.setCreateBy(user.getOrgStaffId());
				imsQcAssignUI.setSys_f(sysF);
				
				//logger.info("imsDSQCService.checkIfAssignisNull(orderIds)" +imsDSQCService.checkIfAssignisNull(orderIds));
				
				if (imsQcAssignUI!=null){
					if (imsDSQCService.checkIfAssignisNull(orderIds)){
						imsDSQCService.updateQcAssign(imsQcAssignUI);
						imsDSQCService.updateOrderCount(imsQcAssignUI);
					}else {
						imsDSQCService.insertQcAssign(imsQcAssignUI);
						imsDSQCService.updateOrderCount(imsQcAssignUI);
					}
				}
			}else if (operationType.equalsIgnoreCase(assignB)){
				
				String[] orderIds = request.getParameter("orderIds").split("\\|");
				logger.info("ImsQcAssignAjaxController orderIds length:"+orderIds.length);
				if (orderIds!=null) {
//					for (String o: orderIds){
//						logger.info("logic for orderId " + o); 
//						//System.out.println("logic for orderId " + o); 
//					}
					for(int i=0;i<orderIds.length;i++){
						imsQcAssignUI = new ImsQcAssignUI();
						imsQcAssignUI.setOrderIds(orderIds[i]);
						imsQcAssignUI.setAssignee(staffId);
						imsQcAssignUI.setLastUpBy(user.getOrgStaffId());
						imsQcAssignUI.setCreateBy(user.getOrgStaffId());
						//imsQcAssignUIList.add(imsQcAssignUI);
//						imsDSQCService.insertQcAssign(imsQcAssignUI);
//						imsDSQCService.updateOrderCount(imsQcAssignUI);
						if (imsDSQCService.checkIfAssignisNull(orderIds[i])){
							imsDSQCService.updateQcAssign(imsQcAssignUI);
							imsDSQCService.updateOrderCount(imsQcAssignUI);
						}else {
							imsDSQCService.insertQcAssign(imsQcAssignUI);
							imsDSQCService.updateOrderCount(imsQcAssignUI);
						}
					}
					//imsDSQCService.insertQcAssignBulk(imsQcAssignUIList);			
				}
				
			}
		
		
		
		/*	String salesType = request.getParameter("st");
		String location = request.getParameter("lo");
		String staffId = request.getParameter("sId");
		String rtnValue;
		
		logger.info("salesType : " + salesType);
		logger.info("location : " + location);
		logger.info("staffId : " + staffId);
		
		BomSalesUserDTO amend = new BomSalesUserDTO();
		amend.setSalesType(salesType);
		amend.setLocation(location);
		amend.setOrgStaffId(staffId);
		
		if(salesType.length()>0){
			rtnValue = "hello world";
			imsDSQCService.updateSalesTypeAndLocation(amend);
			BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
			user.setSalesType(salesType);
			user.setLocation(location);
			request.getSession().setAttribute("bomsalesuser", user); 
		} else {
			rtnValue = null;
		}
		*/
		} catch (Exception e){
			e.printStackTrace();
			return null;	//tmp 
		}
		JSONArray jsonArray = new JSONArray();

		jsonArray.add(
				"{\"testing\":\""	+ rtnValue +
				"\"}");
		
		logger.info("jsonArray : " + jsonArray);
		logger.info("ImsDsAjaxController handle Request: (end)");

		
		return new ModelAndView("ajax_", "jsonArray",	jsonArray);
	}
}
