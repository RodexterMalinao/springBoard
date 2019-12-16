package com.bomwebportal.lts.service.report;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RecontractLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.recontractForm2017.RecontractForm2017RptDTO;

public class RecontractReportLts2017ServiceImpl implements ReportLtsService {

	protected CodeLkupCacheService relationshipCodeLkupCacheService;
	private CodeLkupCacheService relationshipBrCodeLkupCacheService;
	
	@Override
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		
		RecontractForm2017RptDTO rptDTO = (RecontractForm2017RptDTO) pReportDTO;
		String orderMode = sbOrder.getOrderId().substring(0, 1);
		boolean internalUse = rptDTO.isPrintTermsCondition()? false : true;
		
		if (LtsBackendConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType())) {
			fillPipbDetail(rptDTO, sbOrder, pLocale, pIsPrintSignature, internalUse, orderMode);
			return;
		}
		
		ServiceDetailLtsDTO coreService = LtsSbHelper.getLtsService(sbOrder);
		CustomerDetailLtsDTO newCustomer =  coreService.getAccount().getCustomer();
		CustomerDetailLtsDTO oldCustomer = coreService.getProfileAccount().getCustomer(); 
		RecontractLtsDTO recontractDetail = coreService.getRecontract();
		
		rptDTO.setAfRefNo(sbOrder.getOrderId());
		if (LtsBackendConstant.DOC_TYPE_HKBR.equals(recontractDetail.getIdDocType())) {
			rptDTO.setBrFamilyName(recontractDetail.getCustLastName());
			rptDTO.setBrGivenName(recontractDetail.getCustFirstName());
			rptDTO.setBrMrStrikeThrough(StringUtils.equalsIgnoreCase("MR", recontractDetail.getTitle()) ? null : "___");
			rptDTO.setBrMsStrikeThrough(StringUtils.equalsIgnoreCase("MS", recontractDetail.getTitle()) ? null : "___");
			if (StringUtils.equalsIgnoreCase("MR", recontractDetail.getTitle()) 
					|| StringUtils.equalsIgnoreCase("MS", recontractDetail.getTitle())) {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setBrOthersStrikeThrough("_________");
				}
				else {
					rptDTO.setBrOthersStrikeThrough("____");
				}
			}
			else {
				rptDTO.setBrOthersName(recontractDetail.getTitle());
			}
			rptDTO.setBrTitle((String)relationshipBrCodeLkupCacheService.get(recontractDetail.getRelationship()));	
		}
		
		rptDTO.setCallingCardChkbox(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getCallingCardInd()) ? "X" : null);
//		rptDTO.setCallingCardSrv(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getCallingCardInd())); // TBC
//		rptDTO.setCompanyLogo(companyLogo)
		
		rptDTO.seteBillChkbox(LtsBackendConstant.LTS_BILL_MEDIA_PPS_BILL.equals(coreService.getAccount().getBillMedia()) ? "X" : null);
		rptDTO.setPaperBillChkbox(LtsBackendConstant.LTS_BILL_MEDIA_PAPER_BILL.equals(coreService.getAccount().getBillMedia()) ? "X" : null);
