package com.bomwebportal.mob.validate.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.validate.dto.ErrorDTO;
import com.bomwebportal.mob.validate.dto.ResultDTO;
import com.bomwebportal.sbs.dto.CcsDeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.CcsDeliveryTimeSlotDTO;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.CCUnifiedInterfaceClient;
import com.bomwebportal.wsclient.exception.WsClientException;
import com.google.gson.Gson;
import com.tng.api.dto.ReturnValueDTO;
import com.tng.api.dto.VerifyStudentPlanCardResponse;
import com.tng.api.service.PaymentServiceGateway;
import java.text.ParseException;

import bom.mob.schema.javabean.si.xsd.CslNoStatusDTO;
import bom.mob.schema.javabean.si.xsd.GetCurrentDNODTO;

public class ValidateServiceImpl implements ValidateService {

	protected final Log logger = LogFactory.getLog(getClass());

	private MnpService mnpService;

	public MnpService getMnpService() {
		return mnpService;
	}

	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}

	private PaymentServiceGateway tngPsgService;

	public PaymentServiceGateway getTngPsgService() {
		return tngPsgService;
	}

	public void setTngPsgService(PaymentServiceGateway tngPsgService) {
		this.tngPsgService = tngPsgService;
	}

	private CCUnifiedInterfaceClient ccUnifiedInterfaceClient;

	public CCUnifiedInterfaceClient getCcUnifiedInterfaceClient() {
		return ccUnifiedInterfaceClient;
	}
	
	private DeliveryService deliveryService;

	public DeliveryService deliveryService() {
		return deliveryService;
	}

	public void setCcUnifiedInterfaceClient(CCUnifiedInterfaceClient ccUnifiedInterfaceClient) {
		this.ccUnifiedInterfaceClient = ccUnifiedInterfaceClient;
	}

	public ErrorDTO createErrorDTO(String field, String errCd, String errMsg) {
		ErrorDTO error = new ErrorDTO();
		error.setField(field);
		error.setErrorCd(errCd);
		error.setErrorMsg(errMsg);
		return error;
	}

	public void bindErrors(ResultDTO result, Errors errors) {
		if (result == null) {
			return;
		}

		if (result.hasError()) {
			for (int i = 0; i < result.getErrorList().size(); i++) {
				errors.rejectValue(result.getErrorList().get(i).getField(), result.getErrorList().get(i).getErrorCd(),
						result.getErrorList().get(i).getErrorMsg());
			}
		}
	}

	public ResultDTO validateBrandSimNumRelation(String brandType, String simType, String numType) {

		logger.debug("validateBrandSimNumRelation called");

		ResultDTO result = new ResultDTO();

		// 1. Check Empty
		if (StringUtils.isBlank(brandType)) {
			result.getErrorList().add(this.createErrorDTO("brand", "brand.required", "Please select Brand Type."));
		}

		if (StringUtils.isBlank(simType)) {
			result.getErrorList().add(this.createErrorDTO("simType", "simType.required", "Please select SIM Type."));
		}

		if (StringUtils.isBlank(numType)) {
			result.getErrorList().add(this.createErrorDTO("numType", "numType.required", "Please select Number Type."));
		}

		if (result.hasError()) {
			return result;
		}

		// 2. Check Relation
		if ("0".equals(brandType)) {
			if (!"C".equals(simType)) {
				result.getErrorList().add(this.createErrorDTO("simType", "", "Please select C-SIM for 1010 brand."));
			}
		}

		if (!StringUtils.equals(simType, numType)) {
			result.getErrorList().add(this.createErrorDTO("simType", "",
					"SIM Type (" + simType + ") and Number Type (" + numType + ") do not match."));
		}

		return result;

	}

	public ResultDTO validateSimTypeByIccid(String iccid, String selectedSimType, String simTypeErrorField) {
		logger.debug("validateSimTypeByIccid called");

		/*
		 * String simTypeErrorField = "simType"; if (isMember && memberIndex >=
		 * 0){ simTypeErrorField = "members["+memberIndex+"].simType"; }
		 */

		ResultDTO result = new ResultDTO();

		// 1. Check Empty
		if (StringUtils.isBlank(selectedSimType)) {
			result.getErrorList().add(this.createErrorDTO(simTypeErrorField, "", "Please select SIM Type."));
		}

		if (StringUtils.isBlank(iccid)) {
			result.getErrorList().add(this.createErrorDTO(simTypeErrorField, "", "Please input SIM Iccid."));
		}

		if (result.hasError()) {
			return result;
		}

		// 2. Validation
		String simType = Utility.checkSimType(iccid);
		if (StringUtils.isBlank(simType)) {
			result.getErrorList().add(this.createErrorDTO(simTypeErrorField, "invalid.simType",
					"Undefine SIM brand in lookup table (SIM_BRAND_PREFIX)."));
		} else if (!StringUtils.equals(selectedSimType, simType)) {
			result.getErrorList().add(this.createErrorDTO(simTypeErrorField, "",
					"Input SIM ICCID(Prefix) does not match with selected SIM Brand."));
		}

		return result;
	}

	public ResultDTO validateSimType(String simType, String selectedSimType, String simTypeErrorField) {
		logger.debug("validateSimType called");

		/*
		 * String simTypeErrorField = "simType"; if (isMember && memberIndex >=
		 * 0){ simTypeErrorField = "members["+memberIndex+"].simType"; }
		 */

		ResultDTO result = new ResultDTO();

		// 1. Check Empty
		if (StringUtils.isBlank(selectedSimType)) {
			result.getErrorList().add(this.createErrorDTO(simTypeErrorField, "", "Please select SIM Type."));
		}

		if (StringUtils.isBlank(simType)) {
			result.getErrorList().add(this.createErrorDTO(simTypeErrorField, "", "Invalid SIM Type "));
		}

		if (result.hasError()) {
			return result;
		}

		// 2. Validation
		if (!StringUtils.equals(selectedSimType, simType)) {
			result.getErrorList().add(this.createErrorDTO(simTypeErrorField, "",
					"SIM Type does not match with selected order SIM Type."));
		}

		return result;
	}

	public ResultDTO validateNumType(String numType, String selectedNumType, String numTypeErrorField, boolean sbPool) {
		logger.debug("validateNumType called");

		/*
		 * String numTypeErrorField = "numType"; if (isMember && memberIndex >=
		 * 0){ numTypeErrorField = "members["+memberIndex+"].numType"; }
		 */

		ResultDTO result = new ResultDTO();

		// 1. Check Empty
		if (StringUtils.isBlank(selectedNumType)) {
			result.getErrorList().add(this.createErrorDTO(numTypeErrorField, "", "Please select Number Type."));
		}

		if (StringUtils.isBlank(numType)) {
			result.getErrorList()
					.add(this.createErrorDTO(numTypeErrorField, "", "Invalid Number Type for input Number."));
		}

		if (result.hasError()) {
			return result;
		}

		// 2. validation
		String numTypeNotMatch = "Line Number is not in " + selectedNumType + "-System in CNM Inventory.";
		if (sbPool) {
			numTypeNotMatch = "Line Number is not in " + selectedNumType + "-System in SB MRT Pool.";
		}

		if (!StringUtils.equals(selectedNumType, numType)) {
			result.getErrorList().add(this.createErrorDTO(numTypeErrorField, "", numTypeNotMatch));
		}

		return result;
	}

	public ResultDTO validateMNP(MnpDTO mnpDTO, String mnpErrorField, boolean ignorePostpaidInd,
			String ignoreErrorField) {
		logger.debug("validateMNP called");

		ResultDTO result = new ResultDTO();
		boolean isMvnoCode = false;
		boolean isCslDno = false;

		String opssCases[]={"TGTG","T3TG","NPTG","M3M3","MPM3"};//dno rno
		String cDno[]= {"TG","T3","NP","WM"};
		String hDno[]={"M3","MP"};
		// 1. Check Empty
		if (mnpDTO == null) {
			result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", "Invalid MNP Input."));
		} else if (StringUtils.isBlank(mnpDTO.getMnpMsisdn())) {
			result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", "Please input MNP number."));
		}

		if (result.hasError()) {
			return result;
		}

		GetCurrentDNODTO currentDNODTO = null;
		try {
			currentDNODTO = mnpService.getCurrDNODTO(mnpDTO.getMnpMsisdn());
		} catch (AppRuntimeException ex) {
			result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", ex.getCustomMessage()));
			return result;
		} catch (Exception ex) {
			result.getErrorList()
					.add(this.createErrorDTO(mnpErrorField, "", "Exception API return Fail: " + ex.getMessage()));
			return result;
		}

		System.out.println(Utility.toPrettyJson(currentDNODTO));
		if (currentDNODTO == null) {
			result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", "Unexpected Error: API return null"));
		} else if (StringUtils.isBlank(currentDNODTO.getDno())) {
			result.getErrorList().add(
					this.createErrorDTO(mnpErrorField, "", "Unexpected Error: API return currentDNODTO.getDno() null"));
		} else if ("XX".equals(currentDNODTO.getDno()) && "XX".equals(currentDNODTO.getActDNO())) {
			result.getErrorList()
					.add(this.createErrorDTO(mnpErrorField, "", "Error: return XX on MRT :" + mnpDTO.getMnpMsisdn()));
		}

		if (result.hasError()) {
			return result;
		}

		String dno = currentDNODTO.getDno();

		// 2. Validation
		List<CodeLkupDTO> cslDnoList = LookupTableBean.getInstance().getCodeLkupList().get("CSL_DNO");

		if (CollectionUtils.isNotEmpty(cslDnoList)) {
			for (CodeLkupDTO codeLkup : cslDnoList) {
				if (dno.equals(codeLkup.getCodeId())) {
					isCslDno = true;
					break;
				}
			}
		}

		if (isCslDno) {
			MnpDTO checkCNM = null;

			try {
				checkCNM = mnpService.checkCentralNumPoolMsisdnMIP(mnpDTO.getMnpMsisdn());
			} catch (AppRuntimeException ex) {
				result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", ex.getCustomMessage()));
				return result;
			} catch (Exception ex) {
				result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
						"Exception checkCentralNumPoolMsisdnMIP() return Fail: " + ex.getMessage()));
				return result;
			}

			if (checkCNM != null && BomWebPortalConstant.CNM_STATUS_PORTOUT != checkCNM.getCnmStatus()) { // #
																											// exists
																											// in
																											// CNM
																											// &&
																											// cnmStatus
																											// <>
																											// 99

				if (mnpDTO.isPrepaidSimInd() && ArrayUtils.contains(cDno, currentDNODTO.getActDNO())) {
					result = validatePrePaidSim(mnpErrorField, mnpDTO.getMnpMsisdn(), mnpDTO.getCustIdDocNum(),
							currentDNODTO.getActDNO(), checkCNM.getShopCd(), checkCNM.getCnmStatus());
					mnpDTO.setDno(currentDNODTO.getDno());
					mnpDTO.setActualDno(currentDNODTO.getActDNO());
					return result;
				}

				else {// is not prepaid Sim and actual DNO is not TG

					String deptCode = checkCNM.getShopCd();
					int status = checkCNM.getCnmStatus();

					if (StringUtils.isBlank(deptCode)) {
						result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
								"Unexpected Error: API return MnpDTO.getShopCd() null"));
						return result;
					}

					List<CodeLkupDTO> mipMvnoBomShopCdList = LookupTableBean.getInstance().getCodeLkupList()
							.get("MIP_MVNO_BOM_SHOP_CD");
					String mvnoCd = null;

					if (CollectionUtils.isNotEmpty(mipMvnoBomShopCdList)) {
						for (CodeLkupDTO codeLkup : mipMvnoBomShopCdList) {
							if (deptCode.equals("P" + codeLkup.getCodeId())) {
								isMvnoCode = true;
								mvnoCd = codeLkup.getCodeDesc();
								break;
							}
						}
					}

					if (isMvnoCode) {
						if (BomWebPortalConstant.CNM_STATUS_SOLD != status) {
							result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
									"This is C/H MVNO non active number, cannot perform MNP."));
							return result;
						} else {
							mnpDTO.setDno(currentDNODTO.getDno());
							// mnpDTO.setActualDno(currentDNODTO.getActDNO());
							mnpDTO.setActualDno(mvnoCd);
						}
						// sold number, allow to MNP

					} else {
						result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
								"This number exists in CNM, cannot perform MNP."));
						return result;
					}
				}
			} else {

				//
				CslNoStatusDTO cslNoStatusDTO = null;

				try {
					cslNoStatusDTO = mnpService.checkNoStatusInCInv(mnpDTO.getMnpMsisdn());
					logger.debug("cslNoStatusDTO: " + Utility.toPrettyJson(cslNoStatusDTO));
					System.out.println("cslNoStatusDTO: " + Utility.toPrettyJson(cslNoStatusDTO));

				} catch (AppRuntimeException ex) {
					result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", ex.getCustomMessage()));
					return result;
				} catch (Exception ex) {
					result.getErrorList().add(
							this.createErrorDTO(mnpErrorField, "", "Exception API return Fail: " + ex.getMessage()));
					return result;
				}

				if (cslNoStatusDTO == null) {
					result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", "Invalid CSL No Status."));
					return result;
				} else if (StringUtils.isBlank(cslNoStatusDTO.getCheckStatus())) {
					result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", "Invalid CSL No Status."));
					return result;
				}

				if ("Y".equals(cslNoStatusDTO.getCheckStatus()) || "M".equals(cslNoStatusDTO.getCheckStatus())) {
					result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
							"This is a new number in system, cannot perform MNP."));
					return result;
				} else if ("N".equalsIgnoreCase(cslNoStatusDTO.getCheckStatus())) {
					if ("PREPAID".equals(cslNoStatusDTO.getChannel())) {

						boolean mipEnablePrepaidPortIn = false;
						List<CodeLkupDTO> mipEnablePrepaidPortInList = LookupTableBean.getInstance().getCodeLkupList()
								.get("MIP_ENABLE_PREPAID_PORT_IN");
						if (CollectionUtils.isNotEmpty(mipEnablePrepaidPortInList)) {
							for (CodeLkupDTO codeLkup : mipEnablePrepaidPortInList) {
								if ("Y".equals(codeLkup.getCodeId())) {
									mipEnablePrepaidPortIn = true;
									break;
								}
							}
						}

						if (mipEnablePrepaidPortIn) {
							mnpDTO.setDno(currentDNODTO.getDno());
							mnpDTO.setActualDno(currentDNODTO.getActDNO());
						} else {
							result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
									"Prepaid port-in not supported in SB, please perform it in BOM."));
							return result;
						}

					} else if ("POSTPAID".equalsIgnoreCase(cslNoStatusDTO.getChannel())) {
						if (!ignorePostpaidInd) {
							result.getErrorList().add(this.createErrorDTO(ignoreErrorField, "",
									"C-system MRT found, ensure to port-in to H-systems?"));
							return result;
						} else {
							mnpDTO.setDno(currentDNODTO.getDno());
							mnpDTO.setActualDno(currentDNODTO.getActDNO());
							mnpDTO.setIgnorePostpaidcheckbox(true);
						}
					} else if ("MVNO".equalsIgnoreCase(cslNoStatusDTO.getChannel())) {
						mnpDTO.setDno(currentDNODTO.getDno());
						// mnpDTO.setActualDno(currentDNODTO.getActDNO());
						mnpDTO.setActualDno(cslNoStatusDTO.getMvnoCode()); // 20160719
					} else {
						result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", "Invalid Channel."));
						return result;
					}
				} else {
					result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", "Invalid CSL No Status."));
					return result;
				}
			}

		} else {

			if ("M3".equals(dno) || "MP".equals(dno)) {

				// is prePaidSim and actual DNO is M3
				if (mnpDTO.isPrepaidSimInd() && ArrayUtils.contains(hDno, currentDNODTO.getActDNO())) {
					MnpDTO checkCNM = null;
					try {
						checkCNM = mnpService.checkCentralNumPoolMsisdnMIP(mnpDTO.getMnpMsisdn());
					} catch (AppRuntimeException ex) {
						result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", ex.getCustomMessage()));
						return result;
					} catch (Exception ex) {
						result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
								"Exception checkCentralNumPoolMsisdnMIP() return Fail: " + ex.getMessage()));
						return result;
					}
					if (checkCNM != null) {
						result = validatePrePaidSim(mnpErrorField, mnpDTO.getMnpMsisdn(), mnpDTO.getCustIdDocNum(),
								currentDNODTO.getActDNO(), checkCNM.getShopCd(), checkCNM.getCnmStatus());
						mnpDTO.setDno(currentDNODTO.getDno());
						mnpDTO.setActualDno(currentDNODTO.getActDNO());
						return result;
					} else {
						result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
								"Fail to get MRT:" + mnpDTO.getMnpMsisdn() + "checkCentralNumPoolMsisdnMIP()"));
						return result;
					}
				}
				else{
				// check actual DNO
					if (StringUtils.isBlank(currentDNODTO.getActDNO()) || dno.equals(currentDNODTO.getActDNO())) {
						result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
								"This is a new number in system, cannot perform MNP."));
						return result;
					} else {
						mnpDTO.setDno(currentDNODTO.getDno());
						mnpDTO.setActualDno(currentDNODTO.getActDNO());
					}
				}
			} else {

				mnpDTO.setDno(currentDNODTO.getDno());
			}

		}

		System.out.println("isCslDno:" + isCslDno);
		System.out.println("isMvnoCode:" + isMvnoCode);
		System.out.println("dno:" + mnpDTO.getDno());
		System.out.println("dno:" + mnpDTO.getActualDno());

		return result;
	}

	public ResultDTO validatePrePaidSim(String mnpErrorField, String msisdn, String iccid, String actDno,
			String shopCode, int cnmStatus) {
		ResultDTO result = new ResultDTO();
		Map<String, String> getCardInformationResult = new HashMap<String, String>();
		if ("M3".equals(actDno)) {
			if (cnmStatus == 00 || cnmStatus == 99) {
				result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
						"Error: This is new number in system, cannot perform MNP"));
				return result;
			}
		}

		if (! ("P3GPP".equals(shopCode) || "PPSUN".equals(shopCode))) { //for PPSUN 
			result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
					"Error: MRT " + msisdn + " is not Prepaid SIM, cannot perform MNP"));
			return result;
		}
		if (BomWebPortalConstant.CNM_STATUS_SOLD != cnmStatus) {
			result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
					"Error: MRT " + msisdn + " is a new number in System(Prepaid), cannot perform MNP"));
			return result;
		}
		try {
			getCardInformationResult = ccUnifiedInterfaceClient.getCardInformation(msisdn);
		} catch (WsClientException e) {
			result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
					"Exception in getCardInformation() return Fail: " + e.getMessage()));
			return result;
		}
		if (getCardInformationResult == null) {
			result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
					"Unexpected Error MRT: " + msisdn + " return empty on getCardInformation. "));
			return result;
		}

		if (StringUtils.isEmpty(iccid)) {
			result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", "Error: Prepaid SIM No.is empty. "));
			return result;
		}

		if ("H".equalsIgnoreCase(getCardInformationResult.get("SystemId"))) {

			if (!iccid.equals(getCardInformationResult.get("ICCID").substring(0, 18))) {
				result.getErrorList()
						.add(this.createErrorDTO(mnpErrorField, "", "Error: one PSS (H) Prepaid SIM ICCID invalid."));
				return result;
			}
			if ("02".equalsIgnoreCase(getCardInformationResult.get("CardStatusCode"))
					|| "04".equalsIgnoreCase(getCardInformationResult.get("CardStatusCode"))
					|| "05".equalsIgnoreCase(getCardInformationResult.get("CardStatusCode"))) {
				result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
						"Error: Prepaid SIM No. " + iccid + " card status does not match"));
				return result;
			}

			if ("20".equalsIgnoreCase(getCardInformationResult.get("ManangeStatusCode"))) {
				result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
						"Error: Prepaid SIM No. " + iccid + " Manange Status code does not match"));
				return result;
			}
		} else if ("C".equalsIgnoreCase(getCardInformationResult.get("SystemId"))) {

			if (!iccid.equals(getCardInformationResult.get("ICCID"))) {
				result.getErrorList()
						.add(this.createErrorDTO(mnpErrorField, "", "Error: one PSS (C) Prepaid SIM ICCID invalid."));
				return result;
			}
			if (!"A".equalsIgnoreCase(getCardInformationResult.get("CardStatusCode"))) {
				result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
						"Error: Prepaid SIM No. " + iccid + " card status does not match"));
				return result;
			}

			if (!"A".equalsIgnoreCase(getCardInformationResult.get("ManangeStatusCode"))) {
				result.getErrorList().add(this.createErrorDTO(mnpErrorField, "",
						"Error: Prepaid SIM No. " + iccid + " Manange Status code does not match"));
				return result;
			}
		} else {
			result.getErrorList().add(this.createErrorDTO(mnpErrorField, "", "Error: Unknown System ID."));
			return result;
		}
		result.setStartPackNumber(getCardInformationResult.get("SerialNumber"));
		result.setOnePssInd("Y");
		return result;
	}

	public ResultDTO validateStudentPlanDob(String appDateStr, String dobStr, String errorField) {
		logger.debug("validateStudentPlanDob called");

		ResultDTO result = new ResultDTO();

		// 1. Check Empty
		if (StringUtils.isBlank(dobStr)) {
			result.getErrorList().add(this.createErrorDTO(errorField, "", "Date of birth is empty."));
		}

		if (result.hasError()) {
			return result;
		}

		// 2. Validation
		// Check if the applicant is 11 - 18
		Calendar appDate = Calendar.getInstance();
		appDate.setTime(Utility.string2Date(appDateStr));

		/*
		 * Calendar appDate = Calendar.getInstance();
		 * appDate.set(Calendar.HOUR_OF_DAY, 0); appDate.set(Calendar.MINUTE,
		 * 0); appDate.set(Calendar.SECOND, 0);
		 * appDate.set(Calendar.MILLISECOND, 0);
		 */

		Calendar compareDate = Calendar.getInstance();

		compareDate.setTime(Utility.string2Date(dobStr));
		compareDate.add(Calendar.YEAR, 11);

		System.out.println("appDate: " + appDate.getTime());
		System.out.println("compareDate: " + compareDate.getTime());

		logger.info("appDate: " + appDate.getTime());
		logger.info("compareDate: " + compareDate.getTime());

		if (compareDate.after(appDate)) {
			result.getErrorList().add(this.createErrorDTO(errorField, "dobDay.studentPlanOver11Required",
					"The application is only appropriate to over 11."));
		} else {
			// Check the year range: (11 + 7)= 18 years
			compareDate.add(Calendar.YEAR, 7);

			logger.info("compareDate: " + compareDate.getTime());
			if (compareDate.before(appDate) || compareDate.equals(appDate)) {
				result.getErrorList().add(this.createErrorDTO(errorField, "dobDay.studentPlanBelow18Required",
						"The application is only appropriate to below 18."));
			}
		}

		return result;
	}

	public ResultDTO validateStudentPlanIdDocType(String idDocType, String errorField) {
		logger.debug("validateStudentPlanIdDocType called");

		ResultDTO result = new ResultDTO();

		// 1. Check Empty
		if (StringUtils.isBlank(idDocType)) {
			result.getErrorList().add(this.createErrorDTO(errorField, "", "ID Doc Type is empty."));
		}

		if (result.hasError()) {
			return result;
		}

		if ("BS".equals(idDocType)) {
			result.getErrorList()
					.add(this.createErrorDTO(errorField, "", "Student Plan is not allowed for HKBR customer"));
		}

		return result;
	}

	public ResultDTO validateStudentPlanAddressProof(String addressProofInd, String errorField) {
		logger.debug("validateStudentPlanAddressProof called");

		ResultDTO result = new ResultDTO();

		// 1. Check Empty
		if (StringUtils.isBlank(addressProofInd)) {
			result.getErrorList().add(this.createErrorDTO(errorField, "", "Address Proof is empty."));
		}

		if (result.hasError()) {
			return result;
		}

		if (!"0".equals(addressProofInd)) {
			result.getErrorList().add(this.createErrorDTO(errorField, "",
					"No Address Proof must be selected for Student Plan customer."));
		}

		return result;
	}

	public ResultDTO validateStudentPlanBasketCustomerTier(boolean studentPlanSubInd, String customerTierId,
			String errorField) {
		logger.debug("validateStudentPlanBasketCustomerTier called");

		ResultDTO result = new ResultDTO();

		// 1. Check Empty
		if (StringUtils.isBlank(customerTierId)) {
			result.getErrorList().add(this.createErrorDTO(errorField, "", "Customer Tier is empty."));
		}

		if (studentPlanSubInd) {
			if (!"90".equals(customerTierId)) {
				result.getErrorList()
						.add(this.createErrorDTO(errorField, "", "Basket is not allowed for Student Plan Sub."));
			}
		} else {
			if ("90".equals(customerTierId)) {
				result.getErrorList()
						.add(this.createErrorDTO(errorField, "", "Basket is not allowed for Non-Student Plan Sub."));
			}
		}

		return result;
	}

	public ResultDTO validateStudentPlanMonthlyPayment(String paymentMethod, String errorField, boolean ccsMode) {
		logger.debug("validateStudentPlanMonthlyPayment called");

		ResultDTO result = new ResultDTO();

		// 1. Check Empty
		if (StringUtils.isBlank(paymentMethod)) {
			result.getErrorList().add(this.createErrorDTO(errorField, "", "Payment Method is empty."));
		}

		if (result.hasError()) {
			return result;
		}

		// 2. validation
		if (ccsMode) {
			if (!"C".equals(paymentMethod) && !"M".equals(paymentMethod)) {
				result.getErrorList().add(this.createErrorDTO(errorField, "",
						"Monthly payment method must be Credit Card / Cash for Student Plan basket."));
			}
		} else {
			if (!"C".equals(paymentMethod)) {
				result.getErrorList().add(this.createErrorDTO(errorField, "",
						"Monthly payment method must be Credit Card for Student Plan basket."));
			}
		}

		return result;
	}

	public ResultDTO validateStudentPlanCreditCardInfo(String customerName, String customerIdDocType,
			String customerIdDocNum, String thirdPartyInd, String ccCustName, String ccIdDocType, String ccIdDocNum,
			String thirdPartyErrorField, String custNameErrorField, String idDocTypeErrorField,
			String idDocNumErrorField) {
		logger.debug("validateStudentPlanCreditCardInfo called");

		ResultDTO result = new ResultDTO();

		System.out.println(thirdPartyInd);

		if (!"N".equals(thirdPartyInd)) {
			result.getErrorList().add(this.createErrorDTO(thirdPartyErrorField, "",
					"Third Party is not allowed for Student Plan basket."));
		}

		if ("BS".equals(customerIdDocType)) {
			result.getErrorList()
					.add(this.createErrorDTO(idDocTypeErrorField, "", "Student Plan is not allowed for HKBR customer"));
		}

		if (result.hasError()) {
			return result;
		}

		// 1. Check Empty
		if (StringUtils.isBlank(customerName)) {
			result.getErrorList().add(this.createErrorDTO(custNameErrorField, "", "Customer Name is empty."));
		}

		if (StringUtils.isBlank(customerIdDocType)) {
			result.getErrorList().add(this.createErrorDTO(idDocTypeErrorField, "", "Customer ID Doc Type is empty."));
		}

		if (StringUtils.isBlank(customerIdDocNum)) {
			result.getErrorList().add(this.createErrorDTO(idDocNumErrorField, "", "Customer ID Doc Number is empty."));
		}

		if (StringUtils.isBlank(ccCustName)) {
			result.getErrorList()
					.add(this.createErrorDTO(custNameErrorField, "", "Credit Card Customer Name is empty."));
		}

		if (StringUtils.isBlank(ccIdDocType)) {
			result.getErrorList()
					.add(this.createErrorDTO(idDocTypeErrorField, "", "Credit Card Customer ID Doc Type is empty."));
		}

		if (StringUtils.isBlank(ccIdDocNum)) {
			result.getErrorList()
					.add(this.createErrorDTO(idDocNumErrorField, "", "Credit Card Customer ID Doc Number is empty."));
		}

		if (result.hasError()) {
			return result;
		}

		// 2. validation
		if (!StringUtils.equals(customerName, ccCustName)) {
			result.getErrorList().add(this.createErrorDTO(custNameErrorField, "",
					"Customer Name and Credit Card Customer Name must be equal for Student Plan basket."));
		}

		if (!StringUtils.equals(customerIdDocType, ccIdDocType)) {
			result.getErrorList().add(this.createErrorDTO(idDocTypeErrorField, "",
					"Customer ID Doc Type and Credit Card Customer ID Doc Type must be equal for Student Plan basket."));
		}

		if (!StringUtils.equals(customerIdDocNum, ccIdDocNum)) {
			result.getErrorList().add(this.createErrorDTO(idDocNumErrorField, "",
					"Customer ID Doc Number and Credit Card Customer ID Doc Number must be equal for Student Plan basket."));
		}

		return result;
	}

	public ResultDTO verifyStudentPlanCard(String idDocNum, String idDocType, String fullPan, String errorField) {
		logger.debug("verifyStudentPlanCard called");

		System.out.println(idDocNum);
		System.out.println(idDocType);
		System.out.println(fullPan);
		logger.debug("verifyStudentPlanCard called" + idDocNum);
		logger.debug("verifyStudentPlanCard called" + idDocType);
		logger.debug("verifyStudentPlanCard called" + fullPan);

		ResultDTO result = new ResultDTO();

		// 1. Check Empty
		if (StringUtils.isBlank(idDocNum)) {
			result.getErrorList()
					.add(this.createErrorDTO(errorField, "", "Invalid Input: ID Documber Number is empty."));
		}

		if (StringUtils.isBlank(idDocType)) {
			result.getErrorList().add(this.createErrorDTO(errorField, "", "Invalid Input: ID Documber Type is empty."));
		}

		if (StringUtils.isBlank(fullPan)) {
			result.getErrorList().add(this.createErrorDTO(errorField, "", "Invalid Input: Card Pan is empty."));
		} else if (fullPan.length() != 16) {
			result.getErrorList()
					.add(this.createErrorDTO(errorField, "", "Invalid Input: Invalid Card Number Length."));
		}

		if (result.hasError()) {
			return result;
		}

		// 2. validation
		String maskedPan = fullPan.substring(0, 6) + "******" + fullPan.substring(12, 16);
		System.out.println(maskedPan);
		logger.debug("verifyStudentPlanCard called" + maskedPan);

		VerifyStudentPlanCardResponse response = null;
		try {
			response = tngPsgService.verifyStudentPlanCard(idDocNum, idDocType, maskedPan,
					PaymentServiceGateway.OPER_INFO);
			System.out.println("response: " + new Gson().toJson(response));
			ReturnValueDTO resultDTO = response.getReturnValueDTO();

			if (resultDTO == null) {
				result.getErrorList().add(
						this.createErrorDTO(errorField, "", "VerifyStudentPlanCard API return error: " + resultDTO));
			} else if (!"000".equals(resultDTO.getReturnCode())) {
				result.getErrorList().add(this.createErrorDTO(errorField, "", "VerifyStudentPlanCard API return error: "
						+ resultDTO.getErrMsg() + "(" + resultDTO.getReturnCode() + ")"));
			} else {
				// valid
			}

		} catch (Exception ex) {
			result.getErrorList().add(this.createErrorDTO(errorField, "",
					"VerifyStudentPlanCard API return Unexpected Exception: " + ex.getMessage()));
		}

		return result;
	}
	
}
