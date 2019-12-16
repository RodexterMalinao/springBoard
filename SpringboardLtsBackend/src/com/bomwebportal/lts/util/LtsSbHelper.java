package com.bomwebportal.lts.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerIguardRegDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.order.SignatureLtsDTO;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.spring.SpringApplicationContext;

public class LtsSbHelper extends LtsProfileHelper {
	
	
	public static String getEncryptFilePassword(SbOrderDTO ltsSbOrder) {
		ServiceDetailDTO ltsServiceDetail = getLtsService(ltsSbOrder);
		String password = ltsServiceDetail.getActualDocId();
		if (ltsServiceDetail.getRecontract() != null && StringUtils.equals("Y", ltsServiceDetail.getRecontractInd())) {
			password = ltsServiceDetail.getRecontract().getIdDocNum();
		
		}
		password = StringUtils.substring(password, 0, 6).toUpperCase();
		final String passwordPattern = "^[A-Z0-9]{6}$";
		if (!password.matches(passwordPattern)) {
			return null;
		}
		return password;
	}
	
	public static boolean isSelectedMirrorItem(ServiceDetailLtsDTO serviceDetail) {
		if (serviceDetail != null && !ArrayUtils.isEmpty(serviceDetail.getItemDtls())) {
			for (ItemDetailLtsDTO itemDetail : serviceDetail.getItemDtls()) {
				if (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_NOWTV_MIRR, itemDetail.getItemType())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static ItemDetailLtsDTO getSelectedEpdItem(ServiceDetailLtsDTO serviceDetail){
		
		if (serviceDetail != null && !ArrayUtils.isEmpty(serviceDetail.getItemDtls())) {
			for (ItemDetailLtsDTO itemDetail : serviceDetail.getItemDtls()) {
				if (ArrayUtils.contains(LtsBackendConstant.ITEM_TYPE_ALL_EPD_ITEMS, itemDetail.getItemType()) ) {
					return itemDetail;
				}
			}
		}
		
		return null;
		
	}
	
	public static ItemDetailLtsDTO getServiceSubcItem(ServiceDetailLtsDTO serviceDetail, String itemType) {
		
		ItemDetailLtsDTO[] itemDetails = serviceDetail.getItemDtls();
		
		if (ArrayUtils.isEmpty(itemDetails)) {
			return null;
		}

		for (ItemDetailLtsDTO itemDetail : itemDetails) {
			if (StringUtils.equals(itemType, itemDetail.getItemType())){ 
				return itemDetail;
			}
		}
		return null;
	}
	
	public static ItemDetailLtsDTO getPlanItem(ServiceDetailLtsDTO eyeService) {
		return getServiceSubcItem(eyeService, LtsBackendConstant.ITEM_TYPE_PLAN);
	}
	
	public static boolean isSelectedIddItem(ServiceDetailLtsDTO serviceDetail) {
		if (serviceDetail != null && !ArrayUtils.isEmpty(serviceDetail.getItemDtls())) {
			for (ItemDetailLtsDTO itemDetail : serviceDetail.getItemDtls()) {
				if (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_IDD, itemDetail.getItemType())) {
					return true;
				}
			}
		}
		return false;
	}
	
    //Modified by LTS Max.R.MENG
	public static boolean isInstallFfpItem(SbOrderDTO sbOrder){
		
		String[] ffpItemTypes = {LtsBackendConstant.ITEM_TYPE_FFP_HOT,
				               LtsBackendConstant.ITEM_TYPE_FFP_OTHER,
				               LtsBackendConstant.ITEM_TYPE_FFP_STAFF};
		
		ServiceDetailLtsDTO srvDtl = getLtsService(sbOrder);
		for(ItemDetailLtsDTO item: srvDtl.getItemDtls()){
			if(LtsBackendConstant.IO_IND_INSTALL.equals(item.getIoInd())
					&& ArrayUtils.contains(ffpItemTypes, item.getItemType())){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInstallFreeCallPlanItem(SbOrderDTO sbOrder){
		
		ServiceDetailDTO coreService = getLtsService(sbOrder);
		
		ServiceCallPlanDetailLtsDTO[] srvCallPlans = coreService.getSrvCallPlanDtls();
		
		if (ArrayUtils.isEmpty(srvCallPlans)) {
			return false;
		}
		
		for (ServiceCallPlanDetailLtsDTO srvCallPlan : srvCallPlans) {
			if (LtsBackendConstant.CALL_PLAN_TYPE_FREE.equals(srvCallPlan.getPlanType())) {
				return true;
			}
		}

		return false;
	}
	
	public static byte[] getSignature(SbOrderDTO pSbOrder, String pSignType) {
		
		SignatureLtsDTO[] signatures = pSbOrder.getSignatures();

		for (int i=0; signatures!=null && i<signatures.length; ++i) {
			if (StringUtils.equalsIgnoreCase(signatures[i].getSignType(), pSignType)) {
				return signatures[i].getSignatureBytes();
			}
		}
		return null;
	}
	
	public static boolean isWorkQueueOrder(SbOrderDTO sbOrder) {
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (StringUtils.isNotBlank(serviceDetail.getWorkQueueType())) {
				return true;
			}
		}
		return false;
	}
	//----Modified by Max
	public static String getDuplexNum(SbOrderDTO sbOrder){
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if(serviceDetail instanceof ServiceDetailLtsDTO
					&& StringUtils.equals(LtsBackendConstant.LTS_SRV_TYPE_DNY, ((ServiceDetailLtsDTO) serviceDetail).getToSrvType())
					&& StringUtils.equals(LtsBackendConstant.LTS_SRV_TYPE_DNY, ((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())){
				return serviceDetail.getSrvNum();
			}
		}
		return null;
	}
	//----Modified by Max
	public static String getNewDuplexNum(SbOrderDTO sbOrder){
		
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if(serviceDetail instanceof ServiceDetailLtsDTO
					&& StringUtils.equals(LtsBackendConstant.LTS_SRV_TYPE_DNY, ((ServiceDetailLtsDTO) serviceDetail).getToSrvType())
					&& StringUtils.equals(LtsBackendConstant.LTS_SRV_TYPE_DNY, ((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())){
				return serviceDetail.getNewSrvNum();
			}
		}
		return null;
	}
	
	public static boolean isDisconnectDelService(ServiceDetailDTO serviceDetail) {
		if (serviceDetail instanceof ServiceDetailLtsDTO
				&& LtsBackendConstant.LTS_SRV_TYPE_REMOVE.equals(((ServiceDetailLtsDTO) serviceDetail).getToSrvType())
				&& ( LtsBackendConstant.LTS_SRV_TYPE_DEL.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())
						|| LtsBackendConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType()))
				&& LtsBackendConstant.ORDER_TYPE_DISCONNECT.equals(serviceDetail.getOrderType())) {
			return true;
		}
		return false;
	}
	
	public static ServiceDetailLtsDTO getDisconnectDelService(ServiceDetailDTO[] serviceDetails) {
		if (ArrayUtils.isEmpty(serviceDetails)) {
			return null;
		}
		
		for (ServiceDetailDTO serviceDetail : serviceDetails) {
			if (isDisconnectDelService(serviceDetail)) {
				return (ServiceDetailLtsDTO)serviceDetail;
			}
		}
		return null;
	}
	
	public static boolean isDisconnectEyePeService(ServiceDetailDTO serviceDetail) {
		if (serviceDetail instanceof ServiceDetailLtsDTO
				&& LtsBackendConstant.LTS_SRV_TYPE_REMOVE.equals(((ServiceDetailLtsDTO) serviceDetail).getToSrvType())
				&& LtsBackendConstant.LTS_SRV_TYPE_PE.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())
				&& LtsBackendConstant.ORDER_TYPE_DISCONNECT.equals(serviceDetail.getOrderType())) {
			return true;
		}
		return false;
	}
	
	public static boolean isDisconnectEyeSipService(ServiceDetailDTO serviceDetail) {
	
		if (serviceDetail instanceof ServiceDetailLtsDTO
				&& LtsBackendConstant.LTS_SRV_TYPE_REMOVE.equals(((ServiceDetailLtsDTO) serviceDetail).getToSrvType())
				&& LtsBackendConstant.LTS_SRV_TYPE_SIP.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())
				&& LtsBackendConstant.ORDER_TYPE_DISCONNECT.equals(serviceDetail.getOrderType())) {
			return true;
		}
		
		return false;
	}
	
	public static ServiceDetailLtsDTO getDisconnectSipService(ServiceDetailDTO[] pSrvDtlLts) {

		for (int i=0; i<pSrvDtlLts.length; ++i) {
			if (isDisconnectEyeSipService(pSrvDtlLts[i])) {
				return (ServiceDetailLtsDTO)pSrvDtlLts[i];
			}
		}
		return null;
	}
	
	public static ServiceDetailLtsDTO getEyeSipService(ServiceDetailDTO[] pSrvDtlLts) {
		for (int i=0; i<pSrvDtlLts.length; ++i) {
			if (pSrvDtlLts[i] instanceof ServiceDetailLtsDTO
					&& LtsBackendConstant.LTS_SRV_TYPE_SIP.equals(((ServiceDetailLtsDTO)pSrvDtlLts[i]).getFromSrvType())) {
				
				return (ServiceDetailLtsDTO)pSrvDtlLts[i];
			}
		}
		return null;
	}
	
	public static ServiceDetailLtsDTO getEyePeService(ServiceDetailDTO[] pSrvDtlLts) {
		for (int i=0; i<pSrvDtlLts.length; ++i) {
			if (pSrvDtlLts[i] instanceof ServiceDetailLtsDTO
					&& LtsBackendConstant.LTS_SRV_TYPE_PE.equals(((ServiceDetailLtsDTO)pSrvDtlLts[i]).getFromSrvType())) {
				return (ServiceDetailLtsDTO)pSrvDtlLts[i];
			}
		}
		return null;
	}
	
	public static ServiceDetailLtsDTO getDisconnectEyeService(ServiceDetailDTO[] serviceDetails) {

		if (ArrayUtils.isEmpty(serviceDetails)) {
			return null;
		}
		ServiceDetailLtsDTO eyePeService = null;
		ServiceDetailLtsDTO eyeSipService = null;
		
		for (ServiceDetailDTO serviceDetail : serviceDetails) {
			if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
				continue;
			}
			
			if (isDisconnectEyePeService(serviceDetail)) {
				eyePeService = (ServiceDetailLtsDTO)serviceDetail;
			}
			if (isDisconnectEyeSipService(serviceDetail)) {
				eyeSipService = (ServiceDetailLtsDTO)serviceDetail;
			}
		}
		return eyePeService != null ? eyePeService : eyeSipService;
	}
	
	public static ServiceDetailLtsDTO getRenewalDelService(ServiceDetailDTO[] serviceDetails) {

		if (ArrayUtils.isEmpty(serviceDetails)) {
			return null;
		}
		
		ServiceDetailDTO duplexDnChangeService = getDuplexChangeService(serviceDetails);
		
		for (ServiceDetailDTO serviceDetail : serviceDetails) {
			if (serviceDetail instanceof ServiceDetailLtsDTO
					&& StringUtils.equals(serviceDetail.getToProd(), serviceDetail.getFromProd())
					&& LtsBackendConstant.LTS_PRODUCT_TYPE_DEL.equals(serviceDetail.getFromProd())
					&& LtsBackendConstant.ORDER_TYPE_CHANGE.equals(serviceDetail.getOrderType())) {
				
				if (duplexDnChangeService != null 
						&& StringUtils.equals(serviceDetail.getDtlId(), duplexDnChangeService.getDtlId())) {  //modified by Max LTS
					continue;
				}
				
				return (ServiceDetailLtsDTO)serviceDetail;
			}
		}
		
		return null;
	}
	
	public static ServiceDetailLtsDTO getLtsService(SbOrderDTO sbOrder) {
		ServiceDetailLtsDTO eyeService = getLtsEyeService(sbOrder);
		ServiceDetailLtsDTO delService = getDelServices(sbOrder);
		return eyeService != null ? eyeService : delService;
	}
	
	public static boolean isPortInOrder(SbOrderDTO sbOrder) {
		return getPortInService(sbOrder.getSrvDtls()) != null;
	}
	
	public static ServiceDetailDTO getPortInService(ServiceDetailDTO[] serviceDetails) {
		
		for (ServiceDetailDTO serviceDetail : serviceDetails) {
			
			if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
				continue;
			}
			
			if (LtsBackendConstant.LTS_SRV_TYPE_PORT_IN.equals(((ServiceDetailLtsDTO)serviceDetail).getFromSrvType())
					&& (LtsBackendConstant.LTS_SRV_TYPE_DEL.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())
							|| LtsBackendConstant.LTS_SRV_TYPE_PE.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())
							|| LtsBackendConstant.LTS_SRV_TYPE_PE.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType()))) {
				return serviceDetail;
			}
			
		}
		
		return null;
	}
	
	public static boolean isOnlineAcquistionOrder(SbOrderDTO sbOrder) {
		return StringUtils.startsWith(sbOrder.getOrderId(), "O");
	}
	
	public static boolean isRetentionOrder(SbOrderDTO sbOrder) {
		return StringUtils.startsWith(sbOrder.getOrderType(), LtsBackendConstant.ORDER_TYPE_SB_RETENTION);
	}
	
	public static ServiceDetailLtsDTO getLtsEyeService(SbOrderDTO sbOrder) {
		ServiceDetailDTO[] serviceDetails = sbOrder.getSrvDtls();
		if (LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())) {
			return getDisconnectEyeService(sbOrder.getSrvDtls());
		}
		if (isNewAcquistionOrder(sbOrder)) {
			return getAcqEyeService(sbOrder.getSrvDtls());
		}

		// Return PE service if exist
		ServiceDetailLtsDTO sipService = null;
		for (int i=0; serviceDetails != null && i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO 
					&& StringUtils.isNotEmpty(serviceDetails[i].getGrpType())) {
				if (LtsBackendConstant.LTS_SRV_TYPE_PE.equals(((ServiceDetailLtsDTO) serviceDetails[i]).getFromSrvType())) {
					return (ServiceDetailLtsDTO)serviceDetails[i];
				}
				else {
					sipService = (ServiceDetailLtsDTO)serviceDetails[i];
				}
			}
		}
		
		return sipService;
	}
	
	public static ServiceDetailOtherLtsDTO getImsEyeService(ServiceDetailDTO[] pSrvDtlLts) {
		for (int i=0; i<pSrvDtlLts.length; ++i) {
			if (pSrvDtlLts[i] instanceof ServiceDetailOtherLtsDTO) {
				return (ServiceDetailOtherLtsDTO)pSrvDtlLts[i];
			}
		}
		return null;
	}
	
	public static ServiceDetailLtsDTO getDelServices(SbOrderDTO sbOrder) {
		ServiceDetailDTO[] serviceDetails = sbOrder.getSrvDtls();
		
		if (LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())) {
			return getDisconnectDelService(serviceDetails);
		}
		
		if (LtsBackendConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())) {
			return getRenewalDelService(serviceDetails);
		}
		
		for (int i=0; i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO && StringUtils.isEmpty(serviceDetails[i].getGrpType())
					&& (StringUtils.equals(LtsBackendConstant.LTS_PRODUCT_TYPE_DEL, serviceDetails[i].getToProd()))) {
				return (ServiceDetailLtsDTO)serviceDetails[i];
			}
		}
		return null;
	}
	
	//GET DUPLEX SERVICE----Modified by Max
	public static ServiceDetailLtsDTO getDuplexChangeService(ServiceDetailDTO[] serviceDetails){
		for (int i=0; i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO && StringUtils.isEmpty(serviceDetails[i].getGrpType())
					&& StringUtils.equals(LtsBackendConstant.LTS_SRV_TYPE_DNY, ((ServiceDetailLtsDTO) serviceDetails[i]).getToSrvType())
			        && StringUtils.equals(LtsBackendConstant.LTS_SRV_TYPE_DNY, ((ServiceDetailLtsDTO) serviceDetails[i]).getFromSrvType())
			        && StringUtils.isNotEmpty(serviceDetails[i].getNewSrvNum())) {				
				return (ServiceDetailLtsDTO)serviceDetails[i];
			}
		}
		return null;
	}
	//GET DUPLEX CHANGE SERVICE WHEN THE NUM CHANGE TO 2DEL----Modified by Max LTS
	public static ServiceDetailLtsDTO getUpgradeEyeDuplexSrv(ServiceDetailDTO[] serviceDetails){
		//only return one srvdtl?
		for (int i=0; i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO
			        && StringUtils.isNotEmpty(serviceDetails[i].getNewSrvNum())
			        && !StringUtils.equals(serviceDetails[i].getToProd(), LtsBackendConstant.LTS_SRV_TYPE_2DEL)){				
				return (ServiceDetailLtsDTO)serviceDetails[i];
			}
		}
		return null;
	}
	
	public static ServiceDetailLtsDTO get2ndDelServices(ServiceDetailDTO[] pSrvDtlLts) {
		
		for (int i=0; i<pSrvDtlLts.length; ++i) {
			if (pSrvDtlLts[i] instanceof ServiceDetailLtsDTO && StringUtils.isEmpty(pSrvDtlLts[i].getGrpType())
					&& (StringUtils.equals(LtsBackendConstant.LTS_PRODUCT_TYPE_2DEL, pSrvDtlLts[i].getToProd()))) {
				return (ServiceDetailLtsDTO)pSrvDtlLts[i];
			}
		}
		return null;
	}
	
	public static String getFormatedAndSortedNumList(String pList){
		String list = "";
		String[] param = pList.split("[\\r\\n]+");
		ArrayList<String> items = new ArrayList<String>(); 
		
		for(int i=0; i<param.length; i++){
			if(param[i] != null && !("".equals(param[i]))){
				items.add(param[i]);
			}
		}
		
		Collections.sort(items);
	
		for(int i=0; i<items.size() && 2<items.size(); i++){
			int counter = i;
			
			if((i+1) < items.size()){
				while(((counter+1) < items.size()) && (Integer.parseInt(items.get(counter+1)) - Integer.parseInt(items.get(counter)) == 1)){
					counter++;
				}
				
				if(counter-i < 2){
					list += items.get(i) + ",";
				}
				else{
					list += items.get(i) + "-" + items.get(counter);
					if(counter+1 < items.size()){
						list += ",";
					}
					i=counter;
				}
			}
			else{
				list += items.get(i);
			}
		}
		return list;
	}		
	
	public static ItemDetailLtsDTO[] getItemByType(ItemDetailLtsDTO[] pItems, String pItemType) {
		
		List<ItemDetailLtsDTO> itemList = new ArrayList<ItemDetailLtsDTO>();
		
		for (int i=0; pItems!=null && i<pItems.length; ++i) {
			if (StringUtils.equals(pItemType, pItems[i].getItemType())) {
				itemList.add(pItems[i]);
			}
		}
		return itemList.toArray(new ItemDetailLtsDTO[0]);
	}
	
	public static boolean is2ndDelService(ServiceDetailDTO serviceDetail) {
		if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
			return false;
		}
		if (LtsBackendConstant.LTS_PRODUCT_TYPE_2DEL.equals(((ServiceDetailLtsDTO)serviceDetail).getToProd())) {
			return true;
		}
		return false;
	}
	
	public static ServiceDetailDTO get2ndDelService(SbOrderDTO sbOrder) {
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (is2ndDelService(serviceDetail)) {
				return serviceDetail;
			}
		}
		return null;
	}
	
	public static boolean existApprovalItems(ServiceDetailLtsDTO pSrvLts) {
		
		ItemDetailLtsDTO[] items = pSrvLts.getItemDtls();
		
		for (int i=0; items!=null && i<items.length; ++i) {
			
			if (LtsBackendConstant.ITEM_TYPE_FFP_HOT.equals(items[i].getItemType())
					|| LtsBackendConstant.ITEM_TYPE_FFP_OTHER.equals(items[i].getItemType())
					|| LtsBackendConstant.ITEM_TYPE_FFP_STAFF.equals(items[i].getItemType())
					|| LtsBackendConstant.ITEM_TYPE_DN_CHANGE.equals(items[i].getItemType()) //MODIFIED BY Max LTS
					|| LtsBackendConstant.ITEM_TYPE_DNY_CHANGE.equals(items[i].getItemType())
					|| LtsBackendConstant.ITEM_TYPE_VAS_FFP.equals(items[i].getItemType())) {
				continue;
			}
			
			if (StringUtils.equals(LtsBackendConstant.PENALTY_ACTION_AWAIT_APPROVAL, items[i].getPenaltyHandling())) {
				return true;
			}
		}
		return false;
	}
	
	//existApprovalDnChangeItems
	public static boolean existApprovalDnChangeItems(ServiceDetailLtsDTO pSrvLts){
		ItemDetailLtsDTO[] items = pSrvLts.getItemDtls();
		
	    for (int i=0; items!=null && i<items.length; ++i) {
	    	
			if ((LtsBackendConstant.ITEM_TYPE_DN_CHANGE.equals(items[i].getItemType())
					|| LtsBackendConstant.ITEM_TYPE_DNY_CHANGE.equals(items[i].getItemType())) 
					&& StringUtils.equals(LtsBackendConstant.PENALTY_ACTION_AWAIT_APPROVAL, items[i].getPenaltyHandling())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean existApprovalFfpItems(ServiceDetailLtsDTO pSrvLts) {
		
		ItemDetailLtsDTO[] items = pSrvLts.getItemDtls();
		
		for (int i=0; items!=null && i<items.length; ++i) {
			
			if (LtsBackendConstant.ITEM_TYPE_FFP_HOT.equals(items[i].getItemType())
					|| LtsBackendConstant.ITEM_TYPE_FFP_OTHER.equals(items[i].getItemType())
					|| LtsBackendConstant.ITEM_TYPE_FFP_STAFF.equals(items[i].getItemType())
					|| LtsBackendConstant.ITEM_TYPE_VAS_FFP.equals(items[i].getItemType())) {
				if (StringUtils.equals(LtsBackendConstant.PENALTY_ACTION_AWAIT_APPROVAL, items[i].getPenaltyHandling())) {
					return true;
				}	
			}
		}
		return false;
	}
	
	public static boolean existApprovalOffers(ServiceDetailOtherLtsDTO pSrvIms) {
		
		ImsOfferDetailDTO[] offers = pSrvIms.getOfferDtls();
		
		for (int i=0; offers!=null && i<offers.length; ++i) {
			if (StringUtils.equals(LtsBackendConstant.PENALTY_ACTION_AWAIT_APPROVAL, offers[i].getPenaltyHandling())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isTerminateOrderLessThan30DaysNotice(SbOrderDTO sbOrder) {
		
		if (!LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())) {
			return false; 
		}
		
		ServiceDetailDTO ltsService = getLtsService(sbOrder);
		
		if (ltsService == null || ltsService.getAppointmentDtl() == null) {
			return false;
		}
		
		if (StringUtils.isBlank(ltsService.getAppointmentDtl().getAppntStartTime())) {
			return false;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			Date applDate = new Date();
			Date srDate = sdf.parse(ltsService.getAppointmentDtl().getAppntStartTime());
			
			if (StringUtils.isNotBlank(sbOrder.getAppDate())) {
				applDate = sdf.parse(sbOrder.getAppDate());
			}			
			
			return LtsDateFormatHelper.getDaysBetween(applDate, srDate) < 29;
			
		} catch (ParseException e) {
			throw new AppRuntimeException(e);
		}
		
	}
	
	public static boolean existApprovalCriteria(SbOrderDTO pSbOrder, ServiceDetailLtsDTO pSrvLts) {
		
		if(StringUtils.equals("Y", pSrvLts.getApprovedInd())){
			return false;			
		}
		
		if (StringUtils.isNotEmpty(pSrvLts.getPendingApprovalCd())) {
			return true;
		}
		
		if (StringUtils.equals(pSbOrder.getBackDateInd(), "Y")) {
			return true;
		}
		
		if (StringUtils.equals(LtsBackendConstant.DISCONNECT_REA_CD_DECEASED, pSrvLts.getDiscReasonCode())
				|| StringUtils.equals(LtsBackendConstant.WAIVE_D_FORM_CD_UM, pSrvLts.getWaiveDFormReasonCd())
				|| StringUtils.equals(LtsBackendConstant.WAIVE_D_FORM_CD_SM, pSrvLts.getWaiveDFormReasonCd())
				|| StringUtils.equals(LtsBackendConstant.LOST_EQUIP_CD_WAIVE_50, pSrvLts.getLostEquipmentCd())
				|| StringUtils.equals(LtsBackendConstant.LOST_EQUIP_CD_WAIVE_70, pSrvLts.getLostEquipmentCd())
				|| StringUtils.equals(LtsBackendConstant.LOST_EQUIP_CD_WAIVE_100_SM, pSrvLts.getLostEquipmentCd())
				|| StringUtils.equals(LtsBackendConstant.LOST_EQUIP_CD_WAIVE_100_M, pSrvLts.getLostEquipmentCd())) {
			return true;
		}
		
		if (isTerminateOrderLessThan30DaysNotice(pSbOrder)) {
			return true;
		}
		
		if (LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(pSbOrder.getOrderType())) {
			/*Disconnect lost modem case*/
			ServiceDetailOtherLtsDTO srvIms = getImsEyeService(pSbOrder.getSrvDtls());
			if(srvIms != null && StringUtils.isNotBlank(srvIms.getLostModem())){
				return true;				
			}
			
			/*Disconnect idd fixed plan waive penalty case*/
			ServiceDetailLtsDTO srvLts = getLtsService(pSbOrder);
			if(srvLts != null && srvLts.getSrvCallPlanDtls() != null && srvLts.getSrvCallPlanDtls().length > 0){
				for(ServiceCallPlanDetailLtsDTO srvCallPlan : srvLts.getSrvCallPlanDtls()){
					if(LtsBackendConstant.CALL_PLAN_WAIVE_PEN_MKT_APPV.equals(srvCallPlan.getPenaltyHandling())
							|| LtsBackendConstant.CALL_PLAN_WAIVE_PEN_SM_APPV.equals(srvCallPlan.getPenaltyHandling())
							|| LtsBackendConstant.CALL_PLAN_WAIVE_PEN_UM_APPV.equals(srvCallPlan.getPenaltyHandling())){
						return true;
					}
				}
			}
			
		}
		
		return false;
	}
	
	public static boolean isRecontractOrder(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			if (srvDtls[i].getRecontract() != null) {
				return true;
			}
		}
		return false;
	}
	
	public static BillingAddressLtsDTO getLatestBillingAddress(ServiceDetailLtsDTO pSrvLts) {
		return pSrvLts.getAccount().getBillingAddress();
	}
	
	public static String getRelationshipDesc(String pRelationCd) {
		
		CodeLkupCacheService relationshipCache = SpringApplicationContext.getBean("relationshipCodeLkupCacheService");
		String relationshipDesc = (String)relationshipCache.get(pRelationCd);
		
		if (StringUtils.isBlank(relationshipDesc)) {
			relationshipCache = SpringApplicationContext.getBean("relationshipBrCodeLkupCacheService");
			relationshipDesc = (String)relationshipCache.get(pRelationCd);
		}
		return relationshipDesc;
	}
	
	public static int getPipbMinDay(){
		return Integer.parseInt(getPipbDay(LtsBackendConstant.LKUP_KEY_MINIMUM_PIPB_DAY));
	}
	
	public static String getPipbDay(String pDayCd) {
		
		CodeLkupCacheService pipbDayCache = SpringApplicationContext.getBean("pipbDayLkupCacheService");
		String pipbDay = (String)pipbDayCache.get(pDayCd);
		
		return pipbDay;
	}
	
    public static int getContactInfoMonth() {
		
		CodeLkupCacheService contactInfoPeriodCache = SpringApplicationContext.getBean("contactInfoPeriodCacheService");
		String cntctMth = (String)contactInfoPeriodCache.get(LtsBackendConstant.LKUP_KEY_CONTACT_INFO_MONTH);
		
		return Integer.parseInt(cntctMth);
	}
	
	public static <T> T[] concatArray(T[] a, T[] b) {
		
	    final int a1 = a.length;
	    final int a2 = b.length;
	    @SuppressWarnings("unchecked")
		final T[] result = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), a1 + a2);
	    System.arraycopy(a, 0, result, 0, a1);
	    System.arraycopy(b, 0, result, a1, a2);
	    return result;
	}
	
	public static String removeTag(String pTagString) {
		
		if (pTagString == null) {
			return null;
		}
		return pTagString.replaceAll("\\<.*?>","");
	}
	
	public static boolean isNewAcquistionOrder(SbOrderDTO sbOrder) {
		return LtsBackendConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType());
	}
	
	public static String getDisplaySrvNum(String s) {
    	return StringUtils.isNotBlank(s) && s.length()==12? s.substring(4, 12) : s;
    }
    
	public static String leftPadSrvNum(String s) {
    	return StringUtils.isNotBlank(s)? StringUtils.leftPad(s, 12, "0") : s;
    }
	
	public static ServiceDetailLtsDTO getAcqEyeService(ServiceDetailDTO[] serviceDetails) {
		for (int i=0; i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO && StringUtils.isNotEmpty(serviceDetails[i].getGrpType())
					&& !isAcqPortLaterService(serviceDetails[i])) {
				return (ServiceDetailLtsDTO)serviceDetails[i];
			}
		}
		return null;
	}
	
	public static ServiceDetailLtsDTO getAcqPortLaterService(SbOrderDTO sbOrder) {
		ServiceDetailLtsDTO eyeService = getAcqPortLaterEyeService(sbOrder.getSrvDtls());
		ServiceDetailLtsDTO delService = getAcqPortLaterDelService(sbOrder.getSrvDtls());
		return eyeService != null ? eyeService : delService;
	}
	
	public static ServiceDetailLtsDTO getAcqPortLaterEyeService(ServiceDetailDTO[] serviceDetails) {
		for (int i=0; i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO && StringUtils.isNotEmpty(serviceDetails[i].getGrpType())
					&& isAcqPortLaterService(serviceDetails[i])) {
				return (ServiceDetailLtsDTO)serviceDetails[i];
			}
		}
		return null;		
	}
	
	public static ServiceDetailLtsDTO getAcqPortLaterDelService(ServiceDetailDTO[] serviceDetails) {
		for (int i=0; i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO && StringUtils.isEmpty(serviceDetails[i].getGrpType())
					&& isAcqPortLaterService(serviceDetails[i])) {
				return (ServiceDetailLtsDTO)serviceDetails[i];
			}
		}
		return null;		
	}
	
	public static boolean isAcqPortLaterService(ServiceDetailDTO serviceDetails) {
		return (serviceDetails instanceof ServiceDetailLtsDTO && 
				StringUtils.equals(LtsBackendConstant.LTS_PRODUCT_TYPE_PORT_LATER, 
						serviceDetails.getToProd()))?true:false;		
	}
	
	public static ServiceDetailLtsDTO getAcqPipbService(ServiceDetailDTO[] serviceDetails) {
		for (int i=0; i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO && serviceDetails[i].getPipb()!=null) {
				return (ServiceDetailLtsDTO)serviceDetails[i];
			}			
		}
		return null;		
	}
		
	//MB2016081 TC ++
	
	public static CustomerIguardRegDTO getIguardCareCashService(SbOrderDTO sbOrder) {
			if (sbOrder.getCustIguardReg() !=null) {
				return sbOrder.getCustIguardReg();
			}			
		return null;		
	}
	
	//MB2016081 TC --
	
	public static boolean isValidPipbDay(ServiceDetailDTO[] serviceDetails) {		
		String srdStr = getAcqPipbSRD(serviceDetails);
		if (srdStr!=null) {
			Calendar pDate = Calendar.getInstance();
			Calendar srdDate = DateFormatHelper.getCalenderDateFromDTOFormat(srdStr);
			int minPipbDay = getPipbMinDay();
			pDate.add(Calendar.DATE, minPipbDay);
			pDate.set(pDate.get(Calendar.YEAR), pDate.get(Calendar.MONTH), pDate.get(Calendar.DATE), 0, 0, 0);
			srdDate.set(srdDate.get(Calendar.YEAR), srdDate.get(Calendar.MONTH), srdDate.get(Calendar.DATE), 0, 0, 0);
			if (srdDate.compareTo(pDate)>=0) {
				return true;
			}
		}			
		return false;
	}
	
	public static String getAcqPipbSRD(ServiceDetailDTO[] serviceDetails) {
		for (int i=0; i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO && serviceDetails[i].getPipb()!=null) {
				return serviceDetails[i].getAppointmentDtl().getCutOverStartTime();
			}			
		}
		return null;		
	}
	
	public static boolean isValidPipbAppointment(ServiceDetailDTO[] serviceDetails) {
		for (int i=0; i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO && serviceDetails[i].getPipb()!=null) {
				String startSrdStr = serviceDetails[i].getAppointmentDtl().getAppntStartTime();
				String endSrdStr = serviceDetails[i].getAppointmentDtl().getAppntEndTime();
				if (StringUtils.isNotBlank(startSrdStr) && StringUtils.isNotBlank(endSrdStr)) {
					Calendar startSrd = DateFormatHelper.getCalenderDateFromDTOFormat(startSrdStr);
					Calendar endSrd = DateFormatHelper.getCalenderDateFromDTOFormat(endSrdStr);
					startSrd.add(Calendar.HOUR_OF_DAY, LtsBackendConstant.VALID_PIPB_HOURS);
					if (startSrd.compareTo(endSrd)>0) {
						return true;
					}
				}				
			}
		}
		return false;		
	}
	
	public static boolean isDrgPortOutStatus(String dnStatus) {
		if (StringUtils.isNotBlank(dnStatus)) {
			if(StringUtils.equals(LtsBackendConstant.DRG_DN_STATUS_MAP.get(dnStatus), 
					LtsBackendConstant.DRG_DN_PORT_OUT)) {
				return true;				
			}			
		}
		return false;
	}
	
	public static boolean isPortBackForPipb(ServiceDetailDTO serviceDetails) {
		if (serviceDetails instanceof ServiceDetailLtsDTO) {
			ServiceDetailLtsDTO srvLts = (ServiceDetailLtsDTO)serviceDetails;
			if (StringUtils.equals(LtsBackendConstant.DN_SOURCE_DN_PIPB, srvLts.getDnSource())) {
				return isDrgPortOutStatus(srvLts.getDnStatus());			
			}
		}
		return false;
	}
	
	public static boolean isLtsOrderCancelled(ServiceDetailDTO[] serviceDetails) {
		for (int i=0; i<serviceDetails.length; ++i) {
			if (serviceDetails[i] instanceof ServiceDetailLtsDTO) {
				ServiceDetailLtsDTO srvLts = (ServiceDetailLtsDTO)serviceDetails[i];
				if (StringUtils.equals(LtsBackendConstant.ORDER_STATUS_CANCELLED, srvLts.getSbStatus())) {
					return true;			
				}
			}
		}
		return false;
	}
	public static String checkMustQC(SbOrderDTO pSbOrder) {
		ServiceDetailDTO ltsServiceDetail = getLtsService(pSbOrder);		
		if (ltsServiceDetail!=null) {
			// if over 65 with eye
            if (getLtsEyeService(pSbOrder)!=null && isElderCustomer(pSbOrder.getCustomers()[0])) {
				return LtsBackendConstant.AWAIT_QC_REASON_CD_OF_ELDERLY_APPLICATION;
			}       
            
            // if PE Link with DEL
            if (getLtsEyeService(pSbOrder) == null && "Y".equals(pSbOrder.getLtsDsOrderInfo().getPeLink())) {
                return LtsBackendConstant.AWAIT_QC_REASON_CD_OF_PE_LINK;
            }
            
            // if third party
			if (ltsServiceDetail.getThirdPartyDtl()!=null) {
				return LtsBackendConstant.AWAIT_QC_REASON_CD_OF_THIRD_PARTY;				
			}			
		}
		return null;
	}
	
   public static boolean isElderCustomer(CustomerDetailLtsDTO customerDetailLtsDTO) {
		String dobStr = customerDetailLtsDTO.getDob();
    	if (dobStr!=null) {
			Calendar pDate = Calendar.getInstance();
			Calendar cDob = DateFormatHelper.getCalenderDateFromDTOFormat(dobStr);
			cDob.add(Calendar.YEAR, LtsBackendConstant.AGE_OF_ELDERLY_APPLICATION);
			pDate.set(pDate.get(Calendar.YEAR), pDate.get(Calendar.MONTH), pDate.get(Calendar.DATE), 0, 0, 0);
			cDob.set(cDob.get(Calendar.YEAR), cDob.get(Calendar.MONTH), cDob.get(Calendar.DATE), 0, 0, 0);
			if (cDob.compareTo(pDate)<=0) {
				return true;
			}
		}		
		return false;
	}
    //Check term plan penalty already waived  ---Modified by LTS MAX.R.MENG
    public static boolean isPenaltyWaivedPlan(SbOrderDTO sbOrder) {
	
    	for(ServiceDetailDTO obj: sbOrder.getSrvDtls()){
    		if(obj instanceof ServiceDetailLtsDTO){
    		  ItemDetailLtsDTO[] itemDtls = ((ServiceDetailLtsDTO) obj).getItemDtls();
    		  for(int i = 0; itemDtls!=null && i < itemDtls.length; i++){
    			  if(StringUtils.equals(itemDtls[i].getPenaltyWaiveInd() , LtsBackendConstant.PENALTY_WAIVED_IND)
    					  && (StringUtils.equals(itemDtls[i].getItemType(),LtsBackendConstant.ITEM_LTS_TP)
    					     || StringUtils.equals(itemDtls[i].getItemType(), LtsBackendConstant.ITEM_LTS_VAS))){
    				  return true;
    			  }
    		  }
    		}
    	}
	    return false;
    }
    //check if penalty handling null
    public static boolean isPenaltyHandled(SbOrderDTO sbOrder){
    	
    	for(ServiceDetailDTO obj: sbOrder.getSrvDtls()){
    		if(obj instanceof ServiceDetailLtsDTO){
    		  ItemDetailLtsDTO[] itemDtls = ((ServiceDetailLtsDTO) obj).getItemDtls();
    		  for(int i = 0; itemDtls!=null && i < itemDtls.length; i++){
    			  if(itemDtls[i].getPenaltyHandling() !=null){
    				  return true;
    			  }
    		  }
    		}
    	}
    	return false;
    }

    /* Assume tentative but not blacklist case = shortage case*/
    public static boolean isEyeResourceShortage(SbOrderDTO sbOrder){
    	ServiceDetailLtsDTO eyeSrv = getLtsEyeService(sbOrder);
    	if(eyeSrv == null){
    		return false;
    	}
    	
    	String[] blacklistReaCds = {"02", "07", "08"}; 
    	if(StringUtils.equals("Y", eyeSrv.getAppointmentDtl().getTidInd())
    			&& !Arrays.asList(blacklistReaCds).contains(eyeSrv.getSuggestSrdReasonCd()) ){
    		return true;
    	}
    	
    	return false;
    }
    
    public static boolean isPcdBundleFree(SbOrderDTO sbOrder) {
		String pcdBundleFree = "";
		
		if(sbOrder != null && sbOrder.getSrvDtls() != null)
		{
			ServiceDetailDTO[] tempSrvDtls = sbOrder.getSrvDtls();
			for (int i=0; i<tempSrvDtls.length; i++)
			{
				if(tempSrvDtls[i] instanceof ServiceDetailLtsDTO)
				{
					ServiceDetailLtsDTO tempSrvLtsDtls = (ServiceDetailLtsDTO) tempSrvDtls[i];					
					if(tempSrvLtsDtls.getItemDtls() != null)
					{
						ItemDetailLtsDTO[] tempItemDetails = tempSrvLtsDtls.getItemDtls();
						for (int j=0; j<tempItemDetails.length; j++)
						{
							if(tempItemDetails[j].getItemType().equalsIgnoreCase("PLAN"))
							{
								if(tempItemDetails[j].getPcdBundleFree()!=null && !tempItemDetails[j].getPcdBundleFree().equals(""))
								{
									pcdBundleFree = tempItemDetails[j].getPcdBundleFree();
								}
							}							
						}
					}
				}
			}				
		}
		
		return pcdBundleFree!=null&&pcdBundleFree.equalsIgnoreCase("Y")?true:false;		
	}
	
    public static String getPcdSbid(SbOrderDTO sbOrder) {
		String pcdSbid = "";
		
		if(sbOrder != null && sbOrder.getSrvDtls() != null)
		{
			ServiceDetailDTO[] tempSrvDtls = sbOrder.getSrvDtls();
			for (int i=0; i<tempSrvDtls.length; i++)
			{
				if(tempSrvDtls[i] instanceof ServiceDetailLtsDTO)
				{
					ServiceDetailLtsDTO tempSrvLtsDtls = (ServiceDetailLtsDTO) tempSrvDtls[i];					
					if(tempSrvLtsDtls.getItemDtls() != null)
					{
						ItemDetailLtsDTO[] tempItemDetails = tempSrvLtsDtls.getItemDtls();
						for (int j=0; j<tempItemDetails.length; j++)
						{
							if(tempItemDetails[j].getItemType().equalsIgnoreCase("PLAN"))
							{
								if(tempItemDetails[j].getBundlePcdOrderId()!=null && !tempItemDetails[j].getBundlePcdOrderId().equals(""))
								{
									pcdSbid = tempItemDetails[j].getBundlePcdOrderId();
								}
							}							
						}
					}
				}
			}				
		}
		
		return pcdSbid;		
	}
    
    public static boolean isPreInstall(SbOrderDTO sbOrder) {
    	boolean isPreIn = false;
		
		if(sbOrder != null && sbOrder.getSrvDtls() != null)
		{
			ServiceDetailDTO[] tempSrvDtls = sbOrder.getSrvDtls();
			for (int i=0; i<tempSrvDtls.length; i++)
			{
				if(tempSrvDtls[i] instanceof ServiceDetailLtsDTO)
				{
					ServiceDetailLtsDTO tempSrvLtsDtls = (ServiceDetailLtsDTO) tempSrvDtls[i];					
					if(tempSrvLtsDtls.getItemDtls() != null)
					{
						ItemDetailLtsDTO[] tempItemDetails = tempSrvLtsDtls.getItemDtls();
						for (int j=0; j<tempItemDetails.length; j++)
						{
							if(tempItemDetails[j].getItemType().equalsIgnoreCase(LtsBackendConstant.ITEM_TYPE_VAS_PRE_INSTALL))
							{
								isPreIn = true;
							}							
						}
					}
				}
			}				
		}
		
		return isPreIn;		
	}
	
    /* return null if not lost modem case*/
	public static String getLostModemIndIfTrue(SbOrderDTO sbOrder){
		ServiceDetailOtherLtsDTO imsService = getImsEyeService(sbOrder.getSrvDtls());
		if(imsService != null && StringUtils.isNotBlank(imsService.getLostModem())
				&& !StringUtils.equals("N", imsService.getLostModem())){
			return imsService.getLostModem();
		}
		return null;
	}
	
	public static boolean checkLastUpdDate(String lastUpdDate) {		

		if (lastUpdDate!=null) {
			Calendar pDate = Calendar.getInstance();
			Calendar pLastUpdDate = DateFormatHelper.getCalenderDateFromDTOFormat(lastUpdDate);
//			pDate.add(Calendar.MONTH, -3);
			pDate.add(Calendar.MONTH, getContactInfoMonth());
			pDate.set(pDate.get(Calendar.YEAR), pDate.get(Calendar.MONTH), pDate.get(Calendar.DATE), 0, 0, 0);
			pLastUpdDate.set(pLastUpdDate.get(Calendar.YEAR), pLastUpdDate.get(Calendar.MONTH), pLastUpdDate.get(Calendar.DATE), 0, 0, 0);
			//if more than 12 months
			if (pLastUpdDate.compareTo(pDate)<=0) {
				return true;
			}
		}			
		return false;
	}
	

	/** Assumption: 
	 	1. Floor is mandatory for comparison.
	 	2. Output String[0] = flat and String[1] = floor
	 	3. Trim leading 0 for flat and floor  
	 */
	public static List<String[]> getAddrCombinationPattern(String flat, String floor) {
		
		
		List<String[]> resultList = new ArrayList<String[]>();
		String trimFloor = StringUtils.removeStart(floor, "0");
		
		if (StringUtils.isBlank(flat)) {
			resultList.add(new String[]{"", trimFloor});
			return resultList;
		}
		
		String trimFlat = StringUtils.removeStart(flat, "0");
		resultList.add(new String[]{trimFlat, trimFloor});
		resultList.add(new String[]{trimFloor + trimFlat, trimFloor});
		resultList.add(new String[]{trimFloor + "0" + trimFlat, trimFloor});
		
		if (StringUtils.startsWith(trimFlat, trimFloor)) {
			String subStrFlat = StringUtils.removeStart(trimFlat, trimFloor);
			String trimSubStrFlat = StringUtils.removeStart(subStrFlat, "0");
			resultList.add(new String[]{trimSubStrFlat, trimFloor});
		}
		
		return resultList;
	}
	
}
