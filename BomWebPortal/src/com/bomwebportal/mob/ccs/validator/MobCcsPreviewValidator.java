package com.bomwebportal.mob.ccs.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.HsrmDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.mob.ccs.dto.PreviewDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentMonthyUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.util.BeanUtilsHelper;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ccs.web.MobCcsAuthorizeController;
import com.bomwebportal.mobquota.dto.MobQuotaUsageDTO;
import com.bomwebportal.mobquota.dto.QuotaConsumeRequest;
import com.bomwebportal.mobquota.exception.AppServiceException;
import com.bomwebportal.mobquota.service.MobQuotaService;
import com.bomwebportal.service.MobItemQuotaService;
import com.bomwebportal.service.MultiSimInfoService;
import com.bomwebportal.service.OrderHsrmService;
import com.bomwebportal.util.Utility;

public class MobCcsPreviewValidator implements Validator {
	
    private MobCcsMrtService mobCcsMrtService;
    private DeliveryService deliveryService;
    private MultiSimInfoService multiSimInfoService;
    private MobItemQuotaService mobItemQuotaService;
	private MobQuotaService mobQuotaService;
	private OrderHsrmService orderHsrmService;
    
    protected final Log logger = LogFactory.getLog(getClass());
    /**
	 * @return the deliveryService
	 */
	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	/**
	 * @param deliveryService the deliveryService to set
	 */
	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public MultiSimInfoService getMultiSimInfoService() {
		return multiSimInfoService;
	}

	public void setMultiSimInfoService(MultiSimInfoService multiSimInfoService) {
		this.multiSimInfoService = multiSimInfoService;
	}

	public MobCcsMrtService getMobCcsMrtService() {
        return mobCcsMrtService;
    }

