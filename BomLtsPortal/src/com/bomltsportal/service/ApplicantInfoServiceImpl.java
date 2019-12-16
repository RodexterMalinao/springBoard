package com.bomltsportal.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.dao.ApplicantInfoDAO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.SrdDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.LtsDateFormatHelper;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.PrepaymentLkupDAO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupCriteriaDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupResultDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceSlotDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceTypeDTO;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.CodeLkupCacheServiceImpl;

public class ApplicantInfoServiceImpl implements ApplicantInfoService {

	protected final Log logger = LogFactory.getLog(getClass());
	private ApplicantInfoDAO applicantInfoDAO;
	private AppointmentDwfmService appointmentDwfmService;
	private CodeLkupCacheServiceImpl onlineSalesEyeAppointmentCutOffLkupCacheService;
	private CodeLkupCacheServiceImpl onlineSalesDelAppointmentCutOffLkupCacheService;
	private PrepaymentLkupDAO prepaymentLkupDAO;
	private CodeLkupCacheService ltsPrepayExcludePsefCacheService;

	@Override
	public List<String> getServiceNumbers(int amount) {
		logger.info("RegIdentificationServiceImpl getServiceNumbers");
		return applicantInfoDAO.getServiceNumbers(amount);
	}
	
	@Override
	public SrdDTO getEarliestSRD(OrderCaptureDTO orderCaptureDTO) {
		
		boolean blacklist, blacklistAddr, onp, nameNotMatch, twoNBuilding;
		String srvType = orderCaptureDTO.getServiceTypeInd();
		
		blacklist 		= orderCaptureDTO.getCustomerDetailProfile() != null
							&& orderCaptureDTO.getCustomerDetailProfile().isBlacklistCustInd();
		blacklistAddr 	= orderCaptureDTO.getAddressRollout().isLtsAddressBlacklist();
		onp 			= orderCaptureDTO.getApplicantInfoForm() != null
							&& orderCaptureDTO.getApplicantInfoForm().getNewNum() != null
							&& !orderCaptureDTO.getApplicantInfoForm().getNewNum().booleanValue();
		nameNotMatch	= orderCaptureDTO.getApplicantInfoForm() != null
							&& orderCaptureDTO.getCustomerDetailProfile() != null
							&& !orderCaptureDTO.getApplicantInfoForm().isCustNameMatch();
		twoNBuilding 	= orderCaptureDTO.getAddressRollout().isIs2nBuilding();

		return  getEarliestSRD(blacklist, blacklistAddr, onp, nameNotMatch, twoNBuilding, srvType);
	}
	
	public SrdDTO getEarliestSRD(SbOrderDTO sbOrder){

		boolean blacklist, blacklistAddr, onp, nameNotMatch, twoNBuilding;
		String srvType = null;
		
		ServiceDetailLtsDTO srvDtl = LtsSbHelper.getLtsEyeService(sbOrder);
		if(srvDtl == null){
			srvDtl = LtsSbHelper.getDelServices(sbOrder);
			srvType = BomLtsConstant.SERVICE_TYPE_DEL;
		}else{
			srvType = BomLtsConstant.SERVICE_TYPE_EYE;
		}

		blacklist		= "Y".equals(srvDtl.getAccount().getCustomer().getBlacklistInd());
		blacklistAddr	= "Y".equals(sbOrder.getAddress().getBlacklistInd());
		onp 			= BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_PORT_IN_EYE.equals(srvDtl.getSrvTypeCdAction()) 
							|| BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_PORT_IN_DEL.equals(srvDtl.getSrvTypeCdAction());
		nameNotMatch	= "Y".equals(srvDtl.getCustNameNotMatch());
		twoNBuilding 	= "Y".equals(sbOrder.getAddress().getAddrInventory().getN2BuildingInd());
		
		return  getEarliestSRD(blacklist, blacklistAddr, onp, nameNotMatch, twoNBuilding, srvType);
	}
	
