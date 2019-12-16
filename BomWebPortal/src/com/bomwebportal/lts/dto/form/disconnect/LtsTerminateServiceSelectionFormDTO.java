package com.bomwebportal.lts.dto.form.disconnect;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.profile.AccountDetailProfileAcqDTO;
import com.bomwebportal.lts.dto.profile.Idd0060ProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsDateFormatHelper;

public class LtsTerminateServiceSelectionFormDTO implements Serializable {

	private static final long serialVersionUID = 5162157612732858905L;
	
	private String appDate;
	private String today;
	
	private boolean na5;
	private String deceasedRelationship;
	private String deceasedType;
	
	private boolean removeNowtv;
	private boolean removeBoardband;
	
	private boolean lostModem;
	private String lostModemUsageInd;
	
	private boolean callingCardInd;
	private String callingCardDetailsHandling;
	private boolean callingCardSameAcct;
	private String callingCardAcctList;
	
	private boolean splitAcctInd;
	
	private boolean thirdParty;
	private String thirdTitle;
	private String thirdLastName;
	private String thirdFirstName;
	private String thirdDocType;
	private String thirdDocNum;
	private String thirdRelationship;
	private String thirdContactNum;
	private boolean thirdIdVerify;
	private String thirdOtherRelationship;
	
	private String disconnectFormSerial;
	private String disconnectReason;
	private String waiveDFormReason;

	private boolean fsaWgInd;
	private String fch;
	
	private boolean terminatePCDind;
	private boolean terminateHDTVind;
	
	private boolean bundle2GTp;
	private boolean lostEquipment;
	private String lostEquipmentPenalty;

	private List<ItemDetailProfileLtsDTO> srvPlanItemDetails = null;
	private List<ItemDetailProfileLtsDTO> vasPlanItemDetails = null;
	private List<ItemDetailProfileLtsDTO> fsaVasPlanItemDetails = null;
	
	private List<Idd0060ProfileLtsDTO> idd0060ProfileList;
	private List<IddCallPlanProfileLtsDTO> iddCallPlanProfileList;
	
	private boolean hasBillableAcct;
	private List<AccountDetailProfileAcqDTO> billableAcctList;
	private String srvAcctNum;
	private boolean hasOtherSrvSameAcc;
	
	private Action formAction;
	
