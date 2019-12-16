package com.bomwebportal.lts.service.report;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.MessageSource;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.LostModemService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.EyeAppFormRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionCRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionDRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionERptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionFRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionGRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionInternalUseDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionJRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionKRptDTO;

public class UpgradeEyeReportLtsServiceImpl extends ReportLtsBaseServiceImpl {	
	
	private CodeLkupCacheService technologyDescLkupCacheService;
	private CodeLkupCacheService remarkTemplateCacheService;
	private LostModemService lostModemService;
	private MessageSource messageSource;
	
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO pSbOrder, String pLocale, String pRptName, boolean pIsEditable, boolean pIsPrintSignature) {
		
		EyeAppFormRptDTO rptDTO = (EyeAppFormRptDTO)pReportDTO;
		ServiceDetailLtsDTO eyeSrvDtlLts = LtsSbHelper.getLtsEyeService(pSbOrder);
		ServiceDetailOtherLtsDTO eyeSrvDtlIms = LtsSbHelper.getImsEyeService(pSbOrder.getSrvDtls());
		boolean internalUse = rptDTO.isPrintTermsCondition()? false : true;
		boolean isDelFree = false;		
		
		try {
			if (eyeSrvDtlLts != null) {
				
				this.initializeItem(eyeSrvDtlLts.getItemDtls(), pSbOrder.getAppDate(), pLocale, pIsEditable, internalUse);
				this.intializeOffer(eyeSrvDtlIms.getOfferDtls());
				rptDTO.setThirdPartyInd(eyeSrvDtlLts.getThirdPartyDtl() != null);
				if (pIsPrintSignature) {
					rptDTO.setEyeCustSignature(eyeSrvDtlLts.getThirdPartyDtl() == null ? LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_EYE_CUST) : LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_EYE_3RD_PARTY));
				}
			}
			ItemDetailLtsDTO[] srvPlanItem = LtsSbHelper.getItemByType(eyeSrvDtlLts.getItemDtls(), LtsBackendConstant.ITEM_TYPE_PLAN);
			isDelFree = getPcdBundleFreeFromSbOrder(srvPlanItem);
			
			String[] afTypes = new String[] {eyeSrvDtlLts.getDeviceType()};
			if(isDelFree)
			{
				afTypes = new String[] {"DEL_FREE"};
			}
			else {
				String[] itemAfTypes = getItemAfType(pSbOrder);
				if (ArrayUtils.isNotEmpty(itemAfTypes)) {
					afTypes = itemAfTypes;
				}	
			}
			
			
			rptDTO.setOrderMode(pSbOrder.getOrderId().substring(0, 1));
			rptDTO.setOrderType(pSbOrder.getOrderType());
			this.setCspItemType(rptDTO, pSbOrder);
			this.generateReportTerms(rptDTO, afTypes, pLocale, pRptName, pIsEditable);
			this.generateSectionAReport(rptDTO.getSectionA(), eyeSrvDtlLts, pSbOrder, pLocale, rptDTO.getOrderMode(), internalUse);
			this.generateSectionCReport(rptDTO.getSectionC(), pSbOrder, eyeSrvDtlLts, eyeSrvDtlIms, this.basketDetailService.getBasketContractPeriod(srvPlanItem[0].getBasketId()), internalUse);
			this.generateSectionDReport(rptDTO.getSectionD(), eyeSrvDtlIms, pLocale);
			this.generateSectionEReport(rptDTO.getSectionE());
			this.generateSectionFReport(rptDTO.getSectionF(), pLocale);
			this.generateSectionGReport(rptDTO.getSectionG(), eyeSrvDtlIms, pSbOrder);
			this.generateSectionJReport(rptDTO.getSectionJ(), eyeSrvDtlIms);
			this.generateSectionKReport(rptDTO.getSectionK(), pSbOrder, eyeSrvDtlLts, pIsPrintSignature);
			this.generateSectionNReport(rptDTO.getSectionN(), eyeSrvDtlLts, rptDTO.getOrderMode(), internalUse);
			this.generateSectionLReport(rptDTO.getSectionL(), pSbOrder);
			this.generateSectionPReport(rptDTO.getSectionP(), pSbOrder);
			this.generateSectionQReport(rptDTO.getSectionQ(), pSbOrder);
			
			this.generateSectionInternalUseReport(rptDTO.getSectionInternalUse(), pSbOrder, eyeSrvDtlLts, eyeSrvDtlIms);
			
