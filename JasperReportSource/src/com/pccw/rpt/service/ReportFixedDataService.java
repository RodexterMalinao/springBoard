package com.pccw.rpt.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.pccw.rpt.schema.dto.ReportDTO;

public interface ReportFixedDataService {


	@Transactional(readOnly = true)
	public void fillReportFixedData(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO)
			throws Exception;

	@Transactional(readOnly = true)
	public void fillReportFixedData(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO, boolean appendDtlInd)
			throws Exception;
	
	public void postFillReportData(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO) throws Exception;

}