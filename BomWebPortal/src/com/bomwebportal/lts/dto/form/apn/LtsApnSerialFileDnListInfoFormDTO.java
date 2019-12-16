package com.bomwebportal.lts.dto.form.apn;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.apn.LtsApnDnDTO;

public class LtsApnSerialFileDnListInfoFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5369155684025747615L;

	private Action formAction;
	private List<LtsApnDnDTO> dnList;
	
	public enum Action {
		RETURN;
	}
	
	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}
	public List<LtsApnDnDTO> getDnList() {
		return dnList;
	}
	public void setDnList(List<LtsApnDnDTO> dnList) {
		this.dnList = dnList;
	}

}
