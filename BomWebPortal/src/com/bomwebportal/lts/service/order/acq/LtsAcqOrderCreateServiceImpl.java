package com.bomwebportal.lts.service.order.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.dao.CodeLkupDAO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.ItemAttbBaseDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetAttbDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO.LtsAppointmentDetail;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketVasDetailFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO.BillMediaDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillingAddressFormDTO.BillingAddrDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Selection;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPersonalInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSupportDocFormDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AddressInventoryDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerIguardRegDTO;
import com.bomwebportal.lts.dto.order.ImsL2JobDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PipbLtsDTO;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ResDnLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.order.StaffInfoLtsDTO;
import com.bomwebportal.lts.dto.order.ThirdPartyDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.service.OfferChangeService;
import com.bomwebportal.lts.service.order.CallPlanLtsService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;

public class LtsAcqOrderCreateServiceImpl implements LtsAcqOrderCreateService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected LtsBasketService ltsBasketService;
	protected LtsOfferService ltsOfferService;
	protected LtsAppointmentService ltsAppointmentService;
//	protected CodeLkupCacheService eye2GCallPlanLkupCacheService;
	protected LtsCommonService ltsCommonService;
	protected LtsSalesInfoService ltsSalesInfoService;
	protected CodeLkupCacheService premierCustTagLkupCacheService;
	protected CallPlanLtsService callPlanLtsService;
	protected OfferChangeService offerChangeService;
	protected CodeLkupCacheService rentalRouterL2JobCacheService;
	protected CodeLkupCacheService osTypeIosL2JobCacheService;
	protected CodeLkupCacheService brmL2JobCacheService;
	protected CodeLkupCacheService brmViL2JobCacheService;
	
