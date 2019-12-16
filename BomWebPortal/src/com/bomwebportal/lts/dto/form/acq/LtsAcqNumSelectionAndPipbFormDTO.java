package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO;

public class LtsAcqNumSelectionAndPipbFormDTO implements Serializable {

	private static final long serialVersionUID = 5550545808822080401L;
	
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
	private String selectionErrMsg;
	private String searchErrMsg;
	private String lockNumErrMsg;
	private List<LtsAcqNumberSelectionInfoDTO> numSelectionList;
	private List<String> numSelectionStringList;
	private List<LtsAcqNumberSelectionInfoDTO> reservedDnList;
	private List<String> reservedDnStringList;
	private String sessionId;	
	private boolean includeSrvNumOnExDir;
	private String directoryName;
	private String dnSource;
	private String dnStatus;
	private LtsAcqPipbInfoDTO pipbInfo;
	
	public final static String SEARCH_NO_CRITERIA_RADIO_BUTTON = "noCriteriaRadio";
	public final static String RESERVED_DN_RADIO_BUTTON = "reservedDnRadio";
	public final static String USE_NEW_DN_RADIO_BUTTON = "useNewDn";
	public final static String USE_NEW_DN_AND_PIPB_DN_RADIO_BUTTON = "useNewDnAndPipbDn";
	public final static String USE_PIPB_DN_RADIO_BUTTON = "usePipbDn";
	public final static String DISCONNECT_RADIO_BUTTON = "disconnect";
	public final static String RETAIN_RADIO_BUTTON = "retain";
	public final static String PORT_IN_TOGETHER_RADIO_BUTTON = "portInTogether";
	
	public LtsAcqNumSelectionAndPipbFormDTO() {
		this.pipbInfo = new LtsAcqPipbInfoDTO();
	}
	
	public enum Action {
		SUBMIT,
		SEARCH_BY_NO_CRITERIA,
		SEARCH_BY_FIRST_8_DIGITS,
		SEARCH_BY_LAST_3_DIGITS,
		LOCK_NUMBER, 
		RELEASE_NUMBER
	}
	
	public enum Selection {
		USE_NEW_DN,
		USE_NEW_DN_AND_PIPB_DN,
		USE_PIPB_DN
	}
	
	public enum Method {
		DN_POOL,
		RESERVED_DN,
		DN_POOL_THEN_PIPB,
		RESERVED_DN_THEN_PIPB,
		PIPB_DN
	}

	/**
	 * @return the formAction
	 */
	public Action getFormAction() {
		return formAction;
	}