//			if(isDelFree)
//			{
//				eyeSrvDtlLts.setDeviceType(tempDeviceType);
//			}
			
		} catch (Exception e) {
			logger.error("Order Id = " + pSbOrder.getOrderId() + "\n" + ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException("Order Id = " + pSbOrder.getOrderId() + "\n" + e.getStackTrace());
		}
	}
	
	private boolean getPcdBundleFreeFromSbOrder(ItemDetailLtsDTO[] tempItemDetails) {
		String pcdBundleFree = "";
		
		for (int j=0; j<tempItemDetails.length; j++)
		{
			if(tempItemDetails[j].getItemType().equalsIgnoreCase(LtsBackendConstant.ITEM_TYPE_PLAN))
			{
				if(tempItemDetails[j].getPcdBundleFree()!=null && !tempItemDetails[j].getPcdBundleFree().equals(""))
				{
					pcdBundleFree = tempItemDetails[j].getPcdBundleFree();
				}
			}							
		}
					
		
		return pcdBundleFree!=null&&pcdBundleFree.equalsIgnoreCase("Y")?true:false;		
	}
	
	private void intializeOffer(ImsOfferDetailDTO[] pOffers)throws Exception  {
		
		if (ArrayUtils.isEmpty(pOffers)) {
			return;
		}
		ItemDetailDTO item = null;
		List<ItemDetailSummaryDTO> outImsVasItemList = this.getItemTypeList(this.itemDescMap, LtsBackendConstant.ITEM_IMS_VAS);
		
		for (int i=0; pOffers!=null && i<pOffers.length; ++i) {
			if (!StringUtils.equals(LtsBackendConstant.IO_IND_OUT, pOffers[i].getIoInd())) {
				continue;
			}
			item = this.offerService.getTpVasItemDetail(pOffers[i].getItemId(), LtsBackendConstant.LOCALE_ENG);
			outImsVasItemList.add(this.generateItemSummaryDTO(item, pOffers[i]));
		}
	}
	
	protected ItemDetailSummaryDTO generateItemSummaryDTO(ItemDetailDTO pItemDesc, ImsOfferDetailDTO pOffer) throws Exception {
		
		ItemDetailSummaryDTO itemSummary = new ItemDetailSummaryDTO();
		pItemDesc.setItemDesc(pItemDesc.getItemDisplayHtml());
		BeanUtils.copyProperties(itemSummary, pItemDesc);
		itemSummary.setIoInd(pOffer.getIoInd());
		itemSummary.setPenaltyHandling(pOffer.getPenaltyHandling());
		return itemSummary;
	}
	
	private void generateSectionCReport(SectionCRptDTO pSectionC, SbOrderDTO pSbOrder, ServiceDetailLtsDTO pSrvDtlLst, ServiceDetailOtherLtsDTO pSrvDtlOther, String pContractPeriod, boolean pInternalUse) {
		
		ServiceDetailLtsDTO pipbService = LtsSbHelper.getAcqPipbService(pSbOrder.getSrvDtls());
		AppointmentDetailLtsDTO appt = pSrvDtlLst.getAppointmentDtl();
		List<ItemDetailSummaryDTO> itemDescList = null;
		ItemDetailSummaryDTO itemDesc = null;
		boolean is2ndDel = LtsSbHelper.is2ndDelService(pSrvDtlLst);
		boolean isPreInstall = LtsSbHelper.isPreInstall(pSbOrder);
		
		pSectionC.setEyeInstallDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getAppntStartTime()));

		if(isPreInstall)
		{
			pSectionC.setEyeSrvNum(pSrvDtlLst.getNewSrvNum()!=null? pSrvDtlLst.getNewSrvNum().substring(4):null);
			pSectionC.setEyePreinstallation(true);
		}
		else
		{
			pSectionC.setEyeSrvNum(pSrvDtlLst.getSrvNum()!=null?pSrvDtlLst.getSrvNum().substring(4):null);
		}
		
		pSectionC.setEyeContractPeriod(pContractPeriod);
		pSectionC.setNewSrvNum(pSrvDtlLst.getNewSrvNum()!=null? pSrvDtlLst.getNewSrvNum().substring(4):null);
		
		if(LtsSbHelper.isNewAcquistionOrder(pSbOrder) && pipbService != null){
			if(!pInternalUse){
				if((!is2ndDel) 
					|| (is2ndDel && StringUtils.equals("Y",pipbService.getPipb().getDuplexInd()) && StringUtils.equals(LtsBackendConstant.LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER, pipbService.getPipb().getDuplexAction()))){
					pSectionC.setPipbType(pipbService.getPipb().getPipbAction());
					if(StringUtils.equals("Y", pipbService.getPipb().getReuseExSocketInd())){
						pSectionC.setReuseSocketInd(pipbService.getPipb().getReuseSocketType());
					}
					if (is2ndDel && StringUtils.equals("Y",pipbService.getPipb().getDuplexInd()) && StringUtils.equals(LtsBackendConstant.LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER, pipbService.getPipb().getDuplexAction())){
						pSectionC.setSwitchingSrvNum(LtsSbHelper.getDisplaySrvNum(pipbService.getPipb().getDuplexDn()));
					}else{
						pSectionC.setSwitchingSrvNum(LtsSbHelper.getDisplaySrvNum(pipbService.getSrvNum()));
					}
					if(pipbService.getAppointmentDtl() != null){
						if(StringUtils.equals(LtsBackendConstant.LTS_PIPB_ACTION_NEW, pipbService.getPipb().getPipbAction())){
							pSectionC.setPipbTargetInstallDate(LtsDateFormatHelper.getDateFromDTOFormat(pipbService.getAppointmentDtl().getAppntStartTime()));
							pSectionC.setPipbTargetInstallTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(pipbService.getAppointmentDtl().getAppntStartTime(), pipbService.getAppointmentDtl().getAppntEndTime())));
							pSectionC.setPipbTargetSwitchDate(LtsDateFormatHelper.getDateFromDTOFormat(pipbService.getAppointmentDtl().getCutOverStartTime()));
							pSectionC.setPipbTargetSwitchTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(pipbService.getAppointmentDtl().getCutOverStartTime(), pipbService.getAppointmentDtl().getCutOverEndTime())));
						}else if(StringUtils.equals(LtsBackendConstant.LTS_PIPB_ACTION_PIPB, pipbService.getPipb().getPipbAction())){
							pSectionC.setPipbTargetInstallDate(LtsDateFormatHelper.getDateFromDTOFormat(pipbService.getAppointmentDtl().getAppntStartTime()));
							pSectionC.setPipbTargetInstallTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(pipbService.getAppointmentDtl().getAppntStartTime(), pipbService.getAppointmentDtl().getAppntEndTime())));
							pSectionC.setPipbTargetSwitchDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getCutOverStartTime()));
							pSectionC.setPipbTargetSwitchTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getCutOverStartTime(), appt.getCutOverEndTime()));
						}
					}
				}else{
					pSectionC.setResTelCutOrderDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getCutOverStartTime()));
					pSectionC.setResTelCutOrderTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getCutOverStartTime(), appt.getCutOverEndTime()));
				}
			}
		}else{
			pSectionC.setEyeCutOrderDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getCutOverStartTime()));
			pSectionC.setEyeCutOrderTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getCutOverStartTime(), appt.getCutOverEndTime()));
		}
		
		pSectionC.setFieldVisitInd("Y".equals(pSrvDtlLst.getForceFieldVisitInd())? true:false);
