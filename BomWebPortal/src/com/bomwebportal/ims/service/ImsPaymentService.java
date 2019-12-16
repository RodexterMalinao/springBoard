package com.bomwebportal.ims.service;



import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.SalesmanDTO;
import com.bomwebportal.ims.dto.BasketDetailsDTO;
import com.bomwebportal.ims.dto.BomwebOTDTO;
import com.bomwebportal.ims.dto.ImsInstallationInstallmentPlanDTO;
import com.bomwebportal.ims.dto.ui.InstallFeeUI;

public interface ImsPaymentService {
	
	//public boolean validateSalesCd(MobileSimInfoDTO mobileSimInfoDTO);
	public SalesmanDTO getSalesman (String salesType, String salesCd);
	public String getOrgSalesCd(String username);
	public String getStaffNameByOrgSalesCd(String username);
	public String getStaffNameBySalesCd(String salesCd);
	public String getStaffChannelCdBySalesCd(String salesCd);
	public String getShopContact(String shopCd);
	public InstallFeeUI getOtAmount(String technology, String housingtype, String serbdyno);
	public String getIsValidForWaive(String serbdyno, String prodType, String bandwidth, String housingType, Date appdate);
	public String getIsValidForInstallInstallment(String technology, String housingtype);
	//
	public List<BasketDetailsDTO> getBasketInfo(String basket_id);	

	//kinman 2013
	public InstallFeeUI getImsInstallationInstallmentPlanList(String technology, String housingtype, String serbdyno, String floor, String flat);
	//kinman 20140428
	public String getDeflaultSourceCode(String username);
	
	public void logCreditCardInfo(String orderId,String loginId, String srvNo ,String cardNo,String staffNo);
	
	public void insertCeksRecord(String order_id, String app_id, String reference_no, String ret_code, String pay_amount, String card_pan, 
			String exp_date, String ret_parm, String create_by, String gateway_code); // 20190503, BOM2019038, order_id and reference_no = null
	
	public String getSalemanContactNumByStaffId(String staff_id);

	public String getReferrerStaffNameByNo(String referrerStaffNo);
	
	public String getSalesContactNumber(int channelId, String shopCd, String staffId, String salesCd);
	
	public List<BomwebOTDTO> getOverrideOTC(String staffId, String application, String prodType, String housingType, String sbno, String orderType, String fsaMarker);
	
	public InstallFeeUI getOTCByBasketId(String basketId);	
	
	public InstallFeeUI getSpecialOTC(String basketId,String[] itemIdStr);
		
}
