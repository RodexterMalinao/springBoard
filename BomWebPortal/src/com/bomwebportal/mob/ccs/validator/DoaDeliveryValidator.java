package com.bomwebportal.mob.ccs.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.sbs.dto.CcsDeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.CcsDeliveryTimeSlotDTO;
import com.bomwebportal.sbs.dto.DeliveryDateRangeDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.DateUtils;
import com.bomwebportal.util.Utility;

public class DoaDeliveryValidator implements Validator {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	DeliveryService deliveryService;
	
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
	
	public boolean supports(Class arg0) {
		return arg0.equals(DeliveryUI.class);
	}

	public void validate(Object obj, Errors errors) {
		DeliveryUI contact = (DeliveryUI) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"primaryContact.title", "title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"primaryContact.contactName", "contactName.required");
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custLastName",
		// "lastName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"primaryContact.contactPhone", "contactPhone.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"primaryContact.idDocNum", "idDocNum.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"primaryContact.idDocType", "idDocType.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryDateStr",
				"deliveryDate.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryTimeSlot",
				"deliveryTimeSlot.required", "Please select Delivery Time");
		
		
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
					if (!Utility.validatePhoneNum(contact.getThirdPartyContact()
									.getContactPhone())) {
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
			if (!Utility.validatePhoneNum(contact.getPrimaryContact()
					.getContactPhone())) {
				errors.rejectValue("primaryContact.contactPhone",
						"invalid.contactPhone");
			}
		}
		
		/*if ("P".equals(contact.getDeliveryInd())) {
			if ("".equals(contact.getDeliveryCentre())) {
				errors.rejectValue("deliveryCentre", "deliveryCentre.required");

			}

		}*/
		
		
		if ("D".equals(contact.getDeliveryInd())) {
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
			
		}
		
		
		
		// Application Date and Delivery Date validation checking
		
		/*Date deliverDate = Utility.string2Date(contact.getDeliveryDateStr());
		
		BasketDTO basketDto = (BasketDTO) contact.getValue("basket");
		DeliveryDateRangeDTO dateRange = deliveryService.normalDeliveryDateRange(contact.getOrderId(), contact.getPaymentMethod(), basketDto.getHsPosItemCd(), Utility.string2Date((String) contact.getValue("appDate")));
		
		if (dateRange == null)  {
			errors.rejectValue("deliveryDateStr", "deliveryDateStr.error",
					"System eror. Please try again in a few minutes later");
		} else {
			Date startDate = dateRange.getStartDate();
			Date endDate = dateRange.getEndDate();
			//check valid delivery date range
			if (DateUtils.daysDiffBetween(deliverDate, startDate) < 0 || DateUtils.daysDiffBetween(deliverDate, endDate) > 0) {
				errors.rejectValue("deliveryDateStr", "deliveryDateStr.error",
						"Delivery Date must be in between "
								+ Utility.date2String(startDate,
										BomWebPortalConstant.DATE_FORMAT) + " and " 
								+ Utility.date2String(endDate,
												BomWebPortalConstant.DATE_FORMAT));
			}
		}*/
		
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
		BasketDTO basketDto = (BasketDTO) contact.getValue("basket");
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
		String mnpInd = null;
		if (contact.isMnp()) {
			mnpInd = "Y";
		} else {
			mnpInd = "N";
		}
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
		Date deliverDate = Utility.string2Date(contact.getDeliveryDateStr());
		CcsDeliveryDateRangeDTO ccsDeliveryDateRangeDTO = deliveryService.getCcsDeliveryDateRange(orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, formattedAppDate);
		if (ccsDeliveryDateRangeDTO != null) {
			Date startDateRange = ccsDeliveryDateRangeDTO.getStartDate();
			Date endDateRange = ccsDeliveryDateRangeDTO.getEndDate();
			if (DateUtils.daysDiffBetween(deliverDate, startDateRange) < 0 || DateUtils.daysDiffBetween(deliverDate, endDateRange) > 0) {
				errors.rejectValue("topErrorPath", "deliveryDateStr.error", "Delivery Date must be in between "
								+ Utility.date2String(startDateRange, BomWebPortalConstant.DATE_FORMAT) 
								+ " and " 
								+ Utility.date2String(endDateRange, BomWebPortalConstant.DATE_FORMAT));
			}
		} else {
			errors.rejectValue("deliveryDateStr", "", "CCS_DELIVERY_DATE_RANGE [Stored Procedure error] System error. Please try again in a few minutes later");
		}
		
	}
	
}
