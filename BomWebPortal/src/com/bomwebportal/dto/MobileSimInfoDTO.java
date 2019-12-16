package com.bomwebportal.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.util.Utility;

public class MobileSimInfoDTO implements java.io.Serializable{

	private static final long serialVersionUID = 2675440280883544263L;
	
	private String orderId;
	
	private String iccid;
	
	private String imsi;
	
	private String puk1;
	
	private String puk2;
	
	private String itemCd;
	
	private String salesType;
	
	//add by eliot 20110616
	private String salesName;
	
	private String salesContactNum;
	
	//add by eliot 20110622
	private String salesErrCode;
	//add by eliot 20110622
	private String salesErrMsg;
		
	//add by eliot 20110623
	private String actionType;
	
	private String salesCd;
	
	private String cmrid;

	private String imei;
	
	private int hwInvStatus;
	
	private String bomWebItemCd;//add by wilson 20110128
	private List<StockAssgnDTO> stockAssgnList;
	private String simSalesMemoNum;
	private String simSalesMemoInd;
	private int channelId;
	private String channelCd;
	private String centerCd;
	private String teamCd;
	private String shopCd;
	private String aoInd;
	private boolean amend;
	private List<String> simList;
	private String simType;
	
	private String originalIccid;
	private Date appDate;
	private String simWaiveReason;
	private double simCharge;
	private boolean simWaivable;
	private String simBrandType;
	private String selectedSimItemId;
	private String selectedSimItemCd;
	private String rpWaiveReason;
	private double rpCharge;
	
	
	//Add by Whitney 20160825
	private String refSalesId;
	private String refSalesName;
	private String refSalesCentre;
	private String refSalesTeam;
	private boolean manualInputBool;
	private String workStatus;
	
	private String salesLocation;
	
	String stockAssgnListJson;

	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	/**
	 * An object map key to DTO value
	 */
	private Map<String, Object> objectsMap;
	
	/**
	 * Get specific DTO object value which map to certain key
	 * @param key
	 * @return DTO object
	 */
	public Object getValue(String key) {
		if (this.objectsMap == null || this.objectsMap.isEmpty()) {
			return null;
		} else {
			return this.objectsMap.get(key);
		}
	}
	/**
	 * Set specific DTO object value into map which match with a unique key
	 * @param key
	 * @param value DTO object
	 */
	public void setValue(String key, Object value) {
		if (this.objectsMap == null) {
			objectsMap = new HashMap<String, Object>();
		}
		
		objectsMap.put(key, value);
	}
	
	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getPuk1() {
		return puk1;
	}

	public void setPuk1(String puk1) {
		this.puk1 = puk1;
	}

	public String getPuk2() {
		return puk2;
	}

	public void setPuk2(String puk2) {
		this.puk2 = puk2;
	}

