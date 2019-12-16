package com.bomwebportal.dto;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import com.bomwebportal.dto.report.ReportDTO;

public class IGuardDTO extends ReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7955557828178776405L;
	
	public static final String JASPER_TEMPLATE_LDS = "M2IGuardCustInfo_LDS";
	public static final String JASPER_TEMPLATE_TNC_LDS = "M2IGuardTandC_LDS";
	
	public static final String JASPER_TEMPLATE_AD = "M2IGuardCustInfo_AD";
	public static final String JASPER_TEMPLATE_TNC_AD = "M2IGuardTandC_AD";
	
	public static final String JASPER_TEMPLATE_UAD = "M2IGuardCustInfo_UAD";
	private String orderId;
	// Title
	private String copy;
	private String phoneProtectorLogo;
	private String directLogo;
	private String serialNo;
	
	// Detail 2
	private String custLastName;
	private String custFirstName;
	private String title;
	private String idDocNum;
	private String dob;

	private String flat;
	private String floor;
	private String building;
	private String street;
	private String section;
	private String district;
	private String region;
	
	private String email;
	private String contactNo;
	private String otherContactNo;
	
	// Detail 4
	private String handsetDeviceDescription;
	
	private String imei;
	
	private String contractPeriod;
	private String tgtEffDate;
	
	private String msisdn;
	
	private String hsPurchasePrice;
	
	private String ldsSrvPlanFee;
	private String adSrvPlanFee;
	
	private String shopCd;
	
	private String salesCd;
	private List<String> iGuardList;
	
	// Detail 6
	private InputStream custSignature;

	private String hsReceivedDate;
	
	//added by Gary Lai 121011
	private String iGuardLDSPlanCode;
	private String iGuardADPlanCode;
	
	private boolean privacyInd10011; // attb_id (10011)
	private boolean privacyInd10012; // attb_id (10012)
	
	private String privacyInd99992;
	private String privacyInd99993;
	private String privacyInd99994;
	private String brand1010Ind;
	
	public List<String> getiGuardList() {
		return iGuardList;
	}
	public void setiGuardList(List<String> iGuardList) {
		this.iGuardList = iGuardList;
	}
	public String getCustLastName() {
		return custLastName;
	}
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}
	public String getCustFirstName() {
		return custFirstName;
	}
	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	/**
	 * Format: DD/MM/YYYY<br>
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}
	/**
	 * Format: DD/MM/YYYY<br>
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
	/**
	 * Refer to OrderDAO - getHandsetDeviceDescription<br>
	 * @return the handsetDeviceDescription
	 */
	public String getHandsetDeviceDescription() {
		return handsetDeviceDescription;
	}
	/**
	 * Refer to OrderDAO - getHandsetDeviceDescription<br>
	 * @param handsetDeviceDescription the handsetDeviceDescription to set
	 */
	public void setHandsetDeviceDescription(String handsetDeviceDescription) {
		this.handsetDeviceDescription = handsetDeviceDescription;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	/**
	 * Logic:<br>
	 * New Num = SRD<br>
	 * New Num + MNP = SRD<br>
	 * MNP = Cutover Date<br>
	 * @return the tgtEffDate
	 */
	public String getTgtEffDate() {
		return tgtEffDate;
	}
	/**
	 * Logic:<br>
	 * New Num = SRD<br>
	 * New Num + MNP = SRD<br>
	 * MNP = Cutover Date<br>
	 * @param tgtEffDate the tgtEffDate to set
	 */
	public void setTgtEffDate(String tgtEffDate) {
		this.tgtEffDate = tgtEffDate;
	}
	/**
	 * Logic:<br>
	 * New Num + MNP = New Num<br>
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}
	/**
	 * Logic:<br>
	 * New Num + MNP = New Num<br>
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	/**
	 * NS price fm PPOS
	 * @return the hsPurchasePrice
	 */
	public String getHsPurchasePrice() {
		return hsPurchasePrice;
	}
	/**
	 * NS price fm PPOS
	 * @param hsPurchasePrice the hsPurchasePrice to set
	 */
	public void setHsPurchasePrice(String hsPurchasePrice) {
		this.hsPurchasePrice = hsPurchasePrice;
	}
	/**
	 * RS = Shop Code<br>
	 * CCS = Channel Code<br>
	 * Format: P + shop code/channel code<br>
	 * Example: (RS) PTTW / (CCS) PIBS
	 * @return the shopCd
	 */
	public String getShopCd() {
		return shopCd;
	}
	/**
	 * RS = Shop Code<br>
	 * CCS = Channel Code<br>
	 * Format: P + shop code/channel code<br>
	 * Example: (RS) PTTW / (CCS) PIBS
	 * @param shopCd the shopCd to set
	 */
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getSalesCd() {
		return salesCd;
	}
	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}
	public InputStream getCustSignature() {
		return custSignature;
	}
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	/**
	 * RS = app date
	 * CCS = Delivery date
	 * @return the hsReceivedDate
	 */
	public String getHsReceivedDate() {
		return hsReceivedDate;
	}
	/**
	 * @param hsReceivedDate the hsReceivedDate to set
	 */
	public void setHsReceivedDate(String hsReceivedDate) {
		this.hsReceivedDate = hsReceivedDate;
	}

	public String getiGuardLDSPlanCode() {
		return iGuardLDSPlanCode;
	}
	public void setiGuardLDSPlanCode(String iGuardLDSPlanCode) {
		this.iGuardLDSPlanCode = iGuardLDSPlanCode;
	}
	public String getiGuardADPlanCode() {
		return iGuardADPlanCode;
	}
	public void setiGuardADPlanCode(String iGuardADPlanCode) {
		this.iGuardADPlanCode = iGuardADPlanCode;
	}
	public String getCopy() {
		return copy;
	}
	public void setCopy(String copy) {
		this.copy = copy;
	}
	public String getPhoneProtectorLogo() {
		return phoneProtectorLogo;
	}
	public void setPhoneProtectorLogo(String phoneProtectorLogo) {
		this.phoneProtectorLogo = phoneProtectorLogo;
	}
	public String getDirectLogo() {
		return directLogo;
	}
	public void setDirectLogo(String directLogo) {
		this.directLogo = directLogo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getFlat() {
		return flat;
	}
	public void setFlat(String flat) {
		this.flat = flat;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getOtherContactNo() {
		return otherContactNo;
	}
	public void setOtherContactNo(String otherContactNo) {
		this.otherContactNo = otherContactNo;
	}
	public String getLdsSrvPlanFee() {
		return ldsSrvPlanFee;
	}
	public void setLdsSrvPlanFee(String ldsSrvPlanFee) {
		this.ldsSrvPlanFee = ldsSrvPlanFee;
	}
	
	public String getAdSrvPlanFee() {
		return adSrvPlanFee;
	}
	public void setAdSrvPlanFee(String adSrvPlanFee) {
		this.adSrvPlanFee = adSrvPlanFee;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public boolean isPrivacyInd10011() {
		return privacyInd10011;
	}
	public void setPrivacyInd10011(boolean privacyInd10011) {
		this.privacyInd10011 = privacyInd10011;
	}
	public boolean isPrivacyInd10012() {
		return privacyInd10012;
	}
	public void setPrivacyInd10012(boolean privacyInd10012) {
		this.privacyInd10012 = privacyInd10012;
	}
	public String getPrivacyInd99992() {
		return privacyInd99992;
	}
	public void setPrivacyInd99992(String privacyInd99992) {
		this.privacyInd99992 = privacyInd99992;
	}
	public String getPrivacyInd99993() {
		return privacyInd99993;
	}
	public void setPrivacyInd99993(String privacyInd99993) {
		this.privacyInd99993 = privacyInd99993;
	}
	public String getPrivacyInd99994() {
		return privacyInd99994;
	}
	public void setPrivacyInd99994(String privacyInd99994) {
		this.privacyInd99994 = privacyInd99994;
	}
	public String getBrand1010Ind() {
		return brand1010Ind;
	}
	public void setBrand1010Ind(String brand1010Ind) {
		this.brand1010Ind = brand1010Ind;
	}
}
