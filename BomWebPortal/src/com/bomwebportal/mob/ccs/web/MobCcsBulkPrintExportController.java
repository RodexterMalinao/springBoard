package com.bomwebportal.mob.ccs.web;

import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class MobCcsBulkPrintExportController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*Map<String, String[]> records = new LinkedHashMap<String, String[]>();*/
		String[] requestOrderIds = request.getParameterValues("orderIds");
		String[] requestFormLangs = request.getParameterValues("formLangs");
		String[] requestOrderTypes = request.getParameterValues("orderTypes");
		String requestSearchLocations = request.getParameter("searchLocations");
		String[] orderId = new String[requestOrderIds.length];
		String[] formLangs = new String[requestFormLangs.length];
		String[] orderTypes = new String[requestOrderTypes.length];
		
		if (StringUtils.isNotEmpty(requestSearchLocations)){
			requestSearchLocations =  requestSearchLocations.trim();
		}
	
		
		if (requestOrderIds != null && requestFormLangs != null && requestOrderIds.length == requestFormLangs.length) {
			for (int i = 0; i < requestOrderIds.length; i++) {
				orderId[i] = StringUtils.trim(requestOrderIds[i]);
				formLangs[i] = StringUtils.trim(requestFormLangs[i]);
				orderTypes[i] = StringUtils.trim(requestOrderTypes[i]);
			}
		}
		String filename = (new SimpleDateFormat("yyyyMMdd_HHmm")).format(new Date()) + "_" + requestSearchLocations + ".csv";
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(response.getWriter());
			for (int i = 0; i < requestOrderIds.length; i++) {
				
				writer.write(orderId[i] + "," + formLangs[i] + "," + orderTypes[i]);
				writer.newLine();
			}
			writer.close();
			writer = null;
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
		}
		return null;
	}
}
