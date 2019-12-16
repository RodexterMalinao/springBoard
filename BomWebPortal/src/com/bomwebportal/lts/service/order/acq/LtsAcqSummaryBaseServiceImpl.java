package com.bomwebportal.lts.service.order.acq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.bomwebportal.lts.dao.order.OrderDetailDAO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.DeviceDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqServiceSummaryDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqServiceSummaryDTO.SummaryBillAddrDtl;
import com.bomwebportal.lts.dto.acq.LtsAcqServiceSummaryDTO.SummaryPayMtdDtl;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PipbLtsDTO;
import com.bomwebportal.lts.dto.order.RecontractLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.order.ThirdPartyDetailLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.ServiceProfileInventoryDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsDeviceService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsPaymentService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsDrgService;
import com.bomwebportal.lts.service.order.OrderDetailService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public abstract class LtsAcqSummaryBaseServiceImpl implements LtsAcqSummaryBaseService {

	protected LtsOfferService ltsOfferService;
	protected LtsBasketService ltsBasketService;
	protected CodeLkupCacheService penaltyHandleLtsLkupCacheService;
	private LtsPaymentService ltsPaymentService;
	private LtsDeviceService ltsDeviceService;
	private CodeLkupCacheService creditCardTyepCodeLkupCacheService;
	private ServiceProfileLtsDrgService serviceProfileLtsDrgService;
	private OrderDetailService orderDetailService;
	private CodeLkupCacheService idDocTypeLkupCacheService;
	private CodeLkupCacheService recontractModeCacheService;
	private CodeLkupCacheService recontractSrvHandleCacheService;
	private CodeLkupCacheService erHandleCacheService;
	private CodeLkupCacheService waiveReasonLkupCacheService;
	
	private OrderDetailDAO orderDetailDao = null;
	
	private static String[] ITEM_SRV_PLAN = new String[] {LtsConstant.ITEM_TYPE_PLAN, LtsConstant.ITEM_TYPE_VAS_2DEL_HOT};
	private static String[] ITEM_BB_RENTAL = new String[] {LtsConstant.ITEM_TYPE_BB_RENTAL, LtsConstant.ITEM_TYPE_INSTALL, LtsConstant.ITEM_TYPE_INSTALL_WAIVE};
	private static String[] ITEM_ER_CHARGE = new String[] {LtsConstant.ITEM_TYPE_ER_CHARGE};
	private static String[] ITEM_OTHER_CHARGE = new String[] {LtsConstant.ITEM_TYPE_ADMIN_CHARGE, LtsConstant.ITEM_TYPE_CANCEL_CHARGE};
	private static String[] ITEM_PE = new String[] {LtsConstant.ITEM_TYPE_PE_FREE};
	private static String[] ITEM_OPT_ACC = new String[] {LtsConstant.ITEM_TYPE_OPT_ACC};
	private static String[] ITEM_SOCKET = new String[] {LtsConstant.ITEM_TYPE_PE_SOCKET};
	private static String[] ITEM_CONTENT = new String[] {LtsConstant.ITEM_TYPE_CONTENT};
	private static String[] ITEM_MOOV = new String[] {LtsConstant.ITEM_TYPE_MOOV};
	private static String[] ITEM_NOWTV = new String[] {LtsConstant.ITEM_TYPE_NOWTV_FREE, LtsConstant.ITEM_TYPE_NOWTV_CELE, LtsConstant.ITEM_TYPE_NOWTV_MIRR, LtsConstant.ITEM_TYPE_NOWTV_STAR, LtsConstant.ITEM_TYPE_NOWTV_SPT};
	private static String[] ITEM_NOWTV_SPEC = new String[] {LtsConstant.ITEM_TYPE_NOWTV_SPEC, LtsConstant.ITEM_TYPE_NOWTV_PAY};
	private static String[] ITEM_BILL = new String[] {LtsConstant.ITEM_TYPE_PAPER_BILL, LtsConstant.ITEM_TYPE_PAPER_BILL_BR, LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE, LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE, LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE, LtsConstant.ITEM_TYPE_KEEP_EXIST_BILL, LtsConstant.ITEM_TYPE_EBILL, LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL, LtsConstant.ITEM_TYPE_MYHKT_BILL, LtsConstant.ITEM_TYPE_THE_CLUB_BILL,LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL,LtsConstant.ITEM_TYPE_EBILL, LtsConstant.ITEM_TYPE_EMAIL_BILL};
//	private static String[] ITEM_BILL = new String[] {LtsConstant.ITEM_TYPE_PAPER_BILL, LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE, LtsConstant.ITEM_TYPE_KEEP_EXIST_BILL, LtsConstant.ITEM_TYPE_EBILL, LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL, LtsConstant.ITEM_TYPE_MYHKT_BILL, LtsConstant.ITEM_TYPE_EMAIL_BILL};
	private static String[] ITEM_NOWTV_BILL = new String[] {LtsConstant.ITEM_TYPE_TV_EMAIL, LtsConstant.ITEM_TYPE_TV_PRINT, LtsConstant.ITEM_TYPE_TV_DEVICE};
	private static String[] ITEM_VAS_EYE = new String[] {LtsConstant.ITEM_TYPE_EXIST_VAS, LtsConstant.ITEM_TYPE_REPLAC_VAS};
	private static String[] ITEM_VAS = new String[] {LtsConstant.ITEM_TYPE_VAS_HOT, LtsConstant.ITEM_TYPE_VAS_OTHER, LtsConstant.ITEM_TYPE_VAS_2DEL_OTHER, LtsConstant.ITEM_TYPE_BVAS, LtsConstant.ITEM_TYPE_VAS_2DEL_FREE, LtsConstant.ITEM_TYPE_VAS_2DEL_STANDALONE};
	private static String[] ITEM_IDD_0060 = new String[] {LtsConstant.ITEM_TYPE_IDD, LtsConstant.ITEM_TYPE_0060E};
	private static String[] ITEM_OPTIONAL_PREMIUM = new String[] {LtsConstant.ITEM_TYPE_PREM_PAY};
	private static String[] ITEM_PREMIUM = new String[] {LtsConstant.ITEM_TYPE_PREMIUM};
	private static String[] ITEM_PREPAYMENT = new String[] {LtsConstant.ITEM_TYPE_PREPAY};
	private static String[] ITEM_LTS_ITEM = new String[] {LtsConstant.ITEM_LTS_VAS, LtsConstant.ITEM_LTS_TP};
	private static String[] ITEM_OP_OUT = new String[] {LtsConstant.ITEM_TYPE_IDD_OUT, LtsConstant.ITEM_TYPE_0060E_OUT};
	private static String[] ITEM_OUT_LTS = new String[] {LtsConstant.ITEM_LTS_VAS, LtsConstant.ITEM_LTS_TP};
	protected static String[] ITEM_OUT_IMS = new String[] {LtsBackendConstant.ITEM_IMS_VAS};
	private static String[] ITEM_VAS_PRE_WIRING = new String[] {LtsBackendConstant.ITEM_TYPE_VAS_PRE_WIRING};
	private static String[] ITEM_VAS_PRE_INSTALL = new String[] {LtsBackendConstant.ITEM_TYPE_VAS_PRE_INSTALL};
	private static String[] ITEM_FFP = new String[] {LtsBackendConstant.ITEM_TYPE_FFP_HOT, LtsBackendConstant.ITEM_TYPE_FFP_OTHER, LtsBackendConstant.ITEM_TYPE_FFP_STAFF, LtsBackendConstant.ITEM_TYPE_VAS_FFP};
	private static String[] ITEM_SMART_WARRANTY = new String[]{LtsBackendConstant.ITEM_TYPE_SMART_WARRANTY};
	private static String[] ITEM_TYPE_EPD = new String[]{LtsBackendConstant.ITEM_TYPE_EPD_ACCEPT, LtsBackendConstant.ITEM_TYPE_EPD_FORFEIT, LtsBackendConstant.ITEM_TYPE_EPD_UNDER_CONSIDERATION};
	
	public abstract LtsAcqServiceSummaryDTO buildSummary(SbOrderDTO pSbOrder, String pLocale, boolean pIsEnquiry);
	
	
	protected void fillLtsSummaryDetail(LtsAcqServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder, ServiceDetailLtsDTO pSrvDtlLts) {
		
		pSrvSummary.setSrvNum(pSrvDtlLts.getSrvNum()!=null?pSrvDtlLts.getSrvNum().substring(4):null);
		pSrvSummary.setInstallAddr(this.getDisplayAddress(pSbOrder.getAddress()));
		pSrvSummary.setBlacklistAddrInd(StringUtils.equals("Y", pSbOrder.getAddress().getBlacklistInd()));
		pSrvSummary.setExtRelInd(pSrvDtlLts.getErInd());
		pSrvSummary.setWorkQueueType(pSrvDtlLts.getWorkQueueType());
		pSrvSummary.setPendingLtsOcid(pSrvDtlLts.getPendingOcid());
		pSrvSummary.setApplDate(LtsDateFormatHelper.getDateFromDTOFormat(pSbOrder.getAppDate()));
		pSrvSummary.setStaffPlanApplicantId(pSrvDtlLts.getStaffPlanApplicantId());
		pSrvSummary.setExDirInd(StringUtils.equals("Y",pSrvDtlLts.getExDirInd())?"Yes":"No");
		pSrvSummary.setExDirName(pSrvDtlLts.getExDirName());
		this.fillBillingAddress(pSrvSummary, pSrvDtlLts);
	}
	
	private void fillBillingAddress(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
        List<AccountServiceAssignLtsDTO> acctList = new ArrayList<AccountServiceAssignLtsDTO>();
		
		for (AccountServiceAssignLtsDTO acctDtl: pSrvDtlLts.getAccounts()){
			if (StringUtils.equals(acctDtl.getChrgType(), "R")){
				acctList.add(acctDtl);
			}
		}		
		for (AccountServiceAssignLtsDTO acctDtl: pSrvDtlLts.getAccounts()){
			if (StringUtils.equals(acctDtl.getChrgType(), "I") && !StringUtils.equals(acctList.get(0).getAccount().getAcctNo(), acctDtl.getAccount().getAcctNo())){
				acctList.add(acctDtl);
			}	
		}
		
		AccountServiceAssignLtsDTO[] accts = acctList.toArray(new AccountServiceAssignLtsDTO[acctList.size()]);
				
		String billingAddr = null;
		String updInd = null;
		String chgInd = null;
		List<SummaryBillAddrDtl> summaryBillAddrDtlList = new ArrayList<SummaryBillAddrDtl>();

		
		for (int i=0; i<accts.length; i++){
		BillingAddressLtsDTO BillAddr = accts[i].getAccount().getBillingAddress();
		SummaryBillAddrDtl summaryBillAddrDtl = new LtsAcqServiceSummaryDTO().new SummaryBillAddrDtl();
		
//		    if (StringUtils.equals(LtsConstant.BILLING_ADDR_ACTION_NEW, BillAddr.getAction())) {
			    StringBuilder addrSb = new StringBuilder();
			    if (StringUtils.isNotEmpty(BillAddr.getAddrLine1())) {
				    addrSb.append(BillAddr.getAddrLine1());
			        addrSb.append("<p>");
			    }
			    if (StringUtils.isNotEmpty(BillAddr.getAddrLine2())) {
				    addrSb.append(BillAddr.getAddrLine2());
				    addrSb.append("<p>");
			    }
			    if (StringUtils.isNotEmpty(BillAddr.getAddrLine3())) {
				    addrSb.append(BillAddr.getAddrLine3());
				    addrSb.append("<p>");
			    }
			    if (StringUtils.isNotEmpty(BillAddr.getAddrLine4())) {
				    addrSb.append(BillAddr.getAddrLine4());
				    addrSb.append("<p>");
		    	}
			    if (StringUtils.isNotEmpty(BillAddr.getAddrLine5())) {
				    addrSb.append(BillAddr.getAddrLine5());
				    addrSb.append("<p>");
			    }
			    if (StringUtils.isNotEmpty(BillAddr.getAddrLine6())) {
				    addrSb.append(BillAddr.getAddrLine6());
			    }
			    billingAddr = addrSb.toString();
//		    }
		    updInd = BillAddr.getInstantUpdateInd();
		    chgInd = BillAddr.getAction();
		    
		    summaryBillAddrDtl.setBillingAddress(billingAddr);
		    summaryBillAddrDtl.setBillingAddrInstantUpdateInd(updInd);
		    summaryBillAddrDtl.setBillAddrChangeInd(chgInd);
		    
		    summaryBillAddrDtlList.add(summaryBillAddrDtl);
		}
/*		pSrvSummary.setBillingAddress(billingAddr);
		pSrvSummary.setBillingAddrInstantUpdateInd(updInd);
		pSrvSummary.setBillAddrChangeInd(chgInd);*/
		
		pSrvSummary.setSummaryBillAddrDtlList(summaryBillAddrDtlList);
	}
	
	protected void fillSrvStatusStateLts(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
		if (pSrvDtlLts.getSrvNum()!=null) {
			ServiceProfileInventoryDTO srvInventory = this.serviceProfileLtsDrgService.getServiceInventory(pSrvDtlLts.getSrvNum(), LtsConstant.SERVICE_TYPE_TEL);
			if (srvInventory!=null) {
				pSrvDtlLts.setFrozenExchInd(srvInventory.isFrozenExchInd() ? "Y" : null);
				pSrvDtlLts.setDnExchangeId(srvInventory.getDnExchangeId());
				StringBuilder messageSb = new StringBuilder();
				
				if (StringUtils.equals(LtsConstant.ORDER_STATUS_APPROVAL_REJECTED, pSrvDtlLts.getSbStatus())) {
					pSrvSummary.setStatusState(LtsAcqServiceSummaryDTO.STATUS_STATE_APPROVAL_REJECTED);
					pSrvSummary.clearMessage();
					messageSb.append("Approval rejected.  Please cancel this order.");
				} else if (StringUtils.equals("14", srvInventory.getInventStatus())) {
					messageSb.append(pSrvDtlLts.getTypeOfSrv());
					messageSb.append(" ");
					messageSb.append(pSrvDtlLts.getSrvNum());
					messageSb.append(": Clear O/S amount first.  Order is forced to suspend, please recall order within 7 days for TOS.");
					pSrvSummary.setStatusState(LtsAcqServiceSummaryDTO.STATUS_STATE_TOS);
					pSrvDtlLts.setTos(true);
				} else if (StringUtils.isNotEmpty(pSrvDtlLts.getPendingApprovalCd()) || LtsSbHelper.existApprovalItems(pSrvDtlLts)) {
					pSrvSummary.setStatusState(LtsAcqServiceSummaryDTO.STATUS_STATE_APPROVAL);
				}
				pSrvSummary.appendMessage(messageSb.toString());
			}
		}
	}
	
	protected void fillSrvStatusState(String pOrderId, LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailDTO pSrvDtl) {
		
		this.orderDetailService.updateServicePendingBomOrder(pOrderId, pSrvDtl);
		
		String modemArrangement = null;
		if (pSrvDtl instanceof ServiceDetailOtherLtsDTO) {
			modemArrangement = ((ServiceDetailOtherLtsDTO)pSrvDtl).getModemArrangement();
		}
		
		if (pSrvDtl.getPendingOcid() != null 
				&& !StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, modemArrangement)) {
			StringBuilder messageSb = new StringBuilder();
			messageSb.append(pSrvDtl.getTypeOfSrv());
			messageSb.append(" ");
			messageSb.append(pSrvDtl.getSrvNum());
			messageSb.append(": Pending order OCID ");
			messageSb.append(pSrvDtl.getPendingOcid());
			messageSb.append(" exist order.  Order is forced to suspend.");
			pSrvSummary.appendMessage(messageSb.toString());
			pSrvDtl.setPendingOcid(pSrvDtl.getPendingOcid());
			pSrvDtl.setPendingOcidSrd(pSrvDtl.getPendingOcidSrd());
			pSrvSummary.setStatusState(LtsAcqServiceSummaryDTO.STATUS_STATE_PENDING_ORD);
		}
	}
	
	protected void fillAccountDetail(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, AccountServiceAssignLtsDTO[] pAccts) {
		
		if (pSrvDtlLts.getRecontract() != null) {
			this.fillLtsRecontractCustomerDetail(pSrvSummary, pSrvDtlLts, pAccts);
		} else {
			this.fillLtsCustomerDetail(pSrvSummary, pSrvDtlLts, pAccts);
		}
	}
	
	private void fillLtsCustomerDetail(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, AccountServiceAssignLtsDTO[] pAccts) {
		
		pSrvSummary.setDocNum(pSrvDtlLts.getActualDocId());
		pSrvSummary.setDocType((String)this.idDocTypeLkupCacheService.get(pSrvDtlLts.getActualDocType()));
//		AccountDetailLtsDTO account	 = pSrvDtlLts.getAccount();
		
		if (pAccts == null) {
			return;
		}
		String[] acctNum = new String[pAccts.length];
		AccountDetailLtsDTO rAcct = new AccountDetailLtsDTO();
		
		for (int i=0; i<pAccts.length; i++){
			if (StringUtils.equals(pAccts[i].getChrgType(), "R")){
				rAcct = pAccts[i].getAccount();
			}
		}
		
		
					
		    CustomerDetailLtsDTO customer = rAcct.getCustomer();
		    if (customer != null) {
			    pSrvSummary.setTitle(customer.getTitle() == null ? null : customer.getTitle());
			
			    StringBuilder nameSb = new StringBuilder();
			    if (StringUtils.isNotEmpty(customer.getLastName())) {
				    nameSb.append(customer.getLastName());
				    nameSb.append(" ");
			    }
			    if (StringUtils.isNotEmpty(customer.getFirstName())) {
				    nameSb.append(customer.getFirstName());
			    }
			    pSrvSummary.setName(nameSb.toString());
			    pSrvSummary.setCompanyName(customer.getCompanyName());
			    pSrvSummary.setBirthday(LtsDateFormatHelper.getDateFromDTOFormat(customer.getDob()));
			
			    if (StringUtils.equals("Y", pSrvDtlLts.getDummyDocIdInd())) {
				    pSrvSummary.setDummyDocNum(customer.getIdDocNum());
				    pSrvSummary.setDummyDocType((String)this.idDocTypeLkupCacheService.get(customer.getIdDocType()));	
			    }
		    }
		    
		    List<SummaryPayMtdDtl> summaryPayMtdDtlList = new ArrayList<SummaryPayMtdDtl>();
		    SummaryPayMtdDtl summaryPayMtdDtl = new LtsAcqServiceSummaryDTO().new SummaryPayMtdDtl(); 
		    
    
		    if("Y".equals(pSrvDtlLts.getRecontractInd())){ 
		    	summaryPayMtdDtl = this.fillSummaryPayMtdDtl(pSrvSummary, pSrvDtlLts.getRecontract().getFuturePayment(), pSrvDtlLts.getRecontract().getExistPayment(), rAcct.getAcctNo());
		    	summaryPayMtdDtlList.add(summaryPayMtdDtl);
		    	this.fillLtsPaymentDetail(pSrvSummary, summaryPayMtdDtlList);
		    }else{
				for (int i=0; i<pAccts.length; i++){
					acctNum[i] = pAccts[i].getAccount().getAcctNo();
					summaryPayMtdDtl = this.fillSummaryPayMtdDtl(pSrvSummary, pAccts[i].getAccount().getFuturePayment(), pAccts[i].getAccount().getExistPayment(), pAccts[i].getAccount().getAcctNo());
			        summaryPayMtdDtlList.add(summaryPayMtdDtl);
				}
				this.fillLtsPaymentDetail(pSrvSummary, summaryPayMtdDtlList);
		    }
		pSrvSummary.setAcctNum(acctNum);
		pSrvSummary.setRacctNum(rAcct.getAcctNo());
		pSrvSummary.setRedemMedia(rAcct.getRedemptionMedia());
		pSrvSummary.setRedemptionEmail(rAcct.getRedemptionEmailAddr());
		pSrvSummary.setRedemptionSms(rAcct.getRedemptionSmsNo());
		
	}
	
	private void fillLtsRecontractCustomerDetail(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, AccountServiceAssignLtsDTO[] pAccts) {
		
		RecontractLtsDTO recontract = pSrvDtlLts.getRecontract();
		pSrvSummary.setRecontractInd("Y");
		pSrvSummary.setRecontractOptCallCardInd((String)this.recontractSrvHandleCacheService.get(recontract.getCallingCardInd()));
		pSrvSummary.setRecontractOptFixedIddInd((String)this.recontractSrvHandleCacheService.get(recontract.getFixedIddInd()));
		pSrvSummary.setRecontractOptMobIddInd((String)this.recontractSrvHandleCacheService.get(recontract.getMobIddInd()));
		pSrvSummary.setRecontractMode((String)this.recontractModeCacheService.get(recontract.getTransMode()));
		pSrvSummary.setTransfereeTitle(recontract.getTitle());
		
		StringBuilder nameSb = new StringBuilder();
		if (StringUtils.isNotEmpty(recontract.getCustLastName())) {
			nameSb.append(recontract.getCustLastName());
			nameSb.append(" ");
		}
		if (StringUtils.isNotEmpty(recontract.getCustFirstName())) {
			nameSb.append(recontract.getCustFirstName());
		}
		pSrvSummary.setTransfereeName(nameSb.toString());
		pSrvSummary.setTransfereeDocType(recontract.getIdDocType());
		pSrvSummary.setTransfereeDocNum(recontract.getIdDocNum());
		pSrvSummary.setTransfereeRelationship(LtsSbHelper.getRelationshipDesc(recontract.getRelationship()));
		pSrvSummary.setTransfereeContactNum(recontract.getContactNum());
		pSrvSummary.setTransfereeEmail(recontract.getEmailAddr());
		pSrvSummary.setTransfereeBlackListInd(recontract.getBlacklistInd());
		pSrvSummary.setTransfereeAcctNum(recontract.getAcctNum() == null ? "New" : recontract.getAcctNum());
		
//		AccountDetailLtsDTO account = pSrvDtlLts.getAccount();
		
		if (pAccts == null) {
			return;
		}
		
		String[] reContractAcctNum = new String[1];
		String transferAcctNum = null;
		reContractAcctNum[0] = recontract.getAcctNum();
		pSrvSummary.setAcctNum(reContractAcctNum);
		
		for (int i=0; i<pAccts.length; i++){
			if (StringUtils.equals(pAccts[i].getChrgType(), "R")){
				transferAcctNum = pAccts[i].getAccount().getAcctNo();
			}
		}
		pSrvSummary.setTransferorAcctNum(transferAcctNum);
		
		CustomerDetailLtsDTO customer = pSrvDtlLts.getAccount().getCustomer();
		
		if (customer == null) {
			return;
		}
		nameSb = new StringBuilder();
		if (StringUtils.isNotEmpty(customer.getLastName())) {
			nameSb.append(customer.getLastName());
			nameSb.append(" ");
		}
		if (StringUtils.isNotEmpty(customer.getFirstName())) {
			nameSb.append(customer.getFirstName());
		}
		pSrvSummary.setTransferorName(nameSb.toString());
		pSrvSummary.setTransferorTitle(customer.getTitle());
		pSrvSummary.setTransferorDocType(customer.getIdDocType());
		pSrvSummary.setTransferorDocNum(customer.getIdDocNum());
		
		List<SummaryPayMtdDtl> summaryPayMtdDtlList = new ArrayList<SummaryPayMtdDtl>();
	    SummaryPayMtdDtl summaryPayMtdDtl = new LtsAcqServiceSummaryDTO().new SummaryPayMtdDtl(); 
		
	    summaryPayMtdDtl = this.fillSummaryPayMtdDtl(pSrvSummary, pSrvDtlLts.getRecontract().getFuturePayment(), pSrvDtlLts.getRecontract().getExistPayment(), recontract.getAcctNum());
		summaryPayMtdDtlList.add(summaryPayMtdDtl);
    	this.fillLtsPaymentDetail(pSrvSummary, summaryPayMtdDtlList);
	}
	
