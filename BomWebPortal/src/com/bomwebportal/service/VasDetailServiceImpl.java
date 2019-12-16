package com.bomwebportal.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.dao.SbItemPreDAO;
import com.bomwebportal.dao.VasDetailDAO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BasketMinVasLkupDTO;
import com.bomwebportal.dto.BasketParmDTO;
import com.bomwebportal.dto.ExclusiveItemDTO;
import com.bomwebportal.dto.MobItemQuotaDTO;
import com.bomwebportal.dto.ProductInfoDTO;
import com.bomwebportal.dto.ResultDTO;
import com.bomwebportal.dto.SbItemPreDTO;
import com.bomwebportal.dto.SimDTO;
import com.bomwebportal.dto.VasAttbComponentDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.report.RptVasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentMonthyUI;
import com.bomwebportal.util.Utility;

@Transactional(readOnly=true)
public class VasDetailServiceImpl implements VasDetailService {
	
	private VasDetailDAO vasDetailDao;
	
	private OrderDAO orderDao;
	
	private SbItemPreDAO sbItemPreDao;

	public VasDetailDAO getVasDetailDao() {
		return vasDetailDao;
	}

	public void setVasDetailDao(VasDetailDAO vasDetailDao) {
		this.vasDetailDao = vasDetailDao;
	}

	public OrderDAO getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	public SbItemPreDAO getSbItemPreDao() {
		return sbItemPreDao;
	}

	public void setSbItemPreDao(SbItemPreDAO sbItemPreDao) {
		this.sbItemPreDao = sbItemPreDao;
	}

	protected final Log logger = LogFactory.getLog(getClass());
	
	// MIP.P4 modification
	public List<VasDetailDTO> getVasDetailList(String channelId, String locale, String basketId, 
			String[] selectedList,  String displayType, String appDate, String channelCd,
			String mipBrand, String mipSimType, String offerNature){
		List<VasDetailDTO> vasList=new ArrayList<VasDetailDTO>();
    	
		try{
			logger.debug("getVasDetailList() is called in VasDetailService service");
			
			// MIP.P4 modification
			vasList=vasDetailDao.getVasDetailList(channelId,locale, basketId ,selectedList, displayType, appDate, channelCd, mipBrand, mipSimType, offerNature);
			
			if (CollectionUtils.isNotEmpty(vasList)) {
				HashMap<String,MobItemQuotaDTO> quotaMap=  (HashMap<String, MobItemQuotaDTO>) LookupTableBean.getInstance().getMobItemQuotaMap();
				for (VasDetailDTO dto : vasList) {
					MobItemQuotaDTO temp = quotaMap.get(dto.getItemId());
					if (temp!= null 
							&& Utility.dateWithin(Utility.string2Date(appDate) , temp.getEffStartDate(), temp.getEffEndDate())
							&& Utility.dateWithin(Utility.string2Date(appDate) , temp.getQuotaAssignEffStartDate(), temp.getQuotaAssignEffEndDate())
							){
						dto.setMobItemQuotaInfo(temp);
						Utility.toPrettyJson(temp);
					}
				}
			}
			
		}catch (DAOException de) {
			logger.error("Exception caught in getVasDetailtList()", de);
			throw new AppRuntimeException(de);
		}
		
		return vasList;
	}
	
	// MIP.P4 modification
	public List<VasDetailDTO> getHardBundleVasDetailList(String channelId, String locale, String basketId, 
			String[] selectedList,  String displayType, String appDate, String channelCd,
			String mipBrand, String mipSimType, String offerNature){
		List<VasDetailDTO> vasList=new ArrayList<VasDetailDTO>();
    	
		try{
			logger.debug("getHardBundleVasDetailList() is called in VasDetailService service");
			String hardBundleGrpId = this.getBasketHardBundleGrpId(basketId, mipBrand, appDate);
			if (StringUtils.isNotBlank(hardBundleGrpId)) {
				// MIP.P4 modification
				vasList=vasDetailDao.getHardBundleVasDetailList(channelId,locale, basketId ,selectedList, displayType, appDate, channelCd, mipBrand, mipSimType, hardBundleGrpId, offerNature);
				if (CollectionUtils.isNotEmpty(vasList)) {
					HashMap<String,MobItemQuotaDTO> quotaMap=  (HashMap<String, MobItemQuotaDTO>) LookupTableBean.getInstance().getMobItemQuotaMap();
					for (VasDetailDTO dto : vasList) {
						MobItemQuotaDTO temp = quotaMap.get(dto.getItemId());
						if (temp!= null 
								&& Utility.dateWithin(Utility.string2Date(appDate) , temp.getEffStartDate(), temp.getEffEndDate())
								&& Utility.dateWithin(Utility.string2Date(appDate) , temp.getQuotaAssignEffStartDate(), temp.getQuotaAssignEffEndDate())
								){
							dto.setMobItemQuotaInfo(temp);
							Utility.toPrettyJson(temp);
						}
					}
				}
			}
		}catch (DAOException de) {
			logger.error("Exception caught in getHardBundleVasDetailList()", de);
			throw new AppRuntimeException(de);
		}
		
		return vasList;
	}
	
