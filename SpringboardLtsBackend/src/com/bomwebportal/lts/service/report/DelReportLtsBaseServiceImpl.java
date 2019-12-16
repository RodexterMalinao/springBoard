package com.bomwebportal.lts.service.report;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.EyeAppFormRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionCRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionDRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionERptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionFRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionGRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionInternalUseDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionJRptDTO;

public abstract class DelReportLtsBaseServiceImpl extends ReportLtsBaseServiceImpl {

	protected abstract ServiceDetailLtsDTO getDelService(SbOrderDTO pSbOrder);
	
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO pSbOrder, String pLocale, String pRptName, boolean pIsEditable, boolean pIsPrintSignature) {

		EyeAppFormRptDTO rptDTO = (EyeAppFormRptDTO)pReportDTO;
		ServiceDetailLtsDTO delSrvDtl = this.getDelService(pSbOrder);
		boolean internalUse = rptDTO.isPrintTermsCondition()? false : true;
		boolean isDelFree = false;
		
		try {
			if (delSrvDtl != null) {
				//Duplex Number Change Item ------------Modified by Max.R.Meng
				ItemDetailLtsDTO[] itemDtls = delSrvDtl.getItemDtls();
				ServiceDetailLtsDTO duplexChangeDtl = LtsSbHelper.getDuplexChangeService(pSbOrder.getSrvDtls());
		
				if(duplexChangeDtl != null){
					if(LtsBackendConstant.ORDER_TYPE_SB_UPGRADE.equals(pSbOrder.getOrderType())){
						for(int i= 0; duplexChangeDtl.getItemDtls() != null && i<duplexChangeDtl.getItemDtls().length; i++){
							if(LtsBackendConstant.ITEM_TYPE_DNY_CHANGE.equals(duplexChangeDtl.getItemDtls()[i].getItemType())
									|| LtsBackendConstant.ITEM_TYPE_DN_CHANGE.equals(duplexChangeDtl.getItemDtls()[i].getItemType())){
							itemDtls = (ItemDetailLtsDTO[]) ArrayUtils.addAll(itemDtls, duplexChangeDtl.getItemDtls());
							   
						    }
						}
					}else{
							if( !StringUtils.equals(delSrvDtl.getToProd(),LtsBackendConstant.LTS_SRV_TYPE_2DEL)){
								for(int i= 0; duplexChangeDtl.getItemDtls() != null && i<duplexChangeDtl.getItemDtls().length; i++){
									if(LtsBackendConstant.ITEM_TYPE_DNY_CHANGE.equals(duplexChangeDtl.getItemDtls()[i].getItemType())
											|| LtsBackendConstant.ITEM_TYPE_DN_CHANGE.equals(duplexChangeDtl.getItemDtls()[i].getItemType())){
									itemDtls = (ItemDetailLtsDTO[]) ArrayUtils.addAll(itemDtls, duplexChangeDtl.getItemDtls());
									
								    }
								}
							}
					}
				}
							
				//Duplex Number Change Item ------------Modified by Max.R.Meng--end
				this.initializeItem(itemDtls, pSbOrder.getAppDate(), pLocale, pIsEditable, internalUse);
				rptDTO.setThirdPartyInd(delSrvDtl.getThirdPartyDtl() != null);
				if (pIsPrintSignature) {
					if (StringUtils.equals(LtsBackendConstant.LTS_PRODUCT_TYPE_DEL, delSrvDtl.getToProd())) {
						rptDTO.setResTelCustSignature(delSrvDtl.getThirdPartyDtl() == null ? LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_DEL_CUST) : LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_DEL_3RD_PARTY));	
					} else if (StringUtils.equals(LtsBackendConstant.LTS_PRODUCT_TYPE_2DEL, delSrvDtl.getToProd())) {
						rptDTO.setResTelCustSignature(delSrvDtl.getThirdPartyDtl() == null ? LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_SEC_DEL_CUST) : LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_SEC_DEL_3RD_PARTY));	
					}
				}
			}
			rptDTO.setOrderMode(pSbOrder.getOrderId().substring(0, 1));
			rptDTO.setOrderType(pSbOrder.getOrderType());	
			
			ItemDetailLtsDTO[] srvPlanItem = LtsSbHelper.getItemByType(delSrvDtl.getItemDtls(), LtsBackendConstant.ITEM_TYPE_PLAN);
			isDelFree = getPcdBundleFreeFromSbOrder(srvPlanItem);
			String[] afTypes = new String[] {delSrvDtl.getDeviceType()};
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
			
			
			this.setCspItemType(rptDTO, pSbOrder);
			this.generateReportTerms(rptDTO, afTypes, pLocale, pRptName, pIsEditable);
			this.generateSectionAReport(rptDTO.getSectionA(), delSrvDtl, pSbOrder, pLocale, rptDTO.getOrderMode(), internalUse);
			this.generateSectionCReport(rptDTO.getSectionC(), delSrvDtl, pSbOrder, internalUse);
			this.generateSectionDReport(rptDTO.getSectionD());
			this.generateSectionEReport(rptDTO.getSectionE());
			this.generateSectionFReport(rptDTO.getSectionF(), pLocale);
			this.generateSectionGReport(rptDTO.getSectionG(), delSrvDtl);
			this.generateSectionJReport(rptDTO.getSectionJ());
			this.generateSectionKReport(rptDTO.getSectionK(), pSbOrder, delSrvDtl, pIsPrintSignature);
			this.generateSectionNReport(rptDTO.getSectionN(), delSrvDtl, rptDTO.getOrderMode(), internalUse);
			this.generateSectionLReport(rptDTO.getSectionL(), pSbOrder);
			this.generateSectionPReport(rptDTO.getSectionP(), pSbOrder);
			this.generateSectionQReport(rptDTO.getSectionQ(), pSbOrder);			
			this.generateSectionInternalUseReport(rptDTO.getSectionInternalUse(), pSbOrder, delSrvDtl);
			
