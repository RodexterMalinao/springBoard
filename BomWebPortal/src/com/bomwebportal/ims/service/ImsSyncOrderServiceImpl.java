package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.dto.AcctDTO;
import com.bomwebportal.ims.dto.AddrInventoryDTO;
import com.bomwebportal.ims.dto.AppointmentDTO;
import com.bomwebportal.ims.dto.ComponentDTO;
import com.bomwebportal.ims.dto.ContactDTO;
import com.bomwebportal.ims.dto.CustAddrDTO;
import com.bomwebportal.ims.dto.CustOptoutInfoDTO;
import com.bomwebportal.ims.dto.CustomerDTO;
import com.bomwebportal.ims.dto.OrderDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.PaymentDTO;
import com.bomwebportal.ims.dto.RemarkDTO;
import com.bomwebportal.ims.dto.SubscItemDiscDTO;
import com.bomwebportal.ims.dto.SubscribedChannelDTO;
import com.bomwebportal.ims.dto.SubscribedItemDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.wsClientIms.BomwebAcctTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebAddrInventoryTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebAppointmentTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebComponentTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebContactTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebCustAddrTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebCustOptoutInfoTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebCustomerTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebOrderImsTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebOrderTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebPaymentTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebRemarkTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebSubscItemDiscTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebSubscribedChannelTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebSubscribedItemTmpDTO;
import com.bomwebportal.ims.wsClientIms.BomwebTmpDTO;
import com.bomwebportal.ims.wsclient.ImsWSClient;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

public class ImsSyncOrderServiceImpl implements ImsSyncOrderService{
	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();
		
	private ImsWSClient client;	
	private ImsOrderService orderservice;
		
	public void syncOrderToBOM(){
		logger.info("syncOrderToBOM");		
		try{		
			String orderId = orderservice.getAutoProcessOrderId();			
			if(orderId!=null){	
				logger.info("Get signed-off order ID:"+orderId);																
				syncOrderToBOM(orderId);				
			}else{
				logger.info("No pending order found");
			}			
		}catch (Exception e){
			logger.error("Exception caught in syncOrderToBOM()", e);
			throw new AppRuntimeException(e);
		}	
						
	}		
	
