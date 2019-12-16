package com.bomwebportal.lts.dto.form.apn;

import java.io.Serializable;

public class LtsApnSerialFileMainFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3931865411924204031L;
	
	private Action formAction;

	public enum Action {
		UPLOAD, ENQUIRY;
	}
	
	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}
}
