package com.bomwebportal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.AxisProperties;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.dao.BomConstantLkupDAO;
import com.bomwebportal.dao.ConstantLkupDAO;
import com.bomwebportal.dao.MobItemQuotaDAO;
import com.bomwebportal.dto.AddressAreaDTO;
import com.bomwebportal.dto.AddressCategoryDTO;
import com.bomwebportal.dto.AddressDistrictDTO;
import com.bomwebportal.dto.AddressSectionDTO;
import com.bomwebportal.dto.BankBranchDTO;
import com.bomwebportal.dto.CreditCardTypeDTO;
import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.dto.ItemFuncAssgnMobDTO;
import com.bomwebportal.dto.MobBillMediaDTO;
import com.bomwebportal.dto.MobItemQuotaDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.NTVUtil;
import com.bomwebportal.util.Utility;
import com.bomwebportal.dto.report.MobAppFormDTO;

@Transactional(readOnly = true)
public class ConstantLkupServiceImpl implements ConstantLkupService {

	protected final Log logger = LogFactory.getLog(getClass());

	private ConstantLkupDAO constantLkupDao;
	private MobItemQuotaService mobItemQuotaService;
	
	private BomConstantLkupDAO bomConstantLkupDao;
	
	private String ntvProxyUrlAndPort;
	private BomPropertyPlaceholderConfigurer propertyConfigurer;

	public void setBomConstantLkupDao(BomConstantLkupDAO bomConstantLkupDao) {
		this.bomConstantLkupDao = bomConstantLkupDao;
	}

	public BomConstantLkupDAO getBomConstantLkupDao() {
		return bomConstantLkupDao;
	}

	public ConstantLkupDAO getConstantLkupDao() {
		return constantLkupDao;
	}

	public void setConstantLkupDao(ConstantLkupDAO constantLkupDao) {
		this.constantLkupDao = constantLkupDao;
	}

	public MobItemQuotaService getMobItemQuotaService() {
		return mobItemQuotaService;
	}

	public void setMobItemQuotaService(MobItemQuotaService mobItemQuotaService) {
		this.mobItemQuotaService = mobItemQuotaService;
	}

	public String getNtvProxyUrlAndPort() {
		return ntvProxyUrlAndPort;
	}

