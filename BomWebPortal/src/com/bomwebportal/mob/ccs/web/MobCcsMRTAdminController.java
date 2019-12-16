package com.bomwebportal.mob.ccs.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.dto.GoldenMrtAdminDTO;
import com.bomwebportal.mob.ccs.service.GoldenMrtAdminService;

public class MobCcsMRTAdminController implements Controller {
    protected final Log logger = LogFactory.getLog(getClass());
    protected GoldenMrtAdminService goldenMrtAdminService;
    
	public GoldenMrtAdminService getGoldenMrtAdminService() {
		return goldenMrtAdminService;
	}

	public void setGoldenMrtAdminService(GoldenMrtAdminService goldenMrtAdminService) {
		this.goldenMrtAdminService = goldenMrtAdminService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date startDate, endDate;
		if (StringUtils.isBlank(request.getParameter("startDate")) && StringUtils.isBlank(request.getParameter("endDate"))) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -7);
			startDate = calendar.getTime();
			
			endDate = new Date();
		} else {
			startDate = new Date();
			endDate = new Date();
			if (StringUtils.isNotBlank(request.getParameter("startDate"))) {
				try {
					startDate = (Date) sdf.parse(request.getParameter("startDate"));
				} catch (ParseException e) {}
			}
			if (StringUtils.isNotBlank(request.getParameter("endDate"))) {
				try {
					endDate = (Date) sdf.parse(request.getParameter("endDate"));
				} catch (ParseException e) {}
			}
		}
		List<GoldenMrtAdminDTO> recordList = goldenMrtAdminService.getGoldenMrtAdminDTOByDate(startDate, endDate);
    	ModelAndView modelAndView = new ModelAndView("mobccsmrtadmin");
    	modelAndView.addObject("startDate", sdf.format(startDate));
    	modelAndView.addObject("endDate", sdf.format(endDate));
    	modelAndView.addObject("recordList", recordList);
    	return modelAndView;
    }
}
