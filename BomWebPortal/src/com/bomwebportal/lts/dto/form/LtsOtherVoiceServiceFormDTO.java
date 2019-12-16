package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;

public class LtsOtherVoiceServiceFormDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6671710431084395261L;
	
	private Action formAction;
	
	private Boolean create2ndDel;
	private Boolean secondDelThirdPartyAppl;
	private Boolean secondDelDummyDoc;
	
	private String secondDelDocType;
	private String secondDelDocNum;
	
	private DuplexSrvType duplexXSrvType;
	private DuplexSrvType duplexYSrvType;
	
	private String new2ndDelDn;
	private String new2ndDelSrvStatus;
	
	// For 2nd DEL Term Plan
	private List<ItemDetailDTO> secondDelHotVasItemList;
	private List<ItemDetailDTO> secondDelOtherVasItemList;
	private List<ItemDetailDTO> secondDelStandaloneVasItemList;
	
	private Boolean secondDelEr;
	private Boolean secondDelBaCaChange;
	
	//handle dn change when creating new 2nd DEL
	
	private boolean dnChange;
	private String dnChangeNumX;
	private String dnChangeNumY;
	
	private String duplexDnChange;
	
	private boolean duplexProfile;
	private boolean secondDelDiffCust;
	private boolean secondDelDiffAddress;
	private boolean allowSecondDelConfirmSameAddress;
	private boolean secondDelProfileValid;
	private Boolean secondDelConfirmSameIa;
	private String secondDelMsg;
	
	private Boolean secondDelRedeemPremium;
	private boolean showSecondDelRedeemPremium;
	private boolean secondDelOptOutIdd;
	
	private List<ItemDetailDTO> duplex2ndDelAutoInVasList;
	private List<ItemDetailDTO> new2ndDelAutoOutTpList;
	private List<ItemDetailDTO> new2ndDelAutoChangeTpList;
	private List<ItemDetailDTO> new2ndDelExistingTpList;
	private List<ItemDetailDTO> secondDelIddItemList;
	private List<ItemDetailDTO> secondDelOptOutIddItemList;
	
	private String secondDelWqRemark;
	private List<ItemDetailProfileLtsDTO> secondDelCancelVasItemList;
	
	private boolean removeRenewalDuplex;
	private boolean retainRenewalDuplex;
	
	private List<LtsAcqNumberSelectionInfoDTO> numSelectionList;
	private List<String> numSelectionStringList;
	private String first5To8Digits; 
	private String last1To3Digits;
	private Method searchMethod;
	private String searchMethodRadio;
	private String searchErrMsg;
	private String lockNumErrMsg;
	
	private boolean isDnPoolSelectionMethod;
	private boolean onlyAllowWorking;
	
	public enum Action {
		SUBMIT, SEARCH_DN, CLEAR_DN, CUST_VERIFY,
		SEARCH_BY_NO_CRITERIA, SEARCH_BY_FIRST_8_DIGITS,
		SEARCH_BY_LAST_3_DIGITS, LOCK_NUMBER
	}
	
	public enum Method {
		DN_POOL,
		RESERVED_DN
	}
	
	public enum DuplexSrvType {
		UPGRADE, NEW_2ND_DEL, CANCEL, RETAIN
	}

	public Action getFormAction() {
		return formAction;
	}

	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}

	public Boolean getCreate2ndDel() {
		return create2ndDel;
	}

	public void setCreate2ndDel(Boolean create2ndDel) {
		this.create2ndDel = create2ndDel;
	}

	public Boolean getSecondDelThirdPartyAppl() {
		return secondDelThirdPartyAppl;
	}

	public void setSecondDelThirdPartyAppl(Boolean secondDelThirdPartyAppl) {
		this.secondDelThirdPartyAppl = secondDelThirdPartyAppl;
	}

	public Boolean getSecondDelDummyDoc() {
		return secondDelDummyDoc;
	}

	public void setSecondDelDummyDoc(Boolean secondDelDummyDoc) {
		this.secondDelDummyDoc = secondDelDummyDoc;
	}

	public String getSecondDelDocType() {
		return secondDelDocType;
	}

	public void setSecondDelDocType(String secondDelDocType) {
		this.secondDelDocType = secondDelDocType;
	}

	public String getSecondDelDocNum() {
		return secondDelDocNum;
	}

	public void setSecondDelDocNum(String secondDelDocNum) {
		this.secondDelDocNum = secondDelDocNum;
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

	public String getNew2ndDelDn() {
		return new2ndDelDn;
	}

	public void setNew2ndDelDn(String new2ndDelDn) {
		this.new2ndDelDn = new2ndDelDn;
	}

	public String getNew2ndDelSrvStatus() {
		return new2ndDelSrvStatus;
	}

	public void setNew2ndDelSrvStatus(String new2ndDelSrvStatus) {
		this.new2ndDelSrvStatus = new2ndDelSrvStatus;
	}

	public List<ItemDetailDTO> getSecondDelHotVasItemList() {
		return secondDelHotVasItemList;
	}

	public void setSecondDelHotVasItemList(
			List<ItemDetailDTO> secondDelHotVasItemList) {
		this.secondDelHotVasItemList = secondDelHotVasItemList;
	}

	public List<ItemDetailDTO> getSecondDelOtherVasItemList() {
		return secondDelOtherVasItemList;
	}

	public void setSecondDelOtherVasItemList(
			List<ItemDetailDTO> secondDelOtherVasItemList) {
		this.secondDelOtherVasItemList = secondDelOtherVasItemList;
	}

	public Boolean getSecondDelEr() {
		return secondDelEr;
	}

	public void setSecondDelEr(Boolean secondDelEr) {
		this.secondDelEr = secondDelEr;
	}

	public Boolean getSecondDelBaCaChange() {
		return secondDelBaCaChange;
	}

	public void setSecondDelBaCaChange(Boolean secondDelBaCaChange) {
		this.secondDelBaCaChange = secondDelBaCaChange;
	}

	public boolean isDnChange() {
		return dnChange;
	}

	public void setDnChange(boolean dnChange) {
		this.dnChange = dnChange;
	}

	public String getDnChangeNumX() {
		return dnChangeNumX;
	}

	public void setDnChangeNumX(String dnChangeNumX) {
		this.dnChangeNumX = dnChangeNumX;
	}

	public String getDnChangeNumY() {
		return dnChangeNumY;
	}

	public void setDnChangeNumY(String dnChangeNumY) {
		this.dnChangeNumY = dnChangeNumY;
	}

	public String getDuplexDnChange() {
		return duplexDnChange;
	}

	public void setDuplexDnChange(String duplexDnChange) {
		this.duplexDnChange = duplexDnChange;
	}

	public boolean isDuplexProfile() {
		return duplexProfile;
	}

	public void setDuplexProfile(boolean duplexProfile) {
		this.duplexProfile = duplexProfile;
	}

	public boolean isSecondDelDiffCust() {
		return secondDelDiffCust;
	}

	public void setSecondDelDiffCust(boolean secondDelDiffCust) {
		this.secondDelDiffCust = secondDelDiffCust;
	}

	public boolean isSecondDelDiffAddress() {
		return secondDelDiffAddress;
	}

	public void setSecondDelDiffAddress(boolean secondDelDiffAddress) {
		this.secondDelDiffAddress = secondDelDiffAddress;
	}

	public boolean isSecondDelProfileValid() {
		return secondDelProfileValid;
	}

	public void setSecondDelProfileValid(boolean secondDelProfileValid) {
		this.secondDelProfileValid = secondDelProfileValid;
	}

	public String getSecondDelMsg() {
		return secondDelMsg;
	}

	public void setSecondDelMsg(String secondDelMsg) {
		this.secondDelMsg = secondDelMsg;
	}

	public Boolean getSecondDelConfirmSameIa() {
		return secondDelConfirmSameIa;
	}

	public void setSecondDelConfirmSameIa(Boolean secondDelConfirmSameIa) {
		this.secondDelConfirmSameIa = secondDelConfirmSameIa;
	}

	public boolean isAllowSecondDelConfirmSameAddress() {
		return allowSecondDelConfirmSameAddress;
	}

	public void setAllowSecondDelConfirmSameAddress(
			boolean allowSecondDelConfirmSameAddress) {
		this.allowSecondDelConfirmSameAddress = allowSecondDelConfirmSameAddress;
	}

	public List<ItemDetailDTO> getDuplex2ndDelAutoInVasList() {
		return duplex2ndDelAutoInVasList;
	}

	public void setDuplex2ndDelAutoInVasList(
			List<ItemDetailDTO> duplex2ndDelAutoInVasList) {
		this.duplex2ndDelAutoInVasList = duplex2ndDelAutoInVasList;
	}

	public List<ItemDetailDTO> getNew2ndDelAutoOutTpList() {
		return new2ndDelAutoOutTpList;
	}

	public void setNew2ndDelAutoOutTpList(
			List<ItemDetailDTO> new2ndDelAutoOutTpList) {
		this.new2ndDelAutoOutTpList = new2ndDelAutoOutTpList;
	}

	public List<ItemDetailDTO> getNew2ndDelAutoChangeTpList() {
		return new2ndDelAutoChangeTpList;
	}

	public void setNew2ndDelAutoChangeTpList(
			List<ItemDetailDTO> new2ndDelAutoChangeTpList) {
		this.new2ndDelAutoChangeTpList = new2ndDelAutoChangeTpList;
	}

	public List<ItemDetailDTO> getNew2ndDelExistingTpList() {
		return new2ndDelExistingTpList;
	}

	public void setNew2ndDelExistingTpList(
			List<ItemDetailDTO> new2ndDelExistingTpList) {
		this.new2ndDelExistingTpList = new2ndDelExistingTpList;
	}

	public Boolean getSecondDelRedeemPremium() {
		return secondDelRedeemPremium;
	}

	public void setSecondDelRedeemPremium(Boolean secondDelRedeemPremium) {
		this.secondDelRedeemPremium = secondDelRedeemPremium;
	}

	public boolean isShowSecondDelRedeemPremium() {
		return showSecondDelRedeemPremium;
	}

	public void setShowSecondDelRedeemPremium(boolean showSecondDelRedeemPremium) {
		this.showSecondDelRedeemPremium = showSecondDelRedeemPremium;
	}

	public List<ItemDetailDTO> getSecondDelIddItemList() {
		return secondDelIddItemList;
	}

	public void setSecondDelIddItemList(List<ItemDetailDTO> secondDelIddItemList) {
		this.secondDelIddItemList = secondDelIddItemList;
	}

	public boolean isSecondDelOptOutIdd() {
		return secondDelOptOutIdd;
	}

	public void setSecondDelOptOutIdd(boolean secondDelOptOutIdd) {
		this.secondDelOptOutIdd = secondDelOptOutIdd;
	}

	public List<ItemDetailDTO> getSecondDelOptOutIddItemList() {
		return secondDelOptOutIddItemList;
	}

	public void setSecondDelOptOutIddItemList(
			List<ItemDetailDTO> secondDelOptOutIddItemList) {
		this.secondDelOptOutIddItemList = secondDelOptOutIddItemList;
	}

	public List<ItemDetailDTO> getSecondDelStandaloneVasItemList() {
		return secondDelStandaloneVasItemList;
	}

	public void setSecondDelStandaloneVasItemList(
			List<ItemDetailDTO> secondDelStandaloneVasItemList) {
		this.secondDelStandaloneVasItemList = secondDelStandaloneVasItemList;
	}

	public String getSecondDelWqRemark() {
		return secondDelWqRemark;
	}

	public void setSecondDelWqRemark(String secondDelWqRemark) {
		this.secondDelWqRemark = secondDelWqRemark;
	}

	public List<ItemDetailProfileLtsDTO> getSecondDelCancelVasItemList() {
		return secondDelCancelVasItemList;
	}

	public void setSecondDelCancelVasItemList(
			List<ItemDetailProfileLtsDTO> secondDelCancelVasItemList) {
		this.secondDelCancelVasItemList = secondDelCancelVasItemList;
	}

	public boolean isRemoveRenewalDuplex() {
		return removeRenewalDuplex;
	}

	public void setRemoveRenewalDuplex(boolean removeRenewalDuplex) {
		this.removeRenewalDuplex = removeRenewalDuplex;
	}

	public boolean isRetainRenewalDuplex() {
		return retainRenewalDuplex;
	}

	public void setRetainRenewalDuplex(boolean retainRenewalDuplex) {
		this.retainRenewalDuplex = retainRenewalDuplex;
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

	public String getSearchErrMsg() {
		return searchErrMsg;
	}

	public void setSearchErrMsg(String searchErrMsg) {
		this.searchErrMsg = searchErrMsg;
	}

	public String getLockNumErrMsg() {
		return lockNumErrMsg;
	}

	public void setLockNumErrMsg(String lockNumErrMsg) {
		this.lockNumErrMsg = lockNumErrMsg;
	}

	public boolean isDnPoolSelectionMethod() {
		return isDnPoolSelectionMethod;
	}

	public void setDnPoolSelectionMethod(boolean isDnPoolSelectionMethod) {
		this.isDnPoolSelectionMethod = isDnPoolSelectionMethod;
	}

	public boolean isOnlyAllowWorking() {
		return onlyAllowWorking;
	}

	public void setOnlyAllowWorking(boolean onlyAllowWorking) {
		this.onlyAllowWorking = onlyAllowWorking;
	}
	
	
	
}
