package com.bomwebportal.lts.dto.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class AmendLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -8887465981485668162L;

	private String orderId = null;
	private String amendSeq = null;
	private String staffNum = null;
	private String salesChannel = null;
	private String salesContactNum = null;
	private String salesCd = null;
	private String shopCd = null;
	private String name = null;
	private String contactNum = null;
	private String relationshipCd = null;
	private String ocid = null;
	private HashSet<String> amendTypeSet = null;
	private String amendSrdDisplay = null;
	private boolean pipbOrder;
	private List<AmendDetailLtsDTO> amendDtlList = null;
	private List<RemarkDetailLtsDTO> remarks = null;
	
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public String getAmendSeq() {
		return this.amendSeq;
	}

	public void setAmendSeq(String pAmendSeq) {
		this.amendSeq = pAmendSeq;
	}

	public String getStaffNum() {
		return this.staffNum;
	}

	public void setStaffNum(String pStaffNum) {
		this.staffNum = pStaffNum;
	}

	public String getSalesChannel() {
		return this.salesChannel;
	}

	public void setSalesChannel(String pSalesChannel) {
		this.salesChannel = pSalesChannel;
	}

	public String getSalesContactNum() {
		return this.salesContactNum;
	}

	public void setSalesContactNum(String pSalesContactNum) {
		this.salesContactNum = pSalesContactNum;
	}

	public String getSalesCd() {
		return this.salesCd;
	}

	public void setSalesCd(String pSalesCd) {
		this.salesCd = pSalesCd;
	}

	public String getShopCd() {
		return this.shopCd;
	}

	public void setShopCd(String pShopCd) {
		this.shopCd = pShopCd;
	}

	public List<AmendDetailLtsDTO> getAmendDtlList() {
		return amendDtlList;
	}

	public void setAmendDtlList(List<AmendDetailLtsDTO> amendDtlList) {
		this.amendDtlList = amendDtlList;
	}

	public AmendDetailLtsDTO[] getAmendDtls() {
		
		if (this.amendDtlList == null) { 
			return null;
		}
		return this.amendDtlList.toArray(new AmendDetailLtsDTO[this.amendDtlList.size()]);
	}
	
	public void appendAmendDetail(AmendDetailLtsDTO pAmendDtl) {
		
		if (this.amendDtlList == null) { 
			this.amendDtlList = new ArrayList<AmendDetailLtsDTO>();
		}
		this.amendDtlList.add(pAmendDtl);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public String getContactNum() {
		return this.contactNum;
	}

	public void setContactNum(String pContactNum) {
		this.contactNum = pContactNum;
	}

	public String getRelationshipCd() {
		return this.relationshipCd;
	}

	public void setRelationshipCd(String pRelationshipCd) {
		this.relationshipCd = pRelationshipCd;
	}

	public String getOcid() {
		return ocid;
	}

	public void setOcid(String ocid) {
		this.ocid = ocid;
	}

	public HashSet<String> getAmendTypeSet() {
		return amendTypeSet;
	}

	public void setAmendTypeSet(HashSet<String> amendTypeSet) {
		this.amendTypeSet = amendTypeSet;
	}

	public String getAmendSrdDisplay() {
		return amendSrdDisplay;
	}

	public void setAmendSrdDisplay(String amendSrdDisplay) {
		this.amendSrdDisplay = amendSrdDisplay;
	}
	public RemarkDetailLtsDTO[] getRemarks() {
		if (remarks == null) {
			return null;
		}
		return remarks.toArray(new RemarkDetailLtsDTO[remarks.size()]);
	}

	public void setRemarks(List<RemarkDetailLtsDTO> remarks) {
		this.remarks = remarks;
	}

	public void appendRemarks(RemarkDetailLtsDTO[] pRemarks) {
		
		if (this.remarks == null) {
			this.remarks = new ArrayList<RemarkDetailLtsDTO>();
		}
		this.remarks.addAll(0, Arrays.asList(pRemarks));
	}
	
	public void appendRemark(RemarkDetailLtsDTO pRemark) {
		
		if (this.remarks == null) {
			this.remarks = new ArrayList<RemarkDetailLtsDTO>();
		}
		this.remarks.add(pRemark);
	}
	
	public String remarkToString() {
		
		if (this.remarks == null) {
			return "";
		}
		StringBuilder remarkSb = new StringBuilder();
		
		for (int i=0; i<this.remarks.size(); ++i) {
			remarkSb.append(this.remarks.get(i).getRmkDtl());
		}
		return remarkSb.toString();
	}
	
	public String[] remarkToStringArray() {
		
		if (this.remarks == null) {
			return null;
		}
		String[] remarkArr = new String[this.remarks.size()];
		
		for (int i=0; i<this.remarks.size(); ++i) {
			remarkArr[i] = this.remarks.get(i).getRmkDtl();
		}
		return remarkArr;
	}

	public boolean isPipbOrder() {
		return pipbOrder;
	}

	public void setPipbOrder(boolean pipbOrder) {
		this.pipbOrder = pipbOrder;
	}


}