//		rptDTO.setEyeSrv(true); // TBC
		rptDTO.setEyeSrvChkbox("X"); //TBC: eyeSrvStrikeThrough and resSrvStrikeThrough
		if (LtsBackendConstant.EYE_GROUP_TYPE_EYE.equals(coreService.getGrpType())) {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setResSrvStrikeThrough("______________");
				}
				else {
					rptDTO.setResSrvStrikeThrough("______________________");
				}
		}
		else {
			if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
				rptDTO.setEyeSrvStrikeThrough("_______");
			}
			else {
				rptDTO.setEyeSrvStrikeThrough("________");
			}
		}
		rptDTO.setFaxNum(null);
		rptDTO.setFixedIDDChkbox(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getFixedIddInd()) ? "X" : null);
		rptDTO.setFixedIDDSrv(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getFixedIddInd()));
		
		if (coreService.getDeceaseType() == null) {
			if (LtsBackendConstant.DOC_TYPE_HKBR.equals(oldCustomer.getIdDocType())) {
				rptDTO.setFromCustBrStrikeThrough(null);
			}
			else {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setFromCustBrStrikeThrough("__________");
				}
				else {
					rptDTO.setFromCustBrStrikeThrough("_________________");
				}
			}
			rptDTO.setAgreeChkBox("X");
			rptDTO.setFromCustCompanyName(oldCustomer.getCompanyName());
			rptDTO.setFromCustContactNum(null);
			
			StringBuilder idx = new StringBuilder();
			if(!internalUse){
				if (LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) && LtsBackendConstant.DOC_TYPE_HKID.equals(oldCustomer.getIdDocType())) {
					idx.append(StringUtils.substring(oldCustomer.getIdDocNum(), 0, 4));
					idx.append("XXX(X)");
				}
				
				else if (LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) && LtsBackendConstant.DOC_TYPE_PASSPORT.equals(oldCustomer.getIdDocType())) {
					idx.append(StringUtils.substring(oldCustomer.getIdDocNum(), 0, 4));
					idx.append("XXXXX");
				}
			}
			else {
				idx.append(oldCustomer.getIdDocNum());
			}
			
			rptDTO.setFromCustDocNum(idx.toString());
			rptDTO.setFromCustEmailAddr(null);
			rptDTO.setFromCustFamilyName(oldCustomer.getLastName());
			rptDTO.setFromCustGivenName(oldCustomer.getFirstName());
			rptDTO.setFromCustHkidStrikeThrough(LtsBackendConstant.DOC_TYPE_HKID.equals(oldCustomer.getIdDocType()) ? null : "________");
			rptDTO.setFromCustMrStrikeThrough(StringUtils.equalsIgnoreCase("MR", oldCustomer.getTitle()) ? null : "___");
			rptDTO.setFromCustMsStrikeThrough(StringUtils.equalsIgnoreCase("MS", oldCustomer.getTitle()) ? null : "___");
			if (StringUtils.equalsIgnoreCase("MR", oldCustomer.getTitle()) 
					|| StringUtils.equalsIgnoreCase("MS", oldCustomer.getTitle())) {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setFromCustOthersStrikeThrough("__________");
				}
				else {
					rptDTO.setFromCustOthersStrikeThrough("_____");
				}
			}
			else {
				rptDTO.setFromCustOthersName(oldCustomer.getTitle());
			}
			if (LtsBackendConstant.DOC_TYPE_PASSPORT.equals(oldCustomer.getIdDocType())) {
				rptDTO.setFromCustPassStrikeThrough(null);
			}
			else {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setFromCustPassStrikeThrough("____");
				}
				else {
					rptDTO.setFromCustPassStrikeThrough("________");
				}
			}
			if (pIsPrintSignature) {
				rptDTO.setFromCustSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_FROM));
				rptDTO.setFromSignDate(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"));
			}
			rptDTO.setFromCustVerifiedByStaff("X");
		}
		
		else { //get deceased type is not null - deceased case
			rptDTO.setDecCustFamilyName(oldCustomer.getLastName());
			rptDTO.setDecCustGivenName(oldCustomer.getFirstName());
			rptDTO.setDecCustMrStrikeThrough(StringUtils.equalsIgnoreCase("MR", oldCustomer.getTitle()) ? null : "___");
			rptDTO.setDecCustMsStrikeThrough(StringUtils.equalsIgnoreCase("MS", oldCustomer.getTitle()) ? null : "___");
			if (StringUtils.equalsIgnoreCase("MR", oldCustomer.getTitle()) 
					|| StringUtils.equalsIgnoreCase("MS", oldCustomer.getTitle())) {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setDecCustOthersStrikeThrough("__________");
				}
				else {
					rptDTO.setDecCustOthersStrikeThrough("____");
				}
			}
			
			rptDTO.setDecToCustFamilyName(newCustomer.getLastName());
			rptDTO.setDecToCustGivenName(newCustomer.getFirstName());
			rptDTO.setDecToCustMrStrikeThrough(StringUtils.equalsIgnoreCase("MR", newCustomer.getTitle()) ? null : "___");
			rptDTO.setDecToCustMsStrikeThrough(StringUtils.equalsIgnoreCase("MS", newCustomer.getTitle()) ? null : "___");
			if (StringUtils.equalsIgnoreCase("MR", newCustomer.getTitle()) 
					|| StringUtils.equalsIgnoreCase("MS", newCustomer.getTitle())) {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setDecToCustOthersStrikeThrough("__________");
				}
				else {
					rptDTO.setDecToCustOthersStrikeThrough("____");
				}
			}
			rptDTO.setDecToCustRelationship((String)relationshipCodeLkupCacheService.get(recontractDetail.getRelationship()));
			
			if (pIsPrintSignature) {
				rptDTO.setDecToCustSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_TO));
				rptDTO.setDecToSignDate(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"));
			}
		}
		
		rptDTO.setMobIDDChkbox(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getMobIddInd()) ? "X" : null);
		//rptDTO.setMobileIDDSrv(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getMobIddInd())); // TBC
		rptDTO.setNewSrvChkBox("X");
		rptDTO.setSalesmanCode(sbOrder.getSalesCd());
		rptDTO.setSalesStaffName(sbOrder.getSalesName());
		rptDTO.setSrvNum(coreService.getSrvNum());
		rptDTO.setSrvChkBox(StringUtils.equalsIgnoreCase("Y", recontractDetail.getOptOut()) ? "X" : null);
		rptDTO.setStaffContactDetails(sbOrder.getSalesContactNum());
		rptDTO.setStaffNo(sbOrder.getStaffNum());
		rptDTO.setSvcChkBox("X");
		rptDTO.setTeamCode(sbOrder.getSalesTeam());
		rptDTO.setSrvChkBox(StringUtils.equals("Y", newCustomer.getHktOptOut()) ? "X" : null);
		rptDTO.setTheClubHktChkBox(StringUtils.equals("Y", newCustomer.getClubOptOut()) ? "X" : null);
		rptDTO.setToCustBillingAddr(coreService.getAccount().getBillingAddress().getFullBillAddr());
		if (LtsBackendConstant.DOC_TYPE_HKBR.equals(newCustomer.getIdDocType())) {
			rptDTO.setToCustBrStrikeThrough(null);
			}
			else {
				if ((StringUtils.equals(LtsBackendConstant.LOCALE_ENG, pLocale))) {
					rptDTO.setToCustBrStrikeThrough("_________________");
				}
				else {
					rptDTO.setToCustBrStrikeThrough("___________");
				}
			}
		rptDTO.setToCustCompanyName(newCustomer.getCompanyName());
		rptDTO.setToCustContactNum(recontractDetail.getContactNum());
		StringBuilder idx = new StringBuilder();
		if(!internalUse){
			if (LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) && LtsBackendConstant.DOC_TYPE_HKID.equals(newCustomer.getIdDocType())) {
				idx.append(StringUtils.substring(newCustomer.getIdDocNum(), 0, 4));
				idx.append("XXX(X)");
			}
			
			else if (LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) && LtsBackendConstant.DOC_TYPE_PASSPORT.equals(newCustomer.getIdDocType())) {
				idx.append(StringUtils.substring(newCustomer.getIdDocNum(), 0, 4));
				idx.append("XXXXX");
			}
		}
		else {
			idx.append(newCustomer.getIdDocNum());
		}
		
		rptDTO.setToCustDocNum(idx.toString());
		rptDTO.setToCustEmailAddr(newCustomer.getTheClubLogin());
		rptDTO.setToCustEmailAddr2(newCustomer.getTheClubLogin());
		rptDTO.setToCustFamilyName(newCustomer.getLastName());
		rptDTO.setToCustGivenName(newCustomer.getFirstName());
		rptDTO.setToCustHkidStrikeThrough(LtsBackendConstant.DOC_TYPE_HKID.equals(newCustomer.getIdDocType()) ? null : "________");
		rptDTO.setToCustMobileNum(newCustomer.getTheClubMobile());
		rptDTO.setToCustMrStrikeThrough(StringUtils.equalsIgnoreCase("MR", newCustomer.getTitle()) ? null : "___");
		rptDTO.setToCustMsStrikeThrough(StringUtils.equalsIgnoreCase("MS", newCustomer.getTitle()) ? null : "___");
		if (StringUtils.equalsIgnoreCase("MR", newCustomer.getTitle()) 
				|| StringUtils.equalsIgnoreCase("MS", newCustomer.getTitle())) {
			if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
				rptDTO.setToCustOthersStrikeThrough("_________");
			}
			else {
				rptDTO.setToCustOthersStrikeThrough("____");
			}
				
		}
		else {
			rptDTO.setToCustOthersName(newCustomer.getTitle());
		}
		if (LtsBackendConstant.DOC_TYPE_PASSPORT.equals(newCustomer.getIdDocType())) {
			rptDTO.setToCustPassStrikeThrough(null);
		}
		else {
			if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
				rptDTO.setToCustPassStrikeThrough("____");
			}
			else {
				rptDTO.setToCustPassStrikeThrough("________");
			}
		}
		if (pIsPrintSignature) {
			rptDTO.setToCustSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_TO));
			rptDTO.setToSignDate(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"));
		}
		rptDTO.setToCustVerifiedByStaff("X"); //TBC
	}

	
	private void fillPipbDetail(RecontractForm2017RptDTO rptDTO, SbOrderDTO sbOrder, String pLocale, boolean pIsPrintSignature, boolean internalUse, String orderMode) {
		
		ServiceDetailLtsDTO coreService = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailDTO pipbSrvDtl = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls());
		CustomerDetailLtsDTO newCustomer =  coreService.getAccount().getCustomer();
		
		rptDTO.setAfRefNo(sbOrder.getOrderId());
		
		if (LtsBackendConstant.DOC_TYPE_HKBR.equals(newCustomer.getIdDocType())) {
			rptDTO.setBrFamilyName(newCustomer.getLastName());
			rptDTO.setBrGivenName(newCustomer.getFirstName());
			rptDTO.setBrMrStrikeThrough(StringUtils.equalsIgnoreCase("MR", newCustomer.getTitle()) ? null : "___");
			rptDTO.setBrMsStrikeThrough(StringUtils.equalsIgnoreCase("MS", newCustomer.getTitle()) ? null : "___");
			if (StringUtils.equalsIgnoreCase("MR", newCustomer.getTitle()) 
					|| StringUtils.equalsIgnoreCase("MS", newCustomer.getTitle())) {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setBrOthersStrikeThrough("_________");
				}
				else {
					rptDTO.setBrOthersStrikeThrough("____");
				}
			}
			else {
				rptDTO.setBrOthersName(newCustomer.getTitle());
			}
