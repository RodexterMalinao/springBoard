package com.bomwebportal.lts.service.report;

import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.pccw.rpt.schema.dto.ReportDTO;

public interface AmendmentReportLtsService {

	public abstract void fillReport(ReportDTO pReportDTO, SbOrderDTO pSbPorder, String pLocale, String pRptName, boolean pIsEditable, boolean pIsPrintSignature, AmendLtsDTO pAmend);

}