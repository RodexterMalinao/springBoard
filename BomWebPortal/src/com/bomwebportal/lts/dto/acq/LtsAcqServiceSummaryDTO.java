package com.bomwebportal.lts.dto.acq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.CollectDocDto;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;

public class LtsAcqServiceSummaryDTO implements Serializable {

	private static final long serialVersionUID = -7095130420530178732L;
	
	public static final String STATUS_STATE_TOS = "TOS";
	public static final String STATUS_STATE_PENDING_ORD = "PEND";
	public static final String STATUS_STATE_APPROVAL = "APPROVAL";
	public static final String STATUS_STATE_APPROVAL_REJECTED = "APPROVAL_REJECTED";
	
	private String srvNum = null;
	private String srvType = null;
	private String title = null;
	private String name = null;
	private String companyName = null;
	private String docType = null;
	private String docNum = null;
	private String birthday = null;
	private String apptContactName = null;
	private String apptContactNum = null;
	private String apptMobileContactNum = null;
	private String custMobileContactNum = null;
	private String custFixContactNum = null;
	private String custContactEmail = null;
	private String extRelInd = null;
	private String installAddr = null;
/*	private String[] billAddrChangeInd;
	private String[] billingAddress;
	private String[] billingAddrInstantUpdateInd;*/
	private List<SummaryBillAddrDtl> summaryBillAddrDtlList;
	private boolean blacklistAddrInd;
	private String applDate = null;
	private String relatedFSA = null;
	private String lostModem = null;
	private String modemSelection = null;
	private String pcdSbOrder = null;
	private String srvReqDate = null;
	private String workQueueType = null;
	private String pendingLtsOcid = null;
	private String pendingImsOcid = null;
	private String emailBillAddress = null;
	private String imsSbOrder = null;
	private String addOnRemarks = null;
	private String custRemarks = null;
	private String orderRemarks = null;
	private String earliestSrdReasonRemarks = null;
	private String fsRemark = null;
	private String dummyDocType = null;
	private String dummyDocNum = null;
	private String memoNum = null;
	private String cancelDuplexDn = null;
	private String cancelDuplexType = null;
	private boolean fsaExtRelInd;
	
	private List<SummaryPayMtdDtl> summaryPayMtdDtlList;
	private String[] acctNum = null;
	private String racctNum = null;
/*	private String paymentMethod = null;
	private String bankAcctNum = null;
	private String bankBranchCd = null;
	private String bankCd = null;
	private String autoPayLimit = null;*/
//	private String bankApplDate = null;
/*	private String credCardType = null;
	private String credCardNum = null;
	private String credCardExpDate = null;
	private String holderIdNum = null;
	private String holderIdType = null;
	private String holderName = null;*/
	private String mirrorFsa = null;
	private String nowtvSrvType = null;
	private String modemArrangement = null;
/*	private String paymentChangeInd = null;
	private boolean thirdPtyPayment;*/
	
	private String thirdPtyName = null;
	private String thirdPtyDocType = null;
	private String thirdPtyDocId = null;
	private String thirdPtyRelation = null;
	private String thirdPtyContactNum = null;
			
	private String fixedTerm = null;
	private String installDate = null;
	private String installTime = null;
	private String prewiringDate = null;
	private String prewiringTime = null;
	private String switchDate = null;
	private String contractPeriod = null;
	
	private String srvTypeChange = null;
	private String bandwidthChange = null;
	private String technologyChange = null;
	
	private String disMode = null;
	private String collectMethod = null;
	private String esigEmailAddr = null;
	private String esigEmailLang = null;
	private String staffPlanApplicantId = null;
	private String eyeArrangement = null;
	private String fsaArrangement = null;
	private String edfNo = null;
	
	private String recontractInd = null;
	private String recontractOptCallCardInd = null;
	private String recontractOptMobIddInd = null;
	private String recontractOptFixedIddInd = null;
	private String recontractMode = null;
	private String transfereeTitle = null;
	private String transfereeName = null;
	private String transfereeDocType = null;
	private String transfereeDocNum = null;
	private String transfereeRelationship = null;
	private String transfereeContactNum = null;
	private String transfereeEmail = null;
	private String transfereeBlackListInd = null;
	private String transfereeAcctNum = null;
	
	private String transferorTitle = null;
	private String transferorName = null;
	private String transferorDocType = null;
	private String transferorDocNum = null;
	private String transferorAcctNum = null;
	
	private String statusState = null;
	
	private List<String> channelList = null;
	private List<ItemDetailSummaryDTO> srvPlanItemList = null;
	private List<ItemDetailSummaryDTO> bbRentalItemList = null;
	private List<ItemDetailSummaryDTO> peItemList = null;
	private List<ItemDetailSummaryDTO> socketItemList = null;
	private List<ItemDetailSummaryDTO> contentItemList = null;
	private List<ItemDetailSummaryDTO> moovItemList = null;
	private List<ItemDetailSummaryDTO> nowtvItemList = null;
	private List<ItemDetailSummaryDTO> nowtvSpecialItemList = null;
	private List<ItemDetailSummaryDTO> billItemList = null;
	private List<ItemDetailSummaryDTO> nowtvBillItemList = null;
	private List<ItemDetailSummaryDTO> vasEyeItemList = null;
	private List<ItemDetailSummaryDTO> vasItemList = null;
	private List<ItemDetailSummaryDTO> idd0060ItemList = null;
	private List<ItemDetailSummaryDTO> optionalPremiumItemList = null;
	private List<ItemDetailSummaryDTO> premiumItemList = null;
	private List<ItemDetailSummaryDTO> prepaymentItemList = null;
	private List<ItemDetailSummaryDTO> optOutItemList = null;
	private List<ItemDetailSummaryDTO> outLtsItemList = null;
	private List<ItemDetailSummaryDTO> outImsItemList = null;
	private List<ItemDetailSummaryDTO> profileItemList = null;
	private List<ItemDetailSummaryDTO> optAccItemList = null;
	private List<ItemDetailSummaryDTO> erChargeList = null;
	private List<ItemDetailSummaryDTO> otherChargeList = null;
	private List<ItemDetailSummaryDTO> prewiringItemList = null;
	private List<ItemDetailSummaryDTO> preinstallItemList = null;
	private List<ItemDetailSummaryDTO> ffpItemList = null;
	private List<ItemDetailSummaryDTO> smartWrtyList = null;
	private List<ItemDetailSummaryDTO> epdItemList = null;
	
	private List<AllOrdDocAssgnLtsDTO> allOrdDocAssgnsList = null;
	private List<CollectDocDto> collectDocList = null;
	
