package com.bomwebportal.lts.service.report;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.concertTicketForm.ConcertTicketRptDTO;

public class ConcertTicketReportLtsServiceImpl implements ReportLtsService{
	
	protected CodeLkupCacheService swNoSignatureDisplayLkupCacheService;
	
	public CodeLkupCacheService getSwNoSignatureDisplayLkupCacheService() {
		return swNoSignatureDisplayLkupCacheService;
	}

	public void setSwNoSignatureDisplayLkupCacheService(
			CodeLkupCacheService swNoSignatureDisplayLkupCacheService) {
		this.swNoSignatureDisplayLkupCacheService = swNoSignatureDisplayLkupCacheService;
	}
	
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		
		ConcertTicketRptDTO rptDTO = (ConcertTicketRptDTO)pReportDTO;
	
		if (pIsPrintSignature) {
			ServiceDetailDTO ltsService =  LtsSbHelper.getLtsService(sbOrder);
				if (ltsService.getThirdPartyDtl() != null) {
					byte[] concertTicketSign = LtsSbHelper.getSignature(sbOrder, (LtsSbHelper.getLtsEyeService(sbOrder) != null) ? LtsBackendConstant.SIGN_TYPE_EYE_3RD_PARTY : LtsBackendConstant.SIGN_TYPE_DEL_3RD_PARTY);
					rptDTO.setConcertTicketSign(concertTicketSign);
				}
				else {
					byte[] concertTicketSign = LtsSbHelper.getSignature(sbOrder, (LtsSbHelper.getLtsEyeService(sbOrder) != null) ? LtsBackendConstant.SIGN_TYPE_EYE_CUST : LtsBackendConstant.SIGN_TYPE_DEL_CUST);
				    rptDTO.setConcertTicketSign(concertTicketSign);
		    
				    if ((concertTicketSign == null || ArrayUtils.isEmpty(concertTicketSign)) && !StringUtils.startsWith(sbOrder.getOrderId(), LtsBackendConstant.ORDER_PREFIX_RETAIL)) {
				    	rptDTO.setNoSignatureR((String)swNoSignatureDisplayLkupCacheService.get(pLocale));
					}
				}
		}
	}

}
