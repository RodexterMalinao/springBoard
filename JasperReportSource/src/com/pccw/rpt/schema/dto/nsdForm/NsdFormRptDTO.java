package com.pccw.rpt.schema.dto.nsdForm;

import com.pccw.rpt.schema.dto.ReportDTO;

public class NsdFormRptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 720969791995505501L;

	String srvNum;
	String custName;
	String custDocType;
	String custDocNum;
	String userName;
	String userDocType;
	String userDocNum;
	String custAccNum;
	String termSrvNum;
	boolean duplex;
	String duplexSrvNum;
	String equipment;
	String autoPayBankName;
	String autoPayBankAccNum;
	String unitNum;
	String floorNum;
	String block;
	String building;
	String estate;
	String streetNum;
	String streetName;
	String district;
	String lotNum;
	String area;
	String newCorrAddr;
	String contactName;
	String contactPhone;
	String contactFax;
	boolean termDuplex;
	String srvNumList;
	String reciptNetworkOper;

	String custPassStrikeThrough;
	String custHkbrStrikeThrough;
	String custHkidStrikeThrough;
	String userPassStrikeThrough;
	String userHkbrStrikeThrough;
	String userHkidStrikeThrough;
	String duplexCross;
	String equipContCross;
	String equipReturnCross;
	String equipNilCross;
	String knStrikeThrough;
	String ntStrikeThrough;
	String hkStrikeThrough;
	String termChkbox;

	NsdFormRptDTO() {
		this.equipment = "NIL";
		this.reciptNetworkOper = "PCCW-HKT";
	}

	public String getSrvNum() {
		return this.srvNum;
	}

	public void setSrvNum(String pSrvNum) {
		this.srvNum = pSrvNum;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String pCustName) {
		this.custName = pCustName;
	}

	public String getCustDocType() {
		return this.custDocType;
	}

	public void setCustDocType(String pCustDocType) {
		this.custDocType = pCustDocType;
	}

	public String getCustDocNum() {
		return this.custDocNum;
	}

	public void setCustDocNum(String pCustDocNum) {
		this.custDocNum = pCustDocNum;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String pUserName) {
		this.userName = pUserName;
	}

	public String getUserDocType() {
		return this.userDocType;
	}

	public void setUserDocType(String pUserDocType) {
		this.userDocType = pUserDocType;
	}

	public String getUserDocNum() {
		return this.userDocNum;
	}

	public void setUserDocNum(String pUserDocNum) {
		this.userDocNum = pUserDocNum;
	}

	public String getCustAccNum() {
		return this.custAccNum;
	}

	public void setCustAccNum(String pCustAccNum) {
		this.custAccNum = pCustAccNum;
	}

	public String getTermSrvNum() {
		return this.termSrvNum;
	}

	public void setTermSrvNum(String pTermSrvNum) {
		this.termSrvNum = pTermSrvNum;
	}

	public boolean isDuplex() {
		return this.duplex;
	}

	public void setDuplex(boolean pDuplex) {
		this.duplex = pDuplex;
	}

	public String getDuplexSrvNum() {
		return this.duplexSrvNum;
	}

	public void setDuplexSrvNum(String pDuplexSrvNum) {
		this.duplexSrvNum = pDuplexSrvNum;
	}

	public String getEquipment() {
		return this.equipment;
	}

	public void setEquipment(String pEquipment) {
		this.equipment = pEquipment;
	}

	public String getAutoPayBankName() {
		return this.autoPayBankName;
	}

	public void setAutoPayBankName(String pAutoPayBankName) {
		this.autoPayBankName = pAutoPayBankName;
	}

	public String getAutoPayBankAccNum() {
		return this.autoPayBankAccNum;
	}

	public void setAutoPayBankAccNum(String pAutoPayBankAccNum) {
		this.autoPayBankAccNum = pAutoPayBankAccNum;
	}

	public String getUnitNum() {
		return this.unitNum;
	}

	public void setUnitNum(String pUnitNum) {
		this.unitNum = pUnitNum;
	}

	public String getFloorNum() {
		return this.floorNum;
	}

	public void setFloorNum(String pFloorNum) {
		this.floorNum = pFloorNum;
	}

	public String getBlock() {
		return this.block;
	}

	public void setBlock(String pBlock) {
		this.block = pBlock;
	}

	public String getBuilding() {
		return this.building;
	}

	public void setBuilding(String pBuilding) {
		this.building = pBuilding;
	}

	public String getEstate() {
		return this.estate;
	}

	public void setEstate(String pEstate) {
		this.estate = pEstate;
	}

	public String getStreetNum() {
		return this.streetNum;
	}

	public void setStreetNum(String pStreetNum) {
		this.streetNum = pStreetNum;
	}

	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String pStreetName) {
		this.streetName = pStreetName;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String pDistrict) {
		this.district = pDistrict;
	}

	public String getLotNum() {
		return this.lotNum;
	}

	public void setLotNum(String pLotNum) {
		this.lotNum = pLotNum;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String pArea) {
		this.area = pArea;
	}

	public String getNewCorrAddr() {
		return this.newCorrAddr;
	}

	public void setNewCorrAddr(String pNewCorrAddr) {
		this.newCorrAddr = pNewCorrAddr;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String pContactName) {
		this.contactName = pContactName;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String pContactPhone) {
		this.contactPhone = pContactPhone;
	}

	public String getContactFax() {
		return this.contactFax;
	}

	public void setContactFax(String pContactFax) {
		this.contactFax = pContactFax;
	}

	public boolean isTermDuplex() {
		return this.termDuplex;
	}

	public void setTermDuplex(boolean pTermDuplex) {
		this.termDuplex = pTermDuplex;
	}

	public String getSrvNumList() {
		return this.srvNumList;
	}

	public void setSrvNumList(String pSrvNumList) {
		this.srvNumList = pSrvNumList;
	}

	public String getReciptNetworkOper() {
		return this.reciptNetworkOper;
	}

	public void setReciptNetworkOper(String pReciptNetworkOper) {
		this.reciptNetworkOper = pReciptNetworkOper;
	}

	public String getCustPassStrikeThrough() {
		return this.custPassStrikeThrough;
	}

	public void setCustPassStrikeThrough(String pCustPassStrikeThrough) {
		this.custPassStrikeThrough = pCustPassStrikeThrough;
	}

	public String getCustHkbrStrikeThrough() {
		return this.custHkbrStrikeThrough;
	}

	public void setCustHkbrStrikeThrough(String pCustHkbrStrikeThrough) {
		this.custHkbrStrikeThrough = pCustHkbrStrikeThrough;
	}

	public String getCustHkidStrikeThrough() {
		return this.custHkidStrikeThrough;
	}

	public void setCustHkidStrikeThrough(String pCustHkidStrikeThrough) {
		this.custHkidStrikeThrough = pCustHkidStrikeThrough;
	}

	public String getUserPassStrikeThrough() {
		return this.userPassStrikeThrough;
	}

	public void setUserPassStrikeThrough(String pUserPassStrikeThrough) {
		this.userPassStrikeThrough = pUserPassStrikeThrough;
	}

	public String getUserHkbrStrikeThrough() {
		return this.userHkbrStrikeThrough;
	}

	public void setUserHkbrStrikeThrough(String pUserHkbrStrikeThrough) {
		this.userHkbrStrikeThrough = pUserHkbrStrikeThrough;
	}

	public String getUserHkidStrikeThrough() {
		return this.userHkidStrikeThrough;
	}

	public void setUserHkidStrikeThrough(String pUserHkidStrikeThrough) {
		this.userHkidStrikeThrough = pUserHkidStrikeThrough;
	}

	public String getDuplexCross() {
		return this.duplexCross;
	}

	public void setDuplexCross(String pDuplexCross) {
		this.duplexCross = pDuplexCross;
	}

	public String getEquipContCross() {
		return this.equipContCross;
	}

	public void setEquipContCross(String pEquipContCross) {
		this.equipContCross = pEquipContCross;
	}

	public String getEquipReturnCross() {
		return this.equipReturnCross;
	}

	public void setEquipReturnCross(String pEquipReturnCross) {
		this.equipReturnCross = pEquipReturnCross;
	}

	public String getEquipNilCross() {
		return this.equipNilCross;
	}

	public void setEquipNilCross(String pEquipNilCross) {
		this.equipNilCross = pEquipNilCross;
	}

	public String getKnStrikeThrough() {
		return this.knStrikeThrough;
	}

	public void setKnStrikeThrough(String pKnStrikeThrough) {
		this.knStrikeThrough = pKnStrikeThrough;
	}

	public String getNtStrikeThrough() {
		return this.ntStrikeThrough;
	}

	public void setNtStrikeThrough(String pNtStrikeThrough) {
		this.ntStrikeThrough = pNtStrikeThrough;
	}

	public String getHkStrikeThrough() {
		return this.hkStrikeThrough;
	}

	public void setHkStrikeThrough(String pHkStrikeThrough) {
		this.hkStrikeThrough = pHkStrikeThrough;
	}

	public String getTermChkbox() {
		return this.termChkbox;
	}

	public void setTermChkbox(String pTermChkbox) {
		this.termChkbox = pTermChkbox;
	}
}
