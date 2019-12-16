package com.bomwebportal.mob.ccs.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.validate.dto.ResultDTO;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.sbs.dto.CcsDeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.CcsDeliveryTimeSlotDTO;
import com.bomwebportal.sbs.dto.DeliveryDateRangeDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.DateUtils;
import com.bomwebportal.util.Utility;

public class DeliveryValidator implements Validator {

	protected final Log logger = LogFactory.getLog(getClass());

	DeliveryService deliveryService;
	
	private ValidateService validateService;
	
	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	/**
	 * @return the deliveryService
	 */
	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	/**
	 * @param deliveryService
	 *            the deliveryService to set
	 */
	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(DeliveryUI.class);
	}
	
	private Date toNextWorkDay(Date inDate) {

		String dateType = deliveryService.getDateType(Utility.date2String(
				inDate, BomWebPortalConstant.DATE_FORMAT));

		if ("WD".equalsIgnoreCase(dateType)) {
			return inDate;
		} else {
			return toNextWorkDay(DateUtils.dateAfterdays(inDate, 1));
		}
	}
	
	private void checkDateRange(Date inputDate, Date validStartDate, Date validEndDate, 
			Errors errors, String payMethod, String mrtType) {
			
			String dateType = null;
			if ("SER".equalsIgnoreCase(mrtType) || "GOLD".equalsIgnoreCase(mrtType)) {
				dateType = "Service Date ";
			} else {
				dateType = "Cutover Date ";
			}
			if (DateUtils.daysDiffBetween(inputDate, validStartDate) < 0) {
				errors.rejectValue(
						"deliveryDateStr",
						"deliveryDateStr.error",
						dateType
								+ "must be in between "
								+ Utility.date2String(validStartDate,
										BomWebPortalConstant.DATE_FORMAT)
								+ " and "
								+ Utility.date2String(validEndDate,
										BomWebPortalConstant.DATE_FORMAT)
								+ ". Please update it at 'MRT' page.");
			}

			if (DateUtils.daysDiffBetween(inputDate, validEndDate) > 0) {
				errors.rejectValue(
						"deliveryDateStr",
						"deliveryDateStr.error",
						dateType
								+ "must be in between "
								+ Utility.date2String(validStartDate,
										BomWebPortalConstant.DATE_FORMAT)
								+ " and "
								+ Utility.date2String(validEndDate,
										BomWebPortalConstant.DATE_FORMAT)
								+ ". Please update it at 'MRT' page.");
			}
	}
	
	private String getAvailableTimeSlot(int slot) {
		String result = null;
		/*
		 * first digit = am : 1, pm : 2 second to third = hour (00 - 11) fourth
		 * = minutes (00 : 1, 30 : 2)
		 */
		switch (slot) {
		case 1101:
			result = "T01";
			break;
		case 1102:
			result = "T02";
			break;
		case 1111:
			result = "T03";
			break;
		case 1112:
			result = "T04";
			break;
		case 2001:
			result = "T05";
			break;
		case 2002:
			result = "T06";
			break;
		case 2011:
			result = "T07";
			break;
		case 2012:
			result = "T08";
			break;
		case 2021:
			result = "T09";
			break;
		case 2022:
			result = "T10";
			break;
		case 2031:
			result = "T11";
			break;
		case 2032:
			result = "T12";
			break;
		case 2041:
			result = "T13";
			break;
		case 2042:
			result = "T14";
			break;
		case 2051:
			result = "T15";
			break;
		case 2052:
			result = "T16";
			break;
		case 2061:
			result = "T17";
			break;
		case 2062:
			result = "T18";
			break;
		case 2071:
			result = "T19";
			break;
		case 2072:
			result = "T20";
			break;
		case 2081:
			result = "T21";
			break;
		case 2082:
			result = "T22";
			break;
		case 2091:
			result = "T23";
			break;
		default:
			result = "NONE";
		}

		return result;
	}

	
	/**
	 * to check whether the data is dirty
	 * @return
	 */
	private boolean isDirtyData(DeliveryUI inDto) {
		
		if (inDto.isUrgentInd()) {
			if (StringUtils.isNotBlank(inDto.getDeliveryTimeSlot())) {
				if (!inDto.getDeliveryTimeSlot().contains("T")) {
					return true;
				}
			}
		} else {
			if (StringUtils.isNotBlank(inDto.getDeliveryTimeSlot())) {
				if (inDto.getDeliveryTimeSlot().contains("T")) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	public void validate(Object obj, Errors errors) {

		DeliveryUI contact = (DeliveryUI) obj;
		if (contact.isByPassValidation()) {
			return; // add by wilson 20120302, by pass validation
		}

		// check rejectIfEmptyOrWhitespace
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"primaryContact.title", "title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"primaryContact.contactName", "contactName.required");
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custLastName",
		// "lastName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"primaryContact.contactPhone", "contactPhone.required");

		if (!contact.isDummyDeliveryDateInd()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryDateStr", "deliveryDate.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryTimeSlot", "deliveryTimeSlot.required", "Please select Delivery Time");
		}
		
		if (contact.isRecieveByThirdPartyInd()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"thirdPartyContact.title", "title.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"thirdPartyContact.contactName", "contactName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"thirdPartyContact.idDocType", "idDocType.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"thirdPartyContact.idDocNum", "idDocNum.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"thirdPartyContact.contactPhone", "contactPhone.required");

			if (contact.getThirdPartyContact().getIdDocType() != null) {
				// Check the HKID pattern
				if ("HKID"
						.equals(contact.getThirdPartyContact().getIdDocType())
						&& !"".equals(contact.getThirdPartyContact()
								.getIdDocNum())) {
					if (Utility.validateHKID(contact.getThirdPartyContact()
							.getIdDocNum()) == false) {
						errors.rejectValue("thirdPartyContact.idDocNum",
								"invalid.hkid");
					} else {
						if (Utility.validateHKIDcheckDigit(contact
								.getThirdPartyContact().getIdDocNum()) == false) {
							errors.rejectValue("thirdPartyContact.idDocNum",
									"invalid.hkidCheckDigit");
						}
					}
				} else if ("PASS".equalsIgnoreCase(contact.getThirdPartyContact().getIdDocType())
						&& !"".equals(contact.getThirdPartyContact().getIdDocNum())) {
					if (Utility.validatePassport(contact.getThirdPartyContact().getIdDocNum()) == false){
						errors.rejectValue("thirdPartyContact.idDocNum", "invalid.passType","Invalid passport number. Please input again.");	
					  }
				}

			}

			// pattern check contactPhone
			if (contact.getThirdPartyContact().getContactPhone() != null
					&& !"".equals(contact.getThirdPartyContact()
							.getContactPhone())) {
				if (!Utility.validatePhoneNum(contact.getThirdPartyContact().getContactPhone()) 
						|| !Utility.validateHKPhonePreFix(contact.getThirdPartyContact().getContactPhone())) {
					errors.rejectValue("thirdPartyContact.contactPhone",
							"invalid.contactPhone");
				}
			}
			if (contact.getThirdPartyContact().getIdDocNum().trim().length() > 30) {
				errors.rejectValue("thirdPartyContact.idDocNum",
						"error.contact.idDocNum.length", null,
						"error.contact.idDocNum.lengthL(default)");
			}

		}

		if (contact.getPrimaryContact().getIdDocNum().trim().length() > 30) {
			errors.rejectValue("primaryContact.idDocNum",
					"error.contact.idDocNum.length", null,
					"error.contact.idDocNum.lengthL(default)");
		}

		// pattern check contactPhone
		if (contact.getPrimaryContact().getContactPhone() != null
				&& !"".equals(contact.getPrimaryContact().getContactPhone())) {
			if (!Utility.validatePhoneNum(contact.getPrimaryContact().getContactPhone()) 
					|| !Utility.validateHKPhonePreFix(contact.getPrimaryContact().getContactPhone())) {
				errors.rejectValue("primaryContact.contactPhone",
						"invalid.contactPhone");
			}
		}

		if ("P".equals(contact.getDeliveryInd())) {
			if ("".equals(contact.getDeliveryCentre())) {
				errors.rejectValue("deliveryCentre", "deliveryCentre.required");

			}

		}
		if ("D".equals(contact.getDeliveryInd())) {
			
			/*if ("".equals(contact.getFlat()) && "".equals(contact.getFloor())) {
				if ("".equals(contact.getLotNum())){
					errors.rejectValue("flat", "flatOrFloor.required");
				}
			}*/

			// add 201105116
			/*
			 * if (!contact.isNoBillingAddressFlag()){//have billing address
			 * 
			 * if("".equals(contact.getBillingFlat())&&
			 * "".equals(contact.getBillingFloor())){
			 * errors.rejectValue("billingFlat", "flatOrFloor.required"); }
			 * 
			 * }
			 */

			// 2 == Refer Address Proof
			/*
			 * if("2".equals(contact.getAddrProofInd())){
			 * if("".equals(contact.getLob())){ errors.rejectValue("lob",
			 * "lob.required"); } if("".equals(contact.getServiceNum())){
			 * errors.rejectValue("serviceNum", "serviceNum.required"); } }
			 */

			// add by wilson 20110310
			// Building Name or Street Number or Lot number must exist
			// Street Num. and Street Name are co-requisit to each other!.
			// Lot/Hse and Street Num are mutually exclusive.
			// Lot/Hse and Building Name are mutually exclusive.
			// Please enter Street Name or Lot/Hse No. but NOT both!
			// Please enter Area
			// Please enter District
			// Input Length:
			// Flat = 5
			// Floor = 3
			// Lot/Hse = 8
			// PO Box = 6
			// Building = 30
			// Street No = 11
			// Street Name = 23
			if (contact.isCustAddressFlag2()) {
				if (StringUtils.isEmpty(contact.getAddress1())) {
					errors.rejectValue("address1", "address1.required", "Please enter the Address1.");
				}
				if (StringUtils.isEmpty(contact.getDistrictCode())) {
					errors.rejectValue("districtCd", "districtCode.required", "Please enter District");
				}
				if (StringUtils.isEmpty(contact.getAreaCode())) {
					errors.rejectValue("areaCode", "areaCode.required", "Please enter Area");
				}
			} else { 
				if (StringUtils.isEmpty(contact.getFlat()) && StringUtils.isEmpty(contact.getFloor())) {
					errors.rejectValue("flat", "flatOrFloor.required", "Please input flat or floor");
				}
				if (StringUtils.isNotEmpty(contact.getFlat()) && !StringUtils.isAsciiPrintable(contact.getFlat())) {
					errors.rejectValue("flat", "", "Please insert English or numeric character");
				}
				if (StringUtils.isNotEmpty(contact.getFloor()) && !StringUtils.isAsciiPrintable(contact.getFloor())) {
					errors.rejectValue("flat", "", "Please insert English or numeric character");
				}
				if (StringUtils.isNotEmpty(contact.getLotNum()) && !StringUtils.isAsciiPrintable(contact.getLotNum())) {
					errors.rejectValue("lotNum", "", "Please insert English or numeric character");
				}
				if (contact.isCustAddressFlag()) {
					if (StringUtils.isEmpty(contact.getBuildingName()) && StringUtils.isEmpty(contact.getStreetNum()) && StringUtils.isEmpty(contact.getLotNum())) {
						errors.rejectValue("lotNum", "buildingNamelotNumStreetNum.required", "Building Name or Street Number or Lot number must exist");
					}
					if ((StringUtils.isNotEmpty(contact.getStreetNum()) && StringUtils.isEmpty(contact.getStreetName())) ||
							(StringUtils.isEmpty(contact.getStreetNum()) && StringUtils.isNotEmpty(contact.getStreetName()))) {
						errors.rejectValue("streetNum", "streetNumStreetName.required", "Street Num. and Street Name are co-requisit to each other!");
					}
					if (StringUtils.isNotEmpty(contact.getLotNum()) && StringUtils.isNotEmpty(contact.getStreetNum())) {
						errors.rejectValue("lotNum", "lotNumStreetNum.exclusive", "Lot/house and street num are mutually exclusive");
					}
					if (StringUtils.isNotEmpty(contact.getLotNum())&& StringUtils.isNotEmpty(contact.getBuildingName())) {
						errors.rejectValue("lotNum", "lotNumBuildingName.exclusive", "Lot/house and building name are mutually exclusive");
					}
					if (StringUtils.isNotEmpty(contact.getLotNum())&& StringUtils.isNotEmpty(contact.getStreetName())) {
						errors.rejectValue("lotNum", "streetNamelotNum.exclusive", "Please enter Street Name or Lot/Hse No. but NOT both!");
					}
					if (StringUtils.isEmpty(contact.getDistrictCode())) {
						errors.rejectValue("districtCode", "districtCode.required", "Please enter District");
					}
					if (StringUtils.isEmpty(contact.getAreaCode())) {
						errors.rejectValue("areaCode", "areaCode.required", "Please enter Area");
					}
					if (StringUtils.isNotBlank(contact.getStreetName())) {
						if (StringUtils.isBlank(contact.getStreetCatgCode()) || StringUtils.isEmpty(contact.getStreetCatgCode())) {
							errors.rejectValue("streetCatgCode", "streetCatgCode.required", "Please select a value");
						}
					}
					if (StringUtils.isNotEmpty(contact.getBuildingName()) && !StringUtils.isAsciiPrintable(contact.getBuildingName())) {
						errors.rejectValue("lotNum", "", "Please insert English or numeric character");
					}
					if (StringUtils.isNotEmpty(contact.getStreetNum()) && !StringUtils.isAsciiPrintable(contact.getStreetNum())) {
						errors.rejectValue("streetNum", "", "Please insert English or numeric character");
					}
					if (StringUtils.isNotEmpty(contact.getStreetName()) && !StringUtils.isAsciiPrintable(contact.getStreetName())) {
						errors.rejectValue("lotNum", "", "Please insert English or numeric character");
					}
				} else {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quickSearch", "quickSearch.required"); // move to here 20110310
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "districtDesc", "quickSearch.required");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "areaDesc", "quickSearch.required");
					if (StringUtils.isNotEmpty(contact.getLotNum())&& StringUtils.isNotEmpty(contact.getStreetName())) {
						errors.rejectValue("lotNum", "streetNamelotNum.exclusive", "Please enter Street Name or Lot/Hse No. but NOT both!");
					}
				}
			}
			// 20110718, for Exception [BOM-OTH-99] District does not belong to
			// the specified area.
			if (false == Utility.checkDistrictArea(contact.getDistrictCode(),
					contact.getAreaCode())) {
				errors.rejectValue(
						"areaCode",
						"areaDistrict.error",
						"Please check Billing Area/District, District does not belong to the specified area.");
				logger.info("checkDistrictArea error: [BOM-OTH-99] District does not belong to the specified area.");
				logger.info("contact.getDistrictCode:"
						+ contact.getDistrictCode());
				logger.info("contact.getAreaCode:" + contact.getAreaCode());
			} else {
				logger.info("checkDistrictArea finish");
				logger.info("contact.getDistrictCode:"
						+ contact.getDistrictCode());
				logger.info("contact.getAreaCode:" + contact.getAreaCode());
			}

			// check for delivery date where it must be at most 10 days or 14
			// days that starts
			// to calculate from delivery date depends on the payment method
			if (contact.getPaymentMethod() != null) {

				if (contact.getDeliveryDateStr() != null
						&& !contact.getDeliveryDateStr().isEmpty()) {
					Date deliverDate = Utility.string2Date(contact
							.getDeliveryDateStr());

					Calendar calendar = new GregorianCalendar();

					boolean proceed = true;
					
					if (isDirtyData(contact)) {
						
						errors.rejectValue("deliveryTimeSlot",
								"deliveryTimeSlot.error",
								"Invalide Time Slot for Normal/Urgent delivery. Please refresh the page in order to proceed.");
					
						return;
					}
					
					boolean validTimeslot = false;
					String orderType = "ACQ";
					String delMode = "HD";
					String delInd = null;
					if (contact.isUrgentInd()) {
						delInd = "URGENT";
					} else {
						delInd = "NORMAL";
					}
					String hsInd = null;
					String hsItemCd = null;
					BasketDTO basketDto = (BasketDTO) contact.getValue("basketDTO");
					if (StringUtils.isNotBlank(basketDto.getHsPosItemCd())) {
						hsInd = "Y";
						hsItemCd = basketDto.getHsPosItemCd();
					} else {
						hsInd = "N";
						hsItemCd = "NONE";
					}
					String payMthd = null;
					if (StringUtils.isNotBlank(contact.getPaymentMethod())) {
						payMthd = contact.getPaymentMethod();
					} else {
						payMthd = "NONE";
					}
					String fsInd = "Y";
					MRTUI mrtui = (MRTUI) contact.getValue("mrt");
					String mnpInd = mrtui.getMnpInd();
					String orderId = null;
					if (StringUtils.isNotEmpty(contact.getOrderId())) {
						orderId = contact.getOrderId();
					} else {
						orderId = "NONE";
					}
					Date formattedAppDate = null;
					try {
						SimpleDateFormat sdf = new SimpleDateFormat();
						sdf.applyPattern("dd/MM/yyyy");
						formattedAppDate = sdf.parse((String) contact.getValue("appDate"));
					} catch (ParseException e) {
						formattedAppDate = null;
					}
					CcsDeliveryDateRangeDTO ccsDeliveryDateRangeDTO = deliveryService.getCcsDeliveryDateRange(orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, formattedAppDate);
					if (ccsDeliveryDateRangeDTO != null) {
						if (ccsDeliveryDateRangeDTO.getRetValue() == 0) {
							List<CcsDeliveryTimeSlotDTO> ccsDeliveryTimeSlotDTOList = deliveryService.getCcsDeliveryTimeslot(delMode, delInd, Utility.string2Date(contact.getDeliveryDateStr()), contact.getDistrictCode(), ccsDeliveryDateRangeDTO.getStartDate(), ccsDeliveryDateRangeDTO.getTimeSlot());
							if (CollectionUtils.isNotEmpty(ccsDeliveryTimeSlotDTOList)) {
								for (CcsDeliveryTimeSlotDTO ccsDeliveryTimeSlotDTO: ccsDeliveryTimeSlotDTOList) {
									if (StringUtils.isNotBlank(ccsDeliveryTimeSlotDTO.getTimeslot())) {
										if (ccsDeliveryTimeSlotDTO.getTimeslot().contains(contact.getDeliveryTimeSlot())) {
											int quotaRemain = Integer.parseInt(ccsDeliveryTimeSlotDTO.getQuotaRemain());
											if (quotaRemain <= 0) {
												errors.rejectValue("deliveryTimeSlot", "", "No delivery quota");
											}
											validTimeslot = true;
										}
									}
								}
								if (!validTimeslot) {
									errors.rejectValue("deliveryTimeSlot", "", "Invalid delivery Timeslot");
								}
							} else {
								errors.rejectValue("deliveryTimeSlot", "", "[Stored Procedure error] System error. Please try again in a few minutes later");
							}
						} else {
							errors.rejectValue("deliveryDateStr", "", "CCS_DELIVERY_DATE_RANGE Error code " + ccsDeliveryDateRangeDTO.getErrCode() + "Error Message " + ccsDeliveryDateRangeDTO.getErrText());
						}
					} else {
						errors.rejectValue("deliveryDateStr", "", "CCS_DELIVERY_DATE_RANGE [Stored Procedure error] System error. Please try again in a few minutes later");
					}
					
					// Activation Date and Delivery Date validation checking
					CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)mrtui.getValue("customer");
					String opssCases[]={"TGTG","T3TG","NPTG","M3M3","MPM3"};//dno rno
					if (mrtui != null && proceed) {

						Date serviceDate = new Date();
						String mrtType = null;
						if (("O".equalsIgnoreCase(mrtui.getMrtInd()) 
								|| "C".equalsIgnoreCase(mrtui.getMrtInd())) 
								&& "Y".equalsIgnoreCase(mrtui.getMnpInd())) {
								
							serviceDate = Utility.string2Date(mrtui.getCutOverDateStr());
							mrtType = "CUT";
							Date activeStartDate = DateUtils.dateAfterdays(deliverDate, 3);
							if (sessionCustomer!=null){
								if (StringUtils.isNotEmpty(mrtui.getOpssInd())  
										&& StringUtils.isNotEmpty(mrtui.getDno()) 
										&& StringUtils.isNotEmpty(sessionCustomer.getSimType())){
									if ("Y".equals(mrtui.getOpssInd()) && ArrayUtils.contains(opssCases, mrtui.getDno() + Utility.getRno(sessionCustomer.getSimType()))){
										activeStartDate = DateUtils.dateAfterdays(deliverDate, 1);
									}
								
								}
							}
						
							Date activeEndDate = DateUtils.dateAfterdays(Utility.string2Date((String) contact.getValue("appDate")), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
							if ("Y".equalsIgnoreCase(mrtui.getMnpExtendAuthInd())){
								activeEndDate = DateUtils.dateAfterdays(activeEndDate, BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);
							}
							
							checkDateRange(serviceDate, activeStartDate, activeEndDate, errors, null, mrtType);
								
						} else {
								
							serviceDate = Utility.string2Date(mrtui.getServiceReqDateStr());
							mrtType = "SER";
							Date activeStartDate = DateUtils.dateAfterdays(deliverDate, 1);
							Date activeEndDate = DateUtils.dateAfterdays(Utility.string2Date((String) contact.getValue("appDate")), 45);
							checkDateRange(serviceDate, activeStartDate, activeEndDate, errors, null, mrtType);
							
						}
						
					}

				}
			}

		}// end delivery check

	}

}
