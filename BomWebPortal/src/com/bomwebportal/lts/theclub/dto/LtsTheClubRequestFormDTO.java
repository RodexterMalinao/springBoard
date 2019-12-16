package com.bomwebportal.lts.theclub.dto;

public class LtsTheClubRequestFormDTO {
	// common params - start
	private String username;
	private String password;
	private String source;
	private String lang;
	private String lob;
	private String memberId;
	private String theClubOrderId;
	// common params - end
	// getMemberBasicInfoWithMaskedID - start
	private String searchType;
	private String loginId;
	private String idDocType;
	private String idDocNum;
	// getMemberBasicInfoWithMaskedID - end
	// doInstantEarnPoint and doInstantEarnReversePoint - start
	private String packageCode;
	private String reverseTransId;
	private String orderNo;
	private Integer point;
	private String channel;
	private String transId;
	private String agreementNum;
	// doInstantEarnPoint and doInstantEarnReversePoint -end
	// params from Bom - start
	private String custNum;
	private Integer ocId;
	private Integer dtlId;
	private Integer dtlSeq;
	private Integer itemId;
	private Integer offerId;
	private String psefCd;
	private Integer clubPoints; 
	// params from Bom - end
	
	private String transStatus;
	private boolean test=false;
	private boolean verify=true;
	public Integer getItemId() {
		return itemId;
	}

	public Integer getOfferId() {
		return offerId;
	}

	public String getPsefCd() {
		return psefCd;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	public void setPsefCd(String psefCd) {
		this.psefCd = psefCd;
	}
	
	public Integer getOcId() {
		return ocId;
	}

	public void setOcId(Integer ocId) {
		this.ocId = ocId;
	}

	// params from Bom - end	
	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getSource() {
		return source;
	}

	public String getLang() {
		return lang;
	}

	public String getLob() {
		return lob;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getSearchType() {
		return searchType;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public String getIdDocNum() {
		return idDocNum;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public Integer getPoint() {
		return point;
	}

	public String getChannel() {
		return channel;
	}

	public String getTransId() {
		return transId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getAgreementNum() {
		return agreementNum;
	}

	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}

	public Integer getDtlId() {
		return dtlId;
	}

	public Integer getDtlSeq() {
		return dtlSeq;
	}

	public void setDtlId(Integer dtlId) {
		this.dtlId = dtlId;
	}

	public void setDtlSeq(Integer dtlSeq) {
		this.dtlSeq = dtlSeq;
	}

	public Integer getClubPoints() {
		return clubPoints;
	}

	public void setClubPoints(Integer clubPoints) {
		this.clubPoints = clubPoints;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public boolean isTest() {
		return test;
	}

	public boolean isVerify() {
		return verify;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public void setVerify(boolean verify) {
		this.verify = verify;
	}

	public String getTheClubOrderId() {
		return theClubOrderId;
	}

	public void setTheClubOrderId(String theClubOrderId) {
		this.theClubOrderId = theClubOrderId;
	}

	public String getReverseTransId() {
		return reverseTransId;
	}

	public void setReverseTransId(String reverseTransId) {
		this.reverseTransId = reverseTransId;
	}

}
