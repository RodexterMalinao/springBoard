package com.pccw.rpt.schema.dto.recontractForm;

import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.tdo.keyInfoSheet.KeyInfoSheetRptDTO.keyInfo;

public class RecontractFormRptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2279452394893582561L;
	
	private String srvNum;
	private String effectiveDate;
	private boolean callingCardSrv;
	private boolean mobileIDDSrv;
	private boolean fixedIDDSrv;
	
	private String callingCardChkbox;
	private String mobIDDChkbox;
	private String fixedIDDChkbox;
	
	private String fromCustTitle;
	private String fromCustFamilyName;
	private String fromCustGivenName;
	private String fromCustDocNum;
	private String fromCustDocType;
	private String fromCustContactNum;
	private byte[] fromCustSignature;
	private String fromCustPassStrikeThrough;
	private String fromCustHkidStrikeThrough;
	private String fromCustName;
	private String fromCustMrStrikeThrough;
	private String fromCustMsStrikeThrough;
	private String fromCustMrsStrikeThrough;
	
	private String toCustTitle;
	private String toCustFamilyName;
	private String toCustGivenName;
	private String toCustDocNum;
	private String toCustDocType;
	private String toCustContactNum;
	private byte[] toCustSignature;
	private byte[] toCustIndemnitySign;
	private byte[] toCustPersonInfoSignature;
	private String toCustPassStrikeThrough;
	private String toCustHkidStrikeThrough;
	private String toCustName;
	private String toCustMrStrikeThrough;
	private String toCustMsStrikeThrough;
	private String toCustMrsStrikeThrough;
	
	private String toSignDate;
	private String fromSignDate;
	private String transMode;
	
	private String singleST1;
	private String singleST2;
	private String singleST3;
	private String singleST4;
	private String singleST5;
	private String singleST6;
	private String singleST7;
	private String singleST8;
	private String singleST9;
	private String singleST10;
	
	private String bothST1;
	private String bothST2;
	private String bothST3;
	private String bothST4;
	private String bothST5;
	private String bothST6;
	private String bothST7;
	private String bothST8;
	private String bothST9;
	private String bothST10;
	
	private boolean isOptOut;
	private String optOutStrikeThru1;
	private String optOutStrikeThru2;
	private String optOutStrikeThru3;
	private String optOutStrikeThru4;
	private String optOutStrikeThru5;
	private String optOutStrikeThru6;
	private String optOutStrikeThru7;
	private String optOutStrikeThru8;
	private String optOutStrikeThru9;
	private String optOutStrikeThru10;
	private String optOutStrikeThru11;
	private String optOutStrikeThru12;
	
	
	public String getSrvNum() {
		return this.srvNum;
	}
	public void setSrvNum(String pSrvNum) {
		this.srvNum = pSrvNum;
	}
	public String getEffectiveDate() {
		return this.effectiveDate;
	}
	public void setEffectiveDate(String pEffectiveDate) {
		this.effectiveDate = pEffectiveDate;
	}
	public boolean isCallingCardSrv() {
		return this.callingCardSrv;
	}
	public void setCallingCardSrv(boolean pCallingCardSrv) {
		this.callingCardSrv = pCallingCardSrv;
	}
	public boolean isMobileIDDSrv() {
		return this.mobileIDDSrv;
	}
	public void setMobileIDDSrv(boolean pMobileIDDSrv) {
		this.mobileIDDSrv = pMobileIDDSrv;
	}
	public boolean isFixedIDDSrv() {
		return this.fixedIDDSrv;
	}
	public void setFixedIDDSrv(boolean pFixedIDDSrv) {
		this.fixedIDDSrv = pFixedIDDSrv;
	}
	public String getCallingCardChkbox() {
		return this.callingCardChkbox;
	}
	public void setCallingCardChkbox(String pCallingCardChkbox) {
		this.callingCardChkbox = pCallingCardChkbox;
	}
	public String getMobIDDChkbox() {
		return this.mobIDDChkbox;
	}
	public void setMobIDDChkbox(String pMobIDDChkbox) {
		this.mobIDDChkbox = pMobIDDChkbox;
	}
	public String getFixedIDDChkbox() {
		return this.fixedIDDChkbox;
	}
	public void setFixedIDDChkbox(String pFixedIDDChkbox) {
		this.fixedIDDChkbox = pFixedIDDChkbox;
	}
	public String getFromCustTitle() {
		return this.fromCustTitle;
	}
	public void setFromCustTitle(String pFromCustTitle) {
		this.fromCustTitle = pFromCustTitle;
	}
	public String getFromCustFamilyName() {
		return this.fromCustFamilyName;
	}
	public void setFromCustFamilyName(String pFromCustFamilyName) {
		this.fromCustFamilyName = pFromCustFamilyName;
	}
	public String getFromCustGivenName() {
		return this.fromCustGivenName;
	}
	public void setFromCustGivenName(String pFromCustGivenName) {
		this.fromCustGivenName = pFromCustGivenName;
	}
	public String getFromCustDocNum() {
		return this.fromCustDocNum;
	}
	public void setFromCustDocNum(String pFromCustDocNum) {
		this.fromCustDocNum = pFromCustDocNum;
	}
	public String getFromCustContactNum() {
		return this.fromCustContactNum;
	}
	public void setFromCustContactNum(String pFromCustContactNum) {
		this.fromCustContactNum = pFromCustContactNum;
	}
	public byte[] getFromCustSignature() {
		return this.fromCustSignature;
	}
	public void setFromCustSignature(byte[] pFromCustSignature) {
		this.fromCustSignature = pFromCustSignature;
	}
	public String getToCustTitle() {
		return this.toCustTitle;
	}
	public void setToCustTitle(String pToCustTitle) {
		this.toCustTitle = pToCustTitle;
	}
	public String getToCustFamilyName() {
		return this.toCustFamilyName;
	}
	public void setToCustFamilyName(String pToCustFamilyName) {
		this.toCustFamilyName = pToCustFamilyName;
	}
	public String getToCustGivenName() {
		return this.toCustGivenName;
	}
	public void setToCustGivenName(String pToCustGivenName) {
		this.toCustGivenName = pToCustGivenName;
	}
	public String getToCustDocNum() {
		return this.toCustDocNum;
	}
	public void setToCustDocNum(String pToCustDocNum) {
		this.toCustDocNum = pToCustDocNum;
	}
	public String getToCustContactNum() {
		return this.toCustContactNum;
	}
	public void setToCustContactNum(String pToCustContactNum) {
		this.toCustContactNum = pToCustContactNum;
	}
	public byte[] getToCustSignature() {
		return this.toCustSignature;
	}
	public void setToCustSignature(byte[] pToCustSignature) {
		this.toCustSignature = pToCustSignature;
	}
	public String getToSignDate() {
		return this.toSignDate;
	}
	public void setToSignDate(String pToSignDate) {
		this.toSignDate = pToSignDate;
	}
	public String getFromSignDate() {
		return this.fromSignDate;
	}
	public void setFromSignDate(String pFromSignDate) {
		this.fromSignDate = pFromSignDate;
	}
	public String getFromCustDocType() {
		return this.fromCustDocType;
	}
	public void setFromCustDocType(String pFromCustDocType) {
		this.fromCustDocType = pFromCustDocType;
	}
	public String getToCustDocType() {
		return this.toCustDocType;
	}
	public void setToCustDocType(String pToCustDocType) {
		this.toCustDocType = pToCustDocType;
	}
	public String getFromCustPassStrikeThrough() {
		return this.fromCustPassStrikeThrough;
	}
	public void setFromCustPassStrikeThrough(String pFromCustPassStrikeThrough) {
		this.fromCustPassStrikeThrough = pFromCustPassStrikeThrough;
	}
	public String getFromCustHkidStrikeThrough() {
		return this.fromCustHkidStrikeThrough;
	}
	public void setFromCustHkidStrikeThrough(String pFromCustHkidStrikeThrough) {
		this.fromCustHkidStrikeThrough = pFromCustHkidStrikeThrough;
	}
	public String getToCustPassStrikeThrough() {
		return this.toCustPassStrikeThrough;
	}
	public void setToCustPassStrikeThrough(String pToCustPassStrikeThrough) {
		this.toCustPassStrikeThrough = pToCustPassStrikeThrough;
	}
	public String getToCustHkidStrikeThrough() {
		return this.toCustHkidStrikeThrough;
	}
	public void setToCustHkidStrikeThrough(String pToCustHkidStrikeThrough) {
		this.toCustHkidStrikeThrough = pToCustHkidStrikeThrough;
	}
	public byte[] getToCustIndemnitySign() {
		return this.toCustIndemnitySign;
	}
	public void setToCustIndemnitySign(byte[] pToCustIndemnitySign) {
		this.toCustIndemnitySign = pToCustIndemnitySign;
	}
	public String getTransMode() {
		return this.transMode;
	}
	public void setTransMode(String pTransMode) {
		this.transMode = pTransMode;
	}
	public byte[] getToCustPersonInfoSignature() {
		return this.toCustPersonInfoSignature;
	}
	public void setToCustPersonInfoSignature(byte[] pToCustPersonInfoSignature) {
		this.toCustPersonInfoSignature = pToCustPersonInfoSignature;
	}
	public String getSingleST1() {
		return this.singleST1;
	}
	public void setSingleST1(String pSingleST1) {
		this.singleST1 = pSingleST1;
	}
	public String getSingleST2() {
		return this.singleST2;
	}
	public void setSingleST2(String pSingleST2) {
		this.singleST2 = pSingleST2;
	}
	public String getSingleST3() {
		return this.singleST3;
	}
	public void setSingleST3(String pSingleST3) {
		this.singleST3 = pSingleST3;
	}
	public String getSingleST4() {
		return this.singleST4;
	}
	public void setSingleST4(String pSingleST4) {
		this.singleST4 = pSingleST4;
	}
	public String getSingleST5() {
		return this.singleST5;
	}
	public void setSingleST5(String pSingleST5) {
		this.singleST5 = pSingleST5;
	}
	public String getSingleST6() {
		return this.singleST6;
	}
	public void setSingleST6(String pSingleST6) {
		this.singleST6 = pSingleST6;
	}
	public String getSingleST7() {
		return this.singleST7;
	}
	public void setSingleST7(String pSingleST7) {
		this.singleST7 = pSingleST7;
	}
	public String getSingleST8() {
		return this.singleST8;
	}
	public void setSingleST8(String pSingleST8) {
		this.singleST8 = pSingleST8;
	}
	public String getSingleST9() {
		return this.singleST9;
	}
	public void setSingleST9(String pSingleST9) {
		this.singleST9 = pSingleST9;
	}
	public String getSingleST10() {
		return this.singleST10;
	}
	public void setSingleST10(String pSingleST10) {
		this.singleST10 = pSingleST10;
	}
	public String getBothST1() {
		return this.bothST1;
	}
	public void setBothST1(String pBothST1) {
		this.bothST1 = pBothST1;
	}
	public String getBothST2() {
		return this.bothST2;
	}
	public void setBothST2(String pBothST2) {
		this.bothST2 = pBothST2;
	}
	public String getBothST3() {
		return this.bothST3;
	}
	public void setBothST3(String pBothST3) {
		this.bothST3 = pBothST3;
	}
	public String getBothST4() {
		return this.bothST4;
	}
	public void setBothST4(String pBothST4) {
		this.bothST4 = pBothST4;
	}
	public String getBothST5() {
		return this.bothST5;
	}
	public void setBothST5(String pBothST5) {
		this.bothST5 = pBothST5;
	}
	public String getBothST6() {
		return this.bothST6;
	}
	public void setBothST6(String pBothST6) {
		this.bothST6 = pBothST6;
	}
	public String getBothST7() {
		return this.bothST7;
	}
	public void setBothST7(String pBothST7) {
		this.bothST7 = pBothST7;
	}
	public String getBothST8() {
		return this.bothST8;
	}
	public void setBothST8(String pBothST8) {
		this.bothST8 = pBothST8;
	}
	public String getBothST9() {
		return this.bothST9;
	}
	public void setBothST9(String pBothST9) {
		this.bothST9 = pBothST9;
	}
	public String getBothST10() {
		return this.bothST10;
	}
	public void setBothST10(String pBothST10) {
		this.bothST10 = pBothST10;
	}
	public String getFromCustName() {
		return (StringUtils.isEmpty(this.fromCustFamilyName)? "" : this.fromCustFamilyName) 
				+ " " + (StringUtils.isEmpty(this.fromCustGivenName)? "" : this.fromCustGivenName);
	
	}
	public String getToCustName() {
		return (StringUtils.isEmpty(this.toCustFamilyName)? "" : this.toCustFamilyName) 
				+ " " + (StringUtils.isEmpty(this.toCustGivenName)? "" : this.toCustGivenName);
	}
	public boolean isOptOut() {
		return isOptOut;
	}
	public void setOptOut(boolean isOptOut) {
		this.isOptOut = isOptOut;
	}
	public String getOptOutStrikeThru1() {
		return optOutStrikeThru1;
	}
	public void setOptOutStrikeThru1(String optOutStrikeThru1) {
		this.optOutStrikeThru1 = optOutStrikeThru1;
	}
	public String getOptOutStrikeThru2() {
		return optOutStrikeThru2;
	}
	public void setOptOutStrikeThru2(String optOutStrikeThru2) {
		this.optOutStrikeThru2 = optOutStrikeThru2;
	}
	public String getOptOutStrikeThru3() {
		return optOutStrikeThru3;
	}
	public void setOptOutStrikeThru3(String optOutStrikeThru3) {
		this.optOutStrikeThru3 = optOutStrikeThru3;
	}
	public String getOptOutStrikeThru4() {
		return optOutStrikeThru4;
	}
	public void setOptOutStrikeThru4(String optOutStrikeThru4) {
		this.optOutStrikeThru4 = optOutStrikeThru4;
	}
	public String getOptOutStrikeThru5() {
		return optOutStrikeThru5;
	}
	public void setOptOutStrikeThru5(String optOutStrikeThru5) {
		this.optOutStrikeThru5 = optOutStrikeThru5;
	}
	public String getOptOutStrikeThru6() {
		return optOutStrikeThru6;
	}
	public void setOptOutStrikeThru6(String optOutStrikeThru6) {
		this.optOutStrikeThru6 = optOutStrikeThru6;
	}
	public String getOptOutStrikeThru7() {
		return optOutStrikeThru7;
	}
	public void setOptOutStrikeThru7(String optOutStrikeThru7) {
		this.optOutStrikeThru7 = optOutStrikeThru7;
	}
	public String getOptOutStrikeThru8() {
		return optOutStrikeThru8;
	}
	public void setOptOutStrikeThru8(String optOutStrikeThru8) {
		this.optOutStrikeThru8 = optOutStrikeThru8;
	}
	public String getOptOutStrikeThru9() {
		return optOutStrikeThru9;
	}
	public void setOptOutStrikeThru9(String optOutStrikeThru9) {
		this.optOutStrikeThru9 = optOutStrikeThru9;
	}
	public String getOptOutStrikeThru10() {
		return optOutStrikeThru10;
	}
	public void setOptOutStrikeThru10(String optOutStrikeThru10) {
		this.optOutStrikeThru10 = optOutStrikeThru10;
	}
	public String getOptOutStrikeThru11() {
		return optOutStrikeThru11;
	}
	public void setOptOutStrikeThru11(String optOutStrikeThru11) {
		this.optOutStrikeThru11 = optOutStrikeThru11;
	}
	public String getOptOutStrikeThru12() {
		return optOutStrikeThru12;
	}
	public void setOptOutStrikeThru12(String optOutStrikeThru12) {
		this.optOutStrikeThru12 = optOutStrikeThru12;
	}
	public void setToCustName(String toCustName) {
		this.toCustName = toCustName;
	}
	public String getFromCustMrStrikeThrough() {
		return fromCustMrStrikeThrough;
	}
	public void setFromCustMrStrikeThrough(String fromCustMrStrikeThrough) {
		this.fromCustMrStrikeThrough = fromCustMrStrikeThrough;
	}
	public String getFromCustMsStrikeThrough() {
		return fromCustMsStrikeThrough;
	}
	public void setFromCustMsStrikeThrough(String fromCustMsStrikeThrough) {
		this.fromCustMsStrikeThrough = fromCustMsStrikeThrough;
	}
	public String getFromCustMrsStrikeThrough() {
		return fromCustMrsStrikeThrough;
	}
	public void setFromCustMrsStrikeThrough(String fromCustMrsStrikeThrough) {
		this.fromCustMrsStrikeThrough = fromCustMrsStrikeThrough;
	}
	public String getToCustMrStrikeThrough() {
		return toCustMrStrikeThrough;
	}
	public void setToCustMrStrikeThrough(String toCustMrStrikeThrough) {
		this.toCustMrStrikeThrough = toCustMrStrikeThrough;
	}
	public String getToCustMsStrikeThrough() {
		return toCustMsStrikeThrough;
	}
	public void setToCustMsStrikeThrough(String toCustMsStrikeThrough) {
		this.toCustMsStrikeThrough = toCustMsStrikeThrough;
	}
	public String getToCustMrsStrikeThrough() {
		return toCustMrsStrikeThrough;
	}
	public void setToCustMrsStrikeThrough(String toCustMrsStrikeThrough) {
		this.toCustMrsStrikeThrough = toCustMrsStrikeThrough;
	}

}
