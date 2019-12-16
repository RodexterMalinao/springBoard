package com.bomwebportal.ims.web;

import java.io.IOException;
//import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.bean.LookupTableBean;

import net.sf.json.JSONArray;

public class ImsAddressDistrictLookupController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String type = request.getParameter("T");// T=AREA,DISTRICT, SECTION;
		String id = request.getParameter("ID");
		
//		String SECTIONALL = request.getParameter("SECTIONALL");
		// AddressDTO[] addrArray = AddressHelper.searchAddress(keyword);
		//System.out.println("type:" + type);
		//System.out.println("id:" + id);
		//System.out.println("SECTIONALL:" + SECTIONALL);
		// logger.info("id:" + id);
		// "address":"BLK 8 CYBER DOMAINE FANLING NEW TERRITORIES ",
		// logger.info("List of address : "+addrList.size());
		JSONArray jsonArray = new JSONArray();

		if ("AREA".equals(type)) {

			jsonArray.add("{\"id\":\""	+ "" + "\",\"name\":\"" + " " + "\"}");
			for (int i = 0; i < LookupTableBean.getInstance().getImsAddressAreaList().size(); i++) {
				// areaList.put(LookupTableBean.getInstance().getAddressAreaList().get(i).getAreaCode(),
				// LookupTableBean.getInstance().getAddressAreaList().get(i).getAreaDescription());
				jsonArray.add("{\"id\":\""	+ LookupTableBean.getInstance().getImsAddressAreaList().get(i).getAreaCode()
						+ "\",\"name\":\""	+ LookupTableBean.getInstance().getImsAddressAreaList().get(i).getAreaDescription() + "\"}");
			}

		} else if ("DISTRICT".equals(type)) {

			jsonArray.add("{\"id\":\""	+ "" + "\",\"name\":\"" + " " + "\"}");
			for (int i = 0; i < LookupTableBean.getInstance().getImsAddressDistrictList().size(); i++) {
				if (LookupTableBean.getInstance().getImsAddressDistrictList().get(i).getAreaCode().equals(id)) {
					jsonArray.add("{\"id\":\""+ LookupTableBean.getInstance().getImsAddressDistrictList().get(i).getDistrictCode()
							+ "\",\"name\":\""+ LookupTableBean.getInstance().getImsAddressDistrictList().get(i).getDistrictDescription() + "\"}");
				}

			}

		}else if ("SECTION".equals(type)) {

			jsonArray.add("{\"id\":\""	+ "" + "\",\"name\":\"" + " " + "\"}");
			for (int i = 0; i < LookupTableBean.getInstance().getImsAddressSectionList().size(); i++) {
				if (LookupTableBean.getInstance().getImsAddressSectionList().get(i).getDistrictCode().equals(id)  ) {
					String temp="";
					if (!"ZZZZ".equals(LookupTableBean.getInstance().getImsAddressSectionList().get(i).getSectionCode())){
						temp=LookupTableBean.getInstance().getImsAddressSectionList().get(i).getSectionDescription();
					}else{
						temp="";
					}
					jsonArray.add("{\"id\":\""+ LookupTableBean.getInstance().getImsAddressSectionList().get(i).getSectionCode()
							+ "\",\"name\":\""+ temp+ "\"}");
				}
			}
			
		}

		//System.out.println("jsonArray : " + jsonArray);

		logger.info("jsonArray : " + jsonArray);

		return new ModelAndView("ajax_addrDistrictLookup", "jsonArray",	jsonArray);
	}
}
