package com.bomwebportal.lts.dto.acq;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;

public class LtsAcqPipbInfoDTO implements Serializable {
	
	private static final long serialVersionUID = -6022338588191941095L;
	
	private PipbAction pipbAction;
	private String dnStatus;
	private boolean portBack;
	private String pipbSrvNn;
	private String portingFrom;	
	private String pipbSrvNum;
	private String pipbAccountCd;
	private String pipbBoc;
	private String pipbProjectCd;
	private boolean reuseExSocket;
	private String reuseSocketType;
	private boolean waiveDnChrg;
	private boolean portFromDiffCust;
	private String docType;
	private String docNum;
	private String title;
	private String familyName;
	private String givenName;
	private String companyName;
	private boolean portFromDiffAddr;
	private String portFromDiffAddrStr;
	private String quickSearch;
	private LtsAcqPipbAddressDTO address;
	private LtsAddressRolloutFormDTO rolloutAddress;
	private boolean duplexInd;
	private String duplexIndStr;
	private String duplexRadio;
	private DuplexAction duplexAction;
	private String duplexDn;
	private String duplexDnStatus;
	private String dnSource;	
	
	public LtsAcqPipbInfoDTO() {
		this.address = new LtsAcqPipbAddressDTO();
		this.rolloutAddress = new LtsAddressRolloutFormDTO();
	}
	
	public enum PipbAction {
		NEW_DN,
		PIPB_DN
	}
	
	public enum DuplexAction {
		DISCONNECT,
		RETAIN,
		PORT_IN_TOGETHER
	}

	/**
	 * @return the pipbAction
	 */
	public PipbAction getPipbAction() {
		return pipbAction;
	}

