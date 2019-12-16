package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LtsPaymentDAO;
import com.bomwebportal.lts.dao.PrepaymentLkupDAO;
import com.bomwebportal.lts.dao.bom.BankCodeLkupDAO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsPrePaymentDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupCriteriaDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupResultDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsPaymentServiceImpl implements LtsPaymentService {
	
	protected final Log logger = LogFactory.getLog(getClass());

	private LtsPaymentDAO ltsPaymentDAO;
	private BankCodeLkupDAO bankCodeLkupDAO;
	private PrepaymentLkupDAO prepaymentLkupDAO;
	private CodeLkupCacheService ltsPrepayExcludePsefCacheService;
	private CodeLkupCacheService ltsPrepayPsefPriorityCacheService;
	
	public LtsPaymentDAO getLtsPaymentDAO() {
		return ltsPaymentDAO;
	}

	public void setLtsPaymentDAO(LtsPaymentDAO ltsPaymentDAO) {
		this.ltsPaymentDAO = ltsPaymentDAO;
	}

	public BankCodeLkupDAO getBankCodeLkupDAO() {
		return bankCodeLkupDAO;
	}

	public void setBankCodeLkupDAO(BankCodeLkupDAO bankCodeLkupDAO) {
		this.bankCodeLkupDAO = bankCodeLkupDAO;
	}

	public String getTenureCode(double tenure) {
		try {
			return ltsPaymentDAO.getTenureCode(tenure);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<String> getNewPayMethod(String tenureCode, String existPayMethod) {
		try {
			return ltsPaymentDAO.getNewPayMethods(tenureCode, existPayMethod);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<String[]> getPrePayItem(String tenureCode, String houseType, String deviceType){
		try {
			return ltsPaymentDAO.getPrePayItem(tenureCode, houseType, deviceType);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<LookupItemDTO> getBankCode() {
		try {
			return bankCodeLkupDAO.getBankCode();
		}catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<LookupItemDTO> getBranchCode(String bankCode) {
		try {
			return bankCodeLkupDAO.getBranchCode(bankCode);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	/**
	 * @return the prepaymentLkupDAO
	 */
	public PrepaymentLkupDAO getPrepaymentLkupDAO() {
		return prepaymentLkupDAO;
	}

	/**
	 * @param prepaymentLkupDAO the prepaymentLkupDAO to set
	 */
	public void setPrepaymentLkupDAO(PrepaymentLkupDAO prepaymentLkupDAO) {
		this.prepaymentLkupDAO = prepaymentLkupDAO;
	}

	public CodeLkupCacheService getLtsPrepayExcludePsefCacheService() {
		return ltsPrepayExcludePsefCacheService;
	}

	public void setLtsPrepayExcludePsefCacheService(
			CodeLkupCacheService ltsPrepayExcludePsefCacheService) {
		this.ltsPrepayExcludePsefCacheService = ltsPrepayExcludePsefCacheService;
	}

	public ItemDetailDTO getItemDetail(String id, String displayType,
			String locale, String type) {
		try {
			List<ItemDetailDTO> list = ltsPaymentDAO.getItemDetail(id, displayType, locale, type);
			if(list != null){
				return list.get(0);
			}
			return null;
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public ItemDetailDTO getItemDetail(double tenure, String newPayMethod,
			String houseType, String displayType, String locale, String type, String deviceType){
		try {
			String tenureCd = ltsPaymentDAO.getTenureCode(tenure);
			if(tenureCd != null){
				String id = ltsPaymentDAO.getPrePayItem(tenureCd, newPayMethod, houseType, deviceType);
				if(id != null){
					List<ItemDetailDTO> list = ltsPaymentDAO.getItemDetail(id, displayType, locale, type);
					if(list != null){
						return list.get(0);
					}
				}
			}
			return null;
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<ItemDetailDTO> getItemDetailByType(String type) {
		return getItemDetailByType(type, LtsConstant.DISPLAY_TYPE_ITEM_SELECT, "en");
	}
	
	public List<ItemDetailDTO> getItemDetailByType(String type, String displayType,
			String locale) {
		try {
			return ltsPaymentDAO.getItemDetailByType(type, displayType, locale);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<String> getCancelRemarkItemIdList(){
		try {
			return ltsPaymentDAO.getCancelRemarkItemIdList();
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public LtsPrePaymentDTO getOrderLtsPrepayment(String orderId) {
		try {
			return ltsPaymentDAO.getOrderLtsPrepayment(orderId);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public String getBranchNameByCode(String bankCd, String branchCd) {
		try {
			return bankCodeLkupDAO.getBranchNameByCode(bankCd, branchCd);
		}catch (DAOException de) {
			logger.error(de);
			return branchCd;
		}
	}

	public String getBankNameByCode(String bankCd) {
		try {
			return bankCodeLkupDAO.getBankNameByCode(bankCd);
		}catch (DAOException de) {
			logger.error(de);
			return bankCd;
		}
	}
	
	public List<String> getCcExpireYearList(){
		Calendar today = new GregorianCalendar();
		int year = today.get(Calendar.YEAR);
		List<String> yearList = new ArrayList<String>();
		for (int i = year; i < year + 10; i++) {
			yearList.add(Integer.toString(i));
		}
		return yearList;
	}
	

	public List<PrepaymentLkupResultDTO> getPrepaymentItemIdByLkup(PrepaymentLkupCriteriaDTO criteria){
		try {
			
			LookupItemDTO[] psefPriorityLkups = ltsPrepayPsefPriorityCacheService.getCodeLkupDAO().getCodeLkup();
			
			Map<String, Integer> psefPriorityMap = new HashMap<String, Integer>(); //<PSEF prefix, priority>
			if(psefPriorityLkups != null){
				for(LookupItemDTO lkup: psefPriorityLkups){
					psefPriorityMap.put((String) lkup.getItemValue(), Integer.parseInt(lkup.getItemKey()) ); 
				}
			}
			
			List<PrepaymentLkupResultDTO> resultList = prepaymentLkupDAO.getPrepaymentItemId(criteria);
			if(resultList != null && resultList.size() > 0){
				for(PrepaymentLkupResultDTO result : resultList){
					Set<String> psefSet = new HashSet<String>();
					String[] psefCds = StringUtils.split(result.getPsefCd(), "|");
					if(psefCds != null && psefCds.length > 0){
						int priority = 99;
						for(String psef : psefCds){
							psef = StringUtils.trim(psef);
							psefSet.add(psef);
							Integer psefPriority = psefPriorityMap.get(StringUtils.substring(psef, 0, 1));
							if(psefPriority != null && psefPriority < priority){
								priority = psefPriority;
							}
						}
						result.setPriority(priority);
						result.setPsefCdSet(psefSet);
					}
				}
			}
			
			//Sort by priority
			Collections.sort(resultList);
			
			return resultList;
		}catch (DAOException de) {
			logger.error("Fail in LtsPaymentService.getPrepaymentItemIdByLkup " + de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}
	
	public List<String> getPrepayExcludePsefCodeList(){
		
		List<String> excludePsefCode = new ArrayList<String>();
		
		try {
			for(LookupItemDTO codeLkup: ltsPrepayExcludePsefCacheService.getCodeLkupDAO().getCodeLkup()){
				excludePsefCode.add(codeLkup.getItemKey());
			}
		} catch (DAOException e) {
			return new ArrayList<String>();
		}
		
		return excludePsefCode;
		
	}

	public CodeLkupCacheService getLtsPrepayPsefPriorityCacheService() {
		return ltsPrepayPsefPriorityCacheService;
	}

	public void setLtsPrepayPsefPriorityCacheService(
			CodeLkupCacheService ltsPrepayPsefPriorityCacheService) {
		this.ltsPrepayPsefPriorityCacheService = ltsPrepayPsefPriorityCacheService;
	}
}
