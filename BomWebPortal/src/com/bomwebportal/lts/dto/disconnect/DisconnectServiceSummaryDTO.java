package com.bomwebportal.lts.dto.disconnect;

import java.util.ArrayList;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.profile.Idd0060ProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;


public class DisconnectServiceSummaryDTO extends ServiceSummaryDTO {

	private static final long serialVersionUID = -3670975014732486270L;

	private String disconnectReason ;
	private String dFromSerialNum ;
	private String waiveDfromReason ;
	private String nA ;
	private String thirdPartyAppl;
	private String lostEquipment ;
	private String ceaseRentalDate;
	
	private String fsaNumber;
	private String fsaPendingSbOrdId;
	private String fsaPendingOCID;
	private String fsaSrd;
	
	private String newBillingName;
	private String billingMedia;
	
	private String applTime;
	private String apptSrd;
	private String effectiveDate ;
	private String effectiveTime ;
	private String apptDate ;
	private String apptTime ;
	private String callingCardHandling;
	private String collectEquipAddr;
	private String forceFieldVisitInd = null;
	
	private List<ItemDetailSummaryDTO> disSrvItemList = null;
	private List<ItemDetailSummaryDTO> iddFixedFeePlanItemList = null;
	private List<ItemDetailSummaryDTO> imsVasItemList = null;
	private List<Idd0060ProfileLtsDTO> srv0060DtlList = null;
	private List<IddCallPlanProfileLtsDTO> srvCPDtlList = null;
	
	
	public String getDisconnectReason() {
		return disconnectReason;
	}
	public void setDisconnectReason(String disconnectReason) {
		this.disconnectReason = disconnectReason;
	}
	public String getdFromSerialNum() {
		return dFromSerialNum;
	}
	public void setdFromSerialNum(String dFromSerialNum) {
		this.dFromSerialNum = dFromSerialNum;
	}
	public String getWaiveDfromReason() {
		return waiveDfromReason;
	}
	public void setWaiveDfromReason(String waiveDfromReason) {
		this.waiveDfromReason = waiveDfromReason;
	}
	public String getnA() {
		return nA;
	}
	public void setnA(String nA) {
		this.nA = nA;
	}
	public String getThirdPartyAppl() {
		return thirdPartyAppl;
	}
	public void setThirdPartyAppl(String thirdPartyAppl) {
		this.thirdPartyAppl = thirdPartyAppl;
	}
	public String getLostEquipment() {
		return lostEquipment;
	}
	public void setLostEquipment(String lostEquipment) {
		this.lostEquipment = lostEquipment;
	}
	public String getCeaseRentalDate() {
		return ceaseRentalDate;
	}
	public void setCeaseRentalDate(String ceaseRentalDate) {
		this.ceaseRentalDate = ceaseRentalDate;
	}
	public String getFsaNumber() {
		return fsaNumber;
	}
	public void setFsaNumber(String fsaNumber) {
		this.fsaNumber = fsaNumber;
	}
	public String getFsaPendingSbOrdId() {
		return fsaPendingSbOrdId;
	}
	public void setFsaPendingSbOrdId(String fsaPendingSbOrdId) {
		this.fsaPendingSbOrdId = fsaPendingSbOrdId;
	}
	public String getFsaPendingOCID() {
		return fsaPendingOCID;
	}
	public void setFsaPendingOCID(String fsaPendingOCID) {
		this.fsaPendingOCID = fsaPendingOCID;
	}
	public String getFsaSrd() {
		return fsaSrd;
	}
	public void setFsaSrd(String fsaSrd) {
		this.fsaSrd = fsaSrd;
	}
	public String getBillingMedia() {
		return billingMedia;
	}
	public void setBillingMedia(String billingMedia) {
		this.billingMedia = billingMedia;
	}
	public String getApplTime() {
		return applTime;
	}
	public void setApplTime(String applTime) {
		this.applTime = applTime;
	}
	public String getApptSrd() {
		return apptSrd;
	}
	public void setApptSrd(String apptSrd) {
		this.apptSrd = apptSrd;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public String getApptDate() {
		return apptDate;
	}
	public void setApptDate(String apptDate) {
		this.apptDate = apptDate;
	}
	public String getApptTime() {
		return apptTime;
	}
	public void setApptTime(String apptTime) {
		this.apptTime = apptTime;
	}
	public List<ItemDetailSummaryDTO> getDisSrvItemList() {
		return disSrvItemList;
	}
	public void setDisSrvItemList(List<ItemDetailSummaryDTO> disSrvItemList) {
		this.disSrvItemList = disSrvItemList;
	}
	public void addDisSrvItem(ItemDetailSummaryDTO pItem) {
		
		if (this.disSrvItemList == null) {
			this.disSrvItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.disSrvItemList.add(pItem);
	}
	public List<ItemDetailSummaryDTO> getImsVasItemList() {
		return imsVasItemList;
	}
	public void setImsVasItemList(List<ItemDetailSummaryDTO> imsVasItemList) {
		this.imsVasItemList = imsVasItemList;
	}
	public void addImsVasItem(ItemDetailSummaryDTO pItem) {
		
		if (this.imsVasItemList == null) {
			this.imsVasItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.imsVasItemList.add(pItem);
	}
	public List<ItemDetailSummaryDTO> getIddFixedFeePlanItemList() {
		return iddFixedFeePlanItemList;
	}
	public void setIddFixedFeePlanItemList(List<ItemDetailSummaryDTO> iddFixedFeePlanItemList) {
		this.iddFixedFeePlanItemList = iddFixedFeePlanItemList;
	}
	public void addIddFixFeePlanItem(ItemDetailSummaryDTO pItem) {
		
		if (this.iddFixedFeePlanItemList == null) {
			this.iddFixedFeePlanItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.iddFixedFeePlanItemList.add(pItem);
	}
	public List<Idd0060ProfileLtsDTO> getSrv0060DtlList() {
		return srv0060DtlList;
	}
	public void setSrv0060DtlList(List<Idd0060ProfileLtsDTO> srv0060DtlList) {
		this.srv0060DtlList = srv0060DtlList;
	}
	public List<IddCallPlanProfileLtsDTO> getSrvCPDtlList() {
		return srvCPDtlList;
	}
	public void setSrvCPDtlList(List<IddCallPlanProfileLtsDTO> srvCPDtlList) {
		this.srvCPDtlList = srvCPDtlList;
	}
	public String getCallingCardHandling() {
		return callingCardHandling;
	}
	public void setCallingCardHandling(String callingCardHandling) {
		this.callingCardHandling = callingCardHandling;
	}
	public String getCollectEquipAddr() {
		return collectEquipAddr;
	}
	public void setCollectEquipAddr(String collectEquipAddr) {
		this.collectEquipAddr = collectEquipAddr;
	}
	public String getNewBillingName() {
		return newBillingName;
	}
	public void setNewBillingName(String newBillingName) {
		this.newBillingName = newBillingName;
	}
	public String getForceFieldVisitInd() {
		return forceFieldVisitInd;
	}
	public void setForceFieldVisitInd(String forceFieldVisitInd) {
		this.forceFieldVisitInd = forceFieldVisitInd;
	}
	
}
