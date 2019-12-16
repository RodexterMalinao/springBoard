package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class PipbLtsDAO extends DaoBaseImpl {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6423614845868320373L;
	
	
	private String orderId; // BOMWEB_ORDER_PIPB_LTS.ORDER_ID        
	private OraNumber dtlId; // BOMWEB_ORDER_PIPB_LTS.DTL_ID          
	private String isPortBack; // BOMWEB_ORDER_PIPB_LTS.IS_PORT_BACK    
	private String srvNn; // BOMWEB_ORDER_PIPB_LTS.SRV_NN        
	private String pipbAction; // BOMWEB_ORDER_PIPB_LTS.PIPB_ACTION     
	private String operator2n; // BOMWEB_ORDER_PIPB_LTS.OPERATOR2N     
	private String reuseExSocketInd; // BOMWEB_ORDER_PIPB_LTS.REUSE_EX_SOCKET_IND 
	private String reuseSocketType; // BOMWEB_ORDER_PIPB_LTS.RESUE_SOCKET_TYPE
	private String waiveDnChangeInd; // BOMWEB_ORDER_PIPB_LTS.WAIVE_DN_CHANGE_IND  
	private String fromDiffCustInd; // BOMWEB_ORDER_PIPB_LTS.FROM_DIFF_CUST_IND  
	private String idDocType; // BOMWEB_ORDER_PIPB_LTS.ID_DOC_TYPE      
	private String idDocNum; // BOMWEB_ORDER_PIPB_LTS.ID_DOC_NUM      
	private String title; // BOMWEB_ORDER_PIPB_LTS.TITLE        
	private String firstName; // BOMWEB_ORDER_PIPB_LTS.FIRST_NAME      
	private String lastName; // BOMWEB_ORDER_PIPB_LTS.LAST_NAME       
	private String companyName; // BOMWEB_ORDER_PIPB_LTS.COMPANY_NAME     
	private String fromDiffAddrInd; // BOMWEB_ORDER_PIPB_LTS.FROM_DIFF_ADDR_IND   
	private String unitNo; // BOMWEB_ORDER_PIPB_LTS.UNIT_NO
	private String floorNo; // BOMWEB_ORDER_PIPB_LTS.FLOOR_NO
	private String blockNo; // BOMWEB_ORDER_PIPB_LTS.BLOCK_NO
	private String areaCd; // BOMWEB_ORDER_PIPB_LTS.AREA_CD
	private String distNo; // BOMWEB_ORDER_PIPB_LTS.DIST_NO
	private String sectCd; // BOMWEB_ORDER_PIPB_LTS.SECT_CD
	private String strName; // BOMWEB_ORDER_PIPB_LTS.STR_NAME
	private String hiLotNo; // BOMWEB_ORDER_PIPB_LTS.HI_LOT_NO
	private String strCatCd; // BOMWEB_ORDER_PIPB_LTS.STR_CAT_CD
	private String buildNo; // BOMWEB_ORDER_PIPB_LTS.BUILD_NO
	private String strNo; // BOMWEB_ORDER_PIPB_LTS.STR_NO
	private String areaDesc; // BOMWEB_ORDER_PIPB_LTS.AREA_DESC
	private String distDesc; // BOMWEB_ORDER_PIPB_LTS.DIST_DESC
	private String sectDesc; // BOMWEB_ORDER_PIPB_LTS.SECT_DESC
	private String strCatDesc; // BOMWEB_ORDER_PIPB_LTS.STR_CAT_DESC
	private String serbdyno; // BOMWEB_ORDER_PIPB_LTS.SERBDYNO	
	private String duplexInd; // BOMWEB_ORDER_PIPB_LTS.DUPLEX_IND       
	private String duplexAction; // BOMWEB_ORDER_PIPB_LTS.DUPLEX_ACTION     
	private String duplexDn; // BOMWEB_ORDER_PIPB_LTS.DUPLEX_DN 
	private String createBy; // BOMWEB_ORDER_PIPB_LTS.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER_PIPB_LTS.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_PIPB_LTS.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_PIPB_LTS.LAST_UPD_DATE

	

	
	public PipbLtsDAO() {
		primaryKeyFields = new String[] { "orderId", "dtlId" };
	}

	public String getTableName() {
		return "BOMWEB_ORDER_PIPB_LTS";
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}


	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}


	public String getIsPortBack() {
		return isPortBack;
	}


	public void setIsPortBack(String isPortBack) {
		this.isPortBack = isPortBack;
	}


	public String getSrvNn() {
		return srvNn;
	}


	public void setSrvNn(String srvNn) {
		this.srvNn = srvNn;
	}


	public String getPipbAction() {
		return pipbAction;
	}


	public void setPipbAction(String pipbAction) {
		this.pipbAction = pipbAction;
	}


	public String getOperator2n() {
		return operator2n;
	}


	public void setOperator2n(String operator2n) {
		this.operator2n = operator2n;
	}


	public String getReuseExSocketInd() {
		return reuseExSocketInd;
	}

	public void setReuseExSocketInd(String reuseExSocketInd) {
		this.reuseExSocketInd = reuseExSocketInd;
	}

	/**
	 * @return the reuseSocketType
	 */
	public String getReuseSocketType() {
		return reuseSocketType;
	}

	/**
	 * @param reuseSocketType the reuseSocketType to set
	 */
	public void setReuseSocketType(String reuseSocketType) {
		this.reuseSocketType = reuseSocketType;
	}

	public String getWaiveDnChangeInd() {
		return waiveDnChangeInd;
	}


	public void setWaiveDnChangeInd(String waiveDnChangeInd) {
		this.waiveDnChangeInd = waiveDnChangeInd;
	}


	public String getFromDiffCustInd() {
		return fromDiffCustInd;
	}


	public void setFromDiffCustInd(String fromDiffCustInd) {
		this.fromDiffCustInd = fromDiffCustInd;
	}


	public String getIdDocType() {
		return idDocType;
	}


	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}


	public String getIdDocNum() {
		return idDocNum;
	}


	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getFromDiffAddrInd() {
		return fromDiffAddrInd;
	}


	public void setFromDiffAddrInd(String fromDiffAddrInd) {
		this.fromDiffAddrInd = fromDiffAddrInd;
	}
	
	/**
	 * @return the unitNo
	 */
	public String getUnitNo() {
		return unitNo;
	}

	/**
	 * @param unitNo the unitNo to set
	 */
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	/**
	 * @return the floorNo
	 */
	public String getFloorNo() {
		return floorNo;
	}

	/**
	 * @param floorNo the floorNo to set
	 */
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	
	/**
	 * @return the blockNo
	 */
	public String getBlockNo() {
		return blockNo;
	}

	/**
	 * @param blockNo the blockNo to set
	 */
	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	/**
	 * @return the areaCd
	 */
	public String getAreaCd() {
		return areaCd;
	}

	/**
	 * @param areaCd the areaCd to set
	 */
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	/**
	 * @return the distNo
	 */
	public String getDistNo() {
		return distNo;
	}

	/**
	 * @param distNo the distNo to set
	 */
	public void setDistNo(String distNo) {
		this.distNo = distNo;
	}

	/**
	 * @return the sectCd
	 */
	public String getSectCd() {
		return sectCd;
	}

	/**
	 * @param sectCd the sectCd to set
	 */
	public void setSectCd(String sectCd) {
		this.sectCd = sectCd;
	}

	/**
	 * @return the strName
	 */
	public String getStrName() {
		return strName;
	}

	/**
	 * @param strName the strName to set
	 */
	public void setStrName(String strName) {
		this.strName = strName;
	}

	/**
	 * @return the hiLotNo
	 */
	public String getHiLotNo() {
		return hiLotNo;
	}

	/**
	 * @param hiLotNo the hiLotNo to set
	 */
	public void setHiLotNo(String hiLotNo) {
		this.hiLotNo = hiLotNo;
	}

	/**
	 * @return the strCatCd
	 */
	public String getStrCatCd() {
		return strCatCd;
	}

	/**
	 * @param strCatCd the strCatCd to set
	 */
	public void setStrCatCd(String strCatCd) {
		this.strCatCd = strCatCd;
	}

	/**
	 * @return the buildNo
	 */
	public String getBuildNo() {
		return buildNo;
	}

	/**
	 * @param buildNo the buildNo to set
	 */
	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}

	/**
	 * @return the strNo
	 */
	public String getStrNo() {
		return strNo;
	}

	/**
	 * @param strNo the strNo to set
	 */
	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}

	/**
	 * @return the areaDesc
	 */
	public String getAreaDesc() {
		return areaDesc;
	}

	/**
	 * @param areaDesc the areaDesc to set
	 */
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	/**
	 * @return the distDesc
	 */
	public String getDistDesc() {
		return distDesc;
	}

	/**
	 * @param distDesc the distDesc to set
	 */
	public void setDistDesc(String distDesc) {
		this.distDesc = distDesc;
	}

	/**
	 * @return the sectDesc
	 */
	public String getSectDesc() {
		return sectDesc;
	}

	/**
	 * @param sectDesc the sectDesc to set
	 */
	public void setSectDesc(String sectDesc) {
		this.sectDesc = sectDesc;
	}

	/**
	 * @return the strCatDesc
	 */
	public String getStrCatDesc() {
		return strCatDesc;
	}

	/**
	 * @param strCatDesc the strCatDesc to set
	 */
	public void setStrCatDesc(String strCatDesc) {
		this.strCatDesc = strCatDesc;
	}

	/**
	 * @return the serbdyno
	 */
	public String getSerbdyno() {
		return serbdyno;
	}

	/**
	 * @param serbdyno the serbdyno to set
	 */
	public void setSerbdyno(String serbdyno) {
		this.serbdyno = serbdyno;
	}

	public String getDuplexInd() {
		return duplexInd;
	}


	public void setDuplexInd(String duplexInd) {
		this.duplexInd = duplexInd;
	}


	public String getDuplexAction() {
		return duplexAction;
	}


	public void setDuplexAction(String duplexAction) {
		this.duplexAction = duplexAction;
	}


	public String getDuplexDn() {
		return duplexDn;
	}


	public void setDuplexDn(String duplexDn) {
		this.duplexDn = duplexDn;
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


	public String getLastUpdBy() {
		return lastUpdBy;
	}


	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

}
