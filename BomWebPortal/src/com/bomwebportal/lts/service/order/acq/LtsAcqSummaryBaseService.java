package com.bomwebportal.lts.service.order.acq;

import com.bomwebportal.lts.dto.acq.LtsAcqServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsAcqSummaryBaseService {

	public abstract LtsAcqServiceSummaryDTO buildSummary(SbOrderDTO pSbOrder, String pLocale, boolean pIsEnquiry);

}