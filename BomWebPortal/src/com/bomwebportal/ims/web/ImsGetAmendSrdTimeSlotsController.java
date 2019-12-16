package com.bomwebportal.ims.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;

public class ImsGetAmendSrdTimeSlotsController implements Controller{

	protected final Log logger = LogFactory.getLog(getClass());
	
    public static final List<String> ponSlots = new ArrayList<String>(){{
        add("18:00-20:00");
        add("09:00-13:00");
        add("14:00-18:00");
        add("09:00-18:00");
    }};
    
    public static final List<String> adslvdslSlots = new ArrayList<String>(){{
        add("14:00-16:00");
        add("16:00-18:00");
        add("18:00-20:00");
        add("20:00-22:00");
        add("09:00-13:00");
        add("14:00-18:00");
        add("09:00-18:00");
    }};
    
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String instDate = request.getParameter("date");
		logger.info("ApptDate:"+instDate);
		
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if (request.getSession().getAttribute("order_for_amendment") != null) 
			order = (OrderImsUI) request.getSession().getAttribute("order_for_amendment"); 
		
		List<String> timeSlots = getFSAmendTimeSlots(instDate, order);
		
		JSONArray jsonArray = new JSONArray();
		
		for(String slot : timeSlots){

			jsonArray.add("{\"tsdisplay\":\"" + slot
					+ "\",\"tsvalue\":\""	+ slot
					+ "\"}");
			
//			jsonArray.add("{\"tsdisplay\":\"" + slot
//					+ "\"}");
		}			
		
		return new ModelAndView("ajax_view", "jsonArray", jsonArray);
		
	}

	public static List<String> getFSAmendTimeSlots(String instDate, OrderImsUI order){
		List<String> timeSlots = new ArrayList<String>();
		
		if (order == null)  timeSlots.addAll(adslvdslSlots);
		else if (StringUtils.equals(order.getInstallAddress().getAddrInventory().getTechnology(), "PON")) timeSlots.addAll(ponSlots); 
		else timeSlots.addAll(adslvdslSlots);
		
		if (!StringUtils.isEmpty(instDate)) {
			for (int i =0; i< timeSlots.size();i++){
				try {
					String tmp = instDate + "/" + timeSlots.get(i).substring(0, 2); 
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH");
					Date t = null;
					t = formatter.parse(tmp);
					Calendar now = Calendar.getInstance();
					now.setTime(new Date());
					Calendar instTime = Calendar.getInstance();
					instTime.setTime(t);  
//					logger.info("now : " + now.getTime());
//					logger.info("instTime : " + instTime.getTime()); 
//					logger.info("instTime.before(now) : " + instTime.before(now));
					if(instTime.before(now)) {
						timeSlots.remove(i);
						i--;
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return timeSlots;
	}
}
