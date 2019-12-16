package com.bomwebportal.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.AddressAreaDTO;
import com.bomwebportal.dto.AddressCategoryDTO;
import com.bomwebportal.dto.AddressDistrictDTO;
import com.bomwebportal.dto.AddressSectionDTO;
import com.bomwebportal.dto.BankBranchDTO;
import com.bomwebportal.dto.CodeLkupDTO;
import com.bomwebportal.dto.CreditCardTypeDTO;
import com.bomwebportal.dto.IssueBankDTO;

public class LookupTableBean implements Serializable{

	private static final long serialVersionUID = 8111378354240156887L;
	//private static final Logger log = Logger.getLogger(LookupTableBean.class);
	private static LookupTableBean instance;
	
	public static LookupTableBean getInstance () {
		if (instance == null) {
			instance = new LookupTableBean();
		}		
		return instance;
	}
	
	private List<IssueBankDTO> issueBankList;
	private List<Integer> billPeriodList;
	private List<AddressAreaDTO> addressAreaList;
	private List<AddressCategoryDTO> addressCategoryList;
	private List<AddressDistrictDTO> addressDistrictList;
	private List<AddressSectionDTO> addressSectionList;
	private List<BankBranchDTO> bankBranchList;
	private List<CreditCardTypeDTO> creditCardTypeList;
	private List<IssueBankDTO> autopayIssueBankList;//add 20110603
	private List<BankBranchDTO> autopayBankBranchList;//add 20110603
	private Map<String, String> addressDistrictMap = new HashMap<String, String>();//add 2011718
	private Map<String, String> imsSuspendLookupMap = new HashMap<String, String>();//add by IMS
	private Map<String, String> imsCancelLookupMap = new HashMap<String, String>();//add by IMS	
	private Map<String, String> imsPTSuspendLookupMap = new HashMap<String, String>();//add by IMS	
	private Map<String, List<CodeLkupDTO>> codeLkupList;
	private String[] allowFalloutChannelList;
	
	
	/**
	 * @return the codeLkupList
	 */
	public Map<String, List<CodeLkupDTO>> getCodeLkupList() {
		return codeLkupList;
	}

	/**
	 * @param codeLkupList the codeLkupList to set
	 */
	public void setCodeLkupList(Map<String, List<CodeLkupDTO>> codeLkupList) {
		this.codeLkupList = codeLkupList;
	}

	public List<IssueBankDTO> getIssueBankList(){
		return this.issueBankList;
	}
	
	public List<AddressAreaDTO> getAddressAreaList() {
		return addressAreaList;
	}

	public void setAddressAreaList(List<AddressAreaDTO> addressAreaList) {
		this.addressAreaList = addressAreaList;
	}

	public List<AddressCategoryDTO> getAddressCategoryList() {
		return addressCategoryList;
	}

	public void setAddressCategoryList(List<AddressCategoryDTO> addressCategoryList) {
		this.addressCategoryList = addressCategoryList;
	}

	public List<AddressDistrictDTO> getAddressDistrictList() {
		return addressDistrictList;
	}

	public void setAddressDistrictList(List<AddressDistrictDTO> addressDistrictList) {
		this.addressDistrictList = addressDistrictList;
	}

	public List<AddressSectionDTO> getAddressSectionList() {
		return addressSectionList;
	}

	public void setAddressSectionList(List<AddressSectionDTO> addressSectionList) {
		this.addressSectionList = addressSectionList;
	}

	public List<BankBranchDTO> getBankBranchList() {
		return bankBranchList;
	}

	public void setBankBranchList(List<BankBranchDTO> bankBranchList) {
		this.bankBranchList = bankBranchList;
	}

	public List<CreditCardTypeDTO> getCreditCardTypeList() {
		return creditCardTypeList;
	}

	public void setCreditCardTypeList(List<CreditCardTypeDTO> creditCardTypeList) {
		this.creditCardTypeList = creditCardTypeList;
	}

	public void setIssueBankList(List<IssueBankDTO> issueBankList){
		this.issueBankList = issueBankList;
	}

	public void setBillPeriodList(List<Integer> billPeriodList) {
		this.billPeriodList = billPeriodList;
	}

	public List<Integer> getBillPeriodList() {
		return billPeriodList;
	}

	public void setAutopayIssueBankList(List<IssueBankDTO> autopayIssueBankList) {
		this.autopayIssueBankList = autopayIssueBankList;
	}

	public List<IssueBankDTO> getAutopayIssueBankList() {
		return autopayIssueBankList;
	}

	public void setAutopayBankBranchList(List<BankBranchDTO> autopayBankBranchList) {
		this.autopayBankBranchList = autopayBankBranchList;
	}

	public List<BankBranchDTO> getAutopayBankBranchList() {
		return autopayBankBranchList;
	}

	public void setAddressDistrictMap(Map<String, String> addressDistrictMap) {
		this.addressDistrictMap = addressDistrictMap;
	}

	public Map<String, String> getAddressDistrictMap() {
		return addressDistrictMap;
	}

	public Map<String, String> getImsSuspendLookupMap() {
		return imsSuspendLookupMap;
	}

	public void setImsSuspendLookupMap(Map<String, String> imsSuspendLookupMap) {
		this.imsSuspendLookupMap = imsSuspendLookupMap;
	}

	public void setImsPTSuspendLookupMap(Map<String, String> imsPTSuspendLookupMap) {
		this.imsPTSuspendLookupMap = imsPTSuspendLookupMap;
	}

	public Map<String, String> getImsPTSuspendLookupMap() {
		return imsPTSuspendLookupMap;
	}

	public Map<String, String> getImsCancelLookupMap() {
		return imsCancelLookupMap;
	}

	public void setImsCancelLookupMap(Map<String, String> imsCancelLookupMap) {
		this.imsCancelLookupMap = imsCancelLookupMap;
	}

	public String[] getAllowFalloutChannelList() {
		return allowFalloutChannelList;
	}

	public void setAllowFalloutChannelList(String[] allowFalloutChannelList) {
		this.allowFalloutChannelList = allowFalloutChannelList;
	}

	
	
	
}
