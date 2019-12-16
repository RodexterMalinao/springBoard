package com.pccw.rpt.schema.dto.amendmentForm;

import com.pccw.rpt.schema.dto.ReportDTO;

public class AmendmentFormDTO extends ReportDTO {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1848103831496035779L;
	
	
	/**
	 * 
	 */
	

	private boolean isAmendment;
	private boolean isCancellation;
	private String amendStrikeThru;
	private String cancelStrikeThru;
	private String orderId;
	
	private String date;
	private String time;
	private String salesShopCode;
	private String salesStaffNum;
	private String salesTel;
	private String custName;
	private boolean isHKID;
	private boolean isPass;
	private String idStrikethru;
	private String passStrikeThru;
	private String docNum;
	//private String passNum;
	private String srvNum ;
	private String thirdAppName;
	private String thirdContactNo;
	private String thirdAppRelation;
	
	private boolean isChgSRD;
	private String changeSRDchkBox;
	private String changeSRD;
	private boolean isChgIA;
	private String changeIAchkBox;
	private String changeIA;
	private boolean isChgIAoptBoxMovChrg;
	private String changeIAoptBoxMovChrg;
	private boolean isChgIAoptBoxMetCret;
	private String changeIAoptBoxMetCret;
	private boolean isChgIAoptBoxSpecical;
	private String changeIAoptBoxSpecical;
	private boolean isChgDN;
	private String changeDNchkBox;
	private String changeDN;
	private String apprvName;
	private String apprvSN;
	private boolean isChgOffer;
	private String changeOfferChkBox;
	private String changeOffer;
	private boolean isChgCardNum;
	private String changeCredCrdNumChkBox ;
	private String changeCredCrdNum;
	private boolean isChgTvChannel;
	private String changeTvChannelChkBox ;
	private String changeTvChannel;
	private boolean isChgVasPremi;
	private String changeVasPremiChkBox ;
	private String changeVasPremi;
	private boolean isChgShareModFsa;
	private String changeShareModFsaChkBox;
	private String changeShareModFsa;
	private boolean isChgCustInfo;
	private String changeCustInfoChkBox;
	private String changeCustInfo;
	private boolean isChgBA;
	private String changeBAChkBox ; 
	private String changeBA ;
	private String cancelReason;
	private String cancelOrderType;
	private boolean isOther;
	private String otherChkBox;
	private String otherContent;