	public void syncOrderToBOM(String orderId){
		logger.info("sync order "+orderId+" to bom");
		try{			
			orderservice.updateSyncOrderStatus(orderId);
			OrderImsUI order = orderservice.getBomSyncOrderDetail(orderId);
			
			BomwebTmpDTO request = new BomwebTmpDTO();
			
			//copy bomweb order
			//BomwebOrderTmpDTO[] orderlist = new BomwebOrderTmpDTO[1];
			//orderlist[0] = copyBomwebOrder(order);			
			//request.setBomwebOrderTmpDTOArr(orderlist);
			BomwebOrderTmpDTO ordertmp = copyBomwebOrder(order);
			request.getBomwebOrderTmpDTOArr().add(ordertmp);
			
			//copy bomweb order ims
			//BomwebOrderImsTmpDTO[] orderimslist = new BomwebOrderImsTmpDTO[1];
			//orderimslist[0] = copyBomwebOrderIms(order);
			//request.setBomwebOrderImsTmpDTOArr(orderimslist);
			BomwebOrderImsTmpDTO orderimstmp = copyBomwebOrderIms(order);
			request.getBomwebOrderImsTmpDTOArr().add(orderimstmp);
			
			//copy appointment
			//BomwebAppointmentTmpDTO[] appointmentlist = new BomwebAppointmentTmpDTO[1];
			//appointmentlist[0] = copyAppointment(order.getAppointment());
			//request.setBomwebAppointmentTmpDTOArr(appointmentlist);
			BomwebAppointmentTmpDTO appointmenttmp = copyAppointment(order.getAppointment());
			request.getBomwebAppointmentTmpDTOArr().add(appointmenttmp);
			
			//copy remark
			//BomwebRemarkTmpDTO[] remarklist;
			//if(order.getRemarks()!=null){
			//	remarklist = new BomwebRemarkTmpDTO[order.getRemarks().length];
			//	for(int i=0; i<order.getRemarks().length; i++){
			//		remarklist[i] = copyRemark(order.getRemarks()[i]);				
			//	}
			//}else{
			//	remarklist = new BomwebRemarkTmpDTO[0];
			//}			
			//request.setBomwebRemarkTmpDTOArr(remarklist);
			if(order.getRemarks()!=null){				
				for(int i=0; i<order.getRemarks().length; i++){
					BomwebRemarkTmpDTO remarktmp = copyRemark(order.getRemarks()[i]);
					request.getBomwebRemarkTmpDTOArr().add(remarktmp);
				}
			}
			
			//copy customer
			//BomwebCustomerTmpDTO[] customerlist = new BomwebCustomerTmpDTO[1];
			//customerlist[0] = copyCustomer(order.getCustomer());
			//request.setBomwebCustomerTmpDTOArr(customerlist);
			BomwebCustomerTmpDTO customertmp = copyCustomer(order.getCustomer());
			request.getBomwebCustomerTmpDTOArr().add(customertmp);
			
			//copy account
			//BomwebAcctTmpDTO[] accountlist = new BomwebAcctTmpDTO[1];
			//accountlist[0] = copyAccount(order.getCustomer().getAccount());
			//request.setBomwebAcctTmpDTO(accountlist);
			BomwebAcctTmpDTO accttmp = copyAccount(order.getCustomer().getAccount());
			request.getBomwebAcctTmpDTO().add(accttmp);
			
			//copy payment
			//BomwebPaymentTmpDTO[] paymentlist = new BomwebPaymentTmpDTO[1];
			//paymentlist[0] = copyPayment(order.getCustomer().getAccount().getPayment());
			//request.setBomwebPaymentTmpDTOArr(paymentlist);
			BomwebPaymentTmpDTO paymenttmp = copyPayment(order.getCustomer().getAccount().getPayment());
			request.getBomwebPaymentTmpDTOArr().add(paymenttmp);
			
			//copy cust optout
//			BomwebCustOptoutInfoTmpDTO[] optoutlist;
//			if(order.getCustomer().getCustOptInfo()!=null){
//				optoutlist = new BomwebCustOptoutInfoTmpDTO[1];
//				optoutlist[0] = copyCustOptout(order.getCustomer().getCustOptInfo());
//			}else{
//				optoutlist = new BomwebCustOptoutInfoTmpDTO[0];
//			}
//			request.setBomwebCustOptoutInfoTmpDTOArr(optoutlist);
			if(order.getCustomer().getCustOptInfo()!=null){
				BomwebCustOptoutInfoTmpDTO optouttmp = copyCustOptout(order.getCustomer().getCustOptInfo());
				request.getBomwebCustOptoutInfoTmpDTOArr().add(optouttmp);
			}
			
			//copy contact
			//BomwebContactTmpDTO[] contactlist = new BomwebContactTmpDTO[1];
			//contactlist[0] = copyContact(order.getCustomer().getContact());
			//request.setBomwebContactTmpDTOArr(contactlist);
			BomwebContactTmpDTO contacttmp = copyContact(order.getCustomer().getContact());
			request.getBomwebContactTmpDTOArr().add(contacttmp);
			
			//copy address
//			BomwebCustAddrTmpDTO[] addrlist;			
//			if(order.getBillingAddress()!=null){
//				addrlist = new BomwebCustAddrTmpDTO[2];
//				addrlist[0] = copyCustAddr(order.getInstallAddress());
//				addrlist[1] = copyCustAddr(order.getBillingAddress());
//			}else{
//				addrlist = new BomwebCustAddrTmpDTO[1];
//				addrlist[0] = copyCustAddr(order.getInstallAddress());
//			}
//			request.setBomwebCustAddrTmpDTOArr(addrlist);
			BomwebCustAddrTmpDTO addrtmp = copyCustAddr(order.getInstallAddress());
			request.getBomwebCustAddrTmpDTOArr().add(addrtmp);
			if(order.getBillingAddress()!=null){
				addrtmp = copyCustAddr(order.getBillingAddress());
				request.getBomwebCustAddrTmpDTOArr().add(addrtmp);
			}
			
//			BomwebAddrInventoryTmpDTO[] addrinvlist;
//			if(order.getInstallAddress().getAddrInventory()!=null){
//				addrinvlist = new BomwebAddrInventoryTmpDTO[1];
//				addrinvlist[0] = c
//			}else{
//				addrinvlist = new BomwebAddrInventoryTmpDTO[0];
//			}
//			request.setBomwebAddrInventoryTmpDTOArr(addrinvlist);
			if(order.getInstallAddress().getAddrInventory()!=null){
				BomwebAddrInventoryTmpDTO inventorytmp = copyAddrInventory(order.getInstallAddress().getAddrInventory());
				request.getBomwebAddrInventoryTmpDTOArr().add(inventorytmp);
			}
			
//			BomwebSubscribedItemTmpDTO[] itemlist;
//			if(order.getSubscribedItems()!=null){
//				itemlist = new BomwebSubscribedItemTmpDTO[order.getSubscribedItems().length];
//				for(int i=0; i<order.getSubscribedItems().length; i++){
//					itemlist[i] = copySubcribedItem(order.getSubscribedItems()[i]);
//				}
//			}else{
//				itemlist = new BomwebSubscribedItemTmpDTO[0];
//			}
//			request.setBomwebSubscribedItemTmpDTOArr(itemlist);
			if(order.getSubscribedItems()!=null){
				for(int i=0; i<order.getSubscribedItems().length; i++){
					BomwebSubscribedItemTmpDTO itemtmp = copySubcribedItem(order.getSubscribedItems()[i]);
					request.getBomwebSubscribedItemTmpDTOArr().add(itemtmp);
				}
			}
			
//			BomwebSubscribedChannelTmpDTO[] channellist;
//			if(order.getSubscribedChannels()!=null){
//				channellist = new BomwebSubscribedChannelTmpDTO[order.getSubscribedChannels().length];
//				for(int i=0; i<order.getSubscribedChannels().length; i++){
//					channellist[i] = copyChannel(order.getSubscribedChannels()[i]);
//				}				
//			}else{
//				channellist = new BomwebSubscribedChannelTmpDTO[0];
//			}
//			request.setBomwebSubscribedChannelTmpDTOArr(channellist);
			if(order.getSubscribedChannels()!=null){
				for(int i=0; i<order.getSubscribedChannels().length; i++){
					BomwebSubscribedChannelTmpDTO channeltmp = copyChannel(order.getSubscribedChannels()[i]);
					request.getBomwebSubscribedChannelTmpDTOArr().add(channeltmp);
				}
			}
			
//			BomwebComponentTmpDTO[] complist;
//			if(order.getComponents()!=null){
//				complist = new BomwebComponentTmpDTO[order.getComponents().length];
//				for(int i=0; i<order.getComponents().length; i++){
//					complist[i] = copyComponent(order.getComponents()[i]);
//				}
//			}else{
//				complist = new BomwebComponentTmpDTO[0];
//			}
//			request.setBomwebComponentTmpDTOArr(complist);
			if(order.getComponents()!=null){
				for(int i=0; i<order.getComponents().length; i++){
					BomwebComponentTmpDTO comptmp = copyComponent(order.getComponents()[i]);
					request.getBomwebComponentTmpDTOArr().add(comptmp);
				}
			}
			
//			BomwebSubscItemDiscTmpDTO[] disclist;
//			if(order.getSubscDiscountList()!=null){
//				disclist = new BomwebSubscItemDiscTmpDTO[order.getSubscDiscountList().length];
//				for(int i=0; i<order.getSubscDiscountList().length; i++){
//					disclist[i] = copyDiscount(order.getSubscDiscountList()[i]);
//				}				
//			}else{
//				disclist = new BomwebSubscItemDiscTmpDTO[0];
//			}						
//			request.setBomwebSubscItemDiscTmpDTOArr(disclist);
			if(order.getSubscDiscountList()!=null){
				for(int i=0; i<order.getSubscDiscountList().length; i++){
					BomwebSubscItemDiscTmpDTO disctmp = copyDiscount(order.getSubscDiscountList()[i]);
					request.getBomwebSubscItemDiscTmpDTOArr().add(disctmp);
				}
			}
			
			logger.debug(gson.toJson(request));
			
			client.InsertBomwebTmp(request);						
			
		}catch (Exception e){
			logger.error("Exception caught in syncOrderToBOM()", e);
			throw new AppRuntimeException(e);
		}	
		
	}
	
