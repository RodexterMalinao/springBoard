package com.bomwebportal.lts.dto.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;



public class ServiceDetailOtherLtsDTO extends ServiceDetailDTO {

	private static final long serialVersionUID = -4703395238359718711L;

	private String loginId = null;
	private String existSrvTypeCd = null;
	private String newSrvTypeCd = null;
	private String existBandwidth = null;
	private String newBandwidth = null;
	private String assgnBandwidth = null;
	private String relatedSbOrderId = null;
	private String existTvSrvType = null;
	private String newTvSrvType = null;
	private String deactNowTvInd = null;
	private String nowtvGrpCd = null;
	private String nowtvTvCd = null;
	private String nowtvMirrorInd = null;
	private String modemArrangement = null;
	private ChannelDetailLtsDTO[] channels = null;
	private NowtvDetailLtsDTO nowtvDetail = null;
	private String assgnTechnology = null;
	private String shareFsaType = null;
	private String mirrorFsa = null;
	private String existModem = null;
	private String existArpu = "0";
	private String existTechnology = null;
	private String replaceExistFsa = null;
	private String existMirrorInd = null;
	private String edfRef = null;
	private String existNowtvTvCd = null;
	private String repackNowtvInd = null;
	private String terminatePcd = null;
	private String terminateTv = null;
	private String manualLineTypeInd = null;
	private String autoUpgraded=null;
	private String noEyeFsa = null;
	private String lostModem = null;
	private String shareRentalRouter = null;
	private String shareBrmWifi = null;
	private List<ImsOfferDetailDTO> offerDtlList = null;
	private ImsL2JobDTO[] imsL2Jobs = null;
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getExistSrvTypeCd() {
		return existSrvTypeCd;
	}

	public void setExistSrvTypeCd(String existSrvTypeCd) {
		this.existSrvTypeCd = existSrvTypeCd;
	}

	public String getNewSrvTypeCd() {
		return newSrvTypeCd;
	}

	public void setNewSrvTypeCd(String newSrvTypeCd) {
		this.newSrvTypeCd = newSrvTypeCd;
	}

	public String getExistBandwidth() {
		return existBandwidth;
	}

	public void setExistBandwidth(String existBandwidth) {
		this.existBandwidth = existBandwidth;
	}

	public String getNewBandwidth() {
		return newBandwidth;
	}

	public void setNewBandwidth(String newBandwidth) {
		this.newBandwidth = newBandwidth;
	}

	public String getRelatedSbOrderId() {
		return relatedSbOrderId;
	}

	public void setRelatedSbOrderId(String relatedSbOrderId) {
		this.relatedSbOrderId = relatedSbOrderId;
	}

	public String getExistTvSrvType() {
		return existTvSrvType;
	}

	public void setExistTvSrvType(String existTvSrvType) {
		this.existTvSrvType = existTvSrvType;
	}

	public String getNewTvSrvType() {
		return newTvSrvType;
	}

	public void setNewTvSrvType(String newTvSrvType) {
		this.newTvSrvType = newTvSrvType;
	}

	public String getDeactNowTvInd() {
		return deactNowTvInd;
	}

	public void setDeactNowTvInd(String deactNowTvInd) {
		this.deactNowTvInd = deactNowTvInd;
	}

	public String getNowtvGrpCd() {
		return nowtvGrpCd;
	}

	public void setNowtvGrpCd(String nowtvGrpCd) {
		this.nowtvGrpCd = nowtvGrpCd;
	}

	public String getNowtvTvCd() {
		return nowtvTvCd;
	}

	public void setNowtvTvCd(String nowtvTvCd) {
		this.nowtvTvCd = nowtvTvCd;
	}

	public String getNowtvMirrorInd() {
		return nowtvMirrorInd;
	}

	public void setNowtvMirrorInd(String nowtvMirrorInd) {
		this.nowtvMirrorInd = nowtvMirrorInd;
	}

	public ChannelDetailLtsDTO[] getChannels() {
		return channels;
	}

	public void setChannels(ChannelDetailLtsDTO[] channels) {
		this.channels = channels;
	}

	public NowtvDetailLtsDTO getNowtvDetail() {
		return nowtvDetail;
	}

	public void setNowtvDetail(NowtvDetailLtsDTO nowtvDetail) {
		this.nowtvDetail = nowtvDetail;
	}

	public String getModemArrangement() {
		return modemArrangement;
	}

	public void setModemArrangement(String modemArrangement) {
		this.modemArrangement = modemArrangement;
	}
	
	public String getAssgnBandwidth() {
		return assgnBandwidth;
	}

	public void setAssgnBandwidth(String assgnBandwidth) {
		this.assgnBandwidth = assgnBandwidth;
	}

	public String getAssgnTechnology() {
		return assgnTechnology;
	}

	public void setAssgnTechnology(String assgnTechnology) {
		this.assgnTechnology = assgnTechnology;
	}

	public String getShareFsaType() {
		return shareFsaType;
	}

