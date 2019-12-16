package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface SummaryBaseService {

	public abstract ServiceSummaryDTO buildSummary(SbOrderDTO pSbOrder, String pLocale, boolean pIsEnquiry);

}