	private BomwebOrderTmpDTO copyBomwebOrder(OrderDTO src){
		
		BomwebOrderTmpDTO target = new BomwebOrderTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setOcid((src.getOcId()==null)?"":src.getOcId());
		target.setSrc((src.getSrc()==null)?"":src.getSrc());
		target.setOrderType((src.getOrderType()==null)?"":src.getOrderType());
		target.setMsisdn((src.getMsisdn()==null)?"":src.getMsisdn());
		target.setMsisdnlvl((src.getMsisdnlvl()==null)?"":src.getMsisdnlvl());
		target.setMnpInd((src.getMnpInd()==null)?"":src.getMnpInd());
		target.setShopCd((src.getShopCd()==null)?"":src.getShopCd());
		target.setBomCustNo((src.getBomCustNo()==null)?"":src.getBomCustNo());
		target.setMobCustNo((src.getMobCustNo()==null)?"":src.getMobCustNo());
		target.setAcctNo((src.getAcctNo()==null)?"":src.getAcctNo());
		target.setSrvReqDate((src.getSrvReqDate()==null)?"":Utility.date2String(src.getSrvReqDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setAgreeNum((src.getAgreeNum()==null)?"":src.getAgreeNum());
		target.setAppDate((src.getAppDate()==null)?"":Utility.date2String(src.getAppDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setSalesType((src.getSalesType()==null)?"":src.getSalesType());
		target.setSalesCd((src.getSalesCd()==null)?"":src.getSalesCd());
		target.setDepWaive((src.getDepWaive()==null)?"":src.getDepWaive());
		target.setOnHoldInd((src.getOnHoldInd()==null)?"":src.getOnHoldInd());
		target.setOnHoldReaCd((src.getOnHoldReaCd()==null)?"":src.getOnHoldReaCd());
		target.setImei((src.getImei()==null)?"":src.getImei());
		target.setWarrStartDate((src.getWarrStartDate()==null)?"":Utility.date2String(src.getWarrStartDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setWarrPeriod((src.getWarrPeriod()==null)?"":src.getWarrPeriod());
		target.setCreateDate((src.getCreateDate()==null)?"":Utility.date2String(src.getCreateDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setOrderStatus((src.getOrderStatus()==null)?"":src.getOrderStatus());
		target.setErrCd((src.getErrCd()==null)?"":src.getErrCd());
		target.setErrMsg((src.getErrMsg()==null)?"":src.getErrMsg());
		target.setSalesName((src.getSalesName()==null)?"":src.getSalesName());
		target.setAoInd((src.getAoInd()==null)?"":src.getAoInd());
		target.setLastUpdateBy((src.getLastUpdBy()==null)?"":src.getLastUpdBy());
		target.setLastUpdateDate((src.getLastUpdDate()==null)?"":Utility.date2String(src.getLastUpdDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setCreateBy((src.getCreateBy()==null)?"":src.getCreateBy());
		target.setReasonCd((src.getReasonCd()==null)?"":src.getReasonCd());
		target.setLob((src.getLob()==null)?"":src.getLob());
		target.setSignOffDate((src.getSignOffDate()==null)?"":Utility.date2String(src.getSignOffDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setStaffNum((src.getStaffNum()==null)?"":src.getStaffNum());
		target.setSalesChannel((src.getSalesChannel()==null)?"":src.getSalesChannel());
		target.setSalesContactNum((src.getSalesContactNum()==null)?"":src.getSalesContactNum());
		
		return target;
		
	}
	
	private BomwebOrderImsTmpDTO copyBomwebOrderIms(OrderImsDTO src){
		BomwebOrderImsTmpDTO target = new BomwebOrderImsTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setLoginId((src.getLoginId()==null)?"":src.getLoginId());
		target.setDecoderType((src.getDecodeType()==null)?"":src.getDecodeType());
		target.setAdultViewAllow((src.getAdultViewAllow()==null)?"":src.getAdultViewAllow());
		target.setTargetCommDate((src.getTargetCommDate()==null)?"":Utility.date2String(src.getTargetCommDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setTeamCd("");
		target.setStaffName((src.getSalesName()==null)?"":src.getSalesName());
		target.setStaffNo((src.getStaffNum()==null)?"":src.getStaffNum());
		target.setStaffContactNum((src.getSalesContactNum()==null)?"":src.getSalesContactNum());
		target.setPrepayMethod("");
		target.setCashFsPrepay((src.getCashFsPrepay()==null)?"":src.getCashFsPrepay());
		target.setIsBlacklistAddr("");
		target.setChooseNowtvCoupon(src.getChooseNowtvCoupon()==null?"":src.getChooseNowtvCoupon());
		target.setChoose30Free6Mth("");
		target.setProcessCcPrepay((src.getProcessCreditCard()==null)?"":src.getProcessCreditCard());
		target.setProcessVim((src.getProcessVim()==null)?"":src.getProcessVim());
		target.setProcessVms((src.getProcessVms()==null)?"":src.getProcessVms());
		target.setProcessWifi((src.getProcessWifi()==null)?"":src.getProcessWifi());
		target.setPrimaryAcctNo((src.getPrimaryAcctNo()==null)?"":src.getPrimaryAcctNo());
		target.setSecondAcctNo("");
		target.setPrepayAmt((src.getPrepayAmt()==null)?"":src.getPrepayAmt());
		target.setMovingChrg((src.getMovingChrg()==null)?"":src.getMovingChrg());
		target.setNowTvFormType((src.getNowTvFormType()==null)?"":src.getNowTvFormType());
		target.setFixedLineNo((src.getFixedLineNo()==null)?"":src.getFixedLineNo());
		target.setIsCcOffer((src.getIsCreditCardOffer()==null)?"":src.getIsCreditCardOffer());
		target.setLangPreference((src.getLangPreference()==null)?"":src.getLangPreference());
		target.setWaivedPrepay((src.getWaivedPrepay()==null)?"":src.getWaivedPrepay());
		target.setBandwidth((src.getBandwidth()==null)?"":src.getBandwidth());
		target.setTvCredit((src.getTvCredit()==null)?"":src.getTvCredit());		
		target.setOtInstallChrg((src.getOtInstallChrg()==null)?"":src.getOtInstallChrg());
		
		
		return target;
	}
	
	private BomwebAppointmentTmpDTO copyAppointment(AppointmentDTO src){
		BomwebAppointmentTmpDTO target = new BomwebAppointmentTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setSerialNum((src.getSerialNum()==null)?"":src.getSerialNum());
		target.setAppntStartTime((src.getAppntStartDate()==null)?"":Utility.date2String(src.getAppntStartDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setAppntEndTime((src.getAppntEndDate()==null)?"":Utility.date2String(src.getAppntEndDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setDtlId((src.getDtlId()==null)?"":src.getDtlId());
		target.setInstContactName((src.getInstContactName()==null)?"":src.getInstContactName());
		target.setInstContactNum((src.getInstContactNum()==null)?"":src.getInstContactNum());
		target.setInstSmsNum((src.getInstSmsNum()==null)?"":src.getInstSmsNum());
		target.setExactAppntTime((src.getExactAppntTime()==null)?"":Utility.date2String(src.getExactAppntTime(), "dd/MM/yyyy HH:mm:ss"));
		target.setForcedDelayInd((src.getForcedDelayInd()==null)?"":src.getForcedDelayInd());
		target.setPreWiringStartTime((src.getPreWiringStartTime()==null)?"":Utility.date2String(src.getPreWiringStartTime(), "dd/MM/yyyy HH:mm:ss"));
		target.setPreWiringEndTime((src.getPreWiringEndTime()==null)?"":Utility.date2String(src.getPreWiringEndTime(), "dd/MM/yyyy HH:mm:ss"));
		target.setPreWiringType((src.getPreWiringType()==null)?"":src.getPreWiringType());
		target.setTidInd((src.getTidInd()==null)?"":src.getTidInd());
		target.setAppntType((src.getAppntType()==null)?"":src.getAppntType());
		
		return target;
	}
	
	private BomwebRemarkTmpDTO copyRemark(RemarkDTO src){
		BomwebRemarkTmpDTO target = new BomwebRemarkTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setDtlId((src.getDtlId()==null)?"":src.getDtlId());
		target.setRmkType((src.getRmkType()==null)?"":src.getRmkType());
		target.setRmkSeq((src.getRmkSeq()==null)?"":src.getRmkSeq());
		target.setRmkDtl((src.getRmkDtl()==null)?"":src.getRmkDtl());
		
		return target;		
	}
	
	private BomwebCustomerTmpDTO copyCustomer(CustomerDTO src){
		BomwebCustomerTmpDTO target = new BomwebCustomerTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setCustNo((src.getCustNo()==null)?"":src.getCustNo());
		target.setMobCustNo((src.getMobCustNo()==null)?"":src.getMobCustNo());
		target.setFirstName((src.getFirstName()==null)?"":src.getFirstName());
		target.setLastName((src.getLastName()==null)?"":src.getLastName());
		target.setIdDocType((src.getIdDocType()==null)?"":src.getIdDocType());
		target.setIdDocNum((src.getIdDocNum()==null)?"":src.getIdDocNum());
		target.setDob((src.getDob()==null)?"":Utility.date2String(src.getDob(), "dd/MM/yyyy HH:mm:ss"));
		target.setTitle((src.getTitle()==null)?"":src.getTitle());
		target.setCompanyName((src.getCompanyName()==null)?"":src.getCompanyName());
		target.setIndType((src.getIndType()==null)?"":src.getIndType());
		target.setIndSubType((src.getIndSubType()==null)?"":src.getIndSubType());
		target.setNationality((src.getNationality()==null)?"":src.getNationality());
		target.setCreateDate((src.getCreateDate()==null)?"":Utility.date2String(src.getCreateDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setAddrProofInd((src.getAddrProofInd()==null)?"":src.getAddrProofInd());
		target.setLob((src.getLob()==null)?"":src.getLob());
		target.setServiceNum((src.getServiceNum()==null)?"":src.getServiceNum());
		target.setLangWritten("");
		target.setLangSpoken("");
		target.setIdVerifiedInd((src.getIdVerified()==null)?"":src.getIdVerified());
		target.setBlackListInd((src.getBlacklistInd()==null)?"":src.getBlacklistInd());
		
		return target;
	}
	
	private BomwebAcctTmpDTO copyAccount(AcctDTO src){
		BomwebAcctTmpDTO target = new BomwebAcctTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setCustNo((src.getCustNo()==null)?"":src.getCustNo());
		target.setAcctName((src.getAcctName()==null)?"":src.getAcctName());
		target.setBillFreq((src.getBillFreq()==null)?"":src.getBillFreq());
		target.setBillLang((src.getBillLang()==null)?"":src.getBillLang());
		target.setSmsNo((src.getSmsNo()==null)?"":src.getSmsNo());
		target.setEmailAddr((src.getEmailAddr()==null)?"":src.getEmailAddr());
		target.setAcctNo((src.getAcctNo()==null)?"":src.getAcctNo());
		target.setCreateDate((src.getCreateDate()==null)?"":Utility.date2String(src.getCreateDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setBillPeriod((src.getBillPeriod()==null)?"":src.getBillPeriod());
		target.setBillMedia((src.getBillMedia()==null)?"":src.getBillMedia());
		target.setDtlId((src.getDtlId()==null)?"":src.getDtlId());
		
		return target;
		
	}
	
	private BomwebPaymentTmpDTO copyPayment(PaymentDTO src){
		BomwebPaymentTmpDTO target = new BomwebPaymentTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setCustNo((src.getCustNo()==null)?"":src.getCustNo());
		target.setPayMtdKey((src.getPayMtdKey()==null)?"":src.getPayMtdKey());
		target.setAcctNo((src.getAcctNo()==null)?"":src.getAcctNo());
		target.setAutopayAppDate((src.getAutopayAppDate()==null)?"":Utility.date2String(src.getAutopayAppDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setAutopayUpLimAmt((src.getAutopayUpLimAmt()==null)?"":src.getAutopayUpLimAmt());
		target.setBAcctNo((src.getBAcctNo()==null)?"":src.getBAcctNo());
		target.setBAcctHoldName((src.getBAcctHoldName()==null)?"":src.getBAcctHoldName());
		target.setBAcctHoldIdType((src.getBAcctHoldIdType()==null)?"":src.getBAcctHoldIdType());
		target.setBAcctHoldIdNum((src.getBAcctHoldIdNum()==null)?"":src.getBAcctHoldIdNum());
		target.setBranchCd((src.getBranchCd()==null)?"":src.getBranchCd());
		target.setBankCd((src.getBankCd()==null)?"":src.getBankCd());
		target.setPayMtdType((src.getPayMtdType()==null)?"":src.getPayMtdType());
		target.setThirdPartyInd((src.getThirdPartyInd()==null)?"":src.getThirdPartyInd());
		target.setCcType((src.getCcType()==null)?"":src.getCcType());
		target.setCcNum((src.getCcNum()==null)?"":src.getCcNum());
		target.setCcHoldName((src.getCcHoldName()==null)?"":src.getCcHoldName());
		target.setCcExpDate((src.getCcExpDate()==null)?"":src.getCcExpDate());
		target.setCcIssueBank((src.getCcIssueBank()==null)?"":src.getCcIssueBank());
		target.setCcIdDocType((src.getCcIdDocType()==null)?"":src.getCcIdDocType());
		target.setCcIdDocNo((src.getCcIdDocNo()==null)?"":src.getCcIdDocNo());
		target.setCreateDate((src.getCreateDate()==null)?"":Utility.date2String(src.getCreateDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setCcVerifiedInd((src.getCcVerified()==null)?"":src.getCcVerified());
		target.setDtlId((src.getDtlId()==null)?"":src.getDtlId());
		target.setAction((src.getAction()==null)?"":src.getAction());
		
		return target;
	}
	
	private BomwebCustOptoutInfoTmpDTO copyCustOptout(CustOptoutInfoDTO src){
		BomwebCustOptoutInfoTmpDTO target = new BomwebCustOptoutInfoTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setCustNo((src.getCustNo()==null)?"":src.getCustNo());
		target.setDirectMailing((src.getDirectMailing()==null)?"":src.getDirectMailing());
		target.setOutbound((src.getOutbound()==null)?"":src.getOutbound());
		target.setSms((src.getSms()==null)?"":src.getSms());
		target.setEmail((src.getEmail()==null)?"":src.getEmail());
		target.setWebBill((src.getWebBill()==null)?"":src.getWebBill());
		target.setNonsalesSms((src.getNonsalesSms()==null)?"":src.getNonsalesSms());
		target.setInternet((src.getInternet()==null)?"":src.getInternet());
		
		return target;
		
	}
	
	private BomwebContactTmpDTO copyContact(ContactDTO src){
		BomwebContactTmpDTO target = new BomwebContactTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setTitle((src.getTitle()==null)?"":src.getTitle());
		target.setContactName((src.getContactName()==null)?"":src.getContactName());
		target.setContactPhone((src.getContactPhone()==null)?"":src.getContactPhone());
		target.setEmailAddr((src.getEmailAddr()==null)?"":src.getEmailAddr());
		target.setActionInd((src.getActionInd()==null)?"":src.getActionInd());
		target.setCreateDate((src.getCreateDate()==null)?"":Utility.date2String(src.getCreateDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setOtherPhone((src.getOtherPhone()==null)?"":src.getOtherPhone());
		
		return target;
	}
	
	private BomwebCustAddrTmpDTO copyCustAddr(CustAddrDTO src){
		BomwebCustAddrTmpDTO target = new BomwebCustAddrTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setAddrUsage((src.getAddrUsage()==null)?"":src.getAddrUsage());
		target.setFlat((src.getFlat()==null)?"":src.getFlat());
		target.setAreaCd((src.getAreaCd()==null)?"":src.getAreaCd());
		target.setDistNo((src.getDistNo()==null)?"":src.getDistNo());
		target.setSectCd((src.getSectCd()==null)?"":src.getSectCd());
		target.setStrName((src.getStrName()==null)?"":src.getStrName());
		target.setHiLotNo((src.getHiLotNo()==null)?"":src.getHiLotNo());
		target.setStrCatCd((src.getStrCatCd()==null)?"":src.getStrCatCd());
		target.setBuildNo((src.getBuildNo()==null)?"":src.getBuildNo());
		target.setForeignAddrFlag((src.getForeignAddrFlag()==null)?"":src.getForeignAddrFlag());
		target.setFloorNo((src.getFloorNo()==null)?"":src.getFloorNo());
		target.setUnitNo((src.getUnitNo()==null)?"":src.getUnitNo());
		target.setPoBoxNo((src.getPoBoxNo()==null)?"":src.getPoBoxNo());
		target.setAddrType((src.getAddrType()==null)?"":src.getAddrType());
		target.setStrNo((src.getStrNo()==null)?"":src.getStrNo());
		target.setSectDepInd((src.getSectDepInd()==null)?"":src.getSectDepInd());
		target.setCreateDate((src.getCreateDate()==null)?"":Utility.date2String(src.getCreateDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setAreaDesc((src.getAreaDesc()==null)?"":src.getAreaDesc());
		target.setDistDesc((src.getDistDesc()==null)?"":src.getDistDesc());
		target.setSectDesc((src.getSectDesc()==null)?"":src.getSectDesc());
		target.setStrCatDesc((src.getStrCatDesc()==null)?"":src.getStrCatDesc());
		target.setSerbdyno((src.getSerbdyno()==null)?"":src.getSerbdyno());
		target.setBlacklistInd((src.getBlacklistInd()==null)?"":src.getBlacklistInd());
		target.setDtlId((src.getDtlId()==null)?"":src.getDtlId());
		
		return target;
	}
	
	private BomwebAddrInventoryTmpDTO copyAddrInventory(AddrInventoryDTO src){
		BomwebAddrInventoryTmpDTO target = new BomwebAddrInventoryTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setDtlId((src.getDtlId()==null)?"":src.getDtlId());
		target.setAddrUsage((src.getAddrUsage()==null)?"":src.getAddrUsage());
		target.setResourceShortageInd((src.getResourceShortage()==null)?"":src.getResourceShortage());
		target.setTechnology((src.getTechnology()==null)?"":src.getTechnology());
		target.setBuildingType((src.getBuildingType()==null)?"":src.getBuildingType());
		target.setN2BuildingInd((src.getN2BuildingInd()==null)?"":src.getN2BuildingInd());
		target.setFieldWorkPermitDay((src.getFieldPermitDay()==null)?"":src.getFieldPermitDay());
		target.setAddrId((src.getAddrId()==null)?"":src.getAddrId());
		target.setMaxBandwidth((src.getMaxBandwidth()==null)?"":src.getMaxBandwidth());
		target.setFttcInd((src.getFttcInd()==null)?"":src.getFttcInd());
		
		return target;
	}
	
	private BomwebSubscribedItemTmpDTO copySubcribedItem(SubscribedItemDTO src){
		BomwebSubscribedItemTmpDTO target = new BomwebSubscribedItemTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setId((src.getId()==null)?"":src.getId());
		target.setType((src.getType()==null)?"":src.getType());
		target.setCreateDate((src.getCreateDate()==null)?"":Utility.date2String(src.getCreateDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setBasketId((src.getBasketId()==null)?"":src.getBasketId());
		target.setOfferId((src.getOfferId()==null)?"":src.getOfferId());
		target.setOfferSubId((src.getOfferSubId()==null)?"":src.getOfferSubId());
		target.setProductId((src.getProductId()==null)?"":src.getProductId());
		target.setPlanId((src.getPlanId()==null)?"":src.getPlanId());
		target.setProgramId((src.getProgramId()==null)?"":src.getProgramId());
		target.setIncentiveId((src.getIncentiveId()==null)?"":src.getIncentiveId());
		
		return target;
	}
	
	private BomwebSubscribedChannelTmpDTO copyChannel(SubscribedChannelDTO src){
		BomwebSubscribedChannelTmpDTO target = new BomwebSubscribedChannelTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setChannelId((src.getChannelId()==null)?"":src.getChannelId());
		target.setCreateDate((src.getCreateDate()==null)?"":Utility.date2String(src.getCreateDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setDtlId((src.getDtlId()==null)?"":src.getDtlId());
		target.setCampaignCd((src.getCampaignCd()==null)?"":src.getCampaignCd());
		target.setPlanCd((src.getPlanCd()==null)?"":src.getPlanCd());
		target.setTvType((src.getTvTypt()==null)?"":src.getTvTypt());
		target.setChannelCd((src.getChannelCd()==null)?"":src.getChannelCd());
		
		return target;
	}
	
	private BomwebComponentTmpDTO copyComponent(ComponentDTO src){
		BomwebComponentTmpDTO target = new BomwebComponentTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setAttbId((src.getAttbId()==null)?"":src.getAttbId());
		target.setAttbValue((src.getAttbValue()==null)?"":src.getAttbValue());
		target.setCreateDate((src.getCreateDate()==null)?"":Utility.date2String(src.getCreateDate(), "dd/MM/yyyy HH:mm:ss"));
		target.setItemId((src.getItemId()==null)?"":src.getItemId());
		target.setProductId((src.getProductId()==null)?"":src.getProductId());
		
		return target;
	}
	
	private BomwebSubscItemDiscTmpDTO copyDiscount(SubscItemDiscDTO src){
		
		BomwebSubscItemDiscTmpDTO target = new BomwebSubscItemDiscTmpDTO();
		
		target.setOrderId((src.getOrderId()==null)?"":src.getOrderId());
		target.setId((src.getId()==null)?"":src.getId());
		target.setOfferId((src.getOfferId()==null)?"":src.getOfferId());
		target.setOfferSubId((src.getOfferSubId()==null)?"":src.getOfferSubId());
		target.setProductId((src.getProductId()==null)?"":src.getProductId());
		target.setDisId((src.getDisId()==null)?"":src.getDisId());
		target.setDisCd((src.getDisCd()==null)?"":src.getDisCd());

		return target;
		
	}

	public ImsWSClient getClient() {
		return client;
	}

	public void setClient(ImsWSClient client) {
		this.client = client;
	}

	public ImsOrderService getOrderservice() {
		return orderservice;
	}

	public void setOrderservice(ImsOrderService orderservice) {
		this.orderservice = orderservice;
	}
			
}
