package com.bomwebportal.service;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.MobItemQuotaDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.mobquota.dto.QuotaConsumeRequest;

 public interface MobItemQuotaService {
 	public List<MobItemQuotaDTO> getMobItemQuotaDTOALL();
	public Map<String,MobItemQuotaDTO> getMobItemQuotaAsMap();
 	public List<MobItemQuotaDTO> getBasketMobItemQuotaDTOALL(String basketId, String appDate);
 	public List<MobItemQuotaDTO> getMobQuotaByItemList(List<String> items);
	public List<MobItemQuotaDTO> getFinalQuota(String basketId, String appDate, List<String> selectedVasList);
	public QuotaConsumeRequest createMobQuotaConsumeRequest(String idDocType, String idDocNum, String orderId, String staffId, VasDetailDTO vasDetailDTO, String authBy, Date applnDate);
 } 
  
