package com.pccw.rpt.schema.dto.slv.salesAgreement;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.schema.dto.slv.salesAgreement.SectionCRptDTO.ChargingItem;
import com.pccw.rpt.schema.dto.slv.salesAgreement.SectionERptDTO.FixedCharge;
import com.pccw.rpt.util.ReportUtil;

public class SectionInternalUseDTO extends SectionRptDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -396345725799807479L;

	private String orderTakedBy;
	
	private String channel;
	
	private String salesmanCd;
	
	private String salesStaffName;
	
	private String cmrid;
	
	private String contactNum;
	
	private String teamShopCd;
	
	private String orderRemarks;

	public String getOrderTakedBy() {
		return this.orderTakedBy;
	}

	public void setOrderTakedBy(String pOrderTakedBy) {
		this.orderTakedBy = pOrderTakedBy;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String pChannel) {
		this.channel = pChannel;
	}

	public String getSalesmanCd() {
		return this.salesmanCd;
	}

	public void setSalesmanCd(String pSalesmanCd) {
		this.salesmanCd = pSalesmanCd;
	}

	public String getSalesStaffName() {
		return this.salesStaffName;
	}

	public void setSalesStaffName(String pSalesStaffName) {
		this.salesStaffName = pSalesStaffName;
	}

	public String getCmrid() {
		return this.cmrid;
	}

	public void setCmrid(String pCmrid) {
		this.cmrid = pCmrid;
	}

	public String getContactNum() {
		return this.contactNum;
	}

	public void setContactNum(String pContactNum) {
		this.contactNum = pContactNum;
	}

	public String getTeamShopCd() {
		return this.teamShopCd;
	}

	public void setTeamShopCd(String pTeamShopCd) {
		this.teamShopCd = pTeamShopCd;
	}

	public String getOrderRemarks() {
		return this.orderRemarks;
	}

	public void setOrderRemarks(String pOrderRemarks) {
		this.orderRemarks = pOrderRemarks;
	}
	
}
