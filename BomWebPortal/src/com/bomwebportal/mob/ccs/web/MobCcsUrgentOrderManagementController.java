package com.bomwebportal.mob.ccs.web;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentTransDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocUI;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsUrgentOrderManagementUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentMonthyUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentAdminService;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentUpfrontService;
import com.bomwebportal.mob.ccs.service.MobCcsSupportDocService;
import com.bomwebportal.service.DepositService;
import com.bomwebportal.service.MobCosReportService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.ReleaseLockService;
import com.bomwebportal.service.ReleaseLockService.LockResult;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsUrgentOrderManagementController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	private VasDetailService vasDetailService;
	private MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService;
	private OrderService orderService;
	private DeliveryService deliveryService;
	private MobCcsSupportDocService mobCcsSupportDocService;
	private CodeLkupService codeLkupService;
	private MobCcsPaymentAdminService mobCcsPaymentAdminService; 
	private ReleaseLockService releaseLockService;
	private DepositService depositService;
	
	@Autowired
	private MobCosReportService mobCosReportService;
	
	private PaymentDTO paymentDTO;
	private OrderDTO orderDTO;
	private BasketDTO basketDTO;
	private DeliveryUI deliveryUI;
	private PaymentUpFrontUI paymentUpFrontUI;
	private MobCcsSupportDocUI supportingDoc;
	
	private boolean isSupportDocVerified;
	private boolean isPaymantUpfrontVerified;
	private boolean isPaymantMonthlyVerified;
	
	public ReleaseLockService getReleaseLockService() {
		return releaseLockService;
	}

	public void setReleaseLockService(ReleaseLockService releaseLockService) {
		this.releaseLockService = releaseLockService;
	}	
	/**
	 * @param mobCcsPaymentAdminService the mobCcsPaymentAdminService to set
	 */
	public void setMobCcsPaymentAdminService(
			MobCcsPaymentAdminService mobCcsPaymentAdminService) {
		this.mobCcsPaymentAdminService = mobCcsPaymentAdminService;
	}
	
	/**
	 * @return the supportingDoc
	 */
	public MobCcsSupportDocUI getSupportingDoc() {
		return supportingDoc;
	}

	/**
	 * @return the paymentUpFrontUI
	 */
	public PaymentUpFrontUI getPaymentUpFrontUI() {
		return paymentUpFrontUI;
	}

	/**
	 * @return the deliveryUI
	 */
	public DeliveryUI getDeliveryUI() {
		return deliveryUI;
	}

	/**
	 * @return the basketDTO
	 */
	public BasketDTO getBasketDTO() {
		return basketDTO;
	}

	/**
	 * @return the orderDTO
	 */
	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	/**
	 * @return the paymentDTO
	 */
	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	/**
	 * @return the mobCcsPaymentAdminService
	 */
	public MobCcsPaymentAdminService getMobCcsPaymentAdminService() {
		return mobCcsPaymentAdminService;
	}


	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public MobCcsSupportDocService getMobCcsSupportDocService() {
		return mobCcsSupportDocService;
	}

	public void setMobCcsSupportDocService(
			MobCcsSupportDocService mobCcsSupportDocService) {
		this.mobCcsSupportDocService = mobCcsSupportDocService;
	}

	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public MobCcsPaymentUpfrontService getMobCcsPaymentUpfrontService() {
		return mobCcsPaymentUpfrontService;
	}

	public void setMobCcsPaymentUpfrontService(
			MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService) {
		this.mobCcsPaymentUpfrontService = mobCcsPaymentUpfrontService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}
	
	public DepositService getDepositService() {
		return depositService;
	}

	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}

	public MobCcsUrgentOrderManagementController() {
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		String orderId  = (String) request.getParameter("orderId");
		request.getSession().setAttribute("orderIdSession", orderId);
		String basketId = orderService.findBasketId(orderId);
		String locale = RequestContextUtils.getLocale(request).toString();
		orderDTO = orderService.getOrderWithPaidAmount(orderId);
		String appDate = Utility.date2String(orderDTO.getAppInDate(),
				BomWebPortalConstant.DATE_FORMAT);
		deliveryUI = deliveryService.getMobCcsDelivery(orderId);
		
		basketDTO = vasDetailService.getBasketAttribute(basketId, appDate);
		paymentUpFrontUI = mobCcsPaymentUpfrontService.getPaymentUpfront(orderId);
		paymentDTO = orderService.getPayment(orderId);
		MobCcsUrgentOrderManagementUI dto = new MobCcsUrgentOrderManagementUI();

		//MobCcsPaymentTransDTO preTrans= new MobCcsPaymentTransDTO();
		//preTrans.setPay_amount(new BigDecimal( basketDTO.getPrePaymentAmt()));
				
		dto.setOrderId(orderId);
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		LockResult lockresult =null;
		lockresult= this.releaseLockService.getOrderLockInfo(orderDTO.getOrderId(), salesUserDto.getUsername());
		if (LockResult.LOCKED_BY_OTHER_USER==lockresult){
			dto.setOrderLockInd(true);
		}else if (LockResult.LOCK_FREE == lockresult){
			this.releaseLockService.updateOrderLockInd(orderDTO.getOrderId(), "Y", salesUserDto.getUsername());
			dto.setOrderLockInd(false);  
		} else if (LockResult.LOCKED_BY_YOURSELF == lockresult) {
			dto.setOrderLockInd(false);
		}
		
		List<MobCcsPaymentTransDTO> paymentTransList =  mobCcsPaymentAdminService.getMobCcsPaymentTransDTOByOrderId(orderId);
		Double vasHSAmt = mobCcsPaymentAdminService.getVasHSAmt(orderId);
		
		BigDecimal depositAmt = depositService.getDepositAmountForOrder(orderId);
		depositAmt = (depositAmt==null) ? BigDecimal.ZERO : depositAmt;
		
		BigDecimal simAdminCharge = mobCcsPaymentAdminService.getChangeSimChargeForOrder(orderId);
		simAdminCharge = (simAdminCharge==null) ? BigDecimal.ZERO : simAdminCharge;
		
		BigDecimal prepayment = mobCcsPaymentAdminService.getPrePaymentForOrder(orderId);
		prepayment = (prepayment==null) ? BigDecimal.ZERO : prepayment;
		
		BigDecimal basketHsAmt = mobCcsPaymentAdminService.getBasketHsAmtForOrder(orderId);
		basketHsAmt = (basketHsAmt==null) ? BigDecimal.ZERO : basketHsAmt;
		
		
		if (paymentDTO != null) {
			if (("M".equalsIgnoreCase(paymentDTO.getPayMethodType())) 
					|| ("C".equalsIgnoreCase(paymentDTO.getPayMethodType()) && "Y".equalsIgnoreCase(paymentDTO.getCreditCardVerifiedInd()))) {
				dto.setPaymantMonthlyVerifiedInd(true);
			} else {
				dto.setPaymantMonthlyVerifiedInd(false);
			}
		}else{
			//add cos order if no monthly payment, set verified to true
			if ("COS".equals(orderDTO.getBusTxnType()) || "BRM".equals(orderDTO.getBusTxnType())|| "TOO1".equals(orderDTO.getBusTxnType())){
				dto.setPaymantMonthlyVerifiedInd(true);
			}else{
				dto.setPaymantMonthlyVerifiedInd(false);
			}
		}
		
		MobCcsPaymentTransDTO tran = null;
		
		supportingDoc = mobCcsSupportDocService.getMobCcsSupportDocUI(orderId, locale);
		
		//Step tru every single supporting document, enable verified checker which is located
		//in Step 2 : support document table if one of the documents need to be verified
		dto.setSupportDocVerifiedInd(true);
		for (MobCcsSupportDocDTO doc : supportingDoc.getMobCcsSupportDocDTOList()) {
			if (doc.isReceivedByFax() && doc.isMandatory() 
					&& (StringUtils.isBlank(doc.getVerified()) || "N".equalsIgnoreCase(doc.getVerified()))) {
				dto.setSupportDocVerifiedInd(false);
				break;
			}
		}
		
		
		//1. BASKET HS AMT --> basketHsAmt
		//2. VAS HS AMT --> vasHSAmt
		//3.+4.  basket prepayment + optional vas amt --> prepayment
		//5. deposit --> depositAmt
		//6. Change SIM Charge for RET --> simAdminCharge
		
		// Method: Cash -->  Amt = 1+2+3+4+5+6
		// Method: Credit Card --> Amt = 1+2+3+4+5+6
		// Method: Credit Card Installment --> First row = HS AMT = 1+2     second row = prepayment = 3+4+5+6
		// Cannot handle BVAS payment group = HANDSET ( because basketHsAmt look for W_ITEM TYPE = 'HS') 
		
		
		if (paymentUpFrontUI!=null ){
				tran = new MobCcsPaymentTransDTO();
				
				tran.setPay_amount(BigDecimal.ZERO);
				tran.setOrder_id(orderId);
				tran.setMsisdn(orderDTO.getMsisdn());
				tran.setDeliveryDate(deliveryUI.getDeliveryDate());
				tran.setPay_method(paymentUpFrontUI.getPayMethodType());
				tran.setCcNum(paymentUpFrontUI.getCreditCardNum());
				
				if (paymentUpFrontUI.getCreditExpiryMonth() != null && paymentUpFrontUI.getCreditExpiryYear() != null) {
					tran.setCcExpDate(paymentUpFrontUI.getCreditExpiryMonth() + "/" + paymentUpFrontUI.getCreditExpiryYear());
				}
				tran.setCcIssueBank(paymentUpFrontUI.getCreditCardIssueBankCd());
				if (paymentUpFrontUI.getCcInstSchedule() != null) {
					tran.setCcInstSchedule(Integer.parseInt(paymentUpFrontUI.getCcInstSchedule()));
				}
				
				tran.setCreateBy(salesUserDto.getUsername());
				tran.setLastUpdateBy(salesUserDto.getUsername());
				
				if (paymentTransList != null && !paymentTransList.isEmpty()) {
					
					//credit card or cash payment
					if ("C".equalsIgnoreCase(tran.getPay_method()) || "M".equalsIgnoreCase(tran.getPay_method())) {
						
						BigDecimal sumAmount = BigDecimal.ZERO;
						for (MobCcsPaymentTransDTO payTran : paymentTransList) {
							sumAmount = sumAmount.add(payTran.getPay_amount());
						}
						
						//BigDecimal currentPayAmount = new BigDecimal(basketDTO.getUpfrontAmt()).add(new BigDecimal(basketDTO.getPrePaymentAmt()));
						//BigDecimal vasOnetimeAmt = this.optionalVasUpfrontAmt(locale, orderId);
						BigDecimal currentPayAmount = basketHsAmt.add(prepayment).add(new BigDecimal(vasHSAmt));
						
						if (logger.isDebugEnabled()) {
							//logger.debug("currentPayAmount: " + currentPayAmount + ", vasOnetimeAmt: " + vasOnetimeAmt + ", sumAmount: " + sumAmount);
							logger.debug("currentPayAmount: " + currentPayAmount + ", sumAmount: " + sumAmount);
						}
						//currentPayAmount = currentPayAmount.add(vasOnetimeAmt).add(depositAmt).add(simAdminCharge);
						currentPayAmount = currentPayAmount.add(depositAmt).add(simAdminCharge);
						BigDecimal netAmount = currentPayAmount.subtract(sumAmount);
						if (logger.isDebugEnabled()) {
							logger.debug("netAmount: " + netAmount);
						}
						tran.setPay_amount(netAmount);
						
						if ("C".equalsIgnoreCase(tran.getPay_method())) {
							tran.setPayTypeDesc("Credit Card Payment (Net Amount)");
							if (netAmount.compareTo(BigDecimal.ZERO) == 0) {
								tran.setApproveFlag(false);
								tran.setBatchNumFlag(false);
								dto.setPaymantUpfrontVerifiedInd(true);
								dto.setSysPayUpFrontInd(true);
							} else if (netAmount.compareTo(BigDecimal.ZERO) < 0) {
								tran.setApproveFlag(false);
								tran.setBatchNumFlag(false);
								dto.setPaymantUpfrontVerifiedInd(false);
								dto.setSysPayUpFrontInd(false);
							} else if (netAmount.compareTo(BigDecimal.ZERO) > 0) {
								tran.setApproveFlag(true);
								tran.setBatchNumFlag(true);
								dto.setPaymantUpfrontVerifiedInd(false);
								dto.setSysPayUpFrontInd(false);
								
							} 
						} else if ("M".equalsIgnoreCase(tran.getPay_method())) {
							tran.setPayTypeDesc("Cash Payment (Net Amount)");
							dto.setPaymantUpfrontVerifiedInd(true);
							dto.setSysPayUpFrontInd(true);
						}
						
						dto.setPaymentTransList(tran);
					} else { //credit card installment payment method
						BigDecimal sumPrepay = BigDecimal.ZERO, sumHandset = BigDecimal.ZERO; 
						for (MobCcsPaymentTransDTO payTran : paymentTransList) {
							if (payTran.getCcInstSchedule() == null ) {
								sumPrepay = sumPrepay.add(payTran.getPay_amount());
							} else {
								sumHandset = sumHandset.add(payTran.getPay_amount());
							}
						}
							
						MobCcsPaymentTransDTO oldHandset = new MobCcsPaymentTransDTO();
						MobCcsPaymentTransDTO newHandset = new MobCcsPaymentTransDTO();
						MobCcsPaymentTransDTO prepay = new MobCcsPaymentTransDTO();
						
						try {
							BeanUtils.copyProperties(oldHandset, tran);
							BeanUtils.copyProperties(newHandset, tran);
							BeanUtils.copyProperties(prepay, tran);
						} catch (IllegalAccessException e) {
							logger.error(ExceptionUtils.getFullStackTrace(e));
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							logger.error(ExceptionUtils.getFullStackTrace(e));
							e.printStackTrace();
						}
						
						prepay.setPayTypeDesc("Pre-payment (Net Amount)");
						/*BigDecimal netPrepayAmount = new BigDecimal(basketDTO.getPrePaymentAmt())
								.add(this.optionalVasUpfrontAmt(locale, orderId))
								.add(depositAmt)
								.add(simAdminCharge)
								.subtract(new BigDecimal(vasHSAmt))
								.subtract(sumPrepay);*/
						BigDecimal netPrepayAmount = prepayment.add(depositAmt).add(simAdminCharge).subtract(sumPrepay);
						
						prepay.setPay_amount(netPrepayAmount);
						prepay.setCcInstSchedule(null);
						
						if (netPrepayAmount.compareTo(BigDecimal.ZERO) == 0) {
							prepay.setApproveFlag(false);
							prepay.setBatchNumFlag(false);
						} else {
							prepay.setApproveFlag(true);
							prepay.setBatchNumFlag(true);
						} 
						
						oldHandset.setPayTypeDesc("Handset Payment ");
						oldHandset.setPay_amount(sumHandset.negate());
						
						newHandset.setPayTypeDesc("Handset Payment ");
						//newHandset.setPay_amount((new BigDecimal(basketDTO.getUpfrontAmt())).add(new BigDecimal(vasHSAmt)));
						newHandset.setPay_amount(basketHsAmt.add(new BigDecimal(vasHSAmt)));
						
						BigDecimal netHandsetAmount = sumHandset.subtract(newHandset.getPay_amount());
						if (netHandsetAmount.compareTo(BigDecimal.ZERO) == 0) {
							newHandset.setApproveFlag(false);
						} else if (netHandsetAmount.compareTo(BigDecimal.ZERO) != 0) {
							newHandset.setApproveFlag(true);
						}
						
						if (netHandsetAmount.compareTo(BigDecimal.ZERO) == 0 && netPrepayAmount.compareTo(BigDecimal.ZERO) == 0) {
							dto.setPaymantUpfrontVerifiedInd(true);
							dto.setSysPayUpFrontInd(true);
						} else {
							dto.setPaymantUpfrontVerifiedInd(false);
							dto.setSysPayUpFrontInd(false);
						}
						
						dto.setPaymentTransList(oldHandset);
						dto.setPaymentTransList(newHandset);
						dto.setPaymentTransList(prepay);
					}
					
				//First time payment
				} else {
					
					dto.setPaymantUpfrontVerifiedInd(paymentUpFrontUI.getPayMethodType().equalsIgnoreCase("M") ? true : false);
					dto.setSysPayUpFrontInd(dto.getPaymantUpfrontVerifiedInd() ? true : false);
					
					//Installment payment type
					if ("I".equalsIgnoreCase(tran.getPay_method())) {
						tran.setApproveFlag(true);
						
						MobCcsPaymentTransDTO handset = new MobCcsPaymentTransDTO();
						MobCcsPaymentTransDTO prepay = new MobCcsPaymentTransDTO();
						
						try {
							BeanUtils.copyProperties(handset, tran);
							BeanUtils.copyProperties(prepay, tran);
						} catch (IllegalAccessException e) {
							logger.error(ExceptionUtils.getFullStackTrace(e));
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							logger.error(ExceptionUtils.getFullStackTrace(e));
							e.printStackTrace();
						}
						
						handset.setPayTypeDesc("Handset Payment");
						//handset.setPay_amount((new BigDecimal(basketDTO.getUpfrontAmt())).add(new BigDecimal(vasHSAmt)));
						handset.setPay_amount((basketHsAmt).add(new BigDecimal(vasHSAmt)));
						if (logger.isDebugEnabled()) {
							logger.debug("Handset Payment: " + handset.getPay_amount());
						}
						handset.setBatchNumFlag(true);
						if (StringUtils.isNotBlank(paymentUpFrontUI.getCcInstSchedule())) {
							handset.setCcInstSchedule(Integer.valueOf(paymentUpFrontUI.getCcInstSchedule()));
						}
						
						prepay.setPayTypeDesc("Pre-payment");
						/*prepay.setPay_amount(new BigDecimal(basketDTO.getPrePaymentAmt())
							.add(this.optionalVasUpfrontAmt(locale, orderId))
							.add(depositAmt)
							.add(simAdminCharge)
							.subtract(new BigDecimal(vasHSAmt)));*/
						prepay.setPay_amount(prepayment
								.add(depositAmt)
								.add(simAdminCharge)
								);
						if (logger.isDebugEnabled()) {
							logger.debug("Pre-payment: " + prepay.getPay_amount());
						}
						prepay.setCcInstSchedule(null);
						prepay.setBatchNumFlag(true);
						
						dto.setPaymentTransList(handset);
						dto.setPaymentTransList(prepay);
						
					} else {
						//Credit card payment type
						if ("C".equalsIgnoreCase(tran.getPay_method())) {
							tran.setPayTypeDesc("Credit Card Payment");
							tran.setApproveFlag(true);
							tran.setBatchNumFlag(true);
						} else { //Cash payment type
							tran.setPayTypeDesc("Cash Payment");
						}
						
					
						/*tran.setPay_amount(new BigDecimal(basketDTO.getUpfrontAmt())
							.add(new BigDecimal(basketDTO.getPrePaymentAmt()))
							.add(this.optionalVasUpfrontAmt(locale, orderId))
							.add(depositAmt)
							.add(simAdminCharge));*/
						tran.setPay_amount(basketHsAmt
								.add(prepayment)
								.add(new BigDecimal(vasHSAmt))
								.add(depositAmt)
								.add(simAdminCharge));
						
						dto.setPaymentTransList(tran);
					}
							
				}
		
		}else{  //if cos order and no upfront payment 
			if ("COS".equals(orderDTO.getBusTxnType()) || "BRM".equals(orderDTO.getBusTxnType()) || "TOO1".equals(orderDTO.getBusTxnType())){
				dto.setSysPayUpFrontInd(true);
				dto.setPaymantUpfrontVerifiedInd(true);
				dto.setPaymentTransList(null);
			}else{
				dto.setSysPayUpFrontInd(false);
				dto.setPaymantUpfrontVerifiedInd(false);
			}
		}
		
		isSupportDocVerified = dto.getSupportDocVerifiedInd();
		isPaymantMonthlyVerified = dto.getPaymantMonthlyVerifiedInd();
		isPaymantUpfrontVerified = dto.getPaymantUpfrontVerifiedInd();
		
		return dto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		MobCcsUrgentOrderManagementUI dto = (MobCcsUrgentOrderManagementUI)command;
		
		logger.debug("getOrderId" + dto.getOrderId());
		logger.debug("getActionType" + dto.getActionType());
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		dto.setLastUpdBy(salesUserDto.getUsername());
				
		if (dto.getActionType().equalsIgnoreCase("SUBMITTOBOM")) {
						
			//Step tru every single supporting document, enable verified checker which is located
			//in Step 2 : support document table if one of the documents need to be verified
			boolean isEditedSupportdoc = false; 
			for (MobCcsSupportDocDTO doc : supportingDoc.getMobCcsSupportDocDTOList()) {
				if (doc.isReceivedByFax()) {
					doc.setVerified("Y");
					isEditedSupportdoc = true;
				}
			}
			if (isEditedSupportdoc) {
				mobCcsSupportDocService.updateMobCcsSupportDocUI(supportingDoc);
			}
			
			if (paymentDTO!=null){
				if ("C".equalsIgnoreCase(paymentDTO.getPayMethodType())) {
					if (StringUtils.isBlank(paymentDTO.getCreditCardVerifiedInd())) {
						paymentDTO.setCreditCardVerifiedInd("Y");
						orderService.updatePayment(paymentDTO);
					}
				}
			}
			
			/*if (paymentUpFrontUI!=null){
				if ("C".equalsIgnoreCase(paymentUpFrontUI.getPayMethodType()) || "I".equalsIgnoreCase(paymentUpFrontUI.getPayMethodType())){
					if (StringUtils.isBlank(paymentUpFrontUI.getCreditCardVerifiedInd())) {
						paymentUpFrontUI.setCreditCardVerifiedInd("Y");
						orderService.update
					}
				}
			}*/
			
			
			if (dto.getPaymantUpfrontVerifiedInd() && !dto.getSysPayUpFrontInd()) {
				for (MobCcsPaymentTransDTO payTrans : dto.getPaymentTransList()) {
					if (!"M".equalsIgnoreCase(payTrans.getPay_method())){
						mobCcsPaymentUpfrontService.insertPaymentTrans(payTrans);
					}
				}
			}		
			

			orderService.manualOrderStatusProcess(dto.getOrderId());
			orderService.orderSubmitProcess(dto.getOrderId());
			
		} else {
			
			orderDTO.setReasonCd(dto.getFalloutReasonCd());
			orderDTO.setLastUpdateBy(salesUserDto.getUsername());
			
			String codeType = null;
			
			if (dto.getFalloutReasonCd().equals("B001") || dto.getFalloutReasonCd().equals("B002")) {
				codeType = "DOC_FAIL";
			} else {
				codeType = "PAY_FAIL";
			}
			
			orderService.updateOrderFallOut(orderDTO);
			orderService.insertOrderFallout(orderDTO.getOrderId(), salesUserDto.getUsername(),
					codeType, dto.getFalloutReasonCd(), null);
		}
		
		releaseLockService.updateOrderLockInd(orderDTO.getOrderId(), "N", salesUserDto.getUsername());
		
		return new ModelAndView(new RedirectView("mobccsurgentordersearch.html"));

	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		Map referenceData = new HashMap<String, List<String>>();

		logger.info("MobCcsUrgentOrderManagementController is called");

		String action = (String) request.getParameter("action");
		String orderId  = (String) request.getParameter("orderId");
				
		PaymentMonthyUI paymentMonthyUI = null;
		
		String locale = RequestContextUtils.getLocale(request).toString();
				
		String appDate = Utility.date2String(orderDTO.getAppInDate(),
				BomWebPortalConstant.DATE_FORMAT);
		
		if (paymentDTO!=null){
			paymentMonthyUI = new PaymentMonthyUI();
			BeanUtils.copyProperties(paymentMonthyUI, paymentDTO);
		}
		
		referenceData.put("orderId", orderId);
		referenceData.put("appDate", appDate);

		referenceData.put("basketDTO", basketDTO);
		referenceData.put("paymentMonthy", paymentMonthyUI);
		referenceData.put("paymentUpFront", paymentUpFrontUI);
		referenceData.put("supportingDoc", supportingDoc);
		referenceData.put("isSupportDocVerified", isSupportDocVerified);
		referenceData.put("isPaymantMonthlyVerified", isPaymantMonthlyVerified);
		referenceData.put("isPaymantUpfrontVerified", isPaymantUpfrontVerified);
		
		referenceData.put("action", action);

		List<CodeLkupDTO> falloutCodeList = codeLkupService.getCodeLkupDTOALL("URG_ORD_FALLOUT_CODE");
		referenceData.put("falloutCodeList", falloutCodeList);

		return referenceData;

	}

	private BigDecimal optionalVasUpfrontAmt(String locale, String orderId) {
		List<VasDetailDTO> vasHSRPList = vasDetailService.getVasDetailSelectedList(locale, orderId);
		BigDecimal vasOnetimeAmt = BigDecimal.ZERO;
		if (!this.isEmpty(vasHSRPList)) {
			for (VasDetailDTO vasHSRP : vasHSRPList) {
				if ("VAS".equals(vasHSRP.getItemType())) {
					if (StringUtils.isNotBlank(vasHSRP.getItemOnetimeAmt())) {
						vasOnetimeAmt = vasOnetimeAmt.add(new BigDecimal(vasHSRP.getItemOnetimeAmt()));
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("orderId: " + orderId + ", locale: " + locale + ", vasOnetimeAmt: " + vasOnetimeAmt);
		}
		return vasOnetimeAmt;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
