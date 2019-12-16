package com.bomwebportal.lts.service.order;



import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;

public abstract class SummaryDelBaseServiceImpl extends SummaryBaseServiceImpl {

	protected abstract ServiceDetailLtsDTO getDelService(SbOrderDTO pSbOrder);
	
	public ServiceSummaryDTO buildSummary(SbOrderDTO pSbOrder, String pLocale, boolean pIsEnquiry) {
		
		ServiceDetailLtsDTO delSrvDtl = this.getDelService(pSbOrder); 
		
		if (delSrvDtl == null || StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_DNX_REMOVE, delSrvDtl.getSrvTypeCdAction())
				|| StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_DNY_REMOVE, delSrvDtl.getSrvTypeCdAction())) {
			return null;
		}
		
		//add duplex num change ITEM into delSrvDtl  ----Modified by Max
		ItemDetailLtsDTO[] itemDtls = delSrvDtl.getItemDtls();
		ServiceDetailLtsDTO duplexSrvDtl = LtsSbHelper.getDuplexChangeService(pSbOrder.getSrvDtls());
		
		if(duplexSrvDtl != null){
			if(!StringUtils.equals(delSrvDtl.getToProd(),LtsBackendConstant.LTS_SRV_TYPE_2DEL)){
				itemDtls = (ItemDetailLtsDTO[]) ArrayUtils.addAll(itemDtls, duplexSrvDtl.getItemDtls());	
			}else{
				if(LtsBackendConstant.ORDER_TYPE_SB_UPGRADE.equals(pSbOrder.getOrderType())){
					itemDtls = (ItemDetailLtsDTO[]) ArrayUtils.addAll(itemDtls, duplexSrvDtl.getItemDtls());
				}
			}
		}
		ServiceSummaryDTO srvSummary = new ServiceSummaryDTO();
		
		this.fillLtsSummaryDetail(srvSummary, pSbOrder, delSrvDtl);
		this.fillAccountDetail(srvSummary, delSrvDtl);
		this.fillThirdPartyDetail(srvSummary, delSrvDtl.getThirdPartyDtl());
		this.fillAppoinmentDetail(srvSummary, delSrvDtl.getAppointmentDtl());
		this.fillItemDisplay(pSbOrder, srvSummary, itemDtls, pLocale);
		this.fillContactPeriod(srvSummary);
		
		if (!LtsSbOrderHelper.isOnlineAcquistionOrder(pSbOrder)){
			this.fillSrvStatusState(pSbOrder.getOrderId(), srvSummary, delSrvDtl);
			this.fillSrvStatusStateLts(pSbOrder, srvSummary, delSrvDtl);	
		}
		
		this.fillDuplexDetail(srvSummary, pSbOrder);
		this.fillESignDetail(srvSummary, pSbOrder);
		this.fillCspInfo(srvSummary, pSbOrder);
		this.fillRemarks(srvSummary, delSrvDtl);
		this.fillCustPdpo(srvSummary, pSbOrder);
		this.fillContactDetail(srvSummary, pSbOrder.getContact());
		
		srvSummary.setSrvType(LtsConstant.LTS_SRV_TYPE_2DEL.equals(delSrvDtl.getToSrvType()) ? LtsConstant.LTS_SRV_TYPE_2DEL : LtsConstant.DAT_CD_DEL);
		return srvSummary;
	}
	
	private void fillContactPeriod (ServiceSummaryDTO pSrvSummary) {
		List<ItemDetailSummaryDTO> planItemList = pSrvSummary.getSrvPlanItemList();
		
		for (int i=0; planItemList!=null && i<planItemList.size(); ++i) {
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_PLAN, planItemList.get(i).getItemType())) {
				pSrvSummary.setContractPeriod(StringUtils.defaultIfEmpty(ltsBasketService.getBasketContractPeriod(planItemList.get(i).getBasketId()), "0") + " months");
				break;
			}
		}
	}
	
	private void fillRemarks(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
		pSrvSummary.setAddOnRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
		pSrvSummary.setCustRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_CUST_REMARK));
		pSrvSummary.setOrderRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_ORDER_REMARK));
		pSrvSummary.setEarliestSrdReasonRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_EARLIEST_SRD_REMARK));
		pSrvSummary.setFsRemark(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_2ND_DEL));
	}
}
