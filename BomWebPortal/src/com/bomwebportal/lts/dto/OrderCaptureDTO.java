package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bomwebportal.dto.ApprovalLoginFormDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketSelectionFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketVasDetailFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketVasParamFormDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerIdentificationFormDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerInquiryFormDTO;
import com.bomwebportal.lts.dto.form.LtsDeviceSelectionFormDTO;
import com.bomwebportal.lts.dto.form.LtsDigitalSignatureFormDTO;
import com.bomwebportal.lts.dto.form.LtsDnChangeDuplexFormDTO;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO;
import com.bomwebportal.lts.dto.form.LtsMiscellaneousFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.form.LtsNowTvServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.form.LtsPremiumSelectionFormDTO;
import com.bomwebportal.lts.dto.form.LtsRecontractFormDTO;
import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.LtsSummaryFormDTO;
import com.bomwebportal.lts.dto.form.LtsSupportDocFormDTO;
import com.bomwebportal.lts.dto.form.LtsWqRemarkFormDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.ret.RenewBasketPolicyDTO;
import com.bomwebportal.lts.util.LtsConstant;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class OrderCaptureDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3679103540376032187L;
	
	private UpgradeEyeInfoDTO upgradeEyeInfo;
	private ServiceDetailProfileLtsDTO ltsServiceProfile;
	private ServiceDetailProfileLtsDTO secondDelServiceProfile;
	private ImsSbOrderDTO relatedPcdOrder;
	private AddressRolloutDTO newAddressRollout;
	private ModemTechnologyAissgnDTO modemTechnologyAissgn;
	private Boolean create2ndDelByReserveDnInd;
	private ImsPendingOrderDTO imsPendingOrder;
	private BasketDetailDTO selectedBasket;
	
	// UI FORM 
	private LtsCustomerInquiryFormDTO ltsCustomerInquiryForm;
	private LtsMiscellaneousFormDTO ltsMiscellaneousForm;
	private LtsRecontractFormDTO ltsRecontractForm;
	private LtsDnChangeFormDTO ltsDnChangeForm;
    private LtsDnChangeDuplexFormDTO ltsDnChangeDuplexForm;
	private ApprovalLoginFormDTO approvalLoginForm;
	private ApprovalLoginFormDTO approvalDuplexLoginForm;
	
	private LtsAddressRolloutFormDTO ltsAddressRolloutForm;
	private LtsModemArrangementFormDTO ltsModemArrangementForm;
	private LtsDeviceSelectionFormDTO ltsDeviceSelectionForm;
	private LtsBasketSelectionFormDTO ltsBasketSelectionForm;
	private LtsBasketServiceFormDTO ltsBasketServiceForm;
	private LtsNowTvServiceFormDTO ltsNowTvServiceForm;
	private LtsBasketVasDetailFormDTO ltsBasketVasDetailForm;
	private LtsBasketVasParamFormDTO ltsBasketVasParamForm;
	private LtsOtherVoiceServiceFormDTO ltsOtherVoiceServiceForm;
	private LtsPremiumSelectionFormDTO ltsPremiumSelectionForm;
	private LtsCustomerIdentificationFormDTO ltsCustomerIdentificationForm;
	private LtsSalesInfoFormDTO ltsSalesInfoForm;
	private LtsPaymentFormDTO ltsPaymentForm;
	private LtsAppointmentFormDTO ltsAppointmentForm;
	private LtsSupportDocFormDTO ltsSupportDocForm;
	private BasketCriteriaDTO basketCriteria;
	private List<RenewBasketPolicyDTO> renewBasketPolicyList;
	
	 @XmlElement(required=true)
	private LtsSummaryFormDTO ltsSummaryForm;
	 
	private LtsWqRemarkFormDTO ltsWqRemarkForm;
	
	private LtsDigitalSignatureFormDTO ltsDigitalSignatureForm;
	
	private SbOrderDTO sbOrder;
	private String orderAction = LtsConstant.ORDER_ACTION_CREATE;
	private boolean isChannelCs = false;
	private boolean isChannelPremier = false;
	private boolean isChannelRetail = false;
	private String basketChannelId; // FOR BASKET FILTERING 
	private String orderType;
	private String orderSubType;
	
	
	public LtsCustomerIdentificationFormDTO getLtsCustomerIdentificationForm() {
		return ltsCustomerIdentificationForm;
	}
	public void setLtsCustomerIdentificationForm(
			LtsCustomerIdentificationFormDTO ltsCustomerIdentificationForm) {
		this.ltsCustomerIdentificationForm = ltsCustomerIdentificationForm;
	}
	public LtsSalesInfoFormDTO getLtsSalesInfoForm() {
		return ltsSalesInfoForm;
	}
	public void setLtsSalesInfoForm(LtsSalesInfoFormDTO ltsSalesInfoForm) {
		this.ltsSalesInfoForm = ltsSalesInfoForm;
	}
	public LtsPaymentFormDTO getLtsPaymentForm() {
		return ltsPaymentForm;
	}
	public void setLtsPaymentForm(LtsPaymentFormDTO ltsPaymentForm) {
		this.ltsPaymentForm = ltsPaymentForm;
	}
	public UpgradeEyeInfoDTO getUpgradeEyeInfo() {
		return upgradeEyeInfo;
	}
	public void setUpgradeEyeInfo(UpgradeEyeInfoDTO upgradeEyeInfo) {
		this.upgradeEyeInfo = upgradeEyeInfo;
	}
	public ServiceDetailProfileLtsDTO getLtsServiceProfile() {
		return ltsServiceProfile;
	}
	public void setLtsServiceProfile(ServiceDetailProfileLtsDTO ltsServiceProfile) {
		this.ltsServiceProfile = ltsServiceProfile;
	}
	public ImsSbOrderDTO getRelatedPcdOrder() {
		return relatedPcdOrder;
	}
	public void setRelatedPcdOrder(ImsSbOrderDTO relatedPcdOrder) {
		this.relatedPcdOrder = relatedPcdOrder;
	}
	public LtsCustomerInquiryFormDTO getLtsCustomerInquiryForm() {
		return ltsCustomerInquiryForm;
	}
	public void setLtsCustomerInquiryForm(
			LtsCustomerInquiryFormDTO ltsCustomerInquiryForm) {
		this.ltsCustomerInquiryForm = ltsCustomerInquiryForm;
	}
	public LtsMiscellaneousFormDTO getLtsMiscellaneousForm() {
		return ltsMiscellaneousForm;
	}
	public void setLtsMiscellaneousForm(LtsMiscellaneousFormDTO ltsMiscellaneousForm) {
		this.ltsMiscellaneousForm = ltsMiscellaneousForm;
	}
	public LtsRecontractFormDTO getLtsRecontractForm() {
		return ltsRecontractForm;
	}
	public void setLtsRecontractForm(LtsRecontractFormDTO ltsRecontractForm) {
		this.ltsRecontractForm = ltsRecontractForm;
	}
	public LtsDnChangeFormDTO getLtsDnChangeForm() {
		return ltsDnChangeForm;
	}
	public void setLtsDnChangeForm(LtsDnChangeFormDTO ltsDnChangeForm) {
		this.ltsDnChangeForm = ltsDnChangeForm;
	}
	public LtsAddressRolloutFormDTO getLtsAddressRolloutForm() {
		return ltsAddressRolloutForm;
	}
	public void setLtsAddressRolloutForm(
			LtsAddressRolloutFormDTO ltsAddressRolloutForm) {
		this.ltsAddressRolloutForm = ltsAddressRolloutForm;
	}
	public LtsModemArrangementFormDTO getLtsModemArrangementForm() {
		return ltsModemArrangementForm;
	}
	public void setLtsModemArrangementForm(
			LtsModemArrangementFormDTO ltsModemArrangementForm) {
		this.ltsModemArrangementForm = ltsModemArrangementForm;
	}
	public LtsDeviceSelectionFormDTO getLtsDeviceSelectionForm() {
		return ltsDeviceSelectionForm;
	}
	public void setLtsDeviceSelectionForm(
			LtsDeviceSelectionFormDTO ltsDeviceSelectionForm) {
		this.ltsDeviceSelectionForm = ltsDeviceSelectionForm;
	}
	public LtsBasketSelectionFormDTO getLtsBasketSelectionForm() {
		return ltsBasketSelectionForm;
	}
	public void setLtsBasketSelectionForm(
			LtsBasketSelectionFormDTO ltsBasketSelectionForm) {
		this.ltsBasketSelectionForm = ltsBasketSelectionForm;
	}
	public LtsBasketServiceFormDTO getLtsBasketServiceForm() {
		return ltsBasketServiceForm;
	}
	public void setLtsBasketServiceForm(LtsBasketServiceFormDTO ltsBasketServiceForm) {
		this.ltsBasketServiceForm = ltsBasketServiceForm;
	}
	public LtsNowTvServiceFormDTO getLtsNowTvServiceForm() {
		return ltsNowTvServiceForm;
	}
	public void setLtsNowTvServiceForm(LtsNowTvServiceFormDTO ltsNowTvServiceForm) {
		this.ltsNowTvServiceForm = ltsNowTvServiceForm;
	}
	public LtsBasketVasDetailFormDTO getLtsBasketVasDetailForm() {
		return ltsBasketVasDetailForm;
	}
	public void setLtsBasketVasDetailForm(
			LtsBasketVasDetailFormDTO ltsBasketVasDetailForm) {
		this.ltsBasketVasDetailForm = ltsBasketVasDetailForm;
	}
	public LtsBasketVasParamFormDTO getLtsBasketVasParamForm() {
		return ltsBasketVasParamForm;
	}
	public void setLtsBasketVasParamForm(
			LtsBasketVasParamFormDTO ltsBasketVasParamForm) {
		this.ltsBasketVasParamForm = ltsBasketVasParamForm;
	}
	public LtsOtherVoiceServiceFormDTO getLtsOtherVoiceServiceForm() {
		return ltsOtherVoiceServiceForm;
	}
	public void setLtsOtherVoiceServiceForm(
			LtsOtherVoiceServiceFormDTO ltsOtherVoiceServiceForm) {
		this.ltsOtherVoiceServiceForm = ltsOtherVoiceServiceForm;
	}
	public AddressRolloutDTO getNewAddressRollout() {
		return newAddressRollout;
	}
	public void setNewAddressRollout(AddressRolloutDTO newAddressRollout) {
		this.newAddressRollout = newAddressRollout;
	}
	public ServiceDetailProfileLtsDTO getSecondDelServiceProfile() {
		return secondDelServiceProfile;
	}
	public void setSecondDelServiceProfile(
			ServiceDetailProfileLtsDTO secondDelServiceProfile) {
		this.secondDelServiceProfile = secondDelServiceProfile;
	}
	public LtsPremiumSelectionFormDTO getLtsPremiumSelectionForm() {
		return ltsPremiumSelectionForm;
	}
	public void setLtsPremiumSelectionForm(
			LtsPremiumSelectionFormDTO ltsPremiumSelectionForm) {
		this.ltsPremiumSelectionForm = ltsPremiumSelectionForm;
	}
	public LtsAppointmentFormDTO getLtsAppointmentForm() {
		return ltsAppointmentForm;
	}
	public void setLtsAppointmentForm(LtsAppointmentFormDTO ltsAppointmentForm) {
		this.ltsAppointmentForm = ltsAppointmentForm;
	}
	public ModemTechnologyAissgnDTO getModemTechnologyAissgn() {
		return modemTechnologyAissgn;
	}
	public void setModemTechnologyAissgn(
			ModemTechnologyAissgnDTO modemTechnologyAissgn) {
		this.modemTechnologyAissgn = modemTechnologyAissgn;
	}
	public SbOrderDTO getSbOrder() {
		return sbOrder;
	}
	public void setSbOrder(SbOrderDTO sbOrder) {
		this.sbOrder = sbOrder;
	}
	public String getOrderAction() {
		return orderAction;
	}
	public void setOrderAction(String orderAction) {
		this.orderAction = orderAction;
	}
	public LtsSummaryFormDTO getLtsSummaryForm() {
		return ltsSummaryForm;
	}
	public void setLtsSummaryForm(LtsSummaryFormDTO ltsSummaryForm) {
		this.ltsSummaryForm = ltsSummaryForm;
	}
	public Boolean getCreate2ndDelByReserveDnInd() {
		return create2ndDelByReserveDnInd;
	}
	public void setCreate2ndDelByReserveDnInd(Boolean create2ndDelByReserveDnInd) {
		this.create2ndDelByReserveDnInd = create2ndDelByReserveDnInd;
	}
	public LtsSupportDocFormDTO getLtsSupportDocForm() {
		return ltsSupportDocForm;
	}
	public void setLtsSupportDocForm(LtsSupportDocFormDTO ltsSupportDocForm) {
		this.ltsSupportDocForm = ltsSupportDocForm;
	}
	public LtsDigitalSignatureFormDTO getLtsDigitalSignatureForm() {
		return ltsDigitalSignatureForm;
	}
	public void setLtsDigitalSignatureForm(
			LtsDigitalSignatureFormDTO ltsDigitalSignatureForm) {
		this.ltsDigitalSignatureForm = ltsDigitalSignatureForm;
	}
	public boolean isChannelCs() {
		return isChannelCs;
	}
	public void setChannelCs(boolean isChannelCs) {
		this.isChannelCs = isChannelCs;
	}
	public boolean isChannelPremier() {
		return isChannelPremier;
	}
	public void setChannelPremier(boolean isChannelPremier) {
		this.isChannelPremier = isChannelPremier;
	}
	public String getBasketChannelId() {
		return basketChannelId;
	}
	public void setBasketChannelId(String basketChannelId) {
		this.basketChannelId = basketChannelId;
	}
	public boolean isChannelRetail() {
		return isChannelRetail;
	}
	public void setChannelRetail(boolean isChannelRetail) {
		this.isChannelRetail = isChannelRetail;
	}
	public LtsWqRemarkFormDTO getLtsWqRemarkForm() {
		return ltsWqRemarkForm;
	}
	public void setLtsWqRemarkForm(LtsWqRemarkFormDTO ltsWqRemarkForm) {
		this.ltsWqRemarkForm = ltsWqRemarkForm;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderSubType() {
		return orderSubType;
	}
	public void setOrderSubType(String orderSubType) {
		this.orderSubType = orderSubType;
	}
	public ImsPendingOrderDTO getImsPendingOrder() {
		return imsPendingOrder;
	}
	public void setImsPendingOrder(ImsPendingOrderDTO imsPendingOrder) {
		this.imsPendingOrder = imsPendingOrder;
	}
	public BasketDetailDTO getSelectedBasket() {
		return selectedBasket;
	}
	public void setSelectedBasket(BasketDetailDTO selectedBasket) {
		this.selectedBasket = selectedBasket;
	}
	public LtsDnChangeDuplexFormDTO getLtsDnChangeDuplexForm() {
		return ltsDnChangeDuplexForm;
	}
	public void setLtsDnChangeDuplexForm(
			LtsDnChangeDuplexFormDTO ltsDnChangeDuplexForm) {
		this.ltsDnChangeDuplexForm = ltsDnChangeDuplexForm;
	}
	public ApprovalLoginFormDTO getApprovalLoginForm() {
		return approvalLoginForm;
	}
	public void setApprovalLoginForm(ApprovalLoginFormDTO approvalLoginForm) {
		this.approvalLoginForm = approvalLoginForm;
	}
	public ApprovalLoginFormDTO getApprovalDuplexLoginForm() {
		return approvalDuplexLoginForm;
	}
	public void setApprovalDuplexLoginForm(
			ApprovalLoginFormDTO approvalDuplexLoginForm) {
		this.approvalDuplexLoginForm = approvalDuplexLoginForm;
	}
	public BasketCriteriaDTO getBasketCriteria() {
		return basketCriteria;
	}
	public void setBasketCriteria(BasketCriteriaDTO basketCriteria) {
		this.basketCriteria = basketCriteria;
	}
	public List<RenewBasketPolicyDTO> getRenewBasketPolicyList() {
		return renewBasketPolicyList;
	}
	public void setRenewBasketPolicyList(
			List<RenewBasketPolicyDTO> renewBasketPolicyList) {
		this.renewBasketPolicyList = renewBasketPolicyList;
	}
	
}
