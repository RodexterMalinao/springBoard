package com.bomwebportal.dto;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.VasDetailDTO;

public class ServicePlanReportDTO implements Serializable {
	
	protected final Log logger = LogFactory.getLog(getClass());

	public ServicePlanReportDTO() {
		this.setTradeField("tradeField");// function not ready
		this.setSmsBillLanguage("Chinese");
	}

	private static final long serialVersionUID = 5958055566691928236L;

	private List<VasDetailDTO> mainServDtls = new ArrayList<VasDetailDTO>();
	private List<VasDetailDTO> vasDtls = new ArrayList<VasDetailDTO>();
	private List<VasDetailDTO> vasOptionalDtls = new ArrayList<VasDetailDTO>();
	private List<VasDetailDTO> vasFreeGifsDtls = new ArrayList<VasDetailDTO>();

	String[] penaltyinfo; // [0]contract_period, [1]penalty_type ,[2]penalty_amt
	String contractPeriod;
	String penaltyType;
	String penaltyAmt;

	List<VasOnetimeAmtDTO> vasOnetimeAmtList;
	String vasOnetimeAmtFee;
	List<VasDetailDTO> rebateList;
	String handsetDeviceAmount;
	String firstMonthFee;
	String firstMonthServiceLicenceFee; // add by wilson 20110722

	String smsBillLanguage;
	String billPeriod;
	String billMedia;
	String tradeField;

	String handsetDeviceDescription;

	// add 20110610 ServicePlanReportDTO
	String conciergeInd; // Y, N, report use for show concierge service report
	String brandName;// report use for concierge service report
	String modelName;// report use for concierge service report
	String colorName;// report use for concierge service report
	String brandId;
	String modelId;
	String colorId;
	String originalPrice;
	String[] handsetDeviceInfo; // [0]brandName, [1]odelName ,[2]colorName ,
								// [3]brandId, [4]modelId ,[5]colorId, [6]
								// device name [7] original_price
	String locale;// en, zh_HK
	
	//add Eliot 20110819
	String basketType;
	//select * from w_display_lkup dl where dl.type='BASKET_TYPE';
	//1  SIM + HANDSET
	//2  SIM ONLY
	//3  SIM + SMARTPHONE REBATE
	//4	 SIM + TABLET
	//5	 NETVIGATOR Everywhere
	//6	 Concierge

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getConciergeInd() {
		return conciergeInd;
	}

