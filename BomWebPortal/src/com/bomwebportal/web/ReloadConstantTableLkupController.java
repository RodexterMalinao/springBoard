package com.bomwebportal.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.service.ConstantLkupService;
import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.AddressAreaDTO;
import com.bomwebportal.dto.AddressCategoryDTO;
import com.bomwebportal.dto.AddressDistrictDTO;
import com.bomwebportal.dto.AddressSectionDTO;
import com.bomwebportal.dto.BankBranchDTO;
import com.bomwebportal.dto.CreditCardTypeDTO;
import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.dto.ItemFuncAssgnMobDTO;
import com.bomwebportal.dto.MobBillMediaDTO;//Paper bill Athena 20130925
import com.bomwebportal.dto.MobItemQuotaDTO;
import com.bomwebportal.dto.report.MobAppFormDTO;

public class ReloadConstantTableLkupController implements Controller{

    protected final Log logger = LogFactory.getLog(getClass());

    private ConstantLkupService constantLkupService;
        
    public ConstantLkupService getConstantLkupService() {
		return constantLkupService;
	}

	public void setConstantLkupService(ConstantLkupService constantLkupService) {
		this.constantLkupService = constantLkupService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String param = request.getParameter("action");
		
		if (!StringUtils.isEmpty(param) && "reload".equalsIgnoreCase(param)) {
			reload(request);
		} else {
			show(request);
		}

		return new ModelAndView("reloadconstanttablelkup");    	
    }
	
	private void show(HttpServletRequest request) {
		
		List<IssueBankDTO> issueBankList = LookupTableBean.getInstance().getIssueBankList();
		List<Integer> billPeriodList = LookupTableBean.getInstance().getBillPeriodList();
		List<Integer> billPeriodCslList = LookupTableBean.getInstance().getBillPeriodCslList();
		List<Integer> billPeriod1010List = LookupTableBean.getInstance().getBillPeriod1010List();
		List<AddressAreaDTO> addressAreaList = LookupTableBean.getInstance().getAddressAreaList();
		List<AddressCategoryDTO> addressCategoryList = LookupTableBean.getInstance().getAddressCategoryList();
		List<AddressDistrictDTO> addressDistrictList = LookupTableBean.getInstance().getAddressDistrictList();
		List<AddressSectionDTO> addressSectionList = LookupTableBean.getInstance().getAddressSectionList();
		List<AddressAreaDTO> imsAddressAreaList = LookupTableBean.getInstance().getImsAddressAreaList();
		List<AddressDistrictDTO> imsAddressDistrictList = LookupTableBean.getInstance().getImsAddressDistrictList();
		List<AddressSectionDTO> imsAddressSectionList = LookupTableBean.getInstance().getImsAddressSectionList();
		List<BankBranchDTO> bankBranchList = LookupTableBean.getInstance().getBankBranchList();
		List<CreditCardTypeDTO> creditCardTypeList = LookupTableBean.getInstance().getCreditCardTypeList();
		List<IssueBankDTO> autopayIssueBankList = LookupTableBean.getInstance().getAutopayIssueBankList();
		List<BankBranchDTO> autopayBankBranchList = LookupTableBean.getInstance().getAutopayBankBranchList();
		List<MobBillMediaDTO> mobBillMediaList = LookupTableBean.getInstance().getBillMediaOption();//add Paper bill Athena 20130925
		Map<String, String> tempAddressDistrictMap = LookupTableBean.getInstance().getAddressDistrictMap();
		Map<String, List<CodeLkupDTO>> codeLkupMap = LookupTableBean.getInstance().getCodeLkupList();
		String[] allowFalloutChannelList = LookupTableBean.getInstance().getAllowFalloutChannelList();
		List<MobAppFormDTO> cosRSAppFormList = LookupTableBean.getInstance().getMobAppFormList("1", "COS");
		List<MobAppFormDTO> cosCCSAppFormList = LookupTableBean.getInstance().getMobAppFormList("2", "COS");
		Map<String, String> mobSimTypeMap  = LookupTableBean.getInstance().getMobSimTypeMap();
		Map<String, List<ItemFuncAssgnMobDTO>> itemFuncAssgnMobMap = LookupTableBean.getInstance().getItemFuncAssgnMobMap();
		Map<String,MobItemQuotaDTO> mobItemQuotaMap = LookupTableBean.getInstance().getMobItemQuotaMap();
		
		/* Set those extracted values to jsp*/
	    setJspValues(request, issueBankList, billPeriodList, billPeriodCslList, billPeriod1010List, addressAreaList,
				addressCategoryList, addressDistrictList, addressSectionList, 
				bankBranchList, creditCardTypeList, autopayIssueBankList,
				autopayBankBranchList,mobBillMediaList, tempAddressDistrictMap, codeLkupMap, 
				allowFalloutChannelList, cosRSAppFormList, cosCCSAppFormList, mobSimTypeMap, itemFuncAssgnMobMap,
				imsAddressAreaList, imsAddressDistrictList, imsAddressSectionList,
				mobItemQuotaMap);
	}
	
