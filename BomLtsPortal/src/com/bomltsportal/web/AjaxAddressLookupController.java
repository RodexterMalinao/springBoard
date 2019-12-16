package com.bomltsportal.web;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomltsportal.dto.SearchAddressResultDTO;
import com.bomltsportal.util.SearchHelper;


public class AjaxAddressLookupController implements Controller {
	
	private SearchHelper searchHelper;

	protected final Log logger = LogFactory.getLog(getClass());
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		JSONArray jsonArray = new JSONArray();
		JSONObject outter = new JSONObject();
		
		String keyword = URLDecoder.decode(request.getParameter("keyword"),
				"UTF-8");
		logger.info("keyword=" + keyword);
		SearchAddressResultDTO result = searchHelper.searchBuilding(keyword);
		// System.out.println("count found:"+result.getResultSize());
				if (result.getResultList() != null) {
					JSONArray innerArray = new JSONArray();
					outter.put("totalRecord", result.getResultSize());
					for (BuildingMarkerDTO dto : (BuildingMarkerDTO[]) result
							.getResultList()) {

						JSONObject jsonObject = new JSONObject();

						jsonObject.put("sectDescEn", dto.getSectDescEn());
						jsonObject.put("sectDescCh", dto.getSectDescCh());
						jsonObject.put("distDescEn", dto.getDistDescEn());
						jsonObject.put("distDescCh", dto.getDistDescCh());
						jsonObject.put("areaDescEn", dto.getAreaDescEn());
						jsonObject.put("areaDescCh", dto.getAreaDescCh());
						jsonObject.put("housingAddrEn",	dto.getHousingAddrEn());
						jsonObject.put("housingAddrCh",	dto.getHousingAddrCh());
						jsonObject.put("markerIdx", dto.getIndexKey());
						jsonObject.put("lat", dto.getLat());
						jsonObject.put("lng", dto.getLng());
						innerArray.element(jsonObject);


					}
					outter.put("addressList", innerArray);
				}
				jsonArray.element(outter);
				
				logger.info("jsonArray : " + jsonArray);

				return new ModelAndView("ajax_view", "jsonArray", jsonArray);
	}
	

	public SearchHelper getSearchHelper() {
		return searchHelper;
	}

	public void setSearchHelper(SearchHelper searchHelper) {
		this.searchHelper = searchHelper;
	}

}
