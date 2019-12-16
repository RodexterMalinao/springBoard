package com.bomltsportal.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.service.ConnectionCheckService;
import com.bomwebportal.bean.LookupTableBean;
import com.pccw.wq.service.ConstantLkupService;

public class ConnectionCheckController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private static boolean isProcessing;
	private ConnectionCheckService connectionCheckService;
//	private ConstantLkupService lkupService;
	private final String LOOKUP_DESC_YES = "Y";
	private String[] lookupKeys = {"CHECK_SPRINGBOARD_CONNECTION", "CHECK_BOM_CONNECTION", "CHECK_UAMS_CONNECTION", "CHECK_BOM_WEB_SERVICE_CONNECTION", "CHECK_CEKS_CONNECTION", "CHECK_DATA_FILE_CONNECTION"};
	private String[] enableFlags = {"BomWebPortalDSIsChecked", "BomDSIsChecked", "UamsDSIsChecked", "BomWebServiceIsChecked", "CeksIsChecked", "DataFileIsChecked"};
	private String[] resultKeys = {"BomWebPortalDS", "BomDS", "UamsDS", "BomWebService", "Ceks", "DataFile"};
		
//	public ConstantLkupService getLkupService() {
//		return lkupService;
//	}
//
//	public void setLkupService(ConstantLkupService lkupService) {
//		this.lkupService = lkupService;
//	}

	public ConnectionCheckService getConnectionCheckService() {
		return connectionCheckService;
	}

	public void setConnectionCheckService(ConnectionCheckService connectionCheckService) {
		this.connectionCheckService = connectionCheckService;
	}


	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		logger.info("ConnectionCheck request recieved");
		
		ModelAndView mav = new ModelAndView("connectioncheck");
		
		
		if(!isProcessing){
			isProcessing = true;

//			if(!StringUtils.isEmpty((String)arg0.getParameter("reloadlkup")))
//				 lkupService.reloadLkUp();
			
			
//			Map<String, String> lookup = LookupTableBean.getInstance().getImsConnectionCheckLookupMap();
		

			boolean bomWebPortalConnected = this.connectionCheckService.IsBomWebPortalDSConnected();
			boolean BomConnected = this.connectionCheckService.IsBomDSConnnected();
			boolean UamsConnected = this.connectionCheckService.IsUamsDSConnected();
			boolean cspReady = this.connectionCheckService.IsCspReady();
			boolean ceksReady = this.connectionCheckService.IsCeksReady();
			boolean dataFileReady = this.connectionCheckService.IsDataFileReady();
			
			boolean[] connectionInds = {bomWebPortalConnected, BomConnected, UamsConnected, cspReady, dataFileReady, ceksReady};

			int connectionCheck = connectionInds.length;
			int connectionFail = 0;
			
			for(boolean connectionInd : connectionInds){
				if(!connectionInd){
					connectionFail++;
				}
			}

			mav.addObject("BomWebPortalDS", bomWebPortalConnected);
			mav.addObject("BomDS", BomConnected);
			mav.addObject("UamsDS", UamsConnected);
			mav.addObject("Csp", cspReady);
			mav.addObject("Ceks", ceksReady);
			mav.addObject("DataFile", dataFileReady);
			
			mav.addObject("connectionChecked", Integer.toString(connectionCheck));
			mav.addObject("connectionFail", Integer.toString(connectionFail));
	    	isProcessing = false;    	
		}
		else{
			logger.info("previous request is processing");
		}

		mav.addObject("isProcessing",isProcessing);
		logger.info("ConnectionCheck request completed");
		
		
		return mav;
		
		
	}
}