	private List<ItemDetailSummaryDTO> currentProfileItemList = null;
	private List<ItemDetailSummaryDTO> futureProfileItemList = null;
	private double currentChargeTotal = 0;
	private double futureChargeTotal = 0;
	
	private List<String> messageList = new ArrayList<String>();
	private StringBuilder promptAlertMessage = new StringBuilder();
	
	private boolean cspNewReg = false;
	private String cspMobile;
	private String cspEmail;
	private String[] ltsEmailBillAddr;
	private String paperBillCharge = null;
	private String paperBillWaiveRea = null;
	private String paperBillWaiveRemarks = null;
	private String exDirName;
	private String exDirInd;
	
    // FOR PIPB  
	private boolean isPipb = false;
	private String pipbDn = null; 
	private String pipbOperator2n = null;     
	private String pipbReuseExSocketInd = null; 
	private String pipbWaiveDnChangeInd = null;  
	private String pipbFromDiffCustInd = null;  
	private String pipbIdDocType = null;      
	private String pipbIdDocNum = null;      
	private String pipbTitle = null;        
	private String pipbName = null;            
	private String pipbCompanyName = null;     
	private String pipbFromDiffAddrInd = null;   
	private String pipbAddr = null;        
	private String pipbDuplexInd = null;       
	private String pipbDuplexAction = null;     
	private String pipbDuplexDn = null; 
	private String pipbInstallDate = null;
	private String pipbInstallTime = null;
	private String pipbSwitchDate = null;
	private String pipbReuseExSocketType = null;
	
	private String pcdSbid = null;
	private String redemMedia = null;
	private String redemptionEmail = null;
	private String redemptionSms = null;
	
	public class SummaryBillAddrDtl implements Serializable {
		
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 7903179485045069866L;
		
		
		
		private String billAddrChangeInd;
		private String billingAddress;
		private String billingAddrInstantUpdateInd;
		
		
		public String getBillAddrChangeInd() {
			return billAddrChangeInd;
		}
		public void setBillAddrChangeInd(String billAddrChangeInd) {
			this.billAddrChangeInd = billAddrChangeInd;
		}
		public String getBillingAddress() {
			return billingAddress;
		}
		public void setBillingAddress(String billingAddress) {
			this.billingAddress = billingAddress;
		}
		public String getBillingAddrInstantUpdateInd() {
			return billingAddrInstantUpdateInd;
		}
		public void setBillingAddrInstantUpdateInd(String billingAddrInstantUpdateInd) {
			this.billingAddrInstantUpdateInd = billingAddrInstantUpdateInd;
		}
				
	}
	
	
	
	public class SummaryPayMtdDtl implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 882683956484796363L;
		