	public SrdDTO getEarliestSRD(boolean blacklist, boolean blacklistAddr, boolean onp, boolean nameNotMatch, boolean twoNBuilding, String srvType){
		ArrayList<SrdDTO> sRDInfos = new ArrayList<SrdDTO>();
		SrdDTO startDate = new SrdDTO();
		startDate.setInfo("");
		String startDateString = startDate.getDateString();
		
		if(blacklist){
			sRDInfos.add(createSrdDTO(startDateString, 30, "Blacklist Customer", "20"));
		}
		if(onp){
			sRDInfos.add(createSrdDTO(startDateString, 30, "ONP", "21"));
		}
		if(nameNotMatch){
			sRDInfos.add(createSrdDTO(startDateString, 14, "Customer input name not matched with BOM record", "22"));
		}
		if(twoNBuilding){
			sRDInfos.add(createSrdDTO(startDateString, 14, "2N Building", "23"));
		}
		if(blacklistAddr){
			sRDInfos.add(createSrdDTO(startDateString, 30, "Blacklist Address", "24"));
		}
		
		return calculateEarliestSRD(startDate, sRDInfos, srvType);
	}
	
	private SrdDTO calculateEarliestSRD(SrdDTO startDate, ArrayList<SrdDTO> sRDInfos, String srvType){
		SrdDTO earliestSRDInfo = null;
		if(sRDInfos.size() > 0){
			for(int i = 0; i < sRDInfos.size(); i++){
				if(earliestSRDInfo == null || 
						earliestSRDInfo.getLeadTime() + earliestSRDInfo.getSkippedTime() 
						< sRDInfos.get(i).getLeadTime() + sRDInfos.get(i).getSkippedTime()){
					earliestSRDInfo = sRDInfos.get(i);
				}
			}
		}else if(sRDInfos.size() == 0){
			earliestSRDInfo = getNormalLeadTime(srvType);
		}

		earliestSRDInfo.setInfo(startDate.getInfo().concat(earliestSRDInfo.getInfo()));
		return earliestSRDInfo;
	}
	
	private SrdDTO createSrdDTO(String startDateString, int leadtime, String info, String reasonCd){
		SrdDTO srd = new SrdDTO(startDateString);
		srd.setLeadTime(leadtime);
		srd.setInfo(info);
		srd.setReasonCd(reasonCd);
		
		return srd;
	}
	
