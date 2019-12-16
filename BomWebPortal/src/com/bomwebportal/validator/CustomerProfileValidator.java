package com.bomwebportal.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustomerBasicInfoDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.csportal.object.CsldInqArq;
import com.bomwebportal.csportal.service.CsPortalService;
import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.mob.validate.dto.ResultDTO;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.BulkNewActClient;
import com.bomwebportal.wsclient.CustCreditCheckClient;
import com.bomwebportal.wsclient.CustCreditCheckClient.CreditCheckResult;
import com.google.gson.Gson;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.pccw.bom.mob.schemas.CustomerResultDTO;

public class CustomerProfileValidator implements Validator {

	protected final Log logger = LogFactory.getLog(getClass());

	private CsPortalService csPortalService; // cs portal Athena 20131004
	private CodeLkupService codeLkupService;

	public boolean supports(Class clazz) {
		return clazz.equals(CustomerProfileDTO.class);
	}

	private BulkNewActClient bulkNewActClient;

	private CustCreditCheckClient custCreditCheckClient;

	public BulkNewActClient getBulkNewActClient() {
		return bulkNewActClient;
	}

	public void setBulkNewActClient(BulkNewActClient bulkNewActClient) {
		this.bulkNewActClient = bulkNewActClient;
	}

	public CustCreditCheckClient getCustCreditCheckClient() {
		return custCreditCheckClient;
	}

	public void setCustCreditCheckClient(CustCreditCheckClient custCreditCheckClient) {
		this.custCreditCheckClient = custCreditCheckClient;
	}

	public CsPortalService getCsPortalService() {// cs portal Athena 20131004
		return csPortalService;
	}

	public void setCsPortalService(CsPortalService csPortalService) {// cs
																		// portal
																		// Athena
																		// 20131004
		this.csPortalService = csPortalService;
	}

