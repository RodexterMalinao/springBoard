package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;

import com.bomwebportal.util.FastByteArrayInputStream;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.util.ReportUtil;

public class EyeAppFormRptDTO extends ReportDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9108910599929466727L;
	private String applicationNumber;
	private String applicationDate;
	private boolean eyeApplication;
	private boolean resTelApplication;
	private boolean printTermsCondition = true;
	private String eyeTitle;
	private String resTelTitle;
	private String eyeResTelTitle;
	private SectionARptDTO sectionA = new SectionARptDTO();
	private FixContentEyeResTelSectionRptDTO sectionB = new FixContentEyeResTelSectionRptDTO();
	private SectionCRptDTO sectionC = new SectionCRptDTO();
	private SectionDRptDTO sectionD = new SectionDRptDTO();
	private SectionERptDTO sectionE = new SectionERptDTO();
	private SectionFRptDTO sectionF = new SectionFRptDTO();
	private SectionGRptDTO sectionG = new SectionGRptDTO();
	private SectionHRptDTO sectionH = new SectionHRptDTO();
	private FixContentEyeResTelSectionRptDTO sectionI = new FixContentEyeResTelSectionRptDTO();
	private SectionJRptDTO sectionJ = new SectionJRptDTO();
	private SectionKRptDTO sectionK = new SectionKRptDTO();
	private SectionLRptDTO sectionL = new SectionLRptDTO();
	private FixContentEyeResTelSectionRptDTO sectionM = new FixContentEyeResTelSectionRptDTO();
	private SectionNRptDTO sectionN = new SectionNRptDTO();
	private SectionQRptDTO sectionQ = new SectionQRptDTO();
	private FixContentSectionRptDTO sectionO = new FixContentSectionRptDTO();
	private SectionInternalUseDTO sectionInternalUse = new SectionInternalUseDTO();
	private SectionPRptDTO sectionP = new SectionPRptDTO();
	private byte[] eyeCustSignature;
	private byte[] resTelCustSignature;
	private boolean customerCopy;
	private boolean thirdPartyInd;
	private String orderMode = "R";
	private String orderType = null;
	private byte[] companyChop;
	private String companyChopStatement;
	private String cspItemType;

	private String delLogo;
	private String eyeLogo;
	private String companyBottomBar;
	
	private String action;
	private boolean directrSales;
	private boolean highlightSectionTitle;
	
	public String getEyeTitle() {
		return ReportUtil.defaultString(this.eyeTitle);
	}

	public void setEyeTitle(String pEyeTitle) {
		this.eyeTitle = pEyeTitle;
	}

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

	public FixContentEyeResTelSectionRptDTO getSectionB() {
		return this.sectionB;
	}

	public void setSectionB(FixContentEyeResTelSectionRptDTO pSectionB) {
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

	public SectionERptDTO getSectionE() {
		return this.sectionE;
	}

	public void setSectionE(SectionERptDTO pSectionE) {
		this.sectionE = pSectionE;
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

	public SectionHRptDTO getSectionH() {
		return this.sectionH;
	}

	public void setSectionH(SectionHRptDTO pSectionH) {
		this.sectionH = pSectionH;
	}

	public FixContentEyeResTelSectionRptDTO getSectionI() {
		return this.sectionI;
	}

	public void setSectionI(FixContentEyeResTelSectionRptDTO pSectionI) {
		this.sectionI = pSectionI;
	}

	public SectionJRptDTO getSectionJ() {
		return this.sectionJ;
	}

	public void setSectionJ(SectionJRptDTO pSectionJ) {
		this.sectionJ = pSectionJ;
	}

	public SectionKRptDTO getSectionK() {
		return this.sectionK;
	}

	public void setSectionK(SectionKRptDTO pSectionK) {
		this.sectionK = pSectionK;
	}

	public SectionLRptDTO getSectionL() {
		return this.sectionL;
	}

	public void setSectionL(SectionLRptDTO pSectionL) {
		this.sectionL = pSectionL;
	}

	public FixContentEyeResTelSectionRptDTO getSectionM() {
		return this.sectionM;
	}

	public void setSectionM(FixContentEyeResTelSectionRptDTO pSectionM) {
		this.sectionM = pSectionM;
	}

	public SectionNRptDTO getSectionN() {
		return this.sectionN;
	}

	public void setSectionN(SectionNRptDTO pSectionN) {
		this.sectionN = pSectionN;
	}

	public SectionPRptDTO getSectionP() {
		return this.sectionP;
	}

	public void setSectionP(SectionPRptDTO pSectionP) {
		this.sectionP = pSectionP;
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

	public byte[] getEyeCustSignature() {
		return this.eyeCustSignature;
	}

	public void setEyeCustSignature(byte[] eyeCustSignature) {
		this.eyeCustSignature = eyeCustSignature;
	}
	
	public InputStream getEyeCustSignatureStream() {
		return new FastByteArrayInputStream(this.eyeCustSignature, ArrayUtils.isEmpty(this.eyeCustSignature) ? 0 : this.eyeCustSignature.length);
	}
	
	public byte[] getResTelCustSignature() {
		return this.resTelCustSignature;
	}

	public void setResTelCustSignature(byte[] resTelCustSignature) {
		this.resTelCustSignature = resTelCustSignature;
	}
	
	public InputStream getResTelCustSignatureStream() {
		return new FastByteArrayInputStream(this.resTelCustSignature, ArrayUtils.isEmpty(this.resTelCustSignature) ? 0 : this.resTelCustSignature.length);
	}

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
	
	public ArrayList<EyeAppFormRptDTO> getEyeAppFormRptDTOList() {
		ArrayList<EyeAppFormRptDTO> rtnList = new ArrayList<EyeAppFormRptDTO>();
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
			BeanUtils.setProperty(this.sectionL, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionM, "eyeDevice", pEyeDevice);
			BeanUtils.setProperty(this.sectionN, "eyeDevice", pEyeDevice);
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
			BeanUtils.setProperty(this.sectionL, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionM, "eyePackage", pEyePackage);
			BeanUtils.setProperty(this.sectionN, "eyePackage", pEyePackage);
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

	public String getDelLogo() {
		return this.delLogo;
	}

	public void setDelLogo(String pDelLogo) {
		this.delLogo = pDelLogo;
	}

	public String getEyeLogo() {
		return this.eyeLogo;
	}

	public void setEyeLogo(String pEyeLogo) {
		this.eyeLogo = pEyeLogo;
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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCspItemType() {
		return cspItemType;
	}

	public void setCspItemType(String cspItemType) {
		this.cspItemType = cspItemType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public SectionQRptDTO getSectionQ() {
		return sectionQ;
	}

	public void setSectionQ(SectionQRptDTO sectionQ) {
		this.sectionQ = sectionQ;
	}

	public boolean isDirectrSales() {
		return directrSales;
	}

	public void setDirectrSales(boolean directrSales) {
		this.directrSales = directrSales;
	}

	public boolean isHighlightSectionTitle() {
		return highlightSectionTitle;
	}

	public void setHighlightSectionTitle(boolean highlightSectionTitle) {
		this.highlightSectionTitle = highlightSectionTitle;
	}
	


	
	
}