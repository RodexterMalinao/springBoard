package com.bomwebportal.service;

import java.util.Map;

import com.pccw.rpt.schema.dto.ReportDTO;

public interface ReportDataService {

	public static final String PARAM_RPT_NAME = "RPT_NAME";

	public static final String PARAM_RPT_LANGUAGE = "RPT_LANGUAGE";

	public static final String PARAM_SB_ID = "SB_ID";

	public void fillReportData(ReportDTO pReportDTO, Map<String, Object> pRptParamMap);
}
