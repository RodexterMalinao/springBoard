package com.bomwebportal.lts.web.disconnect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileAcqDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.acq.LtsAcqAccountProfileService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.google.common.collect.Lists;

public class LtsTerminateAcctSelectionController extends SimpleFormController {

	private final String viewName = "lts/disconnect/ltsterminateacctselection";

	private LtsAcqAccountProfileService ltsAcqAccountProfileService;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		LtsTerminateServiceSelectionFormDTO form = orderCapture.getLtsTerminateServiceSelectionForm();
		List<AccountDetailProfileAcqDTO> billableAcctList = new ArrayList<AccountDetailProfileAcqDTO>();
		List<AccountDetailProfileAcqDTO> acctList = new ArrayList<AccountDetailProfileAcqDTO>();
		
		String rmvSrvString = request.getParameter("rmvSrv");
		String rmvCallingCardAcctString = request.getParameter("rmvCallingCardAcct");
		
		billableAcctList = form.getBillableAcctList();
		
		if(billableAcctList != null){
			Map<String, List<AccountDetailProfileAcqDTO>> acctProfileMap = new HashMap<String, List<AccountDetailProfileAcqDTO>>();
				
			List<String> rmvSrvList = rmvSrvString == null? new ArrayList<String>() : Arrays.asList(rmvSrvString.split(","));
			List<String> rmvCallingCardAcctList = rmvCallingCardAcctString == null? new ArrayList<String>() : Arrays.asList(rmvCallingCardAcctString.split(","));
			
			billableAccLoop:
			for(AccountDetailProfileAcqDTO billableAcc: billableAcctList){
				
				List<AccountDetailProfileAcqDTO> acctProfileList = acctProfileMap.get(billableAcc.getAcctNum());
				if(acctProfileList == null){
					acctProfileList = new ArrayList<AccountDetailProfileAcqDTO>();
					acctProfileMap.put(billableAcc.getAcctNum(), acctProfileList);
				}
				
				//check if removing srv = billing acct srv
				if(rmvSrvList.contains(billableAcc.getSrvNum())){
					continue billableAccLoop;
				}

				//check if removing calling card acct = billing acct
				if(rmvCallingCardAcctList.contains(billableAcc.getAcctNum())
						&& LtsConstant.SERVICE_TYPE_ITS.equals(billableAcc.getServiceType())){
					continue billableAccLoop;
				}
				
				//if all removing srv / calling card acct do not match, allow acct to be selected as new DCA
				String chargeType = LtsConstant.CUSTOMER_ACCOUNT_CHARGE_TYPE_MAP.get(billableAcc.getChargeType());
				if(StringUtils.isNotBlank(chargeType)){
					billableAcc.setDisplayChargeType(chargeType);
				}
				acctProfileList.add(billableAcc);
//					acctList.add(billableAcc);
			}
			
			for(String key: acctProfileMap.keySet()){
				List<AccountDetailProfileAcqDTO> sameAcctProfileList = acctProfileMap.get(key);
				AccountDetailProfileAcqDTO mergedAcctDisplayDto = new AccountDetailProfileAcqDTO();
				mergedAcctDisplayDto.setAcctNum(key);
				for(AccountDetailProfileAcqDTO acctProfile: sameAcctProfileList){
					if(StringUtils.isBlank(mergedAcctDisplayDto.getSrvNum())){
						mergedAcctDisplayDto.setSrvNum(acctProfile.getSrvNum());
					}else{
						mergedAcctDisplayDto.setSrvNum(mergedAcctDisplayDto.getSrvNum() 
								+ "<br/>" + acctProfile.getSrvNum());
					}

					if(StringUtils.isBlank(mergedAcctDisplayDto.getDisplayChargeType())){
						mergedAcctDisplayDto.setDisplayChargeType(acctProfile.getDisplayChargeType());
					}else{
						mergedAcctDisplayDto.setDisplayChargeType(mergedAcctDisplayDto.getDisplayChargeType() 
								+ "<br/>" + acctProfile.getDisplayChargeType());
					}

					if(StringUtils.isBlank(mergedAcctDisplayDto.getServiceType())){
						mergedAcctDisplayDto.setServiceType(acctProfile.getServiceType());
					}else{
						mergedAcctDisplayDto.setServiceType(mergedAcctDisplayDto.getServiceType() 
								+ "<br/>" + acctProfile.getServiceType());
					}
				}
				acctList.add(mergedAcctDisplayDto);
			}
		
		}
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("acctList", acctList);
		mav.addObject("ffpIdx", request.getParameter("ffpIdx"));
		mav.addObject("sbuid", request.getParameter("sbuid"));
		return mav;
	}
	
	public LtsAcqAccountProfileService getLtsAcqAccountProfileService() {
		return ltsAcqAccountProfileService;
	}

	public void setLtsAcqAccountProfileService(
			LtsAcqAccountProfileService ltsAcqAccountProfileService) {
		this.ltsAcqAccountProfileService = ltsAcqAccountProfileService;
	}
}
