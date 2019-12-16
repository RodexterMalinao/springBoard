package com.bomltsportal.service;

import java.util.Collection;
import java.util.List;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.SrdDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupCriteriaDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupResultDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;

public interface ApplicantInfoService {
	public List<String> getServiceNumbers(int amount);
	public SrdDTO getEarliestSRD(SbOrderDTO sbOrder);
	public SrdDTO getEarliestSRD(OrderCaptureDTO orderCaptureDTO);
	public SrdDTO getEarliestSRD(boolean blacklist, boolean blacklistAddr, boolean onp, boolean nameNotMatch, boolean twoNBuilding, String srvType);
	public ResourceDetailInputDTO getResourceDetailInput(OrderCaptureDTO orderCaptureDTO, String date);
	public PrebookAppointmentInputDTO getPrebookAppointmentInput(OrderCaptureDTO orderCaptureDTO, String date, String timeSlot, String staffId, String timeSlotType);
	public ResourceDetailOutputDTO getResourceDetailWithFilter(ResourceDetailInputDTO resourceInput, boolean technologyIsPON);
	public boolean isPon(OrderCaptureDTO orderCaptureDTO);
	public String getToday();
	public int getMaxSrd(String srvType);
	public String getLtsTenureCode(double tenure);
	public List<ItemDetailDTO> getLtsBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate);
	public List<String[]> getLtsPrePayItem(String tenureCode, String houseType, String deviceType);
	public List<PrepaymentLkupResultDTO> getPrepaymentItemIdByLkup(PrepaymentLkupCriteriaDTO criteria);
	public List<String> getPrepayExcludePsefCodeList();
}
