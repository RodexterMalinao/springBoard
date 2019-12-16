package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import com.pccw.rpt.util.ReportUtil;

public class SectionPRptDTO extends SectionRptDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2925177663206477696L;

	private String content;
	
	private String contentInfo1csp;
	private String contentInfo2csp;
	
	private String sectionTitle2csp;	
	private String sectionTitleClub;
	private String sectionTitleHkt;
	
	private String emailAddr;
	
	private String mobileNum;
	
	private String content1Hkt;
	private String content1Club;
	private String content1HktClub;
	private String content1HktClubver2;
	
	private String content2Hkt;
	private String content2Club;
	private String content2HktClub;
	private String bothRegistered;
	private String bothInvalidEmailMsg;
	private String clubInvalidEmailMsg;
	private String hktInvalidEmailMsg;

	public String getContent() {
		return ReportUtil.defaultString(this.content);
	}

	public void setContent(String pContent) {
		this.content = pContent;
	}

	public String getContent1Hkt() {
		return ReportUtil.defaultString(this.content1Hkt);
	}

	public void setContent1Hkt(String pContent1Hkt) {
		this.content1Hkt = pContent1Hkt;
	}

	public String getContent1Club() {
		return ReportUtil.defaultString(this.content1Club);
	}

	public void setContent1Club(String pContent1Club) {
		this.content1Club = pContent1Club;
	}

	public String getContent1HktClub() {
		return ReportUtil.defaultString(this.content1HktClub);
	}

	public void setContent1HktClub(String pContent1HktClub) {
		this.content1HktClub = pContent1HktClub;
	}
	
	public String getContent1HktClubver2() {
		return content1HktClubver2;
	}

	public void setContent1HktClubver2(String content1HktClubver2) {
		this.content1HktClubver2 = content1HktClubver2;
	}

	public String getBothRegistered() {
		return ReportUtil.defaultString(this.bothRegistered);
	}
	
	public void setBothRegistered(String bothRegistered) {
		this.bothRegistered = bothRegistered;
	}

	public String getContent2Hkt() {
		return ReportUtil.defaultString(this.content2Hkt);
	}

	public void setContent2Hkt(String pContent2Hkt) {
		this.content2Hkt = pContent2Hkt;
	}

	public String getContent2Club() {
		return ReportUtil.defaultString(this.content2Club);
	}

	public void setContent2Club(String pContent2Club) {
		this.content2Club = pContent2Club;
	}

	public String getContent2HktClub() {
		return ReportUtil.defaultString(this.content2HktClub);
	}

	public void setContent2HktClub(String pContent2HktClub) {
		this.content2HktClub = pContent2HktClub;
	}

	public String getEmailAddr() {
		return ReportUtil.defaultString(this.emailAddr);
	}

	public void setEmailAddr(String pEmailAddr) {
		this.emailAddr = pEmailAddr;
	}

	public String getMobileNum() {
		return ReportUtil.defaultString(this.mobileNum);
	}

	public void setMobileNum(String pMobileNum) {
		this.mobileNum = pMobileNum;
	}

	public String getContentInfo1csp() {
		return contentInfo1csp;
	}

	public void setContentInfo1csp(String contentInfo1csp) {
		this.contentInfo1csp = contentInfo1csp;
	}

	public String getContentInfo2csp() {
		return contentInfo2csp;
	}

	public void setContentInfo2csp(String contentInfo2csp) {
		this.contentInfo2csp = contentInfo2csp;
	}
	public String getSectionTitle2csp() {
		return sectionTitle2csp;
	}

	public void setSectionTitle2csp(String sectionTitle2csp) {
		this.sectionTitle2csp = sectionTitle2csp;
	}

	public String getSectionTitleClub() {
		return sectionTitleClub;
	}

	public void setSectionTitleClub(String sectionTitleClub) {
		this.sectionTitleClub = sectionTitleClub;
	}

	public String getSectionTitleHkt() {
		return sectionTitleHkt;
	}

	public void setSectionTitleHkt(String sectionTitleHkt) {
		this.sectionTitleHkt = sectionTitleHkt;
	}

	public String getBothInvalidEmailMsg() {
		return bothInvalidEmailMsg;
	}

	public void setBothInvalidEmailMsg(String bothInvalidEmailMsg) {
		this.bothInvalidEmailMsg = bothInvalidEmailMsg;
	}

	public String getClubInvalidEmailMsg() {
		return clubInvalidEmailMsg;
	}

	public void setClubInvalidEmailMsg(String clubInvalidEmailMsg) {
		this.clubInvalidEmailMsg = clubInvalidEmailMsg;
	}

	public String getHktInvalidEmailMsg() {
		return hktInvalidEmailMsg;
	}

	public void setHktInvalidEmailMsg(String hktInvalidEmailMsg) {
		this.hktInvalidEmailMsg = hktInvalidEmailMsg;
	}
}
