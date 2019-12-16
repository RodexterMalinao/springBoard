package com.pccw.rpt.schema.dto.slv.salesAgreement;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;

import com.bomwebportal.util.FastByteArrayInputStream;
import com.pccw.rpt.schema.dto.ReportDTO;

public class SalesAgreementRptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9118981045314876922L;
	private String applicationNumber;
	private String applicationDate;
	//private boolean slvApplication;
	//private boolean resTelApplication;
	private boolean printTermsCondition = true;
	private String slvTitle;
	//private String resTelTitle;
	//private String eyeResTelTitle;
	
	private FixContentRptDTO thinksToKnow = new FixContentRptDTO();
	private FixContentRptDTO thinksToKnowEn = new FixContentRptDTO();
	private FixContentRptDTO thinksToKnowZh = new FixContentRptDTO();
	private SectionARptDTO sectionA = new SectionARptDTO();
	private FixContentRptDTO sectionB = new FixContentRptDTO();
	private SectionCRptDTO sectionC = new SectionCRptDTO();
	private SectionDRptDTO sectionD = new SectionDRptDTO();
	private SectionERptDTO sectionE = new SectionERptDTO();
	private SectionFRptDTO sectionF = new SectionFRptDTO();
	private SectionGRptDTO sectionG = new SectionGRptDTO();
	private FixContentRptDTO sectionH = new FixContentRptDTO();
	private SectionIRptDTO sectionI = new SectionIRptDTO();
	private SectionJRptDTO sectionJ = new SectionJRptDTO();
	private FixContentRptDTO sectionK = new FixContentRptDTO();
	private FixContentSectionRptDTO sectionO = new FixContentSectionRptDTO();
	private SectionInternalUseDTO sectionInternalUse = new SectionInternalUseDTO();
	private byte[] slvCustSignature;
	//private byte[] resTelCustSignature;
	private boolean customerCopy;
	private boolean thirdPartyInd;
	private String orderMode = "R";
	private byte[] companyChop;
	private String companyChopStatement;

	private String slvLogo;
	private String groupLogo;
	private String hereToServeLogo;
	private String companyBottomBar;
	private String packageTotal;
	private String packageTotalPrefix;
	private String packageTotalSufix;
	
	public String getSlvTitle() {
		return this.slvTitle;
	}

	public void setSlvTitle(String pSlvTitle) {
		this.slvTitle = pSlvTitle;
	}
/*
	public String getResTelTitle() {
		return ReportUtil.defaultString(this.resTelTitle);
	}

	public void setResTelTitle(String pResTelTitle) {
		this.resTelTitle = pResTelTitle;
	}

	public String getEyeResTelTitle() {
		return ReportUtil.defaultString(this.eyeResTelTitle);
	}

	public void setEyeResTelTitle(String pEyeResTelTitle) {
		this.eyeResTelTitle = pEyeResTelTitle;
	}
*/
	public String getApplicationNumber() {
		return this.applicationNumber;
	}

	public void setApplicationNumber(String pApplicationNumber) {
		this.applicationNumber = pApplicationNumber;
	}
	
	public String getApplicationDate() {
		return this.applicationDate;
	}

	public void setApplicationDate(String pApplicationDate) {
		this.applicationDate = pApplicationDate;
	}
/*
	public boolean isEyeApplication() {
		return this.eyeApplication;
	}

	public void setEyeApplication(boolean pEyeApplication) {
		this.eyeApplication = pEyeApplication;
	}

	public boolean isResTelApplication() {
		return this.resTelApplication;
	}

	public void setResTelApplication(boolean pResTelApplication) {
		this.resTelApplication = pResTelApplication;
	}
*/
	public boolean isPrintTermsCondition() {
		return this.printTermsCondition;
	}

	public void setPrintTermsCondition(boolean pPrintTermsCondition) {
		this.printTermsCondition = pPrintTermsCondition;
	}

	public SectionARptDTO getSectionA() {
		return this.sectionA;
	}

	public void setSectionA(SectionARptDTO pSectionA) {
		this.sectionA = pSectionA;
	}

	public FixContentRptDTO getSectionB() {
		return this.sectionB;
	}

	public void setSectionB(FixContentRptDTO pSectionB) {
		this.sectionB = pSectionB;
	}

	public SectionCRptDTO getSectionC() {
		return this.sectionC;
	}

	public void setSectionC(SectionCRptDTO pSectionC) {
		this.sectionC = pSectionC;
	}

	public SectionDRptDTO getSectionD() {
		return this.sectionD;
	}

	public void setSectionD(SectionDRptDTO pSectionD) {
		this.sectionD = pSectionD;
	}

	public void setSectionE(SectionERptDTO pSectionE) {
		this.sectionE = pSectionE;
	}

	
	public void setSectionH(FixContentRptDTO pSectionH) {
		this.sectionH = pSectionH;
	}

	public FixContentRptDTO getSectionH() {
		return this.sectionH;
	}

	
	
	public FixContentSectionRptDTO getSectionO() {
		return this.sectionO;
	}

	public void setSectionO(FixContentSectionRptDTO pSectionO) {
		this.sectionO = pSectionO;
	}

	public SectionInternalUseDTO getSectionInternalUse() {
		return this.sectionInternalUse;
	}

	public void setSectionInternalUse(SectionInternalUseDTO pSectionInternalUse) {
		this.sectionInternalUse = pSectionInternalUse;
	}

	public byte[] getSlvCustSignature() {
		return this.slvCustSignature;
	}

	public void setSlvCustSignature(byte[] slvCustSignature) {
		this.slvCustSignature = slvCustSignature;
	}
	
	public InputStream getSlvCustSignatureStream() {
		return new FastByteArrayInputStream(this.slvCustSignature, ArrayUtils.isEmpty(this.slvCustSignature) ? 0 : this.slvCustSignature.length);
	}
