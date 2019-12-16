package com.bomwebportal.dto;

import java.util.List;




public class SummaryDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3526338861015734008L;
	
	private String orderId;
	
	private String summaryError;
	
	private MobileSimInfoDTO mobileSimInfoDTO;
	
	private String msisdn;
	
	private MnpDTO mnpDTO;
	
	// warn same mrt used by existing order
	private String warnSameMRT;
	private boolean ignoreSameMRTcheckbox; 
	
	private List<MultiSimInfoDTO> multiSimInfoList;
	private List<ClubPointDetailDTO> clubPointDetailDTO;
	
	private boolean ignoreQuotaCheck = false;
	private boolean ignoreHSRMCheck = false;
	private boolean ignoreHSRMComplete = false;
	
	public MnpDTO getMnpDTO() {
		return mnpDTO;
	}

	public void setMnpDTO(MnpDTO mnpDTO) {
		this.mnpDTO = mnpDTO;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public MobileSimInfoDTO getMobileSimInfoDTO() {
		return mobileSimInfoDTO;
	}

	public void setMobileSimInfoDTO(MobileSimInfoDTO mobileSimInfoDTO) {
		this.mobileSimInfoDTO = mobileSimInfoDTO;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public void setSummaryError(String summaryError) {
		this.summaryError = summaryError;
	}

	public String getSummaryError() {
		return summaryError;
	}
	
	public void setWarnSameMRT(String warnSameMRT) {
		this.warnSameMRT = warnSameMRT;
	}

	public String getWarnSameMRT() {
		return warnSameMRT;
	}

	public void setIgnoreSameMRTcheckbox(boolean ignoreSameMRTcheckbox) {
		this.ignoreSameMRTcheckbox = ignoreSameMRTcheckbox;
	}

	public boolean isIgnoreSameMRTcheckbox() {
		return ignoreSameMRTcheckbox;
	}

	public List<MultiSimInfoDTO> getMultiSimInfoList() {
		return multiSimInfoList;
	}

	public void setMultiSimInfoList(List<MultiSimInfoDTO> multiSimInfoList) {
		this.multiSimInfoList = multiSimInfoList;
	}

	public boolean isIgnoreQuotaCheck() {
		return ignoreQuotaCheck;
	}

	public void setIgnoreQuotaCheck(boolean ignoreQuotaCheck) {
		this.ignoreQuotaCheck = ignoreQuotaCheck;
	}

	public boolean isIgnoreHSRMCheck() {
		return ignoreHSRMCheck;
	}

	public void setIgnoreHSRMCheck(boolean ignoreHSRMCheck) {
		this.ignoreHSRMCheck = ignoreHSRMCheck;
	}

	public boolean isIgnoreHSRMComplete() {
		return ignoreHSRMComplete;
	}

	public void setIgnoreHSRMComplete(boolean ignoreHSRMComplete) {
		this.ignoreHSRMComplete = ignoreHSRMComplete;
	}

	public List<ClubPointDetailDTO> getClubPointDetailDTO() {
		return clubPointDetailDTO;
	}

	public void setClubPointDetailDTO(List<ClubPointDetailDTO> clubPointDetailDTO) {
		this.clubPointDetailDTO = clubPointDetailDTO;
	}
	
}
