package com.bomwebportal.listener;

import java.awt.image.LookupTable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.service.ConstantLkupService;
import com.bomwebportal.dto.*;
import com.bomwebportal.dto.report.MobAppFormDTO;
import com.bomwebportal.bean.LookupTableBean;



public class BomWebPortalListener implements ServletContextListener {

    protected final Log logger = LogFactory.getLog(getClass());

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent event) {
		
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());	
		ConstantLkupService service = (ConstantLkupService) wac.getBean("constantLkupService");
		
		try{
			List<IssueBankDTO> issueBankList = service.getIssueBankList();
			LookupTableBean.getInstance().setIssueBankList(issueBankList);
			
			List<Integer> billPeriodList = service.getBillPeriodList();
			LookupTableBean.getInstance().setBillPeriodList(billPeriodList);
			
			List<Integer> billPeriodCslList = service.getBillPeriodCslList();
			LookupTableBean.getInstance().setBillPeriodCslList(billPeriodCslList);
			
			List<Integer> billPeriod1010List = service.getBillPeriod1010List();
			LookupTableBean.getInstance().setBillPeriod1010List(billPeriod1010List);
			
			List<AddressAreaDTO> addressAreaList = service.getAddressAreaList();
			LookupTableBean.getInstance().setAddressAreaList(addressAreaList);
			
			List<AddressCategoryDTO> addressCategoryList = service.getAddressCategoryList();
			LookupTableBean.getInstance().setAddressCategoryList(addressCategoryList);
			
			List<AddressDistrictDTO> addressDistrictList = service.getAddressDistrictList();
			LookupTableBean.getInstance().setAddressDistrictList(addressDistrictList);
			
			HashMap<String,Boolean> imsNonReaplaceKeywordsList = service.getImsNotReplaceKeywordsList();
			LookupTableBean.getInstance().setImsNonReplaceKeywords(imsNonReaplaceKeywordsList);
			
			List<AddressSectionDTO> addressSectionList = service.getAddressSectionList();
			LookupTableBean.getInstance().setAddressSectionList(addressSectionList);
			
			List<AddressAreaDTO> imsAddressAreaList = service.getImsAddressAreaList();
			LookupTableBean.getInstance().setImsAddressAreaList(imsAddressAreaList);
			
			List<AddressDistrictDTO> imsAddressDistrictList = service.getImsAddressDistrictList();
			LookupTableBean.getInstance().setImsAddressDistrictList(imsAddressDistrictList);
			
			List<AddressSectionDTO> imsAddressSectionList = service.getImsAddressSectionList();
			LookupTableBean.getInstance().setImsAddressSectionList(imsAddressSectionList);
			
			List<BankBranchDTO> bankBranchList = service.getBankBranchList();
			LookupTableBean.getInstance().setBankBranchList(bankBranchList);
			
			List<CreditCardTypeDTO> creditCardTypeList = service.getCreditCardTypeList();
			LookupTableBean.getInstance().setCreditCardTypeList(creditCardTypeList);
			
			
			List<IssueBankDTO> autopayIssueBankList = service.getAutopayIssueBankList(); //add 20110603 wilson
			LookupTableBean.getInstance().setAutopayIssueBankList(autopayIssueBankList);//add 20110603 wilson
			
			List<BankBranchDTO> autopayBankBranchList = service.getAutopayBankBranchList();//add 20110603 wilson
			LookupTableBean.getInstance().setAutopayBankBranchList(autopayBankBranchList);//add 20110603 wilson

			List<MobBillMediaDTO> mobBillMediaList = service.getBillMediaOption();//Paper bill Athena 20130925
			LookupTableBean.getInstance().setBillMediaOption(mobBillMediaList);// Paper bill Athena 20130925
	
			//add 20110718, create AddressDistrictMap
			Map<String, String> tempAddressDistrictMap = new HashMap<String, String>();
			for ( int i =0 ; i<LookupTableBean.getInstance().getAddressDistrictList().size(); i++ ){
				tempAddressDistrictMap.put(LookupTableBean.getInstance().getAddressDistrictList().get(i).getDistrictCode(), LookupTableBean.getInstance().getAddressDistrictList().get(i).getAreaCode());
			}
			LookupTableBean.getInstance().setAddressDistrictMap(tempAddressDistrictMap);
			
			//add by IMS
			LookupTableBean.getInstance().setImsSuspendLookupMap(service.getImsSuspendCodeLookup());
			LookupTableBean.getInstance().setImsPTSuspendLookupMap(service.getImsPTSuspendCodeLookup());
			LookupTableBean.getInstance().setImsCancelLookupMap(service.getImsCancelCodeLookup());
			LookupTableBean.getInstance().setImsDSCancelLookupMap(service.getImsDSCancelCodeLookup());
			LookupTableBean.getInstance().setImsDSSuspendLookupMap(service.getImsDSSuspendCodeLookup());
			LookupTableBean.getInstance().setImsWaiveQCLookupMap(service.getImsWaiveQCCodeLookup());
			LookupTableBean.getInstance().setImsDistrictLookupMap(service.getImsdistrictLookupMap());
			LookupTableBean.getInstance().setNewTvPriceCutOff(service.getNewTvPriceCutOff());//kinman new nowtv
			LookupTableBean.getInstance().setImsClubOptoutLookupMap(service.getImsClubOptOutLookup());
			LookupTableBean.getInstance().setImsOptLookupMap(service.getOptLookup());
			LookupTableBean.getInstance().setEnableIOExclusiveCheck(service.getEnableIOExclusiveCheckDesc());
			LookupTableBean.getInstance().setImsThirdPartyRelLookupMap(service.getImsThirdPartyRelationshipLookUpCode());
			LookupTableBean.getInstance().setImsThirdPartyRelCh13LookupMap(service.getImsThirdPartyRelationshipChannel13LookUpCode());
			LookupTableBean.getInstance().setImsNtvClubOptoutLookupMap(service.getImsClubOptOutReasonLookUpCode());
			LookupTableBean.getInstance().setImsDSAppMethodMap(service.getImsDSAppMethod());
			
			//******************added by James 20120221***************
			Map<String, List<CodeLkupDTO>> codeLkupMap = service.getAllCodeLkup();
			LookupTableBean.getInstance().setCodeLkupList(codeLkupMap);
			//********************************************************
			
			
			String[] temp = new String[LookupTableBean.getInstance().getCodeLkupList().get("CCS_FALLOUT_CH").size()];
			   
			List<CodeLkupDTO> wordList = LookupTableBean.getInstance().getCodeLkupList().get("CCS_FALLOUT_CH"); 
	   
	      for (int i=0; i< temp.length; i++)  
	      {  
	    	  temp[i]=wordList.get(i).getCodeId();
	      }  
	      
	      LookupTableBean.getInstance().setAllowFalloutChannelList( temp);
			
	      
	      List<MobAppFormDTO> mobCCSAppFormList = service.getCCSMobAppFormList();
	      LookupTableBean.getInstance().setCCSMobAppFormList(mobCCSAppFormList);
	      
	      List<MobAppFormDTO> mobRSAppFormList = service.getRSMobAppFormList();
	      LookupTableBean.getInstance().setRSMobAppFormList(mobRSAppFormList);
	      
	      List<MobAppFormDTO> mobCosCCSAppFormList = service.getCosCCSMobAppFormList();
	      LookupTableBean.getInstance().setCosCCSMobAppFormList(mobCosCCSAppFormList);
	      
	      List<MobAppFormDTO> mobCosRSAppFormList = service.getCosRSMobAppFormList();
	      LookupTableBean.getInstance().setCosRSMobAppFormList(mobCosRSAppFormList);
	      
	      List<String> asciiReplaceList = service.getAsciiReplaceList(); // martin
	      LookupTableBean.getInstance().setAsciiReplaceList(asciiReplaceList); // martin

	      Map<String, String> mobSimTypeMap = service.getMobSimTypeMap();
	      LookupTableBean.getInstance().setMobSimTypeMap(mobSimTypeMap);
	      
	      Map<String,MobItemQuotaDTO> mobItemQuotaMap = service.getMobItemQuotaMap();
	      LookupTableBean.getInstance().setMobItemQuotaMap(mobItemQuotaMap);
	      
	      Map<String, List<ItemFuncAssgnMobDTO>> itemFuncAssgnMobMap = service.getItemFuncAssgnMobMap();
	      LookupTableBean.getInstance().setItemFuncAssgnMobMap(itemFuncAssgnMobMap);
	      
	      
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
	}
    

}
