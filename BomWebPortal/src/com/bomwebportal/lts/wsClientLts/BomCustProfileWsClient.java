package com.bomwebportal.lts.wsClientLts;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPersonalInfoFormDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.pccw.custProfileGateway.custProfile.ArrayOfCallPlanInfoDTO;
import com.pccw.custProfileGateway.custProfile.CallPlanInfoDTO;
import com.pccw.custProfileGateway.custProfile.CreateCustomerProfile;
import com.pccw.custProfileGateway.custProfile.CreateCustomerProfileResponse;
import com.pccw.custProfileGateway.custProfile.CustAddressDTO;
import com.pccw.custProfileGateway.custProfile.CustPrimaryContactInfoDTO;
import com.pccw.custProfileGateway.custProfile.CustomerDTO;
import com.pccw.custProfileGateway.custProfile.CustomerProfileDTO;
import com.pccw.custProfileGateway.custProfile.GetIddCallPlanEnquiry;
import com.pccw.custProfileGateway.custProfile.GetIddCallPlanEnquiryResponse;
import com.pccw.custProfileGateway.custProfile.InvPreassgnJunctionMsgUpdResponse;
import com.pccw.custProfileGateway.custProfile.UpdateInvPreassgnJunctionMsg;
import com.pccw.custProfileGateway.custProfile.UpdateInvPreassgnJunctionMsgResponse;

public class BomCustProfileWsClient {

	private WebServiceTemplate webServiceTemplate;	

	private final Log logger = LogFactory.getLog(getClass());	
	
	private static final int MAXIMUN_LENGTH_OF_CONTACT_NAME = 40;	
	
