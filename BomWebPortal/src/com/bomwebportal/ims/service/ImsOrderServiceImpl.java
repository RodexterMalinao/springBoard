package com.bomwebportal.ims.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jni.Address;
import org.jsoup.helper.StringUtil;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ImsAddressInfoDAO;
import com.bomwebportal.ims.dao.ImsBomOrderDAO;
import com.bomwebportal.ims.dao.ImsOrderAmendDAO;
import com.bomwebportal.ims.dao.ImsOrderDAO;
import com.bomwebportal.ims.dto.AppointmentDTO;
import com.bomwebportal.ims.dto.BomwebOrderServiceImsDTO;
import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.BomwebSubscribedOfferImsDTO;
import com.bomwebportal.ims.dto.BomwebBillingAddrDTO;
import com.bomwebportal.ims.dto.BomwebThirdPartyApplnDTO;
import com.bomwebportal.ims.dto.ComponentDTO;
import com.bomwebportal.ims.dto.CustAddrDTO;
import com.bomwebportal.ims.dto.CustOptoutInfoDTO;
import com.bomwebportal.ims.dto.ImsAllOrdDocAssgnDTO;
import com.bomwebportal.ims.dto.IOOfferOTCSchemeDTO;
import com.bomwebportal.ims.dto.ImsCollectDocDTO;
import com.bomwebportal.ims.dto.OrderDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.RemarkDTO;
import com.bomwebportal.ims.dto.SalesReferrerDTO;
import com.bomwebportal.ims.dto.SubscItemDiscDTO;
import com.bomwebportal.ims.dto.SubscribedChannelDTO;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.SubscribedItemDTO;
import com.bomwebportal.ims.dto.ui.AcctUI;
import com.bomwebportal.ims.dto.ui.AddrInventoryUI;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.ComponentUI;
import com.bomwebportal.ims.dto.ui.ContactUI;
import com.bomwebportal.ims.dto.ui.CustAddrUI;
import com.bomwebportal.ims.dto.ui.CustOptoutInfoUI;
import com.bomwebportal.ims.dto.ui.CustomerUI;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.ImsPaymentUI;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.PaymentUI;
import com.bomwebportal.ims.dto.ui.RemarkUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.pccw.wq.schema.dto.WorkQueueAttributeDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueDocumentDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.service.WorkQueueService;
import com.bomwebportal.ims.dto.BomwebOrderL2JobDTO;
import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.bomwebportal.ims.service.ImsCOrderWQService;

@Transactional(readOnly = true)
public class ImsOrderServiceImpl implements ImsOrderService{

	public ImsSMSService getImsSMSService() {
		return imsSMSService;
	}

	public void setImsSMSService(ImsSMSService imsSMSService) {
		this.imsSMSService = imsSMSService;
	}

	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();
    private ImsSMSService imsSMSService;

	private LoginService loginService;
	private ImsOrderDAO orderDao;
	//private ImsSyncOrderService ordersyncService;
	private WorkQueueService workQueueService;
	private ImsAddressInfoDAO addressDao;
	private String tmpFilePath;	   
	private ImsAddressInfoService imsAddressInfoService; //Tony added
	private ImsCOrderWQService imsCOrderWQService; //Tony added
	private ImsOrderAmendDAO imsAmendOrderDao;
	private GetImsCustomerService getImsCustomerService;
	
	// Ims direct sales
    private ImsBomOrderDAO bomDao;
	
	
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public GetImsCustomerService getGetImsCustomerService() {
		return getImsCustomerService;
	}

	public void setGetImsCustomerService(GetImsCustomerService getImsCustomerService) {
		this.getImsCustomerService = getImsCustomerService;
	}