	/**
	 * @param formAction the formAction to set
	 */
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}

	/**
	 * @return the numSelection
	 */
	public Selection getNumSelection() {
		return numSelection;
	}

	/**
	 * @param numSelection the numSelection to set
	 */
	public void setNumSelection(Selection numSelection) {
		this.numSelection = numSelection;
	}

	/**
	 * @return the numSelectionRadio
	 */
	public String getNumSelectionRadio() {
		return numSelectionRadio;
	}

	/**
	 * @param numSelectionRadio the numSelectionRadio to set
	 */
	public void setNumSelectionRadio(String numSelectionRadio) {
		this.numSelectionRadio = numSelectionRadio;
	}

	/**
	 * @return the searchMethodRadio
	 */
	public String getSearchMethodRadio() {
		return searchMethodRadio;
	}

	/**
	 * @param searchMethodRadio the searchMethodRadio to set
	 */
	public void setSearchMethodRadio(String searchMethodRadio) {
		this.searchMethodRadio = searchMethodRadio;
	}

	/**
	 * @return the first5To8Digits
	 */
	public String getFirst5To8Digits() {
		return first5To8Digits;
	}

	/**
	 * @param first5To8Digits the first5To8Digits to set
	 */
	public void setFirst5To8Digits(String first5To8Digits) {
		this.first5To8Digits = first5To8Digits;
	}

	/**
	 * @return the last1To3Digits
	 */
	public String getLast1To3Digits() {
		return last1To3Digits;
	}

	/**
	 * @param last1To3Digits the last1To3Digits to set
	 */
	public void setLast1To3Digits(String last1To3Digits) {
		this.last1To3Digits = last1To3Digits;
	}

	/**
	 * @return the reservedSrvNum
	 */
	public String getReservedSrvNum() {
		return reservedSrvNum;
	}

	/**
	 * @param reservedSrvNum the reservedSrvNum to set
	 */
	public void setReservedSrvNum(String reservedSrvNum) {
		this.reservedSrvNum = reservedSrvNum;
	}

	/**
	 * @return the reservedAccountCd
	 */
	public String getReservedAccountCd() {
		return reservedAccountCd;
	}

	/**
	 * @param reservedAccountCd the reservedAccountCd to set
	 */
	public void setReservedAccountCd(String reservedAccountCd) {
		this.reservedAccountCd = reservedAccountCd;
	}

	/**
	 * @return the reservedBoc
	 */
	public String getReservedBoc() {
		return reservedBoc;
	}

	/**
	 * @param reservedBoc the reservedBoc to set
	 */
	public void setReservedBoc(String reservedBoc) {
		this.reservedBoc = reservedBoc;
	}

	/**
	 * @return the reservedProjectCd
	 */
	public String getReservedProjectCd() {
		return reservedProjectCd;
	}

	/**
	 * @param reservedProjectCd the reservedProjectCd to set
	 */
	public void setReservedProjectCd(String reservedProjectCd) {
		this.reservedProjectCd = reservedProjectCd;
	}

	/**
	 * @return the selectionErrMsg
	 */
	public String getSelectionErrMsg() {
		return selectionErrMsg;
	}

	/**
	 * @param selectionErrMsg the selectionErrMsg to set
	 */
	public void setSelectionErrMsg(String selectionErrMsg) {
		this.selectionErrMsg = selectionErrMsg;
	}

	/**
	 * @return the searchErrMsg
	 */
	public String getSearchErrMsg() {
		return searchErrMsg;
	}

	/**
	 * @param searchErrMsg the searchErrMsg to set
	 */
	public void setSearchErrMsg(String searchErrMsg) {
		this.searchErrMsg = searchErrMsg;
	}

	/**
	 * @return the lockNumErrMsg
	 */
	public String getLockNumErrMsg() {
		return lockNumErrMsg;
	}

	/**
	 * @param lockNumErrMsg the lockNumErrMsg to set
	 */
	public void setLockNumErrMsg(String lockNumErrMsg) {
		this.lockNumErrMsg = lockNumErrMsg;
	}

	/**
	 * @return the numSelectionList
	 */
	public List<LtsAcqNumberSelectionInfoDTO> getNumSelectionList() {
		return numSelectionList;
	}

	/**
	 * @param numSelectionList the numSelectionList to set
	 */
	public void setNumSelectionList(
			List<LtsAcqNumberSelectionInfoDTO> numSelectionList) {
		this.numSelectionList = numSelectionList;
	}

	/**
	 * @return the numSelectionStringList
	 */
	public List<String> getNumSelectionStringList() {
		return numSelectionStringList;
	}

	/**
	 * @param numSelectionStringList the numSelectionStringList to set
	 */
	public void setNumSelectionStringList(List<String> numSelectionStringList) {
		this.numSelectionStringList = numSelectionStringList;
	}
	
	/**
	 * @return the reservedDnList
	 */
	public List<LtsAcqNumberSelectionInfoDTO> getReservedDnList() {
		return reservedDnList;
	}

	/**
	 * @param reservedDnList the reservedDnList to set
	 */
	public void setReservedDnList(List<LtsAcqNumberSelectionInfoDTO> reservedDnList) {
		this.reservedDnList = reservedDnList;
	}

	/**
	 * @return the reservedDnStringList
	 */
	public List<String> getReservedDnStringList() {
		return reservedDnStringList;
	}

	/**
	 * @param reservedDnStringList the reservedDnStringList to set
	 */
	public void setReservedDnStringList(List<String> reservedDnStringList) {
		this.reservedDnStringList = reservedDnStringList;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the includeSrvNumOnExDir
	 */
	public boolean isIncludeSrvNumOnExDir() {
		return includeSrvNumOnExDir;
	}

	/**
	 * @param includeSrvNumOnExDir the includeSrvNumOnExDir to set
	 */
	public void setIncludeSrvNumOnExDir(boolean includeSrvNumOnExDir) {
		this.includeSrvNumOnExDir = includeSrvNumOnExDir;
	}

	/**
	 * @return the directoryName
	 */
	public String getDirectoryName() {
		return directoryName;
	}

	/**
	 * @param directoryName the directoryName to set
	 */
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	/**
	 * @return the searchMethod
	 */
	public Method getSearchMethod() {
		return searchMethod;
	}

	/**
	 * @param searchMethod the searchMethod to set
	 */
	public void setSearchMethod(Method searchMethod) {
		this.searchMethod = searchMethod;
	}

	/**
	 * @return the dnSource
	 */
	public String getDnSource() {
		return dnSource;
	}

	/**
	 * @param dnSource the dnSource to set
	 */
	public void setDnSource(String dnSource) {
		this.dnSource = dnSource;
	}
	
	/**
	 * @return the dnStatus
	 */
	public String getDnStatus() {
		return dnStatus;
	}

	/**
	 * @param dnStatus the dnStatus to set
	 */
	public void setDnStatus(String dnStatus) {
		this.dnStatus = dnStatus;
	}

	/**
	 * @return the pipbInfo
	 */
	public LtsAcqPipbInfoDTO getPipbInfo() {
		return pipbInfo;
	}

	/**
	 * @param pipbInfo the pipbInfo to set
	 */
	public void setPipbInfo(LtsAcqPipbInfoDTO pipbInfo) {
		this.pipbInfo = pipbInfo;
	}
	
}