	/**
	 * Retrieve constant lookup from DB and set them back to LookupTableBean
	 * @param request
	 */
	private void reload(HttpServletRequest request) {
		
		/* Issue Bank */
		List<IssueBankDTO> issueBankList = constantLkupService.getIssueBankList();
		LookupTableBean.getInstance().setIssueBankList(issueBankList);	

		/* Bill Period */
		List<Integer> billPeriodList = constantLkupService.getBillPeriodList();
		LookupTableBean.getInstance().setBillPeriodList(billPeriodList);
		
		List<Integer> billPeriodCslList = constantLkupService.getBillPeriodCslList();
		LookupTableBean.getInstance().setBillPeriodCslList(billPeriodCslList);
		
		List<Integer> billPeriod1010List = constantLkupService.getBillPeriod1010List();
		LookupTableBean.getInstance().setBillPeriod1010List(billPeriod1010List);

		/* Address Area */
		List<AddressAreaDTO> addressAreaList = constantLkupService.getAddressAreaList();
		LookupTableBean.getInstance().setAddressAreaList(addressAreaList);

		/* Address Category */
		List<AddressCategoryDTO> addressCategoryList = constantLkupService.getAddressCategoryList();
		LookupTableBean.getInstance().setAddressCategoryList(addressCategoryList);

		/* Address District */
		List<AddressDistrictDTO> addressDistrictList = constantLkupService.getAddressDistrictList();
		LookupTableBean.getInstance().setAddressDistrictList(addressDistrictList);
		
		HashMap<String,Boolean> imsNonReaplaceKeywordsList = constantLkupService.getImsNotReplaceKeywordsList();
		LookupTableBean.getInstance().setImsNonReplaceKeywords(imsNonReaplaceKeywordsList);

		/* Address Section */
		List<AddressSectionDTO> addressSectionList = constantLkupService.getAddressSectionList();
		LookupTableBean.getInstance().setAddressSectionList(addressSectionList);

		/* Ims Address Area */
		List<AddressAreaDTO> imsAddressAreaList = constantLkupService.getImsAddressAreaList();
		LookupTableBean.getInstance().setImsAddressAreaList(imsAddressAreaList);

		/* Ims Address District */
		List<AddressDistrictDTO> imsAddressDistrictList = constantLkupService.getImsAddressDistrictList();
		LookupTableBean.getInstance().setImsAddressDistrictList(imsAddressDistrictList);

		/* Ims Address Section */
		List<AddressSectionDTO> imsAddressSectionList = constantLkupService.getImsAddressSectionList();
		LookupTableBean.getInstance().setImsAddressSectionList(imsAddressSectionList);

		/* Bank Branch */
		List<BankBranchDTO> bankBranchList = constantLkupService.getBankBranchList();
		LookupTableBean.getInstance().setBankBranchList(bankBranchList);

		/* Credit Card Type */
		List<CreditCardTypeDTO> creditCardTypeList = constantLkupService.getCreditCardTypeList();
		LookupTableBean.getInstance().setCreditCardTypeList(creditCardTypeList);

		/* Autopay Issue Bank */
		List<IssueBankDTO> autopayIssueBankList = constantLkupService.getAutopayIssueBankList();
		LookupTableBean.getInstance().setAutopayIssueBankList(autopayIssueBankList);

		/* Autopay Bank Branch */
		List<BankBranchDTO> autopayBankBranchList = constantLkupService.getAutopayBankBranchList();
		LookupTableBean.getInstance().setAutopayBankBranchList(autopayBankBranchList);

		/* MOB Bill Media List */
		List<MobBillMediaDTO> mobBillMediaList = constantLkupService.getBillMediaOption();//add Paper bill Athena 20130925
		LookupTableBean.getInstance().setBillMediaOption(mobBillMediaList);//add Paper bill Athena 20130925 
		
		/* Address District Map */
		Map<String, String> tempAddressDistrictMap = new HashMap<String, String>();
		for ( int i =0 ; i<LookupTableBean.getInstance().getAddressDistrictList().size(); i++ ){
			tempAddressDistrictMap.put(LookupTableBean.getInstance().getAddressDistrictList().get(i).getDistrictCode(), LookupTableBean.getInstance().getAddressDistrictList().get(i).getAreaCode());
		}
		LookupTableBean.getInstance().setAddressDistrictMap(tempAddressDistrictMap);
		
		/* Code Lookup Map */
		Map<String, List<CodeLkupDTO>> codeLkupMap = constantLkupService.getAllCodeLkup();
		LookupTableBean.getInstance().setCodeLkupList(codeLkupMap);

		/* Allow Fallout Channel */
		String[] allowFalloutChannelList = new String[LookupTableBean.getInstance().getCodeLkupList().get("CCS_FALLOUT_CH").size()];
		List<CodeLkupDTO> wordList = LookupTableBean.getInstance().getCodeLkupList().get("CCS_FALLOUT_CH"); 
	    for (int i=0; i< allowFalloutChannelList.length; i++) {  
	    	allowFalloutChannelList[i] = wordList.get(i).getCodeId();
	    }  
	    LookupTableBean.getInstance().setAllowFalloutChannelList(allowFalloutChannelList);
	    
	    List<MobAppFormDTO> cosRSAppFormList = constantLkupService.getCosRSMobAppFormList();
	    LookupTableBean.getInstance().setCosRSMobAppFormList(cosRSAppFormList);
	    
	    List<MobAppFormDTO> cosCCSAppFormList = constantLkupService.getCosCCSMobAppFormList();
	    LookupTableBean.getInstance().setCosCCSMobAppFormList(cosCCSAppFormList);
	    
	    List<String> asciiReplaceList = constantLkupService.getAsciiReplaceList(); // martin
	    LookupTableBean.getInstance().setAsciiReplaceList(asciiReplaceList); // martin
	    
	    Map<String, String> mobSimTypeMap = constantLkupService.getMobSimTypeMap();
	    LookupTableBean.getInstance().setMobSimTypeMap(mobSimTypeMap);
	    
	    Map<String, List<ItemFuncAssgnMobDTO>> itemFuncAssgnMobMap = constantLkupService.getItemFuncAssgnMobMap();
	    LookupTableBean.getInstance().setItemFuncAssgnMobMap(itemFuncAssgnMobMap);
	    
	    Map<String,MobItemQuotaDTO> mobItemQuotaMap = constantLkupService.getMobItemQuotaMap();
	    LookupTableBean.getInstance().setMobItemQuotaMap(mobItemQuotaMap);
	    
	    /* Set those extracted values to jsp*/
	    setJspValues(request, issueBankList, billPeriodList, billPeriodCslList, billPeriod1010List, addressAreaList,
				addressCategoryList, addressDistrictList, addressSectionList, 
				bankBranchList, creditCardTypeList, autopayIssueBankList,
				autopayBankBranchList,  mobBillMediaList,tempAddressDistrictMap, codeLkupMap, 
				allowFalloutChannelList, cosRSAppFormList, cosCCSAppFormList, mobSimTypeMap, itemFuncAssgnMobMap,
				imsAddressAreaList, imsAddressDistrictList, imsAddressSectionList,
				mobItemQuotaMap);
	}
	
