package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;

public class FsaDetailDTO implements Serializable {
	
	private static final long serialVersionUID = -3401305062178738434L;
	
	private boolean selected;
	private int fsa;
	private String docNum;
	private String docType;
	private String custFirstName;
	private String custLastName;
	private FsaServiceType existingService;
	private FsaServiceType newService;
	private String bandwidth;
	private String loginId;
	private String effectiveDate;
	private String modemArrange;
	private String newModemArrange;
	
	private boolean pendingOrderExist;
	private String pendingOcid;
	private String pendingOrderType;
	private String pendingOrderSrd;
	private boolean tos;
	private boolean notAllowToShare;
	private String notAllowToShareReason;
	private boolean eyeService;	
	private String technology = null;
	
	private boolean allowConfirmSameIa;
	private boolean isDifferentIa;
	private AddressDetailProfileLtsDTO addressDetail;
	
	private List<FsaServiceType> futureFsaServiceList;
	private Map<String, String> upgradeBandwidthMap;

	private FsaServiceDetailOutputDTO fsaProfile;
	
	private NowTvServiceDetailDTO nowTvServiceDetail;
	private String deactNowTv;
	
	private String upgradeBandwidth;
	private String tenure;
	private String existingModemArrange;
	private String existMirrorInd;
	
	private String routerGrp;
	private boolean meshRouterGrp;
	private boolean brmWifiInd;
	
	public enum FsaServiceType {
		WG("WG"),
		PCD("PCD"), 
		SDTV("SDTV"), 
		HDTV("HDTV"), 
		PCD_SDTV("PCD+SDTV"), 
		PCD_HDTV("PCD+HDTV"),
		PCD_EW("PCD+EW"),
		PCD_EW_SDTV("PCD+EW+SDTV"),
		PCD_EW_HDTV("PCD+EW+HDTV"),
		EW("EW"),
		EW_SDTV("EW+SDTV"),
		EW_HDTV("EW+HDTV"),
		PS3("PS3"),
		PCD_PS3("PCD+PS3"),
		PCD_SDTV_PS3("PCD+SDTV+PS3"),
		PCD_HDTV_PS3("PCD+HDTV+PS3"),
		PCD_EW_SDTV_PS3("PCD+EW+SDTV+PS3"),
		PCD_EW_HDTV_PS3("PCD+EW+HDTV+PS3"),
		EW_SDTV_PS3("EW+SDTV+PS3"),
		EW_HDTV_PS3("EW+HDTV+PS3"),
		SDTV_PS3("SDTV+PS3"),
		HDTV_PS3("HDTV+PS3"),
		EYE("eye");
		
		private String desc;
		   private FsaServiceType(String desc) {
		       this.desc = desc;
		   }
		   public String getDesc() {
		       return desc;
		   }
		   public String getName() {
		       return name();
		   } 
		   