/*	public CodeLkupCacheService getEye2GCallPlanLkupCacheService() {
		return eye2GCallPlanLkupCacheService;
	}

	public void setEye2GCallPlanLkupCacheService(
			CodeLkupCacheService eye2gCallPlanLkupCacheService) {
		eye2GCallPlanLkupCacheService = eye2gCallPlanLkupCacheService;
	}
*/
	
	public OfferChangeService getOfferChangeService() {
		return offerChangeService;
	}

	public CodeLkupCacheService getRentalRouterL2JobCacheService() {
		return rentalRouterL2JobCacheService;
	}

	public void setRentalRouterL2JobCacheService(
			CodeLkupCacheService rentalRouterL2JobCacheService) {
		this.rentalRouterL2JobCacheService = rentalRouterL2JobCacheService;
	}

	public void setOfferChangeService(OfferChangeService offerChangeService) {
		this.offerChangeService = offerChangeService;
	}

	public CallPlanLtsService getCallPlanLtsService() {
		return callPlanLtsService;
	}

	public void setCallPlanLtsService(CallPlanLtsService callPlanLtsService) {
		this.callPlanLtsService = callPlanLtsService;
	}
	
	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}

	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}
	

	public CodeLkupCacheService getOsTypeIosL2JobCacheService() {
		return osTypeIosL2JobCacheService;
	}

	public void setOsTypeIosL2JobCacheService(
			CodeLkupCacheService osTypeIosL2JobCacheService) {
		this.osTypeIosL2JobCacheService = osTypeIosL2JobCacheService;
	}


	public CodeLkupCacheService getBrmL2JobCacheService() {
		return brmL2JobCacheService;
	}

	public void setBrmL2JobCacheService(CodeLkupCacheService brmL2JobCacheService) {
		this.brmL2JobCacheService = brmL2JobCacheService;
	}

	public CodeLkupCacheService getBrmViL2JobCacheService() {
		return brmViL2JobCacheService;
	}

	public void setBrmViL2JobCacheService(
			CodeLkupCacheService brmViL2JobCacheService) {
		this.brmViL2JobCacheService = brmViL2JobCacheService;
	}



	protected enum CreateServiceType {
		CREATE_SRV_TYPE_ACQ_EYE,
		CREATE_SRV_TYPE_ACQ_DEL,
		CREATE_SRV_TYPE_FSA,
		CREATE_SRV_TYPE_ACQ_2DEL,
		CREATE_SRV_TYPE_ACQ_PORT_LATER;
//		CREATE_SRV_TYPE_DUPLEX_TO_2DEL;
	}
	
	public SbOrderDTO createSbOrder(AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {
		
		Map<String, AccountDetailLtsDTO> accountDetailLtsMap = new HashMap<String, AccountDetailLtsDTO>();
		Map<String, CustomerDetailLtsDTO> customerDetailLtsMap = new HashMap<String, CustomerDetailLtsDTO>();
		String orderType = LtsConstant.ORDER_TYPE_SB_ACQUISITION;
		
		SbOrderDTO sbOrder = new SbOrderDTO();
		sbOrder.setOrderType(orderType);
		sbOrder.setAppDate(DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")));
		sbOrder.setAddress(createAddressDetailLts(acqOrderCapture));
		sbOrder.setLob(LtsConstant.LOB_LTS);
		sbOrder.setSrvDtls(createServiceDetail(acqOrderCapture, accountDetailLtsMap, customerDetailLtsMap, orderType));
		sbOrder.setAccounts(accountDetailLtsMap.values().toArray(new AccountDetailLtsDTO[accountDetailLtsMap.size()]));
		sbOrder.setCustomers(customerDetailLtsMap.values().toArray(new CustomerDetailLtsDTO[customerDetailLtsMap.size()]));		
		setOrderSalesInfo(sbOrder, acqOrderCapture, bomSalesUser);
		setOrderSupportDoc(sbOrder, acqOrderCapture, bomSalesUser);
		setResDn(sbOrder, acqOrderCapture);
//		if((acqOrderCapture.getLtsAcqPersonalInfoForm().getContactEmail() != null && StringUtils.isNotBlank(acqOrderCapture.getLtsAcqPersonalInfoForm().getContactEmail())) ||
//				(acqOrderCapture.getLtsAcqPersonalInfoForm().getMobileNo() != null && StringUtils.isNotBlank(acqOrderCapture.getLtsAcqPersonalInfoForm().getMobileNo())) ||
//						(acqOrderCapture.getLtsAcqPersonalInfoForm().getFixedLineNo() != null && StringUtils.isNotBlank(acqOrderCapture.getLtsAcqPersonalInfoForm().getFixedLineNo()))){
		setContactInfo(sbOrder, acqOrderCapture);
//		}
		setPrepay(sbOrder, acqOrderCapture);
		setDsInfo(sbOrder, acqOrderCapture, bomSalesUser);
		setCustIguard(sbOrder, acqOrderCapture);
		return sbOrder;
	}
	
	
	public void setCustIguard(SbOrderDTO pSbOrder, AcqOrderCaptureDTO pOrderCapture){
		CustomerIguardRegDTO custIguardReg = new CustomerIguardRegDTO();
		BillMediaDtl tempBillMediaDtl;
		tempBillMediaDtl = pOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl();
		String carecashExistOpt = pOrderCapture.getLtsAcqBillMediaBillLangForm().getCarecashExistOpt();
		//boolean showIguard = (!StringUtils.isNotBlank(carecashExistOpt) || StringUtils.isNotBlank(carecashExistOpt) && carecashExistOpt.trim().equalsIgnoreCase("N"));
		boolean showIguard = pOrderCapture.getLtsAcqBillMediaBillLangForm().isShowIguard();
		
		if(!showIguard)
		{
			if(carecashExistOpt.equalsIgnoreCase("I"))
			{
				custIguardReg.setCarecashInd("A");
			}
			else if(carecashExistOpt.equalsIgnoreCase("O") || carecashExistOpt.equalsIgnoreCase("G"))
			{
				custIguardReg.setCarecashInd("X");
			}
			else
			{
				custIguardReg = null;
			}
		}
		else
		{
			String tempCarecashRegisterOption = tempBillMediaDtl.getCarecashRegisterOption();
			if(tempCarecashRegisterOption==null)
			{
				custIguardReg = null;
			}
			else
			{
				if(tempCarecashRegisterOption.equalsIgnoreCase("R"))
				{
					custIguardReg.setCarecashInd("I");
					custIguardReg.setEmailAddr(tempBillMediaDtl.getCarecashEmail());
					custIguardReg.setContactNum(tempBillMediaDtl.getCarecashContactNo());
					if(StringUtils.isNotBlank(tempBillMediaDtl.getCarecashOptInOutInd()))
					{
						if(tempBillMediaDtl.getCarecashOptInOutInd().equalsIgnoreCase("O"))
						{
							custIguardReg.setCarecashDmInd("O");
						}
						else
						{
							custIguardReg.setCarecashDmInd("I");
						}
					}
					else
					{
						custIguardReg.setCarecashDmInd("I");
					}
				}
				else if(tempCarecashRegisterOption.equalsIgnoreCase("N"))
				{
					custIguardReg.setCarecashInd("O");
				}
				else
				{
					custIguardReg.setCarecashInd("P");
				}
			}						
		}
		pSbOrder.setCustIguardReg(custIguardReg);
	}
	
	public void setPrepay(SbOrderDTO pSbOrder, AcqOrderCaptureDTO pOrderCapture){
		List<PrepayLtsDTO> prepayList = new ArrayList<PrepayLtsDTO>();
		PrepayLtsDTO prepayLtsDTO = null;
	
		for(PaymentMethodDtl payMtdDtl: pOrderCapture.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList()){
//			String cardprefix = payMtdDtl.getCardNum().substring(0,4);
			if(LtsConstant.PAYMENT_TYPE_CASH.equals(payMtdDtl.getNewPayMethodType())
					&& payMtdDtl.getCashPrePayItem() != null
					&& payMtdDtl.getCashPrePayItem().isSelected()){
				prepayLtsDTO = new PrepayLtsDTO();
				prepayLtsDTO.setAcctNo(payMtdDtl.getAcctNum());
				prepayLtsDTO.setPrepayType("P");
				prepayLtsDTO.setPrepayAmt(payMtdDtl.getCashPrePayItem().getOneTimeAmt());
			}else if(LtsConstant.PAYMENT_TYPE_AUTO_PAY.equals(payMtdDtl.getNewPayMethodType())
					&& payMtdDtl.getAutopayPrePayItem() != null
					&& payMtdDtl.getAutopayPrePayItem().isSelected()){
				prepayLtsDTO = new PrepayLtsDTO();
				prepayLtsDTO.setAcctNo(payMtdDtl.getAcctNum());
				prepayLtsDTO.setPrepayType("P");
				prepayLtsDTO.setPrepayAmt(payMtdDtl.getAutopayPrePayItem().getOneTimeAmt());
			}else if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(payMtdDtl.getNewPayMethodType())
					&& !StringUtils.startsWith(payMtdDtl.getCardNum(), LtsConstant.TNG_CARD_PREFIX)
					&& payMtdDtl.getCreditCardPrePayItem() != null
					&& payMtdDtl.getCreditCardPrePayItem().isSelected()){
				prepayLtsDTO = new PrepayLtsDTO();
				prepayLtsDTO.setAcctNo(payMtdDtl.getAcctNum());
				prepayLtsDTO.setPrepayType("P");
				prepayLtsDTO.setPaymentStatus("C");
				prepayLtsDTO.setPrepayAmt(payMtdDtl.getCreditCardPrePayItem().getOneTimeAmt());
			}else if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(payMtdDtl.getNewPayMethodType())
					&& StringUtils.startsWith(payMtdDtl.getCardNum(), LtsConstant.TNG_CARD_PREFIX)
					&& payMtdDtl.getCashPrePayItem() != null
					&& payMtdDtl.getCashPrePayItem().isSelected()){
				prepayLtsDTO = new PrepayLtsDTO();
				prepayLtsDTO.setAcctNo(payMtdDtl.getAcctNum());
				prepayLtsDTO.setPrepayType("P");
				prepayLtsDTO.setPaymentStatus("C");
				prepayLtsDTO.setPrepayAmt(payMtdDtl.getCashPrePayItem().getOneTimeAmt());
			}
			prepayList.add(prepayLtsDTO);
		}
		
		PrepayLtsDTO[] prepay = new PrepayLtsDTO[prepayList.size()];
		
		pSbOrder.setPrepay(prepayList.toArray(prepay));
	}
	
	public void setDsInfo(SbOrderDTO pSbOrder, AcqOrderCaptureDTO pOrderCapture, BomSalesUserDTO pBomSalesUser){
		
		if(pOrderCapture.isChannelDirectSales()){
			if(pOrderCapture.getLtsDsOrderInfo()!=null){
				if(pOrderCapture.getLtsAcqAppointmentForm()!=null){
					pOrderCapture.getLtsDsOrderInfo().setPeLink(BooleanUtils.isTrue(pOrderCapture.getLtsAcqAppointmentForm().getPeLinkInd()) ? "Y":"N");
					pOrderCapture.getLtsDsOrderInfo().setWaiveCloff(pOrderCapture.getLtsAcqAppointmentForm().isWaiveCoolingOffInd() ? "Y":"N");
					pOrderCapture.getLtsDsOrderInfo().setMustQc(pOrderCapture.getLtsAcqAppointmentForm().isMustQc() ? "Y":"N");
					pOrderCapture.getLtsDsOrderInfo().setMustQcReasonCd(pOrderCapture.getLtsAcqAppointmentForm().getMustQcReasonCd());
				}
				String salesType = pBomSalesUser.getSalesType();
				if(pOrderCapture.getLtsDsOrderInfo() != null && StringUtils.isNotBlank(pOrderCapture.getLtsDsOrderInfo().getDsType())){
					salesType = pOrderCapture.getLtsDsOrderInfo().getDsType();
				}
				boolean isDoorKnocked;
				if (StringUtils.isNotBlank(salesType)){
					isDoorKnocked = StringUtils.equals(salesType, LtsConstant.LTS_DS_SALES_TYPE_CD_DOOR_KNOCKED)
							|| StringUtils.equals(LtsConstant.SALES_TYPE_MAPPING.get(salesType), LtsConstant.LTS_DS_SALES_TYPE_CD_DOOR_KNOCKED);
					pOrderCapture.getLtsDsOrderInfo().setCoolOff(isDoorKnocked ? "Y":"N");
				}
			
				if(StringUtils.isEmpty(pOrderCapture.getLtsDsOrderInfo().getNameMismatchStatus())){
					if(pOrderCapture.getLtsAcqPersonalInfoForm().isMatchWithBomName()){
						pOrderCapture.getLtsDsOrderInfo().setNameMismatchStatus(LtsConstant.NAME_MISMATCH_APR_CD_NO_MISMATCH);
					}else{
						pOrderCapture.getLtsDsOrderInfo().setNameMismatchStatus(LtsConstant.NAME_MISMATCH_APR_CD_REQUIRE_APPROVAL);
					}
				}
				pSbOrder.setLtsDsOrderInfo(pOrderCapture.getLtsDsOrderInfo());
			}else{
				LtsDsOrderInfoDTO ltsDsOrder = new LtsDsOrderInfoDTO();
				if(pOrderCapture.getLtsAcqAppointmentForm()!=null){
					ltsDsOrder.setCoolOff(pOrderCapture.getLtsAcqAppointmentForm().isDsDoorKnockedInd() ? "Y":"N");
					ltsDsOrder.setPeLink(BooleanUtils.isTrue(pOrderCapture.getLtsAcqAppointmentForm().getPeLinkInd()) ? "Y":"N");
					ltsDsOrder.setWaiveCloff(pOrderCapture.getLtsAcqAppointmentForm().isWaiveCoolingOffInd() ? "Y":"N");
					ltsDsOrder.setMustQc(pOrderCapture.getLtsAcqAppointmentForm().isMustQc() ? "Y":"N");
					ltsDsOrder.setMustQcReasonCd(pOrderCapture.getLtsAcqAppointmentForm().getMustQcReasonCd());
				}
				pSbOrder.setLtsDsOrderInfo(ltsDsOrder);
			}
		}
	}
	
	public void setContactInfo(SbOrderDTO pSbOrder, AcqOrderCaptureDTO pOrderCapture){
		
		ContactLtsDTO contactInfo = new ContactLtsDTO();
		String contactName = null;
		if(StringUtils.equals(pOrderCapture.getLtsAcqPersonalInfoForm().getDocumentType(), LtsConstant.DOC_TYPE_HKBR)){
			contactName = pOrderCapture.getLtsAcqPersonalInfoForm().getCompanyName();
		}else{
			contactName = pOrderCapture.getLtsAcqPersonalInfoForm().getFamilyName()+" "+pOrderCapture.getLtsAcqPersonalInfoForm().getGivenName();
        }
		contactInfo.setTitle(pOrderCapture.getLtsAcqPersonalInfoForm().getTitle());
		contactInfo.setContactName(contactName);
//		contactInfo.setContactPhone(pOrderCapture.getLtsAcqPersonalInfoForm().get);
		if(pOrderCapture.getLtsAcqPersonalInfoForm().getContactEmail() != null){
		   contactInfo.setEmailAddr(pOrderCapture.getLtsAcqPersonalInfoForm().getContactEmail());
		}
		if(pOrderCapture.getLtsAcqPersonalInfoForm().getOldContactEmail() != null){
			   contactInfo.setOldEmailAddr(pOrderCapture.getLtsAcqPersonalInfoForm().getOldContactEmail());
		}
		if(pOrderCapture.getLtsAcqPersonalInfoForm().getOldContactEmailDate() != null){
			contactInfo.setOldEmailAddrDate(pOrderCapture.getLtsAcqPersonalInfoForm().getOldContactEmailDate());
		}
		contactInfo.setContactUpdAlert(pOrderCapture.getLtsAcqPersonalInfoForm().getContactUpdAlert());
//		contactInfo.setActionInd("");
//		contactInfo.setOtherPhone(pOrderCapture.getLtsAcqPersonalInfoForm().get);
//		contactInfo.setContactType(pOrderCapture.getLtsAcqPersonalInfoForm().get);
//		contactInfo.setLockInd(pOrderCapture.getLtsAcqPersonalInfoForm().get);
		contactInfo.setIdDocType(pOrderCapture.getLtsAcqPersonalInfoForm().getDocumentType());
		contactInfo.setIdDocNum(pOrderCapture.getLtsAcqPersonalInfoForm().getDocNum());
		if(pOrderCapture.getLtsAcqPersonalInfoForm().getMobileNo() != null){
		    contactInfo.setContactMobile(pOrderCapture.getLtsAcqPersonalInfoForm().getMobileNo());
		}
		if(pOrderCapture.getLtsAcqPersonalInfoForm().getOldMobileNo() != null){
		    contactInfo.setOldContactMobile(pOrderCapture.getLtsAcqPersonalInfoForm().getOldMobileNo());
		}
		if(pOrderCapture.getLtsAcqPersonalInfoForm().getOldMobileNoDate() != null){
			contactInfo.setOldContactMobileDate(pOrderCapture.getLtsAcqPersonalInfoForm().getOldMobileNoDate());
		}
		if(pOrderCapture.getLtsAcqPersonalInfoForm().getFixedLineNo() != null){
		    contactInfo.setContactFixedLine(pOrderCapture.getLtsAcqPersonalInfoForm().getFixedLineNo());
		}
//		contactInfo.setContactRelationship(pOrderCapture.getLtsAcqPersonalInfoForm().getThirdPartyRelationship());
		contactInfo.setCustNo(pOrderCapture.getCustomerDetailProfileLtsDTO().getCustNum());
		pSbOrder.setContact(contactInfo);
	}
	
    public void setResDn(SbOrderDTO pSbOrder, AcqOrderCaptureDTO pOrderCapture){
		
		List<ResDnLtsDTO> resDnList = new ArrayList<ResDnLtsDTO>();		
		ResDnLtsDTO resDnDTO = null;
		Selection selection = pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection(); 
		if (pOrderCapture.getLtsAcqNumConfirmationForm()!=null) {
			if (selection==Selection.USE_NEW_DN || selection==Selection.USE_NEW_DN_AND_PIPB_DN) {
				if (LtsConstant.DN_SOURCE_DN_RESERVED.equals(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource())){
				    	resDnDTO = new ResDnLtsDTO();
					    resDnDTO.setSrvNum(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getReservedSrvNum());
					    resDnDTO.setDnSource(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource());
					    resDnDTO.setSessionId(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getSessionId());
					    resDnList.add(resDnDTO);
				} else if (LtsConstant.DN_SOURCE_DN_POOL.equals(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource())){
					if (pOrderCapture.getLtsAcqNumConfirmationForm().getNewDn()==null) { // null, DN not confirmed yet    
					    for (LtsAcqNumberSelectionInfoDTO numSelect : pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getReservedDnList()){
					    	resDnDTO = new ResDnLtsDTO();
						    resDnDTO.setSrvNum(numSelect.getSrvNum());
						    resDnDTO.setDnSource(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource());
						    resDnDTO.setSessionId(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getSessionId());
					    	resDnList.add(resDnDTO);
					    }
				    } else { // DN already confirmed
				    	resDnDTO = new ResDnLtsDTO();
					    resDnDTO.setSrvNum(pOrderCapture.getLtsAcqNumConfirmationForm().getNewDn());
					    resDnDTO.setDnSource(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource());
					    resDnDTO.setSessionId(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getSessionId());
					    resDnList.add(resDnDTO);
				    }
				}
			}
			if (selection==Selection.USE_NEW_DN_AND_PIPB_DN || selection==Selection.USE_PIPB_DN) {
				if (LtsConstant.DN_SOURCE_DN_PIPB.equals(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDnSource())) {
					resDnDTO = new ResDnLtsDTO();
				    resDnDTO.setSrvNum(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getPipbSrvNum());
				    resDnDTO.setDnSource(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDnSource());
				    resDnDTO.setSessionId(pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getSessionId());
				    resDnList.add(resDnDTO);
				}
			}
		}		
		pSbOrder.setResDn((ResDnLtsDTO[]) resDnList.toArray(new ResDnLtsDTO[resDnList.size()]));
	}
	
	private void setOrderSalesInfo(SbOrderDTO pSbOrder, AcqOrderCaptureDTO pOrderCapture, BomSalesUserDTO bomSalesUser) {
		if (pOrderCapture.getLtsAcqSalesInfoForm() != null) {
			pSbOrder.setSalesChannel(pOrderCapture.getLtsAcqSalesInfoForm().getSalesChannel());
			pSbOrder.setSalesContactNum(pOrderCapture.getLtsAcqSalesInfoForm().getSalesContact());
			pSbOrder.setStaffNum(pOrderCapture.getLtsAcqSalesInfoForm().getStaffId());
			pSbOrder.setSalesCd(pOrderCapture.getLtsAcqSalesInfoForm().getSalesCode());
			pSbOrder.setSalesTeam(pOrderCapture.getLtsAcqSalesInfoForm().getSalesTeam());
			pSbOrder.setSalesName(pOrderCapture.getLtsAcqSalesInfoForm().getStaffName());
			pSbOrder.setSalesPosition(pOrderCapture.getLtsAcqSalesInfoForm().getPosition());
			pSbOrder.setSalesJob(pOrderCapture.getLtsAcqSalesInfoForm().getJobName());
			
			if(StringUtils.isNotBlank(pOrderCapture.getLtsAcqSalesInfoForm().getDate())
					&& StringUtils.isNotBlank(pOrderCapture.getLtsAcqSalesInfoForm().getTime())){
				pSbOrder.setSalesProcessDate(pOrderCapture.getLtsAcqSalesInfoForm().getDate().concat(" ").concat(pOrderCapture.getLtsAcqSalesInfoForm().getTime()));
			}
			
			pSbOrder.setCreateChannel(bomSalesUser.getChannelCd());
			LtsAcqSalesInfoFormDTO tempSales = ltsSalesInfoService.getAcqSalesInfo(pOrderCapture.getLtsAcqSalesInfoForm().getStaffId());
			pSbOrder.setShopCd(tempSales != null? tempSales.getSalesTeam(): null);
			pSbOrder.setStaffInfo(createStaffInfo(pOrderCapture, bomSalesUser));
		}
	}
	
	private StaffInfoLtsDTO createStaffInfo(AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {
		
		if (acqOrderCapture.getLtsAcqSalesInfoForm() == null) {
			return null;
		}
		
		LtsAcqSalesInfoFormDTO salesInfoForm = acqOrderCapture.getLtsAcqSalesInfoForm();
		
		StaffInfoLtsDTO staffInfo = new StaffInfoLtsDTO();
		staffInfo.setCallDate(salesInfoForm.getDate());
		staffInfo.setPosition(salesInfoForm.getPosition());
		staffInfo.setRefCentreCd(salesInfoForm.getRefSalesCenter());
		staffInfo.setRefTeamCd(salesInfoForm.getRefSalesTeam());
		staffInfo.setRefSalesCd(salesInfoForm.getRefStaffId());
		staffInfo.setRefSalesName(salesInfoForm.getRefStaffName());
		staffInfo.setSalesCd(salesInfoForm.getSalesCode());		
		staffInfo.setContactPhone(salesInfoForm.getSalesContact());		
		staffInfo.setTeamCd(salesInfoForm.getSalesTeam());
		staffInfo.setSalesName(salesInfoForm.getStaffName());	
		staffInfo.setCentreCd(salesInfoForm.getSalesCenter());
		
		return staffInfo;
	}
	
	private AddressDetailLtsDTO createAddressDetailLts (AcqOrderCaptureDTO acqOrderCapture) {

		LtsAddressRolloutFormDTO ltsAddressRolloutForm = acqOrderCapture.getLtsAddressRolloutForm();
		
		AddressDetailLtsDTO addressDetailLts = new AddressDetailLtsDTO();

			addressDetailLts.setAreaCd(ltsAddressRolloutForm.getAreaCode());
			addressDetailLts.setAreaDesc(ltsAddressRolloutForm.getAreaDesc());
			addressDetailLts.setBuildNo(ltsAddressRolloutForm.getBuildingName());
			addressDetailLts.setDistDesc(ltsAddressRolloutForm.getDistrictDesc());
			addressDetailLts.setDistNo(ltsAddressRolloutForm.getDistrictCode());
			addressDetailLts.setFloorNo(ltsAddressRolloutForm.getFloor());
			addressDetailLts.setHiLotNo(ltsAddressRolloutForm.getLotNum());
			addressDetailLts.setSectCd(ltsAddressRolloutForm.getSectionCode());
			addressDetailLts.setSectDesc(ltsAddressRolloutForm.getSectionDesc());
			addressDetailLts.setSerbdyno(ltsAddressRolloutForm.getServiceBoundary());
			addressDetailLts.setStrCatCd(ltsAddressRolloutForm.getStreetCatgCode());
			addressDetailLts.setStrCatDesc(ltsAddressRolloutForm.getStreetCatgDesc());
			addressDetailLts.setStrName(ltsAddressRolloutForm.getStreetName());
			addressDetailLts.setStrNo(ltsAddressRolloutForm.getStreetNum());
			addressDetailLts.setUnitNo(ltsAddressRolloutForm.getFlat());
		
		addressDetailLts.setAddrUsage("IA");
		addressDetailLts.setBlacklistInd(null); //ignore ims blacklist addr
		addressDetailLts.setLtsBlacklistInd(acqOrderCapture.getAddressRollout().isLtsAddressBlacklist() ? "Y" : null);
		addressDetailLts.setAddrInventory(createAddressInventoryDetailLts(acqOrderCapture));
		
		return addressDetailLts;
	}
	
	private AddressInventoryDetailLtsDTO createAddressInventoryDetailLts(AcqOrderCaptureDTO acqOrderCapture) {
		AddressInventoryDetailLtsDTO addressInventory = new AddressInventoryDetailLtsDTO();
		addressInventory.setBuildingType(acqOrderCapture.getAddressRollout().getHousingType());
		addressInventory.setFieldWorkPermitDay(acqOrderCapture.getAddressRollout().getFieldWorkPermit());
		addressInventory.setFttcInd(acqOrderCapture.getAddressRollout().isFttcInd() ? "Y" : null);
		addressInventory.setMaxBandwidth(acqOrderCapture.getAddressRollout().getMaximumBandwidth());
		
		if (acqOrderCapture.getModemTechnologyAssign() != null) {
			// Set assigned technology for IMS order creation 
			addressInventory.setTechnology(acqOrderCapture.getModemTechnologyAssign().getTechnology());
			addressInventory.setResourceShortageInd(acqOrderCapture.getModemTechnologyAssign().isBbShortage() ? "Y" : null);	
		}
		
		if (acqOrderCapture.getAddressRollout().isIs2nBuilding()) {
			addressInventory.setN2BuildingInd(StringUtils.defaultIfEmpty(acqOrderCapture.getAddressRollout().getN2BuildingInd(), "Y"));	
		}
		
		if (StringUtils.isNotBlank(acqOrderCapture.getAddressRollout().getHktPremier())) {
			addressInventory.setHktPremier(getPremierCustTagByLkup(acqOrderCapture.getAddressRollout().getHktPremier()));
		}
		
		return addressInventory;
	}
	
	private String getPremierCustTagByLkup(String s) {		
		try {
			LookupItemDTO[] lookupItemDTO = premierCustTagLkupCacheService.getCodeLkupDAO().getCodeLkup();
			for (int i = 0; i < lookupItemDTO.length; i ++) {
				if (s.equals((String)lookupItemDTO[i].getItemValue()))	{
					return lookupItemDTO[i].getItemKey();
				}
			}
		} catch (DAOException e) {			
			e.printStackTrace();
		}		
		return null;
	}
	
	private ServiceDetailDTO[] createServiceDetail(AcqOrderCaptureDTO acqOrderCapture,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap, String orderType) {
		
		BasketDetailDTO selectedBasket = ltsBasketService.getBasket(acqOrderCapture.getLtsAcqBasketSelectionForm().getSelectedBasketId(),
				LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
		
		List<ServiceDetailDTO> serviceDetailList = new ArrayList<ServiceDetailDTO>();
		
		ServiceDetailDTO acqService = createAcqServiceDetail(acqOrderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap, orderType);
		if (acqService != null) {
			serviceDetailList.add(acqService);
		}
		
		ServiceDetailDTO portBackService = createPipbServiceDetail(acqOrderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap, orderType);
		if (portBackService != null) {
			serviceDetailList.add(portBackService);
		}
		
		ServiceDetailDTO new2DelService = createNew2DelService(acqOrderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap, orderType);
		if (new2DelService != null) {
			serviceDetailList.add(new2DelService);
		}
		
/*		ServiceDetailDTO duplexTo2DelService = createDuplexTo2DelService(acqOrderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap);
		if (duplexTo2DelService != null) {
			serviceDetailList.add(duplexTo2DelService);
		}
		
		ServiceDetailDTO duplexRemoveService = createDuplexRemoveService(acqOrderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap);
		if (duplexRemoveService != null) {
			serviceDetailList.add(duplexRemoveService);
		}*/
		
		if (acqOrderCapture.isEyeOrder()){
			if(acqOrderCapture.getLtsAcqNumConfirmationForm() != null){
				ServiceDetailOtherLtsDTO fsaServiceDetail = createServiceDetailOther(acqOrderCapture, selectedBasket, 
		        		acqOrderCapture.getLtsAcqNumConfirmationForm().getNewDn()==null?
		        				acqOrderCapture.getLtsAcqNumConfirmationForm().getPipbDn()
		        				: acqOrderCapture.getLtsAcqNumConfirmationForm().getNewDn());
		        if (fsaServiceDetail != null) {
			        serviceDetailList.add(fsaServiceDetail);
		        }
			}
		}
		return serviceDetailList.toArray(new ServiceDetailDTO[serviceDetailList.size()]);
	}
	
	private ServiceDetailDTO createPipbServiceDetail(AcqOrderCaptureDTO acqOrderCapture, BasketDetailDTO selectedBasket, 
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap, String orderType) {			
		if (acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection()==Selection.USE_NEW_DN_AND_PIPB_DN) {
			CreateServiceType createServiceType = CreateServiceType.CREATE_SRV_TYPE_ACQ_PORT_LATER;			
			ServiceDetailLtsDTO acqService = createServiceDetailLts(createServiceType, selectedBasket,
					acqOrderCapture, accountDetailLtsMap, customerDetailLtsMap, orderType);			
			acqService.setGrpType(acqOrderCapture.isEyeOrder() ? LtsConstant.GROUP_TYPE_EYE : null);
			acqService.setPendingApprovalCd(getPendingApprovalCd(createServiceType, acqOrderCapture));
			acqService.setPaperBillInd(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getPaperBillItem().isSelected() ? "Y" : null);
			acqService.setSuggestSrd(acqOrderCapture.getLtsAcqAppointmentForm() != null ? acqOrderCapture.getLtsAcqAppointmentForm().getInstallAppntDtl().getEarliestSRD().getDateString() : null);			
			//acqService.setItemDtls(createAllServiceItemDetail(acqOrderCapture, selectedBasket, createServiceType));
			acqService.setForceFieldVisitInd("Y");
			//acqService.setSrvCallPlanDtls(createServiceCallPlanDetailLts(acqOrderCapture));
			return acqService;
		}
		return null;
	}
	
	private ServiceDetailDTO createNew2DelService(AcqOrderCaptureDTO acqOrderCapture, BasketDetailDTO selectedBasket,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap, String orderType) {
		
		if (acqOrderCapture.getLtsAcqOtherVoiceServiceForm() == null
				|| acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getCreate2ndDel() == null
				|| !acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getCreate2ndDel().booleanValue()) {
			return null;
		}
/*		if (acqOrderCapture.getLtsAcqOtherVoiceServiceForm().isDuplexProfile()
				&& DuplexSrvType.CANCEL != acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getDuplexXSrvType() 
				&& DuplexSrvType.CANCEL != acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getDuplexYSrvType()) {
			return null;
		}*/
		if (StringUtils.isEmpty(acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelDn())
				|| (StringUtils.isEmpty(acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus())
						&& !acqOrderCapture.getCreate2ndDelByPipbDnInd())
				|| !acqOrderCapture.getLtsAcqOtherVoiceServiceForm().isSecondDelProfileValid()) {
			return null;
		}
		if (StringUtils.equals(LtsConstant.INVENT_STS_WORKING, acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus())
				&& acqOrderCapture.getSecondDelServiceProfile() == null) {
			return null;
		}
				
		ServiceDetailLtsDTO new2DelService = createServiceDetailLts(
				CreateServiceType.CREATE_SRV_TYPE_ACQ_2DEL, selectedBasket,
				acqOrderCapture, accountDetailLtsMap, customerDetailLtsMap, orderType);
		
		new2DelService.setForceFieldVisitInd("Y");
		
		new2DelService.setItemDtls(createDuplexServiceItemDetail(
				CreateServiceType.CREATE_SRV_TYPE_ACQ_2DEL, acqOrderCapture, selectedBasket));
		
		if (acqOrderCapture.getSecondDelServiceProfile() != null 
				&& acqOrderCapture.getLtsAcqOtherVoiceServiceForm().isShowSecondDelRedeemPremium()
				&& acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelRedeemPremium() != null ) {
			new2DelService.setRedeemPremiumInd(acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelRedeemPremium().booleanValue() ? "Y" : null);
			new2DelService.setPendingApprovalCd(getPendingApprovalCd(CreateServiceType.CREATE_SRV_TYPE_ACQ_2DEL, acqOrderCapture));
		}
		if (StringUtils.equals(LtsConstant.INVENT_STS_RESERVED, acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus())
				&& acqOrderCapture.getSecondDelServiceProfile() == null) {
			new2DelService.setReservedDnInd("Y");
		}
		
//		new2DelService.setRecontract(createRecontractLtsDTO(orderCapture, CreateServiceType.CREATE_SRV_TYPE_NEW_2DEL));
		
		return new2DelService;
	}  
	
	private ItemDetailLtsDTO[] createDuplexServiceItemDetail(CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture, BasketDetailDTO selectedBasket) {
		
		List<ItemDetailLtsDTO> itemDetailLtsList = new ArrayList<ItemDetailLtsDTO>();
		
		addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelHotVasItemList(), itemDetailLtsList);
		addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelOtherVasItemList(), itemDetailLtsList);
		addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelStandaloneVasItemList(), itemDetailLtsList);
		addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelIddItemList(), itemDetailLtsList);
		addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelOptOutIddItemList(), itemDetailLtsList);
		
		switch (createServiceType) {
//		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
//			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getDuplex2ndDelAutoInVasList(), itemDetailLtsList);
//			break;
		case CREATE_SRV_TYPE_ACQ_2DEL: 
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelAutoChangeTpList(), itemDetailLtsList);
			cancelExistingVasItem(selectedBasket, acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelCancelVasItemList(), itemDetailLtsList);
			break;
		default:
			break;
		}
		
		addCommonItemDetail(createServiceType, acqOrderCapture, selectedBasket, itemDetailLtsList);
		return itemDetailLtsList.toArray(new ItemDetailLtsDTO[itemDetailLtsList.size()]);
	}
	
	private void cancelExistingVasItem(BasketDetailDTO selectedBasket, List<ItemDetailProfileLtsDTO> cancelVasItemList, List<ItemDetailLtsDTO> itemDetailLtsList) {
		
		if (cancelVasItemList == null || cancelVasItemList.isEmpty()) {
			return;
		}
		
		for (ItemDetailProfileLtsDTO itemDetailProfile : cancelVasItemList) {
			ItemDetailDTO itemDetail =  itemDetailProfile.getItemDetail();
			ItemDetailLtsDTO itemDetailLts = createItemDetailLts(
					selectedBasket, itemDetail,
					String.valueOf(itemDetailLtsList.size() + 1),
					itemDetail.isSelected() ? LtsConstant.IO_IND_OUT
							: LtsConstant.IO_IND_SPACE, null, null);
			itemDetailLts.setExistEffectivePrice(itemDetail.getNetEffPrice());
			itemDetailLts.setExistGrossPrice(itemDetail.getGrossEffPrice());
			itemDetailLts.setExistEndDate(itemDetailProfile.getConditionEffEndDate());
			itemDetailLts.setPenaltyHandling(itemDetailProfile.getTpExpiredMonths() > 0 ? null : LtsConstant.PENALTY_ACTION_GENERATE);
			itemDetailLts.setPaidVasInd(itemDetailProfile.isPaidVas() ? "Y" : null);
			itemDetailLtsList.add(itemDetailLts);
		}
	}
	
	private ServiceDetailDTO createAcqServiceDetail(AcqOrderCaptureDTO acqOrderCapture, BasketDetailDTO selectedBasket, 
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap, String orderType) {
		
//		ServiceDetailProfileLtsDTO serviceProfile = acqOrderCapture.getLtsServiceProfile();
		
/*		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(acqOrderCapture.getOrderType())) {
			return null;
		}*/
		
		CreateServiceType createServiceType = (acqOrderCapture.isEyeOrder() ? CreateServiceType.CREATE_SRV_TYPE_ACQ_EYE
				: CreateServiceType.CREATE_SRV_TYPE_ACQ_DEL);
		
		ServiceDetailLtsDTO acqService = createServiceDetailLts(
				createServiceType, selectedBasket,
				acqOrderCapture, accountDetailLtsMap, customerDetailLtsMap, orderType);
		
		acqService.setGrpType(acqOrderCapture.isEyeOrder() ? LtsConstant.GROUP_TYPE_EYE : null); //FOR ALL EYE TYPES IN SB_ORDER_DTO
//		acqService.setRedeemPremiumInd(acqOrderCapture.getLtsMiscellaneousForm().isRedeemPrevPremium() ? "Y" : null);
		acqService.setPendingApprovalCd(getPendingApprovalCd(createServiceType, acqOrderCapture));
		acqService.setPaperBillInd(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getPaperBillItem().isSelected() ? "Y" : null);
//		acqService.setPendingOcid(serviceProfile.getPendingOcid());
//		acqService.setPendingOcidSrd(serviceProfile.getPendingOcSrd());
//		acqService.setCallPlanDowngradeInd(getCallPlanDowngradeInd(serviceProfile, selectedBasket));
//		acqService.setCancelVasInd(isCancelProfileVas(acqOrderCapture) ? "Y" : null);
		acqService.setSuggestSrd(acqOrderCapture.getLtsAcqAppointmentForm() != null ? acqOrderCapture.getLtsAcqAppointmentForm().getInstallAppntDtl().getEarliestSRD().getDateString() : null);
		acqService.setItemDtls(createAllServiceItemDetail(acqOrderCapture, selectedBasket, createServiceType));
//		acqService.setOneTwoThreeTvInd(LtsSbOrderHelper.getBundleTvInd(acqOrderCapture, serviceProfile));
		acqService.setForceFieldVisitInd("Y");
		acqService.setSrvCallPlanDtls(createServiceCallPlanDetailLts(acqOrderCapture));
		
		//recontract
/*		if(acqOrderCapture.getLtsMiscellaneousForm().isRecontract()
				&& acqOrderCapture.getLtsRecontractForm() != null){
			acqService.setRecontractInd("Y");
		}
		acqService.setRecontract(createRecontractLtsDTO(acqOrderCapture, createServiceType));*/
		return acqService;
	}
	
	private ServiceCallPlanDetailLtsDTO[] createServiceCallPlanDetailLts(AcqOrderCaptureDTO acqOrderCapture) {
		
		LtsAcqBasketVasDetailFormDTO vasForm = acqOrderCapture.getLtsAcqBasketVasDetailForm();
		
		if (vasForm == null) {
			return null;
		}
		List<ServiceCallPlanDetailLtsDTO> serviceCallPlanList = new ArrayList<ServiceCallPlanDetailLtsDTO>();
		
		addCallPlanDetailList(acqOrderCapture, vasForm.getFfpVasHotItemList(), serviceCallPlanList);
		addCallPlanDetailList(acqOrderCapture, vasForm.getFfpVasOtherItemList(), serviceCallPlanList);
		addCallPlanDetailList(acqOrderCapture, vasForm.getFfpVasStaffItemList(), serviceCallPlanList);
		
		if(vasForm.getBundleVasSetList() != null){
			for(ItemSetDetailDTO itemSet : vasForm.getBundleVasSetList()){
				addCallPlanDetailList(acqOrderCapture, Arrays.asList(itemSet.getItemDetails()) , serviceCallPlanList);
			}
		}
		
		if (serviceCallPlanList.isEmpty()) {
			return null;
		}
		
		return serviceCallPlanList.toArray(new ServiceCallPlanDetailLtsDTO[serviceCallPlanList.size()]);
		
	}
	
	private ServiceCallPlanDetailLtsDTO[] addCallPlanDetailList(AcqOrderCaptureDTO acqOrderCapture, List<ItemDetailDTO> callPlanItemList, List<ServiceCallPlanDetailLtsDTO> serviceCallPlanList) {
		
		if (callPlanItemList == null || callPlanItemList.isEmpty()) {
			return null;
		}
		
		AccountDetailProfileLtsDTO profileIAccount = new AccountDetailProfileLtsDTO();
		for (AccountDetailProfileLtsDTO acct : acqOrderCapture.getAccountDetailProfileLtsDTO()){
			if (StringUtils.containsIgnoreCase(acct.getAcctChrgType(), LtsConstant.ACCOUNT_CHARGE_TYPE_I)){
				profileIAccount = acct;
			}
		}
		
		
		for (ItemDetailDTO callPlanItem : callPlanItemList) {
			if (!callPlanItem.isSelected()) {
				continue;
			}
			
			if(!LtsConstant.ITEM_TYPE_FFP_HOT.equals(callPlanItem.getItemType())
					&& !LtsConstant.ITEM_TYPE_FFP_OTHER.equals(callPlanItem.getItemType())
					&& !LtsConstant.ITEM_TYPE_FFP_STAFF.equals(callPlanItem.getItemType())
					&& !LtsConstant.ITEM_TYPE_VAS_FFP.equals(callPlanItem.getItemType())){
				continue;
			}
			
			String[] callPlanCds = callPlanLtsService.getCallPlanCd(callPlanItem.getItemId());
			
			if (ArrayUtils.isEmpty(callPlanCds)) {
				continue;
			}
			
			for (String callPlanCd : callPlanCds) {
				
				String planType = callPlanLtsService.getCallPlanType(callPlanCd);
				String contractMonth = ltsOfferService.getItemContractPeriod(callPlanItem.getItemId());
				if (StringUtils.equalsIgnoreCase("GIFT", planType)) {
					contractMonth = callPlanLtsService.getCallPlanContractPeriod(callPlanCd);
				}
				
				ServiceCallPlanDetailLtsDTO callPlanDetail = new ServiceCallPlanDetailLtsDTO();
				callPlanDetail.setItemId(callPlanItem.getItemId());
				callPlanDetail.setContractMonth(contractMonth);
				if (acqOrderCapture.getLtsAcqAppointmentForm() != null) {
					callPlanDetail.setEffStartDate(acqOrderCapture.getLtsAcqAppointmentForm().getInstallAppntDtl().getAppntDate());
				}
				callPlanDetail.setIoInd(LtsConstant.IO_IND_INSTALL);
				callPlanDetail.setPlanType(planType);
				callPlanDetail.setPlanCd(callPlanCd);
				callPlanDetail.setPlanHolder(profileIAccount != null ? profileIAccount.getAcctNum() : "");
				callPlanDetail.setPlanHolderType("A");
				serviceCallPlanList.add(callPlanDetail);
			}
		}
		if (serviceCallPlanList.isEmpty()) {
			return null;
		}
		return serviceCallPlanList.toArray(new ServiceCallPlanDetailLtsDTO[serviceCallPlanList.size()]);
	}
	
	private String getPendingApprovalCd(CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture) {
		
		switch (createServiceType) {
/*		case CREATE_SRV_TYPE_ACQ_EYE:
		case CREATE_SRV_TYPE_ACQ_DEL:
			if (acqOrderCapture.getLtsMiscellaneousForm() != null
					&& !acqOrderCapture.getLtsMiscellaneousForm().isRedeemPrevPremium()) {
				return LtsConstant.APPROVAL_CD_NO_REDEEM_PREMIUM;
			}
			break;*/
		case CREATE_SRV_TYPE_ACQ_2DEL:
			if (acqOrderCapture.getLtsAcqOtherVoiceServiceForm() != null
					&& acqOrderCapture.getLtsAcqOtherVoiceServiceForm().isShowSecondDelRedeemPremium()
					&& acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelRedeemPremium() != null
					&& !acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelRedeemPremium().booleanValue()) {
				return LtsConstant.APPROVAL_CD_NO_REDEEM_PREMIUM;
			}
			break;
		}		
		return null;
	}
		
	
	private ItemDetailLtsDTO[] createAllServiceItemDetail(AcqOrderCaptureDTO acqOrderCapture, BasketDetailDTO selectedBasket, CreateServiceType createServiceType) {
		
		List<ItemDetailLtsDTO> itemDetailLtsList = new ArrayList<ItemDetailLtsDTO>();
		
		String pcdSbid = null;
		String pcdBundleFree = null;
		
		if (acqOrderCapture.getLtsAcqBasketSelectionForm() != null)
		{
			pcdSbid = acqOrderCapture.getLtsAcqBasketSelectionForm().getPcdSbid();
			pcdBundleFree = acqOrderCapture.getLtsAcqBasketSelectionForm().isDelFreeBundle()?"Y":"N";
		}

		if (acqOrderCapture.getLtsAcqBasketServiceForm() != null) {
//			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketServiceForm().getBbRentalItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketServiceForm().getInstallItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketServiceForm().getBvasItemList(), itemDetailLtsList);
			addItemDetailPcd(selectedBasket, acqOrderCapture.getLtsAcqBasketServiceForm().getPlanItemList(), itemDetailLtsList, pcdSbid, pcdBundleFree);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketServiceForm().getContentItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketServiceForm().getMoovItemList(), itemDetailLtsList);
//			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketServiceForm().getAdminChargeItemList(), itemDetailLtsList);
//			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketServiceForm().getCancelChargeItemList(), itemDetailLtsList);
			addItemSetDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketServiceForm().getContItemSetList(), itemDetailLtsList, null);
		}
		
