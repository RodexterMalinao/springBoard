package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.acq.LtsRefereeSaleDTO;
import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSalesInfoFormDTO;

public interface LtsSalesInfoService {

	public LtsSalesInfoFormDTO getSalesInfo(String staffId);
	public String[] getShopContactAndChannel(String pShopCode);
	public String[] getOrgStaffId(String staffId);
	public LookupItemDTO[] getImsApplMthdLkup();
	public List<String> getImsApplSourceByMthdDesc(String applMthd);
	public List<String> getImsApplSource(String applMthd);
	public List<String> getTeamCdListByChannelCd(String channelCd);
	public String convertToStaffId(String orgStaffId);
	public String convertToOrgStaffId(String staffId);
	public LtsAcqSalesInfoFormDTO getAcqSalesInfo(String staffId);
	public LtsRefereeSaleDTO getRefSalesInfo(String pOrderId);
	public String getUserBoc(String pUserId);
	public String getSalesContact(String pStaffId);
	public String getStaffCategory(String pStaffId);
}
