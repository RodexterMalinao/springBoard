package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.ConfigProperties;

public class IPhoneTradeInFormDTO extends ReportDTO {

	public static final String JASPER_TEMPLATE = "M2iPhoneTradeInForm";
	public static final String JASPER_TEMPLATE_IPHONE8 = "M2iPhone8TradeInForm";
	protected String imageFilePath = ConfigProperties.getPropertyByEnvironment("imageFilePath");
	public static final String RET_RPT_LANG_ZH_HK = "zh_HK";
	public static final String ACQ_RPT_LANG_ZH_HK = "zh";

	private String customerCopyInd;
	private String custEngName;
	private String msisdn;
	private String mnpType;
	private Date cutoverDate;
	private Date serviceReqDate;
	private String mobCustNum;
	private String imei;
	private String handsetModel;
	private String orderType;
	private String iPhonePdfPageOne;
	private String iPhonePdfPageTwo;
	private String iPhonePdfPageTwoPartTwo;
	private String iPhonePdfPageThreePartOne;
	private String iPhonePdfPageThree;
	private String isIGuardUAD = "N" ;
	private InputStream custSignature;
	private String signatureInd;

	public String getCustomerCopyInd() {
		return customerCopyInd;
	}

	public void setCustomerCopyInd(String customerCopyInd) {
		this.customerCopyInd = customerCopyInd;
	}

	public String getCustEngName() {
		return custEngName;
	}

	public void setCustEngName(String custEngName) {
		this.custEngName = custEngName;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getMnpType() {
		return mnpType;
	}

	public void setMnpType(String mnpType) {
		this.mnpType = mnpType;
	}

	public Date getCutoverDate() {
		return cutoverDate;
	}

	public void setCutoverDate(Date cutoverDate) {
		this.cutoverDate = cutoverDate;
	}

	public Date getServiceReqDate() {
		return serviceReqDate;
	}

	public void setServiceReqDate(Date serviceReqDate) {
		this.serviceReqDate = serviceReqDate;
	}

	public String getMobCustNum() {
		return mobCustNum;
	}

	public void setMobCustNum(String mobCustNum) {
		this.mobCustNum = mobCustNum;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getHandsetModel() {
		return handsetModel;
	}

	public void setHandsetModel(String handsetModel) {
		this.handsetModel = handsetModel;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getiPhonePdfPageOne() {
		return iPhonePdfPageOne;
	}

	public void setiPhonePdfPageOne(String iPhonePdfPageOne) {
		this.iPhonePdfPageOne = iPhonePdfPageOne;
	}

	public String getiPhonePdfPageTwo() {
		return iPhonePdfPageTwo;
	}

	public void setiPhonePdfPageTwo(String iPhonePdfPageTwo) {
		this.iPhonePdfPageTwo = iPhonePdfPageTwo;
	}

	public String getiPhonePdfPageTwoPartTwo() {
		return iPhonePdfPageTwoPartTwo;
	}

	public void setiPhonePdfPageTwoPartTwo(String iPhonePdfPageTwoPartTwo) {
		this.iPhonePdfPageTwoPartTwo = iPhonePdfPageTwoPartTwo;
	}

	public String getiPhonePdfPageThreePartOne() {
		return iPhonePdfPageThreePartOne;
	}

	public void setiPhonePdfPageThreePartOne(String iPhonePdfPageThreePartOne) {
		this.iPhonePdfPageThreePartOne = iPhonePdfPageThreePartOne;
	}

	public String getiPhonePdfPageThree() {
		return iPhonePdfPageThree;
	}

	public void setiPhonePdfPageThree(String iPhonePdfPageThree) {
		this.iPhonePdfPageThree = iPhonePdfPageThree;
	}

	public String getIsIGuardUAD() {
		return isIGuardUAD;
	}

	public void setIsIGuardUAD(String isIGuardUAD) {
		this.isIGuardUAD = isIGuardUAD;
	}

	public InputStream getCustSignature() {
		return custSignature;
	}

	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}

	public String getSignatureInd() {
		return signatureInd;
	}

	public void setSignatureInd(String signatureInd) {
		this.signatureInd = signatureInd;
	}

	public void setpdf(String locale, boolean iphone8, String brand) {

		if (iphone8) {
			
			if ("0".equalsIgnoreCase(brand)){
				if (ACQ_RPT_LANG_ZH_HK.equalsIgnoreCase(locale)) {
					this.setiPhonePdfPageOne(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform1_1010_zh);
	
					this.setiPhonePdfPageTwo(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform2_1010_zh);
	
					this.setiPhonePdfPageThree(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform3_1010_zh);
				} else {
					
					this.setiPhonePdfPageOne(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform1_1010_en);
	
					this.setiPhonePdfPageTwo(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform2_1_1010_en);
					
					this.setiPhonePdfPageTwoPartTwo(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform2_2_1010_en);
						
					this.setiPhonePdfPageThreePartOne(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform3_1_1010_en);
					
					this.setiPhonePdfPageThree(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform3_1010_en);
				}
			}else{
				if (ACQ_RPT_LANG_ZH_HK.equalsIgnoreCase(locale)) {
					this.setiPhonePdfPageOne(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform1_csl_zh);
	
					this.setiPhonePdfPageTwo(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform2_csl_zh);
	
					this.setiPhonePdfPageThree(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform3_csl_zh);
				} else {
					
					this.setiPhonePdfPageOne(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform1_csl_en);
	
					this.setiPhonePdfPageTwo(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform2_1_csl_en);
	
					this.setiPhonePdfPageThreePartOne(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform3_1_csl_en);
					
					this.setiPhonePdfPageThree(imageFilePath + "/" + BomWebPortalConstant.iphone8tradeinform3_csl_en);
				}
			}

		} else {
			if (ACQ_RPT_LANG_ZH_HK.equalsIgnoreCase(locale)) {
				this.setiPhonePdfPageOne(imageFilePath + "/" + BomWebPortalConstant.iphonetradeinform1);

				this.setiPhonePdfPageTwo(imageFilePath + "/" + BomWebPortalConstant.iphonetradeinform2);

				this.setiPhonePdfPageThree(imageFilePath + "/" + BomWebPortalConstant.iphonetradeinform3);
			} else {
				this.setiPhonePdfPageOne(imageFilePath + "/" + BomWebPortalConstant.iphonetradeinform1);

				this.setiPhonePdfPageTwo(imageFilePath + "/" + BomWebPortalConstant.iphonetradeinform2);

				this.setiPhonePdfPageThree(imageFilePath + "/" + BomWebPortalConstant.iphonetradeinform3);
			}

		}
	}

}