	/**
	 * @param pipbAction the pipbAction to set
	 */
	public void setPipbAction(PipbAction pipbAction) {
		this.pipbAction = pipbAction;
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
	 * @return the portBack
	 */
	public boolean isPortBack() {
		return portBack;
	}

	/**
	 * @param portBack the portBack to set
	 */
	public void setPortBack(boolean portBack) {
		this.portBack = portBack;
	}

	/**
	 * @return the pipbSrvNn
	 */
	public String getPipbSrvNn() {
		return pipbSrvNn;
	}

	/**
	 * @param pipbSrvNn the pipbSrvNn to set
	 */
	public void setPipbSrvNn(String pipbSrvNn) {
		this.pipbSrvNn = pipbSrvNn;
	}

	/**
	 * @return the portingFrom
	 */
	public String getPortingFrom() {
		return portingFrom;
	}

	/**
	 * @param portingFrom the portingFrom to set
	 */
	public void setPortingFrom(String portingFrom) {
		this.portingFrom = portingFrom;
	}

	/**
	 * @return the pipbSrvNum
	 */
	public String getPipbSrvNum() {
		return pipbSrvNum;
	}

	/**
	 * @param pipbSrvNum the pipbSrvNum to set
	 */
	public void setPipbSrvNum(String pipbSrvNum) {
		this.pipbSrvNum = pipbSrvNum;
	}

	/**
	 * @return the pipbAccountCd
	 */
	public String getPipbAccountCd() {
		return pipbAccountCd;
	}

	/**
	 * @param pipbAccountCd the pipbAccountCd to set
	 */
	public void setPipbAccountCd(String pipbAccountCd) {
		this.pipbAccountCd = pipbAccountCd;
	}

	/**
	 * @return the pipbBoc
	 */
	public String getPipbBoc() {
		return pipbBoc;
	}

	/**
	 * @param pipbBoc the pipbBoc to set
	 */
	public void setPipbBoc(String pipbBoc) {
		this.pipbBoc = pipbBoc;
	}

	/**
	 * @return the pipbProjectCd
	 */
	public String getPipbProjectCd() {
		return pipbProjectCd;
	}

	/**
	 * @param pipbProjectCd the pipbProjectCd to set
	 */
	public void setPipbProjectCd(String pipbProjectCd) {
		this.pipbProjectCd = pipbProjectCd;
	}

	/**
	 * @return the reuseExSocket
	 */
	public boolean isReuseExSocket() {
		return reuseExSocket;
	}

	/**
	 * @param reuseExSocket the reuseExSocket to set
	 */
	public void setReuseExSocket(boolean reuseExSocket) {
		this.reuseExSocket = reuseExSocket;
	}
	
	/**
	 * @return the reuseSocketType
	 */
	public String getReuseSocketType() {
		return reuseSocketType;
	}

	/**
	 * @param reuseSocketType the reuseSocketType to set
	 */
	public void setReuseSocketType(String reuseSocketType) {
		this.reuseSocketType = reuseSocketType;
	}

	/**
	 * @return the waiveDnChrg
	 */
	public boolean isWaiveDnChrg() {
		return waiveDnChrg;
	}

	/**
	 * @param waiveDnChrg the waiveDnChrg to set
	 */
	public void setWaiveDnChrg(boolean waiveDnChrg) {
		this.waiveDnChrg = waiveDnChrg;
	}

	/**
	 * @return the portFromDiffCust
	 */
	public boolean isPortFromDiffCust() {
		return portFromDiffCust;
	}

	/**
	 * @param portFromDiffCust the portFromDiffCust to set
	 */
	public void setPortFromDiffCust(boolean portFromDiffCust) {
		this.portFromDiffCust = portFromDiffCust;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @return the docNum
	 */
	public String getDocNum() {
		return docNum;
	}

	/**
	 * @param docNum the docNum to set
	 */
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the familyName
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the portFromDiffAddr
	 */
	public boolean isPortFromDiffAddr() {
		return portFromDiffAddr;
	}

	/**
	 * @param portFromDiffAddr the portFromDiffAddr to set
	 */
	public void setPortFromDiffAddr(boolean portFromDiffAddr) {
		this.portFromDiffAddr = portFromDiffAddr;
		this.portFromDiffAddrStr = portFromDiffAddr? "Y" : "N";
	}	
	
	/**
	 * @return the portFromDiffAddrStr
	 */
	public String getPortFromDiffAddrStr() {
		return portFromDiffAddrStr;
	}

	/**
	 * @param portFromDiffAddrStr the portFromDiffAddrStr to set
	 */
	public void setPortFromDiffAddrStr(String portFromDiffAddrStr) {
		this.portFromDiffAddrStr = portFromDiffAddrStr;
		this.portFromDiffAddr = StringUtils.equals("Y", portFromDiffAddrStr)? true : false;
	}

	/**
	 * @return the quickSearch
	 */
	public String getQuickSearch() {
		return quickSearch;
	}

	/**
	 * @param quickSearch the quickSearch to set
	 */
	public void setQuickSearch(String quickSearch) {
		this.quickSearch = quickSearch;
	}

	/**
	 * @return the address
	 */
	public LtsAcqPipbAddressDTO getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(LtsAcqPipbAddressDTO address) {
		this.address = address;
	}	
	
	/**
	 * @return the rolloutAddress
	 */
	public LtsAddressRolloutFormDTO getRolloutAddress() {
		return rolloutAddress;
	}

	/**
	 * @param rolloutAddress the rolloutAddress to set
	 */
	public void setRolloutAddress(LtsAddressRolloutFormDTO rolloutAddress) {
		this.rolloutAddress = rolloutAddress;
	}

	/**
	 * @return the duplexInd
	 */
	public boolean isDuplexInd() {
		return duplexInd;
	}

	/**
	 * @param duplexInd the duplexInd to set
	 */
	public void setDuplexInd(boolean duplexInd) {
		this.duplexInd = duplexInd;
		this.duplexIndStr = duplexInd? "Y" : "N";
	}
	
	/**
	 * @return the duplexIndStr
	 */
	public String getDuplexIndStr() {
		return duplexIndStr;
	}

	/**
	 * @param duplexIndStr the duplexIndStr to set
	 */
	public void setDuplexIndStr(String duplexIndStr) {
		this.duplexIndStr = duplexIndStr;
		this.duplexInd = StringUtils.equals("Y", duplexIndStr)? true : false;
	}

	/**
	 * @return the duplexRadio
	 */
	public String getDuplexRadio() {
		return duplexRadio;
	}

	/**
	 * @param duplexRadio the duplexRadio to set
	 */
	public void setDuplexRadio(String duplexRadio) {
		this.duplexRadio = duplexRadio;
	}

	/**
	 * @return the duplexAction
	 */
	public DuplexAction getDuplexAction() {
		return duplexAction;
	}

	/**
	 * @param duplexAction the duplexAction to set
	 */
	public void setDuplexAction(DuplexAction duplexAction) {
		this.duplexAction = duplexAction;
	}

	/**
	 * @return the duplexDn
	 */
	public String getDuplexDn() {
		return duplexDn;
	}

	/**
	 * @param duplexDn the duplexDn to set
	 */
	public void setDuplexDn(String duplexDn) {
		this.duplexDn = duplexDn;
	}	
	
	/**
	 * @return the duplexDnStatus
	 */
	public String getDuplexDnStatus() {
		return duplexDnStatus;
	}

	/**
	 * @param duplexDnStatus the duplexDnStatus to set
	 */
	public void setDuplexDnStatus(String duplexDnStatus) {
		this.duplexDnStatus = duplexDnStatus;
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

}
