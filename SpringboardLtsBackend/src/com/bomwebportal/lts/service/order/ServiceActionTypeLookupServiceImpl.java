package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.lts.dto.ImsPendingOrderDTO;
import com.bomwebportal.lts.dto.order.ChannelDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.EyeImsActionLkupDTO;
import com.bomwebportal.lts.dto.order.ImsActionLkupDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.LtsActionLkupDTO;
import com.bomwebportal.lts.dto.order.OtherActionLkupDTO;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceActionLkupBaseDTO;
import com.bomwebportal.lts.dto.order.ServiceActionTypeDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class ServiceActionTypeLookupServiceImpl implements ServiceActionTypeLookupService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private CodeLkupCacheService ltsActionLkupCache = null;
	private CodeLkupCacheService imsActionLkupCache = null;
	private CodeLkupCacheService otherActionLkupCache = null;
	private CodeLkupCacheService eyeImsSignoffActionLkupCache = null;
	private SbOrderInfoLtsService sbOrderInfoLtsService;
	private CodeLkupCacheService ltsDrgDownTimeCacheService;
	
	
	public ServiceActionTypeDTO[] getServiceActionType(ServiceDetailDTO pSrv, SbOrderDTO pSbOrder) {
		
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		ServiceActionTypeDTO srvActionByLob = this.getServiceActionTypeByLob(pSrv, pSbOrder.getOrderType());
		ServiceActionTypeDTO[] srvActionAllLob = this.getServiceActionTypeAllLob(pSrv, pSbOrder);
		ServiceActionTypeDTO[] srvActionByTv = this.getServiceActionTypeByTv(pSrv);
		ServiceActionTypeDTO[] srvActionDirectSales = this.getDirectSalesServiceActionType(pSbOrder);
		if (srvActionByLob != null) {
			srvActionList.add(srvActionByLob);
		}
		if (!ArrayUtils.isEmpty(srvActionAllLob)) {
			srvActionList.addAll(Arrays.asList(srvActionAllLob));
		}
		if (!ArrayUtils.isEmpty(srvActionByTv)) {
			srvActionList.addAll(Arrays.asList(srvActionByTv));
		}
		if (!ArrayUtils.isEmpty(srvActionDirectSales)){
			srvActionList.addAll(Arrays.asList(srvActionDirectSales));
		}
		
		return srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()]);
	}
	
	@SuppressWarnings("unchecked")
	public ServiceActionTypeDTO[] getServiceActionType(ServiceDetailOtherLtsDTO pImsSrv, String pFromEyeProd, String pToEyeProd) {
		
		EyeImsActionLkupDTO action = new EyeImsActionLkupDTO();
		action.setExistMirror(pImsSrv.getExistMirrorInd());
		action.setExistModemArrangement(pImsSrv.getExistModem());
		action.setFromEyeProd(pFromEyeProd);
		action.setToEyeProd(pToEyeProd);
		action.setFromImsProd(pImsSrv.getFromProd());
		List<ServiceActionLkupBaseDTO> srvActionList = (List<ServiceActionLkupBaseDTO>)this.eyeImsSignoffActionLkupCache.get(action.getLkupKey());
		
		if (srvActionList == null || srvActionList.size() == 0) {
			return null;
		}
		ServiceActionTypeDTO[] srvActions = new ServiceActionTypeDTO[srvActionList.size()];
		
		for (int i=0; i < srvActionList.size(); ++i) {
			srvActions[i] = this.createServiceAction(srvActionList.get(i));
		}
		return srvActions;
	}
	
	public ServiceActionTypeDTO[] getApprovalServiceActionType(SbOrderDTO pSbOrder, ServiceDetailDTO pSrv, String pOrderType) {
		
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		ServiceActionLkupBaseDTO srvAction = null;
		OtherActionLkupDTO action = new OtherActionLkupDTO();
		if (pSrv instanceof ServiceDetailLtsDTO) {
			ServiceDetailLtsDTO srvLts = (ServiceDetailLtsDTO)pSrv;
			ServiceDetailLtsDTO coreEyeService = LtsSbHelper.getLtsEyeService(pSbOrder);
			if (coreEyeService != null) {
				if (!StringUtils.equals(coreEyeService.getDtlId(), srvLts.getDtlId())
						&& LtsBackendConstant.LTS_SRV_TYPE_SIP.equals(srvLts.getFromSrvType())) {
					return srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()]);
				}
			}
			if (LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(pSbOrder.getOrderType())) {
				ServiceCallPlanDetailLtsDTO[] srvCallPlanDtls = pSrv.getSrvCallPlanDtls();
				if(srvCallPlanDtls != null && srvCallPlanDtls.length > 0){
					boolean mktAppvFlag = false;
					boolean smAppvFlag = false;
					boolean umAppvFlag = false;
					for(ServiceCallPlanDetailLtsDTO srvCallPlan: srvCallPlanDtls){
						if(LtsBackendConstant.CALL_PLAN_WAIVE_PEN_MKT_APPV.equals(srvCallPlan.getPenaltyHandling())){
							mktAppvFlag = true;
						}
						if(LtsBackendConstant.CALL_PLAN_WAIVE_PEN_SM_APPV.equals(srvCallPlan.getPenaltyHandling())){
							smAppvFlag = true;
						}
						if(LtsBackendConstant.CALL_PLAN_WAIVE_PEN_UM_APPV.equals(srvCallPlan.getPenaltyHandling())){
							umAppvFlag = true;
						}
					}
					if(mktAppvFlag){
						action.setActionType(LtsBackendConstant.SP_ACTION_FFP_WAIVE_PEN_MKT);
						srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
					}else if(smAppvFlag){
						action.setActionType(LtsBackendConstant.SP_ACTION_FFP_WAIVE_PEN_SM);
						srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
					}else if(umAppvFlag){
						action.setActionType(LtsBackendConstant.SP_ACTION_FFP_WAIVE_PEN_UM);
						srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
					}
				}
			}
			if (StringUtils.equals("NO-PREM", srvLts.getPendingApprovalCd())) {
				if (StringUtils.equals(LtsBackendConstant.GROUP_TYPE_EYE, srvLts.getGrpType())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_APPV_MARKET_UPG);
				} else {
					action.setActionType(LtsBackendConstant.SP_ACTION_APPV_MARKET_2DEL);
				}
				srvAction = (ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey());
				
				if (srvAction != null) {
					srvActionList.add(this.createServiceAction(srvAction));
				}
			}
			if (!StringUtils.equals("Y", srvLts.getApprovedInd())) {
				if (LtsSbHelper.existApprovalItems(srvLts)) {
					if (LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(pSbOrder.getOrderType())) {
						action.setActionType(LtsBackendConstant.SP_ACTION_D_ORDER_WAIVE_TP);
					}
					else {
						action.setActionType(LtsBackendConstant.SP_ACTION_APPV_WAIVE_TP);
					}
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				if (StringUtils.equals(LtsBackendConstant.DISCONNECT_REA_CD_DECEASED, srvLts.getDiscReasonCode())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_DEATH_CASE_NORMAL);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				if (StringUtils.equals(LtsBackendConstant.WAIVE_D_FORM_CD_UM, srvLts.getWaiveDFormReasonCd())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_D_FORM_WAIVE_UM);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				if (StringUtils.equals(LtsBackendConstant.WAIVE_D_FORM_CD_SM, srvLts.getWaiveDFormReasonCd())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_D_FORM_WAIVE_SM);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				if (StringUtils.equals(LtsBackendConstant.LOST_EQUIP_CD_WAIVE_50, srvLts.getLostEquipmentCd())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_LOST_EQUIP_50_UM);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				if (StringUtils.equals(LtsBackendConstant.LOST_EQUIP_CD_WAIVE_70, srvLts.getLostEquipmentCd())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_LOST_EQUIP_70_SM);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				if (StringUtils.equals(LtsBackendConstant.LOST_EQUIP_CD_WAIVE_100_SM, srvLts.getLostEquipmentCd())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_LOST_EQUIP_100_SM);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				if (StringUtils.equals(LtsBackendConstant.LOST_EQUIP_CD_WAIVE_100_M, srvLts.getLostEquipmentCd())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_LOST_EQUIP_100_M);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				if (StringUtils.equals("Y", srvLts.getSwitchPlanInd())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_CHANGE_PLAN);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				
				if (StringUtils.isEmpty(srvLts.getDeceaseType()) && LtsSbHelper.isTerminateOrderLessThan30DaysNotice(pSbOrder)) {
					action.setActionType(LtsBackendConstant.SP_ACTION_CEASE_RENTAL_DATE);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));	
				}
				
				if(LtsSbHelper.existApprovalDnChangeItems(srvLts) && !StringUtils.equals(pSbOrder.getCreateChannel(),"RS")){
					action.setActionType(LtsBackendConstant.SP_ACTION_WAIVE_DNCHANGE);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				
				if (LtsSbHelper.existApprovalFfpItems(srvLts)) {
					action.setActionType(LtsBackendConstant.SP_ACTION_WAIVE_IDD_FFP);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				
				if (StringUtils.equals("Y", pSbOrder.getBackDateInd())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_BACKDATE_APPROVAL);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				
			}
		} 
		if (pSrv instanceof ServiceDetailOtherLtsDTO) {
			ServiceDetailOtherLtsDTO srvOther = (ServiceDetailOtherLtsDTO)pSrv;
			
			if (LtsSbHelper.existApprovalOffers(srvOther)) {
				if (LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(pSbOrder.getOrderType())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_D_ORDER_WAIVE_TP);
				}
				else {
					action.setActionType(LtsBackendConstant.SP_ACTION_APPV_WAIVE_TP);
				}
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			if (LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(pSbOrder.getOrderType())) {
				if(LtsBackendConstant.LOST_MODEM_HAVE_EYE_USAGE.equals(srvOther.getLostModem())){
					action.setActionType(LtsBackendConstant.SP_ACTION_LOST_MODEM_SM);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}else if(LtsBackendConstant.LOST_MODEM_NO_EYE_USAGE.equals(srvOther.getLostModem())){
					action.setActionType(LtsBackendConstant.SP_ACTION_LOST_MODEM_UM);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
			}
		}
		return srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()]);
	}
	
	private ServiceActionTypeDTO getServiceActionTypeByLob(ServiceDetailDTO pSrv, String pOrderType) {
		
		ServiceActionLkupBaseDTO srvAction = null;
		
		if (pSrv instanceof ServiceDetailLtsDTO) {
			LtsActionLkupDTO ltsAction = new LtsActionLkupDTO();
			ltsAction.setFromProd(pSrv.getFromProd());
			ltsAction.setToProd(pSrv.getToProd());
			srvAction = (ServiceActionLkupBaseDTO)this.ltsActionLkupCache.get(ltsAction.getLkupKey());
		} else if (pSrv instanceof ServiceDetailOtherLtsDTO) {
			ImsActionLkupDTO imsAction = new ImsActionLkupDTO();
			imsAction.setFromProd(pSrv.getFromProd());
			imsAction.setToProd(pSrv.getToProd());
			imsAction.setMirror(((ServiceDetailOtherLtsDTO)pSrv).getNowtvMirrorInd());
			imsAction.setModemArrangement(((ServiceDetailOtherLtsDTO)pSrv).getModemArrangement());
			imsAction.setOrderType(pOrderType);
			srvAction = (ServiceActionLkupBaseDTO)this.imsActionLkupCache.get(imsAction.getLkupKey());
		}
		return this.createServiceAction(srvAction);
	}
	
	private ServiceActionTypeDTO[] getServiceActionTypeAllLob(ServiceDetailDTO pSrv, SbOrderDTO pSbOrder) {
		
		ServiceDetailLtsDTO primarySrv = LtsSbHelper.getLtsService(pSbOrder);
		ServiceDetailLtsDTO portLaterSrv = LtsSbHelper.getAcqPortLaterService(pSbOrder);
		boolean isPreInstall = LtsSbHelper.isPreInstall(pSbOrder);
		
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		OtherActionLkupDTO action = new OtherActionLkupDTO();
		ServiceDetailDTO primaryService = LtsSbHelper.getLtsService(pSbOrder);
		if (StringUtils.equals("Y", pSrv.getRecontractInd()) && StringUtils.isEmpty(((ServiceDetailLtsDTO) pSrv).getUpdateDnProfile())) {
			action.setActionType(LtsBackendConstant.SP_ACTION_RECONTRACT);
			srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
		}
		//TODO
		if (pSrv instanceof ServiceDetailLtsDTO) {
			ServiceDetailLtsDTO srvLts = (ServiceDetailLtsDTO)pSrv;
			
			ItemDetailLtsDTO[] itemDetails = srvLts.getItemDtls();
			if (ArrayUtils.isNotEmpty(itemDetails)) {
				for (ItemDetailLtsDTO itemDetail : itemDetails) {
					if (LtsBackendConstant.IO_IND_OUT.equals(itemDetail.getIoInd()) &&
							StringUtils.equalsIgnoreCase("Y", itemDetail.getForceRetain())) {
						action.setActionType(LtsBackendConstant.SP_ACTION_FORCE_RETAIN_OFFER);
						srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
						break;
					}
				}
			}
			
			if (StringUtils.equals("Y", srvLts.getErInd())) {
				if (StringUtils.equals(LtsBackendConstant.GROUP_TYPE_EYE, srvLts.getGrpType())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_LTS_EYE_ER);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				} else {
					action.setActionType(LtsBackendConstant.SP_ACTION_LTS_DEL_ER);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
			}
//			if (StringUtils.equals("Y", srvLts.getCancelVasInd())) {
//				action.setActionType(LtsBackendConstant.SP_ACTION_CANCEL_VAS);
//				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
//			}
			if (StringUtils.equals("Y", srvLts.getFrozenExchInd())) {
				action.setActionType(LtsBackendConstant.SP_ACTION_FROZEN_EXCHANGE);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			if (StringUtils.equals(primaryService.getDtlId(), pSrv.getDtlId())) {
				if((StringUtils.isNotEmpty(srvLts.getNewSrvNum()))
						|| LtsSbHelper.getDuplexChangeService(pSbOrder.getSrvDtls()) != null){
					action.setActionType(LtsBackendConstant.SP_ACTION_DNCHANGE_FULL_WQ);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}	
			}
			if (StringUtils.equals("Y", srvLts.getCallPlanDowngradeInd())) {
				action.setActionType(LtsBackendConstant.SP_ACTION_CALL_PLAN_DOWN);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			if (StringUtils.equals("Y", srvLts.getOneTwoThreeTvInd())) {
				action.setActionType(LtsBackendConstant.SP_ACTION_123_TV);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			if (StringUtils.equals("Y", srvLts.getCustNameNotMatch())) {
				if (StringUtils.isNotBlank(pSrv.getGrpType())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_CUST_NAME_NOT_MATCH_EYE);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				} else {
					action.setActionType(LtsBackendConstant.SP_ACTION_CUST_NAME_NOT_MATCH_DEL);
				}
			}
			//TODO
			if (((ServiceDetailLtsDTO) pSrv).getUpdateDnProfile() != null) {
				action.setActionType(LtsBackendConstant.SP_ACTION_UPDATE_DN_PROFILE);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			
			if(!isPreInstall)
			{
				if(ArrayUtils.isNotEmpty(srvLts.getSrvCallPlanDtls())){
					for(ServiceCallPlanDetailLtsDTO callPlanDtl: srvLts.getSrvCallPlanDtls()){
						if(LtsBackendConstant.CALL_PLAN_TYPE_FREE.equals(callPlanDtl.getPlanType())){
							action.setActionType(LtsBackendConstant.SP_ACTION_VAS_FFP_FREE);
							srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
							break;
						}
					}
				}
			}
			else
			{
				if(ArrayUtils.isNotEmpty(primarySrv.getSrvCallPlanDtls())&&srvLts.getDtlId().equals(portLaterSrv.getDtlId())){
					for(ServiceCallPlanDetailLtsDTO callPlanDtl: primarySrv.getSrvCallPlanDtls()){
						if(LtsBackendConstant.CALL_PLAN_TYPE_FREE.equals(callPlanDtl.getPlanType())){
							action.setActionType(LtsBackendConstant.SP_ACTION_VAS_FFP_FREE);
							srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
							break;
						}
					}
				}
			}

				
			if(LtsBackendConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(pSbOrder.getOrderSubType())
					&& StringUtils.equals(primaryService.getDtlId(), pSrv.getDtlId())
					&& "Y".equals( ((ServiceDetailLtsDTO) pSrv).getFfpOnlyInd())){
				action.setActionType(LtsBackendConstant.SP_ACTION_STANDALONE_VAS_FFP_ONLY);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			else{
				if(!isPreInstall)
				{
					ItemDetailLtsDTO[] items = srvLts.getItemDtls();
					for (int i=0; items!=null && i<items.length; ++i) {
						if (StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, items[i].getIoInd()) && (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_FFP_OTHER, items[i].getItemType()) 
								|| StringUtils.equals(LtsBackendConstant.ITEM_TYPE_FFP_STAFF, items[i].getItemType())
								|| StringUtils.equals(LtsBackendConstant.ITEM_TYPE_FFP_HOT, items[i].getItemType()))) {
							action.setActionType(LtsBackendConstant.SP_ACTION_IN_IDD_FFP);
							srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
							break;
						}
					}
				}
				else
				{
					if(srvLts.getDtlId().equals(portLaterSrv.getDtlId())){
						ItemDetailLtsDTO[] items = primarySrv.getItemDtls();
						for (int i=0; items!=null && i<items.length; ++i) {
							if (StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, items[i].getIoInd()) && (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_FFP_OTHER, items[i].getItemType()) 
									|| StringUtils.equals(LtsBackendConstant.ITEM_TYPE_FFP_STAFF, items[i].getItemType())
									|| StringUtils.equals(LtsBackendConstant.ITEM_TYPE_FFP_HOT, items[i].getItemType()))) {
								action.setActionType(LtsBackendConstant.SP_ACTION_IN_IDD_FFP);
								srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
								break;
							}
						}
					}
				}
			}
			
			ServiceCallPlanDetailLtsDTO[] srvCallPlanDtls = srvLts.getSrvCallPlanDtls();
			
			if (ArrayUtils.isNotEmpty(srvCallPlanDtls)) {
				boolean chgDcaFlag = false;
				boolean outIddFfpFlag = false;
				for (ServiceCallPlanDetailLtsDTO srvCallPlanDtl : srvCallPlanDtls) {
					if (StringUtils.equals(LtsBackendConstant.IO_IND_OUT, srvCallPlanDtl.getIoInd())) {
						outIddFfpFlag = true;
					}

					if("Y".equals(srvCallPlanDtl.getChgDcaInd())){
						chgDcaFlag = true;
					}
					
				}

				if(outIddFfpFlag){
					action.setActionType(LtsBackendConstant.SP_ACTION_OUT_IDD_FFP);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				
				if(chgDcaFlag){
					action.setActionType(LtsBackendConstant.SP_ACTION_FFP_CHG_DCA);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
				
			}
			
		}
		if (pSrv instanceof ServiceDetailOtherLtsDTO) {
			ServiceDetailOtherLtsDTO srvOther = (ServiceDetailOtherLtsDTO)pSrv;
			if (StringUtils.equals("Y", srvOther.getErInd())) {
				action.setActionType(LtsBackendConstant.SP_ACTION_IMS_ER);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			if (StringUtils.isNotEmpty(srvOther.getNewBandwidth()) && !StringUtils.equals(srvOther.getExistBandwidth(), srvOther.getNewBandwidth())) {
				action.setActionType(LtsBackendConstant.SP_ACTION_UPGRADE_PCD);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			if (StringUtils.equals("Y", srvOther.getDeactNowTvInd())) {
				ServiceDetailDTO ltsCoreService = LtsSbHelper.getLtsService(pSbOrder);
				if (!LtsBackendConstant.LTS_PRODUCT_TYPE_EYE_3_A.equalsIgnoreCase(ltsCoreService.getToProd())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_DEACT_NOWTV);
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));	
				}
			}
			if (StringUtils.isNotEmpty(srvOther.getReplaceExistFsa())) {
				action.setActionType(LtsBackendConstant.SP_ACTION_SWITCH_FSA);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			if (StringUtils.isNotBlank(srvOther.getEdfRef())) {
				action.setActionType(LtsBackendConstant.SP_ACTION_EDF);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			if (isTerminateWithPcdService(srvOther, pSbOrder.getOrderType())) {
				action.setActionType(LtsBackendConstant.SP_ACTION_TERMINATE_PCD_SERVICE);
				ServiceActionTypeDTO srvAction = this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey()));
				srvActionList.add(srvAction);
			}
			if (isTerminateWithTVService(srvOther, pSbOrder.getOrderType())) {
				action.setActionType(LtsBackendConstant.SP_ACTION_TERMINATE_TV_SERVICE);
				ServiceActionTypeDTO srvAction = this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey()));
				srvActionList.add(srvAction);
			}
			if (isPendingImsOrderExist(srvOther, pSbOrder.getOrderType())
					&& !isTerminateWithTVService(srvOther, pSbOrder.getOrderType())
					&& !isTerminateWithPcdService(srvOther, pSbOrder.getOrderType())) {
				action.setActionType(LtsBackendConstant.SP_ACTION_PENDING_IMS_ORDER);
				ServiceActionTypeDTO srvAction = this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey()));
				srvActionList.add(srvAction);
			}
			
			if (ArrayUtils.isNotEmpty(srvOther.getOfferDtls())) {
				for (ImsOfferDetailDTO imsOffer : srvOther.getOfferDtls()) {
					if ((LtsBackendConstant.ITEM_TYPE_MIRROR_PLAN.equals(imsOffer.getItemType()) 
							|| LtsBackendConstant.ITEM_TYPE_EXPIRED_MIRROR_PLAN.equals(imsOffer.getItemType())) 
								&& LtsBackendConstant.IO_IND_OUT.equals(imsOffer.getIoInd())) {
						action.setActionType(LtsBackendConstant.SP_ACTION_REMOVE_MIRROR);
						ServiceActionTypeDTO srvAction = this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey()));
						srvActionList.add(srvAction);
						break;
					}
				}
			}			
			if (LtsBackendConstant.IMS_PRODUCT_TYPE_NEW.equals(srvOther.getFromProd())
					&& LtsBackendConstant.IMS_PRODUCT_TYPE_PCD.equals(srvOther.getToProd())) {
				if (StringUtils.isNotBlank(srvOther.getRelatedSbOrderId()) || StringUtils.isNotBlank(srvOther.getEdfRef())) {
					action.setActionType(LtsBackendConstant.SP_ACTION_SHARE_NEW_PCD_SB);
				}
				else {
					action.setActionType(LtsBackendConstant.SP_ACTION_SHARE_NEW_PCD_NON_SB);
				}
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			
			
			ImsActionLkupDTO imsAction = new ImsActionLkupDTO();
			imsAction.setFromProd("*");
			imsAction.setToProd("*");
			imsAction.setMirror(((ServiceDetailOtherLtsDTO)pSrv).getNowtvMirrorInd());
			imsAction.setModemArrangement(((ServiceDetailOtherLtsDTO)pSrv).getModemArrangement());
			imsAction.setOrderType(pSbOrder.getOrderType());
			ServiceActionLkupBaseDTO srvAction = (ServiceActionLkupBaseDTO)this.imsActionLkupCache.get(imsAction.getLkupKey());
			
			if (srvAction != null) {
				srvActionList.add(this.createServiceAction(srvAction));
			}
			
		}
		return srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()]);
	}

	private boolean isPendingImsOrderExist(ServiceDetailDTO imsService, String orderType) {
		if (!LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(orderType)) {
			return false;
		}
		
		List<ImsPendingOrderDTO> imsPendingOrderList = sbOrderInfoLtsService.getSbImsLatestPendingOrderBySrvNum(imsService.getSrvNum());
		
		if (imsPendingOrderList != null && !imsPendingOrderList.isEmpty()) {
			
			// For generate WQ Cover sheet remark.
			imsService.setPendingOcid(imsPendingOrderList.get(0).getOcId());
			((ServiceDetailOtherLtsDTO)imsService).setRelatedSbOrderId(imsPendingOrderList.get(0).getOrderId());
			return true;
		}
		
//		if (StringUtils.isNotEmpty(imsService.getPendingOcid())
//				|| StringUtils.isNotEmpty(((ServiceDetailOtherLtsDTO)imsService).getRelatedSbOrderId())) {
//			return true;
//		}
		return false;
	}
	
	private boolean isTerminateWithPcdService(ServiceDetailDTO imsService, String orderType) {
		
		if (!LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(orderType)) {
			return false;
		}
		
		if (StringUtils.equals("Y", ((ServiceDetailOtherLtsDTO)imsService).getTerminatePcd())) {
			return true;
		}
		return false;
	}

	private boolean isTerminateWithTVService(ServiceDetailDTO imsService, String orderType) {
		
		if (!LtsBackendConstant.ORDER_TYPE_SB_DISCONNECT.equals(orderType)) {
			return false;
		}
		
		if (StringUtils.equals("Y", ((ServiceDetailOtherLtsDTO)imsService).getTerminateTv())) {
			return true;
		}
		return false;
	}
	
	private ServiceActionTypeDTO[] getServiceActionTypeByTv(ServiceDetailDTO pSrv) {
		
		if (!(pSrv instanceof ServiceDetailOtherLtsDTO)) {
			return null;
		}
		ServiceDetailOtherLtsDTO srvOther = (ServiceDetailOtherLtsDTO)pSrv;
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		ChannelDetailLtsDTO[] channels = srvOther.getChannels();
		OtherActionLkupDTO action = null;
		ServiceActionLkupBaseDTO srvAction = null;
		
		for (int i=0; channels!=null && i<channels.length; ++i) {
			action = new OtherActionLkupDTO();
			action.setActionType(LtsBackendConstant.SP_ACTION_TV);
			action.setStatus(channels[i].getChannelId());
			srvAction = (ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey());
						
			if (srvAction != null) {
				srvActionList.add(this.createServiceAction(srvAction));
			}
		}
		return srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()]);
	}
	
	public ServiceActionTypeDTO[] getDirectSalesServiceActionType(SbOrderDTO pSbOrder) {
		
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		OtherActionLkupDTO action = new OtherActionLkupDTO();
		
		if (pSbOrder != null && pSbOrder.getLtsDsOrderInfo() != null && StringUtils.isNotBlank(pSbOrder.getLtsDsOrderInfo().getDsType())) {
			ServiceDetailLtsDTO srvDtl = LtsSbHelper.getLtsService(pSbOrder);
			if(srvDtl != null && srvDtl.getAccount() != null && srvDtl.getAccount().getCustomer() != null){
				CustomerDetailLtsDTO custInfo = srvDtl.getAccount().getCustomer();
				/*If customer name not match case is "approved with different customer"*/
				if("Y".equals(custInfo.getMismatchInd())
						&& LtsBackendConstant.NAME_MISMATCH_APR_CD_APPROVED_WITH_DIFF_CUST.equals(pSbOrder.getLtsDsOrderInfo().getNameMismatchStatus())){
					if(LtsSbHelper.getLtsEyeService(pSbOrder) != null){
						action.setActionType(LtsBackendConstant.SP_ACTION_DIFF_CUST_EYE);
					}else{
						action.setActionType(LtsBackendConstant.SP_ACTION_DIFF_CUST_DEL);
					}
					srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
				}
			}
			
/*			if(isNowDrgDownTime()){
				action.setActionType(LtsBackendConstant.SP_ACTION_DRG_DOWNTIME_INFO);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}
			*/
/*			LtsDsOrderInfoDTO dsInfo = pSbOrder.getLtsDsOrderInfo();
			if(dsInfo != null && "Y".equals(dsInfo.getWaiveQcCd())){				
				action.setActionType(LtsBackendConstant.SP_ACTION_WAIVE_QC);
				srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
			}*/
			
		}
		return srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()]);
	}	
	
	public ServiceActionTypeDTO[] getDsDrgdwntimeActionType(SbOrderDTO pSbOrder) {
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		OtherActionLkupDTO action = new OtherActionLkupDTO();
		if(isNowDrgDownTime()){
			action.setActionType(LtsBackendConstant.SP_ACTION_DRG_DOWNTIME_INFO);
			srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));
		}
		return srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()]);
	}

	public ServiceActionTypeDTO[] getDirectSalesPrepaymentServiceActionType(SbOrderDTO pSbOrder) {
		
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		OtherActionLkupDTO action = new OtherActionLkupDTO();
		
		if (pSbOrder != null) {		
			PrepayLtsDTO[] prepay = pSbOrder.getPrepay();
			if(prepay != null && prepay.length > 0){				
				action.setActionType(LtsBackendConstant.SP_ACTION_AWAIT_PREPAY);				
				ServiceActionTypeDTO srvAction = this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey()));
				
				if(srvAction != null){
					logger.warn("getDirectSalesPrepaymentServiceActionType; WQ Nature ID:"  + srvAction.getWqNatureId());
				}
				else{
					logger.warn("getDirectSalesPrepaymentServiceActionType, ServiceActionTypeDTO information not found! ");
				}
				
				srvActionList.add(srvAction);
			}			
		}
		return srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()]);
	}	

	public ServiceActionTypeDTO[] getSuspendWithPendingServiceActionType(ServiceDetailDTO pSrv) {
		
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		OtherActionLkupDTO action = new OtherActionLkupDTO();
		
		if (StringUtils.isNotEmpty(pSrv.getPendingOcid())) {
			action.setActionType(LtsBackendConstant.SP_ACTION_PEND_ORDER);
			srvActionList.add(this.createServiceAction((ServiceActionLkupBaseDTO)this.otherActionLkupCache.get(action.getLkupKey())));	
		}
		return srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()]);
	}
	
	private ServiceActionTypeDTO createServiceAction(ServiceActionLkupBaseDTO pActionLkup) {
		
		if (pActionLkup == null) {
			return null;
		}
		ServiceActionTypeDTO srvAction = new ServiceActionTypeDTO();
		
		if (pActionLkup.getWqActionLkup() != null) {
			srvAction.setSuspendReaCdBom(pActionLkup.getSuspendBomReasonCd());
			srvAction.setWqNatureId(String.valueOf(pActionLkup.getWqActionLkup().getWqNatureId()));
			srvAction.setWqSubtype(pActionLkup.getWqActionLkup().getWqSubtype());
			srvAction.setWqType(pActionLkup.getWqActionLkup().getWqType());
		}
		if (pActionLkup instanceof ImsActionLkupDTO) {
			srvAction.setOfferGrpId(((ImsActionLkupDTO)pActionLkup).getOfferGrpId());
		}
		return srvAction;
	}
	
	public boolean isNowDrgDownTime(){
		String startTime = (String)ltsDrgDownTimeCacheService.get("START_TIME");
		String endTime = (String)ltsDrgDownTimeCacheService.get("END_TIME");
		
		if(StringUtils.isBlank(startTime)||StringUtils.isBlank(endTime)){
			return false;
		}
		
		String today = LtsDateFormatHelper.getSysDate("dd/MM/yyyy");
		DateTime startDateTime = DateTime.parse(today + " " + startTime , DateTimeFormat.forPattern("dd/MM/yyyy HH:mm"));
		DateTime endDateTime = DateTime.parse(today + " " + endTime , DateTimeFormat.forPattern("dd/MM/yyyy HH:mm"));
		
		if(startDateTime.isBeforeNow() || endDateTime.isAfterNow()){
			return true;
		}
		
		return false;
	}

	public CodeLkupCacheService getLtsActionLkupCache() {
		return ltsActionLkupCache;
	}

	public void setLtsActionLkupCache(CodeLkupCacheService ltsActionLkupCache) {
		this.ltsActionLkupCache = ltsActionLkupCache;
	}

	public CodeLkupCacheService getImsActionLkupCache() {
		return imsActionLkupCache;
	}

	public void setImsActionLkupCache(CodeLkupCacheService imsActionLkupCache) {
		this.imsActionLkupCache = imsActionLkupCache;
	}

	public CodeLkupCacheService getOtherActionLkupCache() {
		return otherActionLkupCache;
	}

	public void setOtherActionLkupCache(CodeLkupCacheService otherActionLkupCache) {
		this.otherActionLkupCache = otherActionLkupCache;
	}

	public CodeLkupCacheService getEyeImsSignoffActionLkupCache() {
		return eyeImsSignoffActionLkupCache;
	}

	public void setEyeImsSignoffActionLkupCache(
			CodeLkupCacheService eyeImsSignoffActionLkupCache) {
		this.eyeImsSignoffActionLkupCache = eyeImsSignoffActionLkupCache;
	}

	public SbOrderInfoLtsService getSbOrderInfoLtsService() {
		return sbOrderInfoLtsService;
	}

	public void setSbOrderInfoLtsService(SbOrderInfoLtsService sbOrderInfoLtsService) {
		this.sbOrderInfoLtsService = sbOrderInfoLtsService;
	}

	public CodeLkupCacheService getLtsDrgDownTimeCacheService() {
		return ltsDrgDownTimeCacheService;
	}

	public void setLtsDrgDownTimeCacheService(
			CodeLkupCacheService ltsDrgDownTimeCacheService) {
		this.ltsDrgDownTimeCacheService = ltsDrgDownTimeCacheService;
	}

}
