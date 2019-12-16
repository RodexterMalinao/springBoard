package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.MobBuoQuotaDTO;
import com.bomwebportal.dto.QuotaPlanInfoDTO;

public interface QuotaPlanInfoService {

	List<QuotaPlanInfoDTO> getQuotaPlanOptions (String itemId,String appDate);
	List<MobBuoQuotaDTO>getBomWebBuoQuota(String orderId);
}
