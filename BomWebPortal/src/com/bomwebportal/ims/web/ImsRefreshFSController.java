package com.bomwebportal.ims.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.GetCOrderDTO;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.ImsSummaryUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.COrderService;


public class ImsRefreshFSController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("in ImsRefreshFSController");

		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);

		JSONArray jsonArray = new JSONArray();
		
		String type = request.getParameter("type");
		
		logger.info("type is "+type);
		
		if (type.equalsIgnoreCase("FS")){
			
			logger.info("Refresh fax serial");
		
			int listsize = sessionOrder.getImsCollectDocDtoList().size();
		
			String array = "";
		

		
			for (int i=0;i<listsize;i++){
				array = "\",\"fsdoctype\":\"" + sessionOrder.getImsCollectDocDtoList().get(i).getDocTypeDisplay() +
		    			"\",\"fswaivereason\":\""; 
				if(sessionOrder.getImsCollectDocDtoList().get(i).getWaiveReason()!=null){
					array = array + sessionOrder.getImsCollectDocDtoList().get(i).getWaiveReason();
				}else{
					array = array + "--";
				}
				array = array + "\",\"fscolind\":\"" + sessionOrder.getImsCollectDocDtoList().get(i).getCollectedInd() +
								"\",\"fsfsnum\":\"" + sessionOrder.getImsCollectDocDtoList().get(i).getFaxSerial() +
								"\",\"i\":\"" + i;
			
				jsonArray.add("{\"listSize\":\"" +listsize
						+ array
						+ "\"}");
			}	
		
			logger.info(jsonArray);
			}
		
		if (type.equalsIgnoreCase("edf")){
			
			String edf = sessionOrder.getEdfRef();
			jsonArray.add("{\"edf\":\"" +edf
					+ "\"}");
		
			logger.info(jsonArray);
			
		}
		
		return new ModelAndView("ajax_imsrefreshfs", "jsonArray", jsonArray);
				
		
	}

}

