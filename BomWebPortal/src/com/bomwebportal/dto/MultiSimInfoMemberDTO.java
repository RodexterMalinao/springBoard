package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MultiSimInfoMemberDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = 4975159557923981455L;
	
	private String parentOrderId;
	private String memberNum;
	private String memberOrderId;
	private String memberOrderType;
	private String msisdn;
	private String msisdnlvl;
	private String mnpInd;
	private String mnpNumber;
	private String mnpTicketNum;
	private String mnpCutOverDate;
	private String mnpCutOverTime;
	private String mnpRno;
	private String mnpDno;
	private String mnpCustName;
	private String mnpDocNo;
	private SimDTO sim;
	private String ocid;
	private String memberStatus;
	private String errMsg;
	private String errCd;
	private String originalSimICCID;
	private String originalMsisdn;
	
	private String prePaidSimDocInd;
	/*private boolean prePaidSimDocWithCert=false;
	private boolean prePaidSimDocWithoutCert=false;*/
	
	private String selectedSimItemId;
	private List<String> bundleVasItemList;
	private List<String> selectedVasItemList;
	private int cnmStatus;
	private String basketId;
	private String itemId;
	private String csimInd;
	private SignoffDTO signDTO;
	private String originalNewMsisdn;
	private boolean amendMNP;
	private boolean sameAsCust;
	private ActualUserDTO actualUserDTO;	
	private String actualDno;
	private boolean ignorePostpaidcheckbox = false;
	private String opssInd;
	private String starterPack;
	private String curCheckResult;
	private String curMsisdn;
	private String curCheckErrMsg;
	private String curCustNo;
	private String curMobCustNo;
	private String curAcctNo;
	private String curIdDocType;
	private String curIdDocNum;
	private String curTitle;
	private String curFirstName;
	private String curLastName;
	private String curSimIccid;
	private String curSimItemCd;
	private String curSimItemDesc;
	private String curPenaltyWaiveInd;
	private String too1AdminCharge;
	private String too1AdminChargeInd;
	private String too1WaiveReasonCd;
	private String nfcInd;
	private String numType;
	private String sameAsCustInd;
	private String checkResult;
	private String checkPass;
	private String curActiveContractparentProdType;
	private String curActiveContracttcDesc;
	private String curActiveContractduration;
	private String curActiveContracteffStartDate;
	private String curActiveContracteffEndDate;
	private String autoInd;
	private String cosInd;
	private String too1Ind;
	private String cmnInd;
	private String brmInd;
	private String subBrand;
	private String brmChgSimInd;
	private boolean chgSimBool;
	
	public String getSubSimType() {
		return subSimType;
	}

	public void setSubSimType(String subSimType) {
		this.subSimType = subSimType;
	}
	private String subSimType;
	private boolean changeSim;
	private String chgSimIccid;
	private String chgSimWaiveReasonRadio;
	private String chgSimWaiveReason;
	private String chgSimItemCd;
	private String serviceRequestDate;
	private String too1BrmOrder;
	
	
	public String getToo1BrmOrder() {
		return too1BrmOrder;
	}

	public void setToo1BrmOrder(String too1BrmOrder) {
		this.too1BrmOrder = too1BrmOrder;
	}

	public String getMnpOrder() {
		return mnpOrder;
	}

	public void setMnpOrder(String mnpOrder) {
		this.mnpOrder = mnpOrder;
	}
	private String mnpOrder;
	
	public String getServiceRequestDate() {
		return serviceRequestDate;
	}

	public void setServiceRequestDate(String serviceRequestDate) {
		this.serviceRequestDate = serviceRequestDate;
	}

	public boolean isChangeSim() {
		return changeSim;
	}

	public void setChangeSim(boolean changeSim) {
		this.changeSim = changeSim;
	}

	public String getChgSimIccid() {
		return chgSimIccid;
	}

	public void setChgSimIccid(String chgSimIccid) {
		this.chgSimIccid = chgSimIccid;
	}

	public String getChgSimWaiveReasonRadio() {
		return chgSimWaiveReasonRadio;
	}

	public void setChgSimWaiveReasonRadio(String chgSimWaiveReasonRadio) {
		this.chgSimWaiveReasonRadio = chgSimWaiveReasonRadio;
	}

	public String getChgSimWaiveReason() {
		return chgSimWaiveReason;
	}

	public void setChgSimWaiveReason(String chgSimWaiveReason) {
		this.chgSimWaiveReason = chgSimWaiveReason;
	}

	public String getChgSimItemCd() {
		return chgSimItemCd;
	}

	public void setChgSimItemCd(String chgSimItemCd) {
		this.chgSimItemCd = chgSimItemCd;
	}

	public String getSubBrand() {
		return subBrand;
	}

	public void setSubBrand(String subBrand) {
		this.subBrand = subBrand;
	}
	
	public String getToo1AdminChargeInd() {
		return too1AdminChargeInd;
	}

	public void setToo1AdminChargeInd(String too1AdminChargeInd) {
		this.too1AdminChargeInd = too1AdminChargeInd;
	}

	public String getToo1WaiveReasonCd() {
		return too1WaiveReasonCd;
	}

	public void setToo1WaiveReasonCd(String too1WaiveReasonCd) {
		this.too1WaiveReasonCd = too1WaiveReasonCd;
	}

	public String getBrmInd() {
		return brmInd;
	}

	public void setBrmInd(String brmInd) {
		this.brmInd = brmInd;
	}

	public String getAutoInd() {
		return autoInd;
	}

	public void setAutoInd(String autoInd) {
		this.autoInd = autoInd;
	}

	public String getCosInd() {
		return cosInd;
	}

	public void setCosInd(String cosInd) {
		this.cosInd = cosInd;
	}

	public String getToo1Ind() {
		return too1Ind;
	}

	public void setToo1Ind(String too1Ind) {
		this.too1Ind = too1Ind;
	}

	public String getCmnInd() {
		return cmnInd;
	}

	public void setCmnInd(String cmnInd) {
		this.cmnInd = cmnInd;
	}

	public String getCurActiveContractparentProdType() {
		return curActiveContractparentProdType;
	}

	public void setCurActiveContractparentProdType(String curActiveContractparentProdType) {
		this.curActiveContractparentProdType = curActiveContractparentProdType;
	}

	public String getCurActiveContracttcDesc() {
		return curActiveContracttcDesc;
	}

	public void setCurActiveContracttcDesc(String curActiveContracttcDesc) {
		this.curActiveContracttcDesc = curActiveContracttcDesc;
	}

	public String getCurActiveContractduration() {
		return curActiveContractduration;
	}

	public void setCurActiveContractduration(String curActiveContractduration) {
		this.curActiveContractduration = curActiveContractduration;
	}

	public String getCurActiveContracteffStartDate() {
		return curActiveContracteffStartDate;
	}

	public void setCurActiveContracteffStartDate(String curActiveContracteffStartDate) {
		this.curActiveContracteffStartDate = curActiveContracteffStartDate;
	}

	public String getCurActiveContracteffEndDate() {
		return curActiveContracteffEndDate;
	}

	public void setCurActiveContracteffEndDate(String curActiveContracteffEndDate) {
		this.curActiveContracteffEndDate = curActiveContracteffEndDate;
	}

	public String getCheckPass() {
		return checkPass;
	}

	public void setCheckPass(String checkPass) {
		this.checkPass = checkPass;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCurCustNo() {
		return curCustNo;
	}

	public void setCurCustNo(String curCustNo) {
		this.curCustNo = curCustNo;
	}

	public String getCurMobCustNo() {
		return curMobCustNo;
	}

	public void setCurMobCustNo(String curMobCustNo) {
		this.curMobCustNo = curMobCustNo;
	}

	public String getCurAcctNo() {
		return curAcctNo;
	}

	public void setCurAcctNo(String curAcctNo) {
		this.curAcctNo = curAcctNo;
	}

	public String getCurTitle() {
		return curTitle;
	}

	public void setCurTitle(String curTitle) {
		this.curTitle = curTitle;
	}

	public String getCurSimItemCd() {
		return curSimItemCd;
	}

	public void setCurSimItemCd(String curSimItemCd) {
		this.curSimItemCd = curSimItemCd;
	}

	public String getCurSimItemDesc() {
		return curSimItemDesc;
	}

	public void setCurSimItemDesc(String curSimItemDesc) {
		this.curSimItemDesc = curSimItemDesc;
	}

	public String getCurPenaltyWaiveInd() {
		return curPenaltyWaiveInd;
	}

	public void setCurPenaltyWaiveInd(String curPenaltyWaiveInd) {
		this.curPenaltyWaiveInd = curPenaltyWaiveInd;
	}

	public String getToo1AdminCharge() {
		return too1AdminCharge;
	}

	public void setToo1AdminCharge(String too1AdminCharge) {
		this.too1AdminCharge = too1AdminCharge;
	}
	
	public MultiSimInfoMemberDTO() {
		this.setSameAsCust(true);
		this.actualUserDTO = new ActualUserDTO();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		MultiSimInfoMemberDTO clone = (MultiSimInfoMemberDTO) super.clone();
		clone.setSim(new SimDTO());
		clone.getSim().setIccid(this.sim.getIccid());
		clone.getSim().setImsi(this.sim.getImsi());
		clone.getSim().setItemCode(this.sim.getItemCode());
		clone.getSim().setOrderId(this.sim.getOrderId());
		clone.getSim().setPuk1(this.sim.getPuk1());
		clone.getSim().setPuk2(this.sim.getPuk2());
		clone.setSelectedVasItemList(new ArrayList<String>());
		clone.getSelectedVasItemList().addAll(this.getSelectedVasItemList());
		return clone;
	
	}
		
	public MultiSimInfoMemberDTO getClone() {
		try {
			MultiSimInfoMemberDTO clone = (MultiSimInfoMemberDTO) this.clone();		
			
			return clone;
		} catch (CloneNotSupportedException e) {
			return null;
		}
		
	}
	
	public String getParentOrderId() {
		return parentOrderId;
	}
	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	public String getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}
	public String getMemberOrderId() {
		return memberOrderId;
	}
	public void setMemberOrderId(String memberOrderId) {
		this.memberOrderId = memberOrderId;
	}
	public String getMemberOrderType() {
		return memberOrderType;
	}
	public void setMemberOrderType(String memberOrderType) {
		this.memberOrderType = memberOrderType;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMsisdnlvl() {
		return msisdnlvl;
	}
	public void setMsisdnlvl(String msisdnlvl) {
		this.msisdnlvl = msisdnlvl;
	}
	public String getMnpInd() {
		return mnpInd;
	}
	public void setMnpInd(String mnpInd) {
		this.mnpInd = mnpInd;
	}
	public String getMnpNumber() {
		return mnpNumber;
	}
	public void setMnpNumber(String mnpNumber) {
		this.mnpNumber = mnpNumber;
	}
	public String getMnpTicketNum() {
		return mnpTicketNum;
	}
	public void setMnpTicketNum(String mnpTicketNum) {
		this.mnpTicketNum = mnpTicketNum;
	}
	public String getMnpCutOverDate() {
		return mnpCutOverDate;
	}
	public void setMnpCutOverDate(String mnpCutOverDate) {
		this.mnpCutOverDate = mnpCutOverDate;
	}
	public String getMnpCutOverTime() {
		return mnpCutOverTime;
	}
	public void setMnpCutOverTime(String mnpCutOverTime) {
		this.mnpCutOverTime = mnpCutOverTime;
	}
	public String getMnpRno() {
		return mnpRno;
	}
	public void setMnpRno(String mnpRno) {
		this.mnpRno = mnpRno;
	}
	public String getMnpDno() {
		return mnpDno;
	}
	public void setMnpDno(String mnpDno) {
		this.mnpDno = mnpDno;
	}
	public String getMnpCustName() {
		return mnpCustName;
	}
	public void setMnpCustName(String mnpCustName) {
		this.mnpCustName = mnpCustName;
	}
	public String getMnpDocNo() {
		return mnpDocNo;
	}
	public void setMnpDocNo(String mnpDocNo) {
		this.mnpDocNo = mnpDocNo;
	}
	public SimDTO getSim() {
		return sim;
	}
	public void setSim(SimDTO sim) {
		this.sim = sim;
	}
	public String getOcid() {
		return ocid;
	}
	public void setOcid(String ocid) {
		this.ocid = ocid;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getErrCd() {
		return errCd;
	}
	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}
	
	public String getPrePaidSimDocInd() {
		/*if(prePaidSimDocWithCert){
			prePaidSimDocInd="Y";
		}
		if(prePaidSimDocWithoutCert){
			prePaidSimDocInd="N";
		}
		*/
		return prePaidSimDocInd;
	}
	public void setPrePaidSimDocInd(String prePaidSimDocInd) {
		this.prePaidSimDocInd = prePaidSimDocInd;
	}
	/*
	public boolean isPrePaidSimDocWithCert() {
		if("Y".equals(prePaidSimDocInd)){
			prePaidSimDocWithCert = true;
		}
		return prePaidSimDocWithCert;
	}
	public void setPrePaidSimDocWithCert(boolean prePaidSimDocWithCert) {
		this.prePaidSimDocWithCert = prePaidSimDocWithCert;
	}
	public boolean isPrePaidSimDocWithoutCert() {
		if("N".equals(prePaidSimDocInd)){
			prePaidSimDocWithoutCert=true;
		}
		return prePaidSimDocWithoutCert;
	}
	public void setPrePaidSimDocWithoutCert(boolean prePaidSimDocWithoutCert) {
		this.prePaidSimDocWithoutCert = prePaidSimDocWithoutCert;
	}
	*/
	public String getSelectedSimItemId() {
		return selectedSimItemId;
	}
	public void setSelectedSimItemId(String selectedSimItemId) {
		this.selectedSimItemId = selectedSimItemId;
	}
	public List<String> getBundleVasItemList() {
		return bundleVasItemList;
	}

	public void setBundleVasItemList(List<String> bundleVasItemList) {
		this.bundleVasItemList = bundleVasItemList;
	}

	public List<String> getSelectedVasItemList() {
		return selectedVasItemList;
	}
	public void setSelectedVasItemList(List<String> selectedVasItemList) {
		this.selectedVasItemList = selectedVasItemList;
	}
	public int getCnmStatus() {
		return cnmStatus;
	}
	public void setCnmStatus(int cnmStatus) {
		this.cnmStatus = cnmStatus;
	}
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public SignoffDTO getSignDTO() {
		return signDTO;
	}

	public void setSignDTO(SignoffDTO signDTO) {
		this.signDTO = signDTO;
	}

	public String getOriginalSimICCID() {
		return originalSimICCID;
	}

	public String getOriginalMsisdn() {
		return originalMsisdn;
	}

	public void setOriginalSimICCID(String originalSimICCID) {
		this.originalSimICCID = originalSimICCID;
	}

	public void setOriginalMsisdn(String originalMsisdn) {
		this.originalMsisdn = originalMsisdn;
	}

	public String getCsimInd() {
		return csimInd;
	}

	public void setCsimInd(String csimInd) {
		this.csimInd = csimInd;
	}

	public String getCurSimIccid() {
		return curSimIccid;
	}

	public void setCurSimIccid(String curSimIccid) {
		this.curSimIccid = curSimIccid;
	}

	public String getCurFirstName() {
		return curFirstName;
	}

	public void setCurFirstName(String curFirstName) {
		this.curFirstName = curFirstName;
	}

	public String getCurLastName() {
		return curLastName;
	}

	public void setCurLastName(String curLastName) {
		this.curLastName = curLastName;
	}

	public String getCurIdDocType() {
		return curIdDocType;
	}

	public void setCurIdDocType(String curIdDocType) {
		this.curIdDocType = curIdDocType;
	}

	public String getCurIdDocNum() {
		return curIdDocNum;
	}

	public void setCurIdDocNum(String curIdDocNum) {
		this.curIdDocNum = curIdDocNum;
	}

	public String getNfcInd() {
		return nfcInd;
	}

	public void setNfcInd(String nfcInd) {
		this.nfcInd = nfcInd;
	}

	public String getNumType() {
		return numType;
	}

	public void setNumType(String numType) {
		this.numType = numType;
	}

	public String getOriginalNewMsisdn() {
		return originalNewMsisdn;
	}

	public void setOriginalNewMsisdn(String originalNewMsisdn) {
		this.originalNewMsisdn = originalNewMsisdn;
	}

	public boolean isAmendMNP() {
		return amendMNP;
	}

	public void setAmendMNP(boolean amendMNP) {
		this.amendMNP = amendMNP;
	}

	public String getSameAsCustInd() {
		return sameAsCustInd;
	}

	public void setSameAsCustInd(String sameAsCustInd) {
		if ("Y".equalsIgnoreCase(sameAsCustInd)){
			this.sameAsCust = true;
		} else {
			this.sameAsCust = false;
		}
		this.sameAsCustInd = sameAsCustInd;
	}

	public boolean isSameAsCust() {
		return sameAsCust;
	}

	public void setSameAsCust(boolean sameAsCust) {
		if (sameAsCust){
			this.sameAsCustInd = "Y";
		} else {
			this.sameAsCustInd = "N";
		}
		this.sameAsCust = sameAsCust;
	}

	public ActualUserDTO getActualUserDTO() {
		return actualUserDTO;
	}

	public void setActualUserDTO(ActualUserDTO actualUserDTO) {
		this.actualUserDTO = actualUserDTO;
	}

	public String getActualDno() {
		return actualDno;
	}

	public void setActualDno(String actualDno) {
		this.actualDno = actualDno;
	}

	public boolean isIgnorePostpaidcheckbox() {
		return ignorePostpaidcheckbox;
	}

	public void setIgnorePostpaidcheckbox(boolean ignorePostpaidcheckbox) {
		this.ignorePostpaidcheckbox = ignorePostpaidcheckbox;
	}

	public String getOpssInd() {
		return opssInd;
	}

	public void setOpssInd(String opssInd) {
		this.opssInd = opssInd;
	}

	public String getStarterPack() {
		return starterPack;
	}

	public void setStarterPack(String starterPack) {
		this.starterPack = starterPack;
	}
	
	public String getCurCheckResult() {
		return curCheckResult;
	}
	public void setCurCheckResult(String curCheckResult) {
		this.curCheckResult = curCheckResult;
	}
	public String getCurMsisdn() {
		return curMsisdn;
	}
	public void setCurMsisdn(String curMsisdn) {
		this.curMsisdn = curMsisdn;
	}
	public String getCurCheckErrMsg() {
		return curCheckErrMsg;
	}
	public void setCurCheckErrMsg(String curCheckErrMsg) {
		this.curCheckErrMsg = curCheckErrMsg;
	}

	public String getBrmChgSimInd() {
		return brmChgSimInd;
	}

	public void setBrmChgSimInd(String brmChgSimInd) {
		this.brmChgSimInd = brmChgSimInd;
	}

	public boolean isChgSimBool() {
		return chgSimBool;
	}

	public void setChgSimBool(boolean chgSimBool) {
		this.chgSimBool = chgSimBool;
	}
	
}