//		rptDTO.setBrTitle((String)relationshipBrCodeLkupCacheService.get(recontractDetail.getRelationship()));
		}

		
//		rptDTO.setCallingCardChkbox(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getCallingCardInd()) ? "X" : null);
//		rptDTO.setCallingCardSrv(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getCallingCardInd())); // TBC
//		rptDTO.setCompanyLogo(companyLogo)
		
		rptDTO.seteBillChkbox(LtsBackendConstant.LTS_BILL_MEDIA_PPS_BILL.equals(coreService.getAccount().getBillMedia()) ? "X" : null);
		rptDTO.setPaperBillChkbox(LtsBackendConstant.LTS_BILL_MEDIA_PAPER_BILL.equals(coreService.getAccount().getBillMedia()) ? "X" : null);
//		rptDTO.setEyeSrv(true); // TBC
		rptDTO.setEyeSrvChkbox("X"); //TBC: eyeSrvStrikeThrough and resSrvStrikeThrough
		if (LtsBackendConstant.EYE_GROUP_TYPE_EYE.equals(coreService.getGrpType())) {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setResSrvStrikeThrough("______________");
				}
				else {
					rptDTO.setResSrvStrikeThrough("______________________");
				}
		}
		else {
			if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
				rptDTO.setEyeSrvStrikeThrough("_______");
			}
			else {
				rptDTO.setEyeSrvStrikeThrough("________");
			}
		}
		rptDTO.setFaxNum(null);
