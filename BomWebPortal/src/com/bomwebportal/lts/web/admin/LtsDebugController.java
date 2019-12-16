package com.bomwebportal.lts.web.admin;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LtsDebugController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal (HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String attbId = request.getParameter("id");
		Object attbObj = request.getSession().getAttribute(attbId);

		if (StringUtils.isBlank(attbId) || attbObj == null) {
			return new ModelAndView("lts/admin/ltsdebug");
		}
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().serializeNulls().create();
		StringWriter sw = new StringWriter();
		sw.write(gson.toJson(attbObj));
		response.setContentType("application/json");
//		response.addHeader("Content-disposition","attachment; filename=" + attbId +".json");
		response.setHeader("Cache-Control", "private");			
		response.setContentLength( (int) sw.toString().length());
		response.getOutputStream().write(sw.toString().getBytes());
		response.getOutputStream().flush();		 
		response.getOutputStream().close();
		return null;
	}

}