/*		if (acqOrderCapture.getLtsNowTvServiceForm() != null) {
			
			addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvFreeItemList(), itemDetailLtsList);
			
			if (AdditionalTvType.SD_PAY_CHANNEL == acqOrderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
				addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvPayItemList(), itemDetailLtsList);
				addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvSportItemList(), itemDetailLtsList);
			}
			else if (AdditionalTvType.SD_SPECIAL_CHANNEL == acqOrderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
				addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvSpecItemList(), itemDetailLtsList);
				addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvCeleItemList(), itemDetailLtsList);
				addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvSportItemList(), itemDetailLtsList);
			}
			else if (AdditionalTvType.MIRROR == acqOrderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
				addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvMirrorItemList(), itemDetailLtsList);
			}
			else if (AdditionalTvType.ADDITIONAL_CHANNELS == acqOrderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
				addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvSportItemList(), itemDetailLtsList);
			}

			addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvDeviceItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvEmailItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsNowTvServiceForm().getNowTvPrintItemList(), itemDetailLtsList);				
		}*/
		
		if (acqOrderCapture.getLtsAcqBasketVasDetailForm() != null) {
			
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getE0060VasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getE0060OutVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getExistVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getHotVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getIddOutVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getIddVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getOtherVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getPeFreeItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getPeSocketItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getOptAccessaryItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getPrewireVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getPreinstallVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getBvasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getOtherVasComtPeriodItemList(), itemDetailLtsList);
			addItemSetDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getSmartWrtySetList(), itemDetailLtsList, null);			
			addItemSetDetail(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm().getBundleVasSetList(), itemDetailLtsList, null);
			// Add Profile TP/VAS 
//			addItemDetailProfileLts(selectedBasket, acqOrderCapture.getLtsAcqBasketVasDetailForm(), itemDetailLtsList, acqOrderCapture.getLtsServiceProfile());
		}
		
		if (acqOrderCapture.getLtsPremiumSelectionForm() != null) {
			addItemSetDetail(selectedBasket, acqOrderCapture.getLtsPremiumSelectionForm().getPremiumSetList(), itemDetailLtsList, acqOrderCapture.getLtsPremiumSelectionForm().getPcdSbid());
		}

/*		if (acqOrderCapture.getLtsAcqPaymentMethodFormDTO() != null) {
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqPaymentMethodFormDTO().getErChargeItemList(), itemDetailLtsList);
		}*/
		
		if (acqOrderCapture.getLtsModemArrangementForm() != null) {
			addItemDetail(selectedBasket, acqOrderCapture.getLtsModemArrangementForm().getRentalRouterItemList(), itemDetailLtsList);
		}
		//BOM2018061
		if (acqOrderCapture.getLtsAcqAppointmentForm() != null) {
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqAppointmentForm().getEpdItemList(), itemDetailLtsList);
		}
		//BOM2018061
		
		addCommonItemDetail(createServiceType, acqOrderCapture, selectedBasket, itemDetailLtsList);
		return itemDetailLtsList.toArray(new ItemDetailLtsDTO[itemDetailLtsList.size()]);
	}
	
	protected void addItemDetail(BasketDetailDTO selectedBasket, List<ItemDetailDTO> itemDetailList, List<ItemDetailLtsDTO> itemDetailLtsList) {
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return;
		}
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (!itemDetail.isSelected()) {
				continue;
			}
			itemDetailLtsList
					.add(createItemDetailLts(
							selectedBasket,
							itemDetail,
							String.valueOf(itemDetailLtsList.size() + 1),
							LtsConstant.IO_IND_INSTALL, null, null));
		}
	}
	
	protected void addItemDetailPcd(BasketDetailDTO selectedBasket, List<ItemDetailDTO> itemDetailList, List<ItemDetailLtsDTO> itemDetailLtsList, String pcdSbid, String pcdBundleFree) {
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return;
		}
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (!itemDetail.isSelected()) {
				continue;
			}
			itemDetailLtsList
					.add(createItemDetailLts(
							selectedBasket,
							itemDetail,
							String.valueOf(itemDetailLtsList.size() + 1),
							LtsConstant.IO_IND_INSTALL, pcdSbid, pcdBundleFree));
		}
	}
	
	private ItemDetailLtsDTO createItemDetailLts (BasketDetailDTO selectedBasket, ItemDetailDTO itemDetail, String srvItemSeq, String ioInd, String pcdSbid, String pcdBundleFree) {
		ItemDetailLtsDTO itemDetailLts = new ItemDetailLtsDTO();
		itemDetailLts.setSrvItemSeq(srvItemSeq);
		itemDetailLts.setBasketId(selectedBasket.getBasketId());
		itemDetailLts.setItemAttbs(createItemAttributeDetailLts(itemDetail.getItemAttbs(), srvItemSeq));
		itemDetailLts.setItemId(itemDetail.getItemId());
		itemDetailLts.setItemType(itemDetail.getItemType());
		itemDetailLts.setIoInd(ioInd);
		itemDetailLts.setItemQty(StringUtils.isNotBlank(itemDetail.getSelectedQty()) ? itemDetail.getSelectedQty() : "1");
//		itemDetailLts.setPenaltyHandling(getPenaltyHandling(itemDetail));
		itemDetailLts.setBundlePcdOrderId(pcdSbid);
		itemDetailLts.setPcdBundleFree(pcdBundleFree);
		return itemDetailLts;
	}
	