	private SrdDTO getNormalLeadTime(String srvType){
		String cutOffTimeString = null;
		Calendar cutOffTime = null;
		int defaultLeadTime = 4, beforeCutOff = 3, afterCutOff = 4;
		
		try {
			LookupItemDTO[] cutOffDetails;
			if(BomLtsConstant.SERVICE_TYPE_EYE.equals(srvType)){
				cutOffDetails = onlineSalesEyeAppointmentCutOffLkupCacheService.getCodeLkupDAO().getCodeLkup();
			}else{
				cutOffDetails = onlineSalesDelAppointmentCutOffLkupCacheService.getCodeLkupDAO().getCodeLkup();
			}
			
			for(LookupItemDTO cutOffLkup : cutOffDetails){
				if("CUT_OVER_TIME".equals(cutOffLkup.getItemKey())){
					cutOffTimeString = (String) cutOffLkup.getItemValue();
				}else if("DEFAULT".equals(cutOffLkup.getItemKey())){
					defaultLeadTime = Integer.parseInt((String)cutOffLkup.getItemValue());
				}else if("BEFORE_CUT_OVER".equals(cutOffLkup.getItemKey())){
					beforeCutOff = Integer.parseInt((String)cutOffLkup.getItemValue());
				}else if("AFTER_CUT_OVER".equals(cutOffLkup.getItemKey())){
					afterCutOff = Integer.parseInt((String)cutOffLkup.getItemValue());
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		if(!StringUtils.isBlank(cutOffTimeString)){
			int hr = Integer.parseInt(cutOffTimeString.split(":")[0]);
			int min = Integer.parseInt(cutOffTimeString.split(":")[1]);
			cutOffTime = new GregorianCalendar(0, 0, 0, hr, min);
		}
		
		SrdDTO normalLeadTimeSrd = new SrdDTO();
		Calendar now = new GregorianCalendar();
		normalLeadTimeSrd.setInfo("Normal Lead Time ");
		if(cutOffTime != null){
			if(now.get(Calendar.HOUR_OF_DAY) < cutOffTime.get(Calendar.HOUR_OF_DAY)){
				normalLeadTimeSrd.setLeadTime(beforeCutOff);
			}else if(now.get(Calendar.HOUR_OF_DAY) == cutOffTime.get(Calendar.HOUR_OF_DAY)){
				if(now.get(Calendar.MINUTE) < cutOffTime.get(Calendar.MINUTE)){
					normalLeadTimeSrd.setLeadTime(beforeCutOff);
				}else{
					normalLeadTimeSrd.setLeadTime(afterCutOff);
				}
			}else{
				normalLeadTimeSrd.setLeadTime(afterCutOff);
			}
		}else{
			normalLeadTimeSrd.setLeadTime(defaultLeadTime);
		}
		
		normalLeadTimeSrd.setReasonCd("00");
		return normalLeadTimeSrd;
		
	}
	
	/*
	 * date format = "dd/MM/yyyy" 
	 * */
	public ResourceDetailInputDTO getResourceDetailInput(OrderCaptureDTO orderCaptureDTO, String date){
//		final String PROD_SUB_TYPE_CD_V = "VSO-";
		final String SRV_TYPE_CD_SO = "SO";
		String prodSubTypeCd = getProdSubTypeCd(orderCaptureDTO)[0];
		String fromProdSubTypeCd = getProdSubTypeCd(orderCaptureDTO)[1];
		
		ResourceDetailInputDTO resourceInput = new ResourceDetailInputDTO();
		
		List<ResourceTypeDTO> resourceTypeDTOList = new ArrayList<ResourceTypeDTO>(); 
		
		ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO();
		resourceTypeDTO.setProdSubTypeCd(prodSubTypeCd);
		resourceTypeDTO.setProdType("I");
		resourceTypeDTO.setSrvType(SRV_TYPE_CD_SO);
		resourceTypeDTO.setFromProdSubTypeCd(fromProdSubTypeCd);
		resourceTypeDTO.setOrderType(StringUtils.substring(prodSubTypeCd, 3, 4));
//		resourceTypeDTO.setOrderType(orderCaptureDTO.getModemTechnologyAissgn().getImsOrderType());

		resourceTypeDTOList.add(resourceTypeDTO);

		resourceInput.setRscTypes(resourceTypeDTOList.toArray(new ResourceTypeDTO[0]));
		resourceInput.setApptDate(reformatToDwfmDate(date));
		String[] addressInfo = getAreaDistrictCd(orderCaptureDTO);
		resourceInput.setArea(addressInfo[0]);
		resourceInput.setDistrict(addressInfo[1]);
		resourceInput.setAddrChangeInd(addressInfo[2]);
		resourceInput.setSrvBoundary(addressInfo[3]);
		resourceInput.setDrgPermitDays(String.valueOf(isFieldPermitRequired(orderCaptureDTO)));
		resourceInput.setSystemId("SPB");
		resourceInput.setUser("");
		return resourceInput;
	}
	
	public PrebookAppointmentInputDTO getPrebookAppointmentInput(OrderCaptureDTO orderCaptureDTO, String date, String timeSlot, String staffId, String timeSlotType){
		String dateString = reformatToDwfmDate(date);
		String[] time = timeSlot.split("-");
		String startDate = "";
		String endDate = "";
		String[] startTime = time[0].split(":");
		String[] endTime = time[1].split(":");
		startDate = startDate.concat(dateString).concat(startTime[0]).concat(startTime[1]);
		endDate = endDate.concat(dateString).concat(endTime[0]).concat(endTime[1]);
		PrebookAppointmentInputDTO input = new PrebookAppointmentInputDTO();
		input.setProdSubTypeCd(getProdSubTypeCd(orderCaptureDTO)[1]);
		String[] area = getAreaDistrictCd(orderCaptureDTO);
		input.setArea(area[0]);
		input.setDistrict(area[1]);
		input.setApptDateStartDate(startDate);
		input.setApptDateEndDate(endDate);
		input.setSystemId("");
		input.setUser(staffId);
		input.setApptType(timeSlotType);
		return input;
	}
	
	private String[] getProdSubTypeCd(OrderCaptureDTO orderCaptureDTO){
		final String PCD_PREFIX = "PCD";
		final String PON_PREFIX = "FTH";
		
		String prodSubTypeCd = PCD_PREFIX + BomLtsConstant.ORDER_TYPE_INSTALL;
		String fromProdSubTypeCd = PCD_PREFIX + BomLtsConstant.ORDER_TYPE_INSTALL;
		
		if(isPon(orderCaptureDTO)){
			if(orderCaptureDTO.getFsaServiceAssgn() != null){
				prodSubTypeCd = PON_PREFIX + orderCaptureDTO.getFsaServiceAssgn().getImsOrderType();
				fromProdSubTypeCd = PON_PREFIX + orderCaptureDTO.getFsaServiceAssgn().getImsOrderType();
			}else{
				for(ServiceDetailDTO srvDtl : orderCaptureDTO.getSbOrder().getSrvDtls()){
					if(BomLtsConstant.SERVICE_TYPE_IMS.equals(srvDtl.getTypeOfSrv())){
						prodSubTypeCd = PON_PREFIX + orderCaptureDTO.getSbOrder().getSrvDtls()[0].getOrderType();
						fromProdSubTypeCd = PON_PREFIX + orderCaptureDTO.getSbOrder().getSrvDtls()[0].getOrderType();
						break;
					}
				}
			}
		}
		String[] prodSubTypeCds = {prodSubTypeCd, fromProdSubTypeCd};
		return prodSubTypeCds;
	}
	
	public String reformatToDwfmDate(String date){
		return LtsDateFormatHelper.reformatDate(date, "dd/MM/yyyy", "yyyyMMdd");
	}
	
	
	private String[] getAreaDistrictCd(OrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getAddressLookupForm() != null && orderCaptureDTO.getAddressRollout() != null){
			String[] s = {orderCaptureDTO.getAddressLookupForm().getBuildingMarker().getAreaCd(),
					orderCaptureDTO.getAddressLookupForm().getBuildingMarker().getDistrCd(),
					"N",
					orderCaptureDTO.getAddressRollout().getSrvBdary()};
			return s;
		}else{
			String[] s = {orderCaptureDTO.getSbOrder().getAddress().getAreaCd(),
						orderCaptureDTO.getSbOrder().getAddress().getDistNo(),
						"N",
						orderCaptureDTO.getSbOrder().getAddress().getSerbdyno()};
			return s;
		}
	}
	
	public int isFieldPermitRequired(OrderCaptureDTO orderCaptureDTO){
		int fieldPermit = 0;
		try{
			AddressRolloutDTO addressRollout = orderCaptureDTO.getAddressRollout();
			String leadtime = null;
			if(addressRollout != null){
				leadtime = addressRollout.getFieldWorkPermit();
			}else if(orderCaptureDTO.getSbOrder() != null){
				leadtime = orderCaptureDTO.getSbOrder().getAddress().getAddrInventory().getFieldWorkPermitDay();
			}
			if(!StringUtils.isBlank(leadtime) && StringUtils.isNumeric(leadtime)){
				fieldPermit = Integer.parseInt(leadtime);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return fieldPermit;
	}
	
	public boolean isPon(OrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getFsaServiceAssgn() != null){
			return BomLtsConstant.TECHNOLOGY_PON.equals(orderCaptureDTO.getFsaServiceAssgn().getTechnology());
		}else if(orderCaptureDTO.getSbOrder() != null){
			return BomLtsConstant.TECHNOLOGY_PON.equals(orderCaptureDTO.getSbOrder().getAddress().getAddrInventory().getTechnology());
		}
		return false;
	}
	
	public List<String> getPublicHolidayList() {
		try {
			List<String> holidayList = applicantInfoDAO.getHolidayList();
			return holidayList;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getToday(){
		SimpleDateFormat df = new SimpleDateFormat();
		Calendar today = new GregorianCalendar();
		df.applyPattern("dd/MM/yyyy");
		return df.format(today.getTime());
	}

	public ResourceDetailOutputDTO getResourceDetailWithFilter(ResourceDetailInputDTO resourceInput, boolean technologyIsPON) {
		ResourceDetailOutputDTO resource = appointmentDwfmService.getResourceDetail(resourceInput);
		ResourceDetailOutputDTO filteredResource = new ResourceDetailOutputDTO();
		List<ResourceSlotDTO> filteredTimeSlot = new ArrayList<ResourceSlotDTO>();
		
		
		for(ResourceSlotDTO slot: resource.getResourceSlots()){
			if(!"Y".equals(slot.getAvailableInd())){
				continue;
			}
			if("Y".equals(slot.getRestrictInd())){
				continue;
			}
			if(BomLtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE.equals(slot.getApptTimeSlotType())){
				continue;
			}
			
			String timeSlot = LtsDateFormatHelper.convertToSBTimeSlot(slot.getApptTimeSlot());
			if(technologyIsPON){
				timeSlot = LtsDateFormatHelper.convertToPonTimeSlot(timeSlot);
				
			}else{
				if(BomLtsConstant.APPOINTMENT_TIMESLOT_TYPE_PM.equals(slot.getApptTimeSlotType())){
					continue;
				}
			}
			
			slot.setApptTimeSlot(timeSlot);
			filteredTimeSlot.add(slot);
		}
		
		if(filteredTimeSlot.size() > 0){
			Collections.sort(filteredTimeSlot, new Comparator<ResourceSlotDTO>() {
				@Override
				public int compare(ResourceSlotDTO o1, ResourceSlotDTO o2) {
					return o1.getApptTimeSlot().compareTo(o2.getApptTimeSlot());
				}
			});
			filteredResource.setResourceSlots(filteredTimeSlot.toArray(new ResourceSlotDTO[0]));
		}
		return filteredResource;
	}
	
	public int getMaxSrd(String srvType){
		LookupItemDTO[] lkupDetails;
		try{
			if(BomLtsConstant.SERVICE_TYPE_EYE.equals(srvType)){
				lkupDetails = onlineSalesEyeAppointmentCutOffLkupCacheService.getCodeLkupDAO().getCodeLkup();
			}else{
				lkupDetails = onlineSalesDelAppointmentCutOffLkupCacheService.getCodeLkupDAO().getCodeLkup();
			}
			for(LookupItemDTO lkupItem: lkupDetails){
				if("MAX_SRD".equals(lkupItem.getItemKey())){
					return Integer.parseInt((String)(lkupItem.getItemValue()));
				}
			}
		}catch(DAOException e){
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public String getLtsTenureCode(double tenure) {
		try {
			return applicantInfoDAO.getLtsTenureCode(tenure);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<ItemDetailDTO> getLtsBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate) {
		try {
			
			return this.applicantInfoDAO.getLtsBasketItemList(pBasketId, pItemType, pLocale, pSelectDefault, pChannelId, pApplDate);
		
		} catch (DAOException de) {
			logger.error("Fail in ApplicantInfoService.getLtsItemDetailList()");
			throw new RuntimeException(de.getCustomMessage());
		} 
	}
	
	public List<String[]> getLtsPrePayItem(String tenureCode, String houseType, String deviceType){
		try {
			return applicantInfoDAO.getLtsPrePayItem(tenureCode, houseType, deviceType);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	

	
	public List<PrepaymentLkupResultDTO> getPrepaymentItemIdByLkup(PrepaymentLkupCriteriaDTO criteria){
		try {
			List<PrepaymentLkupResultDTO> resultList = prepaymentLkupDAO.getPrepaymentItemId(criteria);
			if(resultList != null && resultList.size() > 0){
				for(PrepaymentLkupResultDTO result : resultList){
					Set<String> psefSet = new HashSet<String>();
					String[] psefCds = StringUtils.split(result.getPsefCd(), "|");
					if(psefCds != null && psefCds.length > 0){
						for(String psef : psefCds){
							psefSet.add(StringUtils.trim(psef));
						}
						result.setPsefCdSet(psefSet);
					}
				}
			}
			
			return resultList;
		}catch (DAOException de) {
			logger.error("Fail in LtsPaymentService.getPrepaymentItemIdByLkup " + de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}
	
	public List<String> getPrepayExcludePsefCodeList(){
		
		List<String> excludePsefCode = new ArrayList<String>();
		
		try {
			for(LookupItemDTO codeLkup: ltsPrepayExcludePsefCacheService.getCodeLkupDAO().getCodeLkup()){
				excludePsefCode.add(codeLkup.getItemKey());
			}
		} catch (DAOException e) {
			return new ArrayList<String>();
		}
		
		return excludePsefCode;
		
	}

	public ApplicantInfoDAO getApplicantInfoDAO() {
		return applicantInfoDAO;
	}

	public void setApplicantInfoDAO(ApplicantInfoDAO applicantInfoDAO) {
		this.applicantInfoDAO = applicantInfoDAO;
	}

	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}

	public void setAppointmentDwfmService(AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}

	public CodeLkupCacheServiceImpl getOnlineSalesEyeAppointmentCutOffLkupCacheService() {
		return onlineSalesEyeAppointmentCutOffLkupCacheService;
	}

	public CodeLkupCacheServiceImpl getOnlineSalesDelAppointmentCutOffLkupCacheService() {
		return onlineSalesDelAppointmentCutOffLkupCacheService;
	}

	public void setOnlineSalesEyeAppointmentCutOffLkupCacheService(
			CodeLkupCacheServiceImpl onlineSalesEyeAppointmentCutOffLkupCacheService) {
		this.onlineSalesEyeAppointmentCutOffLkupCacheService = onlineSalesEyeAppointmentCutOffLkupCacheService;
	}

	public void setOnlineSalesDelAppointmentCutOffLkupCacheService(
			CodeLkupCacheServiceImpl onlineSalesDelAppointmentCutOffLkupCacheService) {
		this.onlineSalesDelAppointmentCutOffLkupCacheService = onlineSalesDelAppointmentCutOffLkupCacheService;
	}

	public PrepaymentLkupDAO getPrepaymentLkupDAO() {
		return prepaymentLkupDAO;
	}

	public void setPrepaymentLkupDAO(PrepaymentLkupDAO prepaymentLkupDAO) {
		this.prepaymentLkupDAO = prepaymentLkupDAO;
	}

	public CodeLkupCacheService getLtsPrepayExcludePsefCacheService() {
		return ltsPrepayExcludePsefCacheService;
	}

	public void setLtsPrepayExcludePsefCacheService(
			CodeLkupCacheService ltsPrepayExcludePsefCacheService) {
		this.ltsPrepayExcludePsefCacheService = ltsPrepayExcludePsefCacheService;
	}

}
