package com.bomwebportal.lts.dto.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class AmendCategoryLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -3035407060740866578L;
	
	private String wqStatus = null;
	private String nature = null;
	private String natureDescription = null;
	private String natureFullDescription = null;

	private List<RemarkDetailLtsDTO> remarks = null;

	private String srd = null;
	
	public String getWqStatus() {
		return wqStatus;
	}

	public void setWqStatus(String wqStatus) {
		this.wqStatus = wqStatus;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
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

	public String getNatureDescription() {
		return natureDescription;
	}

	public void setNatureDescription(String natureDescription) {
		this.natureDescription = natureDescription;
	}

	public String getNatureFullDescription() {
		return natureFullDescription;
	}

	public void setNatureFullDescription(String natureFullDescription) {
		this.natureFullDescription = natureFullDescription;
	}

	public String getSrd() {
		return srd;
	}

	public void setSrd(String srd) {
		this.srd = srd;
	}
}
