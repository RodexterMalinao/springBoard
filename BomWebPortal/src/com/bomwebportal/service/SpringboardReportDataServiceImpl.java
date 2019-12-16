package com.bomwebportal.service;

import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.bomwebportal.lts.util.LtsConstant;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.service.WorkQueueReportDataServiceImpl;

public class SpringboardReportDataServiceImpl implements ReportDataService {

	private static Logger logger = Logger.getLogger(SpringboardReportDataServiceImpl.class);	

	public void fillReportData(ReportDTO pReportDTO, Map<String, Object> pParamMap) {
		try {
			if ("wq/wqCoverSheet".equals((String) pParamMap.get(ReportDataService.PARAM_RPT_NAME))) {
				SpringApplicationContext.getBean(WorkQueueReportDataServiceImpl.class).fillReportData(pReportDTO, pParamMap);
			} else if (LtsConstant.LOB_LTS.equals(pParamMap.get(LtsConstant.RPT_KEY_LOB))) {
				((ReportDataService) SpringApplicationContext.getBean("springboardLtsEyeReportDataService")).fillReportData(pReportDTO, pParamMap);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}
	}
}