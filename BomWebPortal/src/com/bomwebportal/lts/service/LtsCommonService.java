package com.bomwebportal.lts.service;

import java.util.List;

import org.springframework.validation.Errors;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbBaseDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsTeamFunctionDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.util.LtsConstant.LtsOrderFlag;

public interface LtsCommonService {
	public boolean isMobileNumPrefix(String pNumber);
	public boolean isFixLineNumPrefix(String pNumber, boolean pCheckLtsPrefix);
	public boolean isFixLineNumPrefix(String pNumber);
	public void validateAttbValue(ItemAttbBaseDTO attb, List<String> errorMsgList);
	public String getAttbValueByInputFormat(ItemAttbBaseDTO[] attbArray, String attbType);
	public String getLkupKeyByDesc(LookupItemDTO[] lkupItems, String desc);
	public LtsTeamFunctionDTO getTeamFunction(String channelCd, String teamCd);
	public void validateContactValue(String contact, Errors errors, String fieldName);
	public void validateSmsNum(String smsNum, Errors errors, String fieldName);
	public boolean isNowDrgDownTime();
	public boolean isShowDrgDownTimeMsg();
	public List<ItemDetailDTO> getAcqBasketItem(AcqOrderCaptureDTO acqOrderCapture);
	public boolean recheckForceFieldService(OrderCaptureDTO order);
	public boolean validateFsaOfferForceFS(FsaServiceDetailOutputDTO fsaProfile);
	public boolean validateFsaOfferAllowSelfPickup(OrderCaptureDTO order);
	public String getLtsOrderFlag(LtsOrderFlag flag);
	public String getLtsOrderFlag(String flagName);
}
