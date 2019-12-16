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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.QcStaffDTO;
import com.bomwebportal.ims.dto.ui.ImsQcAssignUI;
import com.bomwebportal.ims.dto.ui.QcImsAdminUI;
import com.bomwebportal.service.qc.ImsDSQCService;
import com.google.gson.Gson;



public class ImsQcAdminAjaxController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private final String insert = "insert";
	private final String release = "release";
	private final String remove = "remove";
	private final String update = "update";
	
	private ImsDSQCService imsDSQCService; 
	
	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}
	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}
    
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("ImsQcAdminAjaxController handle Request: (start) ");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String rtnValue="";
		//rtnValue = "hello world";
		
		ImsQcAssignUI imsQcAssignUI; //= new ImsQcAssignUI();
		List<ImsQcAssignUI> imsQcAssignUIList = new ArrayList<ImsQcAssignUI>();
		
		String operationType = request.getParameter("tp");
		
		//rtnValue=operationType;
		
		String staffId = (String) request.getParameter("staffId");
		logger.info("ImsQcAdminAjaxController staffId:"+staffId);
		
		try {
			if (operationType.equalsIgnoreCase(insert)){
				String orderIds = request.getParameter("orderIds");

				QcStaffDTO newStaff = new QcStaffDTO();
				ServletRequestDataBinder binder = new ServletRequestDataBinder(newStaff);
				binder.bind(request);
				logger.info("insertNewQcStaffInfo" + new Gson().toJson(newStaff));
				newStaff.setLastUpBy(user.getUsername());
				newStaff.setCreateBy(user.getUsername());
				
				if (imsDSQCService.isStaffExist(newStaff.getStaffId()) && !imsDSQCService.isQcStaffExist(newStaff.getStaffId()))
				{
					for (int i=0;i<newStaff.getSkillSet().size();i++){	
						newStaff.setSkills(newStaff.getSkillSet().get(i));
						if (!newStaff.getSkillSet().get(i).equalsIgnoreCase("on"))
							imsDSQCService.insertNewQcStaffSkills(newStaff);
					}
					imsDSQCService.insertNewQcStaffInfo(newStaff);
				}else if (imsDSQCService.isQcStaffExist(newStaff.getStaffId())){
					rtnValue = "QC staff already Created!!";
				}else if (!imsDSQCService.isStaffExist(newStaff.getStaffId())){
					rtnValue = "Staff not exist!";
				}
				
			}else if (operationType.equalsIgnoreCase(release)){
				
				logger.info("ImsQcAdminAjaxController release operations START");
				
				QcStaffDTO releaseOrderStaff = new QcStaffDTO();
				ServletRequestDataBinder binder = new ServletRequestDataBinder(releaseOrderStaff);
				binder.bind(request);
				logger.info("ImsQcAdminAjaxController releaseQcOrders" + new Gson().toJson(releaseOrderStaff));
				
				releaseOrderStaff.setLastUpBy(user.getUsername());
				imsDSQCService.releaseQcOrders(releaseOrderStaff);
				
			}else if (operationType.equalsIgnoreCase(remove)){
				logger.info("ImsQcAdminAjaxController remove operations START");
				
				QcStaffDTO removeStaff = new QcStaffDTO();
				ServletRequestDataBinder binder = new ServletRequestDataBinder(removeStaff);
				binder.bind(request);
				logger.info("ImsQcAdminAjaxController removeQcStaff" + new Gson().toJson(removeStaff));
				logger.info("ImsQcAdminAjaxController removeQcStaff STAFF ID: " +removeStaff.getStaffId());
				removeStaff.setLastUpBy(user.getUsername());
				
				imsDSQCService.removeQcStaff(removeStaff);
			}else if (operationType.equalsIgnoreCase(update)){
				logger.info("ImsQcAdminAjaxController remove operations START");
				
				QcStaffDTO updateStaff = new QcStaffDTO();
				ServletRequestDataBinder binder = new ServletRequestDataBinder(updateStaff);
				binder.bind(request);
				logger.info("ImsQcAdminAjaxController updateQcStaff" + new Gson().toJson(updateStaff));
				logger.info("ImsQcAdminAjaxController getStatus() " + new Gson().toJson(updateStaff.getStatus()));
				updateStaff.setLastUpBy(user.getUsername());
				updateStaff.setCreateBy(user.getUsername());
				
				imsDSQCService.updateQCstaffSkillsAndStatus(updateStaff);
				for (int i=0;i<updateStaff.getSkillSet().size();i++){	
					updateStaff.setSkills(updateStaff.getSkillSet().get(i));
					if (!updateStaff.getSkillSet().get(i).equalsIgnoreCase("on"))
						imsDSQCService.insertNewQcStaffSkills(updateStaff);
				}
				
			}
		
		} catch (Exception e){
			e.printStackTrace();
			return null;	//tmp 
		}
		JSONArray jsonArray = new JSONArray();

		jsonArray.add(
				"{\"resultMsg\":\""	+ rtnValue +
				"\"}");
		
		logger.info("jsonArray : " + jsonArray);
		logger.info("ImsDsAjaxController handle Request: (end)");

		
		return new ModelAndView("ajax_", "jsonArray",	jsonArray);
	}
}
