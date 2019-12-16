package com.bomwebportal.mob.ccs.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dao.BomSubscriberDAO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsOrderFalloutDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.service.MobCcsOrderFalloutService;
import com.bomwebportal.mob.ccs.util.BeanUtilsHelper;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.DateUtils;
import com.bomwebportal.util.Utility;

public class MRTValidator extends MRTBaseValidator {

	protected final Log logger = LogFactory.getLog(getClass());

	private MnpService service;
	private MobCcsOrderFalloutService mobCcsOrderFalloutService;
	private String mobile2GRetiredInd;
	private ValidateService validateService;
	
	@Autowired
	private BomSubscriberDAO bomSubscriberDAO;

	public MnpService getService() {
		return service;
	}

	public void setService(MnpService service) {
		this.service = service;
	}

	public MobCcsOrderFalloutService getMobCcsOrderFalloutService() {
		return mobCcsOrderFalloutService;
	}

	public void setMobCcsOrderFalloutService(
			MobCcsOrderFalloutService mobCcsOrderFalloutService) {
		this.mobCcsOrderFalloutService = mobCcsOrderFalloutService;
	}

	public String getMobile2GRetiredInd() {
		return mobile2GRetiredInd;
	}

	public void setMobile2GRetiredInd(String mobile2gRetiredInd) {
		mobile2GRetiredInd = mobile2gRetiredInd;
	}


	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	

	public boolean supports(Class clazz) {
		return clazz.equals(MRTUI.class);
	}

	public void validate(Object obj, Errors errors) {

		MRTUI mrtUI = (MRTUI) obj;
		List<MultiSimInfoDTO> multiSimInfoList = (List<MultiSimInfoDTO>) mrtUI.getSession().getAttribute("MultiSimInfoList");
		List<ComponentDTO> componentList = (List<ComponentDTO>) mrtUI.getSession().getAttribute("componentList");
		BomSalesUserDTO sessionUser = (BomSalesUserDTO)mrtUI.getValue("bomsalesuser");	
		
		if (mrtUI.isByPassValidation()) {
			return; // add by wilson 20120302, by pass validation
		}

		// new MRT
		if ("N".equals(mrtUI.getMnpInd()) && "N".equals(mrtUI.getGoldenInd())
				&& "N".equals(mrtUI.getChinaInd())) {
			newMrtValidator(obj, errors);
		}

		// new Golden MRT
		if ("Y".equals(mrtUI.getGoldenInd()) && "N".equals(mrtUI.getChinaInd())
				&& "N".equals(mrtUI.getMnpInd())) {
			newMrtValidator(obj, errors);
		}

		// MNP
		if ("Y".equals(mrtUI.getMnpInd()) && "N".equals(mrtUI.getGoldenInd()) 
				&& "N".equals(mrtUI.getChinaInd())) {
			mnpValidator(obj, errors);
		}

		// 1C2N(New MRT)
		if ("N".equals(mrtUI.getMnpInd()) && "N".equals(mrtUI.getGoldenInd())
				&& "Y".equals(mrtUI.getChinaInd())) {
			newMrtValidator(obj, errors);
			unicomMrtValidator(obj, errors);
		}

		// 1C2N(New Golden MRT)
		if ("Y".equals(mrtUI.getGoldenInd()) && "Y".equals(mrtUI.getChinaInd())
				&& "N".equals(mrtUI.getMnpInd())) {
			newMrtValidator(obj, errors);
			unicomMrtValidator(obj, errors);
		}

		// 1C2N(MNP)
		if ("Y".equals(mrtUI.getMnpInd()) && "N".equals(mrtUI.getGoldenInd()) 
				&& "Y".equals(mrtUI.getChinaInd())) {
			mnpValidator(obj, errors);
			unicomMrtValidator(obj, errors);
		}

		// New MRT + MNP
		if (BomWebPortalCcsConstant.MRT_NEW_MRT_AND_MNP.equals(mrtUI
				.getMnpInd()) && "N".equals(mrtUI.getChinaInd())) {
			newMrtValidator(obj, errors);
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cutOverDateStr",
					"cutoverDate.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mnpMsisdn",
					"msisdn.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custName",
					"custName.required");
			CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)mrtUI.getValue("customer");	
			if (!errors.hasErrors()) {
				mnpValidator(mrtUI, errors);
			}
			
