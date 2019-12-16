package com.bomwebportal.mob.ccs.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.MrtReserveDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsSpecialMrtRequestService;
import com.bomwebportal.mob.ccs.service.MrtInventoryService;
import com.bomwebportal.mob.ccs.service.SpecialMRTReserveService;
import com.bomwebportal.util.Utility;

public class SpecialMrtRequestValidator implements Validator{
	
	public boolean supports(Class clazz) {
		return clazz.equals(SpecialMrtRequestDTO.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	private MobCcsMrtService mobCcsMrtService;
	private MrtInventoryService mrtInventoryService;
	private MobCcsSpecialMrtRequestService mobCcsSpecialMrtRequestService;
	private CodeLkupService codeLkupService;
	private SpecialMRTReserveService specialMRTReserveService;
	
	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}
	
	public MrtInventoryService getMrtInventoryService() {
		return mrtInventoryService;
	}


	public void setMrtInventoryService(MrtInventoryService mrtInventoryService) {
		this.mrtInventoryService = mrtInventoryService;
	}
	

	public MobCcsSpecialMrtRequestService getMobCcsSpecialMrtRequestService() {
		return mobCcsSpecialMrtRequestService;
	}

	public void setMobCcsSpecialMrtRequestService(
			MobCcsSpecialMrtRequestService mobCcsSpecialMrtRequestService) {
		this.mobCcsSpecialMrtRequestService = mobCcsSpecialMrtRequestService;
	}
	
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	
	

	public SpecialMRTReserveService getSpecialMRTReserveService() {
		return specialMRTReserveService;
	}

	public void setSpecialMRTReserveService(
			SpecialMRTReserveService specialMRTReserveService) {
		this.specialMRTReserveService = specialMRTReserveService;
	}

	public void validate(Object obj, Errors errors) {
		
		logger.debug("SpecialMrtRequestValidator is call");
		
		SpecialMrtRequestDTO specialMrtRequestDTO = (SpecialMrtRequestDTO)obj;
		MrtInventoryDTO mrtInventoryDTO = new MrtInventoryDTO();
	
	
		if ("SAVE".equalsIgnoreCase(specialMrtRequestDTO.getActionType())){

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msisdnPattern", "msisdnPattern.required");
			
			if ("NONE".equals(specialMrtRequestDTO.getTitle().trim())){
				errors.rejectValue("title","title.required");
			}
			
			if ("NONE".equals(specialMrtRequestDTO.getContractPeriod().trim())){
				errors.rejectValue("contractPeriod", "contractPeriod.required");
			}
			
			if ("NONE".equals(specialMrtRequestDTO.getRecurrentAmt().trim())){
				errors.rejectValue("recurrentAmt", "recurrentAmt.required");
			}
			
			
			if (StringUtils.isNotEmpty(specialMrtRequestDTO.getRemark())) {
				if (specialMrtRequestDTO.getRemark().length() > 200) {
					errors.rejectValue("remark", "remark.toolong");		
				}
			}
			
		}
		
		if ("REJECT".equalsIgnoreCase(specialMrtRequestDTO.getActionType())){
			if (StringUtils.isNotEmpty(specialMrtRequestDTO.getAdminRemark())) {
				if (specialMrtRequestDTO.getAdminRemark().length() > 200) {
					errors.rejectValue("adminRemark", "remark.toolong");				
				}
			}
		}
		
		
		if ("UPDATE".equalsIgnoreCase(specialMrtRequestDTO.getActionType())){
			
			if ("NONE".equals(specialMrtRequestDTO.getMsisdnlvl().trim())){
				errors.rejectValue("msisdnlvl", "msisdnlvl.required");
			}
			
			if (StringUtils.isNotEmpty(specialMrtRequestDTO.getAdminRemark())) {
				if (specialMrtRequestDTO.getAdminRemark().length() > 200) {
					errors.rejectValue("adminRemark", "remark.toolong");				
				}
			}
			
			if ((StringUtils.isNotBlank(specialMrtRequestDTO.getReserveId())  && StringUtils.isBlank(specialMrtRequestDTO.getResOperId()) )|| StringUtils.isBlank(specialMrtRequestDTO.getReserveId() )&& StringUtils.isNotBlank(specialMrtRequestDTO.getResOperId() ) ){
				errors.rejectValue("resOperId", "reserveIdresOperId.invalid");			
			}else{
				
				if (StringUtils.isNotBlank(specialMrtRequestDTO.getReserveId())&&!Utility.isDigit(specialMrtRequestDTO.getReserveId())){
					errors.rejectValue("reserveId", "reserveId.invalid");	
				}
				
				if (StringUtils.isNotBlank(specialMrtRequestDTO.getResOperId())&& !isAlphaOrNumber(specialMrtRequestDTO.getResOperId())){
					errors.rejectValue("resOperId", "resOperId.invalid");
				}		
			}
			
			if ( StringUtils.isBlank(specialMrtRequestDTO.getMsisdn()) || "".equals(specialMrtRequestDTO.getMsisdn().trim())  ){
				errors.rejectValue("msisdn", "msisdn.required");
			}else{
				
				if (!isMobileNumber(specialMrtRequestDTO.getMsisdn())){
					errors.rejectValue("msisdn", "msisdnlvl.invalid");	
				}	
				
			}
						
			if ( StringUtils.isBlank(specialMrtRequestDTO.getValidDate())  ){
				errors.rejectValue("validDate", "validDate.required");
			}
			

			if (StringUtils.isNotEmpty(specialMrtRequestDTO.getApprovalSerial())   ) {
				String requestId = mobCcsSpecialMrtRequestService.getSpecialMrtRequestByApprovalSerial(specialMrtRequestDTO.getApprovalSerial());
				
				List<CodeLkupDTO> ccsCh = this.codeLkupService.getCodeLkupDTOALL("CCS_CH");
				List<CodeLkupDTO> sboCh = this.codeLkupService.getCodeLkupDTOALL("SBO_CH");
				
				if (specialMrtRequestDTO.getApprovalSerial().length() == 16){
					String partA = specialMrtRequestDTO.getApprovalSerial().substring(0, 2);
					String partB = specialMrtRequestDTO.getApprovalSerial().substring(2, 4);
					String partC1 = specialMrtRequestDTO.getApprovalSerial().substring(4, 8);
					String partC2 = specialMrtRequestDTO.getApprovalSerial().substring(8, 10);
					String partC3 = specialMrtRequestDTO.getApprovalSerial().substring(10, 12);
					String partD = specialMrtRequestDTO.getApprovalSerial().substring(12,15 );
					String partE = specialMrtRequestDTO.getApprovalSerial().substring(15,16 );
					
					boolean foundCCSCh = false;
					boolean foundSBOCh = false;
					if (ccsCh != null ) {
						for (CodeLkupDTO dto : ccsCh) {
							if (dto.getCodeId().substring(0, dto.getCodeId().length() - 1).equals(partB)) {
								foundCCSCh = true;
								break;
							}
						}
					}
					if (sboCh !=null) {
					
						for (CodeLkupDTO dto : sboCh) {
							if (dto.getCodeId().substring(0, dto.getCodeId().length() - 1 ).equals(partB)) {
							
								foundSBOCh = true;
								break;
							}
						}
					}
				
					if (requestId != null && !requestId.equals(specialMrtRequestDTO.getRequestId())){
						errors.rejectValue("approvalSerial", "approvalSerial.duplicate");
					}
					else if (!"SM".equals(partA)){
						errors.rejectValue("approvalSerial", "approvalSerial.invalid");
					}else if (!( foundCCSCh && !foundSBOCh)){
						errors.rejectValue("approvalSerial", "approvalSerial.invalid");
					}else if ( !Utility.isValidDate(partC3, partC2, partC1)){
								errors.rejectValue("approvalSerial", "approvalSerial.invalid");
					}else if (!isNumber(partD)){
						errors.rejectValue("approvalSerial", "approvalSerial.invalid");
					}else if (!isAlpha(partE)){
						errors.rejectValue("approvalSerial", "approvalSerial.invalid");
					}
				}else{
					errors.rejectValue("approvalSerial", "approvalSerial.invalid");
				}
			   
		    
			}
		
			
			/**check any changes if old record not null**/
			Boolean hasChange = false;
			
			SpecialMrtRequestDTO oldSpecialMrtRequestDTO = new SpecialMrtRequestDTO(); 
			oldSpecialMrtRequestDTO = mobCcsSpecialMrtRequestService.getSpecialMrtRequestDTO(specialMrtRequestDTO.getRequestId());
			if (oldSpecialMrtRequestDTO != null && "PROCEED".equalsIgnoreCase(oldSpecialMrtRequestDTO.getApprovalResult())) {
			
				if (oldSpecialMrtRequestDTO.getApprovalSerial() == null){
					oldSpecialMrtRequestDTO.setApprovalSerial("");
				}
				if (oldSpecialMrtRequestDTO.getAdminRemark()==null){
					oldSpecialMrtRequestDTO.setAdminRemark("");
				}
				if (oldSpecialMrtRequestDTO.getReserveId()==null){
					oldSpecialMrtRequestDTO.setReserveId("");
				}
				if (oldSpecialMrtRequestDTO.getResOperId()==null){
					oldSpecialMrtRequestDTO.setResOperId("");
				}
						
				if (!oldSpecialMrtRequestDTO.getMsisdn().equals(specialMrtRequestDTO.getMsisdn())){
					hasChange = true;
				}else if (!oldSpecialMrtRequestDTO.getMsisdnlvl().equals(specialMrtRequestDTO.getMsisdnlvl())){
					hasChange = true;
				}else if (!oldSpecialMrtRequestDTO.getReserveId().equals(specialMrtRequestDTO.getReserveId())){
					hasChange = true;
				}else if (!oldSpecialMrtRequestDTO.getResOperId().equals(specialMrtRequestDTO.getResOperId())){
				hasChange = true;
				}else if (!oldSpecialMrtRequestDTO.getValidDate().equals(specialMrtRequestDTO.getValidDate())){
					hasChange = true;
				}else if (!oldSpecialMrtRequestDTO.getApprovalSerial().equals(specialMrtRequestDTO.getApprovalSerial())){
					hasChange = true;
				}else if (!oldSpecialMrtRequestDTO.getAdminRemark().equals(specialMrtRequestDTO.getAdminRemark())){
					hasChange = true;
				}
			
			 
				if (hasChange == false){
					errors.rejectValue("approvalResult", "update.required");
				}
			
			}
			/**check any changes if old record not null**/
			
			
			
			
			//reserveId operId  blank ---> assume from pool
			if (StringUtils.isBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isBlank(specialMrtRequestDTO.getResOperId())){
					
					//try to find is the msisdn valid for special mrt request.
					mrtInventoryDTO = mobCcsSpecialMrtRequestService.getMrtInventoryByMrt(specialMrtRequestDTO.getMsisdn());
					
					if (mrtInventoryDTO == null){  
						
						//find that whether the mrt is sold or not exist
						List<MrtInventoryDTO> mrtInventoryList = mrtInventoryService.getMrtInventoryDTOALL(specialMrtRequestDTO.getMsisdn());
						
						//the mrt is in bomweb_mrt_inventory
						if (mrtInventoryList != null && !mrtInventoryList.isEmpty()){
							boolean cnmAndAllow = true;
							for (int k = 0 ; k <mrtInventoryList.size() && cnmAndAllow == true; k++){
								if (!(StringUtils.isNotBlank(mrtInventoryList.get(k).getReserveId()) &&  mrtInventoryList.get(k).getMsisdnStatus() == 25)){
									cnmAndAllow = false;
								}
							}
							
							if (cnmAndAllow == true){
								errors.rejectValue("resOperId", "reserveIdresOperId.required");	
								//all suspend status and cnm number
							}else{
								//inventory number or cnm number but not in free/reserve/suspend
								errors.rejectValue("msisdn", "msisdnStatus.invalid");
							}
						
						}else{	 //CNM number			
							errors.rejectValue("resOperId", "reserveIdresOperId.required");				
						}
						
					}else{ //can find one record that is free/reserve
						
						//check whether the record is really from pool
						if (StringUtils.isNotBlank(mrtInventoryDTO.getReserveId())){
							//it is CNM
							errors.rejectValue("resOperId","reserveIdresOperId.required");
						}else{ //it is inventory number --> OKAY
							
							//if free, go on , if reserve, check bomweb_mrt_status
							if (mrtInventoryDTO.getMsisdnStatus() == 18){						
								if (StringUtils.isBlank(mrtInventoryDTO.getRequestId())){
									//check bomweb_mrt_status
									List<MrtReserveDTO> mrtReserveList = new ArrayList<MrtReserveDTO>();
									mrtReserveList = 	specialMRTReserveService.getMrtReserveByMrt(specialMrtRequestDTO.getMsisdn());
									boolean reserveError = false;
							
									if (mrtReserveList != null && !mrtReserveList.isEmpty()){	
								
										for (int y = 0 ; y < mrtReserveList.size() && !reserveError; y++){
											if ("5".equalsIgnoreCase(mrtReserveList.get(y).getStatus()) || "19".equals(mrtReserveList.get(y).getStatus()) || "18".equals(mrtReserveList.get(y).getStatus()) || "20".equals(mrtReserveList.get(y).getStatus())){
												reserveError = true;
											}
										}
											
									}
									
									if (reserveError == true){
			
										errors.rejectValue("msisdn", "msisdnStatus.invalid");
									}
									
								}else{
									//recall, change information, check whether is same requestId.
									
									if (!specialMrtRequestDTO.getRequestId().equals(mrtInventoryDTO.getRequestId())){
										errors.rejectValue("msisdn", "msisdnStatus.invalid");
									}
								}
							
							}
							
							
							//all ok, check msisdnlvl, check channelCd
							if (!"NONE".equals(specialMrtRequestDTO.getMsisdnlvl()) && !specialMrtRequestDTO.getMsisdnlvl().equals(mrtInventoryDTO.getMsisdnlvl())){
								errors.rejectValue("msisdnlvl", "msisdnlvl.notMatch");
							}
							
							/*if (!mrtInventoryDTO.getChannelCd().equalsIgnoreCase(specialMrtRequestDTO.getChannel())){
								errors.rejectValue("msisdn", "msisdnChannelCd.invalid");
							}*/  //old checking method
							
							//new checking method
							List<CodeLkupDTO> specialMrtPool = this.codeLkupService.getCodeLkupDTOALL("SPECIAL_MRT_POOL");
							boolean foundSpecialMrtPool = false;
							if (specialMrtPool != null ) {
								for (CodeLkupDTO dto : specialMrtPool) {
									if (dto.getCodeId().equals(mrtInventoryDTO.getChannelCd())) {
										foundSpecialMrtPool = true;
										break;
									}
								}
							}
							if (!foundSpecialMrtPool){
								errors.rejectValue("msisdn", "msisdnChannelCd.invalid");
							}
							
							
						}

						
					}
			
			}
			
			
			//reserveId operId not blank ---> assume from CNM
			if (StringUtils.isNotBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isNotBlank(specialMrtRequestDTO.getResOperId())){
				
				mrtInventoryDTO = mobCcsSpecialMrtRequestService.getMrtInventoryByMrt(specialMrtRequestDTO.getMsisdn());
			
				if (mrtInventoryDTO == null){  
					
					//find that whether the mrt is assign/sold or not exist
					List<MrtInventoryDTO> mrtInventoryList = mrtInventoryService.getMrtInventoryDTOALL(specialMrtRequestDTO.getMsisdn());
					
					//the mrt is in bomweb_mrt_inventory
					if (mrtInventoryList != null && !mrtInventoryList.isEmpty()){
						boolean error = false;
						String mrtGrade =  mrtInventoryList.get(0).getMsisdnlvl();
						for (int i = 0 ; i< mrtInventoryList.size() && !error ; i++){
							if ((StringUtils.isBlank(mrtInventoryList.get(i).getReserveId()))){
								error = true;
							}					
							else if ( mrtInventoryList.get(i).getMsisdnStatus() != 25){
								error = true;

							}else if (!mrtGrade.equalsIgnoreCase(mrtInventoryList.get(i).getMsisdnlvl())){
								error = true;						
							}
						}
						if (error == true){ 
							errors.rejectValue("msisdn", "msisdnStatus.invalid");
						}else{			
							//in bomweb_mrt_inventory but all suspend status -->OKAY
							
							/*if (!"NONE".equals(specialMrtRequestDTO.getMsisdnlvl()) && !specialMrtRequestDTO.getMsisdnlvl().equals(mrtGrade)){
								errors.rejectValue("msisdnlvl", "msisdnlvl.notMatch");
							}*/
						}
					}else{				
						//not in bomweb_mrt_inventory yet ---> OKAY	
					}
					
				}else{ //can find one record that is free/reserve
					
					if (StringUtils.isBlank(mrtInventoryDTO.getReserveId())){
						errors.rejectValue("resOperId","reserveIdresOperId.notRequired");
					}
					else {   //CNM number --> OKAY
						if (mrtInventoryDTO.getMsisdnStatus() == 18){		//check whether reserved for this requestId (change information)				
							if (!specialMrtRequestDTO.getRequestId().equals(mrtInventoryDTO.getRequestId())){
								errors.rejectValue("msisdn", "msisdnStatus.invalid");
							}/*else if (!specialMrtRequestDTO.getMsisdnlvl().equals(mrtInventoryDTO.getMsisdnlvl())){
								errors.rejectValue("msisdnlvl", "msisdnlvl.notMatch");
							}*/
							
						}
					}
						
					
						
				}
			}
			
			
			
				
				
			
			
			
		}
			
		
		

		
		
		
		
			
			
			
	
			/*if ("APPROVED".equalsIgnoreCase(specialMrtRequestDTO.getApprovalResult())){
					
				mrtInventoryDTO = mobCcsSpecialMrtRequestService.getMrtInventoryByMrt(specialMrtRequestDTO.getMsisdn());
				if (mrtInventoryDTO != null ){
					if ( StringUtils.isNotBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isNotBlank(specialMrtRequestDTO.getResOperId())){
						errors.rejectValue("resOperId","reserveIdresOperId.notRequired");
					}
								
					
					if (!"NONE".equals(specialMrtRequestDTO.getMsisdnlvl().trim()) && !specialMrtRequestDTO.getMsisdnlvl().equals(mrtInventoryDTO.getMsisdnlvl())){
						errors.rejectValue("msisdnlvl", "msisdnlvl.notMatch");
					}
					
					if (mrtInventoryDTO.getMsisdnStatus() != 2){
						errors.rejectValue("msisdn", "msisdnStatus.invalid");
					}else if (!mrtInventoryDTO.getChannelCd().equalsIgnoreCase(specialMrtRequestDTO.getChannel())){
						errors.rejectValue("msisdn", "msisdnChannelCd.invalid");
					}
					
				
				}
				else{
					List<MrtInventoryDTO> mrtInventoryList = mrtInventoryService.getMrtInventoryDTOALL(specialMrtRequestDTO.getMsisdn());
					
					if (mrtInventoryList != null && !mrtInventoryList.isEmpty()){
						errors.rejectValue("msisdn", "msisdnStatus.invalid");
					}else{
						if ( StringUtils.isNotBlank(specialMrtRequestDTO.getMsisdn()) && !"".equals(specialMrtRequestDTO.getMsisdn().trim()) && isMobileNumber(specialMrtRequestDTO.getMsisdn()) ){
							if ( StringUtils.isBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isBlank(specialMrtRequestDTO.getResOperId())){
									errors.rejectValue("resOperId", "reserveIdresOperId.required");
							}
						}
					}
				}
				
			}*/
			
		/*	if ("PROCEED".equalsIgnoreCase(specialMrtRequestDTO.getApprovalResult())){
							
				Boolean allow = false;
				mrtInventoryDTO = mobCcsSpecialMrtRequestService.getMrtInventoryByMrt(specialMrtRequestDTO.getMsisdn());
				if (mrtInventoryDTO != null){
					
					if (!specialMrtRequestDTO.getRequestId().equals(mrtInventoryDTO.getRequestId()) ){
						if ( StringUtils.isNotBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isNotBlank(specialMrtRequestDTO.getResOperId())){
							errors.rejectValue("resOperId", "reserveIdresOperId.notRequired");
						}
					}else{ //update self information
								
						if ( StringUtils.isNotBlank(mrtInventoryDTO.getReserveId())){  //originally from CNM
							if ( StringUtils.isBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isBlank(specialMrtRequestDTO.getResOperId())){
								errors.rejectValue("resOperId", "dummy", "Old Number from CNM found. Please enter reserveId and operId.");
							}
						}
						else{ //originally From pool
							if ( StringUtils.isNotBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isNotBlank(specialMrtRequestDTO.getResOperId())){
								errors.rejectValue("resOperId", "dummy", "Old Number from inventory found. Please do not enter reserveId and operId.");
							}
						}
					}
					
					
					
					if (!"NONE".equals(specialMrtRequestDTO.getMsisdnlvl().trim()) && !specialMrtRequestDTO.getMsisdnlvl().equals(mrtInventoryDTO.getMsisdnlvl())){
						errors.rejectValue("msisdnlvl", "msisdnlvl.notMatch");
					}
					
					
					if (mrtInventoryDTO.getMsisdnStatus() == 2 ){
						allow = true;
					}else if (mrtInventoryDTO.getMsisdnStatus() == 18){
						if (specialMrtRequestDTO.getRequestId().equals(mrtInventoryDTO.getRequestId())){
							allow = true;
						}
					}
					
					
					if (allow == false){
						errors.rejectValue("msisdn", "msisdnStatus.invalid");
					}else if (!mrtInventoryDTO.getChannelCd().equalsIgnoreCase(specialMrtRequestDTO.getChannel())  && !"CCS".equalsIgnoreCase(mrtInventoryDTO.getChannelCd())   ){
						errors.rejectValue("msisdn", "msisdnChannelCd.invalid");
					}
					
					
				}else{ //input number not in pool-->From CNM
					
					List<MrtInventoryDTO> mrtInventoryList = mrtInventoryService.getMrtInventoryDTOALL(specialMrtRequestDTO.getMsisdn());
					
					if (mrtInventoryList != null && !mrtInventoryList.isEmpty()){
						errors.rejectValue("msisdn", "msisdnStatus.invalid");
					}
					else{
						if ( StringUtils.isNotBlank(specialMrtRequestDTO.getMsisdn()) && !"".equals(specialMrtRequestDTO.getMsisdn().trim())  ){
							if ( StringUtils.isBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isBlank(specialMrtRequestDTO.getResOperId())){
								errors.rejectValue("resOperId", "reserveIdresOperId.required");
							}
						}
					}
				
				}
			}*/
			
		//errors.rejectValue("msisdn", "dummy", "not allow to go in.");
	
	}
	
	public static boolean isMobileNumber(String str) {
	    return str.matches("(?!999)[35689][0-9]{7}");
	}
	
	public static boolean isAlphaOrNumber(String str) {
	    return str.matches("[0-9a-zA-Z]{8,15}");
	}
	
	public static boolean isAlpha(String str) {
	    return str.matches("[a-zA-Z]+");
	}
	
	public static boolean isNumber(String str) {
	    return str.matches("[0-9]+");
	}
	
}
