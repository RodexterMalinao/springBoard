/**
 * 
 */
package com.bomwebportal.web.qc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ui.ImsQcAssignUI;
import com.bomwebportal.ims.dto.ui.QcImsAdminUI;
import com.bomwebportal.service.qc.ImsDSQCService;
import com.google.gson.Gson;

/**
 * @author 01397165
 *
 */
	public class QcImsAdminController extends SimpleFormController  {
		
		private ImsDSQCService imsDSQCService;
		
			public Object formBackingObject(HttpServletRequest request)	throws ServletException {
		
				logger.debug("QcImsAdminController formBackingObject called");
				BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
				QcImsAdminUI model = new QcImsAdminUI();
				
				List <String> qcStaffSkills = new ArrayList<String>();
				List<QcImsAdminUI> sList = new ArrayList<QcImsAdminUI>();
				ImsQcAssignUI imsQcAssignUI= new ImsQcAssignUI();
				
				
				try {
					imsQcAssignUI.setStaffId(bomSalesUserDTO.getUsername());
					imsDSQCService.updateOrderCount(imsQcAssignUI);
					List<QcImsAdminUI> qcStafflist = imsDSQCService.getQcStaffInfoAdmin(bomSalesUserDTO.getChannelId());
					
					logger.info("QcImsAdminController qcStafflist.size():" +qcStafflist.size());
					
					for (int i=0;i<qcStafflist.size();i++){
						qcStaffSkills=imsDSQCService.getQcSkills(qcStafflist.get(i).getQcStaffId());
						qcStafflist.get(i).setSkillSet(qcStaffSkills);
						//logger.info("qcImsAdminController qcStaffInfo.get(i).getQcStaffId():"+qcStaffInfo.get(i).getQcStaffId());
						logger.info("QcImsAdminController qcStaffSkills:"+qcStafflist.get(i).getSkillSet());
						logger.info("QcImsAdminController qcStaffSkills:"+new Gson().toJson(qcStafflist.get(i)));
					}
					request.getSession().setAttribute("qcStafflist",qcStafflist);
					model.setStaffList(qcStafflist);
					
					
//					logger.info("qcImsAdminController qcStaffInfo.get(0).getQcStaffId():"+qcStaffInfo.get(1).getQcStaffId());
//					logger.info("qcImsAdminController qcStaffSkills:"+qcStaffSkills);
					//logger.info("qcImsAdminController qcStaffSkills:"+StringUtils.join(qcStaffSkills, ','));
					//request.getSession().setAttribute("qcStaffSkills", StringUtils.join(qcStaffSkills, ','));
					
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//logger.info("qcImsAdminController orderId:"+orderId);
				
			return model;
		}
			
		@Override
		protected Map referenceData(HttpServletRequest request) throws Exception {
			// TODO Auto-generated method stub
			Map<String, Object> rd = new HashMap<String, Object>();
			Map<String, Map<String, String>> referenceData = new HashMap();
			
			//FOR SKILLS
		 /*   List<String> skills = new ArrayList<String>(){{
		        add("nowTV");
		        add("PCD");
		        add("DEL");
		        add("eye");
		        add("ALL LOB");
		        add("Chinese");
		        add("English");
		    }};
			
		    rd.put("skillset", skills);
		    */
			
		    //For Skills
		    Map<String,String> skills = new LinkedHashMap<String,String>();
		    skills.put("nowTV", "nowTV");
		    skills.put("PCD", "PCD");
		    skills.put("DEL", "DEL");
		    skills.put("eye", "eye");
		    //skills.put("ALL LOB", "ALL LOB");
		    skills.put("Chinese", "CHIN");
		    skills.put("English", "ENG");
			referenceData.put("skillset", skills);
		    		    
		    //FOR Status
			Map<String,String> staffStatus = new LinkedHashMap<String,String>();
			staffStatus.put("A", "Active");
			staffStatus.put("IA", "InActive");
			referenceData.put("staffStatus", staffStatus);
		    
			return referenceData;
		}
	
		public ImsDSQCService getImsDSQCService() {
			return imsDSQCService;
		}

		public void setImsDSQCService(ImsDSQCService imsDSQCService) {
			this.imsDSQCService = imsDSQCService;
		}
		
	}
