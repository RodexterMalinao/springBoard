package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;
import com.bomwebportal.lts.dto.order.RecontractLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.OfferService;
import com.bomwebportal.lts.util.LtsActvBackendConstant;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;
import com.pccw.rpt.dao.WItemRptTemplateVDAO;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;
import com.pccw.wq.service.WorkQueueService;

public class WorkQueueRemarkFactory {

	private static final int WQ_RMK_LENGTH = 4000;
	
	private OfferService offerService = null;
	private LtsOrderDocumentService ltsOrderDocumentService = null;
	private CallPlanLtsService callPlanLtsService;
	private CodeLkupCacheService waiveDFormReasonLkupCacheService;
	private CodeLkupCacheService delDisconnectReasonLkupCacheService;
	private CodeLkupCacheService eyeDisconnectReasonLkupCacheService;
	private CodeLkupCacheService optOutReasonLkupCacheService;
	private CodeLkupCacheService recontractSrvHandleCacheService;
	private CodeLkupCacheService updImsWgProfileBillInfoLkupCacheService;
	private WorkQueueService workQueueService;
	private final Log logger = LogFactory.getLog(getClass());
	
	public String[] generateWqRemark(SbOrderDTO pSbOrder, ServiceDetailDTO pSrvDtl, String pNatureId, String pWqSubType) {
		logger.warn("WorkQueueRemarkFactory.generateWqRemark, ordedId=" + pSbOrder.getOrderId() + ", natureId=" + pNatureId);
		
		String wqRemark = null;

		if (StringUtils.equals("35", pNatureId)) {
			wqRemark = this.generateEdfRemark((ServiceDetailOtherLtsDTO)pSrvDtl);
		} 
		// Approval Item
		else if (StringUtils.equals("110", pNatureId)) {
			if (pSrvDtl instanceof ServiceDetailLtsDTO) {
				wqRemark = this.generateWaviePenaltyLtsRemark((ServiceDetailLtsDTO)pSrvDtl, pSbOrder.getAppDate());
			} else if (pSrvDtl instanceof ServiceDetailOtherLtsDTO) {
				wqRemark = this.generateWaviePenaltyImsRemark((ServiceDetailOtherLtsDTO)pSrvDtl);
			}
		}else if (StringUtils.equals("135", pNatureId)
					|| StringUtils.equals("136", pNatureId)
					|| StringUtils.equals("137", pNatureId)){
			if (pSrvDtl instanceof ServiceDetailLtsDTO) {
				wqRemark = this.generateTerminateCallPlanWaviePenaltyLtsRemark((ServiceDetailLtsDTO)pSrvDtl, pSbOrder.getAppDate());
			}
		}
		else if (StringUtils.equals("43", pNatureId)) {
			wqRemark = generateFixedFeePlanRemark(pSbOrder, LtsBackendConstant.IO_IND_OUT, pWqSubType);
		}
		else if (StringUtils.equals("42", pNatureId)) {
			wqRemark = generateFixedFeePlanRemark(pSbOrder, LtsBackendConstant.IO_IND_INSTALL, pWqSubType);
		}
		else if (StringUtils.equals("69", pNatureId)) {
			wqRemark = generateFreeCallPlanRemark(pSbOrder);
		}
		else if (StringUtils.equals("51", pNatureId)) {
			wqRemark = generatePendingImsOrderRemark(pSbOrder);
		}
		else if (isApprovalNature(pNatureId)) {
			wqRemark = pSrvDtl.remarkToString(LtsBackendConstant.REMARK_TYPE_APPROVL_REMARK);
		}
		else if (StringUtils.equals(LtsActvBackendConstant.ACTV_WQ_NATURE_2DEL, pNatureId)) {
			if (pSrvDtl instanceof ServiceDetailLtsDTO) {
				wqRemark = this.generateCreatePortIn2DELRemark((ServiceDetailLtsDTO)pSrvDtl);
			}
		}
		else if (StringUtils.equals("289", pNatureId)){
			wqRemark = generateDirectSalesMismatchingDocumentInformationRemark(pSbOrder);
		}
		else if (StringUtils.equals("288", pNatureId)){
			wqRemark = generateDirectSalesDragonDowntimeRemark(pSbOrder);
		}
		else if (StringUtils.equals("287", pNatureId)){
			wqRemark = generateDirectSalesAwaitPrepaymentRemark(pSbOrder);
		}
		else if (StringUtils.equals("130", pNatureId)){
			wqRemark = generateDirectSalesWaiveQcRemark(pSbOrder);
		}
		//Modified by Max.R.Meng CHANGE DN WORK QUEUE
		else if(StringUtils.equals("283", pNatureId)){
			wqRemark = generateDnChangeSrv(pSbOrder);
		}
		/*Added by Felix Lee for recontract case*/
		else if (StringUtils.equals("29", pNatureId)) {
			wqRemark = this.generateRecontractRemark((ServiceDetailLtsDTO)pSrvDtl);
		}
		//Added by Johnathan Tse for update DN Profile
		else if (StringUtils.equals("1206", pNatureId)) {
			//TODO - get transferor and transferee info
			wqRemark = this.generateUpdateDnProfileRemark(pSbOrder, (ServiceDetailLtsDTO)pSrvDtl);
		}
		/*Added by Felix Lee for different customer case*/
		else if (StringUtils.equals("68", pNatureId)) {
			return this.generateApprovedDiffCustRemark(pSbOrder, pSrvDtl);
		}
		// Force Retain Offer remark
		else if (StringUtils.equals("70", pNatureId)) {
			wqRemark = generateForceRetainOfferRemark(pSrvDtl);
		}
		else if (StringUtils.equals("59", pNatureId)){
			wqRemark = generateChangeDcaRemarks(pSbOrder, pSrvDtl);
		}
		
		return chopString(wqRemark, WQ_RMK_LENGTH);
	}
	
	private boolean isApprovalNature(String pNatureId) {
		if (StringUtils.equals("101", pNatureId) 
				|| StringUtils.equals("104", pNatureId)
				|| StringUtils.equals("114", pNatureId)
				|| StringUtils.equals("117", pNatureId)
				|| StringUtils.equals("115", pNatureId)
				|| StringUtils.equals("113", pNatureId)
				|| StringUtils.equals("116", pNatureId)
				|| StringUtils.equals("124", pNatureId)
				|| StringUtils.equals("123", pNatureId)
				|| StringUtils.equals("125", pNatureId)
				|| StringUtils.equals("110", pNatureId)) {
			return true;
		}
		return false;
	}
	
	private String generateChangeDcaRemarks(SbOrderDTO pSbOrder, ServiceDetailDTO pSrvDtl){
		StringBuilder sbRemark = new StringBuilder();
		sbRemark.append("Customer Number: ");
		sbRemark.append(pSrvDtl.getProfileAccount().getCustomer().getCustNo());
		sbRemark.append("\nCustomer Name: ");
		sbRemark.append(pSrvDtl.getAccount().getCustomer().getLastName());
		sbRemark.append(" ");
		sbRemark.append(pSrvDtl.getAccount().getCustomer().getFirstName());
		sbRemark.append("\n");
		
		for(ServiceCallPlanDetailLtsDTO callPlanDtl : pSrvDtl.getSrvCallPlanDtls()){
			/*if("Y".equals(callPlanDtl.getChgDcaInd())){
				sbRemark.append("\n");
				sbRemark.append("\nCall Plan Code: ");
				sbRemark.append(callPlanDtl.getPlanCd());
				sbRemark.append("\nAction: Change DCA");
				sbRemark.append("\nFrom Existing Account No.: ");
				sbRemark.append(pSrvDtl.getProfileAccount().getAcctNo());
				sbRemark.append("\nTo New Account No: ");
				sbRemark.append(callPlanDtl.getNewDca());
			}*/
			appendFfpChgDcaRemark(callPlanDtl, sbRemark, pSrvDtl);
		}
		sbRemark.append("\n");
		sbRemark.append("\n");
		sbRemark.append("\nChannel: ");
		sbRemark.append(StringUtils.isEmpty(pSbOrder.getSalesChannel()) ? "" : pSbOrder.getSalesChannel());
		sbRemark.append("\nTeam Code: ");
		sbRemark.append(StringUtils.isEmpty(pSbOrder.getSalesTeam()) ? "" : pSbOrder.getSalesTeam());
		sbRemark.append("\nStaff Number: ");
		sbRemark.append(StringUtils.isEmpty(pSbOrder.getStaffNum()) ? "" : pSbOrder.getStaffNum());
		sbRemark.append("\n");

		return sbRemark.toString();
	}
	
	private void appendFfpChgDcaRemark(ServiceCallPlanDetailLtsDTO callPlanDtl, StringBuilder sbRemark, ServiceDetailDTO pSrvDtl){

		if("Y".equals(callPlanDtl.getChgDcaInd())){
			sbRemark.append("\n");
			sbRemark.append("Call Plan Code: ");
			sbRemark.append(callPlanDtl.getPlanCd());
			sbRemark.append("\nAction: Change DCA");
			sbRemark.append("\nFrom Existing Account No.: ");
			sbRemark.append(pSrvDtl.getProfileAccount().getAcctNo());
			sbRemark.append("\nTo New Account No: ");
			sbRemark.append(callPlanDtl.getNewDca());
			sbRemark.append("\n");
		}
	}
	