//			if(isDelFree)
//			{
//				delSrvDtl.setDeviceType(tempDeviceType);
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
	
	private void generateSectionCReport(SectionCRptDTO pSectionC, ServiceDetailLtsDTO pSrvDtlLst, SbOrderDTO sbOrder, boolean pInternalUse) {
		
		AppointmentDetailLtsDTO appt = pSrvDtlLst.getAppointmentDtl();
		ServiceDetailLtsDTO pipbService = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls());
		List<ItemDetailSummaryDTO> itemDescList = null;
		ItemDetailSummaryDTO itemDesc = null;
		boolean is2ndDel = LtsSbHelper.is2ndDelService(pSrvDtlLst);
		boolean isPreInstall = LtsSbHelper.isPreInstall(sbOrder);
		
		pSectionC.setResTelInstallDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getAppntStartTime()));
		if(isPreInstall)
		{
			pSectionC.setResTelSrvNum(pSrvDtlLst.getNewSrvNum()!=null?pSrvDtlLst.getNewSrvNum().substring(4):null);
			pSectionC.setResTelPreinstallation(true);
		}
		else
		{
			pSectionC.setResTelSrvNum(pSrvDtlLst.getSrvNum()!=null?pSrvDtlLst.getSrvNum().substring(4):null);
		}
		
		pSectionC.setFieldVisitInd("Y".equals(pSrvDtlLst.getForceFieldVisitInd())? true:false);
		pSectionC.setNewSrvNum(pSrvDtlLst.getNewSrvNum()!=null?pSrvDtlLst.getNewSrvNum().substring(4):null);
		if(pSectionC.isFieldVisitInd()){
			pSectionC.setResTelApptTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getAppntStartTime(), appt.getAppntEndTime())));
		}
		if (StringUtils.isNotEmpty(appt.getPreWiringStartTime())
				&& StringUtils.isNotEmpty(appt.getPreWiringEndTime())) {
			pSectionC.setResTelPrewiringDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getPreWiringStartTime()));
			pSectionC.setResTelPrewiringTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getPreWiringStartTime(), appt.getPreWiringEndTime())));
		}
		
		//SET DUPLEX CHANGE NUM----Modified by Max LTS
		if(LtsSbHelper.getDuplexChangeService(sbOrder.getSrvDtls()) != null && !is2ndDel){
			pSectionC.setNewDuplexNum(LtsSbHelper.getNewDuplexNum(sbOrder)!=null? LtsSbHelper.getNewDuplexNum(sbOrder).substring(4) : null);
			pSectionC.setDuplexNum(LtsSbHelper.getDuplexNum(sbOrder) != null? LtsSbHelper.getDuplexNum(sbOrder).substring(4) : null);
		}
		//SET DUPLEX CHANGE NUM----Modified by Max LTS END
		String coreItemType = null;
		
		if (LtsSbHelper.isOnlineAcquistionOrder(sbOrder)) {
			coreItemType = LtsBackendConstant.ITEM_TYPE_PLAN;
		} else if (StringUtils.equals(LtsBackendConstant.LTS_PRODUCT_TYPE_DEL, pSrvDtlLst.getToProd())) {
			coreItemType = LtsBackendConstant.ITEM_TYPE_PLAN;
		} else if (StringUtils.equals(LtsBackendConstant.LTS_PRODUCT_TYPE_2DEL, pSrvDtlLst.getToProd())) {
			coreItemType = LtsBackendConstant.ITEM_TYPE_VAS_2DEL_HOT;
		}
		ItemDetailLtsDTO[] srvPlanItem = LtsSbHelper.getItemByType(pSrvDtlLst.getItemDtls(),  coreItemType);
		
		pSectionC.setExDirectory(StringUtils.equals(pSrvDtlLst.getExDirInd(), "Y"));
		try {
			if (LtsSbHelper.isOnlineAcquistionOrder(sbOrder)) {
				pSectionC.setPortIn(LtsSbHelper.isPortInOrder(sbOrder));
			}
			
			if(srvPlanItem != null && srvPlanItem.length > 0){
				if (LtsBackendConstant.ITEM_TYPE_VAS_2DEL_HOT.equals(coreItemType)) {
					pSectionC.setResTelContractPeriod(this.offerService.getItemContractPeriod(srvPlanItem[0].getItemId()));
				}
				else {
					pSectionC.setResTelContractPeriod(this.basketDetailService.getBasketContractPeriod(srvPlanItem[0].getBasketId()));
				}
			}
			
		} catch (Exception e) {
			throw new AppRuntimeException(e.getCause());
		}
		if(LtsSbHelper.isNewAcquistionOrder(sbOrder) && pipbService != null){
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
				}else{
					pSectionC.setResTelCutOrderDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getCutOverStartTime()));
					pSectionC.setResTelCutOrderTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getCutOverStartTime(), appt.getCutOverEndTime()));
				}
		    }
		}else{
			pSectionC.setResTelCutOrderDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getCutOverStartTime()));
			pSectionC.setResTelCutOrderTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getCutOverStartTime(), appt.getCutOverEndTime()));
		}
	
		if (LtsSbHelper.isOnlineAcquistionOrder(sbOrder)) {
			pSectionC.setWorkingDNInd(LtsSbHelper.isPortInOrder(sbOrder));	
		}
		else if(LtsSbHelper.isNewAcquistionOrder(sbOrder)){
			pSectionC.setWorkingDNInd(false);
		}else{
			pSectionC.setWorkingDNInd(!StringUtils.equals("Y", pSrvDtlLst.getReservedDnInd()));
		}
		
