package com.bomwebportal.web.qc;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.service.qc.ImsDSQCService;



public class GenImsDSQcComXlsController implements Controller {
	protected final Log logger = LogFactory.getLog(getClass());
	private ImsDSQCService imsDSQCService;

	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}

	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		logger.info("In GenImsDSQcComXlsController");
		
		
		List<ImsAlertMsgDTO>  imsOrderList=(List<ImsAlertMsgDTO>)request.getSession().getAttribute("imsOrderList");
		
		String fileName = SetFileName("ImsDirectSalesQAOrderList");
		
		String headerKey = "Content-Disposition";
	    String headerValue = String.format("attachment; filename=\"%s\"", fileName );
	    response.setHeader(headerKey, headerValue);
	    response.setContentType("application/vnd.ms-excel");	
	    
//	    JSONArray jsonArray = new JSONArray();    
	    
	  
		OutputStream outputStream = response.getOutputStream();
		imsDSQCService.genImsDSQcComXls(imsOrderList, outputStream);
		outputStream.flush();
		outputStream.close();
		
		logger.info("In GenImsDSQcComXlsController end");
		
		// TODO Auto-generated method stub
		return null;
		
//		jsonArray.add("{\"success\":" + "Y"
//				+ "}");
		
//		return new ModelAndView("ajax_genimsdsqccomxls", "jsonArray", jsonArray);
	}
	private String SetFileName(String str){
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_Hmmss");
		Date date = new Date();
		return str+"_" + dateFormat.format(date)+ ".xls";
	}
	
}
