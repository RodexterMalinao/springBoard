package com.bomwebportal.lts.dto.profile;


import java.io.Serializable;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.util.LtsProfileConstant;


public class ServiceDetailProfileLtsDTO implements Serializable {

	private static final long serialVersionUID = 556782786775044601L;
	
	private String ccSrvId = null;
	private String srvNum = null;
	private String ngfl3gInd = null;
	private String srvType = null;
	private String datCd = null;
	private String tariff = null;
	private String inventStatus = null;
	private String duplexNum = null;
	private boolean twinLineInd = false;
	private String pendingOcid = null;
	private String pendingOcSrd = null;
	private String pendingOrdType = null;
	private boolean pendingOrderOverdue = false;
	private int imsTenure = 0;
	private int ltsTenure = 0;
	private boolean twoNBuildInd = false;
	private boolean duplexTwoNBuildInd = false;
	private String searchCriteriaDn = null;
	private AccountDetailProfileLtsDTO[] accounts = null;
	private String inventStsDesc = null;
	private String[] missingCorePsefs = null;
	private AddressDetailProfileLtsDTO address = null;
	private CustomerDetailProfileLtsDTO primaryCust = null;
	private ItemDetailProfileLtsDTO[] itemDetails = null;
	private boolean isDnExchFrozen = false;
	private boolean isDuplexDnExchFrozen = false;
	private String ccSrvMemNum = null;
	private String duplexCcSrvMemNum = null;
	private boolean isSharedBsn = false;
	private TenureDTO[] imsTenureDtls = null;
	private String existEyeType = null;
	private String waiveCount = null;
	private String bundleTvInd = null;
	private String bundleTvCredit = null;
	private String effStartDate = null;
	private String effEndDate = null;
	private String eyeDeviceType = null;
	
	private double ltsSrvTenure = 0;
	private ServiceGroupProfileDTO srvGrp = null;
	private OfferDetailProfileLtsDTO[] offerProfiles = null;
	private OfferDetailProfileLtsDTO[] endedOfferProfiles = null;
	private OfferActionLtsDTO[] deviceOfferActions = null;
	private OfferActionLtsDTO[] channelOfferActions = null;
	private OfferActionLtsDTO[] bundleTvOfferActions = null;
	private OfferActionLtsDTO[] restrictedOfferActions = null;
	private FsaServiceDetailOutputDTO eyeFsaProfile;
	private ItemDetailProfileLtsDTO[] endedItemDetails = null;
	
	public String getCcSrvId() {
		return ccSrvId;
	}

	public void setCcSrvId(String ccSrvId) {
		this.ccSrvId = ccSrvId;
	}

	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getNgfl3gInd() {
		return ngfl3gInd;
	}

	public void setNgfl3gInd(String ngfl3gInd) {
		this.ngfl3gInd = ngfl3gInd;
	}

	public String getSrvType() {
		return srvType;
	}

	public void setSrvType(String srvType) {
		this.srvType = srvType;
	}

	public String getDatCd() {
		return datCd;
	}

	public void setDatCd(String datCd) {
		this.datCd = datCd;
	}

	public String getTariff() {
		return tariff;
	}

	public void setTariff(String tariff) {
		this.tariff = tariff;
	}

	public String getInventStatus() {
		return inventStatus;
	}

	public void setInventStatus(String inventStatus) {
		this.inventStatus = inventStatus;
	}

	public String getDuplexNum() {
		return duplexNum;
	}

	public void setDuplexNum(String duplexNum) {
		this.duplexNum = duplexNum;
	}

	public boolean isTwinLineInd() {
		return twinLineInd;
	}

	public void setTwinLineInd(boolean twinLineInd) {
		this.twinLineInd = twinLineInd;
	}

	public String getPendingOcid() {
		return pendingOcid;
	}

	public void setPendingOcid(String pendingOcid) {
		this.pendingOcid = pendingOcid;
	}

	public String getPendingOcSrd() {
		return pendingOcSrd;
	}

	public void setPendingOcSrd(String pendingOcSrd) {
		this.pendingOcSrd = pendingOcSrd;
	}

	public String getPendingOrdType() {
		return pendingOrdType;
	}

	public void setPendingOrdType(String pendingOrdType) {
		this.pendingOrdType = pendingOrdType;
	}

	public boolean isPendingOrderOverdue() {
		return pendingOrderOverdue;
	}

	public void setPendingOrderOverdue(boolean pendingOrderOverdue) {
		this.pendingOrderOverdue = pendingOrderOverdue;
	}

