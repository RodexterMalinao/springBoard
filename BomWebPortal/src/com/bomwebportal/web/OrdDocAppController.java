package com.bomwebportal.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.CNMRTSupportDocDTO;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.OrderService;

public class OrdDocAppController extends MultiActionController { //AbstractController {

	private OrdDocService ordDocService;
	private OrderService orderService;
	private JsonConfig jsonConfig;
	
	
	public OrdDocAppController() {
		jsonConfig = new JsonConfig();
		DateJsonValueProcessor processor = new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss");
		jsonConfig.registerJsonValueProcessor(
				Date.class, processor);
	}
	
	
	public OrdDocService getOrdDocService() {
		return ordDocService;
	}



	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}



	public OrderService getOrderService() {
		return orderService;
	}


	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}


	@RequestMapping("/allcaptures")
	public ModelAndView allRecords(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String orderId = request.getParameter("orderId");
		
		if (StringUtils.isEmpty(orderId)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing orderId");
			return null;
		}
		
		JSONArray jsonArray = null;

		if (orderId.startsWith("MRTCN") && orderId.length() == 16){
			CNMRTSupportDocDTO dto = new CNMRTSupportDocDTO();
			dto.setMrtCn(orderId.replace("MRTCN",""));
			List<CNMRTSupportDocDTO> docs = orderService.getCNMRTSupportDocList(dto);
			jsonArray = JSONArray.fromObject(docs, jsonConfig);
		} else {
			List<OrdDocDTO> docs = new ArrayList<OrdDocDTO>();
			docs = ordDocService.getOrdDoc(orderId);
			jsonArray = JSONArray.fromObject(docs, jsonConfig);
		}

		return new ModelAndView("json_orddoc", "jsonArray", jsonArray);
	}
	
	
	@RequestMapping("/requireddoc")
	public ModelAndView requiredDocs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String orderId = request.getParameter("orderId");
		
		if (StringUtils.isEmpty(orderId)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing orderId");
			return null;
		}
		
		List<OrdDocAssgnDTO> docs = ordDocService.getRequiredDoc(orderId);
		
		JSONArray jsonArray = JSONArray.fromObject(docs, jsonConfig);
		return new ModelAndView("json_orddoc", "jsonArray", jsonArray);

		
	}
	
	
	
	
	class DateJsonValueProcessor implements JsonValueProcessor {

		SimpleDateFormat dateFormat;
		
		DateJsonValueProcessor(String format) {
			dateFormat = new SimpleDateFormat(format);
		}
		public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			// TODO Auto-generated method stub
			return null;
		}

		public Object processObjectValue(String key, Object value,
				JsonConfig jsonConfig) {
			if (value == null) return null;
			return dateFormat.format(value);
		}
		
	}

}