	private ValidateService validateService;

	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	private CustomerProfileService customerProfileService;

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}
	
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	
	public void validate(Object obj, Errors errors) {

		CustomerProfileDTO contact = (CustomerProfileDTO) obj;
		if (contact.isByPassValidation()) {
			if ("NONE".equals(contact.getTitle())) {
				errors.rejectValue("title", "title.required");
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custFirstName", "firstName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custLastName", "lastName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPhone", "contactPhone.required");

			// add by wilson 20120314, Fault ID: 0002498
			// pattern check contactPhone
			if (contact.getContactPhone() != null && !"".equals(contact.getContactPhone())) {
				if (!Utility.validatePhoneNum(contact.getContactPhone())) {
					errors.rejectValue("contactPhone", "invalid.contactPhone");
				}
				// add by wilson 20120314, Fault ID: 0002498
				if (!Utility.validateHKPhonePreFix(contact.getContactPhone())) {
					errors.rejectValue("contactPhone", "contactPhonePrefix.Invalid");
				}
			}

			return; // add by wilson 20120302, by pass validation
		}

		// check rejectIfEmptyOrWhitespace
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custFirstName", "firstName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custLastName", "lastName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idDocType", "idDocType.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idDocNum", "idDocNum.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPhone", "contactPhone.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addrProofInd", "addrProofInd.required");
		// combine account
		if ("current".equalsIgnoreCase(contact.getAcctType())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "activeMobileNum", "activeMobileNum.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "acctInfo", "acctInfo.required");
		}
		// modify and add by eliot 20110608
		if ("HKID".equals(contact.getIdDocType())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dobStr", "dobDay.required");
		} else if ("PASS".equals(contact.getIdDocType())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dobStr", "dobDay.required");
		} else if ("BS".equals(contact.getIdDocType())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "companyName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repIdDocType", "idDocType.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repIdDocNum", "idDocNum.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyDoc", "companyDoc.required");
		}

		// select type checking
		if ("NONE".equals(contact.getTitle())) {
			errors.rejectValue("title", "title.required");
		}

		if (contact.getDobStr() != null && !"".equals(contact.getDobStr().trim())) {
			if (!Utility.isValidDate(contact.getDobStr())) {
				errors.rejectValue("dobStr", "invalid.Date");
			} else {

				if (contact.isStudentPlanSubInd()) {
					// Check if the applicant is 11 - 18
					ResultDTO result = validateService.validateStudentPlanDob(contact.getAppDateStr(), contact.getDobStr(), "dobStr");
					validateService.bindErrors(result, errors);

				} else {
					// Check if the applicant is over 18
					Calendar currentDate = Calendar.getInstance();
					Calendar compareDate = Calendar.getInstance();

					compareDate.setTime(Utility.string2Date(contact.getDobStr()));
					compareDate.add(Calendar.YEAR, 18);
					logger.info("currentDate: " + currentDate.getTime());
					logger.info("compareDate: " + compareDate.getTime());
					if (compareDate.after(currentDate)) {
						errors.rejectValue("dobStr", "dobDay.over18Required");
					} else {
						// Check the year range: (18+82)= 100 years
						compareDate.add(Calendar.YEAR, 82);
						logger.info("compareDate: " + compareDate.getTime());
						if (compareDate.before(currentDate)) {
							errors.rejectValue("dobStr", "invalid.Date");
						}
					}
				}

			}
		}

		if (contact.getIdDocNum().trim().length() > 30) {
			errors.rejectValue("idDocNum", "error.contact.idDocNum.length", null, "error.contact.idDocNum.lengthL(default)");
		}

		// Pattern check idDocType
		if (contact.getIdDocType() != null) {
			// Check the HKID pattern
			if ("HKID".equals(contact.getIdDocType()) && !"".equals(contact.getIdDocNum())) {
				if (Utility.validateHKID(contact.getIdDocNum()) == false) {
					errors.rejectValue("idDocNum", "invalid.hkid");
				} else {
					if (Utility.validateHKIDcheckDigit(contact.getIdDocNum()) == false) {
						errors.rejectValue("idDocNum", "invalid.hkidCheckDigit");
					}
				}
				// James 20120309 @Domestic Helper HKID Validation
				if (contact.isForeignDomesticHelperInd()) {
					if (!contact.getIdDocNum().startsWith("W")) {
						errors.rejectValue("idDocNum", "invalid.domHelpFormat");
					}

					if (contact.getIdDocNum().startsWith("W") && !Utility.isDigit(contact.getIdDocNum().substring(1, 2)) && !contact.getIdDocNum().substring(1, 2).equalsIgnoreCase("X")) {
						errors.rejectValue("idDocNum", "invalid.domHelpFormat");
					}
				} else {
					if (contact.getIdDocNum().startsWith("W") && Utility.isDigit(contact.getIdDocNum().substring(1, 2))) {
						errors.rejectValue("idDocNum", "invalid.notDomHelpFormat");
					}

					if (contact.getIdDocNum().startsWith("W") && contact.getIdDocNum().substring(1, 2).equalsIgnoreCase("X")) {
						errors.rejectValue("idDocNum", "invalid.notDomHelpFormat");
					}

				}
			}
			// Check the HKBR pattern
			// add by eliot 20110608
			if ("BS".equals(contact.getIdDocType()) && !"".equals(contact.getIdDocNum())) {
				if (Utility.validateHKBR(contact.getIdDocNum()) == false) {
					errors.rejectValue("idDocNum", "invalid.hkbr");
				} else {
					if (Utility.validateHKBRcheckDigit(contact.getIdDocNum()) == false) {
						errors.rejectValue("idDocNum", "invalid.hkbrCheckDigit");
					}
				}

				// check representative id doc
				if (contact.getRepIdDocType() != null) {
					if ("HKID".equals(contact.getRepIdDocType()) && !"".equals(contact.getRepIdDocNum())) {
						if (Utility.validateHKID(contact.getRepIdDocNum()) == false) {
							errors.rejectValue("repIdDocNum", "invalid.hkid");
						} else {
							if (Utility.validateHKIDcheckDigit(contact.getRepIdDocNum()) == false) {
								errors.rejectValue("repIdDocNum", "invalid.hkidCheckDigit");
							}
						}
						if (contact.isForeignDomesticHelperInd()) {
							if (!contact.getRepIdDocNum().startsWith("W")) {
								errors.rejectValue("repIdDocNum", "invalid.domHelpFormat");
							}

							if (contact.getRepIdDocNum().startsWith("W") && !Utility.isDigit(contact.getRepIdDocNum().substring(1, 2)) && !contact.getRepIdDocNum().substring(1, 2).equalsIgnoreCase("X")) {
								errors.rejectValue("repIdDocNum", "invalid.domHelpFormat");
							}
						} else {
							if (contact.getRepIdDocNum().startsWith("W") && Utility.isDigit(contact.getRepIdDocNum().substring(1, 2))) {
								errors.rejectValue("repIdDocNum", "invalid.notDomHelpFormat");
							}

							if (contact.getRepIdDocNum().startsWith("W") && contact.getRepIdDocNum().substring(1, 2).equalsIgnoreCase("X")) {
								errors.rejectValue("repIdDocNum", "invalid.notDomHelpFormat");
							}

						}
					}
					if ("PASS".equals(contact.getRepIdDocType()) && !"".equals(contact.getRepIdDocNum())) {
						if (Utility.validatePassport(contact.getRepIdDocNum()) == false) {
							errors.rejectValue("repIdDocNum", "invalid.pass", "Invalid passport number. Please input again.");
						}
					}
				}
			}

			if ("PASS".equals(contact.getIdDocType()) && !"".equals(contact.getIdDocNum())) {
				if (Utility.validatePassport(contact.getIdDocNum()) == false) {
					errors.rejectValue("idDocNum", "invalid.pass", "Invalid passport number. Please input again.");
				}
			}

			// Check customer profile in BOM by web service

			if (!"".equals(contact.getIdDocNum()) && contact.getIgnoreCustomerCheck() == false) {

				boolean ccc_done = false;
				try {

					CreditCheckResult ccResult = custCreditCheckClient.checkCustomer(contact.getIdDocType(), contact.getIdDocNum());
					System.out.println("xxxxxxxxxxxxx=> " + new Gson().toJson(ccResult));

					// TODO: should check 0, 4 is pass ...
					// if Not pass and status = "3", need Authorize AU02, show
					// auth button, if auth'ed, pass...
					// Other status ... NO allowed.
					if (ccResult != null && !ccResult.isPass()) { // not ("0" -
																	// all pass
																	// or "4" -
																	// not
																	// found)
						if ("3".equals(ccResult.getStatus())) { // show
																// authorize
																// button
							if (!"Y".equalsIgnoreCase(contact.getMrtThresholdOverflowAuthInd())) {
								contact.setMrtThresholdOverflow(true);
								errors.rejectValue("idDocNum", "validate.creditStatus", ccResult.getDescription());
							}
						} else {
							contact.setMrtThresholdOverflow(false);
							errors.rejectValue("idDocNum", "validate.creditStatus", ccResult.getDescription());
						}
					} else {
						contact.setMrtThresholdOverflow(false);
					}

					ccc_done = true;
					contact.setIsBomWsAvailable(true);
					/*
					 * already checked by CCC ... skip this CustomerResultDTO
					 * customerResult =
					 * bulkNewActClient.checkCustomer(contact.getIdDocType(),
					 * contact.getIdDocNum());
					 * 
					 * logger.info("Check Customer Error Code: "+
					 * customerResult.getErrCode());
					 * if(!"0".equals(customerResult.getErrCode())){
					 * logger.warn(customerResult.getErrMsg());
					 * errors.rejectValue("idDocNum",
					 * customerResult.getErrCode(), null,
					 * customerResult.getErrMsg()); }
					 */
				} catch (Exception e) {
					contact.setIsBomWsAvailable(false);
					logger.error(e.getMessage(), e);

				}

				// cannot check ccc .. check bom ws instead..
				if (!ccc_done) {
					try {
						CustomerResultDTO customerResult = bulkNewActClient.checkCustomer(contact.getIdDocType(), contact.getIdDocNum());

						logger.info("Check Customer Error Code: " + customerResult.getErrCode());
						if (!"0".equals(customerResult.getErrCode())) {
							logger.warn(customerResult.getErrMsg());
							errors.rejectValue("idDocNum", customerResult.getErrCode(), null, customerResult.getErrMsg());
						}
						contact.setIsBomWsAvailable(true);
					} catch (Exception e) {
						contact.setIsBomWsAvailable(false);
						logger.error(e.getMessage(), e);

					}
				}
				if (!contact.getIsBomWsAvailable()) {
					errors.reject("");
				}

			}
		}

		// Actual User Validation
		if (StringUtils.isNotEmpty(contact.getSameAsCustInd()) && !contact.isSameAsCust()) {

			// check rejectIfEmptyOrWhitespace
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actualUserDTO.subTitle", "title.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actualUserDTO.subFirstName", "firstName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actualUserDTO.subLastName", "lastName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actualUserDTO.subDocType", "idDocType.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actualUserDTO.subDocNum", "idDocNum.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actualUserDTO.subContactTel", "contactPhone.required");

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actualUserDTO.subDateOfBirthStr", "dobDay.required");

			// select type checking
			if ("NONE".equals(contact.getActualUserDTO().getSubTitle())) {
				errors.rejectValue("actualUserDTO.subTitle", "title.required");
			}

			if (contact.getActualUserDTO().getSubDateOfBirthStr() != null && !"".equals(contact.getActualUserDTO().getSubDateOfBirthStr().trim())) {
				if (!Utility.isValidDate(contact.getActualUserDTO().getSubDateOfBirthStr())) {
					errors.rejectValue("actualUserDTO.subDateOfBirthStr", "invalid.Date");
				} else {
					Calendar currentDate = Calendar.getInstance();
					Calendar compareDate = Calendar.getInstance();

					compareDate.setTime(Utility.string2Date(contact.getActualUserDTO().getSubDateOfBirthStr()));

					logger.info("currentDate: " + currentDate.getTime());

					// Check the year range: 100 years
					compareDate.add(Calendar.YEAR, 100);
					logger.info("compareDate: " + compareDate.getTime());
					if (compareDate.before(currentDate)) {
						errors.rejectValue("actualUserDTO.subDateOfBirthStr", "invalid.Date");
					}

				}
			}

			if (contact.getActualUserDTO().getSubDocNum().trim().length() > 30) {
				errors.rejectValue("actualUserDTO.subDocNum", "error.contact.idDocNum.length", null, "error.contact.idDocNum.lengthL(default)");
			}

			// Pattern check idDocType
			if (contact.getActualUserDTO().getSubDocType() != null) {
				// Check the HKID pattern
				if ("HKID".equals(contact.getActualUserDTO().getSubDocType()) && !"".equals(contact.getActualUserDTO().getSubDocNum())) {
					if (Utility.validateHKID(contact.getActualUserDTO().getSubDocNum()) == false) {
						errors.rejectValue("actualUserDTO.subDocNum", "invalid.hkid");
					} else {
						if (Utility.validateHKIDcheckDigit(contact.getActualUserDTO().getSubDocNum()) == false) {
							errors.rejectValue("actualUserDTO.subDocNum", "invalid.hkidCheckDigit");
						}
					}
				}
				// Check the HKBR pattern
				if ("BS".equals(contact.getActualUserDTO().getSubDocType()) && !"".equals(contact.getActualUserDTO().getSubDocNum())) {
					if (Utility.validateHKBR(contact.getActualUserDTO().getSubDocNum()) == false) {
						errors.rejectValue("actualUserDTO.subDocNum", "invalid.hkbr");
					} else {
						if (Utility.validateHKBRcheckDigit(contact.getActualUserDTO().getSubDocNum()) == false) {
							errors.rejectValue("actualUserDTO.subDocNum", "invalid.hkbrCheckDigit");
						}
					}

				}

				if ("PASS".equals(contact.getActualUserDTO().getSubDocType()) && !"".equals(contact.getActualUserDTO().getSubDocNum())) {
					if (Utility.validatePassport(contact.getActualUserDTO().getSubDocNum()) == false) {
						errors.rejectValue("actualUserDTO.subDocNum", "invalid.pass", "Invalid passport number. Please input again.");
					}
				}
			}

			// pattern check contactPhone
			if (contact.getActualUserDTO().getSubContactTel() != null && !"".equals(contact.getActualUserDTO().getSubContactTel())) {
				if (!Utility.validatePhoneNum(contact.getActualUserDTO().getSubContactTel())) {
					errors.rejectValue("actualUserDTO.subContactTel", "invalid.contactPhone");
				}

				if (!Utility.validateHKPhonePreFix(contact.getActualUserDTO().getSubContactTel())) {
					errors.rejectValue("actualUserDTO.subContactTel", "contactPhonePrefix.Invalid");
				}
			}

			// if have email address

			// pattern check e-mail
			if (contact.getActualUserDTO().getSubEmailAddr() != null && !"".equals(contact.getActualUserDTO().getSubEmailAddr())) {
				if (!Utility.validateEmailMobSpecific(contact.getActualUserDTO().getSubEmailAddr())) {// change
																										// validation
																										// function
																										// 20130722
					errors.rejectValue("actualUserDTO.subEmailAddr", "invalid.emailAddr");
				}

			}

		}

		// pattern check contactPhone
		if (contact.getContactPhone() != null && !"".equals(contact.getContactPhone())) {
			if (!Utility.validatePhoneNum(contact.getContactPhone())) {
				errors.rejectValue("contactPhone", "invalid.contactPhone");
			}
			// add by wilson 20120314, Fault ID: 0002498
			if (!Utility.validateHKPhonePreFix(contact.getContactPhone())) {
				errors.rejectValue("contactPhone", "contactPhonePrefix.Invalid");
			}
		}

		// if have email address edit by wilson 20110302
		if (!contact.isNoEmailFlag()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddr", "emailAddr.required");
			// pattern check e-mail
			if (contact.getEmailAddr() != null && !"".equals(contact.getEmailAddr())) {
				if (!Utility.validateEmailMobSpecific(contact.getEmailAddr()) || contact.getEmailAddr().contains("@theclub.com.hk")) {// change
																																		// validation
																																		// function
																																		// 20130722
					errors.rejectValue("emailAddr", "invalid.emailAddr");
				}

			}
		}

		if (!contact.isMob0060OptOutInd() && contact.isNoEmailFlag()) {
			errors.rejectValue("emailAddr", "emailAddr.Mandatory");
		}

		if (contact.isClubOptOut() || contact.isHktClubOptOut()) {
			if (contact.getClubOptRea() == null || "".equals(contact.getClubOptRea())) {
				errors.rejectValue("clubOptRea", "dummy", "Please select an opt-out reason.");
			} else if (contact.getClubOptRea().equals("51") && "".equals(contact.getClubOptRmk().trim())) {
				errors.rejectValue("clubOptRmk", "dummy", "Please input opt-out remark or specify an opt-out reason.");
			}
		}

		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quickSearch",
		// "quickSearch.required");// move to if (contact.isCustAddressFlag())
		// 20110310
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "districtDesc",
		// "quickSearch.required");
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "areaDesc",
		// "quickSearch.required");

		if ("".equals(contact.getFlat()) && "".equals(contact.getFloor())) {
			// if lot/hse not null, allow no flat or floor
			if ("".equals(contact.getLotNum())) {
				errors.rejectValue("flat", "flatOrFloor.required");
			}
		}

		if ("-".equals(contact.getFlat().trim())) {
			errors.rejectValue("flat", "dummy", "Address format is invalid, please re-input.");
		}

		if ("-".equals(contact.getFloor().trim())) {
			errors.rejectValue("floor", "dummy", "Address format is invalid, please re-input.");
		}

		// add 201105116
		if (!contact.isNoBillingAddressFlag()) {// have billing address

			if ("".equals(contact.getBillingFlat()) && "".equals(contact.getBillingFloor())) {
				if ("".equals(contact.getBillingLotNum())) {
					errors.rejectValue("billingFlat", "flatOrFloor.required");
				}
			}

			if ("-".equals(contact.getBillingFlat().trim())) {
				errors.rejectValue("billingFlat", "dummy", "Address format is invalid, please re-input.");
			}

			if ("-".equals(contact.getBillingFloor().trim())) {
				errors.rejectValue("billingFloor", "dummy", "Address format is invalid, please re-input.");
			}

		}

		// 2 == Refer Address Proof
		if ("2".equals(contact.getAddrProofInd())) {
			if ("".equals(contact.getLob())) {
				errors.rejectValue("lob", "lob.required");
			}
			if ("".equals(contact.getServiceNum())) {
				errors.rejectValue("serviceNum", "serviceNum.required");
			}
			CustomerBasicInfoDTO customerBasicInfoDTO = contact.getCustomerBasicInfoDTO();
			if (customerBasicInfoDTO != null) {
				if (!customerBasicInfoDTO.getIdDocType().equalsIgnoreCase(contact.getIdDocType()) || !customerBasicInfoDTO.getIdDocNum().equalsIgnoreCase(contact.getIdDocNum())) {
					errors.rejectValue("idDocNum", "dummy", "Service number in Address Proof does not match with Document Type/Number.");
				}
			}
		} else if ("5".equals(contact.getAddrProofInd())) { // 5 ==
															// Pre-activation
			if ("BS".equals(contact.getIdDocType())) {
				errors.rejectValue("idDocType", "dummy", "Pre - activation is not allowed for HKBR customer");
			}
		}

		if (contact.isStudentPlanSubInd()) {

			ResultDTO validateIdDocType = validateService.validateStudentPlanIdDocType(contact.getIdDocType(), "idDocType");
			validateService.bindErrors(validateIdDocType, errors);

		}

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
		if (contact.isCustAddressFlag()) {
			if ("-".equals(contact.getBuildingName().trim())) {
				errors.rejectValue("buildingName", "dummy", "Address format is invalid, please re-input.");
			}
			if (StringUtils.isEmpty(contact.getBuildingName()) && StringUtils.isEmpty(contact.getStreetNum()) && StringUtils.isEmpty(contact.getLotNum())) {
				errors.rejectValue("lotNum", "buildingNamelotNumStreetNum.required", "Building Name or Street Number or Lot number must exist");
			}
			if ((StringUtils.isNotEmpty(contact.getStreetNum()) && StringUtils.isEmpty(contact.getStreetName())) || (StringUtils.isEmpty(contact.getStreetNum()) && StringUtils.isNotEmpty(contact.getStreetName()))) {
				errors.rejectValue("streetNum", "streetNumStreetName.required", "Street Num. and Street Name are co-requisit to each other!");
			}
			if (StringUtils.isNotEmpty(contact.getLotNum()) && StringUtils.isNotEmpty(contact.getStreetNum())) {
				errors.rejectValue("lotNum", "lotNumStreetNum.exclusive", "Lot/house and street num are mutually exclusive");
			}
			if (StringUtils.isNotEmpty(contact.getLotNum()) && StringUtils.isNotEmpty(contact.getBuildingName())) {
				errors.rejectValue("lotNum", "lotNumBuildingName.exclusive", "Lot/house and building name are mutually exclusive");
			}
			if (StringUtils.isNotEmpty(contact.getLotNum()) && StringUtils.isNotEmpty(contact.getStreetName())) {
				errors.rejectValue("lotNum", "streetNamelotNum.exclusive", "Please enter Street Name or Lot/Hse No. but NOT both!");
			}
			if (StringUtils.isEmpty(contact.getDistrictCode())) {
				errors.rejectValue("districtCode", "districtCode.required", "Please enter District");
			}
			if (StringUtils.isEmpty(contact.getAreaCode())) {
				errors.rejectValue("areaCode", "areaCode.required", "Please enter Area");
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
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quickSearch", "quickSearch.required"); // move
																										// to
																										// here
																										// 20110310
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "districtDesc", "quickSearch.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "areaDesc", "quickSearch.required");
			if (StringUtils.isNotEmpty(contact.getLotNum()) && StringUtils.isNotEmpty(contact.getStreetName())) {
				errors.rejectValue("lotNum", "streetNamelotNum.exclusive", "Please enter Street Name or Lot/Hse No. but NOT both!");
			}
		}
		// 20110718, for Exception [BOM-OTH-99] District does not belong to the
		// specified area.
		if (false == Utility.checkDistrictArea(contact.getDistrictCode(), contact.getAreaCode())) {
			errors.rejectValue("areaCode", "areaDistrict.error", "Please check Billing Area/District, District does not belong to the specified area.");
			logger.info("checkDistrictArea error: [BOM-OTH-99] District does not belong to the specified area.");
			logger.info("contact.getDistrictCode:" + contact.getDistrictCode());
			logger.info("contact.getAreaCode:" + contact.getAreaCode());
		} else {
			logger.info("checkDistrictArea finish");
			logger.info("contact.getDistrictCode:" + contact.getDistrictCode());
			logger.info("contact.getAreaCode:" + contact.getAreaCode());
			// errors.rejectValue("areaCode", "title.required333",
			// "[testing only~~~~~]Please enter Area");
		}

		// add 20110516
		if (!contact.isNoBillingAddressFlag()) {
			if (contact.isBillingCustAddressFlag()) {
				if ("-".equals(contact.getBillingBuildingName().trim())) {
					errors.rejectValue("billingBuildingName", "dummy", "Address format is invalid, please re-input.");
				}

				if ("".equals(contact.getBillingBuildingName()) && "".equals(contact.getBillingStreetNum()) && "".equals(contact.getBillingLotNum())) {
					errors.rejectValue("billingLotNum", "buildingNamelotNumStreetNum.required", "Building Name or Street Number or Lot number must exist");

				}

				if ((!"".equals(contact.getBillingStreetNum()) && "".equals(contact.getBillingStreetName())) || ("".equals(contact.getBillingStreetNum()) && !"".equals(contact.getBillingStreetName()))

				) {
					errors.rejectValue("billingStreetNum", "streetNumStreetName.required", "Street Num. and Street Name are co-requisit to each other!");
				}

				if (!"".equals(contact.getBillingLotNum()) && !"".equals(contact.getBillingStreetNum())) {
					errors.rejectValue("billingLotNum", "lotNumStreetNum.exclusive", "Lot/Hse and Street Num are mutually exclusive.");

				}

				if (!"".equals(contact.getBillingLotNum()) && !"".equals(contact.getBillingBuildingName())) {
					errors.rejectValue("billingLotNum", "lotNumBuildingName.exclusive", "Lot/Hse and Building Name are mutually exclusive.");
				}

				if (!"".equals(contact.getBillingLotNum()) && !"".equals(contact.getBillingStreetName())) {
					errors.rejectValue("billingLotNum", "streetNamelotNum.exclusive", "Please enter Street Name or Lot/Hse No. but NOT both!");
				}

				if ("".equals(contact.getBillingAreaCode())) {
					errors.rejectValue("billingAreaCode", "areaCode.required", "Please enter Area");
				}
				if ("".equals(contact.getBillingDistrictCode())) {
					errors.rejectValue("billingDistrictCode", "districtCode.required", "Please enter District");
				}

			} else {

				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingQuickSearch", "quickSearch.required");// move
																												// to
																												// here
																												// 20110310
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingDistrictDesc", "quickSearch.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingAreaDesc", "quickSearch.required");
				if (!"".equals(contact.getBillingLotNum()) && !"".equals(contact.getBillingStreetName())) {
					errors.rejectValue("billingLotNum", "serviceNum.requireddddd", "Please enter Street Name or Lot/Hse No. but NOT both!");
				}

			}
			// 20110718, for Exception [BOM-OTH-99] District does not belong to
			// the specified area.
			if (false == Utility.checkDistrictArea(contact.getBillingDistrictCode(), contact.getBillingAreaCode())) {
				errors.rejectValue("billingAreaCode", "areaDistrict.error", "Please check Billing Area/District, District does not belong to the specified area.");
				logger.info("Billing checkDistrictArea error: [BOM-OTH-99] District does not belong to the specified area.");
				logger.info("contact.getBillingDistrictCode:" + contact.getBillingDistrictCode());
				logger.info("contact.getBillingAreaCode:" + contact.getBillingAreaCode());
			} else {
				logger.info("Billing checkDistrictArea finish");
				logger.info("contact.getBillingDistrictCode:" + contact.getBillingDistrictCode());
				logger.info("contact.getBillingAreaCode:" + contact.getBillingAreaCode());
				// errors.rejectValue("billingAreaCode", "title.required333",
				// "[testing only~~~~~]Please enter Billing Area");
			}

		}

		if (contact.getSelectedBillMedia() != null && contact.getSelectedBillMedia().contains("EML_SUM_RC") && contact.isNoEmailFlag()) {
			errors.rejectValue("selectedBillMedia", "dummy", "Please provide an email address for the email bill option.");
		}

		if (contact.getEmailAddr() != null && contact.getEmailAddr().toLowerCase().contains("@thclub.com.hk")) {
			errors.rejectValue("emailAddr", "dummy", "email address contains @thclub.com.hk");
		}

		if (contact.isCsPortalBool()) {// cs portal Athena 20131004
			if (!"D".equals(contact.getCsPortalInd())) {
				contact.setCsPortalInd("Y");
			}
			if (contact.isNoEmailFlag()) {
				// errors.rejectValue("csPortalBool","dummy",
				// "Please input an email address to register My HKT and/or The Club account or untick the option"
				// );
				contact.setEmailAddr("");
			}

			if (StringUtils.isNotBlank(contact.getEmailAddr())) {
				String myHktEmail = "";
				String clubEmail = "";
				String inUseMsg = "";
				if (contact.getCsPortalStatus() == null) {
					contact.setCsPortalStatus("");
				}
				if (!contact.getCsPortalStatus().contains("H")) {
					myHktEmail = contact.getEmailAddr();
				}
				if (!contact.getCsPortalStatus().contains("C")) {
					clubEmail = contact.getEmailAddr();
				}
				try {
					CsldInqArq csResult = csPortalService.idCheck(contact.getIdDocType(), contact.getIdDocNum(), myHktEmail, clubEmail);
					if ("RC_IN_USE".equals(csResult.oMyHktLiStatus)) {
						inUseMsg = "My HKT";
					}
					if ("RC_IN_USE".equals(csResult.oClubLiStatus)) {
						if (StringUtils.isBlank(inUseMsg)) {
							inUseMsg = "The Club";
						} else {
							inUseMsg += " / The Club";
						}
					}
					if (StringUtils.isNotBlank(inUseMsg)) {
						errors.rejectValue("csPortalBool", "dummy", inUseMsg + " account is registered under this email. Please input another email address or tick \"No Email\" option.");
					}
				} catch (Exception e) {
					errors.rejectValue("csPortalBool", "dummy", "Cannot check CS Portal Status at the moment.  CS Portal Registration will not proceed for this order.");
				}
			}
		} else {
			if ("D".equals(contact.getCsPortalInd())) {
				// errors.rejectValue("csPortalBool","dummy",
				// "Cannot untick this option as CS Portal account is already registered."
				// );
			} else {
				contact.setCsPortalInd("N");
			}
		}

		// further check for non-alphabet fields
		try {
			this.checkValueinAscii(contact, new String[] { "flat", "floor", "lotNum" }, errors);
			if (contact.isCustAddressFlag()) {
				this.checkValueinAscii(contact, new String[] { "buildingName", "streetNum", "streetName" }, errors);
			}
			if (!contact.isNoBillingAddressFlag()) {
				this.checkValueinAscii(contact, new String[] { "billingFlat", "billingFloor", "billingLotNum" }, errors);
				if (contact.isBillingCustAddressFlag()) {
					this.checkValueinAscii(contact, new String[] { "billingBuildingName", "billingStreetNum", "billingStreetName" }, errors);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception found in checkValueinAscii", e);
		}
		// local/roaming data alert settings checking

		// check email
		if (!contact.isSameAsEbillAddr()) {
			if (contact.getPcrfAlertEmail() != null && !"".equals(contact.getPcrfAlertEmail())) {
				if (!Utility.validateEmailMobSpecific(contact.getPcrfAlertEmail())) {
					errors.rejectValue("pcrfAlertEmail", "invalid.emailAddr");
				}
				if (contact.getPcrfAlertEmail().equalsIgnoreCase(contact.getEmailAddr())) {
					errors.rejectValue("pcrfAlertEmail", "dummy", "Same as email billing address");
				}
			}
		}

		// check sms phone num
		// if
		// ("01".equalsIgnoreCase(contact.getPcrfSmsRecipient())||"02".equalsIgnoreCase(contact.getPcrfSmsRecipient())){
		if (contact.getPcrfSmsNum() != null && !"".equals(contact.getPcrfSmsNum())) {
			if (!Utility.validatePhoneNum(contact.getPcrfSmsNum())) {
				errors.rejectValue("pcrfSmsNum", "invalid.contactPhone");
			}

			if (!Utility.validateHKPhonePreFix(contact.getPcrfSmsNum())) {
				errors.rejectValue("pcrfSmsNum", "contactPhonePrefix.Invalid");
			}
		}/*
		 * else { errors.rejectValue("pcrfSmsNum", "contactPhone.required"); }
		 */
		// }

		if (contact.isCareCashRegisterTimeInd() && !contact.isCareCashOrderSignOffInd()) {
			if (("".equals(contact.getoBiptStatus()) || "N".equals(contact.getoBiptStatus())) && "Y".equals(contact.getCareStatus())) {
				if (contact.getCareOptInd() == null) {
					errors.rejectValue("careOptInd", "dummy", "Please select iGuard CARECash option");
				} else {
					if ("Y".equals(contact.getCareStatus()) && contact.getCareOptInd().equals("I")) {
						if (!"HKID".equals(contact.getIdDocType())) {
							errors.rejectValue("idDocType", "dummy", "Please choose HKID for CARECash subscription");
						}
						if (contact.isStudentPlanSubInd()) {
							errors.rejectValue("idDocType", "dummy", "Student plan cannot subscribe CareCash");
						}
						if (contact.isForeignDomesticHelperInd()) {
							errors.rejectValue("idDocType", "dummy", "Domestic helper cannot subscribe CareCash");
						}
						if (contact.isNoEmailFlag()) {
							errors.rejectValue("emailAddr", "dummy", "Please provide Email for CARECash subscription");
						}

						Date date = new Date();
						Calendar todayDate = Calendar.getInstance();
						todayDate.setTime(date);
						int todayYear = todayDate.get(Calendar.YEAR);
						int todayMonth = todayDate.get(Calendar.MONTH) + 1;
						int todayDay = todayDate.get(Calendar.DATE);

						if (StringUtils.isNotEmpty(contact.getDobStr())) {
							Calendar dobDate = Calendar.getInstance();
							dobDate.setTime(Utility.string2Date(contact.getDobStr()));

							int dobYear = dobDate.get(Calendar.YEAR);
							int dobMonth = dobDate.get(Calendar.MONTH) + 1;
							int dobDay = dobDate.get(Calendar.DATE);

							if (todayYear - dobYear > 81) {
								errors.rejectValue("dobStr", "dummy", "Age of CARECash subscription must be less than 80");
							} else if (todayYear - dobYear == 81) {
								if (todayMonth > dobMonth) {
									errors.rejectValue("dobStr", "dummy", "Age of CARECash subscription must be less than 80");
								} else if (todayMonth - dobMonth == 0) {
									if (todayDay - dobDay >= 0) {
										errors.rejectValue("dobStr", "dummy", "Age of CARECash subscription must be less than 80");
									}
								}
							}
						}
					}
				}
			}
		}

		/** Validate Combine Account **/
		if ("current".equalsIgnoreCase(contact.getAcctType())) {

			String activeMobileNum = contact.getActiveMobileNum();
			String idDocType = contact.getIdDocType();
			String idDocNum = contact.getIdDocNum();
			String brand = contact.getBrand();

			com.bomwebportal.dto.AccountDTO accountDTO = customerProfileService.getAccountInfo(activeMobileNum, idDocType, idDocNum, brand);
			if (accountDTO == null || "".equals(accountDTO)) {
				errors.rejectValue("acctInfo", "acctInfo.required");
			}
		}

		/** Validate Combine Account **/

		// check the idDocType & idDocNum
		
			List<AccountDTO> accountDTO = customerProfileService.getBarredFraud(contact.getIdDocType(), contact.getIdDocNum());
			
			if (accountDTO != null) {
				errors.rejectValue("idDocNum", "dummy", "Barred by fraud Management: ");
				for (AccountDTO accdto: accountDTO){
					errors.rejectValue("idDocNum", "dummy", accdto.getFraudMsg());
				}
			}

	}

	private void checkValueinAscii(CustomerProfileDTO dto, String[] fields, Errors errors) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
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
