package com.bomwebportal.ims.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.report.ReportDTO;
import com.bomwebportal.dto.report.RptHSTradeDescDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsRptBasketDtlDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptChannelDTO;
import com.bomwebportal.ims.dto.ImsRptGiftDTO;
import com.bomwebportal.ims.dto.ImsRptServicePlanDetailDTO;
import com.bomwebportal.ims.dto.report.RptServiceInfoDTO;
import com.bomwebportal.ims.dto.ui.NowTVAddUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;


public interface ImsReport2Service {
	
	public ImsRptBasketDtlDTO getBasketDetail(String basketId, String locale);
	public List<ImsRptBasketItemDTO> getBasketItem(String basketId, String itemIdStr, String locale);
	public List<ImsRptBasketItemDTO> getOptServiceY(String basketId, String itemIdStr, String locale, String orderId, boolean isAF);
	public List<ImsRptBasketItemDTO> getOptServiceN(String basketId, String itemIdStr, String locale, String orderId, boolean isAF);
	public List<ImsRptBasketItemDTO> getBackendVas(String orderId);	
	public List<ImsRptBasketItemDTO> getNtvItem(String itemIdStr, String locale, boolean isAF);
	public List<ImsRptChannelDTO> getNtvChannelFree(String itemIdStr, String locale, String warrPeriod, String tvCredit);
	public List<ImsRptChannelDTO> getNtvChannelPay(String itemIdStr, String locale, String warrPeriod, String tvCredit);
	public List<ImsRptChannelDTO> getBackendChannel(String orderId);
	public String getQosMeasureInd(String itemIdStr);
	public ImsRptServicePlanDetailDTO getServPlanDesc(OrderImsUI order, String locale, Boolean isPT,Boolean isDs);
	public List<String> getPISPdf(String itemIdStr, String locale);
	public HashMap<String, HashMap<String, RptServiceInfoDTO>> getStaticReportWords(Boolean isPT,Boolean isDs);
	public void get3rdPartyCreditCardFormStaticReportWords(String rptName, HashMap<String, HashMap<String, String>> dBRptStaticWords); // martin 20160422, third party credit card form setup
	public String isUseDBRptStaticWords();
	public String isAFShowALL();
	public List<ImsRptGiftDTO> getGiftList(String itemIdStr, String locale, String giftType);
	public NowTVAddUI getNewTVPricingDtl(String housingType, String custType, String payMethod,String contract_period, String locale) throws DAOException; //martin
	public NowTVAddUI getNewPricingItem(OrderImsUI order, String locale);//Gary added for new pricing
	public String getSalesChannel (String shop_cd, String staff_id);
}