	public void setNtvProxyUrlAndPort(String ntvProxyUrlAndPort) {
		this.ntvProxyUrlAndPort = ntvProxyUrlAndPort;
	}

	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}

	public List<IssueBankDTO> getIssueBankList() {
		try {
			return constantLkupDao.getIssueBankList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<Integer> getBillPeriodList() {
		try {
			return constantLkupDao.getBillPeriod();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	public List<Integer> getBillPeriodCslList() {
		try {
			List<Integer> list = constantLkupDao.getBillPeriodByBrand("CSL_BILL_PERIOD");
			Collections.sort(list);
			
			if (list.isEmpty()){
				logger.error("No csl bill period record found! Preset bill day to 2nd");
				list.add(2);
			}
			
			return list;
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<Integer> getBillPeriod1010List() {
		try {
			List<Integer> list = constantLkupDao.getBillPeriodByBrand("1010_BILL_PERIOD");
			Collections.sort(list);
			
			if (list.isEmpty()){
				logger.error("No 1010 bill period record found! Preset bill day to 2nd");
				list.add(2);
			}
			
			return list;
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AddressAreaDTO> getAddressAreaList() {
		try {
			return constantLkupDao.getAddressAreaList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AddressCategoryDTO> getAddressCategoryList() {
		try {
			return constantLkupDao.getAddressCategoryList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AddressDistrictDTO> getAddressDistrictList() {
		try {
			return constantLkupDao.getAddressDistrictList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AddressSectionDTO> getAddressSectionList() {
		try {
			return constantLkupDao.getAddressSectionList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AddressAreaDTO> getImsAddressAreaList() {
		try {
			return bomConstantLkupDao.getImsAddressAreaList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AddressDistrictDTO> getImsAddressDistrictList() {
		try {
			return bomConstantLkupDao.getImsAddressDistrictList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AddressSectionDTO> getImsAddressSectionList() {
		try {
			return bomConstantLkupDao.getImsAddressSectionList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BankBranchDTO> getBankBranchList() {
		try {
			return constantLkupDao.getBankBranchList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<CreditCardTypeDTO> getCreditCardTypeList() {
		try {
			return constantLkupDao.getCreditCardTypeList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	//add 20110603 wilson
	public List<IssueBankDTO> getAutopayIssueBankList() {
		try {
			return constantLkupDao.getAutopayIssueBankList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	//add 20110603 wilson
	public List<BankBranchDTO> getAutopayBankBranchList() {
		try {
			return constantLkupDao.getAutopayBankBranchList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public Map<String, String> getImsSuspendCodeLookup(){
		
		try{
			
			Map<String, String> map = new HashMap<String, String>();
			List<Map<String, String>> list = constantLkupDao.getImsLookUpCode("SB_IMS_ACQ_SUS");
			
			if(list!=null){
				for(int i=0; i<list.size(); i++){
					map.put(list.get(i).get("code"), list.get(i).get("description"));
				}
			}
			
			return map;
			
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	public Map<String, String> getImsDSAppMethod(){
		
		try{
			
			Map<String, String> map = new HashMap<String, String>();
			List<Map<String, String>> list = bomConstantLkupDao.getImsLookUpCode("SBIMS_CHLAPP_MAP");
			
			if(list!=null){
				for(int i=0; i<list.size(); i++){
					map.put(list.get(i).get("bom_code"), list.get(i).get("bom_desc"));
				}
			}
			
			return map;
			
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	public Map<String, String> getImsPTSuspendCodeLookup(){
		
		try{
			
			Map<String, String> map = new HashMap<String, String>();
			List<Map<String, String>> list = constantLkupDao.getImsLookUpCode("SB_IMS_PT_SUS");
			
			if(list!=null){
				for(int i=0; i<list.size(); i++){
					map.put(list.get(i).get("code"), list.get(i).get("description"));
				}
			}
			
			return map;
			
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	public Map<String, String> getImsClubOptOutLookup(){
		
		try{
			
			Map<String, String> map = new HashMap<String, String>();
			List<Map<String, String>> list = constantLkupDao.getImsLookUpCode("SB_IMS_CLUB_OPTOUT");
			
			if(list!=null){
				for(int i=0; i<list.size(); i++){
					map.put(list.get(i).get("code"), list.get(i).get("description"));
				}
			}
			
			return map;
			
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	public HashMap<String,Boolean> getImsNotReplaceKeywordsList(){
		try{
			HashMap<String,Boolean> resultList = new HashMap<String,Boolean>();
			List<Map<String, String>> list = constantLkupDao.getImsLookUpCode("NOT_REPLACE_KEYWORD");
			if(list!=null){
				for(int i=0; i<list.size(); i++){
					resultList.put(list.get(i).get("code").replace(" ", "").toUpperCase(),true);
				}
			}
			return resultList;
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public Map<String, String> getImsCancelCodeLookup(){		
						
		try{
			
			Map<String, String> map = new HashMap<String, String>();
			List<Map<String, String>> list = constantLkupDao.getImsLookUpCode("SB_IMS_ACQ_CAN");
			
			if(list!=null){
				for(int i=0; i<list.size(); i++){
					map.put(list.get(i).get("code"), list.get(i).get("description"));
				}
			}
			
			return map;
			
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
				
	}

	public String getSendSMSorNot() {
		try{
			return constantLkupDao.getSendSMSorNot();
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public String getSendNowRetSmsOrNot() {
		try{
			return constantLkupDao.getSendNowRetSmsOrNot();
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public Map<String, List<CodeLkupDTO>> getAllCodeLkup() {
		List<CodeLkupDTO> codeList = null;
		Map<String, List<CodeLkupDTO>> codeMap = new HashMap<String, List<CodeLkupDTO>>();
		try {
			codeList = constantLkupDao.getAllCodeLkup();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
		//grouping CodeLkupDTO by using code type
		for (CodeLkupDTO dto : codeList) {
			List<CodeLkupDTO> grouped = null;
			
			if (codeMap.containsKey(dto.getCodeType())) {
				grouped = codeMap.get(dto.getCodeType());
				grouped.add(dto);
			} else {
				grouped = new ArrayList<CodeLkupDTO>();
				grouped.add(dto);
				codeMap.put(dto.getCodeType(), grouped);
			}
		}
		
		return codeMap;
	}
	public List<MobBillMediaDTO> getBillMediaOption() {
		try {
			return constantLkupDao.getBillMediaOption();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	//Add for getting AppFormDTOList - Call Center
	public List<MobAppFormDTO> getCCSMobAppFormList(){
		try{
			return constantLkupDao.getMobAppFormList(2, "ACQ");
		} catch (DAOException de) {
			de.printStackTrace();
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	//Add for getting AppFormDTOList - Retail
	public List<MobAppFormDTO> getRSMobAppFormList(){
		try{
			return constantLkupDao.getMobAppFormList(1, "ACQ");
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	//Add for getting AppFormDTOList - COS Call Center
		public List<MobAppFormDTO> getCosCCSMobAppFormList(){
			try{
				return constantLkupDao.getMobAppFormList(2, "COS");
			} catch (DAOException de) {
				de.printStackTrace();
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}
		
		//Add for getting AppFormDTOList - COS Retail
		public List<MobAppFormDTO> getCosRSMobAppFormList(){
			try{
				return constantLkupDao.getMobAppFormList(1, "COS");
			} catch (DAOException de) {
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}
		
	public List<String> getAsciiReplaceList() {
		// martin
		try {
			return constantLkupDao.getAsciiReplaceList();
		} catch (DAOException de) {
			logger.error(de);
				throw new AppRuntimeException(de);
		}
	}

		
	//kinman new nowtv
	public String getNewTvPriceCutOff() {
		try{
			String cutOff="";
			cutOff = constantLkupDao.getNewTvPriceCutOff();
			return cutOff;
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getImsClubCutOff() {
		try{
			String cutOff="";
			cutOff = constantLkupDao.getImsClubCutOff();
			return cutOff;
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getImsFsPrepayCutOff() {
		try{
			String cutOff="";
			cutOff = constantLkupDao.getImsFsPrepayCutOff();
			return cutOff;
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public String getImsNTVClubCutOff() {
		try{
			String cutOff="";
			cutOff = constantLkupDao.getImsNTVClubCutOff();
			return cutOff;
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getImsNTVFsPrepayCutOff() {
		try{
			String cutOff="";
			cutOff = constantLkupDao.getImsNTVFsPrepayCutOff();
			return cutOff;
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
		public Map<String, String> getImsDSSuspendCodeLookup() {
			try{
				
				Map<String, String> map = new HashMap<String, String>();
				List<Map<String, String>> list = constantLkupDao.getImsLookUpCode("SB_IMS_DS_ACQ_SUS");
				
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						map.put(list.get(i).get("code"), list.get(i).get("description"));
					}
				}
				
				return map;
				
			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}

		public Map<String, String> getImsWaiveQCCodeLookup() {
			try{
				
				Map<String, String> map = new HashMap<String, String>();
				List<Map<String, String>> list = constantLkupDao.getImsDisPlayLkup("IMS_WAIVE_QC_REASON", "en");
				
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						map.put(list.get(i).get("id"), list.get(i).get("description"));
					}
				}
				
				return map;
				
			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}

		public Map<String, Map<String, String>> getOptLookup() {
			try{
				
				Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
				List<Map<String, String>> list = constantLkupDao.getImsDisplayLkupAllLang("SB_IMS_NTV_O%");
				
				Map<String, String> contentMap = null;
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						if(map.containsKey(list.get(i).get("locale")))
						{
							contentMap = map.get(list.get(i).get("locale"));
						}
						else
						{
							contentMap = new HashMap<String, String>();
							map.put(list.get(i).get("locale"), contentMap);
						}
//						contentMap.put(list.get(i).get("type"), list.get(i).get("description"));
						contentMap.put(list.get(i).get("type"), NTVUtil.defaultString(list.get(i).get("description")));
					}
				}
				
				return map;
				
			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}
		
		public Map<String, String> getImsDSCancelCodeLookup() {
			try{
				
				Map<String, String> map = new HashMap<String, String>();
				List<Map<String, String>> list = constantLkupDao.getImsLookUpCode("SB_IMS_DS_ACQ_CAN");
				
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						map.put(list.get(i).get("code"), list.get(i).get("description"));
					}
				}
				
				return map;
				
			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}

		public Map<String, String> getImsdistrictLookupMap() {
			Map<String, String> map = new HashMap<String, String>();
			if(LookupTableBean.getInstance().getAddressDistrictList() != null) {
				for (AddressDistrictDTO d : LookupTableBean.getInstance().getAddressDistrictList()){
					map.put(d.getDistrictCode(), d.getDistrictDescription());
				}
			}
			
			return map;
		}

		public Map<String, String> getMobSimTypeMap() {
			try{
				Map<String, String> map = new HashMap<String, String>();
				List<String[]> list = constantLkupDao.getMobSimTypeMap();
				
				if(CollectionUtils.isNotEmpty(list)){
					for (String[] result : list) {
						map.put(result[0], result[1]);
					}
				}
				return map;
			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}
		
		public Map<String,MobItemQuotaDTO> getMobItemQuotaMap() {
			return mobItemQuotaService.getMobItemQuotaAsMap();
		}
		
		public Map<String, String> getImsNTVSuspendCodeLookup() {
			try{
				Map<String, String> map = new HashMap<String, String>();
				List<Map<String, String>> list = constantLkupDao.getImsLookUpCode("SB_IMS_NTV_ACQ_SUS");
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						map.put(list.get(i).get("code"), list.get(i).get("description"));
					}
				}
				return map;
			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}
		
		public Map<String, String> getImsNTVCancelCodeLookup() {		
			try{
				Map<String, String> map = new HashMap<String, String>();
				List<Map<String, String>> list = constantLkupDao.getImsLookUpCode("SB_IMS_NTV_ACQ_CAN");
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						map.put(list.get(i).get("code"), list.get(i).get("description"));
					}
				}
				return map;
			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}
		
		public Map<String,  Map<String, String>> getImsThirdPartyRelationshipLookUpCode(){		

			try{

				Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
				List<Map<String, String>> list = constantLkupDao.getImsThirdPartyRelationshipLookUpCode();

				Map<String, String> contentMap = null;
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						if(map.containsKey(list.get(i).get("locale")))
						{
							contentMap = map.get(list.get(i).get("locale"));
						}
						else
						{
							contentMap = new LinkedHashMap<String, String>();
							map.put(list.get(i).get("locale"), contentMap);
						}
						contentMap.put(list.get(i).get("code"), list.get(i).get("description"));
					}
				}
				
				return map;

			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}

		}
		
		public Map<String,  Map<String, String>> getImsThirdPartyRelationshipChannel13LookUpCode(){		

			try{

				Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
				List<Map<String, String>> list = constantLkupDao.getImsThirdPartyRelationshipChannel13LookUpCode();

				Map<String, String> contentMap = null;
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						if(map.containsKey(list.get(i).get("locale")))
						{
							contentMap = map.get(list.get(i).get("locale"));
						}
						else
						{
							contentMap = new LinkedHashMap<String, String>();
							map.put(list.get(i).get("locale"), contentMap);
						}
						contentMap.put(list.get(i).get("code"), list.get(i).get("description"));
					}
				}
				
				return map;

			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}

		}
		
		public Map<String,  Map<String, String>> getSpecialRequestLookup(){		

			try{

				Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
				List<Map<String, String>> list = constantLkupDao.getSpecialRequest();

				Map<String, String> contentMap = null;
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						if(map.containsKey(list.get(i).get("locale")))
						{
							contentMap = map.get(list.get(i).get("locale"));
						}
						else
						{
							contentMap = new LinkedHashMap<String, String>();
							map.put(list.get(i).get("locale"), contentMap);
						}
						contentMap.put(list.get(i).get("code"), list.get(i).get("description"));
					}
				}
				
				return map;

			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}

		}
		
		public Map<String,  Map<String, String>> getImsClubOptOutReasonLookUpCode(){		

			try{

				Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
				List<Map<String, String>> list = constantLkupDao.getImsLookUpCodeMultiLang("SB_NTV_CLUB_OPTOUT");

				Map<String, String> contentMap = null;
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						if(map.containsKey(list.get(i).get("locale")))
						{
							contentMap = map.get(list.get(i).get("locale"));
						}
						else
						{
							contentMap = new LinkedHashMap<String, String>();
							map.put(list.get(i).get("locale"), contentMap);
						}
						contentMap.put(list.get(i).get("code"), list.get(i).get("description"));
					}
				}
				
				return map;

			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}

		}
		
		public String getEnableIOExclusiveCheckDesc() {
			try{

				String enableIOExclusiveCheckDesc="";
				enableIOExclusiveCheckDesc = constantLkupDao.getEnableIOExclusiveCheckDesc();

				return enableIOExclusiveCheckDesc;

			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}
		
		
		public Map<String,List<ItemFuncAssgnMobDTO>> getItemFuncAssgnMobMap() {
			
			HashMap<String,List<ItemFuncAssgnMobDTO>> itemFuncInfos = new HashMap<String,List<ItemFuncAssgnMobDTO>>();
			
			try{
				List<ItemFuncAssgnMobDTO> getItemFuncAssgnMobListAll = constantLkupDao.getItemFuncAssgnMobListAll();
				
				
				if (CollectionUtils.isNotEmpty(getItemFuncAssgnMobListAll)) {
					
					String oldItemId ="";					
					List<ItemFuncAssgnMobDTO> itemFuncListByItemId = new ArrayList<ItemFuncAssgnMobDTO>();
					
					for (ItemFuncAssgnMobDTO dto : getItemFuncAssgnMobListAll){		
				
						if (!oldItemId.equals(dto.getItemId())){					
							itemFuncListByItemId = new ArrayList<ItemFuncAssgnMobDTO>();												
						}
						
						itemFuncListByItemId.add(dto);		
						itemFuncInfos.put(dto.getItemId(), itemFuncListByItemId);							
						oldItemId = dto.getItemId();								
					}
					
					itemFuncInfos.put(oldItemId, itemFuncListByItemId);
					
				}else{
					return null;
				}
				

				
				return itemFuncInfos;

			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
			
		}
		public String getCareCashVisitDate() {
			try{
				String date="";
				date = constantLkupDao.getCareCashVisitDate();
				return date;
			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}
		
		
		public String getCareCashShowInd() {
			try{
				String ind="";
				ind = constantLkupDao.getCareCashShowInd("IMS_SHOW_CARECASH");
				return ind;
			}catch(DAOException de){
				logger.error("getCareCashShowInd: "+de);
				throw new AppRuntimeException(de);
			}
		}
		
		public String getNTVCareCashShowInd() {
			try{
				String ind="";
				ind = constantLkupDao.getCareCashShowInd("NTVA_SHOW_CARECASH");
				return ind;
			}catch(DAOException de){
				logger.error("getCareCashShowInd: "+de);
				throw new AppRuntimeException(de);
			}
		}
		
		public String getDisableWQInd() {
			try{
				String ind="";
				ind = constantLkupDao.getDisableWQInd();
				return ind;
			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}
		}
		
		public Map<String, Map<String,String>> getImsServPlanStaticReportWords(String rptName, String attribute) {
			Map<String, Map<String,String>> result = new HashMap<String, Map<String,String>>();
			try {
				Map<String, String> result1 = constantLkupDao.getImsServPlanStaticReportWords("en", rptName, attribute);
				Map<String, String> result2 = constantLkupDao.getImsServPlanStaticReportWords("zh_HK", rptName, attribute);
				result.put("en", result1);
				result.put("zh_HK", result2);
			} catch (DAOException de) {
				logger.error(de);
				result = null;
			}
			return result;
		}
		
		public Map<String,  Map<String, String>> getImsNTVRetSellingSegmentLookup(){		

			try{

				Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
				List<Map<String, String>> list = constantLkupDao.getImsNTVRetSellingSegmentLookUpCode();

				Map<String, String> contentMap = null;
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						if(map.containsKey(list.get(i).get("locale")))
						{
							contentMap = map.get(list.get(i).get("locale"));
						}
						else
						{
							contentMap = new LinkedHashMap<String, String>();
							map.put(list.get(i).get("locale"), contentMap);
						}
						contentMap.put(list.get(i).get("pcm_name"), list.get(i).get("description"));
					}
				}
				
				return map;

			}catch(DAOException de){
				logger.error(de);
				throw new AppRuntimeException(de);
			}

		}
		
		public String getSellingSegmentShowInd() {
			try{
				String ind="";
				ind = constantLkupDao.getSellingSegmentShowInd("NTVRET_SHOW_SELLSEG");
				return ind;
			}catch(DAOException de){
				logger.error("getSellingSegmentShowInd: "+de);
				throw new AppRuntimeException(de);
			}
		}
		
		public void getImsNTVCPQProxySettings() {
			String proxyUrl = "";
			String proxyPort = "";
			proxyUrl = ntvProxyUrlAndPort.split(":").length>0?ntvProxyUrlAndPort.split(":")[0]:"";
			proxyPort = ntvProxyUrlAndPort.split(":").length>1?ntvProxyUrlAndPort.split(":")[1]:"";
			
			try {
				String[] settings = constantLkupDao.getImsNTVCPQProxySettings();
				if ("Y".equals(settings[0])) {
					AxisProperties.getProperties().put("proxySet", "true");
					if (proxyUrl!=null && !"".equals(proxyUrl)) {
						AxisProperties.setProperty("http.proxyHost", proxyUrl);
						if (!"".equals(proxyPort)) {
							AxisProperties.setProperty("http.proxyPort", proxyPort);
						}
					} else {
						AxisProperties.setProperty("http.proxyHost", settings[1]);
						if (!"".equals(settings[2])) {
							AxisProperties.setProperty("http.proxyPort", settings[2]);
						}
					}
				}
			} catch (DAOException de) {
				logger.error("getImsNTVCPQProxySettings: "+de);
			}
		}
		
		public boolean reloadLkUp(){
			try{
				
				List<IssueBankDTO> issueBankList = this.getIssueBankList();
				LookupTableBean.getInstance().setIssueBankList(issueBankList);
				
				List<Integer> billPeriodList = this.getBillPeriodList();
				LookupTableBean.getInstance().setBillPeriodList(billPeriodList);
				
				List<AddressAreaDTO> addressAreaList = this.getAddressAreaList();
				LookupTableBean.getInstance().setAddressAreaList(addressAreaList);
				
				List<AddressCategoryDTO> addressCategoryList = this.getAddressCategoryList();
				LookupTableBean.getInstance().setAddressCategoryList(addressCategoryList);
				
				List<AddressDistrictDTO> addressDistrictList = this.getAddressDistrictList();
				LookupTableBean.getInstance().setAddressDistrictList(addressDistrictList);
				
				HashMap<String,Boolean> imsNonReaplaceKeywordsList = this.getImsNotReplaceKeywordsList();
				LookupTableBean.getInstance().setImsNonReplaceKeywords(imsNonReaplaceKeywordsList);
				
				List<AddressSectionDTO> addressSectionList = this.getAddressSectionList();
				LookupTableBean.getInstance().setAddressSectionList(addressSectionList);
				
				List<AddressAreaDTO> imsAddressAreaList = this.getImsAddressAreaList();
				LookupTableBean.getInstance().setImsAddressAreaList(imsAddressAreaList);
				
				List<AddressDistrictDTO> imsAddressDistrictList = this.getImsAddressDistrictList();
				LookupTableBean.getInstance().setImsAddressDistrictList(imsAddressDistrictList);
				
				List<AddressSectionDTO> imsAddressSectionList = this.getImsAddressSectionList();
				LookupTableBean.getInstance().setImsAddressSectionList(imsAddressSectionList);
				
				List<BankBranchDTO> bankBranchList = this.getBankBranchList();
				LookupTableBean.getInstance().setBankBranchList(bankBranchList);
				
				List<CreditCardTypeDTO> creditCardTypeList = this.getCreditCardTypeList();
				LookupTableBean.getInstance().setCreditCardTypeList(creditCardTypeList);
				
				//add 20110718, create AddressDistrictMap
				Map<String, String> tempAddressDistrictMap = new HashMap<String, String>();
				for ( int i =0 ; i<LookupTableBean.getInstance().getAddressDistrictList().size(); i++ ){
					tempAddressDistrictMap.put(LookupTableBean.getInstance().getAddressDistrictList().get(i).getDistrictCode(), LookupTableBean.getInstance().getAddressDistrictList().get(i).getAreaCode());
				}
				LookupTableBean.getInstance().setAddressDistrictMap(tempAddressDistrictMap);
				
				//add by IMS
				LookupTableBean.getInstance().setImsSuspendLookupMap(this.getImsSuspendCodeLookup());
				LookupTableBean.getInstance().setImsPTSuspendLookupMap(this.getImsPTSuspendCodeLookup());
				LookupTableBean.getInstance().setImsCancelLookupMap(this.getImsCancelCodeLookup());
				LookupTableBean.getInstance().setImsDSCancelLookupMap(this.getImsDSCancelCodeLookup());
				LookupTableBean.getInstance().setImsDSSuspendLookupMap(this.getImsDSSuspendCodeLookup());
				LookupTableBean.getInstance().setImsWaiveQCLookupMap(this.getImsWaiveQCCodeLookup());
				LookupTableBean.getInstance().setImsDistrictLookupMap(this.getImsdistrictLookupMap());
				LookupTableBean.getInstance().setImsNTVSuspendLookupMap(this.getImsNTVSuspendCodeLookup());
				LookupTableBean.getInstance().setImsNTVCancelLookupMap(this.getImsNTVCancelCodeLookup());
				LookupTableBean.getInstance().setImsOptLookupMap(this.getOptLookup());
				LookupTableBean.getInstance().setImsThirdPartyRelLookupMap(this.getImsThirdPartyRelationshipLookUpCode());
				LookupTableBean.getInstance().setImsThirdPartyRelCh13LookupMap(this.getImsThirdPartyRelationshipChannel13LookUpCode());
				LookupTableBean.getInstance().setImsNtvClubOptoutLookupMap(this.getImsClubOptOutReasonLookUpCode());
				LookupTableBean.getInstance().setImsNTVRetSellingSegmentLookupMap(this.getImsNTVRetSellingSegmentLookup());
				LookupTableBean.getInstance().setSellSegmentShowInd(this.getSellingSegmentShowInd());
				LookupTableBean.getInstance().setImsDSAppMethodMap(this.getImsDSAppMethod());
				
				//******************added by James 20120221***************
				Map<String, List<CodeLkupDTO>> codeLkupMap = this.getAllCodeLkup();
				LookupTableBean.getInstance().setCodeLkupList(codeLkupMap);
				//********************************************************
				
				
				String[] temp = new String[LookupTableBean.getInstance().getCodeLkupList().get("CCS_FALLOUT_CH").size()];
				   
				List<CodeLkupDTO> wordList = LookupTableBean.getInstance().getCodeLkupList().get("CCS_FALLOUT_CH"); 
		   
		      for (int i=0; i< temp.length; i++)  
		      {  
		    	  temp[i]=wordList.get(i).getCodeId();
		      }  
		      
		      LookupTableBean.getInstance().setAllowFalloutChannelList( temp);
		      
		      List<String> asciiReplaceList = this.getAsciiReplaceList(); // martin
		      LookupTableBean.getInstance().setAsciiReplaceList(asciiReplaceList); // martin
		      
		      this.getImsNTVCPQProxySettings(); // martin
				 
			}catch(Exception e){
				logger.error(e);
				
			}
			
			return true;
		}

}