	public List<CallPlanInfoDTO> iddCallPlanEnquiry(String pAcctNum) {
				
		try {
			GetIddCallPlanEnquiry request = new GetIddCallPlanEnquiry();		
			request.setAcctNum(pAcctNum);
			GetIddCallPlanEnquiryResponse response = (GetIddCallPlanEnquiryResponse) webServiceTemplate.marshalSendAndReceive(request);
			ArrayOfCallPlanInfoDTO acpi = response.getGetIddCallPlanEnquiryResult().getCallPlanInfoDTO();
			if (acpi != null) {
				return acpi.getCallPlanInfoDTO(); 
			}
			return null;
		} catch (Exception ex) {
			logger.error("Error in calling WS to Bom");
			throw new AppRuntimeException(ex);
		}
	}
	
	
	public CustomerDTO ltsAcqCreateNewCust(LtsAcqPersonalInfoFormDTO personalInfo,LtsAddressRolloutFormDTO addressRollout, String userId) {
		
		try {
			  CustomerProfileDTO  cust = new CustomerProfileDTO ();
			
			  cust.setSystemId(LtsConstant.SYSTEM_ID_DRG);			  
			  cust.setCustCatg(LtsConstant.CUST_CATGORY_NORMAL);			  
			  cust.setGrpIdDocNum(personalInfo.getDocNum());
			  cust.setGrpIdDocType(personalInfo.getDocumentType());
			  cust.setIdDocNum(personalInfo.getDocNum());
			  cust.setIdDocType(personalInfo.getDocumentType());			  
			  
			  if(LtsConstant.DOC_TYPE_HKID.equals(personalInfo.getDocumentType())
					  || LtsConstant.DOC_TYPE_PASSPORT.equals(personalInfo.getDocumentType())) {
				  cust.setCustType("P");
				  cust.setTitle(personalInfo.getTitle());				  
				  cust.setCustFirstName(personalInfo.getGivenName());
				  cust.setCustLastName(personalInfo.getFamilyName());
			  } else if (LtsConstant.DOC_TYPE_HKBR.equals(personalInfo.getDocumentType())) {
				  cust.setCustType("B");
				  cust.setCompanyName(personalInfo.getCompanyName());				 
			  }
			  
			  cust.setIdVerifyInd(personalInfo.getVerified()? "Y" : "N");
			  cust.setIndustryType(LtsConstant.INDUSTRY_TYPE_RESIDENTIAL);
			  cust.setIndustrySubType(LtsConstant.INDUSTRY_SUBTYPE_RESIDENTIAL_BLANK);
			  cust.setLastUpdBy(userId);  
			  
			  CustAddressDTO address = new CustAddressDTO();  
			  address.setAddrType("S");
			  address.setAddrUsage("MA");
			  address.setAreaCode(addressRollout.getAreaCode());
			  address.setAreaDesc(addressRollout.getAreaDesc());
			  address.setBuildNum(addressRollout.getBuildingName());
			  address.setDistrictNum(addressRollout.getDistrictCode());
			  address.setDistrict(addressRollout.getDistrictDesc());
			  address.setFloorNum(addressRollout.getFloor());
			  address.setForeignAddrFlag("N");
			  address.setStreetCatCode(addressRollout.getStreetCatgCode());
			  address.setStreetCatCodeDesc(addressRollout.getStreetCatgDesc());
			  address.setStreetName(addressRollout.getStreetName());
			  address.setStreetNum(addressRollout.getStreetNum());
			  address.setUnitNum(addressRollout.getFlat());
			  address.setLastUpdBy(userId);
			  address.setSysId(LtsConstant.SYSTEM_ID_DRG);
			  address.setModify(true);
			  cust.setCustAddressDTO(address);
			  
			  CustPrimaryContactInfoDTO contactInfoDTO = new CustPrimaryContactInfoDTO();			  
			  if (StringUtils.isNotBlank(personalInfo.getContactEmail()) 
					  || StringUtils.isNotBlank(personalInfo.getMobileNo())
					  || StringUtils.isNotBlank(personalInfo.getFixedLineNo())) {
				  contactInfoDTO.setActionInd("A");
				  if(LtsConstant.DOC_TYPE_HKID.equals(personalInfo.getDocumentType())
						  || LtsConstant.DOC_TYPE_PASSPORT.equals(personalInfo.getDocumentType())) {
					  String contactName = personalInfo.getFamilyName() + " " + personalInfo.getGivenName();
					  if (contactName.length() > MAXIMUN_LENGTH_OF_CONTACT_NAME) {
						  contactName = personalInfo.getFamilyName();
					  }
					  contactInfoDTO.setCntctName(contactName);
					  contactInfoDTO.setTitle(personalInfo.getTitle());
				  } else if (LtsConstant.DOC_TYPE_HKBR.equals(personalInfo.getDocumentType())) {
					  String contactName = personalInfo.getThirdPartyFamilyName() + " (" + personalInfo.getCompanyName() + ")";
					  if (contactName.length() > MAXIMUN_LENGTH_OF_CONTACT_NAME) {
						  contactName = personalInfo.getThirdPartyFamilyName();
					  }
					  contactInfoDTO.setCntctName(contactName);
					  contactInfoDTO.setTitle(personalInfo.getThirdPartyTitle());
				  }
				  contactInfoDTO.setEmail(personalInfo.getContactEmail());
				  contactInfoDTO.setMobile(personalInfo.getMobileNo());
				  contactInfoDTO.setDayPhone(personalInfo.getFixedLineNo());
				  contactInfoDTO.setNightPhone(personalInfo.getFixedLineNo());
				  contactInfoDTO.setPrimaryInd("Y");
				  contactInfoDTO.setLastUpdBy(userId);
			  }
			  cust.setCustPrimaryContactInfoDTO(contactInfoDTO);
			  
			  CreateCustomerProfile request = new CreateCustomerProfile();
			  request.setCustomerProfileDTO(cust);
			  
			  CreateCustomerProfileResponse response = (CreateCustomerProfileResponse) webServiceTemplate.marshalSendAndReceive(request);
			
			  if (!"0".equals(response.getCreateCustomerProfileResult().getErrorSeverity())) {
				  throw new Exception();
			  }
			  
			  return response.getCreateCustomerProfileResult().getCustomerDTO();
		
		} catch (Exception ex) {
			logger.error("Error in calling WS to Bom");
			throw new AppRuntimeException(ex);
		}
	}
	
	public InvPreassgnJunctionMsgUpdResponse updateInvPreassgnJunctionMsg(
			String srvNum, String sbOrderId, String lastUpdBy) {
		
		try {
			UpdateInvPreassgnJunctionMsg request = new UpdateInvPreassgnJunctionMsg();
			request.setSrvNum(srvNum);
			request.setSbOrderId(sbOrderId);
			request.setLastUpdBy(lastUpdBy);
			
			UpdateInvPreassgnJunctionMsgResponse response = 
				(UpdateInvPreassgnJunctionMsgResponse) webServiceTemplate.marshalSendAndReceive(request);
			
			if (!"0".equals(response.getUpdateInvPreassgnJunctionMsgResult().getErrorSeverity())) {
				  throw new Exception();
			}
			
			return response.getUpdateInvPreassgnJunctionMsgResult();
			
		} catch (Exception ex) {
			logger.error("Error in calling WS to Bom");
			throw new AppRuntimeException(ex);
		}
	}
	
	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}
}