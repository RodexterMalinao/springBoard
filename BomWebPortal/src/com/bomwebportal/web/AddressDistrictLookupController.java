package com.bomwebportal.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.AddressAreaDTO;
import com.bomwebportal.dto.AddressDistrictDTO;
import com.bomwebportal.dto.AddressSectionDTO;
import com.bomwebportal.service.CustomerProfileService;

public class AddressDistrictLookupController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustomerProfileService customerProfileService;

	public CustomerProfileService getCustomerProfileService() {
	    return customerProfileService;
	}

	public void setCustomerProfileService(
		CustomerProfileService customerProfileService) {
	    this.customerProfileService = customerProfileService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String type = request.getParameter("T");// T=AREA,DISTRICT, SECTION;
		String id = request.getParameter("ID");
		
		String SECTIONALL = request.getParameter("SECTIONALL");
		// AddressDTO[] addrArray = AddressHelper.searchAddress(keyword);
		//System.out.println("type:" + type);
		//System.out.println("id:" + id);
		//System.out.println("SECTIONALL:" + SECTIONALL);
		// logger.info("id:" + id);
		// "address":"BLK 8 CYBER DOMAINE FANLING NEW TERRITORIES ",
		// logger.info("List of address : "+addrList.size());
		JSONArray jsonArray = new JSONArray();

		if ("AREA".equals(type)) {
			List<AddressAreaDTO> addressAreaList = LookupTableBean.getInstance().getAddressAreaList();
			if (!this.isEmpty(addressAreaList)) {
				for (AddressAreaDTO dto : addressAreaList) {
					JSONObject json = new JSONObject();
					json.put("id", dto.getAreaCode());
					json.put("name", dto.getAreaDescription());
					jsonArray.add(json);
				}
			}

		} else if ("DISTRICT".equals(type)) {
			List<AddressDistrictDTO> addressDistrictList = LookupTableBean.getInstance().getAddressDistrictList();
			if (!this.isEmpty(addressDistrictList)) {
				for (AddressDistrictDTO dto : addressDistrictList) {
					if (dto.getAreaCode().equals(id)) {//load district list (by area)
						JSONObject json = new JSONObject();
						json.put("id", dto.getDistrictCode());
						json.put("name", dto.getDistrictDescription());
						jsonArray.add(json);
					}
				}
			}
		} else if ("SECTION".equals(type)) {
			List<AddressSectionDTO> addressSectionList = LookupTableBean.getInstance().getAddressSectionList();
			if (!this.isEmpty(addressSectionList)) {
				for (int i = 0; i < addressSectionList.size(); i++) {
					AddressSectionDTO dto = addressSectionList.get(i);
					if ("true".equals(SECTIONALL)) {
						
						if (i == 0) {//set the dummy ZZZZ to list
							JSONObject json = new JSONObject();
							json.put("id", "ZZZZ");
							json.put("name", "");
							jsonArray.add(json);
							continue;
						}
						
						if (!"ZZZZ".equals(dto.getSectionCode())){//skip the ZZZZ 
							JSONObject json = new JSONObject();
							json.put("id", dto.getSectionCode());
							json.put("name", dto.getSectionDescription());
							jsonArray.add(json);
						}
						
					}else if (dto.getDistrictCode().equals(id)) {//load a district , include ZZZZ 
						JSONObject json = new JSONObject();
						json.put("id", dto.getSectionCode());
						json.put("name", "ZZZZ".equals(dto.getSectionCode()) ? "" : dto.getSectionDescription());
						jsonArray.add(json);
					}
				}
			}
		} else if ("SERVICEBOUNDARYNUM".equals(type)) {
			if (logger.isDebugEnabled()) {
				logger.debug("serviceBoundaryNum: " + id);
			}
			JSONObject json = new JSONObject();
			json.put("phInd", booleanToString(customerProfileService.isPublicHousing(id)));
			json.put("hktPremierAddr", "N");//booleanToString(customerProfileService.isHktPremierAddr(id)));
			jsonArray.add(json);
		}
		
		if (logger.isInfoEnabled()) {
			logger.debug("jsonArray : " + jsonArray);
		}

		return new ModelAndView("ajax_addrDistrictLookup", "jsonArray",	jsonArray);
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	// page use Y/N as indicator
	private String booleanToString(boolean value) {
		return value ? "Y" : "N";
	}
}