	public void setShareFsaType(String shareFsaType) {
		this.shareFsaType = shareFsaType;
	}

	public String getMirrorFsa() {
		return mirrorFsa;
	}

	public void setMirrorFsa(String mirrorFsa) {
		this.mirrorFsa = mirrorFsa;
	}

	public String getExistModem() {
		return existModem;
	}

	public void setExistModem(String existModem) {
		this.existModem = existModem;
	}

	public String getExistArpu() {
		return existArpu;
	}

	public void setExistArpu(String existArpu) {
		this.existArpu = existArpu;
	}

	public String getExistTechnology() {
		return this.existTechnology;
	}

	public void setExistTechnology(String pExistTechnology) {
		this.existTechnology = pExistTechnology;
	}

	public String getReplaceExistFsa() {
		return replaceExistFsa;
	}

	public void setReplaceExistFsa(String replaceExistFsa) {
		this.replaceExistFsa = replaceExistFsa;
	}

	public String getExistMirrorInd() {
		return existMirrorInd;
	}

	public void setExistMirrorInd(String existMirrorInd) {
		this.existMirrorInd = existMirrorInd;
	}

	public String getEdfRef() {
		return edfRef;
	}

	public void setEdfRef(String edfRef) {
		this.edfRef = edfRef;
	}
	
	public ImsOfferDetailDTO[] getOfferDtls() {
		return this.offerDtlList==null ? null : this.offerDtlList.toArray(new ImsOfferDetailDTO[this.offerDtlList.size()]);
	}

	public void setOfferDtls(ImsOfferDetailDTO[] offerDtls) {
		
		if (ArrayUtils.isEmpty(offerDtls)) {
			return;
		}
		if (this.offerDtlList == null) {
			this.offerDtlList = new ArrayList<ImsOfferDetailDTO>();
		}
		this.offerDtlList.addAll(Arrays.asList(offerDtls));
	}
	
	public void appendOfferDtl(ImsOfferDetailDTO pOfferDtl) {
		
		if (this.offerDtlList == null) {
			this.offerDtlList = new ArrayList<ImsOfferDetailDTO>();
		}
		this.offerDtlList.add(pOfferDtl);
	}
	
	public ImsOfferDetailDTO[] getOffersByType(String pType) {
		
		if (this.offerDtlList == null) {
			return null;
		}
		List<ImsOfferDetailDTO> resultList = new ArrayList<ImsOfferDetailDTO>();
		
		for (int i=0; i<this.offerDtlList.size(); ++i) {
			if (StringUtils.equals(pType, this.offerDtlList.get(i).getItemType())) {
				resultList.add(this.offerDtlList.get(i));
			}
		}
		return resultList.toArray(new ImsOfferDetailDTO[resultList.size()]);
	}

	public String getExistNowtvTvCd() {
		return existNowtvTvCd;
	}

	public void setExistNowtvTvCd(String existNowtvTvCd) {
		this.existNowtvTvCd = existNowtvTvCd;
	}

	public String getRepackNowtvInd() {
		return repackNowtvInd;
	}

	public void setRepackNowtvInd(String repackNowtvInd) {
		this.repackNowtvInd = repackNowtvInd;
	}

	public String getTerminatePcd() {
		return terminatePcd;
	}

	public void setTerminatePcd(String terminatePcd) {
		this.terminatePcd = terminatePcd;
	}

	public String getTerminateTv() {
		return terminateTv;
	}

	public void setTerminateTv(String terminateTv) {
		this.terminateTv = terminateTv;
	}

	public String getManualLineTypeInd() {
		return manualLineTypeInd;
	}

	public void setManualLineTypeInd(String manualLineTypeInd) {
		this.manualLineTypeInd = manualLineTypeInd;
	}

	public String getAutoUpgraded() {
		return autoUpgraded;
	}

	public void setAutoUpgraded(String autoUpgraded) {
		this.autoUpgraded = autoUpgraded;
	}

	public String getNoEyeFsa() {
		return noEyeFsa;
	}

	public void setNoEyeFsa(String noEyeFsa) {
		this.noEyeFsa = noEyeFsa;
	}

	public String getLostModem() {
		return lostModem;
	}

	public void setLostModem(String lostModem) {
		this.lostModem = lostModem;
	}

	public String getShareRentalRouter() {
		return shareRentalRouter;
	}

	public void setShareRentalRouter(String shareRentalRouter) {
		this.shareRentalRouter = shareRentalRouter;
	}

	public ImsL2JobDTO[] getImsL2Jobs() {
		return imsL2Jobs;
	}

	public void setImsL2Jobs(ImsL2JobDTO[] imsL2Jobs) {
		this.imsL2Jobs = imsL2Jobs;
	}

	public String getShareBrmWifi() {
		return shareBrmWifi;
	}

	public void setShareBrmWifi(String shareBrmWifi) {
		this.shareBrmWifi = shareBrmWifi;
	}
	
}