	/**
	 * Bind the values between controller and jsp
	 * @param request
	 */
	private void setJspValues(HttpServletRequest request, List<IssueBankDTO> issueBankList,
			List<Integer> billPeriodList, List<Integer> billPeriodCslList, List<Integer> billPeriod1010List,
			List<AddressAreaDTO> addressAreaList,
			List<AddressCategoryDTO> addressCategoryList, List<AddressDistrictDTO> addressDistrictList,
			List<AddressSectionDTO> addressSectionList, List<BankBranchDTO> bankBranchList,
			List<CreditCardTypeDTO> creditCardTypeList, List<IssueBankDTO> autopayIssueBankList,
			List<BankBranchDTO> autopayBankBranchList, List<MobBillMediaDTO> mobBillMediaList,
			Map<String, String> tempAddressDistrictMap,
			Map<String, List<CodeLkupDTO>> codeLkupMap, String[] allowFalloutChannelList, 
			List<MobAppFormDTO> cosRSAppFormList, List<MobAppFormDTO> cosCCSAppFormList,
			Map<String, String> mobSimTypeMap,  Map<String, List<ItemFuncAssgnMobDTO>> itemFuncAssgnMobMap, List<AddressAreaDTO> imsAddressAreaList, 
			List<AddressDistrictDTO> imsAddressDistrictList, List<AddressSectionDTO> imsAddressSectionList,
			Map<String,MobItemQuotaDTO> mobItemQuotaMap
			) {
		
		request.setAttribute("issueBankList", issueBankList);
		if (issueBankList == null) {
			request.setAttribute("issueBankListSize", -1);
		} else {
			request.setAttribute("issueBankListSize", issueBankList.size());
		}
		
		request.setAttribute("billPeriodCslList", billPeriodCslList);
		if (billPeriodCslList == null) {
			request.setAttribute("billPeriodCslListSize", -1);
		} else {
			request.setAttribute("billPeriodCslListSize", billPeriodCslList.size());
		}
		
		request.setAttribute("billPeriod1010List", billPeriod1010List);
		if (billPeriod1010List == null) {
			request.setAttribute("billPeriod1010ListSize", -1);
		} else {
			request.setAttribute("billPeriod1010ListSize", billPeriod1010List.size());
		}
		
		request.setAttribute("billPeriodList", billPeriodList);
		if (billPeriodList == null) {
			request.setAttribute("billPeriodListSize", -1);
		} else {
			request.setAttribute("billPeriodListSize", billPeriodList.size());
		}
		
		request.setAttribute("addressAreaList", addressAreaList);
		if (addressAreaList == null) {
			request.setAttribute("addressAreaListSize", -1);
		} else {
			request.setAttribute("addressAreaListSize", addressAreaList.size());
		}
		
		request.setAttribute("addressCategoryList", addressCategoryList);
		if (addressCategoryList == null) {
			request.setAttribute("addressCategoryListSize", -1);
		} else {
			request.setAttribute("addressCategoryListSize", addressCategoryList.size());
		}
		
		request.setAttribute("addressDistrictList", addressDistrictList);
		if (addressDistrictList == null) {
			request.setAttribute("addressDistrictListSize", -1);
		} else {
			request.setAttribute("addressDistrictListSize", addressDistrictList.size());
		}
		
		request.setAttribute("addressSectionList", addressSectionList);
		if (addressSectionList == null) {
			request.setAttribute("addressSectionListSize", -1);
		} else {
			request.setAttribute("addressSectionListSize", addressSectionList.size());
		}
		
		request.setAttribute("imsAddressAreaList", imsAddressAreaList);
		if (imsAddressAreaList == null) {
			request.setAttribute("imsAddressAreaListSize", -1);
		} else {
			request.setAttribute("imsAddressAreaListSize", imsAddressAreaList.size());
		}
		
		request.setAttribute("imsAddressDistrictList", imsAddressDistrictList);
		if (imsAddressDistrictList == null) {
			request.setAttribute("imsAddressDistrictListSize", -1);
		} else {
			request.setAttribute("imsAddressDistrictListSize", imsAddressDistrictList.size());
		}
		
		request.setAttribute("imsAddressSectionList", imsAddressSectionList);
		if (imsAddressSectionList == null) {
			request.setAttribute("imsAddressSectionListSize", -1);
		} else {
			request.setAttribute("imsAddressSectionListSize", imsAddressSectionList.size());
		}
		
		request.setAttribute("bankBranchList", bankBranchList);
		if (bankBranchList == null) {
			request.setAttribute("bankBranchListSize", -1);
		} else {
			request.setAttribute("bankBranchListSize", bankBranchList.size());
		}
		
		request.setAttribute("creditCardTypeList", creditCardTypeList);
		if (creditCardTypeList == null) {
			request.setAttribute("creditCardTypeListSize", -1);
		} else {
			request.setAttribute("creditCardTypeListSize", creditCardTypeList.size());
		}
		
		request.setAttribute("autopayIssueBankList", autopayIssueBankList);
		if (autopayIssueBankList == null) {
			request.setAttribute("autopayIssueBankListSize", -1);
		} else {
			request.setAttribute("autopayIssueBankListSize", autopayIssueBankList.size());
		}
		
		request.setAttribute("autopayBankBranchList", autopayBankBranchList);
		if (autopayBankBranchList == null) {
			request.setAttribute("autopayBankBranchListSize", -1);
		} else {
			request.setAttribute("autopayBankBranchListSize", autopayBankBranchList.size());
		}
		//add Paper bill Athena 20130925
		request.setAttribute("BillMediaOption", mobBillMediaList);
		if (mobBillMediaList == null) {
			request.setAttribute("BillMediaOptionSize", -1);
		} else {
			request.setAttribute("BillMediaOptionSize", mobBillMediaList.size());
		}		
		request.setAttribute("tempAddressDistrictMap", tempAddressDistrictMap);
		if (tempAddressDistrictMap.size() <= 0) {
			request.setAttribute("tempAddressDistrictMapSize", -1);
		} else {
			request.setAttribute("tempAddressDistrictMapSize", tempAddressDistrictMap.size());
		}
		
		request.setAttribute("codeLkupMap", codeLkupMap);
		if (codeLkupMap.size() <= 0) {
			request.setAttribute("codeLkupMapSize", -1);
		} else {
			request.setAttribute("codeLkupMapSize", codeLkupMap.size());
		}
		
		request.setAttribute("allowFalloutChannelList", allowFalloutChannelList);
		if (allowFalloutChannelList.length <= 0) {
			request.setAttribute("allowFalloutChannelListSize", -1);
		} else {
			request.setAttribute("allowFalloutChannelListSize", allowFalloutChannelList.length);
		}
		
		request.setAttribute("cosRSAppFormList", cosRSAppFormList);
		if (cosRSAppFormList.size() <= 0) {
			request.setAttribute("cosRSAppFormListSize", -1);
		} else {
			request.setAttribute("cosRSAppFormListSize", cosRSAppFormList.size());
		}
		
		request.setAttribute("cosCCSAppFormList", cosCCSAppFormList);
		if (cosCCSAppFormList.size() <= 0) {
			request.setAttribute("cosCCSAppFormListSize", -1);
		} else {
			request.setAttribute("cosCCSAppFormListSize", cosCCSAppFormList.size());
		}
		
		request.setAttribute("mobSimTypeMap", mobSimTypeMap);
		if (mobSimTypeMap.size() <= 0) {
			request.setAttribute("mobSimTypeMapSize", -1);
		} else {
			request.setAttribute("mobSimTypeMapSize", mobSimTypeMap.size());
		}
		
		
		request.setAttribute("itemFuncAssgnMobMap", itemFuncAssgnMobMap);
		if (itemFuncAssgnMobMap.size() <= 0) {
			request.setAttribute("itemFuncAssgnMobMapSize", -1);
		} else {
			request.setAttribute("itemFuncAssgnMobMapSize", itemFuncAssgnMobMap.size());
		}
		
		request.setAttribute("mobItemQuotaMap", mobItemQuotaMap);
		if (mobItemQuotaMap.size() <= 0) {
			request.setAttribute("mobItemQuotaMapSize", -1);
		} else {
			request.setAttribute("mobItemQuotaMapSize", mobItemQuotaMap.size());
		}
	}
}
