package com.bomwebportal.ims.service;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustomerBasicInfoDTO;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.ImsAlreadySignOffException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.GetImsCustomerDAO;
import com.bomwebportal.ims.dao.ImsOrderDAO;
import com.bomwebportal.ims.dao.ImsSupportDocDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsSupportDocDTO;
import com.bomwebportal.ims.dto.ui.AcctUI;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.ComponentUI;
import com.bomwebportal.ims.dto.ui.ContactUI;
import com.bomwebportal.ims.dto.ui.CustomerUI;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.RemarkUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.dao.ImsOrderDocumentDAO;
import com.bomwebportal.ims.dto.AddrInventoryDTO;
import com.bomwebportal.ims.dto.BasketDetailsDTO;
import com.bomwebportal.ims.dto.ComponentDTO;
import com.bomwebportal.ims.dto.CustAddrDTO;
import com.bomwebportal.ims.dto.CustomerDTO;
import com.bomwebportal.ims.dto.ImsAllOrdDocWaiveDTO;
import com.bomwebportal.ims.dto.GetCOrderDTO;
import com.bomwebportal.ims.dto.NtvReplaceSelfPickHddDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.OrderImsSystemFindingDTO;
import com.bomwebportal.ims.dto.SubscribedChannelDTO;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.SubscribedItemDTO;
import com.bomwebportal.dto.BomwebSubscribedOfferImsDTO;
import com.bomwebportal.dto.CareCashRegDTO;
import com.bomwebportal.ims.interceptor.ImsCommonInterceptor;
import com.bomwebportal.service.ClubHktService;
import com.bomwebportal.service.MobilePINService;
import com.bomwebportal.service.MobilePINServiceImpl.MobileOffer;
import com.google.gson.Gson;

public class ImsOrderDetailServiceImpl implements ImsOrderDetailService{

	private Gson gson = new Gson();
	protected final Log logger = LogFactory.getLog(getClass());
	private MobilePINService mobilePINService;
	
	
	public MobilePINService getMobilePINService() {
		return mobilePINService;
	}
	public void setMobilePINService(MobilePINService mobilePINService) {
		this.mobilePINService = mobilePINService;
	}
	private ImsSignOffLogService signOffLogService;
	public void setSignOffLogService(ImsSignOffLogService signOffLogService) {
		this.signOffLogService = signOffLogService;
	}
	public ImsSignOffLogService getSignOffLogService() {
		return signOffLogService;
	}
	private ImsOrderDAO orderDao;
	
	private GetImsCustomerDAO getImsCustomerDAO;
	
	public GetImsCustomerDAO getGetImsCustomerDAO() {
		return getImsCustomerDAO;
	}

	public void setGetImsCustomerDAO(GetImsCustomerDAO getImsCustomerDAO) {
		this.getImsCustomerDAO = getImsCustomerDAO;
	}

	//Gary
	private ClubHktService clubHktService;
	
	private ImsReportService service;
	
	public ImsReportService getService() {
		return service;
	}

	public void setService(ImsReportService service) {
		this.service = service;
	}

    private ImsBasketDetailsService basketService;
	
	private ImsOrderService orderService;

	private COrderService cOrderService; //Tony added
	//	add by steven
	private ImsSupportDocDAO imsSupportDocDAO;
	
	public ImsSupportDocDAO getImsSupportDocDAO() {
		return imsSupportDocDAO;
	}

	public void setImsSupportDocDAO(ImsSupportDocDAO imsSupportDocDAO) {
		this.imsSupportDocDAO = imsSupportDocDAO;
	}
	private ImsOrderDocumentDAO orderDocumentDao;

	public ImsOrderDocumentDAO getOrderDocumentDao() {
		return orderDocumentDao;
	}

	public void setOrderDocumentDao(ImsOrderDocumentDAO orderDocumentDao) {
		this.orderDocumentDao = orderDocumentDao;
	}