	private String generateForceRetainOfferRemark(ServiceDetailDTO pSrvDtl) {
		StringBuilder sb = new StringBuilder("Retain Profile Offer:");
		
		if (pSrvDtl instanceof ServiceDetailLtsDTO) {
			ItemDetailLtsDTO[] itemDetails = ((ServiceDetailLtsDTO) pSrvDtl).getItemDtls();
			if (ArrayUtils.isNotEmpty(itemDetails)) {
				for (ItemDetailLtsDTO itemDetail : itemDetails) {
					if (StringUtils.equals("Y", itemDetail.getForceRetain())
							&& LtsBackendConstant.IO_IND_OUT.equals(itemDetail.getIoInd())) {
						String[] offerCds = offerService.getItemOfferCodes(itemDetail.getItemId());
						if (ArrayUtils.isNotEmpty(offerCds)) {
							for (String offerCd : offerCds) {
								sb.append(" ").append(StringUtils.substring(offerCd, 2, 6));	
							}
						}
					}
				}
			}
			
		}
		return sb.toString();
	}
	
	private String[] generateApprovedDiffCustRemark(SbOrderDTO pSbOrder, ServiceDetailDTO pSrvDtl){
		logger.warn("WorkQueueRemarkFactory.generateApprovedDiffCustRemark, ordedId=" + pSbOrder.getOrderId());
		List<String> remarks = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();

		if (pSrvDtl instanceof ServiceDetailOtherLtsDTO) {
			return remarks.toArray(new String[remarks.size()]);
		}
		
		sb = new StringBuilder("Wrong Customer No: ").append(pSrvDtl.getAccount().getCustomer().getCustNo());
		sb.append("\nWrong Account No: ").append(pSrvDtl.getAccount().getAcctNo());
		sb.append("\nF&S action: create new customer profile & issue BOM order");
		remarks.add(sb.toString());
		
		/*Only generate below remarks for primary service WQ*/
		if(!pSrvDtl.getDtlId().equals(LtsSbHelper.getLtsService(pSbOrder).getDtlId())){
			return remarks.toArray(new String[remarks.size()]);
		}
		
		/*add transfer prepayment remarks*/
		if(pSbOrder.getPrepay() != null){
			for(PrepayLtsDTO prepayment: pSbOrder.getPrepay()){
				if(StringUtils.isNotBlank(prepayment.getPrepayDate())){
					sb = new StringBuilder("Alert: Transfer Prepayment to New Customer");
					sb.append("\nWrong Account No: ").append(prepayment.getAcctNo());
					sb.append("\nPrepayment Amount: ").append(prepayment.getPrepayAmt());
					remarks.add(sb.toString());
					break;
				}
			}
		}
		
		/*add FFP remarks*/
//		if (!ArrayUtils.isEmpty(ltsSrv.getSrvCallPlanDtls())) {
//			String planCdDesc = null;
//			String planContractPeriod = null;
//			for (ServiceCallPlanDetailLtsDTO callPlanDtl : ltsSrv.getSrvCallPlanDtls()) {
//				if (LtsBackendConstant.IO_IND_INSTALL.equals(callPlanDtl.getIoInd())) {
//					planCdDesc = planCdDesc == null ? callPlanDtl.getPlanCd() : planCdDesc + ", " + callPlanDtl.getPlanCd();
//					if(planContractPeriod == null){
//						planContractPeriod = offerService.getItemContractPeriod(callPlanDtl.getItemId());
//					}
//				}
//			}
//			if(planCdDesc != null){
//				sb = new StringBuilder("Alert: Add FFP to New Customer ");
//				sb.append("\nFFP In ").append(planCdDesc);
//				sb.append("\nNew FFP Contract Month: ").append(planContractPeriod);
//				remarks.add(sb.toString());
//			}
//		}
		
		/*add PDPO remarks*/
		CustomerDetailLtsDTO cust = pSrvDtl.getAccount().getCustomer();
		CustOptOutInfoLtsDTO optOutInfo = cust.getCustOptOutInfo();
		if(optOutInfo != null){
			sb = new StringBuilder("Alert: Add PDPO in New Customer ");
			if(StringUtils.equals("Y", optOutInfo.getEmail())){ //OPT OUT ALL
				sb.append("\nLTS status: ").append("OPT OUT ALL");
				sb.append("\nIDD status: ").append("OPT OUT ALL");
			}else{
				sb.append("\nLTS status: ").append("OPT IN ALL");
				sb.append("\nIDD status: ").append("OPT IN ALL");
			}
			remarks.add(sb.toString());
		}
		
		/* add CSP remarks */
		String[] cspRegInfo = getTheClubMyHktOrderRegInfo((ServiceDetailLtsDTO)pSrvDtl); // {target acc, reg email, reg mobile}
		if(cspRegInfo != null && cspRegInfo.length == 3){
			sb = new StringBuilder("Alert: Add The Club & My HKT in New Customer ");
			sb.append("\n\nThe Club & My HKT Email Address: ");
			sb.append("\n").append(cspRegInfo[1]);
			sb.append("\n\nThe Club & My HKT Mobile Number: ");
			sb.append("\n").append(cspRegInfo[2]);
			sb.append("\n\nLanguage: ").append(pSbOrder.getLangPref());
			if(StringUtils.equals("Y", cust.getClubOptOut())){
				sb.append("\n\nDirect Marketing Information: ").append("Opt Out");
				if(StringUtils.isNotBlank(cust.getClubOptRea())){
					String reasonDesc = (String) optOutReasonLkupCacheService.get(cust.getClubOptRea());
					sb.append("\n\nThe Club Opt-out Reason: ").append(reasonDesc);
				}
				if(StringUtils.isNotBlank(cust.getClubOptRmk())){
					sb.append("\n\nThe Club Opt-out Remarks: ").append(cust.getClubOptRmk());
				}
			}else{
				sb.append("\n\nDirect Marketing Information: ").append("Opt In");
			}
			remarks.add(sb.toString());
		}
		
		logger.warn("WorkQueueRemarkFactory.generateApprovedDiffCustRemark, remarks size = " + remarks.size());
		
		if(remarks.size() > 0){
			return remarks.toArray(new String[remarks.size()]);
		}
		
		return null;
	}
	
	private String generateRecontractRemark(ServiceDetailLtsDTO pLtsSrvDtl){
		
		if(! "Y".equals(pLtsSrvDtl.getRecontractInd()) || pLtsSrvDtl.getRecontract() == null) {
			return null;
		}
			
		String callingCardInd = (String)recontractSrvHandleCacheService.get(pLtsSrvDtl.getRecontract().getCallingCardInd());
		String mobIddInd = (String)recontractSrvHandleCacheService.get(pLtsSrvDtl.getRecontract().getMobIddInd());
		String fixedIddInd = (String)recontractSrvHandleCacheService.get(pLtsSrvDtl.getRecontract().getFixedIddInd());
		StringBuilder sbRemark1 = new StringBuilder("ALERT: Recontract Details\n");
		StringBuilder sbRemark2 = new StringBuilder();
		if(StringUtils.isNotBlank(callingCardInd)){
			sbRemark2.append("Global Calling Card Service: \t" + callingCardInd + "\n");
		}
		if(StringUtils.isNotBlank(mobIddInd)){// || StringUtils.isNotBlank(fixedIddInd)){
			sbRemark2.append("Mobile IDD0060 Service: \t" + mobIddInd + "\n");
		}
		if(StringUtils.isNotBlank(fixedIddInd)){
			sbRemark2.append("IDD0060 Fixed Fee Service: \t" + fixedIddInd + "\n");
		}
		

		AccountDetailLtsDTO profileAcct = pLtsSrvDtl.getProfileAccount();
		
		if (StringUtils.isNotEmpty(pLtsSrvDtl.getDeceaseType())
				&& profileAcct != null
				&& profileAcct.getBillingAddress() != null
				&& LtsBackendConstant.BILLING_ADDR_ACTION_NEW.equals(profileAcct.getBillingAddress().getAction())) {
			
			sbRemark2.append("\n");
			sbRemark2.append("\n******");
			sbRemark2.append("\nUpdate old customer account billing name & address & L001/L002:");
			sbRemark2.append("\nA/C: ").append(profileAcct.getAcctNo());
			sbRemark2.append("\nBilling Name: ").append(profileAcct.getAcctName());
			sbRemark2.append("\nBilling Address:");
			if (StringUtils.isNotBlank(profileAcct.getBillingAddress().getAddrLine1())) {
				sbRemark2.append("\n").append(profileAcct.getBillingAddress().getAddrLine1());	
			}
			if (StringUtils.isNotBlank(profileAcct.getBillingAddress().getAddrLine2())) {
				sbRemark2.append("\n").append(profileAcct.getBillingAddress().getAddrLine2());	
			}
			if (StringUtils.isNotBlank(profileAcct.getBillingAddress().getAddrLine3())) {
				sbRemark2.append("\n").append(profileAcct.getBillingAddress().getAddrLine3());	
			}
			if (StringUtils.isNotBlank(profileAcct.getBillingAddress().getAddrLine4())) {
				sbRemark2.append("\n").append(profileAcct.getBillingAddress().getAddrLine4());	
			}
			if (StringUtils.isNotBlank(profileAcct.getBillingAddress().getAddrLine5())) {
				sbRemark2.append("\n").append(profileAcct.getBillingAddress().getAddrLine5());	
			}
			if (StringUtils.isNotBlank(profileAcct.getBillingAddress().getAddrLine6())) {
				sbRemark2.append("\n").append(profileAcct.getBillingAddress().getAddrLine6());	
			}
			sbRemark2.append("\n******");
		}
		
		if(sbRemark2.length() > 0) {
			return sbRemark1.append(sbRemark2.toString()).toString();
		}
		
		return sbRemark1.toString();
	}
	
