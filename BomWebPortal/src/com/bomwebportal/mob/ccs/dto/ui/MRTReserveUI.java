package com.bomwebportal.mob.ccs.dto.ui;

import java.io.Serializable;
import java.util.List;

public class MRTReserveUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4554197986362572190L;

	private List<MRTReserveUI> reserveMRTList;
	
	private String dReservedMrt; // for display only
	private String reserveDate;
	private String remainingTime; // expiry date - sysdate
	private String mrtPool;
	
	private String actionType;
	
	private String errorMsg;
	
	private boolean is3reserved;
	
	private String msisdnlvl;
	private String numType;

	public void setReserveMRTList(List<MRTReserveUI> reserveMRTList) {
		this.reserveMRTList = reserveMRTList;
	}

	public List<MRTReserveUI> getReserveMRTList() {
		return reserveMRTList;
	}

	public String getdReservedMrt() {
		return dReservedMrt;
	}

	public void setdReservedMrt(String dReservedMrt) {
		this.dReservedMrt = dReservedMrt;
	}

	public String getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}

	public String getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(String remainingTime) {
		this.remainingTime = remainingTime;
	}

	public String getMrtPool() {
		return mrtPool;
	}

	public void setMrtPool(String mrtPool) {
		this.mrtPool = mrtPool;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isIs3reserved() {
		return is3reserved;
	}

	public void setIs3reserved(boolean is3reserved) {
		this.is3reserved = is3reserved;
	}

	public String getMsisdnlvl() {
		return msisdnlvl;
	}

	public void setMsisdnlvl(String msisdnlvl) {
		this.msisdnlvl = msisdnlvl;
	}

	public String getNumType() {
		return numType;
	}

	public void setNumType(String numType) {
		this.numType = numType;
	}
}