		   @Override
		   public String toString () {
		       return getDesc();
		   } 
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getFsa() {
		return fsa;
	}
	public void setFsa(int fsa) {
		this.fsa = fsa;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getCustFirstName() {
		return custFirstName;
	}
	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}
	public String getCustLastName() {
		return custLastName;
	}
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}
	public FsaServiceType getExistingService() {
		return existingService;
	}
	public void setExistingService(FsaServiceType existingService) {
		this.existingService = existingService;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getModemArrange() {
		return modemArrange;
	}
	public void setModemArrange(String modemArrange) {
		this.modemArrange = modemArrange;
	}
	public boolean isPendingOrderExist() {
		return pendingOrderExist;
	}
	public void setPendingOrderExist(boolean pendingOrderExist) {
		this.pendingOrderExist = pendingOrderExist;
	}
	public String getPendingOrderType() {
		return pendingOrderType;
	}
	public void setPendingOrderType(String pendingOrderType) {
		this.pendingOrderType = pendingOrderType;
	}
	public boolean isTos() {
		return tos;
	}
	public void setTos(boolean tos) {
		this.tos = tos;
	}
	
	public boolean isNotAllowToShare() {
		return notAllowToShare;
	}
	public void setNotAllowToShare(boolean notAllowToShare) {
		this.notAllowToShare = notAllowToShare;
	}
	public FsaServiceType getNewService() {
		return newService;
	}
	public void setNewService(FsaServiceType newService) {
		this.newService = newService;
	}
	public NowTvServiceDetailDTO getNowTvServiceDetail() {
		return nowTvServiceDetail;
	}
	public void setNowTvServiceDetail(NowTvServiceDetailDTO nowTvServiceDetail) {
		this.nowTvServiceDetail = nowTvServiceDetail;
	}
	public String getPendingOcid() {
		return pendingOcid;
	}
	public void setPendingOcid(String pendingOcid) {
		this.pendingOcid = pendingOcid;
	}
	public boolean isEyeService() {
		return eyeService;
	}
	public void setEyeService(boolean eyeService) {
		this.eyeService = eyeService;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public AddressDetailProfileLtsDTO getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(AddressDetailProfileLtsDTO addressDetail) {
		this.addressDetail = addressDetail;
	}
	public boolean isDifferentIa() {
		return isDifferentIa;
	}
	public void setDifferentIa(boolean isDifferentIa) {
		this.isDifferentIa = isDifferentIa;
	}
	public String getUpgradeBandwidth() {
		return upgradeBandwidth;
	}
	public void setUpgradeBandwidth(String upgradeBandwidth) {
		this.upgradeBandwidth = upgradeBandwidth;
	}
	public String getNewModemArrange() {
		return newModemArrange;
	}
	public void setNewModemArrange(String newModemArrange) {
		this.newModemArrange = newModemArrange;
	}
	public List<FsaServiceType> getFutureFsaServiceList() {
		return futureFsaServiceList;
	}
	public void setFutureFsaServiceList(List<FsaServiceType> futureFsaServiceList) {
		this.futureFsaServiceList = futureFsaServiceList;
	}
	public Map<String, String> getUpgradeBandwidthMap() {
		return upgradeBandwidthMap;
	}
	public void setUpgradeBandwidthMap(Map<String, String> upgradeBandwidthMap) {
		this.upgradeBandwidthMap = upgradeBandwidthMap;
	}
	public String getDeactNowTv() {
		return deactNowTv;
	}
	public void setDeactNowTv(String deactNowTv) {
		this.deactNowTv = deactNowTv;
	}
	public String getTenure() {
		return tenure;
	}
	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
	public String getExistingModemArrange() {
		return existingModemArrange;
	}
	public void setExistingModemArrange(String existingModemArrange) {
		this.existingModemArrange = existingModemArrange;
	}
	public boolean isAllowConfirmSameIa() {
		return allowConfirmSameIa;
	}
	public void setAllowConfirmSameIa(boolean allowConfirmSameIa) {
		this.allowConfirmSameIa = allowConfirmSameIa;
	}
	public String getPendingOrderSrd() {
		return pendingOrderSrd;
	}
	public void setPendingOrderSrd(String pendingOrderSrd) {
		this.pendingOrderSrd = pendingOrderSrd;
	}
	public String getNotAllowToShareReason() {
		return notAllowToShareReason;
	}
	public void setNotAllowToShareReason(String notAllowToShareReason) {
		this.notAllowToShareReason = notAllowToShareReason;
	}
	public String getExistMirrorInd() {
		return existMirrorInd;
	}
	public void setExistMirrorInd(String existMirrorInd) {
		this.existMirrorInd = existMirrorInd;
	}
	public FsaServiceDetailOutputDTO getFsaProfile() {
		return fsaProfile;
	}
	public void setFsaProfile(FsaServiceDetailOutputDTO fsaProfile) {
		this.fsaProfile = fsaProfile;
	}
	public String getRouterGrp() {
		return routerGrp;
	}
	public void setRouterGrp(String routerGrp) {
		this.routerGrp = routerGrp;
	}
	public boolean isMeshRouterGrp() {
		return meshRouterGrp;
	}
	public void setMeshRouterGrp(boolean meshRouterGrp) {
		this.meshRouterGrp = meshRouterGrp;
	}
	public boolean isBrmWifiInd() {
		return brmWifiInd;
	}
	public void setBrmWifiInd(boolean brmWifiInd) {
		this.brmWifiInd = brmWifiInd;
	}
	
}
