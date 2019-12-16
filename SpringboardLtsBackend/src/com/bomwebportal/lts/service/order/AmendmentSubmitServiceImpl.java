package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AmendAppointmentLtsDTO;
import com.bomwebportal.lts.dto.order.AmendCategoryLtsDTO;
import com.bomwebportal.lts.dto.order.AmendDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.AmendPaymentDTO;
import com.bomwebportal.lts.dto.order.AmendPipbInfoDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class AmendmentSubmitServiceImpl implements AmendmentSubmitService {
	
	private WorkQueueSubmissionService workQueueSubmissionService = null;
	private SaveSbOrderLtsService saveSbOrderLtsService = null;
	private CodeLkupCacheService delayReasonCodeLkupCacheService = null;
	private CodeLkupCacheService creditCardTyepCodeLkupCacheService;
	private CodeLkupCacheService idDocTypeLkupCacheService = null;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	
	@Transactional
	public void submitAmendment(AmendLtsDTO pAmend, String pUser, String pShopCd) {
		this.generateAmendmentRemark(pAmend);
		this.workQueueSubmissionService.submitAmendmentToWorkQueue(pAmend, pUser, pShopCd);
		this.saveAmendmentToOrder(pAmend, pUser);
	}
	
	public void saveAmendmentToOrder(AmendLtsDTO pAmend, String pUser) {
		
		AmendDetailLtsDTO[] amendDtls = pAmend.getAmendDtls();
		AmendCategoryLtsDTO[] categories = null;
		
		for (int i=0; amendDtls!=null && i<amendDtls.length; ++i) {
			categories = amendDtls[i].getCategories();
			
			for (int j=0; categories!=null && j<categories.length; ++j) {
				if (categories[j] instanceof AmendAppointmentLtsDTO) {
					AmendAppointmentLtsDTO amendAppnt = ((AmendAppointmentLtsDTO)categories[j]);
					AppointmentDetailLtsDTO ordAppnt = amendAppnt.getReferenceOrdAppnt();
					BeanUtils.copyProperties(amendAppnt, ordAppnt);
					ordAppnt.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
					this.saveSbOrderLtsService.saveAppointmentDetail(ordAppnt, pAmend.getOrderId(), amendDtls[i].getDtlId(), pUser);
					
					AppointmentDetailLtsDTO ordPipbAppnt = amendAppnt.getReferenceOrdPipbAppnt();
					if(ordPipbAppnt != null){
						ordPipbAppnt.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
						this.saveSbOrderLtsService.saveAppointmentDetail(ordPipbAppnt, pAmend.getOrderId(), amendAppnt.getPipbSrvDtlId(), pUser);
					}
					
				}else if (categories[j] instanceof AmendPipbInfoDTO) {
					AmendPipbInfoDTO amendPipb = ((AmendPipbInfoDTO)categories[j]);
					if(amendPipb.getReferenceOrdAddr() != null){
						AddressDetailLtsDTO ordAddr = amendPipb.getReferenceOrdAddr();
						ordAddr.setFloorNo(amendPipb.getFloor());
						ordAddr.setUnitNo(amendPipb.getFlat());
						ordAddr.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
						this.saveSbOrderLtsService.saveAddress(ordAddr, pAmend.getOrderId(), pUser);
					}
					
					if(amendPipb.getReferenceOrdCust() != null){
						CustomerDetailLtsDTO ordCust = amendPipb.getReferenceOrdCust();
						ordCust.setTitle(amendPipb.getTitle());
						ordCust.setFirstName(amendPipb.getFirstName());
						ordCust.setLastName(amendPipb.getLastName());
						ordCust.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
						this.saveSbOrderLtsService.saveCustomer(ordCust, pAmend.getOrderId(), ordCust.getCustNo(), pUser);
					}
					
//					if(amendPipb.getReferencePipbInfo() != null){
//						PipbLtsDTO pipbInfo = amendPipb.getReferencePipbInfo();
//						pipbInfo.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
//						this.saveSbOrderLtsService.savePipb(pipbInfo, pAmend.getOrderId(), amendDtls[i].getDtlId());
//					}
					
				}
			}
		}
	}
	
	/*Append remarks to amendDtls accordingly and return all remarks as a string*/
	public String generateAmendmentRemark(AmendLtsDTO pAmend) {
		AmendDetailLtsDTO[] amendDtls = pAmend.getAmendDtls();
		AmendCategoryLtsDTO[] categories = null;
		String amendRemark = null;
		
		StringBuilder allRemarksSb = new StringBuilder();
		
		String defaultRemarks = this.generateDefaultAmendmentRemark(pAmend);
		pAmend.appendRemarks(this.createRemarks(defaultRemarks));
		allRemarksSb.append(defaultRemarks + "\n");
		
		for (int i=0; amendDtls!=null && i<amendDtls.length; ++i) {
			categories = amendDtls[i].getCategories();
			
			for (int j=0; categories!=null && j<categories.length; ++j) {
				if (categories[j] instanceof AmendAppointmentLtsDTO) {
					SbOrderDTO tempSbOrderDTO =orderRetrieveLtsService.retrieveSbOrder(pAmend.getOrderId(), true);
					boolean isPreInstall = LtsSbHelper.isPreInstall(tempSbOrderDTO);
					
					AmendAppointmentLtsDTO tempAmendAppointmentLtsDTO = (AmendAppointmentLtsDTO)categories[j];
					if(isPreInstall)
					{
						tempAmendAppointmentLtsDTO.setPreInstall(isPreInstall);
					}
					
					amendRemark = this.generateAppointmentAmendRemark(tempAmendAppointmentLtsDTO);
				} else if (categories[j] instanceof AmendPaymentDTO) {
					amendRemark = this.generatePaymentAmendRemark((AmendPaymentDTO)categories[j]);
				}else if (categories[j] instanceof AmendPipbInfoDTO) {
					amendRemark = this.generatePipbInfoAmendRemark((AmendPipbInfoDTO)categories[j]);
				} else {
					amendRemark = "";
				}
				categories[j].appendRemarks(this.createRemarks(amendRemark));
				allRemarksSb.append(amendRemark);
			}
		}
		
		return allRemarksSb.toString();
	}

	private String generatePipbInfoAmendRemark(AmendPipbInfoDTO pAmendDtl) {
		StringBuilder sbRemark = new StringBuilder();
		sbRemark.append("Amend PIPB Order Info: ");
		if(StringUtils.isNotEmpty(pAmendDtl.getFlat())){
			sbRemark.append("\nFlat: ");
			sbRemark.append(pAmendDtl.getFlat());
		}
		if(StringUtils.isNotEmpty(pAmendDtl.getFloor())){
			sbRemark.append("\nFloor: ");
			sbRemark.append(pAmendDtl.getFloor());
		}
		if(StringUtils.isNotEmpty(pAmendDtl.getTitle())){
			sbRemark.append("\nTitle: ");
			sbRemark.append(pAmendDtl.getTitle());
		}
		if(StringUtils.isNotEmpty(pAmendDtl.getFirstName())){
			sbRemark.append("\nFirst Name: ");
			sbRemark.append(pAmendDtl.getFirstName());
		}
		if(StringUtils.isNotEmpty(pAmendDtl.getLastName())){
			sbRemark.append("\nLast Name: ");
			sbRemark.append(pAmendDtl.getLastName());
		}
		if(pAmendDtl.isUploadDocument()){
			sbRemark.append("\nUpload document: Y");
		}
		return sbRemark.toString();
	}
		
		
		
	private String generateDefaultAmendmentRemark(AmendLtsDTO amend) {
		
		StringBuilder sbRemark = new StringBuilder();
		sbRemark.append("Applicant Name: ");
		sbRemark.append(StringUtils.isEmpty(amend.getName()) ? "" : amend.getName());
		sbRemark.append("\nApplicant Contact No.: ");
		sbRemark.append(StringUtils.isEmpty(amend.getContactNum()) ? "" : amend.getContactNum());
		sbRemark.append("\nApplicant Relationship: ");
		if (StringUtils.equals("SU", amend.getRelationshipCd())) {
			sbRemark.append("Sub");	
		} else if (StringUtils.isNotEmpty(amend.getRelationshipCd())) {
			sbRemark.append(LtsSbHelper.getRelationshipDesc(amend.getRelationshipCd()));
		}
		sbRemark.append("\n");
		return sbRemark.toString();
	}
	
	
	public String generateAppointmentAmendRemark(AmendAppointmentLtsDTO pAmendDtl, boolean isGenerateAmendForm) {
		
		StringBuilder sbRemark = new StringBuilder();
		boolean isPreInstall = pAmendDtl.isPreInstall();
		
		if(!StringUtils.equals(pAmendDtl.getPrimarySrvStatus(), LtsBackendConstant.ORDER_STATUS_CLOSED)
				|| StringUtils.isEmpty(pAmendDtl.getPipbAppntStartTime())){

			if(StringUtils.isNotEmpty(pAmendDtl.getAppntStartTime())){
				
				String fromInstallDate = LtsDateFormatHelper.getDateFromDTOFormat(pAmendDtl.getFromAppntStartTime());
				String fromInstallTimeslot = LtsDateFormatHelper.getFromToTimeFormat(pAmendDtl.getFromAppntStartTime(), pAmendDtl.getFromAppntEndTime());
				String targetInstallDate = LtsDateFormatHelper.getDateFromDTOFormat(pAmendDtl.getAppntStartTime());
				String targetInstallTimeslot = LtsDateFormatHelper.getFromToTimeFormat(pAmendDtl.getAppntStartTime(), pAmendDtl.getAppntEndTime());
				
				if(!StringUtils.equals(fromInstallDate,targetInstallDate)
						|| !StringUtils.equals(LtsDateFormatHelper.convertToSBTime(fromInstallTimeslot), targetInstallTimeslot)){
					if(StringUtils.isNotEmpty(pAmendDtl.getFromAppntStartTime())){
						if(isPreInstall)
						{
							sbRemark.append("\nFrom Pre-install Date: ");
						}
						else
						{
							sbRemark.append("\nFrom Installation Date: ");
						}
						sbRemark.append(fromInstallDate);
						sbRemark.append(" ");
						sbRemark.append(fromInstallTimeslot);
					}
					if(isPreInstall)
					{
						sbRemark.append("\nTarget Pre-install Date: ");
					}
					else
					{
						sbRemark.append("\nTarget Installation Date: ");
					}
				}else{					
					if(isPreInstall)
					{
						sbRemark.append("\nPre-install Date (No Change): ");
					}
					else
					{
						sbRemark.append("\nInstallation Date (No Change): ");
					}
				}
				sbRemark.append(targetInstallDate);
				sbRemark.append(" ");
				sbRemark.append(targetInstallTimeslot);
			}
		}

		if (StringUtils.isNotEmpty(pAmendDtl.getPreWiringStartTime())) {
			
			String fromPrewiringDate = LtsDateFormatHelper.getDateFromDTOFormat(pAmendDtl.getFromPreWiringStartTime());
			String fromPrewiringTimeslot = LtsDateFormatHelper.getFromToTimeFormat(pAmendDtl.getFromPreWiringStartTime(), pAmendDtl.getFromPreWiringStartTime());
			String targetPrewiringDate = LtsDateFormatHelper.getDateFromDTOFormat(pAmendDtl.getPreWiringStartTime());
			String targetPrewiringTimeslot = LtsDateFormatHelper.getFromToTimeFormat(pAmendDtl.getPreWiringStartTime(), pAmendDtl.getPreWiringEndTime());
			
			if(!StringUtils.equals(fromPrewiringDate,targetPrewiringDate)
					|| !StringUtils.equals(LtsDateFormatHelper.convertToSBTime(fromPrewiringTimeslot), targetPrewiringTimeslot)){
				if (StringUtils.isNotEmpty(pAmendDtl.getFromPreWiringStartTime())) {
					sbRemark.append("\nFrom Pre-wiring Date: ");
					sbRemark.append(fromPrewiringDate);
					sbRemark.append(" ");
					sbRemark.append(fromPrewiringTimeslot);
				}
				sbRemark.append("\nTarget Pre-wiring Date: ");
			}else{
				sbRemark.append("\nPre-wiring Date (No Change): ");
			}
			sbRemark.append(targetPrewiringDate);
			sbRemark.append(" ");
			sbRemark.append(targetPrewiringTimeslot);
		}
		if(StringUtils.isNotEmpty(pAmendDtl.getCutOverStartTime())){
			LocalDate cutOverDate = LocalDate.parse(pAmendDtl.getCutOverStartTime().substring(0, 10), DateTimeFormat.forPattern("dd/MM/yyyy"));
			if(cutOverDate.isAfter(LocalDate.now())){
				sbRemark.append("\nTarget telephone switching Date: ");
				sbRemark.append(LtsDateFormatHelper.getDateFromDTOFormat(pAmendDtl.getCutOverStartTime()) + " " + LtsDateFormatHelper.getFromToTimeFormat(pAmendDtl.getCutOverStartTime(), pAmendDtl.getCutOverEndTime()));
			}
		}
		
		if(StringUtils.equals(pAmendDtl.getPrimarySrvStatus(), LtsBackendConstant.ORDER_STATUS_CLOSED)){
			if(StringUtils.isNotEmpty(pAmendDtl.getPipbAppntStartTime())){
				
				String fromPipbDate = LtsDateFormatHelper.getDateFromDTOFormat(pAmendDtl.getFromPipbAppntStartTime());
				String fromPipbTimeslot = LtsDateFormatHelper.getFromToTimeFormat(pAmendDtl.getFromPipbAppntStartTime(), pAmendDtl.getFromPipbAppntEndTime());
				String targetPipbDate = LtsDateFormatHelper.getDateFromDTOFormat(pAmendDtl.getPipbAppntStartTime());
				String targetPipbTimeslot = LtsDateFormatHelper.getFromToTimeFormat(pAmendDtl.getPipbAppntStartTime(), pAmendDtl.getPipbAppntEndTime());
				
				if(!StringUtils.equals(fromPipbDate,targetPipbDate)
						|| !StringUtils.equals(LtsDateFormatHelper.convertToSBTime(fromPipbTimeslot), targetPipbTimeslot)){
					sbRemark.append("\nFrom PIPB Appointment Date: ");
					sbRemark.append(fromPipbDate);
					sbRemark.append(" ");
					sbRemark.append(fromPipbTimeslot);
					sbRemark.append("\nTarget PIPB Appointment Date: ");
				}else{
					sbRemark.append("\nPIPB Appointment Date (No Change): ");
				}
				sbRemark.append(targetPipbDate);
				sbRemark.append(" ");
				sbRemark.append(targetPipbTimeslot);
			}
			if(StringUtils.isNotEmpty(pAmendDtl.getPipbCutOverStartTime())){
				LocalDate cutOverDate = LocalDate.parse(pAmendDtl.getPipbCutOverStartTime().substring(0, 10), DateTimeFormat.forPattern("dd/MM/yyyy"));
				if(cutOverDate.isAfter(LocalDate.now())){
					sbRemark.append("\nTarget PIPB telephone switching Date: ");
					sbRemark.append(LtsDateFormatHelper.getDateFromDTOFormat(pAmendDtl.getPipbCutOverStartTime()) + " " + LtsDateFormatHelper.getFromToTimeFormat(pAmendDtl.getPipbCutOverStartTime(), pAmendDtl.getPipbCutOverEndTime()));
				}
			}
		}
	
		
		if(!isGenerateAmendForm){
			sbRemark.append("\nPre-book Serial: ");
			sbRemark.append(pAmendDtl.getSerialNum());
			sbRemark.append("\nDelay Reason Code: ");
			sbRemark.append(this.delayReasonCodeLkupCacheService.get(pAmendDtl.getDelayReasonCd()));
		}
		
		return sbRemark.toString();
	}
	
	private String generateAppointmentAmendRemark(AmendAppointmentLtsDTO pAmendDtl){
		
		return generateAppointmentAmendRemark(pAmendDtl, false);
	}
	
	public String generatePaymentAmendRemark(AmendPaymentDTO pAmendDtl) {
		
		StringBuilder sbRemark = new StringBuilder();
		sbRemark.append("Card Type: ");
		sbRemark.append(this.creditCardTyepCodeLkupCacheService.get(pAmendDtl.getCcType()));
		sbRemark.append("\nCardholder Name: ");
		sbRemark.append(pAmendDtl.getCcHoldName());
		sbRemark.append("\nDoc Type/ Doc ID: ");
		sbRemark.append(this.idDocTypeLkupCacheService.get(pAmendDtl.getCcIdDocType()));
		sbRemark.append(" ");
		sbRemark.append(pAmendDtl.getCcIdDocNo());
		sbRemark.append("\nCredit Card No.: ");
		sbRemark.append(pAmendDtl.getCcNum());
		sbRemark.append("\nExpiry Date: ");
		sbRemark.append(pAmendDtl.getCcExpDate());
//		sbRemark.append("\nIssue Bank: ");
//		sbRemark.append(pAmendDtl.getCcIssueBank());
		sbRemark.append("\nCredit Card Verified: ");
		sbRemark.append(StringUtils.equals("Y", pAmendDtl.getCcVerifiedInd())?"Yes":"No");
		sbRemark.append("\nThird Party Credit Card: ");
		sbRemark.append(StringUtils.equals("Y", pAmendDtl.getCcThirdPartyInd()) ? "Yes" : "No");
		if(StringUtils.isNotBlank(pAmendDtl.getFaxSerialNum())){
			sbRemark.append("\nFax Serial Number: ");
			sbRemark.append(pAmendDtl.getFaxSerialNum());
		}
		return sbRemark.toString();
	}
	
	private RemarkDetailLtsDTO[] createRemarks(String pRemark) {
		
		List<RemarkDetailLtsDTO> remarkList = new ArrayList<RemarkDetailLtsDTO>();
		RemarkDetailLtsDTO rmkContent = null;
		
	    for (int i = 0; i < pRemark.length(); i += 4000) {
	    	rmkContent = new RemarkDetailLtsDTO();
	    	rmkContent.setRmkDtl(pRemark.substring(i, Math.min(pRemark.length(), i + 4000)));
	    	remarkList.add(rmkContent);
	    }
	    return remarkList.toArray(new RemarkDetailLtsDTO[remarkList.size()]);
	}

	public WorkQueueSubmissionService getWorkQueueSubmissionService() {
		return this.workQueueSubmissionService;
	}

	public void setWorkQueueSubmissionService(
			WorkQueueSubmissionService pWorkQueueSubmissionService) {
		this.workQueueSubmissionService = pWorkQueueSubmissionService;
	}

	public CodeLkupCacheService getDelayReasonCodeLkupCacheService() {
		return delayReasonCodeLkupCacheService;
	}

	public void setDelayReasonCodeLkupCacheService(
			CodeLkupCacheService delayReasonCodeLkupCacheService) {
		this.delayReasonCodeLkupCacheService = delayReasonCodeLkupCacheService;
	}

	public SaveSbOrderLtsService getSaveSbOrderLtsService() {
		return saveSbOrderLtsService;
	}

	public void setSaveSbOrderLtsService(SaveSbOrderLtsService saveSbOrderLtsService) {
		this.saveSbOrderLtsService = saveSbOrderLtsService;
	}

	public CodeLkupCacheService getCreditCardTyepCodeLkupCacheService() {
		return creditCardTyepCodeLkupCacheService;
	}

	public void setCreditCardTyepCodeLkupCacheService(
			CodeLkupCacheService creditCardTyepCodeLkupCacheService) {
		this.creditCardTyepCodeLkupCacheService = creditCardTyepCodeLkupCacheService;
	}

	public CodeLkupCacheService getIdDocTypeLkupCacheService() {
		return idDocTypeLkupCacheService;
	}

	public void setIdDocTypeLkupCacheService(
			CodeLkupCacheService idDocTypeLkupCacheService) {
		this.idDocTypeLkupCacheService = idDocTypeLkupCacheService;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}
	
}
