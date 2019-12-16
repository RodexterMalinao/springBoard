package com.bomwebportal.lts.web.disconnect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileAcqDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.acq.LtsAcqAccountProfileService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class LtsTerminateServiceAjaxController implements Controller {
	
	private LtsAcqAccountProfileService ltsAcqAccountProfileService;
	
	protected final Log logger = LogFactory.getLog(getClass());

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("LtsTerminateServiceAjaxController handleRequest: ");

		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		String acctNum = request.getParameter("acctNum");
		String rmvSrvString = request.getParameter("rmvSrv");
		String rmvCallingCardAcctString = request.getParameter("rmvCallingCardAcct");
		ServiceDetailProfileLtsDTO ltsServiceProfile = orderCapture.getLtsServiceProfile();
		AccountDetailProfileLtsDTO[] profileAccounts = ltsServiceProfile.getAccounts();

		String custNum = null;
		
		if (ArrayUtils.isNotEmpty(profileAccounts)) {
			for (AccountDetailProfileLtsDTO profileAccount : profileAccounts) {
				if (StringUtils.containsIgnoreCase(profileAccount.getAcctChrgType(), LtsConstant.ACCOUNT_CHARGE_TYPE_R)) {
					custNum = profileAccount.getCustomerProfile().getCustNum();
				}
			}
		}
		List<AccountDetailProfileAcqDTO> custAcctList = this.ltsAcqAccountProfileService.getAcctListByCustNum(custNum);
		
//		List<AccountDetailProfileAcqDTO> billableAcctList = orderCapture.getLtsTerminateServiceSelectionForm().getBillableAcctList();
		List<String> rmvSrvList = rmvSrvString == null? new ArrayList<String>() : Arrays.asList(rmvSrvString.split(","));
		List<String> rmvCallingCardAcctList = rmvCallingCardAcctString == null? new ArrayList<String>() : Arrays.asList(rmvCallingCardAcctString.split(","));
		
		
		//Map all profiles by every account
		Map<String, List<AccountDetailProfileAcqDTO>> acctProfileMap = new HashMap<String, List<AccountDetailProfileAcqDTO>>();
		Set<String> acctWithSrvList = new HashSet<String>();
		if(custAcctList != null && custAcctList.size() > 0){
			for(AccountDetailProfileAcqDTO acctProfile : custAcctList ){
				List<AccountDetailProfileAcqDTO> acctProfileList = acctProfileMap.get(acctProfile.getAcctNum());
				if(acctProfileList == null){
					acctProfileList = new ArrayList<AccountDetailProfileAcqDTO>();
					acctProfileMap.put(acctProfile.getAcctNum(), acctProfileList);
				}
				if(rmvSrvList.contains(acctProfile.getSrvNum())){
					continue;
				}
				if(rmvCallingCardAcctList.contains(acctProfile.getAcctNum())
						&& LtsConstant.SERVICE_TYPE_ITS.equals(acctProfile.getServiceType())){
					continue;
				}
				if(orderCapture.getLtsServiceProfile().getSrvNum().equals(acctProfile.getSrvNum())){
					continue;
				}
				acctProfileList.add(acctProfile);
				acctWithSrvList.add(acctProfile.getAcctNum());
			}
		}
		
		JsonArray jsonArray = new JsonArray();
		for(String key: acctProfileMap.keySet()){
			if(key.equals(acctNum) || "ALL".equals(acctNum)){
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("acctNum", key);
				if(acctProfileMap.get(key)!= null && acctProfileMap.get(key).size() > 0){
					jsonObj.addProperty("action", "RETAIN");
				}else{
					if(acctWithSrvList.size() > 0){
						jsonObj.addProperty("action", "CHANGE_DCA");
					}else{
						jsonObj.addProperty("action", "REMOVE");
					}
				}
				jsonArray.add(jsonObj);
			}
		}
		

		return new ModelAndView("ajax_", "json", jsonArray.toString());
//		return null;
	}

	public LtsAcqAccountProfileService getLtsAcqAccountProfileService() {
		return ltsAcqAccountProfileService;
	}

	public void setLtsAcqAccountProfileService(
			LtsAcqAccountProfileService ltsAcqAccountProfileService) {
		this.ltsAcqAccountProfileService = ltsAcqAccountProfileService;
	}

}
