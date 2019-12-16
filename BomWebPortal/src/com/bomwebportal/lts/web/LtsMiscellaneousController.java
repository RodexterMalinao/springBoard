package com.bomwebportal.lts.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsMiscellaneousFormDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsMiscellaneousController extends SimpleFormController {

	private final String viewName = "ltsmiscellaneous";
	private final String nextView = "ltsaddressrollout.html";
	private final String commandName = "ltsMiscellaneousCmd";
	private final String recontractView = "ltsrecontract.html";
	private final String dnchangeView = "ltsdnchange.html";
	
	private LtsAcqDnPoolService ltsAcqDnPoolService;
	private CodeLkupCacheService ltsEffStartDateLkupCacheService;
	public LtsAcqDnPoolService getLtsAcqDnPoolService() {
		return ltsAcqDnPoolService;
	}

	public void setLtsAcqDnPoolService(LtsAcqDnPoolService ltsAcqDnPoolService) {
		this.ltsAcqDnPoolService = ltsAcqDnPoolService;
	}

	private CodeLkupCacheService waiveDFormReasonLkupCacheService;
	
	public CodeLkupCacheService getWaiveDFormReasonLkupCacheService() {
		return waiveDFormReasonLkupCacheService;
	}

	public void setWaiveDFormReasonLkupCacheService(
			CodeLkupCacheService waiveDFormReasonLkupCacheService) {
		this.waiveDFormReasonLkupCacheService = waiveDFormReasonLkupCacheService;
	}

	public LtsMiscellaneousController() {
		setCommandClass(LtsMiscellaneousFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null || orderCapture.getLtsServiceProfile() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		if(LtsSbOrderHelper.isStandaloneVasOrder(orderCapture)){
			orderCapture.setLtsMiscellaneousForm(new LtsMiscellaneousFormDTO());
			return new ModelAndView(new RedirectView(nextView));
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		LtsMiscellaneousFormDTO form = (LtsMiscellaneousFormDTO) command;
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		orderCapture.setLtsMiscellaneousForm(form);
		orderCapture.setLtsAddressRolloutForm(null);

		if(!form.isDnChange()){
			if(orderCapture.getLtsDnChangeForm() !=null ){ 
				String sessionId = orderCapture.getLtsDnChangeForm().getSessionId();
				ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_CONFIRMED, sessionId);
				ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, sessionId);
				ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_RESERVED, sessionId);
				orderCapture.setLtsDnChangeForm(null);
			}
			if(orderCapture.getLtsDnChangeDuplexForm() != null){
				String sessionId = orderCapture.getLtsDnChangeDuplexForm().getSessionId();
				ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, sessionId);
				ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_RESERVED, sessionId);
				ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_CONFIRMED, sessionId);
				orderCapture.setLtsDnChangeDuplexForm(null);
			}
			orderCapture.setApprovalLoginForm(null);
			orderCapture.setApprovalDuplexLoginForm(null);
		}
		if(form.isRecontract()){
			return new ModelAndView(new RedirectView(recontractView)); 
		}else{
			orderCapture.setLtsRecontractForm(null);
			if(form.isDnChange()){
				return new ModelAndView(new RedirectView(dnchangeView));
			}
		}
		return new ModelAndView(new RedirectView(nextView));
		
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException, DAOException {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		request.setAttribute("isEyeUpgradeToEye", isEyeUpgradeToEye(orderCapture));
		request.setAttribute("isBrCustomer", isBrCustomer(orderCapture));
		LtsMiscellaneousFormDTO form = orderCapture.getLtsMiscellaneousForm();
		
		if (form == null) {
			form = new LtsMiscellaneousFormDTO();
			initForm(orderCapture, form, bomSalesUser);
		}
		
		return form;
	}
	
	private void initForm(OrderCaptureDTO orderCapture, LtsMiscellaneousFormDTO form, BomSalesUserDTO bomSalesUser) throws DAOException {
		
		if (LtsConstant.EYE_TYPE_EYE1.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())
				|| LtsConstant.EYE_TYPE_EYE2.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			form.setProfilePremiumTp(true);
		}
		else if (orderCapture.getLtsServiceProfile().getItemDetails() != null 
				&& ArrayUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getItemDetails())) {
			for (ItemDetailProfileLtsDTO itemDetailProfileLts : orderCapture.getLtsServiceProfile().getItemDetails()) {
				if (itemDetailProfileLts.getItemDetail()  == null) {
					continue;
				}
				if(StringUtils.equals("Y", itemDetailProfileLts.getItemDetail().getIsPremiumTp()) && !LtsSbOrderHelper.isProfileItemExpired(itemDetailProfileLts)) {
					form.setProfilePremiumTp(true);
					break;
				}
			}
		}
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				&& (orderCapture.isChannelCs() || orderCapture.isChannelPremier())) {
			
			ItemDetailProfileLtsDTO profileTp = LtsSbOrderHelper.getProfileServiceTermPlan(orderCapture.getLtsServiceProfile());
			String profileContractEndDate = LtsSbOrderHelper.getProfileContractEndDate(orderCapture.getLtsServiceProfile());
			String expiredProfileContractEndDate = LtsSbOrderHelper.getExpiredContractEndDate(orderCapture.getLtsServiceProfile());
			String endedProfileContractEndDate = LtsSbOrderHelper.getEndedContractEndDate(orderCapture.getLtsServiceProfile());
			
			if (profileTp != null && StringUtils.isNotBlank(profileTp.getIddFreeMin())
					&& !StringUtils.equals("0", profileTp.getIddFreeMin())) {
				form.setAllowBackDateOrder(false);	
			}
			else if (StringUtils.isEmpty(profileContractEndDate)) {
				form.setAllowBackDateOrder(true);	
				
				int maxBackDateDays = -120;
				
				if (StringUtils.isNotEmpty(expiredProfileContractEndDate)) {
					int contractEndDateDiff = 
						LtsDateFormatHelper.getDateDiffInDays(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), expiredProfileContractEndDate, "dd/MM/yyyy") + 1;
					if (contractEndDateDiff  > maxBackDateDays) {
						maxBackDateDays = contractEndDateDiff;
					}
				}
				if (StringUtils.isNotEmpty(endedProfileContractEndDate)) {
					int contractEndDateDiff = 
						LtsDateFormatHelper.getDateDiffInDays(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), endedProfileContractEndDate, "dd/MM/yyyy") + 1;
					if (contractEndDateDiff  > maxBackDateDays) {
						maxBackDateDays = contractEndDateDiff;
					}
				}
				
				form.setMaxBackDateDays(String.valueOf(maxBackDateDays));
			}
		}
		
		// Bar TERM PLAN ---Max.R.MENG 
		if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())
				&& !LtsConstant.CHANNEL_CD_CUSTOMER_SERVICE.equals(bomSalesUser.getChannelCd())){
			
			ItemDetailProfileLtsDTO profileTermPlan = LtsSbOrderHelper.getProfileServiceTermPlan(orderCapture.getLtsServiceProfile());
            if(profileTermPlan != null){
            	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    			String effStartDate = profileTermPlan.getConditionEffStartDate();
    			form.setBarUpgradeEye(false);
    			String dateToCompare = null;
    			if(form.isBackDateOrder() && form.getBackDate() != null){
    				dateToCompare = form.getBackDate();
    			}else{
    				dateToCompare = form.getApplicationDate();
    			}
    			if(effStartDate != null && dateToCompare != null){
    				try {
    					Date date = formatter.parse(effStartDate);
    					Date applicationDate = formatter.parse(dateToCompare);
    					
    					if(ltsEffStartDateLkupCacheService.getCodeLkupDAO().getCodeLkup()[0] != null){
    						form.setTermPlanBarSlot(ltsEffStartDateLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey());
        					int month = Integer.parseInt(form.getTermPlanBarSlot());
        					Date dateAfterBarSlot = DateUtils.addMonths(date, month);           					            				
        					if(dateAfterBarSlot.after(applicationDate)){
        						form.setBarUpgradeEye(true);
        					}
    					}    				
    				} catch (ParseException e) {
    					e.printStackTrace();
    				}
    			}		
            }
		}
		
		// Only DEL Retention Team can select submit D-Form option
		form.setAllowSubmitDisForm(LtsConstant.TEAM_AREA_CD_DEL_RETENTION.equalsIgnoreCase(bomSalesUser.getAreaCd()));
	}
	
	
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		referenceData.put("waiveDFormReasonList", Arrays.asList(waiveDFormReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		return referenceData;
	}
	
	
	private boolean isEyeUpgradeToEye(OrderCaptureDTO orderCapture){
		if(orderCapture.getLtsServiceProfile().getExistEyeType() == null
				&& ( orderCapture.getLtsServiceProfile().getSrvGrp() == null
						|| StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getSrvGrp().getGrpId()) )){
			return false;
		}
		
		return true;
	}
	
	private boolean isBrCustomer(OrderCaptureDTO orderCapture){
		return LtsConstant.DOC_TYPE_HKBR.equals(orderCapture.getLtsServiceProfile().getPrimaryCust().getDocType());
	}

	public CodeLkupCacheService getLtsEffStartDateLkupCacheService() {
		return ltsEffStartDateLkupCacheService;
	}

	public void setLtsEffStartDateLkupCacheService(
			CodeLkupCacheService ltsEffStartDateLkupCacheService) {
		this.ltsEffStartDateLkupCacheService = ltsEffStartDateLkupCacheService;
	}
}