//		pSectionC.setEyePrewiringDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getPreWiringStartTime()));
//		pSectionC.setEyePrewiringTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getPreWiringStartTime(), appt.getPreWiringEndTime()));
		
		pSectionC.setExDirectory(StringUtils.equals(pSrvDtlLst.getExDirInd(), "Y"));
		pSectionC.setPortIn(LtsSbHelper.isPortInOrder(pSbOrder));
		
		//handle DNX and DNY change----Modified by Max LTS
		if(LtsSbHelper.getDuplexChangeService(pSbOrder.getSrvDtls()) != null && !is2ndDel){
			pSectionC.setNewDuplexNum(LtsSbHelper.getNewDuplexNum(pSbOrder)!=null? LtsSbHelper.getNewDuplexNum(pSbOrder).substring(4) : null);
			pSectionC.setDuplexNum(LtsSbHelper.getDuplexNum(pSbOrder) != null? LtsSbHelper.getDuplexNum(pSbOrder).substring(4) : null);
		}
		
		if (StringUtils.isNotEmpty(appt.getPreWiringStartTime())
				&& StringUtils.isNotEmpty(appt.getPreWiringEndTime())) {
			pSectionC.setEyePrewiringDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getPreWiringStartTime()));
			pSectionC.setEyePrewiringTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getPreWiringStartTime(), appt.getPreWiringEndTime())));
		}
		
		if (LtsSbHelper.isOnlineAcquistionOrder(pSbOrder)) {
			pSectionC.setWorkingDNInd(LtsSbHelper.isPortInOrder(pSbOrder));
		}else if(LtsSbHelper.isNewAcquistionOrder(pSbOrder)){
				pSectionC.setWorkingDNInd(false);
		} else {
			pSectionC.setWorkingDNInd(true);
		}
		String installTime = LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getAppntStartTime(), appt.getAppntEndTime()));
		
		if(pSectionC.isFieldVisitInd()){
			if (StringUtils.equals(LtsBackendConstant.TECHNOLOGY_PON, pSrvDtlOther.getAssgnTechnology())) {
				pSectionC.setEyeApptTime(LtsDateFormatHelper.convertToPonTime(installTime));
			} else {
				pSectionC.setEyeApptTime(installTime);
			}
		}
		
		//TODO
		/* Display SRD as TBC for eye resources shortage case
		 * Assume tentative but not blacklist case = shortage case*/
		if(LtsSbHelper.isEyeResourceShortage(pSbOrder)){
			String tbcMessage = null;
			if(StringUtils.equals(pSbOrder.getLangPref(), "ENG")){
				tbcMessage = messageSource.getMessage("lts.rpt.tbc", null, null);
			}else{
				tbcMessage = messageSource.getMessage("lts.rpt.tbc.chi", null, null);
			}
			if(StringUtils.isNotBlank(tbcMessage)){
				if(StringUtils.isNotBlank(pSectionC.getEyeInstallDate())){
					pSectionC.setEyeInstallDate(tbcMessage);
				}
				if(StringUtils.isNotBlank(pSectionC.getEyeApptTime())){
					pSectionC.setEyeApptTime(tbcMessage);
				}
				if(StringUtils.isNotBlank(pSectionC.getPipbTargetInstallDate())){
					pSectionC.setPipbTargetInstallDate(tbcMessage);
				}
				if(StringUtils.isNotBlank(pSectionC.getPipbTargetInstallTime())){
					pSectionC.setPipbTargetInstallTime(tbcMessage);
				}
				if(StringUtils.isNotBlank(pSectionC.getPipbTargetSwitchDate())){
					pSectionC.setPipbTargetSwitchDate(tbcMessage);
				}
				if(StringUtils.isNotBlank(pSectionC.getPipbTargetSwitchTime())){
					pSectionC.setPipbTargetSwitchTime(tbcMessage);
				}
				if(StringUtils.isNotBlank(pSectionC.getEyeCutOrderDate())){
					pSectionC.setEyeCutOrderDate(tbcMessage);
				}
				if(StringUtils.isNotBlank(pSectionC.getEyeCutOrderTime())){
					pSectionC.setEyeCutOrderTime(tbcMessage);
				}
			}
			
			
		}
		
		for (int i=0; i<SECTION_C_ITEM_TYPE.length; ++i) {
			itemDescList = this.itemDescMap.get(SECTION_C_ITEM_TYPE[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				itemDesc = itemDescList.get(j);
				
				if (StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, itemDesc.getIoInd())) {
					pSectionC.addEyePlan(itemDesc.getItemDisplayHtml(), itemDesc.getRecurrentAmtTxt(), itemDesc.getMthToMthAmtTxt());
				}
			}
		}

	}
	
	private void generateSectionDReport(SectionDRptDTO pSectionD, ServiceDetailOtherLtsDTO pSrvDtlOther, String pLocale) {
		
		super.generateSectionDReport(pSectionD);
		
		if (pSrvDtlOther.getNowtvDetail() != null) {
			pSectionD.setEyeViewAvInd(pSrvDtlOther.getNowtvDetail().getViewAvInd());
		}
		ChannelDetailLtsDTO[] channels = pSrvDtlOther.getChannels();
		StringBuilder channelDescSb = new StringBuilder();
		String channelDesc = null;
		
		for (int i=0; channels!=null && i<channels.length; ++i) {
			channelDesc = this.offerService.getChannelDescription(channels[i].getChannelId(), pLocale);
			
			if (StringUtils.isNotEmpty(channelDesc)) {
				channelDescSb = new StringBuilder(channelDesc);
				channelDescSb.append(this.getChannelAttbsDesc(channels[i], pLocale));
				pSectionD.addEyeNowTvChannel(channelDescSb.toString());	
			}
		}
	}
	
	private String getChannelAttbsDesc(ChannelDetailLtsDTO pChannel, String pLocale) {
		
		if (ArrayUtils.isEmpty(pChannel.getChannelAttbs())) {
			return "";
		}
		ChannelAttbDTO[][] attbs = this.offerService.getChannelAttbDisplay(pChannel.getChannelId(), pLocale);
		StringBuilder attbDescSb = new StringBuilder();
		
		for (int i=0; i<attbs.length; ++i) {
			attbDescSb.append("<br>   ");
			
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
	
	protected void addItemToSectionD(SectionDRptDTO pSectionD, ItemDetailSummaryDTO pItemDesc) {
		
		int itemQty;
		if (StringUtils.isNotEmpty(pItemDesc.getItemQty()) && (itemQty = Integer.parseInt(pItemDesc.getItemQty())) > 1) {
			pSectionD.addEyeSubscribedItem(pItemDesc.getItemType(), pItemDesc.getItemDisplayHtml(), itemQty, pItemDesc.getRecurrentAmtTxt(), pItemDesc.getMthToMthAmtTxt());
		} else {
			pSectionD.addEyeSubscribedItem(pItemDesc.getItemType(), pItemDesc.getItemDisplayHtml(), pItemDesc.getRecurrentAmtTxt(), pItemDesc.getMthToMthAmtTxt());
		}
	}
	
	protected void addItemToSectionE(SectionERptDTO pSectionE, ItemDetailSummaryDTO pItemDesc) {
		pSectionE.addEyePremiumItem(pItemDesc.getItemDisplayHtml(), pItemDesc.getPenaltyAmtTxt());
	}
	
	protected void generateSectionGReport(SectionGRptDTO pSectionG, ServiceDetailOtherLtsDTO pEyeSrvDtlIms, SbOrderDTO sbOrder) {
		ServiceDetailDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		List<ItemDetailSummaryDTO> itemDescList = null;
		
		for (int i=0; i<SECTION_G_ITEM_TYPE.length; ++i) {
			itemDescList = this.itemDescMap.get(SECTION_G_ITEM_TYPE[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				if (!StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, itemDescList.get(j).getIoInd())) {
					continue;
				}
				if (LtsSbHelper.isOnlineAcquistionOrder(sbOrder)) {
					if (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_ONLINE_EBILL, itemDescList.get(j).getItemType()) ) {
						pSectionG.addEyeBillPreference(itemDescList.get(j).getItemType(), itemDescList.get(j).getItemDisplayHtml());	
					}
				} else if (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_TV_EMAIL, itemDescList.get(j).getItemType()) && pEyeSrvDtlIms.getNowtvDetail() != null) {
					pSectionG.addEyeBillPreference(itemDescList.get(j).getItemType(), itemDescList.get(j).getItemDisplayHtml() + ": " + pEyeSrvDtlIms.getNowtvDetail().getEmailAddress());
				} 
				 else if (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_EMAIL_BILL, itemDescList.get(j).getItemType()) && ltsService.getAccount() != null) {
					pSectionG.addEyeBillPreference(itemDescList.get(j).getItemType(), itemDescList.get(j).getItemDisplayHtml() + ": " + ltsService.getAccount().getEmailAddr());
				} 
				else {
					pSectionG.addEyeBillPreference(itemDescList.get(j).getItemType(), itemDescList.get(j).getItemDisplayHtml());
				}
			}
		}
	}
	
	protected void addItemToSectionF(SectionFRptDTO pSectionF, String pCharge, String pChargeDesc) {
		pSectionF.addEyeItemFixedCharge(pChargeDesc, pCharge);
	}
	
	protected void generateSectionJReport(SectionJRptDTO pSectionJ, ServiceDetailOtherLtsDTO pSrvDtlOther) {
		
		super.generateSectionJReport(pSectionJ);
		
		if (pSrvDtlOther.getNowtvDetail() != null) {
			pSectionJ.setEyeViewAvInd(pSrvDtlOther.getNowtvDetail().getViewAvInd());
		}
	}
	
	protected void addItemToSectionJ(SectionJRptDTO pSectionJ, ItemDetailSummaryDTO pItemDesc) {
		pSectionJ.addEyeSubscribedItem(pItemDesc.getItemType(), pItemDesc.getItemDisplayHtml());
	}
	
	protected void generateSectionKReport(SectionKRptDTO pSectionK,SbOrderDTO psbOrder, ServiceDetailDTO pSrvDtlLst, boolean pIsPrintSignature) {
		super.generateSectionKReport(pSectionK, psbOrder, pSrvDtlLst, pIsPrintSignature);
		
		if (pSrvDtlLst.getRecontract() != null) {
			if (pSrvDtlLst.getRecontract().getFuturePayment() != null) {
				pSectionK.setSalesMemoNum(pSrvDtlLst.getRecontract().getFuturePayment().getSalesMemoNum());
			}
		} else {
			if (pSrvDtlLst.getAccount().getFuturePayment() != null) {
				pSectionK.setSalesMemoNum(pSrvDtlLst.getAccount().getFuturePayment().getSalesMemoNum());
			}
		}
	}
	
	protected void generateSectionInternalUseReport(SectionInternalUseDTO pSectionIntUse, SbOrderDTO pSbOrder, ServiceDetailLtsDTO pEyeLtsSrv, ServiceDetailOtherLtsDTO pEyeImsDtl) {
		
		super.generateSectionInternalUseReport(pSectionIntUse, pSbOrder, pEyeLtsSrv);
		
		String langPref = pSbOrder.getLangPref();
		
		if (LtsSbHelper.isOnlineAcquistionOrder(pSbOrder) ) {
			langPref = StringUtils.equals(pEyeLtsSrv.getAccount().getBillLang(), LtsBackendConstant.ACCT_BILL_LANG_ENGLISH) ? "ENG" : "CHI";
		}
		pSectionIntUse.setRefNum(pEyeImsDtl.getRelatedSbOrderId());
		pSectionIntUse.setVimLang(langPref);

		this.setL2Job(pSectionIntUse, pSbOrder);
		this.setDuplexArrangmentInternalUse(pSectionIntUse, pSbOrder.getSrvDtls());
		this.setOutImsTpInternalUse(pSectionIntUse);
		this.setEyeDetailInternalUse(pSectionIntUse, pEyeLtsSrv);
		this.setFsaDetailInternalUse(pSectionIntUse, pEyeImsDtl);
		this.generateInternalUseRemark(pSectionIntUse, pEyeLtsSrv, pEyeImsDtl);
		pSectionIntUse.setOtherCustRequest(this.generateOtherCustRequest(pEyeImsDtl));
	}
	
	private void setL2Job(SectionInternalUseDTO pSectionIntUse, SbOrderDTO pSbOrder) {
		List<String> l2JobList = lostModemService.getL2Cd(pSbOrder.getOrderId());
		if (l2JobList == null || l2JobList.isEmpty()) {
			return;
		}
		StringBuilder l2JobSb = new StringBuilder();
		for (String l2Job : l2JobList) {
			if (l2JobSb.length() != 0) {
				l2JobSb.append("\n");
			}
			l2JobSb.append(l2Job);
		}
		if (l2JobSb.length() != 0) {
			pSectionIntUse.setImsL2Job(l2JobSb.toString());	
		}
	}
	
	
	private void setEyeDetailInternalUse(SectionInternalUseDTO pSectionIntUse, ServiceDetailLtsDTO pEyeLtsSrv) {
		
		List<ItemDetailSummaryDTO> itemDescList = null;	
		
		if (StringUtils.equals("Y", pEyeLtsSrv.getErInd())) {			
			for (int i=0; i<SECTION_INTERNAL_ER.length; ++i) {
				itemDescList = this.itemDescMap.get(SECTION_INTERNAL_ER[i]);
				
				for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
					
					String erHandle = LtsBackendConstant.LTS_PRODUCT_TYPE_DEL.equals(pEyeLtsSrv.getFromProd()) ? 
							(String)this.erDelHandleCacheService.get(itemDescList.get(j).getPenaltyHandling()) :
								(String)this.erEyeHandleCacheService.get(itemDescList.get(j).getPenaltyHandling());
					
					pSectionIntUse.addExternalRelocation(itemDescList.get(j).getItemDisplayHtml(), erHandle);
				}
			}
		}
		StringBuilder eyeArrangementSb = new StringBuilder();
		eyeArrangementSb.append(pEyeLtsSrv.getFromProd());
		
		if (StringUtils.equals(LtsBackendConstant.LTS_SRV_TYPE_NEW, pEyeLtsSrv.getFromProd())) {
			eyeArrangementSb.append(" ");
		} else {
			eyeArrangementSb.append(" (");
			eyeArrangementSb.append(pEyeLtsSrv.getFromSrvType());
			eyeArrangementSb.append(") to ");
		}
		eyeArrangementSb.append(pEyeLtsSrv.getToProd());
		eyeArrangementSb.append(" (");
		eyeArrangementSb.append(pEyeLtsSrv.getToSrvType());
		eyeArrangementSb.append(")");
		pSectionIntUse.setEyeArrangement(eyeArrangementSb.toString());
	}
	
	private void setFsaDetailInternalUse(SectionInternalUseDTO pSectionIntUse, ServiceDetailOtherLtsDTO pEyeImsDtl) {
		
		if (StringUtils.isNotEmpty(pEyeImsDtl.getExistSrvTypeCd()) && StringUtils.isNotEmpty(pEyeImsDtl.getNewSrvTypeCd()) 
				&& !StringUtils.equals(pEyeImsDtl.getExistSrvTypeCd(), pEyeImsDtl.getNewSrvTypeCd())) {
			pSectionIntUse.setSrvType(pEyeImsDtl.getExistSrvTypeCd() + " to " + pEyeImsDtl.getNewSrvTypeCd());
		}
		if (StringUtils.isNotEmpty(pEyeImsDtl.getExistBandwidth()) && StringUtils.isNotEmpty(pEyeImsDtl.getNewBandwidth()) 
				&& !StringUtils.equals(pEyeImsDtl.getExistBandwidth(), pEyeImsDtl.getNewBandwidth())) {
			pSectionIntUse.setBandwidth(pEyeImsDtl.getExistBandwidth() + "M to " + pEyeImsDtl.getNewBandwidth() + "M");
		}
		if (StringUtils.isNotEmpty(pEyeImsDtl.getExistTechnology()) && StringUtils.isNotEmpty(pEyeImsDtl.getAssgnTechnology()) 
				&& !StringUtils.equals(pEyeImsDtl.getExistTechnology(), pEyeImsDtl.getAssgnTechnology())) {
			pSectionIntUse.setTechnology(this.technologyDescLkupCacheService.get(pEyeImsDtl.getExistTechnology()) + " to " + this.technologyDescLkupCacheService.get(pEyeImsDtl.getAssgnTechnology()));
		}
		if ((StringUtils.isNotBlank(pEyeImsDtl.getSrvNum()) && StringUtils.isNotBlank(pEyeImsDtl.getReplaceExistFsa()) && StringUtils.equals(pEyeImsDtl.getSrvNum(), pEyeImsDtl.getReplaceExistFsa()))
				|| (StringUtils.isBlank(pEyeImsDtl.getSrvNum()) && StringUtils.isNotBlank(pEyeImsDtl.getReplaceExistFsa()))) {
			pSectionIntUse.setFsa("New FSA to replace existing FSA " + pEyeImsDtl.getReplaceExistFsa());
		} else if (StringUtils.isNotBlank(pEyeImsDtl.getSrvNum()) && StringUtils.isNotBlank(pEyeImsDtl.getReplaceExistFsa())) {
			pSectionIntUse.setFsa(pEyeImsDtl.getSrvNum() + " to replace existing FSA " + pEyeImsDtl.getReplaceExistFsa());
		} else {
			pSectionIntUse.setFsa(pEyeImsDtl.getSrvNum());	
		}
		StringBuilder fsaArrangementSb = new StringBuilder();
		
		if (StringUtils.equals(LtsBackendConstant.ORDER_TYPE_INSTALL, pEyeImsDtl.getOrderType())) {
			fsaArrangementSb.append("New ");
			fsaArrangementSb.append(pEyeImsDtl.getToProd());
		} else {
			fsaArrangementSb.append(pEyeImsDtl.getFromProd());
			fsaArrangementSb.append(" to ");
			fsaArrangementSb.append(pEyeImsDtl.getToProd());
		}
		pSectionIntUse.setFsaArrangement(fsaArrangementSb.toString());
	}
	
	private void setDuplexArrangmentInternalUse(SectionInternalUseDTO pSectionIntUse, ServiceDetailDTO[] pSrvDtls) {
		
		for (int i=0; i < pSrvDtls.length; ++i) {
			if (!(pSrvDtls[i] instanceof ServiceDetailLtsDTO)) {
				continue;
			}
			if (LtsBackendConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO)pSrvDtls[i]).getFromSrvType())
					&& LtsBackendConstant.LTS_SRV_TYPE_REMOVE.equals(((ServiceDetailLtsDTO)pSrvDtls[i]).getToSrvType())) {
				pSectionIntUse.setCancelDnType("DNX");
				pSectionIntUse.setCancelDnNum(pSrvDtls[i].getSrvNum());
			} else if (LtsBackendConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO)pSrvDtls[i]).getFromSrvType())
					&& LtsBackendConstant.LTS_SRV_TYPE_REMOVE.equals(((ServiceDetailLtsDTO)pSrvDtls[i]).getToSrvType())) {
				pSectionIntUse.setCancelDnType("DNY");
				pSectionIntUse.setCancelDnNum(pSrvDtls[i].getSrvNum());
			}
		}
	}
	
	private void setOutImsTpInternalUse(SectionInternalUseDTO pSectionIntUse) {
		
		List<ItemDetailSummaryDTO> itemDescList = null;
		String penaltyHandling = null;
		
		for (int i=0; i<SECTION_INTERNAL_USE_OUT_IMS.length; ++i) {
			itemDescList = this.itemDescMap.get(SECTION_INTERNAL_USE_OUT_IMS[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				if (StringUtils.equals(LtsBackendConstant.IO_IND_OUT, itemDescList.get(j).getIoInd())
						&& !LtsBackendConstant.ITEM_TYPE_RENTAL_ROUTER.equals(itemDescList.get(j).getItemType())) {
					if (StringUtils.isBlank(itemDescList.get(j).getPenaltyHandling())) {
						penaltyHandling = "Free to go";
					} else {
						penaltyHandling = (String)this.penaltyHandleLtsLkupCacheService.get(itemDescList.get(j).getPenaltyHandling());
					}
					pSectionIntUse.addOutImsTp(itemDescList.get(j).getItemDisplayHtml(), penaltyHandling);
				}
			}
		}
	}
	
	private void generateInternalUseRemark(SectionInternalUseDTO pSectionIntUse, ServiceDetailLtsDTO pEyeLtsSrv, ServiceDetailOtherLtsDTO pEyeImsDtl) {
		
		StringBuilder orderRemarkSb = new StringBuilder();
		StringBuilder custRemarkSb = new StringBuilder();
		this.appendRemark(custRemarkSb, pEyeLtsSrv.remarkToString(LtsBackendConstant.REMARK_TYPE_CUST_REMARK), null);
		this.appendRemark(custRemarkSb, pEyeImsDtl.remarkToString(LtsBackendConstant.REMARK_TYPE_CUST_REMARK), null);
		this.appendRemark(orderRemarkSb, pEyeLtsSrv.remarkToString(LtsBackendConstant.REMARK_TYPE_ORDER_REMARK), "LTS Order Remarks:");
		this.appendRemark(orderRemarkSb, pEyeImsDtl.remarkToString(LtsBackendConstant.REMARK_TYPE_ORDER_REMARK), "IMS Order Remarks:");
		this.appendRemark(orderRemarkSb, pEyeLtsSrv.remarkToString(LtsBackendConstant.REMARK_TYPE_ADD_ON_REMARK), "Field Service Add-on Remarks: ");
		pSectionIntUse.setCustomerRemarks(custRemarkSb.toString());
		pSectionIntUse.setOrderRemarks(orderRemarkSb.toString());
		pSectionIntUse.setEarliestSRDReason(pEyeLtsSrv.remarkToString(LtsBackendConstant.REMARK_TYPE_EARLIEST_SRD_REMARK));
	}
	
	private String generateOtherCustRequest(ServiceDetailOtherLtsDTO pEyeImsDtl) {
		
		StringBuilder sb = new StringBuilder();
		
		if (StringUtils.equals("Y", pEyeImsDtl.getErInd())) {
			sb.append("FSA Relocation");
		}
		return sb.toString();
	}

	public CodeLkupCacheService getTechnologyDescLkupCacheService() {
		return technologyDescLkupCacheService;
	}

	public void setTechnologyDescLkupCacheService(
			CodeLkupCacheService technologyDescLkupCacheService) {
		this.technologyDescLkupCacheService = technologyDescLkupCacheService;
	}

	public CodeLkupCacheService getRemarkTemplateCacheService() {
		return remarkTemplateCacheService;
	}

	public void setRemarkTemplateCacheService(
			CodeLkupCacheService remarkTemplateCacheService) {
		this.remarkTemplateCacheService = remarkTemplateCacheService;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public LostModemService getLostModemService() {
		return lostModemService;
	}

	public void setLostModemService(LostModemService lostModemService) {
		this.lostModemService = lostModemService;
	}
	
}
