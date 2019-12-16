package com.bomwebportal.lts.service.report;

import org.apache.commons.lang.ArrayUtils;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.smartWarrantyForm.SmartWrtyRptDTO;

public class SmartWrtyReportLtsServiceImpl implements ReportLtsService {

	protected CodeLkupCacheService swNoSignatureDisplayLkupCacheService;
	
	
	public CodeLkupCacheService getSwNoSignatureDisplayLkupCacheService() {
		return swNoSignatureDisplayLkupCacheService;
	}


	public void setSwNoSignatureDisplayLkupCacheService(
			CodeLkupCacheService swNoSignatureDisplayLkupCacheService) {
		this.swNoSignatureDisplayLkupCacheService = swNoSignatureDisplayLkupCacheService;
	}


	@Override
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		
		SmartWrtyRptDTO smartWrty = (SmartWrtyRptDTO)pReportDTO;
		smartWrty.setDate(LtsDateFormatHelper.dateConvertFromDAO2DTOWithoutTime(LtsDateFormatHelper.dateConvertFromDTO2DAO(sbOrder.getAppDate())));
		smartWrty.setReference(sbOrder.getOrderId());
		
		if (pIsPrintSignature) {
			ServiceDetailDTO ltsService =  LtsSbHelper.getLtsService(sbOrder);
			if (ltsService.getThirdPartyDtl() != null) {
				smartWrty.setSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_EYE_3RD_PARTY));	
			}
			else {
				smartWrty.setSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_EYE_CUST));
			}
			
			if (smartWrty.getSignature() == null || ArrayUtils.isEmpty(smartWrty.getSignature())) {
				smartWrty.setNoSignatureR((String)swNoSignatureDisplayLkupCacheService.get(pLocale));
			}
		}

	}

}
