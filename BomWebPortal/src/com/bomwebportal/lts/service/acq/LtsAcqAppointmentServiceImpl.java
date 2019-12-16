package com.bomwebportal.lts.service.acq;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LtsAppointmentDAO;
import com.bomwebportal.lts.dao.PremiumSetDetailDAO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO.DuplexAction;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Selection;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.BmoDetailOutput;
import com.bomwebportal.lts.dto.srvAccess.BmoPermitDetail;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceTypeDTO;
import com.bomwebportal.lts.service.bom.BomOrderStatusSynchService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsDrgService;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsAcqAppointmentServiceImpl implements LtsAcqAppointmentService {

	private CodeLkupCacheService ltsAppointmentCutOffLkupCacheService;
	private CodeLkupCacheService ltsAppointmentBasketBasicTLkupCacheService;
	private LtsAppointmentDAO ltsAppointmentDAO;
	private AppointmentDwfmService appointmentDwfmService;
	private BomOrderStatusSynchService bomOrderStatusSynchService;
	private ServiceProfileLtsDrgService serviceProfileLtsDrgService;
	private ImsProfileService imsProfileService;
	private PremiumSetDetailDAO premiumSetDetailDao;
	
	private final String PROD_SUB_TYPE_CD_V = "VSO-";
	private final String SRV_TYPE_CD_SO = "SO";

	public LtsSRDDTO getEarliestSRD(AcqOrderCaptureDTO pOrder, String pAppDate) {
		return getEarliestSRD(pOrder, pAppDate, false);
	}
	
	public LtsSRDDTO getEarliestSRD(AcqOrderCaptureDTO pOrder, String pAppDate, boolean ignore2NBW) {
		ArrayList<LtsSRDDTO> srdInfos = new ArrayList<LtsSRDDTO>();
		LtsSRDDTO startDate = new LtsSRDDTO(pAppDate);
		startDate.setInfo("");
		String startDateString = startDate.getDateString();
		int fieldPermit = getFieldPermitRequired(pOrder);
		BmoPermitDetail bmoPermit = getBmoPermitLeadDays(pOrder, startDateString);
		
		if(bmoPermit != null && !StringUtils.equals("0", bmoPermit.getPermitLeadDays())){
			int permetLeadDays = Integer.parseInt(bmoPermit.getPermitLeadDays());
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, permetLeadDays, "Field Permit Required ", "01");
			pOrder.getLtsAcqAppointmentForm().setBmoAlertMsg(LtsSbOrderHelper.newLineHtmlFilter(LtsSbOrderHelper.quotationFilter(bmoPermit.getAlertMsg())));
			pOrder.getLtsAcqAppointmentForm().setBmoRemark(LtsSbOrderHelper.newLineHtmlFilter(LtsSbOrderHelper.quotationFilter(bmoPermit.getBmoRemark())));
			srdInfos.add(srd);
		}else
		if(fieldPermit != 0){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, fieldPermit, "Field Permit Required ", "01");
			srdInfos.add(srd);
		}
		//BUILD3.200 remove ims blacklist checking 
		/*if(isIMSBlacklistAddress(pOrder) && pOrder.isEyeOrder()){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 30, "IMS Blacklist Address ", "02");
			srdInfos.add(srd);
		}else */
		if(isLTSBlacklistAddress(pOrder)){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 30, "LTS Blacklist Address ", "08");
			srdInfos.add(srd);
		}else if(isBlacklistCustomer(pOrder)){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 30, "Blacklist Customer ", "20");
			srdInfos.add(srd);
		}else if(!ignore2NBW && is2NBW(pOrder)){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 0, "2N Blockwiring ", "11");
			LtsAppointmentHelper.pushWorkingDays(srd, 14, false, getPublicHolidayList());
			srdInfos.add(srd);
		}
		
		BasketDetailDTO selectedBasket = pOrder.getSelectedBasket();
		if (selectedBasket != null) {
			LtsSRDDTO srd = getSpecificBasketSrd(selectedBasket, startDateString);
			if(srd != null){
				srdInfos.add(srd);
			}
		}
		
		if(pOrder.isChannelDirectSales() && isEyeOrderAgeOver(pOrder)
				&& pOrder.getOrderAction() != LtsConstant.ORDER_ACTION_RECALL_N_UPDATE_APPOINTMENT){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 30, "51");
			srdInfos.add(srd);
		}
		
		if(pOrder.isContainPreInstallVAS())
		{
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 2, "DEL Lead Time ", "16" );
			srdInfos.add(srd);
		}

		if(pOrder.getLtsAcqPersonalInfoForm()!= null
				&& LtsConstant.DOC_TYPE_PASSPORT.equals(pOrder.getLtsAcqPersonalInfoForm().getDocumentType())
				&& pOrder.getLtsAcqPaymentMethodFormDTO() != null){
			
			for(PaymentMethodDtl payMtdDtl: pOrder.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList()){
				if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(payMtdDtl.getNewPayMethodType())
						&& payMtdDtl.getCreditCardPrePayItem() != null
						&& payMtdDtl.getCreditCardPrePayItem().isSelected()){
					srdInfos.add(getPassportWithPrepaymentSrd(startDateString));
					break;
				}
			}
			
		}
		
		srdInfos.addAll(getNormalLeadTime(pAppDate, false, pOrder));
		
		LtsSRDDTO earliestSRDInfo = Collections.max(srdInfos);
		earliestSRDInfo.setBmoPermit(bmoPermit);
		return earliestSRDInfo;
	}
	
	public LtsSRDDTO getPipbEarlisetSRD(AcqOrderCaptureDTO pOrder, String pAppDate){
		ArrayList<LtsSRDDTO> srdInfos = new ArrayList<LtsSRDDTO>();
		String startDateString = pAppDate;

		
		/*Calc PIPB lead day*/
		int minPipbDay = LtsSbOrderHelper.getPipbMinDay();
		String pipbCutOverTime = (String)ltsAppointmentCutOffLkupCacheService.get("ACQ_PIPB_CUT_OVER_TIME");
		if(StringUtils.isNotBlank(pipbCutOverTime)){
			if(LocalTime.now().isAfter(LocalTime.parse(pipbCutOverTime, DateTimeFormat.forPattern("HH:mm")))){
				minPipbDay++; //add extra lead day if after cut over time (21:00)
			}
		}
		LtsSRDDTO pipbNormalLeadTime = new LtsSRDDTO(startDateString, minPipbDay, "12");
		srdInfos.add(pipbNormalLeadTime);
		
		int fieldPermit = getFieldPermitRequired(pOrder);
		BmoPermitDetail bmoPermit = getBmoPermitLeadDays(pOrder, startDateString);
		if(bmoPermit != null && !StringUtils.equals("0", bmoPermit.getPermitLeadDays())){
			int permetLeadDays = Integer.parseInt(bmoPermit.getPermitLeadDays());
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, permetLeadDays, "Field Permit Required ", "01");
			pOrder.getLtsAcqAppointmentForm().setBmoAlertMsg(LtsSbOrderHelper.newLineHtmlFilter(LtsSbOrderHelper.quotationFilter(bmoPermit.getAlertMsg())));
			pOrder.getLtsAcqAppointmentForm().setBmoRemark(LtsSbOrderHelper.newLineHtmlFilter(LtsSbOrderHelper.quotationFilter(bmoPermit.getBmoRemark())));
			srdInfos.add(srd);
		}else
		if(fieldPermit != 0){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, fieldPermit, "Field Permit Required ", "01");
			srdInfos.add(srd);
		}