	public List<VasDetailDTO> getVasDetailSelectedList (String locale, String orderId){
		List<VasDetailDTO> vasList=new ArrayList<VasDetailDTO>();
    	
		try{
			logger.debug("getVasDetailSelectedList() is called in VasDetailService service");
			vasList= vasDetailDao.getVasDetailSelectedList(locale, orderId );
		}catch (DAOException de) {
			logger.error("Exception caught in getVasDetailtSelectedList()", de);
			throw new AppRuntimeException(de);
		}
		
		return vasList;
	}
	
	// MIP.P4 modification
	public List<VasDetailDTO> getRPHSRPList(String locale, String basketId, String appDate, String channelCd, String mipBrand, String mipSimType, String offerNature){
		List<VasDetailDTO> rphsrpList=new ArrayList<VasDetailDTO>();
    	
		try{
			logger.debug("getRPHSRPList() is called in VasDetailService service");
			
			// MIP.P4 modification
			rphsrpList= vasDetailDao.getRPHSRPList(locale, basketId, appDate, channelCd, mipBrand, mipSimType, offerNature);
			
		}catch (DAOException de) {
			logger.error("Exception caught in getRPHSRPList()", de);
			throw new AppRuntimeException(de);
		}
		
		return rphsrpList;
	}
	
	public List<VasDetailDTO> getReportUseVasDetailtList (String locale, String orderId, String basketId)
	{
		List<VasDetailDTO> vasList=new ArrayList<VasDetailDTO>();
    	
		try{
			logger.debug("getVasDetailtList() is called in VasDetailService service");
			vasList= vasDetailDao.getReportUseVasDetailtList(locale, orderId, basketId);
		}catch (DAOException de) {
			logger.error("Exception caught in getReportUseVasDetailtList()", de);
			throw new AppRuntimeException(de);
		}
		
		return vasList;
	}
	
	public List<VasDetailDTO> getReportUseRPHSRPList (String locale, String basketId, String displayType, String orderId){
		List<VasDetailDTO> rphsrpList=new ArrayList<VasDetailDTO>();
    	
		try{
			logger.debug("getReportUseRPHSRPList() is called in VasDetailService service");
			rphsrpList= vasDetailDao.getReportUseRPHSRPList ( locale,  basketId,  displayType,  orderId);
		}catch (DAOException de) {
			logger.error("Exception caught in getReportUseRPHSRPList()", de);
			throw new AppRuntimeException(de);
		}
		
		return rphsrpList;
	}
	
	/*public String getOrderId(){
		String orderId= new String();
		orderId= vasDetailDao.getOrderId();
		return orderId;
	}*/
	
	// for vasdetail page use
	public String getBasketDisplayTitle (String locale, String basketId){
	
    	
		try{
			logger.debug("getBasketDisplayTitle() is called in VasDetailService service");
			return  vasDetailDao.getBasketDisplayTitle(locale, basketId);
		}catch (DAOException de) {
			logger.error("Exception caught in getBasketDisplayTitle()", de);
			throw new AppRuntimeException(de);
		}
		
	
	}
	
	// for vasdetail page use
	/*public List<VasDetailDTO> getBFEEList (String locale, String basketId)
	{
		
    	
		try{
			logger.info("getBFEEList() is called in VasDetailService service");
			return  vasDetailDao.getBFEEList(locale, basketId);
		}catch (DAOException de) {
			logger.error("Exception caught in getBFEEList()", de);
			throw new AppRuntimeException(de);
		}
		
	
	}*/
	
	public List<ExclusiveItemDTO> getExclusiveItemList(List<String> selectedList) {

		try {
			logger.debug("getExclusiveItemList() is called in VasDetailService service");
			return vasDetailDao.getExclusiveItemList(selectedList);
		} catch (DAOException de) {
			logger.error("Exception caught in getExclusiveItemList()", de);
			throw new AppRuntimeException(de);
		}

	}
		
