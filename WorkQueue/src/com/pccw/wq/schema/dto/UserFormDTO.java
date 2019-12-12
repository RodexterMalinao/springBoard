package com.pccw.wq.schema.dto;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

public class UserFormDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1327569242227626029L;

	private String loginId;
	
	private WorkingPartyDTO[] workingParties;
	
	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String pLoginId) {
		this.loginId = pLoginId;
	}

	public WorkingPartyDTO[] getWorkingParties() {
		return this.workingParties;
	}

	public void setWorkingParties(WorkingPartyDTO[] pWorkingParties) {
		this.workingParties = pWorkingParties;
	}
	
	public String[] getWpIds() {
		if (ArrayUtils.isEmpty(this.getWorkingParties())) {
			return null;
		}
		ArrayList<String> rtnList = new ArrayList<String>();
		for (WorkingPartyDTO wpDTO : this.getWorkingParties()) {
			rtnList.add(wpDTO.getWpId());
		}
		return rtnList.toArray(new String[0]);
	}
}