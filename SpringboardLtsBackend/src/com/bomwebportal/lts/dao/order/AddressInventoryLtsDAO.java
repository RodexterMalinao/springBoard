package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class AddressInventoryLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 6103327273788172313L;
	
	private String orderId; // BOMWEB_ADDR_INVENTORY.ORDER_ID
	private String addrUsage; // BOMWEB_ADDR_INVENTORY.ADDR_USAGE
	private String resourceShortageInd; // BOMWEB_ADDR_INVENTORY.RESOURCE_SHORTAGE_IND
	private String technology; // BOMWEB_ADDR_INVENTORY.TECHNOLOGY
	private String buildingType; // BOMWEB_ADDR_INVENTORY.BUILDING_TYPE
	private String n2BuildingInd; // BOMWEB_ADDR_INVENTORY.N2_BUILDING_IND
	private OraNumber fieldWorkPermitDay; // BOMWEB_ADDR_INVENTORY.FIELD_WORK_PERMIT_DAY
	private String addrId; // BOMWEB_ADDR_INVENTORY.ADDR_ID
	private OraNumber maxBandwidth; // BOMWEB_ADDR_INVENTORY.MAX_BANDWIDTH
	private String fttcInd; // BOMWEB_ADDR_INVENTORY.FTTC_IND
	private String createBy; // BOMWEB_ADDR_INVENTORY.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ADDR_INVENTORY.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ADDR_INVENTORY.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ADDR_INVENTORY.LAST_UPD_DATE
	private String hktPremier; // BOMWEB_ADDR_INVENTORY.HKT_PREMIER

	
	public AddressInventoryLtsDAO() {
		primaryKeyFields = new String[] {"orderId"};
	}

	public String getTableName() {
		return "BOMWEB_ADDR_INVENTORY";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getAddrUsage() {
		return this.addrUsage;
	}

	public String getResourceShortageInd() {
		return this.resourceShortageInd;
	}

	public String getTechnology() {
		return this.technology;
	}

	public String getBuildingType() {
		return this.buildingType;
	}

	public String getN2BuildingInd() {
		return this.n2BuildingInd;
	}

	public String getAddrId() {
		return this.addrId;
	}

	public String getFttcInd() {
		return this.fttcInd;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getFieldWorkPermitDay() {
		return this.fieldWorkPermitDay != null ? this.fieldWorkPermitDay
				.toString() : null;
	}

	public String getMaxBandwidth() {
		return this.maxBandwidth != null ? this.maxBandwidth.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setAddrUsage(String pAddrUsage) {
		this.addrUsage = pAddrUsage;
	}

	public void setResourceShortageInd(String pResourceShortageInd) {
		this.resourceShortageInd = pResourceShortageInd;
	}

	public void setTechnology(String pTechnology) {
		this.technology = pTechnology;
	}

	public void setBuildingType(String pBuildingType) {
		this.buildingType = pBuildingType;
	}

	public void setN2BuildingInd(String pN2BuildingInd) {
		this.n2BuildingInd = pN2BuildingInd;
	}

	public void setAddrId(String pAddrId) {
		this.addrId = pAddrId;
	}

	public void setFttcInd(String pFttcInd) {
		this.fttcInd = pFttcInd;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setFieldWorkPermitDay(String pFieldWorkPermitDay) {
		this.fieldWorkPermitDay = new OraNumber(pFieldWorkPermitDay);
	}

	public void setMaxBandwidth(String pMaxBandwidth) {
		this.maxBandwidth = new OraNumber(pMaxBandwidth);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getHktPremier() {
		return hktPremier;
	}

	public void setHktPremier(String hktPremier) {
		this.hktPremier = hktPremier;
	}
	
	
}
