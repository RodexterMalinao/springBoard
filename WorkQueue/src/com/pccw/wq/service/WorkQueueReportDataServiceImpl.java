package com.pccw.wq.service;

import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.bomwebportal.service.ReportDataService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.util.spring.SpringApplicationContext;

public class WorkQueueReportDataServiceImpl implements ReportDataService {

	public static final String PARAM_WQ_DETAIL = "WQ_DETAIL";
	
	public static final String PARAM_WP_WQ_ASSGN_ID = "WP_WQ_ASSGN_ID";
	
	public static final String PARAM_WQ_BULK_PRINT_SUMMARY = "WQ_BULK_PRINT_SUMMARY";
	
	private static Logger logger = Logger.getLogger(WorkQueueReportDataServiceImpl.class);	
	
	@Override
	public void fillReportData(ReportDTO pReportDTO, Map<String, Object> pRptParamMap) {
		try {
			if ("wq/wqCoverSheet".equals((String) pRptParamMap.get(ReportDataService.PARAM_RPT_NAME))) {
				fillReportDataWqCoverSheet(pReportDTO, pRptParamMap);
			}
			else if ("wq/bulkPrintCoverSheet".equals((String) pRptParamMap.get(ReportDataService.PARAM_RPT_NAME))) {
				fillReportDataBulkPrintSummary(pReportDTO, pRptParamMap);
			} else if ("LTS".equals(pRptParamMap.get("LOB"))) {
				((ReportDataService) SpringApplicationContext.getBean("springboardLtsEyeReportDataService")).fillReportData(pReportDTO, pRptParamMap);
		    }
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}
	}
	
	private void fillReportDataWqCoverSheet(ReportDTO pReportDTO, Map<String, Object> pRptParamMap) throws Exception {
		SpringApplicationContext.getBean(WorkQueueService.class).fillReportDataWqCoverSheet(pReportDTO, pRptParamMap);
	}
	
	private void fillReportDataBulkPrintSummary(ReportDTO pReportDTO, Map<String, Object> pRptParamMap) throws Exception {
		SpringApplicationContext.getBean(WorkQueueService.class).fillReportDataBulkPrintSummary(pReportDTO, pRptParamMap);
	}
}
