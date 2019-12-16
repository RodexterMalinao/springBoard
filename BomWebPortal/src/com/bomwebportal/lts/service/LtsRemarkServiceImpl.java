package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.CodeLkupDAO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.DeviceDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.order.RemarkLtsServiceImpl;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSbRemarkHelper;
import com.bomwebportal.lts.util.disconnect.LtsSbTerminateRemarkHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsRemarkServiceImpl implements LtsRemarkService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	protected RemarkLtsServiceImpl remarkLtsService;
	protected CodeLkupCacheService relationshipCodeLkupCacheService;
	private CodeLkupCacheService relationshipBrCodeLkupCacheService;
	protected LtsOfferService ltsOfferService;
	protected LtsDeviceService ltsDeviceService;
	protected CodeLkupCacheService remarkTemplateCacheService;
	protected LtsBasketService ltsBasketService;
	private LostModemService lostModemService;
	
	public RemarkLtsServiceImpl getRemarkLtsService() {
		return remarkLtsService;
	}

	public void setRemarkLtsService(RemarkLtsServiceImpl remarkLtsService) {
		this.remarkLtsService = remarkLtsService;
	}

	public CodeLkupCacheService getRelationshipBrCodeLkupCacheService() {
		return relationshipBrCodeLkupCacheService;
	}

	public void setRelationshipBrCodeLkupCacheService(
			CodeLkupCacheService relationshipBrCodeLkupCacheService) {
		this.relationshipBrCodeLkupCacheService = relationshipBrCodeLkupCacheService;
	}

	public CodeLkupCacheService getRelationshipCodeLkupCacheService() {
		return relationshipCodeLkupCacheService;
	}

	public void setRelationshipCodeLkupCacheService(
			CodeLkupCacheService relationshipCodeLkupCacheService) {
		this.relationshipCodeLkupCacheService = relationshipCodeLkupCacheService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public LtsDeviceService getLtsDeviceService() {
		return ltsDeviceService;
	}

	public void setLtsDeviceService(LtsDeviceService ltsDeviceService) {
		this.ltsDeviceService = ltsDeviceService;
	}

	public CodeLkupCacheService getRemarkTemplateCacheService() {
		return remarkTemplateCacheService;
	}

	public void setRemarkTemplateCacheService(
			CodeLkupCacheService remarkTemplateCacheService) {
		this.remarkTemplateCacheService = remarkTemplateCacheService;
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	private String getThirdPartyRelationship(ServiceDetailDTO upgradeServiceDetail) {
		
		if (upgradeServiceDetail.getThirdPartyDtl() == null) {
			return null;
		}
		
		return LtsSbOrderHelper.getRelationshipDesc(upgradeServiceDetail.getThirdPartyDtl().getRelationshipCode());
		
	}
	
	
	private String getRecontractRelationship(ServiceDetailDTO upgradeServiceDetail) {
		if (upgradeServiceDetail.getRecontract() == null) {
			return null;
		}
		
		return LtsSbOrderHelper.getRelationshipDesc(upgradeServiceDetail.getRecontract().getRelationship());
		
	}
	
	//BOM2018061
	private ItemDetailDTO getEpdItem(ServiceDetailLtsDTO srvDtl){
		ItemDetailLtsDTO epdOrdItem = LtsSbOrderHelper.getSelectedEpdItem(srvDtl);
		if(epdOrdItem == null){
			return null;
		}
			
		List<ItemDetailDTO> itemDetailList = ltsOfferService.getItemWithAttb(
				new String[] { epdOrdItem.getItemId() },
				LtsConstant.DISPLAY_TYPE_ITEM_SELECT,
				LtsConstant.LOCALE_ENG, false);
		
		if(itemDetailList == null || itemDetailList.size() == 0){
			return null;
		}
		
		ItemDetailDTO epdItem = itemDetailList.get(0);
		if(epdItem.getItemAttbs() != null && epdItem.getItemAttbs().length > 0){
			for(ItemAttbDTO attb : epdItem.getItemAttbs()){
				for(ItemAttributeDetailLtsDTO ordAttb: epdOrdItem.getItemAttbs()){
					if(StringUtils.equals(attb.getAttbID(), ordAttb.getAttbCd())){
						attb.setAttbValue(ordAttb.getAttbValue());
						break;
					}
				}
			}
		}
		return epdItem;
	}
	
	private String getPrepayment(ServiceDetailLtsDTO upgradeServiceDetail) {
		for (ItemDetailLtsDTO itemDetail : upgradeServiceDetail.getItemDtls()) {
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_PREPAY, itemDetail.getItemType())) {
				List<ItemDetailDTO> itemDetailList = ltsOfferService.getItem(
						new String[] { itemDetail.getItemId() },
						LtsConstant.DISPLAY_TYPE_ITEM_SELECT,
						LtsConstant.LOCALE_ENG, false);
				
				if (itemDetailList != null && !itemDetailList.isEmpty()) {
					return itemDetailList.get(0).getOneTimeAmtTxt();
				}
			}
		}
		return null;
	}
	
	public void generateUpgradeCustomerRemark(SbOrderDTO sbOrder, String pUser) {
		
		ServiceDetailDTO upgradeServiceDetail =LtsSbOrderHelper.getLtsService(sbOrder);
		
		BasketDetailDTO selectedBasket = getSelectedBasketDetail(sbOrder); 
		DeviceDetailDTO selectedDevice = ltsDeviceService.getEyeDevice(selectedBasket.getType(), LtsConstant.LOCALE_ENG);
		RemarkDetailLtsDTO[] ltsCustomerRemarks = LtsSbRemarkHelper.createRemarkDetails(LtsSbRemarkHelper.generateLtsCustomerRemark(sbOrder, 
				selectedDevice, selectedBasket, getThirdPartyRelationship(upgradeServiceDetail), getRecontractRelationship(upgradeServiceDetail)), 
				LtsConstant.REMARK_TYPE_CUST_REMARK);
		
		if (ArrayUtils.isNotEmpty(ltsCustomerRemarks)) {
			upgradeServiceDetail.appendRemarks(ltsCustomerRemarks);
			for (RemarkDetailLtsDTO remarkDetailLts : ltsCustomerRemarks) {
				remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), upgradeServiceDetail.getDtlId());	

			}
		}
		
		ServiceDetailLtsDTO secondDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
		if (secondDelServiceDetail != null) {
			ItemDetailDTO secDelTermPlanItem = getSecondDelPlanItemDetail(secondDelServiceDetail);
			RemarkDetailLtsDTO[] secDelCustomerRemarks = LtsSbRemarkHelper.createRemarkDetails(LtsSbRemarkHelper.generateSecondDelCustomerRemark(sbOrder, 
					secDelTermPlanItem, getThirdPartyRelationship(upgradeServiceDetail)), 
					LtsConstant.REMARK_TYPE_CUST_REMARK);
			if (ArrayUtils.isNotEmpty(secDelCustomerRemarks)) {
				secondDelServiceDetail.appendRemarks(secDelCustomerRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : secDelCustomerRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), secondDelServiceDetail.getDtlId());	
				}
			}
		}
	}
	
	public void generateUpgradeOrderRemark(SbOrderDTO sbOrder, String pUser) {
		ServiceDetailLtsDTO upgradeServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);;
		ServiceDetailDTO imsServiceDetail = LtsSbOrderHelper.getImsService(sbOrder);
		ServiceDetailDTO disconnectServiceDetail = LtsSbOrderHelper.getDisconnectService(sbOrder);

		BasketDetailDTO selectedBasket = getSelectedBasketDetail(sbOrder); 
		DeviceDetailDTO selectedDevice = ltsDeviceService.getEyeDevice(selectedBasket.getType(), LtsConstant.LOCALE_ENG);

		LookupItemDTO[] remarkTemplates = null;
		try {
			CodeLkupDAO remarkTemplateDao = remarkTemplateCacheService.getCodeLkupDAO();
			
			if (remarkTemplateDao != null) {
				remarkTemplates = remarkTemplateDao.getCodeLkup();
			}	
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
		
		
		RemarkDetailLtsDTO[] ltsOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(LtsSbRemarkHelper.generateLtsOrderRemark(sbOrder, 
				selectedDevice, selectedBasket, getPrepayment(upgradeServiceDetail), getThirdPartyRelationship(upgradeServiceDetail), 
				remarkTemplates, false, getRecontractRelationship(upgradeServiceDetail), getManualWaivedItems(upgradeServiceDetail), getEpdItem(upgradeServiceDetail)), 
				LtsConstant.REMARK_TYPE_ORDER_REMARK);

		if (ArrayUtils.isNotEmpty(ltsOrderRemarks)) {
			upgradeServiceDetail.appendRemarks(ltsOrderRemarks);
			for (RemarkDetailLtsDTO remarkDetailLts : ltsOrderRemarks) {
				remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), upgradeServiceDetail.getDtlId());	
			}
		}
		
		if(disconnectServiceDetail != null 
				&& StringUtils.equals(((ServiceDetailLtsDTO)disconnectServiceDetail).getFromSrvType(), LtsConstant.LTS_SRV_TYPE_SIP)){
			RemarkDetailLtsDTO[] ltsDisconnectRemarks = LtsSbRemarkHelper.createRemarkDetails(LtsSbRemarkHelper.generateLtsOrderRemark(sbOrder, 
					selectedDevice, selectedBasket, getPrepayment(upgradeServiceDetail), getThirdPartyRelationship(upgradeServiceDetail), 
					remarkTemplates, true, getRecontractRelationship(upgradeServiceDetail), getManualWaivedItems(upgradeServiceDetail), getEpdItem(upgradeServiceDetail)
					), 
					LtsConstant.REMARK_TYPE_ORDER_REMARK);
			
			if (ArrayUtils.isNotEmpty(ltsDisconnectRemarks)) {
				for (RemarkDetailLtsDTO remarkDetailLts : ltsDisconnectRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), disconnectServiceDetail.getDtlId());	
				}
			}
		}
		if (imsServiceDetail != null && 
				! LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())) {
			RemarkDetailLtsDTO[] imsOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(LtsSbRemarkHelper.generateImsOrderRemark(sbOrder, selectedDevice, getImsManualWaivedItems(imsServiceDetail)), 
					LtsConstant.REMARK_TYPE_ORDER_REMARK);

			if (ArrayUtils.isNotEmpty(imsOrderRemarks)) {
				imsServiceDetail.appendRemarks(imsOrderRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : imsOrderRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), imsServiceDetail.getDtlId());

				}
			}
	
		}
				
		ServiceDetailLtsDTO secondDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
		if (secondDelServiceDetail != null) {
			RemarkDetailLtsDTO[] secDelOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(LtsSbRemarkHelper
					.generateSecondDelOrderRemark(sbOrder, getSecondDelPlanItemDetail(secondDelServiceDetail),
							getThirdPartyRelationship(secondDelServiceDetail)),
					LtsConstant.REMARK_TYPE_ORDER_REMARK);
			
			if (ArrayUtils.isNotEmpty(secDelOrderRemarks)) {
				secondDelServiceDetail.appendRemarks(secDelOrderRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : secDelOrderRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), secondDelServiceDetail.getDtlId());

				}
			}
		}
	}
	
	public void generateRenewalOrderRemark(SbOrderDTO sbOrder, String pUser) {
		ServiceDetailOtherLtsDTO imsServiceDetail = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		BasketDetailDTO selectedBasket = getSelectedBasketDetail(sbOrder); 
		DeviceDetailDTO selectedDevice = ltsDeviceService.getEyeDevice(selectedBasket.getType(), LtsConstant.LOCALE_ENG);

		if (imsServiceDetail != null 
				&& ( StringUtils.equals("Y", imsServiceDetail.getAutoUpgraded())
						|| StringUtils.equals("Y", imsServiceDetail.getShareRentalRouter()))) {
			RemarkDetailLtsDTO[] imsOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(LtsSbRemarkHelper.generateImsOrderRemark(sbOrder, selectedDevice, getImsManualWaivedItems(imsServiceDetail)), 
					LtsConstant.REMARK_TYPE_ORDER_REMARK);

			if (ArrayUtils.isNotEmpty(imsOrderRemarks)) {
				imsServiceDetail.appendRemarks(imsOrderRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : imsOrderRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), imsServiceDetail.getDtlId());

				}
			}
	
		}
		
	}
	
	public void generateDisconnectOrderRemark(SbOrderDTO sbOrder, String pUser) {
		
		ServiceDetailDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailDTO imsServiceDetail = LtsSbOrderHelper.getImsService(sbOrder);
		ServiceDetailLtsDTO sipRemoveServiceDetail = LtsSbOrderHelper.getDisconnectSipService(sbOrder.getSrvDtls());

		String approverName = "-";
		List<String[]> approverInfos = lostModemService.getLostModemApproverName(sbOrder.getOrderId());
		if(approverInfos != null && approverInfos.size() > 0){
			String[] approverInfo = approverInfos.get(0);
			if(approverInfo != null){
				approverName = approverInfo[1] + " (" + approverInfo[0] + ")";
			}
		}

		RemarkDetailLtsDTO[] ltsOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(
				LtsSbTerminateRemarkHelper.generateLtsDisconnetOrderRemark(sbOrder, approverName), LtsConstant.REMARK_TYPE_ORDER_REMARK);

		if (ArrayUtils.isNotEmpty(ltsOrderRemarks)) {
			ltsServiceDetail.appendRemarks(ltsOrderRemarks);
			for (RemarkDetailLtsDTO remarkDetailLts : ltsOrderRemarks) {
				remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), ltsServiceDetail.getDtlId());	
			}
		}
		
		if (imsServiceDetail != null) {
//			RemarkDetailLtsDTO[] imsOrderRemarks = LtsSbRemarkHelper.createRemarkDetails("ORDER CREATE BY " + pUser, LtsConstant.REMARK_TYPE_ORDER_REMARK);
//			if(LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN.equals(imsServiceDetail.getFromProd())){
//				imsOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(
//						"ORDER CREATE BY " + pUser + "\n"
//						+ LtsSbTerminateRemarkHelper.generateLtsDisconnetOrderImsRemark(sbOrder), LtsConstant.REMARK_TYPE_ORDER_REMARK);
//			}
			
			RemarkDetailLtsDTO[] imsOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(
					"ORDER CREATE BY " + pUser + "\n"
					+ LtsSbTerminateRemarkHelper.generateLtsDisconnetOrderImsRemark(sbOrder, approverName), LtsConstant.REMARK_TYPE_ORDER_REMARK);
			
			if (ArrayUtils.isNotEmpty(imsOrderRemarks)) {
				imsServiceDetail.appendRemarks(imsOrderRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : imsOrderRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), imsServiceDetail.getDtlId());	
				}
			}
		}
		
		
		if(sipRemoveServiceDetail != null 
				&& !StringUtils.equals(ltsServiceDetail.getDtlId(), sipRemoveServiceDetail.getDtlId())){
			if (ArrayUtils.isNotEmpty(ltsOrderRemarks)) {
				sipRemoveServiceDetail.appendRemarks(ltsOrderRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : ltsOrderRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), sipRemoveServiceDetail.getDtlId());	
				}
			}
		}
		
	}

	public void generateOrderRemark(SbOrderDTO sbOrder, String pUser) {
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())) {
			generateUpgradeOrderRemark(sbOrder, pUser);
		}
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())) {
			generateUpgradeOrderRemark(sbOrder, pUser);
			generateRenewalOrderRemark(sbOrder, pUser);
		}
		if (LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())) {
			generateDisconnectOrderRemark(sbOrder, pUser);
		}
		if (LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType())) {
			generateNewAcquisitionOrderRemark(sbOrder, pUser);
		}
	}
	
	public void generateCustomerRemark(SbOrderDTO sbOrder, String pUser) {
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())) {
			generateUpgradeCustomerRemark(sbOrder, pUser);
		}
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())
				&& !LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(sbOrder.getOrderSubType())) {
			generateUpgradeCustomerRemark(sbOrder, pUser);
		}
		if (LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())) {
//			generateUpgradeCustomerRemark(sbOrder, pUser);
		}
		if (LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType())) {
			generateNewAcquisitionCustomerRemark(sbOrder, pUser);
		}
	}
	
	
	private BasketDetailDTO getSelectedBasketDetail(SbOrderDTO sbOrder) {
		
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		String displayType = null;
			
		if (LtsSbHelper.isOnlineAcquistionOrder(sbOrder)) {
			displayType = LtsConstant.DISPLAY_TYPE_ONLINE_DESC;
		}
		else {
			displayType = LtsConstant.DISPLAY_TYPE_RP_PROMOT;
		}
		
		for (ItemDetailLtsDTO itemDetail : ltsServiceDetail.getItemDtls()) {
			if (StringUtils.isNotBlank(itemDetail.getBasketId())) {
				return ltsBasketService.getBasket(itemDetail.getBasketId(), LtsConstant.LOCALE_ENG, displayType);
			}
		}
		return null;
	}
	
	private ItemDetailDTO getSecondDelPlanItemDetail(ServiceDetailLtsDTO secondDelServiceDetail) {
		ItemDetailLtsDTO secDelPlanItemDetailLts = LtsSbOrderHelper.getSecDelTermPlanItem(secondDelServiceDetail);
		if (secDelPlanItemDetailLts != null) {
			List<ItemDetailDTO> secDelPlanItemList = ltsOfferService
					.getItem(new String[] { secDelPlanItemDetailLts
							.getItemId() },
							LtsConstant.DISPLAY_TYPE_ITEM_SELECT,
							LtsConstant.LOCALE_ENG, true);
			
			if (secDelPlanItemList != null && !secDelPlanItemList.isEmpty()) {
				return secDelPlanItemList.get(0);
			}
		}
		
		return null;
	}
	
	private ItemDetailDTO[] getManualWaivedItems(ServiceDetailDTO serviceDetail) {
		ItemDetailLtsDTO[] itemDetails = null; 
		if (serviceDetail instanceof ServiceDetailLtsDTO) {
			itemDetails = ((ServiceDetailLtsDTO)serviceDetail).getItemDtls();
		}
		
		if (ArrayUtils.isEmpty(itemDetails)) {
			return null;
		}
		List<ItemDetailDTO> manualWaivedItemList = new ArrayList<ItemDetailDTO>(); 
			
		for (ItemDetailLtsDTO itemDetail : itemDetails) {
			if (LtsConstant.OFFER_HANDLE_MANUAL_WAIVE.equals(itemDetail.getPenaltyHandling())) {
				ItemDetailDTO manualWaiveItem = ltsOfferService.getTpVasItemDetail(itemDetail.getItemId(), LtsConstant.LOCALE_ENG) ;
				if (manualWaiveItem != null ) {
					manualWaivedItemList.add(manualWaiveItem);
				}
			}
		}
		
		if (manualWaivedItemList.isEmpty()) {
			return null;
		}
		
		return (ItemDetailDTO[]) manualWaivedItemList.toArray(new ItemDetailDTO[0]);
	}
	
	
	private ItemDetailDTO[] getImsManualWaivedItems(ServiceDetailDTO serviceDetail) {
		ImsOfferDetailDTO[] imsOfferDetails = ((ServiceDetailOtherLtsDTO)serviceDetail).getOfferDtls();
		
		if (ArrayUtils.isEmpty(imsOfferDetails)) {
			return null;
		}
		List<ItemDetailDTO> manualWaivedItemList = new ArrayList<ItemDetailDTO>(); 
			
		for (ImsOfferDetailDTO imsOfferDetail : imsOfferDetails) {
			if (LtsConstant.OFFER_HANDLE_MANUAL_WAIVE.equals(imsOfferDetail.getPenaltyHandling())) {
				List<ItemDetailDTO> itemDetailList = ltsOfferService.getItem(new String[]{imsOfferDetail.getItemId()}, LtsConstant.DISPLAY_TYPE_ITEM_SELECT, LtsConstant.LOCALE_ENG, false);
				if (itemDetailList != null && !itemDetailList.isEmpty()) {
					manualWaivedItemList.add(itemDetailList.get(0));
				}
			}
		}
		
		if (manualWaivedItemList.isEmpty()) {
			return null;
		}
		
		return (ItemDetailDTO[]) manualWaivedItemList.toArray(new ItemDetailDTO[0]);
	}
	
    public void generateNewAcquisitionCustomerRemark(SbOrderDTO sbOrder, String pUser) {
    	ServiceDetailLtsDTO serviceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		BasketDetailDTO selectedBasket = getSelectedBasketDetail(sbOrder); 
		DeviceDetailDTO selectedDevice = ltsDeviceService.getEyeDevice(selectedBasket.getType(), LtsConstant.LOCALE_ENG);
		
		String remarks = LtsSbRemarkHelper.generateLtsCustomerRemark(sbOrder, selectedDevice, selectedBasket, 
				getThirdPartyRelationship(serviceDetail), getRecontractRelationship(serviceDetail));
		
		if (StringUtils.equals(LtsConstant.DN_SOURCE_DN_PIPB, serviceDetail.getDnSource())
				&& !LtsSbHelper.isPortBackForPipb(serviceDetail)) {
			remarks = LtsSbRemarkHelper.generateAcqPipbCustomerRemark(sbOrder, serviceDetail) + "\n" + remarks;
		}
		
		RemarkDetailLtsDTO[] ltsCustomerRemarks = LtsSbRemarkHelper.createRemarkDetails(remarks, LtsConstant.REMARK_TYPE_CUST_REMARK);
		
		if (ArrayUtils.isNotEmpty(ltsCustomerRemarks)) {
			serviceDetail.appendRemarks(ltsCustomerRemarks);
			for (RemarkDetailLtsDTO remarkDetailLts : ltsCustomerRemarks) {
				remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), serviceDetail.getDtlId());	

			}
		}
		
		// 2DEL customer remarks
		ServiceDetailLtsDTO secondDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
		if (secondDelServiceDetail != null) {
			ItemDetailDTO secDelTermPlanItem = getSecondDelPlanItemDetail(secondDelServiceDetail);
			
			remarks = LtsSbRemarkHelper.generateSecondDelCustomerRemark(sbOrder, secDelTermPlanItem, getThirdPartyRelationship(serviceDetail));
			
			if (StringUtils.equals(LtsConstant.DN_SOURCE_DN_PIPB, secondDelServiceDetail.getDnSource())
					&& !LtsSbHelper.isPortBackForPipb(secondDelServiceDetail)) {
				remarks = LtsSbRemarkHelper.generateAcqPipbCustomerRemark(sbOrder, secondDelServiceDetail) + "\n" + remarks;
			}
			
			RemarkDetailLtsDTO[] secDelCustomerRemarks = LtsSbRemarkHelper.createRemarkDetails(remarks, LtsConstant.REMARK_TYPE_CUST_REMARK);
			if (ArrayUtils.isNotEmpty(secDelCustomerRemarks)) {
				secondDelServiceDetail.appendRemarks(secDelCustomerRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : secDelCustomerRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), secondDelServiceDetail.getDtlId());	
				}
			}
		}
		
		// Port-Later customer remarks
		ServiceDetailLtsDTO portLaterSrv = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
		if (portLaterSrv!=null && !LtsSbHelper.isPortBackForPipb(portLaterSrv)) {								
			remarks = LtsSbRemarkHelper.generateAcqPipbCustomerRemark(sbOrder, portLaterSrv);							
			RemarkDetailLtsDTO[] portLaterltsCustomerRemarks = LtsSbRemarkHelper.createRemarkDetails(remarks, LtsConstant.REMARK_TYPE_CUST_REMARK);
			if (ArrayUtils.isNotEmpty(portLaterltsCustomerRemarks)) {
				portLaterSrv.appendRemarks(portLaterltsCustomerRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : portLaterltsCustomerRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), portLaterSrv.getDtlId());	
				}
			}
		}    	
	}
    
    public void generateNewAcquisitionOrderRemark(SbOrderDTO sbOrder, String pUser) {
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);;
		ServiceDetailDTO imsServiceDetail = LtsSbOrderHelper.getImsService(sbOrder);

		BasketDetailDTO selectedBasket = getSelectedBasketDetail(sbOrder); 
		DeviceDetailDTO selectedDevice = StringUtils.isNotEmpty(ltsServiceDetail.getGrpType())?
				ltsDeviceService.getDevice(selectedBasket.getType(), 
						getAcqSelectedBasketChannel(sbOrder), LtsConstant.LOCALE_ENG) : null;

		LookupItemDTO[] remarkTemplates = null;
		try {
			CodeLkupDAO remarkTemplateDao = remarkTemplateCacheService.getCodeLkupDAO();
			
			if (remarkTemplateDao != null) {
				remarkTemplates = remarkTemplateDao.getCodeLkup();
			}	
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
		
		// LTS order remarks
		String remarks = LtsSbRemarkHelper.generateLtsOrderRemark(sbOrder, selectedDevice, selectedBasket, 
				getPrepayment(ltsServiceDetail), getThirdPartyRelationship(ltsServiceDetail), remarkTemplates, 
				false, getRecontractRelationship(ltsServiceDetail), getManualWaivedItems(ltsServiceDetail), getEpdItem(ltsServiceDetail));		
		
		if (StringUtils.equals(LtsConstant.DN_SOURCE_DN_PIPB, ltsServiceDetail.getDnSource())) {
			remarks += "\n" + LtsSbRemarkHelper.generateAcqPipbOrderRemark(sbOrder, ltsServiceDetail);
		}		
		
		RemarkDetailLtsDTO[] ltsOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(remarks, LtsConstant.REMARK_TYPE_ORDER_REMARK);
     	if (ArrayUtils.isNotEmpty(ltsOrderRemarks)) {
			ltsServiceDetail.appendRemarks(ltsOrderRemarks);
			for (RemarkDetailLtsDTO remarkDetailLts : ltsOrderRemarks) {
				remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), ltsServiceDetail.getDtlId());	
			}
		}
		
		// IMS order remarks
		if (imsServiceDetail != null) {
			RemarkDetailLtsDTO[] imsOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(LtsSbRemarkHelper.generateImsOrderRemark(sbOrder, selectedDevice, getImsManualWaivedItems(imsServiceDetail)), 
					LtsConstant.REMARK_TYPE_ORDER_REMARK);

			if (ArrayUtils.isNotEmpty(imsOrderRemarks)) {
				imsServiceDetail.appendRemarks(imsOrderRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : imsOrderRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), imsServiceDetail.getDtlId());

				}
			}
	
		}
				
		// Second DEL remarks
		ServiceDetailLtsDTO secondDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
		if (secondDelServiceDetail != null) {
			remarks = LtsSbRemarkHelper.generateSecondDelOrderRemark(sbOrder, 
					getSecondDelPlanItemDetail(secondDelServiceDetail), getThirdPartyRelationship(secondDelServiceDetail));
			
			if (StringUtils.equals(LtsConstant.DN_SOURCE_DN_PIPB, secondDelServiceDetail.getDnSource())) {
				remarks += "\n" + LtsSbRemarkHelper.generateAcqPipbOrderRemark(sbOrder, secondDelServiceDetail);
			}
			
			RemarkDetailLtsDTO[] secDelOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(remarks, LtsConstant.REMARK_TYPE_ORDER_REMARK);			
			if (ArrayUtils.isNotEmpty(secDelOrderRemarks)) {
				secondDelServiceDetail.appendRemarks(secDelOrderRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : secDelOrderRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), secondDelServiceDetail.getDtlId());

				}
			}
		}
		
		// Porter-Later remarks
		ServiceDetailLtsDTO portLaterSrv = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
		if (portLaterSrv!=null) {			
			remarks = LtsSbRemarkHelper.generateAcqPortLaterLtsAddOnRemark(sbOrder);			
			RemarkDetailLtsDTO[] portLaterLtsAddOnRemarks = LtsSbRemarkHelper.createRemarkDetails(remarks, LtsConstant.REMARK_TYPE_ADD_ON_REMARK);
			if (ArrayUtils.isNotEmpty(portLaterLtsAddOnRemarks)) {
				portLaterSrv.appendRemarks(portLaterLtsAddOnRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : portLaterLtsAddOnRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), portLaterSrv.getDtlId());	
				}
			}			
			remarks = LtsSbRemarkHelper.generateAcqPortLaterLtsOrderRemark(sbOrder, selectedDevice, selectedBasket, 
					getPrepayment(ltsServiceDetail), getThirdPartyRelationship(ltsServiceDetail), remarkTemplates, 
					false, getRecontractRelationship(ltsServiceDetail), getManualWaivedItems(ltsServiceDetail));			
			remarks += "\n" + LtsSbRemarkHelper.generateAcqPipbOrderRemark(sbOrder, portLaterSrv);			
			RemarkDetailLtsDTO[] portLaterLtsOrderRemarks = LtsSbRemarkHelper.createRemarkDetails(remarks, LtsConstant.REMARK_TYPE_ORDER_REMARK);
			if (ArrayUtils.isNotEmpty(portLaterLtsOrderRemarks)) {
				portLaterSrv.appendRemarks(portLaterLtsOrderRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : portLaterLtsOrderRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), portLaterSrv.getDtlId());	
				}
			}
		}
		
	}
	
	private String getAcqSelectedBasketChannel(SbOrderDTO sbOrder) {
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		return StringUtils.isEmpty(ltsServiceDetail.getGrpType()) ? 
				StringUtils.isEmpty(sbOrder.getAddress().getAddrInventory().getHktPremier()) ? 
						LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_MASS_DEL : LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_PT_DEL
				: StringUtils.isEmpty(sbOrder.getAddress().getAddrInventory().getHktPremier()) ? 
						LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_MASS_EYE : LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_PT_EYE;					
	}

	public LostModemService getLostModemService() {
		return lostModemService;
	}

	public void setLostModemService(LostModemService lostModemService) {
		this.lostModemService = lostModemService;
	}	
	
}