//		pSectionC.setResTelPrewiringDate(LtsDateFormatHelper.getDateFromDTOFormat(appt.getPreWiringStartTime()));
//		pSectionC.setResTelPrewiringTime(LtsDateFormatHelper.getFromToTimeFormat(appt.getPreWiringStartTime(), appt.getPreWiringEndTime()));
		
		for (int i=0; i<SECTION_C_ITEM_TYPE.length; ++i) {
			itemDescList = this.itemDescMap.get(SECTION_C_ITEM_TYPE[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				itemDesc = itemDescList.get(j);
				
				if (StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, itemDesc.getIoInd())) {
					pSectionC.addResTelPlan(itemDesc.getItemDisplayHtml(), itemDesc.getRecurrentAmtTxt(), itemDesc.getMthToMthAmtTxt());
				}
			}
		}

	}
	
	protected void addItemToSectionD(SectionDRptDTO pSectionD, ItemDetailSummaryDTO pItemDesc) {

		int itemQty;
		
		if (StringUtils.isNotEmpty(pItemDesc.getItemQty()) && (itemQty = Integer.parseInt(pItemDesc.getItemQty())) > 1) {
			pSectionD.addResTelSubscribedItem(pItemDesc.getItemType(), pItemDesc.getItemDisplayHtml(), itemQty, pItemDesc.getRecurrentAmtTxt(), pItemDesc.getMthToMthAmtTxt());
		} else {
			pSectionD.addResTelSubscribedItem(pItemDesc.getItemType(), pItemDesc.getItemDisplayHtml(), pItemDesc.getRecurrentAmtTxt(), pItemDesc.getMthToMthAmtTxt());
		}
	}
	
	protected void addItemToSectionF(SectionFRptDTO pSectionF, String pCharge, String pChargeDesc) {
		pSectionF.addResTelItemFixedCharge(pChargeDesc, pCharge);
	}
	
	protected void generateSectionGReport(SectionGRptDTO pSectionG, ServiceDetailDTO ltsService) {
		
		List<ItemDetailSummaryDTO> itemDescList = null;
		
		for (int i=0; i<SECTION_G_ITEM_TYPE.length; ++i) {
			itemDescList = this.itemDescMap.get(SECTION_G_ITEM_TYPE[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				if (StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, itemDescList.get(j).getIoInd())) {
					if (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_EMAIL_BILL, itemDescList.get(j).getItemType()) && ltsService.getAccount() != null) {
						pSectionG.addResTelBillPreference(itemDescList.get(j).getItemType(), itemDescList.get(j).getItemDisplayHtml() + ": " + ltsService.getAccount().getEmailAddr());
					} 
					else {
						pSectionG.addResTelBillPreference(itemDescList.get(j).getItemType(), itemDescList.get(j).getItemDisplayHtml());	
					}
					
				}
			}
		}
	}
	
	protected void addItemToSectionE(SectionERptDTO pSectionE, ItemDetailSummaryDTO pItemDesc) {
		pSectionE.addResTelPremiumItem(pItemDesc.getItemDisplayHtml(), pItemDesc.getPenaltyAmtTxt());
	}
	
	protected void addItemToSectionJ(SectionJRptDTO pSectionJ, ItemDetailSummaryDTO pItemDesc) {
		pSectionJ.addResTelSubscribedItem(pItemDesc.getItemType(), pItemDesc.getItemDisplayHtml());
	}
	
	protected void generateSectionInternalUseReport(SectionInternalUseDTO pSectionIntUse, SbOrderDTO pSbOrder, ServiceDetailLtsDTO pSrvDtlLst) {
		
		super.generateSectionInternalUseReport(pSectionIntUse, pSbOrder, pSrvDtlLst);
		ServiceDetailLtsDTO ltsSrv = LtsSbHelper.getLtsService(pSbOrder);
		
		if (ltsSrv != null) {
			pSectionIntUse.setEyeSrvNum(ltsSrv.getSrvNum());	
		}
		this.generateInternalUseRemark(pSectionIntUse, pSrvDtlLst);
	}
	
	private void generateInternalUseRemark(SectionInternalUseDTO pSectionIntUse, ServiceDetailLtsDTO pSrvDtlLst) {
		
		StringBuilder orderRemarkSb = new StringBuilder();
		this.appendRemark(orderRemarkSb, pSrvDtlLst.remarkToString(LtsBackendConstant.REMARK_TYPE_ORDER_REMARK), null);
		this.appendRemark(orderRemarkSb, pSrvDtlLst.remarkToString(LtsBackendConstant.REMARK_TYPE_ADD_ON_REMARK), "Field Service Add-on Remarks: ");
		pSectionIntUse.setCustomerRemarks(pSrvDtlLst.remarkToString(LtsBackendConstant.REMARK_TYPE_CUST_REMARK));
		pSectionIntUse.setEarliestSRDReason(pSrvDtlLst.remarkToString(LtsBackendConstant.REMARK_TYPE_EARLIEST_SRD_REMARK));
		pSectionIntUse.setOrderRemarks(orderRemarkSb.toString());
	}
}