			if (!errors.hasErrors()) {
				HashMap<String, Object> compareDayResult = validDaysDiffBetweenCutOverAndServReq(
						Utility.string2Date(mrtUI.getCutOverDateStr()),
						Utility.string2Date(mrtUI.getServiceReqDateStr()),
						mrtUI.getMnpInd(), mrtUI.getOrderId(),mrtUI.getDno(),mrtUI.getOpssInd(),Utility.getRno(sessionCustomer.getSimType()),null);
				if (compareDayResult != null
						&& StringUtils.isNotBlank((String) compareDayResult.get("errorType"))) {

					if ("beforeError".equals((String) compareDayResult
							.get("errorType"))) {
						errors.rejectValue(
								"cutOverDateStr",
								"mnpAndMrt.cutBeforeServ",
								"Cutover date should on or after "
										+ Utility.date2String(
												(Date) compareDayResult
														.get("errorDate"),
												BomWebPortalConstant.DATE_FORMAT));

					} else if ("afterError".equals((String) compareDayResult
							.get("errorType"))) {
						errors.rejectValue(
								"cutOverDateStr",
								"mnpAndMrt.cutAfterServ",
								"Cutover date should on or before "
										+ Utility.date2String(
												(Date) compareDayResult
														.get("errorDate"),
												BomWebPortalConstant.DATE_FORMAT));
					}
				}
			
			}
			
			
			
		}
		
		if (multiSimInfoList != null) {
			for (MultiSimInfoDTO msi : multiSimInfoList) {	
				for (MultiSimInfoMemberDTO msim : msi.getMembers()) {
					if (!"MUPS99".equals(msim.getMemberOrderType())) {
						if (msim.getMsisdn().equals(mrtUI.getMobMsisdn())) {
							errors.rejectValue("mnpMsisdn", "invalid.msisdn");
						}
					}
					if ("MUPS02".equals(msim.getMemberOrderType())) {
						if (msim.getMnpNumber().equals(mrtUI.getMobMsisdn())) {
							errors.rejectValue("mnpMsisdn", "invalid.msisdn");
						}
					}
				}
			}	
		}
		
		List<CodeLkupDTO> digitalCouponAttb = LookupTableBean.getInstance().getCodeLkupList().get("COUPON_ATTB");
		if (CollectionUtils.isNotEmpty(digitalCouponAttb)) {
			if (componentList != null) {
				for (int i = 0; i < componentList.size(); i ++) {
					if (digitalCouponAttb.equals(componentList.get(i).getCompAttbId())) {
						if (sessionUser.getChannelId() == 2) {
							if ("        ".equals(componentList.get(i).getCompAttbVal())) {
								if ("N".equals(mrtUI.getMnpInd()) || BomWebPortalCcsConstant.MRT_NEW_MRT_AND_MNP.equals(mrtUI.getMnpInd())) {
									componentList.get(i).setCompAttbValue(mrtUI.getMobMsisdn());
								} else if ("Y".equals(mrtUI.getMnpInd())) {
									componentList.get(i).setCompAttbValue(mrtUI.getMnpMsisdn());
								}
							} else {
								CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)mrtUI.getValue("customer");
								if ("BS".equals(sessionCustomer.getIdDocType())) {
									try {
										int result = bomSubscriberDAO.getCouponAccountInfo(componentList.get(i).getCompAttbVal(), sessionCustomer.getIdDocType(), sessionCustomer.getIdDocNum());
										if (result < 1) {
											if ("N".equals(mrtUI.getMnpInd()) || BomWebPortalCcsConstant.MRT_NEW_MRT_AND_MNP.equals(mrtUI.getMnpInd())) {
												if (!mrtUI.getMobMsisdn().equals(componentList.get(i).getCompAttbVal())) {
													errors.rejectValue("mobMsisdn", "", "Coupon SMS No. [" + componentList.get(i).getCompAttbVal() + "] is not matched with HKBR " + sessionCustomer.getIdDocNum());
												}
											} else if ("Y".equals(mrtUI.getMnpInd())) {
												if (!mrtUI.getMnpMsisdn().equals(componentList.get(i).getCompAttbVal())) {
													errors.rejectValue("mobMsisdn", "", "Coupon SMS No. [" + componentList.get(i).getCompAttbVal() + "] is not matched with HKBR " + sessionCustomer.getIdDocNum());
												}
											}
										}
									} catch (DAOException e) {
										e.printStackTrace();
									}
								} else {
									if ("N".equals(mrtUI.getMnpInd()) || BomWebPortalCcsConstant.MRT_NEW_MRT_AND_MNP.equals(mrtUI.getMnpInd())) {
										if (!mrtUI.getMobMsisdn().equals(componentList.get(i).getCompAttbVal())) {
											errors.rejectValue("mobMsisdn", "", "Coupon SMS No. [" + componentList.get(i).getCompAttbVal() + "] is not match with MRT No. [" + mrtUI.getMobMsisdn() + "]");
										}
									} else if ("Y".equals(mrtUI.getMnpInd())) {
										if (!mrtUI.getMnpMsisdn().equals(componentList.get(i).getCompAttbVal())) {
											errors.rejectValue("mobMsisdn", "", "Coupon SMS No. [" + componentList.get(i).getCompAttbVal() + "] is not match with MRT No. [" + mrtUI.getMnpMsisdn() + "]");
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void mnpValidator(Object obj, Errors errors) {

		MRTUI mrtUI = (MRTUI) obj;
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)mrtUI.getValue("customer");	
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
				
		
		if (!"99999999".equals(mrtUI.getMnpMsisdn().trim())) {

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mnpMsisdn",
					"msisdn.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custName",
					"custName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docNum",
					"custIdDocNum.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cutOverDateStr",
					"cutoverDate.required");

			if (mrtUI.getCustName().indexOf(',') != -1) {
				errors.rejectValue("custName", "custName.noComma");
			}

			if ("NONE".equals(mrtUI.getCutOverTime())) {
				errors.rejectValue("cutOverTime", "cutoverTime.required");
			} else {

				try {
					if (StringUtils.isNotBlank(mrtUI.getMnpMsisdn().trim())) {
						
						if (!Utility.validatePhoneNum(mrtUI.getMnpMsisdn())){
							errors.rejectValue("mnpMsisdn", "invalid.contactPhone");	
						}
												
						/*if (service.isPccw3gPrepaidNumber(mrtUI.getMnpMsisdn().trim())){// add 3GPP number check, 20130118
							errors.rejectValue("mnpMsisdn", "dummy", "This is a PCCW3G Prepaid Number.");
						}else{*/
							
							//DENNIS MIP3
							MnpDTO result = new MnpDTO();
							result.setMnpMsisdn(mrtUI.getMnpMsisdn());
							result.setPrepaidSimInd(mrtUI.isPrepaidSimInd());
							if (result.isPrepaidSimInd()){
								result.setCustIdDocNum(mrtUI.getDocNum());
							}
							com.bomwebportal.mob.validate.dto.ResultDTO validateResult = validateService.validateMNP(result, "mnpMsisdn", mrtUI.isIgnorePostpaidcheckbox(), "ignorePostpaidcheckbox");
						    if (validateResult.hasError()){
						    	validateService.bindErrors(validateResult, errors);
						    }else{
						    	List<String> orderIdList = null;
								if (StringUtils.isNotBlank(mrtUI.getOrderId())){
									orderIdList = mobCcsMrtService
											.getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout(
													mrtUI.getMnpMsisdn(),
													mrtUI.getOrderId());
								}else{
									orderIdList = mobCcsMrtService
											.getPendingOrderExistWithMsisdnByPendingAndFallout(mrtUI
													.getMnpMsisdn());
								}
																
								if (orderIdList != null
										&& !orderIdList.isEmpty()) {
									String orderIdListStr = "";
									Iterator<String> itr = orderIdList.iterator();
									while (itr.hasNext()) {
										orderIdListStr += "\"" + itr.next()	+ "\",";
									}
									orderIdListStr = orderIdListStr.substring(0, orderIdListStr.length() - 1);

									errors.rejectValue("mnpInd",
											"mnpPendingError.invalid",
											"Pending SB order exists with this input mnp mobile number, order ID ="
													+ orderIdListStr);
								}else{
									((MRTUI) obj).setDno(result.getDno());
							    	((MRTUI) obj).setActualDno(result.getActualDno());
							    	((MRTUI) obj).setIgnorePostpaidcheckbox(result.isIgnorePostpaidcheckbox());
							     	((MRTUI) obj).setOpssInd(validateResult.getOnePssInd());
							    	((MRTUI) obj).setStarterPack(validateResult.getStartPackNumber());
								}
								
						    	
						    
						    }
							
							
							
						/*	mnpDto = service.validateMnpMsisdn(mnpDto);
							if (mnpDto != null
									&& StringUtils.isNotBlank(mnpDto.getDno().trim())) {
								if (BomWebPortalConstant.DN_STR_PCCW3G
										.equals(mnpDto.getDno())
										|| BomWebPortalConstant.DN_STR_ERR
												.equals(mnpDto.getDno())) {
									errors.rejectValue("mnpMsisdn",
											"invalid.msisdn");
								} else {
	
									// add by Eliot 20120418
									// modify by Eliot 20120423
									List<String> orderIdList = null;
									if (StringUtils.isNotBlank(mrtUI.getOrderId()))
										orderIdList = mobCcsMrtService
												.getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout(
														mrtUI.getMnpMsisdn(),
														mrtUI.getOrderId());
									else
										orderIdList = mobCcsMrtService
												.getPendingOrderExistWithMsisdnByPendingAndFallout(mrtUI
														.getMnpMsisdn());
	
									if (orderIdList != null
											&& !orderIdList.isEmpty()) {
										String orderIdListStr = "";
										Iterator<String> itr = orderIdList.iterator();
										while (itr.hasNext()) {
											orderIdListStr += "\"" + itr.next()	+ "\",";
										}
										orderIdListStr = orderIdListStr.substring(0, orderIdListStr.length() - 1);
	
										errors.rejectValue("mnpInd",
												"mnpPendingError.invalid",
												"Pending SB order exists with this input mnp mobile number, order ID ="
														+ orderIdListStr);
									}
									//  3GPP number check, 20130319
									if (BomWebPortalConstant.DN_STR_PCCW2G.equals(mnpDto.getDno()) && service.isPccw3gPrepaidNumber(mrtUI.getMnpMsisdn().trim())){
										errors.rejectValue("mnpMsisdn", "dummy", "This is a PCCW 3G prepaid number, please use POSBOM to issue order.");
									} else if(BomWebPortalConstant.DN_STR_PCCW2G.equals(mnpDto.getDno()) ){
										errors.rejectValue("mnpMsisdn", "dummy", "DNO = MP(HKT 2G number), Should be handle in New Mobile Number instead of MNP.");
									}
									// 20130506, IM or EL DNO not allow to create order in SB
									if ("IM".equals(mnpDto.getDno()) ){
										errors.rejectValue("mnpMsisdn", "dummy", "DNO = IM. MVNO porting found and need special arrangement in porting. Please contact BOM Mobile support and helpdesk for arrangement.");
									}
									if ("EL".equals(mnpDto.getDno()) ){
										errors.rejectValue("mnpMsisdn", "dummy", "DNO = EL. MVNO porting found and need special arrangement in porting. Please contact BOM Mobile support and helpdesk for arrangement.");
									}
								}
							} else {
								errors.rejectValue("mnpMsisdn", "invalid.msisdn");
							}	*/
						}
					//}
				} catch (Exception e) {
					logger.error(e);
					errors.rejectValue("mnpInd", "mnp.error");
				}
			}		

			if (!Utility.isValidDate(mrtUI.getCutOverDateStr())) {
				errors.rejectValue("cutOverDateStr", "invalid.Date");
			} else {
				
				List<String> frozenSlot = mobCcsMrtService.getFrozenWindow(mrtUI.getCutOverDateStr());
				if (frozenSlot != null) {
					for (String s : frozenSlot) {
						if (s.equals(mrtUI.getCutOverTime())) {
							errors.rejectValue("cutOverTime", "invalid.cutOverTime", "This Cut Over Time is frozen");
						}
					}
				}
				
				Date cutoverDate = Utility.string2Date(mrtUI.getCutOverDateStr());
				Date mnpTicketReqDate = DateUtils.dateAfterdays(mrtUI.getAppDate(), 30);
				Date cutoverDateEnd = null;
				
				if ("A".equalsIgnoreCase(mrtUI.getMnpInd())) {
					
					Date serviceReqDate = Utility.string2Date(mrtUI.getServiceReqDateStr());
					
					cutoverDateEnd = DateUtils.dateAfterdays(Utility.string2Date(mrtUI.getServiceReqDateStr()), BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS);
					
					Calendar c = Calendar.getInstance();
					c.setTime(serviceReqDate);
					c.add(Calendar.DATE, 3);
					Date cutoverDateRule = c.getTime();
					
					// MNP Cut-Over Date >= SRD + 3
					if (cutoverDate.compareTo(cutoverDateRule) < 0) {
						errors.rejectValue("cutOverDateStr", "cutoverDate.violate");
					}
					
				} else {
					/*MobCcsOrderFalloutDTO orderFalloutDto = mobCcsOrderFalloutService.getOrderFalloutByCat(mrtUI.getOrderId(), "MNP_REJ");
					if (orderFalloutDto != null) {
						//DENNIS201606
						cutoverDateEnd = DateUtils.dateAfterdays(mrtUI.getAppDate(), BomWebPortalConstant.CCS_UPDATE_MNP_APP_EXTEND_DAYS);
						Date falloutCutoverDateEnd = DateUtils.dateAfterdays(orderFalloutDto.getOccuranceDate(), BomWebPortalConstant.CCS_UPDATE_MNP_FALLOUT_EXTEND_DAYS);
						if (cutoverDateEnd.before(falloutCutoverDateEnd)) {
							cutoverDateEnd = falloutCutoverDateEnd;
						}
					} else {
						//DENNIS201606
						cutoverDateEnd = DateUtils.dateAfterdays(mrtUI.getAppDate(), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
						if ("Y".equalsIgnoreCase(mrtUI.getMnpExtendAuthInd())){
							cutoverDateEnd = DateUtils.dateAfterdays(cutoverDateEnd, BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);
						}
					}*/
					
					//DENNIS201606
					cutoverDateEnd = DateUtils.dateAfterdays(mrtUI.getAppDate(), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
					if ("Y".equalsIgnoreCase(mrtUI.getMnpExtendAuthInd())){
						cutoverDateEnd = DateUtils.dateAfterdays(cutoverDateEnd, BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);
					}
				}
                
				if (enableMNP){
					if (cutoverDate.compareTo(mnpTicketReqDate) <= 0) {
						if (StringUtils.isBlank(mrtUI.getMnpTicketNum()) && !StringUtils.startsWith(mrtUI.getOrderId(), "CSBOM"))
							errors.rejectValue("mnpTicketNum", "mnpTicketNum.blank");
					} 
					
					else if(StringUtils.isNotBlank(mrtUI.getMnpTicketNum())) {
						
						errors.rejectValue("mnpTicketNum", "mnpTicketNum.isblank");
						
					}
					
					if (cutoverDate.compareTo(cutoverDateEnd) > 0) {
						//logger.info("cutover date after T + 90");
						errors.rejectValue(
								"cutOverDateStr",
								"mnpAndMrt.cutBeforeServ",
								"Cutover date should on or before "
										+ Utility.date2String(cutoverDateEnd, BomWebPortalConstant.DATE_FORMAT));
					}
										
					if (StringUtils.isNotBlank(mrtUI.getMnpTicketNum())) {
						if (StringUtils.isNotBlank(mrtUI.getMnpTicketNum())
								&& mrtUI.getMnpTicketNum().length() != 10) {
							errors.rejectValue("mnpTicketNum",
									"mnpTicketNum.invalid");
						} else {
													
							String mmdd = Utility.date2String(
									cutoverDate, "MMdd");
							if (!mmdd.equals(mrtUI.getMnpTicketNum()
									.substring(0, 4))) {
								errors.rejectValue("mnpTicketNum",
										"mnpTicketNum.invalid");
							} else if (mrtUI.getMnpTicketNum().charAt(4) != mrtUI
									.getCutOverTime().charAt(0)) {
								errors.rejectValue("mnpTicketNum",
										"mnpTicketNum.invalid");
							} else if (!Utility.isDigit(mrtUI.getMnpTicketNum()
									.substring(5))) {
								errors.rejectValue("mnpTicketNum",
										"mnpTicketNum.invalid");
							}
						}
					}					
				}
				
				
				if (!BomWebPortalCcsConstant.MRT_NEW_MRT_AND_MNP.equals(mrtUI.getMnpInd()) && "N".equals(mrtUI.getChinaInd())) {
					if(mrtUI.getMsiServiceDateList() != null && mrtUI.getMsiServiceDateList().size() != 0 
							&& mrtUI.getMultiSimDnoList()!= null && mrtUI.getMultiSimDnoList().size() != 0
							&& mrtUI.getMultiSimOpssInd() != null && mrtUI.getMultiSimOpssInd().size() != 0){
						int count = 0;
						for(String msiDateStr : mrtUI.getMsiServiceDateList()){
							HashMap<String, Object> compareDayResult = validDaysDiffBetweenCutOverAndServReq(
									Utility.string2Date(msiDateStr),
									Utility.string2Date(mrtUI.getCutOverDateStr()),
									"A", mrtUI.getOrderId(),mrtUI.getMultiSimDnoList().get(count),mrtUI.getMultiSimOpssInd().get(count),Utility.getRno(sessionCustomer.getSimType()),mrtUI.getMultiSimMemberOrderTypeList().get(count));
							if (compareDayResult != null && StringUtils.isNotBlank((String) compareDayResult.get("errorType"))) {
								if ("beforeError".equals((String) compareDayResult.get("errorType"))) {
									errors.rejectValue("cutOverDateStr","dummy",
											"MultiSim Cutover date should on or after " 
													+ Utility.date2String((Date) compareDayResult.get("errorDate"), BomWebPortalConstant.DATE_FORMAT));
	
								} else if ("afterError".equals((String) compareDayResult.get("errorType"))) {
									errors.rejectValue("cutOverDateStr", "dummy",
											"MultiSim Cutover date should on or before "
													+ Utility.date2String((Date) compareDayResult.get("errorDate"), BomWebPortalConstant.DATE_FORMAT));
								}
							}
							count ++;
						}
					}
				}
			}
		}

	}

	private void newMrtValidator(Object obj, Errors errors) {

		MRTUI mrtUI = (MRTUI) obj;
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)mrtUI.getValue("customer");	
		BomSalesUserDTO sessionUser = (BomSalesUserDTO)mrtUI.getValue("bomsalesuser");	
		
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobMsisdn",
				"ccsMobMsisdn.required");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msisdnLvl",
				"ccsMsisdnLvl.required");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceReqDateStr",
				"serviceReqDate.required");

		if (StringUtils.isNotBlank(mrtUI.getMobMsisdn()) &&
			StringUtils.isNotBlank(mrtUI.getMsisdnLvl()) && 
			StringUtils.isNotBlank(mrtUI.getServiceReqDateStr())) {
			
			if (!Utility.validatePhoneNum(mrtUI.getMobMsisdn())){
				errors.rejectValue("mnpMsisdn", "invalid.contactPhone");	
			}
			
			
			//Dennis MIP3 WS    comment SB num type
			/*String[] mrtInfo = service.getMrtNum(mrtUI.getMobMsisdn(), sessionUser.getChannelCd(), null); //Dennis MIP3
			if (mrtInfo==null || mrtInfo.length == 0){
				mrtInfo = service.getMrtNum(mrtUI.getMobMsisdn(), "CCS", null); //Dennis MIP3
			}
			
			if (mrtInfo==null || mrtInfo.length == 0){
				errors.rejectValue("mobMsisdn","", "MRT not found in SB Inventory.");	
			}else{
				com.bomwebportal.mob.validate.dto.ResultDTO sbPoolResult = validateService.validateNumType( mrtInfo[6], sessionCustomer.getNumType(), "mobMsisdn", true);
				validateService.bindErrors(sbPoolResult, errors);		
			}*/
			
			//Dennis MIP3  CNM
			MnpDTO inMnpDto = new MnpDTO();		
			inMnpDto.setNewMsisdn(mrtUI.getMobMsisdn());
			inMnpDto.setShopCd(sessionUser.getBomShopCd());
			if (StringUtils.isBlank(sessionUser.getBomShopCd())){
				errors.rejectValue("mobMsisdn","", "BOM Shop Code is empty.");	
			}else{			
				MnpDTO outMnpDto = null;
				outMnpDto = service.validateNewMsisdn(inMnpDto);
				com.bomwebportal.mob.validate.dto.ResultDTO cnmInvResult = validateService.validateNumType(outMnpDto.getNumType(), sessionCustomer.getNumType(), "mobMsisdn",false);			
				if (cnmInvResult.hasError()){//Dennis MIP3	
					validateService.bindErrors(cnmInvResult, errors);			 
				}else{
					mrtUI.setNumType(outMnpDto.getNumType());
				}
			}
			
			
			
			if (mrtUI.getPreviousMrtUi() != null) {
				boolean same = true;
				try {
					same = BeanUtilsHelper.compareSpecificProperties(mrtUI, mrtUI.getPreviousMrtUi(), new String[]{"mobMsisdn"});
				} catch (IllegalAccessException e) {
					logger.error(e);
				} catch (InvocationTargetException e) {
					logger.error(e);
				}
				if (!same) {
					if (!mobCcsMrtService.validate3GnewMRTStatus(mrtUI.getMobMsisdn())) {
						errors.rejectValue("mobMsisdn", "ccsMsisdn.used");
					}
				} else {
					if (StringUtils.isBlank(mrtUI.getOrderId())) {
						if (!mobCcsMrtService.validate3GnewMRTStatus(mrtUI.getMobMsisdn())) {
							errors.rejectValue("mobMsisdn", "ccsMsisdn.used");
						}
					}
				}
			} 
		}
					
			//modify by Eliot 20120515, special number
			if(!mrtUI.isSpecialNumInd()){
				//skip "N1" and "N2"
				if (!MobCcsMrtBaseDTO.NORMAL_MRT_N1.equals(mrtUI.getMsisdnLvl())
						&& !MobCcsMrtBaseDTO.NORMAL_MRT_N2.equals(mrtUI.getMsisdnLvl())) {
					/*
					 * service plan and contract period will limit msisdn selection
					 * base on msisdn Lvl
					 */
					
					// added & modified by Joyce 20120604
					// check golden num lvl cond by appDate
					String appDate = Utility.date2String(mrtUI.getAppDate(), BomWebPortalConstant.DATE_FORMAT);
					BasketDTO tempBasket = new BasketDTO();
					tempBasket.setContractPeriod("" + mrtUI.getContractPeriod());
					tempBasket.setRecurrentAmt("" + mrtUI.getRpRecurChange());
					tempBasket.setGrossPlanFee("" + mrtUI.getGrossPlanFee());
					
					MnpDTO tempMnp = new MnpDTO();
					tempMnp.setMsisdnLvl(mrtUI.getMsisdnLvl());
					tempMnp.setValue("customer", sessionCustomer);
					tempBasket.setByPassGoldenNum(mrtUI.getByPassGoldenNum());
					goldenNumCheck(logger, tempBasket, tempMnp, errors, appDate);
					mrtUI.setShowGoldenNumAuth(tempBasket.getShowGoldenNumAuth());
				}
			}
			if(mrtUI.getMsiServiceDateList() != null && mrtUI.getMsiServiceDateList().size() != 0 
					&& mrtUI.getMultiSimDnoList()!= null && mrtUI.getMultiSimDnoList().size() != 0
					&& mrtUI.getMultiSimOpssInd() != null && mrtUI.getMultiSimOpssInd().size() != 0){
				int count = 0 ;
				for(String msiDateStr : mrtUI.getMsiServiceDateList()){
					HashMap<String, Object> compareDayResult = validDaysDiffBetweenCutOverAndServReq(
							Utility.string2Date(msiDateStr),
							Utility.string2Date(mrtUI.getServiceReqDateStr()),
							"A", mrtUI.getOrderId(),mrtUI.getMultiSimDnoList().get(count),mrtUI.getMultiSimOpssInd().get(count),Utility.getRno(sessionCustomer.getSimType()),mrtUI.getMultiSimMemberOrderTypeList().get(count));
					
					if (compareDayResult != null && StringUtils.isNotBlank((String) compareDayResult.get("errorType"))) {
						if ("beforeError".equals((String) compareDayResult.get("errorType")) && 
								StringUtils.isNotBlank(mrtUI.getMultiSimDnoList().get(count))
								&& StringUtils.isNotBlank(mrtUI.getMultiSimOpssInd().get(count))
								&& StringUtils.isNotBlank(mrtUI.getRno())) {
							if ("Y".equals(mrtUI.getMultiSimOpssInd().get(count)) && mrtUI.getMultiSimDnoList().get(count).equals(mrtUI.getRno())){
								errors.rejectValue("serviceReqDateStr","dummy",
										"MultiSim Cutover date should on or after " 
												+ Utility.date2String((Date) compareDayResult.get("errorDate"), BomWebPortalConstant.DATE_FORMAT));
	
							}
						}
						else if ("beforeError".equals((String) compareDayResult.get("errorType"))) {
							errors.rejectValue("serviceReqDateStr","dummy",
									"MultiSim Cutover date should on or after " 
											+ Utility.date2String((Date) compareDayResult.get("errorDate"), BomWebPortalConstant.DATE_FORMAT));

						} else if ("afterError".equals((String) compareDayResult.get("errorType"))) {
							errors.rejectValue("serviceReqDateStr", "dummy",
									"MultiSim Cutover date should on or before "
											+ Utility.date2String((Date) compareDayResult.get("errorDate"), BomWebPortalConstant.DATE_FORMAT));
						}
					}
					count++;
				}
			}
		}

	private void unicomMrtValidator(Object obj, Errors errors) {

		MRTUI mrtUI = (MRTUI) obj;

		if ("Select".equals(mrtUI.getCityCd())) {
			errors.rejectValue("cityCd", "cityCd.required");
		}
		
		if (mrtUI.getPreviousMrtUi() != null) {
			boolean same = true;
			try {
				same = BeanUtilsHelper.compareSpecificProperties(mrtUI, mrtUI.getPreviousMrtUi(), new String[]{"unicomMobMsisdn"});
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (!same) {
				if (!mobCcsMrtService.validateUnicomNumStatus(mrtUI
						.getUnicomMobMsisdn())) {
					errors.rejectValue("unicomMobMsisdn", "unicomMobMsisdn.used");
				}
			} else {
				if (StringUtils.isBlank(mrtUI.getOrderId())) {
					if (!mobCcsMrtService.validateUnicomNumStatus(mrtUI
							.getUnicomMobMsisdn())) {
						errors.rejectValue("unicomMobMsisdn", "unicomMobMsisdn.used");
					}
				}
			}
		} 
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unicomMobGrade",
				"unicomMobGrade.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cityCd",
				"cityCd.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unicomMobMsisdn",
				"unicomMobMsisdn.required");
	}

	private HashMap<String, Object> validDaysDiffBetweenCutOverAndServReq(
			Date cutOverDate, Date servReqDate, String mnpInd, String orderId,String dno,String opssInd,String rno,String multiSimMemberOrderType) {

		HashMap<String, Object> error = new HashMap<String, Object>();
		String opssCases[]={"TGTG","T3TG","NPTG","M3M3","MPM3"};//dno rno
		try {
			Calendar servReqDateStartDate = Calendar.getInstance();
			servReqDateStartDate.setTime(servReqDate);
			if (StringUtils.isNotBlank(dno) && StringUtils.isNotBlank(opssInd) && StringUtils.isNotBlank(rno)) {
				if ("Y".equals(opssInd) && ArrayUtils.contains(opssCases, dno+rno)) {
				} else {
					servReqDateStartDate.add(Calendar.DATE, 3);
				}
			} else if (multiSimMemberOrderType.equals("MUPS05")) {
				
			} else {
				servReqDateStartDate.add(Calendar.DATE, 3);
			}
			Calendar servReqDateEndDate = Calendar.getInstance();
			servReqDateEndDate.setTime(servReqDate);
			if ("A".equalsIgnoreCase(mnpInd)) {
			if (StringUtils.isNotEmpty(multiSimMemberOrderType)){
					if (multiSimMemberOrderType.equals("MUPS05")) {
						servReqDateEndDate.add(Calendar.DATE, BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
					} else {
						servReqDateEndDate.add(Calendar.DATE,
								BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS);
					}
				} else {
					servReqDateEndDate.add(Calendar.DATE, BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS);
				}
			} else if ("Y".equalsIgnoreCase(mnpInd)) {          //DENNIS201606 never meet
				servReqDateEndDate.add(Calendar.DATE, BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
				
				/*MobCcsOrderFalloutDTO orderFalloutDto = mobCcsOrderFalloutService.getOrderFalloutByCat(orderId, "MNP_REJ");
				if (orderFalloutDto != null) {
					 Date falloutMnpDate = DateUtils.dateAfterdays(orderFalloutDto.getOccuranceDate(), BomWebPortalConstant.CCS_UPDATE_MNP_FALLOUT_EXTEND_DAYS);
					 servReqDateEndDate.add(Calendar.DATE, BomWebPortalConstant.CCS_UPDATE_MNP_APP_EXTEND_DAYS);
					if (servReqDateEndDate.before(falloutMnpDate)) {
						servReqDateEndDate.setTime(falloutMnpDate);
					}
				} else {
					servReqDateEndDate.add(Calendar.DATE, BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
				}*/
				
			} else {                                             //DENNIS201606 never meet
				//DENNIS201606
				servReqDateEndDate.add(Calendar.DATE, 45);
			}

			Calendar cutOverDateC = Calendar.getInstance();
			cutOverDateC.setTime(cutOverDate);

			if (cutOverDateC.before(servReqDateStartDate)) {
				error.put("errorType", "beforeError");
				error.put("errorDate", servReqDateStartDate.getTime());
				return error;
			}

			if (cutOverDateC.after(servReqDateEndDate)) {
				if (StringUtils.isNotEmpty(multiSimMemberOrderType)) {
					if (!multiSimMemberOrderType.equals("MUPS05")) {
						error.put("errorType", "afterError");
						error.put("errorDate", servReqDateEndDate.getTime());
						return error;
					}
				} else {
					error.put("errorType", "afterError");
					error.put("errorDate", servReqDateEndDate.getTime());
					return error;
				}
			}

		} catch (Exception ex) {
			logger.equals(ex);
		}

		return error;
	}
	
	@Override
	protected String getSbType() {
		return "MobCcsMrt";
	}
	
	
	
	
	

}
