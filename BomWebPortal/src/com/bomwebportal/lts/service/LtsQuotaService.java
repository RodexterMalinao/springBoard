package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.lts.dto.LtsQuotaDTO;

public interface LtsQuotaService {

	public LtsQuotaDTO getQuota(String programCd, String psef);
	public List<LtsQuotaDTO> getQuotaListByPsef(String psef);
	public void increaseQuotaUsed(String programCd);
	public void increaseQuotaUsed(LtsQuotaDTO quota);
}
