package com.bomwebportal.lts.web.acq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.CsPortalIdInqArqDTO;
import com.bomwebportal.lts.dto.CsPortalTxnDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAccountSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO.BillMediaDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillingAddressFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSalesInfoFormDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.service.CsPortalService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsPaymentService;
import com.bomwebportal.lts.service.acq.LtsAcqAccountDetailService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.LtsConstant.LtsOrderFlag;
import com.bomwebportal.lts.util.acq.LtsAcqSbOrderHelper;
import com.bomwebportal.lts.wsClientLts.BomCreateAccountWsClient;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.pccw.custProfileGateway.acctInfo.AccountDTO;


public class LtsAcqBillMediaBillLangController extends SimpleFormController{

    protected final Log log = LogFactory.getLog(getClass());
	private CsPortalService csPortalService;
    private LtsPaymentService ltsPaymentService;
	private LtsOfferService ltsOfferService;
	private LtsCommonService ltsCommonService;
    
	private final String viewName = "lts/acq/ltsacqbillmediabilllang";
	private final String nextView = "ltsacqpaymentmethod.html";
	private final String commandName = "ltsbillmediabilllang";
	
	public final String THE_CLUB_LABEL = "The Club";
	public final String MY_HKT_LABEL = "My HKT";
	public final String CREATE_NEW_ACCOUNT_LABEL = "New Account";
	
	private String wsUrl;
	private String user;
	private String password;
	private BomCreateAccountWsClient bomWsCreateAcctClient;
	private CustomerProfileLtsService customerProfileLtsService;
	private LtsAcqAccountDetailService ltsAcqAccountDetailService;
	private CodeLkupCacheService theClubPremiumCacheService;
	private CodeLkupCacheService optOutReasonLkupCacheService;
	private CodeLkupCacheService ltsIguardCarecashLkupCacheService;
	
	private boolean isDocValid;
	private boolean isCsReplyError;
	private boolean isCsSystemBusy;
	private boolean isPhantomAcc;
	private boolean isEmailExist;
	private boolean isMobNoExist;
	private boolean isDummyEmailMob;
    private String csAcctInd;
    private String DOC_NUM_PATTERN = "(?=(.*\\d){6})^.*";
    private boolean is6Digits;
    
    private boolean showIguard;
    private String carecashExistOpt;
    
    private CodeLkupCacheService waiveReasonPropertiesLkupCacheService;
    
    private String oIdStatus;
    private String oCareVisit;
    
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAcqBillMediaBillLangController(){
		this.setFormView(viewName);	
		this.setCommandName(commandName);
		this.setCommandClass(LtsAcqBillMediaBillLangFormDTO.class);
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);		
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqBillMediaBillLangFormDTO form = orderCaptureDTO.getLtsAcqBillMediaBillLangForm();
		Locale locale = RequestContextUtils.getLocale(request);
		LtsAcqAccountSelectionFormDTO ltsAcqAccountSelectionFormDTO = orderCaptureDTO.getLtsAcqAccountSelectionForm();
		String cspDocType = orderCaptureDTO.getCustomerDetailProfileLtsDTO().getDocType();
		String cspDocNum = orderCaptureDTO.getCustomerDetailProfileLtsDTO().getDocNum(); 
		String staffId = orderCaptureDTO.getLtsAcqSalesInfoForm().getStaffId();
		String custNameForEmail = StringUtils.deleteWhitespace(orderCaptureDTO.getLtsAcqPersonalInfoForm().getFamilyName().replaceAll(",", ""))+StringUtils.deleteWhitespace(orderCaptureDTO.getLtsAcqPersonalInfoForm().getGivenName().replaceAll(",", ""));	
		if(custNameForEmail.length()>30){
			custNameForEmail = custNameForEmail.substring(0,30);
		}
		String sysGenEmail = custNameForEmail+LtsBackendConstant.CSP_DUMMY_EMAIL_DOMAIN;
		CsPortalIdInqArqDTO chkLi = checkCsIdForTheClubAndHkt(cspDocType, cspDocNum, 
				sysGenEmail, 
				sysGenEmail, staffId);
		if (chkLi.isLiInUse()) {
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMdd");
		    Date now = new Date();
		    String strDate = sdfDate.format(now);
			sysGenEmail = custNameForEmail+strDate+LtsBackendConstant.CSP_DUMMY_EMAIL_DOMAIN;				
		}
		
		showIguard = (!StringUtils.isNotBlank(chkLi.getoBiptStatus()) || StringUtils.isNotBlank(chkLi.getoBiptStatus()) && chkLi.getoBiptStatus().trim().equalsIgnoreCase("N"));
		carecashExistOpt = chkLi.getoBiptStatus();
		
		is6Digits = Pattern.compile(DOC_NUM_PATTERN).matcher(cspDocNum).matches();
		isEmailExist = StringUtils.isNotBlank(orderCaptureDTO.getLtsAcqPersonalInfoForm().getContactEmail());
		isMobNoExist = StringUtils.isNotBlank(orderCaptureDTO.getLtsAcqPersonalInfoForm().getMobileNo());

		oIdStatus = chkLi.getoIdStatus();
		oCareVisit = chkLi.getoCareVisit();
		
		boolean isDelFree = false;
		if(orderCaptureDTO.getLtsAcqBasketSelectionForm()!=null && orderCaptureDTO.getLtsAcqBasketSelectionForm().isDelFreeBundle())
		{
			isDelFree = true;
		}
		
		CsPortalIdInqArqDTO	csPortalIdInqArqDTO = checkCsIdForTheClubAndHkt(cspDocType, cspDocNum,	"", "", staffId);
		