/*	private String getPenaltyHandling(ItemDetailDTO itemDetail) {
		
		if (itemDetail == null 
				|| StringUtils.isEmpty(itemDetail.getPenaltyHandling())) {
			return null;
		}
		
		else if (LtsConstant.ITEM_TYPE_ER_CHARGE.equals(itemDetail.getItemType())) {
			return itemDetail.getPenaltyHandling();
		}
		else if (LtsConstant.OFFER_HANDLE_AUTO_WAIVE.equals(itemDetail.getPenaltyHandling())) {
			return LtsConstant.PENALTY_ACTION_AUTO_WAIVE;
		}
		else if (LtsConstant.OFFER_HANDLE_MANUAL_WAIVE.equals(itemDetail.getPenaltyHandling())){
			return LtsConstant.PENALTY_ACTION_AWAIT_APPROVAL;
		}
		else if (LtsConstant.OFFER_HANDLE_AUTO_GENERATE.equals(itemDetail.getPenaltyHandling())){
			return LtsConstant.PENALTY_ACTION_GENERATE;
		}
		
		return null;
	}*/
	
    private ItemAttributeDetailLtsDTO[] createItemAttributeDetailLts(ItemAttbDTO[] itemAttbs, String srvItemSeq) {
		
		if (ArrayUtils.isEmpty(itemAttbs)) {
			return null;
		}
		
		List<ItemAttributeDetailLtsDTO> itemAttributeDetailLtsList = new ArrayList<ItemAttributeDetailLtsDTO>();
		
		for (ItemAttbDTO itemAttb : itemAttbs) {
			ItemAttributeDetailLtsDTO itemAttributeDetailLts = new ItemAttributeDetailLtsDTO();
			itemAttributeDetailLts.setAttbCd(itemAttb.getAttbID());
			itemAttributeDetailLts.setAttbValue(itemAttb.getAttbValue());
			itemAttributeDetailLts.setSrvItemSeq(srvItemSeq);
			itemAttributeDetailLts.setComptId(itemAttb.getComptId());
			itemAttributeDetailLts.setProductId(itemAttb.getProdId());
			itemAttributeDetailLts.setOfferId(itemAttb.getOfferId());
			itemAttributeDetailLtsList.add(itemAttributeDetailLts);
		}
		return itemAttributeDetailLtsList.toArray(new ItemAttributeDetailLtsDTO[itemAttributeDetailLtsList.size()]);
	}
    
	private void addItemSetDetail(BasketDetailDTO selectedBasket, List<ItemSetDetailDTO> itemSetDetailList, List<ItemDetailLtsDTO> itemDetailLtsList, String pcdSbid) {
		if (itemSetDetailList == null || itemSetDetailList.isEmpty()) {
			return;
		}
		
		for (ItemSetDetailDTO itemSetDetail : itemSetDetailList) {
			
			String membershipId = null;
			ItemSetAttbDTO[] itemSetAttbs = itemSetDetail.getItemSetAttbs();
			
			if (ArrayUtils.isNotEmpty(itemSetAttbs)) {
				membershipId = itemSetAttbs[0].getAttbValue();
			}
			
			for (ItemDetailDTO itemDetail : itemSetDetail.getItemDetails()) {
				
				if (StringUtils.equals(LtsConstant.ITEM_TYPE_NOWTV_PAY, itemDetail.getItemType()) 
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_NOWTV_SPEC, itemDetail.getItemType())) {
					continue;
				}
				
				if (itemDetail.isSelected()) {
					ItemDetailLtsDTO itemDetailLts = createItemDetailLts(selectedBasket, itemDetail,
							String.valueOf(itemDetailLtsList.size() + 1),
							LtsConstant.IO_IND_INSTALL, null, null);
					itemDetailLts.setMembershipId(membershipId);
					if(itemSetDetail.getItemSetType().equalsIgnoreCase("PREM-PCD"))
					{
						itemDetailLts.setBundlePcdOrderId(pcdSbid);
						itemDetailLts.setMembershipId(null);
					}
					itemDetailLtsList.add(itemDetailLts);
				}
			}
		}
	}
	
	private void addCommonItemDetail(
			CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture,
			BasketDetailDTO selectedBasket, List<ItemDetailLtsDTO> itemDetailLtsList) {
		// New Acct setting
		AccountDetailProfileLtsDTO acqSrvPrimaryAcct = LtsSbOrderHelper.getAcqPrimaryAccount(acqOrderCapture.getAccountDetailProfileLtsDTO());
		AccountDetailProfileLtsDTO secDelSrvPrimaryAcct = acqOrderCapture.getSecondDelServiceProfile() == null ? LtsSbOrderHelper.getAcqPrimaryAccount(acqOrderCapture.getAccountDetailProfileLtsDTO())
				: LtsSbOrderHelper.getPrimaryAccount(acqOrderCapture.getSecondDelServiceProfile());	
		
		switch (createServiceType) {
//		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_ACQ_2DEL:
			if (!StringUtils.equals(acqSrvPrimaryAcct.getAcctNum(), secDelSrvPrimaryAcct.getAcctNum())) {
				List<ItemDetailDTO> existBillList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_KEEP_EXIST_BILL, LtsConstant.LOCALE_ENG, true, acqOrderCapture.getBasketChannelId(), null);
				addItemDetail(selectedBasket, existBillList, itemDetailLtsList);
			}	
			break;
		default:
			break;
		}
		
		if (acqOrderCapture.getLtsAcqPaymentMethodFormDTO() != null ) {
			switch (createServiceType) {
/*			case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
				addItemDetail(selectedBasket, Lists.newArrayList(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getEmailBillItem()), itemDetailLtsList);	
				addItemDetail(selectedBasket, Lists.newArrayList(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPaperBillItem()), itemDetailLtsList);	
				if(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getCsPortalItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getCsPortalItem()), itemDetailLtsList);
				}
				break;*/
			case CREATE_SRV_TYPE_ACQ_2DEL:
				if (StringUtils.equals(acqSrvPrimaryAcct.getAcctNum(), secDelSrvPrimaryAcct.getAcctNum())) {
					addItemDetail(selectedBasket, Lists.newArrayList(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getEmailBillItem()), itemDetailLtsList);	
					addItemDetail(selectedBasket, Lists.newArrayList(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getPaperBillItem()), itemDetailLtsList);		
					if(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem() != null){
						addItemDetail(selectedBasket, Lists.newArrayList(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem()), itemDetailLtsList);
					}
				}
				break;
			case CREATE_SRV_TYPE_ACQ_DEL:
			case CREATE_SRV_TYPE_ACQ_EYE:
				for(int i = 0; i < acqOrderCapture.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList().size(); i++){
				addItemDetail(selectedBasket, Lists.newArrayList(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList().get(i).getEmailBillItem()), itemDetailLtsList);	
				addItemDetail(selectedBasket, Lists.newArrayList(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList().get(i).getPaperBillItem()), itemDetailLtsList);	
				if(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList().get(i).getCsPortalItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList().get(i).getCsPortalItem()), itemDetailLtsList);
				}
				}
				
				
				for(PaymentMethodDtl payMtdDtl: acqOrderCapture.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList()){
					if(LtsConstant.PAYMENT_TYPE_CASH.equals(payMtdDtl.getNewPayMethodType())
							&& payMtdDtl.getCashPrePayItem() != null
							&& payMtdDtl.getCashPrePayItem().isSelected()){
						addItemDetail(selectedBasket, Lists.newArrayList(payMtdDtl.getCashPrePayItem()) , itemDetailLtsList);
					}else if(LtsConstant.PAYMENT_TYPE_AUTO_PAY.equals(payMtdDtl.getNewPayMethodType())
							&& payMtdDtl.getAutopayPrePayItem() != null
							&& payMtdDtl.getAutopayPrePayItem().isSelected()){
						addItemDetail(selectedBasket, Lists.newArrayList(payMtdDtl.getAutopayPrePayItem()) , itemDetailLtsList);
					}else if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(payMtdDtl.getNewPayMethodType())// if not Tap and Go card
							&& !StringUtils.equals(payMtdDtl.getCardNum().substring(0,4), LtsConstant.TNG_CARD_PREFIX)
							&& payMtdDtl.getCreditCardPrePayItem() != null
							&& payMtdDtl.getCreditCardPrePayItem().isSelected()){
						addItemDetail(selectedBasket, Lists.newArrayList(payMtdDtl.getCreditCardPrePayItem()) , itemDetailLtsList);
					}else if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(payMtdDtl.getNewPayMethodType())// if Tap and Go card
							&& StringUtils.equals(payMtdDtl.getCardNum().substring(0,4), LtsConstant.TNG_CARD_PREFIX)
							&& payMtdDtl.getCashPrePayItem() != null
							&& payMtdDtl.getCashPrePayItem().isSelected()){
						addItemDetail(selectedBasket, Lists.newArrayList(payMtdDtl.getCashPrePayItem()) , itemDetailLtsList);
					}
					
					if(payMtdDtl.getIddDepositItem() != null && payMtdDtl.getIddDepositItem().isSelected()){
						addItemDetail(selectedBasket, Lists.newArrayList(payMtdDtl.getIddDepositItem()) , itemDetailLtsList);
					}
				}
				break;
			default:
				break;
			}
		}
	}	
	
	

	private ServiceDetailOtherLtsDTO createServiceDetailOther(AcqOrderCaptureDTO acqOrderCapture, BasketDetailDTO selectedBasket, String acqServiceNum) {
		
		FsaDetailDTO selectedFsaDetail = getSelectedFsaDetail(acqOrderCapture);
		ModemType selectedModemType = null;
		
		
		ServiceDetailOtherLtsDTO serviceDetailOtherLts = new ServiceDetailOtherLtsDTO();
		String orderType = getImsOrderType(acqOrderCapture);
		serviceDetailOtherLts.setOrderType(orderType);
		
		serviceDetailOtherLts.setAppointmentDtl(createAppointmentDetailLts(CreateServiceType.CREATE_SRV_TYPE_FSA, acqOrderCapture));
		
		if(acqOrderCapture.getLtsModemArrangementForm() != null){
		    serviceDetailOtherLts.setAssgnBandwidth(acqOrderCapture.getModemTechnologyAssign().getBandwidth());
		    serviceDetailOtherLts.setAssgnTechnology(acqOrderCapture.getModemTechnologyAssign().getTechnology());
		    serviceDetailOtherLts.setModemArrangement(acqOrderCapture.getModemTechnologyAssign().getModemArrangment());
		    serviceDetailOtherLts.setRelatedSbOrderId(getRelatedSbOrderId(acqOrderCapture));
		    serviceDetailOtherLts.setEdfRef(getEdfRef(acqOrderCapture));
			serviceDetailOtherLts.setFromProd(getImsFromProductType(acqOrderCapture));
			serviceDetailOtherLts.setToProd(getImsToProductType(acqOrderCapture));
			selectedModemType = acqOrderCapture.getLtsModemArrangementForm().getModemType();
			serviceDetailOtherLts.setShareFsaType(selectedModemType.getName());
			serviceDetailOtherLts.setLoginId(getLoginId(orderType, selectedFsaDetail, acqServiceNum, selectedModemType));
			serviceDetailOtherLts.setManualLineTypeInd(LtsSbOrderHelper.getSelectedLineType(acqOrderCapture.getLtsModemArrangementForm()));
			serviceDetailOtherLts.setAutoUpgraded(acqOrderCapture.getModemTechnologyAssign().isAutoUpgraded() ? "Y" : "N");
			serviceDetailOtherLts.setNoEyeFsa(LtsSbOrderHelper.getNoEyeFsa(acqOrderCapture.getLtsModemArrangementForm()));
			serviceDetailOtherLts.setLostModem(acqOrderCapture.getLtsModemArrangementForm().isLostModem() ? "Y" : "N" );
			serviceDetailOtherLts.setShareRentalRouter(getShareRentalRouterInd(acqOrderCapture));
			serviceDetailOtherLts.setShareBrmWifi(getBrmWifiInd(acqOrderCapture, selectedFsaDetail));
			serviceDetailOtherLts.setImsL2Jobs(createImsL2job(acqOrderCapture, selectedBasket));
		}
		
//		serviceDetailOtherLts.setChannels(createChannelDetailLts(acqOrderCapture));
//		serviceDetailOtherLts.setExistArpu(getExistNowTvArpu(acqOrderCapture));		
//		serviceDetailOtherLts.setNowtvDetail(createNowTvDetailLts(acqOrderCapture));
//		serviceDetailOtherLts.setNowtvMirrorInd(isMirrorNowTv(acqOrderCapture) ? "Y" : null);
//		serviceDetailOtherLts.setNowtvTvCd(getNowBundleTv(acqOrderCapture));
		
		
		serviceDetailOtherLts.setSuggestSrd(getSuggestedSrd(acqOrderCapture));
		serviceDetailOtherLts.setSuggestSrdReasonCd(getSuggestedSrdReason(acqOrderCapture));
		

		serviceDetailOtherLts.setTypeOfSrv(LtsConstant.SERVICE_TYPE_IMS); 
		serviceDetailOtherLts.setGrpType(LtsConstant.GROUP_TYPE_EYE); //FOR ALL EYE TYPES IN SB_ORDER_DTO
		

		// if isMirrorNowTV, set select Mirror FSA
/*		if (isMirrorNowTv(acqOrderCapture) && acqOrderCapture.getLtsNowTvServiceForm() != null){
			serviceDetailOtherLts.setMirrorFsa(acqOrderCapture.getLtsNowTvServiceForm().getMirrorFsa());
		}*/
		
		serviceDetailOtherLts.setTenure(String.valueOf(acqOrderCapture.getLtsAcqPaymentMethodFormDTO().getImsTenure()));
		serviceDetailOtherLts.setOfferDtls(createAllImsOfferDetails(acqOrderCapture));
		
		if (selectedFsaDetail != null ) {
			serviceDetailOtherLts.setDeactNowTvInd(selectedFsaDetail.getDeactNowTv());

			if (acqOrderCapture.getModemTechnologyAssign() != null
					&& !StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B,
							acqOrderCapture.getModemTechnologyAssign().getModemArrangment())) {
				serviceDetailOtherLts.setCcServiceId(String.valueOf(selectedFsaDetail.getFsa()));
			}
			
			serviceDetailOtherLts.setErInd(isFsaPerformEr(acqOrderCapture, selectedFsaDetail) ? "Y" : null);
			serviceDetailOtherLts.setExistBandwidth(selectedFsaDetail.getBandwidth());
			serviceDetailOtherLts.setExistSrvTypeCd(selectedFsaDetail.getExistingService().getDesc());
			serviceDetailOtherLts.setPendingOcid(selectedFsaDetail.getPendingOcid());
			serviceDetailOtherLts.setSrvNum(String.valueOf(selectedFsaDetail.getFsa()));
			serviceDetailOtherLts.setNewSrvTypeCd(getNewSrvTypeCode(selectedFsaDetail));
			serviceDetailOtherLts.setNewBandwidth(getNewBandwidth(selectedFsaDetail));
			serviceDetailOtherLts.setExistModem(selectedFsaDetail.getExistingModemArrange());
			serviceDetailOtherLts.setExistTechnology(selectedFsaDetail.getTechnology());
			serviceDetailOtherLts.setExistMirrorInd(selectedFsaDetail.getExistMirrorInd());
		}
		if (StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_WG)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD_SD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD_HD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_SD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_HD)) {
			serviceDetailOtherLts.setExistModem(LtsConstant.EXIST_MODEM_TYPE_NIL);
		}