/*	
	public byte[] getResTelCustSignature() {
		return this.resTelCustSignature;
	}

	public void setResTelCustSignature(byte[] resTelCustSignature) {
		this.resTelCustSignature = resTelCustSignature;
	}
	
	public InputStream getResTelCustSignatureStream() {
		return new FastByteArrayInputStream(this.resTelCustSignature, ArrayUtils.isEmpty(this.resTelCustSignature) ? 0 : this.resTelCustSignature.length);
	}
*/
	public boolean isCustomerCopy() {
		return this.customerCopy;
	}

	public void setCustomerCopy(boolean customerCopy) {
		this.customerCopy = customerCopy;
	}

	public boolean isThirdPartyInd() {
		return this.thirdPartyInd;
	}

	public void setThirdPartyInd(boolean thirdPartyInd) {
		this.thirdPartyInd = thirdPartyInd;
	}
	
	public ArrayList<SalesAgreementRptDTO> getSalesAgreementRptDTOList() {
		ArrayList<SalesAgreementRptDTO> rtnList = new ArrayList<SalesAgreementRptDTO>();
		rtnList.add(this);
		return rtnList;
	}

	public void setEyeDevice(String pEyeDevice) {
		try {
			BeanUtils.setProperty(this.sectionA, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionB, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionC, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionD, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionE, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionF, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionG, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionH, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionI, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionJ, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionK, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionO, "eyeDevice", pEyeDevice);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setEyePackage(String pEyePackage) {
		try {
			BeanUtils.setProperty(this.sectionA, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionB, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionC, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionD, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionE, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionF, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionG, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionH, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionI, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionJ, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionK, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionO, "eyePackage", pEyePackage);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getOrderMode() {
		return this.orderMode;
	}

	public void setOrderMode(String pOrderMode) {
		this.orderMode = pOrderMode;
	}

	public String getSlvLogo() {
		return this.slvLogo;
	}

	public void setSlvLogo(String pSlvLogo) {
		this.slvLogo = pSlvLogo;
	}

	public String getCompanyBottomBar() {
		return this.companyBottomBar;
	}

	public void setCompanyBottomBar(String pCompanyBottomBar) {
		this.companyBottomBar = pCompanyBottomBar;
	}
	
	public byte[] getCompanyChop() {
		return this.companyChop;
	}

	public void setcompanyChop(byte[] companyChop) {
		this.companyChop = companyChop;
	}
	
	public InputStream getCompanyChopStream() {
		return new FastByteArrayInputStream(this.companyChop, ArrayUtils.isEmpty(this.companyChop) ? 0 : this.companyChop.length);
	}

	public String getCompanyChopStatement() {
		return this.companyChopStatement;
	}

	public void setCompanyChopStatement(String pCompanyChopStatement) {
		this.companyChopStatement = pCompanyChopStatement;
	}

	public SectionFRptDTO getSectionF() {
		return this.sectionF;
	}

	public void setSectionF(SectionFRptDTO pSectionF) {
		this.sectionF = pSectionF;
	}

	public SectionGRptDTO getSectionG() {
		return this.sectionG;
	}

	public void setSectionG(SectionGRptDTO pSectionG) {
		this.sectionG = pSectionG;
	}

	public SectionIRptDTO getSectionI() {
		return this.sectionI;
	}

	public void setSectionI(SectionIRptDTO pSectionI) {
		this.sectionI = pSectionI;
	}

	public SectionJRptDTO getSectionJ() {
		return this.sectionJ;
	}

	public void setSectionJ(SectionJRptDTO pSectionJ) {
		this.sectionJ = pSectionJ;
	}

	public FixContentRptDTO getSectionK() {
		return this.sectionK;
	}

	public void setSectionK(FixContentRptDTO pSectionK) {
		this.sectionK = pSectionK;
	}

	public SectionERptDTO getSectionE() {
		return this.sectionE;
	}

	public String getPackageTotal() {
		return this.packageTotal;
	}

	public void setPackageTotal(String pPackageTotal) {
		this.packageTotal = pPackageTotal;
	}

	public String getHereToServeLogo() {
		return this.hereToServeLogo;
	}

	public void setHereToServeLogo(String pHereToServeLogo) {
		this.hereToServeLogo = pHereToServeLogo;
	}

	public String getGroupLogo() {
		return this.groupLogo;
	}

	public void setGroupLogo(String pGroupLogo) {
		this.groupLogo = pGroupLogo;
	}

	public String getPackageTotalPrefix() {
		return this.packageTotalPrefix;
	}

	public void setPackageTotalPrefix(String pPackageTotalPrefix) {
		this.packageTotalPrefix = pPackageTotalPrefix;
	}

	public String getPackageTotalSufix() {
		return this.packageTotalSufix;
	}

	public void setPackageTotalSufix(String pPackageTotalSufix) {
		this.packageTotalSufix = pPackageTotalSufix;
	}

	public FixContentRptDTO getThinksToKnowEn() {
		return this.thinksToKnowEn;
	}

	public void setThinksToKnowEn(FixContentRptDTO pThinksToKnowEn) {
		this.thinksToKnowEn = pThinksToKnowEn;
	}

	public FixContentRptDTO getThinksToKnowZh() {
		return this.thinksToKnowZh;
	}

	public void setThinksToKnowZh(FixContentRptDTO pThinksToKnowZh) {
		this.thinksToKnowZh = pThinksToKnowZh;
	}

	public FixContentRptDTO getThinksToKnow() {
		return this.thinksToKnow;
	}

	public void setThinksToKnow(FixContentRptDTO pThinksToKnow) {
		this.thinksToKnow = pThinksToKnow;
	}
	
	
}