package com.bomwebportal.mob.validate.service;

import org.springframework.validation.Errors;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.validate.dto.ErrorDTO;
import com.bomwebportal.mob.validate.dto.ResultDTO;

public interface ValidateService {
	public ResultDTO validateBrandSimNumRelation(String brandType, String simType, String numType) ;
	public ResultDTO validateSimTypeByIccid(String iccid, String selectedSimType, String simTypeErrorField) ;
	public ResultDTO validateSimType(String simType, String selectedSimType, String simTypeErrorField) ;
	public ResultDTO validateNumType(String numType, String selectedNumType, String numTypeErrorField, boolean sbPool);
	public ResultDTO validateMNP(MnpDTO mnpDTO, String errorField, boolean ignorePostpaidInd, String ignoreErrorField);
	
	public ResultDTO validateStudentPlanDob(String appDateStr, String dobStr,  String errorField);
	public ResultDTO validateStudentPlanIdDocType(String idDocType , String errorField);
	public ResultDTO validateStudentPlanAddressProof(String addressProof , String errorField);
	public ResultDTO validateStudentPlanBasketCustomerTier(boolean studentPlanSubInd, String customerTierId , String errorField);
	public ResultDTO validateStudentPlanMonthlyPayment(String paymentMethod , String errorField, boolean ccsMode);
	public ResultDTO validateStudentPlanCreditCardInfo(String customerName, String customerIdDocType, String customerIdDocNum ,String thirdPartyInd, String ccCustName, String ccIdDocType, String ccIdDocNum , String thirdPartyErrorField, String custNameErrorField, String idDocTypeErrorField, String idDocNumErrorField);
	public ResultDTO verifyStudentPlanCard(String idDocNum, String idDocType,String fullPan, String errorField);

	public ErrorDTO createErrorDTO(String field,String errCd, String errMsg);
	public void bindErrors(ResultDTO result, Errors errors);

}