//		serviceDetailOtherLts.setReplaceExistFsa(getReplaceExistFsa(acqOrderCapture, selectedFsaDetail, orderType));
		
		serviceDetailOtherLts.setForceFieldVisitInd("Y");
		
		return serviceDetailOtherLts;
	}
	

	private String getBrmWifiInd(AcqOrderCaptureDTO orderCapture, FsaDetailDTO selectedFsaDetail) {
		
		switch(orderCapture.getLtsModemArrangementForm().getModemType()){
		case SHARE_EX_FSA:
			if(selectedFsaDetail != null && selectedFsaDetail.isBrmWifiInd()){
				return "Y";
			}
			break;
			
		case SHARE_PCD: 
			if(orderCapture.getRelatedPcdOrder() != null){
				return orderCapture.getRelatedPcdOrder().getHasBrmWifi();
			}
			break;
		}
		
		return null;
	}
	
	private FsaDetailDTO getSelectedFsaDetail(AcqOrderCaptureDTO acqOrderCapture) {
		
		List<FsaDetailDTO> fsaDetailList = null;
		
		if (acqOrderCapture.getLtsModemArrangementForm() != null){
		
		    switch (acqOrderCapture.getLtsModemArrangementForm().getModemType()) {
		    case STANDALONE:
		    case SHARE_PCD:
		    case SHARE_TV:
		    case SHARE_BUNDLE:
			    return null;
		    case SHARE_EX_FSA:
			    fsaDetailList = acqOrderCapture.getLtsModemArrangementForm().getExistingFsaList();
			    break;
		    case SHARE_OTHER_FSA:
			    fsaDetailList = acqOrderCapture.getLtsModemArrangementForm().getOtherFsaList();
			    break;
		    default:
			    break;
		    }
		
		}
		
		if (fsaDetailList != null && !fsaDetailList.isEmpty()) {
			for (FsaDetailDTO fsaDetail : fsaDetailList) {
				if (fsaDetail.isSelected()) {
					return fsaDetail;
				}
			}
		}
		return null;
	}
	
	protected AppointmentDetailLtsDTO createAppointmentDetailLts(CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture) {

		LtsAcqAppointmentFormDTO appointmentForm = acqOrderCapture.getLtsAcqAppointmentForm();
		
		if (appointmentForm == null) {
			return null;
		}
		
		AppointmentDetailLtsDTO appointmentDetailLts = new AppointmentDetailLtsDTO();
		appointmentDetailLts.setInstContactMobile(appointmentForm.getInstallationMobileSMSAlert());
		appointmentDetailLts.setCustContactMobile(appointmentForm.getCustomerContactMobileNum());
		appointmentDetailLts.setInstContactName(appointmentForm.getInstallationContactName());
		appointmentDetailLts.setInstContactNum(appointmentForm.getInstallationContactNum());
		appointmentDetailLts.setInstSmsNum(appointmentForm.getInstallationMobileSMSAlert());
		appointmentDetailLts.setCustContactFix(appointmentForm.getCustomerContactFixLineNum());
		
		if(!appointmentForm.isConfirmedInd()){
//		if(!LtsConstant.TRUE_IND.equals(appointment.getConfirmedInd())){
			return appointmentDetailLts;
		}
		
		String appntTimeSlot = null;
		String appntDate = null;
		String[] appntDateString = null;
		String cutOverTimeSlot = null;
		String cutOverDate = null;
		String[] cutOverDateString = null;
		
		LtsAppointmentDetail appointmentDtl = null;
		LtsAppointmentDetail preWiringDtl = null;
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_2DEL:
			appointmentDtl = appointmentForm.getSecDelInstallAppntDtl();
			break;
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:
			appointmentDtl = appointmentForm.getPortLaterAppntDtl();
			break;
		default:
			appointmentDtl = appointmentForm.getInstallAppntDtl();
			preWiringDtl = appointmentForm.getPreWiringAppntDtl();
			
			appointmentDetailLts.setSerialNum(appointmentForm.getPreBookSerialNum());
			if(appointmentForm.isBbShortageInd()){
				appointmentDetailLts.setTidInd("Y");
				if(acqOrderCapture.getAddressRollout().isIs2nBuilding()){
					appntDate = DateTime.now().plusDays(14).toString("dd/MM/yyyy");
				}else{
					appntDate = DateTime.now().plusDays(7).toString("dd/MM/yyyy");
				}
				appntDateString = ltsAppointmentService.reformatDateTimeSlot(appntDate, LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
				appointmentDetailLts.setTidStartTime(appntDateString[0]);
				appointmentDetailLts.setTidEndTime(appntDateString[1]);
			}else if(LtsAppointmentHelper.tentativeReasonCodeList.contains(appointmentDtl.getEarliestSRD().getReasonCd())){
				appointmentDetailLts.setTidInd("Y");
				DateTime tid = new DateTime(DateTime.now().getYear()+1, DateTimeConstants.DECEMBER, 31, 0, 0, 0);
				appntDate = tid.toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
				appntDateString = ltsAppointmentService.reformatDateTimeSlot(appntDate, LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
				appointmentDetailLts.setTidStartTime(appntDateString[0]);
				appointmentDetailLts.setTidEndTime(appntDateString[1]);
			}

			break;
		}
		
		if(appointmentDtl != null){
			appntTimeSlot = LtsDateFormatHelper.convertPonTime(appointmentDtl.getAppntTimeSlot());
			appntTimeSlot = LtsDateFormatHelper.revertToBomTime(appntTimeSlot);
			appointmentDetailLts.setAppntType(appointmentDtl.getAppntTimeSlotType());	
			appntDate = appointmentDtl.getAppntDate();
			
			if(appointmentDtl.isCutOverInd()){
				cutOverTimeSlot = appointmentDtl.getCutOverTime();
				cutOverDate = appointmentDtl.getCutOverDate();
			}
		}

		if (preWiringDtl != null){
			appntDateString = ltsAppointmentService.reformatDateTimeSlot(preWiringDtl.getAppntDate(), preWiringDtl.getAppntTimeSlot());
			appointmentDetailLts.setPreWiringStartTime(appntDateString[0]);
			appointmentDetailLts.setPreWiringEndTime(appntDateString[1]);
			appointmentDetailLts.setPreWiringType(preWiringDtl.getAppntTimeSlotType());
		}
		
		if (StringUtils.isNotBlank(appntTimeSlot) && StringUtils.isNotBlank(appntDate)) {
			appntDateString = ltsAppointmentService.reformatDateTimeSlot(appntDate, appntTimeSlot);
			appointmentDetailLts.setAppntStartTime(appntDateString[0]);
			appointmentDetailLts.setAppntEndTime(appntDateString[1]);	
		}
		
		if(StringUtils.isNotBlank(cutOverTimeSlot) && StringUtils.isNotBlank(cutOverDate)){
			cutOverDateString = ltsAppointmentService.reformatDateTimeSlot(cutOverDate, cutOverTimeSlot);
			appointmentDetailLts.setCutOverStartTime(cutOverDateString[0]);
			appointmentDetailLts.setCutOverEndTime(cutOverDateString[1]);
		}
		
		return appointmentDetailLts;
	}
	
	private String getRelatedSbOrderId(AcqOrderCaptureDTO acqOrderCapture) {
		if (ModemType.SHARE_PCD == acqOrderCapture.getLtsModemArrangementForm().getModemType()
				|| ModemType.SHARE_BUNDLE == acqOrderCapture.getLtsModemArrangementForm().getModemType()) {
			if (acqOrderCapture.getLtsModemArrangementForm().isPcdSbOrderExist() && acqOrderCapture.getRelatedPcdOrder() != null) {
				return acqOrderCapture.getRelatedPcdOrder().getOrderId(); 
			}/*else if(acqOrderCapture.getLtsAcqAppointmentForm().getPCDOrderId() != null){
				return acqOrderCapture.getLtsAcqAppointmentForm().getPCDOrderId();
			}*/
		}
		return null;
	}
	
	private boolean isDummyDoc(CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_2DEL:
			return acqOrderCapture.getLtsAcqPersonalInfoForm().isSecondDelDummyDoc();
		default:
			return acqOrderCapture.getLtsAcqPersonalInfoForm().isDummyDoc();
		}

	}
	
	private String getLtsOrderType(CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_DEL:
		case CREATE_SRV_TYPE_ACQ_EYE:			
			return LtsConstant.ORDER_TYPE_INSTALL;
		
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:
			return LtsConstant.ORDER_TYPE_CHANGE;
			
		case CREATE_SRV_TYPE_ACQ_2DEL:
			if (StringUtils.equals(acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus(), LtsConstant.INVENT_STS_WORKING)) {
				return LtsConstant.ORDER_TYPE_CHANGE;
			}
			if (StringUtils.equals(acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus(), LtsConstant.INVENT_STS_RESERVED)
					|| StringUtils.equals(acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus(), LtsConstant.DRG_DN_SPARE_STATUS)
					|| acqOrderCapture.getCreate2ndDelByPipbDnInd()) {
				return LtsConstant.ORDER_TYPE_INSTALL;
			}
		default:
			return null;
		}
	}
	
	private String getEdfRef(AcqOrderCaptureDTO acqOrderCapture) {
		return acqOrderCapture.getLtsModemArrangementForm().getEdfRefNum(); 
	}
	
	private String getSuggestedSrd(AcqOrderCaptureDTO acqOrderCapture) {
		if (acqOrderCapture.getLtsAcqAppointmentForm() == null
				|| acqOrderCapture.getLtsAcqAppointmentForm().getInstallAppntDtl().getEarliestSRD() == null) {
			return null;
		}
		
		return acqOrderCapture.getLtsAcqAppointmentForm().getInstallAppntDtl().getEarliestSRD().getDateString();
	}
	
	private String getSuggestedSrdReason(AcqOrderCaptureDTO acqOrderCapture) {
		if (acqOrderCapture.getLtsAcqAppointmentForm() == null
				|| acqOrderCapture.getLtsAcqAppointmentForm().getInstallAppntDtl().getEarliestSRD() == null) {
			return null;
		}
		return acqOrderCapture.getLtsAcqAppointmentForm().getInstallAppntDtl().getEarliestSRD().getReasonCd();
	}
	
	private String getImsFromProductType(AcqOrderCaptureDTO pOrderCapture) {
		LtsModemArrangementFormDTO modemArrangementForm = pOrderCapture.getLtsModemArrangementForm();
		if (modemArrangementForm == null) {
			return null;
		}
		
		switch (modemArrangementForm.getModemType()) {
		case SHARE_EX_FSA:
		case SHARE_OTHER_FSA:	
			FsaDetailDTO selectedFsaDetail = LtsSbOrderHelper.getSelectedFsa(modemArrangementForm);
			if (selectedFsaDetail == null) {
				return null;
			}
			return StringUtils.replace(selectedFsaDetail.getExistingService().getDesc(), "TV", "");
		case SHARE_BUNDLE:
		case SHARE_PCD:
		case SHARE_TV:
		case STANDALONE:
			return LtsConstant.IMS_PRODUCT_TYPE_NEW;
		default:
			return null;
		}
	}
	
	private String getImsToProductType(AcqOrderCaptureDTO pOrderCapture) {
		LtsModemArrangementFormDTO modemArrangementForm = pOrderCapture.getLtsModemArrangementForm();
		if (modemArrangementForm == null) {
			return null;
		}

//		if (LtsConstant.MODEM_TYPE_2L2B.equals(pOrderCapture.getModemTechnologyAissgn().getModemArrangment())
//				&& LtsConstant.ORDER_TYPE_INSTALL.equals(pOrderCapture.getModemTechnologyAissgn().getImsOrderType())) {
//			return LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN;
//		}
		
		switch (modemArrangementForm.getModemType()) {
		case SHARE_EX_FSA:
		case SHARE_OTHER_FSA:
			FsaDetailDTO selectedFsaDetail = LtsSbOrderHelper.getSelectedFsa(modemArrangementForm);
			if (selectedFsaDetail == null) {
				return null;
			}
			if (selectedFsaDetail.getNewService() != null) {
				return StringUtils.replace(selectedFsaDetail.getNewService().getDesc(), "TV", "");
			}
			if (FsaServiceType.WG == selectedFsaDetail.getExistingService()) {
				if (StringUtils.isNotBlank(selectedFsaDetail.getUpgradeBandwidth())) {
					return LtsConstant.IMS_PRODUCT_TYPE_PCD; 
				}
			}
			return StringUtils.replace(selectedFsaDetail.getExistingService().getDesc(), "TV", "");
		case SHARE_PCD:
			return LtsConstant.IMS_PRODUCT_TYPE_PCD;
		case SHARE_TV:
			return StringUtils.equals(modemArrangementForm.getNowTvServiceType(),
					LtsConstant.TV_TYPE_HDTV) ? LtsConstant.IMS_PRODUCT_TYPE_HD
					: LtsConstant.IMS_PRODUCT_TYPE_SD;
		case SHARE_BUNDLE:
			return StringUtils.equals(modemArrangementForm.getNowTvServiceType(),
					LtsConstant.TV_TYPE_HDTV) ? LtsConstant.IMS_PRODUCT_TYPE_PCD_HD
					: LtsConstant.IMS_PRODUCT_TYPE_PCD_SD;
		case STANDALONE:
			return LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN;
		default:
			return null;
		}
	}
	
	private String getImsOrderType(AcqOrderCaptureDTO acqOrderCapture) {
		
		if (acqOrderCapture.getModemTechnologyAssign() != null 
				&& !StringUtils.isEmpty(acqOrderCapture.getModemTechnologyAssign().getImsOrderType()) ) {
			return acqOrderCapture.getModemTechnologyAssign().getImsOrderType();
		}
		
		if (acqOrderCapture.getLtsModemArrangementForm() != null){
		    switch (acqOrderCapture.getLtsModemArrangementForm().getModemType()) {
		    case STANDALONE:
		    case SHARE_PCD:
		    case SHARE_TV:
		    case SHARE_BUNDLE:
			    return LtsConstant.ORDER_TYPE_INSTALL;
			
		    case SHARE_EX_FSA:
		    case SHARE_OTHER_FSA:
			    return LtsConstant.ORDER_TYPE_CHANGE;
		    default:
			    break;
		    }
		}
		return null;
	}
	
	private String getLoginId(String orderType, FsaDetailDTO selectedFsaDetail,
			String acqServiceNum, ModemType selectedModemType) {
		
		if (StringUtils.equals(orderType, LtsConstant.ORDER_TYPE_INSTALL)) {
			return LtsSbOrderHelper.generateWgLoginId(acqServiceNum);
		}
		else if (selectedFsaDetail != null) {
			return selectedFsaDetail.getLoginId();
		}
		return null;
	}
	
	private boolean isFsaPerformEr(AcqOrderCaptureDTO acqOrderCapture, FsaDetailDTO selectedFsaDetail) {
		if (selectedFsaDetail.isDifferentIa()) {
			if (ModemType.SHARE_EX_FSA == acqOrderCapture.getLtsModemArrangementForm().getModemType()) {
				return acqOrderCapture.getLtsModemArrangementForm().isExistingFsaER();
			}
			if (ModemType.SHARE_OTHER_FSA == acqOrderCapture.getLtsModemArrangementForm().getModemType()) {
				return acqOrderCapture.getLtsModemArrangementForm().isOtherFsaER();
			}
		}
		return false;
	}
	
	private ImsOfferDetailDTO[] createAllImsOfferDetails(AcqOrderCaptureDTO acqOrderCapture) {
		List<ImsOfferDetailDTO> imsOfferDetailList = new ArrayList<ImsOfferDetailDTO>();
		
		
		if (acqOrderCapture.getLtsAcqBasketVasDetailForm() == null) {
			return null;
		}
		
/*		if (acqOrderCapture.getLtsAcqBasketVasDetailForm().getImsProfileAutoOutItemList() != null 
				&& !acqOrderCapture.getLtsAcqBasketVasDetailForm().getImsProfileAutoOutItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO imsProfileAutoOutItem : acqOrderCapture.getLtsAcqBasketVasDetailForm().getImsProfileAutoOutItemList()) {
				createImsOfferDetail(imsProfileAutoOutItem, LtsConstant.IO_IND_OUT, imsOfferDetailList);
			}
		}
		
		if (acqOrderCapture.getLtsAcqBasketVasDetailForm().getImsProfileExistingItemList() != null 
				&& !acqOrderCapture.getLtsAcqBasketVasDetailForm().getImsProfileExistingItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO imsProfileExistingItem : acqOrderCapture.getLtsAcqBasketVasDetailForm().getImsProfileExistingItemList()) {
				imsProfileExistingItem.getItemDetail().setPenaltyHandling(LtsConstant.PENALTY_ACTION_GENERATE);
				createImsOfferDetail(
						imsProfileExistingItem,
						imsProfileExistingItem.getItemDetail().isSelected() ? LtsConstant.IO_IND_OUT
								: LtsConstant.IO_IND_SPACE, imsOfferDetailList);
			}
		}
		
		
		if (acqOrderCapture.getLtsAcqBasketVasDetailForm().getImsProfileIngoreItemList() != null 
				&& !acqOrderCapture.getLtsAcqBasketVasDetailForm().getImsProfileIngoreItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO imsProfileIngoreItem : acqOrderCapture.getLtsAcqBasketVasDetailForm().getImsProfileIngoreItemList()) {
				createImsOfferDetail(imsProfileIngoreItem, LtsConstant.IO_IND_SPACE, imsOfferDetailList);
			}
		}*/
		addOfferChangeDetails(acqOrderCapture, imsOfferDetailList);
		
		if (imsOfferDetailList.isEmpty()) {
			return null;
		}
		
		return imsOfferDetailList.toArray(new ImsOfferDetailDTO[imsOfferDetailList.size()]);
	}
	
	private void addOfferChangeDetails(AcqOrderCaptureDTO orderCapture, List<ImsOfferDetailDTO> imsOfferDetailList) {
		
		String ltsFromProd = LtsConstant.LTS_PRODUCT_TYPE_NEW;
		String ltsToProd = LtsConstant.LTS_PRODUCT_TYPE_EYE_3_A;
		String imsFromProd = this.getImsFromProductType(orderCapture);
		String imsToProd = this.getImsToProductType(orderCapture);
		
		String rentalRouter = LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER.equals(orderCapture.getLtsModemArrangementForm().getRentalRouterInd()) ? "Y" : null;
		
		OfferDetailProfileLtsDTO[] profileImsOffers = null; 
		String mirror = null;
		FsaDetailDTO selectedFsaDetail = getSelectedFsaDetail(orderCapture);
		if (selectedFsaDetail != null) {
			profileImsOffers = selectedFsaDetail.getFsaProfile().getOffers();
			mirror = selectedFsaDetail.getExistMirrorInd();
		}
			
		List<String> offerIdList = new ArrayList<String>();
		
		if(ArrayUtils.isNotEmpty(profileImsOffers)){
			for (OfferDetailProfileLtsDTO offerDetail : profileImsOffers) {
				offerIdList.add(offerDetail.getOfferId());
			}
		}
	
		List<String[]> offerChangeList = offerChangeService.getOfferChangeList(ltsFromProd, ltsToProd, imsFromProd, imsToProd, mirror, offerIdList.toArray(new String[offerIdList.size()]), rentalRouter);
		if (offerChangeList != null && !offerChangeList.isEmpty()) {
			for (String[] offerChanges : offerChangeList) {
				if (ArrayUtils.isNotEmpty(offerChanges)) {
					ImsOfferDetailDTO imsOfferDetail = new ImsOfferDetailDTO();
					imsOfferDetail.setOfferId(offerChanges[0]);
					imsOfferDetail.setItemId(null);
					imsOfferDetail.setIoInd(offerChanges[1]);
	//				imsOfferDetail.setPenaltyWaiveInd(getPenaltyWaiveInd(imsProfileItemDetail.getItemDetail()));
	//				imsOfferDetail.setPenaltyHandling(getPenaltyHandling(imsProfileItemDetail.getItemDetail()));
					imsOfferDetail.setItemType(StringUtils.defaultIfEmpty(offerChanges[2], LtsConstant.ITEM_TYPE_OFFER_CHANGE));
					imsOfferDetail.setItemId(offerChanges[3]);
					imsOfferDetailList.add(imsOfferDetail);
				}
			}
		}
	}
	
	
	private String getNewSrvTypeCode(FsaDetailDTO fsaDetail) {
		if (fsaDetail.getNewService() == null) {
			return fsaDetail.getExistingService().getDesc();
		}
		return fsaDetail.getNewService().getDesc();
	}
	
	private String getNewBandwidth(FsaDetailDTO fsaDetail) {
		if (StringUtils.isEmpty(fsaDetail.getUpgradeBandwidth()) ) {
			return fsaDetail.getBandwidth();
		}
		if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, fsaDetail.getUpgradeBandwidth())) {
			return "1000";	
		}
		return fsaDetail.getUpgradeBandwidth();
	}
	
	private ServiceDetailLtsDTO createServiceDetailLts(CreateServiceType createServiceType,
			BasketDetailDTO selectedBasket, AcqOrderCaptureDTO acqOrderCapture, Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap, String orderType) {
        
		List<AccountServiceAssignLtsDTO> accountServiceAssignLtsList = new ArrayList<AccountServiceAssignLtsDTO>();
		

		String serviceNum = getServiceNum(createServiceType, acqOrderCapture);
		
		ServiceDetailLtsDTO serviceDetailLts = new ServiceDetailLtsDTO();
		AccountDetailLtsDTO account = null;
/*        serviceDetailLts.setAccount(createAccountDetailLts(createServiceType,
			    	acqOrderCapture, accountDetailLtsMap, customerDetailLtsMap, 0));*/
        
		switch (createServiceType) {
			case CREATE_SRV_TYPE_ACQ_DEL:
			case CREATE_SRV_TYPE_ACQ_EYE:
			case CREATE_SRV_TYPE_ACQ_2DEL:	
			case CREATE_SRV_TYPE_ACQ_PORT_LATER:
				
	        for (int i=0; i<acqOrderCapture.getAccountDetailProfileLtsDTO().length; i++){
			    account = createAccountDetailLts(createServiceType, acqOrderCapture, accountDetailLtsMap, customerDetailLtsMap, i);
			    
			  
	/*		    accountServiceAssignLtsDTO.setAccount(account);
			    if (StringUtils.equals(orderType, LtsConstant.ORDER_TYPE_SB_ACQUISITION)){
			        accountServiceAssignLtsDTO.setAction("I");
			    }
			    accountServiceAssignLtsList.add(accountServiceAssignLtsDTO);*/
			    String action = null;
			    if (StringUtils.equals(orderType, LtsConstant.ORDER_TYPE_SB_ACQUISITION)){
			    	action="I";
			    }
			    
			    
	/*		    System.out.println("Acct_num = "+ account.getAcctNo());
			    System.out.println("chrgType = "+ account.getChrgType());*/
			    
			    if (acqOrderCapture.getAccountDetailProfileLtsDTO()[i].getAcctChrgType()!=null){
			    	String[] chrgType = new String[acqOrderCapture.getAccountDetailProfileLtsDTO()[i].getAcctChrgType().length()];
			    	int chrgTypeLength = acqOrderCapture.getAccountDetailProfileLtsDTO()[i].getAcctChrgType().length();
	//		    	System.out.println("ChrgType LENGTH = "+ chrgTypeLength);
	//		    	System.out.println(" ");
			    	
			    	for(int c=0; c < chrgTypeLength; c++){
	//		    		System.out.println("c= "+ c);
			    		chrgType[c] = acqOrderCapture.getAccountDetailProfileLtsDTO()[i].getAcctChrgType().substring(c, c+1);
			    	}
			    	
	//		    	chrgType = account.getChrgType().split("");	
			    	
			    	for(int c=0; c < chrgType.length; c++){
			    		String v_chrgType = chrgType[c];
	/*		    		System.out.println("chrgType = "+ v_chrgType);
			    		System.out.println("ChrgType_array_length = "+  account.getChrgType().length());
			    		System.out.println("ChrgType = "+  account.getChrgType());*/
	//		    		if(StringUtils.equals(v_chrgType, "R")){
	//				        serviceDetailLts.setAccount(account);
	//				    }
			    		AccountServiceAssignLtsDTO accountServiceAssignLtsDTO = new AccountServiceAssignLtsDTO();
			    		accountServiceAssignLtsDTO.setAccount(account);
			    		accountServiceAssignLtsDTO.setChrgType(v_chrgType);
			    	    accountServiceAssignLtsDTO.setAction(action);
					    accountServiceAssignLtsList.add(accountServiceAssignLtsDTO);
			    	}	
			    }
	        }
	        break;
		}
		
		
		serviceDetailLts.setAccounts(accountServiceAssignLtsList.toArray(new AccountServiceAssignLtsDTO[accountServiceAssignLtsList.size()]));
		
		serviceDetailLts.setAppointmentDtl(createAppointmentDetailLts(createServiceType, acqOrderCapture));
		// TBC
//		serviceDetailLts.setCopyErIaToBa(isCopyErIaToBa(createServiceType, acqOrderCapture) ? "Y" : null);
		
		serviceDetailLts.setDummyDocIdInd(isDummyDoc(createServiceType, acqOrderCapture) ? "Y" : null);
		
		serviceDetailLts.setErInd(isServiceEr(createServiceType, acqOrderCapture) ? "Y" : null);
		serviceDetailLts.setOrderType(getLtsOrderType(createServiceType, acqOrderCapture));
		serviceDetailLts.setSrvNum(StringUtils.leftPad(serviceNum, 12, "0"));
		serviceDetailLts.setSuggestSrd(getSuggestedSrd(acqOrderCapture));
		serviceDetailLts.setSuggestSrdReasonCd(getSuggestedSrdReason(acqOrderCapture));
		serviceDetailLts.setTypeOfSrv(LtsConstant.SERVICE_TYPE_TEL);
		serviceDetailLts.setDeviceType(acqOrderCapture.getLtsAcqBasketSelectionForm().getSelectedType());
		serviceDetailLts.setNewOsType(selectedBasket.getOsType() == null ? LtsBackendConstant.OS_TYPE_AND : selectedBasket.getOsType());
		serviceDetailLts.setFromProd(getLtsFromProductType(createServiceType, acqOrderCapture));
		serviceDetailLts.setToProd(getLtsToProductType(createServiceType, selectedBasket, acqOrderCapture));
		serviceDetailLts.setFromSrvType(getLtsFromSrvType(createServiceType, acqOrderCapture, selectedBasket));
		serviceDetailLts.setToSrvType(getLtsToSrvType(createServiceType, acqOrderCapture, selectedBasket));
		serviceDetailLts.setRemarks(getRemarkDetailLts(createServiceType, acqOrderCapture));
		serviceDetailLts.setActualDocType(getActualDocType(createServiceType, acqOrderCapture));
		serviceDetailLts.setActualDocId(getActualDocId(createServiceType, acqOrderCapture));

		ThirdPartyDetailLtsDTO thirdPartyDetail = createThirdPartyDetailLts(createServiceType, acqOrderCapture);
		serviceDetailLts.setThirdPartyAppln(thirdPartyDetail != null ? "Y" : null);
		serviceDetailLts.setThirdPartyDtl(thirdPartyDetail);
		serviceDetailLts.setExDirInd(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().isIncludeSrvNumOnExDir() ? "Y" : "N");
		serviceDetailLts.setExDirName(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDirectoryName());
		
		if (acqOrderCapture.getCreate1stDelByReserveDnInd()== null ){
			acqOrderCapture.setCreate1stDelByReserveDnInd(false);
		}
		
		if (acqOrderCapture.getCreate2ndDelByReserveDnInd()== null ){
			acqOrderCapture.setCreate2ndDelByReserveDnInd(false);
		}
		
		if (LtsConstant.LTS_PRODUCT_TYPE_NEW.equals(serviceDetailLts.getFromProd()) && 
				(LtsConstant.LTS_PRODUCT_TYPE_EYE_3_A.equals(serviceDetailLts.getToProd())
					|| LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(serviceDetailLts.getToProd()))){
			if (acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection()==Selection.USE_NEW_DN
				|| acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection()==Selection.USE_NEW_DN_AND_PIPB_DN) {
				serviceDetailLts.setDnStatus(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnStatus());
				if (LtsConstant.DN_SOURCE_DN_RESERVED.equals(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource())){
					serviceDetailLts.setDnSource(LtsConstant.DN_SOURCE_DN_RESERVED);
					serviceDetailLts.setNrpAccountCd(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getReservedAccountCd());
					serviceDetailLts.setNrpBoc(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getReservedBoc());
					serviceDetailLts.setNrpProjectCd(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getReservedProjectCd());
				} else if (LtsConstant.DN_SOURCE_DN_POOL.equals(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource())){
					serviceDetailLts.setDnSource(LtsConstant.DN_SOURCE_DN_POOL);
				}				
			} else if (acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection()==Selection.USE_PIPB_DN) {
				if (LtsConstant.DN_SOURCE_DN_PIPB.equals(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDnSource())) {
					serviceDetailLts.setDnSource(LtsConstant.DN_SOURCE_DN_PIPB);
					serviceDetailLts.setDnStatus(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDnStatus());
					if (StringUtils.equals(LtsConstant.INVENT_STS_RESERVED, 
							acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDnStatus())) {
						serviceDetailLts.setNrpAccountCd(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getPipbAccountCd());
						serviceDetailLts.setNrpBoc(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getPipbBoc());
						serviceDetailLts.setNrpProjectCd(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getPipbProjectCd());
				    }					
					// set pipb here..
					serviceDetailLts.setPipb(getPipbInfo(acqOrderCapture));					
				}
			}
		} else if (LtsConstant.LTS_PRODUCT_TYPE_NEW_DN_FIRST.equals(serviceDetailLts.getFromProd()) &&
				     LtsConstant.LTS_PRODUCT_TYPE_PORT_LATER.equals(serviceDetailLts.getToProd())){
			if (LtsConstant.DN_SOURCE_DN_PIPB.equals(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDnSource())){
				serviceDetailLts.setDnSource(LtsConstant.DN_SOURCE_DN_PIPB);
				serviceDetailLts.setDnStatus(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDnStatus());
				// set pipb here..
				serviceDetailLts.setPipb(getPipbInfo(acqOrderCapture));
			}
		} else if (LtsConstant.LTS_PRODUCT_TYPE_NEW.equals(serviceDetailLts.getFromProd()) && 
				LtsConstant.LTS_PRODUCT_TYPE_2DEL.equals(serviceDetailLts.getToProd())){
			if(acqOrderCapture.getCreate2ndDelByReserveDnInd()){
				serviceDetailLts.setDnSource(LtsConstant.DN_SOURCE_DN_RESERVED);
				serviceDetailLts.setDnStatus(LtsConstant.DRG_DN_RESERVED_STATUS);
		    } else if (acqOrderCapture.getCreate2ndDelByPipbDnInd()) {
		    	serviceDetailLts.setDnSource(LtsConstant.DN_SOURCE_DN_PIPB);
		    	serviceDetailLts.setDnStatus(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDuplexDnStatus());
		    } else {
		    	serviceDetailLts.setDnSource(LtsConstant.DN_SOURCE_DN_POOL);
		    	serviceDetailLts.setDnStatus(LtsConstant.DRG_DN_SPARE_STATUS);
		    }
		} else if (LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(serviceDetailLts.getFromProd()) && 
				LtsConstant.LTS_PRODUCT_TYPE_2DEL.equals(serviceDetailLts.getToProd())){
			serviceDetailLts.setDnSource(LtsConstant.DN_SOURCE_DN_WORKING);
			serviceDetailLts.setDnStatus(LtsConstant.DRG_DN_WORKING_STATUS);
		}	
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_EYE:
			serviceDetailLts.setDatCd(StringUtils.equals(
					selectedBasket.getPeInd(), "Y") ? LtsConstant.DAT_CD_DEL
					: LtsConstant.DAT_CD_NCR);	
			break;
		case CREATE_SRV_TYPE_ACQ_DEL:
		case CREATE_SRV_TYPE_ACQ_2DEL:
            serviceDetailLts.setDatCd(LtsConstant.DAT_CD_DEL);
            break;
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:
			serviceDetailLts.setDatCd(acqOrderCapture.isEyeOrder()? StringUtils.equalsIgnoreCase(
			"Y", selectedBasket.getPeInd())? LtsConstant.DAT_CD_DEL : LtsConstant.DAT_CD_NCR 
					: LtsConstant.LTS_SRV_TYPE_DEL);
		    break;    
		}
		
