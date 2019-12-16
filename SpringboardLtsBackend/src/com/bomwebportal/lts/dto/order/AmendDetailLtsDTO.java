package com.bomwebportal.lts.dto.order;

import java.util.ArrayList;
import java.util.List;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class AmendDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -2501262807989210977L;

	private String dtlId = null;
	private String srvNum = null;
	private String srvType = null;
	private String wqBatchId = null;
	private String grpType = null;
	private String fromProd = null;
	private String toProd = null;
	private String relatedSrvNum = null;
	private String relatedSrvType = null;
	
	
	private List<AmendCategoryLtsDTO> categoryList = null;


	public String getDtlId() {
		return this.dtlId;
	}

	public void setDtlId(String pDtlId) {
		this.dtlId = pDtlId;
	}

	public String getSrvNum() {
		return this.srvNum;
	}

	public void setSrvNum(String pSrvNum) {
		this.srvNum = pSrvNum;
	}

	public String getSrvType() {
		return this.srvType;
	}

	public void setSrvType(String pSrvType) {
		this.srvType = pSrvType;
	}

	public List<AmendCategoryLtsDTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<AmendCategoryLtsDTO> categoryList) {
		this.categoryList = categoryList;
	}

	public AmendCategoryLtsDTO[] getCategories() {
		
		if (this.categoryList == null) {
			return null;
		}
		return this.categoryList.toArray(new AmendCategoryLtsDTO[this.categoryList.size()]);
	}
	
	public void appendCategory(AmendCategoryLtsDTO pCategory) {
		
		if (this.categoryList == null) {
			this.categoryList = new ArrayList<AmendCategoryLtsDTO>();
		}
		this.categoryList.add(pCategory);
	}

	public String getWqBatchId() {
		return wqBatchId;
	}

	public void setWqBatchId(String wqBatchId) {
		this.wqBatchId = wqBatchId;
	}

	public String getGrpType() {
		return grpType;
	}

	public void setGrpType(String grpType) {
		this.grpType = grpType;
	}

	public String getFromProd() {
		return fromProd;
	}

	public void setFromProd(String fromProd) {
		this.fromProd = fromProd;
	}

	public String getToProd() {
		return toProd;
	}

	public void setToProd(String toProd) {
		this.toProd = toProd;
	}

	public String getRelatedSrvNum() {
		return relatedSrvNum;
	}

	public void setRelatedSrvNum(String relatedSrvNum) {
		this.relatedSrvNum = relatedSrvNum;
	}

	public String getRelatedSrvType() {
		return relatedSrvType;
	}

	public void setRelatedSrvType(String relatedSrvType) {
		this.relatedSrvType = relatedSrvType;
	}
}