	public String getModelName() {
		return modelName;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setConciergeInd(String conciergeInd) {
		this.conciergeInd = conciergeInd;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	String secSrvContractPeriod; // add by wilson 20110216, for SecSrv report

	public String getSecSrvContractPeriod() {
		return secSrvContractPeriod;
	}

	public void setSecSrvContractPeriod(String secSrvContractPeriod) {
		this.secSrvContractPeriod = secSrvContractPeriod;
	}

	public String getBillPeriod() {
		return billPeriod;
	}

	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}

	public String getHandsetDeviceDescription() {
		return handsetDeviceDescription;
	}

	public void setHandsetDeviceDescription(String handsetDeviceDescription) {
		this.handsetDeviceDescription = handsetDeviceDescription;
	}

	public String getSmsBillLanguage() {
		return smsBillLanguage;
	}

	public void setSmsBillLanguage(String smsBillLanguage) {
		this.smsBillLanguage = smsBillLanguage;
	}

	public String getBillMedia() {
		return billMedia;
	}

	public void setBillMedia(String billMedia) {
		this.billMedia = billMedia;
	}

	public String getTradeField() {
		return tradeField;
	}

	public void setTradeField(String tradeField) {
		this.tradeField = tradeField;
	}

	public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public String getPenaltyType() {
		return penaltyType;
	}

	public void setPenaltyType(String penaltyType) {
		this.penaltyType = penaltyType;
	}

	public String getPenaltyAmt() {
		return penaltyAmt;
	}

	public void setPenaltyAmt(String penaltyAmt) {
		this.penaltyAmt = penaltyAmt;
	}

	// for output html for testing
	public String toString() {
		String tempString = "ApplicationReportDTO.toString()<BR>";
		tempString += "getHandsetDeviceDescription():"
				+ this.getHandsetDeviceDescription() + "<BR>";
		tempString += "handsetDeviceAmount:" + handsetDeviceAmount + "<BR>";
		tempString += "firstMonthFee:" + firstMonthFee + "<BR>";
		tempString += "penaltyinfo[0]" + penaltyinfo[0] + "<BR>";
		tempString += "penaltyinfo[1]" + penaltyinfo[1] + "<BR>";
		tempString += "penaltyinfo[2]" + penaltyinfo[2] + "<BR>";
		for (int i = 0; i < rebateList.size(); i++) {
			tempString += "VasDetailDTO==>" + i + "<BR>";
			tempString += "VasDetailDTO.getItemId():"
					+ rebateList.get(i).getItemId() + "\n";
			tempString += "VasDetailDTO.getItemDisplayType():"
					+ rebateList.get(i).getItemDisplayType() + "\n";
			tempString += "VasDetailDTO.getItemType():"
					+ rebateList.get(i).getItemType() + "\n";
			tempString += "VasDetailDTO.getItemHtml():"
					+ rebateList.get(i).getItemHtml() + "\n";
			tempString += "VasDetailDTO.getItemRebateAmt():"
					+ rebateList.get(i).getItemRebateAmt() + "\n";
			tempString += "\n";

		}

		return tempString;
	}

	public List<VasDetailDTO> getRebateList() {
		return rebateList;
	}

	public void setRebateList(List<VasDetailDTO> rebateList) {
		this.rebateList = rebateList;
	}

	public List<VasOnetimeAmtDTO> getVasOnetimeAmtList() {
		return vasOnetimeAmtList;
	}

	public void setVasOnetimeAmtList(List<VasOnetimeAmtDTO> vasOnetimeAmtList) {
		this.vasOnetimeAmtList = vasOnetimeAmtList;
	}

	public String getHandsetDeviceAmount() {
		return handsetDeviceAmount;
	}

	public void setHandsetDeviceAmount(String handsetDeviceAmount) {
		this.handsetDeviceAmount = handsetDeviceAmount;
	}

	public String getFirstMonthFee() {
		return firstMonthFee;
	}

	public void setFirstMonthFee(String firstMonthFee) {
		this.firstMonthFee = firstMonthFee;
	}

	public String[] getPenaltyinfo() {
		return penaltyinfo;
	}

	public void setPenaltyinfo(String[] penaltyinfo) {
		this.penaltyinfo = penaltyinfo;
		this.contractPeriod = penaltyinfo[0];
		this.penaltyType = penaltyinfo[1];
		this.penaltyAmt = penaltyinfo[2];
	}

	public String getBrandId() {
		return brandId;
	}

	public String getModelId() {
		return modelId;
	}

	public String getColorId() {
		return colorId;
	}

	public String[] getHandsetDeviceInfo() {
		return handsetDeviceInfo;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public void setColorId(String colorId) {
		this.colorId = colorId;
	}

	public List<VasDetailDTO> getMainServDtls() {
		return mainServDtls;
	}

	public List<VasDetailDTO> getVasDtls() {
		return vasDtls;
	}

	public void setVasDtls(List<VasDetailDTO> vasDtls) {
		this.vasDtls = vasDtls;
	}

	public void setMainServDtls(List<VasDetailDTO> mainServDtls) {
		this.mainServDtls = mainServDtls;
	}

	public void setHandsetDeviceInfo(String[] handsetDeviceInfo) {
		this.handsetDeviceInfo = handsetDeviceInfo;
		this.brandName = handsetDeviceInfo[0];
		this.modelName = handsetDeviceInfo[1];
		this.colorName = handsetDeviceInfo[2];
		this.brandId = handsetDeviceInfo[3];
		this.modelId = handsetDeviceInfo[4];
		this.colorId = handsetDeviceInfo[5];
		this.handsetDeviceDescription = handsetDeviceInfo[6];
		if (handsetDeviceInfo[7] != null) {
			try {
				NumberFormat numInCommaFmt = NumberFormat.getInstance(Locale.US);
				handsetDeviceInfo[7] = numInCommaFmt.format(Double.parseDouble(handsetDeviceInfo[7]));
			} catch (NumberFormatException nfe) {
				logger.debug("NumberFormatException @ServicePlanReportDTO" + nfe);
			}
		}
		this.originalPrice= handsetDeviceInfo[7];
	}

	public String getFirstMonthServiceLicenceFee() {
		return firstMonthServiceLicenceFee;
	}

	public void setFirstMonthServiceLicenceFee(
			String firstMonthServiceLicenceFee) {
		this.firstMonthServiceLicenceFee = firstMonthServiceLicenceFee;
	}

	public void setVasFreeGifsDtls(List<VasDetailDTO> vasFreeGifsDtls) {
		this.vasFreeGifsDtls = vasFreeGifsDtls;
	}

	public List<VasDetailDTO> getVasFreeGifsDtls() {
		return vasFreeGifsDtls;
	}

	public List<VasDetailDTO> getVasOptionalDtls() {
		return vasOptionalDtls;
	}

	public void setVasOptionalDtls(List<VasDetailDTO> vasOptionalDtls) {
		this.vasOptionalDtls = vasOptionalDtls;
	}

	public String getBasketType() {
		return basketType;
	}

	public void setBasketType(String basketType) {
		this.basketType = basketType;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getVasOnetimeAmtFee() {
		return vasOnetimeAmtFee;
	}

	public void setVasOnetimeAmtFee(String vasOnetimeAmtFee) {
		this.vasOnetimeAmtFee = vasOnetimeAmtFee;
	}
	
	
}
