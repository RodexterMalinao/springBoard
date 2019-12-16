package com.bomwebportal.lts.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LtsRefereeSalesInfoDAO;
import com.bomwebportal.lts.dao.LtsSalesInfoDAO;
import com.bomwebportal.lts.dao.bom.ImsApplSrcLkupDAO;
import com.bomwebportal.lts.dao.order.OrderDetailDAO;
import com.bomwebportal.lts.dto.acq.LtsRefereeSaleDTO;
import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSalesInfoFormDTO;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsSalesInfoServiceImpl implements LtsSalesInfoService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private LtsRefereeSalesInfoDAO ltsRefSalesInfoDAO;
	private LtsSalesInfoDAO ltsSalesInfoDAO;
	private ImsApplSrcLkupDAO imsApplSrcLkupDao;
    private CodeLkupCacheService imsApplMthdCodeLkupCacheService;
    private LtsCommonService ltsCommonService;
    private CodeLkupCacheService salesPrefixConversionCodeLkupCacheService;
    private OrderDetailDAO orderDetailDao;

    public LtsRefereeSaleDTO getRefSalesInfo(String pOrderId){
    	try {
			return ltsRefSalesInfoDAO.searchReferreeStaffInformation(pOrderId);
		} catch (DAOException de) {
			logger.error(de);
			return null;
		}
    }
    
	public LtsSalesInfoFormDTO getSalesInfo(String orgStaffId) {
		try {
//			String staffId = "T"+orgStaffId; //default add T as prefix
//			
//			LookupItemDTO[] codeLkupItems = salesPrefixConversionCodeLkupCacheService.getCodeLkupDAO().getCodeLkup();
//			//lookup to see if the prefix need to be replaced
//			for(LookupItemDTO codeLkupItem : codeLkupItems){
//				if(StringUtils.startsWith(orgStaffId, codeLkupItem.getItemKey())){
//					staffId = StringUtils.replaceOnce(orgStaffId, codeLkupItem.getItemKey(), (String) codeLkupItem.getItemValue());
//					break;
//				}
//			}
			String staffId = convertToStaffId(orgStaffId);
			return ltsSalesInfoDAO.getStaffInfo(orgStaffId, staffId);
		} catch (DAOException de) {
			logger.error(de);
			return null;
		}
	}
	
	public LtsAcqSalesInfoFormDTO getAcqSalesInfo(String orgStaffId) {
		try {

			String staffId = convertToStaffId(orgStaffId);
			return ltsSalesInfoDAO.getAcqStaffInfo(orgStaffId, staffId);
		} catch (DAOException de) {
			logger.error(de);
			return null;
		}
	}

	public String convertToStaffId(String orgStaffId){
		if(StringUtils.isEmpty(orgStaffId)){
			return null;
		}
		try {
			String staffId = "T"+orgStaffId; //default add T as prefix
			LookupItemDTO[] codeLkupItems = salesPrefixConversionCodeLkupCacheService.getCodeLkupDAO().getCodeLkup();
			
			for(LookupItemDTO codeLkupItem : codeLkupItems){
				if(StringUtils.startsWith(orgStaffId, codeLkupItem.getItemKey())){
					staffId = StringUtils.replaceOnce(orgStaffId, codeLkupItem.getItemKey(), (String) codeLkupItem.getItemValue());
					break;
				}
			}
			return staffId;
			
		} catch (DAOException de) {
			logger.error(de);
			return null;
		}
	}

	public String convertToOrgStaffId(String staffId){
		if(StringUtils.isEmpty(staffId)){
			return null;
		}
		try {
			String orgStaffId = null;
			LookupItemDTO[] codeLkupItems = salesPrefixConversionCodeLkupCacheService.getCodeLkupDAO().getCodeLkup();
			
			for(LookupItemDTO codeLkupItem : codeLkupItems){
				if(StringUtils.startsWith(staffId, (String) codeLkupItem.getItemValue())){
					orgStaffId = StringUtils.replaceOnce(staffId, (String) codeLkupItem.getItemValue(),  codeLkupItem.getItemKey());
					break;
				}
			}
			
			if(orgStaffId != null){
				return orgStaffId;
			}else{
				if(staffId.startsWith("T")){
					return StringUtils.replaceOnce(staffId, "T",  "");
				}else{
					return staffId;
				}
			}
			
		} catch (DAOException de) {
			logger.error(de);
			return null;
		}
	}
	
	//return {SALES_CHANNEL, CONTACT_PHONE}
	public String[] getShopContactAndChannel(String pShopCode){
		try {
			return ltsSalesInfoDAO.getShopContactAndChannel(pShopCode);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	//return {CONTACT_NUMBER}
	public String getSalesContact(String pStaffId){
		try {
			return ltsSalesInfoDAO.getSalesContact(pStaffId);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String[] getOrgStaffId(String staffId){
		try {
			List<String[]> orgStaffId = ltsSalesInfoDAO.getOrgStaffId(staffId);
			return orgStaffId == null || orgStaffId.size() == 0 ? new String[]{null, null} :orgStaffId.get(0);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		} catch (IndexOutOfBoundsException oiibe){
//			logger.error(oiibe);
			return new String[]{null, null};
		}
	}
	
	public LookupItemDTO[] getImsApplMthdLkup(){
		try{
			return imsApplMthdCodeLkupCacheService.getCodeLkupDAO().getCodeLkup();
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}
	
	/* get ims application source by application method description
	 * eg. input 'CSS' => get source by code of 'CSS'(30) from lkup
	 */
	public List<String> getImsApplSourceByMthdDesc(String applMthd){
		try{
			LookupItemDTO[] imsApplMthdCodes = imsApplMthdCodeLkupCacheService.getCodeLkupDAO().getCodeLkup();
			String imsApplMthdCd = null;
			for(LookupItemDTO lkup: imsApplMthdCodes){
				if(StringUtils.equals(lkup.getItemKey(), applMthd)){
					imsApplMthdCd = (String) lkup.getItemValue();
				}
			}
			return getImsApplSource(imsApplMthdCd);
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}
	
	public List<String> getImsApplSource(String applMthdCd){
		try {
			return imsApplSrcLkupDao.getImsApplSource(applMthdCd);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		} 
	}
	
	public List<String> getTeamCdListByChannelCd(String channelCd){
		try {
			return ltsSalesInfoDAO.getTeamCdListByChannelCd(channelCd);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public String getUserBoc(String pUserId) {
		try {
			return orderDetailDao.getUserBoc(pUserId);
		} catch (DAOException de) {
			logger.error(de);
			return null;
			//throw new AppRuntimeException(de);
		}
	}
	
	public String getStaffCategory(String pStaffId){
		try {
			return ltsSalesInfoDAO.getStaffCategory(pStaffId);
		} catch (DAOException de) {
			logger.error(de);
			return null;
		}
	}
	
	public LtsRefereeSalesInfoDAO getLtsRefSalesInfoDAO() {
		return ltsRefSalesInfoDAO;
	}

	public void setLtsRefSalesInfoDAO(LtsRefereeSalesInfoDAO ltsRefSalesInfoDAO) {
		this.ltsRefSalesInfoDAO = ltsRefSalesInfoDAO;
	}

	public LtsSalesInfoDAO getLtsSalesInfoDAO() {
		return ltsSalesInfoDAO;
	}

	public void setLtsSalesInfoDAO(LtsSalesInfoDAO ltsSalesInfoDAO) {
		this.ltsSalesInfoDAO = ltsSalesInfoDAO;
	}
	
	public ImsApplSrcLkupDAO getImsApplSrcLkupDao() {
		return imsApplSrcLkupDao;
	}

	public void setImsApplSrcLkupDao(ImsApplSrcLkupDAO imsApplSrcLkupDao) {
		this.imsApplSrcLkupDao = imsApplSrcLkupDao;
	}

	public CodeLkupCacheService getImsApplMthdCodeLkupCacheService() {
		return imsApplMthdCodeLkupCacheService;
	}

	public void setImsApplMthdCodeLkupCacheService(
			CodeLkupCacheService imsApplMthdCodeLkupCacheService) {
		this.imsApplMthdCodeLkupCacheService = imsApplMthdCodeLkupCacheService;
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	public CodeLkupCacheService getSalesPrefixConversionCodeLkupCacheService() {
		return salesPrefixConversionCodeLkupCacheService;
	}
	
	public void setSalesPrefixConversionCodeLkupCacheService(
			CodeLkupCacheService salesPrefixConversionCodeLkupCacheService) {
		this.salesPrefixConversionCodeLkupCacheService = salesPrefixConversionCodeLkupCacheService;
	}

	public OrderDetailDAO getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDAO orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}
}