//		rptDTO.setFixedIDDChkbox(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getFixedIddInd()) ? "X" : null);
//		rptDTO.setFixedIDDSrv(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getFixedIddInd()));
		
		if (coreService.getDeceaseType() == null) {
			if (LtsBackendConstant.DOC_TYPE_HKBR.equals(pipbSrvDtl.getPipb().getIdDocType())) {
				rptDTO.setFromCustBrStrikeThrough(null);
			}
			else {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setFromCustBrStrikeThrough("__________");
				}
				else {
					rptDTO.setFromCustBrStrikeThrough("_________________");
				}
			}
			rptDTO.setAgreeChkBox("X");
			rptDTO.setFromCustCompanyName(pipbSrvDtl.getPipb().getCompanyName());
			rptDTO.setFromCustContactNum(null);
			
			StringBuilder idx = new StringBuilder();
			if(!internalUse){
				if (LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) && LtsBackendConstant.DOC_TYPE_HKID.equals(pipbSrvDtl.getPipb().getIdDocType())) {
					idx.append(StringUtils.substring(pipbSrvDtl.getPipb().getIdDocNum(), 0, 4));
					idx.append("XXX(X)");
				}
				
				else if (LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) && LtsBackendConstant.DOC_TYPE_PASSPORT.equals(pipbSrvDtl.getPipb().getIdDocType())) {
					idx.append(StringUtils.substring(pipbSrvDtl.getPipb().getIdDocNum(), 0, 4));
					idx.append("XXXXX");
				}
			}
			else {
				idx.append(pipbSrvDtl.getPipb().getIdDocNum());
			}

			rptDTO.setFromCustDocNum(idx.toString());
			rptDTO.setFromCustEmailAddr(null);
			rptDTO.setFromCustFamilyName(pipbSrvDtl.getPipb().getLastName());
			rptDTO.setFromCustGivenName(pipbSrvDtl.getPipb().getFirstName());
			rptDTO.setFromCustHkidStrikeThrough(LtsBackendConstant.DOC_TYPE_HKID.equals(pipbSrvDtl.getPipb().getIdDocType()) ? null : "________");
			rptDTO.setFromCustMrStrikeThrough(StringUtils.equalsIgnoreCase("MR", pipbSrvDtl.getPipb().getTitle()) ? null : "___");
			rptDTO.setFromCustMsStrikeThrough(StringUtils.equalsIgnoreCase("MS", pipbSrvDtl.getPipb().getTitle()) ? null : "___");
			if (StringUtils.equalsIgnoreCase("MR", pipbSrvDtl.getPipb().getTitle()) 
					|| StringUtils.equalsIgnoreCase("MS", pipbSrvDtl.getPipb().getTitle())) {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setFromCustOthersStrikeThrough("__________");
				}
				else {
					rptDTO.setFromCustOthersStrikeThrough("_____");
				}
			}
			else {
				rptDTO.setFromCustOthersName(pipbSrvDtl.getPipb().getTitle());
			}
			if (LtsBackendConstant.DOC_TYPE_PASSPORT.equals(pipbSrvDtl.getPipb().getIdDocType())) {
				rptDTO.setFromCustPassStrikeThrough(null);
			}
			else {
				if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
					rptDTO.setFromCustPassStrikeThrough("____");
				}
				else {
					rptDTO.setFromCustPassStrikeThrough("________");
				}
			}
			if (pIsPrintSignature) {
				rptDTO.setFromCustSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_FROM));
				rptDTO.setFromSignDate(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"));
			}
			rptDTO.setFromCustVerifiedByStaff("X");
			
		}
		
		