	public String getItemCd() {
		return itemCd;
	}

	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	public String getSalesCd() {
		return salesCd;
	}

	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}
	
	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesContactNum() {
		return salesContactNum;
	}
	public void setSalesContactNum(String salesContactNum) {
		this.salesContactNum = salesContactNum;
	}
	public String getCmrid() {
		return cmrid;
	}

	public void setCmrid(String cmrid) {
		this.cmrid = cmrid;
	}
	
	public int getHwInvStatus() {
		return hwInvStatus;
	}

	public void setHwInvStatus(int hwInvStatus) {
		this.hwInvStatus = hwInvStatus;
	}

	public String getBomWebItemCd() {
		return bomWebItemCd;
	}

	public void setBomWebItemCd(String bomWebItemCd) {
		this.bomWebItemCd = bomWebItemCd;
	}

	public List<StockAssgnDTO> getStockAssgnList() {
		return stockAssgnList;
	}
	public void setStockAssgnList(List<StockAssgnDTO> stockAssgnList) {
		this.stockAssgnList = stockAssgnList;
	}
	public String getSimSalesMemoNum() {
		return simSalesMemoNum;
	}
	public void setSimSalesMemoNum(String simSalesMemoNum) {
		this.simSalesMemoNum = simSalesMemoNum;
	}
	public String getSimSalesMemoInd() {
		return simSalesMemoInd;
	}
	public void setSimSalesMemoInd(String simSalesMemoInd) {
		this.simSalesMemoInd = simSalesMemoInd;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public String getCenterCd() {
		return centerCd;
	}
	public void setCenterCd(String centerCd) {
		this.centerCd = centerCd;
	}
	public String getTeamCd() {
		return teamCd;
	}
	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}
	public String getSalesErrCode() {
		return salesErrCode;
	}

	public void setSalesErrCode(String salesErrCode) {
		this.salesErrCode = salesErrCode;
	}

	public String getSalesErrMsg() {
		return salesErrMsg;
	}

	public void setSalesErrMsg(String salesErrMsg) {
		this.salesErrMsg = salesErrMsg;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getAoInd() {
		return aoInd;
	}

	public void setAoInd(String aoInd) {
		this.aoInd = aoInd;
	}
	public boolean isAmend() {
		return amend;
	}
	public void setAmend(boolean amend) {
		this.amend = amend;
	}
	public List<String> getSimList(){
		return simList;
	}
	public void setSimList(List<String> simList){
		this.simList = simList;
	}
	public String getSimType() {
		return simType;
	}
	public void setSimType(String simType) {
		this.simType = simType;
	}
	public String getOriginalIccid() {
		return originalIccid;
	}
	public void setOriginalIccid(String originalIccid) {
		this.originalIccid = originalIccid;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getSimWaiveReason() {
		return simWaiveReason;
	}
	public void setSimWaiveReason(String simWaiveReason) {
		this.simWaiveReason = simWaiveReason;
	}
	public double getSimCharge() {
		return simCharge;
	}
	public void setSimCharge(double simCharge) {
		this.simCharge = simCharge;
	}
	public boolean isSimWaivable() {
		return simWaivable;
	}
	public void setSimWaivable(boolean simWaivable) {
		this.simWaivable = simWaivable;
	}
	public String getSimBrandType() {
		return simBrandType;
	}
	public void setSimBrandType(String simBrandType) {
		this.simBrandType = simBrandType;
	}
	public String getSelectedSimItemId() {
		return selectedSimItemId;
	}
	public void setSelectedSimItemId(String selectedSimItemId) {
		this.selectedSimItemId = selectedSimItemId;
	}
	public String getSelectedSimItemCd() {
		return selectedSimItemCd;
	}
	public void setSelectedSimItemCd(String selectedSimItemCd) {
		this.selectedSimItemCd = selectedSimItemCd;
	}
	public String getRpWaiveReason() {
		return rpWaiveReason;
	}
	public void setRpWaiveReason(String rpWaiveReason) {
		this.rpWaiveReason = rpWaiveReason;
	}
	public double getRpCharge() {
		return rpCharge;
	}
	public void setRpCharge(double rpCharge) {
		this.rpCharge = rpCharge;
	}
	public String getRefSalesId() {
		return refSalesId;
	}
	public void setRefSalesId(String refSalesId) {
		this.refSalesId = refSalesId;
	}
	public String getRefSalesName() {
		return refSalesName;
	}
	public void setRefSalesName(String refSalesName) {
		this.refSalesName = refSalesName;
	}
	public String getRefSalesCentre() {
		return refSalesCentre;
	}
	public void setRefSalesCentre(String refSalesCentre) {
		this.refSalesCentre = refSalesCentre;
	}
	public String getRefSalesTeam() {
		return refSalesTeam;
	}
	public void setRefSalesTeam(String refSalesTeam) {
		this.refSalesTeam = refSalesTeam;
	}	
	public boolean isManualInputBool() {
		return manualInputBool;
	}
	public void setManualInputBool(boolean manualInputBool) {
		this.manualInputBool = manualInputBool;
	}
	public String getSalesLocation() {
		return salesLocation;
	}
	public void setSalesLocation(String salesLocation) {
		this.salesLocation = salesLocation;
	}
	public String getStockAssgnListJson() {
		return Utility.toPrettyJson(this.getStockAssgnList());
	}
	public void setStockAssgnListJson(String stockAssgnListJson) {
		this.stockAssgnListJson = stockAssgnListJson;
	}
	
}
