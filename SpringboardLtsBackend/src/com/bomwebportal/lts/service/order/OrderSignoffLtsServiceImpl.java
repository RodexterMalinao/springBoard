package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.CodeLkupDAO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.CsPortalIdRegrArqDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceActionTypeDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.CsPortalService;
import com.bomwebportal.lts.service.CustomerIguardApiService;
import com.bomwebportal.lts.service.LostModemService;
import com.bomwebportal.lts.service.bom.BomInvPreassgnService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.ServiceActionBase;
import com.bomwebportal.service.lts.OrderSignoffLtsService;
import com.pccw.util.spring.SpringApplicationContext;

public class OrderSignoffLtsServiceImpl implements OrderSignoffLtsService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected OrderRetrieveLtsService orderRetrieveLtsService = null;
	protected OrderStatusService orderStatusService = null;
	protected WorkQueueSubmissionService workQueueSubmissionService = null;
	protected ServiceActionTypeLookupService serviceActionTypeLookupService = null;
	private CodeLkupDAO wqPriorityonCodeLkupDao = null;
	protected OrderDetailService orderDetailService = null;
	private WorkQueueRemarkFactory workQueueRemarkFactory = null;
	protected SaveSbOrderLtsService saveSbOrderLtsService = null;
	protected CsPortalService csPortalService;
	protected BomInvPreassgnService bomInvPreassgnService;
	protected CustomerIguardApiService customerIguardApiService;
	protected LostModemService lostModemService;
	
	
    public void updateInvPreassgnJunctionMsg(SbOrderDTO sbOrder, String userId) {
    	bomInvPreassgnService.updateInvPreassgnJunctionMsg(sbOrder, userId);
    }
	
	public String regMyHktTheClub (SbOrderDTO pSbOrder, ServiceDetailLtsDTO pSrvDtl, String pUser){

		String emailAddr = null;
		ItemDetailLtsDTO cspItemDto = null;
		
		//CsPortal Registration
		for(ItemDetailLtsDTO cspItem : pSrvDtl.getItemDtls()){
			
			if(cspItem != null 
					&& ( StringUtils.equals(LtsBackendConstant.ITEM_TYPE_MYHKT_BILL, cspItem.getItemType())
							|| StringUtils.equals(LtsBackendConstant.ITEM_TYPE_HKT_THE_CLUB_BILL, cspItem.getItemType())
							|| StringUtils.equals(LtsBackendConstant.ITEM_TYPE_THE_CLUB_BILL, cspItem.getItemType())
					//&& cspItem.isSelected() 
					&& cspItem.getItemAttbs() != null))
			{
				emailAddr = pSrvDtl.getAccount().getCustomer().getCsPortalLogin();
				
				if(StringUtils.isEmpty(emailAddr)){
					emailAddr = pSrvDtl.getAccount().getCustomer().getTheClubLogin();
				}
//				for(ItemAttributeDetailLtsDTO itemAttb : cspItem.getItemAttbs()){
//					//if (StringUtils.equalsIgnoreCase(LtsBackendConstant.CHANNEL_ATTB_FORMAT_EMAIL, itemAttb.getInputFormat())){
//					if(StringUtils.contains(itemAttb.getAttbValue(), "@")){	
//						emailAddr = itemAttb.getAttbValue();
//						break;
//					}
//				}
				cspItemDto = cspItem;
				break;
			}
		}
			
				
				
				
		if(StringUtils.isNotBlank(emailAddr) && cspItemDto != null){
			boolean premier = StringUtils.isNotBlank(pSbOrder.getAddress().getAddrInventory().getHktPremier());
			String targetAcct = StringUtils.equals(LtsBackendConstant.ITEM_TYPE_THE_CLUB_BILL, cspItemDto.getItemType()) ? 
									LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY : (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_MYHKT_BILL, cspItemDto.getItemType()) ? 
				                		LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY : LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER);
			
			
			//Do not register myHKT if the email or mobile is dummy
			if(StringUtils.equals(targetAcct, LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY)
					&& ( StringUtils.contains(pSrvDtl.getAccount().getCustomer().getCsPortalLogin(), LtsBackendConstant.CSP_DUMMY_EMAIL_DOMAIN)
						 || StringUtils.contains(pSrvDtl.getAccount().getCustomer().getCsPortalMobile(),LtsBackendConstant.CSP_DUMMY_MOBILE_NUM))){
				logger.debug("regCsIdForTheClubAndHkt :: OrderId:" + pSbOrder.getOrderId() + " Dummy info, no registration.");
				return null;
			}else if(StringUtils.equals(targetAcct, LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER)
					&& ( StringUtils.contains(pSrvDtl.getAccount().getCustomer().getCsPortalLogin(), LtsBackendConstant.CSP_DUMMY_EMAIL_DOMAIN)
						 || StringUtils.contains(pSrvDtl.getAccount().getCustomer().getCsPortalMobile(),LtsBackendConstant.CSP_DUMMY_MOBILE_NUM))){
				targetAcct = LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY;
				logger.debug("regCsIdForTheClubAndHkt :: OrderId:" + pSbOrder.getOrderId() + " Dummy info, register the club only.");
			}
			
			CsPortalIdRegrArqDTO  cspRes = null;
			
			try{
				cspRes = (CsPortalIdRegrArqDTO )csPortalService.regCsIdForTheClubAndHkt(pSbOrder, pSbOrder.getStaffNum(), "SB", premier, targetAcct);
				
				if(cspRes != null){
					logger.debug("regCsIdForTheClubAndHkt :: OrderId:" + pSbOrder.getOrderId() + " oClubRes:" + cspRes.getoClubRes() + "oMyHktRes:" + cspRes.getoMyHktRes() );
					updateHktClubInd(pSbOrder, pSrvDtl, cspRes, cspItemDto, pUser);
				}else{
					logger.debug("regCsIdForTheClubAndHkt :: OrderId:" + pSbOrder.getOrderId() + "Response is NULL.");
				}
				
				StringBuilder sb = new StringBuilder();
				
				if (!LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS.equals(cspRes.getReply())) {
					sb.append(cspRes.getReply() + "\n");
				}
				if (StringUtils.isNotBlank(cspRes.getoMyHktResEnMsg())) {
					sb.append(cspRes.getoMyHktResEnMsg() + "\n");
				}
				
				if (StringUtils.isNotBlank(cspRes.getoClubResMsg())) {
					sb.append(cspRes.getoClubResMsg() + "\n");
				}
				if (sb.length() == 0) {
					return null;
				}
				return "[MyHKT / The Club Registration] " + sb.toString();	
				

			}catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
				updateHktClubInd(pSbOrder, pSrvDtl, cspRes, cspItemDto, pUser);
			}
		}
		return null;
}
	

	
	public void updateHktClubInd(SbOrderDTO pSbOrder, ServiceDetailLtsDTO pSrvDtl, CsPortalIdRegrArqDTO  cspRes, ItemDetailLtsDTO cspItem, String userId){
		
		
		CustomerDetailLtsDTO custDtl = pSrvDtl.getAccount().getCustomer();
		
		if(custDtl == null || cspItem == null){
			return;
		}
			
		if(cspRes == null || csPortalService.isCsldReplyFail(cspRes.getReply())){
			custDtl.setCsPortalInd(LtsBackendConstant.CSP_FAIL_REG);
			custDtl.setTheClubInd(LtsBackendConstant.CSP_FAIL_REG);
		}else{
			
			if(StringUtils.equals(cspItem.getItemType(), LtsBackendConstant.ITEM_TYPE_THE_CLUB_BILL)){
				//!club & hkt
				custDtl.setTheClubInd(StringUtils.equals(cspRes.getoClubRes(),LtsCsPortalBackendConstant.CSP_REPLY_OK)? LtsBackendConstant.CSP_REG : LtsBackendConstant.CSP_FAIL_REG);
				
			}else if(StringUtils.equals(cspItem.getItemType(), LtsBackendConstant.ITEM_TYPE_MYHKT_BILL)){
				//club & !hkt
				custDtl.setCsPortalInd(StringUtils.equals(cspRes.getoMyHktRes(),LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS)? LtsBackendConstant.CSP_REG : LtsBackendConstant.CSP_FAIL_REG);
				
			}else if(StringUtils.equals(cspItem.getItemType(), LtsBackendConstant.ITEM_TYPE_HKT_THE_CLUB_BILL)){
				//!club & !hkt
				if(StringUtils.isNotBlank(cspRes.getiClubLi())){
					custDtl.setTheClubInd(StringUtils.equals(cspRes.getoClubRes(),LtsCsPortalBackendConstant.CSP_REPLY_OK)? LtsBackendConstant.CSP_REG : LtsBackendConstant.CSP_FAIL_REG);
				}
				if(StringUtils.isNotBlank(cspRes.getiMyHktLi())){
					custDtl.setCsPortalInd(StringUtils.equals(cspRes.getoMyHktRes(),LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS)? LtsBackendConstant.CSP_REG : LtsBackendConstant.CSP_FAIL_REG);
				}
			}
		}
		
		custDtl.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		
		saveSbOrderLtsService.saveCustomer(custDtl, pSbOrder.getOrderId(), custDtl.getCustNo(), userId);
}
	public void setPaymentTermDate(SbOrderDTO pSbOrder, String pTermDate){
		
        ServiceDetailLtsDTO srvDtl = LtsSbHelper.getLtsService(pSbOrder);
		
		if(srvDtl == null){
			srvDtl = LtsSbHelper.getLtsEyeService(pSbOrder);
		}
		
        AccountDetailLtsDTO[] accounts = pSbOrder.getAccounts();
		
		for (int i=0; accounts!=null && i<accounts.length; ++i) {

			PaymentMethodDetailLtsDTO paymentMethod = srvDtl.getAccounts()[i].getAccount().getExistPayment();
						
			if(paymentMethod == null){
				return;				
			}
			if(StringUtils.equals(paymentMethod.getPayMtdType(), LtsBackendConstant.PAYMENT_TYPE_AUTO_PAY)
					&& StringUtils.isNotBlank(paymentMethod.getTermCd())){
				paymentMethod.setTermDate(pTermDate);
			}

	    }
}
	
	// For LTS online sale Work Queue use only, this signoff method is not a full signoff
	@Transactional
	public void signoffLtsOnlineSalesOrder(String pOrderId, String pUserId, String pShopCd) {
		
		this.orderDetailService.clearCustNotMatchInd(pOrderId);
		
		SbOrderDTO sbOrder = this.orderRetrieveLtsService.retrieveSbOrder(pOrderId, false);
		
		if (sbOrder == null) {
			throw new AppRuntimeException("SB order not found.");
		}				
		if (!verifySignoff(sbOrder)) {
			throw new AppRuntimeException("Invalid signoff status.");
		}
		this.preSubmit(sbOrder, pUserId);
		this.determineWorkQueueNatureForSignoff(sbOrder);
		this.submitToWorkQueue(sbOrder, pUserId, pShopCd);
		this.postSubmitSignoff(sbOrder, pUserId);
	}
	
	private void preSubmit(SbOrderDTO sbOrder, String userId) {
		this.initializeSignoff(sbOrder, userId);
		this.clearFollowupWorkQueue(sbOrder, userId);
		this.getServiceActions(sbOrder, userId);
		this.updateSuspendBomOrder(sbOrder, userId);
	}
	
	private void postSubmitSignoff(SbOrderDTO sbOrder, String userId) {
		this.updateSignoffStatus(sbOrder, userId);
		sbOrder.setSignOffDate(this.orderDetailService.updateSignoffDate(sbOrder.getOrderId()));
	}
	
	protected boolean verifySignoff(SbOrderDTO sbOrder) {
		
		OrderStatusDTO[] status = this.orderStatusService.getStatus(sbOrder.getOrderId());
		
		for (int i=0; status!=null && i<status.length; ++i) {
			if (!StringUtils.equals(LtsBackendConstant.ORDER_STATUS_SUSPENDED, status[i].getSbStatus())) {
				return false;
			}
		}
		return true;
	}
	
	protected void initializeSignoff(SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			this.orderStatusService.setPendingSignoffStatus(sbOrder.getOrderId(), srvDtls[i].getDtlId(), userId);
		}
	}
	
 	protected void clearFollowupWorkQueue(SbOrderDTO sbOrder, String userId) {
		this.workQueueSubmissionService.clearWorkQueue(sbOrder, "SB_SIGNOFF", userId);
	}
	
	protected void getServiceActions(SbOrderDTO sbOrder, String userId) {

		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		ServiceActionTypeDTO[] srvActions = null;

		for (int i = 0; srvDtls != null && i < srvDtls.length; ++i) {
			srvActions = this.serviceActionTypeLookupService.getServiceActionType(srvDtls[i], sbOrder);
			
			if (ArrayUtils.isEmpty(srvActions)) {
				continue;
			}
			
			srvDtls[i].setSrvActions(srvActions);
			
			for (int j=0; j<srvActions.length; ++j) {
				if (srvActions[j] == null) {
					continue;
				}
				if (StringUtils.isNotBlank(srvActions[j].getSuspendReaCdBom())) {
					srvDtls[i].setSuspendBomInd(LtsBackendConstant.SUSPEND_IND_BY_DTL);
					srvDtls[i].setSuspendBomReasonCd(srvActions[j].getSuspendReaCdBom());
				}
			}
		}
		
		ServiceDetailLtsDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailOtherLtsDTO imsEyeSrv = LtsSbHelper.getImsEyeService(srvDtls);
		
		if (imsEyeSrv == null) {
			return;
		}
		ServiceActionTypeDTO[] imsEyeActions = this.serviceActionTypeLookupService.getServiceActionType(imsEyeSrv, ltsService.getFromProd(), ltsService.getToProd());
		
		for (int i=0; imsEyeActions!=null && i<imsEyeActions.length; ++i) {
			imsEyeSrv.addSrvAction(imsEyeActions[i]);
			
			if (StringUtils.isNotBlank(imsEyeActions[i].getSuspendReaCdBom())) {
				imsEyeSrv.setSuspendBomInd(LtsBackendConstant.SUSPEND_IND_BY_DTL);
				imsEyeSrv.setSuspendBomReasonCd(imsEyeActions[i].getSuspendReaCdBom());
			}
		}
	}
	
	protected void updateSuspendBomOrder(SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		ServiceActionBase serviceDetailService = SpringApplicationContext.getBean("serviceDetailService");
		for (int i=0; i<srvDtls.length; ++i) {
			if (StringUtils.isNotBlank(srvDtls[i].getSuspendBomReasonCd())) {
				srvDtls[i].setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
				serviceDetailService.doSave(srvDtls[i], userId, sbOrder.getOrderId(), null);
			}
		}
	}
	
	protected void determineWorkQueueNatureForSignoff(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			srvDtls[i].setWorkQueueType(determineWorkQueueNatureForSignoff(srvDtls[i]));
		}
	}
	
	public String determineWorkQueueNatureForSignoff(ServiceDetailDTO pSrvDtl) {
		
		ServiceActionTypeDTO[] srvActions = pSrvDtl.getSrvActions();
		int wqPriority = 99;
		int curPriority;
		
		for (int i=0; srvActions!=null && i<srvActions.length; ++i) {
			if (srvActions[i] == null) {
				continue;
			}
			if (StringUtils.isNotBlank(srvActions[i].getWqSubtype()) && wqPriority > (curPriority=this.getWqPriority(srvActions[i].getWqSubtype()))) {
				wqPriority = curPriority;
			}
		}
		try {
			LookupItemDTO[] wqPrior = this.wqPriorityonCodeLkupDao.getCodeLkup();
			
			for (int i=0; i<wqPrior.length; ++i) {
				if (StringUtils.equals(String.valueOf(wqPriority), (String)wqPrior[i].getItemValue())) {
					return wqPrior[i].getItemKey();
				}
			}
		} catch (DAOException ex) {}
		
		return null;
	}
	
	private int getWqPriority(String pAction) {
		
		try {
			LookupItemDTO[] wqPrior = this.wqPriorityonCodeLkupDao.getCodeLkup();
			
			for (int i=0; i<wqPrior.length; ++i) {
				if (StringUtils.equals(pAction, wqPrior[i].getItemKey())) {
					return Integer.parseInt((String)wqPrior[i].getItemValue());
				}
			}
		} catch (DAOException ex) {}
		return 99;
	}
	
	protected void submitToWorkQueue(SbOrderDTO sbOrder, String userId, String shopCd) {
		
		ServiceDetailDTO[] wqSrvs = this.getRequiredWorkQueueServices(sbOrder);
		
		if (ArrayUtils.isEmpty(wqSrvs)) {
			return;
		}
		String[] standardWqRemarks = null;
		String[] faxSerialsRemarks = null;
		String[] fullWqRemarks = null;
		boolean isFullWq = false;
		for (int i=0; i < wqSrvs.length; ++i) {
			if (LtsBackendConstant.WQ_TYPE_FULL.equals(wqSrvs[i].getWorkQueueType())) {
				isFullWq = true;
			}
		}
		
		for (int i=0; i < wqSrvs.length; ++i) {
			
			if (LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType()) && isFullWq) {
				standardWqRemarks = this.workQueueRemarkFactory.generateDisconnectFullWqRemark(sbOrder);
			}
			else {
				standardWqRemarks = WorkQueueRemarkFactory.generateStandardWqRemark(sbOrder);
			}
			wqSrvs[i].setWqRemarks(standardWqRemarks);
			
			faxSerialsRemarks = this.workQueueRemarkFactory.generateFaxSerialRemark(sbOrder);	
			if (ArrayUtils.isNotEmpty(faxSerialsRemarks)) {
				wqSrvs[i].setWqRemarks(LtsSbHelper.concatArray(wqSrvs[i].getWqRemarks(), faxSerialsRemarks));
			}
			
			if (isFullWq) {
				fullWqRemarks = this.workQueueRemarkFactory.generateFullWqRemark(sbOrder);
				if (ArrayUtils.isNotEmpty(fullWqRemarks)) {
					wqSrvs[i].setWqRemarks(LtsSbHelper.concatArray(wqSrvs[i].getWqRemarks(), fullWqRemarks));
				}
			}
		}
		this.workQueueSubmissionService.submitToWorkQueue(sbOrder, wqSrvs, userId, shopCd);
	}
	
	private ServiceDetailDTO[] getRequiredWorkQueueServices(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		List<ServiceDetailDTO> srvDtlList = new ArrayList<ServiceDetailDTO>();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			if (StringUtils.isNotEmpty(srvDtls[i].getWorkQueueType())) {
				srvDtlList.add(srvDtls[i]);
			} else if (StringUtils.equals(LtsBackendConstant.WQ_TYPE_PORT_IN, srvDtls[i].getWorkQueueType())) {
				ServiceActionTypeDTO[] actions = srvDtls[i].getSrvActions();
				
				for (int j=0; actions != null && j<actions.length; ++j) {
					if (actions[j] == null) {
						continue;
					}
					
					if (StringUtils.equals(LtsBackendConstant.WQ_TYPE_PORT_IN, actions[j].getWqSubtype())) {
						srvDtls[i].setSrvActions(new ServiceActionTypeDTO[] {actions[j]});
						break;
					}
				}
				srvDtlList.clear();
				srvDtlList.add(srvDtls[i]);
				break;
			}
		}
		return srvDtlList.toArray(new ServiceDetailDTO[srvDtlList.size()]);
	}
	
	protected void updateSignoffStatus (SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailLtsDTO ltsSrv = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailOtherLtsDTO eyeImsSrv = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());

		if (StringUtils.equals(LtsBackendConstant.WQ_TYPE_OL_CUST_INFO, ltsSrv.getWorkQueueType())
				|| (eyeImsSrv != null && StringUtils.equals(LtsBackendConstant.WQ_TYPE_OL_CUST_INFO, eyeImsSrv.getWorkQueueType()))) {
			this.orderStatusService.setSuspendStatus(sbOrder.getOrderId(), ltsSrv.getDtlId(), null, userId, ltsSrv.getWorkQueueType());
			if (eyeImsSrv != null) {
				this.orderStatusService.setSuspendStatus(sbOrder.getOrderId(), eyeImsSrv.getDtlId(), null, userId, eyeImsSrv.getWorkQueueType());	
			}
		} else if (StringUtils.equals(LtsBackendConstant.WQ_TYPE_FULL, ltsSrv.getWorkQueueType()) || StringUtils.equals(LtsBackendConstant.WQ_TYPE_PORT_IN, ltsSrv.getWorkQueueType())
				|| (eyeImsSrv != null && StringUtils.equals(LtsBackendConstant.WQ_TYPE_FULL, eyeImsSrv.getWorkQueueType()))) {
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), ltsSrv.getDtlId(), userId, ltsSrv.getWorkQueueType());
			if (eyeImsSrv != null) {
				this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), eyeImsSrv.getDtlId(), userId, eyeImsSrv.getWorkQueueType());	
			}
		} else {
			this.orderStatusService.setSubmissionStatus(sbOrder.getOrderId(), ltsSrv.getDtlId(), userId, ltsSrv.getWorkQueueType());
			if (eyeImsSrv != null) {
				this.orderStatusService.setSubmissionStatus(sbOrder.getOrderId(), eyeImsSrv.getDtlId(), userId, eyeImsSrv.getWorkQueueType());	
			}
		}
	}
	
	protected void setWqNatureRemark(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		ServiceActionTypeDTO[] actions = null;
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			actions = srvDtls[i].getSrvActions();

			for (int j=0; actions!=null && j<actions.length; ++j) {
				
				if(actions[j] == null) {
					continue;
				}
				
				actions[j].setWqNatureRemarks(this.workQueueRemarkFactory.generateWqRemark(sbOrder, srvDtls[i], actions[j].getWqNatureId(), actions[j].getWqSubtype()));
			}
		}
	}
	
	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}
	
	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}
	
	public WorkQueueSubmissionService getWorkQueueSubmissionService() {
		return workQueueSubmissionService;
	}

	public void setWorkQueueSubmissionService(
			WorkQueueSubmissionService workQueueSubmissionService) {
		this.workQueueSubmissionService = workQueueSubmissionService;
	}
	
	public ServiceActionTypeLookupService getServiceActionTypeLookupService() {
		return serviceActionTypeLookupService;
	}

	public void setServiceActionTypeLookupService(
			ServiceActionTypeLookupService serviceActionTypeLookupService) {
		this.serviceActionTypeLookupService = serviceActionTypeLookupService;
	}
	
	public CodeLkupDAO getWqPriorityonCodeLkupDao() {
		return wqPriorityonCodeLkupDao;
	}

	public void setWqPriorityonCodeLkupDao(CodeLkupDAO wqPriorityonCodeLkupDao) {
		this.wqPriorityonCodeLkupDao = wqPriorityonCodeLkupDao;
	}
	
	public OrderDetailService getOrderDetailService() {
		return orderDetailService;
	}

	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	public WorkQueueRemarkFactory getWorkQueueRemarkFactory() {
		return workQueueRemarkFactory;
	}

	public void setWorkQueueRemarkFactory(
			WorkQueueRemarkFactory workQueueRemarkFactory) {
		this.workQueueRemarkFactory = workQueueRemarkFactory;
	}

	public SaveSbOrderLtsService getSaveSbOrderLtsService() {
		return saveSbOrderLtsService;
	}

	public void setSaveSbOrderLtsService(SaveSbOrderLtsService saveSbOrderLtsService) {
		this.saveSbOrderLtsService = saveSbOrderLtsService;
	}

	public CsPortalService getCsPortalService() {
		return csPortalService;
	}

	public void setCsPortalService(CsPortalService csPortalService) {
		this.csPortalService = csPortalService;
	}
	public BomInvPreassgnService getBomInvPreassgnService() {
		return bomInvPreassgnService;
	}

	public void setBomInvPreassgnService(BomInvPreassgnService bomInvPreassgnService) {
		this.bomInvPreassgnService = bomInvPreassgnService;
	}

	public CustomerIguardApiService getCustomerIguardApiService() {
		return customerIguardApiService;
	}

	public void setCustomerIguardApiService(
			CustomerIguardApiService customerIguardApiService) {
		this.customerIguardApiService = customerIguardApiService;
	}

}
