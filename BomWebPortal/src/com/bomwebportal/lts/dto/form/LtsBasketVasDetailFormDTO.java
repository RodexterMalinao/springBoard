package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;

public class LtsBasketVasDetailFormDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7218902959781180526L;
	
	private Action formAction;
	private boolean selectedFfpOnly;
	
	List<ItemDetailDTO> existVasItemList;
	List<ItemDetailDTO> hotVasItemList;
	List<ItemDetailDTO> otherVasItemList;
	List<ItemDetailDTO> iddVasItemList;
	List<ItemDetailDTO> iddOutVasItemList;
	List<ItemDetailDTO> e0060VasItemList;
	List<ItemDetailDTO> e0060OutVasItemList;
	List<ItemDetailDTO> peFreeItemList;
	List<ItemDetailDTO> peSocketItemList;
	List<ItemDetailDTO> optAccessaryItemList;
	
	List<ItemDetailDTO> ffpHotItemList;
	List<ItemDetailDTO> ffpOtherItemList;
	List<ItemDetailDTO> ffpStaffItemList;
	
	List<ItemDetailProfileLtsDTO> profileAutoOutVasItemList;
	List<ItemDetailProfileLtsDTO> profileExistingVasItemList;
	
	List<ItemDetailProfileLtsDTO> profileAutoChangeTpItemList;
	List<ItemDetailProfileLtsDTO> profileAutoOutTpItemList;
	List<ItemDetailProfileLtsDTO> profileExistingTpItemList;
	
	List<ItemDetailProfileLtsDTO> profileExpiredItemList;
	
	List<ItemDetailProfileLtsDTO> imsProfileAutoOutItemList;
	List<ItemDetailProfileLtsDTO> imsProfileExistingItemList;
	List<ItemDetailProfileLtsDTO> imsProfileIngoreItemList;
	
	List<ItemDetailDTO> bvasItemList;
	
	List<ItemSetDetailDTO> teamVasSetList;
	List<ItemSetDetailDTO> smartWrtySetList;
	List<ItemSetDetailDTO> bundleVasSetList;
	
	//For Standalone VAS
	List<ItemDetailDTO> moovItemList;
	List<ItemDetailDTO> contentItemList;
	List<ItemSetDetailDTO> contItemSetList;
	
	boolean optOutIdd;
	boolean optOut0060e;

	private boolean hasFfp;
	private boolean mth12Ffp;
	private boolean mth18Ffp;
	private boolean mth24Ffp;
	
	String bundle2gNum;
	
	public enum Action {
		SUBMIT, UPDATE, FILTER;
	}
	
	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}
	public List<ItemDetailDTO> getExistVasItemList() {
		return existVasItemList;
	}
	public void setExistVasItemList(List<ItemDetailDTO> existVasItemList) {
		this.existVasItemList = existVasItemList;
	}
	public List<ItemDetailDTO> getHotVasItemList() {
		return hotVasItemList;
	}
	public void setHotVasItemList(List<ItemDetailDTO> hotVasItemList) {
		this.hotVasItemList = hotVasItemList;
	}
	public List<ItemDetailDTO> getOtherVasItemList() {
		return otherVasItemList;
	}
	public void setOtherVasItemList(List<ItemDetailDTO> otherVasItemList) {
		this.otherVasItemList = otherVasItemList;
	}
	public List<ItemDetailDTO> getIddVasItemList() {
		return iddVasItemList;
	}
	public void setIddVasItemList(List<ItemDetailDTO> iddVasItemList) {
		this.iddVasItemList = iddVasItemList;
	}
	public List<ItemDetailDTO> getIddOutVasItemList() {
		return iddOutVasItemList;
	}
	public void setIddOutVasItemList(List<ItemDetailDTO> iddOutVasItemList) {
		this.iddOutVasItemList = iddOutVasItemList;
	}
	public List<ItemDetailDTO> getE0060VasItemList() {
		return e0060VasItemList;
	}
	public void setE0060VasItemList(List<ItemDetailDTO> e0060VasItemList) {
		this.e0060VasItemList = e0060VasItemList;
	}
	public List<ItemDetailDTO> getE0060OutVasItemList() {
		return e0060OutVasItemList;
	}
	public void setE0060OutVasItemList(List<ItemDetailDTO> e0060OutVasItemList) {
		this.e0060OutVasItemList = e0060OutVasItemList;
	}
	public List<ItemDetailDTO> getPeFreeItemList() {
		return peFreeItemList;
	}
	public void setPeFreeItemList(List<ItemDetailDTO> peFreeItemList) {
		this.peFreeItemList = peFreeItemList;
	}
	public List<ItemDetailDTO> getPeSocketItemList() {
		return peSocketItemList;
	}
	public void setPeSocketItemList(List<ItemDetailDTO> peSocketItemList) {
		this.peSocketItemList = peSocketItemList;
	}
	public boolean isOptOutIdd() {
		return optOutIdd;
	}
	public void setOptOutIdd(boolean optOutIdd) {
		this.optOutIdd = optOutIdd;
	}
	public boolean isOptOut0060e() {
		return optOut0060e;
	}
	public void setOptOut0060e(boolean optOut0060e) {
		this.optOut0060e = optOut0060e;
	}
	public List<ItemDetailProfileLtsDTO> getProfileAutoOutVasItemList() {
		return profileAutoOutVasItemList;
	}
	public void setProfileAutoOutVasItemList(
			List<ItemDetailProfileLtsDTO> profileAutoOutVasItemList) {
		this.profileAutoOutVasItemList = profileAutoOutVasItemList;
	}
	public List<ItemDetailProfileLtsDTO> getProfileAutoChangeTpItemList() {
		return profileAutoChangeTpItemList;
	}
	public void setProfileAutoChangeTpItemList(
			List<ItemDetailProfileLtsDTO> profileAutoChangeTpItemList) {
		this.profileAutoChangeTpItemList = profileAutoChangeTpItemList;
	}
	public List<ItemDetailProfileLtsDTO> getProfileAutoOutTpItemList() {
		return profileAutoOutTpItemList;
	}
	public void setProfileAutoOutTpItemList(
			List<ItemDetailProfileLtsDTO> profileAutoOutTpItemList) {
		this.profileAutoOutTpItemList = profileAutoOutTpItemList;
	}
	public List<ItemDetailProfileLtsDTO> getProfileExistingTpItemList() {
		return profileExistingTpItemList;
	}
	public void setProfileExistingTpItemList(
			List<ItemDetailProfileLtsDTO> profileExistingTpItemList) {
		this.profileExistingTpItemList = profileExistingTpItemList;
	}
	public List<ItemDetailDTO> getOptAccessaryItemList() {
		return optAccessaryItemList;
	}
	public void setOptAccessaryItemList(List<ItemDetailDTO> optAccessaryItemList) {
		this.optAccessaryItemList = optAccessaryItemList;
	}
	public List<ItemDetailProfileLtsDTO> getProfileExistingVasItemList() {
		return profileExistingVasItemList;
	}
	public void setProfileExistingVasItemList(
			List<ItemDetailProfileLtsDTO> profileExistingVasItemList) {
		this.profileExistingVasItemList = profileExistingVasItemList;
	}
	public List<ItemDetailProfileLtsDTO> getImsProfileAutoOutItemList() {
		return imsProfileAutoOutItemList;
	}
	public void setImsProfileAutoOutItemList(
			List<ItemDetailProfileLtsDTO> imsProfileAutoOutItemList) {
		this.imsProfileAutoOutItemList = imsProfileAutoOutItemList;
	}
	public List<ItemDetailProfileLtsDTO> getImsProfileExistingItemList() {
		return imsProfileExistingItemList;
	}
	public void setImsProfileExistingItemList(
			List<ItemDetailProfileLtsDTO> imsProfileExistingItemList) {
		this.imsProfileExistingItemList = imsProfileExistingItemList;
	}
	public List<ItemDetailProfileLtsDTO> getProfileExpiredItemList() {
		return profileExpiredItemList;
	}
	public void setProfileExpiredItemList(
			List<ItemDetailProfileLtsDTO> profileExpiredItemList) {
		this.profileExpiredItemList = profileExpiredItemList;
	}
	public List<ItemDetailDTO> getBvasItemList() {
		return bvasItemList;
	}
	public void setBvasItemList(List<ItemDetailDTO> bvasItemList) {
		this.bvasItemList = bvasItemList;
	}
	public List<ItemDetailProfileLtsDTO> getImsProfileIngoreItemList() {
		return imsProfileIngoreItemList;
	}
	public void setImsProfileIngoreItemList(
			List<ItemDetailProfileLtsDTO> imsProfileIngoreItemList) {
		this.imsProfileIngoreItemList = imsProfileIngoreItemList;
	}
	public List<ItemDetailDTO> getFfpHotItemList() {
		return ffpHotItemList;
	}
	public void setFfpHotItemList(List<ItemDetailDTO> ffpHotItemList) {
		this.ffpHotItemList = ffpHotItemList;
	}
	public List<ItemDetailDTO> getFfpOtherItemList() {
		return ffpOtherItemList;
	}
	public void setFfpOtherItemList(List<ItemDetailDTO> ffpOtherItemList) {
		this.ffpOtherItemList = ffpOtherItemList;
	}
	public List<ItemDetailDTO> getFfpStaffItemList() {
		return ffpStaffItemList;
	}
	public void setFfpStaffItemList(List<ItemDetailDTO> ffpStaffItemList) {
		this.ffpStaffItemList = ffpStaffItemList;
	}
	public List<ItemSetDetailDTO> getTeamVasSetList() {
		return teamVasSetList;
	}
	public void setTeamVasSetList(List<ItemSetDetailDTO> teamVasSetList) {
		this.teamVasSetList = teamVasSetList;
	}
	public String getBundle2gNum() {
		return bundle2gNum;
	}
	public void setBundle2gNum(String bundle2gNum) {
		this.bundle2gNum = bundle2gNum;
	}
	public List<ItemSetDetailDTO> getSmartWrtySetList() {
		return smartWrtySetList;
	}
	public void setSmartWrtySetList(List<ItemSetDetailDTO> smartWrtySetList) {
		this.smartWrtySetList = smartWrtySetList;
	}
	public List<ItemSetDetailDTO> getBundleVasSetList() {
		return bundleVasSetList;
	}
	public void setBundleVasSetList(List<ItemSetDetailDTO> bundleVasSetList) {
		this.bundleVasSetList = bundleVasSetList;
	}
	public boolean isSelectedFfpOnly() {
		return selectedFfpOnly;
	}
	public void setSelectedFfpOnly(boolean selectedFfpOnly) {
		this.selectedFfpOnly = selectedFfpOnly;
	}
	public List<ItemDetailDTO> getMoovItemList() {
		return moovItemList;
	}
	public void setMoovItemList(List<ItemDetailDTO> moovItemList) {
		this.moovItemList = moovItemList;
	}
	public List<ItemDetailDTO> getContentItemList() {
		return contentItemList;
	}
	public void setContentItemList(List<ItemDetailDTO> contentItemList) {
		this.contentItemList = contentItemList;
	}
	public List<ItemSetDetailDTO> getContItemSetList() {
		return contItemSetList;
	}
	public void setContItemSetList(List<ItemSetDetailDTO> contItemSetList) {
		this.contItemSetList = contItemSetList;
	}
	public boolean isMth12Ffp() {
		return mth12Ffp;
	}
	public void setMth12Ffp(boolean mth12Ffp) {
		this.mth12Ffp = mth12Ffp;
	}
	public boolean isMth18Ffp() {
		return mth18Ffp;
	}
	public void setMth18Ffp(boolean mth18Ffp) {
		this.mth18Ffp = mth18Ffp;
	}
	public boolean isMth24Ffp() {
		return mth24Ffp;
	}
	public void setMth24Ffp(boolean mth24Ffp) {
		this.mth24Ffp = mth24Ffp;
	}
	public boolean isHasFfp() {
		return hasFfp;
	}
	public void setHasFfp(boolean hasFfp) {
		this.hasFfp = hasFfp;
	}

}
