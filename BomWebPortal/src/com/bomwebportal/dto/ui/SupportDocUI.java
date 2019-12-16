package com.bomwebportal.dto.ui;

import java.util.List;

import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;

public class SupportDocUI {
	public static enum SpringboardForm {
		MOBILE_APPLICATION_FORM("Mobile / N.E. Application Form")
		, SERVICE_GUIDE("Service Guide")
		, TRADE_DESCRIPTIONS_FOR_ELECTIONIC_PRODUCTS("Product Information")
		, PCCW_CONCIERGE_SERVICE_FORM("Concierge Service Form")
		, APPLICATION_IN_RESPECT_OF_MOBILE_SECRETARIAL_SERVICE("Application in respect of Mobile Secretarial Service")
		, MNP_APPLICATION_FORM("MNP Application Form")
		, CHANGE_OF_SERVICE_FORM("Change of Service Form")
		, THRID_PARTY_AUTOPAY_FORM("3rd Party Autopay Form")
		, IGUARD_FORM_LDS("i-Guard LDS Form") 
		, IGUARD_FORM_AD("i-Guard AD Form")
		, MOBILE_SAFETY_PHONE("Mobile Safety Phone Form")
		, NFC_SIM("NFC Mobile Payment Consent Form")
		, IGUARD_FORM_UAD("i-Guard UAD Form")
		, TRAVEL_INSURANCE_FORM("Travel Insurance Form")
		, HELPERCARE_INSURANCE_FORM("HKT Care 2-year Helper Insurance Coupon Form")
		, PROJECT_EAGLE_FORM("Restart Service Form")
		/*, OCTOPUS_SIM("NFC SIM (Octopus) Service Consent Form")*/
		;
		SpringboardForm(String label) {
			this.label = label;
		}
		public String getLabel() {
			return label;
		}
		private String label;
	}

	public static class GenerateSpringboardForm {
		public SpringboardForm getSpringboardForm() {
			return springboardForm;
		}
		public void setSpringboardForm(SpringboardForm springboardForm) {
			this.springboardForm = springboardForm;
		}
		public boolean isRequired() {
			return required;
		}
		public void setRequired(boolean required) {
			this.required = required;
		}
		private SpringboardForm springboardForm;
		private boolean required;
	}

	public List<GenerateSpringboardForm> getGenerateSpringboardForms() {
		return generateSpringboardForms;
	}
	public void setGenerateSpringboardForms(
			List<GenerateSpringboardForm> generateSpringboardForms) {
		this.generateSpringboardForms = generateSpringboardForms;
	}
	public CollectMethod getCollectMethod() {
		return collectMethod;
	}
	public void setCollectMethod(CollectMethod collectMethod) {
		this.collectMethod = collectMethod;
	}
	public List<AllOrdDocAssgnDTO> getAllOrdDocAssgnDTOs() {
		return allOrdDocAssgnDTOs;
	}
	public void setAllOrdDocAssgnDTOs(List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs) {
		this.allOrdDocAssgnDTOs = allOrdDocAssgnDTOs;
	}
	public DisMode getDisMode() {
		return disMode;
	}
	public void setDisMode(DisMode disMode) {
		this.disMode = disMode;
	}
	public String getEsigEmailAddr() {
		return esigEmailAddr;
	}
	public void setEsigEmailAddr(String esigEmailAddr) {
		this.esigEmailAddr = esigEmailAddr;
	}
	public EsigEmailLang getEsigEmailLang() {
		return esigEmailLang;
	}
	public void setEsigEmailLang(EsigEmailLang esigEmailLang) {
		this.esigEmailLang = esigEmailLang;
	}
	public String getDsMissingDocReason(){
		return dsMissingDocReason;
	}
	public void setDsMissingDocReason(String dsMissingDocReason){
		this.dsMissingDocReason = dsMissingDocReason;
	}
	
	public String getManualAfNo() {
		return manualAfNo;
	}
	public void setManualAfNo(String manualAfNo) {
		this.manualAfNo = manualAfNo;
	}
	
	private List<GenerateSpringboardForm> generateSpringboardForms;
	private CollectMethod collectMethod;
	private List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs;
	private DisMode disMode;
	private String esigEmailAddr;
	private EsigEmailLang esigEmailLang;
	private String dsMissingDocReason;
	private String manualAfNo;

	
}
