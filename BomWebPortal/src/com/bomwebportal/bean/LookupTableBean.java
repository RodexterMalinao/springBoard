package com.bomwebportal.bean;

import java.io.Serializable;
import java.util.*;

import com.bomwebportal.dto.*;
import com.bomwebportal.dto.report.MobAppFormDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;


public class LookupTableBean implements Serializable{

	public Map<String, Map<String, String>> getImsThirdPartyRelLookupMap() {
		return imsThirdPartyRelLookupMap;
	}

	public void setImsThirdPartyRelLookupMap(
			Map<String, Map<String, String>> imsThirdPartyRelLookupMap) {
		this.imsThirdPartyRelLookupMap = imsThirdPartyRelLookupMap;
	}

	public Map<String, Map<String, String>> getImsThirdPartyRelCh13LookupMap() {
		return imsThirdPartyRelCh13LookupMap;
	}

	public void setImsThirdPartyRelCh13LookupMap(
			Map<String, Map<String, String>> imsThirdPartyRelCh13LookupMap) {
		this.imsThirdPartyRelCh13LookupMap = imsThirdPartyRelCh13LookupMap;
	}

	public Map<String, Map<String, String>> getImsNtvClubOptoutLookupMap() {
		return imsNtvClubOptoutLookupMap;
	}

	public void setImsNtvClubOptoutLookupMap(
			Map<String, Map<String, String>> imsNtvClubOptoutLookupMap) {
		this.imsNtvClubOptoutLookupMap = imsNtvClubOptoutLookupMap;
	}

	public void setImsNTVRetSellingSegmentLookupMap(
			Map<String, Map<String, String>> imsNTVRetSellingSegmentLookupMap) {
		this.imsNTVRetSellingSegmentLookupMap = imsNTVRetSellingSegmentLookupMap;
	}

	public Map<String, Map<String, String>> getImsNTVRetSellingSegmentLookupMap() {
		return imsNTVRetSellingSegmentLookupMap;
	}

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
	private List<Integer> billPeriodCslList;
	private List<Integer> billPeriod1010List;
	private List<AddressAreaDTO> addressAreaList;
	private List<AddressCategoryDTO> addressCategoryList;
	private List<AddressDistrictDTO> addressDistrictList;
	private List<AddressSectionDTO> addressSectionList;
	private List<AddressAreaDTO> imsAddressAreaList;
	private List<AddressDistrictDTO> imsAddressDistrictList;
	private List<AddressSectionDTO> imsAddressSectionList;
	private List<BankBranchDTO> bankBranchList;
	private List<CreditCardTypeDTO> creditCardTypeList;
	private List<IssueBankDTO> autopayIssueBankList;//add 20110603
	private List<BankBranchDTO> autopayBankBranchList;//add 20110603
	private Map<String, String> addressDistrictMap = new HashMap<String, String>();//add 2011718
	private Map<String, String> imsSuspendLookupMap = new HashMap<String, String>();//add by IMS
	private Map<String, String> imsDSSuspendLookupMap = new HashMap<String, String>();//add by IMS
	private Map<String, String> imsCancelLookupMap = new HashMap<String, String>();//add by IMS
	private Map<String, String> imsDSCancelLookupMap = new HashMap<String, String>();//add by IMS
	private Map<String, String> imsPTSuspendLookupMap = new HashMap<String, String>();//add by IMS	
	private Map<String, String> imsWaiveQCLookupMap = new HashMap<String, String>();//add by IMS
	private Map<String, String> imsDistrictLookupMap = new HashMap<String, String>();//add by IMS
	private Map<String, String> imsClubOptoutLookupMap = new HashMap<String, String>();//add by IMS
	private Map<String, Map<String, String>> imsOptLookupMap;//add by IMS
	private Map<String, Map<String, String>> imsThirdPartyRelLookupMap;//add by IMS
	private Map<String, Map<String, String>> imsThirdPartyRelCh13LookupMap;//add by IMS
	private Map<String, Map<String, String>> imsNtvClubOptoutLookupMap;//add by IMS
	private Map<String, Map<String, String>> imsNTVRetSellingSegmentLookupMap;//add by IMS
	private Map<String, String> imsDSAppMethodMap = new HashMap<String, String>();//add by IMS
	
	
	private Map<String, List<CodeLkupDTO>> codeLkupList;
	private String[] allowFalloutChannelList;
	private List<MobBillMediaDTO> billMediaOption; //paper bill Athena 20130925	
	private List<MobAppFormDTO> mobRSAppFormList;
	private List<MobAppFormDTO> mobCCSAppFormList;
	private List<MobAppFormDTO> mobCosRSAppFormList;
	private List<MobAppFormDTO> mobCosCCSAppFormList;
	private List<String> asciiReplaceList; // martin
	private HashMap<String,Boolean> imsNonReplaceKeywords; // steven 20150122
	private String newTvPriceCutOff; //kinman new nowtv
	private Map<String, String> mobSimTypeMap = new HashMap<String, String>();
	public Map<String,MobItemQuotaDTO> mobItemQuotaMap;
	public Map<String,List<ItemFuncAssgnMobDTO>> itemFuncAssgnMobMap;
	
