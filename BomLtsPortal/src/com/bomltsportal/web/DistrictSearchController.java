package com.bomltsportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.dto.ListItemDTO;
import com.bomltsportal.util.SearchHelper;

public class DistrictSearchController implements Controller{

	protected final Log logger = LogFactory.getLog(getClass());
	private SearchHelper searchHelper;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String t = request.getParameter("T"); 
		String k = request.getParameter("K");
		if (k==null)
			k="";
		JSONArray jsonArray = new JSONArray();
		
		ListItemDTO[] dtos = searchHelper.getListItem(t, k);
		for(ListItemDTO dto : dtos){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("resultKey",dto.getResultKey());
			jsonObject.put("resultEn",dto.getResultEn());
			jsonObject.put("resultCh",dto.getResultCh());
			jsonObject.put("lat",dto.getLat());
			jsonObject.put("lng",dto.getLng());
			jsonObject.put("indx", dto.getIndexKey());
			System.out.println(dto.getIndexKey());
			jsonArray.element(jsonObject);
//			jsonArray.add(
//					"{\"resultKey\":\""+dto.getResultKey()
//					+ "\",\"resultEn\":\""+dto.getResultEn()
//					+ "\",\"resultCh\":\""+dto.getResultCh()
//					+ "\",\"lat\":\""+dto.getLat()
//					+ "\",\"lng\":\""+dto.getLng()
//					+ "\"}");
			logger.debug(jsonObject);
		}
		return new ModelAndView("ajax_view", "jsonArray", jsonArray);
	}

	public SearchHelper getSearchHelper() {
		return searchHelper;
	}

	public void setSearchHelper(SearchHelper searchHelper) {
		this.searchHelper = searchHelper;
	}

}
