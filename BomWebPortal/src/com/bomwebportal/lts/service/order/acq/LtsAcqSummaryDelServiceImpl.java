package com.bomwebportal.lts.service.order.acq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.CollectDocDto;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsAcqSummaryDelServiceImpl extends LtsAcqSummaryBaseServiceImpl {

	private LtsOrderDocumentService ltsOrderDocumentService = null;
	private CodeLkupCacheService ltsDocTypeLkupCacheService = null;
	private CodeLkupCacheService ltsWaiveReasonCacheService = null;
	
	public LtsAcqServiceSummaryDTO buildSummary(SbOrderDTO pSbOrder, String pLocale, boolean pIsEnquiry) {
		
		ServiceDetailLtsDTO delSrvDtl = LtsSbOrderHelper.getDelServices(pSbOrder);
		
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
		this.fillESignDetail(srvSummary, pSbOrder);
		this.fillCspInfo(srvSummary, pSbOrder);
		this.fillPipbDetail(srvSummary, pSbOrder);
		srvSummary.setSrvType(LtsConstant.DAT_CD_DEL);
		return srvSummary;
	}
	
	private void fillPipbDetail(LtsAcqServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder) {
		
		ServiceDetailLtsDTO pipbSrvDtlLts = LtsSbOrderHelper.getAcqPipbService(pSbOrder.getSrvDtls());
		
		if(pipbSrvDtlLts!=null){
			pSrvSummary.setPipb(true);
			pSrvSummary.setPipbDn(LtsSbOrderHelper.getDisplaySrvNum(pipbSrvDtlLts.getSrvNum()));
			pSrvSummary.setPipbOperator2n(pipbSrvDtlLts.getPipb().getOperator2n());
			pSrvSummary.setPipbReuseExSocketInd(pipbSrvDtlLts.getPipb().getReuseExSocketInd());
			pSrvSummary.setPipbReuseExSocketType(pipbSrvDtlLts.getPipb().getReuseSocketType());
			pSrvSummary.setPipbWaiveDnChangeInd(pipbSrvDtlLts.getPipb().getWaiveDnChangeInd());
			pSrvSummary.setPipbFromDiffCustInd(pipbSrvDtlLts.getPipb().getFromDiffCustInd());
			pSrvSummary.setPipbIdDocType(pipbSrvDtlLts.getPipb().getIdDocType());
			pSrvSummary.setPipbIdDocNum(pipbSrvDtlLts.getPipb().getIdDocNum());
			pSrvSummary.setPipbCompanyName(pipbSrvDtlLts.getPipb().getCompanyName());
			pSrvSummary.setPipbTitle(pipbSrvDtlLts.getPipb().getTitle());
			StringBuilder nameSb = new StringBuilder();
			    if (StringUtils.isNotEmpty(pipbSrvDtlLts.getPipb().getLastName())) {
				    nameSb.append(pipbSrvDtlLts.getPipb().getLastName());
				    nameSb.append(" ");
			    }
			    if (StringUtils.isNotEmpty(pipbSrvDtlLts.getPipb().getFirstName())) {
				    nameSb.append(pipbSrvDtlLts.getPipb().getFirstName());
			    }
			pSrvSummary.setPipbName(nameSb.toString());
			pSrvSummary.setPipbFromDiffAddrInd(pipbSrvDtlLts.getPipb().getFromDiffAddrInd());
//			StringBuilder addrSb = new StringBuilder();
//		    if (StringUtils.isNotEmpty(pipbSrvDtlLts.getPipb().getAddrLine1())) {
//			    addrSb.append(pipbSrvDtlLts.getPipb().getAddrLine1());
//		        addrSb.append("<br>");
//		    }
//		    if (StringUtils.isNotEmpty(pipbSrvDtlLts.getPipb().getAddrLine2())) {
//			    addrSb.append(pipbSrvDtlLts.getPipb().getAddrLine2());
//			    addrSb.append("<br>");
//		    }
//		    if (StringUtils.isNotEmpty(pipbSrvDtlLts.getPipb().getAddrLine3())) {
//			    addrSb.append(pipbSrvDtlLts.getPipb().getAddrLine3());
//			    addrSb.append("<br>");
//		    }
//		    if (StringUtils.isNotEmpty(pipbSrvDtlLts.getPipb().getAddrLine4())) {
//			    addrSb.append(pipbSrvDtlLts.getPipb().getAddrLine4());
//			    addrSb.append("<br>");
//	    	}
//		    if (StringUtils.isNotEmpty(pipbSrvDtlLts.getPipb().getAddrLine5())) {
//			    addrSb.append(pipbSrvDtlLts.getPipb().getAddrLine5());
//			    addrSb.append("<br>");
//		    }
//		    if (StringUtils.isNotEmpty(pipbSrvDtlLts.getPipb().getAddrLine6())) {
//			    addrSb.append(pipbSrvDtlLts.getPipb().getAddrLine6());
//		    }
//		    pSrvSummary.setPipbAddr(addrSb.toString()); 
//			AddressDetailLtsDTO pipbAddr = new AddressDetailLtsDTO();
//			if (StringUtils.isNotBlank(pipbSrvDtlLts.getPipb().getUnitNo())){
//				pipbAddr.setUnitNo(pipbSrvDtlLts.getPipb().getUnitNo());
//			}
//			if (StringUtils.isNotBlank(pipbSrvDtlLts.getPipb().getFloorNo())){
//				pipbAddr.setFloorNo(pipbSrvDtlLts.getPipb().getFloorNo());
//			}
//			if (StringUtils.isNotBlank(pipbSrvDtlLts.getPipb().getBuildNo())){
//				pipbAddr.setBuildNo(pipbSrvDtlLts.getPipb().getBuildNo());
//			}
//			if (StringUtils.isNotBlank(pipbSrvDtlLts.getPipb().getHiLotNo())){
//				pipbAddr.setHiLotNo(pipbSrvDtlLts.getPipb().getHiLotNo());
//			}
//			if (StringUtils.isNotBlank(pipbSrvDtlLts.getPipb().getSectDesc())){
//				pipbAddr.setSectDesc(pipbSrvDtlLts.getPipb().getSectDesc());
//			}
//			if (StringUtils.isNotBlank(pipbSrvDtlLts.getPipb().getStrNo())){
//				pipbAddr.setStrNo(pipbSrvDtlLts.getPipb().getStrNo());
//			}
//			if (StringUtils.isNotBlank(pipbSrvDtlLts.getPipb().getStrName())){
//				pipbAddr.setStrName(pipbSrvDtlLts.getPipb().getStrName());
//			}
//			if (StringUtils.isNotBlank(pipbSrvDtlLts.getPipb().getStrCatDesc())){
//				pipbAddr.setStrCatDesc(pipbSrvDtlLts.getPipb().getStrCatDesc());
//			}
//			if (StringUtils.isNotBlank(pipbSrvDtlLts.getPipb().getDistDesc())){
//				pipbAddr.setDistDesc(pipbSrvDtlLts.getPipb().getDistDesc());
//			}
//			if (StringUtils.isNotBlank(pipbSrvDtlLts.getPipb().getAreaDesc())){
//				pipbAddr.setAreaDesc(pipbSrvDtlLts.getPipb().getAreaDesc());
//			}
			if(StringUtils.equals("Y", (pipbSrvDtlLts.getPipb().getFromDiffAddrInd()))){
			    pSrvSummary.setPipbAddr(getDisplayPipbAddress(pipbSrvDtlLts.getPipb())); 
			}
			pSrvSummary.setPipbDuplexInd(pipbSrvDtlLts.getPipb().getDuplexInd());
			if(StringUtils.equals("Y", pipbSrvDtlLts.getPipb().getDuplexInd())){
			    pSrvSummary.setPipbDuplexAction(pipbSrvDtlLts.getPipb().getDuplexAction());
                pSrvSummary.setPipbDuplexDn(LtsSbOrderHelper.getDisplaySrvNum(pipbSrvDtlLts.getPipb().getDuplexDn()));
			}
			
			if(pipbSrvDtlLts.getAppointmentDtl() != null){
				pSrvSummary.setPipbInstallDate(LtsDateFormatHelper.getDateFromDTOFormat(pipbSrvDtlLts.getAppointmentDtl().getAppntStartTime()));
				pSrvSummary.setPipbInstallTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(pipbSrvDtlLts.getAppointmentDtl().getAppntStartTime(), pipbSrvDtlLts.getAppointmentDtl().getAppntEndTime())));
				pSrvSummary.setPipbSwitchDate(LtsDateFormatHelper.getDateFromDTOFormat(pipbSrvDtlLts.getAppointmentDtl().getCutOverStartTime()) + " " + LtsDateFormatHelper.getFromToTimeFormat(pipbSrvDtlLts.getAppointmentDtl().getCutOverStartTime(), pipbSrvDtlLts.getAppointmentDtl().getCutOverEndTime()));
			}
		}
		
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
	
	private void fillESignDetail(LtsAcqServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder){
		pSrvSummary.setDisMode(pSbOrder.getDisMode());
		pSrvSummary.setCollectMethod(pSbOrder.getCollectMethod());
		pSrvSummary.setEsigEmailAddr(pSbOrder.getEsigEmailAddr());
		pSrvSummary.setEsigEmailLang(pSbOrder.getEsigEmailLang());
//		pSrvSummary.setAllOrdDocAssgnsList(Arrays.asList(pSbOrder.getAllOrdDocAssgns()));
//		List<AllOrdDocAssgnLtsDTO> ordDocAssgnList = new ArrayList<AllOrdDocAssgnLtsDTO>();
		
		if (ArrayUtils.isEmpty(pSbOrder.getAllOrdDocAssgns())) {
			return;
		}
		
//		for(AllOrdDocAssgnLtsDTO ordDocAssgn: pSbOrder.getAllOrdDocAssgns()){
//			AllOrdDocAssgnLtsDTO copyOrdDocAssgn = new AllOrdDocAssgnLtsDTO();
//			copyOrdDocAssgn.setDocType((String) ltsDocTypeLkupCacheService.get(ordDocAssgn.getDocType()));
//			copyOrdDocAssgn.setMarkDelInd(ordDocAssgn.getMarkDelInd());
//			copyOrdDocAssgn.setWaiveReason((String) ltsWaiveReasonCacheService.get(ordDocAssgn.getWaiveReason()));
//			copyOrdDocAssgn.setCollectedInd(ordDocAssgn.getCollectedInd());
//			ordDocAssgnList.add(copyOrdDocAssgn);
//		}
//		pSrvSummary.setAllOrdDocAssgnsList(ordDocAssgnList);
		
		
		List<CollectDocDto> collectDocList = new ArrayList<CollectDocDto>();
		HashMap<String, String> faxSerialMap = ltsOrderDocumentService.getFaxSerialMap(pSbOrder.getOrderId()); // new HashMap<String, String>();
		for(AllOrdDocAssgnLtsDTO ordDocAssgn: pSbOrder.getAllOrdDocAssgns()){
			OrderDocDTO orderDocument = ltsOrderDocumentService.getOrderDoc(ordDocAssgn.getDocType());
			CollectDocDto collectDoc = new CollectDocDto();
			collectDoc.setDocType(ordDocAssgn.getDocType());
			collectDoc.setDocTypeDisplay((String) ltsDocTypeLkupCacheService.get(ordDocAssgn.getDocType()));
			collectDoc.setWaiveReason(ordDocAssgn.getWaiveReason());
			collectDoc.setWaiveReasonDisplay((String) ltsWaiveReasonCacheService.get(ordDocAssgn.getWaiveReason()));
			collectDoc.setMarkDelInd(ordDocAssgn.getMarkDelInd());
			collectDoc.setCollectedInd(ordDocAssgn.getCollectedInd());
			collectDoc.setMandatory(orderDocument != null ? StringUtils.equalsIgnoreCase("M", orderDocument.getMdoInd()) : false);
			collectDoc.setFaxSerial(faxSerialMap.get(ordDocAssgn.getDocType()));
			collectDocList.add(collectDoc);
		}
		
		pSrvSummary.setCollectDocList(collectDocList);
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	public CodeLkupCacheService getLtsDocTypeLkupCacheService() {
		return ltsDocTypeLkupCacheService;
	}

	public void setLtsDocTypeLkupCacheService(
			CodeLkupCacheService ltsDocTypeLkupCacheService) {
		this.ltsDocTypeLkupCacheService = ltsDocTypeLkupCacheService;
	}

	public CodeLkupCacheService getLtsWaiveReasonCacheService() {
		return ltsWaiveReasonCacheService;
	}

	public void setLtsWaiveReasonCacheService(
			CodeLkupCacheService ltsWaiveReasonCacheService) {
		this.ltsWaiveReasonCacheService = ltsWaiveReasonCacheService;
	}
	
	
	
}
