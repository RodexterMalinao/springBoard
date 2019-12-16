package com.bomwebportal.ims.dto.ui;

import java.util.List;

import com.bomwebportal.ims.dto.HousTypeOTChrgDTO;

public class AddressInfoUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5248514987697312232L;

	private String commonErrorArea;
	private String ActionInd;
	
	private CustomerUI customer;
	private InstallAddrUI installAddress;
	private String hosType;
	private String ponSrd;
	private String adslSrd;
	private String vdslSrd;
	private String vectorSrd;
	private String dobStr;
	private String blacklistCustInfo;
	private String prevSbNo;
	private String phInd;
	private String approvalRequested;
	private String prevFlat;
	private String prevFloor;
	
	private List<AddressInfoUI> relatedSbList;
	private String sbSelected;
	private String cslOfferEnableInd;
	private String mobileOfferInd;
	private String isPon4Village;
	private String pon4VillageSubmitted;
	
	public String getPrevFlat() {
		return prevFlat;
	}
	public void setPrevFlat(String prevFlat) {
		this.prevFlat = prevFlat;
	}
	public String getPrevFloor() {
		return prevFloor;
	}
	public void setPrevFloor(String prevFloor) {
		this.prevFloor = prevFloor;
	}
	//kinman
	private String ponInstFee;
	private String adslInstFee;
	private String vdslInstFee;
	private String vectorInstFee;
	private List<HousTypeOTChrgDTO> HousTypeOTChrgList;
	private int ErrorCode;
	private String ErrorText;
	private String oneTimeFee;
	private String oTFeeProdType;
	private String OtChrgType;
	private String ponOTChrgType;
	private String adslOTChrgType;
	private String vdslOTChrgType;
	private String vectorOTChrgType;
	
	private String cpqHosType;
	
	public int getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(int errorCode) {
		ErrorCode = errorCode;
	}
	public String getErrorText() {
		return ErrorText;
	}
	public void setErrorText(String errorText) {
		ErrorText = errorText;
	}
	
	public List<HousTypeOTChrgDTO> getHousTypeOTChrgList() {
		return HousTypeOTChrgList;
	}
	public void setHousTypeOTChrgList(
			List<HousTypeOTChrgDTO> housTypeOTChrgList) {
		HousTypeOTChrgList = housTypeOTChrgList;
	}
		
	public String getPonInstFee() {
		return ponInstFee;
	}

	public void setPonInstFee(String ponInstFee) {
		this.ponInstFee = ponInstFee;
	}

	public String getAdslInstFee() {
		return adslInstFee;
	}

	public void setAdslInstFee(String adslInstFee) {
		this.adslInstFee = adslInstFee;
	}

	public String getVdslInstFee() {
		return vdslInstFee;
	}

	public void setVdslInstFee(String vdslInstFee) {
		this.vdslInstFee = vdslInstFee;
	}
	
	public String getOneTimeFee() {
		return oneTimeFee;
	}
	public void setOneTimeFee(String oneTimeFee) {
		this.oneTimeFee = oneTimeFee;
	}

	public String getoTFeeProdType() {
		return oTFeeProdType;
	}
	public void setoTFeeProdType(String oTFeeProdType) {
		this.oTFeeProdType = oTFeeProdType;
	}
	
	
	
	//
	public String getBlacklistCustInfo() {
		return blacklistCustInfo;
	}

	public void setBlacklistCustInfo(String blacklistCustInfo) {
		this.blacklistCustInfo = blacklistCustInfo;
	}

	public String getDobStr() {
		return dobStr;
	}

	public void setDobStr(String dobStr) {
		this.dobStr = dobStr;
	}

	public String getPonSrd() {
		return ponSrd;
	}

	public void setPonSrd(String ponSrd) {
		this.ponSrd = ponSrd;
	}

	public String getAdslSrd() {
		return adslSrd;
	}

	public void setAdslSrd(String adslSrd) {
		this.adslSrd = adslSrd;
	}

	public String getVdslSrd() {
		return vdslSrd;
	}

	public void setVdslSrd(String vdslSrd) {
		this.vdslSrd = vdslSrd;
	}

	public String getHosType() {
		return hosType;
	}

	public void setHosType(String hosType) {
		this.hosType = hosType;
	}

	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}

	public CustomerUI getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerUI customer) {
		this.customer = customer;
	}

	public InstallAddrUI getInstallAddress() {
		return installAddress;
	}

	public void setInstallAddress(InstallAddrUI installAddress) {
		this.installAddress = installAddress;
	}

	public String getPrevSbNo() {
		return prevSbNo;
	}

	public void setPrevSbNo(String prevSbNo) {
		this.prevSbNo = prevSbNo;
	}

	public String getPhInd() {
		return phInd;
	}

	public void setPhInd(String phInd) {
		this.phInd = phInd;
	}

	public String getApprovalRequested() {
		return approvalRequested;
	}

	public void setApprovalRequested(String approvalRequested) {
		this.approvalRequested = approvalRequested;
	}
	public void setOtChrgType(String otChrgType) {
		OtChrgType = otChrgType;
	}
	public String getOtChrgType() {
		return OtChrgType;
	}
	public void setVectorSrd(String vectorSrd) {
		this.vectorSrd = vectorSrd;
	}
	public String getVectorSrd() {
		return vectorSrd;
	}
	public void setVectorInstFee(String vectorInstFee) {
		this.vectorInstFee = vectorInstFee;
	}
	public String getVectorInstFee() {
		return vectorInstFee;
	}
	public void setRelatedSbList(List<AddressInfoUI> relatedSbList) {
		this.relatedSbList = relatedSbList;
	}
	public List<AddressInfoUI> getRelatedSbList() {
		return relatedSbList;
	}
	public void setSbSelected(String sbSelected) {
		this.sbSelected = sbSelected;
	}
	public String getSbSelected() {
		return sbSelected;
	}
	public void setCommonErrorArea(String commonErrorArea) {
		this.commonErrorArea = commonErrorArea;
	}
	public String getCommonErrorArea() {
		return commonErrorArea;
	}
	public void setCpqHosType(String cpqHosType) {
		this.cpqHosType = cpqHosType;
	}
	public String getCpqHosType() {
		return cpqHosType;
	}
	public void setCslOfferEnableInd(String cslOfferEnableInd) {
		this.cslOfferEnableInd = cslOfferEnableInd;
	}
	public String getCslOfferEnableInd() {
		return cslOfferEnableInd;
	}
	public void setMobileOfferInd(String mobileOfferInd) {
		this.mobileOfferInd = mobileOfferInd;
	}
	public String getMobileOfferInd() {
		return mobileOfferInd;
	}
	public void setPonOTChrgType(String ponOTChrgType) {
		this.ponOTChrgType = ponOTChrgType;
	}
	public String getPonOTChrgType() {
		return ponOTChrgType;
	}
	public void setAdslOTChrgType(String adslOTChrgType) {
		this.adslOTChrgType = adslOTChrgType;
	}
	public String getAdslOTChrgType() {
		return adslOTChrgType;
	}
	public void setVdslOTChrgType(String vdslOTChrgType) {
		this.vdslOTChrgType = vdslOTChrgType;
	}
	public String getVdslOTChrgType() {
		return vdslOTChrgType;
	}
	public void setVectorOTChrgType(String vectorOTChrgType) {
		this.vectorOTChrgType = vectorOTChrgType;
	}
	public String getVectorOTChrgType() {
		return vectorOTChrgType;
	}
	public void setIsPon4Village(String isPon4Village) {
		this.isPon4Village = isPon4Village;
	}
	public String getIsPon4Village() {
		return isPon4Village;
	}
	public void setPon4VillageSubmitted(String pon4VillageSubmitted) {
		this.pon4VillageSubmitted = pon4VillageSubmitted;
	}
	public String getPon4VillageSubmitted() {
		return pon4VillageSubmitted;
	}

}