		if (form == null || (form.getBillMediaDtlList() != null	&& form.getBillMediaDtlList().size() > 0 
						&& !StringUtils.equals(orderCaptureDTO.getCustomerDetailProfileLtsDTO().getCustNum(),
								form.getBillMediaDtlList().get(0).getCustNum()))) {

			form = new LtsAcqBillMediaBillLangFormDTO();
			
			List<BillMediaDtl> billMediaList = new ArrayList<BillMediaDtl>();
			
			if(orderCaptureDTO.getLtsPremiumSelectionForm()!=null && orderCaptureDTO.getLtsPremiumSelectionForm().getPremiumSetList()!=null){
				form.setRedemMediaRequired(true);
			}else{
				form.setRedemMediaRequired(false);
			}
						
			for (AccountDetailProfileLtsDTO acctProfile : orderCaptureDTO.getAccountDetailProfileLtsDTO()) {

				List<ItemDetailDTO> tempItemList;
				BillMediaDtl billMedia = form.new BillMediaDtl();
				billMedia.setAcctNum(ltsAcqAccountSelectionFormDTO.isNewAccount() ? messageSource.getMessage("lts.acq.billingAddress.newAccount", new Object[] {}, this.locale) : acctProfile.getAcctNum());
				billMedia.setBillLang(acctProfile.getBillLang());
				billMedia.setContactEmail(orderCaptureDTO.getLtsAcqPersonalInfoForm().getContactEmail());
				billMedia.setContactMobileNo(orderCaptureDTO.getLtsAcqPersonalInfoForm().getMobileNo());
				billMedia.setCustNum(orderCaptureDTO.getCustomerDetailProfileLtsDTO().getCustNum());
				billMedia.setSysGenEmail(sysGenEmail);
				billMedia.setDummyEmailInd("N");
				billMedia.setDummyMobileInd("N");
				billMedia.setOptInOutInd("OIA");
				billMedia.setPaperBillItem(null);
				tempItemList = ltsOfferService.getItemList(
						LtsConstant.ITEM_TYPE_PAPER_BILL, locale.toString(),
						true, orderCaptureDTO.getBasketChannelId(), null);
				if (tempItemList != null && tempItemList.size() > 0) {
					billMedia.setPaperBillItem(tempItemList.get(0));
				}

				billMedia.setEmailBillItem(null);
				tempItemList = ltsOfferService.getItemList(
						LtsConstant.ITEM_TYPE_EMAIL_BILL, locale.toString(),
						true, true, orderCaptureDTO.getBasketChannelId(), null);
				if (tempItemList != null && tempItemList.size() > 0) {
					billMedia.setEmailBillItem(tempItemList.get(0));
				}

				billMedia.setProfileBillMedia(acctProfile.getBillMedia());
				if (StringUtils.equals(LtsConstant.LTS_BILL_MEDIA_PAPER_BILL, acctProfile.getBillMedia())) {
					billMedia.setSelectedBillItem(billMedia.getPaperBillItem().getItemId());
				} else if (StringUtils.equals(LtsConstant.LTS_BILL_MEDIA_PPS_BILL, acctProfile.getBillMedia())) {
					billMedia.setSelectedBillItem(billMedia.getEmailBillItem().getItemId());
					billMedia.setBillMediaEmail(acctProfile.getEmailAddr());
				}
				
				if(form.isRedemMediaRequired()){
					billMedia.setRedemMedia(LtsConstant.REDEMPTION_MEDIA_SMS);
				}
				
				/* if exist cust profile paper bill ind is not blank/null
				 * set bill charging correspondingly*/
				String paperBillInd = orderCaptureDTO.getCustomerDetailProfileLtsDTO().getPaperBill();
				if(StringUtils.isNotBlank(paperBillInd) && !"N".equals(paperBillInd)){
					billMedia.setCustExistingPaperBillInd(paperBillInd);
					if("G".equals(paperBillInd)){
						billMedia.setSelectedBillCharging("Y");
					}else if("W".equals(paperBillInd)){
						billMedia.setSelectedBillCharging("W");
					}
				}
				
//				if(showIguard && billMediaList.size()==0&&!oIdStatus.equalsIgnoreCase("RC_NO_CUSTOMER"))
//				{
//					billMedia.setCarecashRegisterOption("R");
//					billMedia.setCarecashOptInOutInd("I");
//				}
				
				billMedia.setContactFixedLineNo(orderCaptureDTO.getLtsAcqPersonalInfoForm().getFixedLineNo());
				
				billMediaList.add(billMedia);
			}

			form.setBillMediaDtlList(billMediaList);

		} else {
			
			for (BillMediaDtl billMedia : form.getBillMediaDtlList()) {
				if (billMedia.getEmailBillItem().isSelected()) {
					billMedia.setSelectedBillItem(billMedia.getEmailBillItem().getItemId());
				} else if (billMedia.getPaperBillItem().isSelected()) {
					billMedia.setSelectedBillItem(billMedia.getPaperBillItem().getItemId());
				} else {
					billMedia.setSelectedBillItem(null);
				}
				if((StringUtils.isNotBlank(billMedia.getDummyEmailInd()) && StringUtils.equals("Y", billMedia.getDummyEmailInd()))||
						(StringUtils.isNotBlank(billMedia.getDummyMobileInd()) && StringUtils.equals("Y", billMedia.getDummyMobileInd()))){
					isDummyEmailMob=true;
				}
				isDocValid = billMedia.isDocValid();
				csAcctInd = billMedia.getCsAcctInd();
			}
		}
		
		String tempDocumentType = "";
		if(orderCaptureDTO!=null)
			if(orderCaptureDTO.getLtsAcqPersonalInfoForm()!=null)
				tempDocumentType = orderCaptureDTO.getLtsAcqPersonalInfoForm().getDocumentType();
				