	public AccountDetailProfileLtsDTO[] getAccounts() {
		return accounts;
	}

	public void setAccounts(AccountDetailProfileLtsDTO[] accounts) {
		this.accounts = accounts;
	}

	public AddressDetailProfileLtsDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDetailProfileLtsDTO address) {
		this.address = address;
	}

	public boolean isTwoNBuildInd() {
		return twoNBuildInd;
	}

	public void setTwoNBuildInd(boolean twoNBuildInd) {
		this.twoNBuildInd = twoNBuildInd;
	}

	public boolean isDuplexTwoNBuildInd() {
		return duplexTwoNBuildInd;
	}

	public void setDuplexTwoNBuildInd(boolean duplexTwoNBuildInd) {
		this.duplexTwoNBuildInd = duplexTwoNBuildInd;
	}

	public CustomerDetailProfileLtsDTO getPrimaryCust() {
		return primaryCust;
	}

	public void setPrimaryCust(CustomerDetailProfileLtsDTO primaryCust) {
		this.primaryCust = primaryCust;
	}

	public String getSearchCriteriaDn() {
		return searchCriteriaDn;
	}

	public void setSearchCriteriaDn(String searchCriteriaDn) {
		this.searchCriteriaDn = searchCriteriaDn;
	}

	public String[] getMissingCorePsefs() {
		return missingCorePsefs;
	}

	public void setMissingCorePsefs(String[] missingCorePsefs) {
		this.missingCorePsefs = missingCorePsefs;
	}

	public String getInventStsDesc() {
		return inventStsDesc;
	}

	public void setInventStsDesc(String inventStsDesc) {
		this.inventStsDesc = inventStsDesc;
	}

	public ItemDetailProfileLtsDTO[] getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(ItemDetailProfileLtsDTO[] itemDetails) {
		this.itemDetails = itemDetails;
	}

	public boolean isDnExchFrozen() {
		return isDnExchFrozen;
	}

	public void setDnExchFrozen(boolean isDnExchFrozen) {
		this.isDnExchFrozen = isDnExchFrozen;
	}

	public boolean isDuplexDnExchFrozen() {
		return isDuplexDnExchFrozen;
	}

	public void setDuplexDnExchFrozen(boolean isDuplexDnExchFrozen) {
		this.isDuplexDnExchFrozen = isDuplexDnExchFrozen;
	}

	public String getCcSrvMemNum() {
		return ccSrvMemNum;
	}

	public void setCcSrvMemNum(String ccSrvMemNum) {
		this.ccSrvMemNum = ccSrvMemNum;
	}

	public String getDuplexCcSrvMemNum() {
		return duplexCcSrvMemNum;
	}

	public void setDuplexCcSrvMemNum(String duplexCcSrvMemNum) {
		this.duplexCcSrvMemNum = duplexCcSrvMemNum;
	}

	public String getBundleTvCredit() {
		return bundleTvCredit;
	}

	public void setBundleTvCredit(String bundleTvCredit) {
		this.bundleTvCredit = bundleTvCredit;
	}

	public boolean isSharedBsn() {
		return isSharedBsn;
	}

	public void setSharedBsn(boolean isSharedBsn) {
		this.isSharedBsn = isSharedBsn;
	}

	public TenureDTO[] getImsTenureDtls() {
		return this.imsTenureDtls;
	}

	public void setImsTenureDtls(TenureDTO[] pImsTenureDtls) {
		
		this.imsTenureDtls = pImsTenureDtls;
		this.imsTenure = 0;
		
		for (int i=0; pImsTenureDtls!=null && i<pImsTenureDtls.length; ++i) {
			if (StringUtils.equals(pImsTenureDtls[i].getCustNum(), this.getPrimaryCust().getParentCustNum()) && pImsTenureDtls[i].getTenure() > this.imsTenure) {
				this.imsTenure = pImsTenureDtls[i].getTenure();
			}
		}
	}

	public String getExistEyeType() {
		return existEyeType;
	}

	public void setExistEyeType(String existEyeType) {
		this.existEyeType = existEyeType;
	}

	public boolean isExistStaffPlan() {
		if (ArrayUtils.isEmpty(itemDetails)) {
			return false;
		}

		for (ItemDetailProfileLtsDTO profileItem : itemDetails) {
			if (profileItem.getItemDetail() != null
					&& StringUtils.containsIgnoreCase(profileItem.getItemDetail().getTpCatg(), "STAFF")) {
				return true;
			}
		}

		return false;
	}

	public String getWaiveCount() {
		return waiveCount;
	}

	public void setWaiveCount(String waiveCount) {
		this.waiveCount = waiveCount;
	}

	public ServiceGroupProfileDTO getSrvGrp() {
		return srvGrp;
	}

	public void setSrvGrp(ServiceGroupProfileDTO srvGrp) {
		this.srvGrp = srvGrp;
	}

	public OfferDetailProfileLtsDTO[] getOfferProfiles() {
		return offerProfiles;
	}

	public void setOfferProfiles(OfferDetailProfileLtsDTO[] offerProfiles) {
		this.offerProfiles = offerProfiles;
	}

	public OfferActionLtsDTO[] getDeviceOfferActions() {
		return deviceOfferActions;
	}

	public void setDeviceOfferActions(OfferActionLtsDTO[] deviceOfferActions) {
		this.deviceOfferActions = deviceOfferActions;
	}

	public String getExistEyeTypeDisplay() {
		if(StringUtils.isNotBlank(this.existEyeType)){
			if(this.srvGrp != null && this.srvGrp.getGrpMems() != null){
				if(this.srvGrp.getMemberByType(LtsProfileConstant.DAT_CD_DEL) != null){
					return this.existEyeType + " (PE)";
				}
			}
		}
		return this.existEyeType;
	}

	public String getRelatedEyeFsa() {
		if(this.srvGrp != null && this.srvGrp.getGrpMems() != null){
			ServiceGroupMemberProfileDTO mem = this.srvGrp.getMemberByType(LtsProfileConstant.SERVICE_TYPE_IMS);
			if(mem != null){
				return mem.getSrvNum();
			}
		}
		return null;
	}

	public FsaServiceDetailOutputDTO getEyeFsaProfile() {
		return eyeFsaProfile;
	}

	public void setEyeFsaProfile(FsaServiceDetailOutputDTO eyeFsaProfile) {
		this.eyeFsaProfile = eyeFsaProfile;
	}

	public double getLtsSrvTenure() {
		return ltsSrvTenure;
	}

	public void setLtsSrvTenure(double ltsSrvTenure) {
		this.ltsSrvTenure = ltsSrvTenure;
	}

	public String getBundleTvInd() {
		return bundleTvInd;
	}

	public void setBundleTvInd(String bundleTvInd) {
		this.bundleTvInd = bundleTvInd;
	}
	
	

	public OfferActionLtsDTO[] getChannelOfferActions() {
		return channelOfferActions;
	}

	public OfferActionLtsDTO[] getBundleTvOfferActions() {
		return bundleTvOfferActions;
	}

	public void setBundleTvOfferActions(OfferActionLtsDTO[] bundleTvOfferActions) {
		this.bundleTvOfferActions = bundleTvOfferActions;
	}

	public void setChannelOfferActions(OfferActionLtsDTO[] channelOfferActions) {
		this.channelOfferActions = channelOfferActions;
	}

	public String getEffStartDate() {
		return effStartDate;
	}

	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}

	public String getEffEndDate() {
		return effEndDate;
	}

	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}

	public String getEyeDeviceType() {
		return eyeDeviceType;
	}

	public void setEyeDeviceType(String eyeDeviceType) {
		this.eyeDeviceType = eyeDeviceType;
	}

	public OfferActionLtsDTO[] getRestrictedOfferActions() {
		return restrictedOfferActions;
	}

	public void setRestrictedOfferActions(OfferActionLtsDTO[] restrictedOfferActions) {
		this.restrictedOfferActions = restrictedOfferActions;
	}

	public OfferDetailProfileLtsDTO[] getEndedOfferProfiles() {
		return endedOfferProfiles;
	}

	public void setEndedOfferProfiles(OfferDetailProfileLtsDTO[] endedOfferProfiles) {
		this.endedOfferProfiles = endedOfferProfiles;
	}

	public ItemDetailProfileLtsDTO[] getEndedItemDetails() {
		return endedItemDetails;
	}

	public void setEndedItemDetails(ItemDetailProfileLtsDTO[] endedItemDetails) {
		this.endedItemDetails = endedItemDetails;
	}

	public int getImsTenure() {
		return imsTenure;
	}

	public void setImsTenure(int imsTenure) {
		this.imsTenure = imsTenure;
	}

	public int getLtsTenure() {
		return ltsTenure;
	}

	public void setLtsTenure(int ltsTenure) {
		this.ltsTenure = ltsTenure;
	}
	
	
}

