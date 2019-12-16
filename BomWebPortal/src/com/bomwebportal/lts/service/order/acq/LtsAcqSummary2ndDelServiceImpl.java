package com.bomwebportal.lts.service.order.acq;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;

public class LtsAcqSummary2ndDelServiceImpl extends LtsAcqSummaryBaseServiceImpl {

	public LtsAcqServiceSummaryDTO buildSummary(SbOrderDTO pSbOrder, String pLocale, boolean pIsEnquiry) {
		
		ServiceDetailLtsDTO delSrvDtl = LtsSbOrderHelper.get2ndDelService(pSbOrder);
		
		if (delSrvDtl == null || StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_DNX_REMOVE, delSrvDtl.getSrvTypeCdAction())
				|| StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_DNY_REMOVE, delSrvDtl.getSrvTypeCdAction())) {
			return null;
		}
		LtsAcqServiceSummaryDTO srvSummary = new LtsAcqServiceSummaryDTO();
		
/*		AccountServiceAssignLtsDTO[] accts = new AccountServiceAssignLtsDTO[delSrvDtl.getAccounts().length];
		accts = delSrvDtl.getAccounts().clone();*/
		
        List<AccountServiceAssignLtsDTO> acctList = new ArrayList<AccountServiceAssignLtsDTO>();
		
		for (AccountServiceAssignLtsDTO acctDtl: delSrvDtl.getAccounts()){
			if (StringUtils.equals(acctDtl.getChrgType(), "R")){
				acctList.add(acctDtl);
			}
		}
		
		for (AccountServiceAssignLtsDTO acctDtl: delSrvDtl.getAccounts()){
			if (StringUtils.equals(acctDtl.getChrgType(), "I") && !StringUtils.equals(acctList.get(0).getAccount().getAcctNo(), acctDtl.getAccount().getAcctNo())){
				acctList.add(acctDtl);
			}	
		}
		
		AccountServiceAssignLtsDTO[] accts = acctList.toArray(new AccountServiceAssignLtsDTO[acctList.size()]);
		
		this.fillLtsSummaryDetail(srvSummary, pSbOrder, delSrvDtl);
		this.fillAccountDetail(srvSummary, delSrvDtl, accts);
		this.fillThirdPartyDetail(srvSummary, delSrvDtl.getThirdPartyDtl());
		this.fillAppoinmentDetail(srvSummary, delSrvDtl.getAppointmentDtl(), pSbOrder);
		this.fillItemDisplay(pSbOrder, srvSummary, delSrvDtl.getItemDtls(), pLocale);
		this.fillContactPeriod(srvSummary);
		
		if (!LtsSbOrderHelper.isOnlineAcquistionOrder(pSbOrder)){
			this.fillSrvStatusState(pSbOrder.getOrderId(), srvSummary, delSrvDtl);
			this.fillSrvStatusStateLts(srvSummary, delSrvDtl);	
		}
		
		this.fillRemarks(srvSummary, delSrvDtl);
		this.fillCspInfo(srvSummary, pSbOrder);
		srvSummary.setSrvType("2DEL");
		return srvSummary;
	}
	
	private void fillContactPeriod (LtsAcqServiceSummaryDTO pSrvSummary) {
		
		List<ItemDetailSummaryDTO> planItemList = pSrvSummary.getSrvPlanItemList();
		
		for (int i=0; planItemList!=null && i<planItemList.size(); ++i) {
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_VAS_2DEL_HOT, planItemList.get(i).getItemType())) {
				pSrvSummary.setContractPeriod(this.ltsOfferService.getItemContractPeriod(planItemList.get(i).getItemId()) + " months");
				break;
			}
		}
	}
	
	private void fillRemarks(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
		pSrvSummary.setAddOnRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
		pSrvSummary.setCustRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_CUST_REMARK));
		pSrvSummary.setOrderRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_ORDER_REMARK));
		pSrvSummary.setEarliestSrdReasonRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_EARLIEST_SRD_REMARK));
		pSrvSummary.setFsRemark(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_2ND_DEL));
	}
}