/*		if (serviceProfile != null) {
			serviceDetailLts.setCcServiceId(getCcServiceId(createServiceType, serviceProfile));
			serviceDetailLts.setCcServiceMemNum(getCcServiceMemNum(createServiceType, serviceProfile));
			serviceDetailLts.setDatCd(serviceProfile.getDatCd());
			serviceDetailLts.setDuplexInd(getDuplexInd(serviceNum, serviceProfile));
			serviceDetailLts.setTenure(String.valueOf(serviceProfile.getLtsTenure()));
			serviceDetailLts.setTwoNInd(getTwoNInd(serviceNum, serviceProfile));
			serviceDetailLts.setSharedBsnInd(serviceProfile.isSharedBsn() ? "Y" : null);
		}*/
		
		return serviceDetailLts;
	}
	
	private AccountDetailLtsDTO createAccountDetailLts(CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap, int acctCnt) {
		
		AccountDetailProfileLtsDTO[] accountDetailProfileLtsDTO = new AccountDetailProfileLtsDTO[acqOrderCapture.getAccountDetailProfileLtsDTO().length];
		AccountDetailProfileLtsDTO profileAccount = null; 
		accountDetailProfileLtsDTO = acqOrderCapture.getAccountDetailProfileLtsDTO().clone();
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_DEL:
		case CREATE_SRV_TYPE_ACQ_EYE:
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:	
		    profileAccount = accountDetailProfileLtsDTO[acctCnt];
				break;
		case CREATE_SRV_TYPE_ACQ_2DEL:
			    if(acqOrderCapture.getSecondDelServiceProfile() != null){
			    	profileAccount = LtsSbOrderHelper.getAcqPrimaryAccount(acqOrderCapture.getSecondDelServiceProfile().getAccounts());
			    }else{
			    	profileAccount = accountDetailProfileLtsDTO[acctCnt];
			    }
			    break;
		}
				
		AccountDetailLtsDTO accountDetailLts = null;
		if (accountDetailLtsMap.containsKey(profileAccount.getAcctNum())) {
			accountDetailLts = accountDetailLtsMap.get(profileAccount.getAcctNum());
			return accountDetailLts;
		}
		
		accountDetailLts = new AccountDetailLtsDTO();
		accountDetailLts.setAcctNo(profileAccount.getAcctNum());
		accountDetailLts.setBillFreq(profileAccount.getBillFreq());
		accountDetailLts.setCustomer(createCustomerDetailLts(acqOrderCapture.getCustomerDetailProfileLtsDTO(), customerDetailLtsMap, acqOrderCapture));
		accountDetailLts.setBillFmt(profileAccount.getBillFmt());
		accountDetailLts.setExistBillMedia(profileAccount.getBillMedia());
		accountDetailLts.setIsNew(acqOrderCapture.getLtsAcqAccountSelectionForm().isNewAccountSelected()? "Y" : "N");
		
		for(BillMediaDtl dtl: acqOrderCapture.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList()){
			    if (StringUtils.equals(profileAccount.getAcctNum(),dtl.getAcctNum())){
			    	accountDetailLts.setEmailAddr(dtl.getBillMediaEmail());
			    	accountDetailLts.setBillLang(dtl.getBillLang());
			    	accountDetailLts.setAcctWaivePaperInd(dtl.getSelectedBillCharging());
			    	accountDetailLts.setWaivePaperReaCd(dtl.getSelectedWaiveReason());
			    	if(LtsConstant.PAPER_BILL_WAIVE_REASON_OTHER.equals(dtl.getSelectedWaiveReason())){
				    	accountDetailLts.setWaivePaperRemarks(dtl.getPaperBillWaiveOtherStaffId());	
			    	}		
			    }
		}
		
		accountDetailLts.setAutopayStatementInd(profileAccount.isAutopayStatementInd() ? "Y" : null);
		setRedemptionMediaDetail(acqOrderCapture, accountDetailLts);
		
		String billMedia = profileAccount.getBillMedia();
		
		if (CreateServiceType.CREATE_SRV_TYPE_ACQ_EYE == createServiceType
				|| CreateServiceType.CREATE_SRV_TYPE_ACQ_DEL == createServiceType) {
			billMedia = getBillMedia(acqOrderCapture, profileAccount.getAcctNum());
		}
		
		accountDetailLts.setBillMedia(billMedia);
		
		accountDetailLts.setPaymethods(createPaymentMethodDetailLts(acqOrderCapture, profileAccount, createServiceType));
		accountDetailLts.setBillingAddress(createBillingAddressLtsDTO(acqOrderCapture, createServiceType, accountDetailLts));
		
		accountDetailLtsMap.put(accountDetailLts.getAcctNo(), accountDetailLts);

		return accountDetailLts;
	}
	
	protected void setRedemptionMediaDetail(AcqOrderCaptureDTO acqOrderCapture, AccountDetailLtsDTO accountDetailLts) {
		if (acqOrderCapture.getLtsAcqBillMediaBillLangForm() != null) {
		
			for (BillMediaDtl billMediaDtl : acqOrderCapture.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList()){
				if (billMediaDtl.getAcctNum().equals(accountDetailLts.getAcctNo())){
				    accountDetailLts.setRedemptionMedia(billMediaDtl.getRedemMedia());
				    if (LtsConstant.REDEMPTION_MEDIA_SMS.equals(billMediaDtl.getRedemMedia())) {
					    accountDetailLts.setRedemptionSmsNo(billMediaDtl.getRedemSms());	
				    }
				    else if(LtsConstant.REDEMPTION_MEDIA_EMAIL.equals(billMediaDtl.getRedemMedia())) {
					    accountDetailLts.setRedemptionEmailAddr(billMediaDtl.getRedemMediaEmail());	
				    }
				}
			}
			
		}
	}
	
	protected String getBillMedia(AcqOrderCaptureDTO acqOrderCapture, String pAcctNum) {
		if (acqOrderCapture.getLtsAcqBillMediaBillLangForm() == null) {
			return null;
		}
		
		for (BillMediaDtl billMediaDtl : acqOrderCapture.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList()){
		    if(billMediaDtl.getAcctNum().equals(pAcctNum)){
			    if (billMediaDtl.getPaperBillItem() != null 
				        && billMediaDtl.getPaperBillItem().isSelected()) {
			        return LtsConstant.LTS_BILL_MEDIA_PAPER_BILL;
		        }else if (billMediaDtl.getEmailBillItem() != null 
				        && billMediaDtl.getEmailBillItem().isSelected()) {
			        return LtsConstant.LTS_BILL_MEDIA_PPS_BILL;
		        }
		    }
		}
		return null;
	}
	
	protected PaymentMethodDetailLtsDTO[] createPaymentMethodDetailLts(AcqOrderCaptureDTO acqOrderCapture, 
			AccountDetailProfileLtsDTO accountDetailProfileLts, CreateServiceType createServiceType) {
		
		List<PaymentMethodDetailLtsDTO> paymentMethodDetailLtsList = new ArrayList<PaymentMethodDetailLtsDTO>();
		AccountDetailProfileLtsDTO primaryProfileAccount = LtsSbOrderHelper.getAcqPrimaryAccount(acqOrderCapture.getAccountDetailProfileLtsDTO());
		
		if (accountDetailProfileLts == null) {
			accountDetailProfileLts = primaryProfileAccount;
		}
		
		PaymentMethodDetailLtsDTO existingPaymentDetail = setupExistingPaymentDetail(accountDetailProfileLts);
		
		
		LtsAcqPaymentMethodFormDTO ltsPaymentForm = acqOrderCapture.getLtsAcqPaymentMethodFormDTO();
		
		if (ltsPaymentForm == null 
				|| (!StringUtils.equals(primaryProfileAccount.getAcctNum(), accountDetailProfileLts.getAcctNum())&&
						createServiceType == CreateServiceType.CREATE_SRV_TYPE_ACQ_2DEL)) {
			existingPaymentDetail.setAction(LtsConstant.IO_IND_SPACE);
			return new PaymentMethodDetailLtsDTO[] {existingPaymentDetail}; 
		}
//		PaymentMethodDtl primaryPayMtdDtl = ltsPaymentForm.getPrimaryPaymentMethodDtl();
		
		for(PaymentMethodDtl dtl: ltsPaymentForm.getPaymentMethodDtlList()){
			if(StringUtils.equals(accountDetailProfileLts.getAcctNum(), dtl.getAcctNum())){ 
				if(!StringUtils.equals(dtl.getExistingPayMethodType(), dtl.getNewPayMethodType())
						&& StringUtils.equals(dtl.getExistingPayMethodType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)){
					existingPaymentDetail.setTermCd(dtl.getTermCd());
				}
				if(StringUtils.equals(dtl.getExistingPayMethodType(), dtl.getNewPayMethodType())){
					existingPaymentDetail.setSalesMemoNum(dtl.getSalesMemoNum());
					existingPaymentDetail.setAction(LtsConstant.IO_IND_SPACE);
					return new PaymentMethodDetailLtsDTO[] {existingPaymentDetail}; 
				}
			}
		}
		
		existingPaymentDetail.setAction(LtsConstant.IO_IND_OUT);
		paymentMethodDetailLtsList.add(existingPaymentDetail);
		
		
		paymentMethodDetailLtsList.add(setupNewPaymentDetail(ltsPaymentForm, accountDetailProfileLts));	
		
		return paymentMethodDetailLtsList.toArray(new PaymentMethodDetailLtsDTO[paymentMethodDetailLtsList.size()]);
	}
	
	private PaymentMethodDetailLtsDTO setupExistingPaymentDetail(AccountDetailProfileLtsDTO accountDetailProfileLts){

		PaymentMethodDetailLtsDTO existingPaymentDetail = new PaymentMethodDetailLtsDTO();
		existingPaymentDetail.setPayMtdType(accountDetailProfileLts.getPayMethod());
		
		if (StringUtils.equals(accountDetailProfileLts.getPayMethod(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
			existingPaymentDetail.setBankAcctNo(accountDetailProfileLts.getBankAcctNum());
			existingPaymentDetail.setBankCd(accountDetailProfileLts.getBankCd());
			existingPaymentDetail.setBranchCd(accountDetailProfileLts.getBranchCd());
		}
		else if (StringUtils.equals(accountDetailProfileLts.getPayMethod(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
			existingPaymentDetail.setCcNum(accountDetailProfileLts.getCreditCardNum());
		}
		
		return existingPaymentDetail;
	}
	
	private PaymentMethodDetailLtsDTO setupNewPaymentDetail(LtsAcqPaymentMethodFormDTO ltsPaymentForm, AccountDetailProfileLtsDTO accountDetailProfileLts){
		PaymentMethodDetailLtsDTO newPaymentDetail = new PaymentMethodDetailLtsDTO();
		newPaymentDetail.setAction(LtsConstant.IO_IND_INSTALL);
		newPaymentDetail.setPayMtdKey("-1");
		for(PaymentMethodDtl dtl: ltsPaymentForm.getPaymentMethodDtlList()){
			if(StringUtils.equals(accountDetailProfileLts.getAcctNum(), dtl.getAcctNum())){ 
		        newPaymentDetail.setPayMtdType(dtl.getNewPayMethodType());
			}
		}
		
		setupPaymentMethodDetailLtsByForm(ltsPaymentForm, newPaymentDetail, accountDetailProfileLts);
		return newPaymentDetail;
	}
	
	private void setupPaymentMethodDetailLtsByForm(LtsAcqPaymentMethodFormDTO ltsPaymentForm, PaymentMethodDetailLtsDTO paymentDetail, AccountDetailProfileLtsDTO accountDetailProfileLts){
		PaymentMethodDtl payMtdDtl = null;
		for(PaymentMethodDtl dtl: ltsPaymentForm.getPaymentMethodDtlList()){
			if(StringUtils.equals(accountDetailProfileLts.getAcctNum(), dtl.getAcctNum())){ 
				payMtdDtl = dtl;
			}
		}
		if(StringUtils.equals(LtsConstant.PAYMENT_TYPE_AUTO_PAY, paymentDetail.getPayMtdType())){
			paymentDetail.setAutopayAppDate(payMtdDtl.getApplicationDate());
			paymentDetail.setAutopayUpLimAmt(payMtdDtl.getAutoPayUpperLimit());
			paymentDetail.setBankAcctHoldName(payMtdDtl.getBankAccHolderName());
			paymentDetail.setBankAcctHoldNum(payMtdDtl.getBankAccHolderDocNum());
			paymentDetail.setBankAcctHoldType(payMtdDtl.getBankAccHolderDocType());
			paymentDetail.setBankAcctNo(payMtdDtl.getBankAccNum());
			paymentDetail.setBankCd(payMtdDtl.getBankCode());
			paymentDetail.setBranchCd(payMtdDtl.getBranchCode());
			paymentDetail.setThirdPartyInd(BooleanUtils.isTrue(payMtdDtl.getThirdPartyBankAccount())?"Y":null);
		}
		else if(StringUtils.equals(LtsConstant.PAYMENT_TYPE_CREDIT_CARD, paymentDetail.getPayMtdType())){
			String expDate = StringUtils.leftPad(String.valueOf(payMtdDtl.getExpireMonth()), 2, '0')  + "/" + payMtdDtl.getExpireYear();
			paymentDetail.setCcExpDate(expDate);
			paymentDetail.setCcHoldName(payMtdDtl.getCardHolderName());
			paymentDetail.setCcIdDocNo(payMtdDtl.getCardHolderDocNum());
			paymentDetail.setCcIdDocType(payMtdDtl.getCardHolderDocType());
			paymentDetail.setCcNum(payMtdDtl.getCardNum());
			paymentDetail.setCcType(payMtdDtl.getCardType());
			paymentDetail.setCcVerifiedInd(payMtdDtl.isCardVerified()?"Y":null);
			paymentDetail.setThirdPartyInd(BooleanUtils.isTrue(payMtdDtl.getThirdPartyCard())?"Y":null);
			paymentDetail.setCcIssueBank("999");
		}
		if(payMtdDtl!=null){
		    paymentDetail.setSalesMemoNum(payMtdDtl.getSalesMemoNum());
		}
	}
	
	private BillingAddressLtsDTO createBillingAddressLtsDTO(AcqOrderCaptureDTO acqOrderCapture, CreateServiceType createServiceType, AccountDetailLtsDTO accountDetailLts){
		
		BillingAddressLtsDTO billingAddressLts = new BillingAddressLtsDTO();
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_DEL:
		case CREATE_SRV_TYPE_ACQ_EYE:
			if(acqOrderCapture.getLtsAcqBillingAddressForm()!=null){
				for (BillingAddrDtl billingAddrDtl : acqOrderCapture.getLtsAcqBillingAddressForm().getBillingAddrDtlList()){
					if(StringUtils.equals(billingAddrDtl.getAcctNum(), accountDetailLts.getAcctNo())){
					    billingAddressLts.setAction(billingAddrDtl.getBaCaAction());
					    String fullAddr = billingAddrDtl.getBillingAddressTextArea();
					    if(StringUtils.isNotBlank(fullAddr)){
						    String[] addrLines = fullAddr.split("\n");
						    billingAddressLts.setAddrLine1(StringUtils.strip(addrLines[0]));
						    billingAddressLts.setAddrLine2(addrLines.length > 1? StringUtils.strip(addrLines[1]) :null);
						    billingAddressLts.setAddrLine3(addrLines.length > 2? StringUtils.strip(addrLines[2]) :null);
						    billingAddressLts.setAddrLine4(addrLines.length > 3? StringUtils.strip(addrLines[3]) :null);
						    billingAddressLts.setAddrLine5(addrLines.length > 4? StringUtils.strip(addrLines[4]) :null);
						    billingAddressLts.setAddrLine6(addrLines.length > 5? StringUtils.strip(addrLines[5]) :null);
					    }
					}
				}
			}
			return billingAddressLts;
		case CREATE_SRV_TYPE_ACQ_2DEL:
			if(Boolean.TRUE.equals(acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelBaCaChange())){
				billingAddressLts.setAction(LtsConstant.BILL_ADDR_ACTION_IA);
			}else{
				billingAddressLts.setAction(LtsConstant.BILL_ADDR_ACTION_EXISTING);
			}
			return billingAddressLts;
		default:
			billingAddressLts.setAction(LtsConstant.BILL_ADDR_ACTION_EXISTING);
			return billingAddressLts;
		}
	}
	
	private CustomerDetailLtsDTO createCustomerDetailLts(CustomerDetailProfileLtsDTO customerDetailProfileLts, 
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap,
			AcqOrderCaptureDTO acqOrderCapture) {
		
		if (customerDetailLtsMap.containsKey(customerDetailProfileLts.getCustNum())) {
			return customerDetailLtsMap.get(customerDetailProfileLts.getCustNum());
		}
		
		CustomerDetailLtsDTO customerDetailLts = new CustomerDetailLtsDTO();
		customerDetailLts.setBlacklistInd(customerDetailProfileLts.isBlacklistCustInd() ? "Y" : null);
		customerDetailLts.setCompanyName(customerDetailProfileLts.getCompanyName());
//		customerDetailLts.setContactMobileNum(contactMobileNum);
		customerDetailLts.setCustNo(customerDetailProfileLts.getCustNum());
		if(!StringUtils.equals(customerDetailProfileLts.getDocType(), LtsConstant.DOC_TYPE_HKBR)){
			if(acqOrderCapture.getLtsAcqPersonalInfoForm().getDateOfBirth()!=null && StringUtils.isNotBlank(acqOrderCapture.getLtsAcqPersonalInfoForm().getDateOfBirth())){
				customerDetailLts.setDob(LtsDateFormatHelper.reformatDate(acqOrderCapture.getLtsAcqPersonalInfoForm().getDateOfBirth(), "dd/MM/yyyy", "yyyyMMdd"));
		    }
		}
		customerDetailLts.setFirstName(customerDetailProfileLts.getFirstName());
		customerDetailLts.setIdDocNum(customerDetailProfileLts.getDocNum());
		customerDetailLts.setIdDocType(customerDetailProfileLts.getDocType());
		customerDetailLts.setIdVerifiedInd(customerDetailProfileLts.isIdVerifyInd()? "Y" : "N");
//		customerDetailLts.setLangWritten(langWritten);
		customerDetailLts.setLastName(customerDetailProfileLts.getLastName());
		customerDetailLts.setLob(LtsConstant.LOB_LTS);
		customerDetailLts.setTitle(customerDetailProfileLts.getTitle());	
		
		customerDetailLts.setCsPortalInd("N");
		customerDetailLts.setCsPortalLogin(null);
		customerDetailLts.setCsPortalMobile(null);
		customerDetailLts.setTheClubInd("N");
		customerDetailLts.setTheClubLogin(null);
		customerDetailLts.setTheClubMobile(null);
		customerDetailLts.setMismatchInd(acqOrderCapture.getLtsAcqPersonalInfoForm().isMatchWithBomName()? "N" : "Y");
		
		if(acqOrderCapture.getLtsAcqBillMediaBillLangForm() != null && acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem() != null
				&& acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem().isSelected()){
			if(StringUtils.equals(LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL, acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem().getItemType())){
				String cspEmail = ltsCommonService.getAttbValueByInputFormat(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem().getItemAttbs(), 
						LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL);
				String cspMobile = ltsCommonService.getAttbValueByInputFormat(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem().getItemAttbs(), 
						LtsConstant.CHANNEL_ATTB_FORMAT_MOBILE_NUM);
				customerDetailLts.setCsPortalInd("Y");
				customerDetailLts.setCsPortalLogin(cspEmail);
				customerDetailLts.setCsPortalMobile(cspMobile);
				customerDetailLts.setHktOptOut(StringUtils.equals(LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_ALL, acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getOptInOutInd())? "Y":"N");
				customerDetailLts.setTheClubInd("Y");
				customerDetailLts.setTheClubLogin(cspEmail);
				customerDetailLts.setTheClubMobile(cspMobile);
				customerDetailLts.setClubOptOut(StringUtils.equals(LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_ALL, acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getOptInOutInd())? "Y":"N");
				if(StringUtils.equals(LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_ALL, acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getOptInOutInd())){
					customerDetailLts.setClubOptRea(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getOptOutReason());
					customerDetailLts.setClubOptRmk(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getOptOutRemarks());
				}
			}		
			if(StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL, acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem().getItemType())){
				String cspEmail = getAttbValueByAttbTypeAndKey(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem().getItemAttbs(),
						LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL, LtsConstant.ITEM_ATTB_INFO_KEY_HKT);
				String cspMobile = getAttbValueByAttbTypeAndKey(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem().getItemAttbs(),
						LtsConstant.CHANNEL_ATTB_FORMAT_MOBILE_NUM, LtsConstant.ITEM_ATTB_INFO_KEY_HKT);
				customerDetailLts.setCsPortalInd("Y");
				customerDetailLts.setCsPortalLogin(cspEmail);
				customerDetailLts.setCsPortalMobile(cspMobile);
				customerDetailLts.setHktOptOut(StringUtils.equals(LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_ALL, acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getOptInOutInd())? "Y":"N");
			}	
			if(StringUtils.equals(LtsConstant.ITEM_TYPE_THE_CLUB_BILL, acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem().getItemType())){
				String clubEmail = getAttbValueByAttbTypeAndKey(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem().getItemAttbs(),
						LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL, LtsConstant.ITEM_ATTB_INFO_KEY_CLUB);
				String clubMobile = getAttbValueByAttbTypeAndKey(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem().getItemAttbs(),
						LtsConstant.CHANNEL_ATTB_FORMAT_MOBILE_NUM, LtsConstant.ITEM_ATTB_INFO_KEY_CLUB);
				customerDetailLts.setTheClubInd("Y");
				customerDetailLts.setTheClubLogin(clubEmail);
				customerDetailLts.setTheClubMobile(clubMobile);		
				customerDetailLts.setClubOptOut(StringUtils.equals(LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_ALL, acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getOptInOutInd())? "Y":"N");
				if(StringUtils.equals(LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_ALL, acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getOptInOutInd())){
					customerDetailLts.setClubOptRea(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getOptOutReason());
					customerDetailLts.setClubOptRmk(acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getOptOutRemarks());
				}
			}
		}
		
		customerDetailLts.setCustOptOutInfo(createCustOptOutInfo(acqOrderCapture));
		
		customerDetailLtsMap.put(customerDetailLts.getCustNo(), customerDetailLts);
		return customerDetailLts;
	}
	
	private String getAttbValueByAttbTypeAndKey(ItemAttbBaseDTO[] attbArray, String attbType, String attbKey){
		for(ItemAttbBaseDTO attb: attbArray){
			if(attbType.equals(attb.getInputFormat()) && attbKey.equals(attb.getAttbInfoKey())){
				return attb.getAttbValue();
			}
		}		
		return null;
	}
	
	private String getLtsFromProductType(CreateServiceType pCreateSrvType, AcqOrderCaptureDTO pOrderCapture) {
		
		switch (pCreateSrvType) {
		case CREATE_SRV_TYPE_ACQ_EYE:
		case CREATE_SRV_TYPE_ACQ_DEL:	
			return LtsConstant.LTS_PRODUCT_TYPE_NEW;

		case CREATE_SRV_TYPE_ACQ_PORT_LATER:	
			return LtsConstant.LTS_PRODUCT_TYPE_NEW_DN_FIRST;	

		case CREATE_SRV_TYPE_ACQ_2DEL:
			return LtsConstant.INVENT_STS_WORKING.equals(pOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus()) ?
				 LtsConstant.LTS_PRODUCT_TYPE_DEL : LtsConstant.LTS_PRODUCT_TYPE_NEW;
		default:
			return null;
		}
	}
	
	private String getLtsToProductType(CreateServiceType pCreateSrvType, BasketDetailDTO pSelectedBasket, AcqOrderCaptureDTO acqOrderCapture) {
		
		switch (pCreateSrvType) {
		
		case CREATE_SRV_TYPE_ACQ_DEL:
			return LtsConstant.LTS_PRODUCT_TYPE_DEL;
		case CREATE_SRV_TYPE_ACQ_EYE:
			if (StringUtils.equals(LtsConstant.DEVICE_TYPE_EYE3A, pSelectedBasket.getType())) {
				return LtsConstant.LTS_PRODUCT_TYPE_EYE_3_A;
			}
			return null;			
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:	
			return LtsConstant.LTS_PRODUCT_TYPE_PORT_LATER;		
		case CREATE_SRV_TYPE_ACQ_2DEL:
			return LtsConstant.LTS_PRODUCT_TYPE_2DEL;
		default:
			return null;
		}
	}
	
	private String getLtsFromSrvType(CreateServiceType pCreateServiceType, AcqOrderCaptureDTO pOrderCapture, BasketDetailDTO pSelectedBasket) {

		switch (pCreateServiceType) {
		case CREATE_SRV_TYPE_ACQ_DEL:
		case CREATE_SRV_TYPE_ACQ_EYE:
				return LtsConstant.LTS_PRODUCT_TYPE_NEW;
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:	
			return pOrderCapture.isEyeOrder()? StringUtils.equalsIgnoreCase(
					"Y", pSelectedBasket.getPeInd())? LtsConstant.LTS_SRV_TYPE_PE : LtsConstant.LTS_SRV_TYPE_SIP 
							: LtsConstant.LTS_SRV_TYPE_DEL;
		case CREATE_SRV_TYPE_ACQ_2DEL:
			return LtsConstant.INVENT_STS_WORKING.equals(pOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus()) ?
					 LtsConstant.LTS_PRODUCT_TYPE_DEL : LtsConstant.LTS_PRODUCT_TYPE_NEW;
		}
		return null;
	}
	
	private String getLtsToSrvType(CreateServiceType pCreateServiceType, AcqOrderCaptureDTO pOrderCapture, BasketDetailDTO pSelectedBasket) {
		switch (pCreateServiceType) {
		case CREATE_SRV_TYPE_ACQ_DEL:
			return LtsConstant.LTS_SRV_TYPE_DEL;

		case CREATE_SRV_TYPE_ACQ_EYE:
			if (pSelectedBasket != null) {
				return StringUtils.equalsIgnoreCase("Y", pSelectedBasket.getPeInd()) ? LtsConstant.LTS_SRV_TYPE_PE : LtsConstant.LTS_SRV_TYPE_SIP;
			}
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:	
			return pOrderCapture.isEyeOrder()? StringUtils.equalsIgnoreCase(
					"Y", pSelectedBasket.getPeInd())? LtsConstant.LTS_SRV_TYPE_PE : LtsConstant.LTS_SRV_TYPE_SIP 
							: LtsConstant.LTS_SRV_TYPE_DEL;
		case CREATE_SRV_TYPE_ACQ_2DEL:
			return LtsConstant.LTS_SRV_TYPE_2DEL;
		default:
			return null;
		}
	}
	
	private RemarkDetailLtsDTO[] getRemarkDetailLts(CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture) {
		List<RemarkDetailLtsDTO> remarkDetailList = new ArrayList<RemarkDetailLtsDTO>();
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_DEL:
		case CREATE_SRV_TYPE_ACQ_EYE:	
			List<RemarkDetailLtsDTO> addonRemarkList = createAddonRemarkDetailLts(acqOrderCapture);
			if (addonRemarkList != null && !addonRemarkList.isEmpty()) {
				remarkDetailList.addAll(addonRemarkList);	
			}
		default:
			break;
		}
		
		switch (createServiceType) {
		
/*		case CREATE_SRV_TYPE_ACQ_DEL:
		case CREATE_SRV_TYPE_ACQ_EYE:
			List<RemarkDetailLtsDTO> wqRemarkList = createWqRemarkDetailLts(acqOrderCapture);
			if (wqRemarkList != null && !wqRemarkList.isEmpty()) {
				remarkDetailList.addAll(wqRemarkList);	
			}
			break;*/
		case CREATE_SRV_TYPE_ACQ_2DEL:
			List<RemarkDetailLtsDTO> secDelRemarkList = createRemarkDetailLts(
					acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelWqRemark(),
					LtsConstant.REMARK_TYPE_2ND_DEL);  
			
			if (secDelRemarkList != null && !secDelRemarkList.isEmpty()) {
				remarkDetailList.addAll(secDelRemarkList);	
			}
		default:
			break;
		}
		
		if (remarkDetailList.isEmpty()) {
			return null;
		}
		return remarkDetailList.toArray(new RemarkDetailLtsDTO[remarkDetailList.size()]);
	}
	
	protected List<RemarkDetailLtsDTO> createAddonRemarkDetailLts(AcqOrderCaptureDTO acqOrderCapture) {
		
		if (acqOrderCapture.getLtsAcqAppointmentForm() == null && StringUtils.isBlank(acqOrderCapture.getSuspendRemark())) {
			return null;
		}
		List<RemarkDetailLtsDTO> remarkDetailLtsList = new ArrayList<RemarkDetailLtsDTO>();
		if (acqOrderCapture.getLtsAcqAppointmentForm() != null){
			remarkDetailLtsList.addAll(this.createRemarkDetailLts(acqOrderCapture.getLtsAcqAppointmentForm().getRemarks(), LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
			remarkDetailLtsList.addAll(this.createRemarkDetailLts(acqOrderCapture.getLtsAcqAppointmentForm().getQcRemarks(), LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
	//		remarkDetailLtsList.addAll(this.createRemarkDetailLts(acqOrderCapture.getLtsAcqAppointmentForm().getPreWiringAppntDtl().getEarliestSRDReason(), LtsConstant.REMARK_TYPE_EARLIEST_SRD_REMARK));
			remarkDetailLtsList.addAll(this.createRemarkDetailLts(acqOrderCapture.getLtsAcqAppointmentForm().getInstallAppntDtl().getEarliestSRDReason(), LtsConstant.REMARK_TYPE_EARLIEST_SRD_REMARK));
		}
		if(StringUtils.isNotBlank(acqOrderCapture.getSuspendRemark())){
			remarkDetailLtsList.addAll(this.createRemarkDetailLts(acqOrderCapture.getSuspendRemark(), LtsConstant.REMARK_TYPE_SUSPEND_REMARK));
		}
		return remarkDetailLtsList;
	}
	
	private List<RemarkDetailLtsDTO> createRemarkDetailLts(String pRemarkContent, String pRemarkType) {
		
		List<RemarkDetailLtsDTO> remarkDetailLtsList = new ArrayList<RemarkDetailLtsDTO>();
		RemarkDetailLtsDTO remarkDetailLts = null;
		
		for (int start = 0; pRemarkContent != null && start < pRemarkContent.length(); start += 250) {
			remarkDetailLts = new RemarkDetailLtsDTO();
			remarkDetailLts.setRmkDtl(pRemarkContent.substring(start, Math.min(pRemarkContent.length(), start + 250))); 
			remarkDetailLts.setRmkSeq(String.valueOf(remarkDetailLtsList.size() + 1));
			remarkDetailLts.setRmkType(pRemarkType);
			remarkDetailLtsList.add(remarkDetailLts);
	    }
		return remarkDetailLtsList;
	}
	
/*	protected List<RemarkDetailLtsDTO> createWqRemarkDetailLts(AcqOrderCaptureDTO acqOrderCapture) {
		if (acqOrderCapture.getLtsWqRemarkForm() == null) {
			return null;
		}
		List<RemarkDetailLtsDTO> remarkDetailLtsList = new ArrayList<RemarkDetailLtsDTO>();
		remarkDetailLtsList.addAll(this.createRemarkDetailLts(acqOrderCapture.getLtsWqRemarkForm().getWqRemark(), LtsConstant.REMARK_TYPE_APPROVL_REMARK));
		return remarkDetailLtsList;
	}*/
	
	private String getActualDocType(CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_DEL:
		case CREATE_SRV_TYPE_ACQ_EYE:
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:	
			return acqOrderCapture.getLtsAcqPersonalInfoForm().getDocumentType();
		case CREATE_SRV_TYPE_ACQ_2DEL:
			return acqOrderCapture.getLtsAcqPersonalInfoForm().getDocumentType();
		default:
			return null;
		}
	}
	
	private String getActualDocId(CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_DEL:
		case CREATE_SRV_TYPE_ACQ_EYE:
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:
			return acqOrderCapture.getLtsAcqPersonalInfoForm().getDocNum();
		case CREATE_SRV_TYPE_ACQ_2DEL:	
			return acqOrderCapture.getLtsAcqPersonalInfoForm().getDocNum();
		default:
			return null;
		}
	}
	
	private ThirdPartyDetailLtsDTO createThirdPartyDetailLts (CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture) {
		
		LtsAcqPersonalInfoFormDTO custIdForm = acqOrderCapture.getLtsAcqPersonalInfoForm();
		
		if (custIdForm == null) {
			return null;
		}
		
		ThirdPartyDetailLtsDTO thirdPartyDetailLts = new ThirdPartyDetailLtsDTO();
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_DEL:
		case CREATE_SRV_TYPE_ACQ_EYE:
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:	
			if (!custIdForm.isThirdParty()) {
				return null;
			}
			thirdPartyDetailLts.setTitle(custIdForm.getThirdPartyTitle());
			thirdPartyDetailLts.setAppntDocId(custIdForm.getThirdPartyDocId());
			thirdPartyDetailLts.setAppntDocType(custIdForm.getThirdPartyDoctype());
			thirdPartyDetailLts.setAppntContactNum(custIdForm.getThirdPartyContactNum());
			thirdPartyDetailLts.setAppntFirstName(custIdForm.getThirdPartyGivenName());
			thirdPartyDetailLts.setAppntLastName(custIdForm.getThirdPartyFamilyName());
			thirdPartyDetailLts.setAppntIdVerifiedInd(custIdForm.isThirdPartyAppIdVerify()?"Y":null);
			thirdPartyDetailLts.setRelationshipCode(custIdForm.getThirdPartyRelationship());
//			thirdPartyDetailLts.setDob(LtsDateFormatHelper.reformatDate(custIdForm.getDateOfBirth(), "dd/MM/yyyy", "yyyyMMdd"));
			if(StringUtils.isNotBlank(custIdForm.getThirdPartyDateOfBirth())){
				thirdPartyDetailLts.setDob(LtsDateFormatHelper.reformatDate(custIdForm.getThirdPartyDateOfBirth(), "dd/MM/yyyy", "yyyyMMdd"));
			}
			break;
		case CREATE_SRV_TYPE_ACQ_2DEL:
			if (!custIdForm.isThirdParty()) {
				return null;
			}
			thirdPartyDetailLts.setTitle(custIdForm.getThirdPartyTitle());
			thirdPartyDetailLts.setAppntDocId(custIdForm.getThirdPartyDocId());
			thirdPartyDetailLts.setAppntDocType(custIdForm.getThirdPartyDoctype());
			thirdPartyDetailLts.setAppntFirstName(custIdForm.getThirdPartyGivenName());
			thirdPartyDetailLts.setAppntLastName(custIdForm.getThirdPartyFamilyName());
			thirdPartyDetailLts.setAppntContactNum(custIdForm.getThirdPartyContactNum());
			thirdPartyDetailLts.setRelationshipCode(custIdForm.getThirdPartyRelationship());
			thirdPartyDetailLts.setAppntIdVerifiedInd(custIdForm.isThirdPartyAppIdVerify() ? "Y" : null);
			if(StringUtils.isNotBlank(custIdForm.getThirdPartyDateOfBirth())){
				thirdPartyDetailLts.setDob(LtsDateFormatHelper.reformatDate(custIdForm.getDateOfBirth(), "dd/MM/yyyy", "yyyyMMdd"));
			}
			break;
		default:
			return null;
		}
		
		return thirdPartyDetailLts;
	}
	
	
	private String getServiceNum(CreateServiceType createService, AcqOrderCaptureDTO acqOrderCapture) {
		
		switch (createService) {
		case CREATE_SRV_TYPE_ACQ_EYE:
		case CREATE_SRV_TYPE_ACQ_DEL:
			if(acqOrderCapture.getLtsAcqNumConfirmationForm() != null
					&& acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection()==Selection.USE_PIPB_DN){
				return acqOrderCapture.getLtsAcqNumConfirmationForm().getPipbDn();
			}
			if(acqOrderCapture.getLtsAcqNumConfirmationForm() != null){
				return acqOrderCapture.getLtsAcqNumConfirmationForm().getNewDn();
			}
			return null;
			
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:
			if(acqOrderCapture.getLtsAcqNumConfirmationForm() != null){
				return acqOrderCapture.getLtsAcqNumConfirmationForm().getPipbDn();
			}
			return null;
			
		case CREATE_SRV_TYPE_ACQ_2DEL:
			if (!acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getCreate2ndDel().booleanValue()) {
				return null;
			}
			return StringUtils.leftPad(acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelDn(), 12, "0");

		default:
			return null;
		}
	}
	
	private void setOrderSupportDoc(SbOrderDTO pSbOrder, AcqOrderCaptureDTO pOrderCapture, BomSalesUserDTO pBomSalesUser) {
		if (pOrderCapture.getLtsAcqSupportDocForm() != null) {
			pSbOrder.setDisMode(pOrderCapture.getLtsAcqSupportDocForm().getDistributionMode());
			pSbOrder.setEsigEmailLang(pOrderCapture.getLtsAcqSupportDocForm().getDistributeLang());	
			pSbOrder.setCollectMethod(pOrderCapture.getLtsAcqSupportDocForm().getCollectMethod());
			pSbOrder.setAllOrdDocAssgns(createAllOrdDocAssgnLts(pOrderCapture, pBomSalesUser));	
			pSbOrder.setSignoffMode(pOrderCapture.getLtsAcqSupportDocForm().getSignoffMode());
			
			if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE, 
					pOrderCapture.getLtsAcqSupportDocForm().getDistributionMode())) {
				if (pOrderCapture.getLtsAcqSupportDocForm().isSendSms()) {
					pSbOrder.setSmsNo(pOrderCapture.getLtsAcqSupportDocForm().getDistributeSms());
				}
				if (pOrderCapture.getLtsAcqSupportDocForm().isSendEmail()) {
					pSbOrder.setEsigEmailAddr(pOrderCapture.getLtsAcqSupportDocForm().getDistributeEmail());
				}
			}
		}
	}
	
	protected AllOrdDocAssgnLtsDTO[] createAllOrdDocAssgnLts (AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {
		
		LtsAcqSupportDocFormDTO supportDocForm = acqOrderCapture.getLtsAcqSupportDocForm();
		
		if (supportDocForm == null) {
			return null;
		}
		
		List<OrderDocDTO> supportDocList = supportDocForm.getSupportDocumentList();
		
		if (supportDocList == null || supportDocList.isEmpty()) {
			return null;
		}
		
		List<AllOrdDocAssgnLtsDTO> allOrdDocAssgnList = new ArrayList<AllOrdDocAssgnLtsDTO>();
		
		AllOrdDocAssgnLtsDTO allOrdDocAssgnLts = null;
		for (OrderDocDTO supportDoc : supportDocList) {
			allOrdDocAssgnLts = new AllOrdDocAssgnLtsDTO();
			allOrdDocAssgnLts.setDocType(supportDoc.getDocType());
			allOrdDocAssgnLts.setWaiveReason(supportDoc.getWaiveReasonCd());
			allOrdDocAssgnLts.setWaivedBy(StringUtils.isNotBlank(supportDoc.getWaiveReasonCd()) ? bomSalesUser.getUsername() : null);
			allOrdDocAssgnList.add(allOrdDocAssgnLts);
		}
		
		return allOrdDocAssgnList.toArray(new AllOrdDocAssgnLtsDTO[allOrdDocAssgnList.size()]); 
	}
	

	/**
	 * @return the premierCustTagLkupCacheService
	 */
	public CodeLkupCacheService getPremierCustTagLkupCacheService() {
		return premierCustTagLkupCacheService;
	}

	/**
	 * @param premierCustTagLkupCacheService the premierCustTagLkupCacheService to set
	 */
	public void setPremierCustTagLkupCacheService(
			CodeLkupCacheService premierCustTagLkupCacheService) {
		this.premierCustTagLkupCacheService = premierCustTagLkupCacheService;
	}
	
	private boolean isServiceEr (CreateServiceType createServiceType, AcqOrderCaptureDTO acqOrderCapture) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_ACQ_2DEL:
			if (acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelEr() != null) {
				return acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getSecondDelEr().booleanValue();
			}
			break;
		default:
			return false;
		}
		return false;
	}

	private CustOptOutInfoLtsDTO createCustOptOutInfo(AcqOrderCaptureDTO orderCapture){
		if(!orderCapture.getLtsAcqPersonalInfoForm().isDisplayPICS()){
			return null;
		}
		
		CustOptOutInfoLtsDTO custOptOutInfo = new CustOptOutInfoLtsDTO();
		String ind = "N";
		String optInd = LtsConstant.DATA_PRIVACY_OPT_IND_OIA_CD;
		if(orderCapture.getLtsAcqPersonalInfoForm().getAgreement()){
			ind = "Y";
			optInd = LtsConstant.DATA_PRIVACY_OPT_IND_OOA_CD;
		}
		custOptOutInfo.setOptInd(optInd);
		custOptOutInfo.setEmail(ind);
		custOptOutInfo.setDirectMailing(ind);
		custOptOutInfo.setInternet(ind);
		custOptOutInfo.setNonsalesSms(ind);
		custOptOutInfo.setOutbound(ind);
		custOptOutInfo.setSms(ind);
		custOptOutInfo.setWebBill(ind);
		custOptOutInfo.setUpdBomStatus(LtsConstant.PDPO_UPDATE_BOM_STATUS_PENDING);	
		
		return custOptOutInfo;
	}
	
	public PipbLtsDTO getPipbInfo(AcqOrderCaptureDTO pOrderCapture) {
		LtsAcqPipbInfoDTO acqPipb = pOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo();
		PipbLtsDTO sbPipb = new PipbLtsDTO();
		sbPipb.setIsPortBack(acqPipb.isPortBack()?"Y":"N");
		sbPipb.setPipbAction(acqPipb.getPipbAction().toString());
		sbPipb.setOperator2n(acqPipb.getPortingFrom());
		sbPipb.setReuseExSocketInd(acqPipb.isReuseExSocket()?"Y":"N");
		if (acqPipb.isReuseExSocket()) {
			sbPipb.setReuseSocketType(acqPipb.getReuseSocketType());
		}
		sbPipb.setWaiveDnChangeInd(acqPipb.isWaiveDnChrg()?"Y":"N");
		sbPipb.setFromDiffCustInd(acqPipb.isPortFromDiffCust()?"Y":"N");
		if (acqPipb.isPortFromDiffCust()) {
			sbPipb.setIdDocType(acqPipb.getDocType());
			sbPipb.setIdDocNum(acqPipb.getDocNum());
			if (LtsConstant.DOC_TYPE_HKBR.equals(acqPipb.getDocType())) {
				sbPipb.setCompanyName(acqPipb.getCompanyName());
			} else {
				sbPipb.setTitle(acqPipb.getTitle());
				sbPipb.setFirstName(acqPipb.getGivenName());
				sbPipb.setLastName(acqPipb.getFamilyName());
			}			
		}
		sbPipb.setFromDiffAddrInd(acqPipb.isPortFromDiffAddr()?"Y":"N");
		sbPipb.setUnitNo(acqPipb.getAddress().getFlat());
		sbPipb.setFloorNo(acqPipb.getAddress().getFloor());
		sbPipb.setBlockNo(acqPipb.getAddress().getBlock());
		sbPipb.setHiLotNo(acqPipb.getAddress().getLotNum());
		sbPipb.setBuildNo(acqPipb.getAddress().getBuildingName());
		sbPipb.setStrNo(acqPipb.getAddress().getStreetNum());
		sbPipb.setStrName(acqPipb.getAddress().getStreetName());
		sbPipb.setStrCatCd(acqPipb.getAddress().getStreetCatgCode());
		sbPipb.setStrCatDesc(acqPipb.getAddress().getStreetCatgDesc());
		sbPipb.setSectCd(acqPipb.getAddress().getSectionCode());
		sbPipb.setSectDesc(acqPipb.getAddress().getSectionDesc());
		sbPipb.setDistNo(acqPipb.getAddress().getDistrictCode());
		sbPipb.setDistDesc(acqPipb.getAddress().getDistrictDesc());
		sbPipb.setAreaCd(acqPipb.getAddress().getAreaCode());
		sbPipb.setAreaDesc(acqPipb.getAddress().getAreaDesc());
		sbPipb.setSerbdyno(acqPipb.getAddress().getServiceBoundary());
		sbPipb.setDuplexInd(acqPipb.isDuplexInd()?"Y":"N");
		if (acqPipb.isDuplexInd()) {
			sbPipb.setDuplexAction(acqPipb.getDuplexAction().toString());
			sbPipb.setDuplexDn(acqPipb.getDuplexDn());
		}
		return sbPipb;
	}
	
	private String getShareRentalRouterInd(AcqOrderCaptureDTO orderCapture) {
		if(LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER.equals(orderCapture.getLtsModemArrangementForm().getRentalRouterInd())){
			return "Y";
		}else if(LtsConstant.ROUTER_OPTION_BRM.equals(orderCapture.getLtsModemArrangementForm().getRentalRouterInd())){
			return "N";
		}else{
			return null;
		}
	}
	
	private ImsL2JobDTO createImsL2Job(String l2Cd, String l2Qty, String ftInd, String actInd) {
		ImsL2JobDTO imsL2Job = new ImsL2JobDTO();
		imsL2Job.setActInd(actInd);
		imsL2Job.setL2Cd(l2Cd);
		imsL2Job.setL2Oty(l2Qty);
		imsL2Job.setFtInd(ftInd);
		return imsL2Job;
	}
	
	private ImsL2JobDTO[] createImsL2job(AcqOrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket) {

		List<ImsL2JobDTO> imsL2JobList = new ArrayList<ImsL2JobDTO>();
		
		try {
		
			// L2 Job for Lose Modem 
			if(orderCapture.getLtsModemArrangementForm().isLostModem()){
				imsL2JobList.add(createImsL2Job(LtsConstant.LOST_MODEM_L2_JOB_CODE, "1", "T", LtsConstant.IO_IND_INSTALL));
			}
			
			// L2 Job for Share Rental Router
			if (StringUtils.equals("Y", getShareRentalRouterInd(orderCapture))) {
				
					CodeLkupDAO rentalRouterL2JobCodeLkupDao = rentalRouterL2JobCacheService.getCodeLkupDAO();
					if (rentalRouterL2JobCodeLkupDao != null 
							&& ArrayUtils.isNotEmpty(rentalRouterL2JobCodeLkupDao.getCodeLkup())) {
						for (LookupItemDTO lookupItem : rentalRouterL2JobCodeLkupDao.getCodeLkup()) {
							imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
						}
					}	
				}
			
			// L2 Job for PON BRM no stock
			if (LtsConstant.TECHNOLOGY_PON.equals(orderCapture.getModemTechnologyAssign().getTechnology())
					&& (StringUtils.isBlank(getShareRentalRouterInd(orderCapture)) 
							|| StringUtils.equals("N", getShareRentalRouterInd(orderCapture)))
					&& !StringUtils.equals("Y", getBrmWifiInd(orderCapture, getSelectedFsaDetail(orderCapture)))) {
				// BRM for standalone TV
				if (LtsConstant.IMS_PRODUCT_TYPE_HD.equals(getImsToProductType(orderCapture))
						|| LtsConstant.IMS_PRODUCT_TYPE_SD.equals(getImsToProductType(orderCapture))) {
					CodeLkupDAO brmViL2JobCodeLkupDao = brmViL2JobCacheService.getCodeLkupDAO();
					if (brmViL2JobCodeLkupDao != null 
							&& ArrayUtils.isNotEmpty(brmViL2JobCodeLkupDao.getCodeLkup())) {
						for (LookupItemDTO lookupItem : brmViL2JobCodeLkupDao.getCodeLkup()) {
							imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
						}
					}
				}
				// BRM for PCD
				else if (StringUtils.contains(getImsToProductType(orderCapture), LtsConstant.IMS_PRODUCT_TYPE_PCD)) {
					CodeLkupDAO brmViL2JobCodeLkupDao = brmViL2JobCacheService.getCodeLkupDAO();
					if (brmViL2JobCodeLkupDao != null 
							&& ArrayUtils.isNotEmpty(brmViL2JobCodeLkupDao.getCodeLkup())) {
						for (LookupItemDTO lookupItem : brmViL2JobCodeLkupDao.getCodeLkup()) {
							imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
						}
					}
				}
				else {
					CodeLkupDAO brmL2JobCodeLkupDao = brmL2JobCacheService.getCodeLkupDAO();
					if (brmL2JobCodeLkupDao != null 
							&& ArrayUtils.isNotEmpty(brmL2JobCodeLkupDao.getCodeLkup())) {
						for (LookupItemDTO lookupItem : brmL2JobCodeLkupDao.getCodeLkup()) {
							imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
						}
					}
				}
			}
			
			// If OS Type = IOS
			if (selectedBasket != null) {
				if(LtsConstant.OS_TYPE_IOS.equals(selectedBasket.getOsType())) {
					CodeLkupDAO osTypeIosL2JobCodeLkupDao = osTypeIosL2JobCacheService.getCodeLkupDAO();
					if (osTypeIosL2JobCodeLkupDao != null 
							&& ArrayUtils.isNotEmpty(osTypeIosL2JobCodeLkupDao.getCodeLkup())) {
						for (LookupItemDTO lookupItem : osTypeIosL2JobCodeLkupDao.getCodeLkup()) {
							imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
						}
					}	
				}
			}
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(ExceptionUtils.getFullStackTrace(e));
		}
		
		
		if (imsL2JobList.size() == 0) {
			return null;
		}
		
		return imsL2JobList.toArray(new ImsL2JobDTO[imsL2JobList.size()]);
	}
	
}