		if(form.getBillMediaDtlList() != null && form.getBillMediaDtlList().size() > 0 && !tempDocumentType.equalsIgnoreCase("BS")){
			List<ItemDetailDTO> tempItemList;
			for (BillMediaDtl billMedia : form.getBillMediaDtlList()) {
				/* obtain CSP item */
				ItemDetailDTO cspItem = null;
				//if (isRegHktAndClub() && !isCsReplyError) {
				if (isRegHktAndClub()) {
					tempItemList = ltsPaymentService.getItemDetailByType(
							LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL,
							LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale.toString());
					if (tempItemList != null && tempItemList.size() > 0) {
						cspItem = ltsOfferService.getItemAttbByItemAttbAssign((tempItemList.get(0)));
						if(is6Digits && !isDelFree){
							cspItem.setSelected(true);
						}else{
							cspItem.setSelected(false);
						}
					}
					billMedia.setExistingCspEmail(orderCaptureDTO.getLtsAcqPersonalInfoForm().getContactEmail());// if no existing login id
				} else if (isRegHktOnly() && !isCsReplyError) {
					tempItemList = ltsPaymentService.getItemDetailByType(
							LtsConstant.ITEM_TYPE_MYHKT_BILL,
							LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale.toString());
					if (tempItemList != null && tempItemList.size() > 0) {
						cspItem = ltsOfferService.getItemAttbByItemAttbAssign((tempItemList.get(0)));
						if(is6Digits && !isDelFree){
							cspItem.setSelected(true);
						}else{
							cspItem.setSelected(false);
						}
					}
					billMedia.setClubEmail(csPortalIdInqArqDTO.getoCorrClubLi());
					billMedia.setExistingCspEmail(csPortalIdInqArqDTO.getoCorrClubLi());
					isEmailExist=StringUtils.isNotBlank(csPortalIdInqArqDTO.getoCorrClubLi());
				} else if (isRegClubOnly() && !isCsReplyError) {
					tempItemList = ltsPaymentService.getItemDetailByType(
							LtsConstant.ITEM_TYPE_THE_CLUB_BILL,
							LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale.toString());
					if (tempItemList != null && tempItemList.size() > 0) {
						cspItem = ltsOfferService.getItemAttbByItemAttbAssign((tempItemList.get(0)));
						if(is6Digits && !isDelFree){
							cspItem.setSelected(true);
						}else{
							cspItem.setSelected(false);
						}
					}
					billMedia.setCspEmail(csPortalIdInqArqDTO.getoCorrMyHktLi());
					billMedia.setExistingCspEmail(csPortalIdInqArqDTO.getoCorrMyHktLi());
					isEmailExist=StringUtils.isNotBlank(csPortalIdInqArqDTO.getoCorrMyHktLi());
				} else if (isExistHktAndClub() && !isCsReplyError) {
					tempItemList = ltsPaymentService.getItemDetailByType(
							LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL,
							LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale.toString());
					if (tempItemList != null && tempItemList.size() > 0) {
						cspItem = ltsOfferService.getItemAttbByItemAttbAssign((tempItemList.get(0)));
						cspItem.setSelected(true);
					}
					billMedia.setCspEmail(csPortalIdInqArqDTO.getoCorrMyHktLi());
					billMedia.setClubEmail(csPortalIdInqArqDTO.getoCorrClubLi());
					billMedia.setExistingCspEmail(csPortalIdInqArqDTO.getoCorrMyHktLi());
					isEmailExist=StringUtils.isNotBlank(csPortalIdInqArqDTO.getoCorrMyHktLi());
				}

				/* replace with new one if not same as existing one */
				if (billMedia.getCsPortalItem() == null
						|| (cspItem != null && !StringUtils.equals(cspItem.getItemId(), 
								billMedia.getCsPortalItem().getItemId()))) {
					billMedia.setCsPortalItem(cspItem);
				}

				if (!isDocValid && billMedia.getCsPortalItem() != null) {
					billMedia.getCsPortalItem().setSelected(false);
				}else if(billMedia.getCsPortalItem() != null){
					if (isSelectedTheClubPremium(orderCaptureDTO) && (isRegClubOnly() || isRegHktAndClub())) {
						billMedia.getCsPortalItem().setSelected(true);
						billMedia.getCsPortalItem().setMdoInd(LtsConstant.ITEM_MDO_MANDATORY);
					}else if(cspItem != null){
						billMedia.getCsPortalItem().setMdoInd(cspItem.getMdoInd());
					}
				}

				billMedia.setCsPortalExist(isExistHktAndClub());
				billMedia.setCsAcctInd(csAcctInd);
				billMedia.setDocValid(isDocValid);
							
					
			}
		}
		
		
		if(isDummyEmailMob){
			form.setDummyEmailMobInd("Y");
		}else{
			form.setDummyEmailMobInd("N");
		}
		
		// showIguard only if HKID
		if(!tempDocumentType.equalsIgnoreCase("HKID"))
		{
			showIguard = false;
		}
		//
		
