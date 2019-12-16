package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;

public class Service0060DetailLtsDAO extends DaoBaseImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7141386403402291041L;
	
	private String orderId; // BOMWEB_ORDER_SERVICE_0060.ORDER_ID
	private String typeOfSrv; // BOMWEB_ORDER_SERVICE_0060.TYPE_OF_SRV
	private String datCd; // BOMWEB_ORDER_SERVICE_0060.DAT_CD
	private String srvNum; // BOMWEB_ORDER_SERVICE_0060.SRV_NUM
	private String ioInd; // BOMWEB_ORDER_SERVICE_0060.IO_IND
	private String createBy; // BOMWEB_ORDER_SERVICE_0060.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER_SERVICE_0060.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_SERVICE_0060.LAST_UPD_BY
	private OraDateLastUpdDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_SERVICE_0060.LAST_UPD_DATE

	public Service0060DetailLtsDAO() {
		primaryKeyFields = new String[] {orderId, typeOfSrv, datCd, srvNum};
	}

	public String getTableName() {
		return "BOMWEB_ORDER_SERVICE_0060";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getTypeOfSrv() {
		return this.typeOfSrv;
	}

	public String getDatCd() {
		return this.datCd;
	}

	public String getSrvNum() {
		return this.srvNum;
	}

	public String getIoInd() {
		return this.ioInd;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
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

	public OraDateLastUpdDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setTypeOfSrv(String pTypeOfSrv) {
		this.typeOfSrv = pTypeOfSrv;
	}

	public void setDatCd(String pDatCd) {
		this.datCd = pDatCd;
	}

	public void setSrvNum(String pSrvNum) {
		this.srvNum = pSrvNum;
	}

	public void setIoInd(String pIoInd) {
		this.ioInd = pIoInd;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

}
