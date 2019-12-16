package com.bomwebportal.lts.web.acq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.profile.CustPdpoProfileDTO;
import com.bomwebportal.lts.service.bom.CustPdpoProfileService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAcqCustomerInfoLkupController implements Controller{
	private CustPdpoProfileService custPdpoProfileService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		if (order == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}

		String custNum = request.getParameter("cust_num");
		
		CustPdpoProfileDTO[] dataInfoList = custPdpoProfileService.getCustDataPrivacyInfo(custNum, LtsConstant.SYSTEM_ID_DRG);
		
		boolean showPdpoStatement = false;
		boolean isOptIndEmpty = true;
		String ltsStatus = "";
		String iddStatus = "";
		
		if(dataInfoList != null && dataInfoList.length > 0){
			for(CustPdpoProfileDTO dataInfo : dataInfoList){
				if(StringUtils.isNotBlank(dataInfo.getOptInd())){
					isOptIndEmpty = false;
				}
				
				if(StringUtils.isNotBlank(dataInfo.getOptInd()) 
						&& LtsConstant.DATA_PRIVACY_OPT_IND_OOA_CD.equals(dataInfo.getOptInd())){
					showPdpoStatement = true;
				}
				
				if(LtsConstant.DATA_PRIVACY_BOM_LOB_LTS.equals(dataInfo.getLob())){
					ltsStatus = LtsConstant.DATA_PRIVACY_STATUS_MAP.get(dataInfo.getOptInd());
				}
				
				if(LtsConstant.DATA_PRIVACY_BOM_LOB_IDD.equals(dataInfo.getLob())){
					iddStatus = LtsConstant.DATA_PRIVACY_STATUS_MAP.get(dataInfo.getOptInd());
				}
			}
			
			if(!showPdpoStatement && isOptIndEmpty){
				showPdpoStatement = true;
			}
			
			if(StringUtils.isBlank(ltsStatus) || StringUtils.isBlank(iddStatus)){
				showPdpoStatement = true;
			}
		}
		else{		// No PDPO records
			showPdpoStatement = true;
		}

		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isNotBlank(custNum)){
			jsonObject.put("result", showPdpoStatement);
			jsonObject.put("ltsStatus", (StringUtils.isBlank(ltsStatus)) ? "NEW" : ltsStatus);
			jsonObject.put("iddStatus", (StringUtils.isBlank(iddStatus)) ? "NEW" : iddStatus);
		}
		else{
			jsonObject.put("result", true);
			jsonObject.put("ltsStatus", "NEW");
			jsonObject.put("iddStatus", "NEW");
		}
		
		return new ModelAndView("ajax_view", jsonObject);
	}

	public CustPdpoProfileService getCustPdpoProfileService() {
		return custPdpoProfileService;
	}

	public void setCustPdpoProfileService(
			CustPdpoProfileService custPdpoProfileService) {
		this.custPdpoProfileService = custPdpoProfileService;
	}

}