		// showIguard only if age 18-80 or null
		String dateOfBirth = orderCaptureDTO.getLtsAcqPersonalInfoForm().getDateOfBirth();
		try{
			if (!StringUtils.isEmpty(dateOfBirth)) {
				Period diff = new Period(LocalDate.parse(dateOfBirth, DateTimeFormat.forPattern("dd/MM/yyyy")), LocalDate.parse(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), DateTimeFormat.forPattern("dd/MM/yyyy")));
				if(diff.getYears() < 18 || diff.getYears() > 80)
				{
					showIguard = false;
				}
			}		
		}
		catch (Exception E)
		{
			showIguard = false;
		}
		//
		
		// showIguard only if oCareVisit is null or more than 7 days
		try{
			if (!StringUtils.isEmpty(oCareVisit)) {
				/*
				Period visitDiff = new Period(LocalDate.parse(oCareVisit, DateTimeFormat.forPattern("yyyyMMddHHmmss")), LocalDate.parse(LtsDateFormatHelper.getSysDate("yyyyMMddHHmmss"), DateTimeFormat.forPattern("yyyyMMddHHmmss")));
				if(visitDiff.getDays() < 7)
				{
					showIguard = false;
				}
				*/
				SimpleDateFormat visitformat = new SimpleDateFormat("yyyyMMddHHmmss");

				Date datevisit = null;
				Date today = null;

				datevisit = visitformat.parse(oCareVisit);
				today = visitformat.parse(LtsDateFormatHelper.getSysDate("yyyyMMddHHmmss"));

				//in milliseconds
				long diff = today.getTime() - datevisit.getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				if(diffDays < 7)
				{
					showIguard = false;
				}

			}
		}
		catch (Exception E)
		{
			showIguard = false;
		}
		//
		
		// showIguard only if effective = Y
		if(!"Y".equalsIgnoreCase((String)ltsIguardCarecashLkupCacheService.get(LtsConstant.CODE_LKUP_IGUARD_CARECASH_EFFECTIVE)))
		{
			showIguard = false;
		}
		//

		//only allow redemption by paper for age > 65
		if("N".equals(ltsCommonService.getLtsOrderFlag(LtsOrderFlag.CHECK_AGE_REDEMPTION_PAPER))){
			form.setAllowRedemPaper(true);
		}else{
			if (StringUtils.isNotEmpty(dateOfBirth)) {
				if(LtsSbOrderHelper.isAgeOver(dateOfBirth)){
					form.setAllowRedemPaper(true);
				}else{
					form.setAllowRedemPaper(false);
				}
			}
		}
				
		request.setAttribute("isRegHktAndClub", isRegHktAndClub());
		request.setAttribute("isRegHktOnly", isRegHktOnly());
		request.setAttribute("isRegClubOnly", isRegClubOnly());
		request.setAttribute("isDocValidCsp", isDocValid);
		request.setAttribute("is6Digits", is6Digits);
		request.setAttribute("isCsReplyError", isCsReplyError);
		request.setAttribute("isBrCust", LtsConstant.DOC_TYPE_HKBR.equals(cspDocType)?true:false);
		request.setAttribute("isPhantomAcc", isPhantomAcc);
		request.setAttribute("isEmailExist", isEmailExist);
		request.setAttribute("isMobNoExist", isMobNoExist);
		request.setAttribute("showIguard", showIguard);
		request.setAttribute("carecashExistOpt", carecashExistOpt);
		request.setAttribute("oIdStatus", oIdStatus);
		request.setAttribute("isDelFree", isDelFree);
		
		return form;
	}
	
	private ValidationResultDTO[] validateSubmit(HttpServletRequest request,
			AcqOrderCaptureDTO orderCapture, LtsAcqBillMediaBillLangFormDTO form) {

		List<ValidationResultDTO> validationResultList = new ArrayList<ValidationResultDTO>();
		validationResultList.add(validatePaperBillWaiveReason(orderCapture, form));
		validationResultList.add(validateMyHktandCarecash(orderCapture, form));
		return validationResultList
				.toArray(new ValidationResultDTO[validationResultList.size()]);
	}
	
	private ValidationResultDTO validateMyHktandCarecash(AcqOrderCaptureDTO orderCapture, LtsAcqBillMediaBillLangFormDTO form)
	{
		if(form.getoIdStatus().equalsIgnoreCase("RC_NO_CUSTOMER"))
		{
			if (form.getBillMediaDtlList() != null && form.getBillMediaDtlList().size() > 0)
			{
				if(form.getBillMediaDtlList().get(0).getCarecashRegisterOption()!=null)
				{
					if ( !form.getBillMediaDtlList().get(0).isCsPortalExist()
							&& form.getBillMediaDtlList().get(0).getCsPortalItem() != null
							&& form.getBillMediaDtlList().get(0).getCsPortalItem().isSelected()) {
						if(StringUtils.equals("Y", form.getDummyEmailMobInd())){
							return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.acq.billingSelection.MyHKTRegNeediGUARDCARECashReg", new Object[] {}, this.locale)), null); 
						}else if (form.getBillMediaDtlList().get(0).getCspEmail()==null || form.getBillMediaDtlList().get(0).getCspMobile()==null ||
								form.getBillMediaDtlList().get(0).getCspEmail().equalsIgnoreCase("") || form.getBillMediaDtlList().get(0).getCspMobile().equalsIgnoreCase("") )
						{
							return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.acq.billingSelection.MyHKTRegNeediGUARDCARECashReg", new Object[] {}, this.locale)), null); 
						}
						
					}
					else if ( !form.getBillMediaDtlList().get(0).isCsPortalExist()
							&& form.getBillMediaDtlList().get(0).getCsPortalItem() != null
							&& !form.getBillMediaDtlList().get(0).getCsPortalItem().isSelected())
					{
						return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.acq.billingSelection.MyHKTRegNotSelected", new Object[] {}, this.locale)), null); 
					}
				}
			}
			
		}		
		
		return new ValidationResultDTO(Status.VALID, null, null); 
	}
	
	private ValidationResultDTO validatePaperBillWaiveReason(AcqOrderCaptureDTO orderCapture, LtsAcqBillMediaBillLangFormDTO form) {
		if (! ( ltsOfferService.isSelectedPaperBillItem(form)
					&& LtsConstant.PAPER_BILL_CHARGE_WAIVE.equals(form.getPrimaryBillMediaDtl().getSelectedBillCharging())
					&& LtsConstant.PAPER_BILL_WAIVE_REASON_AGE_OVER_65.equals(form.getPrimaryBillMediaDtl().getSelectedWaiveReason()))) {
			return new ValidationResultDTO(Status.VALID, null, null); 
		}
		
		/* if paper bill ind exist (either waive/charge), skip validation */
		if (!StringUtils.isBlank(form.getPrimaryBillMediaDtl().getCustExistingPaperBillInd())){
			return new ValidationResultDTO(Status.VALID, null, null); 
		}
		
		String dateOfBirth = orderCapture.getLtsAcqPersonalInfoForm().getDateOfBirth();
		if (StringUtils.isEmpty(dateOfBirth)) {
			return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.acq.billingSelection.dateOfBirthEmpty", new Object[] {}, this.locale)), null); 
		}
		
		final String PAPER_BILL_EFFECTIVE_DATE = "01/09/2016";
		String compareDate = 
			LtsDateFormatHelper.isDateOverdue(PAPER_BILL_EFFECTIVE_DATE, "dd/MM/yyyy") ? LtsDateFormatHelper.getSysDate("dd/MM/yyyy") 
					: PAPER_BILL_EFFECTIVE_DATE; 
		
		if (!LtsSbOrderHelper.isAgeOver(dateOfBirth, compareDate)) {
			return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.acq.billingSelection.ageNotOver65", new Object[] {}, this.locale)), null); 
		}
		return new ValidationResultDTO(Status.VALID, null, null);
	}
	
	
	private void setBillMediaDetail(AcqOrderCaptureDTO orderCaptureDTO, LtsAcqBillMediaBillLangFormDTO form, String locale) {
	
		for(BillMediaDtl billMedia: form.getBillMediaDtlList()){
			if(billMedia.getEmailBillItem()!= null 
					&& StringUtils.equals(billMedia.getEmailBillItem().getItemId(), billMedia.getSelectedBillItem())){
				billMedia.getEmailBillItem().setSelected(true);
			}else{
				billMedia.getEmailBillItem().setSelected(false);
			}	
			
			if(billMedia.getPaperBillItem()!= null 
					&& StringUtils.equals(billMedia.getPaperBillItem().getItemId(), billMedia.getSelectedBillItem())){
				billMedia.getPaperBillItem().setSelected(true);
			}else{
				billMedia.getPaperBillItem().setSelected(false);
			}
			
			if(billMedia.getPaperBillItem().isSelected()){
				/*Paper bill waive handling*/
				String docType = orderCaptureDTO.getLtsAcqPersonalInfoForm().getDocumentType();
				if(StringUtils.equals(docType, LtsBackendConstant.DOC_TYPE_HKBR)){
					List<ItemDetailDTO> paperBRItemList  = ltsOfferService.getItemList(
							LtsConstant.ITEM_TYPE_PAPER_BILL_BR, locale.toString(), billMedia.getPaperBillItem().isSelected(),
							orderCaptureDTO.getBasketChannelId(), null);
							billMedia.setPaperBillItem(paperBRItemList != null? paperBRItemList.get(0) : null);
				}else{
					if(StringUtils.equals(billMedia.getSelectedBillCharging(), "Y")){
						List<ItemDetailDTO> paperGenerateItemList  = ltsOfferService.getItemList(
								LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE, locale.toString(), billMedia.getPaperBillItem().isSelected(),
								orderCaptureDTO.getBasketChannelId(), null);
						billMedia.setPaperBillItem(paperGenerateItemList != null? paperGenerateItemList.get(0) : null);
					}
					if(StringUtils.isBlank(billMedia.getSelectedBillCharging())
							|| StringUtils.equals(billMedia.getSelectedBillCharging(), "W")){
						List<ItemDetailDTO> paperWaiveItemList  = ltsOfferService.getItemList(
								LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE, locale.toString(), billMedia.getPaperBillItem().isSelected(),
								orderCaptureDTO.getBasketChannelId(), null);
						billMedia.setPaperBillItem(paperWaiveItemList != null ? paperWaiveItemList.get(0) : null);
					}
				}
			}

			for(AccountDetailProfileLtsDTO accProfileDtl : orderCaptureDTO.getAccountDetailProfileLtsDTO()){
				if(StringUtils.equals(billMedia.getAcctNum(), accProfileDtl.getAcctNum())
						&& StringUtils.isNotBlank(accProfileDtl.getAcctChrgType())
						&& accProfileDtl.getAcctChrgType().contains("R")){
					billMedia.setPrimaryAcct(true);
				}
				if(StringUtils.equals(billMedia.getAcctNum(), accProfileDtl.getAcctNum())){
					accProfileDtl.setBillLang(billMedia.getBillLang());
				}
			}
			
		}

	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception{
	 	AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqBillMediaBillLangFormDTO form = (LtsAcqBillMediaBillLangFormDTO)command;
		String locale = RequestContextUtils.getLocale(request).toString();
		
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		ValidationResultDTO[] validationResults = validateSubmit(request,
				orderCaptureDTO, form);

		for (ValidationResultDTO validationResult : validationResults) {
			if (Status.INVAILD == validationResult.getStatus()) {
				ModelAndView mav = new ModelAndView(viewName, referenceData(request));
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", validationResult.getMessageList());
				return mav;
			}
		}

		/*if set billmedia detail before validation will mess-up the checking*/
		setBillMediaDetail(orderCaptureDTO, form, locale);
		
		LtsAcqAccountSelectionFormDTO ltsAcqAccountSelectionFormDTO = orderCaptureDTO.getLtsAcqAccountSelectionForm();
				
		// CS PORTAL
		if (form.getBillMediaDtlList() != null && form.getBillMediaDtlList().size() > 0
				&& !form.getBillMediaDtlList().get(0).isCsPortalExist()
				&& form.getBillMediaDtlList().get(0).getCsPortalItem() != null
				&& form.getBillMediaDtlList().get(0).getCsPortalItem().isSelected()) {			
									
			String cspDocType = orderCaptureDTO.getCustomerDetailProfileLtsDTO().getDocType();
			String cspDocNum = orderCaptureDTO.getCustomerDetailProfileLtsDTO().getDocNum();
			String staffId = orderCaptureDTO.getLtsAcqSalesInfoForm().getStaffId();
			boolean isLoginIdUse = false;
						
			String cspAcct = MY_HKT_LABEL + " / " + THE_CLUB_LABEL;
			isDocValid = form.getBillMediaDtlList().get(0).isDocValid();
			csAcctInd = form.getBillMediaDtlList().get(0).getCsAcctInd();
			
			if (isRegHktAndClub()) {
				form.getBillMediaDtlList().get(0).setClubEmail(form.getBillMediaDtlList().get(0).getCspEmail());
				form.getBillMediaDtlList().get(0).setClubMobile(form.getBillMediaDtlList().get(0).getCspMobile());
			}
			
			if (form.getBillMediaDtlList().size() > 1) {
				for (int i=1; i<form.getBillMediaDtlList().size(); i++) {
					form.getBillMediaDtlList().get(i).setCspEmail(form.getBillMediaDtlList().get(0).getCspEmail());
					form.getBillMediaDtlList().get(i).setCspMobile(form.getBillMediaDtlList().get(0).getCspMobile());
					form.getBillMediaDtlList().get(i).setClubEmail(form.getBillMediaDtlList().get(0).getClubEmail());
					form.getBillMediaDtlList().get(i).setClubMobile(form.getBillMediaDtlList().get(0).getClubMobile());
				}
		    }
			
			List<String> errorMsgList = new ArrayList<String>();
			
			/*if (isSelectedTheClubPremium(orderCaptureDTO) && (!isDocValid || isCsReplyError || isCsSystemBusy)) {
				errorMsgList.add("Cannot register The Club. Please unselect The Cluc Premium / cancel the order.");
			}*/
			
			for(BillMediaDtl billMedia: form.getBillMediaDtlList()) {
				errorMsgList = new ArrayList<String>();
				if (billMedia.getCsPortalItem().getItemAttbs() != null
						&& billMedia.getCsPortalItem().getItemAttbs().length > 0) {
					for (ItemAttbDTO itemAttb : billMedia.getCsPortalItem().getItemAttbs()) {
						if (LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL.equals(itemAttb.getInputFormat())) {
							if (LtsConstant.ITEM_ATTB_INFO_KEY_CLUB.equals(itemAttb.getAttbInfoKey())) {
								itemAttb.setAttbValue(billMedia.getClubEmail());
							} else {
								itemAttb.setAttbValue(billMedia.getCspEmail());
							}
						} else if (LtsConstant.CHANNEL_ATTB_FORMAT_MOBILE_NUM.equals(itemAttb.getInputFormat())) {
							if (StringUtils.isNotBlank(billMedia.getCspMobile())) {
								itemAttb.setAttbValue(billMedia.getCspMobile());
							} else {
								itemAttb.setAttbValue(billMedia.getClubMobile());
							}
						}
						ltsCommonService.validateAttbValue(itemAttb, errorMsgList);
					}				
				}
				
			}
			
			if (isRegHktAndClub()) {
				CsPortalIdInqArqDTO csPortalIdInqArqDTO = checkCsIdForTheClubAndHkt(cspDocType, cspDocNum, 
						form.getPrimaryBillMediaDtl().getCspEmail(), 
						form.getPrimaryBillMediaDtl().getCspEmail(), staffId);
				if (csPortalIdInqArqDTO.isLiInUse()) {
					if(!StringUtils.equals("Y", form.getDummyEmailMobInd())){
						isLoginIdUse = true;
					}else{
						csPortalService.createCsPortalTxn(setCspVaildateRsp(csPortalIdInqArqDTO), staffId);
					}
				}
				cspAcct = MY_HKT_LABEL + " / " + THE_CLUB_LABEL;
			}
			
			if (isRegHktOnly()) {
				CsPortalIdInqArqDTO csPortalIdInqArqDTO = checkCsIdForTheClubAndHkt(cspDocType, cspDocNum, 
						form.getPrimaryBillMediaDtl().getCspEmail(), "", staffId);
				if (csPortalIdInqArqDTO.isHktLiInUse()) {
					if(!StringUtils.equals("Y", form.getDummyEmailMobInd())){
						isLoginIdUse = true;
					}else{
						csPortalService.createCsPortalTxn(setCspVaildateRsp(csPortalIdInqArqDTO), staffId);
					}					
				}
				cspAcct = MY_HKT_LABEL;
			}
			
			if (isRegClubOnly()) {
				CsPortalIdInqArqDTO csPortalIdInqArqDTO = checkCsIdForTheClubAndHkt(cspDocType, cspDocNum, 
						"", form.getPrimaryBillMediaDtl().getClubEmail(), staffId);
				if (csPortalIdInqArqDTO.isClubLiInUse()) {
					if(!StringUtils.equals("Y", form.getDummyEmailMobInd())){
						isLoginIdUse = true;
					}else{
						csPortalService.createCsPortalTxn(setCspVaildateRsp(csPortalIdInqArqDTO), staffId);
					}				
				}
				cspAcct = THE_CLUB_LABEL;
			}
			
			if (isLoginIdUse) {
				//errorMsgList.add("The email has already been used to register \"" + cspAcct + "\" account. Please enter a new one.");
				if(cspAcct.equals(THE_CLUB_LABEL))
				{
					errorMsgList.add(messageSource.getMessage("lts.acq.billingSelection.EmailUsedRegisterTheClub", new Object[] {}, this.locale));
				}
				else if(cspAcct.equals(MY_HKT_LABEL))
				{
					errorMsgList.add(messageSource.getMessage("lts.acq.billingSelection.EmailUsedRegisterMyHKT", new Object[] {}, this.locale));
				}
				else
				{
					errorMsgList.add(messageSource.getMessage("lts.acq.billingSelection.EmailUsedRegisterMyHKTorTheClub", new Object[] {}, this.locale));
				}
			}/* else if (isCsSystemBusy) {
				errorMsgList.add("System busy from CS Portal, can not register \"" + cspAcct + "\" account. Please try again later.");
			} else if (isCsReplyError) {
				errorMsgList.add("Error occured from CS Portal, can not register \"" + cspAcct + "\" account. Please try again later.");
			}*/
			
			if (!errorMsgList.isEmpty()) {
					ModelAndView mav = new ModelAndView(viewName, referenceData(request));
					mav.addObject(commandName, form);
					mav.addObject("errorMsgList", errorMsgList);
					return mav;
			}
			
		}		
		
		if(ltsAcqAccountSelectionFormDTO.isNewAccount()) {			
			try {
				 CustomerDetailProfileLtsDTO cust = orderCaptureDTO.getCustomerDetailProfileLtsDTO();				 
				 LtsAddressRolloutFormDTO addressRollout = orderCaptureDTO.getLtsAddressRolloutForm();
				 LtsAcqBillingAddressFormDTO billAddr = orderCaptureDTO.getLtsAcqBillingAddressForm();
				 LtsAcqSalesInfoFormDTO salesInfo = orderCaptureDTO.getLtsAcqSalesInfoForm();
				 
				 if(ltsCommonService.isNowDrgDownTime() || LtsAcqSbOrderHelper.isDummyCustomer(cust.getCustNum())) {
					 AccountDetailProfileLtsDTO acctInfo = getNewDummyAccount(cust, form.getBillMediaDtlList().get(0),
							 addressRollout, billAddr, orderCaptureDTO.isEyeOrder());
					 form.getBillMediaDtlList().get(0).setAcctNum(acctInfo.getAcctNum());
					 form.getBillMediaDtlList().get(0).setPrimaryAcct(true);
					 AccountDetailProfileLtsDTO[] acctDTOs = {acctInfo};
					 orderCaptureDTO.setAccountDetailProfileLtsDTO(acctDTOs);
					 orderCaptureDTO.getLtsAcqBillingAddressForm().getBillingAddrDtlList().get(0).setAcctNum(acctInfo.getAcctNum());
					 ltsAcqAccountSelectionFormDTO.setNewAccount(false);					 
				 } else {
					 //check bill media null when new acct 20170210
					 boolean chkBillMediaNull = true;
					 if(form.getBillMediaDtlList().get(0).getEmailBillItem()!=null && form.getBillMediaDtlList().get(0).getEmailBillItem().isSelected()) {
						 chkBillMediaNull = false;
				      } else if (form.getBillMediaDtlList().get(0).getPaperBillItem()!=null && form.getBillMediaDtlList().get(0).getPaperBillItem().isSelected()) {
				    	 chkBillMediaNull = false;
				      }
					 if (chkBillMediaNull){
						 logger.warn("LtsAcqBillMediaBillLangController no BillMedia, custnum: " + cust.getCustNum() + " userid " + orderCaptureDTO.getLtsAcqSalesInfoForm().getStaffId());
					     ModelAndView mav = new ModelAndView(viewName, referenceData(request));
						 List<String> messageList = new ArrayList<String>();
						 messageList.add(messageSource.getMessage("lts.acq.billingSelection.failToCreateNewAccountPlsSelectBillMedia", new Object[] {}, this.locale));
						 mav.addObject("errorMsgList", messageList);
						 mav.addObject(commandName, form);
						 return mav;
					 }
					 
					 
					 logger.warn("LtsAcqBillMediaBillLangController before create Acct, custnum: " + cust.getCustNum() + " userid " + orderCaptureDTO.getLtsAcqSalesInfoForm().getStaffId());
					 AccountDTO acctDTO = bomWsCreateAcctClient.ltsAcqCreateNewAcct(cust, form.getBillMediaDtlList().get(0),
							 addressRollout, billAddr, orderCaptureDTO.isEyeOrder(), salesInfo.getStaffId());
					 logger.warn("LtsAcqBillMediaBillLangController After create Acct, custnum: " + cust.getCustNum() + " userid " + orderCaptureDTO.getLtsAcqSalesInfoForm().getStaffId());
					 if(acctDTO != null){
						logger.warn("LtsAcqBillMediaBillLangController After create Acct, acctnum: " + acctDTO.getAcctNum());
						AccountDetailProfileLtsDTO acctInfo = customerProfileLtsService.getAccountbyAcctNum(acctDTO.getAcctNum(), LtsConstant.SYSTEM_ID_DRG);
						if(acctInfo != null){
							acctInfo.setAcctChrgType("RIAP");
							acctInfo.setPrimaryAcctInd(true);
							form.getBillMediaDtlList().get(0).setAcctNum(acctInfo.getAcctNum());
							form.getBillMediaDtlList().get(0).setPrimaryAcct(true);
						}
						AccountDetailProfileLtsDTO[] acctDTOs = {acctInfo};
						orderCaptureDTO.setAccountDetailProfileLtsDTO(acctDTOs);
						orderCaptureDTO.getLtsAcqBillingAddressForm().getBillingAddrDtlList().get(0).setAcctNum(acctInfo.getAcctNum());
						ltsAcqAccountSelectionFormDTO.setNewAccount(false);
					 }
				 }
				 
			} catch (Exception e) {
				    logger.warn("Exception in call remote service", e);
					ModelAndView mav = new ModelAndView(viewName, referenceData(request));
					List<String> messageList = new ArrayList<String>();
					messageList.add(messageSource.getMessage("lts.acq.billingSelection.failToCreateNewAccount", new Object[] {}, this.locale));
					mav.addObject("errorMsgList", messageList);
					mav.addObject(commandName, form);
					return mav;
			}	
		}

		orderCaptureDTO.setLtsAcqBillMediaBillLangForm(form);
		
		return new ModelAndView(new RedirectView(nextView));
		
	}

	 private boolean isSelectedTheClubPremium(AcqOrderCaptureDTO orderCapture) {
	    	SbOrderDTO sbOrder = orderCapture.getSbOrder();
	    	
	    	if (sbOrder != null) {
	    		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
	    		if (ltsService != null && ArrayUtils.isNotEmpty(ltsService.getItemDtls())) {
	    			for (ItemDetailLtsDTO subcItem : ltsService.getItemDtls()) {
	    				if (LtsConstant.ITEM_TYPE_PREMIUM.equals(subcItem.getItemType())) {
	    					String theClubPoint = (String)theClubPremiumCacheService.get(subcItem.getItemId());
	    					if (StringUtils.isNotBlank(theClubPoint)) {
	    						return true;
	    					}
	    				}
	    			}
	    		}
	    	}
	    	
	    	if (orderCapture.getLtsPremiumSelectionForm() != null) {
	    		if (orderCapture.getLtsPremiumSelectionForm().getPremiumSetList() != null 
	    				&& !orderCapture.getLtsPremiumSelectionForm().getPremiumSetList().isEmpty()) {
	    			for (ItemSetDetailDTO premiumSet : orderCapture.getLtsPremiumSelectionForm().getPremiumSetList()) {
	    				if (ArrayUtils.isNotEmpty(premiumSet.getItemDetails())) {
	    					for (ItemDetailDTO premium : premiumSet.getItemDetails()) {
	    						if (premium.isSelected()) {
	    							String theClubPoint = (String)theClubPremiumCacheService.get(premium.getItemId());
	    	    					if (StringUtils.isNotBlank(theClubPoint)) {
	    	    						return true;
	    	    					}		
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}
	    	
	    	return false;
	    }
	
	public boolean checkCspDummyDoc(String docId){
		for(String dummyString: LtsConstant.CSP_DUMMY_DOC_STRINGS){
			if(StringUtils.containsIgnoreCase(docId, dummyString)){
				return false;
			}
		}
		return true;
	}
	
	public CsPortalIdInqArqDTO checkCsIdForTheClubAndHkt(String docType, String docNum, String hktLiId, String clubLiId, String StaffId) {
		CsPortalIdInqArqDTO csPortalIdInqArqDTO = (CsPortalIdInqArqDTO)csPortalService.checkCsIdForTheClubAndHkt(
				docType, docNum, hktLiId, clubLiId, StaffId);		
		boolean isHktAldyReg = csPortalIdInqArqDTO.isCustAldyMyHkt();			
		boolean isClubAldyReg = csPortalIdInqArqDTO.isCustAldyTheClub();
		if (!isHktAldyReg && !isClubAldyReg) {
			csAcctInd = LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER;
		} else if (isHktAldyReg && !isClubAldyReg) {
			csAcctInd = LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY;
		} else if (!isHktAldyReg && isClubAldyReg) {
			csAcctInd = LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY;
		} else if (isHktAldyReg && isClubAldyReg) {
			csAcctInd = LtsCsPortalBackendConstant.EXIST_MY_HKT_AND_THE_CLUB;	
		}
 		isDocValid = checkCspDummyDoc(docNum) && csPortalIdInqArqDTO.isDocValid();
		isCsReplyError = csPortalIdInqArqDTO.isReturnError();
		isCsSystemBusy = csPortalIdInqArqDTO.isSystemBusy();
		isPhantomAcc = StringUtils.equals("Y", csPortalIdInqArqDTO.getoPhantomAcc());
		return csPortalIdInqArqDTO;
	}
	
	public CsPortalTxnDTO setCspVaildateRsp(CsPortalIdInqArqDTO csPortalIdInqArqDTO){
		Gson gson = new Gson();		   		 
		// save records into W_ONLINE_CSPORTAL_TXN
        CsPortalTxnDTO csPortalTxn = new CsPortalTxnDTO();
		csPortalTxn.setApiTy(csPortalIdInqArqDTO.getApiTy());
		csPortalTxn.setReply(csPortalIdInqArqDTO.getReply());
		csPortalTxn.setReqObj(gson.toJson(csPortalIdInqArqDTO));
		csPortalTxn.setSysId(csPortalIdInqArqDTO.getSysId());
		csPortalTxn.setClubReply(csPortalIdInqArqDTO.getoClubLiStatus());
		csPortalTxn.setMyhktReply(csPortalIdInqArqDTO.getoMyHktLiStatus());
		return csPortalTxn;		
	}
	
	public boolean isRegHktOnly() {
		return StringUtils.equals(LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY, csAcctInd);
	}
	
	public boolean isRegClubOnly() {
		return StringUtils.equals(LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY, csAcctInd);
	}
	
	public boolean isRegHktAndClub() {
		return StringUtils.equals(LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER, csAcctInd);
	}
	
	public boolean isExistHktAndClub() {
		return StringUtils.equals(LtsCsPortalBackendConstant.EXIST_MY_HKT_AND_THE_CLUB, csAcctInd);
	}

	public AccountDetailProfileLtsDTO getNewDummyAccount(CustomerDetailProfileLtsDTO cust, BillMediaDtl bill, 
			LtsAddressRolloutFormDTO addressRollout, LtsAcqBillingAddressFormDTO billAddr, boolean isEyeOrder) {
		AccountDetailProfileLtsDTO acct = new AccountDetailProfileLtsDTO();
		acct.setAcctChrgType("RIAP");
		acct.setPrimaryAcctInd(true);
		acct.setAutopayStatementInd(false);
		acct.setBillFmt("Y");
		acct.setBillFreq(isEyeOrder?LtsConstant.ACCT_BILL_FREQ_MONTHLY:LtsConstant.ACCT_BILL_FREQ_QUATERLY);	    	
    	acct.setBillLang(bill.getBillLang());
    	if(bill.getEmailBillItem().isSelected()) {
    		acct.setBillMedia(LtsConstant.LTS_BILL_MEDIA_PPS_BILL);
    		acct.setEmailAddr(bill.getBillMediaEmail());
    	} else if (bill.getPaperBillItem().isSelected()) {
    		acct.setBillMedia(LtsConstant.LTS_BILL_MEDIA_PAPER_BILL);
    	}
    	acct.setCreditStatus(LtsConstant.ACCOUNT_CREDIT_STATUS_NORMAL);
    	acct.setOutstandingAmount("0");
    	acct.setPayMethod(LtsConstant.PAYMENT_TYPE_CASH);
    	acct.setAcctNum(ltsAcqAccountDetailService.getBomDummyAcctNum());    	
    	acct.setBillAddr(getFullBillingAddress(billAddr));
    	if(LtsConstant.DOC_TYPE_HKID.equals(cust.getDocType())
				  || LtsConstant.DOC_TYPE_PASSPORT.equals(cust.getDocType())) {
    		acct.setAcctName(cust.getTitle()+ " " + cust.getLastName()+ " " + cust.getFirstName());	    		
  	    } else if (LtsConstant.DOC_TYPE_HKBR.equals(cust.getDocType())) {
  		    acct.setAcctName(cust.getCompanyName());
  	    }
    	
		return acct;
	}
	protected Map<String, List<LookupItemDTO>> referenceData(HttpServletRequest request) throws Exception {
		Locale locale = RequestContextUtils.getLocale(request);
		Map<String, List<LookupItemDTO>> referenceData = new HashMap<String, List<LookupItemDTO>>();
		referenceData.put("optOutReasonList", Arrays.asList(optOutReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		//referenceData.put("ltsPaperWaiveLkupList", Arrays.asList(waiveReasonPropertiesLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		List<LookupItemDTO> tempLtsPaperWaiveLkupList = Arrays.asList(waiveReasonPropertiesLkupCacheService.getCodeLkupDAO().getCodeLkup());
		if(tempLtsPaperWaiveLkupList!=null)
		{
			for(int i=0; i<tempLtsPaperWaiveLkupList.size(); i++)
			{
				LookupItemDTO tempItem = tempLtsPaperWaiveLkupList.get(i);
				tempItem.setItemValue(messageSource.getMessage(tempItem.getItemValue().toString(), new Object[] {}, this.locale));
				tempLtsPaperWaiveLkupList.set(i, tempItem);
			}
		}
		referenceData.put("ltsPaperWaiveLkupList", tempLtsPaperWaiveLkupList);
		
		return referenceData;
	}
	public String getFullBillingAddress(LtsAcqBillingAddressFormDTO billAddr) {
		StringBuilder sb = new StringBuilder();
		sb.append(billAddr.getBillingAddrDtlList().get(0).getAddrLine1());
		sb.append("\n");
		sb.append(billAddr.getBillingAddrDtlList().get(0).getAddrLine2());
		sb.append("\n");
		sb.append(billAddr.getBillingAddrDtlList().get(0).getAddrLine3());
		sb.append("\n");
		sb.append(billAddr.getBillingAddrDtlList().get(0).getAddrLine4());
		sb.append("\n");
		sb.append(billAddr.getBillingAddrDtlList().get(0).getAddrLine5());
		sb.append("\n");
		sb.append(billAddr.getBillingAddrDtlList().get(0).getAddrLine6());
		
		return sb.toString();
	}
	
	public CsPortalService getCsPortalService() {
		return csPortalService;
	}

	public void setCsPortalService(CsPortalService csPortalService) {
		this.csPortalService = csPortalService;
	}

	public LtsPaymentService getLtsPaymentService() {
		return ltsPaymentService;
	}

	public void setLtsPaymentService(LtsPaymentService ltsPaymentService) {
		this.ltsPaymentService = ltsPaymentService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	
	public String getWsUrl() {
		return wsUrl;
	}

	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public BomCreateAccountWsClient getBomWsCreateAcctClient() {
		return bomWsCreateAcctClient;
	}

	public void setBomWsCreateAcctClient(
			BomCreateAccountWsClient bomWsCreateAcctClient) {
		this.bomWsCreateAcctClient = bomWsCreateAcctClient;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	/**
	 * @return the ltsCommonService
	 */
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	/**
	 * @param ltsCommonService the ltsCommonService to set
	 */
	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsAcqAccountDetailService getLtsAcqAccountDetailService() {
		return ltsAcqAccountDetailService;
	}

	public void setLtsAcqAccountDetailService(
			LtsAcqAccountDetailService ltsAcqAccountDetailService) {
		this.ltsAcqAccountDetailService = ltsAcqAccountDetailService;
	}

	public CodeLkupCacheService getTheClubPremiumCacheService() {
		return theClubPremiumCacheService;
	}

	public void setTheClubPremiumCacheService(
			CodeLkupCacheService theClubPremiumCacheService) {
		this.theClubPremiumCacheService = theClubPremiumCacheService;
	}
	
	public CodeLkupCacheService getOptOutReasonLkupCacheService() {
		return optOutReasonLkupCacheService;
	}

	public void setOptOutReasonLkupCacheService(
			CodeLkupCacheService optOutReasonLkupCacheService) {
		this.optOutReasonLkupCacheService = optOutReasonLkupCacheService;
	}

	public CodeLkupCacheService getWaiveReasonPropertiesLkupCacheService() {
		return waiveReasonPropertiesLkupCacheService;
	}

	public void setWaiveReasonPropertiesLkupCacheService(
			CodeLkupCacheService waiveReasonPropertiesLkupCacheService) {
		this.waiveReasonPropertiesLkupCacheService = waiveReasonPropertiesLkupCacheService;
	}

	public CodeLkupCacheService getLtsIguardCarecashLkupCacheService() {
		return ltsIguardCarecashLkupCacheService;
	}

	public void setLtsIguardCarecashLkupCacheService(
			CodeLkupCacheService ltsIguardCarecashLkupCacheService) {
		this.ltsIguardCarecashLkupCacheService = ltsIguardCarecashLkupCacheService;
	}

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