//	private void fillLtsPaymentDetail(LtsAcqServiceSummaryDTO pSrvSummary, PaymentMethodDetailLtsDTO pFuturePayment, PaymentMethodDetailLtsDTO pExistPayment, String pAcctNum) {
	private void fillLtsPaymentDetail(LtsAcqServiceSummaryDTO pSrvSummary, List<SummaryPayMtdDtl> pPayMtdDtlList){	

/*		if (StringUtils.equals(LtsConstant.IO_IND_INSTALL, pFuturePayment.getAction())
				|| "Y".equals(pSrvSummary.getRecontractInd())) {
			if (StringUtils.equals(pFuturePayment.getPayMtdType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
				pSrvSummary.setBankAcctNum(pFuturePayment.getBankAcctNo());
				pSrvSummary.setBankBranchCd(this.ltsPaymentService.getBranchNameByCode(pFuturePayment.getBankCd(), pFuturePayment.getBranchCd()) + " (" + pFuturePayment.getBranchCd() + ")");
				pSrvSummary.setBankCd(this.ltsPaymentService.getBankNameByCode(pFuturePayment.getBankCd()) + " (" + pFuturePayment.getBankCd() + ")");
				pSrvSummary.setHolderIdNum(pFuturePayment.getBankAcctHoldNum());
				pSrvSummary.setHolderIdType(pFuturePayment.getBankAcctHoldType());
				pSrvSummary.setHolderName(pFuturePayment.getBankAcctHoldName());
				pSrvSummary.setBankApplDate(LtsDateFormatHelper.getDateFromDTOFormat(pFuturePayment.getAutopayAppDate()));
					
				if (StringUtils.isNotEmpty(pFuturePayment.getAutopayUpLimAmt())) {
					pSrvSummary.setAutoPayLimit("$" + pFuturePayment.getAutopayUpLimAmt());	
				}
			} else if (StringUtils.equals(pFuturePayment.getPayMtdType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
				pSrvSummary.setCredCardType((String)this.creditCardTyepCodeLkupCacheService.get(pFuturePayment.getCcType()));
				pSrvSummary.setCredCardNum(pFuturePayment.getCcNum());
				pSrvSummary.setCredCardExpDate(pFuturePayment.getCcExpDate());
				pSrvSummary.setHolderIdNum(pFuturePayment.getCcIdDocNo());
				pSrvSummary.setHolderIdType((String)this.idDocTypeLkupCacheService.get(pFuturePayment.getCcIdDocType()));
				pSrvSummary.setHolderName(pFuturePayment.getCcHoldName());
			}
			pSrvSummary.setPaymentMethod(pFuturePayment.getPayMtdType());
			pSrvSummary.setPaymentChangeInd("Y");
		} else {
			pSrvSummary.setPaymentMethod(pExistPayment.getPayMtdType());
			pSrvSummary.setPaymentChangeInd("N");
		}
		pSrvSummary.setThirdPtyPayment(StringUtils.equals("Y", pFuturePayment.getThirdPartyInd()));*/

		pSrvSummary.setSummaryPayMtdDtlList(pPayMtdDtlList);
	}
	
	private SummaryPayMtdDtl fillSummaryPayMtdDtl(LtsAcqServiceSummaryDTO pSrvSummary, PaymentMethodDetailLtsDTO pFuturePayment, PaymentMethodDetailLtsDTO pExistPayment, String pAcctNum) {
		
		
//		List<SummaryPayMtdDtl> summaryPayMtdDtlList = new ArrayList<SummaryPayMtdDtl>();
		
		SummaryPayMtdDtl summaryPayMtdDtl = new LtsAcqServiceSummaryDTO().new SummaryPayMtdDtl();
		
		summaryPayMtdDtl.setAcctNum(pAcctNum);
		
		if (StringUtils.equals(LtsConstant.IO_IND_INSTALL, pFuturePayment.getAction())
				|| "Y".equals(pSrvSummary.getRecontractInd())) {
			if (StringUtils.equals(pFuturePayment.getPayMtdType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
				summaryPayMtdDtl.setBankAcctNum(pFuturePayment.getBankAcctNo());
				summaryPayMtdDtl.setBankBranchCd(this.ltsPaymentService.getBranchNameByCode(pFuturePayment.getBankCd(), pFuturePayment.getBranchCd()) + " (" + pFuturePayment.getBranchCd() + ")");
				summaryPayMtdDtl.setBankCd(this.ltsPaymentService.getBankNameByCode(pFuturePayment.getBankCd()) + " (" + pFuturePayment.getBankCd() + ")");
				summaryPayMtdDtl.setHolderIdNum(pFuturePayment.getBankAcctHoldNum());
				summaryPayMtdDtl.setHolderIdType(pFuturePayment.getBankAcctHoldType());
				summaryPayMtdDtl.setHolderName(pFuturePayment.getBankAcctHoldName());
				summaryPayMtdDtl.setBankApplDate(LtsDateFormatHelper.getDateFromDTOFormat(pFuturePayment.getAutopayAppDate()));
					
				if (StringUtils.isNotEmpty(pFuturePayment.getAutopayUpLimAmt())) {
					summaryPayMtdDtl.setAutoPayLimit("$" + pFuturePayment.getAutopayUpLimAmt());	
				}
			} else if (StringUtils.equals(pFuturePayment.getPayMtdType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
				summaryPayMtdDtl.setCredCardType((String)this.creditCardTyepCodeLkupCacheService.get(pFuturePayment.getCcType()));
				summaryPayMtdDtl.setCredCardNum(pFuturePayment.getCcNum());
				summaryPayMtdDtl.setCredCardExpDate(pFuturePayment.getCcExpDate());
				summaryPayMtdDtl.setHolderIdNum(pFuturePayment.getCcIdDocNo());
				summaryPayMtdDtl.setHolderIdType((String)this.idDocTypeLkupCacheService.get(pFuturePayment.getCcIdDocType()));
				summaryPayMtdDtl.setHolderName(pFuturePayment.getCcHoldName());
			}
			summaryPayMtdDtl.setPaymentMethod(pFuturePayment.getPayMtdType());
			summaryPayMtdDtl.setPaymentChangeInd("Y");
		} else {
			summaryPayMtdDtl.setPaymentMethod(pExistPayment.getPayMtdType());
			summaryPayMtdDtl.setPaymentChangeInd("N");
		}
		summaryPayMtdDtl.setThirdPtyPayment(StringUtils.equals("Y", pFuturePayment.getThirdPartyInd()));
		
//		summaryPayMtdDtlList.add(summaryPayMtdDtl);
		
		
		return summaryPayMtdDtl;
	}
	
	protected void fillThirdPartyDetail(LtsAcqServiceSummaryDTO pSrvSummary, ThirdPartyDetailLtsDTO pThirdPtyDtl) {
		
		if (pThirdPtyDtl == null) {
			return;
		}
		pSrvSummary.setThirdPtyName(pThirdPtyDtl.getTitle() + " " + pThirdPtyDtl.getAppntLastName() + " " + pThirdPtyDtl.getAppntFirstName());
		pSrvSummary.setThirdPtyDocId(pThirdPtyDtl.getAppntDocId());
		pSrvSummary.setThirdPtyDocType((String)this.idDocTypeLkupCacheService.get(pThirdPtyDtl.getAppntDocType()));
		pSrvSummary.setThirdPtyRelation(LtsSbHelper.getRelationshipDesc(pThirdPtyDtl.getRelationshipCode()));
		pSrvSummary.setThirdPtyContactNum(pThirdPtyDtl.getAppntContactNum());
	}
	
	protected void fillAppoinmentDetail(LtsAcqServiceSummaryDTO pSrvSummary, AppointmentDetailLtsDTO pAppt, SbOrderDTO pSbOrder) {
		
		if (pAppt == null) {
			return;
		}
		pSrvSummary.setApptContactName(pAppt.getInstContactName());
		pSrvSummary.setApptContactNum(pAppt.getInstContactNum());
		pSrvSummary.setApptMobileContactNum(pAppt.getInstContactMobile());
		if(pSbOrder.getContact()!= null){
			if(pSbOrder.getContact().getContactMobile()!=null){
				pSrvSummary.setCustMobileContactNum(pSbOrder.getContact().getContactMobile());
			}
			if(pSbOrder.getContact().getContactFixedLine()!=null){
				pSrvSummary.setCustFixContactNum(pSbOrder.getContact().getContactFixedLine());
			}
			if(pSbOrder.getContact().getEmailAddr()!=null){
				pSrvSummary.setCustContactEmail(pSbOrder.getContact().getEmailAddr());
			}
		}
		pSrvSummary.setInstallDate(LtsDateFormatHelper.getDateFromDTOFormat(pAppt.getAppntStartTime()));
		pSrvSummary.setInstallTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(pAppt.getAppntStartTime(), pAppt.getAppntEndTime())));
		pSrvSummary.setPrewiringDate(LtsDateFormatHelper.getDateFromDTOFormat(pAppt.getPreWiringStartTime()));
		pSrvSummary.setPrewiringTime(LtsDateFormatHelper.getFromToTimeFormat(pAppt.getPreWiringStartTime(), pAppt.getPreWiringEndTime()));
		pSrvSummary.setSwitchDate(LtsDateFormatHelper.getDateFromDTOFormat(pAppt.getCutOverStartTime()) + " " + LtsDateFormatHelper.getFromToTimeFormat(pAppt.getCutOverStartTime(), pAppt.getCutOverEndTime()));
	}
	
	protected void fillItemDisplay(SbOrderDTO pSbOrder, LtsAcqServiceSummaryDTO pSrvSummary, ItemDetailLtsDTO[] pItems, String pLocale) {
		
		if (pItems == null) {
			return;
		}
		Map<String, ItemDetailLtsDTO> itemIdMap = new HashMap<String, ItemDetailLtsDTO>();
		
		for (int i=0; pItems!=null && i<pItems.length; ++i) {
			itemIdMap.put(pItems[i].getItemId(), pItems[i]);
		}
		List<ItemDetailDTO> itemDescList =  this.ltsOfferService.getItemWithAttb(itemIdMap.keySet().toArray(new String[itemIdMap.size()]), LtsConstant.DISPLAY_TYPE_ITEM_SELECT, pLocale, false, true);
		ItemDetailSummaryDTO itemDtlSummary = null;
		
		for (int i=0; i<itemDescList.size(); ++i) {
			itemDtlSummary = this.generateItemSummaryDTO(itemDescList.get(i), itemIdMap.get(itemDescList.get(i).getItemId()));
			itemIdMap.remove(itemDtlSummary.getItemId());
			
			if (!StringUtils.equals(LtsConstant.IO_IND_OUT, itemDtlSummary.getIoInd())
					&& !StringUtils.equals(LtsConstant.IO_IND_SPACE, itemDtlSummary.getIoInd())) {
				if (LtsSbOrderHelper.isContainString(ITEM_SRV_PLAN, itemDtlSummary.getItemType())) {
					pSrvSummary.addSrvPlanItem(itemDtlSummary);
					
					// temp to add Plan 2C msg
					Set<String> plan2C = this.orderDetailDao.getPlan2C();
					
					if (plan2C.contains(itemDtlSummary.getItemId())) {
						ItemDetailSummaryDTO temp = new ItemDetailSummaryDTO();
						temp.setItemType(LtsConstant.ITEM_LTS_VAS);
						temp.setItemDisplayHtml("Voice package / Deluxe package (if have)");
						temp.setPenaltyHandling("Auto waive");
						pSrvSummary.addOutLtsItem(temp);
					}
					
				} else if (LtsSbOrderHelper.isContainString(ITEM_BB_RENTAL, itemDtlSummary.getItemType())) {
					pSrvSummary.addBbRentalItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_PE, itemDtlSummary.getItemType())) {
					pSrvSummary.addPeItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_OPT_ACC, itemDtlSummary.getItemType())) {
					pSrvSummary.addOptAccItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_SOCKET, itemDtlSummary.getItemType())) {
					pSrvSummary.addSocketItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_CONTENT, itemDtlSummary.getItemType())) {
					pSrvSummary.addContentItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_MOOV, itemDtlSummary.getItemType())) {
					pSrvSummary.addMoovItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_NOWTV, itemDtlSummary.getItemType())) {
					pSrvSummary.addNowtvItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_NOWTV_SPEC, itemDtlSummary.getItemType())) {	
					pSrvSummary.addNowtvSpecialItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_BILL, itemDtlSummary.getItemType())) {
					pSrvSummary.addBillItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_NOWTV_BILL, itemDtlSummary.getItemType())) {
					pSrvSummary.addNowtvBillItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_VAS_EYE, itemDtlSummary.getItemType())) {
					pSrvSummary.addVasEyeItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_VAS, itemDtlSummary.getItemType())) {
					pSrvSummary.addVasItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_IDD_0060, itemDtlSummary.getItemType())) {
					pSrvSummary.addIdd0060Item(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_OPTIONAL_PREMIUM, itemDtlSummary.getItemType())) {
					pSrvSummary.addOptionalPremiumItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_PREMIUM, itemDtlSummary.getItemType())) {
					pSrvSummary.addPremiumItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_PREPAYMENT, itemDtlSummary.getItemType())) {
					pSrvSummary.addPrepaymentItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_OTHER_CHARGE, itemDtlSummary.getItemType())) {
					pSrvSummary.addOtherChargeItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_VAS_PRE_WIRING, itemDtlSummary.getItemType())) {
					pSrvSummary.addPrewiringItem(itemDtlSummary);	
				} else if (LtsSbOrderHelper.isContainString(ITEM_VAS_PRE_INSTALL, itemDtlSummary.getItemType())) {
					pSrvSummary.addPreInstallItem(itemDtlSummary);	
				} else if (LtsSbOrderHelper.isContainString(ITEM_FFP, itemDtlSummary.getItemType())) {
					pSrvSummary.addFfpItem(itemDtlSummary);	
				} else if(LtsSbOrderHelper.isContainString(ITEM_SMART_WARRANTY, itemDtlSummary.getItemType())){
					pSrvSummary.addSmartWrtyItem(itemDtlSummary);
				} else if(LtsSbOrderHelper.isContainString(ITEM_TYPE_EPD, itemDtlSummary.getItemType())){
					pSrvSummary.addEpdItem(itemDtlSummary);
				} else if (LtsSbOrderHelper.isContainString(ITEM_ER_CHARGE, itemDtlSummary.getItemType())) {
					if (StringUtils.isNotBlank(itemDtlSummary.getPenaltyHandling())) {
						itemDtlSummary.setPenaltyHandling((String)this.erHandleCacheService.get(itemDtlSummary.getPenaltyHandling()));
					} else {
						itemDtlSummary.setPenaltyHandling("Free to go");
					}
					pSrvSummary.addErChargeItem(itemDtlSummary);
				}
			}
			if (LtsConstant.ITEM_TYPE_EMAIL_BILL.equals(itemDtlSummary.getItemType())) {
				ServiceDetailDTO srvDtl = LtsSbOrderHelper.getLtsService(pSbOrder);
				List<AccountServiceAssignLtsDTO> acctList = new ArrayList<AccountServiceAssignLtsDTO>();
				
				for (AccountServiceAssignLtsDTO acctDtl: srvDtl.getAccounts()){
					if (StringUtils.equals(acctDtl.getChrgType(), "R")){
						acctList.add(acctDtl);
					}
				}		
				for (AccountServiceAssignLtsDTO acctDtl: srvDtl.getAccounts()){
					if (StringUtils.equals(acctDtl.getChrgType(), "I") && !StringUtils.equals(acctList.get(0).getAccount().getAcctNo(), acctDtl.getAccount().getAcctNo())){
						acctList.add(acctDtl);
					}	
				}
				
				List<String> emailBillAddr = new  ArrayList<String>();
				
				AccountServiceAssignLtsDTO[] accts = acctList.toArray(new AccountServiceAssignLtsDTO[acctList.size()]);
				for (int j=0; j<accts.length; j++){
					if(LtsConstant.LTS_BILL_MEDIA_PPS_BILL.equals(accts[j].getAccount().getBillMedia())){
					    emailBillAddr.add(accts[j].getAccount().getEmailAddr());
					}
				}
				pSrvSummary.setLtsEmailBillAddr(emailBillAddr.toArray(new String[emailBillAddr.size()]));
			}
			if(LtsConstant.ITEM_TYPE_PAPER_BILL.equals(itemDtlSummary.getItemType())
					||LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE.equals(itemDtlSummary.getItemType())
					||LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE.equals(itemDtlSummary.getItemType()) ){
				ServiceDetailDTO primaryService = LtsSbOrderHelper.getLtsService(pSbOrder);
				String waiveReason =  (String) waiveReasonLkupCacheService.get(primaryService.getAccount().getWaivePaperReaCd());				
				pSrvSummary.setPaperBillWaiveRea(waiveReason);
				pSrvSummary.setPaperBillWaiveRemarks(primaryService.getAccount().getWaivePaperRemarks());
				pSrvSummary.setPaperBillCharge(null);
				String paperWaiveInd = primaryService.getAccount().getAcctWaivePaperInd();
				if(StringUtils.equals(paperWaiveInd, "Y")){
					pSrvSummary.setPaperBillCharge(LtsBackendConstant.PAPER_BILL_CHARGE_GENERATE);
				}else{
					if(StringUtils.equals(paperWaiveInd, "W")){
						pSrvSummary.setPaperBillCharge(LtsBackendConstant.PAPER_BILL_CHARGE_WAIVE);
					}else if(StringUtils.isEmpty(paperWaiveInd)){
						pSrvSummary.setPaperBillCharge(LtsBackendConstant.PAPER_BILL_CHARGE_REMAIN_UNCHANGE);
					}
					
				}	
			}
			if (LtsSbOrderHelper.isContainString(ITEM_OP_OUT, itemDtlSummary.getItemType())) {
				pSrvSummary.addOptOutItem(itemDtlSummary);
			} else if (LtsSbOrderHelper.isContainString(ITEM_LTS_ITEM, itemDtlSummary.getItemType())) {
				pSrvSummary.addProfileItem(itemDtlSummary);
				
				if (StringUtils.equals(LtsConstant.IO_IND_INSTALL, itemDtlSummary.getIoInd())) {
					pSrvSummary.addVasItem(itemDtlSummary);
				}
			}
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_PLAN,  itemDtlSummary.getItemType())) {
				pSrvSummary.setFixedTerm(itemDtlSummary.getContractMonth());
				
				if (!LtsSbHelper.isOnlineAcquistionOrder(pSbOrder)) {
					BasketDetailDTO pBasketDetailDTO = this.ltsBasketService.getBasket(itemDtlSummary.getBasketId(), pLocale, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
					String pDeviceType = pBasketDetailDTO.getType();
					DeviceDetailDTO pDeviceDetailDTO = this.ltsDeviceService.getEyeDevice(pDeviceType, pLocale);
					if (pDeviceDetailDTO != null){
					    itemDtlSummary.setImagePath(pDeviceDetailDTO.getImagePath());
					}
//					itemDtlSummary.setImagePath(this.ltsDeviceService.getEyeDevice(this.ltsBasketService.getBasket(itemDtlSummary.getBasketId(), pLocale, LtsConstant.DISPLAY_TYPE_RP_PROMOT).getType(), pLocale).getImagePath());	
				}
			}
			if (StringUtils.equals(LtsConstant.IO_IND_OUT, itemDtlSummary.getIoInd())
					&& LtsSbOrderHelper.isContainString(ITEM_OUT_LTS, itemDtlSummary.getItemType())) {
				
				if (StringUtils.isNotBlank(itemDescList.get(i).getPenaltyHandling())) {
					itemDtlSummary.setPenaltyHandling((String)this.penaltyHandleLtsLkupCacheService.get(itemDescList.get(i).getPenaltyHandling()));
				} else {
					itemDtlSummary.setPenaltyHandling("Free to go");
				}
				pSrvSummary.addOutLtsItem(itemDtlSummary);
			}
		}
		String[] unmappedItemIds = itemIdMap.keySet().toArray(new String[itemIdMap.size()]);
		ItemDetailDTO unmappedItem = null;
		
		for (int i=0; i<unmappedItemIds.length; ++i) {
			unmappedItem = this.ltsOfferService.getTpVasItemDetail(unmappedItemIds[i], pLocale);
			
			if (unmappedItem != null) {
				itemDtlSummary = this.generateProfileItemSummaryDTO(unmappedItem, itemIdMap.get(unmappedItem.getItemId()));
				pSrvSummary.addProfileItem(itemDtlSummary);
				
				if (StringUtils.equals(LtsConstant.IO_IND_INSTALL, itemDtlSummary.getIoInd())) {
					pSrvSummary.addVasItem(itemDtlSummary);
				} else if (StringUtils.equals(LtsConstant.IO_IND_OUT, itemDtlSummary.getIoInd())
						&& LtsSbOrderHelper.isContainString(ITEM_OUT_LTS, itemDtlSummary.getItemType())) {
					pSrvSummary.addOutLtsItem(itemDtlSummary);
				}
			}
		}
	}
	
	private ItemDetailSummaryDTO generateProfileItemSummaryDTO(ItemDetailDTO pItemDesc, ItemDetailLtsDTO pItemDtl) {
		
		ItemDetailSummaryDTO itemSummary = new ItemDetailSummaryDTO();
		pItemDesc.setRecurrentAmt(pItemDesc.getGrossEffPrice());
		pItemDesc.setRecurrentAmtTxt("$" + pItemDesc.getGrossEffPrice());
		pItemDesc.setItemDesc(pItemDesc.getItemDisplayHtml());
		BeanUtils.copyProperties(pItemDesc, itemSummary);
		itemSummary.setBasketId(pItemDtl.getBasketId());
		itemSummary.setItemQty(pItemDtl.getItemQty());
		itemSummary.setIoInd(pItemDtl.getIoInd());
		itemSummary.setPaidVas(StringUtils.equals("Y", pItemDtl.getPaidVasInd()));
		
		if (StringUtils.isNotBlank(pItemDtl.getPenaltyHandling())) {
			itemSummary.setPenaltyHandling((String)this.penaltyHandleLtsLkupCacheService.get(pItemDtl.getPenaltyHandling()));
		} else {
			itemSummary.setPenaltyHandling("Free to go");
		}
		try {
			if (StringUtils.isNotBlank(pItemDtl.getExistEndDate())) {
				Date sysdate = new Date();
				Date tpExpDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(pItemDtl.getExistEndDate());				
				itemSummary.setExpired(tpExpDate.before(sysdate));
			} else {
				itemSummary.setExpired(false);
			}
		} catch (ParseException e) {}
		return itemSummary;
	}
	
	private ItemDetailSummaryDTO generateItemSummaryDTO(ItemDetailDTO pItemDesc, ItemDetailLtsDTO pItem) {
		
		ItemDetailSummaryDTO itemSummary = new ItemDetailSummaryDTO();
		BeanUtils.copyProperties(pItemDesc, itemSummary);
		itemSummary.setBasketId(pItem.getBasketId());
		itemSummary.setItemQty(pItem.getItemQty());
		itemSummary.setIoInd(pItem.getIoInd());
		itemSummary.setPaidVas(StringUtils.equals("Y", pItem.getPaidVasInd()));
		itemSummary.setItemAttbs(this.generateItemAttbSummaryDTOs(pItemDesc.getItemAttbs(), pItem.getItemAttbs()));
		itemSummary.setPenaltyHandling(pItem.getPenaltyHandling());

		try {
			if (StringUtils.isNotBlank(pItem.getExistEndDate())) {
				Date sysdate = new Date();
				Date tpExpDate = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(pItem.getExistEndDate());
				itemSummary.setExpired(tpExpDate.before(sysdate));
			} else {
				itemSummary.setExpired(false);
			}
		} catch (ParseException e) {}
		return itemSummary;
	}
	
	private ItemAttbDTO[] generateItemAttbSummaryDTOs(ItemAttbDTO[] pAttbDescs, ItemAttributeDetailLtsDTO[] pAttbValues) {
		
		if (ArrayUtils.isEmpty(pAttbValues)) {
			return null;
		}
		List<ItemAttbDTO> attbList = new ArrayList<ItemAttbDTO>();
		ItemAttbDTO attbSummary = null;
		
		for (int i=0; i < pAttbValues.length; ++i) {
			for (int j=0; pAttbDescs!=null && j<pAttbDescs.length; ++j) {
				if (StringUtils.equals(pAttbDescs[j].getAttbID(), pAttbValues[i].getAttbCd())) {
					attbSummary = new ItemAttbDTO();
					BeanUtils.copyProperties(pAttbDescs[j], attbSummary);
					attbSummary.setAttbValue(pAttbValues[i].getAttbValue());
					attbList.add(attbSummary);
					break;
				}
			}
		}
		return attbList.toArray(new ItemAttbDTO[attbList.size()]);
	}
	
	protected String getDisplayAddress(AddressDetailLtsDTO pAddress) {
		
		if (pAddress == null) {
			return "";
		}
		StringBuilder targetIa = new StringBuilder();
		if (StringUtils.isNotBlank(pAddress.getUnitNo())) {
			targetIa.append("FLAT ").append(pAddress.getUnitNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getFloorNo())) {
			targetIa.append(pAddress.getFloorNo()).append("/FLOOR ").append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getBuildNo())) {
			targetIa.append(pAddress.getBuildNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getHiLotNo())) {
			targetIa.append("LAND LOT NO ").append(pAddress.getHiLotNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getSectDesc())) {
			targetIa.append(pAddress.getSectDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getStrNo())) {
			targetIa.append(pAddress.getStrNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getStrName())) {
			targetIa.append(pAddress.getStrName()).append(" ");	
		}
		if (StringUtils.isNotBlank(pAddress.getStrCatDesc())) {
			targetIa.append(pAddress.getStrCatDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getDistDesc())) {
			targetIa.append(pAddress.getDistDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getAreaDesc())) {
			targetIa.append(pAddress.getAreaDesc()).append(", ");	
		}
		return targetIa.substring(0, targetIa.length()-2).toString();
	}
	
	protected String getDisplayPipbAddress(PipbLtsDTO pAddress) {
		
		if (pAddress == null) {
			return "";
		}
		StringBuilder targetIa = new StringBuilder();
		if (StringUtils.isNotBlank(pAddress.getUnitNo())) {
			targetIa.append("FLAT ").append(pAddress.getUnitNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getFloorNo())) {
			targetIa.append(pAddress.getFloorNo()).append("/FLOOR ").append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getBlockNo())) {
			targetIa.append("BLOCK ").append(pAddress.getBlockNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getBuildNo())) {
			targetIa.append(pAddress.getBuildNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getHiLotNo())) {
			targetIa.append("LAND LOT NO ").append(pAddress.getHiLotNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getSectDesc())) {
			targetIa.append(pAddress.getSectDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getStrNo())) {
			targetIa.append(pAddress.getStrNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getStrName())) {
			targetIa.append(pAddress.getStrName()).append(" ");	
		}
		if (StringUtils.isNotBlank(pAddress.getStrCatDesc())) {
			targetIa.append(pAddress.getStrCatDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getDistDesc())) {
			targetIa.append(pAddress.getDistDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(pAddress.getAreaDesc())) {
			targetIa.append(pAddress.getAreaDesc()).append(", ");	
		}
		return targetIa.substring(0, targetIa.length()-2).toString();
	}
	
	protected void fillCspInfo(LtsAcqServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder){
		ServiceDetailDTO service = LtsSbOrderHelper.getLtsService(pSbOrder);
		boolean regHkt = StringUtils.equals("Y", service.getAccount().getCustomer().getCsPortalInd());
		boolean regTheClub = StringUtils.equals("Y", service.getAccount().getCustomer().getTheClubInd());
		
		if(regHkt){
			//pSrvSummary.setCspNewReg(true);
			pSrvSummary.setCspEmail(service.getAccount().getCustomer().getCsPortalLogin());
			pSrvSummary.setCspMobile(service.getAccount().getCustomer().getCsPortalMobile());
		}else if(regTheClub && !regHkt){
			//pSrvSummary.setCspNewReg(true);
			pSrvSummary.setCspEmail(service.getAccount().getCustomer().getTheClubLogin());
			pSrvSummary.setCspMobile(service.getAccount().getCustomer().getTheClubMobile());
		}
	}

	public LtsPaymentService getLtsPaymentService() {
		return this.ltsPaymentService;
	}

	public void setLtsPaymentService(LtsPaymentService pLtsPaymentService) {
		this.ltsPaymentService = pLtsPaymentService;
	}

	public LtsOfferService getLtsOfferService() {
		return this.ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService pLtsOfferService) {
		this.ltsOfferService = pLtsOfferService;
	}

	public LtsBasketService getLtsBasketService() {
		return this.ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService pLtsBasketService) {
		this.ltsBasketService = pLtsBasketService;
	}

	public LtsDeviceService getLtsDeviceService() {
		return this.ltsDeviceService;
	}

	public void setLtsDeviceService(LtsDeviceService pLtsDeviceService) {
		this.ltsDeviceService = pLtsDeviceService;
	}

	public CodeLkupCacheService getCreditCardTyepCodeLkupCacheService() {
		return this.creditCardTyepCodeLkupCacheService;
	}

	public void setCreditCardTyepCodeLkupCacheService(
			CodeLkupCacheService pCreditCardTyepCodeLkupCacheService) {
		this.creditCardTyepCodeLkupCacheService = pCreditCardTyepCodeLkupCacheService;
	}

	public ServiceProfileLtsDrgService getServiceProfileLtsDrgService() {
		return serviceProfileLtsDrgService;
	}

	public void setServiceProfileLtsDrgService(ServiceProfileLtsDrgService serviceProfileLtsDrgService) {
		this.serviceProfileLtsDrgService = serviceProfileLtsDrgService;
	}

	public CodeLkupCacheService getIdDocTypeLkupCacheService() {
		return idDocTypeLkupCacheService;
	}

	public void setIdDocTypeLkupCacheService(
			CodeLkupCacheService idDocTypeLkupCacheService) {
		this.idDocTypeLkupCacheService = idDocTypeLkupCacheService;
	}

	public CodeLkupCacheService getPenaltyHandleLtsLkupCacheService() {
		return penaltyHandleLtsLkupCacheService;
	}

	public void setPenaltyHandleLtsLkupCacheService(
			CodeLkupCacheService penaltyHandleLtsLkupCacheService) {
		this.penaltyHandleLtsLkupCacheService = penaltyHandleLtsLkupCacheService;
	}

	public CodeLkupCacheService getRecontractModeCacheService() {
		return recontractModeCacheService;
	}

	public void setRecontractModeCacheService(
			CodeLkupCacheService recontractModeCacheService) {
		this.recontractModeCacheService = recontractModeCacheService;
	}

	public CodeLkupCacheService getRecontractSrvHandleCacheService() {
		return recontractSrvHandleCacheService;
	}

	public void setRecontractSrvHandleCacheService(
			CodeLkupCacheService recontractSrvHandleCacheService) {
		this.recontractSrvHandleCacheService = recontractSrvHandleCacheService;
	}

	public CodeLkupCacheService getErHandleCacheService() {
		return erHandleCacheService;
	}

	public void setErHandleCacheService(CodeLkupCacheService erHandleCacheService) {
		this.erHandleCacheService = erHandleCacheService;
	}

	public OrderDetailDAO getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDAO orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	public OrderDetailService getOrderDetailService() {
		return orderDetailService;
	}

	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}


	public CodeLkupCacheService getWaiveReasonLkupCacheService() {
		return waiveReasonLkupCacheService;
	}


	public void setWaiveReasonLkupCacheService(
			CodeLkupCacheService waiveReasonLkupCacheService) {
		this.waiveReasonLkupCacheService = waiveReasonLkupCacheService;
	}
}
