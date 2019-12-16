package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dao.order.OrderModifyDAO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO.BaCaActionType;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateBillingInfoFormDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;

public class DisconnectOrderModifyServiceImpl extends DisconnectOrderCreateServiceImpl implements DisconnectOrderModifyService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected LtsOrderDocumentService ltsOrderDocumentService;
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	protected OrderModifyDAO orderModifyDao;
	
	public OrderModifyDAO getOrderModifyDao() {
		return orderModifyDao;
	}

	public void setOrderModifyDao(OrderModifyDAO orderModifyDao) {
		this.orderModifyDao = orderModifyDao;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}
	
	public SbOrderDTO modifyTerminateSbOrder(TerminateOrderCaptureDTO orderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		
		
		if(LtsConstant.SALES_ROLE_SUPPORT.equals(bomSalesUser.getCategory())){
			modifyTerminateSalesInfo(orderCapture, sbOrder, bomSalesUser);
			modifyTerminateBillingInfo(orderCapture, sbOrder);
		}
		
		modifyTerminateAppointment(orderCapture, sbOrder);
		modifyTerminateRemark(orderCapture, sbOrder);
		modifyTerminateCallPlan(orderCapture, sbOrder);
		
		if (orderCapture.getLtsTerminateSupportDocForm() != null) {
			sbOrder.setDisMode(orderCapture.getLtsTerminateSupportDocForm().getDistributionMode());
			sbOrder.setEsigEmailLang(orderCapture.getLtsTerminateSupportDocForm().getDistributeLang());
			sbOrder.setCollectMethod(orderCapture.getLtsTerminateSupportDocForm().getCollectMethod());
			sbOrder.setSmsNo(orderCapture.getLtsTerminateSupportDocForm().getDistributeSms());
			
			sbOrder.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			modifyTerminateAllOrdDocAssgnLts(orderCapture, sbOrder, bomSalesUser);	
		}
		
		return sbOrder;
	}
	
	private void modifyTerminateCallPlan(TerminateOrderCaptureDTO orderCapture, SbOrderDTO pSbOrder){
		   
		ServiceCallPlanDetailLtsDTO[] srvCPDtlDtos = null;
		String action = LtsBackendConstant.IDD_ACTION_REMOVE;
		
		for (ServiceDetailDTO serviceDetail : pSbOrder.getSrvDtls()){
			 
			 srvCPDtlDtos = serviceDetail.getSrvCallPlanDtls();
			 
				if (srvCPDtlDtos == null || srvCPDtlDtos.length == 0) {
					continue;
				}
				else{
					for(ServiceCallPlanDetailLtsDTO srvCPDtlDto : srvCPDtlDtos){
						
					   action = LtsConstant.IO_IND_OUT.equals(srvCPDtlDto.getIoInd())? LtsBackendConstant.IDD_ACTION_REMOVE : LtsBackendConstant.IDD_ACTION_RETAIN;
					   if("Y".equals(srvCPDtlDto.getChgDcaInd())){
						   action = LtsBackendConstant.IDD_ACTION_CHG_ACCT_DCA;
					   }
					   srvCPDtlDto.setEffEndDate(calculateCallPlanEffEndDate(orderCapture, srvCPDtlDto, action));
//					   srvCPDtlDto.setEffEndDate(this.calculateCallPlanEffEndDate(action, srvCPDtlDto.getEffStartDate(), srvCPDtlDto.getEffEndDate(), 
//							   					  									orderCapture.getLtsTerminateAppointmentForm().getAppntDate(), pSbOrder.getLastServiceInd()));
					   srvCPDtlDto.setEffStartDate(StringUtils.substring(srvCPDtlDto.getEffStartDate(), 0, 8));
					   srvCPDtlDto.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
					}
				};
			 
		 }
	}
	
	protected String calculateCallPlanEffEndDate(TerminateOrderCaptureDTO orderCapture, ServiceCallPlanDetailLtsDTO iddCallPlanProfileLts, String action){

		if (LtsBackendConstant.IDD_ACTION_REMOVE.equals(action)) {
			
			if (!LtsSbOrderHelper.isDeceasedCase(orderCapture.getLtsTerminateServiceSelectionForm())) {
				if(LtsConstant.CALL_PLAN_PEN_OTHER_PARTY_HNDL.equals(iddCallPlanProfileLts.getPenaltyHandling())){
					return StringUtils.substring(iddCallPlanProfileLts.getEffEndDate(), 0, 8);
				}
			}
			
			//check if effStartDate < today
			try {
				Date effStartDate = DateUtils.parseDate(StringUtils.substring(iddCallPlanProfileLts.getEffStartDate(), 0, 8) , new String[]{"yyyyMMdd"});
				Date currentDate = new Date();
				if (currentDate.before(effStartDate)) {
					return LocalDate.now().plusDays(2).toString(DateTimeFormat.forPattern("yyyyMMdd"));
				}
			}
			catch (Exception e) {
				throw new AppRuntimeException(e);
			}
			
			return LtsDateFormatHelper.reformatDate(orderCapture.getLtsTerminateAppointmentForm().getAppntDate(), "dd/MM/yyyy", "yyyyMMdd");
		}
		
		return StringUtils.substring(iddCallPlanProfileLts.getEffEndDate(), 0, 8);
	}
	
	private void modifyTerminateSalesInfo(TerminateOrderCaptureDTO orderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser){
		if(orderCapture.getLtsSalesInfoForm() == null){
			return;
		}
		
		setTerminateOrderSalesInfo(sbOrder, orderCapture, bomSalesUser);
		sbOrder.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
	}
	
	private void modifyTerminateRemark(TerminateOrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {
		if (orderCapture.getLtsTerminateAppointmentForm() == null) {
			return;
		}
		
		ServiceDetailDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		
		if (ltsServiceDetail.getRemarks() != null && ArrayUtils.isNotEmpty(ltsServiceDetail.getRemarks())) {
			for (RemarkDetailLtsDTO remarkDetailLts : ltsServiceDetail.getRemarks()) {
				if (!LtsConstant.REMARK_TYPE_ADD_ON_REMARK.equals(remarkDetailLts.getRmkType())) {
					continue;
				}
				remarkDetailLts.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			}
		}
		List<RemarkDetailLtsDTO> addonRemarkList = createTerminateAddonRemarkDetailLts(orderCapture);
		if (addonRemarkList != null && !addonRemarkList.isEmpty()) {
			for (RemarkDetailLtsDTO addonRemark : addonRemarkList) {
				ArrayUtils.add(ltsServiceDetail.getRemarks(), addonRemark);	
			}
		}
		
	}
	
	private void modifyTerminateAppointment(TerminateOrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {
		if (orderCapture.getLtsTerminateAppointmentForm() == null) {
			return;
		}
		CreateServiceType createServiceType = null;
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			
			createServiceType = CreateServiceType.CREATE_SRV_TYPE_DISCONNECT;
			AppointmentDetailLtsDTO newAppointmentDetailLts = createTerminateAppointmentDetailLts(createServiceType, orderCapture);
			
			if (newAppointmentDetailLts == null) {
				continue;
			}
			
			if (serviceDetail.getAppointmentDtl() != null) {
				newAppointmentDetailLts.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			}
			serviceDetail.setAppointmentDtl(newAppointmentDetailLts);
		}

		sbOrder.setSrvReqDate(orderCapture.getLtsTerminateAppointmentForm().getAppntDate());
		sbOrder.setBackDateInd(isBackDateOrder(orderCapture) ? "Y" : null);
		sbOrder.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		ltsServiceDetail.setDiscFiveNaInd(orderCapture.getLtsTerminateAppointmentForm().isFiveNa()? "Y" : "N");
		ltsServiceDetail.setCeaseRentalDate(orderCapture.getLtsTerminateAppointmentForm().getCeaseRentalDate());
		
		modifyExternalEqtCollection(ltsServiceDetail, orderCapture);
		
		ltsServiceDetail.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
	}
	
	private void modifyExternalEqtCollection(ServiceDetailLtsDTO srvDtlDto, TerminateOrderCaptureDTO orderCapture){
		
		String collectAddr = orderCapture.getLtsTerminateAppointmentForm().getAlternateAddressDetails();
		String existAddr = orderCapture.getLtsServiceProfile().getAddress().getFullAddress();
		
		srvDtlDto.setEqtCollectionAddr(orderCapture.getLtsTerminateAppointmentForm().isExternalCollection() ? collectAddr : existAddr);
		srvDtlDto.setExtEqtCollect(orderCapture.getLtsTerminateAppointmentForm().isExternalCollection() ? "Y" : "N");
		
	}
	
	private void modifyTerminateBillingInfo(TerminateOrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {
		
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			
			if (LtsSbOrderHelper.isImsService(serviceDetail)) {
				continue;
			}
			
			/*Billing Address update*/
			LtsTerminateBillingInfoFormDTO paymentForm = orderCapture.getLtsTerminateBillingInfoForm();
			
			if(paymentForm != null){
				String fullAddr = null;
				BillingAddressLtsDTO billingAddressLts = serviceDetail.getAccount().getBillingAddress();//new BillingAddressLtsDTO();
				
				if(billingAddressLts != null){
					if(paymentForm.isChangeBa()){
						fullAddr = paymentForm.getNewBillingAddress();
						serviceDetail.getAccount().setAcctName(paymentForm.getNewBillingName());
						serviceDetail.getAccount().setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
						billingAddressLts.setInstantUpdateInd(paymentForm.isInstantUpdateBa()? "Y" : "N");
						billingAddressLts.setAction(BaCaActionType.DIFFERENT_FROM_NEW_OLD_IA.getCode());
					}else{
						billingAddressLts.setAction(BaCaActionType.REMAIN_UNCHANGE.getCode());
						fullAddr = paymentForm.getBillingAddress();
					}
					
					if(StringUtils.isNotBlank(fullAddr)){
						String[] addrLines = fullAddr.split("\n");
						billingAddressLts.setAddrLine1(StringUtils.strip(addrLines[0]));
						billingAddressLts.setAddrLine2(addrLines.length > 1? StringUtils.strip(addrLines[1]) :null);
						billingAddressLts.setAddrLine3(addrLines.length > 2? StringUtils.strip(addrLines[2]) :null);
						billingAddressLts.setAddrLine4(addrLines.length > 3? StringUtils.strip(addrLines[3]) :null);
						billingAddressLts.setAddrLine5(addrLines.length > 4? StringUtils.strip(addrLines[4]) :null);
						billingAddressLts.setAddrLine6(addrLines.length > 5? StringUtils.strip(addrLines[5]) :null);
					}
					billingAddressLts.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
				}
			}			
		}
		
	}
	
	private void modifyTerminateAllOrdDocAssgnLts(TerminateOrderCaptureDTO orderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		
		AllOrdDocAssgnLtsDTO[] existingAllOrdDocAssgns = orderRetrieveLtsService.retrieveAllOrdDocAssgn(sbOrder.getOrderId()); 
		AllOrdDocAssgnLtsDTO[] newAllOrdDocAssgns = createTerminateAllOrdDocAssgnLts(orderCapture, bomSalesUser);
		List<AllOrdDocAssgnLtsDTO> allOrdDocAssgnList = new ArrayList<AllOrdDocAssgnLtsDTO>();
		
		if (ArrayUtils.isNotEmpty(existingAllOrdDocAssgns)) {
			for (AllOrdDocAssgnLtsDTO allOrdDocAssgnLts : existingAllOrdDocAssgns) {
				allOrdDocAssgnLts.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			}
			allOrdDocAssgnList.addAll(Arrays.asList(existingAllOrdDocAssgns));
		}
		if (ArrayUtils.isNotEmpty(newAllOrdDocAssgns)) {
			// Set Collected Ind
			if (ArrayUtils.isNotEmpty(existingAllOrdDocAssgns)) {
				for (AllOrdDocAssgnLtsDTO newAllOrdDocAssgn : newAllOrdDocAssgns) {
					for (AllOrdDocAssgnLtsDTO existingAllOrdDocAssgn : existingAllOrdDocAssgns) {
						if (StringUtils.equals(existingAllOrdDocAssgn.getDocType(), newAllOrdDocAssgn.getDocType())) {
							newAllOrdDocAssgn.setCollectedInd(existingAllOrdDocAssgn.getCollectedInd());	
						}
					}
				}	
			}
			allOrdDocAssgnList.addAll(Arrays.asList(newAllOrdDocAssgns));
		}
		
		sbOrder.setAllOrdDocAssgns(allOrdDocAssgnList.toArray(new AllOrdDocAssgnLtsDTO[allOrdDocAssgnList.size()]));
	}
	
}
