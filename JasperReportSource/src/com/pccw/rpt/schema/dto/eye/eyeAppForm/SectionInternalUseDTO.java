package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionCRptDTO.ChargingItem;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionFRptDTO.FixedCharge;
import com.pccw.rpt.util.ReportUtil;

public class SectionInternalUseDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 408891307785622186L;
	
	private String orderTakedBy;
	
	private String sourceCd;
	
	private String salesmanCd;
	
	private String salesStaffName;
	
	private String cmrid;
	
	private String contactNum;
	
	private String location;
	
	private String uamsNum;
	
	private String refNum;
	
	private String fsa;
	
	private String customerRemarks;
	
	private String orderRemarks;
	
	private String eyeSrvNum;
	
	private String idDocType;
	
	private String idDocNum;
	
	private String earliestSRDReason;
	
	private boolean blacklistAddr;
	
	private String cancelDnType;
	
	private String cancelDnNum;
	
	private String otherCustRequest;
	
	private ArrayList<ChargingItem> optOutDtlList = new ArrayList<ChargingItem>();
	
	private ArrayList<ChargingItem> chargingList = new ArrayList<ChargingItem>();
	
	private ArrayList<FixedCharge> externalRelocationList = new ArrayList<FixedCharge>();
	
	private String srvType;
	
	private String bandwidth;
	
	private String technology;
	
	private String vimLang;
	
	private String eyeArrangement;
	
	private String fsaArrangement;
	
	private ArrayList<FixedCharge> outLtsTpList = new ArrayList<FixedCharge>();
	private ArrayList<FixedCharge> outImsTpList = new ArrayList<FixedCharge>();
	
	private ArrayList<DocumentItem> documentList = new ArrayList<DocumentItem>();

	private String redemptionMedia;
	
	private String smsNumber;
	
	private String acctNum;
	
	private String billType;
	
	private String paperBillCharging;
	
	private String waiveReason;
	
	private String imsL2Job;
	
	public String getVimLang() {
		return vimLang;
	}

	public void setVimLang(String vimLang) {
		this.vimLang = vimLang;
	}

	public String getOrderTakedBy() {
		return ReportUtil.defaultString(this.orderTakedBy);
	}

	public void setOrderTakedBy(String orderTakedBy) {
		this.orderTakedBy = orderTakedBy;
	}

	public String getSourceCd() {
		return ReportUtil.defaultString(this.sourceCd);
	}

	public void setSourceCd(String sourceCd) {
		this.sourceCd = sourceCd;
	}

	public String getSalesmanCd() {
		return ReportUtil.defaultString(this.salesmanCd);
	}

	public void setSalesmanCd(String salesmanCd) {
		this.salesmanCd = salesmanCd;
	}

	public String getSalesStaffName() {
		return ReportUtil.defaultString(this.salesStaffName);
	}

	public void setSalesStaffName(String salesStaffName) {
		this.salesStaffName = salesStaffName;
	}

	public String getCmrid() {
		return ReportUtil.defaultString(this.cmrid);
	}

	public void setCmrid(String cmrid) {
		this.cmrid = cmrid;
	}

	public String getContactNum() {
		return ReportUtil.defaultString(this.contactNum);
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getLocation() {
		return ReportUtil.defaultString(this.location);
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUamsNum() {
		return ReportUtil.defaultString(this.uamsNum);
	}

	public void setUamsNum(String uamsNum) {
		this.uamsNum = uamsNum;
	}

	public String getRefNum() {
		return ReportUtil.defaultString(this.refNum);
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public String getFsa() {
		return ReportUtil.defaultString(this.fsa);
	}

	public void setFsa(String fsa) {
		this.fsa = fsa;
	}

	public String getCustomerRemarks() {
		return StringUtils.defaultString(this.customerRemarks);
	}

	public void setCustomerRemarks(String customerRemarks) {
		this.customerRemarks = customerRemarks;
	}

	public String getOrderRemarks() {
		return StringUtils.defaultString(this.orderRemarks);
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}
	
	public ArrayList<ChargingItem> getOptOutDtlList() {
		return this.optOutDtlList;
	}

	public void addOptOutDtl(String pDescription) {
		this.optOutDtlList.add(new ChargingItem(pDescription,
				null, null));
	}
	public boolean isOptOutDtlListEmpty() {
		return this.optOutDtlList.isEmpty();
	}
	
	public ArrayList<ChargingItem> getChargingList() {
		return this.chargingList;
	}

	public void addCharging(String pDescription) {
		this.chargingList.add(new ChargingItem(pDescription,
				null, null));
	}
	public boolean isChargingListEmpty() {
		return this.chargingList.isEmpty();
	}

	public String getEyeSrvNum() {
		return ReportUtil.defaultString(this.eyeSrvNum);
	}

	public void setEyeSrvNum(String eyeSrvNum) {
		this.eyeSrvNum = eyeSrvNum;
	}

	public String getIdDocType() {
		return ReportUtil.defaultString(this.idDocType);
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdDocNum() {
		return ReportUtil.defaultString(this.idDocNum);
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public String getEarliestSRDReason() {
		return ReportUtil.defaultString(this.earliestSRDReason);
	}

	public void setEarliestSRDReason(String earliestSRDReason) {
		this.earliestSRDReason = earliestSRDReason;
	}

	public String getCancelDnType() {
		return ReportUtil.defaultString(this.cancelDnType);
	}

	public void setCancelDnType(String cancelDnType) {
		this.cancelDnType = cancelDnType;
	}

	public String getCancelDnNum() {
		return ReportUtil.defaultString(this.cancelDnNum);
	}

	public void setCancelDnNum(String cancelDnNum) {
		this.cancelDnNum = cancelDnNum;
	}

	public boolean isBlacklistAddr() {
		return this.blacklistAddr;
	}

	public void setBlacklistAddr(boolean blacklistAddr) {
		this.blacklistAddr = blacklistAddr;
	}

	public String getOtherCustRequest() {
		return ReportUtil.defaultString(this.otherCustRequest);
	}

	public void setOtherCustRequest(String otherCustRequest) {
		this.otherCustRequest = otherCustRequest;
	}

	public String getSrvType() {
		return srvType;
	}

	public void setSrvType(String srvType) {
		this.srvType = srvType;
	}

	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getEyeArrangement() {
		return this.eyeArrangement;
	}

	public void setEyeArrangement(String pEyeArrangement) {
		this.eyeArrangement = pEyeArrangement;
	}
	
	public String getFsaArrangement() {
		return this.fsaArrangement;
	}

	public void setFsaArrangement(String pFsaArrangement) {
		this.fsaArrangement = pFsaArrangement;
	}

	public void addOutLtsTp(String pTermplan, String pPenalty) {
		this.outLtsTpList.add(new FixedCharge(pTermplan, pPenalty));
	}

	public boolean isOutLtsTpListEmpty() {
		return this.outLtsTpList.isEmpty();
	}
	
	public ArrayList<FixedCharge> getOutLtsTpList() {
		return this.outLtsTpList;
	}
	
	public void addOutImsTp(String pTermplan, String pPenalty) {
		this.outImsTpList.add(new FixedCharge(pTermplan, pPenalty));
	}

	public boolean isOutImsTpListEmpty() {
		return this.outImsTpList.isEmpty();
	}
	
	public ArrayList<FixedCharge> getOutImsTpList() {
		return this.outImsTpList;
	}
	
	public void addDocument(String pDocType, String pWaiveReason, String pCollectedInd, String pFaxSerialNum) {
		this.documentList.add(new DocumentItem(pDocType, pWaiveReason, pCollectedInd, pFaxSerialNum));
	}
	
	public boolean isDocumentListEmpty(){
		return this.documentList.isEmpty();
	}
	
	public ArrayList<DocumentItem> getDocumentList() {
		return this.documentList;
	}
	
	public void addExternalRelocation(String pErItem, String pErHandling) {
		this.externalRelocationList.add(new FixedCharge(pErItem, pErHandling));
	}
	
	public boolean isExternalRelocationListEmpty() {
		return this.externalRelocationList.isEmpty();
	}
	
	public ArrayList<FixedCharge> getExternalRelocationList() {
		return this.externalRelocationList;
	}
	
	public static class DocumentItem implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3732575773446676830L;
		
		private String docType;
		private String waiveReason;
		private String collectedInd;
		private String faxSerialNum;
		
		public DocumentItem() {
			super();
		}

		public DocumentItem(String pDocType, String pWaiveReason, String pCollectedInd, String pFaxSerialNum) {
			super();
			this.docType = pDocType;
			this.waiveReason = pWaiveReason;
			this.collectedInd = pCollectedInd;
			this.faxSerialNum = pFaxSerialNum;
		}

		public String getDocType() {
			return ReportUtil.defaultString(this.docType);
		}

		public void setDocType(String pDocType) {
			this.docType = pDocType;
		}

		public String getWaiveReason() {
			return ReportUtil.defaultString(this.waiveReason);
		}

		public void setWaiveReason(String pWaiveReason) {
			this.waiveReason = pWaiveReason;
		}

		public String getCollectedInd() {
			return ReportUtil.defaultString(this.collectedInd);
		}

		public void setCollectedInd(String pCollectedInd) {
			this.collectedInd = pCollectedInd;
		}

		public String getFaxSerialNum() {
			return ReportUtil.defaultString(this.faxSerialNum);
		}

		public void setFaxSerialNum(String pFaxSerialNum) {
			this.faxSerialNum = pFaxSerialNum;
		}
	}

	public String getRedemptionMedia() {
		return redemptionMedia;
	}

	public void setRedemptionMedia(String redemptionMedia) {
		this.redemptionMedia = redemptionMedia;
	}

	public String getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}

	public String getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getPaperBillCharging() {
		return paperBillCharging;
	}

	public void setPaperBillCharging(String paperBillCharging) {
		this.paperBillCharging = paperBillCharging;
	}

	public String getWaiveReason() {
		return waiveReason;
	}

	public void setWaiveReason(String waiveReason) {
		this.waiveReason = waiveReason;
	}

	public String getImsL2Job() {
		return imsL2Job;
	}

	public void setImsL2Job(String imsL2Job) {
		this.imsL2Job = imsL2Job;
	}


}
