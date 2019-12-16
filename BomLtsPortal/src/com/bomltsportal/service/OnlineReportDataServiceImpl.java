package com.bomltsportal.service;

import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.lts.service.report.EyeReportDataLtsServiceImpl;
import com.bomwebportal.service.ReportDataService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.service.WorkQueueReportDataServiceImpl;

public class OnlineReportDataServiceImpl implements ReportDataService {

	private static Logger logger = Logger.getLogger(OnlineReportDataServiceImpl.class);	

	public void fillReportData(ReportDTO pReportDTO, Map<String, Object> pParamMap) {
		try {
			if ("wq/wqCoverSheet".equals((String) pParamMap.get(ReportDataService.PARAM_RPT_NAME))) {
				SpringApplicationContext.getBean(WorkQueueReportDataServiceImpl.class).fillReportData(pReportDTO, pParamMap);
			} else if (BomLtsConstant.LOB_LTS.equals(pParamMap.get(BomLtsConstant.RPT_KEY_LOB))) {
				((ReportDataService) SpringApplicationContext.getBean("springboardLtsEyeReportDataService")).fillReportData(pReportDTO, pParamMap);
//				 springboardLtsEyeReportDataService.fillReportData(pReportDTO, pParamMap);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}
	}
}