//		rptDTO.setMobIDDChkbox(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getMobIddInd()) ? "X" : null);
		//rptDTO.setMobileIDDSrv(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDetail.getMobIddInd())); // TBC
		rptDTO.setNewSrvChkBox("X");
		rptDTO.setSalesmanCode(sbOrder.getSalesCd());
		rptDTO.setSalesStaffName(sbOrder.getSalesName());
		rptDTO.setSrvNum(coreService.getSrvNum());
//		rptDTO.setSrvChkBox(StringUtils.equalsIgnoreCase("Y", recontractDetail.getOptOut()) ? "X" : null);
		rptDTO.setStaffContactDetails(sbOrder.getSalesContactNum());
		rptDTO.setStaffNo(sbOrder.getStaffNum());
		rptDTO.setSvcChkBox("X");
		rptDTO.setTeamCode(sbOrder.getSalesTeam());
		rptDTO.setSrvChkBox(StringUtils.equals("Y", newCustomer.getHktOptOut()) ? "X" : null);
		rptDTO.setTheClubHktChkBox(StringUtils.equals("Y", newCustomer.getClubOptOut()) ? "X" : null);
		rptDTO.setToCustBillingAddr(coreService.getAccount().getBillingAddress().getFullBillAddr());
		if (LtsBackendConstant.DOC_TYPE_HKBR.equals(newCustomer.getIdDocType())) {
			rptDTO.setToCustBrStrikeThrough(null);
			}
			else {
				if ((StringUtils.equals(LtsBackendConstant.LOCALE_ENG, pLocale))) {
					rptDTO.setToCustBrStrikeThrough("_________________");
				}
				else {
					rptDTO.setToCustBrStrikeThrough("___________");
				}
			}
		rptDTO.setToCustCompanyName(newCustomer.getCompanyName());
