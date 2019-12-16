package com.bomwebportal.lts.service.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.order.AmendAppointmentLtsDTO;
import com.bomwebportal.lts.dto.order.AmendCategoryLtsDTO;
import com.bomwebportal.lts.dto.order.AmendDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.AmendPaymentDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.order.AmendmentSubmitService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.amendmentForm.AmendmentFormDTO;


public class AmendmentReportLtsServiceImpl implements AmendmentReportLtsService {
	
	AmendmentSubmitService amendmentSubmitService;
	private CodeLkupCacheService relationshipCodeLkupCacheService;
	
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO pSbOrder, String pLocale, String pRptName, boolean pIsEditable, boolean pIsPrintSignature, AmendLtsDTO pAmend) {
		
		AmendmentFormDTO rptDTO = (AmendmentFormDTO) pReportDTO;
		StringBuffer originalRemark = null;
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");	
		DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
		Date date = new Date();
	
		boolean isPreInstall = LtsSbHelper.isPreInstall(pSbOrder);

		rptDTO.setOrderId(pAmend.getOrderId());
		rptDTO.setDate(dateFormat.format(date).toString());
		rptDTO.setTime(timeFormat.format(date).toString());
		
		// sales info
		rptDTO.setSalesShopCode(pAmend.getShopCd());
		rptDTO.setSalesStaffNum(pAmend.getStaffNum());
		rptDTO.setSalesTel(pAmend.getSalesContactNum());
		
		//customer info
		ServiceDetailLtsDTO srvDtl =  LtsSbHelper.getLtsService(pSbOrder);
		
		if(srvDtl.getAccount() !=null){
			
			String docNum = StringUtils.isEmpty(srvDtl.getActualDocId())? srvDtl.getAccount().getCustomer().getIdDocNum() : srvDtl.getActualDocId();
			String docType = StringUtils.isEmpty(srvDtl.getActualDocType())? srvDtl.getAccount().getCustomer().getIdDocType() : srvDtl.getActualDocType();
			
			rptDTO.setCustName(srvDtl.getAccount().getCustomer().getFirstName() + " " + srvDtl.getAccount().getCustomer().getLastName());
			rptDTO.setDocNum(docNum);
			rptDTO.setHKID(StringUtils.equals(LtsBackendConstant.DOC_TYPE_HKID,docType)? true: false);
			rptDTO.setPass(StringUtils.equals(LtsBackendConstant.DOC_TYPE_PASSPORT,docType)? true: false);
		}
			
		//third party info
		rptDTO.setThirdAppName(pAmend.getName());
		rptDTO.setThirdAppRelation((String)relationshipCodeLkupCacheService.get(pAmend.getRelationshipCd()));
		rptDTO.setThirdContactNo(pAmend.getContactNum());
		
		
		for(AmendDetailLtsDTO amendDtl : pAmend.getAmendDtlList()){
			
			rptDTO.setSrvNum(amendDtl.getSrvNum());
			
			for(AmendCategoryLtsDTO amendCatgry : amendDtl.getCategoryList()){
				
			if(LtsBackendConstant.WQ_NATURE_AMEND_LIST.contains(amendCatgry.getNature())){
					rptDTO.setAmendment(true);
				
			
				if(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_CONTACT, amendCatgry.getNature())
						||	StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_CUST_NAME_TITLE, amendCatgry.getNature())){
					
					rptDTO.setChgCustInfo(true);
					
					//append remark for customer info as two natures are using the same field
					if(StringUtils.isEmpty(rptDTO.getChangeCustInfo())){
						rptDTO.setChangeCustInfo(createRemark(amendCatgry.getRemarks()));
					}else{
						originalRemark = new StringBuffer();
						originalRemark.append(rptDTO.getChangeCustInfo());
						originalRemark.append(" ");
						originalRemark.append(createRemark(amendCatgry.getRemarks()));
						
						rptDTO.setChangeCustInfo(originalRemark.toString());
					}
				}
				if(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_BA, amendCatgry.getNature())){
					rptDTO.setChgBA(true);
					rptDTO.setChangeBA(createRemark(amendCatgry.getRemarks()));
				}				
				if(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_DN, amendCatgry.getNature())){
					rptDTO.setChgDN(true);
					rptDTO.setChangeDN(createRemark(amendCatgry.getRemarks()));
				}
				if(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_IA, amendCatgry.getNature())){
					rptDTO.setChgIA(true);
					rptDTO.setChangeIA(createRemark(amendCatgry.getRemarks()));
				}
				if(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_PROG_OFFER, amendCatgry.getNature())){
					rptDTO.setChgOffer(true);
					rptDTO.setChangeOffer(createRemark(amendCatgry.getRemarks()));
				}
				if(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_PCD, amendCatgry.getNature())){
					rptDTO.setChgShareModFsa(true);
					rptDTO.setChangeShareModFsa(createRemark(amendCatgry.getRemarks()));
				}
				if(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_VAS_PREMIUM, amendCatgry.getNature())){
					rptDTO.setChgVasPremi(true);
					rptDTO.setChangeVasPremi(createRemark(amendCatgry.getRemarks()));
				}
