package com.bomwebportal.lts.service.order.acq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.CollectDocDto;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OfferItemRelationDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsRetrieveFsaService;
import com.bomwebportal.lts.service.OfferService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.service.order.OfferItemService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsAcqSummaryEyeServiceImpl extends LtsAcqSummaryBaseServiceImpl {

	private OfferItemService offerItemService = null;
	private CodeLkupCacheService technologyDescLkupCacheService = null;
	private CodeLkupCacheService ltsWaiveReasonCacheService = null;
	private CodeLkupCacheService ltsDocTypeLkupCacheService = null;
	private LtsOrderDocumentService ltsOrderDocumentService = null;
	private OfferService offerService = null;
	private ImsServiceProfileAccessService imsServiceProfileAccessService;
	private LtsRetrieveFsaService ltsRetrieveFsaService;
	
	public LtsAcqServiceSummaryDTO buildSummary(SbOrderDTO pSbOrder, String pLocale, boolean pIsEnquiry) {
		
		LtsAcqServiceSummaryDTO srvSummary = new LtsAcqServiceSummaryDTO();
		
		ServiceDetailLtsDTO eyeSrvDtlLts = LtsSbOrderHelper.getLtsEyeService(pSbOrder);
		ServiceDetailOtherLtsDTO eyeSrvDtlIms = LtsSbOrderHelper.getImsEyeService(pSbOrder.getSrvDtls());
		
		if (eyeSrvDtlLts == null) {
			return null;
		}
		
		List<AccountServiceAssignLtsDTO> acctList = new ArrayList<AccountServiceAssignLtsDTO>();
		
		for (AccountServiceAssignLtsDTO acctDtl: eyeSrvDtlLts.getAccounts()){
			if (StringUtils.equals(acctDtl.getChrgType(), "R")){
				acctList.add(acctDtl);
			}
		}
		
		for (AccountServiceAssignLtsDTO acctDtl: eyeSrvDtlLts.getAccounts()){
			if (StringUtils.equals(acctDtl.getChrgType(), "I") && !StringUtils.equals(acctList.get(0).getAccount().getAcctNo(), acctDtl.getAccount().getAcctNo())){
				acctList.add(acctDtl);
			}	
		}
		
		AccountServiceAssignLtsDTO[] accts = acctList.toArray(new AccountServiceAssignLtsDTO[acctList.size()]);
/*		AccountServiceAssignLtsDTO[] accts = new AccountServiceAssignLtsDTO[eyeSrvDtlLts.getAccounts().length];
		accts = eyeSrvDtlLts.getAccounts().clone();*/
				
		this.fillLtsSummaryDetail(srvSummary, pSbOrder, eyeSrvDtlLts);
		this.fillAccountDetail(srvSummary, accts, eyeSrvDtlLts);
		this.fillThirdPartyDetail(srvSummary, eyeSrvDtlLts.getThirdPartyDtl());
		this.fillAppoinmentDetail(srvSummary, eyeSrvDtlLts.getAppointmentDtl(), eyeSrvDtlIms, pSbOrder);
		this.fillItemDisplay(pSbOrder, srvSummary, eyeSrvDtlLts.getItemDtls(), pLocale);
		this.fillOfferDisplay(srvSummary, eyeSrvDtlIms.getOfferDtls(), pLocale);
		this.fillEyeServiceSummary(srvSummary, eyeSrvDtlLts, eyeSrvDtlIms, pLocale);
		this.fillContactPeriod(srvSummary);
		
		if (!LtsSbOrderHelper.isOnlineAcquistionOrder(pSbOrder)) {
			this.fillSrvStatusState(pSbOrder.getOrderId(), srvSummary, eyeSrvDtlLts);
			this.fillSrvStatusState(pSbOrder.getOrderId(), srvSummary, eyeSrvDtlIms);
			this.fillSrvStatusStateLts(srvSummary, eyeSrvDtlLts, eyeSrvDtlIms);	
			this.fillSrvStatusStateIms(pSbOrder, srvSummary);
		}
		this.fillDuplexDetail(srvSummary, pSbOrder);
		this.fillRemarks(srvSummary, eyeSrvDtlLts, eyeSrvDtlIms);
		this.fillOtherRequestDetails(srvSummary, eyeSrvDtlIms);
		this.fillSignoffMessages(srvSummary, eyeSrvDtlIms);
		this.fillESignDetail(srvSummary, pSbOrder);
		this.fillCspInfo(srvSummary, pSbOrder);
		this.fillPipbDetail(srvSummary, pSbOrder);
		srvSummary.setSrvType("EYE");
		return srvSummary;
	}
	
	protected void fillSrvStatusStateIms(SbOrderDTO pSbOrder, LtsAcqServiceSummaryDTO pSrvSummary) {
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(pSbOrder);

		if (imsService == null) {
			return;
		}
		StringBuilder messageSb = new StringBuilder();
		// Check whether the FSA Profile was changed in BOM 
		if( (LtsConstant.MODEM_TYPE_SHARE_EX_FSA.equals(imsService.getShareFsaType()) 
				|| LtsConstant.MODEM_TYPE_SHARE_OTHER_FSA.equals(imsService.getShareFsaType())) 
				&& !LtsConstant.MODEM_TYPE_2L2B.equals(imsService.getModemArrangement())) {
			FsaServiceDetailOutputDTO fsaProfile = imsServiceProfileAccessService.getServiceDetailByFSA(imsService.getSrvNum(), StringUtils.defaultIfEmpty(pSbOrder.getStaffNum(), "SB_SYS"));
			
			if (fsaProfile == null) {
				pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_PROFILE_OUTDATED);
				pSrvSummary.clearMessage();
				messageSb.append("FSA profile not found.  Please cancel this order.");
			}
			else {
				String existSrvType = ltsRetrieveFsaService.getExistSrvType(fsaProfile).getDesc();
				if (!StringUtils.equals(imsService.getExistTechnology(), fsaProfile.getTechnology())
						 || !StringUtils.equals(imsService.getExistSrvTypeCd(), existSrvType)) {
					pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_PROFILE_OUTDATED);
					pSrvSummary.clearMessage();
					messageSb.append("FSA profile outdated.  Please cancel this order.");	
				}
			}
		}
		pSrvSummary.appendMessage(messageSb.toString());
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
//			pSrvSummary.setPipbAddr(addrSb.toString()); 
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
	
	private void fillOfferDisplay(LtsAcqServiceSummaryDTO pSrvSummary, ImsOfferDetailDTO[] pOfferDtls, String pLocale) {
	
		ItemDetailDTO item = null;
		
		for (int i=0; pOfferDtls!=null && i<pOfferDtls.length; ++i) {
			if (StringUtils.equals(LtsBackendConstant.IO_IND_OUT, pOfferDtls[i].getIoInd())) {
				item = this.offerService.getTpVasItemDetail(pOfferDtls[i].getItemId(), pLocale);
				
				if (item != null) {
					pSrvSummary.addOutImsItem(this.generateOfferProfileSummaryDTO(item, pOfferDtls[i]));
				}
			}
		}
	}
	
	private ItemDetailSummaryDTO generateOfferProfileSummaryDTO(ItemDetailDTO pItemDesc, ImsOfferDetailDTO pOffer) {
		
		ItemDetailSummaryDTO itemSummary = new ItemDetailSummaryDTO();
		BeanUtils.copyProperties(pItemDesc, itemSummary);
		itemSummary.setIoInd(pOffer.getIoInd());
		
		if (StringUtils.isNotBlank(pOffer.getPenaltyHandling())) {
			itemSummary.setPenaltyHandling((String)this.penaltyHandleLtsLkupCacheService.get(pOffer.getPenaltyHandling()));
		} else {
			itemSummary.setPenaltyHandling("Free to go");
		}
		return itemSummary;
	}
	
	private LtsAcqServiceSummaryDTO fillEyeServiceSummary(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, ServiceDetailOtherLtsDTO pSrvDtlIms, String pLocale) {
		
		if (pSrvSummary == null) {
			pSrvSummary = new LtsAcqServiceSummaryDTO();
		}
		this.setFsaDetails(pSrvSummary, pSrvDtlIms);
		this.setChannels(pSrvSummary, pSrvDtlIms, pLocale);
		this.setWorkQueueType(pSrvSummary, pSrvDtlLts, pSrvDtlIms);
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvDtlLts);
		this.setEyeArrangement(pSrvSummary, pSrvDtlLts);
		this.setFsaArrangement(pSrvSummary, pSrvDtlIms);
		return pSrvSummary;
	}
	
	protected void fillSrvStatusState(String pOrderId, LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailDTO pSrvDtl) {
		
		if (pSrvDtl instanceof ServiceDetailOtherLtsDTO && StringUtils.isNotBlank(((ServiceDetailOtherLtsDTO)pSrvDtl).getRelatedSbOrderId())) {
			return;
		}
		super.fillSrvStatusState(pOrderId, pSrvSummary, pSrvDtl);
	}
	
	private void fillSrvStatusStateLts(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, ServiceDetailOtherLtsDTO pSrvDtlOther) {
		
		this.fillSrvStatusStateLts(pSrvSummary, pSrvDtlLts);
		
		if (StringUtils.isBlank(pSrvSummary.getStatusState()) && LtsSbHelper.existApprovalOffers(pSrvDtlOther)) {
			pSrvSummary.setStatusState(LtsAcqServiceSummaryDTO.STATUS_STATE_APPROVAL);
		}
	}
	
	private void setEyeArrangement(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(pSrvDtlLts.getFromProd());
		
		if (StringUtils.equals(LtsConstant.LTS_SRV_TYPE_NEW, pSrvDtlLts.getFromProd())) {
			sb.append(" ");
		} else {
			sb.append(" (");
			sb.append(pSrvDtlLts.getFromSrvType());
			sb.append(") to ");
		}
		sb.append(pSrvDtlLts.getToProd());
		sb.append(" (");
		sb.append(pSrvDtlLts.getToSrvType());
		sb.append(")");
		pSrvSummary.setEyeArrangement(sb.toString());
	}
	
	private void setFsaArrangement(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		
		StringBuilder sb = new StringBuilder();
		
		if (StringUtils.equals(LtsConstant.ORDER_TYPE_INSTALL, pSrvDtlIms.getOrderType())) {
			sb.append("NEW ");
			sb.append(pSrvDtlIms.getToProd());
		} else {
			sb.append(pSrvDtlIms.getFromProd());
			
			if (StringUtils.equals(LtsConstant.IMS_PRODUCT_TYPE_NEW, pSrvDtlIms.getFromProd())) {
				sb.append(" ");
			} else {
				sb.append(" to ");
			}
			sb.append(pSrvDtlIms.getToProd());
		}
		pSrvSummary.setFsaArrangement(sb.toString());
	}
	
	protected void fillAccountDetail(LtsAcqServiceSummaryDTO pSrvSummary, AccountServiceAssignLtsDTO[] pAccts, ServiceDetailLtsDTO pSrvDtlLts) {
		
		super.fillAccountDetail(pSrvSummary, pSrvDtlLts, pAccts);
		
//		AccountDetailLtsDTO account	 = pSrvDtlLts.getAccount();		
		if (pAccts == null) {
			return;
		}
		
		for (int i=0; i<pAccts.length; i++){
		    if("Y".equals(pSrvDtlLts.getRecontractInd())){
			    pSrvSummary.setMemoNum(pSrvDtlLts.getRecontract().getFuturePayment().getSalesMemoNum());
		    }else{
			    pSrvSummary.setMemoNum(pAccts[i].getAccount().getFuturePayment().getSalesMemoNum());
		    }
		}
	}
	
	private void fillRemarks(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		
		StringBuilder orderRemarkSb = new StringBuilder();
		StringBuilder custRemarkSb = new StringBuilder();
		this.appendRemark(orderRemarkSb, pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_ORDER_REMARK), "LTS Order Remarks");
		this.appendRemark(orderRemarkSb, pSrvDtlIms.remarkToString(LtsConstant.REMARK_TYPE_ORDER_REMARK), "IMS Order Remarks");
		this.appendRemark(custRemarkSb, pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_CUST_REMARK), "");
		this.appendRemark(custRemarkSb, pSrvDtlIms.remarkToString(LtsConstant.REMARK_TYPE_CUST_REMARK), "");
		pSrvSummary.setOrderRemarks(orderRemarkSb.toString());
		pSrvSummary.setCustRemarks(custRemarkSb.toString());
		pSrvSummary.setEarliestSrdReasonRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_EARLIEST_SRD_REMARK));
		pSrvSummary.setAddOnRemarks(pSrvDtlLts.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
	}
	
	private void appendRemark(StringBuilder pRemarkSb, String pRemarkContent, String pPrefix) {

		if (StringUtils.isNotEmpty(pRemarkContent)) {
			if (StringUtils.isNotEmpty(pPrefix)) {
				pRemarkSb.append("\n");	
				pRemarkSb.append(pPrefix);
				pRemarkSb.append("\n");	
			}
			pRemarkSb.append(pRemarkContent);
			pRemarkSb.append("\n");	
		}
	}
	
	private void fillDuplexDetail(LtsAcqServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder) {
		
		ServiceDetailDTO[] srvDtls = pSbOrder.getSrvDtls();
		
		for (int i=0; i < srvDtls.length; ++i) {
			if (StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_DNX_REMOVE, srvDtls[i].getSrvTypeCdAction())) {
				pSrvSummary.setCancelDuplexType("DNX");
				pSrvSummary.setCancelDuplexDn(srvDtls[i].getSrvNum());
			} else if (StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_DNY_REMOVE, srvDtls[i].getSrvTypeCdAction())) {
				pSrvSummary.setCancelDuplexType("DNY");
				pSrvSummary.setCancelDuplexDn(srvDtls[i].getSrvNum());
			}
		}
	}
	
	protected void fillAppoinmentDetail(LtsAcqServiceSummaryDTO pSrvSummary, AppointmentDetailLtsDTO pAppt, ServiceDetailOtherLtsDTO pSrvDtlIms, SbOrderDTO pSbOrder) {
		
		if (pAppt == null) {
			return;
		}
		super.fillAppoinmentDetail(pSrvSummary, pAppt, pSbOrder);
		/*String installTime = LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(pAppt.getAppntStartTime(), pAppt.getAppntEndTime()));
		
		if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, pSrvDtlIms.getAssgnTechnology())) {
			pSrvSummary.setInstallTime(LtsDateFormatHelper.convertToPonTime(installTime));
		} else {
			pSrvSummary.setInstallTime(installTime);
		}*/
	}
	
	private void setFsaDetails(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		
		pSrvSummary.setImsSbOrder(pSrvDtlIms.getRelatedSbOrderId());
		pSrvSummary.setNowtvSrvType(pSrvDtlIms.getNewTvSrvType() == null ? pSrvDtlIms.getExistTvSrvType() : pSrvDtlIms.getNewTvSrvType());
		pSrvSummary.setPendingImsOcid(pSrvDtlIms.getPendingOcid());
		pSrvSummary.setEdfNo(pSrvDtlIms.getEdfRef());
		
		if (StringUtils.equals("Y", pSrvDtlIms.getNowtvMirrorInd())) {
			pSrvSummary.setMirrorFsa(pSrvDtlIms.getMirrorFsa() == null ? pSrvDtlIms.getSrvNum() : pSrvDtlIms.getMirrorFsa());
		}
		if (!StringUtils.equals("Y", pSrvDtlIms.getNowtvMirrorInd()) 
				&& StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, pSrvDtlIms.getModemArrangement())) {
			pSrvSummary.setModemArrangement("2 X 1L1B");
		} else {
			pSrvSummary.setModemArrangement(pSrvDtlIms.getModemArrangement());
		}
		if (pSrvDtlIms.getNowtvDetail() != null) {
			List<ItemDetailSummaryDTO> nowtvBillList = pSrvSummary.getNowtvBillItemList();
			
			for (int i=0; nowtvBillList!=null && i<nowtvBillList.size(); ++i) {
				if (StringUtils.equals(LtsConstant.ITEM_TYPE_TV_EMAIL, nowtvBillList.get(i).getItemType())) {
					pSrvSummary.setEmailBillAddress(pSrvDtlIms.getNowtvDetail().getEmailAddress());
				}
			}
		}
		if (StringUtils.isNotEmpty(pSrvDtlIms.getExistSrvTypeCd()) && StringUtils.isNotEmpty(pSrvDtlIms.getNewSrvTypeCd()) 
				&& !StringUtils.equals(pSrvDtlIms.getExistSrvTypeCd(), pSrvDtlIms.getNewSrvTypeCd())) {
			pSrvSummary.setSrvTypeChange(pSrvDtlIms.getExistSrvTypeCd() + " to " + pSrvDtlIms.getNewSrvTypeCd());
		}
		if (StringUtils.isNotEmpty(pSrvDtlIms.getExistBandwidth()) && StringUtils.isNotEmpty(pSrvDtlIms.getNewBandwidth()) 
				&& !StringUtils.equals(pSrvDtlIms.getExistBandwidth(), pSrvDtlIms.getNewBandwidth())) {
			pSrvSummary.setBandwidthChange(pSrvDtlIms.getExistBandwidth() + "M to " + pSrvDtlIms.getNewBandwidth() + "M");
		}
		if (StringUtils.isNotEmpty(pSrvDtlIms.getExistTechnology()) && StringUtils.isNotEmpty(pSrvDtlIms.getAssgnTechnology()) 
				&& !StringUtils.equals(pSrvDtlIms.getExistTechnology(), pSrvDtlIms.getAssgnTechnology())) {
			pSrvSummary.setTechnologyChange(this.technologyDescLkupCacheService.get(pSrvDtlIms.getExistTechnology()) + " to " + this.technologyDescLkupCacheService.get(pSrvDtlIms.getAssgnTechnology()));
		}
		if ((StringUtils.isNotBlank(pSrvDtlIms.getSrvNum()) && StringUtils.isNotBlank(pSrvDtlIms.getReplaceExistFsa()) && StringUtils.equals(pSrvDtlIms.getSrvNum(), pSrvDtlIms.getReplaceExistFsa()))
				|| (StringUtils.isBlank(pSrvDtlIms.getSrvNum()) && StringUtils.isNotBlank(pSrvDtlIms.getReplaceExistFsa()))) {
			pSrvSummary.setRelatedFSA("New FSA to replace existing FSA " + pSrvDtlIms.getReplaceExistFsa());
		} else if (StringUtils.isNotBlank(pSrvDtlIms.getSrvNum()) && StringUtils.isNotBlank(pSrvDtlIms.getReplaceExistFsa())) {
			pSrvSummary.setRelatedFSA(pSrvDtlIms.getSrvNum() + " to replace existing FSA " + pSrvDtlIms.getReplaceExistFsa());
		} else {
			pSrvSummary.setRelatedFSA(pSrvDtlIms.getSrvNum());	
		}
		
		if(StringUtils.isNotBlank(pSrvDtlIms.getLostModem()) && !"N".equals(pSrvDtlIms.getLostModem())){
			pSrvSummary.setLostModem("Y");
		}else{
			pSrvSummary.setLostModem("N");
		}
		
		if("Y".equals(pSrvDtlIms.getShareRentalRouter()) || "E".equals(pSrvDtlIms.getShareRentalRouter())){
			pSrvSummary.setModemSelection("Rental Router");
		}
		else if(StringUtils.isBlank(pSrvDtlIms.getShareRentalRouter()) || "N".equals(pSrvDtlIms.getShareRentalRouter())){
			pSrvSummary.setModemSelection("BRM");
		}
	}
	
	private void setChannels(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailOtherLtsDTO pSrvDtlIms, String pLocale) {
		
		ChannelDetailLtsDTO[] channels = pSrvDtlIms.getChannels();
		String channelDesc = null;
		StringBuilder channelDescSb = null;
		
		for (int i=0; channels!=null && i<channels.length; ++i) {
			channelDesc = this.ltsOfferService.getChannelDescription(channels[i].getChannelId(), pLocale);
			
			if (StringUtils.isNotEmpty(channelDesc)) {
				channelDescSb = new StringBuilder(channelDesc);
				channelDescSb.append(this.getChannelAttbsDesc(channels[i], pLocale));
				pSrvSummary.addChannel(channelDescSb.toString());	
			}
		}
	}
	
	private String getChannelAttbsDesc(ChannelDetailLtsDTO pChannel, String pLocale) {
		
		if (ArrayUtils.isEmpty(pChannel.getChannelAttbs())) {
			return "";
		}
		ChannelAttbDTO[][] attbs = this.ltsOfferService.getChannelAttbDisplay(pChannel.getChannelId(), pLocale);
		StringBuilder attbDescSb = new StringBuilder();
		
		for (int i=0; i<attbs.length; ++i) {
			attbDescSb.append("<br>• ");
			
			for (int j=0; j<attbs[i].length; ++j) {
				for (int k=0; k<pChannel.getChannelAttbs().length; ++k) {
					if (StringUtils.equals(pChannel.getChannelAttbs()[k].getAttbId(), attbs[i][j].getAttbID())) {
						attbs[i][j].setAttbValue(pChannel.getChannelAttbs()[k].getAttbValue());
					}
				}
				attbDescSb.append(this.getChannelAttbDesc(attbs[i][j]));
				attbDescSb.append("  ");
			}
		}
		return attbDescSb.toString();
	}
	
	private String getChannelAttbDesc(ChannelAttbDTO pAttb) {
		
		StringBuilder attbDescSb = new StringBuilder();
		
		if (StringUtils.equals("TEXT", pAttb.getInputMethod())) {
			attbDescSb.append(pAttb.getAttbDesc());
		} else if (StringUtils.equals("INPUT", pAttb.getInputMethod())) {
			attbDescSb.append(pAttb.getAttbDesc());
			attbDescSb.append(pAttb.getAttbValue());
		} else if (StringUtils.equals("CHECK", pAttb.getInputMethod())) {
			attbDescSb.append("( ");
			
			if (!StringUtils.equals("Y", pAttb.getAttbValue())) {
				attbDescSb.append("Not ");	
			}
			attbDescSb.append(pAttb.getAttbDesc());
			attbDescSb.append(")");
		}
		return attbDescSb.toString();
	}
	
	private void setWorkQueueType(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		
		String workQueueType = null;
		
		if (StringUtils.equals(LtsConstant.WQ_TYPE_FULL, pSrvDtlLts.getWorkQueueType()) || StringUtils.equals(LtsConstant.WQ_TYPE_FULL, pSrvDtlIms.getWorkQueueType())) {
			workQueueType = LtsConstant.WQ_TYPE_FULL;
		} else if (StringUtils.isNotEmpty(pSrvDtlLts.getWorkQueueType())) {
			workQueueType = pSrvDtlLts.getWorkQueueType();
		} else if (StringUtils.isNotEmpty(pSrvDtlIms.getWorkQueueType())) {
			workQueueType = pSrvDtlIms.getWorkQueueType();
		}
		pSrvSummary.setWorkQueueType(workQueueType);
	}

	private void fillOtherRequestDetails(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		pSrvSummary.setFsaExtRelInd(StringUtils.equals("Y", pSrvDtlIms.getErInd()));
	}
	
	private void generateCurrentFutureItemChargeList(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getSrvPlanItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getBbRentalItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getPeItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getSocketItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getContentItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getMoovItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getNowtvItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getBillItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getNowtvBillItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getVasEyeItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getVasItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getIdd0060ItemList(), pSrvDtlLts.getItemDtls());
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvSummary.getProfileItemList(), pSrvDtlLts.getItemDtls());
	}
	
	private void generateCurrentFutureItemChargeList(LtsAcqServiceSummaryDTO pSrvSummary, List<ItemDetailSummaryDTO> pItemDescList, ItemDetailLtsDTO[] pItems) {
		
		Map<String, OfferItemRelationDTO> noTpItemMap = this.offerItemService.getNoTpItems();
		
		for (int i=0; pItemDescList!=null && i<pItemDescList.size(); ++i) {
			
			if (LtsSbOrderHelper.isFutureProfileAction(pItemDescList.get(i).getIoInd())
					&& !noTpItemMap.containsKey(pItemDescList.get(i).getItemId())) {
				pSrvSummary.addFutureProfileItem(pItemDescList.get(i));
			}
			if (LtsSbOrderHelper.isCurrentProfileAction(pItemDescList.get(i).getIoInd())) {
				pSrvSummary.addCurrentProfileItem(pItemDescList.get(i));
			}
		}
	}

	private void fillContactPeriod (LtsAcqServiceSummaryDTO pSrvSummary) {
		
		List<ItemDetailSummaryDTO> planItemList = pSrvSummary.getSrvPlanItemList();
		
		for (int i=0; planItemList!=null && i<planItemList.size(); ++i) {
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_PLAN, planItemList.get(i).getItemType())) {
				pSrvSummary.setContractPeriod(this.ltsBasketService.getBasketContractPeriod(planItemList.get(i).getBasketId()) + " months");
				break;
			}
		}
	}
	
	private void fillSignoffMessages(LtsAcqServiceSummaryDTO pSrvSummary, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		// Select upgrade PCD
		if (StringUtils.isNotEmpty(pSrvDtlIms.getNewBandwidth()) && !StringUtils.equals(pSrvDtlIms.getExistBandwidth(), pSrvDtlIms.getNewBandwidth())) {
			pSrvSummary.appendPromptAlertMessage("Please submit Upgrade PCD AF to Checking Team");
		}
		// Select New TV
		if (StringUtils.indexOf(pSrvDtlIms.getExistSrvTypeCd(), "TV") < 0 && StringUtils.indexOf(pSrvDtlIms.getNewSrvTypeCd(), "TV") >= 0) {
			pSrvSummary.appendPromptAlertMessage("Please submit nowTV AF to Checking Team");
		}
		// Select Upgrade to HDTV
		if (StringUtils.indexOf(pSrvDtlIms.getExistSrvTypeCd(), "SDTV") >= 0 && StringUtils.indexOf(pSrvDtlIms.getNewSrvTypeCd(), "HDTV") >= 0) {
			pSrvSummary.appendPromptAlertMessage("Please submit upgrade nowTV AF to Checking Team");
		}
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
	
//	private void fillCspInfo(LtsAcqServiceSummaryDTO pSrvSummary, AccountServiceAssignLtsDTO[] pAccts){
//            boolean cspNewReg = false;
//            String[] cspEmailLogin = new String[pAccts.length];
//			String[] cspMobile = new String[pAccts.length];
//
//			for (int i = 0; i < pAccts.length; i++){
//		        cspNewReg = StringUtils.equals("Y", pAccts[i].getAccount().getCustomer().getCsPortalInd());
//		        if(cspNewReg){
//			        pSrvSummary.setCspNewReg(true);
//			        cspEmailLogin[i] = pAccts[i].getAccount().getCustomer().getCsPortalLogin();
//			        cspMobile[i] = pAccts[i].getAccount().getCustomer().getCsPortalMobile();
//		        }
//			}
//			pSrvSummary.setCspEmail(cspEmailLogin);
//			pSrvSummary.setCspMobile(cspMobile);
//		
//	}
	
	public OfferItemService getOfferItemService() {
		return this.offerItemService;
	}

	public void setOfferItemService(OfferItemService pOfferItemService) {
		this.offerItemService = pOfferItemService;
	}

	public CodeLkupCacheService getTechnologyDescLkupCacheService() {
		return technologyDescLkupCacheService;
	}

	public void setTechnologyDescLkupCacheService(
			CodeLkupCacheService technologyDescLkupCacheService) {
		this.technologyDescLkupCacheService = technologyDescLkupCacheService;
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

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	public OfferService getOfferService() {
		return offerService;
	}

	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}

	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}

	public LtsRetrieveFsaService getLtsRetrieveFsaService() {
		return ltsRetrieveFsaService;
	}

	public void setLtsRetrieveFsaService(LtsRetrieveFsaService ltsRetrieveFsaService) {
		this.ltsRetrieveFsaService = ltsRetrieveFsaService;
	}
	
}
