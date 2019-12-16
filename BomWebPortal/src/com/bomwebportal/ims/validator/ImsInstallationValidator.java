package com.bomwebportal.ims.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.ClubHktCheckIdDTO;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.util.ImsUtil;
import com.bomwebportal.util.Utility;

import com.bomwebportal.service.ClubHktService;
import com.bomwebportal.service.NowIdService;
import com.bomwebportal.service.NowIdServiceImpl.NowIdVerfResult;
import com.google.gson.Gson;


public class ImsInstallationValidator implements Validator{
	
	private ClubHktService clubHktService;
	
	private NowIdService nowIdService;
	
	protected final Log logger = LogFactory.getLog(getClass());

	private final Gson gson = new Gson();

	
	public boolean supports(Class clazz) {
		return clazz.isAssignableFrom(ImsInstallationUI.class);
	}
	
	/**
	 * main validationr logic
	 */
	public void validate(Object obj, Errors errors){
		logger.info("validate is called");
		
		ImsInstallationUI cust = (ImsInstallationUI)obj;
		//check rejectIfEmptyOrWhitespace

		logger.info("getLoginId():" + cust.getLoginId());
		if(cust.getLoginId()!=null && Utility.imsIsContainCreditCardPattern(cust.getLoginId())){
			errors.rejectValue("loginId", "ims.pcd.imsinstallation.msg.057");		
		}
		
		ValidationUtils.rejectIfEmpty(errors, "idVerified", "ims.pcd.imsinstallation.msg.037");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idDocType", "ims.pcd.imsinstallation.msg.038");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idDocNum", "ims.pcd.imsinstallation.msg.039");
	
