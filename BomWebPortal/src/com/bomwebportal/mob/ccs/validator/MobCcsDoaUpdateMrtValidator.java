package com.bomwebportal.mob.ccs.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsOrderFalloutDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.service.MobCcsDoaUpdateMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsDoaUpdateMrtService.AmendScenario;
import com.bomwebportal.mob.ccs.service.MobCcsDoaUpdateMrtService.MnpInd;
import com.bomwebportal.mob.ccs.service.MobCcsDoaUpdateMrtService.Team;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderFalloutService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderSearchService;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ccs.web.MobCcsDoaUpdateMrtController.DoaUpdateMrtUI;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.DateUtils;
import com.bomwebportal.util.Utility;

public class MobCcsDoaUpdateMrtValidator implements Validator {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private OrderService orderService;
	private MobCcsMrtService mobCcsMrtService;
	private MnpService mnpService;
	private MobCcsOrderSearchService mobCcsOrderSearchService;
	private MobCcsDoaUpdateMrtService mobCcsDoaUpdateMrtService;
	private MobCcsOrderFalloutService mobCcsOrderFalloutService;
	private ValidateService validateService;
	
	
	private String mobile2GRetiredInd;
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}

	public MnpService getMnpService() {
		return mnpService;
	}

	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}

	public MobCcsOrderSearchService getMobCcsOrderSearchService() {
		return mobCcsOrderSearchService;
	}

	public void setMobCcsOrderSearchService(
			MobCcsOrderSearchService mobCcsOrderSearchService) {
		this.mobCcsOrderSearchService = mobCcsOrderSearchService;
	}

	public MobCcsDoaUpdateMrtService getMobCcsDoaUpdateMrtService() {
		return mobCcsDoaUpdateMrtService;
	}

	public void setMobCcsDoaUpdateMrtService(
			MobCcsDoaUpdateMrtService mobCcsDoaUpdateMrtService) {
		this.mobCcsDoaUpdateMrtService = mobCcsDoaUpdateMrtService;
	}

	public String getMobile2GRetiredInd() {
		return mobile2GRetiredInd;
	}

	public void setMobile2GRetiredInd(String mobile2gRetiredInd) {
		mobile2GRetiredInd = mobile2gRetiredInd;
	}

	public MobCcsOrderFalloutService getMobCcsOrderFalloutService() {
		return mobCcsOrderFalloutService;
	}

	public void setMobCcsOrderFalloutService(MobCcsOrderFalloutService mobCcsOrderFalloutService) {
		this.mobCcsOrderFalloutService = mobCcsOrderFalloutService;
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public boolean supports(Class clazz) {
		return DoaUpdateMrtUI.class.equals(clazz);
	}

	public void validate(Object command, Errors errors) {
		DoaUpdateMrtUI form = (DoaUpdateMrtUI) command;
		OrderDTO orderDto = this.orderService.getOrder(form.getOrderId());
		Boolean isFulfillment = "Y".equalsIgnoreCase(form.getFulfillmentAuth());
		if (!"MOB".equals(orderDto.getOrderSumLob())) {
			errors.rejectValue("orderId", null, "Not MOB order");
		}
		// no change in mrt info, only update delivery info
		boolean allowUpdateSRD = this.mobCcsDoaUpdateMrtService.allowUpdateSRD(orderDto, form.getChannelCd());
		boolean allowUpdateMnp = this.mobCcsDoaUpdateMrtService.allowUpdateMnp(orderDto, form.getChannelCd());
		if (!(allowUpdateSRD || allowUpdateMnp)) {
			return;
		}

		MRTUI currentMrtUI = mobCcsDoaUpdateMrtService.getMrtUI(form.getOrderId());

		/*if (!"N".equals(currentMrtUI.getChinaInd())) {
			errors.rejectValue("orderId", null, "Only allow HK Phone order");
		}*/
		
		MnpInd mnpInd = MnpInd.valueOf(currentMrtUI.getMnpInd());

		// fallout to sales, no update in mrt info
		if (form.isByPassValidation()) {
			if (!this.mobCcsDoaUpdateMrtService.allowFalloutToSales(orderDto, form.getChannelCd())) {
				errors.rejectValue("byPassValidation", null, "Not allow fallout to sales, channelCd: " + form.getChannelCd());
			}
			return;
		}

		AmendScenario amendScenario = mobCcsDoaUpdateMrtService.getAmendScenario(orderDto);
		if (amendScenario == null) {
			//errors.rejectValue("orderId", null, "No update order for status");
			// allow update delivery info without updating SRD/MNP info
			return;
		}
		Team team = this.mobCcsDoaUpdateMrtService.getTeam(form.getChannelCd());
		switch (mnpInd) {
		case N:
			this.validateNewNumber(orderDto, amendScenario, form, currentMrtUI, errors, team,isFulfillment);
			break;
		case Y:
			this.validateMnp(orderDto, amendScenario, form, currentMrtUI, errors, team,isFulfillment);
			break;
		case A:
			this.validateNewNumberAndMnp(orderDto, amendScenario, form, currentMrtUI, errors, team,isFulfillment);
			break;
		}
		
		// further checking
		if (errors.hasErrors() && !isFulfillment) {
			return;
		}
		
		switch (mnpInd) {
		case Y:
		case A:
			List<String> mnpUsedOrderIdList = mobCcsMrtService.getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout(form.getMnpMsisdn(), form.getOrderId());
			if (!this.isEmpty(mnpUsedOrderIdList)) {
				StringBuilder sb = new StringBuilder();
				for (String orderId : mnpUsedOrderIdList) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append(orderId);
				}
				errors.rejectValue("mnpMsisdn", null, "Mobile Number used in pending SB order: " + sb);
				return;
			}
			// 19 Mar 2013: no checking apply on doa update mrt
			//if (this.mnpService.isPccw3gPrepaidNumber(form.getMnpMsisdn())) {
			//	errors.rejectValue("mnpMsisdn", null, "This is a PCCW 3G prepaid number");
			//	return;
			//}
			
			//DENNIS MIP3
			MnpDTO result = new MnpDTO();
			result.setMnpMsisdn(form.getMnpMsisdn());
			if ("Prepaid Sim".equals(form.getCustName())) {
				result.setPrepaidSimInd(true);
				result.setCustIdDocNum(form.getDocNum());
			} else {
				result.setPrepaidSimInd(false);
			}
			com.bomwebportal.mob.validate.dto.ResultDTO validateResult = validateService.validateMNP(result, "mnpMsisdn", form.isIgnorePostpaidcheckbox(), "ignorePostpaidcheckbox");
		    if (validateResult.hasError()){
		    	validateService.bindErrors(validateResult, errors);
		    	return;
		    }else{
		    	form.setDno(result.getDno());
		    	form.setActualDno(result.getActualDno());
		    	form.setIgnorePostpaidcheckbox(result.isIgnorePostpaidcheckbox());
		    }
		    
			/*MnpDTO mnpDTO = new MnpDTO();
			mnpDTO.setMnpMsisdn(form.getMnpMsisdn());
			mnpDTO = this.mnpService.validateMnpMsisdn(mnpDTO);
			if (BomWebPortalConstant.DN_STR_ERR.equals(mnpDTO.getDno())) {
				errors.rejectValue("mnpMsisdn", null, "Invalid Mobile Number");
				return;
			}
			if (BomWebPortalConstant.DN_STR_PCCW3G.equals(mnpDTO.getDno())) {
				errors.rejectValue("mnpMsisdn", null, "Existing PCCW3G Mobile Number");
				return;
			}
			if ("Y".equalsIgnoreCase(mobile2GRetiredInd) && BomWebPortalConstant.DN_STR_PCCW2G.equals(mnpDTO.getDno())) {
				errors.rejectValue("mnpMsisdn", null, "Existing PCCW2G Mobile Number");
				return;
			}*/
			break;
		case N:
			break;
		}
	}
	
	private Date trunc(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	private Date getDate(int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, amount);
		return calendar.getTime();
	}
	
	private Date addDate(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, amount);
		return calendar.getTime();
	}
	
	private boolean isHKMobileNumValid(String msisdn, Errors errors) {
		if (StringUtils.isNotBlank(msisdn)
				&& Utility.validatePhoneNum(msisdn)
				&& Utility.validateHKMobilePrefix(msisdn)) {
			return true;
		}
		errors.rejectValue("mnpMsisdn", null, "Requires 8 digits and starts with 3, 5, 6, 8 or 9");
		return false;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	private void validateNewNumber(OrderDTO orderDto, AmendScenario amendScenario, DoaUpdateMrtUI form, MRTUI currentMrtUI, Errors errors, Team team, Boolean isFulfillment) {
		Calendar now = Calendar.getInstance();
		Date srvReqDate = Utility.string2Date(form.getServiceReqDateStr());
		Date currentSrvReqDate = Utility.string2Date(currentMrtUI.getServiceReqDateStr());
		// srvReqDate
		if (srvReqDate == null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceReqDateStr", null, "Requires Service Request Date");
			return;
		}
		// srvReqDate range
		Date minDate = null;
		Date maxDate = null;
		switch (amendScenario) {
		case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
		case SRD_EXP:
		case DOA:
		case SALES_FOLLOW_UP:
			minDate = getDate(now.get(Calendar.HOUR_OF_DAY) < 17 ? 0 : 1);
			maxDate = addDate(orderDto.getAppInDate(), 90);
			break;
		case ONSITE_DOA:
			minDate = getDate(1);
			maxDate = addDate(orderDto.getAppInDate(), 90);
			break;
		case MNP_REJECT:
		default:
		}
		// no access to session's doa delivery date, will check in Controller
		// check minDate / maxDate
		switch (amendScenario) {
		case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
		case ONSITE_DOA:
		case SRD_EXP:
			if (maxDate.before(trunc(now.getTime())) || maxDate.before(minDate)) {
				errors.rejectValue("serviceReqDateStr", null, "Today already behind max amendment date " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
			} else if (srvReqDate.before(minDate)){
				errors.rejectValue("serviceReqDateStr", null, "Requires Service Request Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
			} else if (srvReqDate.after(maxDate) && !isFulfillment) {
				errors.rejectValue("serviceReqDateStr", null, "Requires Service Request Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
			}
			break;
		case DOA:
			if (sameDate(currentSrvReqDate, srvReqDate)) {
				if (currentSrvReqDate.before(trunc(now.getTime()))) {
					// not allow back day
					errors.rejectValue("serviceReqDateStr", null, "Current Service Request Date is a back date");
				} 
			} else {
				// changed date -> need to bound by minDate
				if (maxDate.before(trunc(now.getTime())) || maxDate.before(minDate)) {
					errors.rejectValue("serviceReqDateStr", null, "Today already behind max amendment date " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				} else if (srvReqDate.before(minDate) || srvReqDate.after(maxDate)) {
					errors.rejectValue("serviceReqDateStr", null, "Requires Service Request Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				}
			}
			break;
		case SALES_FOLLOW_UP:
			// fallout from K001 / Jxxx
			if (maxDate.before(trunc(now.getTime())) || maxDate.before(minDate)) {
				errors.rejectValue("serviceReqDateStr", null, "Today already behind max amendment date " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
			} else if ((srvReqDate.before(minDate) || srvReqDate.after(maxDate)) && !isFulfillment) {
				errors.rejectValue("serviceReqDateStr", null, "Requires Service Request Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
			}
			break;
		case MNP_REJECT:
		default:
			errors.rejectValue("serviceReqDateStr", null, "[Unknown scenario]: cannot check Service Request Date, please contact SB Support.");
		}
	}
	
	private void validateMnp(OrderDTO orderDto, AmendScenario amendScenario, DoaUpdateMrtUI form, MRTUI currentMrtUI, Errors errors, Team team, Boolean isFulfillment) {
		//dennis enable mnp
		boolean enableMNP = true;
		List<CodeLkupDTO> enableMNPList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("MIP_SHOW_MNP_TICKET_BTN");
		if (CollectionUtils.isNotEmpty(enableMNPList)){
			for (CodeLkupDTO dto: enableMNPList){
				if ("N".equalsIgnoreCase(dto.getCodeId())){
					enableMNP = false;
					break;
				}
			}
		}
		
		Calendar now = Calendar.getInstance();
		Date cutOverDate = Utility.string2Date(form.getCutOverDateStr());
		Date currentCutOverDate = Utility.string2Date(currentMrtUI.getCutOverDateStr());
		// cutOverDate
		if (cutOverDate == null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cutOverDateStr", null, "Requires Cutover Date");
			return;
		}
		// cutOverDate range
		Date minDate = null;
		Date maxDate = null;

		/*MobCcsOrderFalloutDTO orderFalloutDto = mobCcsOrderFalloutService.getOrderFalloutByCat(orderDto.getOrderId(), "MNP_REJ");
		if (orderFalloutDto != null) {
			minDate = getDate(now.get(Calendar.HOUR_OF_DAY) < 15 ? 2 : 3);
			//DENNIS201606
			maxDate = DateUtils.dateAfterdays(orderFalloutDto.getOccuranceDate(), BomWebPortalConstant.CCS_UPDATE_MNP_FALLOUT_EXTEND_DAYS);
			if (addDate(orderDto.getAppInDate(), BomWebPortalConstant.CCS_UPDATE_MNP_APP_EXTEND_DAYS).after(maxDate)) {
				maxDate = addDate(orderDto.getAppInDate(), BomWebPortalConstant.CCS_UPDATE_MNP_APP_EXTEND_DAYS);
			}
		} else {*/
		//DENNIS201606 no longer depends on fallout date , app + 180
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case DOA:
				minDate = getDate(now.get(Calendar.HOUR_OF_DAY) < 15 ? 2 : 3);
				maxDate = addDate(orderDto.getAppInDate(), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
				if ("Y".equalsIgnoreCase(form.getMnpExtendAuthInd())){
					maxDate = DateUtils.dateAfterdays(maxDate, BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);
				}
				break;
			case MNP_REJECT:
				minDate = getDate(now.get(Calendar.HOUR_OF_DAY) < 15 ? 2 : 3);
				//maxDate = addDate(orderDto.getAppInDate(), 180);
				break;
			case SRD_EXP:
				minDate = getDate(now.get(Calendar.HOUR_OF_DAY) < 15 ? 2 : 3);
				//maxDate = addDate(orderDto.getAppInDate(), 180);
				break;
			case ONSITE_DOA:
				minDate = getDate(3);
				maxDate = addDate(orderDto.getAppInDate(), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
				if ("Y".equalsIgnoreCase(form.getMnpExtendAuthInd())){
					maxDate = DateUtils.dateAfterdays(maxDate, BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);
				}
				break;
			case SALES_FOLLOW_UP:
				minDate = getDate(now.get(Calendar.HOUR_OF_DAY) < 15 ? 2 : 3);
				maxDate = addDate(orderDto.getAppInDate(), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
				if ("Y".equalsIgnoreCase(form.getMnpExtendAuthInd())){
					maxDate = DateUtils.dateAfterdays(maxDate, BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);
				}
				break;
			}
		/*}*/
		
		// no access to session's doa delivery date, will check in Controller
		// check minDate / maxDate
		switch (amendScenario) {
		case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			switch (team) {
			case SUPPORT:
			{
				if (maxDate.before(trunc(now.getTime())) || maxDate.before(minDate)) {
					errors.rejectValue("cutOverDateStr", null, "Today already behind max amendment date " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				} else if (cutOverDate.before(minDate) ) {
					errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and "  + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				} else if (cutOverDate.after(maxDate)  && !isFulfillment){
					errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and "  + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				}
			}
				break;
			case SALES:
			{
				if (this.mobCcsDoaUpdateMrtService.isMnpWindowFrozen(orderDto)) {
					// cutOverDate is frozen
					// Today+1 not allow to update MNP date (disable fields and show a remarks "Not allow to update cutover date as MNP window frozen")
					if (!sameDate(currentCutOverDate, cutOverDate)) {
						errors.rejectValue("cutOverDateStr", null, "Not allow to update CutOver Date as MNP window frozen");
					}
					if (!currentMrtUI.getCutOverTime().equals(form.getCutOverTime())) {
						errors.rejectValue("cutOverTime", null, "Not allow to update CutOver Time as MNP window frozen");
					}
				} else {
					if (maxDate.before(trunc(now.getTime())) || maxDate.before(minDate)) {
						errors.rejectValue("cutOverDateStr", null, "Today already behind max amendment date " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
					} else if (cutOverDate.before(minDate)) {
						errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
					} else if (cutOverDate.after(maxDate) && !isFulfillment){
						errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
					}
				}
			}
				break;
			}
			break;
		case ONSITE_DOA:
			if (maxDate.before(trunc(now.getTime())) || maxDate.before(minDate)) {
				errors.rejectValue("cutOverDateStr", null, "Today already behind max amendment date " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
			} else if (cutOverDate.before(minDate) || cutOverDate.after(maxDate)) {
				errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
			}
			break;
		case MNP_REJECT:
			if (cutOverDate.before(minDate)) {
				errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date on or after " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT));
			}
			break;
		case SRD_EXP:
			if (cutOverDate.before(minDate)) {
				errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date on or after " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT));
			}
			break;
		case DOA:
			if (this.mobCcsDoaUpdateMrtService.isMnpWindowFrozen(orderDto)) {
				// Today+1 not allow to update MNP date (disable fields and show a remarks "Not allow to update cutover date as MNP window frozen")
				if (!sameDate(currentCutOverDate, cutOverDate)) {
					errors.rejectValue("cutOverDateStr", null, "Not allow to update CutOver Date as MNP window frozen");
				}
				if (!currentMrtUI.getCutOverTime().equals(form.getCutOverTime())) {
					errors.rejectValue("cutOverTime", null, "Not allow to update CutOver Time as MNP window frozen");
				}
			} else {
				if (sameDate(currentCutOverDate, cutOverDate)) {
					if (currentCutOverDate.before(trunc(now.getTime()))) {
						// not allow back day
						errors.rejectValue("cutOverDateStr", null, "Current Cutover Date is a back date");
					}
				} else {
					// changed date -> need to bound by minDate
					if (maxDate.before(trunc(now.getTime())) || maxDate.before(minDate)) {
						errors.rejectValue("cutOverDateStr", null, "Today already behind max amendment date " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
					} else if (cutOverDate.before(minDate) || cutOverDate.after(maxDate)) {
						errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
					}
				}
			}
			break;
		case SALES_FOLLOW_UP:
			if (this.mobCcsDoaUpdateMrtService.isMnpWindowFrozen(orderDto)) {
				// cutOverDate is frozen
				// Today+1 not allow to update MNP date (disable fields and show a remarks "Not allow to update cutover date as MNP window frozen")
				if (!sameDate(currentCutOverDate, cutOverDate)) {
					errors.rejectValue("cutOverDateStr", null, "Not allow to update CutOver Date as MNP window frozen");
				}
				if (!currentMrtUI.getCutOverTime().equals(form.getCutOverTime())) {
					errors.rejectValue("cutOverTime", null, "Not allow to update CutOver Time as MNP window frozen");
				}
			} else {
				if (maxDate.before(trunc(now.getTime())) || maxDate.before(minDate)) {
					errors.rejectValue("cutOverDateStr", null, "Today already behind max amendment date " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				} else if (cutOverDate.before(minDate)) {
					errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				} else if (cutOverDate.after(maxDate) && !isFulfillment){
					errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				}
			}
			break;
		default:
			errors.rejectValue("cutOverDateStr", null, "[Unknown scenario]: cannot check Cutover Date, please contact SB Support.");
		}
		// cutOverTime
		if (!this.isCutOverTimeValid(form.getCutOverTime(), errors)) {
			return;
		}
		if (errors.hasErrors()) {
			return;
		}
		if (this.isMnpCutOverTimeFrozen(cutOverDate, form.getCutOverTime())) {
			errors.rejectValue("cutOverTime", null, "This cutover time is frozen.");
		}
		if (errors.hasErrors()) {
			return;
		}
		// mnpMsisdn
		if (!this.isHKMobileNumValid(form.getMnpMsisdn(), errors)) {
			return;
		}
		// mnpTicketNum
		if (enableMNP){ //dennis enable mnp
			if (!this.isMnpTicketNumValid(form.getMnpTicketNum(), cutOverDate, form.getCutOverTime(), errors)) {
				return;
			}
		}
		// custName
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custName", null, "Requires Customer Name");
		// docNum
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docNum", null
				, "Prepaid Sim".equals(form.getCustName()) ? "Requires Prepaid Sim No." : "Requires Old Customer Identity No.");
	}
	
	private void validateNewNumberAndMnp(OrderDTO orderDto, AmendScenario amendScenario, DoaUpdateMrtUI form, MRTUI currentMrtUI, Errors errors, Team team, Boolean isFulfillment) {
		//dennis enable mnp
		boolean enableMNP = true;
		List<CodeLkupDTO> enableMNPList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("MIP_SHOW_MNP_TICKET_BTN");
		if (CollectionUtils.isNotEmpty(enableMNPList)){
			for (CodeLkupDTO dto: enableMNPList){
				if ("N".equalsIgnoreCase(dto.getCodeId())){
					enableMNP = false;
					break;
				}
			}
		}
		
		Calendar now = Calendar.getInstance();
		Date srvReqDate;
		
		if (this.mobCcsDoaUpdateMrtService.allowUpdateSRD(orderDto, form.getChannelCd())) {
			srvReqDate = Utility.string2Date(form.getServiceReqDateStr());
			Date currentSrvReqDate = Utility.string2Date(currentMrtUI.getServiceReqDateStr());
			// srvReqDate
			if (srvReqDate == null) {
				errors.rejectValue("serviceReqDateStr", null, "Requires Service Request Date");
				return;
			}
			// srvReqDate range
			Date minDate = null;
			Date maxDate = null;
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case SRD_EXP:
			case DOA:
			case SALES_FOLLOW_UP:
				minDate = getDate(now.get(Calendar.HOUR_OF_DAY) < 17 ? 0 : 1);
				maxDate = addDate(orderDto.getAppInDate(), 90);
				break;
			case ONSITE_DOA:
				minDate = getDate(1);
				maxDate = addDate(orderDto.getAppInDate(), 90);
				break;
			case MNP_REJECT:
				break;
			}
			// no access to session's doa delivery date, will check in Controller
			// check minDate / maxDate
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case ONSITE_DOA:
			case SRD_EXP:
			case SALES_FOLLOW_UP:
				if (maxDate.before(trunc(now.getTime())) || maxDate.before(minDate)) {
					errors.rejectValue("serviceReqDateStr", null, "Today already behind max amendment date " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				} else if (srvReqDate.before(minDate)) {
					errors.rejectValue("serviceReqDateStr", null, "Requires Service Request Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				} else if ( srvReqDate.after(maxDate) && !isFulfillment){
					errors.rejectValue("serviceReqDateStr", null, "Requires Service Request Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
				}
				break;
			case DOA:
				if (sameDate(currentSrvReqDate, srvReqDate)) {
					if (currentSrvReqDate.before(trunc(now.getTime()))) {
						// not allow back day
						errors.rejectValue("serviceReqDateStr", null, "Current Service Request Date is a back date");
					}
				} else {
					// changed date -> need to bound by minDate
					if (maxDate.before(trunc(now.getTime())) || maxDate.before(minDate)) {
						errors.rejectValue("serviceReqDateStr", null, "Today already behind max amendment date " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
					} else if (srvReqDate.before(minDate) || srvReqDate.after(maxDate)) {
						errors.rejectValue("serviceReqDateStr", null, "Requires Service Request Date between " + Utility.date2String(minDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(maxDate, BomWebPortalConstant.DATE_FORMAT));
					}
				}
				break;
			case MNP_REJECT:
				// only for new number + mnp
				break;
			default:
				errors.rejectValue("serviceReqDateStr", null, "[Unknown scenario]: cannot check Service Request Date, please contact SB Support.");
			}
		} else {
			srvReqDate = Utility.string2Date(currentMrtUI.getServiceReqDateStr());
		}
		
		if (errors.hasErrors()) {
			return;
		}
		Date cutOverDate = Utility.string2Date(form.getCutOverDateStr());
		Date currentCutOverDate = Utility.string2Date(currentMrtUI.getCutOverDateStr());
		// cutOverDate
		if (cutOverDate == null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cutOverDateStr", null, "Requires Cutover Date");
			return;
		}
		// cutOverDate range
		Date cutOverDateMinDate = this.addDate(srvReqDate, 3);
		Date cutOverDateMaxDate = this.addDate(srvReqDate, BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS);
		if (cutOverDate.before(cutOverDateMinDate)) {
			errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date between " + Utility.date2String(cutOverDateMinDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(cutOverDateMaxDate, BomWebPortalConstant.DATE_FORMAT));
			return;
		} else if (cutOverDate.after(cutOverDateMaxDate) && !isFulfillment){
			errors.rejectValue("cutOverDateStr", null, "Requires Cutover Date between " + Utility.date2String(cutOverDateMinDate, BomWebPortalConstant.DATE_FORMAT) + " and " + Utility.date2String(cutOverDateMaxDate, BomWebPortalConstant.DATE_FORMAT));
			return;
		}
		// cutOverTime
		if (!this.isCutOverTimeValid(form.getCutOverTime(), errors)) {
			return;
		}
		if (errors.hasErrors()) {
			return;
		}
		if (this.isMnpCutOverTimeFrozen(cutOverDate, form.getCutOverTime())) {
			errors.rejectValue("cutOverTime", null, "This cutover time is frozen.");
		}
		if (errors.hasErrors()) {
			return;
		}
		// mnpMsisdn
		if (!this.isHKMobileNumValid(form.getMnpMsisdn(), errors)) {
			return;
		}
		// mnpTicketNum
		if (enableMNP){ //dennis enable mnp
			if (!this.isMnpTicketNumValid(form.getMnpTicketNum(), cutOverDate, form.getCutOverTime(), errors)) {
				return;
			}
		}
		// custName
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custName", null, "Requires Customer Name");
		// docNum
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docNum", null
				, "Prepaid Sim".equals(form.getCustName()) ? "Requires Prepaid Sim No." : "Requires Old Customer Identity No.");
	}
	
	private boolean isMnpTicketNumValid(String mnpTicketNum, Date cutOverDate, String cutOverTime, Errors errors) {
		if (StringUtils.isBlank(mnpTicketNum) || !mnpTicketNum.matches("^[0-9]{10}$")) {
			errors.rejectValue("mnpTicketNum", null, "Requires MNP Ticket Number in 10 digits");
			return false;
		}
		String beginWith = Utility.date2String(cutOverDate, "MMdd");
		String char5th = cutOverTime;
		if (!mnpTicketNum.startsWith(beginWith)) {
			errors.rejectValue("mnpTicketNum", null, "Requires MNP Ticket Number starts with " + beginWith);
			return false;
		}
		if (!(mnpTicketNum.charAt(4) + "").equals(char5th)) {
			errors.rejectValue("mnpTicketNum", null, "Requires MNP Ticket Number's 5th digit = " + char5th);
			return false;
		}
		return true;
	}
	
	private boolean isCutOverTimeValid(String cutOverTime, Errors errors) {
		for (String value : new String[] { "0", "1" }) {
			if (value.equals(cutOverTime)) {
				return true;
			}
		}
		errors.rejectValue("cutOverTime", null, "Requires Cutover Time");
		return false;
	}
	
	// compare year / month / day only
	private boolean sameDate(Date date, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		return calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) 
				&& calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
				&& calendar.get(Calendar.DATE) == calendar2.get(Calendar.DATE);
		
	}
	
	private boolean isMnpCutOverTimeFrozen(Date cutOverDate, String cutOverTime) {
		if (logger.isDebugEnabled()) {
			logger.debug("cutOverDate: " + cutOverDate + ", cutOverTime: " + cutOverTime);
		}
		List<String> frozenWindows = this.mobCcsMrtService.getFrozenWindow(Utility.date2String(cutOverDate, "dd/MM/yyyy"));
		if (logger.isDebugEnabled()) {
			logger.debug("frozenWindows: " + (frozenWindows == null ? "(null)" : StringUtils.join(frozenWindows, ' ')));
		}
		if (this.isEmpty(frozenWindows)) {
			return false;
		}
		return frozenWindows.contains(StringUtils.trim(cutOverTime));
	}
}