	private String generateUpdateDnProfileRemark(SbOrderDTO pSbOrder, ServiceDetailLtsDTO pLtsSrvDtl) {
		
		if(! "Y".equals(pLtsSrvDtl.getRecontractInd()) || pLtsSrvDtl.getRecontract() == null) {
			return null;
		}
		
		StringBuilder sbRemark = new StringBuilder("");
		String callingCardInd = (String)recontractSrvHandleCacheService.get(pLtsSrvDtl.getRecontract().getCallingCardInd());
		String mobIddInd = (String)recontractSrvHandleCacheService.get(pLtsSrvDtl.getRecontract().getMobIddInd());
		String fixedIddInd = (String)recontractSrvHandleCacheService.get(pLtsSrvDtl.getRecontract().getFixedIddInd());

		if (StringUtils.equalsIgnoreCase("M", pLtsSrvDtl.getUpdateDnProfile())) {
			sbRemark.append("***********************");
			sbRemark.append("\nALERT: Customer name IS exactly matched");
			sbRemark.append("\n***********************\n");
		}
		
		else if (StringUtils.equalsIgnoreCase("N", pLtsSrvDtl.getUpdateDnProfile())) {
			sbRemark.append("***********************");
			sbRemark.append("\nALERT: Customer name IS NOT matched");
			sbRemark.append("\n***********************\n");
		}
		
		if (pLtsSrvDtl.getUpdateDnProfile() != null) {
			
			CustomerDetailLtsDTO oldCust = LtsSbHelper.getLtsService(pSbOrder).getProfileAccount().getCustomer();
			RecontractLtsDTO newCust = pLtsSrvDtl.getRecontract();
			String num = StringUtils.substring(LtsSbHelper.getLtsService(pSbOrder).getSrvNum(), 4);
			sbRemark.append("\nService No: ").append(num);
			sbRemark.append("\nTransferor info: ").append(oldCust.getTitle()).append(" ").append(oldCust.getLastName());
			sbRemark.append(" ").append(oldCust.getFirstName());
			sbRemark.append("\nTransferee info: ").append(newCust.getTitle()).append(" ").append(newCust.getCustLastName());
			sbRemark.append(" ").append(newCust.getCustFirstName());
			sbRemark.append("\n");
			
			if(StringUtils.isNotBlank(callingCardInd)){
				sbRemark.append("Global Calling Card Service: \t" + callingCardInd + "\n");
			}
			if(StringUtils.isNotBlank(mobIddInd)){
				sbRemark.append("Mobile IDD0060 Service: \t" + mobIddInd + "\n");
			}
			if(StringUtils.isNotBlank(fixedIddInd)){
				sbRemark.append("IDD0060 Fixed Fee Service: \t" + fixedIddInd + "\n");
			}
			
		}
		
		return sbRemark.toString();
	}
	
	private String generateEdfRemark(ServiceDetailOtherLtsDTO pSrvDtlIms) {
		
		if (StringUtils.isNotBlank(pSrvDtlIms.getEdfRef())) {
			return "EDF No.: " + pSrvDtlIms.getEdfRef();
		}
		return null;
	}
	
	private String generateTerminateCallPlanWaviePenaltyLtsRemark(ServiceDetailLtsDTO pLtsSrvDtl, String pAppDate) {
		
		StringBuilder sbRemark = new StringBuilder("Request waive IDD Fixed Fee penalty / charge:\n");
		ServiceCallPlanDetailLtsDTO[] srvCallPlans = pLtsSrvDtl.getSrvCallPlanDtls();
		
		for(ServiceCallPlanDetailLtsDTO srvCallPlan: srvCallPlans){
			if(LtsBackendConstant.CALL_PLAN_WAIVE_PEN_MKT_APPV.equals(srvCallPlan.getPenaltyHandling())
					|| LtsBackendConstant.CALL_PLAN_WAIVE_PEN_SM_APPV.equals(srvCallPlan.getPenaltyHandling())
					|| LtsBackendConstant.CALL_PLAN_WAIVE_PEN_UM_APPV.equals(srvCallPlan.getPenaltyHandling())){
				sbRemark.append("\nPlan Code: ");
				sbRemark.append(srvCallPlan.getPlanCd());
				if(srvCallPlan.getContractStartDate() != null){
					sbRemark.append("\nContract Start Date: ");
					sbRemark.append(LtsDateFormatHelper.reformatDate(srvCallPlan.getContractStartDate().substring(0, 8), "yyyyMMdd", "dd/MM/yyyy"));
				}
				if(srvCallPlan.getContractEndDate() != null){
					sbRemark.append("\nContract End Date: ");
					sbRemark.append(LtsDateFormatHelper.reformatDate(srvCallPlan.getContractEndDate().substring(0, 8), "yyyyMMdd", "dd/MM/yyyy"));
				}
				sbRemark.append("\n");
			}
		}

		sbRemark.append("\n");
		sbRemark.append(pLtsSrvDtl.remarkToString(LtsBackendConstant.REMARK_TYPE_APPROVL_REMARK));
		sbRemark.append("\n");
		return sbRemark.toString();
	}
	
	private String generateWaviePenaltyLtsRemark(ServiceDetailLtsDTO pLtsSrvDtl, String pAppDate) {
		
		StringBuilder sbRemark = new StringBuilder("Request waive LTS penalty / charge:\n");
		ItemDetailLtsDTO[] items = pLtsSrvDtl.getItemDtls();
		ItemDetailDTO itemDesc = null;
		
		for (int i=0; items!=null && i<items.length; ++i) {
			if (!StringUtils.equals(LtsBackendConstant.PENALTY_ACTION_AWAIT_APPROVAL, items[i].getPenaltyHandling())
					&& !StringUtils.equals(LtsBackendConstant.ER_CHARGE_AWAIT_APPROVAL, items[i].getPenaltyHandling())) {
				continue;
			}
			itemDesc = this.offerService.getTpVasItemDetail(items[i].getItemId(), LtsBackendConstant.LOCALE_ENG);
			
			if (itemDesc != null) {
				sbRemark.append(LtsSbHelper.removeTag(itemDesc.getItemDisplayHtml()));
			} else {
				String desc = getItemDesc(items[i].getItemId(), pAppDate);
				
				if (StringUtils.isNotBlank(desc)) {
					sbRemark.append(desc);
				}
			}
			sbRemark.append("\n");
		}
		sbRemark.append(pLtsSrvDtl.remarkToString(LtsBackendConstant.REMARK_TYPE_APPROVL_REMARK));
		sbRemark.append("\n");
		return sbRemark.toString();
	}
	
	private String generateWaviePenaltyImsRemark(ServiceDetailOtherLtsDTO pSrvImsDtl) {
		
		StringBuilder sbRemark = new StringBuilder("Request waive IMS penalty / charge:\n");
		ImsOfferDetailDTO[] offers = pSrvImsDtl.getOfferDtls();
		ItemDetailDTO itemDesc = null;
		
		for (int i=0; offers!=null && i<offers.length; ++i) {
			if (!StringUtils.equals(LtsBackendConstant.ER_CHARGE_AWAIT_APPROVAL, offers[i].getPenaltyHandling())) {
				continue;
			}
			itemDesc = this.offerService.getTpVasItemDetail(offers[i].getItemId(), LtsBackendConstant.LOCALE_ENG);

			if (itemDesc == null) {
				continue;
			}
			sbRemark.append(LtsSbHelper.removeTag(itemDesc.getItemDisplayHtml()));
			sbRemark.append("\n");
		}
		return sbRemark.toString();
	}
	
	private String getItemDesc(String pItemId, String pAppDate){
		
		try {
			StringBuilder additionWhere = new StringBuilder();
			WItemRptTemplateVDAO criteria = SpringApplicationContext.getBean(WItemRptTemplateVDAO.class);
			criteria.setSearchKey("displayType", "ITEM_DESC");
			criteria.setSearchKey("locale", "en");
			additionWhere.append(" TO_DATE( :appDate , 'DD/MM/YYYY') BETWEEN PRC_EFF_START_DATE AND PRC_EFF_END_DATE");
			criteria.setExtraBind("appDate", LtsDateFormatHelper.getDateFromDTOFormat(pAppDate));
			criteria.setSearchKey("itemId", pItemId);
			criteria.setAdditionWhere(additionWhere.toString());
			WItemRptTemplateVDAO[] itemDescs = (WItemRptTemplateVDAO[])criteria.doSelect(null, null);
			
			if (ArrayUtils.isNotEmpty(itemDescs)) {
				return LtsSbHelper.removeTag(itemDescs[0].getHtml());
			}
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
		return null;
	}
	
	private static String[] chopString(String pString, int pLength) {
		
		if (StringUtils.isBlank(pString)) {
			return null;
		}
		List<String> remarkList = new ArrayList<String>();
		int count = 0;
		
		while (pString.length() > (count+1)*pLength) {
			remarkList.add(pString.substring(count*pLength, (count+1)*pLength));
			count++;
		}
		remarkList.add(pString.substring(count));
		return remarkList.toArray(new String[remarkList.size()]);
	}

	public String[] generateFullWqRemark(SbOrderDTO sbOrder) {
		List<String> fullWqRemarkList = new ArrayList<String>(); 
		
		String[] rentalRouterOfferChgRmk = generateRentalRouterOfferChgRmk(sbOrder);
		if (ArrayUtils.isNotEmpty(rentalRouterOfferChgRmk)) {
			fullWqRemarkList.addAll(Lists.newArrayList(rentalRouterOfferChgRmk));
		}
		
		//BOM2019014
		String[] redemRmk = generateDeviceRedemptionRmk(sbOrder);
		if (ArrayUtils.isNotEmpty(redemRmk)) {
			fullWqRemarkList.addAll(Lists.newArrayList(redemRmk));
		}

		//BOM2018061
		String[] epdRmk = generateEpdRmk(sbOrder);
		if (ArrayUtils.isNotEmpty(epdRmk)) {
			fullWqRemarkList.addAll(Lists.newArrayList(epdRmk));
		}
		
		//BOM2018088
		String[] imsL2JobRmk = generateL2JobRmk(sbOrder);
		if (ArrayUtils.isNotEmpty(imsL2JobRmk)) {
			fullWqRemarkList.addAll(Lists.newArrayList(imsL2JobRmk));
		}
		
		if (fullWqRemarkList.isEmpty()) {
			return null;
		}
		return fullWqRemarkList.toArray(new String[fullWqRemarkList.size()]);
	}

	//BOM2019014
	public String[] generateDeviceRedemptionRmk(SbOrderDTO sbOrder) {
		StringBuilder sbRemark = new StringBuilder();
		if(!LtsBackendConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())){
			return null;
		}
		
		boolean selfPickup = false;
		ServiceDetailLtsDTO coreLtsService = LtsSbHelper.getLtsService(sbOrder);
		if (coreLtsService == null || ArrayUtils.isNotEmpty(coreLtsService.getItemDtls())) {
			for (ItemDetailLtsDTO itemDetail : coreLtsService.getItemDtls()) {
				if(LtsBackendConstant.ITEM_TYPE_SELF_PICKUP.equals(itemDetail.getItemType())){
					selfPickup = true;
					break;
				}
			}
		}
		
		sbRemark.append("***********************");
		sbRemark.append("\n\nRedemption Option: " + (selfPickup ? "Self Pickup" : "Field Service"));
		if(selfPickup){
			sbRemark.append("\n\nLTS Order Handling");
			sbRemark.append("\n\n> Change Field Service Indicator from Y to N");
			sbRemark.append("\n\n> Delete Field Service Workgroup");
			sbRemark.append("\n\n> Change SIP as Last Workgroup");
			sbRemark.append("\n\nIMS Order Handling");
			sbRemark.append("\n\n> Change TID Indicator from N to Y at Appointment page");
		}
		sbRemark.append("\n\n***********************");
		return chopString(sbRemark.toString(), WQ_RMK_LENGTH);
		
	}
	