	public List<ProductInfoDTO> getBomProductList (String itemId){
		try {
			logger.debug("getBomProductList() is called in VasDetailService service");
			return vasDetailDao.getBomProductList(itemId);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomProductList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<VasAttbComponentDTO> getVasAttbComponentList(List<String> vasList, String channelId){
		try{
			return vasDetailDao.getVasAttbComponentList(vasList, channelId);
		}catch (DAOException de){
			logger.error("Exception caught in getVasAttbComponentList()", de);
			throw new AppRuntimeException(de);			
		}
	}
	
	public List<String> getSubscribedVASList(String basketId, String[] selectedItemList, String mipBrand, String mipSimType){
		List<String> bundleVasList = new ArrayList<String>();
		try{
			logger.info("Basket ID: " + basketId);
			bundleVasList = orderDao.getBundleVASList(basketId, mipBrand, mipSimType);
			if(selectedItemList != null)
            {
                for(int i = 0; i < selectedItemList.length; i++)
                {
                	bundleVasList.add(selectedItemList[i]);
                }

            }
			logger.info("bundleVasList size: " + bundleVasList.size());
			List<ExclusiveItemDTO> exclusiveItemList = vasDetailDao.getExclusiveItemList(bundleVasList);
			logger.info("exclusiveItemList size: " + exclusiveItemList.size());
			List<String> exclusiveItemIdList = new ArrayList<String>();
            for(int i = 0; i < exclusiveItemList.size(); i++)
            {
                ExclusiveItemDTO exclusiveItem = (ExclusiveItemDTO)exclusiveItemList.get(i);
                if("BVAS".equals(exclusiveItem.getItemTypeA()))
                {
                    exclusiveItemIdList.add(exclusiveItem.getItemIdA());
                } else
                if("BVAS".equals(exclusiveItem.getItemTypeB()))
                {
                    exclusiveItemIdList.add(exclusiveItem.getItemIdB());
                }
            }
			
			for(int i=0; i<exclusiveItemIdList.size(); i++){
				String exclusiveItem = (String)exclusiveItemIdList.get(i);
				//logger.info("exclusiveItem: " + exclusiveItem.get
				if(bundleVasList.contains(exclusiveItem)){
					bundleVasList.remove(exclusiveItem);
				}
			}		
		}catch(DAOException de){
			logger.error("Exception caught in getSubscribedVASList()", de);
			throw new AppRuntimeException(de);		
		}
			return bundleVasList;
	}
	
	public List<ProductInfoDTO> getBomProductNonDisplayItemList (String basketId){
		try {
			logger.debug("getBomProductNonDisplayItemList() is called in VasDetailService service");
			return vasDetailDao.getBomProductNonDisplayItemList(basketId);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomProductNonDisplayItemList()", de);
			throw new AppRuntimeException(de);
		}
	}
		
	public List<VasDetailDTO> getReportUseFreeGifsDetailtList(String locale,String orderId, String basketId) 
	{
		List<VasDetailDTO> vasList=new ArrayList<VasDetailDTO>();
    	
		try{
			logger.debug("getReportUseFreeGifsDetailtList() is called in VasDetailService service");
			vasList= vasDetailDao.getReportUseFreeGifsDetailtList(locale, orderId, basketId);
		}catch (DAOException de) {
			logger.error("Exception caught in getReportUseFreeGifsDetailtList()", de);
			throw new AppRuntimeException(de);
		}
		
		return vasList;
	}

	public List<VasDetailDTO> getReportUseVasOptionalDetailtList(String locale,
			String orderId, String basketId)
			{
		List<VasDetailDTO> vasList=new ArrayList<VasDetailDTO>();
    	
		try{
			logger.debug("getReportUseVasOptionalDetailtList() is called in VasDetailService service");
			vasList= vasDetailDao.getReportUseVasOptionalDetailtList(locale, orderId, basketId);
		}catch (DAOException de) {
			logger.error("Exception caught in getReportUseVasOptionalDetailtList()", de);
			throw new AppRuntimeException(de);
		}
		
		return vasList;
	}
	
	
	
	 
	 
	public List<VasDetailDTO> getUserSelectedBasketItemList(String locale, String appDate, String basketId,String selectedItemList[]
	 )
			{
		List<VasDetailDTO> vasList=new ArrayList<VasDetailDTO>();
    	
		try{
			logger.debug("getUserSelectedBasketItemList() is called in VasDetailService service");
			vasList= vasDetailDao.getUserSelectedBasketItemList(locale, appDate, basketId, selectedItemList);
		}catch (DAOException de) {
			logger.error("Exception caught in getUserSelectedBasketItemList()", de);
			throw new AppRuntimeException(de);
		}
		
		return vasList;
	}
	
	
	
	
	public BasketDTO getBasketAttribute(String basketId, String appDate) {
		
		try{
			
			return vasDetailDao.getBasketAttribute(basketId, appDate);
		}catch (DAOException de){
			logger.error("Exception caught in getBasketAttribute()", de);
			throw new AppRuntimeException(de);			
		}
	}

	// MIP.P4 modification
	public List<SimDTO> getSimSelection(String locale, String appDate, String basketId, String orderId, String channelCd, String mipSimType, String mipBrand, String ivOfferNature) {
		try {
			// MIP.P4 modification
			return vasDetailDao.getSimSelection(locale, appDate, basketId, orderId, channelCd, mipSimType, mipBrand, ivOfferNature);
		} catch (DAOException e) {
			logger.error("Exception caught in getSimTypeSelection()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	// MIP.P4 modification
	public List<VasDetailDTO> getSimTypeSelection(String locale, String appDate, String basketId, String orderId, String channelCd, String mipSimType, String mipBrand, String offerNature) {
		try {
			// MIP.P4 modification
			return vasDetailDao.getSimTypeSelection(locale, appDate, basketId, orderId, channelCd, mipSimType, mipBrand, offerNature);
		} catch (DAOException e) {
			logger.error("Exception caught in getSimTypeSelection()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	
	public String getDefaultSimItemId(String basketId, String appDate){
		
		try{
			
			return vasDetailDao.getDefaultSimItemId(basketId, appDate);
		}catch (DAOException de){
			logger.error("Exception caught in getDefaultSimItemId()", de);
			throw new AppRuntimeException(de);			
		}
	}
	
	public List<String> getItemSelectionGrpList(String grpId) {
		try {
			return vasDetailDao.getItemSelectionGrpList( grpId);
		} catch (DAOException e) {
			logger.error("Exception caught in getItemSelectionGrpList()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public boolean existInSelectionGrpList(String grpId, String[] itemIdList) {
		if (itemIdList != null) {
			List<String> selectedItemIdList = new ArrayList<String>(Arrays.asList(itemIdList));
			return existInSelectionGrpList(grpId, selectedItemIdList) ;
		} else {
			return false;
		}
	}
	
	public boolean existInSelectionGrpList(String grpId, List<String> itemIdList) {
		List<String> selectionVasList = new ArrayList<String>();
		try{
			selectionVasList = vasDetailDao.getItemSelectionGrpList(grpId);
			if (CollectionUtils.isNotEmpty(selectionVasList) && CollectionUtils.isNotEmpty(itemIdList)) {
				return CollectionUtils.containsAny(selectionVasList, itemIdList);
			} else {
				return false;
			}
		}catch(DAOException de){
			logger.error("Exception caught in existInSelectionGrpList()", de);
			throw new AppRuntimeException(de);		
		}
	}
	
	public boolean existInSelectionGrpList(String grpId, String itemId) {
		if (StringUtils.isNotEmpty(itemId)) {
			return existInSelectionGrpList(grpId, Arrays.asList(itemId)) ;
		} else {
			return false;
		}
	}
	
	public boolean isSecretarialItem(String itemId) {
		try {
			return vasDetailDao.isSecretarialItem(itemId);
		} catch (DAOException e) {
			logger.error("Exception caught in getItemSelectionGrpList()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	
			
	public String getOneCardTwoSimInd(String[] selectedItemList) {
		try {
			return vasDetailDao.getOneCardTwoSimInd(selectedItemList);
		} catch (DAOException e) {
			logger.error("Exception caught in getOneCardTwoSimInd()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<VasDetailDTO> getUserSelectedBasketItemList(String locale, String appDate, String basketId,String selectedItemList[], MRTUI mrtUI, PaymentMonthyUI paymentMonthyUI 
	 )
			{
		List<VasDetailDTO> vasList=new ArrayList<VasDetailDTO>();
   	
		try{
			logger.debug("getUserSelectedBasketItemList() is called in VasDetailService service");
			vasList= vasDetailDao.getUserSelectedBasketItemList(locale, appDate, basketId, selectedItemList, mrtUI,paymentMonthyUI);
		}catch (DAOException de) {
			logger.error("Exception caught in getUserSelectedBasketItemList()", de);
			throw new AppRuntimeException(de);
		}
		
		return vasList;
	}
	
	public Double getVasHSAmt(String selectedItemList[], String appDate) {
		try {
			Double result = this.vasDetailDao.getVasHSAmt(selectedItemList, appDate);
			if (result == null) {
				return new Double(0.0);
			} else {
				return result;
			}
		} catch (DAOException e) {
			logger.error("Exception caught in getVasHSAmt()", e);
			throw new AppRuntimeException(e);
		}
	}

	public Double getVasAmt (String selectedItemList[], String appDate) {
		try {
			Double result = this.vasDetailDao.getVasAmt(selectedItemList, appDate);
			if (result == null) {
				return new Double(0.0);
			} else {
				return result;
			}
		} catch (DAOException e) {
			logger.error("Exception caught in getVasAmt()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public boolean isItemsInGroup(String basketId, String[] vasList, Date appDate, String groupId) {
		try {
			return this.vasDetailDao.isItemsInGroup(basketId, vasList, appDate, groupId);
		} catch (DAOException de) {
			logger.error("Exception caught in isItemsInGroup()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public Map<SbItemPreDTO, List<SbItemPreDTO>> checkSbItemPre(List<String> itemIds) {
		if (this.isEmpty(itemIds)) {
			if (logger.isDebugEnabled()) {
				logger.debug("itemIds is empty");
			}
			return Collections.emptyMap();
		}
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("itemIds: " + StringUtils.join(itemIds, ' '));
			}
			List<SbItemPreDTO> list = this.sbItemPreDao.getSbItemPreDTOList(itemIds);
			if (this.isEmpty(list)) {
				if (logger.isDebugEnabled()) {
					logger.debug("getSbItemPreDTOList is empty");
				}
				return Collections.emptyMap();
			}
			Map<SbItemPreDTO, List<SbItemPreDTO>> missingItems = new LinkedHashMap<SbItemPreDTO, List<SbItemPreDTO>>();
			for (SbItemPreDTO dto : list) {
				if (!(itemIds.contains(dto.getSourceItemId()) && itemIds.contains(dto.getTargetItemId()))) {
					List<SbItemPreDTO> sbItemPreList = null;
					if (missingItems.containsKey(dto)) {
						sbItemPreList = missingItems.get(dto);
					} else {
						sbItemPreList = new ArrayList<SbItemPreDTO>();
						missingItems.put(dto, sbItemPreList);
					}
					sbItemPreList.add(dto);
				}
			}
			return missingItems;
		}catch (DAOException de) {
			logger.error("Exception caught in getUserSelectedBasketItemList()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<String> getItemIdByPosItemCd(String posItemCd) {
		if (StringUtils.isBlank(posItemCd)) {
			if (logger.isDebugEnabled()) {
				logger.debug("posItemCd is blank");
			}
			return Collections.emptyList();
		}
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("posItemCd: " + posItemCd);
			}
			return this.vasDetailDao.getItemIdByPosItemCd(posItemCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getItemIdByPosItemCd()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<String> getPosItemCdByItemId(String itemId) {
		if (StringUtils.isBlank(itemId)) {
			if (logger.isDebugEnabled()) {
				logger.debug("itemId is blank");
			}
			return Collections.emptyList();
		}
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("itemId: " + itemId);
			}
			return this.vasDetailDao.getPosItemCdByItemId(itemId);
		} catch (DAOException de) {
			logger.error("Exception caught in getPosItemCdByItemId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	/*public boolean hasNFCSim(String orderId) {
		try {
			return orderDao.hasNFCSim(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	/*public boolean hasOctopusSim(String orderId) {
		try {
			return orderDao.hasOctopusSim(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	/*public boolean isNFCSim(String itemCd) {
		try {
			return orderDao.isNFCSim(itemCd);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isOctopusSim(String itemCd) {
		try {
			return orderDao.isOctopusSim(itemCd);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	
	public boolean isExtraFunctionSim(String itemCd) {
		try{
			String simType  = orderDao.getSimType(itemCd);
			return StringUtils.isNotBlank(simType) && !"0".equals(simType);
		} catch (DAOException de) {
			logger.error("Exception caught in isExtraFunctionSim()", de);
			throw new AppRuntimeException(de);
		}
	
	}
	
	public boolean isExtraFunctionSimByNfcInd(String nfcInd) {
		return StringUtils.isNotBlank(nfcInd) && !"0".equals(nfcInd);
	
	}
	
	
	public boolean hasProductionInfo(String[] vasList) { 
		try {
			return vasDetailDao.hasProductionInfo( vasList);
		} catch (DAOException de) {
			logger.error("Exception caught in hasProductionInfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getHSWaiveType(String basketId, String appDate) {
		try{
			return orderDao.getHSWaiveType(basketId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getHSWaiveType()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public BasketParmDTO getBasketParmByType(String basketId, String parmType) {
		try {
			return vasDetailDao.getBasketParmByType(basketId, parmType);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketParmByType()", de);
			throw new AppRuntimeException(de);
		}
	}
		
	public String getWaiveVasItemCd(String basketId, String appDate, String channelId) {
		try{
			return vasDetailDao.getWaiveVasItemCd(basketId, appDate, channelId);
		} catch (DAOException de) {
			logger.error("Exception caught in getHSWaiveType()", de);
			throw new AppRuntimeException(de);
		}
	}
	public String getSimType(String itemCd) {
		try{
			return orderDao.getSimType(itemCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getSimType()", de);
			throw new AppRuntimeException(de);
		}
	}
	public String getOneTimeChargeAmount(String basketId, String appDate, String channelId) {
		try{
			return orderDao.getOneTimeChargeAmount(basketId, appDate, channelId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOneTimeChargeAmount()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean isOctopusWaived(String basketId, String appDate) {
		try {
			return orderDao.isOctopusWaived(basketId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in isOctopusWaived()", de);
			throw new AppRuntimeException(de);
		}
	}
	public String getSelectedSimExTraFunction(String appDate, String basketId, String itemId) {
		try{
			return vasDetailDao.getSelectedSimExTraFunction(appDate, basketId, itemId);
		} catch (DAOException de) {
			logger.error("Exception caught in getSelectedSimType()", de);
			throw new AppRuntimeException(de);
		}
	}
	public Double getOneTimeChargeAmount(String orderId) {
		try{
			return vasDetailDao.getAdminCharge(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOneTimeChargeAmount()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public Double getMupAdminChargeAmount(String orderId) {
		try{
			return vasDetailDao.getMupAdminChargeAmount(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getMupAdminChargeAmount()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<String> getOverMaxCountItemList(String[] selectedList, String basketId) {
		try {
			return vasDetailDao.getOverMaxCountItemList(selectedList, basketId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOverMaxCountItemList()", de);
			throw new AppRuntimeException(de);
		}
	}

	public String getSimOnlyBasketSimType(String basketId, String appDate) {
		try {
			return vasDetailDao.getSimOnlyBasketSimType(basketId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getSimOnlyBasketSimType()", de);
			throw new AppRuntimeException(de);
		}
	}
	public List<RptVasDetailDTO> getReportUseMultiSimRPHSRPList (String locale, 
			String basketId, String orderId,String memberNum){
		//MultiSim Athena 20140128
		List<RptVasDetailDTO> rphsrpList=new ArrayList<RptVasDetailDTO>();
		try{
			logger.debug("getReportUseMultiSimRPHSRPList() is called in VasDetailService service");
			rphsrpList= vasDetailDao.getReportUseMultiSimRPHSRPList (locale,basketId,orderId,memberNum);
		}catch (DAOException de) {
			logger.error("Exception caught in getReportUseMultiSimRPHSRPList()", de);
			throw new AppRuntimeException(de);
		}
		
		return rphsrpList;
	}
	public List<RptVasDetailDTO> getReportUseMultiSimVasDetailtList (String locale, 
			String basketId, String orderId,String memberNum)
	{	//MultiSim Athena 20140128
		List<RptVasDetailDTO> vasList=new ArrayList<RptVasDetailDTO>();
    	
		try{
			logger.debug("getReportUseMultiSimVasDetailtList() is called in VasDetailService service");
			vasList= vasDetailDao.getReportUseMultiSimVasDetailtList(locale,basketId,orderId,memberNum);
		}catch (DAOException de) {
			logger.error("Exception caught in getReportUseMultiSimVasDetailtList()", de);
			throw new AppRuntimeException(de);
		}
		
		return vasList;
	}
	public List<RptVasDetailDTO> getReportUseMultiSimVasOptionalDetailtList(String locale, 
			String basketId, String orderId,String memberNum)
			{//MultiSim Athena 20140128
		List<RptVasDetailDTO> vasList=new ArrayList<RptVasDetailDTO>();
    	
		try{
			logger.debug("getReportUseMultiSimVasOptionalDetailtList() is called in VasDetailService service");
			vasList= vasDetailDao.getReportUseMultiSimVasOptionalDetailtList(locale,basketId,orderId,memberNum);
		}catch (DAOException de) {
			logger.error("Exception caught in getReportUseMultiSimVasOptionalDetailtList()", de);
			throw new AppRuntimeException(de);
		}
		
		return vasList;
	}
	
	public List<String[]> getOctopusMspItemlist(String basketId, String appDate, String channelId) {
		try{
			return vasDetailDao.getOctopusMspItemList(basketId, appDate, channelId);
		} catch (DAOException de) {
			de.printStackTrace();
			logger.error("Exception caught in getOctopusMspItemlist()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean hasSimInBasket(String basketId,String appDate) {
		try {
			return vasDetailDao.hasSimInBasket(basketId, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in hasSimInBasket()", e);
		}
		return false;
	}

	public boolean isAioSim(String selectedSimItemId){
		boolean isAioSim = false;
        List<CodeLkupDTO> aioSimList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("AIO_SIM_ITEM_CODE");
        List<String> posItemCd = this.getPosItemCdByItemId(selectedSimItemId);
        if (CollectionUtils.isNotEmpty(posItemCd)){
	        String[] arr = posItemCd.toArray(new String[posItemCd.size()]);
	        if (CollectionUtils.isNotEmpty(aioSimList)){
	              for(CodeLkupDTO a: aioSimList){
	            	  if (isExistInString(arr, a.getCodeId())){
	            		  isAioSim = true;
	            	  }
	              }
	              
	        }
        }
        
        return isAioSim;
	}
	
	public boolean isExistAioMspItem(String[] vasItem){
		boolean isExistAioMspItem = false;
        List<CodeLkupDTO> nanoSimAdaptorList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("AIO_MSP_ITEM");

        if (CollectionUtils.isNotEmpty(nanoSimAdaptorList)){
              for(CodeLkupDTO a: nanoSimAdaptorList){
                    if (isExistInString(vasItem, a.getCodeId())){
                    	isExistAioMspItem = true;
                    }
              }
              
        }
        
        return isExistAioMspItem;
	}
	
	private boolean  isExistInString(String stringList[], String a){
		boolean result =false;
		
		for (int i=0; stringList!=null && i<stringList.length; i++ ){
			if (a.equals(stringList[i])){
				result= true;
				break;
			}
		}

		return result;
	}
	
	public List<String> getUnmatchDocAssignedVas(String[] selectedList, String docType, Date appDate) {
		try {
			return vasDetailDao.getUnmatchDocAssignedVas(selectedList, docType, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getUnmatchDocAssignedVas()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<VasDetailDTO> getSystemAssignVas(String vasCodeType, String appDate, String locale, String mipBrand, String mipSimType) {
		try {
			return vasDetailDao.getSystemAssignVas(vasCodeType, appDate, locale, mipBrand, mipSimType);
		} catch (DAOException de) {
			logger.error("Exception caught in getSystemAssignVas()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String checkBasketVas(String basketId, String appDate, String[] vasItemId) {
		try {
			return vasDetailDao.checkBasketVas(basketId, appDate, vasItemId);
		} catch (DAOException e) {
			logger.error("Exception caught in checkBasketVas()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<String> getHsItemIdByBasket(String basketId, Date appDate) {
		logger.info("getHsItemIdByBasket() is called in VasDetailService");
		try {
			return vasDetailDao.getHsItemIdByBasket(basketId, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in getHsItemIdByBasket()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<String> getDummyWaiveRpPrepayItemExist(String basketId, String appDate) {
		try {
			return vasDetailDao.getDummyWaiveRpPrepayItemExist(basketId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getDummyWaiveRpPrepayItemExist()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean isYahooCoupon(String orderId) {
		try {
			return vasDetailDao.isYahooCoupon(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in isYahooCoupon()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean isExistTNGCardItem(String[] vasItem){
		boolean isExistTNGCardItem = false;
        List<CodeLkupDTO> isExistTNGCardItemList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("TNG_CARD");
        List<CodeLkupDTO> isExistTNGSimItemList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("TNG_SIM");
        
        List<CodeLkupDTO> isExistTNGItemList = new ArrayList<CodeLkupDTO>();
        
        if (CollectionUtils.isNotEmpty(isExistTNGCardItemList)){
        	isExistTNGItemList.addAll(isExistTNGCardItemList);
        }
        
        if (CollectionUtils.isNotEmpty(isExistTNGSimItemList)){
        	isExistTNGItemList.addAll(isExistTNGSimItemList);
        }
        
        if (CollectionUtils.isNotEmpty(isExistTNGItemList)){
              for(CodeLkupDTO a: isExistTNGItemList){
                    if (isExistInString(vasItem, a.getCodeId())){
                    	isExistTNGCardItem = true;
                    }
              }
              
        }
        
        return isExistTNGCardItem;
	}
	
	public String getMipSimType (String itemId){ //DENNIS MIP3
	 	
		try{
			logger.debug("getMipSimType() is called in VasDetailService service");
			return  vasDetailDao.getMipSimType(itemId);
		}catch (DAOException de) {
			logger.error("Exception caught in getMipSimType()", de);
			throw new AppRuntimeException(de);
		}
			
	}
	
	public String getBasketHardBundleGrpId(String basketId, String mipBrand, String appDate) {
		try {
			logger.debug("getBasketHardBundleGrpId() is called in VasDetailService service");
			return vasDetailDao.getBasketHardBundleGrpId(basketId, mipBrand, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getHardBundledGrpId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public BasketMinVasLkupDTO getBasketMinVasLkup(String basketId, String appDate) {
		try {
			logger.debug("getBasketMinVasLkup() is called in VasDetailService service");
			return vasDetailDao.getBasketMinVasLkup(basketId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketMinVasLkup()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public ResultDTO basketValidate(String basketId , Date appDate) {
		try {
			logger.debug("basketValidate() is called in VasDetailService service");
			return vasDetailDao.basketValidate(basketId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in basketValidate()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public boolean isUppReport(Date appDate) {
		logger.debug("isUppReport() is called in ReportServiceImpl");
		String uppDateStr = LookupTableBean.getInstance().getCodeLkupList().get("UPP_FORM_TRI").get(0).getCodeId();
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date uppDate = dateFormat.parse(uppDateStr);
			if (uppDate == null) {
				return false;
			} else if (uppDate.equals(appDate) || uppDate.before(appDate)) {
				logger.info("uppDate = " + uppDate + "; appDate = " + appDate);
				return true;
			}
		}catch(ParseException ex){
			return false;
		}
		return false;
	}
	

	public boolean enableUADPlan(Date appDate) {
		logger.info("enableUADPlan() is called in vasDetailService");
		String uadDateStr = LookupTableBean.getInstance().getCodeLkupList().get("UAD_EFF_DATE").get(0).getCodeId();
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date uadDate = dateFormat.parse(uadDateStr);
			if (uadDate == null) {
				return false;
			} else if (uadDate.equals(appDate) || uadDate.before(appDate)) {
				logger.info("uppDate = " + uadDate);
				logger.info("appDate = " + appDate);
				return true;
			}
		}catch(ParseException ex){
			return false;
		}
		return false;
	}
	
	public boolean validJOC(String basketId, List<String> vasList, Date appDate) {
		try {
			logger.debug("VasDetailService@validJOC is called");
			return vasDetailDao.validJOC(basketId, vasList, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught", e);
			return false;
		}
	}
	
	public String getHandsetDescriptionByItemCd(String itemCode){
		try {
			logger.debug("VasDetailService@getHandsetDescriptionByItemCd is called");
			return vasDetailDao.getHandsetDescriptionByItemCd(itemCode);
		} catch (DAOException e) {
			logger.error("Exception caught", e);
			return itemCode;
		}
	}
	
	public List<VasDetailDTO> getOnlineReportUseRPHSRPList (String orderId){
		List<VasDetailDTO> rphsrpList=new ArrayList<VasDetailDTO>();
    	
		try{
			logger.debug("getOnlineReportUseRPHSRPList() is called in VasDetailService service");
			rphsrpList= vasDetailDao.getOnlineReportUseRPHSRPList (orderId);
		}catch (DAOException de) {
			logger.error("Exception caught in getOnlineReportUseRPHSRPList()", de);
			throw new AppRuntimeException(de);
		}
		
		return rphsrpList;
	}
	
	public String getItemIdExistInSelectionGrpList(String grpId, String[] itemIdList) {
		if (itemIdList != null) {
			List<String> selectedItemIdList = new ArrayList<String>(Arrays.asList(itemIdList));
			return getItemIdExistInSelectionGrpList(grpId, selectedItemIdList);
		}
		return null;
	}
	
	public String getItemIdExistInSelectionGrpList(String grpId, List<String> itemIdList) {
		if (CollectionUtils.isNotEmpty(itemIdList)) {
			List<String> selectionGroupItemIdList;
			try {
				selectionGroupItemIdList = vasDetailDao.getItemSelectionGrpList(grpId);
			} catch (DAOException e) {
				logger.debug(e);
				return null;
			}
			if (CollectionUtils.isNotEmpty(itemIdList) && CollectionUtils.isNotEmpty(selectionGroupItemIdList)) {
				Collection<String> resultCollection = CollectionUtils.intersection(itemIdList, selectionGroupItemIdList);
				Iterator<String> resultIterator = resultCollection.iterator();
				while (resultIterator.hasNext()) {
					return resultIterator.next().toString();
				}
			}
		}
		return null;
	}
}
