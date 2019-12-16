package com.pccw.rpt.schema.dto.nsdFormPipb;

import com.pccw.rpt.schema.dto.ReportDTO;

public class NsdFormPipbRptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 720969791995505501L;

	private String portFrom;
	private String custName;
	private String custDocType;
	private String custDocNum;
	private String userName;
	private String userDocType;
	private String userDocNum;
	private String custAccNum;
	private String termSrvNum;
	private String numList;
	private boolean duplex;
	private String duplexSrvNum;
	private String equipment;
	private String autoPayBankName;
	private String autoPayBankAccNum;
	private String unitNum;
	private String floorNum;
	private String block;
	private String building;
	private String estate;
	private String streetNum;
	private String streetName;
	private String district;
	private String lotNum;
	private String area;
	private String newCorrAddr;
	private String contactName;
	private String contactPhone;
	private String contactFax;
	private boolean termDuplex;
	private String srvNumList;
	private String reciptNetworkOper;

	private String custPassStrikeThrough;
	private String custHkbrStrikeThrough;
	private String custHkidStrikeThrough;
	private String userPassStrikeThrough;
	private String userHkbrStrikeThrough;
	private String userHkidStrikeThrough;
	private String duplexCross;
	private String equipContCross;
	private String equipReturnCross;
	private String equipNilCross;
	private String knStrikeThrough;
	private String ntStrikeThrough;
	private String hkStrikeThrough;
	private String termChkbox;
	
	private byte[] authorisedSign;
	private String authorisedDate;
	private String authorisedName;
	private byte[] autopaySign;


	NsdFormPipbRptDTO() {
		this.equipment = "NIL";
		this.reciptNetworkOper = "PCCW-HKT";
	}

	public String getPortFrom() {
		return portFrom;
	}

	public void setPortFrom(String portFrom) {
		this.portFrom = portFrom;
	}

	public String getNumList() {
		return numList;
	}

	public void setNumList(String numList) {
		this.numList = numList;
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

	public byte[] getAuthorisedSign() {
		return authorisedSign;
	}

	public void setAuthorisedSign(byte[] authorisedSign) {
		this.authorisedSign = authorisedSign;
	}

	public String getAuthorisedDate() {
		return authorisedDate;
	}

	public void setAuthorisedDate(String authorisedDate) {
		this.authorisedDate = authorisedDate;
	}

	public String getAuthorisedName() {
		return authorisedName;
	}

	public void setAuthorisedName(String authorisedName) {
		this.authorisedName = authorisedName;
	}

	public byte[] getAutopaySign() {
		return autopaySign;
	}

	public void setAutopaySign(byte[] autopaySign) {
		this.autopaySign = autopaySign;
	}
	
}
