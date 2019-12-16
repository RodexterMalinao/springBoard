package com.bomwebportal.sbs.validator;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.sbs.dto.SbsContactInfoDTO;
import com.bomwebportal.sbs.dto.SbsDeliveryInfoDTO;
import com.bomwebportal.sbs.dto.form.SbsContactDeliveryForm;
import com.bomwebportal.util.Utility;


public class SbsCustomerProfileValidator implements Validator{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean supports(Class clazz) {
		return SbsContactDeliveryForm.class.isAssignableFrom(clazz);
	}


	public void validate(Object obj, Errors errors){
		SbsContactDeliveryForm form = (SbsContactDeliveryForm) obj;
		
		SbsContactInfoDTO contact = form.getContactInfo();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactInfo.title", "title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactInfo.firstName", "firstName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactInfo.lastName", "lastName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactInfo.contactPhone", "contactPhone.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactInfo.emailAddr", "emailAddr.required");

		if (StringUtils.isNotEmpty(contact.getContactPhone())) {
			if (!Utility.validatePhoneNum(contact.getContactPhone())){
				errors.rejectValue("contactInfo.contactPhone", "invalid.contactPhone");	
			}

			
			if ("2".equals(form.getOrderSubType())) {
				// vkk
				if(!Utility.validateHKPhonePreFix(contact.getContactPhone())){
					errors.rejectValue("contactInfo.contactPhone", "contactPhonePrefix.Invalid");
				}
			} else {
				if (!StringUtils.startsWithAny(contact.getContactPhone(), new String[]{"5","6","9"})) {
					errors.rejectValue("contactInfo.contactPhone", "invalid.msisdn");
				}
			}
		}
		
		if (StringUtils.isNotEmpty(contact.getEmailAddr())) {
			if (!Utility.validateEmailMobSpecific(contact.getEmailAddr())) {//change validation function 20130722
				errors.rejectValue("contactInfo.emailAddr", "invalid.emailAddr");
			}
		}
		
		
		
		
		if (!"2".equals(form.getOrderSubType()))  {
			validateDeliveryInfo(form, errors);
		}
		

	}
	
	private void validateDeliveryInfo(SbsContactDeliveryForm dto, Errors errors) {
		
		SbsDeliveryInfoDTO delivery = dto.getDeliveryInfo();
		
		if (delivery.getDeliveryDate() == null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryInfo.deliveryDate",
					"deliveryDate.required");
		}
		
		if (StringUtils.isEmpty(delivery.getUnitNo())
				&& StringUtils.isEmpty(delivery.getFloorNo())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryInfo.unitNo", "flatOrFloor.required");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryInfo.buildNo", "buildingNamelotNumStreetNum.required");
		if (StringUtils.isNotEmpty(delivery.getStrNo()) ^ StringUtils.isNotEmpty(delivery.getStrName())) {
			errors.rejectValue("deliveryInfo.strNo", "streetNumStreetName.required", "Street Num. and Street Name are co-requisit to each other!" );
		}
		
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryInfo.strCatCd", "flatOrFloor.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryInfo.distNo", "districtCode.required", "Please enter District");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryInfo.areaCd", "areaCode.required", "Please enter Area");
		if (false ==Utility.checkDistrictArea(delivery.getDistNo(), delivery.getAreaCd() )){
			errors.rejectValue("deliveryInfo.distNo", "areaDistrict.error", "Please check Billing Area/District, District does not belong to the specified area.");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryInfo.deliveryDate", "deliveryDate.required", "Please select delivery date");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryInfo.deliveryTimeSlot", "deliveryTimeSlot.required", "Please select delivery time slot");

		try {
			checkValueinAscii(dto, errors, "deliveryInfo.unitNo", "deliveryInfo.floorNo", "deliveryInfo.buildNo", "deliveryInfo.strNo", "deliveryInfo.strName");
		} catch (Exception e) {
			
		}		
	}
	
	private void checkValueinAscii(SbsContactDeliveryForm dto, Errors errors, String ...fields ) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		for (String field : fields) {
			Object value = PropertyUtils.getSimpleProperty(dto, field);
			if (value instanceof String) {
				if (StringUtils.isNotBlank((String) value) && !StringUtils.isAsciiPrintable((String) value)) {
					errors.rejectValue(field, null, "Only English/Number/Symbol/Space");
				}
			}
		}
	}
	
	
	

}

