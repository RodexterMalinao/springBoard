package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ServiceGroupProfileDTO implements Serializable {

	private static final long serialVersionUID = 5418744796922105036L;
	
	private String grpType = null;
	private String grpId = null;
	
	private List<ServiceGroupMemberProfileDTO> grpMemList = null;

	
	public String getGrpType() {
		return grpType;
	}

	public void setGrpType(String grpType) {
		this.grpType = grpType;
	}

	public String getGrpId() {
		return grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	public ServiceGroupMemberProfileDTO[] getGrpMems() {
		return this.grpMemList==null ? null : this.grpMemList.toArray(new ServiceGroupMemberProfileDTO[this.grpMemList.size()]);
	}

	public void setGrpMems(ServiceGroupMemberProfileDTO[] grpMems) {
		this.grpMemList = new ArrayList<ServiceGroupMemberProfileDTO>(Arrays.asList(grpMems));
	}
	
	public void appendGrpMemeber(ServiceGroupMemberProfileDTO pGrpMem) {
		
		if (this.grpMemList == null) {
			this.grpMemList = new ArrayList<ServiceGroupMemberProfileDTO>();
		}
		this.grpMemList.add(pGrpMem);
	}
	
	public ServiceGroupMemberProfileDTO getMemberByType(String pType) {
		
		if (this.grpMemList == null) {
			return null;
		}
		for (int i=0; i<this.grpMemList.size(); ++i) {
			if (StringUtils.equalsIgnoreCase(this.grpMemList.get(i).getDatCd(), pType)) {
				return this.grpMemList.get(i);
			}
		}
		return null;
	}
}
