package com.bomwebportal.lts.service.report;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.productSpec.ProductSpecRptDTO;

public class ProductSpecReportLtsServiceImpl implements ReportLtsService {

	@Override
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		
		ProductSpecRptDTO productSpec = (ProductSpecRptDTO)pReportDTO;
		productSpec.setDate(LtsDateFormatHelper.dateConvertFromDAO2DTOWithoutTime(LtsDateFormatHelper.dateConvertFromDTO2DAO(sbOrder.getAppDate())));
		productSpec.setReference(sbOrder.getOrderId());
		
		if (pIsPrintSignature) {
			ServiceDetailDTO eyeService =  LtsSbHelper.getLtsEyeService(sbOrder);
			if (eyeService.getThirdPartyDtl() != null) {
				productSpec.setSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_EYE_3RD_PARTY));	
			}
			else {
				productSpec.setSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_EYE_CUST));
			}
		}
	}

}