//				if(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_EQUIPMENT, amendCatgry.getNature())){
//					rptDTO.setChgTvChannel(true);
//					rptDTO.setChangeTvChannel(createRemark(amendCatgry.getRemarks()));
//				}
				if(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_PDPO, amendCatgry.getNature())){
					rptDTO.setOther(true);
					rptDTO.setOtherContent(createRemark(amendCatgry.getRemarks()));
				}
				
				if(amendCatgry instanceof AmendAppointmentLtsDTO ){
					//rptDTO.setChgSRD(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_SRD, amendCatgry.getNature()));
					rptDTO.setChgSRD(true);
					AmendAppointmentLtsDTO tempAmendAppointmentLtsDTO = (AmendAppointmentLtsDTO)amendCatgry;
					if(isPreInstall)
					{
						tempAmendAppointmentLtsDTO.setPreInstall(isPreInstall);
					}
					String remark = amendmentSubmitService.generateAppointmentAmendRemark(tempAmendAppointmentLtsDTO, true);
					String[] splitRemark = StringUtils.split(remark, "\n", 2);
					
					if (ArrayUtils.isNotEmpty(splitRemark) && splitRemark.length == 2) {
						rptDTO.setChangeSRD(splitRemark[0] + "  " + splitRemark[1]);
					}
				}
				if(amendCatgry instanceof AmendPaymentDTO ){
					//rptDTO.setChgSRD(StringUtils.equals(LtsBackendConstant.WQ_NATURE_AMEND_SRD, amendCatgry.getNature()));
					rptDTO.setChgCardNum(true);
					
					
					String[] creditCardRemarks = StringUtils.split(amendmentSubmitService.generatePaymentAmendRemark((AmendPaymentDTO)amendCatgry), "\n");
					String disCreditCardRemark = "" ;
							
					for(String creditCardRemark : creditCardRemarks)
					{
						if(StringUtils.contains(creditCardRemark, "Credit Card No.:")){
							disCreditCardRemark = disCreditCardRemark + " " + creditCardRemark;
						    disCreditCardRemark = StringUtils.replace(disCreditCardRemark, "\n", " ");
						}else if(StringUtils.contains(creditCardRemark, "Expiry Date:")){
							disCreditCardRemark = disCreditCardRemark + " " + creditCardRemark;
							disCreditCardRemark = StringUtils.replace(disCreditCardRemark, "\n", " ");
						}
					}
					rptDTO.setChangeCredCrdNum(disCreditCardRemark);
				}
		
			}else if(LtsBackendConstant.WQ_NATURE_CANCEL_LIST.contains(amendCatgry.getNature())){
				rptDTO.setCancellation(true);
				rptDTO.setCancelReason(StringUtils.replace(createRemark(amendCatgry.getRemarks()), "Cancel Reason:", ""));
				
				if(StringUtils.equals(amendCatgry.getNature(), LtsBackendConstant.WQ_NATURE_CANCEL_DEL)
						||StringUtils.equals(amendCatgry.getNature(), LtsBackendConstant.WQ_NATURE_CANCEL_DEL_PREPAY)){
					rptDTO.setCancelOrderType("Cancel DEL Order");
				}else if(StringUtils.equals(amendCatgry.getNature(), LtsBackendConstant.WQ_NATURE_CANCEL_NEW_PCD_RETAIN_EYE)){
					rptDTO.setCancelOrderType("Cancel new PCD / VI order but upgrade eye order remains unchanged");
				}else if(StringUtils.equals(amendCatgry.getNature(), LtsBackendConstant.WQ_NATURE_CANCEL_EXTG_PCD_REMAIN_EYE)){
					rptDTO.setCancelOrderType("Cancel PCD / VI change plan order but upgrade eye order remains unchanged");
				}
			}
		}
		}
}
	
	private String createRemark(RemarkDetailLtsDTO[] pRemark){
		
		StringBuilder remark = new StringBuilder();
				
		if(pRemark != null){
			for(RemarkDetailLtsDTO remarlDtl : pRemark){
				remark.append(remarlDtl.getRmkDtl());
			}
			
			return remark.toString();
		}
		
		return null;
	}

	public AmendmentSubmitService getAmendmentSubmitService() {
		return amendmentSubmitService;
	}

	public void setAmendmentSubmitService(
			AmendmentSubmitService amendmentSubmitService) {
		this.amendmentSubmitService = amendmentSubmitService;
	}

	public CodeLkupCacheService getRelationshipCodeLkupCacheService() {
		return relationshipCodeLkupCacheService;
	}

	public void setRelationshipCodeLkupCacheService(
			CodeLkupCacheService relationshipCodeLkupCacheService) {
		this.relationshipCodeLkupCacheService = relationshipCodeLkupCacheService;
	}
}