	private String enableIOExclusiveCheck;     // add by IMS
	
	private String sellSegmentShowInd;
	
	//NOWTVSALES
	public Map<String, String> imsNTVSuspendLookupMap;
	public Map<String, String> imsNTVSuspendLookupMapEN;
	public Map<String, String> imsNTVSuspendLookupMapCHI;
	public Map<String, String> imsNTVCancelLookupMap;
	public Map<String, String> imsNTVCancelLookupMapEN;
	public Map<String, String> imsNTVCancelLookupMapCHI;
		
	public List<MobAppFormDTO> getMobAppFormList(String channelId, String nature) {
		if("1".equals(channelId)){
			if ("COS".equalsIgnoreCase(nature)) {
				return this.mobCosRSAppFormList;
			} else {
				return this.mobRSAppFormList;
			}
		}else{
			if ("COS".equalsIgnoreCase(nature)) {
				return this.mobCosCCSAppFormList;
			} else {
				return this.mobCCSAppFormList;
			}
		}
	}

	public void setCCSMobAppFormList(List<MobAppFormDTO> mobCCSAppFormList) {
		this.mobCCSAppFormList = mobCCSAppFormList;
	}
	
	public void setRSMobAppFormList(List<MobAppFormDTO> mobRSAppFormList) {
		this.mobRSAppFormList = mobRSAppFormList;
	}
	
	public void setCosRSMobAppFormList(List<MobAppFormDTO> mobCosRSAppFormList) {
		this.mobCosRSAppFormList = mobCosRSAppFormList;
	}

	public void setCosCCSMobAppFormList(List<MobAppFormDTO> mobCosCCSAppFormList) {
		this.mobCosCCSAppFormList = mobCosCCSAppFormList;
	}

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

	public List<Integer> getBillPeriodCslList() {
		return billPeriodCslList;
	}

	public void setBillPeriodCslList(List<Integer> billPeriodCslList) {
		this.billPeriodCslList = billPeriodCslList;
	}

	public List<Integer> getBillPeriod1010List() {
		return billPeriod1010List;
	}