//		rptDTO.setToCustContactNum(recontractDetail.getContactNum());
		StringBuilder idx = new StringBuilder();
		if(!internalUse){
			if (LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) && LtsBackendConstant.DOC_TYPE_HKID.equals(newCustomer.getIdDocType())) {
				idx.append(StringUtils.substring(newCustomer.getIdDocNum(), 0, 4));
				idx.append("XXX(X)");
			}
			
			else if (LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) && LtsBackendConstant.DOC_TYPE_PASSPORT.equals(newCustomer.getIdDocType())) {
				idx.append(StringUtils.substring(newCustomer.getIdDocNum(), 0, 4));
				idx.append("XXXXX");
			}
		}
		else {
			idx.append(newCustomer.getIdDocNum());
		}
		rptDTO.setToCustDocNum(idx.toString());
		rptDTO.setToCustEmailAddr(newCustomer.getTheClubLogin());
		rptDTO.setToCustEmailAddr2(newCustomer.getTheClubLogin());
		rptDTO.setToCustFamilyName(newCustomer.getLastName());
		rptDTO.setToCustGivenName(newCustomer.getFirstName());
		rptDTO.setToCustHkidStrikeThrough(LtsBackendConstant.DOC_TYPE_HKID.equals(newCustomer.getIdDocType()) ? null : "________");
		rptDTO.setToCustMobileNum(newCustomer.getTheClubMobile());
		rptDTO.setToCustMrStrikeThrough(StringUtils.equalsIgnoreCase("MR", newCustomer.getTitle()) ? null : "___");
		rptDTO.setToCustMsStrikeThrough(StringUtils.equalsIgnoreCase("MS", newCustomer.getTitle()) ? null : "___");
		if (StringUtils.equalsIgnoreCase("MR", newCustomer.getTitle()) 
				|| StringUtils.equalsIgnoreCase("MS", newCustomer.getTitle())) {
			if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
				rptDTO.setToCustOthersStrikeThrough("_________");
			}
			else {
				rptDTO.setToCustOthersStrikeThrough("____");
			}
				
		}
		else {
			rptDTO.setToCustOthersName(newCustomer.getTitle());
		}
		if (LtsBackendConstant.DOC_TYPE_PASSPORT.equals(newCustomer.getIdDocType())) {
			rptDTO.setToCustPassStrikeThrough(null);
		}
		else {
			if (StringUtils.equals(LtsBackendConstant.LOCALE_CHI, pLocale)) {
				rptDTO.setToCustPassStrikeThrough("____");
			}
			else {
				rptDTO.setToCustPassStrikeThrough("________");
			}
		}
		
		if (pIsPrintSignature) {
			rptDTO.setToCustSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_TO));
			rptDTO.setToSignDate(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"));
		}
		rptDTO.setToCustVerifiedByStaff("X"); //TBC
	}
	
	
	public CodeLkupCacheService getRelationshipCodeLkupCacheService() {
		return relationshipCodeLkupCacheService;
	}

	public void setRelationshipCodeLkupCacheService(
			CodeLkupCacheService relationshipCodeLkupCacheService) {
		this.relationshipCodeLkupCacheService = relationshipCodeLkupCacheService;
	}

	public CodeLkupCacheService getRelationshipBrCodeLkupCacheService() {
		return relationshipBrCodeLkupCacheService;
	}

	public void setRelationshipBrCodeLkupCacheService(
			CodeLkupCacheService relationshipBrCodeLkupCacheService) {
		this.relationshipBrCodeLkupCacheService = relationshipBrCodeLkupCacheService;
	}

}