		private String acctNum = null;
		private String paymentMethod = null;
		private String bankAcctNum = null;
		private String bankBranchCd = null;
		private String bankCd = null;
		private String bankApplDate = null;
		private String credCardType = null;
		private String credCardNum = null;
		private String credCardExpDate = null;
		private String holderIdNum = null;
		private String holderIdType = null;
		private String holderName = null;
		private String paymentChangeInd = null;
		private boolean thirdPtyPayment;
		private String autoPayLimit = null;
		
		
		public String getAcctNum() {
			return acctNum;
		}
		public void setAcctNum(String acctNum) {
			this.acctNum = acctNum;
		}
		public String getPaymentMethod() {
			return paymentMethod;
		}
		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}
		public String getBankAcctNum() {
			return bankAcctNum;
		}
		public void setBankAcctNum(String bankAcctNum) {
			this.bankAcctNum = bankAcctNum;
		}
		public String getBankBranchCd() {
			return bankBranchCd;
		}
		public void setBankBranchCd(String bankBranchCd) {
			this.bankBranchCd = bankBranchCd;
		}
		public String getBankCd() {
			return bankCd;
		}
		public void setBankCd(String bankCd) {
			this.bankCd = bankCd;
		}
		public String getBankApplDate() {
			return bankApplDate;
		}
		public void setBankApplDate(String bankApplDate) {
			this.bankApplDate = bankApplDate;
		}
		public String getHolderIdNum() {
			return holderIdNum;
		}
		public void setHolderIdNum(String holderIdNum) {
			this.holderIdNum = holderIdNum;
		}
		public String getHolderIdType() {
			return holderIdType;
		}
		public void setHolderIdType(String holderIdType) {
			this.holderIdType = holderIdType;
		}
		public String getHolderName() {
			return holderName;
		}
		public void setHolderName(String holderName) {
			this.holderName = holderName;
		}
		public String getCredCardType() {
			return credCardType;
		}
		public void setCredCardType(String credCardType) {
			this.credCardType = credCardType;
		}
		public String getCredCardNum() {
			return credCardNum;
		}
		public void setCredCardNum(String credCardNum) {
			this.credCardNum = credCardNum;
		}
		public String getCredCardExpDate() {
			return credCardExpDate;
		}
		public void setCredCardExpDate(String credCardExpDate) {
			this.credCardExpDate = credCardExpDate;
		}
		public String getPaymentChangeInd() {
			return paymentChangeInd;
		}
		public void setPaymentChangeInd(String paymentChangeInd) {
			this.paymentChangeInd = paymentChangeInd;
		}
		public boolean isThirdPtyPayment() {
			return thirdPtyPayment;
		}
		public void setThirdPtyPayment(boolean thirdPtyPayment) {
			this.thirdPtyPayment = thirdPtyPayment;
		}
		public String getAutoPayLimit() {
			return autoPayLimit;
		}
		public void setAutoPayLimit(String autoPayLimit) {
			this.autoPayLimit = autoPayLimit;
		}
		
		
		
	}
	
	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getSrvType() {
		return srvType;
	}

	public void setSrvType(String srvType) {
		this.srvType = srvType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getApptContactNum() {
		return apptContactNum;
	}

	public void setApptContactNum(String apptContactNum) {
		this.apptContactNum = apptContactNum;
	}

	public String getApptMobileContactNum() {
		return this.apptMobileContactNum;
	}

	public void setApptMobileContactNum(String pApptMobileContactNum) {
		this.apptMobileContactNum = pApptMobileContactNum;
	}

	public String getCustMobileContactNum() {
		return this.custMobileContactNum;
	}

	public void setCustMobileContactNum(String pCustMobileContactNum) {
		this.custMobileContactNum = pCustMobileContactNum;
	}

	public String getInstallAddr() {
		return installAddr;
	}

	public void setInstallAddr(String installAddr) {
		this.installAddr = installAddr;
	}
	
	public String getApplDate() {
		return applDate;
	}

	public void setApplDate(String applDate) {
		this.applDate = applDate;
	}

	public String getRelatedFSA() {
		return relatedFSA;
	}

	public void setRelatedFSA(String relatedFSA) {
		this.relatedFSA = relatedFSA;
	}

	public String getPcdSbOrder() {
		return pcdSbOrder;
	}

	public void setPcdSbOrder(String pcdSbOrder) {
		this.pcdSbOrder = pcdSbOrder;
	}

	public String[] getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(String[] acctNum) {
		this.acctNum = acctNum;
	}

/*	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBankAcctNum() {
		return bankAcctNum;
	}

	public void setBankAcctNum(String bankAcctNum) {
		this.bankAcctNum = bankAcctNum;
	}

	public String getBankBranchCd() {
		return bankBranchCd;
	}

	public void setBankBranchCd(String bankBranchCd) {
		this.bankBranchCd = bankBranchCd;
	}

	public String getBankCd() {
		return bankCd;
	}

	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}

	public String getCredCardType() {
		return credCardType;
	}

	public void setCredCardType(String credCardType) {
		this.credCardType = credCardType;
	}

	public String getCredCardNum() {
		return credCardNum;
	}

	public void setCredCardNum(String credCardNum) {
		this.credCardNum = credCardNum;
	}

	public String getCredCardExpDate() {
		return credCardExpDate;
	}

	public void setCredCardExpDate(String credCardExpDate) {
		this.credCardExpDate = credCardExpDate;
	}

	public String getHolderIdNum() {
		return holderIdNum;
	}

	public void setHolderIdNum(String holderIdNum) {
		this.holderIdNum = holderIdNum;
	}

	public String getHolderIdType() {
		return holderIdType;
	}

	public void setHolderIdType(String holderIdType) {
		this.holderIdType = holderIdType;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
*/
	public List<String> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<String> channelList) {
		this.channelList = channelList;
	}

	public String getFixedTerm() {
		return fixedTerm;
	}

	public void setFixedTerm(String fixedTerm) {
		this.fixedTerm = fixedTerm;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}

	public String getInstallTime() {
		return installTime;
	}

	public void setInstallTime(String installTime) {
		this.installTime = installTime;
	}

	public String getPrewiringDate() {
		return prewiringDate;
	}

	public void setPrewiringDate(String prewiringDate) {
		this.prewiringDate = prewiringDate;
	}

	public String getPrewiringTime() {
		return prewiringTime;
	}

	public void setPrewiringTime(String prewiringTime) {
		this.prewiringTime = prewiringTime;
	}

	public String getSwitchDate() {
		return switchDate;
	}

	public void setSwitchDate(String switchDate) {
		this.switchDate = switchDate;
	}

	public String getSrvReqDate() {
		return srvReqDate;
	}

	public void setSrvReqDate(String srvReqDate) {
		this.srvReqDate = srvReqDate;
	}

	public String getMirrorFsa() {
		return mirrorFsa;
	}

	public void setMirrorFsa(String mirrorFsa) {
		this.mirrorFsa = mirrorFsa;
	}

	public String getNowtvSrvType() {
		return nowtvSrvType;
	}

	public void setNowtvSrvType(String nowtvSrvType) {
		this.nowtvSrvType = nowtvSrvType;
	}

	public String getThirdPtyName() {
		return thirdPtyName;
	}

	public void setThirdPtyName(String thirdPtyName) {
		this.thirdPtyName = thirdPtyName;
	}

	public String getThirdPtyDocType() {
		return thirdPtyDocType;
	}

	public void setThirdPtyDocType(String thirdPtyDocType) {
		this.thirdPtyDocType = thirdPtyDocType;
	}

	public String getThirdPtyDocId() {
		return thirdPtyDocId;
	}

	public void setThirdPtyDocId(String thirdPtyDocId) {
		this.thirdPtyDocId = thirdPtyDocId;
	}

	public String getThirdPtyRelation() {
		return thirdPtyRelation;
	}

	public void setThirdPtyRelation(String thirdPtyRelation) {
		this.thirdPtyRelation = thirdPtyRelation;
	}

	public String getModemArrangement() {
		return modemArrangement;
	}

	public void setModemArrangement(String modemArrangement) {
		this.modemArrangement = modemArrangement;
	}

	public String getThirdPtyContactNum() {
		return thirdPtyContactNum;
	}

	public void setThirdPtyContactNum(String thirdPtyContactNum) {
		this.thirdPtyContactNum = thirdPtyContactNum;
	}

	public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

