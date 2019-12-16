package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class ResourceDetailOutputDTO implements Serializable {

	private static final long serialVersionUID = 8268460246805481956L;

	private String apptDate = null;
	private ResourceSlotDTO[] resourceSlots = null;
	private String returnValue = null;
	private String errorCd = null;
	private String errorMsg = null;

	
	public String getApptDate() {
		return apptDate;
	}

	public void setApptDate(String apptDate) {
		this.apptDate = apptDate;
	}

	public ResourceSlotDTO[] getResourceSlots() {
		return resourceSlots;
	}

	public void setResourceSlots(ResourceSlotDTO[] resourceSlots) {
		this.resourceSlots = resourceSlots;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getErrorCd() {
		return errorCd;
	}

	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" apptDate = ");
		sb.append(this.apptDate);
		sb.append(" returnValue = ");
		sb.append(this.returnValue);
		sb.append(" errorCd = ");
		sb.append(this.errorCd);
		sb.append(" errorMsg = ");
		sb.append(this.errorMsg);
		
		for (ResourceSlotDTO resourceSlot : this.resourceSlots) {
			if (resourceSlot != null) {
				sb.append(resourceSlot.toString());
			}
		}
		return sb.toString();
	}
}
