package com.bomwebportal.mob.ccs.web;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocUI;
import com.bomwebportal.mob.ccs.dto.MobSponsorshipDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentMonthyUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentUpfrontService;
import com.bomwebportal.mob.ccs.service.MobCcsSupportDocService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsSupportDocController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private MobCcsSupportDocService mobCcsSupportDocService;
	private MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService;
	private OrderService orderService;
	private VasDetailService vasDetailService;
	private CodeLkupService codeLkupService;

	public MobCcsSupportDocService getMobCcsSupportDocService() {
		return mobCcsSupportDocService;
	}

	public void setMobCcsSupportDocService(
			MobCcsSupportDocService mobCcsSupportDocService) {
		this.mobCcsSupportDocService = mobCcsSupportDocService;
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

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		logger.info("MobCcsSupportDocController formBackingObject called");
		
		MobCcsSupportDocUI sdUI = (MobCcsSupportDocUI) MobCcsSessionUtil
				.getSession(request, "supportingDoc");
		if (sdUI == null) {
			sdUI = new MobCcsSupportDocUI();
			sdUI.setMobCcsSupportDocDTOList(mobCcsSupportDocService
					.getMobCcsSupportDocDTOInitialList("en"));

		}
		/*sdUI.setIdDocCopyWaived(false);*/
		integrateSupportDoc(request, sdUI);

		MobCcsSessionUtil.setSession(request, "supportingDoc", sdUI);
		return sdUI;
	}

	private void updateSupportDoc(MobCcsSupportDocDTO doc, boolean docRequired,
			String docValue) {

		if ((!docRequired && doc.isMandatory())
				|| (docRequired && !doc.isMandatory())) {

			/*
			 * if (doc.isVerified()) { doc.setFaxServerSerialNo(null);
			 * doc.setReceivedByFax(false); doc.setVerified(false);
			 * doc.setRecalled(false); }
			 */

			if (doc.isRecalled()) {
				// Y->N
				if (doc.isMandatory() && !docRequired) {
					doc.setRemovable(true);
					// N->Y
				} else {
					doc.setFaxServerSerialNo(null);
					doc.setReceivedByFax(false);
					doc.setRemovable(false);
					doc.setVerified(null);
				}
			} else {
				doc.setFaxServerSerialNo(null);
				doc.setReceivedByFax(false);
				doc.setRemovable(false);
				doc.setVerified(null);
			}

			doc.setMandatory(docRequired);
		} 

		if (docRequired) {
			// Doc value of session is different from database
			if (doc.getDocValue() != null && docValue != null) {
				if (!doc.getDocValue().equalsIgnoreCase(docValue.trim())) {
					
					doc.setDocValue(docValue.trim());
					if (doc.isFaxMandatory()) {
						doc.setReceivedByFax(true);
					} else {
						doc.setReceivedByFax(false);
					}
					doc.setFaxServerSerialNo(null);
					doc.setVerified(null);
				}
			} else {
				doc.setDocValue(docValue == null ? null : docValue.trim());
			}

		}
	}


	private void integrateSupportDoc(HttpServletRequest request,
			MobCcsSupportDocUI supportDoc) {
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) MobCcsSessionUtil
				.getSession(request, "customer");
		MRTUI mrtUI = (MRTUI) MobCcsSessionUtil.getSession(request, "mrt");
		PaymentMonthyUI paymentMonthyUI = (PaymentMonthyUI) MobCcsSessionUtil
				.getSession(request, "paymentMonthy");
		PaymentUpFrontUI paymentUpFrontUI = (PaymentUpFrontUI) MobCcsSessionUtil
				.getSession(request, "paymentUpFront");
		BasketDTO basketDto = (BasketDTO) MobCcsSessionUtil.getSession(request,
				"basket");
		
		DeliveryUI deliveryUI = (DeliveryUI) MobCcsSessionUtil
				.getSession(request, "delivery");

		MobSponsorshipDTO mobSponsorshipDTO = (MobSponsorshipDTO)MobCcsSessionUtil.getSession(request, "mobSponsorshipDTO");

		List<MobCcsSupportDocDTO> mobCcsSupportDocList = supportDoc
				.getMobCcsSupportDocDTOList();
		String[] vasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		String basketId = (String) request.getSession().getAttribute("basketSession");

		for (MobCcsSupportDocDTO sd : mobCcsSupportDocList) {

			boolean requiredInDB = mobCcsSupportDocService.isSupportDocRequired(sd.getDocId(), basketId, vasItemList, "CS");
			// Doc ID 1: ID Document Copy
			if ("01".equals(sd.getDocId()) && customerInfoDto != null) {
				updateSupportDoc(sd, sd.isMandatory() || requiredInDB, customerInfoDto.getIdDocNum());
			}

			// Doc ID 2: Address Proof
			else if ("02".equals(sd.getDocId()) && customerInfoDto != null) {
				// if "2".equals(customerInfoDto.getAddrProofInd() == true,
				// address proof is not required
				
				//boolean addressProof = !"2".equals(customerInfoDto.getAddrProofInd()) || requiredInDB;
				
				// only for With Address Proof (1) case   
				boolean addressProof = "1".equals(customerInfoDto.getAddrProofInd()) || requiredInDB;

				updateSupportDoc(sd, addressProof,
						customerInfoDto.getAddress());
			}

			// Doc ID 3: Monthly payment - Credit Card Copy
			/*if ("03".equals(sd.getDocId()) && paymentMonthyUI != null) {
				boolean creditCardCopyRequired = "C"
						.equalsIgnoreCase(paymentMonthyUI.getPayMethodType());

				updateSupportDoc(sd, creditCardCopyRequired,
						paymentMonthyUI.getCreditCardNum());

			}*/

			// Doc ID 04: Monthly payment -
			// 3rd Party Credit Card Authorization and
			// 3rd Party Credit Card Holder ID Document Copy
			else if ("04".equals(sd.getDocId()) || "05".equals(sd.getDocId())) {
				if (paymentMonthyUI != null) {
					boolean thirdPartyCreditCard = ("Y"
							.equalsIgnoreCase(paymentMonthyUI.getThirdPartyInd()) && "C"
							.equalsIgnoreCase(paymentMonthyUI.getPayMethodType())) || requiredInDB;
	
					updateSupportDoc(sd, thirdPartyCreditCard,
							paymentMonthyUI.getCreditCardNum());
				}
			}

			// Doc ID 06: Upfront payment - Credit Card Installment Form
			else if ("06".equals(sd.getDocId()) && paymentUpFrontUI != null) {
				// Case I and 3rd Party are selected
				boolean creditCardInstallment = "I".equalsIgnoreCase(paymentUpFrontUI.getPayMethodType()) 
												|| requiredInDB;
				if (creditCardInstallment) {
					// Exemption of Credit Card Installment Form in Support Document
					if (logger.isDebugEnabled()) {
						logger.debug("creditCardIssueBankCd: " + paymentUpFrontUI.getCreditCardIssueBankCd());
					}
					List<CodeLkupDTO> exempInstallBanks = this.codeLkupService.getCodeLkupDTOALL("EXEMP_INSTALL_BANK");
					if (!this.isEmpty(exempInstallBanks)) {
						for (CodeLkupDTO exempInstallBank : exempInstallBanks) {
							if (exempInstallBank.getCodeId().equals(paymentUpFrontUI.getCreditCardIssueBankCd())) {
								if (logger.isDebugEnabled()) {
									logger.debug("exempInstallBank: " + exempInstallBank.getCodeId() + " matched");
								}
								creditCardInstallment = false;
								break;
							}
						}
					}
				}
				updateSupportDoc(sd, creditCardInstallment, paymentUpFrontUI.getCreditCardNum());
				if (this.creditCardInstallmentChanged(paymentUpFrontUI)) {
					sd.setFaxServerSerialNo(null);
					sd.setVerified(null);
				}
			}

			// Doc ID 07: MNP - MNP Application Form
			else if ("07".equals(sd.getDocId()) && mrtUI != null) {
				boolean MNPApplicationForm = "Y".equalsIgnoreCase(mrtUI.getMnpInd()) || "A".equalsIgnoreCase(mrtUI.getMnpInd()) || requiredInDB || isMNPMultiSIM(request);

				updateSupportDoc(sd, MNPApplicationForm, mrtUI.getMnpMsisdn());// mnpMsisdn
			}

			// Doc ID 08: Staff Copy
			else if ("08".equals(sd.getDocId()) && basketDto != null) {
				boolean staffCopy = "3".equals(basketDto.getBasketOfferTypeCd()) || requiredInDB;

				updateSupportDoc(sd, staffCopy, null);

			}

			// Doc ID 09: Student Card Copy
			else if ("09".equals(sd.getDocId()) && basketDto != null) {

				boolean studCardCopy = "2".equals(basketDto.getBasketOfferTypeCd()) || requiredInDB;

				updateSupportDoc(sd, studCardCopy, null);

			}

			// Doc ID 10: Change of Service Form
			else if ("10".equals(sd.getDocId()) && mrtUI != null
					&& customerInfoDto != null) {

				boolean changeServiceForm = requiredInDB;/*(
						"A".equalsIgnoreCase(mrtUI.getMrtInd())
	
				);*/
				//edit by wilson 20120628
				//If (MRT_IND = ‘A’) or (MNP_IND = ‘Y’ and MNP ID Doc No <> Cust ID Doc No and NOT prepaid SIM)
				if ("A".equalsIgnoreCase(mrtUI.getMrtInd())){
					changeServiceForm =true;
					
				}else if ("Y".equalsIgnoreCase(mrtUI.getMnpInd())  ){

					if (!mrtUI.getDocNum().equalsIgnoreCase(customerInfoDto.getIdDocNum())){
						changeServiceForm=true;
					}
					
					if ("Prepaid Sim".equalsIgnoreCase(mrtUI.getCustName())){
						changeServiceForm=false;
					}
				}
				
				updateSupportDoc(sd, changeServiceForm || isMNPTransferMultiSIM(request), mrtUI.getDocNum());
			}

			// Doc ID 11: Foreign Domestic Helper Contract Copy
			else if ("11".equals(sd.getDocId()) && customerInfoDto != null) {

				boolean foreignHelperCopy = ("HKID".equalsIgnoreCase(customerInfoDto.getIdDocType())
						&& (customerInfoDto.getIdDocNum().toUpperCase().startsWith("W"))
						&& customerInfoDto.isForeignDomesticHelperInd()) 
						|| requiredInDB;

				updateSupportDoc(sd, foreignHelperCopy, null);

			}

			// Doc ID 12: Prepaid SIM copy
			else if ("12".equals(sd.getDocId()) && mrtUI != null) {

				boolean prepaidSIMCopy = "Prepaid Sim".equalsIgnoreCase(mrtUI.getCustName()) || requiredInDB || isMNPTransferPrepaidMultiSIM(request);

				updateSupportDoc(sd, prepaidSIMCopy, mrtUI.getDocNum());

			}
			
			// Doc ID 13: Concierge Service Authorization Letter
			else if ("13".equals(sd.getDocId()) && basketDto != null) {

				boolean conciergeAuthorization = "6".equals(basketDto.getBasketTypeId()) || requiredInDB;

				updateSupportDoc(sd, conciergeAuthorization, null);

			}
			// Doc ID 14: Authorization Letter for 3rd party collection			
			else if ("14".equals(sd.getDocId()) && deliveryUI != null) {

				boolean thirdPartyCollection = deliveryUI.isRecieveByThirdPartyInd() || requiredInDB;

				updateSupportDoc(sd, thirdPartyCollection, deliveryUI.getThirdPartyContact().getIdDocNum());

			}
			
			// Doc ID 15: (BR customer) Business Name Card of Contact person
			else if ("15".equals(sd.getDocId()) && customerInfoDto != null) {

				boolean businessNameCard = ("BS".equalsIgnoreCase(customerInfoDto.getIdDocType()) && "C".equalsIgnoreCase(customerInfoDto.getCompanyDoc())) || requiredInDB;
				updateSupportDoc(sd, businessNameCard, customerInfoDto.getIdDocNum());
			}
			
			// Doc ID 16: (BR customer) Business Name Card / Staff copy of User <- Old version
			// Doc ID 16: 2N register HKID copy (MNP owner ID copy) <- 20130822 version
			else if ("16".equals(sd.getDocId()) && customerInfoDto != null && mrtUI != null) {
				boolean twoNRegisterHKIDCopy = requiredInDB;
				if ("Y".equalsIgnoreCase(mrtUI.getMnpInd()) || "A".equalsIgnoreCase(mrtUI.getMrtInd())) {
					twoNRegisterHKIDCopy = (!customerInfoDto.getIdDocNum().equalsIgnoreCase(mrtUI.getDocNum())) || requiredInDB ;
					if ("Prepaid Sim".equalsIgnoreCase(mrtUI.getCustName())) {
						twoNRegisterHKIDCopy = false;
					}
				}
				updateSupportDoc(sd, twoNRegisterHKIDCopy || isMNPTransferMultiSIM(request), mrtUI.getDocNum());
			}
			
			else if ("17".equals(sd.getDocId()) && mobSponsorshipDTO != null) {
				
				boolean required = StringUtils.isNotEmpty(mobSponsorshipDTO.getStaffId());
				updateSupportDoc(sd, required, null);
			}
			// Doc ID 22: (BR customer) Authorized Letter
			else if ("22".equals(sd.getDocId()) && customerInfoDto != null) {

				boolean authorizedLetter = ("BS".equalsIgnoreCase(customerInfoDto.getIdDocType()) && "L".equalsIgnoreCase(customerInfoDto.getCompanyDoc())) || requiredInDB;
				updateSupportDoc(sd, authorizedLetter, customerInfoDto.getIdDocNum());
			}
			// Others
			else {
				updateSupportDoc(sd, requiredInDB, null);
			}
			
		}

		supportDoc.setMobCcsSupportDocDTOList(mobCcsSupportDocList);

	}


	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		logger.info("MobCcsSupportDocController is called!");		

		String nextView = (String) request.getAttribute("nextView");
		logger.info("Next View: " + nextView);

		MobCcsSupportDocUI sdUI = (MobCcsSupportDocUI) MobCcsSessionUtil
				.getSession(request, "supportingDoc");
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) MobCcsSessionUtil
				.getSession(request, "customer");


		if (sdUI != null) {
			List<MobCcsSupportDocDTO> sdList = sdUI
					.getMobCcsSupportDocDTOList();
			for (MobCcsSupportDocDTO sd : sdList) {
				

				if ("01".equals(sd.getDocId())){
					if (customerInfoDto != null) {
						updateSupportDoc(sd, sd.isMandatory(),
								customerInfoDto.getIdDocNum());
					}	
				}
				
				if (!sd.isReceivedByFax()){
					sd.setFaxServerSerialNo(null);
				}
				
				if (sd.getFaxServerSerialNo() != null) {
					if (!StringUtils.isAsciiPrintable(sd.getFaxServerSerialNo())) {
						return new ModelAndView(new RedirectView("mobccssupportdoc.html"));
					}
				}
				
				// If it's a failed case and user updated the fax serial no. verify
				// indicator will be marked to null				
				if (sd.getVerified() != null && "N".equalsIgnoreCase(sd.getVerified())	) {//add check getFaxServerSerialNo != null and sd.getStoredFaxServerSerialNo()!=null
					if (sd.getFaxServerSerialNo()!= null && sd.getStoredFaxServerSerialNo()!=null && !sd.getFaxServerSerialNo()	.trim()	.equalsIgnoreCase(sd.getStoredFaxServerSerialNo())){
						sd.setVerified(null);
					}
				}
			}
			
			MobCcsSessionUtil.setSession(request, "supportingDoc", sdUI);
		}

		return new ModelAndView(new RedirectView(nextView));
	}
	
	private boolean creditCardInstallmentChanged(PaymentUpFrontUI paymentUpFrontUI) {
		// payMethodType: Cash (M) / CreditCard (C) / CreditCard Installment
		if (logger.isDebugEnabled()) {
			logger.debug("orderId: " + paymentUpFrontUI.getOrderId());
		}
		// new order creation
		if (StringUtils.isBlank(paymentUpFrontUI.getOrderId())) {
			return false;
		}
		boolean newCreditCardInstallment = "I".equalsIgnoreCase(paymentUpFrontUI.getPayMethodType());
		if (logger.isDebugEnabled()) {
			logger.debug("new payMethodType: " + paymentUpFrontUI.getPayMethodType());
		}
		// now not creditCardInstalment
		if (!newCreditCardInstallment) {
			return false;
		}
		// upfrontAmt changed
		if (StringUtils.isNotBlank(paymentUpFrontUI.getUpfrontAmt())) {
			OrderDTO orderDTO = orderService.getOrder(paymentUpFrontUI.getOrderId());
			String basketId = orderService.findBasketId(orderDTO.getOrderId());
			String appDate = Utility.date2String(orderDTO.getAppInDate(), BomWebPortalConstant.DATE_FORMAT);
			BasketDTO basketDTO = vasDetailService.getBasketAttribute(basketId, appDate);
			if (logger.isDebugEnabled()) {
				logger.debug("upfrontAmt: " + basketDTO.getUpfrontAmt() + " -> " + paymentUpFrontUI.getUpfrontAmt());
			}
			if (StringUtils.isNotBlank(basketDTO.getUpfrontAmt())) {
				BigDecimal newUpfrontAmt = new BigDecimal(paymentUpFrontUI.getUpfrontAmt());
				BigDecimal currentUpfrontAmt = new BigDecimal(basketDTO.getUpfrontAmt());
				if (newUpfrontAmt.compareTo(currentUpfrontAmt) != 0) {
					return true;
				}
			}
		}
		PaymentUpFrontUI currentPaymentUpFrontUI = this.mobCcsPaymentUpfrontService.getPaymentUpfront(paymentUpFrontUI.getOrderId());
		boolean currentCreditCardInstallment = "I".equals(currentPaymentUpFrontUI.getPayMethodType());
		if (logger.isDebugEnabled()) {
			logger.debug("current payMethodType: " + currentPaymentUpFrontUI.getPayMethodType());
		}
		if (!currentCreditCardInstallment) {
			return false;
		}
		// (2)     Installment information amendment trigger support document verify indicator overriding:
		// credit card changed
		// Valid Thru
		// Card Holder Name
		// Issue Bank Name
		// Installment Schedule
		for (String field : new String[] { "creditCardNum", "creditExpiryMonth", "creditExpiryYear"
				, "creditCardHolderName", "creditCardIssueBankCd", "ccInstSchedule" }) {
			try {
				String currentValue = BeanUtils.getSimpleProperty(currentPaymentUpFrontUI, field);
				String newValue = BeanUtils.getSimpleProperty(paymentUpFrontUI, field);
				if (logger.isDebugEnabled()) {
					logger.debug(field + ": " + currentValue + " -> " + newValue);
				}
				if (StringUtils.isNotBlank(currentValue)) {
					if (!currentValue.equals(newValue)) {
						return true;
					}
				}
			} catch (Exception e) {
				logger.warn("Exception found", e);
			}
		}
		return false;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	private boolean isMNPMultiSIM(HttpServletRequest request) {
		boolean required = false;
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) MobCcsSessionUtil
				.getSession(request, "customer");
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		
		List<String> vasItemList = vasDetailService.getSubscribedVASList(basketDTO.getBasketId(), selectedVasItemList, customerInfoDto.getBrand(), customerInfoDto.getSimType());
		if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
			for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
				if (vasItemList.contains(msi.getItemId()) && msi.getMembers() != null) {
					for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
						if ("A".equals(msim.getMnpInd()) || "MUPS05".equals(msim.getMemberOrderType())) {
							required = true;
						}
					}
				}
			}
		}
		return required;
	}
	
	private boolean isMNPTransferPrepaidMultiSIM(HttpServletRequest request) {
		boolean required = false;
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) MobCcsSessionUtil
				.getSession(request, "customer");
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		
		List<String> vasItemList = vasDetailService.getSubscribedVASList(basketDTO.getBasketId(), selectedVasItemList, customerInfoDto.getBrand(), customerInfoDto.getSimType());
		if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
			for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
				if (vasItemList.contains(msi.getItemId()) && msi.getMembers() != null) {
					for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
						if (("A".equals(msim.getMnpInd()) || "MUPS05".equals(msim.getMemberOrderType())) && "Prepaid Sim".equals(msim.getMnpCustName())) {
							required = true;
						}
					}
				}
			}
		}
		
		return required;
	}
	
	private boolean isMNPTransferMultiSIM(HttpServletRequest request) {
		boolean required = false;
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) MobCcsSessionUtil
				.getSession(request, "customer");
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		
		List<String> vasItemList = vasDetailService.getSubscribedVASList(basketDTO.getBasketId(), selectedVasItemList, customerInfoDto.getBrand(), customerInfoDto.getSimType());
		if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
			for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
				if (vasItemList.contains(msi.getItemId()) && msi.getMembers() != null) {
					for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
						if (("A".equals(msim.getMnpInd()) || "MUPS05".equals(msim.getMemberOrderType())) && !"Prepaid Sim".equals(msim.getMnpCustName())) {
							if (StringUtils.isBlank(msim.getMnpDocNo())) {
								required = true;
							} else {
								if (msim.getMnpDocNo() != null && customerInfoDto != null && !msim.getMnpDocNo().equals(customerInfoDto.getIdDocNum())) {
									required = true;
								}
							}
						}
					}
				}
			}
		}
		
		return required;
	}
}