	public List<ImsAllOrdDocAssgnDTO> getSupportDocFromDB(String orderId){
		try {
			return orderDao.getSupportDocFromDB(orderId);
		} catch (DAOException e) {
			logger.error(e);
		}
		return null;
	}
	
	
	public boolean isCreateChangeOrder(OrderImsUI order){
		logger.info("isCreateChangeOrder");
		try{
			return addressDao.isCreateCOrder(
					order.getInstallAddress().getSerbdyno(), 
					order.getInstallAddress().getUnitNo(), 
					order.getInstallAddress().getFloorNo(),
					order.getCustomer().getCustNo());
		}catch (DAOException de) {
			logger.error("Exception caught in isCreateChangeOrder()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public Date getApplicationDate(){
		logger.info("getApplicationDate");
		try{
			return orderDao.getSystemDate();
		}catch(DAOException de){
			logger.error("Exception caught in getApplication()", de);
			throw new AppRuntimeException(de);
		}
	}
		
	
	public String getAutoProcessOrderId(){
		logger.info("getAutoProcessOrderId");				
		try{
			return orderDao.getAutoProcessOrderId();
		}catch (DAOException de) {
			logger.error("Exception caught in getAutoProcessOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public OrderImsUI getBomSyncOrderDetail(String orderId){
		logger.info("getBomSyncOrderDetail");
		try{
			OrderImsUI order = getBomWebImsOrder(orderId);				
			getBomSyncOrderDetail(order);
			return order;
		}catch (Exception e) {
			logger.error("Exception caught in getBomSyncOrderDetail()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateSyncOrderStatus(String orderId){
		logger.info("updateSyncOrderStatus");
		logger.info("order id:"+orderId);
		
		try{
			orderDao.updateSyncOrderStatus(orderId);
		}catch (DAOException de) {
			logger.error("Exception caught in updateSyncOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	/*@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void syncOrderToBOM(String orderId){
		logger.info("syncOrderToBOM");
		
		try{
			if(orderId==null){
				orderId = orderDao.getAutoProcessOrderId();
			}
			if(orderId!=null){
				
				logger.info("Get signed-off order ID:"+orderId);
				OrderImsUI order = getBomWebImsOrder(orderId);				
				getBomSyncOrderDetail(order);
				System.out.println(gson.toJson(order));
				ordersyncService.syncOrderToBom(order);
				orderDao.updateSyncOrderStatus(order.getOrderId());
				
			}else{
				logger.info("No pending order found");
			}
			
		}catch (DAOException de) {
			logger.error("Exception caught in syncOrderToBOM()", de);
			throw new AppRuntimeException(de);
		}catch (Exception e){
			logger.error("Exception caught in syncOrderToBOM()", e);
			throw new AppRuntimeException(e);
		}	
						
	}		*/
	
	private void getBomSyncOrderDetail(OrderImsUI order){
		logger.info("getBomSyncOrderDetail");
		
		try{
			
			List<SubscribedItemDTO> items = orderDao.getSubscribedItemSync(order.getOrderId());
			if(items!=null){
				SubscribedItemUI[] itemsui = new SubscribedItemUI[items.size()];
				for(int i=0; i<items.size(); i++){
					SubscribedItemUI itemui = new SubscribedItemUI();
					BeanUtils.copyProperties(itemui, items.get(i));
					itemsui[i] = itemui;
				}
				order.setSubscribedItems(itemsui);
			}else{
				order.setSubscribedItems(null);
			}
			
			List<SubscribedChannelDTO> channels = orderDao.getSubscribedChannelSync(order.getOrderId());
			if(channels!=null){
				SubscribedChannelUI[] channelsui = new SubscribedChannelUI[channels.size()];
				for(int i=0; i<channels.size(); i++){
					SubscribedChannelUI channelui = new SubscribedChannelUI();
					BeanUtils.copyProperties(channelui, channels.get(i));
					channelsui[i] = channelui;
				}
				order.setSubscribedChannels(channelsui);
			}else{
				order.setSubscribedChannels(null);
			}
			
			List<SubscItemDiscDTO> discounts = orderDao.getSubscItemDisc(order.getOrderId());
			if(discounts!=null){
				order.setSubscDiscountList(discounts.toArray(new SubscItemDiscDTO[0]));
			}else{
				order.setSubscDiscountList(null);
			}
			
			List<ComponentDTO> components = orderDao.getComponentSync(order.getOrderId());
			if(components!=null){
				ComponentUI[] componentsui = new ComponentUI[components.size()];
				for(int i=0; i<components.size(); i++){
					ComponentUI componentui = new ComponentUI();
					BeanUtils.copyProperties(componentui, components.get(i));
					componentsui[i] = componentui;
				}
				order.setComponents(componentsui);
			}else{
				order.setComponents(null);
			}
			
			//set sync time
			order.setWarrStartDate(orderDao.getSystemDate());
			
		}catch (DAOException de) {
			logger.error("Exception caught in getBomWebImsOrder()", de);
			throw new AppRuntimeException(de);
		}catch (Exception e){
			logger.error("Exception caught in getBomWebImsOrder()", e);
			throw new AppRuntimeException(e);
		}													
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public OrderImsUI saveNewOrder(OrderImsUI order) {
		logger.info("saveNewOrder");
		logger.debug(gson.toJson(order));
		
		try {			
			
			//default ims customer			
			if(order.getCustomer().getLob()==null || order.getCustomer().getLob().trim().length()==0){
				order.getCustomer().setLob(ImsConstants.IMS_ORDER_LOB);
			}
			
			//insertBomWebOrder
			order.setLob(ImsConstants.IMS_ORDER_LOB);						
			
			if (order.isOrderTypeNowRet())
			{
				initBomWebOrder(order);
			}
			orderDao.insertBomWebOrder(order);			
			
			//insertBomWebOrderIms
			orderDao.insertBomWebOrderIms(order);
			
			//insertBomWebAppointment
			if (appointmentRequired(order))
			{
				order.getAppointment().setOrderId(order.getOrderId());
				order.getAppointment().setCreateBy(order.getCreateBy());
				orderDao.insertBomWebAppointment(order.getAppointment());
			}
			//insertRemark
			if(order.getRemarks()!=null){
				for(int i=0; i<order.getRemarks().length; i++){
					order.getRemarks()[i].setOrderId(order.getOrderId());
					order.getRemarks()[i].setCreateBy(order.getCreateBy());
					orderDao.insertRemark(order.getRemarks()[i]);
				}
			}
			
			//insertBomWebCustomer
			order.getCustomer().setOrderId(order.getOrderId());
			order.getCustomer().setCreateBy(order.getCreateBy());
			orderDao.insertBomWebCustomer(order.getCustomer());
			
			//insertBomWebAcct
			order.getCustomer().getAccount().setOrderId(order.getOrderId());
			order.getCustomer().getAccount().setCreateBy(order.getCreateBy());
			orderDao.insertBomWebAcct(order.getCustomer().getAccount());
			
			//insertBomWebPayment
			order.getCustomer().getAccount().getPayment().setOrderId(order.getOrderId());
			order.getCustomer().getAccount().getPayment().setCreateBy(order.getCreateBy());
			if (order.isOrderTypeNowRet())
			{
				setPaymentExtraFields(order);
			}
			orderDao.insertBomWebPayment(order.getCustomer().getAccount().getPayment());
			
			//insertBomWebCustOptoutInfo
			if(order.getCustomer().getCustOptInfo()!=null){
				order.getCustomer().getCustOptInfo().setOrderId(order.getOrderId());
				order.getCustomer().getCustOptInfo().setCreateBy(order.getCreateBy());
				orderDao.insertBomWebCustOptoutInfo(order.getCustomer().getCustOptInfo());
			}			
			
			//insertBomWebContact
			order.getCustomer().getContact().setOrderId(order.getOrderId());
			order.getCustomer().getContact().setCreateBy(order.getCreateBy());
			orderDao.insertBomWebContact(order.getCustomer().getContact());
			
			//insertBomWebCustAddr - install
			order.getInstallAddress().setOrderId(order.getOrderId());
			order.getInstallAddress().setCreateBy(order.getCreateBy());
			orderDao.insertBomWebCustAddr(order.getInstallAddress());
			//insertBomWebAddrInventory - install
			if(order.getInstallAddress().getAddrInventory()!=null){
				order.getInstallAddress().getAddrInventory().setOrderId(order.getOrderId());
				order.getInstallAddress().getAddrInventory().setCreateBy(order.getCreateBy());
				orderDao.insertBomWebAddrInventory(order.getInstallAddress().getAddrInventory());
			}			
			
			//insertBomWebCustAddr - billing
			if(isNewBillAddress(order)){
				order.getBillingAddress().setOrderId(order.getOrderId());
				order.getBillingAddress().setCreateBy(order.getCreateBy());
				orderDao.insertBomWebCustAddr(order.getBillingAddress());
				//insertBomWebAddrInventory - billing
				if(order.getBillingAddress().getAddrInventory()!=null){
					order.getBillingAddress().getAddrInventory().setOrderId(order.getOrderId());
					order.getBillingAddress().setCreateBy(order.getCreateBy());
					orderDao.insertBomWebAddrInventory(order.getBillingAddress().getAddrInventory());
				}				
			}
			
			//insertBomWebSubscribedItem
			if(order.getSubscribedItems()!=null){
				for(int i=0; i<order.getSubscribedItems().length; i++){
					order.getSubscribedItems()[i].setOrderId(order.getOrderId());
					if(!"backend".equalsIgnoreCase(order.getSubscribedItems()[i].getCreateBy())){
						order.getSubscribedItems()[i].setCreateBy(order.getCreateBy());
					}
					orderDao.insertBomWebSubscribedItem(order.getSubscribedItems()[i]);
				}
			}
			
			//insertBomWebSubscribedChannel
			if(order.getSubscribedChannels()!=null){
				for(int i=0; i<order.getSubscribedChannels().length; i++){
					order.getSubscribedChannels()[i].setOrderId(order.getOrderId());
					order.getSubscribedChannels()[i].setCreateBy(order.getCreateBy());
					orderDao.insertBomWebSubscribedChannel(order.getSubscribedChannels()[i]);
				}
			}
			
			//insertBomWebVasParmIms csl offer
			if(order.getSubscribedCslOffers()!=null){
				for(int i=0; i<order.getSubscribedCslOffers().length; i++){
					order.getSubscribedCslOffers()[i].setOrder_id(order.getOrderId());
					order.getSubscribedCslOffers()[i].setCreateBy(order.getCreateBy());
					orderDao.insertBomWebVasParmIms(order.getSubscribedCslOffers()[i]);
				}
			}

		    //insertBomWebVasParmIms non csl offer
			if(order.getSubscribedImsVasParm()!=null){
				for(int i=0; i<order.getSubscribedImsVasParm().length; i++){
					order.getSubscribedImsVasParm()[i].setOrder_id(order.getOrderId());
					order.getSubscribedImsVasParm()[i].setCreateBy(order.getCreateBy());
					orderDao.insertBomWebVasParmIms(order.getSubscribedImsVasParm()[i]);
				}
			}
			
			//insertBomWebComponent
			if(order.getComponents()!=null){
				for(int i=0; i<order.getComponents().length; i++){
					order.getComponents()[i].setOrderId(order.getOrderId());
					order.getComponents()[i].setCreateBy(order.getCreateBy());
					orderDao.insertBomWebComponent(order.getComponents()[i]);
				}
			}
						
			//if(order.getShopCd()!=null){
				orderDao.updateSalesChannel(order.getOrderId());
			//}
			
			//insertBomwebOrderL2Job
			if(order.getBomwebOrderL2JobList()!=null){
				for(int i=0; i<order.getBomwebOrderL2JobList().length; i++){	
					order.getBomwebOrderL2JobList()[i].setOrderId(order.getOrderId());
					order.getBomwebOrderL2JobList()[i].setLastUpdBy(order.getLastUpdBy());
					orderDao.insertBomwebOrderL2Job(order.getBomwebOrderL2JobList()[i]);
				}
			}
			
	//insertBomWebSalesReferrerIms 
			if(order.getSalesReferrerDTO()!=null){
			if( order.getSalesReferrerDTO().getReferrerAppMethod() != null && !StringUtils.isEmpty(order.getSalesReferrerDTO().getReferrerAppMethod())){
				order.getSalesReferrerDTO().setOrderId(order.getOrderId());
				order.getSalesReferrerDTO().setCreateBy(order.getCreateBy());
				orderDao.insertBomWebSalesReferrerIms(order.getSalesReferrerDTO());
				
			}
			}
			
			//insert bomweb care cash 
			if(order.getCustomer().getCareCashInd() != null){
				orderDao.insertBomwebCustIguardReg(order.getCustomer());
			}
			
			if(order.getSubscribedOffersIms()!=null){
				logger.info("insertBomWebSubscribedOfferIms");
				for(BomwebSubscribedOfferImsDTO dto:order.getSubscribedOffersIms()){
					dto.setOrderId(order.getOrderId());
					if ("Y".equals(order.getWaivePenalty())&&"O".equals(dto.getIoInd())&&
							dto.getTcCd()!=null&&!"".equals(dto.getTcCd()))
					{
						dto.setWaiveCd(order.getSelectedWaivedReason());
					}
					orderDao.insertBomWebSubscribedOfferIms(dto); 
				}
			}else{
				logger.info("getSubscribedOffersIms is null");				
			}
			
			if (order.isOrderTypeNowRet())
			{
				saveExtraDetails(order);
			}
			return getBomWebImsOrder(order.getOrderId());
			
		}catch (DAOException de) {
			logger.error("Exception caught in saveNewOrder()", de);
			throw new AppRuntimeException(de);
		}				
		
	}
	
	protected boolean isNewBillAddress(OrderImsUI order)
	{
		logger.info("isNewBillAddress(OrderImsUI order) - start");
		if (order.isOrderTypeNowRet())
		{
			ImsInstallationUI customer = (ImsInstallationUI) order.getCustomer();
			AcctUI accountUI = customer.getAccount();
			ImsPaymentUI payment = (ImsPaymentUI)accountUI.getPayment();
			return order.getBillingAddress()!=null && "C".equals(accountUI.getUpdateBillingMethod()) && "P".equals(accountUI.getBillMedia()) && "N".equals(accountUI.getKeepExistingBillingAddress()) && !"Y".equals(payment.getIsFreeFormat());
		}
		else
		{
			return order.getBillingAddress()!=null;
		}
		
	}
	
	protected boolean appointmentRequired(OrderImsUI order)
	{
		logger.info("appointmentRequired(OrderImsUI order) - start");
		if (order.isOrderTypeNowRet())
		{
			return order.getAppointment()!= null;
		}
		else
		{
			return true;
		}
	}
	
	protected void initBomWebOrder(OrderImsUI order)
	{
		logger.info("initBomWebOrder(OrderImsUI order) - start");
		if (order.getAppointment()!= null && "N".equals(order.getFieldInd()))
		{
			order.setSrvReqDate(order.getAppointment().getServiceEffectiveDate());
		}
		if(order.isOrderTypeNowRet()){
			if (order.getSrvReqDate()==null)
			{
				order.setSrvReqDate(order.getAppointment().getServiceEffectiveDate());
			}
			if (order.getSrvReqDate()==null)
			{
				order.setSrvReqDate(order.getAppointment().getSrvReqDate());
			}
		}
	}
	
	protected void saveExtraDetails(OrderImsUI order) throws DAOException
	{
		logger.info("saveExtraDetails(OrderImsUI order) - start");
		try{
			ImsInstallationUI customer = (ImsInstallationUI) order.getCustomer();
			if ("Y".equals(customer.getIsThirdPartyApplication()))
			{
				BomwebThirdPartyApplnDTO bomwebThirdPartyApplnDTO =new BomwebThirdPartyApplnDTO();
				bomwebThirdPartyApplnDTO.setOrderId(order.getOrderId());
				bomwebThirdPartyApplnDTO.setCreateBy(order.getCreateBy());
				bomwebThirdPartyApplnDTO.setAppntDocId(customer.getApplicantDocType());
				bomwebThirdPartyApplnDTO.setTitle(customer.getApplicantTitle());
				bomwebThirdPartyApplnDTO.setAppntLastName(customer.getApplicantLastName());
				bomwebThirdPartyApplnDTO.setAppntFirstName(customer.getApplicantFirstName());
				bomwebThirdPartyApplnDTO.setAppntDocType(customer.getApplicantDocType());
				bomwebThirdPartyApplnDTO.setAppntIdVerifiedInd(customer.getApplicantIDVerifiedInd());
				bomwebThirdPartyApplnDTO.setAppntDocId(customer.getApplicantIDNum());
				bomwebThirdPartyApplnDTO.setRelationshipCd(customer.getRelationshipwithCustomer());
				bomwebThirdPartyApplnDTO.setAppntContactNum(customer.getApplicantContactNo());
				bomwebThirdPartyApplnDTO.setRemarks(customer.getOtherRelationship());
				getOrderDao().insertBomWebThirdPartyAppln(bomwebThirdPartyApplnDTO);
			}
			
			AcctUI accountUI = customer.getAccount();
			ImsPaymentUI payment = (ImsPaymentUI)accountUI.getPayment();
			if ("C".equals(accountUI.getUpdateBillingMethod()) && "P".equals(accountUI.getBillMedia()))
			{
				if("N".equals(accountUI.getKeepExistingBillingAddress()) && "Y".equals(payment.getIsFreeFormat()))
				{
					BomwebBillingAddrDTO bomwebBillingAddrDTO= new BomwebBillingAddrDTO();
					bomwebBillingAddrDTO.setOrderId(order.getOrderId());
					bomwebBillingAddrDTO.setCreateBy(order.getCreateBy());
					bomwebBillingAddrDTO.setAcctNo(order.getAcctNo());
					bomwebBillingAddrDTO.setAction("I");
					bomwebBillingAddrDTO.setAddrLine1(payment.getFreeFormatAddress1());
					bomwebBillingAddrDTO.setAddrLine2(payment.getFreeFormatAddress2());
					bomwebBillingAddrDTO.setAddrLine3(payment.getFreeFormatAddress3());
					bomwebBillingAddrDTO.setAddrLine4(payment.getFreeFormatAddress4());
					getOrderDao().insertBomWebBillingAddr(bomwebBillingAddrDTO);
				}
				else if ("C".equals(accountUI.getKeepExistingBillingAddress()))
				{
					//copy installation address as billing address
					order.getInstallAddress().setAddrUsage(ImsConstants.IMS_CUST_BILLING_ADDR);
					try{
						getOrderDao().insertBomWebCustAddr(order.getInstallAddress());
					}finally
					{
						order.getInstallAddress().setAddrUsage(ImsConstants.IMS_CUST_INSTALL_ADDR);
					}
				}
			}
			
			BomwebOrderServiceImsDTO bomwebOrderServiceImsDTO = new BomwebOrderServiceImsDTO();
			bomwebOrderServiceImsDTO.setOrderId(order.getOrderId());
			bomwebOrderServiceImsDTO.setCreateBy(order.getCreateBy());
			bomwebOrderServiceImsDTO.setProRata(customer.getProRataRefund());
			bomwebOrderServiceImsDTO.setFsa(order.getServiceIms().getFsa());
			bomwebOrderServiceImsDTO.setLineType(order.getServiceIms().getLineType());
			getOrderDao().insertBomWebOrderServiceIms(bomwebOrderServiceImsDTO);
		} catch (DAOException e) {
			throw e;			
		}
	}
	
	protected void setPaymentExtraFields(OrderImsUI order)
	{
		ImsPaymentUI payment = (ImsPaymentUI) order.getCustomer().getAccount().getPayment();
		// selected payment method is not equal to existing payment method
		// or using an new credit card
		if(!payment.getPayMtdType().equals(payment.getExistingPaymentMethod()) 
				|| ("C".equals(payment.getPayMtdType()) && "Y".equals(payment.getIsNewCard()) && payment.getCcNum()!=null && payment.getCcNum().length()>0))
		{
			payment.setAction("C");
		}
		else
		{
			payment.setAction(null);
		}
		logger.info(order.getOrderId()+" setPaymentExtraFields setAction:"+payment.getAction());
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public OrderImsUI updateOrder(OrderImsUI order){
		logger.info("updateOrder");
		logger.info("order id:"+order.getOrderId());
		logger.info(gson.toJson(order));
		
		try{
			
			//default ims customer			
			if(order.getCustomer().getLob()==null || order.getCustomer().getLob().trim().length()==0){
				order.getCustomer().setLob(ImsConstants.IMS_ORDER_LOB);
			}
			
			//temp use clean up original date
			orderDao.deleteUpdateTable(order.getOrderId());
			
			//update order
			if(order.isOrderTypeNowRet())
			{
				initBomWebOrder(order);
			}
			orderDao.updateBomWebOrder(order);
			
			//update order ims
			orderDao.updateBomWebOrderIms(order);
			
			//update appointment
			if (appointmentRequired(order))
			{
				order.getAppointment().setOrderId(order.getOrderId());
				order.getAppointment().setLastUpdBy(order.getLastUpdBy());
				orderDao.updateAppointment(order.getAppointment());
			}
			
			//update remarks
			if(order.getRemarks()!=null){
				for(int i=0; i<order.getRemarks().length; i++){
					order.getRemarks()[i].setOrderId(order.getOrderId());
					//order.getRemarks()[i].setCreateBy(order.getLastUpdBy());
					//orderDao.updateRemark(order.getRemarks()[i]);
					orderDao.insertRemark(order.getRemarks()[i]);
				}
			}			
			
			//update customer
			order.getCustomer().setOrderId(order.getOrderId());
			order.getCustomer().setLastUpdBy(order.getLastUpdBy());
			logger.info("doctype "+order.getCustomer().getIdDocType());
			orderDao.updateBomWebCustomer(order.getCustomer());
			
			//update account
			order.getCustomer().getAccount().setOrderId(order.getOrderId());
			order.getCustomer().getAccount().setLastUpdBy(order.getLastUpdBy());
			orderDao.updateAccount(order.getCustomer().getAccount());

			//update payment
			order.getCustomer().getAccount().getPayment().setOrderId(order.getOrderId());
			order.getCustomer().getAccount().getPayment().setLastUpdBy(order.getLastUpdBy());
			if (order.isOrderTypeNowRet())
			{
				setPaymentExtraFields(order);
			}
			orderDao.updatePayment(order.getCustomer().getAccount().getPayment());
			
			//update cust optout
			if(order.getCustomer().getCustOptInfo()!=null){
				order.getCustomer().getCustOptInfo().setOrderId(order.getOrderId());
				order.getCustomer().getCustOptInfo().setCreateBy(order.getLastUpdBy());
				//orderDao.updateCustOptoutInfo(order.getCustomer().getCustOptInfo());
				orderDao.insertBomWebCustOptoutInfo(order.getCustomer().getCustOptInfo());
			}			
			
			
			//update contact
			order.getCustomer().getContact().setOrderId(order.getOrderId());
			order.getCustomer().getContact().setLastUpdBy(order.getLastUpdBy());
			orderDao.updateContact(order.getCustomer().getContact());
			
			//update inst addr			
			//order.getInstallAddress().setCreateBy(order.getLastUpdBy());
			//orderDao.updateCustAddr(order.getInstallAddress());
			order.getInstallAddress().setOrderId(order.getOrderId());
			order.getInstallAddress().setCreateBy(order.getLastUpdBy());
			orderDao.insertBomWebCustAddr(order.getInstallAddress());
			
			
			//update billing addr
			if(isNewBillAddress(order)){
				order.getBillingAddress().setOrderId(order.getOrderId());
				order.getBillingAddress().setCreateBy(order.getLastUpdBy());
				orderDao.insertBomWebCustAddr(order.getBillingAddress());
			}
			
			//update addr inventory
			order.getInstallAddress().getAddrInventory().setOrderId(order.getOrderId());
			order.getInstallAddress().getAddrInventory().setLastUpdBy(order.getLastUpdBy());
			orderDao.updateAddrInventory(order.getInstallAddress().getAddrInventory());
			
			//update subscribed item
			if(order.getSubscribedItems()!=null){
				for(int i=0; i<order.getSubscribedItems().length; i++){
					order.getSubscribedItems()[i].setOrderId(order.getOrderId());
					if(!"backend".equalsIgnoreCase(order.getSubscribedItems()[i].getCreateBy())){
						order.getSubscribedItems()[i].setCreateBy(order.getLastUpdBy());
					}
					//orderDao.updateSubscribedItem(order.getSubscribedItems()[i]);
					orderDao.insertBomWebSubscribedItem(order.getSubscribedItems()[i]);
				}
			}			
			
			//update component
			if(order.getComponents()!=null){
				for(int i=0; i<order.getComponents().length; i++){
					order.getComponents()[i].setOrderId(order.getOrderId());
					order.getComponents()[i].setCreateBy(order.getLastUpdBy());
					//orderDao.updateComponent(order.getComponents()[i]);
					orderDao.insertBomWebComponent(order.getComponents()[i]);
				}
			}
			
			//update subscribed channel
			if(order.getSubscribedChannels()!=null){
				for(int i=0; i<order.getSubscribedChannels().length; i++){
					order.getSubscribedChannels()[i].setOrderId(order.getOrderId());
					order.getSubscribedChannels()[i].setCreateBy(order.getLastUpdBy());
					//orderDao.updateSubscribedChannel(order.getSubscribedChannels()[i]);
					orderDao.insertBomWebSubscribedChannel(order.getSubscribedChannels()[i]);
				}
			}
			
			
			//if(order.getShopCd()!=null){
				orderDao.updateSalesChannel(order.getOrderId());
			//}
			

		    //update csl offer
			if(order.getSubscribedCslOffers()!=null){
				for(int i=0; i<order.getSubscribedCslOffers().length; i++){
					order.getSubscribedCslOffers()[i].setOrder_id(order.getOrderId());
					order.getSubscribedCslOffers()[i].setCreateBy(order.getCreateBy());
					orderDao.insertBomWebVasParmIms(order.getSubscribedCslOffers()[i]);
				}
			}

		    //update ims vas parm list
			if(order.getSubscribedImsVasParm()!=null){
				for(int i=0; i<order.getSubscribedImsVasParm().length; i++){
					order.getSubscribedImsVasParm()[i].setOrder_id(order.getOrderId());
					order.getSubscribedImsVasParm()[i].setCreateBy(order.getCreateBy());
					orderDao.insertBomWebVasParmIms(order.getSubscribedImsVasParm()[i]);
				}
			}
			
			//updateBomwebOrderL2Job
			if(order.getBomwebOrderL2JobList()!=null){
				for(int i=0; i<order.getBomwebOrderL2JobList().length; i++){	
					order.getBomwebOrderL2JobList()[i].setOrderId(order.getOrderId());
					order.getBomwebOrderL2JobList()[i].setLastUpdBy(order.getLastUpdBy());
					orderDao.insertBomwebOrderL2Job(order.getBomwebOrderL2JobList()[i]);
				}
			}
			
			if(order.getSalesReferrerDTO()!=null){
				if(order.getSalesReferrerDTO().getReferrerAppMethod() != null && !StringUtils.isEmpty(order.getSalesReferrerDTO().getReferrerAppMethod())){
					order.getSalesReferrerDTO().setOrderId(order.getOrderId());
					order.getSalesReferrerDTO().setCreateBy(order.getCreateBy());
					orderDao.insertBomWebSalesReferrerIms(order.getSalesReferrerDTO());
				}
			}
			
			if(order.getCustomer().getCareCashInd() != null){
				orderDao.updateBomwebCustIguardReg(order.getCustomer());
			}
			
			if(order.getSubscribedOffersIms()!=null){
				logger.info("insertBomWebSubscribedOfferIms");
				for(BomwebSubscribedOfferImsDTO dto:order.getSubscribedOffersIms()){
					dto.setOrderId(order.getOrderId());
					if ("Y".equals(order.getWaivePenalty())&&"O".equals(dto.getIoInd())&&
							dto.getTcCd()!=null&&!"".equals(dto.getTcCd()))
					{
						dto.setWaiveCd(order.getSelectedWaivedReason());
					}
					orderDao.insertBomWebSubscribedOfferIms(dto); 
				}
			}else{
				logger.info("getSubscribedOffersIms is null");				
			}
			
			if (order.isOrderTypeNowRet())
			{
				updateExtraDetails(order);
			}
			
			return getBomWebImsOrder(order.getOrderId());
			
		}catch (DAOException de) {
			logger.error("Exception caught in updateOrder()", de);
			throw new AppRuntimeException(de);
		}	
		
	}
	
	protected void updateExtraDetails(OrderImsUI order) throws DAOException
	{
		logger.info("updateExtraDetails(OrderImsUI order) - start");
		try{
			ImsInstallationUI customer = (ImsInstallationUI) order.getCustomer();
			if ("Y".equals(customer.getIsThirdPartyApplication()))
			{
				BomwebThirdPartyApplnDTO bomwebThirdPartyApplnDTO =new BomwebThirdPartyApplnDTO();
				bomwebThirdPartyApplnDTO.setOrderId(order.getOrderId());
				bomwebThirdPartyApplnDTO.setAppntDocId(customer.getApplicantDocType());
				bomwebThirdPartyApplnDTO.setTitle(customer.getApplicantTitle());
				bomwebThirdPartyApplnDTO.setAppntLastName(customer.getApplicantLastName());
				bomwebThirdPartyApplnDTO.setAppntFirstName(customer.getApplicantFirstName());
				bomwebThirdPartyApplnDTO.setAppntDocType(customer.getApplicantDocType());
				bomwebThirdPartyApplnDTO.setAppntIdVerifiedInd(customer.getApplicantIDVerifiedInd());
				bomwebThirdPartyApplnDTO.setAppntDocId(customer.getApplicantIDNum());
				bomwebThirdPartyApplnDTO.setRelationshipCd(customer.getRelationshipwithCustomer());
				bomwebThirdPartyApplnDTO.setAppntContactNum(customer.getApplicantContactNo());
				bomwebThirdPartyApplnDTO.setRemarks(customer.getOtherRelationship());
				getOrderDao().insertBomWebThirdPartyAppln(bomwebThirdPartyApplnDTO);
			}
			AcctUI accountUI = customer.getAccount();
			ImsPaymentUI payment = (ImsPaymentUI)accountUI.getPayment();
			if ("P".equals(accountUI.getBillMedia()))
			{
				if("N".equals(accountUI.getKeepExistingBillingAddress()) && "Y".equals(payment.getIsFreeFormat()))
				{
					BomwebBillingAddrDTO bomwebBillingAddrDTO= new BomwebBillingAddrDTO();
					bomwebBillingAddrDTO.setOrderId(order.getOrderId());
					bomwebBillingAddrDTO.setCreateBy(order.getCreateBy());
					bomwebBillingAddrDTO.setAcctNo(order.getAcctNo());
					bomwebBillingAddrDTO.setAction("I");
					bomwebBillingAddrDTO.setAddrLine1(payment.getFreeFormatAddress1());
					bomwebBillingAddrDTO.setAddrLine2(payment.getFreeFormatAddress2());
					bomwebBillingAddrDTO.setAddrLine3(payment.getFreeFormatAddress3());
					bomwebBillingAddrDTO.setAddrLine4(payment.getFreeFormatAddress4());
					getOrderDao().insertBomWebBillingAddr(bomwebBillingAddrDTO);
				}
				else if ("C".equals(accountUI.getKeepExistingBillingAddress()))
				{
					//copy installation address as billing address
					order.getInstallAddress().setAddrUsage(ImsConstants.IMS_CUST_BILLING_ADDR);
					try{
						getOrderDao().insertBomWebCustAddr(order.getInstallAddress());
					}finally
					{
						order.getInstallAddress().setAddrUsage(ImsConstants.IMS_CUST_INSTALL_ADDR);
					}
				}
			}
			
			BomwebOrderServiceImsDTO bomwebOrderServiceImsDTO = new BomwebOrderServiceImsDTO();
			bomwebOrderServiceImsDTO.setOrderId(order.getOrderId());
			bomwebOrderServiceImsDTO.setCreateBy(order.getCreateBy());
			bomwebOrderServiceImsDTO.setProRata(customer.getProRataRefund());
			bomwebOrderServiceImsDTO.setFsa(order.getServiceIms().getFsa());
			bomwebOrderServiceImsDTO.setLineType(order.getServiceIms().getLineType());
			getOrderDao().insertBomWebOrderServiceIms(bomwebOrderServiceImsDTO);
		} catch (DAOException e) {
			throw e;			
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public String getNewOrderId(String shopCd, String staffId,String orderType){
		logger.info("getNewOrderId");
		String orderId = null;
		try{
			orderId = orderDao.generateNewOrderId(shopCd,staffId,orderType);
		}catch (DAOException de) {
			logger.error("Exception caught in getNewOrderId()", de);
			throw new AppRuntimeException(de);
		}	
		return orderId;
	}
	public String getNewOrderId(String shopCd, String staffId){
		return this.getNewOrderId(shopCd, staffId, null);
	}
	
	public OrderImsUI getBomWebImsOrder(String orderId){
		logger.info("getBomWebImsOrder order id:"+orderId);
		
		try{
			OrderImsUI order = new OrderImsUI();			
			/*
			DateConverter d = new DateConverter(null);    
			String[] datePattern = { "yyyy/MM/dd", "yyyy/mm/dd HH:mm:ss", "yyyy-MM-dd",  "yyyy.MM.dd" };    
			d.setPatterns(datePattern);                   
			ConvertUtils.register(d, java.util.Date.class);
			ConvertUtils.register(d, String.class);*/
			
			//get bomweb_order_ims			
			BeanUtils.copyProperties(order, orderDao.getBomWebOrderIms(orderId).get(0));			
			if (!StringUtil.isBlank(order.getSpecialRequestCd()))
			{
				order.setSpecialRequest("Y");
			}
			
			//get bomweb_order						
			BeanUtils.copyProperties(order, orderDao.getBomWebOrder(orderId).get(0));			
			
			//get appointment
			AppointmentUI appointment = new AppointmentUI();
			List<AppointmentDTO> appointmentList = orderDao.getBomWebAppointment(orderId);
			if (appointmentList != null && appointmentList.size() > 0)
			{
				BeanUtils.copyProperties(appointment, appointmentList.get(0));
			}
			order.setAppointment(appointment);
			
			//get remarks
			List<RemarkDTO> remarks = orderDao.getRemark(orderId);
			if(remarks.size()>0){
				RemarkUI[] remarksui = new RemarkUI[remarks.size()];
				for(int i=0; i<remarks.size(); i++){
					RemarkUI remarkui = new RemarkUI();
					BeanUtils.copyProperties(remarkui, remarks.get(i));
					remarksui[i] = remarkui;
				}
				order.setRemarks(remarksui);
			}
			
			//get customer
			CustomerUI customer = new CustomerUI();
			BeanUtils.copyProperties(customer, orderDao.getBomWebCustomer(orderId).get(0));
			
			//get accout
			AcctUI account = new AcctUI();
			BeanUtils.copyProperties(account, orderDao.getBomWebAcct(orderId).get(0));			
			
			//get payment
			PaymentUI payment = order.isOrderTypeNowRet()?new ImsPaymentUI():new PaymentUI();
			BeanUtils.copyProperties(payment, orderDao.getBomWebPayment(orderId).get(0));
			account.setPayment(payment);
			
			//get salesReferrer
			//SalesReferrerDTO salesReferrerDTO = new SalesReferrerDTO();
			SalesReferrerDTO salesReferrerDTO = null;
			List<SalesReferrerDTO> salesReferrer = orderDao.getBomWebSalesReferrerIms(orderId);
			if(salesReferrer!=null && salesReferrer.size()>0){
				salesReferrerDTO = new SalesReferrerDTO();
				BeanUtils.copyProperties(salesReferrerDTO, salesReferrer.get(0));
			}	
			order.setSalesReferrerDTO(salesReferrerDTO);
			
			//get cust optout info
			CustOptoutInfoUI custoptinfo = null;
			List<CustOptoutInfoDTO> optinfos = orderDao.getCustOptoutInfo(orderId);
			if(optinfos!=null && optinfos.size()>0){
				custoptinfo = new CustOptoutInfoUI();
				BeanUtils.copyProperties(custoptinfo, optinfos.get(0));
				if(order.isOrderTypeNowRet() && StringUtil.isBlank(custoptinfo.getActionInd())){
					custoptinfo.setActionInd(custoptinfo.getDirectMailing()); // suppose all opt with same value, choose anyone to assign value for the action ind
				}
			}			
			
			//get contact
			ContactUI contact = new ContactUI();
			BeanUtils.copyProperties(contact, orderDao.getBomWebContact(orderId).get(0));
			
			customer.setContact(contact);
			customer.setCustOptInfo(custoptinfo);
			customer.setAccount(account);
			order.setCustomer(customer);
			
			//get custaddr
			List<CustAddrDTO> addresses = orderDao.getBomWebCustAddr(orderId);
			for(int i=0; i<addresses.size(); i++){
				if(ImsConstants.IMS_CUST_INSTALL_ADDR.equals(addresses.get(i).getAddrUsage())){
					InstallAddrUI instaddr = new InstallAddrUI();					
					BeanUtils.copyProperties(instaddr, addresses.get(i));
					
					//get addr inventory
					AddrInventoryUI inventory = new AddrInventoryUI();
					BeanUtils.copyProperties(inventory, orderDao.getAddrInventory(orderId).get(0));
					instaddr.setAddrInventory(inventory);
					
					order.setInstallAddress(instaddr);
				}
				if(ImsConstants.IMS_CUST_BILLING_ADDR.equals(addresses.get(i).getAddrUsage())){
					CustAddrUI billaddr = new CustAddrUI();					
					BeanUtils.copyProperties(billaddr, addresses.get(i));
					order.setBillingAddress(billaddr);
				}
			}
			
			//get subscribed items
			List<SubscribedItemDTO> items = orderDao.getSubscribedItem(orderId);
			if(items!=null && items.size()>0){
				SubscribedItemUI[] itemsui = new SubscribedItemUI[items.size()];
				for(int i=0; i<items.size(); i++){
					SubscribedItemUI itemui = new SubscribedItemUI();
					BeanUtils.copyProperties(itemui, items.get(i));
					itemsui[i] = itemui;
				}
				order.setSubscribedItems(itemsui);
			}
			
			//get component
			List<ComponentDTO> components = orderDao.getComponent(orderId);
			if(components!=null && components.size()>0){
				ComponentUI[] componentsui = new ComponentUI[components.size()];
				for(int i=0; i<components.size(); i++){
					ComponentUI componentui = new ComponentUI();
					BeanUtils.copyProperties(componentui, components.get(i));
					componentsui[i] = componentui;
				}
				order.setComponents(componentsui);
			}
			
			//get subscribed Csl Offer
			List<SubscribedCslOfferDTO> subscribedCslOffers = orderDao.getSubscribedCslOffer(orderId);
			if(subscribedCslOffers!=null && subscribedCslOffers.size()>0){
				SubscribedCslOfferDTO[] subscribedcsloffersui = new SubscribedCslOfferDTO[subscribedCslOffers.size()];
				for(int i=0; i<subscribedCslOffers.size(); i++){
					SubscribedCslOfferDTO subscribedcslofferui = new SubscribedCslOfferDTO();
					BeanUtils.copyProperties(subscribedcslofferui, subscribedCslOffers.get(i));
					subscribedcsloffersui[i] = subscribedcslofferui;
				}
				order.setSubscribedCslOffers(subscribedcsloffersui);
			}
			
			
			//get subscribed ims vas parm list
			List<SubscribedCslOfferDTO> subscribedImsVasParm = orderDao.getSubscribedImsVasParm(orderId);
			if(subscribedImsVasParm!=null && subscribedImsVasParm.size()>0){
				SubscribedCslOfferDTO[] imsvasparm = new SubscribedCslOfferDTO[subscribedImsVasParm.size()];
				for(int i=0; i<subscribedImsVasParm.size(); i++){
					SubscribedCslOfferDTO subscribedvarparmui = new SubscribedCslOfferDTO();
					BeanUtils.copyProperties(subscribedvarparmui, subscribedImsVasParm.get(i));
					imsvasparm[i] = subscribedvarparmui;
				}
				order.setSubscribedImsVasParm(imsvasparm);
			}
			
			//get channel
			List<SubscribedChannelDTO> channels = orderDao.getSubscribedChannel(orderId);
			if(channels!=null && channels.size()>0){
				SubscribedChannelUI[] channelsui = new SubscribedChannelUI[channels.size()];
				for(int i=0; i<channels.size(); i++){
					SubscribedChannelUI channelui = new SubscribedChannelUI();
					BeanUtils.copyProperties(channelui, channels.get(i));
					channelsui[i] = channelui;
				}
				order.setSubscribedChannels(channelsui);
			}
			
			//get backend items
			List<SubscribedItemDTO> backendItems = orderDao.getBackendSubscribedItem(orderId);
			if(backendItems!=null && backendItems.size()>0){
				SubscribedItemUI[] backenditemsui = new SubscribedItemUI[backendItems.size()];
				for(int i=0; i<backendItems.size(); i++){
					SubscribedItemUI backenditemui = new SubscribedItemUI();
					BeanUtils.copyProperties(backenditemui, backendItems.get(i));
					backenditemsui[i] = backenditemui;
				}
				order.setSubscribedBackEndItems(backenditemsui);
			}
			
			//get backend channels
			List<SubscribedChannelDTO> backendChannels = orderDao.getBackendSubscribedChannel(orderId);
			if(backendChannels!=null && backendChannels.size()>0){
				SubscribedChannelUI[] backendchannelsui = new SubscribedChannelUI[backendChannels.size()];
				for(int i=0; i<backendChannels.size(); i++){
					SubscribedChannelUI backendchannelui = new SubscribedChannelUI();
					BeanUtils.copyProperties(backendchannelui, backendChannels.get(i));
					backendchannelsui[i] = backendchannelui;
				}
				order.setSubscribedBackEndChannels(backendchannelsui);
			}
			
			setupAppointmentTimeDisplayDetail(order);
			
			//get EDF REF
			
			String edfRef = "";
			edfRef = orderDao.getImsEdfRef(orderId,"1");
			order.setEdfRef(edfRef);
			
			//get support document
			
			List<ImsCollectDocDTO> imsCollectDocList = null;
			imsCollectDocList = orderDao.getImsCollectDocList(orderId);
			order.setImsCollectDocDtoList(imsCollectDocList);

			String isExistingStr = getImsCustomerService.checkImsCustomer(order.getCustomer().getIdDocType(), order.getCustomer().getIdDocNum());
			String isDataPrivacyEmtpy = getImsCustomerService.checkImsDataPrivacy(order.getCustomer().getIdDocType(), order.getCustomer().getIdDocNum(), "PCD");
			String hasBBDailup = "N";
			if("Y".equalsIgnoreCase(isExistingStr)&&!"Y".equalsIgnoreCase(isDataPrivacyEmtpy)){
				hasBBDailup = "Y";
			}
			if ("NTV-A".equals(order.getOrderType()) || order.isOrderTypeNowRet()) { // martin, 20190806, fix INC000000220323
				hasBBDailup = "N";
				String isExistingStrNtv = getImsCustomerService.checkImsCustomerNowTV(order.getCustomer().getIdDocType(), order.getCustomer().getIdDocNum());
				if ("NTV-A".equals(order.getOrderType())) {
					String isDataPrivacyEmtpyNtv = getImsCustomerService.checkImsDataPrivacy(order.getCustomer().getIdDocType(), order.getCustomer().getIdDocNum(), "TV");
					if ("Y".equalsIgnoreCase(isExistingStrNtv)&&!"Y".equalsIgnoreCase(isDataPrivacyEmtpyNtv)) {
						hasBBDailup = "Y";
					}
				} else {
					hasBBDailup = isExistingStrNtv;
				}
			}
			//added by steven 20140207 start
			order.setHasBBDailup(hasBBDailup);
			//added by steven 20140207 end
			

			//get service ims
			List<BomwebOrderServiceImsDTO> services = orderDao.getBomwebOrderServiceIms(orderId);
			if(services!=null && services.size()>0){
				order.setServiceIms(services.get(0));
			};
			
			//getBomwebOrderL2Job
			List<BomwebOrderL2JobDTO> bomwebOrderL2Job = orderDao.getBomwebOrderL2Job(orderId);
			if(bomwebOrderL2Job!=null && bomwebOrderL2Job.size()>0){
				order.setBomwebOrderL2JobList(bomwebOrderL2Job.toArray(new BomwebOrderL2JobDTO[bomwebOrderL2Job.size()]));
			};
					


			if(order.isOrderTypeNowRet()){
				//get subs offer						
				List<BomwebSubscribedOfferImsDTO> offers = orderDao.getBomwebSubscribedOfferIms(orderId);
				if(offers!=null && offers.size()>0){
					order.setSubscribedOffersIms(offers.toArray(new BomwebSubscribedOfferImsDTO[0]));
				};
				order.setShortenUrlAf(imsSMSService.getShortenUrl(order.getOrderId()));
			}
			
			if(order.isOrderTypeNowRet())
			{
				getExtraDetails(order);
			}
			
			if ("NTV-A".equals(order.getOrderType())) {
				String shortenUrlAf = orderDao.getShortenUrl(order.getOrderId());
				String shortenUtlQr = orderDao.getShortenUrlQrCode(order.getOrderId());
				if (!StringUtils.isEmpty(shortenUrlAf) && !"Not Exist".equals(shortenUrlAf)) {
					order.setShortenUrlAf(shortenUrlAf);
				}
				if (!StringUtils.isEmpty(shortenUtlQr) && !"Not Exist".equals(shortenUtlQr)) {
					order.setShortenUrlQr(shortenUtlQr);
				}
//				order.setShortenUrlAf(imsSMSService.getShortenUrl(order.getOrderId()));
//				order.setShortenUrlQr(imsSMSService.getShortenUrlQrCode(order.getOrderId()));

			}
			
			//logger.info(gson.toJson(order));
			this.setCreateByUserDto(order);
			
			return order;
			
		}catch (DAOException de) {
			logger.error("Exception caught in getBomWebImsOrder()", de);
			throw new AppRuntimeException(de);
		}catch (Exception e){
			logger.error("Exception caught in getBomWebImsOrder()", e);
			throw new AppRuntimeException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public void setCreateByUserDto(OrderImsUI order){		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
			String date = sdf.format(order.getAppDate()); 
			BomSalesUserDTO s=orderDao.getCreateByStaff(order.getCreateBy(), date);
			if(s!=null){
				order.setCreateByUser(s);
			}else{
				throw new DAOException();
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(loginService==null){
				logger.error("setCreateByUserDto, loginService is null");
			}else{
				logger.info("setCreateByUserDto, loginService is not null");
				order.setCreateByUser(loginService.getSalesAssign(order.getCreateBy()));	
			}
		}
	}
	
	protected void getExtraDetails(OrderImsUI order) throws Exception
	{
		logger.info("getExtraDetails(OrderImsUI order) - start");
		try{
			ImsInstallationUI customer = new ImsInstallationUI();
			
			BeanUtils.copyProperties(customer, order.getCustomer());
			
			order.setCustomer(customer);
			
			List<BomwebThirdPartyApplnDTO> thirdPartyDTOList = getOrderDao().getBomWebThirdPartyAppln(order.getOrderId());
			
			if (thirdPartyDTOList != null && thirdPartyDTOList.size() > 0)
			{
				customer.setIsThirdPartyApplication("Y");
				customer.setApplicantDocType(thirdPartyDTOList.get(0).getAppntDocId());
				customer.setApplicantTitle(thirdPartyDTOList.get(0).getTitle());
				customer.setApplicantLastName(thirdPartyDTOList.get(0).getAppntLastName());
				customer.setApplicantFirstName(thirdPartyDTOList.get(0).getAppntFirstName());
				customer.setApplicantDocType(thirdPartyDTOList.get(0).getAppntDocType());
				customer.setApplicantIDVerifiedInd(thirdPartyDTOList.get(0).getAppntIdVerifiedInd());
				customer.setApplicantIDNum(thirdPartyDTOList.get(0).getAppntDocId());
				customer.setRelationshipwithCustomer(thirdPartyDTOList.get(0).getRelationshipCd());
				customer.setApplicantContactNo(thirdPartyDTOList.get(0).getAppntContactNum());
				customer.setOtherRelationship(thirdPartyDTOList.get(0).getRemarks());				
			}
			
			List<BomwebBillingAddrDTO> bomwebBillingAddrDTOList = getOrderDao().getBomwebBillingAddr(order.getOrderId());
			if (bomwebBillingAddrDTOList != null && bomwebBillingAddrDTOList.size() > 0)
			{
				AcctUI accountUI = customer.getAccount();
				accountUI.setBillMedia("P");
				accountUI.setKeepExistingBillingAddress("N");
				ImsPaymentUI payment = (ImsPaymentUI) accountUI.getPayment();
				payment.setIsFreeFormat("Y");
				BomwebBillingAddrDTO bomwebBillingAddrDTO=bomwebBillingAddrDTOList.get(0);
				payment.setFreeFormatAddress1(bomwebBillingAddrDTO.getAddrLine1());
				payment.setFreeFormatAddress2(bomwebBillingAddrDTO.getAddrLine2());
				payment.setFreeFormatAddress3(bomwebBillingAddrDTO.getAddrLine3());
				payment.setFreeFormatAddress4(bomwebBillingAddrDTO.getAddrLine4());
				accountUI.setPayment(payment);
			}
			
			List<BomwebOrderServiceImsDTO> bomwebOrderServiceImsDTOList = getOrderDao().getBomwebOrderServiceIms(order.getOrderId());
			if (bomwebOrderServiceImsDTOList!=null && bomwebOrderServiceImsDTOList.size()>0)
			{
				customer.setProRataRefund(bomwebOrderServiceImsDTOList.get(0).getProRata());
				order.setProRataRefund(bomwebOrderServiceImsDTOList.get(0).getProRata());
			}
		} catch (Exception e) {
			throw e;			
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public void cancelPendingOrder(){
		logger.info("cancelPendingOrder");				
		try{
			
			orderDao.updateSuspendedOrderStatus(
					ImsConstants.IMS_ORDER_SUSPEND_AWAIT_CREDIT_CARD, 7);
			orderDao.updateSuspendedOrderStatus(
					ImsConstants.IMS_ORDER_SUSPEND_WAIT_CASH_PREPAYMENT, 7);
			orderDao.updateSuspendedOrderStatus(
					ImsConstants.IMS_ORDER_SUSPEND_AWAIT_NOWTV_CHANNEL_SELECTION, 1);
			orderDao.updateWaitingOrderStatus(
					ImsConstants.IMS_ORDER_STATUS_APPROVED, 30);
			orderDao.updateWaitingOrderStatus(
					ImsConstants.IMS_ORDER_STATUS_APPROVALREJ, 30);
			
			
		}catch (DAOException de) {
			logger.error("Exception caught in updateSyncOrderStatus()", de);
			throw new AppRuntimeException(de);
		}			
	}
	
	private void submitMarketApproval(OrderImsUI order, String wqNatureId, String remark){
		logger.info("submitMarketApproval order_id:"+order.getOrderId()+" wqNatureId:"+wqNatureId);
		WorkQueueDTO wqDTO = new WorkQueueDTO();   		
		setWqDto(order, wqDTO,"Y");		
		com.pccw.wq.schema.dto.RemarkDTO wqRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		wqRemark.setRemarkContent(remark);
		
		WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
		wqNatureDTO.setWorkQueueType("SB-PCD"); //IMS marketing approval
		wqNatureDTO.setWorkQueueSubType("APPROVAL"); //
		wqNatureDTO.setWorkQueueNatureId(wqNatureId);
		wqNatureDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {wqRemark} );
		wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
		
		try {
			workQueueService.createWorkQueue(
					wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, "SB-IMS");						
		} catch (Exception e) {
			//temp use
			System.out.println(ExceptionUtils.getFullStackTrace(e));
		}
	}
		
	
	public void submitCashPermitWQ(OrderImsUI order){
		logger.info("submitCashPermitWQ");		
			submitMarketApproval(order, "102", 	order.getCustomer().getAccount().getPayment().getTextAreaInfo()); //cash approval nature id 102
	}
	
	public void submitWaivePrepayWQ(OrderImsUI order){
		logger.info("submitWaviePrepayWQ");		
			submitMarketApproval(order, "103",	order.getCustomer().getAccount().getPayment().getTextAreaInfoWP()); //waive prepay nature id 103		
	}
	
	public void submitWaiveOTCWQ(OrderImsUI order){
		logger.info("submitWavieOTCWQ");		
			submitMarketApproval(order, "105",	order.getCustomer().getAccount().getPayment().getTextAreaInfoWO()); //waive otc nature id 105	
	}
	
	public void submitDiscountedOTCWQ(OrderImsUI order){
		logger.info("submitDiscountedOTCWQ");					
		submitMarketApproval(order, "106",	order.getCustomer().getAccount().getPayment().getTextAreaInfoDO()+ ((char)10)); //discounted otc nature id 106	
	}
	
	
	private void setupAppointmentTimeDisplayDetail(OrderImsUI order){			
//		logger.info("setupAppointmentTimeDisplayDetail");
		
		if(order.getAppointment().getAppntStartDate()!=null || order.getSrvReqDate()!=null){
			Calendar appntStartTime = Calendar.getInstance();
			Calendar appntEndTime = Calendar.getInstance();
			
			//set timeslot display
			if(order.getAppointment().getAppntStartDate()!=null && 
					!"Y".equals(order.getInstallAddress().getAddrInventory().getResourceShortage())){			
				appntStartTime.setTime(order.getAppointment().getAppntStartDate());			
				appntEndTime.setTime(order.getAppointment().getAppntEndDate());
				
				if(order.getInstallAddress().getAddrInventory().getTechnology().equals("PON") &&
						appntStartTime.get(Calendar.HOUR_OF_DAY)==18 && appntEndTime.get(Calendar.HOUR_OF_DAY)==20){				
					appntEndTime.set(Calendar.HOUR_OF_DAY, 22);				
				}			
			}else{
				appntStartTime.setTime(order.getSrvReqDate());
				appntStartTime.set(Calendar.HOUR_OF_DAY, 9);
				appntStartTime.set(Calendar.MINUTE, 0);
				
				appntEndTime.setTime(order.getSrvReqDate());
				appntEndTime.set(Calendar.HOUR_OF_DAY, 18);
				appntEndTime.set(Calendar.MINUTE, 0);
			}						
			
			order.getAppointment().setAppntStartDateDisplay(appntStartTime.getTime());
			order.getAppointment().setAppntEndDateDisplay(appntEndTime.getTime());
		}
		
	}
	
	//Tony added
	
	public void submitSBWQ(OrderImsUI order, String reason) {
		logger.info("submitSBWQ reason:"+reason);
		String bomWebRemarkDtl="";
		String wqNatureId = "";	
		
		String COrder_reason="";
		if(reason.length()>4&&reason.indexOf("_PT")!=-1){
			COrder_reason = reason.substring(7, reason.length());
			reason = reason.substring(0, 7);
		}else if (reason.length()>4&&reason.indexOf("_PT")==-1){
			COrder_reason = reason.substring(4, reason.length());
			reason = reason.substring(0, 4);
		}
		logger.info("COrder_reason:"+COrder_reason);
		logger.info("reason:"+reason);
		
		if(reason.equalsIgnoreCase(ImsConstants.WQ_Reason_cOrder)||
				reason.equalsIgnoreCase(ImsConstants.WQ_Reason_cOrder_PT)){
			if("13".equals(COrder_reason)){
				bomWebRemarkDtl="EYE C order WQ";
				wqNatureId = ImsConstants.WQ_nature_PCD_cOrder_EYE;
			}else if("12".equals(COrder_reason)){
				bomWebRemarkDtl="VI C order WQ";
				wqNatureId = ImsConstants.WQ_nature_PCD_cOrder_VI;
			}else if("11".equals(COrder_reason)){
				bomWebRemarkDtl="EYE TV C order WQ";
				wqNatureId = ImsConstants.WQ_nature_PCD_cOrder_VI_EYE;
			}			
			if(reason.equalsIgnoreCase(ImsConstants.WQ_Reason_cOrder_PT)){
				bomWebRemarkDtl += " PT";
			}	
			imsCOrderWorkQueue(order, wqNatureId, bomWebRemarkDtl); 
		}else if(reason.equalsIgnoreCase(ImsConstants.WQ_Reason_MissMatch)){//Only direct Sales has MisMatch
				dsMisMatchWQ(order, ImsConstants.WQ_nature_DS_Mismatch, null, "DS Cust Info MisMatch WQ"); 	
		}else if(reason.equalsIgnoreCase(ImsConstants.WQ_Reason_both_cOrder_MissMatch)){//Only direct Sales has MisMatch
			if("13".equals(COrder_reason)){
				dsMisMatchWQ(order, ImsConstants.WQ_nature_DS_Mismatch, 
						ImsConstants.WQ_nature_co_Mismatch_EYE, "Both EYE C order And Cust Info MisMatch WQ"); 
			}else if("12".equals(COrder_reason)){
				dsMisMatchWQ(order, ImsConstants.WQ_nature_DS_Mismatch, 
						ImsConstants.WQ_nature_co_Mismatch_VI, "Both VI C order And Cust Info MisMatch WQ"); 	
			}else if("11".equals(COrder_reason)){
				dsMisMatchWQ(order, ImsConstants.WQ_nature_DS_Mismatch, 
						ImsConstants.WQ_nature_co_Mismatch_EYE_VI, "Both EYE TV C order And Cust Info MisMatch WQ"); 
			}	
		}	
		
		if(!wqNatureId.equalsIgnoreCase("")){
			logger.error("error: no wqNature, order id:"+order.getOrderId()+ " reason:"+reason);
		}
	}	
	
	private void dsMisMatchWQ(OrderImsUI order, String wqNatureId, String wqNatureId2, String temp_BomWeb_Remark_dtl){
		logger.info("dsMisMatchWQ  temp_BomWeb_Remark_dtl:"+temp_BomWeb_Remark_dtl+
				"  wqNatureId:"+wqNatureId+"  wqNatureId2:"+wqNatureId2+	"  orderId:"+order.getOrderId());
		WorkQueueDTO wqDTO = new WorkQueueDTO();
		setWqDto(order, wqDTO,"N");
		com.pccw.wq.schema.dto.RemarkDTO wqRemark = setWqRemarks(order);
		wqDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {wqRemark} );

		logger.info("wqNatureDTO");
		WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
		WorkQueueNatureDTO wqNatureDTO2 = null;
		wqNatureDTO.setWorkQueueType("SB-PCD");
		wqNatureDTO.setWorkQueueSubType("FULL_WQ");
		wqNatureDTO.setWorkQueueNatureId(wqNatureId);
		if(wqNatureId2!=null){
			wqNatureDTO2 = new WorkQueueNatureDTO();
			wqNatureDTO2.setWorkQueueType("SB-PCD");
			wqNatureDTO2.setWorkQueueSubType("FULL_WQ");
			wqNatureDTO2.setWorkQueueNatureId(wqNatureId2);
		}
		if(wqNatureDTO2==null){
			wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
		}else{
			wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO, wqNatureDTO2});
		}
		logger.info("go to wq, orderId:"+order.getOrderId());
		try {
			imsCOrderWQService.insertBomwebRemark(order.getOrderId(), temp_BomWeb_Remark_dtl+" created", "ImsDsWQ");
			WorkQueueDTO retDto=null;
			if(wqNatureDTO2==null){
				 retDto = workQueueService.createWorkQueue(	wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, "DS-PCD");
			}else{
				 retDto = workQueueService.createWorkQueue(wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO, wqNatureDTO2}, "DS-PCD");
			}
			if(retDto.getDocuments().length>1){
				WorkQueueDocumentDTO[] requiredDocuments = retDto.getDocuments();		
				requiredDocuments[0].setAttachmentFullPath(tmpFilePath+order.getOrderId()+"/"+order.getOrderId()+"_AF_FS.pdf");
				requiredDocuments[1].setAttachmentFullPath(getFileNameOfLatestSupportDocHKID(order.getOrderId()));
				workQueueService.attachWorkQueueDocument(retDto, requiredDocuments, "DS-PCD");
			}
		} catch (Exception e) {
			//temp use
			logger.info(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	public String getFileNameOfLatestSupportDocHKID(String orderId) {
		try {
			return this.orderDao.getFileNameOfLatestSupportDocHKID(orderId,tmpFilePath);
		} catch (DAOException e) {
			e.printStackTrace();
			return tmpFilePath+"IMS_I007_0.pdf";
		}
	}
	
	public void dsAlertWQ(OrderImsUI order, String wqNatureId, String temp_BomWeb_Remark_dtl, String fsRemarks){
		logger.info("dsAlertWQ  rmk:"+temp_BomWeb_Remark_dtl+
				"  wqNatureId:"+wqNatureId+"   orderId:"+order.getOrderId());
		WorkQueueDTO wqDTO = new WorkQueueDTO();
		setWqDto(order, wqDTO,"N");
		com.pccw.wq.schema.dto.RemarkDTO wqRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		if(ImsConstants.DS_Alert_WQ_DS_S_B4_AMEND_FS.equals(wqNatureId)){ 
			wqRemark.setRemarkContent(fsRemarks);
		}else{
			wqRemark = setWqRemarks(order);
		}
		wqDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {wqRemark} );

		WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
		String pUser = "DS-PCD";
		wqNatureDTO.setWorkQueueType("SB-PCD");
		if("NTV-A".equals(order.getOrderType()) || order.isOrderTypeNowRet()){
			wqNatureDTO.setWorkQueueType("SB-NOW");
			pUser = "DS-NOW";
		}
		wqNatureDTO.setWorkQueueSubType("FOLLOWUP");
		wqNatureDTO.setWorkQueueNatureId(wqNatureId);
		wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
		logger.debug("wqDTO:"+gson.toJson(wqDTO));
		logger.info("go to wq, ds alert, orderId:"+order.getOrderId());
		try {
			imsCOrderWQService.insertBomwebRemark(order.getOrderId(), temp_BomWeb_Remark_dtl+" created", "ImsDsWQ");
			WorkQueueDTO retDto= workQueueService.createWorkQueue(	wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, pUser);
			if(retDto!=null&&retDto.getDocuments()!=null&&retDto.getDocuments().length>0){
				WorkQueueDocumentDTO[] requiredDocuments = retDto.getDocuments();		
				requiredDocuments[0].setAttachmentFullPath(tmpFilePath+order.getOrderId()+"/"+order.getOrderId()+"_AF_FS.pdf");
				workQueueService.attachWorkQueueDocument(retDto, requiredDocuments, pUser);
			}
		} catch (Exception e) {
			//temp use
			System.out.println(ExceptionUtils.getFullStackTrace(e));
		}
	}

	public void setWqDto(OrderImsUI order, WorkQueueDTO wqDTO, String approval) {
		logger.info("setWqDto order_id:"+order.getOrderId()+" approval:"+approval);
		wqDTO.setSbId(order.getOrderId());
		wqDTO.setSbDtlId("1"); //IMS default "1"
		String shopOrTeam = order.getShopCd();
		if("Y".equals(order.getIsCC())||"DS".equals(order.getImsOrderType())){
			shopOrTeam = order.getSourceCd();
		}
		wqDTO.setSbShopCode(shopOrTeam);//should be SBO for online sales
		wqDTO.setTypeOfService("FSA");// Nanon said FSA
		wqDTO.setServiceId(order.getCustomer().getServiceNum());
		if("N".equals(approval)){
			if(order.getAppointment().getAppntStartDate()!=null){
				wqDTO.setSrd(Utility.date2String(order.getAppointment().getAppntStartDate(), "yyyyMMdd"));
			}else{
				wqDTO.setSrd(Utility.date2String(order.getSrvReqDate(), "yyyyMMdd"));
			}
		}
		setWqAttb(order, wqDTO);
	}
	
	//steven added end
	
	private void imsCOrderWorkQueue(OrderImsUI order, String wqNatureId, String temp_BomWeb_Remark_dtl){
		logger.info("Ims C Order Work Queue, temp_BomWeb_Remark_dtl:"+temp_BomWeb_Remark_dtl+ "  apprID:"+wqNatureId);
		WorkQueueDTO wqDTO = new WorkQueueDTO();
		setWqDto(order, wqDTO,"N");
		com.pccw.wq.schema.dto.RemarkDTO wqRemark = setWqRemarks(order);
		WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
		wqNatureDTO = imsCOrderWQService.getTypeSubtypeByNatureID(wqNatureId);
		wqNatureDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {wqRemark} );
		wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
		temp_BomWeb_Remark_dtl=temp_BomWeb_Remark_dtl+" created";
		logger.info("go to wq");
		try {
			imsCOrderWQService.insertBomwebRemark(order.getOrderId(), temp_BomWeb_Remark_dtl+" created", "ImsDsWQ");
			WorkQueueDTO retDto = workQueueService.createWorkQueue(wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, "SBIMS");
			logger.debug("retDto:"+gson.toJson(order));
			WorkQueueDocumentDTO[] requiredDocuments = retDto.getDocuments();	
			requiredDocuments[0].setAttachmentFullPath(tmpFilePath+order.getOrderId()+"/"+order.getOrderId()+"_AF_FS.pdf");
			workQueueService.attachWorkQueueDocument(retDto, requiredDocuments, "SBIMS");
		} catch (Exception e) {
			//temp use
			System.out.println(ExceptionUtils.getFullStackTrace(e));
		}
	}

	public com.pccw.wq.schema.dto.RemarkDTO setWqRemarks(OrderImsUI order) {
		logger.info(order.getCustomer().getIdDocType()+" "+ order.getCustomer().getIdDocNum());
		String resourceShortage = order.getInstallAddress().getAddrInventory().getResourceShortage();
		String blackListCust = imsAddressInfoService.isBlacklistCust(order.getCustomer().getIdDocType(), order.getCustomer().getIdDocNum());
		String blackListAddr = imsAddressInfoService.getBlacklistAddr(order.getInstallAddress().getSerbdyno(), order.getInstallAddress().getFloorNo());
		com.pccw.wq.schema.dto.RemarkDTO wqRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		logger.info("blackListAddr:"+blackListAddr);
		logger.info("blackListCust:"+blackListCust);
		logger.info("resourceShortage:"+resourceShortage);
		blackListAddr = ((char)10)+"Black List Address:"+blackListAddr;
		blackListCust = ((char)10)+"Black List Customer:"+blackListCust;
		resourceShortage = ((char)10)+"Resource Shortage:"+resourceShortage;
		//wq remark for fax serial by Tony
		String fsRemark = "";
		logger.debug("order.getImsCollectDocDtoList(): "+gson.toJson(order.getImsCollectDocDtoList())); 
		if(order.getImsCollectDocDtoList() != null && order.getImsCollectDocDtoList().size()>0){
			for (int i=0; i<order.getImsCollectDocDtoList().size();i++){
				if("Y".equalsIgnoreCase(order.getImsCollectDocDtoList().get(i).getCollectedInd())){
					fsRemark = order.getImsCollectDocDtoList().get(i).getFaxSerial() + "\n";
					logger.debug("fsRemark: "+fsRemark); 
				}
			}
			if (fsRemark != null && !"".equals(fsRemark)){
				fsRemark = "Fax Serial(s):\n" + fsRemark +"\n";
			}
		}else{
			fsRemark = "";
		}
		String edfRef = null;
		if(order.getEdfRef()!= null && !order.getEdfRef().isEmpty()){
			edfRef = "EDF No.: " + order.getEdfRef() + "\n\n";
		}else{
			edfRef = "";
		}
		String custName = "null".equals(order.getCustomer().getLastName())?"":order.getCustomer().getLastName() + " " + order.getCustomer().getFirstName();
		if(custName!= null && !custName.isEmpty()){
			custName = "Customer Name: " + custName + "\n";
		}else{
			custName = "";
		}
		String staffNo = order.getCreateBy();
		if(staffNo!= null && !staffNo.isEmpty()){
			staffNo = "Staff No.: " + staffNo + "\n";
		}else{
			staffNo = "";
		}
		String salesCode = order.getSalesCd();
		if(salesCode!= null && !salesCode.isEmpty()){
			salesCode = "Salesman Code: " + salesCode + "\n";
		}else{
			salesCode = "";
		}
		String salesChannel = LookupTableBean.getInstance().getImsDSAppMethodMap().get(order.getShopCd());
		if(salesChannel==null||salesChannel.isEmpty()){
			salesChannel = order.getSalesChannel();
		}
		if(salesChannel!= null && !salesChannel.isEmpty()){
			salesChannel = "Sales Channel: " + salesChannel + "\n";
		}else{
			salesChannel = "";
		}
		String teamCode = order.getShopCd();
		if(teamCode!= null && !teamCode.isEmpty()){
			teamCode = "Team/ Shop Code: " + teamCode + "\n";
		}else{
			teamCode = "";
		}
//		logger.debug("Team/ Shop Code: "+gson.toJson(order)); 
		if("DS".equals(order.getImsOrderType())){
			teamCode = "Team/ Shop Code: " + order.getSourceCd() + "\n";
		}
		String salesContactNum = order.getSalesContactNum();
		if(salesContactNum!= null && !salesContactNum.isEmpty()){
			salesContactNum = "Sales Contact No: " + salesContactNum + "\n";
		}else{
			salesContactNum = "";
		}
		String fsa = order.getCustomer().getServiceNum();
		if(fsa!= null && !fsa.isEmpty()){
			fsa = "FSA:" + fsa + "\n";
		}else{
			fsa = "";
		}
		
		/* generate one time charge remark */

		String oldItemString = "0";
		String newItemString = "";

		String separator = "";
		for (SubscribedItemUI i:order.getSubscribedItems()) {
			newItemString += separator + i.getId();
			separator = ",";
		}
		
		List<IOOfferOTCSchemeDTO> ioOfferList = new ArrayList<IOOfferOTCSchemeDTO>();
		
		try {
			ioOfferList = orderDao.getIOProdId(oldItemString, newItemString);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String inPrdIdString = "";
		String outPrdIdString = "";
		
		String inOTCRemark = "";
		
		if(ioOfferList!=null&&ioOfferList.size()>0){
			for(IOOfferOTCSchemeDTO dto:ioOfferList){
				if("I".equalsIgnoreCase(dto.getIOInd())){
					inPrdIdString += dto.getProdId() + ",";
				}
				if("O".equalsIgnoreCase(dto.getIOInd())){
					outPrdIdString += dto.getProdId() + ",";
				}
			}

			if(!"".equalsIgnoreCase(inPrdIdString)){
				inPrdIdString = inPrdIdString.substring(0, inPrdIdString.length()-1);
			}else{
				inPrdIdString = "0";
			}
			if(!"".equalsIgnoreCase(outPrdIdString)){
				outPrdIdString = outPrdIdString.substring(0, outPrdIdString.length()-1);
			}else{
				outPrdIdString = "0";
			}
			
			List<IOOfferOTCSchemeDTO> ioOTCList = new ArrayList<IOOfferOTCSchemeDTO>();
			
			try {
				ioOTCList = bomDao.getIOOneTimeCharge(inPrdIdString, outPrdIdString);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(ioOTCList!=null&&ioOTCList.size()>0){
				for(IOOfferOTCSchemeDTO dto:ioOTCList){
					if("I".equalsIgnoreCase(dto.getIOInd())){
						inOTCRemark += "Add OneTime Chrg: " + dto.getSchemeCode() + " " + dto.getSchemeDesc() + "\\n";
					}
				}
			}
		}
		
		/* generate one time charge remark (end) */
		
		//end
		wqRemark.setRemarkContent(
				edfRef+
				fsa+"\n"+
				custName+
				((blackListAddr!=null)?blackListAddr:"")+
				((blackListCust!=null)?blackListCust:"")+
				((resourceShortage!=null)?resourceShortage:"")+"\n\n"
				+fsRemark+
				staffNo+
				salesCode+
				salesChannel+
				teamCode+
				salesContactNum+
				inOTCRemark
				);// Remark, input FSA = ServiceNum, which get from raymond's stor prod
		return wqRemark;
	}

	public void setWqAttb(OrderImsUI order, WorkQueueDTO wqDTO) {
		logger.info("setWqAttb order_id:"+order.getOrderId());
		WorkQueueAttributeDTO attb = new WorkQueueAttributeDTO();
		attb.setAttbName("SB_ISSUE_BY");
		try { 
			String realSalesCD= imsAmendOrderDao.getRealStaffID(order.getSalesCd());
			String realCreateBy= imsAmendOrderDao.getRealStaffID(order.getCreateBy());
			if(realSalesCD!=null && imsAmendOrderDao.isStaffExist(realSalesCD)){
				attb.setAttbValue(realSalesCD);
			}else if(imsAmendOrderDao.isStaffExist(order.getSalesCd())){
				attb.setAttbValue(order.getSalesCd());
			}else if(realCreateBy!=null && imsAmendOrderDao.isStaffExist(realCreateBy)){
				attb.setAttbValue(realCreateBy);
			}else{
				attb.setAttbValue(order.getCreateBy());
			}
		} catch (DAOException e1) {
			e1.printStackTrace();
		}

		if(attb.getAttbValue()==null){
			attb.setAttbValue(order.getCreateBy());
		}
		logger.info("getAttbValue:"+attb.getAttbValue());
		wqDTO.addAttribute(attb);

		add_PRIORITY_WP_ID(order, wqDTO, "BOM2017038");
		add_PRIORITY_WP_ID(order, wqDTO, "delightToCSL");
	}
	
	//Tony added end
	public void add_PRIORITY_WP_ID(OrderImsUI order, WorkQueueDTO wqDTO, String grpId) {
		try {
			String priorityWpId = imsAmendOrderDao.getPriorityWpId(order.getSalesCd(), order.getCreateBy(), grpId);
			if (priorityWpId != null) {
				WorkQueueAttributeDTO attb2 = new WorkQueueAttributeDTO();
				attb2.setAttbName("PRIORITY_WP_ID");
				attb2.setAttbValue(priorityWpId);
				wqDTO.addAttribute(attb2);
			}
		} catch (DAOException e1) {
			logger.error("add_PRIORITY_WP_ID Error: " +
					" order: " + gson.toJson(order) +
					" wqDTO: " + gson.toJson(order) +
					" grpId: " + gson.toJson(grpId)
					, e1);
		}
	}
	
	//steven added 20131209
	public void orderStatusAlertWQ(OrderImsUI order, String wqNatureId, String reasonCd){
		logger.info("orderStatusAlertWQ"+"\twqNatureId:"+wqNatureId);
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date current = new Date();
		String rmk=
			"Suspended by:"+order.getSalesCd()+
			"\nOrder Status:Suspended\nReasonCD:"+reasonCd+
			"\nReason:"+imsCOrderWQService.getSuspendReasonByCode(reasonCd)+
			"\nSuspend Time:"+sdFormat.format(current);
		if(imsCOrderWQService.checkPendingAlertWQExist(order.getOrderId())){
			logger.info("checkPendingAlertWQExist, insert remarks");
			imsCOrderWQService.insertAlertWQRemark(rmk, order.getOrderId(), order.getSalesCd());
		}else{
			logger.info("no checkPendingAlertWQExist, create wq");
			WorkQueueDTO wqDTO = new WorkQueueDTO();   
			setWqDto(order, wqDTO,"N");
			com.pccw.wq.schema.dto.RemarkDTO wqRemark = new com.pccw.wq.schema.dto.RemarkDTO();
			wqRemark.setRemarkContent(rmk);
	
			logger.info("wqNatureDTO");
			WorkQueueNatureDTO wqNatureDTO = imsCOrderWQService.getTypeSubtypeByNatureID(wqNatureId);
			wqNatureDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {wqRemark} );
			wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
			logger.info("go to wq");
			try {
				workQueueService.createWorkQueue(wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, order.getSalesCd());
				imsCOrderWQService.updateStatusAlertWQ(order.getOrderId(), order.getSalesCd());
			} catch (Exception e) {
				System.out.println(ExceptionUtils.getFullStackTrace(e));
			}
		}
	}
	//steven added 20131209 end
	

	public ImsOrderDAO getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(ImsOrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	public WorkQueueService getWorkQueueService() {
		return workQueueService;
	}
	
	//Tony added
    
	public void setImsAddressInfoService(ImsAddressInfoService imsAddressInfoService) {
		this.imsAddressInfoService = imsAddressInfoService;
	}
	public ImsAddressInfoService getImsAddressInfoService() {
		return imsAddressInfoService;
	}
    
	public void setImsCOrderWQService(ImsCOrderWQService imsCOrderWQService) {
		this.imsCOrderWQService = imsCOrderWQService;
	}
	public ImsCOrderWQService getImsCOrderWQService() {
		return imsCOrderWQService;
	}
	
	//Tony added end

	public void setWorkQueueService(WorkQueueService workQueueService) {
		this.workQueueService = workQueueService;
	}		


	public ImsAddressInfoDAO getAddressDao() {
		return addressDao;
	}


	public void setAddressDao(ImsAddressInfoDAO addressDao) {
		this.addressDao = addressDao;
	}


	public String getTmpFilePath() {
		return tmpFilePath;
	}


	public void setTmpFilePath(String tmpFilePath) {
		this.tmpFilePath = tmpFilePath;
	}		
	
	public List<String> getPendingOrderIdList(int withinMinute){
		logger.info("getPendingOrderIdList");
		List<String> pendingOrderList = new ArrayList<String>();
		try{
			
			for(OrderDTO pendingOrder : orderDao.getPendingOrderList(withinMinute)){
				pendingOrderList.add(pendingOrder.getOrderId());
			}
			
		}catch (DAOException de) {
			logger.error("Exception caught in getPendingOrderIdList()", de);
			throw new AppRuntimeException(de);
		}
		
		return pendingOrderList;
	}

	

	public Boolean needSuspendWQ(String orderStatus, String createBy){
		return imsCOrderWQService.needSuspendWQ( orderStatus,  createBy);
	}

	public void setImsAmendOrderDao(ImsOrderAmendDAO imsAmendOrderDao) {
		this.imsAmendOrderDao = imsAmendOrderDao;
	}

	public ImsOrderAmendDAO getImsAmendOrderDao() {
		return imsAmendOrderDao;
	}
	

	public Boolean isStaffExist(String salesCD) throws DAOException{
		return imsAmendOrderDao.isStaffExist(salesCD);
		
	}
	public List<ImsInstallationUI> getImsCustomer(String idDocType, String idDocNum){
		return this.getGetImsCustomerService().getImsCustomer(idDocType, idDocNum);
	}

	public void setBomDao(ImsBomOrderDAO bomDao) {
		this.bomDao = bomDao;
	}

	public ImsBomOrderDAO getBomDao() {
		return bomDao;
	}
	
	
	public String genQRCodeString(OrderImsUI order, String depInd, String amt) {
		String merchantCode = "055";
		String billType = "62";
		String billingAccount="";
		String dragenAccount="";
		//String billingAmount= StringUtils.leftPad(order.getPrepayCash() , 10, "0");
		String billingAmount = StringUtils.leftPad(String.valueOf( ((Double)(Double.parseDouble(amt) * 100)).intValue() ) , 10, "0");
		
		try {
			billingAccount = bomDao.getPrimaryAcct();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppRuntimeException("genQRCodeString error");
			//billingAccount = "1200325490";
		}		
		
		order.setPrimaryAcctNo(billingAccount);
		
		dragenAccount = genDrgnAccount(billingAccount, depInd); // throw AppRuntimeException if failed
			
		return merchantCode+billType+dragenAccount+billingAmount;
	}
	
	public String genDrgnAccount(String billingAccount, String depInd) {
		String dragenAccount = "";
		
		try {
			dragenAccount = bomDao.getVIDepDrgnAcctnb(depInd, billingAccount);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppRuntimeException("genDrgnAccount error");
			//dragenAccount= "68105732901063";
		}
		logger.info("dragenAccount:"+dragenAccount);
		
		return dragenAccount;
	}
	
	public void saveQRCodeFile(OrderImsUI order, String billType, String amt){
		String str = this.genQRCodeString(order, billType, amt);
		byte[] file =  QRCode.from(str).to(ImageType.PNG).stream().toByteArray();
		
		saveQRCodeFile(file, order.getOrderId()+"_QR.jpg", order);
	}
	
	public void saveQRCodeFile(byte[] file, String fileName, OrderImsUI order) {
		File directory;
		File QRCodeName;
		try {
			if ("NTV-A".equals(order.getOrderType()) || order.isOrderTypeNowRet()) {
				directory = new File(tmpFilePath + "ims");
				if (directory.exists() == false) {
					directory.mkdir();
					logger.debug("Directory " + directory.toString() + " created.");
				} else {
					logger.debug("Directory " + directory.toString() + " is existed.");
				}
				
				Calendar now = Calendar.getInstance();
				Integer calendarMonth = 0;
				Integer finalMonth = now.get(Calendar.MONTH) + 1;
				String finalMonth2 = "";
				String YYYYMM;
				if (finalMonth < 10){
					finalMonth2 = Integer.toString(calendarMonth) + Integer.toString(finalMonth);
					YYYYMM = Integer.toString(now.get(Calendar.YEAR)) + finalMonth2;
				} else {
					YYYYMM = Integer.toString(now.get(Calendar.YEAR)) + finalMonth;
				}
				
				String newFilePath = directory.toString()+"/"+ YYYYMM;
				logger.debug("newFilePath " + newFilePath);
				directory = new File(newFilePath);
				if (directory.exists() == false) {
					directory.mkdir();
					logger.debug("Directory " + directory.toString() + " created.");
				} else {
					logger.debug("Directory " + directory.toString() + " is existed.");
				}
				
				newFilePath = directory.toString()+"/"+ order.getOrderId();
				logger.debug("newFilePath " + newFilePath);
				directory = new File(newFilePath);
				if (directory.exists() == false) {
					directory.mkdir();
					logger.debug("Directory " + directory.toString() + " created.");
				} else {
					logger.debug("Directory " + directory.toString() + " is existed.");
				}
			} else {
				directory = new File(tmpFilePath + order.getOrderId());
				if (directory.exists() == false) {
					directory.mkdir();
					logger.debug("Directory " + directory.toString() + " created.");
				} else {
					logger.debug("Directory " + directory.toString() + " is existed.");
				}
			}
			QRCodeName = new File(directory, fileName);
			FileOutputStream saveToServer = new FileOutputStream(QRCodeName);
			saveToServer.write(file);

			saveToServer.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public String readQRCode(OrderImsUI order) {
		String str = null;
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMM");
		String YYYYMM = "";
		if (order.getSignOffDate()!=null) {
			YYYYMM = dt.format(order.getSignOffDate());
		}
		String withYyyyMmPath = tmpFilePath + "ims" + File.separator + YYYYMM + File.separator + order.getOrderId();
		String withoutYyyyMmPath = tmpFilePath + order.getOrderId();
		
		File directory = new File(withYyyyMmPath);
		if (!directory.exists()) {
			logger.info("withYyyyMmPath not exists: " + withYyyyMmPath + " @ readQRCode");
			directory = new File(withoutYyyyMmPath);
			if (!directory.exists()) {
				logger.info("withoutYyyyMmPath not exists: " + withoutYyyyMmPath + " @ readQRCode");
				return str;
			}
		}
		
		File QRCodeFile = new File(directory, order.getOrderId() + "_QR.jpg");
		if (!QRCodeFile.exists()) {
			logger.info("File: " + QRCodeFile.getPath() + " does not exist. @ readQRCode");
		} else {
			try {
				BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(QRCodeFile))));
				Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
				str = qrCodeResult.getText();
			} catch (Exception e) {
				logger.info("QR Code decode error. OrderId: " + order.getOrderId() + " @ readQRCode");
				e.printStackTrace();
			}
		}
		return str;
	}
	
	public boolean finishCashDepositRenameQR(OrderImsUI order) {
		boolean success = false;
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMM");
		String YYYYMM = "";
		if (order.getSignOffDate()!=null) {
			YYYYMM = dt.format(order.getSignOffDate());
		}
		String withYyyyMmPath = tmpFilePath + "ims" + File.separator + YYYYMM + File.separator + order.getOrderId();
		String withoutYyyyMmPath = tmpFilePath + order.getOrderId();
		
		File directory = new File(withYyyyMmPath);
		if (!directory.exists()) {
			logger.info(order.getOrderId() + " withYyyyMmPath not exists: " + withYyyyMmPath + " @ readQRCode");
			directory = new File(withoutYyyyMmPath);
			if (!directory.exists()) {
				logger.info(order.getOrderId() + " withoutYyyyMmPath not exists: " + withoutYyyyMmPath + " @ readQRCode");
				throw new AppRuntimeException(order.getOrderId() + " Folder does not exist.");
			}
		}

		// 7-Eleven QR Code Rename
		File QRCodeFile = new File(directory, order.getOrderId() + "_QR.jpg");
		File newName = new File(directory, "DONE_" + order.getOrderId() + "_QR.jpg");
		if (newName.exists()) {
			throw new AppRuntimeException(order.getOrderId() + " already finished deposit process.");
		}
		if (!QRCodeFile.exists()) {
			throw new AppRuntimeException(order.getOrderId() + " has no QR code to complete deposit process");
		}
		try {
			success = QRCodeFile.renameTo(newName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// FPS QR Code Rename (NowTV Acq Cash Deposit)
		QRCodeFile = new File(directory, order.getOrderId() + "_FPS_QR.jpg");
		newName = new File(directory, "DONE_" + order.getOrderId() + "_FPS_QR.jpg");
		if (QRCodeFile.exists()) {
			try {
				success = QRCodeFile.renameTo(newName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return success;
	}
	
	public void deleteQRCodeFile(String orderId) {
		File directory = new File(tmpFilePath + orderId);
		File QRCodeName;
		try {
			if (directory.exists() == false) {
				return;
			}
			QRCodeName = new File(directory, orderId+"_QR.jpg");
			if (QRCodeName.exists() == false) {
				return;
			}
			QRCodeName.delete();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean checkQRCodeExist(OrderImsUI order, String fileName) {
		boolean exist = false;
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMM");
		String YYYYMM = "";
		if (order.getSignOffDate()!=null) {
			YYYYMM = dt.format(order.getSignOffDate());
		}
		String withYyyyMmPath = tmpFilePath + "ims" + File.separator + YYYYMM + File.separator + order.getOrderId();
		String withoutYyyyMmPath = tmpFilePath + order.getOrderId();
		
		File directory = new File(withYyyyMmPath);
		if (!directory.exists()) {
			logger.info(order.getOrderId() + " withYyyyMmPath not exists: " + withYyyyMmPath + " @ readQRCode");
			directory = new File(withoutYyyyMmPath);
			if (!directory.exists()) {
				logger.info(order.getOrderId() + " withoutYyyyMmPath not exists: " + withoutYyyyMmPath + " @ readQRCode");
//				throw new AppRuntimeException(order.getOrderId() + " Folder does not exist.");
				return false;
			}
		}

		// Check QR Code Exist
		File QRCodeFile = new File(directory, fileName);
		exist = QRCodeFile.exists();
		
		return exist;
	}

	public String[] integerListToIntArray(List<String> a){
		String[] rtn=null;
		if(a==null || a.isEmpty()) return new String[0];
		
		rtn = new String[a.size()];
		int i = 0;

//		for (Integer f : a) {
//			rtn[i++] = (f != null ? f.intValue() : 0); 
//		}
		for (String f : a) {
			rtn[i++] = f;
		}
		
		return rtn;
	};
	
	public String genProgOfferChangeRmk(OrderImsUI order) {
		
		OrderImsUI orderImg = order.getOrderImg();
		
		String rmk=null;
		String i_related_sb="";
		String i_price_ind = order.getTvPriceInd();
		
		String i_old_install_fee="";
		String i_new_install_fee="";
		String i_pre_inst_ind="N";

		String oldItemString = "";
		String newItemString = "";
		
		List<String> oldItem = new ArrayList<String>();//i_old_item_list
		List<String> newItem = new ArrayList<String>();//i_new_item_list
		List<String> oldChannel = new ArrayList<String>();//i_old_channel_list
		List<String> newChannel = new ArrayList<String>();//i_new_channel_list
		List<String> oldBackendOffer = new ArrayList<String>();//i_old_backend_offer_list
		List<String> newBackendOffer = new ArrayList<String>();//i_new_backend_offer_list
		List<String> oldBackendChannel = new ArrayList<String>();//i_old_backend_channel_list
		List<String> newBackEndChannel = new ArrayList<String>();//i_new_backend_channel_list
		List<String> oldBasket = new ArrayList<String>();//i_old_basket_list
		List<String> newBasket = new ArrayList<String>();//i_new_basket_list
		List<String> oldBasketItem = new ArrayList<String>();//i_old_basket_item_id_list
		List<String> newBasketItem = new ArrayList<String>();//i_new_basket_item_id_list
		
		String l1OrdNum;
		
		if(!StringUtils.equals(order.getInstallAddress().getSerbdyno(), orderImg.getInstallAddress().getSerbdyno())) 
		{i_related_sb = "SB: "+order.getInstallAddress().getSerbdyno()+"\n"+
			"Change installation address : Flat "	+order.getInstallAddress().getUnitNo()+", "
			+order.getInstallAddress().getFloorNo()+"/F, ";
		if (!StringUtils.isEmpty(order.getInstallAddress().getSbFlat()))
			i_related_sb =  i_related_sb+"Flat "+order.getInstallAddress().getSbFlat()+", ";
		if (!StringUtils.isEmpty(order.getInstallAddress().getSbFloor()))
			i_related_sb =  i_related_sb+order.getInstallAddress().getSbFloor()+"/F, ";
		i_related_sb =  i_related_sb+order.getInstallAddress().getBuildNo()+", "+order.getInstallAddress().getDistDesc();			
		}
		else if (!StringUtils.equals(order.getInstallAddress().getFloorNo(), orderImg.getInstallAddress().getFloorNo())||
				(!StringUtils.equals(order.getInstallAddress().getUnitNo(), orderImg.getInstallAddress().getUnitNo())&& !(StringUtils.isEmpty(order.getInstallAddress().getUnitNo())&&StringUtils.isEmpty(orderImg.getInstallAddress().getUnitNo())))) {
			if (!StringUtils.isEmpty(order.getInstallAddress().getUnitNo()))
				i_related_sb = "Change installation address : Flat "	+order.getInstallAddress().getUnitNo()+", "
				+order.getInstallAddress().getFloorNo()+"/F ";
			else 
				i_related_sb = "Change installation address :  "+order.getInstallAddress().getFloorNo()+"/F ";
		}
		
		if (order.getIsPT().equalsIgnoreCase("Y")&&
				!order.getInstallAddress().getAddrInventory().getTechnology().equals(orderImg.getInstallAddress().getAddrInventory().getTechnology())){
			i_old_install_fee = "PT";
			i_new_install_fee = getOtInstallStr(order);
		}else{
			i_old_install_fee = getOtInstallStr(orderImg);
			i_new_install_fee = getOtInstallStr(order);
		}

		String separator = "";
		for (SubscribedItemUI i:orderImg.getSubscribedItems()) {
			oldItem.add(i.getId());
			oldItemString += separator + i.getId();
			separator = ",";
		}
		separator = "";
		for (SubscribedItemUI i:order.getSubscribedItems()) {
			newItem.add(i.getId());
			newItemString += separator + i.getId();
			separator = ",";
		}
		
		for (SubscribedItemUI i:orderImg.getSubscribedItems()) {
			if(!oldBasket.contains(i.getBasketId())) oldBasket.add(i.getBasketId());
		}
		for (SubscribedItemUI i:order.getSubscribedItems()) {
			if(!newBasket.contains(i.getBasketId())) newBasket.add(i.getBasketId());
		}
		
		if(orderImg.getSubscribedChannels()!=null){
			for (SubscribedChannelUI i:orderImg.getSubscribedChannels()) {
				oldChannel.add(i.getChannelId());
			}
		}
		if(order.getSubscribedChannels()!=null){
			for (SubscribedChannelUI i:order.getSubscribedChannels()) {
				newChannel.add(i.getChannelId());
			}
		}
		
		if(orderImg.getSubscribedBackEndItems()!=null){
			for (SubscribedItemUI i:orderImg.getSubscribedBackEndItems()) {
				oldBackendOffer.add(i.getOfferCode());
			}
		}
		
		if(order.getSubscribedBackEndItems()!=null){
			for (SubscribedItemUI i:order.getSubscribedBackEndItems()) {
				newBackendOffer.add(i.getOfferCode());
			}
		}
		
		if(orderImg.getSubscribedBackEndChannels()!=null){
			for (SubscribedChannelUI i:orderImg.getSubscribedBackEndChannels()) {
				oldBackendChannel.add(i.getCampaignCd()+"@"+i.getPlanCd());
			}
		}
		if(order.getSubscribedBackEndChannels()!=null){
			for (SubscribedChannelUI i:order.getSubscribedBackEndChannels()) {
				newBackEndChannel.add(i.getCampaignCd()+"@"+i.getPlanCd());
			}
		}
		
		if (orderImg.getSubscribedItems()!=null) {
			for (SubscribedItemUI i: orderImg.getSubscribedItems()) {
				if (!StringUtils.isEmpty(i.getBasketId()) && !StringUtils.isEmpty(i.getId()))
					oldBasketItem.add(i.getBasketId()+"-"+i.getId()+"-"+(StringUtils.isEmpty(i.getCampaignCd())?" ":i.getCampaignCd()));
			}
		}
		
		if (order.getSubscribedItems()!=null) {
			for (SubscribedItemUI i: order.getSubscribedItems()) {
				if (!StringUtils.isEmpty(i.getBasketId()) && !StringUtils.isEmpty(i.getId()))
					newBasketItem.add(i.getBasketId()+"-"+i.getId()+"-"+(StringUtils.isEmpty(i.getCampaignCd())?" ":i.getCampaignCd()));
			}
		}	
		
		if("Y".equalsIgnoreCase(order.getPreInstallInd())){
			i_pre_inst_ind = "Y";
		}
		
		try {
			l1OrdNum = bomDao.getL1orderNum(order.getOrderId());

			rmk = orderDao.genProgOfferChangeRmk(
					order.getCreateBy(), //i_staff_id
					i_related_sb, //i_related_sb
					i_price_ind, //i_price_ind
					i_old_install_fee,//i_old_install_fee
					i_new_install_fee,//i_new_install_fee
					integerListToIntArray(oldItem), //i_old_item_list
					integerListToIntArray(newItem),//i_new_item_list
					integerListToIntArray(oldBasket), //i_old_basket_list
					integerListToIntArray(newBasket),//i_new_basket_list
					Integer.valueOf(orderImg.getWarrPeriod()), //i_old_contract_period
					Integer.valueOf(order.getWarrPeriod()), //i_new_contract_period
					Integer.valueOf(orderImg.getTvCredit()==null?"0":orderImg.getTvCredit()), //i_old_credit
					Integer.valueOf(order.getTvCredit()==null?"0":order.getTvCredit()), //i_new_credit
					integerListToIntArray(oldChannel), //i_old_channel_list
					integerListToIntArray(newChannel), //i_new_channel_list
					integerListToIntArray(oldBackendOffer), //i_old_backend_offer_list
					integerListToIntArray(newBackendOffer), //i_new_backend_offer_list
					integerListToIntArray(oldBackendChannel), //i_old_backend_channel_list
					integerListToIntArray(newBackEndChannel), //i_new_backend_channel_list
					integerListToIntArray(oldBasketItem),//i_old_basket_item_id_list
					integerListToIntArray(newBasketItem),//i_new_basket_item_id_list
					l1OrdNum,
					order,
					i_pre_inst_ind);//i_l1_order_num

			if(rmk != null){
				if(order!=null && order.getComponents()!=null){
					for (ComponentUI com: order.getComponents()) {
						rmk = rmk.replace("#"+ com.getItemId() + "%" + com.getAttbId() + "#",  com.getAttbValue()); 
					}
				}
				if(order.getOrderImg()!=null && order.getOrderImg().getComponents()!=null){
					for (ComponentUI com: order.getOrderImg().getComponents()) {
						rmk = rmk.replace("#"+ com.getItemId() + "%" + com.getAttbId() + "#",  com.getAttbValue()); 
					}
				}
				//replace vas parm value
				if(order!=null && order.getSubscribedImsVasParm()!=null){
					for(SubscribedCslOfferDTO imsVasParm: order.getSubscribedImsVasParm()){
						rmk = rmk.replaceAll(imsVasParm.getVas_parm_a_cd() + " : #" + imsVasParm.getItem_id() + "#", 
								imsVasParm.getVas_parm_a_cd() + " : " + imsVasParm.getVas_parm_a());
						rmk = rmk.replaceAll(imsVasParm.getVas_parm_b_cd() + " : #" + imsVasParm.getItem_id() + "#", 
								imsVasParm.getVas_parm_b_cd() + " : " + imsVasParm.getVas_parm_a());
						rmk = rmk.replaceAll(imsVasParm.getVas_parm_c_cd() + " : #" + imsVasParm.getItem_id() + "#", 
								imsVasParm.getVas_parm_c_cd() + " : " + imsVasParm.getVas_parm_a());
						rmk = rmk.replaceAll("DUMMY : #" + imsVasParm.getItem_id() + "#", 
								"");
					}
				}
				if(order.getOrderImg()!=null && order.getOrderImg().getSubscribedImsVasParm()!=null){
					for(SubscribedCslOfferDTO imsVasParm: order.getOrderImg().getSubscribedImsVasParm()){
						rmk = rmk.replaceAll(imsVasParm.getVas_parm_a_cd() + " : #" + imsVasParm.getItem_id() + "#", 
								imsVasParm.getVas_parm_a_cd() + " : " + imsVasParm.getVas_parm_a());
						rmk = rmk.replaceAll(imsVasParm.getVas_parm_b_cd() + " : #" + imsVasParm.getItem_id() + "#", 
								imsVasParm.getVas_parm_b_cd() + " : " + imsVasParm.getVas_parm_a());
						rmk = rmk.replaceAll(imsVasParm.getVas_parm_c_cd() + " : #" + imsVasParm.getItem_id() + "#", 
								imsVasParm.getVas_parm_c_cd() + " : " + imsVasParm.getVas_parm_a());
						rmk = rmk.replaceAll("DUMMY : #" + imsVasParm.getItem_id() + "#", 
								"");
					}
				}
				//replace vas parm value end
			}
			
			/* generate in/out one time charge remark */
			
			String ioOTCRemark = "";
			
			List<IOOfferOTCSchemeDTO> ioOfferList = new ArrayList<IOOfferOTCSchemeDTO>();
			
			ioOfferList = orderDao.getIOProdId(oldItemString, newItemString);
			
			String inPrdIdString = "";
			String outPrdIdString = "";
			
			if(ioOfferList!=null&&ioOfferList.size()>0){
				for(IOOfferOTCSchemeDTO dto:ioOfferList){
					if("I".equalsIgnoreCase(dto.getIOInd())){
						inPrdIdString += dto.getProdId() + ",";
					}
					if("O".equalsIgnoreCase(dto.getIOInd())){
						outPrdIdString += dto.getProdId() + ",";
					}
				}

				if(!"".equalsIgnoreCase(inPrdIdString)){
					inPrdIdString = inPrdIdString.substring(0, inPrdIdString.length()-1);
				}else{
					inPrdIdString = "0";
				}
				if(!"".equalsIgnoreCase(outPrdIdString)){
					outPrdIdString = outPrdIdString.substring(0, outPrdIdString.length()-1);
				}else{
					outPrdIdString = "0";
				}
				
				List<IOOfferOTCSchemeDTO> ioOTCList = new ArrayList<IOOfferOTCSchemeDTO>();
				
				ioOTCList = bomDao.getIOOneTimeCharge(inPrdIdString, outPrdIdString);
				
				String inOTCRemark = "";
				String outOTCRemark = ""; 
				
				if(ioOTCList!=null&&ioOTCList.size()>0){
					for(IOOfferOTCSchemeDTO dto:ioOTCList){
						if("I".equalsIgnoreCase(dto.getIOInd())){
							inOTCRemark += dto.getSchemeCode() + " : " + dto.getSchemeDesc() + "\\n";
						}
						if("O".equalsIgnoreCase(dto.getIOInd())){
							outOTCRemark += dto.getSchemeCode() + " : " + dto.getSchemeDesc() + "\\n";
						}
					}
					if(!"".equalsIgnoreCase(inOTCRemark)||!"".equalsIgnoreCase(outOTCRemark)){
						ioOTCRemark = "OneTime Chrg Remarks : \\n\\n";
						if(!"".equalsIgnoreCase(inOTCRemark)){
							ioOTCRemark += "In OneTime Chrg : \\n\\n" + inOTCRemark;
						}
						if(!"".equalsIgnoreCase(outOTCRemark)){
							ioOTCRemark += "Out OneTime Chrg : \\n\\n" + outOTCRemark;
						}
					}
				}
				
			}
			
			rmk += ioOTCRemark;
			
			/* generate in/out one time charge remark (end) */
			
			
	}catch (Exception e){
		e.printStackTrace();
		rmk = "error loading remarks \n"+ e.getMessage();
	}
		
		return rmk;
	}

	private String getOtInstallStr(OrderImsUI order){
		String i_install_fee= "";
		
		if(!StringUtils.equals(order.getInstallmentMonth(), "0") && !StringUtils.equals(order.getInstallmentChrg(), "0")) {
			i_install_fee = "$"+ order.getInstallmentChrg() + " x " + order.getInstallmentMonth() + "(" + order.getInstallmentCode() +  ")"; 
		} else i_install_fee = "$"+ order.getOtInstallChrg() + "(" + order.getInstallOtCode() +  ")"; 
		
		return i_install_fee;
	}
	
	public void updateWqAttachment(String orderId, String docType) {
		if("I007".equals(docType)){
			try {
				this.orderDao.updateWqAttachmentDSMisMatch(orderId, this.orderDao.getFileNameOfLatestSupportDocHKID(orderId,tmpFilePath));
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	public void insertBomwebAmendTables(OrderImsUI order, String p_wq_batch_id, String isNtvOrder) {

		
		OrderImsUI orderImg = order.getOrderImg();
		
		String i_related_sb="";
		String i_install_fee="";
		List<String> a = new ArrayList<String>();
		List<String> b = new ArrayList<String>();
		List<String> c = new ArrayList<String>();
		List<String> d = new ArrayList<String>();
		List<String> f = new ArrayList<String>();
		List<String> g = new ArrayList<String>();
		List<String> j = new ArrayList<String>();
		List<String> k = new ArrayList<String>();
		List<String> l = new ArrayList<String>();
		List<String> m = new ArrayList<String>();
		List<String> oldBasketItem = new ArrayList<String>();
		List<String> newBasketItem = new ArrayList<String>();
		String l1OrdNum;
		
		if(!StringUtils.equals(order.getInstallAddress().getSerbdyno(), orderImg.getInstallAddress().getSerbdyno())) 
			i_related_sb = order.getInstallAddress().getSerbdyno();

		if(!StringUtils.equals(order.getOtInstallChrg(), orderImg.getOtInstallChrg())) 
			i_install_fee = order.getOtInstallChrg();
		
		for (SubscribedItemUI i:orderImg.getSubscribedItems()) {
			a.add(i.getId());
		}
		for (SubscribedItemUI i:order.getSubscribedItems()) {
			b.add(i.getId());
		}
		for (SubscribedItemUI i:orderImg.getSubscribedItems()) {
			if (!StringUtils.isEmpty(i.getBasketId())) l.add(i.getBasketId());
		}
		for (SubscribedItemUI i:order.getSubscribedItems()) {
			if (!StringUtils.isEmpty(i.getBasketId())) m.add(i.getBasketId());
		}
		if(orderImg.getSubscribedChannels()!=null){
			for (SubscribedChannelUI i:orderImg.getSubscribedChannels()) {
				c.add(i.getChannelId());
			}
		}
		if(order.getSubscribedChannels()!=null){
			for (SubscribedChannelUI i:order.getSubscribedChannels()) {
				d.add(i.getChannelId());
			}
		}
		
		if(orderImg.getSubscribedBackEndItems()!=null){
			for (SubscribedItemUI i:orderImg.getSubscribedBackEndItems()) {
				f.add(i.getOfferCode()+"@"+i.getImsServiceType());
			}
		}
		
		if(order.getSubscribedBackEndItems()!=null){
			for (SubscribedItemUI i:order.getSubscribedBackEndItems()) {
				g.add(i.getOfferCode()+"@"+i.getImsServiceType());
			}
		}
		
		if(orderImg.getSubscribedBackEndChannels()!=null){
			for (SubscribedChannelUI i:orderImg.getSubscribedBackEndChannels()) {
				j.add(i.getCampaignCd()+"@"+i.getPlanCd());
			}
		}
		
		if(order.getSubscribedBackEndItems()!=null){
			for (SubscribedChannelUI i:order.getSubscribedBackEndChannels()) {
				k.add(i.getCampaignCd()+"@"+i.getPlanCd());
			}
		}	
		
		if (orderImg.getSubscribedItems()!=null) {
			for (SubscribedItemUI i: orderImg.getSubscribedItems()) {
				String basketId = StringUtils.isBlank(i.getBasketId())?"":i.getBasketId();
				String itemId = StringUtils.isBlank(i.getId())?"":i.getId();
				String campaignCd = StringUtils.isBlank(i.getCampaignCd())?"":i.getCampaignCd();
				oldBasketItem.add(basketId+"-"+itemId+"-"+campaignCd);
			}
		}
		
		if (order.getSubscribedItems()!=null) {
			for (SubscribedItemUI i: order.getSubscribedItems()) {
				String basketId = StringUtils.isBlank(i.getBasketId())?"":i.getBasketId();
				String itemId = StringUtils.isBlank(i.getId())?"":i.getId();
				String campaignCd = StringUtils.isBlank(i.getCampaignCd())?"":i.getCampaignCd();
				newBasketItem.add(basketId+"-"+itemId+"-"+campaignCd);
			}
		}

		
		try {
			
			orderDao.insertBomwebAmendTables(order.getOrderId(), p_wq_batch_id, i_related_sb, isNtvOrder,
					integerListToIntArray(a), //i_old_item_list
					integerListToIntArray(b),//i_new_item_list
					integerListToIntArray(c), //i_old_channel_list
					integerListToIntArray(d),//i_new_channel_list
					integerListToIntArray(f), //i_old_backend_offer_list
					integerListToIntArray(g),//i_new_backend_offer_list
					integerListToIntArray(j), //i_old_backend_channel_list
					integerListToIntArray(k),//i_new_backend_channel_list
					integerListToIntArray(l),//i_old_basket_list
					integerListToIntArray(m),//i_new_basket_list
					integerListToIntArray(oldBasketItem),//i_old_basket_item_id_list
					integerListToIntArray(newBasketItem)//i_new_basket_item_id_list
					);
			
			order.getInstallAddress().setOrderId(order.getOrderId());
			order.getInstallAddress().setCreateBy(order.getCreateBy());
			
			orderDao.insertBomWebCustAddrAmd(order.getInstallAddress(), p_wq_batch_id);
			orderDao.insertBomWebOrderImsAmd(order, p_wq_batch_id);
			
//			if(order.getSubscribedOffersIms()!=null){
//				logger.info("insertBomWebSubscribedOfferIms");
//				for(BomwebSubscribedOfferImsDTO dto:order.getSubscribedOffersIms()){
//					dto.setOrderId(order.getOrderId());
//					orderDao.insertBomWebSubscribedOfferImsAmd(dto, p_wq_batch_id); 
//				}
//			}else{
//				logger.info("getSubscribedOffersIms is null");				
//			}
			
			if(order.getComponents()!=null){
				for(int i=0; i<order.getComponents().length; i++){
					order.getComponents()[i].setOrderId(order.getOrderId());
					order.getComponents()[i].setCreateBy(order.getCreateBy());
					orderDao.insertBomWebComponentAmd(order.getComponents()[i], p_wq_batch_id);
				}
			}
			if(order.getSubscribedImsVasParm()!=null){
				for(int i=0; i<order.getSubscribedImsVasParm().length; i++){
					order.getSubscribedImsVasParm()[i].setOrder_id(order.getOrderId());
					order.getSubscribedImsVasParm()[i].setCreateBy(order.getCreateBy());
					orderDao.insertBomWebVasParmImsAmd(order.getSubscribedImsVasParm()[i], p_wq_batch_id);
				}
			}
	}catch (Exception e){
		e.printStackTrace();
		throw new AppRuntimeException("Exception caught insertBomwebAmendTables()");
	}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public String isDsStaffMobbile(String staffId, String mobNum){
		logger.info("isDsStaffMobbile");
		String orderId = null;
		try{
			orderId = orderDao.isDsStaffMobbile(staffId,mobNum);
		}catch (DAOException de) {
			logger.error("Exception caught in isDsStaffMobbile()", de);
			throw new AppRuntimeException(de);
		}	
		return orderId;
	}
	
	public void getBackendOfferChange(OrderImsUI order) {

		try {
			orderDao.getBackendOfferChannel(order);
		}catch (Exception e){
			e.printStackTrace();
			throw new AppRuntimeException("Exception caught in getBackendOfferChange()");
		}
	}
	
	public String getL1OrderNum(String orderId){
		String l1ordnum = "";
		
		try {
			l1ordnum = bomDao.getL1orderNum(orderId);
			
		} catch (Exception e){
			logger.error("Exception caught in getL1OrderNum()", e);
			l1ordnum = "";
		}
		
		return l1ordnum;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void acqOrdBackendOfferChange(String OrderId){
		try {
			orderDao.acqOrdBackendOfferChange(OrderId, "RETAIL");
		} catch (DAOException e) {
			logger.error("Exception caught in signOffOrder orderDao.acqOrdBackendOfferChange()", e);
			throw new AppRuntimeException("Exception caught in signOffOrder orderDao.acqOrdBackendOfferChange()");
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public String isDs5DummyStaff(String orgStaffId){
		logger.info("isDs5DummyStaff");
		String isDs5DummyStaff = null;
		try{
			isDs5DummyStaff = orderDao.isDs5DummyStaff(orgStaffId);
		}catch (DAOException de) {
			logger.error("Exception caught in isDs5DummyStaff()", de);
			throw new AppRuntimeException(de);
		}	
		return isDs5DummyStaff;
		
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public String isDsMobileAgent(String teamCd, String mobileNo){
		logger.info("isDsMobileAgent");
		String isDsMobileAgent = null;
		try{
			isDsMobileAgent = orderDao.isDsMobileAgent(teamCd, mobileNo);
		}catch (DAOException de) {
			logger.error("Exception caught in isDsMobileAgent()", de);
			throw new AppRuntimeException(de);
		}	
		return isDsMobileAgent;
		
	}

	public List<BomwebSubscribedOfferImsDTO> getBomwebSubOfferSophieNowRet(String txnId) {
		try {
			return orderDao.getBomwebSubOfferSophieNowRet(txnId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public void insertBomWebSubscribedOfferImsAmd(OrderImsUI order) {	
		try {	
			orderDao.deleteBomWebSubscribedOfferImsAmd(order);
			if(order.getSubscribedOffersIms()!=null){
				logger.info("insertBomWebSubscribedOfferIms");
				for(BomwebSubscribedOfferImsDTO dto:order.getSubscribedOffersIms()){
					dto.setOrderId(order.getOrderId());
					orderDao.insertBomWebSubscribedOfferImsAmd(dto, order.getAmendSeq()); 
				}
			}else{
				logger.info("getSubscribedOffersIms is null");				
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public void insertNowRetAmendTvOnly(OrderImsUI order) {
		try {
			orderDao.insertNowRetAmendTvOnly(order);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public String isNowRetAmendTvOnly(OrderImsUI order) {
		try {
			return orderDao.isNowRetAmendTvOnly(order);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "N";	
	}
	

	public String getSignOffDate (String orderId){
		try {
			return orderDao.getSignOffDate(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getSignOffDate()", de);
			throw new AppRuntimeException(de);
		}
	}

	private String reportServerPath; //dataFilePath

	public String getReportServerPath() {
		return reportServerPath;
	}

	public void setReportServerPath(String reportServerPath) {
		this.reportServerPath = reportServerPath;
	}
	
	public Boolean isSignOffAfExist(OrderImsUI order){
		Boolean result=false;
		String YYYYMM = this.getSignOffDate(order.getOrderId());
		String pathname="";
		pathname = reportServerPath + "ims" + File.separator + YYYYMM + File.separator + order.getOrderId() + File.separator + order.getOrderId()+"_AF_SignOffed.pdf";
		File f = new File(pathname);
		if(!f.exists()) {
			logger.info("new filePath not exists:" + pathname);			
			pathname = reportServerPath + order.getOrderId() + File.separator + order.getOrderId()+"_AF_SignOffed.pdf";	
			f = new File(pathname);
			if(!f.exists()) { 
				logger.info("old filePath not exists:" + pathname);							
			}else{
				result=true;
				logger.info("old filePath exists:" + pathname);								
			}
		}else{
			result=true;
			logger.info("new filePath exists:" + pathname);					
		}
		return result;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public BomwebSubscribedOfferImsDTO getHomeNetworkRequired(String sbno, String unit, String floor, String technology) {
		logger.info("getHomeNetworkRequired");
		BomwebSubscribedOfferImsDTO result = new BomwebSubscribedOfferImsDTO();
		try{
			result = addressDao.getHomeNetworkRequired(sbno,unit,floor,technology);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return result;
	}

	public String getCodeFromWCodeLkup(String grpId) {
		try {
			return orderDao.getCodeFromWCodeLkup(grpId);
		} catch (DAOException e) {
			logger.error(grpId+" error in getCodeFromWCodeLkup, result N");
			return "N";
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public String isMatchRole(String channelCd, String catogery){
		logger.info("isMatchRole");
		String isMatchRole = null;
		try{
			isMatchRole = orderDao.isMatchRole(channelCd,catogery);
		}catch (DAOException de) {
			logger.error("Exception caught in isMatchRole()", de);
			throw new AppRuntimeException(de);
		}	
		return isMatchRole;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppRuntimeException.class)
	public String isDS6User(String staffID){
		logger.info("isDS6User");
		String isMatchRole = null;
		try{
			isMatchRole = orderDao.isDS6User(staffID);
		}catch (DAOException de) {
			logger.error("Exception caught in isMatchRole()", de);
			throw new AppRuntimeException(de);
		}	
		return isMatchRole;
	}
	
	public boolean updateOrderReasonCd_R014(String orderId, String updateBy) {
		boolean success = false;
		try {
			success = orderDao.updateImsOrderReasonCd_R014(orderId, updateBy);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	public boolean updateOrderReasonCd_R008(String orderId, String updateBy) {
		boolean success = false;
		try {
			success = orderDao.updateImsOrderReasonCd_R008(orderId, updateBy);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	public void updateImsOrderLast2Hours_R014() throws DAOException {
		orderDao.updateImsOrderLast2Hours_R014();
	}
}