package com.bomwebportal.lts.service.report;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.pccw.rpt.schema.dto.ReportDTO;

public interface ReportLtsService {

	public abstract void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder, String pLocale, String pRptName, boolean pIsEditable, boolean pIsPrintSignature);

}