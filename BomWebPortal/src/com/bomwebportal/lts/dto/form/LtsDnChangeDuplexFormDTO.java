package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO.DuplexSrvType;


public class LtsDnChangeDuplexFormDTO implements Serializable{


	private static final long serialVersionUID = -8778474303619767264L;

	
	private Action formAction;	
	private Selection numSelection;
	private String numSelectionRadio;
	private Method searchMethod;
	private String searchMethodRadio;
	private String first5To8Digits; 
	private String last1To3Digits; 
	private String reservedSrvNum;
	private String reservedAccountCd;
	private String reservedBoc;
	private String reservedProjectCd;
	private String searchErrMsg;
	private String chooseOneErrMsg;
	private String lockNumErrMsg;
	private String keepDNErrMsg;
	private List<LtsAcqNumberSelectionInfoDTO> numSelectionList;
	private List<String> numSelectionStringList;
	private List<LtsAcqNumberSelectionInfoDTO> reservedDnList;
	
	private String confirmedDn;
	private List<String> reservedDnStringList;
	private String sessionId;
	private String dnSource;
	
	private ItemDetailDTO chargeItem;
	private ItemDetailDTO rebateItem;
	private boolean duplexProfile;
	
	private DuplexSrvType duplexXSrvType;
	private DuplexSrvType duplexYSrvType;
	private int channelID;
	private boolean penaltyApprovalInd;
	
	
	public final static String SEARCH_NO_CRITERIA_RADIO_BUTTON = "noCriteriaRadio";
	public final static String RESERVED_DN_RADIO_BUTTON = "reservedDnRadio";
	
	public enum Action {
		SUBMIT,
		SEARCH_BY_NO_CRITERIA,
		SEARCH_BY_FIRST_8_DIGITS,
		SEARCH_BY_LAST_3_DIGITS,
		LOCK_NUMBER, 
		RELEASE_NUMBER,
		WAIVE_APPROVED
	}
	
	public enum Selection {
		USE_NEW_DN,
		KEEP_EXIST_DN
	}
	
	public enum Method {
		DN_POOL,
		RESERVED_DN
	}
	
	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}
	public Selection getNumSelection() {
		return numSelection;
	}
	public void setNumSelection(Selection numSelection) {
		this.numSelection = numSelection;
	}
	public String getNumSelectionRadio() {
		return numSelectionRadio;
	}
	public void setNumSelectionRadio(String numSelectionRadio) {
		this.numSelectionRadio = numSelectionRadio;
	}
	public Method getSearchMethod() {
		return searchMethod;
	}
	public void setSearchMethod(Method searchMethod) {
		this.searchMethod = searchMethod;
	}
	public String getSearchMethodRadio() {
		return searchMethodRadio;
	}
	public void setSearchMethodRadio(String searchMethodRadio) {
		this.searchMethodRadio = searchMethodRadio;
	}
	public String getFirst5To8Digits() {
		return first5To8Digits;
	}
	public void setFirst5To8Digits(String first5To8Digits) {
		this.first5To8Digits = first5To8Digits;
	}
	public String getLast1To3Digits() {
		return last1To3Digits;
	}
	public void setLast1To3Digits(String last1To3Digits) {
		this.last1To3Digits = last1To3Digits;
	}
	public String getReservedSrvNum() {
		return reservedSrvNum;
	}
	public void setReservedSrvNum(String reservedSrvNum) {
		this.reservedSrvNum = reservedSrvNum;
	}
	public String getReservedAccountCd() {
		return reservedAccountCd;
	}
	public void setReservedAccountCd(String reservedAccountCd) {
		this.reservedAccountCd = reservedAccountCd;
	}
	public String getReservedBoc() {
		return reservedBoc;
	}
	public void setReservedBoc(String reservedBoc) {
		this.reservedBoc = reservedBoc;
	}
	public String getReservedProjectCd() {
		return reservedProjectCd;
	}
	public void setReservedProjectCd(String reservedProjectCd) {
		this.reservedProjectCd = reservedProjectCd;
	}
	public String getSearchErrMsg() {
		return searchErrMsg;
	}
	public void setSearchErrMsg(String searchErrMsg) {
		this.searchErrMsg = searchErrMsg;
	}
	public String getChooseOneErrMsg() {
		return chooseOneErrMsg;
	}
	public void setChooseOneErrMsg(String chooseOneErrMsg) {
		this.chooseOneErrMsg = chooseOneErrMsg;
	}
	public String getLockNumErrMsg() {
		return lockNumErrMsg;
	}
	public void setLockNumErrMsg(String lockNumErrMsg) {
		this.lockNumErrMsg = lockNumErrMsg;
	}
	public List<LtsAcqNumberSelectionInfoDTO> getNumSelectionList() {
		return numSelectionList;
	}
	public void setNumSelectionList(
			List<LtsAcqNumberSelectionInfoDTO> numSelectionList) {
		this.numSelectionList = numSelectionList;
	}
	public List<String> getNumSelectionStringList() {
		return numSelectionStringList;
	}
	public void setNumSelectionStringList(List<String> numSelectionStringList) {
		this.numSelectionStringList = numSelectionStringList;
	}
	public List<LtsAcqNumberSelectionInfoDTO> getReservedDnList() {
		return reservedDnList;
	}
	public void setReservedDnList(List<LtsAcqNumberSelectionInfoDTO> reservedDnList) {
		this.reservedDnList = reservedDnList;
	}
	public ItemDetailDTO getChargeItem() {
		return chargeItem;
	}
	public void setChargeItem(ItemDetailDTO chargeItem) {
		this.chargeItem = chargeItem;
	}
	public String getConfirmedDn() {
		return confirmedDn;
	}
	public void setConfirmedDn(String confirmedDn) {
		this.confirmedDn = confirmedDn;
	}
	public List<String> getReservedDnStringList() {
		return reservedDnStringList;
	}
	public void setReservedDnStringList(List<String> reservedDnStringList) {
		this.reservedDnStringList = reservedDnStringList;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getDnSource() {
		return dnSource;
	}
	public void setDnSource(String dnSource) {
		this.dnSource = dnSource;
	}
	public boolean isDuplexProfile() {
		return duplexProfile;
	}
	public void setDuplexProfile(boolean duplexProfile) {
		this.duplexProfile = duplexProfile;
	}
	public DuplexSrvType getDuplexXSrvType() {
		return duplexXSrvType;
	}
	public void setDuplexXSrvType(DuplexSrvType duplexXSrvType) {
		this.duplexXSrvType = duplexXSrvType;
	}
	public DuplexSrvType getDuplexYSrvType() {
		return duplexYSrvType;
	}
	public void setDuplexYSrvType(DuplexSrvType duplexYSrvType) {
		this.duplexYSrvType = duplexYSrvType;
	}
	public boolean isPenaltyApprovalInd() {
		return penaltyApprovalInd;
	}
	public void setPenaltyApprovalInd(boolean penaltyApprovalInd) {
		this.penaltyApprovalInd = penaltyApprovalInd;
	}
	public int getChannelID() {
		return channelID;
	}
	public void setChannelID(int channelID) {
		this.channelID = channelID;
	}
	public ItemDetailDTO getRebateItem() {
		return rebateItem;
	}
	public void setRebateItem(ItemDetailDTO rebateItem) {
		this.rebateItem = rebateItem;
	}
	public String getKeepDNErrMsg() {
		return keepDNErrMsg;
	}
	public void setKeepDNErrMsg(String keepDNErrMsg) {
		this.keepDNErrMsg = keepDNErrMsg;
	}

}