	public ImsSupportDocDTO get_PayMtd_ccNum_DisMode(String orderId){

		try {
			return imsSupportDocDAO.getImsSupportDoc(orderId);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
		
	}
	public List<ImsAllOrdDocWaiveDTO> getWaiveReasonList(String docType, String DisMode) {
		try {
			return this.orderDocumentDao.getWaiveReasonList(docType, DisMode);
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getWaiveReasonList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	//	add by steven end
	public String isImsShowChangeLang(){
		try {
			return imsSupportDocDAO.isImsShowChangeLang();
		} catch (DAOException e) {
			logger.error("Fail in isImsShowChangeLang()", e);			
		}
		return "N";
	}
	public OrderImsUI signOffOrder(OrderImsUI order) throws ImsAlreadySignOffException{
		signOffLogService.signOffOrderLog(order, "StartSignOff", null);
		logger.info("signOffOrder:"+order.getOrderId());
		logger.info("order status:"+order.getOrderStatus());
		System.out.println("signOffOrder:"+order.getOrderId());
		System.out.println("order status:"+order.getOrderStatus());
		
		if(order.getSignOffDate() == null){//by jacky 20141124
			order.setSignOffDate(orderService.getApplicationDate());	
		}
		
		if ( "Y".equals(order.getIsCC()) )
		{
			order.setShopCd(null);
		}
		
		if(!"Y".equals(order.isPending())){
			logger.info("order not in pending status["+order.getOrderStatus()+"]");
			throw new ImsAlreadySignOffException();
		}
		
		OrderImsUI savedOrder;
		String orderStatus;				
		String isNowTV = "N";
		String CustProfileMissMatch = "F";
		CustProfileMissMatch = order.getCustomer().getExistingCustomerConflictWithName();
		logger.info("DiffName:"+CustProfileMissMatch);
		if(order.getProcessVim()!=null){
			 isNowTV="Y";
		}else{
			 isNowTV="N";
		}
	
		
		logger.info("isNowTV:"+isNowTV);
		//GetCOrderDTO cOrderDTO = cOrderService.getCOrder("Y", isNowTV, order.getInstallAddress().getAddrInventory().getTechnology(), order.getCustomer().getCustNo(), order.getInstallAddress().getSerbdyno(), order.getInstallAddress().getUnitNo(), order.getInstallAddress().getFloorNo());
//		GetCOrderDTO cOrderDTO = order.getcOrderDTO();
//		
//		if (cOrderDTO == null){
//			cOrderDTO = cOrderService.getCOrder("Y", isNowTV, order.getInstallAddress().getAddrInventory().getTechnology(), order.getCustomer().getCustNo(), order.getInstallAddress().getSerbdyno(), order.getInstallAddress().getUnitNo(), order.getInstallAddress().getFloorNo());
//		}
//		
//		logger.info("create_c_order:"+cOrderDTO.getO_create_c_order()+"  reason:"+cOrderDTO.getO_reason()+"  related_fsa:"+cOrderDTO.getO_related_fsa());
		
		order.setSubscribedOffersIms(null);
		
		BomwebSubscribedOfferImsDTO homeNetworkBackend = new BomwebSubscribedOfferImsDTO();
		
		homeNetworkBackend = orderService.getHomeNetworkRequired(order.getInstallAddress().getSerbdyno(), order.getInstallAddress().getUnitNo(), order.getInstallAddress().getFloorNo(), order.getInstallAddress().getAddrInventory().getTechnology());
		
		if(homeNetworkBackend!=null&&"I".equalsIgnoreCase(homeNetworkBackend.getIoInd())&&!homeNetworkBackend.getOfferCd().isEmpty()){
			
			homeNetworkBackend.setImsserviceType("PCD");
			homeNetworkBackend.setCreateBy("BACKEND");
			
			BomwebSubscribedOfferImsDTO[] hnbackend = new BomwebSubscribedOfferImsDTO[1];
			
			BomwebSubscribedOfferImsDTO temp = new BomwebSubscribedOfferImsDTO();
			try {
				BeanUtils.copyProperties(temp, homeNetworkBackend);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hnbackend[0]=temp;
			
			order.setSubscribedOffersIms(hnbackend);
		}
		
		
		if("DS".equals(order.getImsOrderType()) &&
				"M".equals(order.getCustomer().getAccount().getPayment().getPayMtdType()) &&
				"N".equals(order.getCashFsPrepay()) &&
				!"Y".equals(order.getWaivedPrepay()) &&
				!ImsConstants.IMS_ORDER_STATUS_SIGNOFF_AWAIT_CASH.equals(order.getOrderStatus())						
				){
			logger.info("1st occasion: "+order.getOrderStatus());
			savedOrder = suspendOrder(order, ImsConstants.IMS_ORDER_AWAIT_CASH); //tmp	
			
			orderService.acqOrdBackendOfferChange(order.getOrderId());
			
			return savedOrder;
		}else if("Y".equals(CustProfileMissMatch) && "Y".equals(order.getRide_on_FSA_Ind()) && StringUtils.equals(order.getImsOrderType(), ImsCommonInterceptor.IMS_ORDER_TYPE_DS)){
			logger.info("both CustProfileMissMatch and c order");
			order.setWQsubmmitted("Y");
			order.getCustomer().setServiceNum(order.getRelated_FSA());
				savedOrder = suspendOrder(order, ImsConstants.WQ_Reason_both_cOrder_MissMatch+order.getRide_on_FSA_reason_Cd());
				releaseMrtPin(order);				
				if(!"Y".equals(order.getIsCC()) &&
						"M".equals(order.getCustomer().getAccount().getPayment().getPayMtdType()) &&
						"N".equals(order.getCashFsPrepay()) )
				preGenAccount(order);			
					
		}else if("Y".equals(CustProfileMissMatch) && StringUtils.equals(order.getImsOrderType(), ImsCommonInterceptor.IMS_ORDER_TYPE_DS)){//diff name
			logger.info("CustProfileMissMatch wq");

			order.setWQsubmmitted("Y");
				savedOrder = suspendOrder(order, ImsConstants.WQ_Reason_MissMatch);
				releaseMrtPin(order);	

		}else if("Y".equals(order.getRide_on_FSA_Ind())){//c order ride on FSA
			logger.info("c order wq");
			order.setSrc("CO");			
			order.setWQsubmmitted("Y");
			order.getCustomer().setServiceNum(order.getRelated_FSA());
			if("Y".equalsIgnoreCase(order.getIsPT())){
				savedOrder = suspendOrder(order, ImsConstants.WQ_Reason_cOrder_PT+order.getRide_on_FSA_reason_Cd());
			}else{
				savedOrder = suspendOrder(order, ImsConstants.WQ_Reason_cOrder+order.getRide_on_FSA_reason_Cd());
			} 
			releaseMrtPin(order);
			if(!"Y".equals(order.getIsCC()) &&
					"M".equals(order.getCustomer().getAccount().getPayment().getPayMtdType()) &&
					"N".equals(order.getCashFsPrepay()) )
			preGenAccount(order);
		}else{ 
			logger.info("last occation signoff");
			orderStatus = ImsConstants.IMS_ORDER_STATUS_SIGNOFF;
			if(order.getOrderStatus()==null){
				order.setOrderStatus(orderStatus);
				savedOrder = orderService.saveNewOrder(order);
			}else{						
				order.setOrderStatus(orderStatus);
				savedOrder = orderService.updateOrder(order);
			}	
			
			savedOrder.setOrderActionInd("R");
		}
		
		genQCandBlackListAddrWQ(savedOrder);
		
		if(order.getSubscribedItems()!=null){
			for(SubscribedItemUI subItem:order.getSubscribedItems()){
				if(subItem.getType().equalsIgnoreCase("PROM_GIFT")){
					try {
						orderDao.updatePromoGiftQuota(subItem.getId());
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		orderService.acqOrdBackendOfferChange(order.getOrderId());

		logger.info(order.getOrderId()+" savedOrder.getCustomer(): " + gson.toJson(savedOrder.getCustomer()));

			if ( ("Y".equalsIgnoreCase(order.getCustomer().getCsPortalInd()))||("Y".equalsIgnoreCase(order.getCustomer().getTheClubInd()))){
				
				logger.info("order.getAppMethodDesc():"+order.getAppMethodDesc());
				String mobilenum = savedOrder.getCustomer().getCsPortalMobile();
				if (mobilenum==null||mobilenum.length()==0){
					mobilenum = savedOrder.getCustomer().getTheClubMoblie();
				}
				
				String iPremier = "N";
				
				if("Y".equalsIgnoreCase(order.getIsPT())){
					iPremier = "Y";
				}
				
				String serviceNum = "";
				String hktId = savedOrder.getCustomer().getCsPortalLogin();
				String clubId = savedOrder.getCustomer().getTheClubLogin();
				
				if(hktId!=null&&hktId.length()>0&&clubId!=null&&clubId.length()>0){
					serviceNum = hktId.toLowerCase().substring(0, hktId.toLowerCase().indexOf('@'));
				}else if(clubId!=null&&clubId.length()>0){
					serviceNum = clubId.toLowerCase().substring(0, clubId.toLowerCase().indexOf('@'));
				}else{
					serviceNum = "";
				}
				
				clubHktService.reg(
						savedOrder.getCustomer().getIdDocType(), 
						savedOrder.getCustomer().getIdDocNum(), 
						iPremier, 
						("Y".equals(order.getIsCC())?order.getAppMethodDesc():savedOrder.getSalesChannel()), 
						("Y".equals(order.getIsCC())?savedOrder.getSourceCd():savedOrder.getShopCd()), //teamCd
						savedOrder.getSalesCd(), //staffId
						hktId, 
						clubId, 
						mobilenum, 
						serviceNum, 
						"ENG".equalsIgnoreCase(savedOrder.getLangPreference())?"en":"zh", 
						"SB", //system
						savedOrder.getOrderId(), 
						savedOrder.getCustomer().getTitle()+savedOrder.getCustomer().getLastName(), //nickName 
						savedOrder.getCustomer().getTheClubOptOutInd(), 
						savedOrder.getCustomer().getOptoutTheClubReason(), 
						savedOrder.getCustomer().getOptoutTheClubRmk(), 
						savedOrder.getCustomer().getCsPortalOptOutInd());
				
			}

			Boolean checkIsRetail = false;
			if ("Y".equals(order.getIsCC()) || "Y".equals(order.getIsPT()) || "DS".equals(savedOrder.getImsOrderType())){
				
			}else
				checkIsRetail = true;
				
			
			String careCashRegStatus = null;
			String careCashRtnMsg = null;
			CareCashRegDTO regCareCashresult = null;
			if(StringUtils.isNotBlank(savedOrder.getCustomer().getCareCashInd())){
				String ind = savedOrder.getCustomer().getCareCashInd();
				Date current = new Date();
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String timeStamp = dateFormat.format(current);
				if(!ind.equals("X")&&!ind.equals("A")){					
					regCareCashresult = clubHktService.regCareCash(
							savedOrder.getCustomer().getIdDocType(), 
							savedOrder.getCustomer().getIdDocNum(), 
							ind,
							savedOrder.getOrderId(), 
							checkIsRetail?savedOrder.getShopCd():(StringUtils.isNotBlank(savedOrder.getSalesChannel())?savedOrder.getSalesChannel():savedOrder.getShopCd()),
							savedOrder.getCustomer().getTitle()+" "+savedOrder.getCustomer().getLastName()+" "+savedOrder.getCustomer().getFirstName(),
							savedOrder.getCustomer().getCareCashEmail(), 
							savedOrder.getCustomer().getCareCashMobile(), 
							savedOrder.getCustomer().getDob().toString().replaceAll("-", ""), 
							savedOrder.getCustomer().getCareCashOptOutInd(), 
							"ENG".equalsIgnoreCase(savedOrder.getLangPreference())?"en":"zh", 
							0,0,
							1,
							0,
							("P").equalsIgnoreCase(ind)?timeStamp:null);
					if(regCareCashresult!=null && regCareCashresult.getReply()!=null){
						if(regCareCashresult.getReply().equalsIgnoreCase("RC_SUCC"))						
							careCashRegStatus = "S";						
						else
							careCashRegStatus = "F";
						
						careCashRtnMsg = gson.toJson(regCareCashresult);
					}
				}
				else
					careCashRegStatus = "N";					
				savedOrder.getCustomer().setCareCashRegStatus(careCashRegStatus);
				savedOrder.getCustomer().setCareCashRtnMsg(careCashRtnMsg);
				logger.info("care cash update celia: "+gson.toJson(savedOrder.getCustomer()));
				try {
					orderDao.updateBomwebCustIguardReg(savedOrder.getCustomer());
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			signOffLogService.signOffOrderLog(order, "FinishSignOff", null);
		return savedOrder;
		
//      commented by Tony		
//		if(orderService.isCreateChangeOrder(order)){
//			order.setSrc("CO");			
//			orderService.submitChangeOrderWQ(order);
//			orderStatus = ImsConstants.IMS_ORDER_STATUS_FULL_WQ;
//		}else{
//			orderStatus = ImsConstants.IMS_ORDER_STATUS_SIGNOFF;
//		}
//		
//		if(order.getOrderStatus()==null){
//						
//			//order.setCreateBy(user);
//			order.setOrderStatus(orderStatus);
//			savedOrder = orderService.saveNewOrder(order);
//			
//		}else{						
//			
//			//order.setLastUpdBy(user);
//			order.setOrderStatus(orderStatus);
//			savedOrder = orderService.updateOrder(order);
//			
//		}				
//		
//		savedOrder.setOrderActionInd("R");
//		
//		return savedOrder;
		
	}
	public void genQCandBlackListAddrWQ(OrderImsUI savedOrder) {
		if("DS".equals(savedOrder.getImsOrderType())){
			if("1".equals(savedOrder.getDsWaiveQC())){
				orderService.dsAlertWQ(savedOrder, ImsConstants.DS_Alert_WQ_waive_QC_1, "Waive QC WQ Deaf","");
			}else if("2".equals(savedOrder.getDsWaiveQC())){
				orderService.dsAlertWQ(savedOrder, ImsConstants.DS_Alert_WQ_waive_QC_2, "Waive QC WQ Mute","");
			}else if("3".equals(savedOrder.getDsWaiveQC())){
				orderService.dsAlertWQ(savedOrder, ImsConstants.DS_Alert_WQ_waive_QC_3, "Waive QC WQ Foreign Language","");
			}else if("4".equals(savedOrder.getDsWaiveQC())){
				orderService.dsAlertWQ(savedOrder, ImsConstants.DS_Alert_WQ_waive_QC_4, "Waive QC WQ VIP","");
			}
			
			logger.info("savedOrder.getInstallAddress().getBlacklistInd():"+savedOrder.getInstallAddress().getBlacklistInd());
//			savedOrder.getInstallAddress().setBlacklistInd("Y");
			if("Y".equals(savedOrder.getInstallAddress().getBlacklistInd())){
				orderService.dsAlertWQ(savedOrder, ImsConstants.DS_Alert_WQ_black_list_addr, "Black List Addres WQ","");
			}
		}
	}
	
	public void releaseMrtPin(OrderImsUI order){
		if (order.getSubscribedCslOffers() != null){
			for(SubscribedCslOfferDTO i:order.getSubscribedCslOffers()){
				if(i.getVas_parm_a()!= null && !("").equals(i.getVas_parm_a()) && i.getVas_parm_b()!= null && !("").equals(i.getVas_parm_b())){
					MobileOffer imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(i.getVas_parm_a(), i.getVas_parm_b(), order.getCreateBy(), order.getOrderId(), "NONONLINE");
					logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);								
				}
			}
		}
	}
	public void preGenAccount(OrderImsUI order){
		try {
			orderDao.insertPreGenAccount(order);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public OrderImsUI suspendOrder(OrderImsUI order, String reasonCd) throws ImsAlreadySignOffException{
		
		
		logger.info("suspendOrder:"+order.getOrderId());
		//logger.info("order id:"+order.getOrderId());
		logger.info("order status:"+order.getOrderStatus());
		
		logger.info("Suspend reason code:"+reasonCd);

		
		/*if(service.isDBSignOffEd(order.getOrderId())){
			throw new ImsAlreadySignOffException();
		}*/
		//jacky 20141211
		if(!"Y".equals(order.isPending())){
			logger.info("order not in pending status["+order.getOrderStatus()+"]");
			throw new ImsAlreadySignOffException();
		}
		
		order.setReasonCd(reasonCd);
		if ( "Y".equals(order.getIsCC()) )
		{
			order.setShopCd(null);
		}
		
		OrderImsUI retOrder;
		String orderStatus;
		String tmpreasonCd="";
		
		if(reasonCd.indexOf("_PT")!=-1){
			order.setReasonCd(reasonCd.replace("_PT", "").substring(0, 4));
		}else{ 
			order.setReasonCd((reasonCd).substring(0, 4));
		}
		
		//Tony added
		String COrder_reason="";
		if(reasonCd.length()>4&&reasonCd.indexOf("_PT")!=-1){
			COrder_reason = reasonCd.substring(7, reasonCd.length());
			tmpreasonCd = reasonCd.substring(0, 7);
		}else if (reasonCd.length()>4&&reasonCd.indexOf("_PT")==-1){
			COrder_reason = reasonCd.substring(4, reasonCd.length());
			tmpreasonCd = reasonCd.substring(0, 4);
		}else{
			tmpreasonCd = reasonCd;
		}
		
		if(tmpreasonCd.equals(ImsConstants.IMS_ORDER_AWAIT_CASH)){
			orderStatus = ImsConstants.IMS_ORDER_STATUS_SIGNOFF_AWAIT_CASH;
		}else if(tmpreasonCd.equals(ImsConstants.IMS_ORDER_CASH_REQ) ||
				tmpreasonCd.equals(ImsConstants.IMS_ORDER_WAIVE_PREPAY) ||
					tmpreasonCd.equals(ImsConstants.IMS_ORDER_WAIVE_OTC) ||
						tmpreasonCd.equals(ImsConstants.IMS_ORDER_DISCOUNTED_OTC)){
			orderStatus = ImsConstants.IMS_ORDER_STATUS_WAITING_APPROVAL;
		}else if(tmpreasonCd.equals(ImsConstants.WQ_Reason_cOrder) ||
					tmpreasonCd.equals(ImsConstants.WQ_Reason_cOrder_PT)){
			order.setSrc("CO");			
			orderStatus = ImsConstants.IMS_ORDER_STATUS_FULL_WQ;
		}else if(tmpreasonCd.equals(ImsConstants.WQ_Reason_MissMatch) ||
					tmpreasonCd.equals(ImsConstants.WQ_Reason_both_cOrder_MissMatch)){
			orderStatus = ImsConstants.IMS_ORDER_STATUS_FULL_WQ_MISMATCH;
		}else{
			orderStatus = ImsConstants.IMS_ORDER_STATUS_SUSPENDED;
		}
		
		if(order.getOrderStatus()==null){
			
			order.setOrderStatus(orderStatus);			
			if(order.getOrderId()==null){				
				order.setOrderId(getNewOrderId("Y".equals(order.getIsCC())?order.getChannelCd():order.getShopCd(),order.getCreateBy()));
			}			
			//order.setCreateBy(user);
			retOrder = orderService.saveNewOrder(order);
			
		}else{						
			
			order.setOrderStatus(orderStatus);
			//order.setLastUpdBy(user);			
			retOrder = orderService.updateOrder(order);
			
		}
		
		if(tmpreasonCd.equals(ImsConstants.IMS_ORDER_CASH_REQ)){
			orderService.submitCashPermitWQ(order);
		}else if(tmpreasonCd.equals(ImsConstants.IMS_ORDER_WAIVE_PREPAY)){
			orderService.submitWaivePrepayWQ(order);
		}else if(tmpreasonCd.equals(ImsConstants.IMS_ORDER_WAIVE_OTC)){
			orderService.submitWaiveOTCWQ(order);
		}else if(tmpreasonCd.equals(ImsConstants.IMS_ORDER_DISCOUNTED_OTC)){
			orderService.submitDiscountedOTCWQ(order);
		//Tony added
		}else if(tmpreasonCd.equals(ImsConstants.WQ_Reason_MissMatch)||tmpreasonCd.equals(ImsConstants.WQ_Reason_MissMatch_PT)
			     ||tmpreasonCd.equals(ImsConstants.WQ_Reason_cOrder)||tmpreasonCd.equals(ImsConstants.WQ_Reason_cOrder_PT)
			     ||tmpreasonCd.equals(ImsConstants.WQ_Reason_both_cOrder_MissMatch)||tmpreasonCd.equals(ImsConstants.WQ_Reason_both_cOrder_MissMatch_PT)){		
			orderService.submitSBWQ(order,reasonCd);
		} 
		//Tony added end
		
		if(!tmpreasonCd.equals("S000")){
			retOrder.setOrderActionInd("R");
		}		

		if("06".equals(retOrder.getOrderStatus())){
			orderService.dsAlertWQ(order, ImsConstants.DS_Alert_WQ_06_await_cash, "Await Cash Prepayment WQ","");
		}

		if(!tmpreasonCd.equals("S000")&&orderService.needSuspendWQ(order.getOrderStatus(), order.getSalesCd())){
			orderService.orderStatusAlertWQ(order, ImsConstants.Order_Status_Alert_WQ, reasonCd);
		}

		if(LookupTableBean.getInstance().getImsDSSuspendLookupMap().containsKey(tmpreasonCd)
				&& "DS".equals(order.getImsOrderType()) && ImsConstants.IMS_ORDER_STATUS_SUSPENDED.equals(order.getOrderStatus())){
			orderService.orderStatusAlertWQ(order, ImsConstants.Order_Status_Alert_WQ, reasonCd);
		}
		
		return retOrder;
	}
	
	public OrderImsUI cancelOrder(OrderImsUI order, String reasonCd){		
		logger.info("cancelOrder:"+order.getOrderId());
		//logger.info("order id:"+order.getOrderId());
		logger.info("order status:"+order.getOrderStatus());
		
		order.setReasonCd(reasonCd);
		if ( "Y".equals(order.getIsCC()) )
		{
			order.setShopCd(null);
		}
		
		if(order.getOrderStatus()==null){
									
			if(order.getOrderId()==null){				
				order.setOrderId(getNewOrderId("Y".equals(order.getIsCC())?order.getChannelCd():order.getShopCd(),order.getCreateBy()));
			}
			//order.setCreateBy(user);
			order.setOrderStatus(ImsConstants.IMS_ORDER_STATUS_CANCELLED);
			return orderService.saveNewOrder(order);
			
		}else{						
			
			//order.setLastUpdBy(user);
			order.setOrderStatus(ImsConstants.IMS_ORDER_STATUS_CANCELLED);
			return orderService.updateOrder(order);
			
		}
		
	}
	
	public OrderImsUI getNewOrderTemplate(String shopCd){
		logger.info("getNewOrderTemplate");
		
		OrderImsUI order = new OrderImsUI();
		order.setShopCd(shopCd);		
		
		initNewOrder(order);

		return order;
		
	}
	
	private void initNewOrder(OrderImsUI order){		
		
		AppointmentUI appointment = new AppointmentUI();		
		order.setAppointment(appointment);
		
		RemarkUI remark = new RemarkUI();
		remark.setRmkType("IA");
		RemarkUI[] remarks = new RemarkUI[1];
		remarks[0] = remark;		
		order.setRemarks(remarks);
		
		InstallAddrUI installAddress = new InstallAddrUI();		
		installAddress.setAddrUsage("IA");
		order.setInstallAddress(installAddress);

		CustomerUI customer = new CustomerUI();		
		
		AcctUI account = new AcctUI();		
		customer.setAccount(account);
		
		ContactUI contact = new ContactUI();		
		customer.setContact(contact);
		
		order.setCustomer(customer);
		
		//order.setOrderId(orderService.getNewOrderId(order.getShopCd()));
		order.setAppDate(orderService.getApplicationDate());
		
	}
	
	public String getNewOrderId(String shopCd,String staffId){
		return orderService.getNewOrderId(shopCd,staffId);
	}
	
	public OrderImsUI getOrder(String orderId){
		return orderService.getBomWebImsOrder(orderId);
	}

	public ImsOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(ImsOrderService orderService) {
		this.orderService = orderService;
	}

	public void setcOrderService(COrderService cOrderService) {
		this.cOrderService = cOrderService;
	}

	public COrderService getcOrderService() {
		return cOrderService;
	}
	
	public List<String> getImsDocTypeList() {
		try {
			return this.orderDocumentDao.getImsDocTypeList();
		} catch (DAOException de) {
			logger.error("Fail in LtsOrderDocumentService.getWaiveReasonList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	public void setClubHktService(ClubHktService clubHktService) {
		this.clubHktService = clubHktService;
	}

	public ClubHktService getClubHktService() {
		return clubHktService;
	}

	public List<CustomerBasicInfoDTO> getBomWebCustomer(String custId) {//wrongly done, should use BOM but not SpringBoard, Steven 20140612
		return this.orderDao.getBomWebCustomerByCustId(custId);
	}

	public List<CustomerBasicInfoDTO> getBomWebCustomerBom(String custId) {//wrongly done, should use BOM but not SpringBoard, Steven 20140612
		try {
			return this.getImsCustomerDAO.getImsCustomerForRetention(custId);
		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("Fail in getBomWebCustomerBom()", e);
			return null;
		}
	}

	public void setOrderDao(ImsOrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	public ImsOrderDAO getOrderDao() {
		return orderDao;
	}
	
	public void updateOrderImgByLatestAmend(OrderImsUI order){
		try {
			String orderId = order.getOrderId();
			String amendSeq = "";

			if ("NTV-A".equals(order.getOrderType()) || order.isOrderTypeNowRet()) {
				amendSeq = orderDao.getLatestAmendSeq4Ntv(orderId);
			} else {
				amendSeq = orderDao.getLatestAmendSeq(orderId);
			}
			
			logger.info("orderId : " + orderId+"  amendSeq : " + amendSeq);
			
			order.setLastTimeSuccessAmendSeq(amendSeq);
			
			if(StringUtils.isEmpty(amendSeq)) {
				order.setLastTimeSuccessAmendSeq("null");
				return;				
			}else {
				//get bomweb_order_ims 
				List<OrderImsDTO> orderIms = orderDao.getBomWebOrderImsAmd(orderId, amendSeq);
				if (orderIms != null && orderIms.size()>0) {
					BeanUtils.copyProperties(order, orderIms.get(0));
					
					//get bomweb_order						
					BeanUtils.copyProperties(order, orderDao.getBomWebOrder(orderId).get(0));
				}
				
				//get subscribed items Amd
				List<SubscribedItemDTO> items = orderDao.getSubscribedItemAmd(orderId, amendSeq);
				if(items!=null && items.size()>0){
					SubscribedItemUI[] itemsui = new SubscribedItemUI[items.size()];
					for(int i=0; i<items.size(); i++){
						SubscribedItemUI itemui = new SubscribedItemUI();
						BeanUtils.copyProperties(itemui, items.get(i));
						itemsui[i] = itemui;
					}
					order.setSubscribedItems(itemsui);
				} else order.setSubscribedItems(new SubscribedItemUI[0]);
				
				//get channel Amd
				List<SubscribedChannelDTO> channels = orderDao.getSubscribedChannelAmd(orderId, amendSeq);
				if(channels!=null && channels.size()>0){
					SubscribedChannelUI[] channelsui = new SubscribedChannelUI[channels.size()];
					for(int i=0; i<channels.size(); i++){
						SubscribedChannelUI channelui = new SubscribedChannelUI();
						BeanUtils.copyProperties(channelui, channels.get(i));
						channelsui[i] = channelui;
					}
					order.setSubscribedChannels(channelsui);
				} else order.setSubscribedChannels(new SubscribedChannelUI[0]);
				
				//get custaddr
				List<CustAddrDTO> addresses = orderDao.getBomWebCustAddrAmd(orderId, amendSeq);
				for(int i=0; i<addresses.size(); i++){
					if(addresses.get(i).getAddrUsage().equals(ImsConstants.IMS_CUST_INSTALL_ADDR)){
						InstallAddrUI instaddr = new InstallAddrUI();					
						BeanUtils.copyProperties(instaddr, addresses.get(i));
						
						order.setInstallAddress(instaddr);
					}
				}
				
				List<BasketDetailsDTO> basketDetailsList = new ArrayList<BasketDetailsDTO>();
				if(order.getSubscribedItems().length>0){
					for(SubscribedItemUI ui: order.getSubscribedItems()){
						if(ui.getType().equalsIgnoreCase("PROG")){
							basketDetailsList = basketService.getBasketDetailsList("en", ui.getBasketId());
							if(basketDetailsList!=null&&basketDetailsList.size()>0){
								if(basketDetailsList.get(0).getTechnology()!=null){
									if (order.getInstallAddress().getAddrInventory() == null) {
										AddrInventoryDTO addrInventory = new AddrInventoryDTO();
										addrInventory.setTechnology(basketDetailsList.get(0).getTechnology());
										order.getInstallAddress().setAddrInventory(addrInventory);
									}else{
										order.getInstallAddress().getAddrInventory().setTechnology(basketDetailsList.get(0).getTechnology());
									}
								}
							}
						}
					}
				}
				

				
				//get component
				List<ComponentDTO> components = orderDao.getComponentAmd(orderId, amendSeq);
				if(components!=null && components.size()>0){
					ComponentUI[] componentsui = new ComponentUI[components.size()];
					for(int i=0; i<components.size(); i++){
						ComponentUI componentui = new ComponentUI();
						BeanUtils.copyProperties(componentui, components.get(i));
						componentsui[i] = componentui;
					}
					order.setComponents(componentsui);
				} else order.setComponents(new ComponentUI[0]);
				
				//get imsvasparm
				List<SubscribedCslOfferDTO> imsvasparm = orderDao.getSubscribedImsVasParmAmd(orderId, amendSeq);
				if(imsvasparm!=null && imsvasparm.size()>0){
					SubscribedCslOfferDTO[] dto = new SubscribedCslOfferDTO[imsvasparm.size()];
					for(int i=0; i<imsvasparm.size(); i++){
						SubscribedCslOfferDTO subscribedvarparmui = new SubscribedCslOfferDTO();
						BeanUtils.copyProperties(subscribedvarparmui, imsvasparm.get(i));
						dto[i] = subscribedvarparmui;
					}
					order.setSubscribedImsVasParm(dto);
				} else order.setSubscribedImsVasParm(new SubscribedCslOfferDTO[0]);
				
				//get backend items Amd
				List<SubscribedItemDTO> backenditems = orderDao.getSubscribedBackendItemAmd(orderId, amendSeq);
				if(backenditems!=null && backenditems.size()>0){
					SubscribedItemUI[] backenditemsui = new SubscribedItemUI[backenditems.size()];
					for(int i=0; i<backenditems.size(); i++){
						SubscribedItemUI backenditemui = new SubscribedItemUI();
						BeanUtils.copyProperties(backenditemui, backenditems.get(i));
						backenditemsui[i] = backenditemui;
					}
					order.setSubscribedBackEndItems(backenditemsui);
				} else order.setSubscribedBackEndItems(new SubscribedItemUI[0]);
				
				//get backend channel Amd
				List<SubscribedChannelDTO> backendchannels = orderDao.getSubscribedBackendChannelAmd(orderId, amendSeq);
				if(backendchannels!=null && backendchannels.size()>0){
					SubscribedChannelUI[] backendchannelsui = new SubscribedChannelUI[backendchannels.size()];
					for(int i=0; i<backendchannels.size(); i++){
						SubscribedChannelUI backendchannelui = new SubscribedChannelUI();
						BeanUtils.copyProperties(backendchannelui, backendchannels.get(i));
						backendchannelsui[i] = backendchannelui;
					}
					order.setSubscribedBackEndChannels(backendchannelsui);
				} else order.setSubscribedBackEndChannels(new SubscribedChannelUI[0]);
				
				//Fix for pre-install order 2nd amendments
				List<OrderImsDTO> orderImsFlag = orderDao.getBomWebOrderFlag(orderId);
				if (orderImsFlag != null && orderImsFlag.size()>0) {
					order.setPreInstallInd(orderImsFlag.get(0).getPreInstallInd());
				}
				if(order.isOrderTypeNowRet()){
					//get subs offer						
					List<BomwebSubscribedOfferImsDTO> offers = orderDao.getBomwebSubscribedOfferImsAmd(orderId, amendSeq);
					if(offers!=null && offers.size()>0){
						order.setSubscribedOffersIms(offers.toArray(new BomwebSubscribedOfferImsDTO[0]));
					};
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Fail in updateOrderImgByLatestAmend()", e);
			throw new RuntimeException();
		}
	}	
	
	
	public boolean checkPCDretentionPeriod(String service_num){
		boolean check =false;
		
		try{	
			check = orderDao.checkPCDretentionPeriod(service_num);
			return check;
		
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
	};
	
	public boolean checkLTSretentionPeriod(String service_num){
		boolean check =false;
		
		try{	
			check = orderDao.checkLTSretentionPeriod(service_num);
			return check;
		
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
	};
	
	public boolean checkPCDexistingServcie(String service_num){
		boolean check =false;
		
		try{	
			check = orderDao.checkPCDexistingServcie(service_num);
			return check;
		
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
	};
	
	
	public List<OrderImsSystemFindingDTO> sysFchecking(){
		boolean check =false;
		
		try{	
			return orderDao.sysFchecking();
			
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
	};
	
	public int updateDSSysFinding(OrderImsSystemFindingDTO dto) throws DAOException{
		return orderDao.updateDSSysFinding(dto);
	}
	
	public void addBackendOffers(String i_order_id) throws  SQLException {
		logger.info("addBackendOffers(NTV) is called");	

		try {
			orderDao.addBackendOffers(i_order_id);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void addBackendOffersDtl(OrderImsUI order) throws  SQLException {
		logger.info("addBackendOffersDtl(NTV) is called");	

		try {
			orderDao.addBackendOffersDtl(order);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public void setBasketService(ImsBasketDetailsService basketService) {
		this.basketService = basketService;
	}
	public ImsBasketDetailsService getBasketService() {
		return basketService;
	}
	
	public void getSystemFinding(OrderImsUI order, String staffId, int channelId, String dob) {
		logger.info("@ getSystemFinding");
		try {
			String sys_f = orderDao.getSystemFinding(order, staffId, dob);
			if (!StringUtils.isEmpty(sys_f)) {
				order.setSysF(sys_f);
				order.setMust_QC_Ind("Y");
			} else {
				order.setSysF("");
				order.setMust_QC_Ind("N");
			}
			if (channelId==99 && !"SWOFFER".equals(sys_f)) {
				order.setSysF("");
				order.setMust_QC_Ind("N");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<OrderImsSystemFindingDTO> sysFcheckingNowTV(){
		boolean check =false;
		
		try{	
			return orderDao.sysFcheckingNowTV();
			
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
	};
	
	public Map<String, String> getLookUpMapByLocale (Map<String, String> ilist , String Locale){
		
		
		logger.debug("Celia teset 20170613    "+Locale);
		Map<String, String> olist = new HashMap<String, String>();
		if(ilist!=null){
			for(Map.Entry<String, String> entry:ilist.entrySet()){
				if(entry.getKey().length()<=4){
					olist.put(entry.getKey().substring(0,4),entry.getValue());
				}else if(entry.getKey().contains(Locale))
					olist.put(entry.getKey().substring(0,4),entry.getValue());
			}
		}
		
		return olist;
		
	}
	public List<NtvReplaceSelfPickHddDTO> getReplaceSelfPickHddLkup() {
		return orderDao.getReplaceSelfPickHddLkup();
	}
	
}
