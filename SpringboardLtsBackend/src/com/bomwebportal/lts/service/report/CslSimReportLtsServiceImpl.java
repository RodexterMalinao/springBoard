package com.bomwebportal.lts.service.report;

import org.apache.commons.lang.ArrayUtils;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.cslSimForm.CslSimRptDTO;


public class CslSimReportLtsServiceImpl implements ReportLtsService {

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
		
		CslSimRptDTO cslSim = (CslSimRptDTO)pReportDTO;
		cslSim.setDate(LtsDateFormatHelper.dateConvertFromDAO2DTOWithoutTime(LtsDateFormatHelper.dateConvertFromDTO2DAO(sbOrder.getAppDate())));
		cslSim.setReference(sbOrder.getOrderId());
		
		if (pIsPrintSignature) {
			ServiceDetailDTO ltsService =  LtsSbHelper.getLtsService(sbOrder);
			if (ltsService.getThirdPartyDtl() != null) {
				cslSim.setSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_EYE_3RD_PARTY));	
			}
			else {
				cslSim.setSignature(LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_EYE_CUST));
			}
			
			if (cslSim.getSignature() == null || ArrayUtils.isEmpty(cslSim.getSignature())) {
				cslSim.setNoSignatureR((String)swNoSignatureDisplayLkupCacheService.get(pLocale));
			}
		}

	}

}