/*	public String getPaymentChangeInd() {
		return this.paymentChangeInd;
	}

	public void setPaymentChangeInd(String pPaymentChangeInd) {
		this.paymentChangeInd = pPaymentChangeInd;
	}
*/
	public List<ItemDetailSummaryDTO> getCurrentProfileItemList() {
		return this.currentProfileItemList;
	}

	public void setCurrentProfileItemList(
			List<ItemDetailSummaryDTO> pCurrentProfileItemList) {
		this.currentProfileItemList = pCurrentProfileItemList;
	}

	public List<ItemDetailSummaryDTO> getFutureProfileItemList() {
		return this.futureProfileItemList;
	}
	
	public void addChannel(String pChannel) {
		
		if (this.channelList == null) {
			this.channelList = new ArrayList<String>();
		}
		this.channelList.add(pChannel);
	}
	
	public void addSrvPlanItem(ItemDetailSummaryDTO pItem) {
		
		if (this.srvPlanItemList == null) {
			this.srvPlanItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.srvPlanItemList.add(pItem);
	}
	
	public void addBbRentalItem(ItemDetailSummaryDTO pItem) {
		
		if (this.bbRentalItemList == null) {
			this.bbRentalItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.bbRentalItemList.add(pItem);
	}
	
	public void addErChargeItem(ItemDetailSummaryDTO pItem) {
		
		if (this.erChargeList == null) {
			this.erChargeList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.erChargeList.add(pItem);
	}
	
	public void addOtherChargeItem(ItemDetailSummaryDTO pItem) {
		
		if (this.otherChargeList == null) {
			this.otherChargeList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.otherChargeList.add(pItem);
	}
	
    public void addPrewiringItem(ItemDetailSummaryDTO pItem) {
		
		if (this.prewiringItemList == null) {
			this.prewiringItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.prewiringItemList.add(pItem);
	}
	
    public void addPreInstallItem(ItemDetailSummaryDTO pItem) {
		
		if (this.preinstallItemList == null) {
			this.preinstallItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.preinstallItemList.add(pItem);
	}
    
	public void addPeItem(ItemDetailSummaryDTO pItem) {
		
		if (this.peItemList == null) {
			this.peItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.peItemList.add(pItem);
	}
	
	public void addOptAccItem(ItemDetailSummaryDTO pItem) {
		
		if (this.optAccItemList == null) {
			this.optAccItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.optAccItemList.add(pItem);
	}
	
	public void addSocketItem(ItemDetailSummaryDTO pItem) {
		
		if (this.socketItemList == null) {
			this.socketItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.socketItemList.add(pItem);
	}
	
	public void addContentItem(ItemDetailSummaryDTO pItem) {
		
		if (this.contentItemList == null) {
			this.contentItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.contentItemList.add(pItem);
	}
	
	public void addMoovItem(ItemDetailSummaryDTO pItem) {
		
		if (this.moovItemList == null) {
			this.moovItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.moovItemList.add(pItem);
	}
	
	public void addNowtvItem(ItemDetailSummaryDTO pItem) {
		
		if (this.nowtvItemList == null) {
			this.nowtvItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.nowtvItemList.add(pItem);
	}
	
	public void addNowtvSpecialItem(ItemDetailSummaryDTO pItem) {
		
		if (this.nowtvSpecialItemList == null) {
			this.nowtvSpecialItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.nowtvSpecialItemList.add(pItem);
	}
	
	public void addBillItem(ItemDetailSummaryDTO pItem) {
		
		if (this.billItemList == null) {
			this.billItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.billItemList.add(pItem);
	}

	public void addNowtvBillItem(ItemDetailSummaryDTO pItem) {
		
		if (this.nowtvBillItemList == null) {
			this.nowtvBillItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.nowtvBillItemList.add(pItem);
	}
	
	public void addVasEyeItem(ItemDetailSummaryDTO pItem) {
		
		if (this.vasEyeItemList == null) {
			this.vasEyeItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.vasEyeItemList.add(pItem);
	}
	
	public void addVasItem(ItemDetailSummaryDTO pItem) {
		
		if (this.vasItemList == null) {
			this.vasItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.vasItemList.add(pItem);
	}
	
	public void addIdd0060Item(ItemDetailSummaryDTO pItem) {
		
		if (this.idd0060ItemList == null) {
			this.idd0060ItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.idd0060ItemList.add(pItem);
	}

	public void addOptionalPremiumItem(ItemDetailSummaryDTO pItem) {
		
		if (this.optionalPremiumItemList == null) {
			this.optionalPremiumItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.optionalPremiumItemList.add(pItem);
	}
	
	public void addPremiumItem(ItemDetailSummaryDTO pItem) {
		
		if (this.premiumItemList == null) {
			this.premiumItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.premiumItemList.add(pItem);
	}

	public void addPrepaymentItem(ItemDetailSummaryDTO pItem) {
		
		if (this.prepaymentItemList == null) {
			this.prepaymentItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.prepaymentItemList.add(pItem);
	}
	
    public void addFfpItem(ItemDetailSummaryDTO pItem) {
		
		if (this.ffpItemList == null) {
			this.ffpItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.ffpItemList.add(pItem);
	}
    
	public void addSmartWrtyItem(ItemDetailSummaryDTO pItem){
		if (this.smartWrtyList == null) {
			this.smartWrtyList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.smartWrtyList.add(pItem);
	}
	
	public void addOptOutItem(ItemDetailSummaryDTO pItem) {
		
		if (this.optOutItemList == null) {
			this.optOutItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.optOutItemList.add(pItem);
	}
	
	public void addOutLtsItem(ItemDetailSummaryDTO pItem) {
		
		if (this.outLtsItemList == null) {
			this.outLtsItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.outLtsItemList.add(pItem);
	}
	
	public void addOutImsItem(ItemDetailSummaryDTO pItem) {
		
		if (this.outImsItemList == null) {
			this.outImsItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.outImsItemList.add(pItem);
	}
	
	public void addProfileItem(ItemDetailSummaryDTO pItem) {
		
		if (this.profileItemList == null) {
			this.profileItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.profileItemList.add(pItem);
	}
	
	public void addEpdItem(ItemDetailSummaryDTO pItem){
		if (this.epdItemList == null) {
			this.epdItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		this.epdItemList.add(pItem);
	}


	public void addFutureProfileItem(ItemDetailSummaryDTO pItemSummary) {
		
		if (this.getFutureProfileItemList() == null) {
			 this.futureProfileItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		double amount;
		
		if (StringUtils.isNotEmpty(pItemSummary.getRecurrentAmt()) && (amount = Double.parseDouble(pItemSummary.getRecurrentAmt())) > 0 && pItemSummary.isPaidVas()) {
			this.futureProfileItemList.add(pItemSummary);
			this.futureChargeTotal += amount;
		} 
	}
	
	public void addCurrentProfileItem(ItemDetailSummaryDTO pItemSummary) {
		
		if (this.getCurrentProfileItemList() == null) {
			this.currentProfileItemList = new ArrayList<ItemDetailSummaryDTO>();
		}
		double amount;
		
		if (StringUtils.isNotEmpty(pItemSummary.getRecurrentAmt()) && (amount = Double.parseDouble(pItemSummary.getRecurrentAmt())) > 0 && pItemSummary.isPaidVas()) {
			this.currentProfileItemList.add(pItemSummary);
			this.currentChargeTotal += amount;
		}
	}

	public void setFutureProfileItemList(List<ItemDetailSummaryDTO> pFutureProfileItemList) {
		this.futureProfileItemList = pFutureProfileItemList;
	}

	public double getCurrentChargeTotal() {
		return this.currentChargeTotal;
	}

	public void setCurrentChargeTotal(double pCurrentChargeTotal) {
		this.currentChargeTotal = pCurrentChargeTotal;
	}

	public double getFutureChargeTotal() {
		return this.futureChargeTotal;
	}

	public void setFutureChargeTotal(double pFutureChargeTotal) {
		this.futureChargeTotal = pFutureChargeTotal;
	}

	public String getExtRelInd() {
		return this.extRelInd;
	}

	public void setExtRelInd(String pExtRelInd) {
		this.extRelInd = pExtRelInd;
	}

/*	public String getAutoPayLimit() {
		return this.autoPayLimit;
	}

	public void setAutoPayLimit(String pAutoPayLimit) {
		this.autoPayLimit = pAutoPayLimit;
	}*/
/*
	public String getBankApplDate() {
		return this.bankApplDate;
	}

	public void setBankApplDate(String pBankApplDate) {
		this.bankApplDate = pBankApplDate;
	}
*/
	public String getWorkQueueType() {
		return this.workQueueType;
	}

	public void setWorkQueueType(String pWorkQueueType) {
		this.workQueueType = pWorkQueueType;
	}

	public List<ItemDetailSummaryDTO> getSrvPlanItemList() {
		return this.srvPlanItemList;
	}

	public void setSrvPlanItemList(List<ItemDetailSummaryDTO> pSrvPlanItemList) {
		this.srvPlanItemList = pSrvPlanItemList;
	}

	public List<ItemDetailSummaryDTO> getBbRentalItemList() {
		return this.bbRentalItemList;
	}

	public void setBbRentalItemList(List<ItemDetailSummaryDTO> pBbRentalItemList) {
		this.bbRentalItemList = pBbRentalItemList;
	}
	
	public List<ItemDetailSummaryDTO> getErChargeList() {
		return this.erChargeList;
	}

	public void setErChargeList(List<ItemDetailSummaryDTO> pErChargeList) {
		this.erChargeList = pErChargeList;
	}
	
	public List<ItemDetailSummaryDTO> getOtherChargeList() {
		return this.otherChargeList;
	}

	public void setOtherChargeList(List<ItemDetailSummaryDTO> pOtherChargeList) {
		this.otherChargeList = pOtherChargeList;
	}

	public List<ItemDetailSummaryDTO> getPeItemList() {
		return this.peItemList;
	}

	public void setPeItemList(List<ItemDetailSummaryDTO> pPeItemList) {
		this.peItemList = pPeItemList;
	}

	public List<ItemDetailSummaryDTO> getSocketItemList() {
		return this.socketItemList;
	}

	public void setSocketItemList(List<ItemDetailSummaryDTO> pSocketItemList) {
		this.socketItemList = pSocketItemList;
	}

	public List<ItemDetailSummaryDTO> getContentItemList() {
		return this.contentItemList;
	}

	public void setContentItemList(List<ItemDetailSummaryDTO> pContentItemList) {
		this.contentItemList = pContentItemList;
	}

	public List<ItemDetailSummaryDTO> getMoovItemList() {
		return this.moovItemList;
	}

	public void setMoovItemList(List<ItemDetailSummaryDTO> pMoovItemList) {
		this.moovItemList = pMoovItemList;
	}

	public List<ItemDetailSummaryDTO> getNowtvItemList() {
		return this.nowtvItemList;
	}

	public void setNowtvItemList(List<ItemDetailSummaryDTO> pNowtvItemList) {
		this.nowtvItemList = pNowtvItemList;
	}

	public List<ItemDetailSummaryDTO> getBillItemList() {
		return this.billItemList;
	}

	public void setBillItemList(List<ItemDetailSummaryDTO> pBillItemList) {
		this.billItemList = pBillItemList;
	}

	public List<ItemDetailSummaryDTO> getNowtvBillItemList() {
		return this.nowtvBillItemList;
	}

	public void setNowtvBillItemList(List<ItemDetailSummaryDTO> pNowtvBillItemList) {
		this.nowtvBillItemList = pNowtvBillItemList;
	}

	public List<ItemDetailSummaryDTO> getVasEyeItemList() {
		return this.vasEyeItemList;
	}

	public void setVasEyeItemList(List<ItemDetailSummaryDTO> pVasEyeItemList) {
		this.vasEyeItemList = pVasEyeItemList;
	}

	public List<ItemDetailSummaryDTO> getVasItemList() {
		return this.vasItemList;
	}

	public void setVasItemList(List<ItemDetailSummaryDTO> pVasItemList) {
		this.vasItemList = pVasItemList;
	}

	public List<ItemDetailSummaryDTO> getIdd0060ItemList() {
		return this.idd0060ItemList;
	}

	public void setIdd0060ItemList(List<ItemDetailSummaryDTO> pIdd0060ItemList) {
		this.idd0060ItemList = pIdd0060ItemList;
	}

	public List<ItemDetailSummaryDTO> getOptionalPremiumItemList() {
		return this.optionalPremiumItemList;
	}

	public void setOptionalPremiumItemList(
			List<ItemDetailSummaryDTO> pOptionalPremiumItemList) {
		this.optionalPremiumItemList = pOptionalPremiumItemList;
	}

	public List<ItemDetailSummaryDTO> getPremiumItemList() {
		return this.premiumItemList;
	}

	public void setPremiumItemList(List<ItemDetailSummaryDTO> pPremiumItemList) {
		this.premiumItemList = pPremiumItemList;
	}

	public List<ItemDetailSummaryDTO> getSmartWrtyList() {
		return smartWrtyList;
	}

	public void setSmartWrtyList(List<ItemDetailSummaryDTO> smartWrtyList) {
		this.smartWrtyList = smartWrtyList;
	}

	public List<ItemDetailSummaryDTO> getPrepaymentItemList() {
		return this.prepaymentItemList;
	}

	public void setPrepaymentItemList(List<ItemDetailSummaryDTO> pPrepaymentItemList) {
		this.prepaymentItemList = pPrepaymentItemList;
	}

	public List<ItemDetailSummaryDTO> getOutLtsItemList() {
		return this.outLtsItemList;
	}

	public void setOutLtsItemList(List<ItemDetailSummaryDTO> pOutItemList) {
		this.outLtsItemList = pOutItemList;
	}
	
	public List<ItemDetailSummaryDTO> getOutImsItemList() {
		return this.outImsItemList;
	}

	public void setOutImsItemList(List<ItemDetailSummaryDTO> pOutItemList) {
		this.outImsItemList = pOutItemList;
	}

	public List<ItemDetailSummaryDTO> getProfileItemList() {
		return this.profileItemList;
	}

	public void setProfileItemList(List<ItemDetailSummaryDTO> pProfileItemList) {
		this.profileItemList = pProfileItemList;
	}

	public List<ItemDetailSummaryDTO> getPrewiringItemList() {
		return prewiringItemList;
	}

	public void setPrewiringItemList(List<ItemDetailSummaryDTO> pPrewiringItemList) {
		this.prewiringItemList = pPrewiringItemList;
	}

	public List<ItemDetailSummaryDTO> getPreinstallItemList() {
		return preinstallItemList;
	}

	public void setPreinstallItemList(List<ItemDetailSummaryDTO> preinstallItemList) {
		this.preinstallItemList = preinstallItemList;
	}

	public List<ItemDetailSummaryDTO> getFfpItemList() {
		return ffpItemList;
	}

	public void setFfpItemList(List<ItemDetailSummaryDTO> ffpItemList) {
		this.ffpItemList = ffpItemList;
	}

	public String getCustFixContactNum() {
		return this.custFixContactNum;
	}

	public void setCustFixContactNum(String pCustFixContactNum) {
		this.custFixContactNum = pCustFixContactNum;
	}

	public String getPendingLtsOcid() {
		return this.pendingLtsOcid;
	}

	public void setPendingLtsOcid(String pPendingLtsOcid) {
		this.pendingLtsOcid = pPendingLtsOcid;
	}

	public String getPendingImsOcid() {
		return this.pendingImsOcid;
	}

	public void setPendingImsOcid(String pPendingImsOcid) {
		this.pendingImsOcid = pPendingImsOcid;
	}

	public List<ItemDetailSummaryDTO> getNowtvSpecialItemList() {
		return this.nowtvSpecialItemList;
	}

	public void setNowtvSpecialItemList(
			List<ItemDetailSummaryDTO> pNowtvSpecialItemList) {
		this.nowtvSpecialItemList = pNowtvSpecialItemList;
	}

	public String getEmailBillAddress() {
		return this.emailBillAddress;
	}

	public void setEmailBillAddress(String pEmailBillAddress) {
		this.emailBillAddress = pEmailBillAddress;
	}

	public String getImsSbOrder() {
		return this.imsSbOrder;
	}

	public void setImsSbOrder(String pImsSbOrder) {
		this.imsSbOrder = pImsSbOrder;
	}

	public String getAddOnRemarks() {
		return this.addOnRemarks;
	}

	public void setAddOnRemarks(String pAddOnRemarks) {
		this.addOnRemarks = pAddOnRemarks;
	}

	public String getApptContactName() {
		return this.apptContactName;
	}

	public void setApptContactName(String pApptContactName) {
		this.apptContactName = pApptContactName;
	}

	public String getCustRemarks() {
		return this.custRemarks;
	}

	public void setCustRemarks(String pCustRemarks) {
		this.custRemarks = pCustRemarks;
	}

	public String getOrderRemarks() {
		return this.orderRemarks;
	}

	public void setOrderRemarks(String pOrderRemarks) {
		this.orderRemarks = pOrderRemarks;
	}

	public List<ItemDetailSummaryDTO> getOptOutItemList() {
		return this.optOutItemList;
	}

	public void setOptOutItemList(List<ItemDetailSummaryDTO> pOptOutItemList) {
		this.optOutItemList = pOptOutItemList;
	}

	public String getDummyDocType() {
		return this.dummyDocType;
	}

	public void setDummyDocType(String pDummyDocType) {
		this.dummyDocType = pDummyDocType;
	}

	public String getDummyDocNum() {
		return this.dummyDocNum;
	}

	public void setDummyDocNum(String pDummyDocNum) {
		this.dummyDocNum = pDummyDocNum;
	}

	public String getStatusState() {
		return this.statusState;
	}

	public void setStatusState(String pStatusState) {
		this.statusState = pStatusState;
	}

	public List<String> getMessageList() {
		return this.messageList;
	}

	public void setMessageList(List<String> pMessageList) {
		this.messageList = pMessageList;
	}

	public void appendMessage(String pMessage) {
		
		if (StringUtils.isNotEmpty(pMessage)) {
			this.messageList.add(pMessage);	
		}
	}
	
	public void clearMessage() {
		this.messageList = new ArrayList<String>();
	}

	public String getMemoNum() {
		return this.memoNum;
	}

	public void setMemoNum(String pMemoNum) {
		this.memoNum = pMemoNum;
	}

	public String getEarliestSrdReasonRemarks() {
		return this.earliestSrdReasonRemarks;
	}

	public void setEarliestSrdReasonRemarks(String pEarliestSrdReasonRemarks) {
		this.earliestSrdReasonRemarks = pEarliestSrdReasonRemarks;
	}

	public String getCancelDuplexDn() {
		return this.cancelDuplexDn;
	}

	public void setCancelDuplexDn(String pCancelDuplexDn) {
		this.cancelDuplexDn = pCancelDuplexDn;
	}

	public String getCancelDuplexType() {
		return this.cancelDuplexType;
	}

	public void setCancelDuplexType(String pCancelDuplexType) {
		this.cancelDuplexType = pCancelDuplexType;
	}

	public boolean getBlacklistAddrInd() {
		return this.blacklistAddrInd;
	}

	public void setBlacklistAddrInd(boolean pBlacklistAddrInd) {
		this.blacklistAddrInd = pBlacklistAddrInd;
	}

	public boolean getFsaExtRelInd() {
		return this.fsaExtRelInd;
	}

	public void setFsaExtRelInd(boolean pFsaExtRelInd) {
		this.fsaExtRelInd = pFsaExtRelInd;
	}

/*	public boolean getThirdPtyPayment() {
		return this.thirdPtyPayment;
	}

	public void setThirdPtyPayment(boolean pThirdPtyPayment) {
		this.thirdPtyPayment = pThirdPtyPayment;
	}
*/
	public List<ItemDetailSummaryDTO> getOptAccItemList() {
		return this.optAccItemList;
	}

	public void setOptAccItemList(List<ItemDetailSummaryDTO> pOptAccItemList) {
		this.optAccItemList = pOptAccItemList;
	}

	public String getFsRemark() {
		return fsRemark;
	}

	public void setFsRemark(String fsRemark) {
		this.fsRemark = fsRemark;
	}

	public String getPromptAlertMessage() {
		return this.promptAlertMessage.toString();
	}

	public void appendPromptAlertMessage(String promptAlertMessage) {
		this.promptAlertMessage.append("\r\n");
		this.promptAlertMessage.append(promptAlertMessage);
	}

	public String getSrvTypeChange() {
		return srvTypeChange;
	}

	public void setSrvTypeChange(String srvTypeChange) {
		this.srvTypeChange = srvTypeChange;
	}

	public String getBandwidthChange() {
		return bandwidthChange;
	}

	public void setBandwidthChange(String bandwidthChange) {
		this.bandwidthChange = bandwidthChange;
	}

	public String getTechnologyChange() {
		return technologyChange;
	}

	public void setTechnologyChange(String technologyChange) {
		this.technologyChange = technologyChange;
	}

	public String getCollectMethod() {
		return collectMethod;
	}

	public void setCollectMethod(String collectMethod) {
		this.collectMethod = collectMethod;
	}

	public String getEsigEmailAddr() {
		return esigEmailAddr;
	}

	public void setEsigEmailAddr(String esigEmailAddr) {
		this.esigEmailAddr = esigEmailAddr;
	}

	public String getEsigEmailLang() {
		return esigEmailLang;
	}

	public void setEsigEmailLang(String esigEmailLang) {
		this.esigEmailLang = esigEmailLang;
	}

	public List<AllOrdDocAssgnLtsDTO> getAllOrdDocAssgnsList() {
		return allOrdDocAssgnsList;
	}

	public void setAllOrdDocAssgnsList(
			List<AllOrdDocAssgnLtsDTO> allOrdDocAssgnsList) {
		this.allOrdDocAssgnsList = allOrdDocAssgnsList;
	}

	public String getDisMode() {
		return disMode;
	}

	public void setDisMode(String disMode) {
		this.disMode = disMode;
	}

	public boolean isCspNewReg() {
		return cspNewReg;
	}

	public void setCspNewReg(boolean cspNewReg) {
		this.cspNewReg = cspNewReg;
	}
	
	public String getCspMobile() {
		return cspMobile;
	}

	public void setCspMobile(String cspMobile) {
		this.cspMobile = cspMobile;
	}

	public String getCspEmail() {
		return cspEmail;
	}

	public void setCspEmail(String cspEmail) {
		this.cspEmail = cspEmail;
	}

	public List<CollectDocDto> getCollectDocList() {
		return collectDocList;
	}

	public void setCollectDocList(List<CollectDocDto> collectDocList) {
		this.collectDocList = collectDocList;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStaffPlanApplicantId() {
		return staffPlanApplicantId;
	}

	public void setStaffPlanApplicantId(String staffPlanApplicantId) {
		this.staffPlanApplicantId = staffPlanApplicantId;
	}

	public String getEyeArrangement() {
		return eyeArrangement;
	}

	public void setEyeArrangement(String eyeArrangement) {
		this.eyeArrangement = eyeArrangement;
	}

	public String getFsaArrangement() {
		return fsaArrangement;
	}

	public void setFsaArrangement(String fsaArrangement) {
		this.fsaArrangement = fsaArrangement;
	}

	public String getEdfNo() {
		return edfNo;
	}

	public void setEdfNo(String edfNo) {
		this.edfNo = edfNo;
	}

	public String getRecontractInd() {
		return recontractInd;
	}

	public void setRecontractInd(String recontractInd) {
		this.recontractInd = recontractInd;
	}

	public String getRecontractOptCallCardInd() {
		return recontractOptCallCardInd;
	}

	public void setRecontractOptCallCardInd(String recontractOptCallCardInd) {
		this.recontractOptCallCardInd = recontractOptCallCardInd;
	}

	public String getRecontractOptMobIddInd() {
		return recontractOptMobIddInd;
	}

	public void setRecontractOptMobIddInd(String recontractOptMobIddInd) {
		this.recontractOptMobIddInd = recontractOptMobIddInd;
	}

	public String getRecontractOptFixedIddInd() {
		return recontractOptFixedIddInd;
	}

	public void setRecontractOptFixedIddInd(String recontractOptFixedIddInd) {
		this.recontractOptFixedIddInd = recontractOptFixedIddInd;
	}

	public String getTransfereeTitle() {
		return transfereeTitle;
	}

	public void setTransfereeTitle(String transfereeTitle) {
		this.transfereeTitle = transfereeTitle;
	}

	public String getTransfereeName() {
		return transfereeName;
	}

	public void setTransfereeName(String transfereeName) {
		this.transfereeName = transfereeName;
	}

	public String getTransfereeDocType() {
		return transfereeDocType;
	}

	public void setTransfereeDocType(String transfereeDocType) {
		this.transfereeDocType = transfereeDocType;
	}

	public String getTransfereeDocNum() {
		return transfereeDocNum;
	}

	public void setTransfereeDocNum(String transfereeDocNum) {
		this.transfereeDocNum = transfereeDocNum;
	}

	public String getTransfereeRelationship() {
		return transfereeRelationship;
	}

	public void setTransfereeRelationship(String transfereeRelationship) {
		this.transfereeRelationship = transfereeRelationship;
	}

	public String getTransfereeContactNum() {
		return transfereeContactNum;
	}

	public void setTransfereeContactNum(String transfereeContactNum) {
		this.transfereeContactNum = transfereeContactNum;
	}

	public String getTransfereeEmail() {
		return transfereeEmail;
	}

	public void setTransfereeEmail(String transfereeEmail) {
		this.transfereeEmail = transfereeEmail;
	}

	public String getTransfereeBlackListInd() {
		return transfereeBlackListInd;
	}

	public void setTransfereeBlackListInd(String transfereeBlackListInd) {
		this.transfereeBlackListInd = transfereeBlackListInd;
	}

	public String getTransfereeAcctNum() {
		return transfereeAcctNum;
	}

	public void setTransfereeAcctNum(String transfereeAcctNum) {
		this.transfereeAcctNum = transfereeAcctNum;
	}

	public String getTransferorTitle() {
		return transferorTitle;
	}

	public void setTransferorTitle(String transferorTitle) {
		this.transferorTitle = transferorTitle;
	}

	public String getTransferorName() {
		return transferorName;
	}

	public void setTransferorName(String transferorName) {
		this.transferorName = transferorName;
	}

	public String getTransferorDocType() {
		return transferorDocType;
	}

	public void setTransferorDocType(String transferorDocType) {
		this.transferorDocType = transferorDocType;
	}

	public String getTransferorDocNum() {
		return transferorDocNum;
	}

	public void setTransferorDocNum(String transferorDocNum) {
		this.transferorDocNum = transferorDocNum;
	}

	public String getTransferorAcctNum() {
		return transferorAcctNum;
	}

	public void setTransferorAcctNum(String transferorAcctNum) {
		this.transferorAcctNum = transferorAcctNum;
	}

	public String getRecontractMode() {
		return recontractMode;
	}

	public void setRecontractMode(String recontractMode) {
		this.recontractMode = recontractMode;
	}

	public List<SummaryBillAddrDtl> getSummaryBillAddrDtlList() {
		return summaryBillAddrDtlList;
	}

	public void setSummaryBillAddrDtlList(
			List<SummaryBillAddrDtl> summaryBillAddrDtlList) {
		this.summaryBillAddrDtlList = summaryBillAddrDtlList;
	}

	public List<SummaryPayMtdDtl> getSummaryPayMtdDtlList() {
		return summaryPayMtdDtlList;
	}

	public void setSummaryPayMtdDtlList(List<SummaryPayMtdDtl> summaryPayMtdDtlList) {
		this.summaryPayMtdDtlList = summaryPayMtdDtlList;
	}

	public String getRacctNum() {
		return racctNum;
	}

	public void setRacctNum(String racctNum) {
		this.racctNum = racctNum;
	}

	public String[] getLtsEmailBillAddr() {
		return ltsEmailBillAddr;
	}

	public void setLtsEmailBillAddr(String[] ltsEmailBillAddr) {
		this.ltsEmailBillAddr = ltsEmailBillAddr;
	}

	public boolean isPipb() {
		return isPipb;
	}

	public void setPipb(boolean isPipb) {
		this.isPipb = isPipb;
	}

	public String getPipbOperator2n() {
		return pipbOperator2n;
	}

	public void setPipbOperator2n(String pipbOperator2n) {
		this.pipbOperator2n = pipbOperator2n;
	}

	public String getPipbReuseExSocketInd() {
		return pipbReuseExSocketInd;
	}

	public void setPipbReuseExSocketInd(String pipbReuseExSocketInd) {
		this.pipbReuseExSocketInd = pipbReuseExSocketInd;
	}

	public String getPipbWaiveDnChangeInd() {
		return pipbWaiveDnChangeInd;
	}

	public void setPipbWaiveDnChangeInd(String pipbWaiveDnChangeInd) {
		this.pipbWaiveDnChangeInd = pipbWaiveDnChangeInd;
	}

	public String getPipbFromDiffCustInd() {
		return pipbFromDiffCustInd;
	}

	public void setPipbFromDiffCustInd(String pipbFromDiffCustInd) {
		this.pipbFromDiffCustInd = pipbFromDiffCustInd;
	}

	public String getPipbIdDocType() {
		return pipbIdDocType;
	}

	public void setPipbIdDocType(String pipbIdDocType) {
		this.pipbIdDocType = pipbIdDocType;
	}

	public String getPipbIdDocNum() {
		return pipbIdDocNum;
	}

	public void setPipbIdDocNum(String pipbIdDocNum) {
		this.pipbIdDocNum = pipbIdDocNum;
	}

	public String getPipbTitle() {
		return pipbTitle;
	}

	public void setPipbTitle(String pipbTitle) {
		this.pipbTitle = pipbTitle;
	}

	public String getPipbName() {
		return pipbName;
	}

	public void setPipbName(String pipbName) {
		this.pipbName = pipbName;
	}

	public String getPipbCompanyName() {
		return pipbCompanyName;
	}

	public void setPipbCompanyName(String pipbCompanyName) {
		this.pipbCompanyName = pipbCompanyName;
	}

	public String getPipbFromDiffAddrInd() {
		return pipbFromDiffAddrInd;
	}

	public void setPipbFromDiffAddrInd(String pipbFromDiffAddrInd) {
		this.pipbFromDiffAddrInd = pipbFromDiffAddrInd;
	}

	public String getPipbAddr() {
		return pipbAddr;
	}

	public void setPipbAddr(String pipbAddr) {
		this.pipbAddr = pipbAddr;
	}

	public String getPipbDuplexInd() {
		return pipbDuplexInd;
	}

	public void setPipbDuplexInd(String pipbDuplexInd) {
		this.pipbDuplexInd = pipbDuplexInd;
	}

	public String getPipbDuplexAction() {
		return pipbDuplexAction;
	}

	public void setPipbDuplexAction(String pipbDuplexAction) {
		this.pipbDuplexAction = pipbDuplexAction;
	}

	public String getPipbDuplexDn() {
		return pipbDuplexDn;
	}

	public void setPipbDuplexDn(String pipbDuplexDn) {
		this.pipbDuplexDn = pipbDuplexDn;
	}

	public String getPipbDn() {
		return pipbDn;
	}

	public void setPipbDn(String pipbDn) {
		this.pipbDn = pipbDn;
	}

	public void setPromptAlertMessage(StringBuilder promptAlertMessage) {
		this.promptAlertMessage = promptAlertMessage;
	}

	public String getExDirName() {
		return exDirName;
	}

	public void setExDirName(String exDirName) {
		this.exDirName = exDirName;
	}

	public String getPipbInstallDate() {
		return pipbInstallDate;
	}

	public void setPipbInstallDate(String pipbInstallDate) {
		this.pipbInstallDate = pipbInstallDate;
	}

	public String getPipbInstallTime() {
		return pipbInstallTime;
	}

	public void setPipbInstallTime(String pipbInstallTime) {
		this.pipbInstallTime = pipbInstallTime;
	}

	public String getPipbSwitchDate() {
		return pipbSwitchDate;
	}

	public void setPipbSwitchDate(String pipbSwitchDate) {
		this.pipbSwitchDate = pipbSwitchDate;
	}

	public String getPipbReuseExSocketType() {
		return pipbReuseExSocketType;
	}

	public void setPipbReuseExSocketType(String pipbReuseExSocketType) {
		this.pipbReuseExSocketType = pipbReuseExSocketType;
	}

	public String getExDirInd() {
		return exDirInd;
	}

	public void setExDirInd(String exDirInd) {
		this.exDirInd = exDirInd;
	}
	
	public String getPaperBillCharge() {
		return paperBillCharge;
	}

	public void setPaperBillCharge(String paperBillCharge) {
		this.paperBillCharge = paperBillCharge;
	}

	public String getPaperBillWaiveRea() {
		return paperBillWaiveRea;
	}

	public void setPaperBillWaiveRea(String paperBillWaiveRea) {
		this.paperBillWaiveRea = paperBillWaiveRea;
	}

	public String getPcdSbid() {
		return pcdSbid;
	}

	public void setPcdSbid(String pcdSbid) {
		this.pcdSbid = pcdSbid;
	}

	public String getRedemMedia() {
		return redemMedia;
	}

	public void setRedemMedia(String redemMedia) {
		this.redemMedia = redemMedia;
	}

	public String getRedemptionEmail() {
		return redemptionEmail;
	}

	public void setRedemptionEmail(String redemptionEmail) {
		this.redemptionEmail = redemptionEmail;
	}

	public String getRedemptionSms() {
		return redemptionSms;
	}

	public void setRedemptionSms(String redemptionSms) {
		this.redemptionSms = redemptionSms;
	}
	
	public String getPaperBillWaiveRemarks() {
		return paperBillWaiveRemarks;
	}

	public void setPaperBillWaiveRemarks(String paperBillWaiveRemarks) {
		this.paperBillWaiveRemarks = paperBillWaiveRemarks;
	}

	public String getLostModem() {
		return lostModem;
	}

	public void setLostModem(String lostModem) {
		this.lostModem = lostModem;
	}

	public String getModemSelection() {
		return modemSelection;
	}

	public void setModemSelection(String modemSelection) {
		this.modemSelection = modemSelection;
	}

	public String getCustContactEmail() {
		return custContactEmail;
	}

	public void setCustContactEmail(String custContactEmail) {
		this.custContactEmail = custContactEmail;
	}

	public List<ItemDetailSummaryDTO> getEpdItemList() {
		return epdItemList;
	}

	public void setEpdItemList(List<ItemDetailSummaryDTO> epdItemList) {
		this.epdItemList = epdItemList;
	}

	
}
