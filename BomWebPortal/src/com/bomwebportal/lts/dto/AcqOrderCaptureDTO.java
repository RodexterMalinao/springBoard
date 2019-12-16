package com.bomwebportal.lts.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.BomCustomerVerificationDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsDigitalSignatureFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.form.LtsPremiumSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAccountSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketServiceFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketVasDetailFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillingAddressFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumConfirmationFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPersonalInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSummaryFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSupportDocFormDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;

public class AcqOrderCaptureDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7707801826911211438L;


	private SbOrderDTO SbOrder;
	private AddressRolloutDTO addressRollout;
	private Boolean create1stDelByReserveDnInd;
	private Boolean create2ndDelByReserveDnInd; 
	private Boolean create2ndDelByPipbDnInd;
	private ServiceDetailProfileLtsDTO secondDelServiceProfile;
	private ImsSbOrderDTO relatedPcdOrder;
	private ModemTechnologyAissgnDTO modemTechnologyAssign;
	private CustomerDetailProfileLtsDTO customerDetailProfileLtsDTO;
	private AccountDetailProfileLtsDTO[] accountDetailProfileLtsDTO;
	
	
	//UI FORM
	private LtsAddressRolloutFormDTO ltsAddressRolloutForm;
	private LtsAcqBasketSelectionFormDTO ltsAcqBasketSelectionForm;
	private LtsAcqBasketServiceFormDTO ltsAcqBasketServiceForm;
	private LtsAcqBasketVasDetailFormDTO ltsAcqBasketVasDetailForm;
	private LtsAcqOtherVoiceServiceFormDTO ltsAcqOtherVoiceServiceForm;
	private LtsPremiumSelectionFormDTO ltsPremiumSelectionForm;
	private LtsAcqAppointmentFormDTO ltsAcqAppointmentForm;
	private LtsModemArrangementFormDTO ltsModemArrangementForm;	
	private LtsAcqNumSelectionAndPipbFormDTO ltsAcqNumSelectionAndPipbForm;
	private LtsAcqNumConfirmationFormDTO ltsAcqNumConfirmationForm;
	private LtsAcqSupportDocFormDTO ltsAcqSupportDocForm;
	private LtsAcqSummaryFormDTO ltsAcqSummaryForm;
	private LtsAcqPersonalInfoFormDTO ltsAcqPersonalInfoForm;
	private LtsAcqSalesInfoFormDTO ltsAcqSalesInfoForm;
    private LtsAcqAccountSelectionFormDTO ltsAcqAccountSelectionForm;
    private LtsAcqBillingAddressFormDTO  ltsAcqBillingAddressForm;
    private LtsAcqBillMediaBillLangFormDTO ltsAcqBillMediaBillLangForm;   
    private LtsAcqPaymentMethodFormDTO ltsAcqPaymentMethodFormDTO;
    private LtsDigitalSignatureFormDTO ltsDigitalSignatureForm;

    private BasketDetailDTO selectedBasket; 
    private boolean containPrewiringVAS = false;
    private boolean containFfpVAS = false;
    private boolean containIddVAS = false;
    private boolean containPrepayment = false;
    private boolean containPreInstallVAS = false;
    
	private boolean isEyeOrder = false;
	private boolean isChannelCs = false;
	private boolean isChannelPremier = false;
	private boolean isChannelRetail = false;
	private boolean isChannelDirectSales = false;
	
	private String orderAction = LtsConstant.ORDER_ACTION_CREATE;

	private boolean isDefinedCustNum = false;
	
	// Felix SF Cheung LTS ACQ Direct Sales
	private BomCustomerVerificationDTO bomVerifiedCustomerResult;
	private LtsDsOrderInfoDTO ltsDsOrderInfo;
	private String premiumItemSrdBeforeDateLimit;
	private String premiumItemSrdDayLimit;
	// Felix SF Cheung [END]
	
	private String suspendRemark;
	
	//GETTERS & SETTERS
	public AddressRolloutDTO getAddressRollout() {
		return addressRollout;
	}

	public void setAddressRollout(AddressRolloutDTO addressRollout) {
		this.addressRollout = addressRollout;
	}
	
	public ImsSbOrderDTO getRelatedPcdOrder() {
		return relatedPcdOrder;
	}

	public void setRelatedPcdOrder(ImsSbOrderDTO relatedPcdOrder) {
		this.relatedPcdOrder = relatedPcdOrder;
	}

	public LtsAddressRolloutFormDTO getLtsAddressRolloutForm() {
		return ltsAddressRolloutForm;
	}

	public void setLtsAddressRolloutForm(
			LtsAddressRolloutFormDTO ltsAddressRolloutForm) {
		this.ltsAddressRolloutForm = ltsAddressRolloutForm;
	}

	public LtsAcqBasketSelectionFormDTO getLtsAcqBasketSelectionForm() {
		return ltsAcqBasketSelectionForm;
	}

	public void setLtsAcqBasketSelectionForm(LtsAcqBasketSelectionFormDTO ltsAcqBasketSelectionForm) {
		this.ltsAcqBasketSelectionForm = ltsAcqBasketSelectionForm;
	}


	public LtsAcqBasketServiceFormDTO getLtsAcqBasketServiceForm() {
		return ltsAcqBasketServiceForm;
	}

	public void setLtsAcqBasketServiceForm(LtsAcqBasketServiceFormDTO ltsAcqBasketServiceForm) {
		this.ltsAcqBasketServiceForm = ltsAcqBasketServiceForm;
	}

	public LtsAcqBasketVasDetailFormDTO getLtsAcqBasketVasDetailForm() {
		return ltsAcqBasketVasDetailForm;
	}

	public void setLtsAcqBasketVasDetailForm(LtsAcqBasketVasDetailFormDTO ltsAcqBasketVasDetailForm) {
		this.ltsAcqBasketVasDetailForm = ltsAcqBasketVasDetailForm;
	}

	public LtsAcqOtherVoiceServiceFormDTO getLtsAcqOtherVoiceServiceForm() {
		return ltsAcqOtherVoiceServiceForm;
	}

	public void setLtsAcqOtherVoiceServiceForm(
			LtsAcqOtherVoiceServiceFormDTO ltsAcqOtherVoiceServiceForm) {
		this.ltsAcqOtherVoiceServiceForm = ltsAcqOtherVoiceServiceForm;
	}

	public LtsPremiumSelectionFormDTO getLtsPremiumSelectionForm() {
		return ltsPremiumSelectionForm;
	}

	public void setLtsPremiumSelectionForm(LtsPremiumSelectionFormDTO ltsPremiumSelectionForm) {
		this.ltsPremiumSelectionForm = ltsPremiumSelectionForm;
	}

	public LtsAcqAppointmentFormDTO getLtsAcqAppointmentForm() {
		return ltsAcqAppointmentForm;
	}

	public void setLtsAcqAppointmentForm(LtsAcqAppointmentFormDTO ltsAcqAppointmentForm) {
		this.ltsAcqAppointmentForm = ltsAcqAppointmentForm;
	}

	public LtsModemArrangementFormDTO getLtsModemArrangementForm() {
		return ltsModemArrangementForm;
	}

	public void setLtsModemArrangementForm(LtsModemArrangementFormDTO ltsModemArrangementForm) {
		this.ltsModemArrangementForm = ltsModemArrangementForm;
	}

	public ModemTechnologyAissgnDTO getModemTechnologyAssign() {
		return modemTechnologyAssign;
	}

	public void setModemTechnologyAssign(ModemTechnologyAissgnDTO modemTechnologyAssign) {
		this.modemTechnologyAssign = modemTechnologyAssign;
	}

	public boolean isEyeOrder() {
		return isEyeOrder;
	}

	public void setEyeOrder(boolean isEyeOrder) {
		this.isEyeOrder = isEyeOrder;
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

	public boolean isChannelRetail() {
		return isChannelRetail;
	}

	public void setChannelRetail(boolean isChannelRetail) {
		this.isChannelRetail = isChannelRetail;
	}	
	
	/**
	 * @return the isChannelDirectSales
	 */
	public boolean isChannelDirectSales() {
		return isChannelDirectSales;
	}

	/**
	 * @param isChannelDirectSales the isChannelDirectSales to set
	 */
	public void setChannelDirectSales(boolean isChannelDirectSales) {
		this.isChannelDirectSales = isChannelDirectSales;
	}

	public Boolean getCreate2ndDelByReserveDnInd() {
		return create2ndDelByReserveDnInd;
	}

	public void setCreate2ndDelByReserveDnInd(Boolean create2ndDelByReserveDnInd) {
		this.create2ndDelByReserveDnInd = create2ndDelByReserveDnInd;
	}
	
	/**
	 * @return the create2ndDelByPipbDnInd
	 */
	public Boolean getCreate2ndDelByPipbDnInd() {
		return create2ndDelByPipbDnInd;
	}

	/**
	 * @param create2ndDelByPipbDnInd the create2ndDelByPipbDnInd to set
	 */
	public void setCreate2ndDelByPipbDnInd(Boolean create2ndDelByPipbDnInd) {
		this.create2ndDelByPipbDnInd = create2ndDelByPipbDnInd;
	}

	/**
	 * @return the ltsAcqNumSelectionAndPipbForm
	 */
	public LtsAcqNumSelectionAndPipbFormDTO getLtsAcqNumSelectionAndPipbForm() {
		return ltsAcqNumSelectionAndPipbForm;
	}

	/**
	 * @param ltsAcqNumSelectionAndPipbForm the ltsAcqNumSelectionAndPipbForm to set
	 */
	public void setLtsAcqNumSelectionAndPipbForm(
			LtsAcqNumSelectionAndPipbFormDTO ltsAcqNumSelectionAndPipbForm) {
		this.ltsAcqNumSelectionAndPipbForm = ltsAcqNumSelectionAndPipbForm;
	}

	/**
	 * @return the ltsAcqNumConfirmationForm
	 */
	public LtsAcqNumConfirmationFormDTO getLtsAcqNumConfirmationForm() {
		return ltsAcqNumConfirmationForm;
	}

	/**
	 * @param ltsAcqNumConfirmationForm the ltsAcqNumConfirmationForm to set
	 */
	public void setLtsAcqNumConfirmationForm(
			LtsAcqNumConfirmationFormDTO ltsAcqNumConfirmationForm) {
		this.ltsAcqNumConfirmationForm = ltsAcqNumConfirmationForm;
	}
	

	/**
	 * @return the ltsAcqSupportDocForm
	 */
	public LtsAcqSupportDocFormDTO getLtsAcqSupportDocForm() {
		return ltsAcqSupportDocForm;
	}

	/**
	 * @param ltsAcqSupportDocForm the ltsAcqSupportDocForm to set
	 */
	public void setLtsAcqSupportDocForm(LtsAcqSupportDocFormDTO ltsAcqSupportDocForm) {
		this.ltsAcqSupportDocForm = ltsAcqSupportDocForm;
	}

	/**
	 * @return the ltsAcqSummaryForm
	 */
	public LtsAcqSummaryFormDTO getLtsAcqSummaryForm() {
		return ltsAcqSummaryForm;
	}

	/**
	 * @param ltsAcqSummaryForm the ltsAcqSummaryForm to set
	 */
	public void setLtsAcqSummaryForm(LtsAcqSummaryFormDTO ltsAcqSummaryForm) {
		this.ltsAcqSummaryForm = ltsAcqSummaryForm;
	}
	
	public AccountDetailProfileLtsDTO[] getAccountDetailProfileLtsDTO() {
		return accountDetailProfileLtsDTO;
	}

	public void setAccountDetailProfileLtsDTO(
			AccountDetailProfileLtsDTO[] accountDetailProfileLtsDTO) {
		this.accountDetailProfileLtsDTO = accountDetailProfileLtsDTO;
	}
	

	public LtsAcqPaymentMethodFormDTO getLtsAcqPaymentMethodFormDTO() {
		return ltsAcqPaymentMethodFormDTO;
	}

	public void setLtsAcqPaymentMethodFormDTO(
			LtsAcqPaymentMethodFormDTO ltsAcqPaymentMethodFormDTO) {
		this.ltsAcqPaymentMethodFormDTO = ltsAcqPaymentMethodFormDTO;
	}
	
	
	public LtsAcqPersonalInfoFormDTO getLtsAcqPersonalInfoForm() {
		return ltsAcqPersonalInfoForm;
	}

	public void setLtsAcqPersonalInfoForm(
			LtsAcqPersonalInfoFormDTO ltsAcqPersonalInfoForm) {
		this.ltsAcqPersonalInfoForm = ltsAcqPersonalInfoForm;
	}

	public LtsAcqSalesInfoFormDTO getLtsAcqSalesInfoForm() {
		return ltsAcqSalesInfoForm;
	}

	public void setLtsAcqSalesInfoForm(LtsAcqSalesInfoFormDTO ltsAcqSalesInfoForm) {
		this.ltsAcqSalesInfoForm = ltsAcqSalesInfoForm;
	}

	public LtsAcqAccountSelectionFormDTO getLtsAcqAccountSelectionForm() {
		return ltsAcqAccountSelectionForm;
	}

	public void setLtsAcqAccountSelectionForm(
			LtsAcqAccountSelectionFormDTO ltsAcqAccountSelectionForm) {
		this.ltsAcqAccountSelectionForm = ltsAcqAccountSelectionForm;
	}

	public LtsAcqBillingAddressFormDTO getLtsAcqBillingAddressForm() {
		return ltsAcqBillingAddressForm;
	}

	public void setLtsAcqBillingAddressForm(
			LtsAcqBillingAddressFormDTO ltsAcqBillingAddressForm) {
		this.ltsAcqBillingAddressForm = ltsAcqBillingAddressForm;
	}
	
	public LtsAcqBillMediaBillLangFormDTO getLtsAcqBillMediaBillLangForm() {
		return ltsAcqBillMediaBillLangForm;
	}

	public void setLtsAcqBillMediaBillLangForm(
			LtsAcqBillMediaBillLangFormDTO ltsAcqBillMediaBillLangForm) {
		this.ltsAcqBillMediaBillLangForm = ltsAcqBillMediaBillLangForm;
	}

	public CustomerDetailProfileLtsDTO getCustomerDetailProfileLtsDTO() {
		return customerDetailProfileLtsDTO;
	}

	public void setCustomerDetailProfileLtsDTO(
			CustomerDetailProfileLtsDTO customerDetailProfileLtsDTO) {
		this.customerDetailProfileLtsDTO = customerDetailProfileLtsDTO;
	}

	public SbOrderDTO getSbOrder() {
		return SbOrder;
	}

	public void setSbOrder(SbOrderDTO sbOrder) {
		SbOrder = sbOrder;
	}

	public ServiceDetailProfileLtsDTO getSecondDelServiceProfile() {
		return secondDelServiceProfile;
	}

	public void setSecondDelServiceProfile(ServiceDetailProfileLtsDTO secondDelServiceProfile) {
		this.secondDelServiceProfile = secondDelServiceProfile;
	}

	public String getBasketChannelId() {
		String hktPremierInd = null;
		
		if(this.addressRollout != null){
			hktPremierInd = this.addressRollout.getHktPremier();
		}
		
		if(StringUtils.isNotEmpty(hktPremierInd)){
			if(this.isEyeOrder){
				return LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_PT_EYE;
			}else{
				return LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_PT_DEL;
			}
		}else{
			if(this.isEyeOrder){
				return LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_MASS_EYE;
			}else{
				return LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_MASS_DEL;
			}
		}

	}

	public String getOrderAction() {
		return orderAction;
	}

	public void setOrderAction(String orderAction) {
		this.orderAction = orderAction;
	}

	/**
	 * @return the ltsDigitalSignatureForm
	 */
	public LtsDigitalSignatureFormDTO getLtsDigitalSignatureForm() {
		return ltsDigitalSignatureForm;
	}

	/**
	 * @param ltsDigitalSignatureForm the ltsDigitalSignatureForm to set
	 */
	public void setLtsDigitalSignatureForm(
			LtsDigitalSignatureFormDTO ltsDigitalSignatureForm) {
		this.ltsDigitalSignatureForm = ltsDigitalSignatureForm;
	}

	public BasketDetailDTO getSelectedBasket() {
		return selectedBasket;
	}

	public void setSelectedBasket(BasketDetailDTO selectedBasket) {
		this.selectedBasket = selectedBasket;
	}

	public boolean isContainPrewiringVAS() {
		return containPrewiringVAS;
	}

	public void setContainPrewiringVAS(boolean containPrewiringVAS) {
		this.containPrewiringVAS = containPrewiringVAS;
	}

	public boolean isContainFfpVAS() {
		return containFfpVAS;
	}

	public void setContainFfpVAS(boolean containFfpVAS) {
		this.containFfpVAS = containFfpVAS;
	}

	public boolean isContainIddVAS() {
		return containIddVAS;
	}

	public void setContainIddVAS(boolean containIddVAS) {
		this.containIddVAS = containIddVAS;
	}

	public boolean isContainPrepayment() {
		return containPrepayment;
	}

	public void setContainPrepayment(boolean containPrepayment) {
		this.containPrepayment = containPrepayment;
	}
	
	public boolean isContainPreInstallVAS() {
		return containPreInstallVAS;
	}

	public void setContainPreInstallVAS(boolean containPreInstallVAS) {
		this.containPreInstallVAS = containPreInstallVAS;
	}

	public boolean isDNOptionND() {
		String dnOption = selectedBasket.getDnOption();
		return isDNOptionMatch(dnOption, LtsConstant.DN_OPTION_ND);
	}

	public boolean isDNOptionNP() {
		String dnOption = selectedBasket.getDnOption();
		return isDNOptionMatch(dnOption, LtsConstant.DN_OPTION_NP);
	}

	public boolean isDNOptionPD() {
		String dnOption = selectedBasket.getDnOption();
		return isDNOptionMatch(dnOption, LtsConstant.DN_OPTION_PD);
	}

	public boolean isDNOptionMatch(String dnOptions, String optionConstant){
		boolean optionInd = false;
		if(dnOptions == null || dnOptions.isEmpty()){
			return true;
		}
		String[] options = dnOptions.split(",");
		for(String option : options){
			if(optionConstant.trim().equals(option.trim())){
				optionInd = true;
			} 
 
		}
		return optionInd;
	}

	/**
	 * @return the create1stDelByReserveDnInd
	 */
	public Boolean getCreate1stDelByReserveDnInd() {
		return create1stDelByReserveDnInd;
	}

	/**
	 * @param create1stDelByReserveDnInd the create1stDelByReserveDnInd to set
	 */
	public void setCreate1stDelByReserveDnInd(Boolean create1stDelByReserveDnInd) {
		this.create1stDelByReserveDnInd = create1stDelByReserveDnInd;
	}

	public boolean isDefinedCustNum() {
		return isDefinedCustNum;
	}

	public void setDefinedCustNum(boolean isDefinedCustNum) {
		this.isDefinedCustNum = isDefinedCustNum;
	}

	public BomCustomerVerificationDTO getBomVerifiedCustomerResult() {
		return bomVerifiedCustomerResult;
	}

	public void setBomVerifiedCustomerResult(
			BomCustomerVerificationDTO bomVerifiedCustomerResult) {
		this.bomVerifiedCustomerResult = bomVerifiedCustomerResult;
	}

	public LtsDsOrderInfoDTO getLtsDsOrderInfo() {
		return ltsDsOrderInfo;
	}

	public void setLtsDsOrderInfo(LtsDsOrderInfoDTO ltsDsOrderInfo) {
		this.ltsDsOrderInfo = ltsDsOrderInfo;
	}

	public String getPremiumItemSrdBeforeDateLimit() {
		return premiumItemSrdBeforeDateLimit;
	}

	public void setPremiumItemSrdBeforeDateLimit(
			String premiumItemSrdBeforeDateLimit) {
		this.premiumItemSrdBeforeDateLimit = premiumItemSrdBeforeDateLimit;
	}

	public String getPremiumItemSrdDayLimit() {
		return premiumItemSrdDayLimit;
	}

	public void setPremiumItemSrdDayLimit(String premiumItemSrdDayLimit) {
		this.premiumItemSrdDayLimit = premiumItemSrdDayLimit;
	}

	public String getSuspendRemark() {
		return suspendRemark;
	}

	public void setSuspendRemark(String suspendRemark) {
		this.suspendRemark = suspendRemark;
	}
	
}