		if ( !"BS".equals(cust.getIdDocType()) )
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "ims.pcd.imsinstallation.msg.040");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "ims.pcd.imsinstallation.msg.041");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "ims.pcd.imsinstallation.msg.042");
			//if(cust.getCustNo() == null || ("").equals(cust.getCustNo())){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dobStr", "ims.pcd.imsinstallation.msg.043");
			if(cust.getFirstName()!=null && !"".equals(cust.getFirstName()) && !Utility.isEng(cust.getFirstName()) && !cust.getIsFromBOM().equalsIgnoreCase("Y")){
				errors.rejectValue( "firstName", "ims.pcd.imsinstallation.msg.042");
			}
			if(cust.getLastName()!=null && !"".equals(cust.getLastName()) && !Utility.isEng(cust.getLastName()) && !cust.getIsFromBOM().equalsIgnoreCase("Y")){
				errors.rejectValue( "lastName", "ims.pcd.imsinstallation.msg.041");
			}
			//}
		}
		else
		{
			ValidationUtils.rejectIfEmpty(errors, "companyName", "ims.pcd.imsinstallation.msg.044");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPersonName", "ims.pcd.imsinstallation.msg.045");
			if (!StringUtils.isEmpty(cust.getCompanyName()) && !Utility.isEng(cust.getCompanyName())&& !"Y".equals(cust.getIsFromBOM())) 
				errors.rejectValue( "companyName","ims.pcd.imsinstallation.msg.046");
			if (!StringUtils.isEmpty(cust.getContactPersonName()) && !Utility.isEng(cust.getContactPersonName())&& !"Y".equals(cust.getIsFromBOM())) 
				errors.rejectValue( "contactPersonName","ims.pcd.imsinstallation.msg.047");
		}
		if((cust.getContact().getContactPhone() == null || ("").equals(cust.getContact().getContactPhone()))){
			errors.rejectValue("contact.contactPhone", "ims.pcd.imsinstallation.msg.069");
		}
		if((cust.getContact().getEmailAddr() == null || ("").equals(cust.getContact().getEmailAddr()))){
			errors.rejectValue("contact.emailAddr", "ims.pcd.imsinstallation.msg.068");
		}
		if(cust.getContact().getEmailAddr()!=null && !("").equals(cust.getContact().getEmailAddr()) && Utility.imsIsContainCreditCardPattern(cust.getContact().getEmailAddr())){
			errors.rejectValue("contact.emailAddr", "ims.pcd.summary.msg.013");		
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId", "ims.pcd.imsinstallation.msg.049");
		if(cust.getNoBillingAddress().equals("N")){// && (cust.getBillingAddress().getFloorNo() == null || !("").equals(cust.getBillingAddress().getFloorNo()))){
			logger.info(cust.getNoBillingAddress());
			logger.info(cust.getBillingAddress().getFloorNo());
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingAddress.floorNo", "ims.pcd.imsinstallation.msg.050");
			if ( cust.getBillingQuickSearch() == null || cust.getBillingQuickSearch().trim().isEmpty())
			{
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingAddress.distDesc", "ims.pcd.imsinstallation.msg.051");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingAddress.areaCd", "ims.pcd.imsinstallation.msg.052");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingAddress.lotNoStreetNo", "ims.pcd.imsinstallation.msg.053");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingAddress.streetCatStreetName", "ims.pcd.imsinstallation.msg.054");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingAddress.streetCatStreetNameStreetNo", "ims.pcd.imsinstallation.msg.055");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingAddress.buildingNameStreetNoLotNo", "ims.pcd.imsinstallation.msg.056");

			}
			//errors.rejectValue("billingAddress.floorNo", "floorNoOrBillFlag.requied");
		}
		/*
		if((cust.getBillingAddress().getFloorNo() == null || !("").equals(cust.getBillingAddress().getFloorNo()))){
			logger.info(cust.getBillingAddress().getFloorNo());
			errors.rejectValue("noBillingAddress", "noBillingAddress.error");
		}
		*/
		logger.info("getIsRegHKTLoginId"+cust.getIsRegHKTLoginId());
		
		String hktId = null;
		String clubId = null;
		String[] result = new String[5];
		
		result[2] = "success";
		result[3] = "success";
		
		ClubHktCheckIdDTO checkDto1 = new ClubHktCheckIdDTO();
		ClubHktCheckIdDTO checkDto2 = new ClubHktCheckIdDTO();
		ClubHktCheckIdDTO checkDto3 = new ClubHktCheckIdDTO();
		
		
		if(cust.getIsRegHKTLoginId()!=null && cust.getIsRegHKTLoginId().equalsIgnoreCase("Y") && cust.getIsRegClubLoginId()!=null && cust.getIsRegClubLoginId().equalsIgnoreCase("Y")){
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hktMobileNum", "ims.pcd.imsinstallation.msg.002");
			
			if(!cust.getNoEmailInd().equalsIgnoreCase("Y")){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hktClubLoginId", "ims.pcd.imsinstallation.msg.004");
				hktId = cust.getHktClubLoginId();
				clubId = cust.getHktClubLoginId();
				checkDto1 = clubHktService.checkId(cust.getIdDocType(), cust.getIdDocNum(), hktId, clubId, null, null);
				result = ImsUtil.convertAPIReturnResult(checkDto1);
				logger.info(gson.toJson(result));
				if(result[2]!="success"||result[3]!="success"){
					errors.rejectValue("hktClubLoginId", "ims.pcd.imsinstallation.msg.021");
				}
			}
		}else if(cust.getIsRegHKTLoginId()!=null && cust.getIsRegHKTLoginId().equalsIgnoreCase("Y")){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hktLoginId", "ims.pcd.imsinstallation.msg.011");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hktMobileNum", "ims.pcd.imsinstallation.msg.002");
			hktId = cust.getHktLoginId();
			clubId = null;
			checkDto2 = clubHktService.checkId(cust.getIdDocType(), cust.getIdDocNum(), hktId, clubId, null, null);
			result = ImsUtil.convertAPIReturnResult(checkDto2);
			logger.info(gson.toJson(result));
			if(result[2]!="success"){
				errors.rejectValue("hktLoginId", "ims.pcd.imsinstallation.msg.021");
			}
		}else if(cust.getIsRegClubLoginId()!=null && cust.getIsRegClubLoginId().equalsIgnoreCase("Y")){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hktMobileNum", "ims.pcd.imsinstallation.msg.002");
			if(!cust.getNoEmailInd().equalsIgnoreCase("Y")){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clubLoginId", "ims.pcd.imsinstallation.msg.014");
				hktId = null;
				clubId = cust.getClubLoginId();
				checkDto3 = clubHktService.checkId(cust.getIdDocType(), cust.getIdDocNum(), hktId, clubId, null, null);
				result = ImsUtil.convertAPIReturnResult(checkDto3);
				logger.info(gson.toJson(result));
				if(result[3]!="success"){
					errors.rejectValue("clubLoginId", "ims.pcd.imsinstallation.msg.021");
				}
			}
		}
		
		if(cust.getIsRegNowId().equalsIgnoreCase("Y") && cust.getNowId()!=null){
			logger.info("nowId validation: "+cust.getNowId());
			
			
			NowIdVerfResult nowIdVerfResult = nowIdService.verifyNowIdStatusByNowId(cust.getNowId(), null, "ACQ_NOWID", null);
	
			logger.info("nowIdVerfResult.nowIdInd: "+nowIdVerfResult.nowIdInd);
			logger.info("nowIdVerfResult.errorMessage: "+nowIdVerfResult.errorMessage);
			if(nowIdVerfResult.nowIdInd != null && "X".equalsIgnoreCase(nowIdVerfResult.nowIdInd)){
				errors.rejectValue("nowId", "", nowIdVerfResult.errorMessage);
			}
		}
		
		
		String ClubMemberId = clubHktService.getClubMemberIdbyDoc(cust.getIdDocType(),cust.getIdDocNum());
		 
   logger.info("celia 1025 " +ClubMemberId);
	}

	public void setClubHktService(ClubHktService clubHktService) {
		this.clubHktService = clubHktService;
	}

	public ClubHktService getClubHktService() {
		return clubHktService;
	}

	public void setNowIdService(NowIdService nowIdService) {
		this.nowIdService = nowIdService;
	}

	public NowIdService getNowIdService() {
		return nowIdService;
	}
}