	//2018088
	public String[] generateL2JobRmk(SbOrderDTO sbOrder) {
		ServiceDetailOtherLtsDTO imsSrv = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());
		
		if(imsSrv == null){
			return null;
		}
		
		if(imsSrv.getImsL2Jobs() != null && imsSrv.getImsL2Jobs().length > 0){
			StringBuilder sbRemark = new StringBuilder();
			sbRemark.append("***********************");
			sbRemark.append("\n\nAdd L2 job (refer below internal use for details)");
			sbRemark.append("\n\n***********************");
			return chopString(sbRemark.toString(), WQ_RMK_LENGTH);
		}

		return null;
	}

	//BOM2018061
	public String[] generateEpdRmk(SbOrderDTO sbOrder) {
		ServiceDetailLtsDTO ltsSrv = LtsSbHelper.getLtsEyeService(sbOrder);
		
		if(ltsSrv == null){
			return null;
		}
		
		ItemDetailLtsDTO epdItem = LtsSbHelper.getSelectedEpdItem(ltsSrv);
		
		if(epdItem == null){
			return null;
		}
		
		String epdDesc = offerService.getItemDisplayDesc(epdItem.getItemId(), LtsBackendConstant.LOCALE_ENG);
		String[] epdOfferCds = offerService.getItemOfferCodes(epdItem.getItemId());
		ItemAttbDTO[] epdItemAttbs = offerService.getItemAttb(epdItem.getItemId());
		
		StringBuilder sbRemark = new StringBuilder();
		sbRemark.append("***********************");
		sbRemark.append("\n\nWEEE OPTION");
		sbRemark.append("\n" + epdDesc + ": ");
		if(epdOfferCds != null && epdOfferCds.length > 0){
			String psefCd = StringUtils.substring(epdOfferCds[0], 2, 6);
			sbRemark.append(psefCd + " / " + LtsBackendConstant.WEEE_PSEF_2ND_CD_MAP.get(psefCd));
		}
		if(epdItem.getItemAttbs() != null && epdItem.getItemAttbs().length > 0
				&& epdItemAttbs != null && epdItemAttbs.length > 0){
			for(ItemAttributeDetailLtsDTO epdAttbDtl : epdItem.getItemAttbs() ){
				for(ItemAttbDTO epdAttb : epdItemAttbs ){
					if(StringUtils.equals(epdAttbDtl.getAttbCd(), epdAttb.getAttbID())
							&& StringUtils.isNotEmpty(epdAttbDtl.getAttbValue())){
						sbRemark.append("\n" + epdAttb.getAttbDesc() + ": " + epdAttbDtl.getAttbValue());
						break;
					}
				}
			}
		}
		
		sbRemark.append("\n\n");
		sbRemark.append("***********************");
		return chopString(sbRemark.toString(), WQ_RMK_LENGTH);
		
	}
	
	public String[] generateRentalRouterOfferChgRmk(SbOrderDTO sbOrder) {
		ServiceDetailOtherLtsDTO imsService = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());
		if (imsService == null || !StringUtils.equals("Y", imsService.getShareRentalRouter())) {
			return null;
		}
		
		StringBuilder sbRemark = new StringBuilder();
		sbRemark.append("************");
		sbRemark.append("\n\n");
		sbRemark.append("SHARE PCD HN RENTAL ROUTER");
		
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
		
		if (ArrayUtils.isNotEmpty(ltsCoreService.getItemDtls())) {
			StringBuilder ltsInOfferRemark = new StringBuilder();
			for (ItemDetailLtsDTO itemDetail : ltsCoreService.getItemDtls()) {
				if (LtsBackendConstant.ITEM_TYPE_RENTAL_ROUTER.equals(itemDetail.getItemType())) {
					String[] offerCds = offerService.getItemOfferCodes(itemDetail.getItemId());
					if (ArrayUtils.isNotEmpty(offerCds)) {
						ltsInOfferRemark.append("'IN' LTS VAS Code:");
						for (String offerCd : offerCds) {
							ltsInOfferRemark.append("\n");
							ltsInOfferRemark.append(offerCd);
						}
					}
				}
			}
			if (ltsInOfferRemark.length() > 0) {
				sbRemark.append("\n\n");
				sbRemark.append(ltsInOfferRemark.toString());
			}
		}
		
		ImsOfferDetailDTO[] imsOffers = imsService.getOfferDtls();
		if (ArrayUtils.isNotEmpty(imsOffers)) {
			StringBuilder imsOutOfferRemark = new StringBuilder();
			StringBuilder imsInOfferRemark = new StringBuilder();
			
			for (ImsOfferDetailDTO imsOffer : imsOffers) {
				if (LtsBackendConstant.ITEM_TYPE_RENTAL_ROUTER.equals(imsOffer.getItemType())
						&& LtsBackendConstant.IO_IND_OUT.equals(imsOffer.getIoInd())) {
					String[] offerCds = offerService.getItemOfferCodes(imsOffer.getItemId());
					if (ArrayUtils.isNotEmpty(offerCds)) {
						if (imsOutOfferRemark.length() == 0) {
							imsOutOfferRemark.append("'OUT' IMS VAS Code:");
						}
						for (String offerCd : offerCds) {
							imsOutOfferRemark.append("\n");
							imsOutOfferRemark.append(offerCd);		
						}
					}
				}
				else if (LtsBackendConstant.ITEM_TYPE_RENTAL_ROUTER.equals(imsOffer.getItemType())
						&& LtsBackendConstant.IO_IND_INSTALL.equals(imsOffer.getIoInd())) {
					String[] offerCds = offerService.getItemOfferCodes(imsOffer.getItemId());
					if (ArrayUtils.isNotEmpty(offerCds)) {
						if (imsInOfferRemark.length() == 0) {
							imsInOfferRemark.append("'IN' IMS VAS Code:");
						}
						for (String offerCd : offerCds) {
							imsInOfferRemark.append("\n");
							imsInOfferRemark.append(offerCd);		
						}
					}
				}
			}
			if (imsOutOfferRemark.length() > 0) {
				sbRemark.append("\n\n");
				sbRemark.append(imsOutOfferRemark.toString());
			}
			if (imsInOfferRemark.length() > 0) {
				sbRemark.append("\n\n");
				sbRemark.append(imsInOfferRemark.toString());
			}
		}
		
		sbRemark.append("\n\n");
		sbRemark.append("************");
		return chopString(sbRemark.toString(), WQ_RMK_LENGTH);
	}
	
	public static String[] generateStandardWqRemark(SbOrderDTO pSbOrder) {
		
		StringBuilder sbRemark = new StringBuilder();
		sbRemark.append("Customer Name: ");
		
		ServiceDetailLtsDTO srvDtls = LtsSbHelper.getLtsService(pSbOrder);
		
		if (srvDtls.getRecontract() != null) {
			sbRemark.append(srvDtls.getRecontract().getCustLastName());
			sbRemark.append(" ");
			sbRemark.append(srvDtls.getRecontract().getCustFirstName());
		} else {
			sbRemark.append(srvDtls.getAccount().getCustomer().getLastName());
			sbRemark.append(" ");
			sbRemark.append(srvDtls.getAccount().getCustomer().getFirstName());
		}
		sbRemark.append("\nStaff No.: ");
		sbRemark.append(StringUtils.isEmpty(pSbOrder.getStaffNum()) ? "" : pSbOrder.getStaffNum());
		sbRemark.append("\nSalesman Code: ");
		sbRemark.append(StringUtils.isEmpty(pSbOrder.getSalesCd()) ? "" : pSbOrder.getSalesCd());
		sbRemark.append("\nSales Channel: ");
		sbRemark.append(StringUtils.isEmpty(pSbOrder.getSalesChannel()) ? "" : pSbOrder.getSalesChannel());
		sbRemark.append("\nTeam/ Shop Code: ");
		sbRemark.append(StringUtils.isEmpty(pSbOrder.getSalesTeam()) ? "" : pSbOrder.getSalesTeam());
		sbRemark.append("\nSales Contact No.: ");
		sbRemark.append(StringUtils.isEmpty(pSbOrder.getSalesContactNum()) ? "" : pSbOrder.getSalesContactNum());
		sbRemark.append("\nBOC: ");
		sbRemark.append(pSbOrder.getBoc());
		sbRemark.append("\n");
		return chopString(sbRemark.toString(), WQ_RMK_LENGTH);
	}
	
	public String[] generateFaxSerialRemark(SbOrderDTO pSbOrder) {
		
		AllOrdDocAssgnLtsDTO[] docs = pSbOrder.getAllOrdDocAssgns();
		Map<String, String> faxSerialMap = this.ltsOrderDocumentService.getFaxSerialMap(pSbOrder.getOrderId()); 
		StringBuilder faxSb = new StringBuilder();
		String faxSerial = null;
		
		for (int i=0; docs!=null && i<docs.length; ++i) {
			faxSerial = faxSerialMap.get(docs[i].getDocType());
			
			if (StringUtils.isBlank(faxSerial)) {
				continue;
			}
			faxSb.append(faxSerial);
			faxSb.append("\n");
		}
		if (faxSb.length() == 0) {
			return null;
		}
		faxSb.insert(0, "\nFax Serial(s):\n");
		return chopString(faxSb.toString(), WQ_RMK_LENGTH);
	}
	
	public String generateDirectSalesMismatchingDocumentInformationRemark(SbOrderDTO pSbOrder){
		StringBuilder remark = new StringBuilder();
		remark.append("Order ID: " + pSbOrder.getOrderId());
		remark.append("\nThe user input document information compared to the information stored in BOM" );
		return remark.toString(); 
	}
	
	public String generateDirectSalesDragonDowntimeRemark(SbOrderDTO pSbOrder){
		StringBuilder remark = new StringBuilder();
		remark.append("Order ID: " + pSbOrder.getOrderId());
		remark.append("\nPending due to dragon downtime..." );
		return remark.toString(); 
	}
	
	public String generateDirectSalesAwaitPrepaymentRemark(SbOrderDTO pSbOrder){
		StringBuilder remark = new StringBuilder();
		remark.append("Order ID: " + pSbOrder.getOrderId());
		remark.append("\nWaiting for the customer's prepayment..." );
		return remark.toString(); 
	}

	public String generateDirectSalesWaiveQcRemark(SbOrderDTO pSbOrder){
		StringBuilder remark = new StringBuilder();
		remark.append("Order ID: " + pSbOrder.getOrderId());
		remark.append("\nThe user input document information compared to the information stored in BOM" );
		return remark.toString(); 
	}
	
	//Modified by Max.R.Meng  
	public String generateDnChangeSrv(SbOrderDTO sbOrder){
		StringBuilder remark = new StringBuilder();

		ServiceDetailDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
		String serviceNumLabel = "Service No: ";

		if(ltsCoreService != null 
					&& StringUtils.isNotEmpty(ltsCoreService.getNewSrvNum())) {
			if ("A".equals(((ServiceDetailLtsDTO)ltsCoreService).getDuplexInd())
					&& LtsBackendConstant.LTS_SRV_TYPE_DEL.equals(((ServiceDetailLtsDTO)ltsCoreService).getToSrvType())) {
				serviceNumLabel = "DNX No: ";
			}	
			remark.append("Change DN Request");
			remark.append("\n");
			remark.append("Exist ").append(serviceNumLabel).append(ltsCoreService.getSrvNum());
			remark.append("\n");
			remark.append("New ").append(serviceNumLabel).append(ltsCoreService.getNewSrvNum());
			remark.append("\n\n");
		}
		
		ServiceDetailDTO duplexDnChangeService = LtsSbHelper.getDuplexChangeService(sbOrder.getSrvDtls());
		
		if(duplexDnChangeService != null
				&& StringUtils.isNotEmpty(duplexDnChangeService.getNewSrvNum())) {
			
			remark.append("Change DN Request");
			remark.append("\n");
			remark.append("Exist DNY No:").append(duplexDnChangeService.getSrvNum());
			remark.append("\n");
			remark.append("New DNY No:").append(duplexDnChangeService.getNewSrvNum());
			remark.append("\n\n");
		}	
		return remark.toString();
	}
	
	public String generatePendingImsOrderRemark(SbOrderDTO pSbOrder) {
		
		ServiceDetailDTO imsService = LtsSbHelper.getImsEyeService(pSbOrder.getSrvDtls());
		if (imsService == null) {
			return null;
		}
		
		StringBuilder remark = new StringBuilder();
		remark.append("Pending Order ID: ");
		
		if (StringUtils.isNotEmpty(imsService.getPendingOcid())) {
			remark.append("(BOM) ").append(imsService.getPendingOcid());
		}
		if (StringUtils.isNotEmpty(((ServiceDetailOtherLtsDTO)imsService).getRelatedSbOrderId())) {
			remark.append("(Springboard) ").append(((ServiceDetailOtherLtsDTO)imsService).getRelatedSbOrderId());
		}
		return remark.toString(); 
	}
	
	public String generateBackDateSrdRemark(SbOrderDTO pSbOrder) {
		
		StringBuilder remark = new StringBuilder();
		ServiceDetailDTO ltsCoreService = LtsSbHelper.getLtsService(pSbOrder);
		remark.append("Backdate SRD: ").append(LtsDateFormatHelper.getDateFromDTOFormat(ltsCoreService.getAppointmentDtl().getAppntStartTime()));
		return remark.toString();
	}
	
	public String generateFreeCallPlanRemark(SbOrderDTO pSbOrder) {
		
		ServiceDetailDTO ltsCoreService = LtsSbHelper.getLtsService(pSbOrder);
		if (ArrayUtils.isEmpty(ltsCoreService.getSrvCallPlanDtls())) {
			return null;
		}
		
		StringBuilder ffpRemark = new StringBuilder();
		ffpRemark.append("**********************\n");
		ffpRemark.append("F&S: pls add IDD free mins in account level\n");
		ffpRemark.append("**********************\n\n");
		
		appendGeneralFfpRemarks(ffpRemark, ltsCoreService);
		
		for (ServiceCallPlanDetailLtsDTO callPlanDtl : ltsCoreService.getSrvCallPlanDtls()) {
			String callPlanType = callPlanLtsService.getCallPlanType(callPlanDtl.getPlanCd());
			boolean isFreePlan = StringUtils.equalsIgnoreCase(LtsBackendConstant.CALL_PLAN_TYPE_FREE, callPlanType);
			boolean isDummyPlanCd = StringUtils.equalsIgnoreCase(LtsBackendConstant.CALL_PLAN_TYPE_DUMMY, callPlanType);
			if(!isFreePlan && !isDummyPlanCd){
				continue;
			}
			
			String callPlanContractPeriod = isDummyPlanCd? "0": offerService.getItemContractPeriod(callPlanDtl.getItemId());
			
			if (LtsBackendConstant.IO_IND_INSTALL.equals(callPlanDtl.getIoInd())) {
				
				AccountServiceAssignLtsDTO[] tempIAccounts = ltsCoreService.getAccounts();				
				String tempIAccountNo = "";
				if (ArrayUtils.isNotEmpty(tempIAccounts)) {
					for (AccountServiceAssignLtsDTO acctSrvAssgn : tempIAccounts) {
						if("I".equalsIgnoreCase(acctSrvAssgn.getChrgType()))
						{
							AccountDetailLtsDTO tempIAccount = acctSrvAssgn.getAccount();
							if(tempIAccount!=null)
							{
								tempIAccountNo = tempIAccount.getAcctNo();
							}							
						}
					}
				}
				
				ffpRemark.append("\n");
				ffpRemark.append("IDD Free Mins Plan In (Call Plan Code): ").append(callPlanDtl.getPlanCd());
				ffpRemark.append("\n");
				ffpRemark.append("New Free IDD Call Plan Contract Month: ").append(callPlanContractPeriod);
				ffpRemark.append("\n");
				ffpRemark.append("Effective Date = SRD or SRD + 1");
				ffpRemark.append("\n");
				ffpRemark.append("Termination Date = " + (isDummyPlanCd ? "Nil" :"Open End Date" ));
				ffpRemark.append("\n");
				if (StringUtils.isNotBlank(tempIAccountNo)) {
					ffpRemark.append("Account No: "+tempIAccountNo);
					ffpRemark.append("\n");	
				}
			}
		}

		ffpRemark.append("\nChannel: ");
		ffpRemark.append(StringUtils.isEmpty(pSbOrder.getSalesChannel()) ? "" : pSbOrder.getSalesChannel());
		ffpRemark.append("\nTeam Code: ");
		ffpRemark.append(StringUtils.isEmpty(pSbOrder.getSalesTeam()) ? "" : pSbOrder.getSalesTeam());
		ffpRemark.append("\nStaff Number: ");
		ffpRemark.append(StringUtils.isEmpty(pSbOrder.getStaffNum()) ? "" : pSbOrder.getStaffNum());
		ffpRemark.append("\n");

		return ffpRemark.toString();
	}
	
	public String generateFixedFeePlanRemark(SbOrderDTO pSbOrder, String pIoInd, String pWqSubType) {
		
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(pSbOrder);
		
		if (ArrayUtils.isEmpty(ltsCoreService.getSrvCallPlanDtls())) {
			return null;
		}
		
		StringBuilder ffpRemark = new StringBuilder();
		
		if (LtsBackendConstant.IO_IND_OUT.equals(pIoInd)
				&& StringUtils.isNotEmpty(ltsCoreService.getDeceaseType())){
			ffpRemark.append("******");
			ffpRemark.append("\nDeceased case: Amend termination date of below Call Plan(s)");
			ffpRemark.append("\n******\n\n");
		}
		
		if(pSbOrder.getLtsDsOrderInfo() == null 
				|| !LtsBackendConstant.NAME_MISMATCH_APR_CD_APPROVED_WITH_DIFF_CUST.equals(pSbOrder.getLtsDsOrderInfo().getNameMismatchStatus())){
			ffpRemark.append("Customer Number: ");
			if (ltsCoreService.getRecontract() != null) {
				ffpRemark.append(ltsCoreService.getRecontract().getCustNum());
			} else {
				ffpRemark.append(ltsCoreService.getAccount().getCustomer().getCustNo());
			}
		}
		
		appendGeneralFfpRemarks(ffpRemark, ltsCoreService);
		
		for (ServiceCallPlanDetailLtsDTO callPlanDtl : ltsCoreService.getSrvCallPlanDtls()) {
			String callPlanContractPeriod = offerService.getItemContractPeriod(callPlanDtl.getItemId());
			String callPlanType = callPlanLtsService.getCallPlanType(callPlanDtl.getPlanCd());
			String giftPlanContractPeriod = callPlanLtsService.getCallPlanContractPeriod(callPlanDtl.getPlanCd());
			boolean isGiftPlan = StringUtils.equalsIgnoreCase(LtsBackendConstant.CALL_PLAN_TYPE_GIFT, callPlanType);
			boolean isFreePlan = StringUtils.equalsIgnoreCase(LtsBackendConstant.CALL_PLAN_TYPE_FREE, callPlanType);
			if(isFreePlan){
				continue;
			}
			
			if (LtsBackendConstant.IO_IND_INSTALL.equals(pIoInd) && LtsBackendConstant.IO_IND_INSTALL.equals(callPlanDtl.getIoInd())) {
				AccountServiceAssignLtsDTO[] tempIAccounts = ltsCoreService.getAccounts();				
				String tempIAccountNo = "";
				if (ArrayUtils.isNotEmpty(tempIAccounts)) {
					for (AccountServiceAssignLtsDTO acctSrvAssgn : tempIAccounts) {
						if("I".equalsIgnoreCase(acctSrvAssgn.getChrgType()))
						{
							AccountDetailLtsDTO tempIAccount = acctSrvAssgn.getAccount();
							if(tempIAccount!=null)
							{
								tempIAccountNo = tempIAccount.getAcctNo();
							}							
						}
					}
				}
				
				ffpRemark.append("\n");
				ffpRemark.append("FFP In ").append(isGiftPlan ? "(Gift Plan Code): " : "(Call Plan Code): ").append(callPlanDtl.getPlanCd());
				ffpRemark.append("\n");
				ffpRemark.append("New FFP Contract Month: ").append(isGiftPlan ? giftPlanContractPeriod : callPlanContractPeriod);
				ffpRemark.append("\n");
				ffpRemark.append("Effective Date = SRD or SRD + 1");
				ffpRemark.append("\n");
				ffpRemark.append("Termination Date = " + (isGiftPlan ? "Effective Date + Contract Month" : "Nil"));
				ffpRemark.append("\n");
				if (StringUtils.isNotBlank(tempIAccountNo)) {
					ffpRemark.append("Account No: "+tempIAccountNo);
					ffpRemark.append("\n");	
				}
			}
			
			if (LtsBackendConstant.IO_IND_OUT.equals(pIoInd)){
				appendFfpRemoveRemarks(ffpRemark, isGiftPlan, callPlanDtl);
			}
			
		}
		
		if(LtsBackendConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(pSbOrder.getOrderSubType())
				&& LtsBackendConstant.WQ_TYPE_FULL.equals(pWqSubType)){
			boolean isBillMediaChanged = false;
			boolean isPaymentMethodChanged = false;
			boolean isBillingAddressChanged = false;
			
			AccountDetailLtsDTO ltsSrvAcct = LtsSbHelper.getLtsService(pSbOrder).getAccount();
			if(!StringUtils.equals(ltsSrvAcct.getBillMedia(), ltsSrvAcct.getExistBillMedia())){
				isBillMediaChanged = true;
			}
			
			if(!StringUtils.equals(ltsSrvAcct.getExistPayment().getPayMtdType(), ltsSrvAcct.getFuturePayment().getPayMtdType())){
				isPaymentMethodChanged = true;
			}
			
			if(LtsBackendConstant.BILLING_ADDR_ACTION_NEW.equals(ltsSrvAcct.getBillingAddress().getAction())){
				isBillingAddressChanged = true;
			}

			ffpRemark.append("\n");
			ffpRemark.append("********************");
			ffpRemark.append("\n");
			ffpRemark.append("ALERT: ");
			ffpRemark.append("\n");
			ffpRemark.append("Update Billing Address: " + (isBillingAddressChanged ? "Y" : "N"));
			ffpRemark.append("\n");
			ffpRemark.append("Update Bill Media: " + (isBillMediaChanged ? "Y" : "N"));
			ffpRemark.append("\n");
			ffpRemark.append("Update Payment Method: " + (isPaymentMethodChanged ? "Y" : "N"));
			ffpRemark.append("\n");
			ffpRemark.append("********************");
			
		}
		
		ffpRemark.append("\nChannel: ");
		ffpRemark.append(StringUtils.isEmpty(pSbOrder.getSalesChannel()) ? "" : pSbOrder.getSalesChannel());
		ffpRemark.append("\nTeam Code: ");
		ffpRemark.append(StringUtils.isEmpty(pSbOrder.getSalesTeam()) ? "" : pSbOrder.getSalesTeam());
		ffpRemark.append("\nStaff Number: ");
		ffpRemark.append(StringUtils.isEmpty(pSbOrder.getStaffNum()) ? "" : pSbOrder.getStaffNum());
		ffpRemark.append("\n");

		return ffpRemark.toString();
	}
	
	private void appendGeneralFfpRemarks(StringBuilder ffpRemark, ServiceDetailDTO ltsCoreService){
		
		ffpRemark.append("\nDocument Type/Number: ");
		if (ltsCoreService.getRecontract() != null) {
			ffpRemark.append(ltsCoreService.getRecontract().getIdDocType()).append("/").append(ltsCoreService.getRecontract().getIdDocNum());
		} else {
			ffpRemark.append(ltsCoreService.getAccount().getCustomer().getIdDocType()).append("/").append(ltsCoreService.getAccount().getCustomer().getIdDocNum());
		}
		
		ffpRemark.append("\nCustomer Name: ");
		if (ltsCoreService.getRecontract() != null) {
			ffpRemark.append(ltsCoreService.getRecontract().getCustLastName());
			ffpRemark.append(" ");
			ffpRemark.append(ltsCoreService.getRecontract().getCustFirstName());
		} else {
			ffpRemark.append(ltsCoreService.getAccount().getCustomer().getLastName());
			ffpRemark.append(" ");
			ffpRemark.append(ltsCoreService.getAccount().getCustomer().getFirstName());
		}
		ffpRemark.append("\n");
	}
	
	private void appendFfpRemoveRemarks(StringBuilder ffpRemark, boolean isGiftPlan, ServiceCallPlanDetailLtsDTO callPlanDtl){
		if (LtsBackendConstant.IO_IND_OUT.equals(callPlanDtl.getIoInd())) {
			ffpRemark.append("\n");
			ffpRemark.append("FFP Out ").append(isGiftPlan ? "(Gift Plan Code): " : "(Call Plan Code): ").append(callPlanDtl.getPlanCd());
			ffpRemark.append("\n");
			if(callPlanDtl.getEffEndDate() != null){
				ffpRemark.append("Termination Date = ").append(LtsDateFormatHelper.reformatDate(callPlanDtl.getEffEndDate().substring(0, 8), "yyyyMMdd", "dd/MM/yyyy"));
				ffpRemark.append("\n");
			}else{
				ffpRemark.append("Termination Date = --");
				ffpRemark.append("\n");
			}
			ffpRemark.append("Plan Holder: ").append(callPlanDtl.getPlanHolder());
			String penaltyHandlingDesc = getPenaltyHandlingDesc(callPlanDtl.getPenaltyHandling());
			if(StringUtils.isNotBlank(penaltyHandlingDesc)){
				ffpRemark.append("\n");
				ffpRemark.append("Penalty Handling: ");
				ffpRemark.append(penaltyHandlingDesc);
			}
			
			if(callPlanDtl.getEffStartDate() != null){
				LocalDate startDate = LocalDate.parse(callPlanDtl.getEffStartDate().substring(0, 8), DateTimeFormat.forPattern("yyyyMMdd"));
				if(LocalDate.now().isBefore(startDate)){
					ffpRemark.append("\n");
					ffpRemark.append("Plan Not Yet Start, remove ASAP");
				}
			}
			
			ffpRemark.append("\n");
			
		}
	}
	
	public String[] generateDisconnectFullWqRemark(SbOrderDTO pSbOrder) {
		
		if (!LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(pSbOrder.getOrderType())) {
			return null;
		}
		
		ServiceDetailLtsDTO ltsService = LtsSbHelper.getLtsService(pSbOrder);
		ServiceDetailOtherLtsDTO imsService = LtsSbHelper.getImsEyeService(pSbOrder.getSrvDtls());
		ServiceDetailLtsDTO sipService = LtsSbHelper.getEyeSipService(pSbOrder.getSrvDtls());
		
		StringBuilder remark = new StringBuilder();
		
		//BOM2018105 Update Billing info to IMS WG Profile
		if (imsService != null
				&& LtsBackendConstant.ORDER_TYPE_DISCONNECT.equals(imsService.getOrderType())
				&& LtsBackendConstant.IMS_PRODUCT_TYPE_WALL_GARDEN.equals(imsService.getFromProd())) {
			List<String> fromProdList = new ArrayList<String>();
			try {
				LookupItemDTO[] lkups = updImsWgProfileBillInfoLkupCacheService.getCodeLkupDAO().getCodeLkup();
				if(lkups != null){
					for(LookupItemDTO lkup: lkups){
						fromProdList.add(lkup.getItemKey());
					}
				}
			} catch (Exception de) {
				logger.info("Exception at updImsWgProfileBillInfoLkupCacheService");
			}
			
			if(fromProdList.contains(ltsService.getFromProd())){
				remark.append("\n");
				remark.append("***********************");
				remark.append("\nALERT: UPDATE BILL MEDIA, BILLING ADDR / EMAIL TO IMS PROFILE");
				remark.append("\nBILL MEDIA: ");
				if(StringUtils.equals("P", ltsService.getAccount().getBillMedia())){
					remark.append("PAPER");
					remark.append("\nBILLING ADDR: \n");
					String[] addrLines = ltsService.getAccount().getBillingAddress().getAddrLines();
					for(int i = 0; addrLines != null && i < addrLines.length; i++){
						if(i < 1){
							remark.append(addrLines[i]);
						}else{
							remark.append("\n" + addrLines[i]);
						}
					}
					
				}else if(StringUtils.equals("S", ltsService.getAccount().getBillMedia())){
					remark.append("EMAIL");
					remark.append("\nEMAIL: " + ltsService.getAccount().getEmailAddr());
				}else{
					remark.append("-");
				}
				remark.append("\n***********************\n");
			}
		}
		
		if (imsService != null) {
			if (StringUtils.isNotEmpty(imsService.getRelatedSbOrderId())) {
				remark.append("IMS Pending Order (SBID): ").append(imsService.getRelatedSbOrderId());
				remark.append("\n");
			}
			if (StringUtils.isNotEmpty(imsService.getPendingOcid())) {
				remark.append("IMS Pending Order (OCID): ").append(imsService.getPendingOcid());
				remark.append("\n");
			}
			if (StringUtils.isNotEmpty(imsService.getRelatedSbOrderId())) {
				remark.append("IMS Pending Order SRD: ").append(imsService.getPendingOcidSrd());
				remark.append("\n");
			}
			remark.append("FSA: ").append(imsService.getSrvNum());
			remark.append("\n");
		}
		
		remark.append("TEL ").append(ltsService.getDatCd()).append(" No.: ").append(ltsService.getSrvNum());
		remark.append("\n");
		if (sipService != null) {
			remark.append("TEL ").append(sipService.getDatCd()).append(" No.: ").append(sipService.getSrvNum());	
			remark.append("\n");
		}
		
		CustomerDetailLtsDTO ltsCustomer = ltsService.getAccount().getCustomer();
		
		remark.append("Customer Name: ").append("BR".equals(ltsCustomer.getIdDocType()) ? ltsCustomer
						.getCompanyName() : ltsCustomer.getLastName() + " " + ltsCustomer.getFirstName());
		remark.append("\n");
		
		remark.append("Appn Date: ").append(pSbOrder.getAppDate());
		remark.append("\n");
		
		if (StringUtils.isNotEmpty(ltsService.getDFormSerial())) {
			remark.append("D-Form Serial: ").append(ltsService.getDFormSerial());
			remark.append("\n");	
		}

		if (StringUtils.isNotEmpty(ltsService.getWaiveDFormReasonCd())) {
			remark.append("Waive D-Form Reason: ").append((String) waiveDFormReasonLkupCacheService.get(ltsService.getWaiveDFormReasonCd()));
			remark.append("\n");
		}
		
		if (StringUtils.isNotEmpty(ltsService.getDiscReasonCode())) {
			remark.append("Disconnect Reason: ");
			if (LtsBackendConstant.LTS_PRODUCT_TYPE_DEL.equals(ltsService.getFromProd())) {
				remark.append((String)delDisconnectReasonLkupCacheService.get(ltsService.getDiscReasonCode()));
			}
			else {
				remark.append((String)eyeDisconnectReasonLkupCacheService.get(ltsService.getDiscReasonCode()));				
			}
			remark.append("\n");
		}
		
		if(ArrayUtils.isNotEmpty(ltsService.getItemDtls())) {
			remark.append("\n");
			remark.append("All TP codes (Penalty Handling): ");
			for (ItemDetailLtsDTO profileItem : ltsService.getItemDtls()) {
				String planCode = null;
				if (LtsBackendConstant.ITEM_TYPE_VAS.equals(profileItem.getProfileItemType())) {
					planCode = offerService.getVasPlanCode(profileItem.getItemId());	
				}
				if (LtsBackendConstant.ITEM_TYPE_SERVICE.equals(profileItem.getProfileItemType())) {
					planCode = offerService.getServicePlanCode(profileItem.getItemId());	
				}
				if (StringUtils.isEmpty(planCode)) {
					continue;
				}
				remark.append("\n");
				remark.append(planCode).append(" ").append(getPenaltyHandlingDesc(profileItem.getPenaltyHandling()));
			}
			remark.append("\n");
		}
		
		if (ArrayUtils.isNotEmpty(ltsService.getSrvCallPlanDtls())) {
			remark.append("\n");
			remark.append("Remove IDD FFP codes (Penalty Handling):  ");
			
			for (ServiceCallPlanDetailLtsDTO callPlanDtl : ltsService.getSrvCallPlanDtls()) {
				/*if (LtsBackendConstant.IO_IND_OUT.equals(callPlanDtl.getIoInd())) {
					remark.append("\n");
					remark.append(callPlanDtl.getPlanCd()).append(" ").append(getPenaltyHandlingDesc(callPlanDtl.getPenaltyHandling()));
					remark.append("\n");
					remark.append("Termination Date: ").append(StringUtils.equals("Y", pSbOrder.getLastServiceInd()) ? pSbOrder.getSrvReqDate() : "Next Month Effective Date");
					remark.append("\n");
					remark.append("Plan Holder: ").append(callPlanDtl.getPlanHolder());
				}*/
				

				String callPlanType = callPlanLtsService.getCallPlanType(callPlanDtl.getPlanCd());
				boolean isGiftPlan = StringUtils.equalsIgnoreCase(LtsBackendConstant.CALL_PLAN_TYPE_GIFT, callPlanType);
				appendFfpRemoveRemarks(remark, isGiftPlan, callPlanDtl);
				
			}
			remark.append("\n");
		}
		
		if (StringUtils.isNotEmpty(ltsService.getDiscCallingCardInd())) {
			remark.append("\n");
			remark.append("Calling Card: ").append(StringUtils.equals("Y", ltsService.getDiscCallingCardInd()) ? "Disconnect" : "Retain");
			remark.append("\n");
		}
		
		boolean isLostModem = false;
		if (LtsSbHelper.getLostModemIndIfTrue(pSbOrder) != null){
			remark.append("\n");
			remark.append("LOST MODEM: Y");
			isLostModem = true;
		}
		
		if(StringUtils.isNotBlank(ltsService.getAppointmentDtl().getTidInd())){
			remark.append("\n");
			remark.append("TID INDICATOR: ").append(StringUtils.defaultIfEmpty(ltsService.getAppointmentDtl().getTidInd(), "N"));
		}

		remark.append("\n");
		remark.append("SRD: ").append(StringUtils.substring(pSbOrder.getSrvReqDate(), 0, 10));
		remark.append("\n");
		if (StringUtils.equals("Y", ltsService.getForceFieldVisitInd())) {
			remark.append("Appt Date & Time: ").append(ltsService.getAppointmentDtl().getAppntStartTime()).append(" - ").append(ltsService.getAppointmentDtl().getAppntEndTime());
			remark.append("\n");
		}
		if (StringUtils.equals("Y", ltsService.getForceFieldVisitInd())) {
			remark.append("Pre-book Serial Number: ").append(ltsService.getAppointmentDtl().getSerialNum());
			remark.append("\n");
		}
		
		
		remark.append("Customer Contact Name: ").append(ltsService.getAppointmentDtl().getInstContactName());
		remark.append("\n");
		remark.append("Customer Contact Number: ").append(ltsService.getAppointmentDtl().getInstContactNum());
		remark.append("\n");
		
		String addonRemark = ltsService.remarkToString(LtsBackendConstant.REMARK_TYPE_ADD_ON_REMARK);
		if (StringUtils.isNotEmpty(addonRemark)) {
			remark.append("Field Service Remark: ");
			remark.append("\n");
			remark.append(addonRemark);
			remark.append("\n");
		}

		if (!isLostModem){ //hide if lost modem case
			remark.append("Lost all equipment: ").append(StringUtils.isNotEmpty(ltsService.getLostEquipmentCd()) ? "Y" : "N");
			remark.append("\n");
		}
		
		if (StringUtils.isNotEmpty(ltsService.getFchNum())) {
			remark.append("FCH No.: ").append(ltsService.getFchNum());
			remark.append("\n");
		}
		
		if (!isLostModem){ //hide if lost modem case
			if (StringUtils.equals("Y", ltsService.getForceFieldVisitInd())
					&& StringUtils.isNotEmpty(ltsService.getEqtCollectionAddr())) {
				remark.append("Collect Equipment Address: ");
				remark.append("\n");
				remark.append(ltsService.getEqtCollectionAddr());
				remark.append("\n");
			}
		}
		
		remark.append("\n");
		remark.append("New Billing Name: ").append(ltsService.getAccount().getAcctName());
		remark.append("\n");
		remark.append("New Billing Address: ").append(LtsBackendConstant.BILLING_ADDR_ACTION_NEW.equals(ltsService.getAccount().getBillingAddress().getAction()) ?  ltsService.getAccount().getBillingAddress().getFullBillAddr() : "N");
		remark.append("\n");
		remark.append("Instant Update: ").append(StringUtils.defaultIfEmpty(ltsService.getAccount().getBillingAddress().getInstantUpdateInd(), "N"));
		remark.append("\n");
		remark.append("\nChannel: ");
		remark.append(StringUtils.isEmpty(pSbOrder.getSalesChannel()) ? "" : pSbOrder.getSalesChannel());
		remark.append("\nTeam Code: ");
		remark.append(StringUtils.isEmpty(pSbOrder.getSalesTeam()) ? "" : pSbOrder.getSalesTeam());
		remark.append("\nStaff Number: ");
		remark.append(StringUtils.isEmpty(pSbOrder.getStaffNum()) ? "" : pSbOrder.getStaffNum());
		remark.append("\nSales Contact No.: ");
		remark.append(StringUtils.isEmpty(pSbOrder.getSalesContactNum()) ? "" : pSbOrder.getSalesContactNum());
		remark.append("\nBOC: ");
		remark.append(StringUtils.isEmpty(pSbOrder.getBoc()) ? "" : pSbOrder.getBoc());
		remark.append("\n");
		
		
		
		try {
			List<WqInquiryResultFormDTO> wqStatusList = workQueueService.getWqNaturestatus(pSbOrder.getOrderId());	
			if (wqStatusList != null && !wqStatusList.isEmpty()) {
				remark.append("\n");
				remark.append("Approval Details:");
				for (WqInquiryResultFormDTO wqStatus : wqStatusList) {
					remark.append("\n");
					remark.append(wqStatus.getWqNatureDesc()).append(" / ")
						.append(wqStatus.getWqStatusDesc()).append(" / ")
						.append(wqStatus.getWqStatusDate()).append(" / ")
						.append(wqStatus.getAssignee());
				}
				remark.append("\n");
			}
		}
		catch (Exception e) {
			throw new AppRuntimeException(ExceptionUtils.getFullStackTrace(e));
		}
		
		remark.append("\n");
		remark.append("LTS Order Remarks:");
		remark.append("\n");
		remark.append(ltsService.remarkToString(LtsBackendConstant.REMARK_TYPE_ORDER_REMARK));
		
		if (imsService != null) {
			remark.append("\n\n");
			remark.append("IMS Order Remarks:");
			remark.append("\n");
			remark.append(imsService.remarkToString(LtsBackendConstant.REMARK_TYPE_ORDER_REMARK));
		}
		
		return chopString(remark.toString(), WQ_RMK_LENGTH);
	}
	
	
	private String getPenaltyHandlingDesc(String penaltyHandling) {
		if (StringUtils.isEmpty(penaltyHandling)) {
			return "Free To Go";
		}
		
		if (LtsBackendConstant.PENALTY_ACTION_GENERATE.equals(penaltyHandling)) {
			return "Generate";
		}
		
		if (LtsBackendConstant.PENALTY_ACTION_AUTO_WAIVE.equals(penaltyHandling)) {
			return "Auto Waive";
		}
		
		if (LtsBackendConstant.PENALTY_ACTION_AWAIT_APPROVAL.equals(penaltyHandling)) {
			return "Manual Waive (Await Approval)";
		}
		
		/*if (LtsBackendConstant.PENALTY_ACTION_MANUAL_WAIVE.equals(penaltyHandling)) {
			return "Manual Waive";
		}*/
		
		if(LtsBackendConstant.CALL_PLAN_WAIVE_PEN_MKT_APPV.equals(penaltyHandling)){
			return "Waived (Special Approval by Marketing)";			
		}
		
		if(LtsBackendConstant.CALL_PLAN_WAIVE_PEN_SM_APPV.equals(penaltyHandling)){
			return "Waived (Special Approval by SM)";
		}
		
		if(LtsBackendConstant.CALL_PLAN_WAIVE_PEN_UM_APPV.equals(penaltyHandling)){
			return "Waived (Special Approval by UM)";
		}
		
		if(LtsBackendConstant.CALL_PLAN_PEN_OTHER_PARTY_HNDL.equals(penaltyHandling)){
			return "Other Party Handled";
		}

		if(LtsBackendConstant.CALL_PLAN_PEN_FREE_TO_GO.equals(penaltyHandling)){
			return "Free To Go";
		}
		
		return "Free To Go"; 
	}
	
	private String generateCreatePortIn2DELRemark(ServiceDetailLtsDTO pLtsSrvDtl) {		
		if (LtsSbHelper.is2ndDelService(pLtsSrvDtl) 
				&& StringUtils.equals(LtsBackendConstant.DN_SOURCE_DN_PIPB, pLtsSrvDtl.getDnSource())
				&& StringUtils.isBlank(pLtsSrvDtl.getDnStatus())) {
			return LtsActvBackendConstant.ACTV_REMAKRS_FOR_CREATE_DN;
		}
		return null;
	}
	
	/* return {target acc, reg email, reg mobile} if The Club/MyHKT registration item exist in order
     * return null if not*/
    private static String[] getTheClubMyHktOrderRegInfo(ServiceDetailLtsDTO ltsSrv){
		String regLogin = null;
		String regMobile = null;
		String targetAcct = null;
		
    	for(ItemDetailLtsDTO itemDtl: ltsSrv.getItemDtls()){
			if(StringUtils.equals(itemDtl.getItemType(), LtsBackendConstant.ITEM_TYPE_HKT_THE_CLUB_BILL)){
				regLogin = ltsSrv.getAccount().getCustomer().getCsPortalLogin();
				regMobile = ltsSrv.getAccount().getCustomer().getCsPortalMobile();
				//Do not register myHKT if the email or mobile is dummy
				if(( StringUtils.contains(regLogin, LtsBackendConstant.CSP_DUMMY_EMAIL_DOMAIN)
					 || StringUtils.contains(regMobile, LtsBackendConstant.CSP_DUMMY_MOBILE_NUM))){
					targetAcct = LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY;
				}else{
					targetAcct = LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER;
				}
				break;
			}
			if(StringUtils.equals(itemDtl.getItemType(), LtsBackendConstant.ITEM_TYPE_MYHKT_BILL)){
				regLogin = ltsSrv.getAccount().getCustomer().getCsPortalLogin();
				regMobile = ltsSrv.getAccount().getCustomer().getCsPortalMobile();
				targetAcct = LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY;
				break;
			}
			if(StringUtils.equals(itemDtl.getItemType(), LtsBackendConstant.ITEM_TYPE_THE_CLUB_BILL)){
				regLogin = ltsSrv.getAccount().getCustomer().getTheClubLogin();
				regMobile = ltsSrv.getAccount().getCustomer().getTheClubMobile();
				targetAcct = LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY;
				break;
			}
		}
    	
    	if(StringUtils.isNotBlank(targetAcct)){
        	return new String[]{targetAcct, regLogin, regMobile};
    	}else{
    		return null;
    	}
    }
	
	public OfferService getOfferService() {
		return offerService;
	}

	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	public CallPlanLtsService getCallPlanLtsService() {
		return callPlanLtsService;
	}

	public void setCallPlanLtsService(CallPlanLtsService callPlanLtsService) {
		this.callPlanLtsService = callPlanLtsService;
	}

	public CodeLkupCacheService getWaiveDFormReasonLkupCacheService() {
		return waiveDFormReasonLkupCacheService;
	}

	public void setWaiveDFormReasonLkupCacheService(
			CodeLkupCacheService waiveDFormReasonLkupCacheService) {
		this.waiveDFormReasonLkupCacheService = waiveDFormReasonLkupCacheService;
	}

	public CodeLkupCacheService getDelDisconnectReasonLkupCacheService() {
		return delDisconnectReasonLkupCacheService;
	}

	public void setDelDisconnectReasonLkupCacheService(
			CodeLkupCacheService delDisconnectReasonLkupCacheService) {
		this.delDisconnectReasonLkupCacheService = delDisconnectReasonLkupCacheService;
	}

	public CodeLkupCacheService getEyeDisconnectReasonLkupCacheService() {
		return eyeDisconnectReasonLkupCacheService;
	}

	public void setEyeDisconnectReasonLkupCacheService(
			CodeLkupCacheService eyeDisconnectReasonLkupCacheService) {
		this.eyeDisconnectReasonLkupCacheService = eyeDisconnectReasonLkupCacheService;
	}

	public WorkQueueService getWorkQueueService() {
		return workQueueService;
	}

	public void setWorkQueueService(WorkQueueService workQueueService) {
		this.workQueueService = workQueueService;
	}

	public CodeLkupCacheService getRecontractSrvHandleCacheService() {
		return recontractSrvHandleCacheService;
	}

	public void setRecontractSrvHandleCacheService(
			CodeLkupCacheService recontractSrvHandleCacheService) {
		this.recontractSrvHandleCacheService = recontractSrvHandleCacheService;
	}

	public CodeLkupCacheService getOptOutReasonLkupCacheService() {
		return optOutReasonLkupCacheService;
	}

	public void setOptOutReasonLkupCacheService(
			CodeLkupCacheService optOutReasonLkupCacheService) {
		this.optOutReasonLkupCacheService = optOutReasonLkupCacheService;
	}

	public CodeLkupCacheService getUpdImsWgProfileBillInfoLkupCacheService() {
		return updImsWgProfileBillInfoLkupCacheService;
	}

	public void setUpdImsWgProfileBillInfoLkupCacheService(
			CodeLkupCacheService updImsWgProfileBillInfoLkupCacheService) {
		this.updImsWgProfileBillInfoLkupCacheService = updImsWgProfileBillInfoLkupCacheService;
	}
	
}


