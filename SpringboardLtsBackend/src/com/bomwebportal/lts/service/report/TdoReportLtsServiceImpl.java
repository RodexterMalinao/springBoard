package com.bomwebportal.lts.service.report;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.tdo.keyInfoSheet.KeyInfoSheetRptDTO;
import com.pccw.rpt.service.ReportFixedDataService;

public class TdoReportLtsServiceImpl implements ReportLtsService {

	private ReportFixedDataService reportFixedDataService;
	
	private static final String ONLINE_REPORT_SUFFIX = "/online";
	private static final String RETAIL_REPORT_SUFFIX = "/retail";
	
	
	public ReportFixedDataService getReportFixedDataService() {
		return reportFixedDataService;
	}

	public void setReportFixedDataService(
			ReportFixedDataService reportFixedDataService) {
		this.reportFixedDataService = reportFixedDataService;
	}

	private boolean getPcdBundleFreeFromSbOrder(SbOrderDTO sbOrder, String itemType) {
		String pcdBundleFree = "";
		
		if(sbOrder != null && sbOrder.getSrvDtls() != null)
		{
			ServiceDetailDTO[] tempSrvDtls = sbOrder.getSrvDtls();
			for (int i=0; i<tempSrvDtls.length; i++)
			{
				if(tempSrvDtls[i] instanceof ServiceDetailLtsDTO)
				{
					ServiceDetailLtsDTO tempSrvLtsDtls = (ServiceDetailLtsDTO) tempSrvDtls[i];					
					if(tempSrvLtsDtls.getItemDtls() != null)
					{
						ItemDetailLtsDTO[] tempItemDetails = tempSrvLtsDtls.getItemDtls();
						for (int j=0; j<tempItemDetails.length; j++)
						{
							if(tempItemDetails[j].getItemType().equalsIgnoreCase(itemType))
							{
								if(tempItemDetails[j].getPcdBundleFree()!=null && !tempItemDetails[j].getPcdBundleFree().equals(""))
								{
									pcdBundleFree = tempItemDetails[j].getPcdBundleFree();
								}
							}							
						}
					}
				}
			}				
		}
		
		return pcdBundleFree!=null&&pcdBundleFree.equalsIgnoreCase("Y")?true:false;		
	}
	
	@Override
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		
		KeyInfoSheetRptDTO keyInfoSheetRpt = (KeyInfoSheetRptDTO) pReportDTO;
		String signatureType = null;
		String tdoType = null;
		if (StringUtils.equals(pRptName, LtsBackendConstant.RPT_NAME_TDO_EYE)) {
			tdoType = "eye";
			signatureType = LtsBackendConstant.SIGN_TYPE_EYE_TDO;
		}
		else if (StringUtils.equals(pRptName, LtsBackendConstant.RPT_NAME_TDO_2DEL)) {
			tdoType = "2del";
			signatureType = LtsBackendConstant.SIGN_TYPE_DEL_TDO;
		}
		else if (StringUtils.equals(pRptName, LtsBackendConstant.RPT_NAME_TDO_0060)) {
			tdoType = "0060";
			signatureType = LtsBackendConstant.SIGN_TYPE_IDD_0060_TDO;
		}
		else if (StringUtils.equals(pRptName, LtsBackendConstant.RPT_NAME_TDO_NOWTV)) {
			tdoType = "tv";
			signatureType = LtsBackendConstant.SIGN_TYPE_NOWTV_TDO;
		}
		
		if(getPcdBundleFreeFromSbOrder(sbOrder, LtsBackendConstant.ITEM_TYPE_PLAN)){
			pRptName = LtsBackendConstant.RPT_NAME_TDO_DEL_FREE;
		}
		
		else if (StringUtils.equals(pRptName, LtsBackendConstant.RPT_NAME_TDO_STD_VAS)) {
			pRptName = LtsBackendConstant.RPT_NAME_TDO_STD_VAS;
		}

		
		try {
			ServiceDetailDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
			this.fillReportFixedData(keyInfoSheetRpt, pLocale, pRptName, pIsEditable, tdoType, sbOrder);
			keyInfoSheetRpt.setApplicationDate(LtsDateFormatHelper.dateConvertFromDAO2DTOWithoutTime(LtsDateFormatHelper.dateConvertFromDTO2DAO(sbOrder.getAppDate())));
			keyInfoSheetRpt.setApplicationNo(sbOrder.getOrderId());
			String custName = null;
			
			if (ltsService.getRecontract() != null) {
				if (StringUtils.isBlank(ltsService.getRecontract().getCompanyName())) {
					custName = ltsService.getRecontract().getCustLastName() + " " + ltsService.getRecontract().getCustFirstName();
				} else {
					custName = ltsService.getRecontract().getCompanyName();
				}
			} else {
				CustomerDetailLtsDTO cust = ltsService.getAccount().getCustomer();
				
				if (StringUtils.isBlank(cust.getCompanyName())) {
					custName = StringUtils.isBlank(cust.getTitle())? " " : cust.getTitle() + " " + cust.getLastName() +  " " + cust.getFirstName();
				} else {
					custName = cust.getCompanyName();
				}
			}
			keyInfoSheetRpt.setCustName(custName);
			
			if (pIsPrintSignature) {
				keyInfoSheetRpt.setCustSignature(LtsSbHelper.getSignature(sbOrder, signatureType));	
			}
		} catch (Exception e) {
			throw new AppRuntimeException(e.getCause());
		}
	}

	
	private void fillReportFixedData(ReportDTO pReportDTO, String pLocale, String pRptName, boolean pIsEditable, String tdoType, SbOrderDTO sbOrder)
	 	throws Exception {
		Map<String, Object> rptParmMap = new HashMap<String, Object>();
		rptParmMap.put("TDO_TYPE", tdoType);
		if (pIsEditable) {
			rptParmMap.put("EDIT_BUTTON", "Y");
		}
		
		this.reportFixedDataService.fillReportFixedData(pRptName, pLocale, rptParmMap, pReportDTO);
		
		this.reportFixedDataService
				.fillReportFixedData(pRptName + (
						LtsSbHelper.isOnlineAcquistionOrder(sbOrder) ? 
								 ONLINE_REPORT_SUFFIX : 
								 RETAIL_REPORT_SUFFIX ), pLocale, rptParmMap,
						pReportDTO);
	}
}
