package com.bomwebportal.lts.service.order;

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
import com.bomwebportal.lts.dto.CollectDocDto;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.disconnect.DisconnectServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderAttbDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RecontractLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.order.ThirdPartyDetailLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ServiceProfileInventoryDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsDeviceService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsPaymentService;
import com.bomwebportal.lts.service.LtsRetrieveFsaService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsDrgService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsConstant.UpgradeOrderAttb;
import com.bomwebportal.lts.util.LtsCommonHelper;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public abstract class SummaryBaseServiceImpl implements SummaryBaseService {

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
	private CodeLkupCacheService erDelHandleCacheService;
	private CodeLkupCacheService erEyeHandleCacheService;
	private CodeLkupCacheService ltsWaiveReasonCacheService = null;
	private CodeLkupCacheService ltsDocTypeLkupCacheService = null;
	private CodeLkupCacheService waiveReasonLkupCacheService = null;
	private LtsOrderDocumentService ltsOrderDocumentService = null;
	private ImsServiceProfileAccessService imsServiceProfileAccessService;
	private LtsRetrieveFsaService ltsRetrieveFsaService;
	private LtsCommonService ltsCommonService;
	
	private OrderDetailDAO orderDetailDao = null;
	
	private static String[] ITEM_SRV_PLAN = new String[] {LtsConstant.ITEM_TYPE_PLAN, LtsConstant.ITEM_TYPE_VAS_2DEL_HOT};
	private static String[] ITEM_BB_RENTAL = new String[] {LtsConstant.ITEM_TYPE_BB_RENTAL, LtsConstant.ITEM_TYPE_INSTALL, LtsConstant.ITEM_TYPE_INSTALL_WAIVE};
	private static String[] ITEM_ER_CHARGE = new String[] {LtsConstant.ITEM_TYPE_ER_CHARGE};
	private static String[] ITEM_OTHER_CHARGE = new String[] {LtsConstant.ITEM_TYPE_ADMIN_CHARGE, LtsConstant.ITEM_TYPE_CANCEL_CHARGE};
	private static String[] ITEM_PE = new String[] {LtsConstant.ITEM_TYPE_PE_FREE};
	private static String[] ITEM_OPT_ACC = new String[] {LtsConstant.ITEM_TYPE_OPT_ACC, LtsConstant.ITEM_TYPE_FFP_OTHER, LtsConstant.ITEM_TYPE_FFP_STAFF, LtsConstant.ITEM_TYPE_FFP_HOT, LtsConstant.ITEM_TYPE_VAS_FFP};
	private static String[] ITEM_SOCKET = new String[] {LtsConstant.ITEM_TYPE_PE_SOCKET};
	private static String[] ITEM_CONTENT = new String[] {LtsConstant.ITEM_TYPE_CONTENT, LtsConstant.ITEM_TYPE_SELF_PICKUP, LtsConstant.ITEM_TYPE_FIELD_SERVICE};
	private static String[] ITEM_MOOV = new String[] {LtsConstant.ITEM_TYPE_MOOV};
	private static String[] ITEM_NOWTV = new String[] {LtsConstant.ITEM_TYPE_NOWTV_FREE, LtsConstant.ITEM_TYPE_NOWTV_CELE, LtsConstant.ITEM_TYPE_NOWTV_MIRR, LtsConstant.ITEM_TYPE_NOWTV_STAR, LtsConstant.ITEM_TYPE_NOWTV_SPT};
	private static String[] ITEM_NOWTV_SPEC = new String[] {LtsConstant.ITEM_TYPE_NOWTV_SPEC, LtsConstant.ITEM_TYPE_NOWTV_PAY};
	private static String[] ITEM_BILL = new String[] {LtsConstant.ITEM_TYPE_PAPER_BILL, LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE, LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE, LtsConstant.ITEM_TYPE_PAPER_BILL_BR, LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE, LtsConstant.ITEM_TYPE_KEEP_EXIST_BILL, LtsConstant.ITEM_TYPE_EBILL, LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL, LtsConstant.ITEM_TYPE_MYHKT_BILL, LtsConstant.ITEM_TYPE_THE_CLUB_BILL,LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL,LtsConstant.ITEM_TYPE_EBILL, LtsConstant.ITEM_TYPE_EMAIL_BILL};
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
	private static String[] ITEM_SMART_WARRANTY = new String[]{LtsBackendConstant.ITEM_TYPE_SMART_WARRANTY};
	private static String[] ITEM_DN_CHANGE = new String[]{LtsBackendConstant.ITEM_TYPE_DN_CHANGE};
	private static String[] ITEM_DNY_CHANGE = new String[]{LtsBackendConstant.ITEM_TYPE_DNY_CHANGE};
	private static String[] ITEM_DN_CHANGE_WAIVE = new String[]{LtsBackendConstant.ITEM_TYPE_DN_CHANGE_WAIVE};
	private static String[] ITEM_DNY_CHANGE_WAIVE = new String[]{LtsBackendConstant.ITEM_TYPE_DNY_CHANGE_WAIVE};
	private static String[] ITEM_EPD = new String[]{LtsConstant.ITEM_TYPE_EPD_ACCEPT, LtsConstant.ITEM_TYPE_EPD_FORFEIT, LtsConstant.ITEM_TYPE_EPD_UNDER_CONSIDERATION};
	
	public abstract ServiceSummaryDTO buildSummary(SbOrderDTO pSbOrder, String pLocale, boolean pIsEnquiry);
	
	
	protected void fillLtsSummaryDetail(ServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder, ServiceDetailLtsDTO pSrvDtlLts) {
		
		pSrvSummary.setSrvNum(pSrvDtlLts.getSrvNum().substring(4));
		pSrvSummary.setInstallAddr(this.getDisplayAddress(pSbOrder.getAddress()));
		pSrvSummary.setBlacklistAddrInd(StringUtils.equals("Y", pSbOrder.getAddress().getBlacklistInd()));
		pSrvSummary.setExtRelInd(pSrvDtlLts.getErInd());
		pSrvSummary.setWorkQueueType(pSrvDtlLts.getWorkQueueType());
		pSrvSummary.setPendingLtsOcid(pSrvDtlLts.getPendingOcid());
		pSrvSummary.setApplDate(LtsDateFormatHelper.getDateFromDTOFormat(pSbOrder.getAppDate()));
		pSrvSummary.setStaffPlanApplicantId(pSrvDtlLts.getStaffPlanApplicantId());
		pSrvSummary.setBundle2gNum(pSrvDtlLts.getBundle2gNum());
		pSrvSummary.setFieldVisitRequired(StringUtils.equals("Y", pSrvDtlLts.getForceFieldVisitInd()));
		pSrvSummary.setNewSrvNum(pSrvDtlLts.getNewSrvNum() != null ? pSrvDtlLts.getNewSrvNum().substring(4) : null);
		
		//ADD dn change info   Modified by Max.R.MENG  LTS
		if(StringUtils.equals(pSrvDtlLts.getFromSrvType(), LtsBackendConstant.LTS_SRV_TYPE_DEL)){
			ServiceDetailLtsDTO serviceDetail = LtsSbHelper.getDuplexChangeService(pSbOrder.getSrvDtls());
			pSrvSummary.setDuplexNum(serviceDetail != null ? serviceDetail.getSrvNum().substring(4) : null);
			pSrvSummary.setNewDuplexNum(serviceDetail != null ? serviceDetail.getNewSrvNum().substring(4) : null);
		}
		//ADD dn change info   Modified by Max.R.MENG  LTS END
		
		this.fillOrderAttbs(pSrvSummary, pSbOrder, pSrvDtlLts);
		this.fillBillingAddress(pSrvSummary, LtsSbHelper.getLatestBillingAddress(pSrvDtlLts));
	}
	
	private void fillOrderAttbs(ServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder, ServiceDetailLtsDTO pSrvDtlLts){
		if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(pSbOrder.getOrderType())){
			for(int i = 0; pSrvDtlLts.getOrderAttbs() != null && i < pSrvDtlLts.getOrderAttbs().length; i++){
				OrderAttbDTO attb = pSrvDtlLts.getOrderAttbs()[i];
				if(LtsCommonHelper.isValidEnum(UpgradeOrderAttb.class, attb.getAttbName())){
					switch(UpgradeOrderAttb.valueOf(attb.getAttbName())){
						case ALLOW_SELF_PICKUP_DEVICE:
							pSrvSummary.setAllowSelfPickup(attb.getAttbValue());
					}
				}
			}
		}
	}
	
	private void fillBillingAddress(ServiceSummaryDTO pSrvSummary, BillingAddressLtsDTO pBillAddr) {
		   if (pBillAddr == null) {
	            return;
	        }

	        if (StringUtils.equals(LtsConstant.BILLING_ADDR_ACTION_NEW, pBillAddr.getAction())
	                || (StringUtils.equals(LtsConstant.BILL_ADDR_ACTION_EXISTING, pBillAddr.getAction())
	                        && pSrvSummary instanceof DisconnectServiceSummaryDTO)) {

			StringBuilder addrSb = new StringBuilder();
			if (StringUtils.isNotEmpty(pBillAddr.getAddrLine1())) {
				addrSb.append(pBillAddr.getAddrLine1());
			addrSb.append("<p>");
			}
			if (StringUtils.isNotEmpty(pBillAddr.getAddrLine2())) {
				addrSb.append(pBillAddr.getAddrLine2());
				addrSb.append("<p>");
			}
			if (StringUtils.isNotEmpty(pBillAddr.getAddrLine3())) {
				addrSb.append(pBillAddr.getAddrLine3());
				addrSb.append("<p>");
			}
			if (StringUtils.isNotEmpty(pBillAddr.getAddrLine4())) {
				addrSb.append(pBillAddr.getAddrLine4());
				addrSb.append("<p>");
			}
			if (StringUtils.isNotEmpty(pBillAddr.getAddrLine5())) {
				addrSb.append(pBillAddr.getAddrLine5());
				addrSb.append("<p>");
			}
			if (StringUtils.isNotEmpty(pBillAddr.getAddrLine6())) {
				addrSb.append(pBillAddr.getAddrLine6());
			}
			pSrvSummary.setBillingAddress(addrSb.toString());
		}
		pSrvSummary.setBillingAddrInstantUpdateInd(pBillAddr.getInstantUpdateInd());
		pSrvSummary.setBillAddrChangeInd(pBillAddr.getAction());
	}
	
	protected void fillSrvStatusStateIms(SbOrderDTO pSbOrder, ServiceSummaryDTO pSrvSummary) {
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(pSbOrder);
		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(pSbOrder);

		if (imsService == null) {
			return;
		}
		StringBuilder messageSb = new StringBuilder();
		// Check whether the FSA Profile was changed in BOM 
		if( (LtsConstant.MODEM_TYPE_SHARE_EX_FSA.equals(imsService.getShareFsaType()) 
				|| LtsConstant.MODEM_TYPE_SHARE_OTHER_FSA.equals(imsService.getShareFsaType())) 
				&& !LtsConstant.MODEM_TYPE_2L2B.equals(imsService.getModemArrangement())) {
			FsaServiceDetailOutputDTO fsaProfile = imsServiceProfileAccessService.getServiceDetailByFSA(imsService.getSrvNum(), StringUtils.defaultIfEmpty(pSbOrder.getStaffNum(), "SB_SYS"));
			
			if (fsaProfile == null) {
				pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_PROFILE_OUTDATED);
				pSrvSummary.clearMessage();
				messageSb.append("FSA profile not found.  Please cancel this order.");
			}
			else {
				String existEyeType = "";
				if(!LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(ltsService.getFromProd())
						&& !LtsConstant.LTS_PRODUCT_TYPE_NEW.equals(ltsService.getFromProd())){
					existEyeType = StringUtils.lowerCase(ltsService.getFromProd());
				}
				imsServiceProfileAccessService.getFsaOfferProfile(fsaProfile, existEyeType);
				
				String existSrvType = ltsRetrieveFsaService.getExistSrvType(fsaProfile).getDesc();
				if (!StringUtils.equals(imsService.getExistTechnology(), fsaProfile.getTechnology())
						 || !StringUtils.equals(imsService.getExistSrvTypeCd(), existSrvType)) {
					pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_PROFILE_OUTDATED);
					pSrvSummary.clearMessage();
					messageSb.append("FSA profile outdated.  Please cancel this order.");	
				}
				//TODO check allow self-pick condition 
				if(ArrayUtils.isNotEmpty(ltsService.getItemDtls())){
					for (int i = 0; i < ltsService.getItemDtls().length; i++){
						if(LtsConstant.ITEM_TYPE_SELF_PICKUP.equals(ltsService.getItemDtls()[i].getItemType())){
							boolean forceFsFlag = false;
							if(!StringUtils.equals("Y", imsService.getShareRentalRouter())){

								if(ltsCommonService.validateFsaOfferForceFS(fsaProfile)){
									forceFsFlag = true;
								}
								
								if(StringUtils.equals(imsService.getShareBrmWifi(), "Y")){
									if(!ltsRetrieveFsaService.checkBrmWifiOffer(imsService.getSrvNum())){
										forceFsFlag = true;
									}
								}
								
							}
							
							if(forceFsFlag){
								pSrvSummary.setAllowSelfPickup("N");
								pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_PROFILE_OUTDATED);
								pSrvSummary.clearMessage();
								messageSb.append("FSA profile outdated, not allow to self-pickup device.  Please cancel this order.");	
							}
							break;
						}
					}
				}
			}
		}
		
		pSrvSummary.appendMessage(messageSb.toString());
	}
	
	protected void fillSrvStatusStateLts(SbOrderDTO pSbOrder, ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
		ServiceProfileInventoryDTO srvInventory = this.serviceProfileLtsDrgService.getServiceInventory(pSrvDtlLts.getSrvNum(), LtsConstant.SERVICE_TYPE_TEL);
		pSrvDtlLts.setFrozenExchInd(srvInventory.isFrozenExchInd() ? "Y" : null);
		pSrvDtlLts.setDnExchangeId(srvInventory.getDnExchangeId());
		StringBuilder messageSb = new StringBuilder();
		
		if (StringUtils.equals(LtsConstant.ORDER_STATUS_APPROVAL_REJECTED, pSrvDtlLts.getSbStatus())) {
			pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_APPROVAL_REJECTED);
			pSrvSummary.clearMessage();
			messageSb.append("Approval rejected.  Please cancel this order.");
		} 
		else if (StringUtils.equals("Y", pSbOrder.getBackDateInd()) && LtsDateFormatHelper.getDateDiffInDays(pSbOrder.getAppDate(), LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), "dd/MM/yyyy") > 120) {
			pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_BACKDATE_OVER_120);
			pSrvSummary.clearMessage();
			messageSb.append("Back Date over 120 days.  Please cancel this order.");
		}
		else if (StringUtils.equals("Y", pSrvDtlLts.getDiscFiveNaInd()) && LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(pSbOrder.getOrderType())) {
			pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_DISC_5NA);
			pSrvSummary.clearMessage();
			messageSb.append("5NA case,  order is forced to suspend.");
		}
		else if (StringUtils.equals("14", srvInventory.getInventStatus())
				&& !LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(pSbOrder.getOrderType())) {
			messageSb.append(pSrvDtlLts.getTypeOfSrv());
			messageSb.append(" ");
			messageSb.append(pSrvDtlLts.getSrvNum());
			messageSb.append(": Clear O/S amount first.  Order is forced to suspend, please recall order within 7 days for TOS.");
			pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_TOS);
			pSrvDtlLts.setTos(true);
		} 
		else if (LtsSbHelper.existApprovalItems(pSrvDtlLts) || LtsSbHelper.existApprovalCriteria(pSbOrder, pSrvDtlLts)
				|| LtsSbHelper.existApprovalFfpItems(pSrvDtlLts) || (LtsSbHelper.existApprovalDnChangeItems(pSrvDtlLts) && !StringUtils.equals(pSbOrder.getCreateChannel(),"RS"))) {
			pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_APPROVAL);
		}
		
		pSrvSummary.appendMessage(messageSb.toString());
	}
	
	protected void fillSrvStatusState(String pOrderId, ServiceSummaryDTO pSrvSummary, ServiceDetailDTO pSrvDtl) {
		
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
			pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_PENDING_ORD);
		}
	}
	
	protected void fillAccountDetail(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
		if (pSrvDtlLts.getRecontract() != null) {
			this.fillLtsRecontractCustomerDetail(pSrvSummary, pSrvDtlLts);
		} else {
			this.fillLtsCustomerDetail(pSrvSummary, pSrvDtlLts);
		}
	}
	
	protected void fillLtsCustomerDetail(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
		pSrvSummary.setDocNum(pSrvDtlLts.getActualDocId());
		pSrvSummary.setDocType((String)this.idDocTypeLkupCacheService.get(pSrvDtlLts.getActualDocType()));
		AccountDetailLtsDTO account	 = pSrvDtlLts.getAccount();
		
		if (account == null) {
			return;
		}
		pSrvSummary.setAcctNum(account.getAcctNo());
		pSrvSummary.setRedemMedia(account.getRedemptionMedia());
		pSrvSummary.setRedemptionEmail(account.getRedemptionEmailAddr());
		pSrvSummary.setRedemptionSms(account.getRedemptionSmsNo());
		
		CustomerDetailLtsDTO customer = pSrvDtlLts.getAccount().getCustomer();
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
//		if("Y".equals(pSrvDtlLts.getRecontractInd())){
//			this.fillLtsPaymentDetail(pSrvSummary, pSrvDtlLts.getRecontract().getFuturePayment(), pSrvDtlLts.getRecontract().getExistPayment());
//		}else{
			this.fillLtsPaymentDetail(pSrvSummary, account.getFuturePayment(), account.getExistPayment());
//		}
	}
	
	private void fillLtsRecontractCustomerDetail(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
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
		pSrvSummary.setTransfereeCompanyName(recontract.getCompanyName());

		pSrvSummary.setAcctNum(recontract.getAcctNum());
		AccountDetailLtsDTO recontractAccount = pSrvDtlLts.getAccount();
		if(recontractAccount != null && StringUtils.equals(recontractAccount.getAcctNo(), recontract.getAcctNum())){
			pSrvSummary.setRedemMedia(recontractAccount.getRedemptionMedia());
			pSrvSummary.setRedemptionEmail(recontractAccount.getRedemptionEmailAddr());
			pSrvSummary.setRedemptionSms(recontractAccount.getRedemptionSmsNo());
		}
		
		pSrvSummary.setDeceasedType(pSrvDtlLts.getDeceaseType());
		
		AccountDetailLtsDTO profileAccount = pSrvDtlLts.getProfileAccount();
		
		if (profileAccount == null) {
			return;
		}
		
		if (StringUtils.isNotEmpty(pSrvDtlLts.getDeceaseType())) {
			pSrvSummary.setTransferorNewBillingName(profileAccount.getAcctName());
			if (profileAccount.getBillingAddress() != null) {
				pSrvSummary.setTransferorNewBillingAddr(profileAccount.getBillingAddress().getFullBillAddr());	
			}
		}
		
//		pSrvSummary.setTransferorAcctNum(profileAccount.getAcctNo());
		
		CustomerDetailLtsDTO customer = profileAccount.getCustomer();
		
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
		
		this.fillLtsPaymentDetail(pSrvSummary, pSrvDtlLts.getRecontract().getFuturePayment(), pSrvDtlLts.getRecontract().getExistPayment());
	}
	
	private void fillLtsPaymentDetail(ServiceSummaryDTO pSrvSummary, PaymentMethodDetailLtsDTO pFuturePayment, PaymentMethodDetailLtsDTO pExistPayment) {

		if (pFuturePayment != null) {
			pSrvSummary.setThirdPtyPayment(StringUtils.equals("Y", pFuturePayment.getThirdPartyInd()));	
		}
		
		if (pFuturePayment != null 
				&& (StringUtils.equals(LtsConstant.IO_IND_INSTALL, pFuturePayment.getAction()))) {
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
		} else if (pExistPayment != null) {
			pSrvSummary.setPaymentMethod(pExistPayment.getPayMtdType());
			pSrvSummary.setPaymentChangeInd("N");
		}
		
	}
	
	protected void fillThirdPartyDetail(ServiceSummaryDTO pSrvSummary, ThirdPartyDetailLtsDTO pThirdPtyDtl) {
		
		if (pThirdPtyDtl == null) {
			return;
		}
		pSrvSummary.setThirdPtyName(pThirdPtyDtl.getTitle() + " " + pThirdPtyDtl.getAppntLastName() + " " + pThirdPtyDtl.getAppntFirstName());
		pSrvSummary.setThirdPtyDocId(pThirdPtyDtl.getAppntDocId());
		pSrvSummary.setThirdPtyDocType((String)this.idDocTypeLkupCacheService.get(pThirdPtyDtl.getAppntDocType()));
		pSrvSummary.setThirdPtyRelation(LtsSbHelper.getRelationshipDesc(pThirdPtyDtl.getRelationshipCode()));
		pSrvSummary.setThirdPtyContactNum(pThirdPtyDtl.getAppntContactNum());
	}
	
	protected void fillAppoinmentDetail(ServiceSummaryDTO pSrvSummary, AppointmentDetailLtsDTO pAppt) {
		
		if (pAppt == null) {
			return;
		}
		pSrvSummary.setApptContactName(pAppt.getInstContactName());
		pSrvSummary.setApptContactNum(pAppt.getInstContactNum());
		pSrvSummary.setApptMobileContactNum(pAppt.getInstContactMobile());

		pSrvSummary.setInstallDate(LtsDateFormatHelper.getDateFromDTOFormat(pAppt.getAppntStartTime()));
		pSrvSummary.setInstallTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(pAppt.getAppntStartTime(), pAppt.getAppntEndTime())));
		pSrvSummary.setPrewiringDate(LtsDateFormatHelper.getDateFromDTOFormat(pAppt.getPreWiringStartTime()));
		pSrvSummary.setPrewiringTime(LtsDateFormatHelper.getFromToTimeFormat(pAppt.getPreWiringStartTime(), pAppt.getPreWiringEndTime()));
		pSrvSummary.setSwitchDate(LtsDateFormatHelper.getDateFromDTOFormat(pAppt.getCutOverStartTime()) + " " + LtsDateFormatHelper.getFromToTimeFormat(pAppt.getCutOverStartTime(), pAppt.getCutOverEndTime()));
	}
	
	protected void fillContactDetail(ServiceSummaryDTO pSrvSummary, ContactLtsDTO pContact) {
		
		if (pContact == null) {
			return;
		}
		
		pSrvSummary.setCustMobileContactNum(pContact.getContactMobile());
		pSrvSummary.setCustFixContactNum(pContact.getContactFixedLine());	
		pSrvSummary.setCustContactEmail(pContact.getEmailAddr());
	}
	
	
	protected void fillItemDisplay(SbOrderDTO pSbOrder, ServiceSummaryDTO pSrvSummary, ItemDetailLtsDTO[] pItems, String pLocale) {
		
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
				} else if (LtsSbOrderHelper.isContainString(ITEM_SMART_WARRANTY, itemDtlSummary.getItemType())){ 
					pSrvSummary.addSmartWrtyItem(itemDtlSummary);
				} else if(LtsSbOrderHelper.isContainString(ITEM_DN_CHANGE, itemDtlSummary.getItemType())){
					pSrvSummary.addDnChangeItem(itemDtlSummary);
				} else if(LtsSbOrderHelper.isContainString(ITEM_DNY_CHANGE, itemDtlSummary.getItemType())){
					pSrvSummary.addDnyChangeItem(itemDtlSummary);
				} else if(LtsSbOrderHelper.isContainString(ITEM_DN_CHANGE_WAIVE, itemDtlSummary.getItemType())){
					pSrvSummary.addDnChangeItem(itemDtlSummary);
				} else if(LtsSbOrderHelper.isContainString(ITEM_DNY_CHANGE_WAIVE, itemDtlSummary.getItemType())){
					pSrvSummary.addDnyChangeItem(itemDtlSummary);
				} else if(LtsSbOrderHelper.isContainString(ITEM_EPD, itemDtlSummary.getItemType())){
					pSrvSummary.setEpdItem(itemDtlSummary);
				}
				
				else if (LtsSbOrderHelper.isContainString(ITEM_ER_CHARGE, itemDtlSummary.getItemType())) {
					if (StringUtils.isNotBlank(itemDtlSummary.getPenaltyHandling())) {
						ServiceDetailDTO ltsService = LtsSbOrderHelper.getLtsService(pSbOrder);
						String erHandle = LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(ltsService.getFromProd()) ?
								(String)this.erDelHandleCacheService.get(itemDtlSummary.getPenaltyHandling()) :
									(String)this.erEyeHandleCacheService.get(itemDtlSummary.getPenaltyHandling());
						itemDtlSummary.setPenaltyHandling(erHandle);
					} else {
						itemDtlSummary.setPenaltyHandling("Free to go");
					}
					pSrvSummary.addErChargeItem(itemDtlSummary);
				}
			}
			
			if (LtsConstant.ITEM_TYPE_EMAIL_BILL.equals(itemDtlSummary.getItemType())) {
				ServiceDetailDTO primaryService = LtsSbOrderHelper.getLtsService(pSbOrder);
				pSrvSummary.setLtsEmailBillAddr(primaryService.getAccount().getEmailAddr());
			}
			if(LtsConstant.ITEM_TYPE_PAPER_BILL.equals(itemDtlSummary.getItemType())
					|| LtsConstant.ITEM_TYPE_PAPER_BILL_BR.equals(itemDtlSummary.getItemType())
					|| LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE.equals(itemDtlSummary.getItemType())
					|| LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE.equals(itemDtlSummary.getItemType())){
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
				
				if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(pSbOrder.getOrderType())) {
					itemDtlSummary.setImagePath(this.ltsDeviceService.getEyeDevice(this.ltsBasketService.getBasket(itemDtlSummary.getBasketId(), pLocale, LtsConstant.DISPLAY_TYPE_RP_PROMOT).getType(), pLocale).getImagePath());	
				}
			}
			if (StringUtils.equals(LtsConstant.IO_IND_OUT, itemDtlSummary.getIoInd())
					&& LtsSbOrderHelper.isContainString(ITEM_OUT_LTS, itemDtlSummary.getItemType())
					&& !itemDtlSummary.isForceRetain())   {
				
				if (StringUtils.isNotBlank(itemDescList.get(i).getPenaltyHandling())) {
					itemDtlSummary.setPenaltyHandling((String)this.penaltyHandleLtsLkupCacheService.get(itemDescList.get(i).getPenaltyHandling()));
				} else {
					itemDtlSummary.setPenaltyHandling("Free to go");
				}
				pSrvSummary.addOutLtsItem(itemDtlSummary);
			}
			if(StringUtils.equals(LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL, itemDtlSummary.getItemType())){
				
				ServiceDetailLtsDTO srvDtl = LtsSbOrderHelper.getLtsService(pSbOrder);
				if(StringUtils.equals(LtsConstant.CSP_DUMMY_EMAIL_DOMAIN, srvDtl.getAccount().getCustomer().getCsPortalLogin())
						||StringUtils.equals(LtsConstant.CSP_DUMMY_MOBILE_NUM, srvDtl.getAccount().getCustomer().getCsPortalMobile())){
					itemDtlSummary.setItemDisplayHtml(itemDtlSummary.getItemDisplayHtml() + "<br><b>(My HKT account is not registered as dummy Email/Mobile Number is used.)</b>");
				}
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
						&& LtsSbOrderHelper.isContainString(ITEM_OUT_LTS, itemDtlSummary.getItemType())
						&& !itemDtlSummary.isForceRetain()) {
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
		itemSummary.setForceRetain(StringUtils.equals("Y", pItemDtl.getForceRetain()));
		
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
		itemSummary.setForceRetain(StringUtils.equals("Y", pItem.getForceRetain()));

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
	
	private String getDisplayAddress(AddressDetailLtsDTO pAddress) {
		
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
	
	protected String billMediaConvertFromCdToDesc (String pBillMediaCd){
		
		if(StringUtils.equals(pBillMediaCd, LtsConstant.LTS_BILL_MEDIA_PPS_BILL)){
			return "PPS";
		}
		
		else if(StringUtils.equals(pBillMediaCd, LtsConstant.LTS_BILL_MEDIA_DEVICE_BILL)){
			return "Device Bill";
		}
		
		else if(StringUtils.equals(pBillMediaCd, LtsConstant.LTS_BILL_MEDIA_ONLINE_BILL)){
			return "Online Bill";
		}
		
		else if(StringUtils.equals(pBillMediaCd, LtsConstant.LTS_BILL_MEDIA_PAPER_BILL)){
			return "Paper Bill";
		}
		
		return null;
	}

	protected void fillDuplexDetail(ServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder) {
		
		ServiceDetailDTO[] srvDtls = pSbOrder.getSrvDtls();
		
		for (int i=0; i < srvDtls.length; ++i) {
			if (srvDtls[i] instanceof ServiceDetailLtsDTO 
					&& LtsConstant.LTS_SRV_TYPE_REMOVE.equals(((ServiceDetailLtsDTO)srvDtls[i]).getToSrvType())) {
				if (LtsConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO)srvDtls[i]).getFromSrvType())) {
					pSrvSummary.setCancelDuplexType("DNX");
					pSrvSummary.setCancelDuplexDn(srvDtls[i].getSrvNum());	
				}
				if (LtsConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO)srvDtls[i]).getFromSrvType())) {
					pSrvSummary.setCancelDuplexType("DNY");
					pSrvSummary.setCancelDuplexDn(srvDtls[i].getSrvNum());	
				}
			}
		}
	}
	
	protected void fillESignDetail(ServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder){
		pSrvSummary.setDisMode(pSbOrder.getDisMode());
		pSrvSummary.setCollectMethod(pSbOrder.getCollectMethod());
		pSrvSummary.setEsigEmailAddr(pSbOrder.getEsigEmailAddr());
		pSrvSummary.setEsigEmailLang(pSbOrder.getEsigEmailLang());
//		pSrvSummary.setAllOrdDocAssgnsList(Arrays.asList(pSbOrder.getAllOrdDocAssgns()));
//		List<AllOrdDocAssgnLtsDTO> ordDocAssgnList = new ArrayList<AllOrdDocAssgnLtsDTO>();
		
		if (ArrayUtils.isEmpty(pSbOrder.getAllOrdDocAssgns())) {
			return;
		}
		
//		for(AllOrdDocAssgnLtsDTO ordDocAssgn: pSbOrder.getAllOrdDocAssgns()){
//			AllOrdDocAssgnLtsDTO copyOrdDocAssgn = new AllOrdDocAssgnLtsDTO();
//			copyOrdDocAssgn.setDocType((String) ltsDocTypeLkupCacheService.get(ordDocAssgn.getDocType()));
//			copyOrdDocAssgn.setMarkDelInd(ordDocAssgn.getMarkDelInd());
//			copyOrdDocAssgn.setWaiveReason((String) ltsWaiveReasonCacheService.get(ordDocAssgn.getWaiveReason()));
//			copyOrdDocAssgn.setCollectedInd(ordDocAssgn.getCollectedInd());
//			ordDocAssgnList.add(copyOrdDocAssgn);
//		}
//		pSrvSummary.setAllOrdDocAssgnsList(ordDocAssgnList);
		
		
		List<CollectDocDto> collectDocList = new ArrayList<CollectDocDto>();
		HashMap<String, String> faxSerialMap = ltsOrderDocumentService.getFaxSerialMap(pSbOrder.getOrderId()); // new HashMap<String, String>();
		for(AllOrdDocAssgnLtsDTO ordDocAssgn: pSbOrder.getAllOrdDocAssgns()){
			OrderDocDTO orderDocument = ltsOrderDocumentService.getOrderDoc(ordDocAssgn.getDocType());
			
			CollectDocDto collectDoc = new CollectDocDto();
			collectDoc.setDocType(ordDocAssgn.getDocType());
			collectDoc.setDocTypeDisplay((String) ltsDocTypeLkupCacheService.get(ordDocAssgn.getDocType()));
			collectDoc.setWaiveReason(ordDocAssgn.getWaiveReason());
			collectDoc.setWaiveReasonDisplay((String) ltsWaiveReasonCacheService.get(ordDocAssgn.getWaiveReason()));
			collectDoc.setMarkDelInd(ordDocAssgn.getMarkDelInd());
			collectDoc.setCollectedInd(ordDocAssgn.getCollectedInd());
			collectDoc.setFaxSerial(faxSerialMap.get(ordDocAssgn.getDocType()));
			collectDoc.setMandatory(orderDocument != null ? StringUtils.equalsIgnoreCase("M", orderDocument.getMdoInd()) : false);
			collectDocList.add(collectDoc);
		}
		
		pSrvSummary.setCollectDocList(collectDocList);
	}
	
	protected void fillCspInfo(ServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder){
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
	
	
	protected void fillCustPdpo(ServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder) {
		ServiceDetailDTO service = LtsSbOrderHelper.getLtsService(pSbOrder);
		CustOptOutInfoLtsDTO custOptoutInfo = service.getAccount().getCustomer().getCustOptOutInfo();
		
		if (custOptoutInfo == null) {
			return;
		}
		
		pSrvSummary.setCustOptOutInd(custOptoutInfo.getOptInd());
		pSrvSummary.setUpdBomStatus(custOptoutInfo.getUpdBomStatus());
		pSrvSummary.setLtsPdpoBillInsert(StringUtils.equals("Y", custOptoutInfo.getBillInsert()));
		pSrvSummary.setLtsPdpoBm(StringUtils.equals("Y", custOptoutInfo.getBm()));
		pSrvSummary.setLtsPdpoCdOutdial(StringUtils.equals("Y", custOptoutInfo.getCdOutdial()));
		pSrvSummary.setLtsPdpoDirectMailing(StringUtils.equals("Y", custOptoutInfo.getDirectMailing()));
		pSrvSummary.setLtsPdpoEmail(StringUtils.equals("Y", custOptoutInfo.getEmail()));
		pSrvSummary.setLtsPdpoOutbound(StringUtils.equals("Y", custOptoutInfo.getOutbound()));
		pSrvSummary.setLtsPdpoSms(StringUtils.equals("Y", custOptoutInfo.getSms()));
		pSrvSummary.setLtsPdpoSmsEye(StringUtils.equals("Y", custOptoutInfo.getSmsEye()));
		pSrvSummary.setLtsPdpoWebBill(StringUtils.equals("Y", custOptoutInfo.getWebBill()));
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

	public CodeLkupCacheService getErDelHandleCacheService() {
		return erDelHandleCacheService;
	}

	public void setErDelHandleCacheService(
			CodeLkupCacheService erDelHandleCacheService) {
		this.erDelHandleCacheService = erDelHandleCacheService;
	}

	public CodeLkupCacheService getErEyeHandleCacheService() {
		return erEyeHandleCacheService;
	}

	public void setErEyeHandleCacheService(
			CodeLkupCacheService erEyeHandleCacheService) {
		this.erEyeHandleCacheService = erEyeHandleCacheService;
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
	
	public CodeLkupCacheService getLtsDocTypeLkupCacheService() {
		return ltsDocTypeLkupCacheService;
	}

	public void setLtsDocTypeLkupCacheService(
			CodeLkupCacheService ltsDocTypeLkupCacheService) {
		this.ltsDocTypeLkupCacheService = ltsDocTypeLkupCacheService;
	}

	public CodeLkupCacheService getLtsWaiveReasonCacheService() {
		return ltsWaiveReasonCacheService;
	}

	public void setLtsWaiveReasonCacheService(
			CodeLkupCacheService ltsWaiveReasonCacheService) {
		this.ltsWaiveReasonCacheService = ltsWaiveReasonCacheService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}


	public CodeLkupCacheService getWaiveReasonLkupCacheService() {
		return waiveReasonLkupCacheService;
	}


	public void setWaiveReasonLkupCacheService(
			CodeLkupCacheService waiveReasonLkupCacheService) {
		this.waiveReasonLkupCacheService = waiveReasonLkupCacheService;
	}


	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}


	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}


	public LtsRetrieveFsaService getLtsRetrieveFsaService() {
		return ltsRetrieveFsaService;
	}


	public void setLtsRetrieveFsaService(LtsRetrieveFsaService ltsRetrieveFsaService) {
		this.ltsRetrieveFsaService = ltsRetrieveFsaService;
	}


	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}


	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	
}
