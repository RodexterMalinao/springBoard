package com.bomwebportal.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.eagle.dto.BlacklistRequest;
import com.bomwebportal.eagle.dto.EagleErrorResponse;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;
import com.bomwebportal.mob.validation.exception.AppServiceException;

public interface IGuardService {

	IGuardDTO getRsIGuardDTO(CustomerProfileDTO customerInfo, 
			BasketDTO basketInfo, MobileSimInfoDTO mobileSimInfo, SignoffDTO signInfo,
			MnpDTO mnpInfo, OrderDTO orderInfo
			, List<ComponentDTO> componentList,String srvPlanType);
	
	/*IGuardDTO getCcsIGuardDTO(CustomerProfileDTO customerInfo,
			OrderDTO orderInfo, MRTUI ccsmnpInfo, MobileSimInfoDTO mobileSimInfo, 
			BasketDTO basketInfo, StaffInfoUI salesInfo, StockDTO stockInfo);*/
	public IGuardDTO getCcsIGuardDTO(CustomerProfileDTO customerInfo,
			OrderDTO orderInfo, MRTUI ccsmnpInfo, 
			BasketDTO basketInfo,  List<StockDTO> stockList
			, List<ComponentDTO> componentList,String srvPlanType);
	
	String getIGuardSN();

	List<String> isIGuardOrder(String basketid, String[] vasList, Date appDate);
	
	public IGuardDTO getCcsPreviewIGuardDTO(String orderId, Date appDate,String iGuardSerialNo,  CustomerProfileDTO customerInfo,
			 MRTUI mrtUI, BasketDTO basketDto, StaffInfoUI staffInfoUI, DeliveryUI deliveryUI, BomSalesUserDTO salesUserDto, List<StockDTO> stockList
			, List<ComponentDTO> componentList,String srvPlanType);
	
	public Boolean getIGuardPlan( Date appDate,BasketDTO basketDto, String srvPlanType,String errorMessage);

	public boolean fulfillSubscribeProjectEagle(BigDecimal nsPrice, String srvPlanCd, Date appDate);
	
	public BigDecimal getNsPrice(String posItemCd, Date appDate);
	
	public String getIGuardPlan(BigDecimal nsPrice, int contractPeriod,String srvPlanType, Date appDate);
	
	public String getIGuardPlanPrice(String iGuardPlan);
	
	public String getIGuardSnByOrder(String orderId);
	
	public boolean isIGuardUADPlanSubscribed(String orderId ,String grpId );
	
	public String getIGuardUADDateOfBirth(Date appDate, String dateOfBirth);
	
	public int updateUADOptInd(String orderId, boolean optInInd);
	
	public String getProductCodeBySelectGrpAndLineGrp(String itemId, String itemSelectionGroupId, String serviceLineGroupCategory);
	
	public EagleErrorResponse eagleBlacklist (BlacklistRequest req);
	
}