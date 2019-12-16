package com.bomwebportal.lts.service.report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.BackingStoreException;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RecontractLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.recontractForm.RecontractFormRptDTO;
import com.pccw.rpt.schema.dto.recontractFormSuppl.RecontractFormSupplRptDTO;

public class RecontractReportSupplLtsServiceImpl implements ReportLtsService {

	public void fillReport(ReportDTO pReportDTO, SbOrderDTO pSbOrder, String pLocale, String pRptName, boolean pIsEditable, boolean pIsPrintSignature) {
		
		RecontractFormSupplRptDTO rptDTO = (RecontractFormSupplRptDTO) pReportDTO;
		ServiceDetailLtsDTO ltsSrvDtlLts = LtsSbHelper.getLtsService(pSbOrder);
		ServiceDetailDTO pipbSrvDtl = LtsSbHelper.getAcqPipbService(pSbOrder.getSrvDtls());
		CustomerDetailLtsDTO custDtls = ltsSrvDtlLts.getAccount().getCustomer();
		
		if(LtsBackendConstant.ORDER_TYPE_SB_ACQUISITION.equals(pSbOrder.getOrderType())){ // For order type SBA
			
			
			
			
			if (ltsSrvDtlLts != null && pipbSrvDtl != null) {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");			
				Date date = new Date();
				String orderMode = pSbOrder.getOrderId().substring(0, 1);
				
//					try {
////						rptDTO.setEffectiveDate(dateFormat.format(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(ltsSrvDtlLts.getAppointmentDtl().getAppntStartTime())));
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}

				    if (StringUtils.equalsIgnoreCase(LtsBackendConstant.DOC_TYPE_HKBR, pipbSrvDtl.getPipb().getIdDocType())){
						rptDTO.setFromCompanyName(pipbSrvDtl.getPipb().getCompanyName());
						
						String fromCustTitle = pipbSrvDtl.getPipb().getTitle();
						String fromCustFamilyName = pipbSrvDtl.getPipb().getLastName();
						String fromCustGivenName = pipbSrvDtl.getPipb().getFirstName();
						
						rptDTO.setFromAuthrzName((StringUtils.isEmpty(fromCustTitle)? "" : fromCustTitle) 
						+ " " + (StringUtils.isEmpty(fromCustFamilyName)? "" : fromCustFamilyName) 
						+ " " + (StringUtils.isEmpty(fromCustGivenName)? "" : fromCustGivenName));
						
						rptDTO.setFromBusinessRegNo(pipbSrvDtl.getPipb().getIdDocNum());
	//					rptDTO.setFromCntctNameTelNo(pipbSrvDtl.getPipb().get());
						rptDTO.setFromSignDate(dateFormat.format(date));
						
						if (pIsPrintSignature) {
							byte[] fromCustSign = LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_FROM);							
							rptDTO.setFromSignChop(fromCustSign);
						}
				    }
					
					if (StringUtils.equalsIgnoreCase(LtsBackendConstant.DOC_TYPE_HKBR, custDtls.getIdDocType())){
						rptDTO.setToCompanyName(custDtls.getCompanyName());
						if(ltsSrvDtlLts.getThirdPartyDtl()!=null){
							
							String toCustTitle = ltsSrvDtlLts.getThirdPartyDtl().getTitle();
							String toCustFamilyName = ltsSrvDtlLts.getThirdPartyDtl().getAppntLastName();
							String toCustGivenName = ltsSrvDtlLts.getThirdPartyDtl().getAppntFirstName();

							rptDTO.setToCntctNameTelNo(ltsSrvDtlLts.getThirdPartyDtl().getAppntContactNum());
							rptDTO.setToAuthrzName((StringUtils.isEmpty(toCustTitle)? "" : toCustTitle) 
									+ " " + (StringUtils.isEmpty(toCustFamilyName)? "" : toCustFamilyName) 
									+ " " + (StringUtils.isEmpty(toCustGivenName)? "" : toCustGivenName));
						}
						rptDTO.setToBusinessRegNo(custDtls.getIdDocNum());
						rptDTO.setToSignDate(dateFormat.format(date));
						if (pIsPrintSignature) {
							byte[] toCustSign = LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_TO);
							rptDTO.setToSignChop(toCustSign);
						}
							
					}

	
					
				}
			}
	}
}