    public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
        this.mobCcsMrtService = mobCcsMrtService;
    }

	public MobItemQuotaService getMobItemQuotaService() {
		return mobItemQuotaService;
	}

	public void setMobItemQuotaService(MobItemQuotaService mobItemQuotaService) {
		this.mobItemQuotaService = mobItemQuotaService;
	}

	public MobQuotaService getMobQuotaService() {
		return mobQuotaService;
	}

	public void setMobQuotaService(MobQuotaService mobQuotaService) {
		this.mobQuotaService = mobQuotaService;
	}

	
	
	public OrderHsrmService getOrderHsrmService() {
		return orderHsrmService;
	}

	public void setOrderHsrmService(OrderHsrmService orderHsrmService) {
		this.orderHsrmService = orderHsrmService;
	}

	private String getAvailableTimeSlot(int slot) {
		String result = null;
		/*
		 * first digit = am : 1, pm : 2
		 * second to third = hour (00 - 11)
		 * fourth = minutes (00 : 1, 30 : 2)
		 * 
		 */
		switch (slot) {
		case 1101 : result = "T01";
		break;
		case 1102 : result = "T02";
		break;
		case 1111 : result = "T03";
		break;
		case 1112 : result = "T04";
		break;
		case 2001 : result = "T05";
		break;
		case 2002 : result = "T06";
		break;
		case 2011 : result = "T07";
		break;
		case 2012 : result = "T08";
		break;
		case 2021 : result = "T09";
		break;
		case 2022 : result = "T10";
		break;
		case 2031 : result = "T11";
		break;
		case 2032 : result = "T12";
		break;
		case 2041 : result = "T13";
		break;
		case 2042 : result = "T14";
		break;
		case 2051 : result = "T15";
		break;
		case 2052 : result = "T16";
		break;
		case 2061 : result = "T17";
		break;
		case 2062 : result = "T18";
		break;
		case 2071 : result = "T19";
		break;
		case 2072 : result = "T20";
		break;
		case 2081 : result = "T21";
		break;
		case 2082 : result = "T22";
		break;
		case 2091 : result = "T23";
		break;
		default : result = "NONE";
		}
		
		
		return result;
	}
	
	public boolean supports(Class arg0) {
		return arg0.equals(PreviewDTO.class);
	}
	
	private void mnpValidator(MRTUI mrtui, Object obj, Errors errors) {
		
		List<String> orderIdList = null;
		if (StringUtils.isNotBlank(mrtui.getOrderId()))
			orderIdList = mobCcsMrtService
					.getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout(
							mrtui.getMnpMsisdn(),
							mrtui.getOrderId());
		else
			orderIdList = mobCcsMrtService
					.getPendingOrderExistWithMsisdnByPendingAndFallout(mrtui
							.getMnpMsisdn());
		
		if (StringUtils.isNotBlank(mrtui.getCutOverDateStr())) {
			List<String> frozenSlot = mobCcsMrtService.getFrozenWindow(mrtui.getCutOverDateStr());
			if (frozenSlot != null) {
				for (String s : frozenSlot) {
					if (s.equals(mrtui.getCutOverTime())) {
						errors.rejectValue("topErrorPath", "invalid.cutOverTime", "This Cut Over Time is frozen");
					}
				}
			}
		}
		
		if (orderIdList != null
				&& !orderIdList.isEmpty()) {
			String orderIdListStr = "";
			Iterator<String> itr = orderIdList.iterator();
			while (itr.hasNext()) {
				orderIdListStr += "\"" + itr.next()	+ "\",";
			}
			orderIdListStr = orderIdListStr.substring(0, orderIdListStr.length() - 1);

			errors.rejectValue("topErrorPath",
					"mnpPendingError.invalid",
					"Pending SB order exists with this input mnp mobile number, order ID ="
							+ orderIdListStr);
		}
	}
	
	private void newMrtValidator(MRTUI mrtui, Object obj, Errors errors) {
		if (StringUtils.isNotBlank(mrtui.getMobMsisdn()) &&
				StringUtils.isNotBlank(mrtui.getMsisdnLvl()) && 
				StringUtils.isNotBlank(mrtui.getServiceReqDateStr())) {
				
			if (mrtui.getPreviousMrtUi() != null) {
				boolean same = true;
				try {
					same = BeanUtilsHelper.compareSpecificProperties(mrtui, mrtui.getPreviousMrtUi(), new String[]{"mobMsisdn"});
				} catch (IllegalAccessException e) {
					logger.error(e);
				} catch (InvocationTargetException e) {
					logger.error(e);
				}
				if (!same) {
					if (!mobCcsMrtService.validate3GnewMRTStatus(mrtui.getMobMsisdn())) {
						errors.rejectValue("topErrorPath", "ccsMsisdn.used");
					}
				} else {
					if (StringUtils.isBlank(mrtui.getOrderId())) {
						if (!mobCcsMrtService.validate3GnewMRTStatus(mrtui.getMobMsisdn())) {
							errors.rejectValue("topErrorPath", "ccsMsisdn.used");
						}
					}
				}
			} 
		}
	}
	
	private void unicomMrtValidator(MRTUI mrtui, Object obj, Errors errors) {
		
		if (mrtui.getPreviousMrtUi() != null) {
			boolean same = true;
			try {
				same = BeanUtilsHelper.compareSpecificProperties(mrtui, mrtui.getPreviousMrtUi(), new String[]{"unicomMobMsisdn"});
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (!same) {
				if (!mobCcsMrtService.validateUnicomNumStatus(mrtui
						.getUnicomMobMsisdn())) {
					errors.rejectValue("topErrorPath", "unicomMobMsisdn.used");
				}
			} else {
				if (StringUtils.isBlank(mrtui.getOrderId())) {
					if (!mobCcsMrtService.validateUnicomNumStatus(mrtui
							.getUnicomMobMsisdn())) {
						errors.rejectValue("topErrorPath", "unicomMobMsisdn.used");
					}
				}
			}
		} 
	}
	
	private void validateMrt(MRTUI mrtui, Object obj, Errors errors) {
		
		// new MRT
		if ("N".equals(mrtui.getMnpInd()) && "N".equals(mrtui.getGoldenInd())
				&& "N".equals(mrtui.getChinaInd())) {
			newMrtValidator(mrtui, obj, errors);
		}

		// new Golden MRT
		if ("Y".equals(mrtui.getGoldenInd()) && "N".equals(mrtui.getChinaInd())
				&& "N".equals(mrtui.getMnpInd())) {
			newMrtValidator(mrtui, obj, errors);
		}

		// MNP
		if ("Y".equals(mrtui.getMnpInd()) && "N".equals(mrtui.getGoldenInd()) 
				&& "N".equals(mrtui.getChinaInd())) {
			mnpValidator(mrtui, obj, errors);
		}

		// 1C2N(New MRT)
		if ("N".equals(mrtui.getMnpInd()) && "N".equals(mrtui.getGoldenInd())
				&& "Y".equals(mrtui.getChinaInd())) {
			newMrtValidator(mrtui, obj, errors);
			unicomMrtValidator(mrtui, obj, errors);
		}

		// 1C2N(New Golden MRT)
		if ("Y".equals(mrtui.getGoldenInd()) && "Y".equals(mrtui.getChinaInd())
				&& "N".equals(mrtui.getMnpInd())) {
			newMrtValidator(mrtui, obj, errors);
			unicomMrtValidator(mrtui, obj, errors);
		}

		// 1C2N(MNP)
		if ("Y".equals(mrtui.getMnpInd()) && "N".equals(mrtui.getGoldenInd()) 
				&& "Y".equals(mrtui.getChinaInd())) {
			mnpValidator(mrtui, obj, errors);
			unicomMrtValidator(mrtui, obj, errors);
		}
		
	}
	
	public void validate(Object obj, Errors errors) {
		PreviewDTO dto = (PreviewDTO) obj;
		String previousOrderType = (String) dto.getValue("previousOrderType");
		OrderDTO orderDTO =(OrderDTO) dto.getValue("orderDTO");
		if (orderDTO==null){
			orderDTO= new OrderDTO();
			orderDTO.setBusTxnType("DRAF");
		}

		if("DRAF".equals(orderDTO.getBusTxnType()) && "DRAF".equals(dto.getOrderType())){
			return;
		}
		
		if (BomWebPortalCcsConstant.ORD_CCS_TYPE_PRE.equalsIgnoreCase(orderDTO.getBusTxnType())) {
			if (BomWebPortalCcsConstant.ORD_CCS_TYPE_DRAF.equalsIgnoreCase(dto.getOrderType())) {
				errors.rejectValue("topErrorPath", "topErrorPath.noDowngrade", "Pre-Order, Not allow to save as Draft Order" );
			}
		}
		
		//fall into this if statement when order is being recalled
		CustomerProfileDTO sessionCustProfile = (CustomerProfileDTO) dto.getValue("customer");
		
		if (dto.getOrderId() != null) {
						
			BasketDTO sessionBasket = (BasketDTO) dto.getValue("basket");
			
			if (sessionCustProfile != null) {
				if ("N".equalsIgnoreCase(sessionCustProfile.getPhInd()) && "Y".equalsIgnoreCase(sessionBasket.getPublicHouseBaksetInd())) {
					errors.rejectValue("topErrorPath", "topErrorPath.phIndNotMatch", "This basket only suit for public housing customer" );
				}
			}
			
		}
		//activation date checking
		MRTUI mrtui = (MRTUI) dto.getValue("mrt");
		DeliveryUI deliveryUI = (DeliveryUI) dto.getValue("delivery");
		BasketDTO sessionBasket = (BasketDTO) dto.getValue("basket");
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) dto.getValue("multiSimInfoList");
		if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
			for (MultiSimInfoDTO msi : multiSimInfoDTOs) {
				for (MultiSimInfoMemberDTO msim : msi.getMembers()) {
					String conflictOrderId = multiSimInfoService.validateSBPendingOrder(msim.getMsisdn(), orderDTO.getOrderId());
					if (conflictOrderId != null) {
						errors.rejectValue("topErrorPath", "topErrorPath.noByPass", "Secondary MRT " + msim.getMsisdn() + " has pending order (" + conflictOrderId + ") in SB. Please go back and change before saving." );
					}
				}
			} 
		}
		if (mrtui != null && deliveryUI != null) {
			
			if (BomWebPortalCcsConstant.ORD_CCS_TYPE_PEND.equalsIgnoreCase(dto.getOrderType())) {
				PaymentMonthyUI paymentMonthyUI = (PaymentMonthyUI) dto.getValue("paymentMonthyUI");
				PaymentUpFrontUI paymentUpFrontUI = (PaymentUpFrontUI) dto.getValue("paymentUpFrontUI");
			
				if (sessionCustProfile.isByPassValidation()) {
					errors.rejectValue("topErrorPath", "topErrorPath.noByPass", "No By-Pass validation is allowed on Customer Information" );
				}
				
				if (deliveryUI.isByPassValidation()) {
					errors.rejectValue("topErrorPath", "topErrorPath.noByPass", "No By-Pass validation is allowed on Delivery Info" );
				}
				
				if (paymentMonthyUI.isByPassValidation()) {
					errors.rejectValue("topErrorPath", "topErrorPath.noByPass", "No By-Pass validation is allowed on Upfront Payment" );
				}
				
				if (paymentUpFrontUI.isByPassValidation()) {
					errors.rejectValue("topErrorPath", "topErrorPath.noByPass", "No By-Pass validation is allowed on Monthly Payment" );
				}
				//modify by eliot 20120321
//				if (mrtui.getGoldenInd() && !BomWebPortalCcsConstant.MRT_GOLDEN_NUM_COMPLETE.equalsIgnoreCase(mrtui.getGoldenRequestStatus())) {
//					errors.rejectValue("topErrorPath", "topErrorPath.goldNotComplete", "Golden Number Request Status not yet complete" );
//				}
			}
				
			if (deliveryUI.isByPassValidation()){
				return;
			}
			
			
			
			validateMrt(mrtui, obj, errors);
			
			if (BomWebPortalCcsConstant.ORD_CCS_TYPE_PRE.equalsIgnoreCase(dto.getOrderType())) {
				//check for urgent delivery
				
				
				
				if (deliveryUI.isUrgentInd()) {
					errors.rejectValue("topErrorPath", "topErrorPath.noUrgentDelivery", "No Urgent Delivery is allowed" );
				}
				
				if (BomWebPortalCcsConstant.ORD_CCS_TYPE_PEND.equalsIgnoreCase(previousOrderType)) {
					errors.rejectValue("topErrorPath", "topErrorPath.noDowngrade", "Not allow to save as Pre-Order" );
				}
				
				
			}
			
			//before today 3pm validation
			if (deliveryUI.getDeliveryDateStr() != null && !deliveryUI.getDeliveryDateStr().isEmpty()) {
				Date deliverDate = Utility.string2Date(deliveryUI.getDeliveryDateStr());
				
				Calendar calendar = new GregorianCalendar();
				
				if (deliveryUI.isUrgentInd()) {
					
					//add by wilson 20120410
					if ("Y".equalsIgnoreCase(sessionBasket.getDummyBasketInd())) {
						errors.rejectValue("topErrorPath", "topErrorPath.noUrgentDeliveryDummyBasket", "Dummy Basket Not Allow Select Urgent Delivery" );
					}
				}
				/*boolean validTimeslot = false;
				String orderType = "ACQ";
				String delMode = "HD";
				String delInd = null;
				if (deliveryUI.isUrgentInd()) {
					delInd = "URGENT";
				} else {
					delInd = "NORMAL";
				}
				String hsInd = null;
				String hsItemCd = null;
				if (StringUtils.isNotBlank(sessionBasket.getHsPosItemCd())) {
					hsInd = "Y";
					hsItemCd = sessionBasket.getHsPosItemCd();
				} else {
					hsInd = "N";
					hsItemCd = "NONE";
				}
				String payMthd = null;
				if (StringUtils.isNotBlank(deliveryUI.getPaymentMethod())) {
					payMthd = deliveryUI.getPaymentMethod();
				} else {
					payMthd = "NONE";
				}
				String fsInd = "Y";
				String mnpInd = mrtui.getMnpInd();
				String orderId = null;
				if (StringUtils.isNotEmpty(deliveryUI.getOrderId())) {
					orderId = deliveryUI.getOrderId();
				} else {
					orderId = "NONE";
				}
				Date formattedAppDate = null;
				try {
					SimpleDateFormat sdf = new SimpleDateFormat();
					sdf.applyPattern("dd/MM/yyyy");
					formattedAppDate = sdf.parse((String) deliveryUI.getValue("appDate"));
				} catch (ParseException e) {
					formattedAppDate = null;
				}
				CcsDeliveryDateRangeDTO ccsDeliveryDateRangeDTO = deliveryService.getCcsDeliveryDateRange(orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, formattedAppDate);
				if (ccsDeliveryDateRangeDTO != null) {
					if (ccsDeliveryDateRangeDTO.getErrCode() == 0) {
						List<CcsDeliveryTimeSlotDTO> ccsDeliveryTimeSlotDTOList = deliveryService.getCcsDeliveryTimeslot(delMode, delInd, Utility.string2Date(deliveryUI.getDeliveryDateStr()), deliveryUI.getDistrictCode(), ccsDeliveryDateRangeDTO.getStartDate(), ccsDeliveryDateRangeDTO.getTimeSlot());
						if (CollectionUtils.isNotEmpty(ccsDeliveryTimeSlotDTOList)) {
							for (CcsDeliveryTimeSlotDTO ccsDeliveryTimeSlotDTO: ccsDeliveryTimeSlotDTOList) {
								if (StringUtils.isNotBlank(ccsDeliveryTimeSlotDTO.getTimeslot())) {
									if (ccsDeliveryTimeSlotDTO.getTimeslot().contains(deliveryUI.getDeliveryTimeSlot())) {
										validTimeslot = true;
									}
								}
							}
							if (!validTimeslot) {
								errors.rejectValue("topErrorPath", "", "Invalid delivery Timeslot");
							}
						} else {
							errors.rejectValue("topErrorPath", "", "[Stored Procedure error] System error. Please try again in a few minutes later");
						}
					} else {
						errors.rejectValue("topErrorPath", "", "CCS_DELIVERY_DATE_RANGE Error code " + ccsDeliveryDateRangeDTO.getErrCode() + "Error Message " + ccsDeliveryDateRangeDTO.getErrText());
					}
				} else {
					errors.rejectValue("topErrorPath", "", "CCS_DELIVERY_DATE_RANGE [Stored Procedure error] System error. Please try again in a few minutes later");
				}
				Date startDateRange = ccsDeliveryDateRangeDTO.getStartDate();
				Date endDateRange = ccsDeliveryDateRangeDTO.getEndDate();
				if (DateUtils.daysDiffBetween(deliverDate, startDateRange) < 0 || DateUtils.daysDiffBetween(deliverDate, endDateRange) > 0) {
					errors.rejectValue("topErrorPath", "deliveryDateStr.error", "Delivery Date must be in between "
									+ Utility.date2String(startDateRange, BomWebPortalConstant.DATE_FORMAT) 
									+ " and " 
									+ Utility.date2String(endDateRange, BomWebPortalConstant.DATE_FORMAT));
				}*/
			}
		}
		
		/****************************** Start Quota Validation ***********************************/
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		VasDetailDTO vasDetail =  (VasDetailDTO) MobCcsSessionUtil.getSession(request, "vasDetail");
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		OrderDTO orderDto = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
		Date appDate = (orderDto == null) ? new Date() : orderDto.getAppInDate();
		
		// get the list of finalQuotaList again, in case not come from prev. page...
		List<String> selectedItemList = null;
		if (vasDetail.getVasitem() != null && vasDetail.getVasitem().length > 0) {
			selectedItemList = Arrays.asList(vasDetail.getVasitem());
		}
		vasDetail.setFinalOuotaList(mobItemQuotaService.getFinalQuota(sessionBasket.getBasketId(), Utility.date2String(appDate, "dd/MM/yyyy"), selectedItemList));
		
		//createMobQuotaConsumeRequest
		String orderId = (orderDto == null) ? "" : orderDto.getOrderId();
		SuperOrderDTO superOrderDto = (SuperOrderDTO) request.getSession().getAttribute("superOrderDto");
		String authBy = MobCcsAuthorizeController.getSessionAuthorizedBy(request, "AU15");  // tmp use AU15 ...
		QuotaConsumeRequest qcr = mobItemQuotaService.createMobQuotaConsumeRequest(
				sessionCustomer.getIdDocType(), sessionCustomer.getIdDocNum(), orderId, 
				user.getUsername(), vasDetail, authBy, appDate);
		
		//Validate Quota
		superOrderDto.setQuotaConsumeRequest(null);
		
		if (CollectionUtils.isNotEmpty(qcr.getItems())) {
			if (!dto.isIgnoreQuotaCheck()) {
				MobQuotaUsageDTO[] quotaUsages = this.checkQuota(qcr, errors);
			}
		} else {
			qcr = null;
		}
		
		if (qcr != null) {
			superOrderDto.setQuotaConsumeRequest(qcr);
		}
		/****************************** End Quota Validation ***********************************/
		
		
		/******************************	Start HSRM Validation ***********************************/
		List<ComponentDTO> componentList = (List<ComponentDTO>) request.getSession().getAttribute("componentList");
		if (!dto.isIgnoreHSRMCheck()){
			HsrmDTO validateResult = orderHsrmService.validateHSRM(componentList,orderId,"2", sessionBasket.getHsPosItemCd(), user.getUsername(), sessionCustomer.getIdDocNum(), appDate,sessionBasket.getMipBrand());
			if (!validateResult.getReturnBool()){
				errors.rejectValue("topErrorPath", "dummy", "Validation failure on HSRM Pre-reg number: "+validateResult.getReturnMessage());
			}else{
				if (StringUtils.isNotBlank(validateResult.getReturnMessage())){
					errors.rejectValue("ignoreHSRMCheck", "dummy", validateResult.getReturnMessage());
				}
			}
		}
		/****************************** End HSRM Validation ***********************************/
	}
	
	private MobQuotaUsageDTO[] checkQuota(QuotaConsumeRequest qcr, Errors errors) {
		MobQuotaUsageDTO[] quotaUsages = null;
		boolean validQuota = true;
		try {
			quotaUsages = mobQuotaService.mockConsumeQuota(qcr);
		} catch (AppServiceException e) {
			logger.error("Error while checking quota", e);
			errors.rejectValue("ignoreQuotaCheck", "dummy", "Quota Check is not available.");
		}
		
		if (quotaUsages != null) {
			if (StringUtils.isEmpty(qcr.getAuthBy())) {
				for (MobQuotaUsageDTO usage : quotaUsages) {
					// check also the returned orderid , over quota error if the quota id is a newly added one ...
					if (usage.isOverQuota() && StringUtils.isEmpty(usage.getOrderId())) {
						validQuota = false;
					}
				}
			}
		}
		
		if (!validQuota) {
			errors.rejectValue("topErrorPath", "dummy", "Exceeded Quota, but missing Auth info.  Order is not submitted.  Please go back and modify.");
		}
		
		return quotaUsages;
	}

}
