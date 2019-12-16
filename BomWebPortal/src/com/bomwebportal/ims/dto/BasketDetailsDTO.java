package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.VasParmDTO;


public class BasketDetailsDTO extends BasketDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 629210834742496125L;
	
	/*
	 * IMS related
	 */
	private String BasketId;
	private String ItemID;
	private String Title;
	private String Summary;
	private String ItemDetail;
	private String RecurrentAmt;
	private String MthFixText;
	private String MthToMthRate;
	private String MthToMthText;
	private String ContractPeriod;
	private String OfferCode;
	private String ImagePath;
	private String Bandwidth;	
	private String NowTvFormType;		
	private String IsCouponPlan;
	private String CanSubcOptSrv;
	private String IsPreintalltion;
	private String Technology;
	private String OtInstallChrgReq;
	private String PlanId;
	private String TermExt;
	private String ItemType;
	private String OnetimeAmt;
	private List<String> paymentMethodList;
	private String productId;
	
	private VasParmDTO vasParmDTO;
	
	private String WaiveISFInd;
	private String WaiveASFInd;
	private String Bandwidth_desc;

	public VasParmDTO getVasParmDTO() {
		return vasParmDTO;
	}

	public void setVasParmDTO(VasParmDTO vasParmDTO) {
		this.vasParmDTO = vasParmDTO;
	}
	
	/*
	 * DTO members
	 */
	
	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public BasketDetailsDTO(){
		
	}

	
	public List<String> getPaymentMethodList() {
		return paymentMethodList;
	}


	public void setPaymentMethodList(List<String> paymentMethodList) {
		this.paymentMethodList = paymentMethodList;
	}


	public String getContractPeriod() {
		return ContractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		ContractPeriod = contractPeriod;
	}
	
	public String getOfferCode() {
		return OfferCode;
	}

	public void setOfferCode(String offerCode) {
		OfferCode = offerCode;
	}
	
	public String getImagePath() {
		return ImagePath;
	}

	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}

	public String getRecurrentAmt() {
		return RecurrentAmt;
	}

	public void setRecurrentAmt(String recurrentAmt) {
		RecurrentAmt = recurrentAmt;
	}
	
	public String getMthToMthRate() {
		return MthToMthRate;
	}

	public void setMthToMthRate(String mthToMthRate) {
		MthToMthRate = mthToMthRate;
	}
	
	public String getItemID() {
		return ItemID;
	}

	public void setItemID(String ItemID) {
		this.ItemID = ItemID;
	}
	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String ItemType) {
		this.ItemType = ItemType;
	}
	
	public String getBasketId() {
		return BasketId;
	}

	public void setBasketId(String basketId) {
		BasketId = basketId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getSummary() {
		return Summary;
	}

	public void setSummary(String summary) {
		Summary = summary;
	}

	public String getItemDetail() {
		return ItemDetail;
	}

	public void setItemDetail(String itemDetail) {
		ItemDetail = itemDetail;
	}

	public String getMthFixText() {
		return MthFixText;
	}

	public void setMthFixText(String mthFixText) {
		MthFixText = mthFixText;
	}

	public String getMthToMthText() {
		return MthToMthText;
	}

	public void setMthToMthText(String mthToMthText) {
		MthToMthText = mthToMthText;
	}

	public String getBandwidth() {
		return Bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		Bandwidth = bandwidth;
	}

	public String getNowTvFormType() {
		return NowTvFormType;
	}

	public void setNowTvFormType(String nowTvFormType) {
		NowTvFormType = nowTvFormType;
	}

	public String getIsCouponPlan() {
		return IsCouponPlan;
	}

	public void setIsCouponPlan(String isCouponPlan) {
		IsCouponPlan = isCouponPlan;
	}

	public String getCanSubcOptSrv() {
		return CanSubcOptSrv;
	}

	public void setCanSubcOptSrv(String canSubcOptSrv) {
		CanSubcOptSrv = canSubcOptSrv;
	}

	public String getIsPreintalltion() {
		return IsPreintalltion;
	}

	public void setIsPreintalltion(String isPreintalltion) {
		IsPreintalltion = isPreintalltion;
	}

	public String getTechnology() {
		return Technology;
	}

	public void setTechnology(String technology) {
		Technology = technology;
	}

	public String getOtInstallChrgReq() {
		return OtInstallChrgReq;
	}

	public void setOtInstallChrgReq(String otInstallChrgReq) {
		OtInstallChrgReq = otInstallChrgReq;
	}

	public String getPlanId() {
		return PlanId;
	}

	public void setPlanId(String planId) {
		PlanId = planId;
	}

	public String getTermExt() {
		return TermExt;
	}

	public void setTermExt(String termExt) {
		TermExt = termExt;
	}
	
	public String getOnetimeAmt() {
		return OnetimeAmt;
	}

	public void setOnetimeAmt(String onetimeAmt) {
		OnetimeAmt = onetimeAmt;
	}

	public void setWaiveISFInd(String waiveISFInd) {
		WaiveISFInd = waiveISFInd;
	}

	public String getWaiveISFInd() {
		return WaiveISFInd;
	}

	public void setWaiveASFInd(String waiveASFInd) {
		WaiveASFInd = waiveASFInd;
	}

	public String getWaiveASFInd() {
		return WaiveASFInd;
	}

	public void setBandwidth_desc(String bandwidth_desc) {
		Bandwidth_desc = bandwidth_desc;
	}

	public String getBandwidth_desc() {
		return Bandwidth_desc;
	}
	
}