	public enum Action {
		SUBMIT, UPDATE_LIST;
	}
	
	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}
		
	public LtsTerminateServiceSelectionFormDTO() {
		appDate = LtsDateFormatHelper.getSysDate("dd/MM/yyyy");
	}
	
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public boolean isNa5() {
		return na5;
	}
	public void setNa5(boolean na5) {
		this.na5 = na5;
	}
	public String getDeceasedRelationship() {
		return deceasedRelationship;
	}
	public void setDeceasedRelationship(String deceasedRelationship) {
		this.deceasedRelationship = deceasedRelationship;
	}
	public boolean isRemoveNowtv() {
		return removeNowtv;
	}
	public void setRemoveNowtv(boolean removeNowtv) {
		this.removeNowtv = removeNowtv;
	}
	public boolean isRemoveBoardband() {
		return removeBoardband;
	}
	public void setRemoveBoardband(boolean removeBoardband) {
		this.removeBoardband = removeBoardband;
	}
	public boolean isCallingCardInd() {
		return callingCardInd;
	}
	public void setCallingCardInd(boolean callingCardInd) {
		this.callingCardInd = callingCardInd;
	}
	public String getCallingCardDetailsHandling() {
		return callingCardDetailsHandling;
	}
	public void setCallingCardDetailsHandling(String callingCardDetailsHandling) {
		this.callingCardDetailsHandling = callingCardDetailsHandling;
	}
	public boolean isThirdParty() {
		return thirdParty;
	}
	public void setThirdParty(boolean thirdParty) {
		this.thirdParty = thirdParty;
	}
	public String getThirdTitle() {
		return thirdTitle;
	}
	public void setThirdTitle(String thirdTitle) {
		this.thirdTitle = thirdTitle;
	}
	public String getThirdLastName() {
		return thirdLastName;
	}
	public void setThirdLastName(String thirdLastName) {
		this.thirdLastName = thirdLastName;
	}
	public String getThirdFirstName() {
		return thirdFirstName;
	}
	public void setThirdFirstName(String thirdFirstName) {
		this.thirdFirstName = thirdFirstName;
	}
	public String getThirdDocType() {
		return thirdDocType;
	}
	public void setThirdDocType(String thirdDocType) {
		this.thirdDocType = thirdDocType;
	}
	public String getThirdDocNum() {
		return thirdDocNum;
	}
	public void setThirdDocNum(String thirdDocNum) {
		this.thirdDocNum = thirdDocNum;
	}
	public String getThirdRelationship() {
		return thirdRelationship;
	}
	public void setThirdRelationship(String thirdRelationship) {
		this.thirdRelationship = thirdRelationship;
	}
	public String getThirdContactNum() {
		return thirdContactNum;
	}
	public void setThirdContactNum(String thirdContactNum) {
		this.thirdContactNum = thirdContactNum;
	}
	public boolean isThirdIdVerify() {
		return thirdIdVerify;
	}
	public void setThirdIdVerify(boolean thirdIdVerify) {
		this.thirdIdVerify = thirdIdVerify;
	}
	public String getDisconnectFormSerial() {
		return disconnectFormSerial;
	}
	public void setDisconnectFormSerial(String disconnectFormSerial) {
		this.disconnectFormSerial = disconnectFormSerial;
	}
	public String getDisconnectReason() {
		return disconnectReason;
	}
	public void setDisconnectReason(String disconnectReason) {
		this.disconnectReason = disconnectReason;
	}
	public String getWaiveDFormReason() {
		return waiveDFormReason;
	}
	public void setWaiveDFormReason(String waiveDFormReason) {
		this.waiveDFormReason = waiveDFormReason;
	}
	public String getFch() {
		return fch;
	}
	public void setFch(String fch) {
		this.fch = fch;
	}
	public boolean isTerminatePCDind() {
		return terminatePCDind;
	}
	public void setTerminatePCDind(boolean terminatePCDind) {
		this.terminatePCDind = terminatePCDind;
	}
	public boolean isTerminateHDTVind() {
		return terminateHDTVind;
	}
	public void setTerminateHDTVind(boolean terminateHDTVind) {
		this.terminateHDTVind = terminateHDTVind;
	}
	public boolean isBundle2GTp() {
		return bundle2GTp;
	}
	public void setBundle2GTp(boolean bundle2gTp) {
		bundle2GTp = bundle2gTp;
	}
	public boolean isLostEquipment() {
		return lostEquipment;
	}
	public void setLostEquipment(boolean lostEquipment) {
		this.lostEquipment = lostEquipment;
	}
	public String getLostEquipmentPenalty() {
		return lostEquipmentPenalty;
	}
	public void setLostEquipmentPenalty(String lostEquipmentPenalty) {
		this.lostEquipmentPenalty = lostEquipmentPenalty;
	}

	public List<ItemDetailProfileLtsDTO> getSrvPlanItemDetails() {
		return srvPlanItemDetails;
	}
	public void setSrvPlanItemDetails(
			List<ItemDetailProfileLtsDTO> srvPlanItemDetails) {
		this.srvPlanItemDetails = srvPlanItemDetails;
	}
	public List<ItemDetailProfileLtsDTO> getVasPlanItemDetails() {
		return vasPlanItemDetails;
	}
	public void setVasPlanItemDetails(
			List<ItemDetailProfileLtsDTO> vasPlanItemDetails) {
		this.vasPlanItemDetails = vasPlanItemDetails;
	}
	public List<ItemDetailProfileLtsDTO> getFsaVasPlanItemDetails() {
		return fsaVasPlanItemDetails;
	}
	public void setFsaVasPlanItemDetails(
			List<ItemDetailProfileLtsDTO> fsaVasPlanItemDetails) {
		this.fsaVasPlanItemDetails = fsaVasPlanItemDetails;
	}
	public List<Idd0060ProfileLtsDTO> getIdd0060ProfileList() {
		return idd0060ProfileList;
	}
	public void setIdd0060ProfileList(List<Idd0060ProfileLtsDTO> idd0060ProfileList) {
		this.idd0060ProfileList = idd0060ProfileList;
	}
	public List<IddCallPlanProfileLtsDTO> getIddCallPlanProfileList() {
		return iddCallPlanProfileList;
	}
	public void setIddCallPlanProfileList(
			List<IddCallPlanProfileLtsDTO> iddCallPlanProfileList) {
		this.iddCallPlanProfileList = iddCallPlanProfileList;
	}
	public String getThirdOtherRelationship() {
		return thirdOtherRelationship;
	}
	public void setThirdOtherRelationship(String thirdOtherRelationship) {
		this.thirdOtherRelationship = thirdOtherRelationship;
	}
	public boolean isSplitAcctInd() {
		return splitAcctInd;
	}
	public void setSplitAcctInd(boolean splitAcctInd) {
		this.splitAcctInd = splitAcctInd;
	}
	public String getDeceasedType() {
		return deceasedType;
	}
	public void setDeceasedType(String deceasedType) {
		this.deceasedType = deceasedType;
	}
	public boolean isLostModem() {
		return lostModem;
	}
	public void setLostModem(boolean lostModem) {
		this.lostModem = lostModem;
	}
	public String getLostModemUsageInd() {
		return lostModemUsageInd;
	}
	public void setLostModemUsageInd(String lostModemUsageInd) {
		this.lostModemUsageInd = lostModemUsageInd;
	}
	public boolean isFsaWgInd() {
		return fsaWgInd;
	}
	public void setFsaWgInd(boolean fsaWgInd) {
		this.fsaWgInd = fsaWgInd;
	}
	public boolean isHasBillableAcct() {
		return hasBillableAcct;
	}
	public void setHasBillableAcct(boolean hasBillableAcct) {
		this.hasBillableAcct = hasBillableAcct;
	}
	public List<AccountDetailProfileAcqDTO> getBillableAcctList() {
		return billableAcctList;
	}
	public void setBillableAcctList(List<AccountDetailProfileAcqDTO> billableAcctList) {
		this.billableAcctList = billableAcctList;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public boolean isCallingCardSameAcct() {
		return callingCardSameAcct;
	}
	public void setCallingCardSameAcct(boolean callingCardSameAcct) {
		this.callingCardSameAcct = callingCardSameAcct;
	}
	public String getSrvAcctNum() {
		return srvAcctNum;
	}
	public void setSrvAcctNum(String srvAcctNum) {
		this.srvAcctNum = srvAcctNum;
	}
	public boolean isHasOtherSrvSameAcc() {
		return hasOtherSrvSameAcc;
	}
	public void setHasOtherSrvSameAcc(boolean hasOtherSrvSameAcc) {
		this.hasOtherSrvSameAcc = hasOtherSrvSameAcc;
	}
	public String getCallingCardAcctList() {
		return callingCardAcctList;
	}
	public void setCallingCardAcctList(String callingCardAcctList) {
		this.callingCardAcctList = callingCardAcctList;
	}
	
}
