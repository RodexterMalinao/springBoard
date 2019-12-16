package com.bomwebportal.service;


import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.report.MobAppFormDTO;
import com.bomwebportal.util.FastByteArrayOutputStream;

public interface MobCosReportService {
	//public byte[] generateMobAppForminPDF(List<MobAppFormDTO> requiredMobAppFormList, String orderId, String locale);
	public byte[] generateMobAppForm(FastByteArrayOutputStream baosMerged,
			String orderId, String locale, String save, String nature, String channel, 
			String companyCopyInd, Map<String, SignoffDTO> signOffMap, String copInd,
			String delInd, String dmsInd,
			String filePath, String watermark, String updateStatus, List<String> formIds
	) throws Exception;
	
	public List<MobAppFormDTO> getReportListByFormIds(String channelId, String orderNature, List<String> formIds);
}
