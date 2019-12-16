package com.bomwebportal.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.CnpDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.util.BomWebPortalConstant;

public class MrtSelectionAJAXController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private MnpService mnpService;
	public MnpService getMnpService() {
		return mnpService;
	}
	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String searchType = request.getParameter("searchType");
		String searchPattern = request.getParameter("pattern");
		String msisdnlvl = request.getParameter("msisdnlvl");
		String shopCd = request.getParameter("shopCd");
		String numType = request.getParameter("numType");
		
		List<CodeLkupDTO> mrtReturnQtyList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("MRT_RETURN_QTY");
		int returnQty = 0;
		if (mrtReturnQtyList != null && !mrtReturnQtyList.isEmpty()) {
			try{
				returnQty = Integer.parseInt(mrtReturnQtyList.get(0).getCodeId());
			}catch(Exception e){
				returnQty = 0;
			}
		} 
		
		/*String numType = "B";	
		List<CodeLkupDTO> cdLkupList = LookupTableBean.getInstance().getCodeLkupList().get("MRT_NUM_TYPE");
			
		if (cdLkupList != null&& !cdLkupList.isEmpty()) {						
			numType = (cdLkupList.get(0).getCodeId());	
		} */
		
	
		JSONArray jsonArray = new JSONArray();
		
		if (StringUtils.isNotEmpty(searchType)) {
			if ("random".equals(searchType)){
				
				List<CnpDTO> output  = mnpService.getNewMsisdn(searchPattern,BomWebPortalConstant.CNM_STATUS_NORMAL, shopCd, "R", numType, returnQty);
				if (output != null && !output.isEmpty()){
					for (CnpDTO temp : output){
							jsonArray.add("{\"msisdn\":\""	+ temp.getMsisdn()
									+ "\",\"numType\":\""	+ temp.getNumType()
							    	+ "\",\"msisdnlvl\":\""	+ temp.getLevel() + "\"}");
					}	
				}
				logger.info("jsonArray : " + jsonArray);
			}else if ("pattern".equals(searchType)){
				if (StringUtils.isNotEmpty(searchPattern) && StringUtils.isNotEmpty(msisdnlvl)) {
					
					List<CnpDTO> output  = mnpService.getNewMsisdn(searchPattern, BomWebPortalConstant.CNM_STATUS_NORMAL, shopCd, msisdnlvl, numType, returnQty);
					if (output != null && !output.isEmpty()){
						for (CnpDTO temp : output){
							jsonArray.add("{\"msisdn\":\""	+ temp.getMsisdn()
							+ "\",\"numType\":\""	+ temp.getNumType()
					    	+ "\",\"msisdnlvl\":\""	+ temp.getLevel() + "\"}");
								
						}	
					}
					logger.info("jsonArray : " + jsonArray);
				}else{
					jsonArray.add("ERROR");
				}
			}else if ("1c2npattern".equals(searchType)){
				String cityCd = request.getParameter("cityCd");
				if (StringUtils.isNotEmpty(searchPattern)) {
					
					List<CnpDTO> output  = mnpService.getNew1C2NMsisdn(searchPattern, BomWebPortalConstant.CNM_STATUS_NORMAL, shopCd, "R", cityCd, numType, returnQty);
					if (output != null && !output.isEmpty()){
						for (CnpDTO temp : output){
							jsonArray.add("{\"msisdn\":\""	+ temp.getMsisdn()
							+ "\",\"numType\":\""	+ temp.getNumType()
							+ "\",\"cityCd\":\""	+ temp.getCity()
							+ "\",\"msisdnStatus\":\""	+ temp.getStatus()
							+ "\",\"srvNumType\":\""	+ temp.getType()
							+ "\",\"shopCd\":\""	+ temp.getDepartmentCode().substring(1)										
					    	+ "\",\"msisdnlvl\":\""	+ temp.getLevel() + "\"}");
						}	
					}
					logger.info("jsonArray : " + jsonArray);
				}else{
					jsonArray.add("ERROR");
				}
			}else if ("sspattern".equals(searchType)){
				if (StringUtils.isNotEmpty(searchPattern)) {
					
					List<CnpDTO> output  = mnpService.getNewSsMsisdn(searchPattern, BomWebPortalConstant.CNM_STATUS_NORMAL, shopCd, "R", numType, returnQty);
					if (output != null && !output.isEmpty()){
						for (CnpDTO temp : output){
							jsonArray.add("{\"msisdn\":\""	+ temp.getMsisdn()
							+ "\",\"numType\":\""	+ temp.getNumType()
							+ "\",\"cityCd\":\""	+ temp.getCity()
							+ "\",\"msisdnStatus\":\""	+ temp.getStatus()
							+ "\",\"srvNumType\":\""	+ temp.getType()
							+ "\",\"shopCd\":\""	+ temp.getDepartmentCode().substring(1)										
					    	+ "\",\"msisdnlvl\":\""	+ temp.getLevel() + "\"}");
						}	
					}
					logger.info("jsonArray : " + jsonArray);
				}else{
					jsonArray.add("ERROR");
				}
			}
			
		}else{
			jsonArray.add("ERROR");
		}

		return new ModelAndView("ajax_mrtselection", "jsonArray",	jsonArray);
		
	}


}
