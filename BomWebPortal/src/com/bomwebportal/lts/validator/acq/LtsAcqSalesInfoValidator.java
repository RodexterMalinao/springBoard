package com.bomwebportal.lts.validator.acq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSalesInfoFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsSalesInfoService;

public class LtsAcqSalesInfoValidator implements Validator{

	private LtsSalesInfoService ltsSalesInfoService;
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private LtsCommonService ltsCommonService;
	
    public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public boolean supports(Class clazz) {
		return LtsAcqSalesInfoFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsAcqSalesInfoFormDTO salesInfo = (LtsAcqSalesInfoFormDTO) command;
		LtsSalesInfoFormDTO retrievedSalesInfo;
		
		//List<LtsSalesInfoFormDTO> retrievedSalesInfoList;
		
			if (StringUtils.isBlank(salesInfo.getStaffId())) {
				errors.rejectValue("staffId", "lts.staffId.required");
			}else{
				salesInfo.setStaffId(StringUtils.upperCase(salesInfo.getStaffId()));
				retrievedSalesInfo = ltsSalesInfoService.getSalesInfo(salesInfo.getStaffId());
				if(retrievedSalesInfo == null){
					errors.rejectValue("staffId", "lts.invalid.staffId");
				} 
			}

			
			if(salesInfo.isCallCenter() || salesInfo.isPremier()){
				if (StringUtils.isBlank(salesInfo.getImsApplicationMethod())) {
					errors.rejectValue("salesChannel", "lts.salesChannel.required");
				}

				if (StringUtils.isBlank(salesInfo.getImsSource())) {
					errors.rejectValue("salesTeam", "lts.shopCode.required");
				}
				
				
			} else {
				if (StringUtils.isBlank(salesInfo.getSalesCode())) {
					errors.rejectValue("salesCode", "lts.salesCode.required");
				}
				
				if (StringUtils.isBlank(salesInfo.getSalesChannel())) {
					errors.rejectValue("salesChannel", "lts.salesChannel.required");
				}

				if (StringUtils.isBlank(salesInfo.getSalesTeam())) {
					errors.rejectValue("salesTeam", "lts.shopCode.required");
				}
			}
			
			
			
//			if(salesInfo.isCallCenter()){
//				/*if (StringUtils.isBlank(salesInfo.getDate())){
//					errors.rejectValue("date", "callDateStr.required");
//				}
//				if (StringUtils.isBlank(salesInfo.getTime())){
//					errors.rejectValue("time", "callTimeStr.required");
//				}
//				
//				if (StringUtils.isBlank(salesInfo.getPosition())) {
//					errors.rejectValue("position", "lts.position.required");
//				}
//				
//				if (StringUtils.isBlank(salesInfo.getJobName())) {
//					errors.rejectValue("jobName", "lts.jobName.required");
//				}*/
//			}

			
			if (StringUtils.isBlank(salesInfo.getSalesContact())) {
				errors.rejectValue("salesContact", "lts.contactPhone.required");
//			}else{
//				if (!Utility.validatePhoneNum(salesInfo.getSalesContact())) {
//					errors.rejectValue("salesContact", "lts.invalid.contactPhone");
//				}
			}else{
				if(salesInfo.isVaildateMobileNo() && !ltsCommonService.isMobileNumPrefix(salesInfo.getSalesContact())){
					errors.rejectValue("salesContact", "lts.invalid.contactPhone");
				}
			}
			

			if (StringUtils.isBlank(salesInfo.getDate())){
//				errors.rejectValue("date", "lts.invalid.salesdate");
			}else{
				if(salesInfo.getDate().length() != 10){
					errors.rejectValue("date", "lts.invalid.salesdate");
				}else{
					String[] date = salesInfo.getDate().split("/");
					if(date == null || date.length != 3 || date[0].length() != 2 || date[1].length() != 2 || date[2].length() != 4){
						errors.rejectValue("date", "lts.invalid.salesdate");
					}
				}
			}
					
			if (StringUtils.isBlank(salesInfo.getTime())){
//				errors.rejectValue("time", "lts.invalid.salestime");
			}else{
				if(salesInfo.getTime().length() != 5){
					errors.rejectValue("time", "lts.invalid.salestime");
				}else{
					String[] time = salesInfo.getTime().split(":");
					if(time == null || time.length != 2 || time[0].length() != 2 || time[1].length() != 2){
						errors.rejectValue("time", "lts.invalid.salestime");
					}else{
						try{
							if(Integer.parseInt(time[0])  > 23
								|| Integer.parseInt(time[1])  > 59){
								errors.rejectValue("time", "lts.verification.required");
							}
						}catch(NumberFormatException nfe){
							errors.rejectValue("time", "lts.invalid.salestime");
						}
					}
				}
			}
			
			if(StringUtils.isNotBlank(salesInfo.getDate())
					&& StringUtils.isNotBlank(salesInfo.getTime())
					&& errors.getFieldErrorCount("date") == 0 
					&& errors.getFieldErrorCount("time") == 0 ){
				try{
					Date salesDate = df.parse(salesInfo.getDate()+" "+salesInfo.getTime());
					Date now = new Date();
					if(!salesDate.before(now)){
						errors.rejectValue("date", "lts.invalid.salesdate2");
						errors.rejectValue("time", "lts.invalid.salesdate2");
					}
//					salesInfo.setDate(df.format(df.parse(salesInfo.getDate())));
				}catch(ParseException pe){
					errors.rejectValue("date", "lts.invalid.salesdate");
					errors.rejectValue("time", "lts.invalid.salestime");
				}
			}

			
	}

}