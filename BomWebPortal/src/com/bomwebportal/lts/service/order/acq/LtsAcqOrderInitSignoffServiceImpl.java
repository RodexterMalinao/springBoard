package com.bomwebportal.lts.service.order.acq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.lts.dto.CareCashOptInArqDTO;
import com.bomwebportal.lts.dto.CsPortalIdRegrArqDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerIguardRegDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceActionTypeDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsClubMembershipService;
import com.bomwebportal.lts.service.LtsEmailService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsRemarkService;
import com.bomwebportal.lts.service.PipbActvLtsService;
import com.bomwebportal.lts.service.acq.LtsAcq0060DetailService;
import com.bomwebportal.lts.service.bom.CustContactInfoService;
import com.bomwebportal.lts.service.bom.CustPdpoProfileService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;
import com.bomwebportal.lts.service.order.LtsDsOrderApprovalSubmitService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.PipbOrderResumeSubmitService;
import com.bomwebportal.lts.service.order.PipbOrderSignoffLtsServiceImpl;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.service.sms.LtsSmsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.ServiceActionBase;
import com.pccw.util.spring.SpringApplicationContext;


public class LtsAcqOrderInitSignoffServiceImpl extends PipbOrderSignoffLtsServiceImpl implements
		LtsAcqOrderInitSignoffService {
	
	private LtsRemarkService ltsRemarkService;
	private ReportCreationLtsService reportCreationLtsService;
	private CodeLkupCacheService reportSetLkupCacheService;
	private LtsSmsService ltsSmsService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private LtsEmailService ltsEmailService;
    private PipbActvLtsService pipbActvLtsService;
    private LtsDsOrderApprovalSubmitService ltsDsOrderApprovalSubmitService;
    private PipbOrderResumeSubmitService pipbOrderResumeSubmitService; 
	private LtsAcq0060DetailService ltsAcq0060DetailService;
	private ServiceProfileLtsService serviceProfileLtsService;
	private LtsClubMembershipService ltsClubMembershipService; 
	private CustContactInfoService custContactInfoService;
	private CustPdpoProfileService custPdpoProfileService;
	
	public ValidationResultDTO initialSignoff(SbOrderDTO pSbOrder, BomSalesUserDTO bomSalesUser) {
		List<String> messageList = new ArrayList<String>(); 
		Status sts = Status.VALID;
		try{
			String userId = bomSalesUser.getUsername();
			ServiceDetailLtsDTO ltsSrvDtl = LtsSbOrderHelper.getLtsService(pSbOrder);
			
			/*get order latest status*/
			OrderStatusDTO[] latestStatus = orderStatusService.getStatus(pSbOrder.getOrderId());
			OrderStatusDTO primaryDtlStatus = null;
			for(OrderStatusDTO dtlStatus : latestStatus){
				if(ltsSrvDtl.getDtlId().equals(dtlStatus.getDtlId())){
					primaryDtlStatus = dtlStatus;
					break;
				}
			}
			
			if(LtsConstant.ORDER_STATUS_SUSPENDED.equals(primaryDtlStatus.getSbStatus())){
				/*update status to O*/
				this.initializeSignoff(pSbOrder, userId);
				preSubmitSignoff(pSbOrder, userId);
				
				setSignOffDate(pSbOrder);
				String emailResult = saveFormsAndEmailAndSms(pSbOrder, userId);
				if(StringUtils.isNotBlank(emailResult) && !StringUtils.equals(emailResult, "SUCCESS")){
					messageList.add(emailResult);
					sts = Status.INVAILD;
				}
				
				if(ltsSrvDtl != null && 
						(LtsConstant.DN_SOURCE_DN_RESERVED.equals(ltsSrvDtl.getDnSource()) || LtsConstant.DN_SOURCE_DN_PIPB.equals(ltsSrvDtl.getDnSource()))){
					pipbActvLtsService.insertDummyRecordToBInvPreassgn(pSbOrder);
				}
				
				if(!checkAndSubmitNameMismatchOrder(pSbOrder, ltsSrvDtl, userId)){
					ltsDsOrderApprovalSubmitService.resumeSignOffDsOrder(pSbOrder, userId);
				}
				String updatePdpoResult = updateCustDataPrivayInfo(pSbOrder, userId);
				if (StringUtils.isNotBlank(updatePdpoResult)) {
					messageList.add(updatePdpoResult);	
					sts = Status.INVAILD;
				}
				custContactInfoService.updateCustContactInfo(pSbOrder.getOrderId(), pSbOrder.getContact(), bomSalesUser.getUsername(), pSbOrder.getSalesChannel());
				checkAndRegCspForSbOrder(pSbOrder, userId);
				regIguardCarecashForSbOrder(pSbOrder, userId);
				ltsClubMembershipService.earnClubPointsAndUpdateItems(pSbOrder, userId);
			}
			/*await prepayment status*/
			else if(LtsConstant.ORDER_STATUS_AWAIT_PREPAYMENT.equals(primaryDtlStatus.getSbStatus())
					&& LtsConstant.ORDER_STATUS_REASON_AWAIT_PREPAY.equals(primaryDtlStatus.getReasonCd())){
				cleanupExistingDsWorkQueueInTheService(pSbOrder);
				this.initializeSignoff(pSbOrder, userId);
				preSubmitSignoff(pSbOrder, userId);

				if(ltsSrvDtl != null && 
						(LtsConstant.DN_SOURCE_DN_RESERVED.equals(ltsSrvDtl.getDnSource()) || LtsConstant.DN_SOURCE_DN_PIPB.equals(ltsSrvDtl.getDnSource()))){
					pipbActvLtsService.insertDummyRecordToBInvPreassgn(pSbOrder);
				}
				
				if(!checkAndSubmitNameMismatchOrder(pSbOrder, ltsSrvDtl, userId)){
					ltsDsOrderApprovalSubmitService.resumeSignOffDsOrder(pSbOrder, userId);
				}
				String updatePdpoResult = updateCustDataPrivayInfo(pSbOrder, userId);
				if (StringUtils.isNotBlank(updatePdpoResult)) {
					messageList.add(updatePdpoResult);	
					sts = Status.INVAILD;
				}
				custContactInfoService.updateCustContactInfo(pSbOrder.getOrderId(), pSbOrder.getContact(), bomSalesUser.getUsername(), pSbOrder.getSalesChannel());
				
			}
			/*await QC status*/
			else if(LtsConstant.ORDER_STATUS_AWAIT_QC.equals(primaryDtlStatus.getSbStatus())){
				cleanupExistingDsWorkQueueInTheService(pSbOrder);
				this.initializeSignoff(pSbOrder, userId);
				pipbOrderResumeSubmitService.resumeSignOffPipbOrder(pSbOrder, userId);
				String updatePdpoResult = updateCustDataPrivayInfo(pSbOrder, userId);
				if (StringUtils.isNotBlank(updatePdpoResult)) {
					messageList.add(updatePdpoResult);	
					sts = Status.INVAILD;
				}
				custContactInfoService.updateCustContactInfo(pSbOrder.getOrderId(), pSbOrder.getContact(), bomSalesUser.getUsername(), pSbOrder.getSalesChannel());
				
			}
			else {
				messageList.add("Status not match for initialSignoff : " + primaryDtlStatus.getSbStatus());
				sts = Status.INVAILD;
			}
			
			return new ValidationResultDTO(sts, messageList, null);
		} catch (Exception e) {
			logger.error("Exception caught in Signoff Order " + pSbOrder.getOrderId() + " - " + e.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			messageList.add("Exception caught in initialSignoff : " + e.getClass().getName());
			return new ValidationResultDTO(Status.INVAILD, messageList, null);
		}
	}
	
	private boolean checkAndSubmitNameMismatchOrder(SbOrderDTO pSbOrder, ServiceDetailLtsDTO ltsService, String userId){
		if(pSbOrder.getLtsDsOrderInfo() == null){
			return false;
		}
		
		if(StringUtils.equals("Y", ltsService.getAccount().getCustomer().getMismatchInd())
				&& !Arrays.asList(LtsConstant.NAME_MISMATCH_AFTER_APPROVAL).contains(pSbOrder.getLtsDsOrderInfo().getNameMismatchStatus())){
			/* update status and create approval WQ*/
			return ltsDsOrderApprovalSubmitService.submitOrderForCustNameNotMatch(pSbOrder, userId);
		}
		return false;
	}

	private void generateRemarks(SbOrderDTO sbOrder, String userId) {
		ltsRemarkService.generateOrderRemark(sbOrder, userId);
		ltsRemarkService.generateCustomerRemark(sbOrder, userId);
	}
	
	private void preSubmitSignoff(SbOrderDTO sbOrder, String userId) throws Exception {		
		generateRemarks(sbOrder, userId);
		checkAndTerminateExistingMobTel(sbOrder, userId);
		this.clearFollowupWorkQueue(sbOrder, userId);
		getServiceActions(sbOrder, userId);		
		
		logger.warn("preSubmitSignoff: createDsWaiveQcActivity [START]");
		LtsDsOrderInfoDTO dsInfo = sbOrder.getLtsDsOrderInfo();
		if(dsInfo != null && StringUtils.isNotBlank(dsInfo.getWaiveQcCd())){
			logger.warn("preSubmitSignoff: createDsWaiveQcActivity processing...");
		}
		logger.warn("preSubmitSignoff: createDsWaiveQcActivity [END]");
		
		updateSuspendBomOrder(sbOrder, userId);
	}
	
	private void checkAndTerminateExistingMobTel(SbOrderDTO sbOrder, String userId) {
		// primary del
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		if (ltsServiceDetail != null) {
			ServiceDetailProfileLtsDTO serviceProfile = serviceProfileLtsService.getServiceProfileBySrvNum(
						ltsServiceDetail.getSrvNum(), LtsConstant.SERVICE_TYPE_MOB);
			if (serviceProfile != null && LtsConstant.DAT_CD_TEL.equals(serviceProfile.getDatCd()) 
					&& StringUtils.isEmpty(serviceProfile.getEffEndDate())) {
				ltsAcq0060DetailService.terminate0060DetailService(sbOrder, 
						serviceProfile.getSrvNum(), serviceProfile.getSrvType(), serviceProfile.getDatCd(), userId);
			}
		}
	}
	
	public String saveFormsAndEmailAndSms(SbOrderDTO sbOrder, String userId) {
		saveAfForm(sbOrder);
		sendSignOffSms(sbOrder, userId);
		createLetterPrintReq(sbOrder, userId);
		return saveAndSendApplicationForm(sbOrder, userId);
	}
	
	protected String saveAfForm(SbOrderDTO sbOrder) {		
        try {
        	saveForms(sbOrder, ReportCreationLtsService.RPT_SET_SIGNOFF_AF);
        	return null;
	    } catch (Exception e) {
	    	logger.error("Failed to save AF when sign off order");
	    	return "Failed to save AF when sign off order";
	    }
	}
	
	private String createLetterPrintReq(SbOrderDTO sbOrder, String userId) {
		if (LtsConstant.DISTRIBUTE_MODE_PAPER.equals(sbOrder.getDisMode())) {
			return ltsEmailService.createLetterPrintReq(sbOrder, userId);	
		}
		return null;
	}
	
    protected void sendSignOffSms(SbOrderDTO sbOrder, String userId) {
		if (LtsConstant.DISTRIBUTE_MODE_ESIGNATURE.equals(sbOrder.getDisMode())
				&& StringUtils.isNotBlank(sbOrder.getSmsNo())) {
			ltsSmsService.sendSignOffSms(sbOrder, userId);
		}
	}
    
    protected void saveForms(SbOrderDTO sbOrder, String action) {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(LtsConstant.RPT_KEY_SBORDER, sbOrder);
		inputMap.put(LtsConstant.RPT_KEY_LOB, LtsConstant.LOB_LTS);		
		ReportSetDTO rptSet = new ReportSetDTO();
		rptSet.setLob(LtsConstant.LOB_LTS);
		rptSet.setChannelId(sbOrder.getOrderId().substring(0,1));
		rptSet.setRptSet(action);
		reportCreationLtsService.generateReport((ReportSetDTO)reportSetLkupCacheService.get(rptSet.getLkupKey()), inputMap);
	}

    public void setSignOffDate(SbOrderDTO sbOrder) {
    	sbOrder.setSignOffDate(orderDetailService.updateSignoffDate(sbOrder.getOrderId())); // update bomweb_order.sign_off_date 
    	String termDate = this.orderDetailService.updatePaymentTermDate(sbOrder.getOrderId());
    	this.setPaymentTermDate(sbOrder, termDate);
	}

    protected String saveAndSendApplicationForm(SbOrderDTO sbOrder, String userId) {
		List<AllOrdDocLtsDTO> generatedFormList = ltsOrderDocumentService.getGeneratedFormList(sbOrder);
		ltsOrderDocumentService.saveGeneratedForm(generatedFormList, userId);
		return sendSignOffEmail(sbOrder, userId);
	}

    protected String sendSignOffEmail(SbOrderDTO sbOrder, String userId) {		
		if (LtsConstant.DISTRIBUTE_MODE_ESIGNATURE.equals(sbOrder.getDisMode()) 
				&& StringUtils.isNotBlank(sbOrder.getEsigEmailAddr())) {
			return ltsEmailService.sendSignOffEmail(sbOrder, sbOrder.getEsigEmailAddr(), userId);	
		}
    	return null;
	}
    
    public boolean checkAndRegCspForSbOrder(SbOrderDTO sbOrder, String username){
		ServiceDetailLtsDTO ltsSrv = LtsSbHelper.getLtsService(sbOrder);
		String regLogin = null;
		String regMobile = null;
		String targetAcct = null;
		
		for(ItemDetailLtsDTO itemDtl: ltsSrv.getItemDtls()){
			if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL)){
				regLogin = ltsSrv.getAccount().getCustomer().getCsPortalLogin();
				regMobile = ltsSrv.getAccount().getCustomer().getCsPortalMobile();
				//Do not register myHKT if the email or mobile is dummy
				if(( StringUtils.contains(regLogin, LtsConstant.CSP_DUMMY_EMAIL_DOMAIN)
					 || StringUtils.contains(regMobile, LtsConstant.CSP_DUMMY_MOBILE_NUM))){
					targetAcct = LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY;
				}else{
					targetAcct = LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER;
				}
				break;
			}
			if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_MYHKT_BILL)){
				regLogin = ltsSrv.getAccount().getCustomer().getCsPortalLogin();
				regMobile = ltsSrv.getAccount().getCustomer().getCsPortalMobile();
				targetAcct = LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY;
				break;
			}
			if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_THE_CLUB_BILL)){
				regLogin = ltsSrv.getAccount().getCustomer().getTheClubLogin();
				regMobile = ltsSrv.getAccount().getCustomer().getTheClubMobile();
				targetAcct = LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY;
				break;
			}
		}
		
		if(StringUtils.isNotBlank(regLogin)){
			CsPortalIdRegrArqDTO csPortalIdRegrArqDTO = (CsPortalIdRegrArqDTO)csPortalService.regCsIdForTheClubAndHkt(sbOrder, 
					sbOrder.getStaffNum(), 
					LtsCsPortalBackendConstant.SOURCE_SYSTEM_SPRINGBOARD, 
					StringUtils.isNotBlank(sbOrder.getAddress().getAddrInventory().getHktPremier()), 
					targetAcct);
			CustomerDetailLtsDTO cust = LtsSbHelper.getLtsService(sbOrder).getAccount().getCustomer();				
			if (LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY.equals(targetAcct)) {
				cust.setCsPortalInd(csPortalIdRegrArqDTO!=null && csPortalIdRegrArqDTO.isMyHktRegSucces()?
						LtsConstant.CSP_REG:LtsConstant.CSP_FAIL_REG);
			}
			if (LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY.equals(targetAcct)) {
                cust.setTheClubInd(csPortalIdRegrArqDTO!=null && csPortalIdRegrArqDTO.isTheClubRegSucces()?
						LtsConstant.CSP_REG:LtsConstant.CSP_FAIL_REG);
			} 
			if (LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER.equals(targetAcct)) {
				cust.setCsPortalInd(csPortalIdRegrArqDTO!=null && csPortalIdRegrArqDTO.isMyHktRegSucces()?
						LtsConstant.CSP_REG:LtsConstant.CSP_FAIL_REG);
				cust.setTheClubInd(csPortalIdRegrArqDTO!=null && csPortalIdRegrArqDTO.isTheClubRegSucces()?
						LtsConstant.CSP_REG:LtsConstant.CSP_FAIL_REG);	
			}
			cust.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			saveSbOrderLtsService.saveCustomer(cust, sbOrder.getOrderId(), cust.getCustNo(), username);
		}			
		
		return true;	
	}

	public boolean regIguardCarecashForSbOrder(SbOrderDTO sbOrder, String username) {
		
		boolean returnVal = false;
		CustomerIguardRegDTO custIguard = new CustomerIguardRegDTO();
		CustomerDetailLtsDTO cust = LtsSbHelper.getLtsService(sbOrder).getAccount().getCustomer();	
		
		custIguard = sbOrder.getCustIguardReg();
		
		if(custIguard!=null)
		{			
			CareCashOptInArqDTO arq = new CareCashOptInArqDTO();
			
			if(custIguard.getCarecashInd().equalsIgnoreCase("I"))
			{
				arq.setiFormNum(sbOrder.getOrderId());
				//arq.setiChnlCode(sbOrder.getSalesChannel());
				if(sbOrder.getCreateChannel().equalsIgnoreCase("RS"))
				{
					arq.setiChnlCode(sbOrder.getShopCd());
				}
				else
				{
					arq.setiChnlCode(sbOrder.getCreateChannel());
				}
				
				arq.setiEnName(cust.getTitle()+ " " +cust.getLastName() + " " + cust.getFirstName());
				arq.setiDocTy(cust.getIdDocType());
				arq.setiDocNum(cust.getIdDocNum());
				arq.setiOptFor("I");
				arq.setiCtMail(custIguard.getEmailAddr().toLowerCase());
				arq.setiCtPhone(custIguard.getContactNum());
								
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				String dateInString = cust.getDob(); // "14/10/1998 00:00"
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
				
				try {
					Date tempdate = sdf1.parse(dateInString);
					arq.setiDob(sdf2.format(tempdate));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					arq.setiDob("");
				}
				
				if(custIguard.getCarecashDmInd().equalsIgnoreCase("Y"))
				{
					arq.setiOpt4Dm("O");
				}
				else
				{
					arq.setiOpt4Dm("I");
				}
				
				if(sbOrder.getEsigEmailLang().equalsIgnoreCase("CHN"))
				{
					arq.setiLang("zh");
				}
				else
				{
					arq.setiLang("en");
				}
				
				ServiceDetailLtsDTO lts2ndDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
				if (lts2ndDelServiceDetail!=null) {
					arq.setiNuLtsSubn(2);
				}
				else
				{
					arq.setiNuLtsSubn(1);
				}				
				arq.setiVisitTs("");
				
				try
				{
					arq = customerIguardApiService.regCustomerIguardCareCash(arq);
				}				
				catch (Exception rEx) 
				{
					logger.error(ExceptionUtils.getFullStackTrace(rEx));
					arq = new CareCashOptInArqDTO();
					arq.setReply("Api Fail");
				}
			}
			
			if(custIguard.getCarecashInd().equalsIgnoreCase("O"))
			{
				arq.setiDocTy(cust.getIdDocType());
				arq.setiDocNum(cust.getIdDocNum());
				arq.setiOptFor("O");
				
				arq.setiFormNum(sbOrder.getOrderId()); // arq.setiFormNum("");
				arq.setiChnlCode("");
				arq.setiEnName("");
				arq.setiCtMail("");
				arq.setiCtPhone("");
				arq.setiDob("");
				arq.setiOpt4Dm("");
				arq.setiLang("");
				arq.setiVisitTs("");
				
				try
				{
					arq = customerIguardApiService.regCustomerIguardCareCash(arq);
				}				
				catch (Exception rEx) 
				{
					logger.error(ExceptionUtils.getFullStackTrace(rEx));
					arq = new CareCashOptInArqDTO();
					arq.setReply("Api Fail");
				}
				
			}
			
			if(custIguard.getCarecashInd().equalsIgnoreCase("P"))
			{
				arq.setiDocTy(cust.getIdDocType());
				arq.setiDocNum(cust.getIdDocNum());
				arq.setiOptFor("P");
				
				arq.setiFormNum(sbOrder.getOrderId()); // arq.setiFormNum("");
				arq.setiChnlCode("");
				arq.setiEnName("");
				arq.setiCtMail("");
				arq.setiCtPhone("");
				arq.setiDob("");
				arq.setiOpt4Dm("");
				arq.setiLang("");
				arq.setiVisitTs(LtsDateFormatHelper.getSysDate("yyyyMMddHHmmss"));
				
				try
				{
					arq = customerIguardApiService.regCustomerIguardCareCash(arq);
				}				
				catch (Exception rEx) 
				{
					logger.error(ExceptionUtils.getFullStackTrace(rEx));
					arq = new CareCashOptInArqDTO();
					arq.setReply("Api Fail");
				}
				
			}
			
			ServiceActionBase customerIguardRegService = SpringApplicationContext.getBean("customerIguardRegService");
			custIguard.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			custIguard.setRtnMsg(arq.getReply());
			
			if(arq.getReply()!=null)
			{
				if(arq.getReply().equalsIgnoreCase("RC_SUCC"))
				{
					custIguard.setStatus("S");
				}
				else
				{
					custIguard.setStatus("F");
				}
			}			
			
			customerIguardRegService.doSave(custIguard, username, sbOrder.getOrderId());
			
			if(custIguard.getCarecashInd().equalsIgnoreCase("I"))
			{
				if(arq.getReply().equalsIgnoreCase("RC_SUCC"))
				{      	
					saveForms(sbOrder, "IGCC_AF");
					saveForms(sbOrder, "IGCP_AF");
					ltsEmailService.sendCareCashEmailToCustomer(sbOrder, custIguard.getEmailAddr(), username);
					ltsEmailService.sendCareCashEmailToAdmin(sbOrder, username);
				}
			}
		}
				
		return returnVal;
	}
	
	public String updateCustDataPrivayInfo(SbOrderDTO sbOrder, String userId) {
		ServiceDetailDTO coreService = LtsSbOrderHelper.getLtsService(sbOrder);
		CustOptOutInfoLtsDTO custOptOutInfo = coreService.getAccount().getCustomer().getCustOptOutInfo();
		if (custOptOutInfo == null) {
			return null;
		}

		String returnMsg = null;
		String returnMsgLts = null;
		String returnMsgIdd = null;
		
		try {
			if (LtsConstant.PDPO_UPDATE_BOM_STATUS_PENDING.equals(custOptOutInfo.getUpdBomStatus())) {
				custOptOutInfo.setBillInsert(custOptOutInfo.getDirectMailing());	
				custOptOutInfo.setCdOutdial(custOptOutInfo.getDirectMailing());	
				custOptOutInfo.setBm(custOptOutInfo.getDirectMailing());	
				custOptOutInfo.setSmsEye(custOptOutInfo.getDirectMailing());
				returnMsgLts = custPdpoProfileService.updateCustDataPrivayInfoWithLob(custOptOutInfo, 
						coreService.getAccount().getBillingAddress(), userId, LtsConstant.DATA_PRIVACY_BOM_LOB_LTS);
				custOptOutInfo.setBillInsert(custOptOutInfo.getDirectMailing());	
				custOptOutInfo.setCdOutdial(custOptOutInfo.getDirectMailing());	
				custOptOutInfo.setBm(null);	
				custOptOutInfo.setSmsEye(null);
				returnMsgIdd = custPdpoProfileService.updateCustDataPrivayInfoWithLob(custOptOutInfo, 
						coreService.getAccount().getBillingAddress(), userId, LtsConstant.DATA_PRIVACY_BOM_LOB_IDD);
				if (StringUtils.isNotBlank(returnMsgLts)){
					custOptOutInfo.setUpdBomStatus(LtsConstant.PDPO_UPDATE_BOM_STATUS_FAIL);
					custOptOutInfo.setErrMsg(returnMsgLts);
					returnMsg = "Fail to update customer data privay in BOM: [" + returnMsgLts + "]";
				}else if(StringUtils.isNotBlank(returnMsgIdd)){
					custOptOutInfo.setUpdBomStatus(LtsConstant.PDPO_UPDATE_BOM_STATUS_FAIL);
					custOptOutInfo.setErrMsg(returnMsgLts);
					returnMsg = "Fail to update customer data privay in BOM: [" + returnMsgIdd + "]";
				}else{
					custOptOutInfo.setUpdBomStatus(LtsConstant.PDPO_UPDATE_BOM_STATUS_COMPLETED);
				}
				custOptOutInfo.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
				ServiceActionBase custOptOutInfoLtsService = SpringApplicationContext.getBean("custOptOutInfoLtsService");
				custOptOutInfoLtsService.doSave(custOptOutInfo, userId, sbOrder.getOrderId(), custOptOutInfo.getCustNo());
			}	
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return "Fail to update customer data privay in BOM:[" + e.getMessage() + "]";	
		}
		return returnMsg;
	}
	
    private void cleanupExistingDsWorkQueueInTheService(SbOrderDTO pSbOrder){		
		if(pSbOrder != null && pSbOrder.getSrvDtls() != null){
			ServiceDetailDTO[] srvDtls = pSbOrder.getSrvDtls();			
			for (int i = 0; srvDtls != null && i < srvDtls.length; ++i) {
				srvDtls[i].setSrvActions(new ServiceActionTypeDTO[]{});
			}
		}
	}

	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	public LtsRemarkService getLtsRemarkService() {
		return ltsRemarkService;
	}

	public void setLtsRemarkService(LtsRemarkService ltsRemarkService) {
		this.ltsRemarkService = ltsRemarkService;
	}

	public ReportCreationLtsService getReportCreationLtsService() {
		return reportCreationLtsService;
	}

	public void setReportCreationLtsService(
			ReportCreationLtsService reportCreationLtsService) {
		this.reportCreationLtsService = reportCreationLtsService;
	}

	public CodeLkupCacheService getReportSetLkupCacheService() {
		return reportSetLkupCacheService;
	}

	public void setReportSetLkupCacheService(
			CodeLkupCacheService reportSetLkupCacheService) {
		this.reportSetLkupCacheService = reportSetLkupCacheService;
	}

	public LtsSmsService getLtsSmsService() {
		return ltsSmsService;
	}

	public void setLtsSmsService(LtsSmsService ltsSmsService) {
		this.ltsSmsService = ltsSmsService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	public LtsEmailService getLtsEmailService() {
		return ltsEmailService;
	}

	public void setLtsEmailService(LtsEmailService ltsEmailService) {
		this.ltsEmailService = ltsEmailService;
	}

	public PipbActvLtsService getPipbActvLtsService() {
		return pipbActvLtsService;
	}

	public void setPipbActvLtsService(PipbActvLtsService pipbActvLtsService) {
		this.pipbActvLtsService = pipbActvLtsService;
	}

	public LtsDsOrderApprovalSubmitService getLtsDsOrderApprovalSubmitService() {
		return ltsDsOrderApprovalSubmitService;
	}

	public void setLtsDsOrderApprovalSubmitService(
			LtsDsOrderApprovalSubmitService ltsDsOrderApprovalSubmitService) {
		this.ltsDsOrderApprovalSubmitService = ltsDsOrderApprovalSubmitService;
	}

	public PipbOrderResumeSubmitService getPipbOrderResumeSubmitService() {
		return pipbOrderResumeSubmitService;
	}

	public void setPipbOrderResumeSubmitService(
			PipbOrderResumeSubmitService pipbOrderResumeSubmitService) {
		this.pipbOrderResumeSubmitService = pipbOrderResumeSubmitService;
	}

	public LtsAcq0060DetailService getLtsAcq0060DetailService() {
		return ltsAcq0060DetailService;
	}

	public void setLtsAcq0060DetailService(
			LtsAcq0060DetailService ltsAcq0060DetailService) {
		this.ltsAcq0060DetailService = ltsAcq0060DetailService;
	}

	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
	}

	public LtsClubMembershipService getLtsClubMembershipService() {
		return ltsClubMembershipService;
	}

	public void setLtsClubMembershipService(
			LtsClubMembershipService ltsClubMembershipService) {
		this.ltsClubMembershipService = ltsClubMembershipService;
	}

	public CustContactInfoService getCustContactInfoService() {
		return custContactInfoService;
	}

	public void setCustContactInfoService(
			CustContactInfoService custContactInfoService) {
		this.custContactInfoService = custContactInfoService;
	}

	public CustPdpoProfileService getCustPdpoProfileService() {
		return custPdpoProfileService;
	}

	public void setCustPdpoProfileService(
			CustPdpoProfileService custPdpoProfileService) {
		this.custPdpoProfileService = custPdpoProfileService;
	}
	
	
}
