package com.bomwebportal.lts.service.report;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.theclub.dto.LtsTheClubResponseFormDTO;
import com.bomwebportal.lts.theclub.service.LtsTheClubPointConstant;
import com.bomwebportal.lts.theclub.service.LtsTheClubPointService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.itemSpec.ItemSpecRptDTO;
import com.pccw.util.spring.SpringApplicationContext;


public class ItemSpecReportLtsServiceImpl implements ReportLtsService {
	
	protected CodeLkupCacheService swNoSignatureDisplayLkupCacheService;

	protected final Log logger = LogFactory.getLog(getClass());
	
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
		
		ItemSpecRptDTO itemSpec = (ItemSpecRptDTO)pReportDTO;
		itemSpec.setReference(sbOrder.getOrderId());
		ServiceDetailDTO ltsService =  LtsSbHelper.getLtsService(sbOrder);
	    
	    //if path of pRptName contains 'the-club', pass the following, check theClubInfo is null or not
	    //to-do: ltsCustomer.docType, docNum
		//the club web service is defined in BomWebPortal
		if (!LtsSbHelper.isOnlineAcquistionOrder(sbOrder)) {
		    if(StringUtils.contains(pRptName, "theClub") && ltsService != null) {	
		    	CustomerDetailLtsDTO ltsCustomer = ltsService.getAccount().getCustomer();
			    if(StringUtils.isNotBlank(ltsCustomer.getIdDocType()) && StringUtils.isNotBlank(ltsCustomer.getIdDocNum())){
					try{
						
							LtsTheClubPointService ltsTheClubPointService = SpringApplicationContext.getBean("ltsTheClubPointService");
							LtsTheClubResponseFormDTO theClubInfo = ltsTheClubPointService.getTheClubMembershipInfo(LtsTheClubPointConstant.MEMBER_SEARCH_TYPE_DOCUMENT, null, null, ltsCustomer.getIdDocType(), ltsCustomer.getIdDocNum());
							logger.info("Calling ltsTheClubPointService.getTheClubMembershipInfo, docType: " + ltsCustomer.getIdDocType() + ", docNum: " + ltsCustomer.getIdDocNum());
							if (theClubInfo != null) {
								itemSpec.setMembershipId(theClubInfo.getMemberId());
							}
						
					}catch(Exception e){
						logger.error(ExceptionUtils.getFullStackTrace(e));
					};
			    }
		    }
		}
	    
		
		
		
		if (pIsPrintSignature) {
				if (ltsService.getThirdPartyDtl() != null) {
					byte[] itemSpecSign = LtsSbHelper.getSignature(sbOrder, (LtsSbHelper.getLtsEyeService(sbOrder) != null) ? LtsBackendConstant.SIGN_TYPE_EYE_3RD_PARTY : LtsBackendConstant.SIGN_TYPE_DEL_3RD_PARTY);
					itemSpec.setItemSpecSign(itemSpecSign);
				}
				else {
					byte[] itemSpecSign = LtsSbHelper.getSignature(sbOrder, (LtsSbHelper.getLtsEyeService(sbOrder) != null) ? LtsBackendConstant.SIGN_TYPE_EYE_CUST : LtsBackendConstant.SIGN_TYPE_DEL_CUST);
					itemSpec.setItemSpecSign(itemSpecSign);
		    
				    if ((itemSpecSign == null || ArrayUtils.isEmpty(itemSpecSign)) && !StringUtils.startsWith(sbOrder.getOrderId(), LtsBackendConstant.ORDER_PREFIX_RETAIL)) {
				    	itemSpec.setNoSignatureR((String)swNoSignatureDisplayLkupCacheService.get(pLocale));
					}
				}
		}
		
	}

}