//		if(isPipbDnExistInDrg(pOrder)){
//			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 9, "12");
//			sRDInfos.add(srd);
//		}
		if(is2NBW(pOrder)){
			//Need to clarify to push working days or not
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 0, "2N Blockwiring ", "11");
			LtsAppointmentHelper.pushWorkingDays(srd, 14, false, getPublicHolidayList());
			srdInfos.add(srd);
		}
		//BUILD3.200 remove ims blacklist checking 
		/*if(isIMSBlacklistAddress(pOrder) && pOrder.isEyeOrder()){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 30, "IMS Blacklist Address ", "02");
			srdInfos.add(srd);
		}else */
		if(isLTSBlacklistAddress(pOrder)){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 30, "LTS Blacklist Address ", "08");
			srdInfos.add(srd);
		}else if(isBlacklistCustomer(pOrder)){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 30, "Blacklist Customer ", "20");
			srdInfos.add(srd);
		}

		if(pOrder.isChannelDirectSales() && isEyeOrderAgeOver(pOrder)
				&& pOrder.getOrderAction() != LtsConstant.ORDER_ACTION_RECALL_N_UPDATE_APPOINTMENT
				&& pOrder.getOrderAction() != LtsConstant.ORDER_ACTION_UPDATE_APPOINTMENT_FOR_QC){
			LtsSRDDTO srd = new LtsSRDDTO(startDateString, 30, "51");
			srdInfos.add(srd);
		}

		BasketDetailDTO selectedBasket = pOrder.getSelectedBasket();
		if (selectedBasket != null) {
			LtsSRDDTO srd = getSpecificBasketSrd(selectedBasket, startDateString);
			if(srd != null){
				srdInfos.add(srd);
			}
		}
		
		LtsSRDDTO pipbEarliestSRDInfo = Collections.max(srdInfos);
		
		if(!LtsAppointmentHelper.tentativeReasonCodeList.contains(pipbEarliestSRDInfo.getReasonCd())) {
			StringBuilder sb = new StringBuilder();
			if(!isPipbDnExistInDrg(pOrder)){
				LtsAppointmentHelper.addExtraDaysToSRD(sb, pipbEarliestSRDInfo, 2, "18");
			}
			if(isSecDelPorting(pOrder)){
				LtsAppointmentHelper.addExtraDaysToSRD(sb, pipbEarliestSRDInfo, 2, "19");
			}
			if(isFsaEr(pOrder)){
				LtsAppointmentHelper.addExtraDaysToSRD(sb, pipbEarliestSRDInfo, 2, "40");
			}
			
			if(sb.length() > 0){
				sb.append(pipbEarliestSRDInfo.getInfo());
				pipbEarliestSRDInfo.setInfo(sb.toString());
			}
		}
		pipbEarliestSRDInfo.setBmoPermit(bmoPermit);
		return pipbEarliestSRDInfo;
	}
	
	private LtsSRDDTO getSpecificBasketSrd(BasketDetailDTO selectedBasket, String startDateString ){
		LookupItemDTO[] basicTArray;
		try {
			basicTArray = ltsAppointmentBasketBasicTLkupCacheService.getCodeLkupDAO().getCodeLkup();
			if(basicTArray != null){
				for(LookupItemDTO deviceMinT: basicTArray){
					if(StringUtils.equals(StringUtils.defaultIfEmpty(selectedBasket.getBaseBasketId(), selectedBasket.getBasketId()), deviceMinT.getItemKey())){
						String minTString = (String)deviceMinT.getItemValue();
						int minT = Integer.parseInt(minTString);
						LtsSRDDTO srd = new LtsSRDDTO(startDateString, minT, "Selected Specific Basket", "32");
						return srd;
					}
				}
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private boolean isFsaEr(AcqOrderCaptureDTO pOrder){
		if(pOrder.getLtsModemArrangementForm() == null){
			return false;
		}
		
		if (ModemType.SHARE_EX_FSA == pOrder.getLtsModemArrangementForm().getModemType()) {
			return pOrder.getLtsModemArrangementForm().isExistingFsaER();
		}
		if (ModemType.SHARE_OTHER_FSA == pOrder.getLtsModemArrangementForm().getModemType()) {
			return pOrder.getLtsModemArrangementForm().isOtherFsaER();
		}
		
		return false;
		
	}
	
	private boolean isSecDelPorting(AcqOrderCaptureDTO pOrder){
		LtsAcqPipbInfoDTO acqPipb = pOrder.getLtsAcqNumSelectionAndPipbForm().getPipbInfo();
		if(acqPipb != null
				&& acqPipb.isDuplexInd()
				&& acqPipb.getDuplexAction() == DuplexAction.PORT_IN_TOGETHER){
			return true;
		}
		return false;
	}
	
	private boolean isPipbDnExistInDrg(AcqOrderCaptureDTO pOrder){
		LtsAcqPipbInfoDTO acqPipb = pOrder.getLtsAcqNumSelectionAndPipbForm().getPipbInfo();
		if(acqPipb != null){
			return StringUtils.isNotEmpty(acqPipb.getDnStatus());
		}
		return false;
	}
	
	private boolean isFfpSelected(AcqOrderCaptureDTO order){
		if(order.getSbOrder() != null){
			return LtsSbOrderHelper.isInstallFfpItem(order.getSbOrder()) 
						|| LtsSbOrderHelper.isInstallFreeCallPlanItem(order.getSbOrder());
		}
		return order.isContainFfpVAS();
	}

	private boolean isBlacklistCustomer(AcqOrderCaptureDTO order){
		if(order.getCustomerDetailProfileLtsDTO() != null){
			return order.getCustomerDetailProfileLtsDTO().isBlacklistCustInd();
		}
		return false;
	}
	
	public LtsSRDDTO getSecDelEarliestSRD(AcqOrderCaptureDTO order, String pAppDate, String pOCID, LtsSRDDTO primaryEarliestSrd) {		
		ArrayList<LtsSRDDTO> srdInfos = new ArrayList<LtsSRDDTO>();
		
		if(isNewSecDel2NBW(order)){
			LtsSRDDTO twoNBWSrd = new LtsSRDDTO(pAppDate);
			twoNBWSrd.setInfo("");
			calculatePendingOrder(twoNBWSrd, order.getSecondDelServiceProfile(), order.getModemTechnologyAssign(), getSelectedFsa(order), pOCID);
			twoNBWSrd.setInfo("New 2nd Del 2NBW ");
			twoNBWSrd.setReasonCd("04");
			LtsAppointmentHelper.pushWorkingDays(twoNBWSrd, 14, false, getPublicHolidayList());
			
			srdInfos.add(twoNBWSrd);
		}
		
		if(primaryEarliestSrd != null){
			if(!primaryEarliestSrd.getSRDate().isBefore(DateTime.parse(pAppDate, DateTimeFormat.forPattern("DD/MM/YYYY")))){
				LtsSRDDTO primarySrd = new LtsSRDDTO(pAppDate);
				primarySrd.setSRDate(primaryEarliestSrd.getSRDate());
				primarySrd.setInfo("Primary SRD ");

				srdInfos.add(primarySrd);
			}
		}
		
		srdInfos.add(new LtsSRDDTO(pAppDate, 2, "00"));
		
		return Collections.max(srdInfos);
	}
	
	public List<LtsSRDDTO> getNormalLeadTime(String pAppDate, boolean isAmendment, AcqOrderCaptureDTO pOrder){
		String cutOffTimeString = null;
//		boolean isCC = pOrder.isChannelCs();
		boolean isPT = pOrder.isChannelPremier();
		boolean isDS = pOrder.isChannelDirectSales();
		String deviceType = pOrder.getLtsAcqBasketSelectionForm() != null? pOrder.getLtsAcqBasketSelectionForm().getSelectedType(): null;
		boolean isFfp = isFfpSelected(pOrder);
		
		cutOffTimeString 	= (String)ltsAppointmentCutOffLkupCacheService.get("ACQ_DEL_CUT_OVER_TIME");
		int defaultLeadTime = NumberUtils.toInt((String) ltsAppointmentCutOffLkupCacheService.get("DEFAULT"));
		int beforeCutOff 	= NumberUtils.toInt((String) ltsAppointmentCutOffLkupCacheService.get("ACQ_DEL_BEFORE_CUT_OVER"));
		int afterCutOff	 	= NumberUtils.toInt((String) ltsAppointmentCutOffLkupCacheService.get("ACQ_DEL_AFTER_CUT_OVER"));
		int eyeNormal 		= NumberUtils.toInt((String) ltsAppointmentCutOffLkupCacheService.get("ACQ_EYE_NORMAL"));
		int ptLeadTime 		= NumberUtils.toInt((String) ltsAppointmentCutOffLkupCacheService.get("ACQ_PT_NORMAL"));
		int ffpNormal 		= NumberUtils.toInt((String) ltsAppointmentCutOffLkupCacheService.get("ACQ_FFP_NORMAL"));
		int dsCustNameDiffLeadTime 	= NumberUtils.toInt((String) ltsAppointmentCutOffLkupCacheService.get("ACQ_DS_CUST_NAME_DIFF"));

		List<LtsSRDDTO> srdList = new ArrayList<LtsSRDDTO>();
		
		if(StringUtils.equals(deviceType, LtsConstant.DEVICE_TYPE_EYE3A) && eyeNormal > 0){
			LtsSRDDTO eye3aNormalLeadTimeSrd = new LtsSRDDTO(pAppDate, eyeNormal, "EYE Normal Lead Time ", "13");
			srdList.add(eye3aNormalLeadTimeSrd);
		}
		if(isPT && ptLeadTime > 0){
			LtsSRDDTO ptSrd = new LtsSRDDTO(pAppDate, ptLeadTime, "Premier Team Lead Time ", "17");
			srdList.add(ptSrd);
		}
		if(isFfp && ffpNormal > 0){
			LtsSRDDTO ptSrd = new LtsSRDDTO(pAppDate, ffpNormal, "FFP Normal Lead Time ", "14");
			srdList.add(ptSrd);
		}
		if(isDS && pOrder.getLtsAcqPersonalInfoForm() != null 
				&& !pOrder.getLtsAcqPersonalInfoForm().isMatchWithBomName() && dsCustNameDiffLeadTime > 0){
			LtsSRDDTO ptSrd = new LtsSRDDTO(pAppDate, dsCustNameDiffLeadTime, "50");
			srdList.add(ptSrd);
		}
		if(srdList.size()== 0){
			LtsSRDDTO normalLeadTimeSrd = new LtsSRDDTO(pAppDate);
			if(isAmendment){
				normalLeadTimeSrd = new LtsSRDDTO(pAppDate, defaultLeadTime, "Normal Lead Time (Amendment) ", "00" );
			}else{
				normalLeadTimeSrd = new LtsSRDDTO(pAppDate, defaultLeadTime, "Normal Lead Time ", "00" );
			}

			if(StringUtils.equals(deviceType, LtsConstant.DEVICE_TYPE_DEL) && !StringUtils.isBlank(cutOffTimeString)){
				if(LocalTime.now().isBefore(LocalTime.parse(cutOffTimeString, DateTimeFormat.forPattern("HH:mm")))){
					normalLeadTimeSrd = new LtsSRDDTO(pAppDate, beforeCutOff, "DEL Lead Time before cut-off time ", "15" );
				}else{
					normalLeadTimeSrd = new LtsSRDDTO(pAppDate, afterCutOff, "DEL Lead Time ", "16" );
				}
			}
			
			srdList.add(normalLeadTimeSrd);
		}
		return srdList;
	}
	
	private void calculatePendingOrder(LtsSRDDTO srd, ServiceDetailProfileLtsDTO serviceDetail, ModemTechnologyAissgnDTO modemTechAssign, FsaDetailDTO fsa, String pOCID){
		
		DateTime pendingOrder = null;
		DateTime ltsPendingOrderDate = null;
		DateTime imsPendingOrderDate = null;
		String ltsPendingOrderId = null; 
		String imsPendingOrderId = null;
		String pendingOrderId = null; 
		
//		df.applyPattern("dd/MM/yyyy");
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy");
		
		if(serviceDetail != null){
			PendingOrdStatusDetailDTO pendingOrderDTO = this.serviceProfileLtsDrgService.getPendingOrder(serviceDetail.getSrvNum(), serviceDetail.getSrvType());
			if(pendingOrderDTO != null){
				String dateString = pendingOrderDTO.getSrd();
				ltsPendingOrderId = pendingOrderDTO.getOcid();
				if(!StringUtils.equals(pOCID, ltsPendingOrderId)){
					if(!StringUtils.isBlank(dateString)){
						ltsPendingOrderDate = DateTime.parse(dateString, format);
					}
				}else{
					ltsPendingOrderDate = null;
				}
			}
		}
		
		if(modemTechAssign != null){
			if(!StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, modemTechAssign.getModemArrangment())){
				if(fsa != null){
					PendingOrdStatusDetailDTO imsPendingOrderDTO = imsProfileService.getPendingOrder(Integer.toString(fsa.getFsa()));
					if(imsPendingOrderDTO != null){
						String dateString = imsPendingOrderDTO.getSrd();
						imsPendingOrderId = imsPendingOrderDTO.getOcid();
						if(!StringUtils.equals(pOCID, imsPendingOrderId)){
							imsPendingOrderDate = DateTime.parse(dateString, format);
						}
					}
				}
			}else{
				imsPendingOrderDate = null;
			}
		}
		
		if(ltsPendingOrderDate != null){
			if(imsPendingOrderDate != null){
				if(ltsPendingOrderDate.isAfter(imsPendingOrderDate)){
					pendingOrder = ltsPendingOrderDate;
					pendingOrderId = ltsPendingOrderId;
				}else{
					pendingOrder = imsPendingOrderDate;
					pendingOrderId = imsPendingOrderId;
				}
			}else{
				pendingOrder = ltsPendingOrderDate;
				pendingOrderId = ltsPendingOrderId;
			}
		}else{
			if(imsPendingOrderDate != null){
				pendingOrder = imsPendingOrderDate;
				pendingOrderId = imsPendingOrderId;
			}
		}
		
		if(pendingOrder != null){
			Calendar pendingOrderSRD = new GregorianCalendar();
			Calendar today = new GregorianCalendar();
			pendingOrderSRD.setTime(pendingOrder.toDate());
			if(pendingOrderSRD.compareTo(today) >= 0){
				pendingOrderSRD.add(Calendar.DATE, srd.getLeadTime());
				srd.setDate(pendingOrderSRD);
				srd.setInfo("PendingOrder ("
						+ pendingOrderId
						+ ") Exist + 2 days "
						+ "& ".concat(srd.getInfo()));
				srd.addDate(2);
			}
		}
	}
	
	public int getFieldPermitRequired(AcqOrderCaptureDTO orderCaptureDTO){
		int fieldPermit = 0;
		if(orderCaptureDTO.getAddressRollout() != null){
			String leadtime = orderCaptureDTO.getAddressRollout().getFieldWorkPermit();
			if(!StringUtils.isBlank(leadtime) && StringUtils.isNumeric(leadtime)){
				fieldPermit = Integer.parseInt(leadtime);
			}
		}
		return fieldPermit;
	}
	
	public BmoPermitDetail getBmoPermitLeadDays(AcqOrderCaptureDTO orderCaptureDTO, String appDate){
		ResourceDetailInputDTO input;
		try{
			input = getResourceDetailInput(orderCaptureDTO, appDate, 
					LtsConstant.DWFM_INPUT_TYPE_ALL);
		}catch(Exception e){
			return null;
		}
		BmoDetailOutput output= appointmentDwfmService.getBmoPermits(input);
//		int largestLeadDay = 0;
		if(output != null && output.getBmoDtls() != null && output.getBmoDtls().length > 0){
			BmoPermitDetail largestBmoLeadDay = output.getBmoDtls()[0];
			for(int i = 0; i < output.getBmoDtls().length; i++){
				int largestLeadDay = Integer.parseInt(largestBmoLeadDay.getPermitLeadDays());
				int currentLeadDay = Integer.parseInt(output.getBmoDtls()[i].getPermitLeadDays());
				largestBmoLeadDay = largestLeadDay > currentLeadDay? largestBmoLeadDay: output.getBmoDtls()[i];
			}
			return largestBmoLeadDay;
		}
		return null;
	}
	
	public List<String> getPublicHolidayList() {
		try {
			List<String> holidayList = ltsAppointmentDAO.getHolidayList();
			return holidayList;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isBBShortage(AcqOrderCaptureDTO orderCaptureDTO) {
		if(orderCaptureDTO.getSbOrder() != null
				&& checkImsL1Status(orderCaptureDTO.getSbOrder())){
			return false;
		}
		
		if(orderCaptureDTO.getLtsModemArrangementForm() == null){
			return false;
		}
		
		if(orderCaptureDTO.getModemTechnologyAssign() != null){
			if(orderCaptureDTO.getModemTechnologyAssign().isBbShortage()){
				String newTech = orderCaptureDTO.getModemTechnologyAssign().getTechnology();
				FsaDetailDTO selectedFsa = getSelectedFsa(orderCaptureDTO);
				if(selectedFsa != null){
					boolean isFsaEr = orderCaptureDTO.getLtsModemArrangementForm().isExistingFsaER()
							|| orderCaptureDTO.getLtsModemArrangementForm().isOtherFsaER();
					String originalTech = selectedFsa.getTechnology();
					if(StringUtils.equals(newTech, originalTech) && !isFsaEr){
						return false;
					}else{
						return true;
					}
				}
			}
			return orderCaptureDTO.getModemTechnologyAssign().isBbShortage();
		}
		return false;
	}
	
	public boolean is2NBW(AcqOrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getAddressRollout() != null){
			return orderCaptureDTO.getAddressRollout().isIs2nBuilding();
		}
		return false;
	}
	
	public boolean isNewSecDel2NBW(AcqOrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO == null || orderCaptureDTO.getLtsAcqOtherVoiceServiceForm() == null){
			return false;
		}
		
		if(orderCaptureDTO.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus().equals(LtsConstant.INVENT_STS_RESERVED)){
			return is2NBW(orderCaptureDTO);
		}else if(orderCaptureDTO.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus().equals(LtsConstant.INVENT_STS_WORKING)
				&& orderCaptureDTO.getLtsAcqOtherVoiceServiceForm().isSecondDelDiffAddress()){
			if(orderCaptureDTO.getLtsAcqOtherVoiceServiceForm().getSecondDelConfirmSameIa() != null
					&& orderCaptureDTO.getLtsAcqOtherVoiceServiceForm().getSecondDelConfirmSameIa()){
				return is2NBW(orderCaptureDTO);
			}
			return is2NBW(orderCaptureDTO);
		}
		
		return false;
	}
	
	private boolean isIMSBlacklistAddress(AcqOrderCaptureDTO orderCaptureDTO){
		/*ignore IMS blacklist if use existing fsa*/
		if(orderCaptureDTO.getLtsModemArrangementForm() != null
				&& ModemType.SHARE_EX_FSA.equals(orderCaptureDTO.getLtsModemArrangementForm().getModemType())){
			return false;
		}
			
		if(orderCaptureDTO.getAddressRollout() != null){
			return orderCaptureDTO.getAddressRollout().isImsAddressBlacklist();
		}
		return false;
	}

	private boolean isLTSBlacklistAddress(AcqOrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getSbOrder() != null
				&& validateLtsLegacyStatus(orderCaptureDTO.getSbOrder())){
			return false;
		}
		if(orderCaptureDTO.getAddressRollout() != null){
			return orderCaptureDTO.getAddressRollout().isLtsAddressBlacklist();
		}
		return false;
	}

	public ResourceDetailInputDTO getResourceDetailInput(
			AcqOrderCaptureDTO orderCaptureDTO, String date, String type){
		
		ResourceDetailInputDTO resourceInput = new ResourceDetailInputDTO();
		
//		type = "ALL", "NEWACQ_EYE", "2NDDEL"
		List<ResourceTypeDTO> resourceTypeDTOList = new ArrayList<ResourceTypeDTO>(); 
		if(StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_ALL)
				|| StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_NEWACQ_EYE)
				|| StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_PREWIRING)){

			String prodSubTypeCd = orderCaptureDTO.isEyeOrder()? getProdSubTypeCd(orderCaptureDTO)[0]: PROD_SUB_TYPE_CD_V;
			String fromProdSubTypeCd = orderCaptureDTO.isEyeOrder()? getProdSubTypeCd(orderCaptureDTO)[1]: PROD_SUB_TYPE_CD_V;
			
			resourceTypeDTOList.add(getResourceTypeDTO(fromProdSubTypeCd, prodSubTypeCd, "I", SRV_TYPE_CD_SO));
		}
		if(StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_ALL)
				|| StringUtils.equals(type, LtsConstant.DWFM_INPUT_TYPE_NEWACQ_DEL)){
			ResourceTypeDTO tempResourceType = getResourceTypeDTO(PROD_SUB_TYPE_CD_V, PROD_SUB_TYPE_CD_V, "V", SRV_TYPE_CD_SO);
			if(orderCaptureDTO.getLtsAcqOtherVoiceServiceForm() != null &&
					!StringUtils.equals(orderCaptureDTO.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus(), LtsConstant.INVENT_STS_RESERVED)){
				tempResourceType.setOrderType(LtsConstant.ORDER_TYPE_CHANGE);
			}else{
				tempResourceType.setOrderType(LtsConstant.ORDER_TYPE_INSTALL);
			}
			resourceTypeDTOList.add(tempResourceType);
		}

		resourceInput.setRscTypes(resourceTypeDTOList.toArray(new ResourceTypeDTO[0]));
		
		resourceInput.setApptDate(LtsAppointmentHelper.reformatToDwfmDate(date));
		String[] addressInfo = getAreaDistrictCd(orderCaptureDTO.getLtsAddressRolloutForm());
		resourceInput.setArea(addressInfo[0]);
		resourceInput.setDistrict(addressInfo[1]);
		resourceInput.setAddrChangeInd(addressInfo[2]);
		resourceInput.setSrvBoundary(addressInfo[3]);
		resourceInput.setDrgPermitDays(String.valueOf(getFieldPermitRequired(orderCaptureDTO)));
		resourceInput.setSystemId("SPB");
		resourceInput.setUser(orderCaptureDTO.getLtsAcqSalesInfoForm().getStaffId());
		return resourceInput;
	}
	
	private ResourceTypeDTO getResourceTypeDTO(String fromProdSubTypeCd, String prodSubTypeCd, String prodType, String srvType){
		ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO();
		resourceTypeDTO.setProdSubTypeCd(prodSubTypeCd);
		resourceTypeDTO.setProdType(prodType);
		resourceTypeDTO.setSrvType(srvType);
		resourceTypeDTO.setFromProdSubTypeCd(fromProdSubTypeCd);
		resourceTypeDTO.setOrderType(StringUtils.substring(prodSubTypeCd, 3, 4));
		return resourceTypeDTO;

	}

	public PrebookAppointmentInputDTO getPrebookAppointmentInput(AcqOrderCaptureDTO orderCaptureDTO, String date, String timeSlot, String staffId, String timeSlotType){
		String dateString = LtsAppointmentHelper.reformatToDwfmDate(date);
		String[] time = timeSlot.split("-");
		String startDate = "";
		String endDate = "";
		String[] startTime = time[0].split(":");
		String[] endTime = time[1].split(":");
		startDate = startDate.concat(dateString).concat(startTime[0]).concat(startTime[1]);
		endDate = endDate.concat(dateString).concat(endTime[0]).concat(endTime[1]);
		PrebookAppointmentInputDTO input = new PrebookAppointmentInputDTO();
		input.setProdSubTypeCd(orderCaptureDTO.isEyeOrder()? getProdSubTypeCd(orderCaptureDTO)[1]: PROD_SUB_TYPE_CD_V);
		String[] area = getAreaDistrictCd(orderCaptureDTO.getLtsAddressRolloutForm());
		input.setArea(area[0]);
		input.setDistrict(area[1]);
		input.setApptDateStartDate(startDate);
		input.setApptDateEndDate(endDate);
		input.setSystemId("SPB");
		input.setUser(staffId);
		input.setApptType(timeSlotType);
		return input;
	}
	
	public String[] getAreaDistrictCd(LtsAddressRolloutFormDTO form ) {
		String[] s = {form.getAreaCode(), form.getDistrictCode(), "N", form.getServiceBoundary()};
		return s;
	}
	
	private String[] getProdSubTypeCd(AcqOrderCaptureDTO orderCaptureDTO){
		String prodSubTypeCd = null;
		String frProdSubTypeCd = null;
		
		if(orderCaptureDTO.getLtsModemArrangementForm() == null){
			String[] delProdSubTypeCd = {PROD_SUB_TYPE_CD_V, PROD_SUB_TYPE_CD_V};
			return delProdSubTypeCd;
		}
		
		ModemType m = orderCaptureDTO.getLtsModemArrangementForm().getModemType();
		if(StringUtils.equals(orderCaptureDTO.getModemTechnologyAssign().getTechnology(), LtsConstant.TECHNOLOGY_PON)){
			if(ModemType.STANDALONE.equals(m)
					||  ModemType.SHARE_TV.equals(m)
					|| ModemType.SHARE_PCD.equals(m)
					|| ModemType.SHARE_BUNDLE.equals(m)){
				prodSubTypeCd = "FTHI";
				frProdSubTypeCd = "FTHI";
			}else if(ModemType.SHARE_EX_FSA.equals(m)){
				if(isInstallUpgradePon(orderCaptureDTO, true)){
					prodSubTypeCd = "FTHI";
				}else{
					prodSubTypeCd = "FTHC";
				}
				frProdSubTypeCd = "FTHC";
			}else if(ModemType.SHARE_OTHER_FSA.equals(m)){
				if(isInstallUpgradePon(orderCaptureDTO, false)){
					prodSubTypeCd = "FTHI";
				}else{
					prodSubTypeCd = "FTHC";
				}
				frProdSubTypeCd = "FTHC";
			}
		}else{
			if(ModemType.STANDALONE.equals(m)
					|| ModemType.SHARE_TV.equals(m)
					|| ModemType.SHARE_PCD.equals(m)
					|| ModemType.SHARE_BUNDLE.equals(m)){
				prodSubTypeCd = "PCDI";
				frProdSubTypeCd = "PCDI";
			}else if(ModemType.SHARE_EX_FSA.equals(m)
					|| ModemType.SHARE_OTHER_FSA.equals(m)){
				prodSubTypeCd = "PCDC";
				frProdSubTypeCd = "PCDC";
			}
		}
		
		String[] prodSubTypeCds = {prodSubTypeCd, frProdSubTypeCd};
		return prodSubTypeCds;
	}
	
	private boolean isInstallUpgradePon(AcqOrderCaptureDTO orderCaptureDTO, boolean pExistFsaInd){	
		
		if(orderCaptureDTO.getLtsModemArrangementForm() == null){
			return false;
		}
		
		boolean ponInd = StringUtils.equals(orderCaptureDTO.getModemTechnologyAssign().getTechnology(), LtsConstant.TECHNOLOGY_PON);
		FsaDetailDTO selectedFsa = getSelectedFsa(orderCaptureDTO, pExistFsaInd);
		String fsaTechology = null;
	
		if(selectedFsa != null){
			fsaTechology = selectedFsa.getTechnology();
			if(ponInd){
				if(LtsConstant.TECHNOLOGY_PON.equals(fsaTechology)){
						if(pExistFsaInd
								? !orderCaptureDTO.getLtsModemArrangementForm().isExistingFsaER()
								: !orderCaptureDTO.getLtsModemArrangementForm().isOtherFsaER()
							){
							return false;
						}else{
							return true;
						}
				}else{
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * will check for ModemType in ModemArrangementForm
	 * return null if not share fsa
	 */
	private FsaDetailDTO getSelectedFsa(AcqOrderCaptureDTO orderCaptureDTO){

		if(orderCaptureDTO.getLtsModemArrangementForm() == null){
			return null;
		}
		
		ModemType m = orderCaptureDTO.getLtsModemArrangementForm().getModemType();
		FsaDetailDTO selectedFsa = null;
		if(ModemType.SHARE_EX_FSA.equals(m)){
			selectedFsa = getSelectedFsa(orderCaptureDTO, true);
		}
		if(ModemType.SHARE_OTHER_FSA.equals(m)){
			selectedFsa = getSelectedFsa(orderCaptureDTO, false);
		}
		return selectedFsa;
	}

	private FsaDetailDTO getSelectedFsa(AcqOrderCaptureDTO orderCaptureDTO, boolean pExistFsaInd){
		FsaDetailDTO selectedFsa = null;
		if(orderCaptureDTO.getLtsModemArrangementForm() != null){
			if(pExistFsaInd && orderCaptureDTO.getLtsModemArrangementForm().getExistingFsaList() != null){
				for(FsaDetailDTO fsa: orderCaptureDTO.getLtsModemArrangementForm().getExistingFsaList()){
					if(fsa.isSelected()){
						selectedFsa = fsa;
						break;
					}
				}
			}else if(!pExistFsaInd && orderCaptureDTO.getLtsModemArrangementForm().getOtherFsaList() != null){
				for(FsaDetailDTO fsa: orderCaptureDTO.getLtsModemArrangementForm().getOtherFsaList()){
					if(fsa.isSelected()){
						selectedFsa = fsa;
						break;
					}
				}
			}
		}
		return selectedFsa;
	}

	public boolean getChangePonTimeSlotInd(AcqOrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getLtsModemArrangementForm() == null){
			return false;
		}
		ModemType m = orderCaptureDTO.getLtsModemArrangementForm().getModemType();
		if(ModemType.SHARE_EX_FSA.equals(m)){
			return isInstallUpgradePon(orderCaptureDTO, true);
		}
		if(ModemType.SHARE_OTHER_FSA.equals(m)){
			return isInstallUpgradePon(orderCaptureDTO, false);
		}
		return false;
	}
	
	private boolean validateLtsLegacyStatus(SbOrderDTO sbOrder){
		if(sbOrder == null){
			return false;
		}
		ServiceDetailDTO srvDtl = LtsSbOrderHelper.getLtsService(sbOrder);
		OrderStatusSynchDTO status = getOrderStatus(sbOrder.getOrderId(), srvDtl);
		if(status == null){
			return false;
		}
		
		return StringUtils.equals("D", status.getBomLegacyStatus());
	}

	private boolean checkImsL1Status(SbOrderDTO sbOrder){
		if(sbOrder == null){
			return false;
		}
		ServiceDetailDTO srvDtl = LtsSbOrderHelper.getImsService(sbOrder);
		OrderStatusSynchDTO status = getOrderStatus(sbOrder.getOrderId(), srvDtl);
		
		if(status == null){
			return false;
		}
		
		return StringUtils.equals("D", status.getL1OrdStatus());
	}
	
	private OrderStatusSynchDTO getOrderStatus(String orderId, ServiceDetailDTO srvDtl){
		if(srvDtl == null){
			return null;
		}
		
		OrderStatusSynchDTO[] status = bomOrderStatusSynchService.getBomOrderStatus(
				orderId, srvDtl.getTypeOfSrv(), 
				srvDtl.getSrvNum(), srvDtl.getCcServiceId(), srvDtl.getCcServiceMemNum());
		for(OrderStatusSynchDTO tempStatus: status){
			if(StringUtils.equals(srvDtl.getCcServiceId(), tempStatus.getCcServiceId())){
				return tempStatus;
			}
		}
		return null;
	}
	
	public boolean isUsePipbDirectly(AcqOrderCaptureDTO orderCaptureDTO){
		LtsAcqNumSelectionAndPipbFormDTO numSelectForm = orderCaptureDTO.getLtsAcqNumSelectionAndPipbForm();
		if(numSelectForm != null
				&& numSelectForm.getNumSelection() == Selection.USE_PIPB_DN
				&& StringUtils.isNotBlank(numSelectForm.getPipbInfo().getPipbSrvNum())){
			return true;
		}
		
		return false;
	}

	/*RETRUN REASON CODE IN PRIORITY*/
	public String getMustQcReasonCd(AcqOrderCaptureDTO pOrder, LtsAcqAppointmentFormDTO pForm){
		
		//Is EYE Order and age over 65
		if(isEyeOrderAgeOver(pOrder)){
			return LtsConstant.LTS_DS_MUST_QC_REASON_CD_ELDERLY;
		}

		//Is DEL Order PE Link
		if(!pOrder.isEyeOrder() && pForm.getPeLinkInd() != null && pForm.getPeLinkInd()){
			return LtsConstant.LTS_DS_MUST_QC_REASON_CD_PELINK;
		}
		
		//THIRD PARTY
		if(pOrder.getLtsAcqPersonalInfoForm().isThirdParty()){
			return LtsConstant.LTS_DS_MUST_QC_REASON_CD_3RDPARTY;
		}
		
		return null;
	}
	
	public boolean isMustQc(AcqOrderCaptureDTO pOrder, LtsAcqAppointmentFormDTO pForm){
		
		//Is DEL Order PE Link
		if(!pOrder.isEyeOrder() && pForm.getPeLinkInd() != null && pForm.getPeLinkInd()){
			return true;
		}
		
		if(pOrder.getLtsAcqPersonalInfoForm().isThirdParty()){
			return true;
		}
		
		//Is EYE Order and age over 65
		if(isEyeOrderAgeOver(pOrder)){
			return true;
		}
		
		return false;
		
	}
	
	public boolean isEyeOrderAgeOver(AcqOrderCaptureDTO pOrder){
		String dob = null;
		try{
			dob = LtsSbOrderHelper.getLtsService(pOrder.getSbOrder()).getAccount().getCustomer().getDob().substring(0, 10);
		}catch(NullPointerException npe){
			dob = null;
		}

		if(dob == null && pOrder.getLtsAcqPersonalInfoForm() != null 
				&& StringUtils.length(pOrder.getLtsAcqPersonalInfoForm().getDateOfBirth()) >= 10){
			dob = pOrder.getLtsAcqPersonalInfoForm().getDateOfBirth().substring(0, 10);
		}
		
		if(dob != null){
			boolean isOver65 = isAgeOver(dob);
			//boolean isOver65 = LtsSbOrderHelper.isAgeOver(dob);
			if(isOver65 && pOrder.isEyeOrder() 
					&& !pOrder.getLtsAcqPersonalInfoForm().isThirdParty()){
				return true;
			}
		}
		return false;
	}

	
	public boolean isAgeOver(String dob){
		if(StringUtils.isNotBlank(dob)){
			Period diff = new Period(LocalDate.parse(dob, DateTimeFormat.forPattern("dd/MM/yyyy")), LocalDate.now());
			if(diff.getYears() >= 65){
				return true;
			}
		}
		return false;
	}
	
	public boolean isResourceShortage(String pcdsbid){
		boolean returnVal = false;
		try {
			String resourceShortageInd = premiumSetDetailDao.getResourceShortageInd(pcdsbid);
			
			if (resourceShortageInd != null)
			{
				returnVal = resourceShortageInd.equalsIgnoreCase("Y");
			}
			}
		catch(Exception e) {
			//logger.error("Error in getResourceShortageInd - ", e);
			throw new AppRuntimeException(e.getMessage(), e);
		}
		return returnVal;
	}

	private LtsSRDDTO getPassportWithPrepaymentSrd(String pAppDate){
		int leadtime = 0;
		try {
			leadtime = Integer.parseInt((String)ltsAppointmentCutOffLkupCacheService.get("PASSPORT_PREPAY_CASE"));
		} catch (Exception e){
			e.printStackTrace();
		}
		LtsSRDDTO srd = new LtsSRDDTO(pAppDate, leadtime, "55");
		return srd;
	}
	
	public CodeLkupCacheService getLtsAppointmentCutOffLkupCacheService() {
		return ltsAppointmentCutOffLkupCacheService;
	}

	public void setLtsAppointmentCutOffLkupCacheService(
			CodeLkupCacheService ltsAppointmentCutOffLkupCacheService) {
		this.ltsAppointmentCutOffLkupCacheService = ltsAppointmentCutOffLkupCacheService;
	}

	public LtsAppointmentDAO getLtsAppointmentDAO() {
		return ltsAppointmentDAO;
	}

	public void setLtsAppointmentDAO(LtsAppointmentDAO ltsAppointmentDAO) {
		this.ltsAppointmentDAO = ltsAppointmentDAO;
	}

	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}

	public void setAppointmentDwfmService(
			AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}

	public BomOrderStatusSynchService getBomOrderStatusSynchService() {
		return bomOrderStatusSynchService;
	}

	public void setBomOrderStatusSynchService(
			BomOrderStatusSynchService bomOrderStatusSynchService) {
		this.bomOrderStatusSynchService = bomOrderStatusSynchService;
	}

	public ServiceProfileLtsDrgService getServiceProfileLtsDrgService() {
		return serviceProfileLtsDrgService;
	}

	public void setServiceProfileLtsDrgService(
			ServiceProfileLtsDrgService serviceProfileLtsDrgService) {
		this.serviceProfileLtsDrgService = serviceProfileLtsDrgService;
	}

	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}
	
	public CodeLkupCacheService getLtsAppointmentBasketBasicTLkupCacheService() {
		return ltsAppointmentBasketBasicTLkupCacheService;
	}

	public void setLtsAppointmentBasketBasicTLkupCacheService(
			CodeLkupCacheService ltsAppointmentBasketBasicTLkupCacheService) {
		this.ltsAppointmentBasketBasicTLkupCacheService = ltsAppointmentBasketBasicTLkupCacheService;
	}

	public PremiumSetDetailDAO getPremiumSetDetailDao() {
		return premiumSetDetailDao;
	}

	public void setPremiumSetDetailDao(PremiumSetDetailDAO premiumSetDetailDao) {
		this.premiumSetDetailDao = premiumSetDetailDao;
	}
	
	
}
