package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;

public class LtsAcqBasketVasDetailFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4666727208142267331L;
	
	private Action formAction;
	
	List<ItemDetailDTO> prewireVasItemList;
	List<ItemDetailDTO> preinstallVasItemList;
	List<ItemDetailDTO> ffpVasHotItemList;
	List<ItemDetailDTO> ffpVasOtherItemList;
	List<ItemDetailDTO> ffpVasStaffItemList;
	
	List<ItemDetailDTO> existVasItemList;
	List<ItemDetailDTO> hotVasItemList;
	List<ItemDetailDTO> otherVasItemList;
	
	List<ItemDetailDTO> otherVasComtPeriodItemList;
	
	List<ItemDetailDTO> iddVasItemList;
	List<ItemDetailDTO> iddOutVasItemList;
	List<ItemDetailDTO> e0060VasItemList;
	List<ItemDetailDTO> e0060OutVasItemList;
	List<ItemDetailDTO> peFreeItemList;
	List<ItemDetailDTO> peSocketItemList;
	List<ItemDetailDTO> optAccessaryItemList;

	List<ItemDetailDTO> bvasItemList;
	List<ItemSetDetailDTO> smartWrtySetList;
	List<ItemSetDetailDTO> bundleVasSetList;
	
	private String controlBasketId;
	
	boolean optOutIdd;
	boolean optOut0060e;
	
	private boolean mth12Ffp;
	private boolean mth18Ffp;
	private boolean mth24Ffp;

	public enum Action {
		SUBMIT, FILTER;
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

	public List<ItemDetailDTO> getOptAccessaryItemList() {
		return optAccessaryItemList;
	}

	public void setOptAccessaryItemList(List<ItemDetailDTO> optAccessaryItemList) {
		this.optAccessaryItemList = optAccessaryItemList;
	}

	public List<ItemDetailDTO> getBvasItemList() {
		return bvasItemList;
	}

	public void setBvasItemList(List<ItemDetailDTO> bvasItemList) {
		this.bvasItemList = bvasItemList;
	}

	public List<ItemSetDetailDTO> getSmartWrtySetList() {
		return smartWrtySetList;
	}

	public void setSmartWrtySetList(List<ItemSetDetailDTO> smartWrtySetList) {
		this.smartWrtySetList = smartWrtySetList;
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

	public List<ItemDetailDTO> getPrewireVasItemList() {
		return prewireVasItemList;
	}

	public void setPrewireVasItemList(List<ItemDetailDTO> prewireVasItemList) {
		this.prewireVasItemList = prewireVasItemList;
	}

	public List<ItemDetailDTO> getPreinstallVasItemList() {
		return preinstallVasItemList;
	}

	public void setPreinstallVasItemList(List<ItemDetailDTO> preinstallVasItemList) {
		this.preinstallVasItemList = preinstallVasItemList;
	}
	
	public List<ItemDetailDTO> getFfpVasHotItemList() {
		return ffpVasHotItemList;
	}

	public void setFfpVasHotItemList(List<ItemDetailDTO> ffpVasHotItemList) {
		this.ffpVasHotItemList = ffpVasHotItemList;
	}

	public List<ItemDetailDTO> getFfpVasOtherItemList() {
		return ffpVasOtherItemList;
	}

	public void setFfpVasOtherItemList(List<ItemDetailDTO> ffpVasOtherItemList) {
		this.ffpVasOtherItemList = ffpVasOtherItemList;
	}

	public List<ItemDetailDTO> getFfpVasStaffItemList() {
		return ffpVasStaffItemList;
	}

	public void setFfpVasStaffItemList(List<ItemDetailDTO> ffpVasStaffItemList) {
		this.ffpVasStaffItemList = ffpVasStaffItemList;
	}

	public String getControlBasketId() {
		return controlBasketId;
	}

	public void setControlBasketId(String controlBasketId) {
		this.controlBasketId = controlBasketId;
	}

	public List<ItemDetailDTO> getOtherVasComtPeriodItemList() {
		return otherVasComtPeriodItemList;
	}

	public void setOtherVasComtPeriodItemList(
			List<ItemDetailDTO> otherVasComtPeriodItemList) {
		this.otherVasComtPeriodItemList = otherVasComtPeriodItemList;
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

	public List<ItemSetDetailDTO> getBundleVasSetList() {
		return bundleVasSetList;
	}

	public void setBundleVasSetList(List<ItemSetDetailDTO> bundleVasSetList) {
		this.bundleVasSetList = bundleVasSetList;
	}
}