	private String receiveDate;
	private String receiveTime;
	private String handleBy;
	private String edfUpdBy;
	
	
	public boolean isAmendment() {
		return isAmendment;
	}
	public void setAmendment(boolean isAmendment) {
		this.isAmendment = isAmendment;
	}
	public boolean isCancellation() {
		return isCancellation;
	}
	public void setCancellation(boolean isCancellation) {
		this.isCancellation = isCancellation;
	}
	public String getAmendStrikeThru() {
		return amendStrikeThru;
	}
	public void setAmendStrikeThru(String amendStrikeThru) {
		this.amendStrikeThru = amendStrikeThru;
	}
	public String getCancelStrikeThru() {
		return cancelStrikeThru;
	}
	public void setCancelStrikeThru(String cancelStrikeThru) {
		this.cancelStrikeThru = cancelStrikeThru;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getIdStrikethru() {
		return idStrikethru;
	}
	public void setIdStrikethru(String idStrikethru) {
		this.idStrikethru = idStrikethru;
	}
	public String getPassStrikeThru() {
		return passStrikeThru;
	}
	public void setPassStrikeThru(String passStrikeThru) {
		this.passStrikeThru = passStrikeThru;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getSrvNum() {
		return srvNum;
	}
	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}
	public String getThirdAppName() {
		return thirdAppName;
	}
	public void setThirdAppName(String thirdAppName) {
		this.thirdAppName = thirdAppName;
	}
	public String getThirdContactNo() {
		return thirdContactNo;
	}
	public void setThirdContactNo(String thirdContactNo) {
		this.thirdContactNo = thirdContactNo;
	}
	public String getThirdAppRelation() {
		return thirdAppRelation;
	}
	public void setThirdAppRelation(String thirdAppRelation) {
		this.thirdAppRelation = thirdAppRelation;
	}
	public String getChangeSRDchkBox() {
		return changeSRDchkBox;
	}
	public void setChangeSRDchkBox(String changeSRDchkBox) {
		this.changeSRDchkBox = changeSRDchkBox;
	}
	public String getChangeSRD() {
		return changeSRD;
	}
	public void setChangeSRD(String changeSRD) {
		this.changeSRD = changeSRD;
	}
	public String getChangeIAchkBox() {
		return changeIAchkBox;
	}
	public void setChangeIAchkBox(String changeIAchkBox) {
		this.changeIAchkBox = changeIAchkBox;
	}
	public String getChangeIA() {
		return changeIA;
	}
	public void setChangeIA(String changeIA) {
		this.changeIA = changeIA;
	}
	public String getChangeIAoptBoxMovChrg() {
		return changeIAoptBoxMovChrg;
	}
	public void setChangeIAoptBoxMovChrg(String changeIAoptBoxMovChrg) {
		this.changeIAoptBoxMovChrg = changeIAoptBoxMovChrg;
	}
	public String getChangeIAoptBoxMetCret() {
		return changeIAoptBoxMetCret;
	}
	public void setChangeIAoptBoxMetCret(String changeIAoptBoxMetCret) {
		this.changeIAoptBoxMetCret = changeIAoptBoxMetCret;
	}
	public String getChangeIAoptBoxSpecical() {
		return changeIAoptBoxSpecical;
	}
	public void setChangeIAoptBoxSpecical(String changeIAoptBoxSpecical) {
		this.changeIAoptBoxSpecical = changeIAoptBoxSpecical;
	}
	public String getChangeDNchkBox() {
		return changeDNchkBox;
	}
	public void setChangeDNchkBox(String changeDNchkBox) {
		this.changeDNchkBox = changeDNchkBox;
	}
	public String getChangeDN() {
		return changeDN;
	}
	public void setChangeDN(String changeDN) {
		this.changeDN = changeDN;
	}
	public String getApprvName() {
		return apprvName;
	}
	public void setApprvName(String apprvName) {
		this.apprvName = apprvName;
	}
	public String getApprvSN() {
		return apprvSN;
	}
	public void setApprvSN(String apprvSN) {
		this.apprvSN = apprvSN;
	}
	public String getChangeOfferChkBox() {
		return changeOfferChkBox;
	}
	public void setChangeOfferChkBox(String changeOfferChkBox) {
		this.changeOfferChkBox = changeOfferChkBox;
	}
	public String getChangeOffer() {
		return changeOffer;
	}
	public void setChangeOffer(String changeOffer) {
		this.changeOffer = changeOffer;
	}
	public String getChangeCredCrdNumChkBox() {
		return changeCredCrdNumChkBox;
	}
	public void setChangeCredCrdNumChkBox(String changeCredCrdNumChkBox) {
		this.changeCredCrdNumChkBox = changeCredCrdNumChkBox;
	}
	public String getChangeCredCrdNum() {
		return changeCredCrdNum;
	}
	public void setChangeCredCrdNum(String changeCredCrdNum) {
		this.changeCredCrdNum = changeCredCrdNum;
	}
	public String getChangeTvChannelChkBox() {
		return changeTvChannelChkBox;
	}
	public void setChangeTvChannelChkBox(String changeTvChannelChkBox) {
		this.changeTvChannelChkBox = changeTvChannelChkBox;
	}
	public String getChangeTvChannel() {
		return changeTvChannel;
	}
	public void setChangeTvChannel(String changeTvChannel) {
		this.changeTvChannel = changeTvChannel;
	}
	public String getChangeVasPremiChkBox() {
		return changeVasPremiChkBox;
	}
	public void setChangeVasPremiChkBox(String changeVasPremiChkBox) {
		this.changeVasPremiChkBox = changeVasPremiChkBox;
	}
	public String getChangeVasPremi() {
		return changeVasPremi;
	}
	public void setChangeVasPremi(String changeVasPremi) {
		this.changeVasPremi = changeVasPremi;
	}
	public String getChangeShareModFsaChkBox() {
		return changeShareModFsaChkBox;
	}
	public void setChangeShareModFsaChkBox(String changeShareModFsaChkBox) {
		this.changeShareModFsaChkBox = changeShareModFsaChkBox;
	}
	public String getChangeShareModFsa() {
		return changeShareModFsa;
	}
	public void setChangeShareModFsa(String changeShareModFsa) {
		this.changeShareModFsa = changeShareModFsa;
	}
	public String getChangeCustInfoChkBox() {
		return changeCustInfoChkBox;
	}
	public void setChangeCustInfoChkBox(String changeCustInfoChkBox) {
		this.changeCustInfoChkBox = changeCustInfoChkBox;
	}
	public String getChangeCustInfo() {
		return changeCustInfo;
	}
	public void setChangeCustInfo(String changeCustInfo) {
		this.changeCustInfo = changeCustInfo;
	}
	public String getChangeBAChkBox() {
		return changeBAChkBox;
	}
	public void setChangeBAChkBox(String changeBAChkBox) {
		this.changeBAChkBox = changeBAChkBox;
	}
	public String getChangeBA() {
		return changeBA;
	}
	public void setChangeBA(String changeBA) {
		this.changeBA = changeBA;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getHandleBy() {
		return handleBy;
	}
	public void setHandleBy(String handleBy) {
		this.handleBy = handleBy;
	}
	public String getEdfUpdBy() {
		return edfUpdBy;
	}
	public void setEdfUpdBy(String edfUpdBy) {
		this.edfUpdBy = edfUpdBy;
	}
	public boolean isHKID() {
		return isHKID;
	}
	public void setHKID(boolean isHKID) {
		this.isHKID = isHKID;
	}
	public boolean isPass() {
		return isPass;
	}
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	public boolean isChgOffer() {
		return isChgOffer;
	}
	public void setChgOffer(boolean isChgOffer) {
		this.isChgOffer = isChgOffer;
	}
	public boolean isChgCardNum() {
		return isChgCardNum;
	}
	public void setChgCardNum(boolean isChgCardNum) {
		this.isChgCardNum = isChgCardNum;
	}
	public boolean isChgTvChannel() {
		return isChgTvChannel;
	}
	public void setChgTvChannel(boolean isChgTvChannel) {
		this.isChgTvChannel = isChgTvChannel;
	}
	public boolean isChgVasPremi() {
		return isChgVasPremi;
	}
	public void setChgVasPremi(boolean isChgVasPremi) {
		this.isChgVasPremi = isChgVasPremi;
	}
	public boolean isChgShareModFsa() {
		return isChgShareModFsa;
	}
	public void setChgShareModFsa(boolean isChgShareModFsa) {
		this.isChgShareModFsa = isChgShareModFsa;
	}
	public boolean isChgCustInfo() {
		return isChgCustInfo;
	}
	public void setChgCustInfo(boolean isChgCustInfo) {
		this.isChgCustInfo = isChgCustInfo;
	}
	public boolean isChgBA() {
		return isChgBA;
	}
	public void setChgBA(boolean isChgBA) {
		this.isChgBA = isChgBA;
	}
	public boolean isChgSRD() {
		return isChgSRD;
	}
	public void setChgSRD(boolean isChgSRD) {
		this.isChgSRD = isChgSRD;
	}
	public boolean isChgIA() {
		return isChgIA;
	}
	public void setChgIA(boolean isChgIA) {
		this.isChgIA = isChgIA;
	}
	public boolean isChgIAoptBoxMovChrg() {
		return isChgIAoptBoxMovChrg;
	}
	public void setChgIAoptBoxMovChrg(boolean isChgIAoptBoxMovChrg) {
		this.isChgIAoptBoxMovChrg = isChgIAoptBoxMovChrg;
	}
	public boolean isChgIAoptBoxMetCret() {
		return isChgIAoptBoxMetCret;
	}
	public void setChgIAoptBoxMetCret(boolean isChgIAoptBoxMetCret) {
		this.isChgIAoptBoxMetCret = isChgIAoptBoxMetCret;
	}
	public boolean isChgIAoptBoxSpecical() {
		return isChgIAoptBoxSpecical;
	}
	public void setChgIAoptBoxSpecical(boolean isChgIAoptBoxSpecical) {
		this.isChgIAoptBoxSpecical = isChgIAoptBoxSpecical;
	}
	public boolean isChgDN() {
		return isChgDN;
	}
	public void setChgDN(boolean isChgDN) {
		this.isChgDN = isChgDN;
	}
	public String getOtherChkBox() {
		return otherChkBox;
	}
	public void setOtherChkBox(String otherChkBox) {
		this.otherChkBox = otherChkBox;
	}
	public boolean isOther() {
		return isOther;
	}
	public void setOther(boolean isOther) {
		this.isOther = isOther;
	}
	public String getOtherContent() {
		return otherContent;
	}
	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}
	public String getSalesShopCode() {
		return salesShopCode;
	}
	public void setSalesShopCode(String salesShopCode) {
		this.salesShopCode = salesShopCode;
	}
	public String getSalesStaffNum() {
		return salesStaffNum;
	}
	public void setSalesStaffNum(String salesStaffNum) {
		this.salesStaffNum = salesStaffNum;
	}
	public String getSalesTel() {
		return salesTel;
	}
	public void setSalesTel(String salesTel) {
		this.salesTel = salesTel;
	}
	public String getCancelOrderType() {
		return cancelOrderType;
	}
	public void setCancelOrderType(String cancelOrderType) {
		this.cancelOrderType = cancelOrderType;
	}
	
	
}

	
	