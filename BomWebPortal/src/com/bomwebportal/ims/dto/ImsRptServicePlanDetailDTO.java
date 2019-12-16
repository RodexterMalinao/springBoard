package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.VasParmDTO;
import com.bomwebportal.dto.report.RptHSTradeDescDTO;
import com.bomwebportal.ims.dto.ImsRptCoreServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptOptServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptNtvServiceDetailDTO;
import com.bomwebportal.ims.dto.report.RptServiceInfoDTO;
import com.bomwebportal.ims.dto.ui.BasketUI;
import com.pccw.rpt.schema.dto.wq.ImsWorkQueueRptDTO;


public class ImsRptServicePlanDetailDTO implements Serializable{
	
	private static final long serialVersionUID = 629210834742496125L;
	
	private List<VasParmDTO> cslMrtNumPinList;
	private String appMethod;
	private ImsWorkQueueRptDTO imsWQDTO;
	private String wqRemarkForInterialUse;
	private String isAFShowALL;
	private ImsRptCoreServiceDetailDTO coreServiceDetail;
	private ImsRptOptServiceDetailDTO optServiceDetail;
	private ImsRptNtvServiceDetailDTO ntvServiceDetail;
	private String qosMeasureInd;
	private List<RptHSTradeDescDTO> mobList;
	private List<String> pisPdfs;
	private HashMap<String, HashMap<String, RptServiceInfoDTO>> dBRptStaticWords;
	private List<ImsRptGiftDTO> giftList;
	
	private List<VasParmDTO> imsVasParmList;
	private String preInstRouterPurchasedInd;
	
	// NOWTVSALES
	private BasketUI ftaPack;
	private List<BasketUI> selectedBaskets;
	
	
	
	public BasketUI getFtaPack() {
		return ftaPack;
	}

	public void setFtaPack(BasketUI ftaPack) {
		this.ftaPack = ftaPack;
	}

	public List<BasketUI> getSelectedBaskets() {
		return selectedBaskets;
	}
	
	public void setSelectedBaskets(List<BasketUI> selectedBaskets) {
		this.selectedBaskets = selectedBaskets;
	}

	public void setMobList(List<RptHSTradeDescDTO> mobList) {
		this.mobList = mobList;
	}
	
	public List<VasParmDTO> getCslMrtNumPinList() {
		return cslMrtNumPinList;
	}

	public void setVasParmDTO(List<VasParmDTO> cslMrtNumPinList) {
		this.cslMrtNumPinList = cslMrtNumPinList;
	}

	public List<RptHSTradeDescDTO> getMobList() {
		return mobList;
	}

	public ImsRptServicePlanDetailDTO(){
		
	}

	public ImsRptCoreServiceDetailDTO getCoreServiceDetail() {
		return coreServiceDetail;
	}

	public void setCoreServiceDetail(ImsRptCoreServiceDetailDTO coreServiceDetail) {
		this.coreServiceDetail = coreServiceDetail;
	}

	public ImsRptOptServiceDetailDTO getOptServiceDetail() {
		return optServiceDetail;
	}

	public void setOptServiceDetail(ImsRptOptServiceDetailDTO optServiceDetail) {
		this.optServiceDetail = optServiceDetail;
	}

	public ImsRptNtvServiceDetailDTO getNtvServiceDetail() {
		return ntvServiceDetail;
	}

	public void setNtvServiceDetail(ImsRptNtvServiceDetailDTO ntvServiceDetail) {
		this.ntvServiceDetail = ntvServiceDetail;
	}

	public String getQosMeasureInd() {
		return qosMeasureInd;
	}

	public void setQosMeasureInd(String qosMeasureInd) {
		this.qosMeasureInd = qosMeasureInd;
	}

	@Override
	public String toString() {
		return "ImsRptServicePlanDetailDTO [coreServiceDetail="
				+ coreServiceDetail + ", optServiceDetail=" + optServiceDetail
				+ ", ntvServiceDetail=" + ntvServiceDetail + ", qosMeasureInd="
				+ qosMeasureInd + "]";
	}

	public void setPisPdfs(List<String> pisPdfs) {
		this.pisPdfs = pisPdfs;
	}

	public List<String> getPisPdfs() {
		return pisPdfs;
	}

	public void setdBRptStaticWords(HashMap<String, HashMap<String, RptServiceInfoDTO>> dBRptStaticWords) {
		this.dBRptStaticWords = dBRptStaticWords;
	}

	public HashMap<String, HashMap<String, RptServiceInfoDTO>> getdBRptStaticWords() {
		return dBRptStaticWords;
	}

	public void setIsAFShowALL(String isAFShowALL) {
		this.isAFShowALL = isAFShowALL;
	}

	public String getIsAFShowALL() {
		return isAFShowALL;
	}

	public void setWqRemarkForInterialUse(String wqRemarkForInterialUse) {
		this.wqRemarkForInterialUse = wqRemarkForInterialUse;
	}

	public String getWqRemarkForInterialUse() {
		return wqRemarkForInterialUse;
	}

	public void setImsWQDTO(ImsWorkQueueRptDTO imsWQDTO) {
		this.imsWQDTO = imsWQDTO;
	}

	public ImsWorkQueueRptDTO getImsWQDTO() {
		return imsWQDTO;
	}

	public void setAppMethod(String appMethod) {
		this.appMethod = appMethod;
	}

	public String getAppMethod() {
		return appMethod;
	}

	public void setGiftList(List<ImsRptGiftDTO> giftList) {
		this.giftList = giftList;
	}

	public List<ImsRptGiftDTO> getGiftList() {
		return giftList;
	}

	public void setImsVasParmList(List<VasParmDTO> imsVasParmList) {
		this.imsVasParmList = imsVasParmList;
	}

	public List<VasParmDTO> getImsVasParmList() {
		return imsVasParmList;
	}

	public void setPreInstRouterPurchasedInd(String preInstRouterPurchasedInd) {
		this.preInstRouterPurchasedInd = preInstRouterPurchasedInd;
	}

	public String getPreInstRouterPurchasedInd() {
		return preInstRouterPurchasedInd;
	}


}
