package com.bomwebportal.lts.service.report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class RecontractReportLtsServiceImpl implements ReportLtsService {

	public void fillReport(ReportDTO pReportDTO, SbOrderDTO pSbOrder, String pLocale, String pRptName, boolean pIsEditable, boolean pIsPrintSignature) {
		
		RecontractFormRptDTO rptDTO = (RecontractFormRptDTO) pReportDTO;
		ServiceDetailLtsDTO ltsSrvDtlLts = LtsSbHelper.getLtsService(pSbOrder);
		ServiceDetailDTO pipbSrvDtl = LtsSbHelper.getAcqPipbService(pSbOrder.getSrvDtls());
		
		
		if(LtsBackendConstant.ORDER_TYPE_SB_ACQUISITION.equals(pSbOrder.getOrderType())){ // For order type SBA
			
			if (ltsSrvDtlLts != null && pipbSrvDtl != null) {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");			
				Date date = new Date();
				String orderMode = pSbOrder.getOrderId().substring(0, 1);
				CustomerDetailLtsDTO custDtls = ltsSrvDtlLts.getAccount().getCustomer();

					rptDTO.setSrvNum(pipbSrvDtl.getSrvNum());
					
					try {
						rptDTO.setEffectiveDate(dateFormat.format(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(ltsSrvDtlLts.getAppointmentDtl().getAppntStartTime())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (StringUtils.equalsIgnoreCase(LtsBackendConstant.DOC_TYPE_PASSPORT, pipbSrvDtl.getPipb().getIdDocType())
							||StringUtils.equalsIgnoreCase(LtsBackendConstant.DOC_TYPE_HKID, pipbSrvDtl.getPipb().getIdDocType())){
						rptDTO.setFromCustTitle(pipbSrvDtl.getPipb().getTitle());
						rptDTO.setFromCustFamilyName(pipbSrvDtl.getPipb().getLastName());
						rptDTO.setFromCustGivenName(pipbSrvDtl.getPipb().getFirstName());
                        StringBuilder sb = new StringBuilder();
						if (pipbSrvDtl.getPipb().getIdDocNum().length() > 8) {
							sb.append(StringUtils.substring(pipbSrvDtl.getPipb().getIdDocNum(), 0, 3));
							sb.append("****");
							sb.append(StringUtils.substring(pipbSrvDtl.getPipb().getIdDocNum(), 7));
						} else {
							sb.append(pipbSrvDtl.getPipb().getIdDocNum());
						}
						rptDTO.setFromCustDocNum(LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode)?sb.toString():null);
						rptDTO.setFromCustDocType(pipbSrvDtl.getPipb().getIdDocType());
						if (pIsPrintSignature) {
							byte[] fromCustSign = LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_FROM);
							
							rptDTO.setFromCustSignature(fromCustSign);
							rptDTO.setFromSignDate(dateFormat.format(date));

						}
					}

					
					if (StringUtils.equalsIgnoreCase(LtsBackendConstant.DOC_TYPE_PASSPORT, custDtls.getIdDocType())
							||StringUtils.equalsIgnoreCase(LtsBackendConstant.DOC_TYPE_HKID, custDtls.getIdDocType())){
						rptDTO.setToCustTitle(custDtls.getTitle());
						rptDTO.setToCustFamilyName(custDtls.getLastName());
						rptDTO.setToCustGivenName(custDtls.getFirstName());
						rptDTO.setToCustDocNum(LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode)?custDtls.getIdDocNum():null);
						rptDTO.setToCustDocType(custDtls.getIdDocType());
						if(pSbOrder.getContact()!=null){
							if(StringUtils.isNotBlank(pSbOrder.getContact().getContactMobile())){
								rptDTO.setToCustContactNum(pSbOrder.getContact().getContactMobile());
							}else if(StringUtils.isNotBlank(pSbOrder.getContact().getContactFixedLine())){
								rptDTO.setToCustContactNum(pSbOrder.getContact().getContactFixedLine());
							}else if(StringUtils.isNotBlank(pSbOrder.getContact().getContactPhone())){
								rptDTO.setToCustContactNum(pSbOrder.getContact().getContactPhone());
							}
						}
						if (pIsPrintSignature) {
							byte[] toCustSign = LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_TO);
							
							rptDTO.setToCustSignature(toCustSign);
							rptDTO.setToCustPersonInfoSignature(toCustSign);
							rptDTO.setToSignDate(dateFormat.format(date));
						}
					}
					
				}
			}else{ // For other order type
				
			if (ltsSrvDtlLts != null) {
				RecontractLtsDTO recontractDTO = ltsSrvDtlLts.getRecontract();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");			
				Date date = new Date();
				String orderMode = pSbOrder.getOrderId().substring(0, 1);
				
				if (ltsSrvDtlLts != null) {
					rptDTO.setSrvNum(ltsSrvDtlLts.getSrvNum());
					
					try {
						rptDTO.setEffectiveDate(dateFormat.format(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(ltsSrvDtlLts.getAppointmentDtl().getAppntStartTime())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					rptDTO.setCallingCardSrv(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDTO.getCallingCardInd()));
					rptDTO.setMobileIDDSrv(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDTO.getMobIddInd()));
					rptDTO.setFixedIDDSrv(LtsBackendConstant.RECONTRACT_SRV_HANDLE_CARRY.equals(recontractDTO.getFixedIddInd()));
	
					CustomerDetailLtsDTO custDtls = ltsSrvDtlLts.getProfileAccount().getCustomer();
					rptDTO.setFromCustTitle(custDtls.getTitle());
					rptDTO.setFromCustFamilyName(custDtls.getLastName());
					rptDTO.setFromCustGivenName(custDtls.getFirstName());
					rptDTO.setOptOut(StringUtils.equals(recontractDTO.getOptOut(), "Y"));
					StringBuilder sb = new StringBuilder();
					
					if (custDtls.getIdDocNum().length() > 8) {
						sb.append(StringUtils.substring(custDtls.getIdDocNum(), 0, 3));
						sb.append("****");
						sb.append(StringUtils.substring(custDtls.getIdDocNum(), 7));
					} else {
						sb.append(custDtls.getIdDocNum());
					}
					rptDTO.setFromCustDocNum(LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode)?sb.toString():null);
					rptDTO.setFromCustDocType(custDtls.getIdDocType());
					rptDTO.setToCustTitle(recontractDTO.getTitle());
					rptDTO.setToCustFamilyName(recontractDTO.getCustLastName());
					rptDTO.setToCustGivenName(recontractDTO.getCustFirstName());
					rptDTO.setToCustDocNum(LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode)?recontractDTO.getIdDocNum():null);
					rptDTO.setToCustDocType(recontractDTO.getIdDocType());
					rptDTO.setToCustContactNum(recontractDTO.getContactNum());
					rptDTO.setTransMode(recontractDTO.getTransMode());
	
					if (pIsPrintSignature) {
						byte[] toCustSign = LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_TO);
						byte[] fromCustSign = LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_RECONTRACT_FROM);
						
						if (StringUtils.equals(LtsBackendConstant.RECONTRACT_MODE_BOTH, recontractDTO.getTransMode())) {
							rptDTO.setFromCustSignature(fromCustSign);
							rptDTO.setFromSignDate(dateFormat.format(date));
						} else {
							rptDTO.setToCustIndemnitySign(toCustSign);
						}
						rptDTO.setToCustSignature(toCustSign);
						rptDTO.setToCustPersonInfoSignature(toCustSign);
						rptDTO.setToSignDate(dateFormat.format(date));
					}
				}
			}
		}
	}
}