	public void setBillPeriod1010List(List<Integer> billPeriod1010List) {
		this.billPeriod1010List = billPeriod1010List;
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
	public List<MobBillMediaDTO> getBillMediaOption() {//paper bill Athena 20130925
		return billMediaOption;
	}

	public void setBillMediaOption(List<MobBillMediaDTO> billMediaOption) { //paper bill Athena 20130925
		this.billMediaOption = billMediaOption;
	}
	
	public List<String> getAsciiReplaceList() { // martin
		return asciiReplaceList;
	}
	
	public void setImsDSSuspendLookupMap(Map<String, String> imsDSSuspendLookupMap) {
		this.imsDSSuspendLookupMap = imsDSSuspendLookupMap;
	}

	public Map<String, String> getImsDSSuspendLookupMap() {
		return imsDSSuspendLookupMap;
	}

	public void setImsWaiveQCLookupMap(Map<String, String> imsWaiveQCLookupMap) {
		this.imsWaiveQCLookupMap = imsWaiveQCLookupMap;
	}

	public Map<String, String> getImsWaiveQCLookupMap() {
		return imsWaiveQCLookupMap;
	}

	public void setImsDSCancelLookupMap(Map<String, String> imsDSCancelLookupMap) {
		this.imsDSCancelLookupMap = imsDSCancelLookupMap;
	}

	public Map<String, String> getImsDSCancelLookupMap() {
		return imsDSCancelLookupMap;
	}
	
	public void setAsciiReplaceList(List<String> asciiReplaceList) { // martin
		this.asciiReplaceList = asciiReplaceList;
	}
	public void setImsDistrictLookupMap(Map<String, String> imsDistrictLookupMap) {
		this.imsDistrictLookupMap = imsDistrictLookupMap;
	}

	public Map<String, String> getImsDistrictLookupMap() {
		return imsDistrictLookupMap;
	}
	
	public void setImsNonReplaceKeywords(HashMap<String,Boolean> imsNonReplaceKeywords) {
		this.imsNonReplaceKeywords = imsNonReplaceKeywords;
	}

	public HashMap<String,Boolean> getImsNonReplaceKeywords() {
		return imsNonReplaceKeywords;
	}

	public String getNewTvPriceCutOff() { //kinman new nowtv
		return newTvPriceCutOff;
	}

	public void setNewTvPriceCutOff(String newTvPriceCutOff) { //kinman new nowtv
		this.newTvPriceCutOff = newTvPriceCutOff;
	}

	public Map<String, String> getMobSimTypeMap() {
		return mobSimTypeMap;
	}

	public void setMobSimTypeMap(Map<String, String> mobSimTypeMap) {
		this.mobSimTypeMap = mobSimTypeMap;
	}

	public Map<String, MobItemQuotaDTO> getMobItemQuotaMap() {
		return mobItemQuotaMap;
	}

	public void setMobItemQuotaMap(Map<String, MobItemQuotaDTO> mobItemQuotaMap) {
		this.mobItemQuotaMap = mobItemQuotaMap;
	}
	
	public Map<String, List<ItemFuncAssgnMobDTO>> getItemFuncAssgnMobMap() {
		return itemFuncAssgnMobMap;
	}

	public void setItemFuncAssgnMobMap(Map<String, List<ItemFuncAssgnMobDTO>> itemFuncAssgnMobMap) {
		this.itemFuncAssgnMobMap = itemFuncAssgnMobMap;
	}

	public void setImsAddressDistrictList(List<AddressDistrictDTO> imsAddressDistrictList) {
		this.imsAddressDistrictList = imsAddressDistrictList;
	}

	public List<AddressDistrictDTO> getImsAddressDistrictList() {
		return imsAddressDistrictList;
	}

	public void setImsAddressSectionList(List<AddressSectionDTO> imsAddressSectionList) {
		this.imsAddressSectionList = imsAddressSectionList;
	}

	public List<AddressSectionDTO> getImsAddressSectionList() {
		return imsAddressSectionList;
	}

	public void setImsAddressAreaList(List<AddressAreaDTO> imsAddressAreaList) {
		this.imsAddressAreaList = imsAddressAreaList;
	}

	public List<AddressAreaDTO> getImsAddressAreaList() {
		return imsAddressAreaList;
	}

	public Map<String, String> getImsNTVSuspendLookupMap() {
		return imsNTVSuspendLookupMap;
	}

	public void setImsNTVSuspendLookupMap(Map<String, String> imsNTVSuspendLookupMap) {
		this.imsNTVSuspendLookupMap = imsNTVSuspendLookupMap;
		Map<String, String> enList = new HashMap<String, String>();
		Map<String, String> zhList = new HashMap<String, String>();
		for (Map.Entry<String, String> entry: imsNTVSuspendLookupMap.entrySet()) {
			if (entry.getKey().indexOf("EN") != -1) {
				enList.put(entry.getKey().substring(0,4), entry.getValue());
			} else if (entry.getKey().indexOf("CHI") != -1) {
				zhList.put(entry.getKey().substring(0,4), entry.getValue());
			}
		}
		setImsNTVSuspendLookupMapEN(enList);
		setImsNTVSuspendLookupMapCHI(zhList);
	}

	public Map<String, String> getImsNTVSuspendLookupMapEN() {
		return imsNTVSuspendLookupMapEN;
	}

	public void setImsNTVSuspendLookupMapEN(
			Map<String, String> imsNTVSuspendLookupMapEN) {
		this.imsNTVSuspendLookupMapEN = imsNTVSuspendLookupMapEN;
	}

	public Map<String, String> getImsNTVSuspendLookupMapCHI() {
		return imsNTVSuspendLookupMapCHI;
	}

	public void setImsNTVSuspendLookupMapCHI(
			Map<String, String> imsNTVSuspendLookupMapCHI) {
		this.imsNTVSuspendLookupMapCHI = imsNTVSuspendLookupMapCHI;
	}

	public Map<String, String> getImsNTVCancelLookupMap() {
		return imsNTVCancelLookupMap;
	}

	public void setImsNTVCancelLookupMap(Map<String, String> imsNTVCancelLookupMap) {
		this.imsNTVCancelLookupMap = imsNTVCancelLookupMap;
		Map<String, String> enList = new HashMap<String, String>();
		Map<String, String> zhList = new HashMap<String, String>();
		for (Map.Entry<String, String> entry: imsNTVCancelLookupMap.entrySet()) {
			if (entry.getKey().indexOf("EN") != -1) {
				enList.put(entry.getKey().substring(0,4), entry.getValue());
			} else if (entry.getKey().indexOf("CHI") != -1) {
				zhList.put(entry.getKey().substring(0,4), entry.getValue());
			}
		}
		setImsNTVCancelLookupMapEN(enList);
		setImsNTVCancelLookupMapCHI(zhList);
	}

	public Map<String, String> getImsNTVCancelLookupMapEN() {
		return imsNTVCancelLookupMapEN;
	}

	public void setImsNTVCancelLookupMapEN(
			Map<String, String> imsNTVCancelLookupMapEN) {
		this.imsNTVCancelLookupMapEN = imsNTVCancelLookupMapEN;
	}

	public Map<String, String> getImsNTVCancelLookupMapCHI() {
		return imsNTVCancelLookupMapCHI;
	}

	public void setImsNTVCancelLookupMapCHI(
			Map<String, String> imsNTVCancelLookupMapCHI) {
		this.imsNTVCancelLookupMapCHI = imsNTVCancelLookupMapCHI;
	}

	public void setImsClubOptoutLookupMap(Map<String, String> imsClubOptoutLookupMap) {
		this.imsClubOptoutLookupMap = imsClubOptoutLookupMap;
	}

	public Map<String, String> getImsClubOptoutLookupMap() {
		return imsClubOptoutLookupMap;
	}

	public Map<String, Map<String, String>> getImsOptLookupMap() {
		return imsOptLookupMap;
	}

	public void setImsOptLookupMap(Map<String, Map<String, String>> imsOptLookupMap) {
		this.imsOptLookupMap = imsOptLookupMap;
	}

	public void setEnableIOExclusiveCheck(String enableIOExclusiveCheck) {
		this.enableIOExclusiveCheck = enableIOExclusiveCheck;
	}

	public String getEnableIOExclusiveCheck() {
		return enableIOExclusiveCheck;
	}

	public void setSellSegmentShowInd(String sellSegmentShowInd) {
		this.sellSegmentShowInd = sellSegmentShowInd;
	}

	public String getSellSegmentShowInd() {
		return sellSegmentShowInd;
	}

	public void setImsDSAppMethodMap(Map<String, String> imsDSAppMethodMap) {
		this.imsDSAppMethodMap = imsDSAppMethodMap;
	}

	public Map<String, String> getImsDSAppMethodMap() {
		return imsDSAppMethodMap;
	}
	
	
}
