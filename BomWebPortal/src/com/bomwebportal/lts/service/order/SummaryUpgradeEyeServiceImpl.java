package com.bomwebportal.lts.service.order;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OfferItemRelationDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.OfferService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class SummaryUpgradeEyeServiceImpl extends SummaryBaseServiceImpl {

	private OfferItemService offerItemService = null;
	private CodeLkupCacheService technologyDescLkupCacheService = null;
	private OfferService offerService = null;
	
	public ServiceSummaryDTO buildSummary(SbOrderDTO pSbOrder, String pLocale, boolean pIsEnquiry) {
		
		ServiceSummaryDTO srvSummary = new ServiceSummaryDTO();
		
		ServiceDetailLtsDTO eyeSrvDtlLts = LtsSbOrderHelper.getLtsEyeService(pSbOrder);
		ServiceDetailOtherLtsDTO eyeSrvDtlIms = LtsSbOrderHelper.getImsEyeService(pSbOrder.getSrvDtls());
		
		if (eyeSrvDtlLts == null) {
			return null;
		}
		this.fillLtsSummaryDetail(srvSummary, pSbOrder, eyeSrvDtlLts);
		this.fillAccountDetail(srvSummary, eyeSrvDtlLts);
		this.fillThirdPartyDetail(srvSummary, eyeSrvDtlLts.getThirdPartyDtl());
		this.fillAppoinmentDetail(srvSummary, eyeSrvDtlLts.getAppointmentDtl(), eyeSrvDtlIms);
		this.fillItemDisplay(pSbOrder, srvSummary, eyeSrvDtlLts.getItemDtls(), pLocale);
		this.fillOfferDisplay(srvSummary, eyeSrvDtlIms.getOfferDtls(), pLocale);
		this.fillEyeServiceSummary(srvSummary, eyeSrvDtlLts, eyeSrvDtlIms, pLocale);
		this.fillContactPeriod(srvSummary);
		
		if (!LtsSbOrderHelper.isOnlineAcquistionOrder(pSbOrder)) {
			this.fillSrvStatusState(pSbOrder.getOrderId(), srvSummary, eyeSrvDtlLts);
			
			if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(pSbOrder.getOrderType())) {
				this.fillSrvStatusState(pSbOrder.getOrderId(), srvSummary, eyeSrvDtlIms);	
			}
			
			this.fillSrvStatusStateLts(pSbOrder, srvSummary, eyeSrvDtlLts, eyeSrvDtlIms);
			this.fillSrvStatusStateIms(pSbOrder, srvSummary);
		}
		this.fillDuplexDetail(srvSummary, pSbOrder);
		this.fillRemarks(srvSummary, eyeSrvDtlLts, eyeSrvDtlIms);
		this.fillOtherRequestDetails(srvSummary, eyeSrvDtlIms);
		this.fillSignoffMessages(srvSummary, eyeSrvDtlIms);
		this.fillESignDetail(srvSummary, pSbOrder);
		this.fillCspInfo(srvSummary, pSbOrder);
		this.fillCustPdpo(srvSummary, pSbOrder);
		this.fillContactDetail(srvSummary, pSbOrder.getContact());
		srvSummary.setSrvType("EYE");
		return srvSummary;
	}
	
	private void fillOfferDisplay(ServiceSummaryDTO pSrvSummary, ImsOfferDetailDTO[] pOfferDtls, String pLocale) {
	
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
	
	private ServiceSummaryDTO fillEyeServiceSummary(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, ServiceDetailOtherLtsDTO pSrvDtlIms, String pLocale) {
		
		if (pSrvSummary == null) {
			pSrvSummary = new ServiceSummaryDTO();
		}
		this.setFsaDetails(pSrvSummary, pSrvDtlIms);
		this.setChannels(pSrvSummary, pSrvDtlIms, pLocale);
		this.setWorkQueueType(pSrvSummary, pSrvDtlLts, pSrvDtlIms);
		this.generateCurrentFutureItemChargeList(pSrvSummary, pSrvDtlLts);
		this.setEyeArrangement(pSrvSummary, pSrvDtlLts);
		this.setFsaArrangement(pSrvSummary, pSrvDtlIms);
		return pSrvSummary;
	}
	
	protected void fillSrvStatusState(String pOrderId, ServiceSummaryDTO pSrvSummary, ServiceDetailDTO pSrvDtl) {
		
		if (pSrvDtl instanceof ServiceDetailOtherLtsDTO && StringUtils.isNotBlank(((ServiceDetailOtherLtsDTO)pSrvDtl).getRelatedSbOrderId())) {
			return;
		}
		super.fillSrvStatusState(pOrderId, pSrvSummary, pSrvDtl);
	}
	
	private void fillSrvStatusStateLts(SbOrderDTO pSbOrder, ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, ServiceDetailOtherLtsDTO pSrvDtlOther) {
		
		this.fillSrvStatusStateLts(pSbOrder, pSrvSummary, pSrvDtlLts);
		
		if (StringUtils.isBlank(pSrvSummary.getStatusState()) && LtsSbHelper.existApprovalOffers(pSrvDtlOther)) {
			pSrvSummary.setStatusState(ServiceSummaryDTO.STATUS_STATE_APPROVAL);
		}
	}
	
	private void setEyeArrangement(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		
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
	
	private void setFsaArrangement(ServiceSummaryDTO pSrvSummary, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		
		StringBuilder sb = new StringBuilder();
		
		if (StringUtils.equals(LtsConstant.ORDER_TYPE_INSTALL, pSrvDtlIms.getOrderType())) {
			sb.append("New WG");
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
	
	protected void fillAccountDetail(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
		super.fillAccountDetail(pSrvSummary, pSrvDtlLts);
		AccountDetailLtsDTO account	 = pSrvDtlLts.getAccount();
		
		if (account == null) {
			return;
		}
		if("Y".equals(pSrvDtlLts.getRecontractInd())){
			pSrvSummary.setMemoNum(pSrvDtlLts.getRecontract().getFuturePayment().getSalesMemoNum());
		}else{
			pSrvSummary.setMemoNum(account.getFuturePayment().getSalesMemoNum());
		}
	}
	
	private void fillRemarks(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		
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
	
	protected void fillAppoinmentDetail(ServiceSummaryDTO pSrvSummary, AppointmentDetailLtsDTO pAppt, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		
		if (pAppt == null) {
			return;
		}
		super.fillAppoinmentDetail(pSrvSummary, pAppt);
		String installTime = LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(pAppt.getAppntStartTime(), pAppt.getAppntEndTime()));
		
		if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, pSrvDtlIms.getAssgnTechnology())) {
			pSrvSummary.setInstallTime(LtsDateFormatHelper.convertToPonTime(installTime));
		} else {
			pSrvSummary.setInstallTime(installTime);
		}
	}
	
	private void setFsaDetails(ServiceSummaryDTO pSrvSummary, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		
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
	
	private void setChannels(ServiceSummaryDTO pSrvSummary, ServiceDetailOtherLtsDTO pSrvDtlIms, String pLocale) {
		
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
	
	private void setWorkQueueType(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		
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

	private void fillOtherRequestDetails(ServiceSummaryDTO pSrvSummary, ServiceDetailOtherLtsDTO pSrvDtlIms) {
		pSrvSummary.setFsaExtRelInd(StringUtils.equals("Y", pSrvDtlIms.getErInd()));
	}
	
	private void generateCurrentFutureItemChargeList(ServiceSummaryDTO pSrvSummary, ServiceDetailLtsDTO pSrvDtlLts) {
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
	
	private void generateCurrentFutureItemChargeList(ServiceSummaryDTO pSrvSummary, List<ItemDetailSummaryDTO> pItemDescList, ItemDetailLtsDTO[] pItems) {
		
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

	private void fillContactPeriod (ServiceSummaryDTO pSrvSummary) {
		
		List<ItemDetailSummaryDTO> planItemList = pSrvSummary.getSrvPlanItemList();
		
		for (int i=0; planItemList!=null && i<planItemList.size(); ++i) {
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_PLAN, planItemList.get(i).getItemType())) {
				pSrvSummary.setContractPeriod(this.ltsBasketService.getBasketContractPeriod(planItemList.get(i).getBasketId()) + " months");
				break;
			}
		}
	}
	
	private void fillSignoffMessages(ServiceSummaryDTO pSrvSummary, ServiceDetailOtherLtsDTO pSrvDtlIms) {
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
	

	public OfferService getOfferService() {
		return offerService;
	}

	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}
}
