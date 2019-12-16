package com.bomwebportal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.*;
import com.bomwebportal.dto.report.MobAppFormDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;

public interface ConstantLkupService {

	HashMap<String,Boolean> getImsNotReplaceKeywordsList();
	
	public List<IssueBankDTO> getIssueBankList();

	public List<Integer> getBillPeriodList();
	
	public List<Integer> getBillPeriodCslList();
	
	public List<Integer> getBillPeriod1010List();

	public List<AddressAreaDTO> getAddressAreaList();

	public List<AddressCategoryDTO> getAddressCategoryList();

	public List<AddressDistrictDTO> getAddressDistrictList();

	public List<AddressSectionDTO> getAddressSectionList();
	
	public List<AddressAreaDTO> getImsAddressAreaList();

	public List<AddressDistrictDTO> getImsAddressDistrictList();

	public List<AddressSectionDTO> getImsAddressSectionList();

	public List<BankBranchDTO> getBankBranchList();

	public List<CreditCardTypeDTO> getCreditCardTypeList();
	
	public List<IssueBankDTO> getAutopayIssueBankList();//add 20110603
	
	public List<BankBranchDTO> getAutopayBankBranchList();//add 20110603
	
	public Map<String, String> getImsSuspendCodeLookup(); //add by IMS
	
	public Map<String, String> getImsDSAppMethod(); //add by IMS
	
	public Map<String, String> getImsDSSuspendCodeLookup(); //add by IMS
	
	public Map<String, String> getImsClubOptOutLookup(); //add by IMS
	
	public Map<String, String> getImsWaiveQCCodeLookup(); //add by IMS
	
	public Map<String, String> getImsPTSuspendCodeLookup();
	
	public Map<String, String> getImsCancelCodeLookup(); //add by IMS
	
	public Map<String, String> getImsDSCancelCodeLookup(); //add by IMS
	
	public Map<String, String> getImsdistrictLookupMap(); //add by IMS
	
	public String getSendSMSorNot();
	
	public String getSendNowRetSmsOrNot();
	
	/**
	 * Grouping list of CodeLkupDTO into different categories 
	 * by using CodeType. 
	 * @return List of Code Look Up DTO object Map
	 */
	Map<String, List<CodeLkupDTO>> getAllCodeLkup();
	public List<MobBillMediaDTO> getBillMediaOption();//add by Paper bill Athena 20130925
	
	//Add for getting AppFormDTOList - Call Center & Retail 
	public List<MobAppFormDTO> getCCSMobAppFormList();
	
	public List<MobAppFormDTO> getRSMobAppFormList();
	
	public List<MobAppFormDTO> getCosCCSMobAppFormList();
	
	public List<MobAppFormDTO> getCosRSMobAppFormList();
	
	public List<String> getAsciiReplaceList(); // martin
	
	public String getNewTvPriceCutOff();//kinman new nowtv
	
	public String getImsClubCutOff();
	
	public String getImsFsPrepayCutOff();
	
	public Map<String, String> getMobSimTypeMap();
	
	public Map<String,MobItemQuotaDTO> getMobItemQuotaMap();
	
	public Map<String, String> getImsNTVSuspendCodeLookup(); // martin
	
	public Map<String, String> getImsNTVCancelCodeLookup(); // martin
	
	public Map<String, Map<String, String>> getOptLookup();
	
	public Map<String,  Map<String, String>> getImsThirdPartyRelationshipLookUpCode();
	
	public Map<String,  Map<String, String>> getImsThirdPartyRelationshipChannel13LookUpCode();
	
	public Map<String,  Map<String, String>>  getSpecialRequestLookup();
	
	public Map<String,  Map<String, String>> getImsClubOptOutReasonLookUpCode();

	public String getImsNTVClubCutOff();
	
	public String getImsNTVFsPrepayCutOff();
	
	public String getEnableIOExclusiveCheckDesc();      // add by IMS
	
	public Map<String,List<ItemFuncAssgnMobDTO>> getItemFuncAssgnMobMap();
	
	public String getCareCashVisitDate();
	
	public String getCareCashShowInd();
	
	public String getNTVCareCashShowInd();
	
	public String getDisableWQInd();
	
	public Map<String, Map<String,String>> getImsServPlanStaticReportWords(String rptName, String attribute); // martin, 20170915
	
	public Map<String,  Map<String, String>> getImsNTVRetSellingSegmentLookup();
	
	public String getSellingSegmentShowInd();
	
	public void getImsNTVCPQProxySettings();
	
	public boolean reloadLkUp();
}
