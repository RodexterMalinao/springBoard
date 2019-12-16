package com.bomwebportal.service;

import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.SalesmanDTO;
import com.bomwebportal.dto.SimDTO;

public interface MobileSimInfoService {
	
	public MobileSimInfoDTO validateSim(MobileSimInfoDTO mobileSimInfoDTO);
	public boolean validateSalesCd(MobileSimInfoDTO mobileSimInfoDTO);
	public String getBomWebSimItemId(String basketId, String posItemCd);
	public List<String[]>getBomWebItemCdList (String basketId, String appDate);
	public SalesmanDTO getSalesman (String salesType, String salesCd);	
	public List<String[]> getSimPrice(String itemId, java.util.Date appDate);
	public String getMockSimItemId(String basketId, String simType, String stockPool, java.util.Date appDate);
	public String getPendingOrderExistWithIccid(String iccid);
	public String getPendingOrderExistWithIccidOrderId(String iccid, String orderId);
	public Map<String,String> getDSLocationList();
}
