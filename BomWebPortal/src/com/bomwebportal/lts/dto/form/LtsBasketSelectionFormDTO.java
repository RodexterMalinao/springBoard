package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.DeviceDetailDTO;

public class LtsBasketSelectionFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -682894449839670505L;

	private String selectedBasketId;
	private String filterBasketId;
	private Action formAction;
	
	private List<BasketDetailDTO> hotBasketList;
	private List<BasketDetailDTO> regularBasketList;

	private boolean filterContractMthGe24 = true;
	private boolean filterContractMthEq21 = true;
	private boolean filterContractMthEq18 = true;
	private boolean filterContractMthEq15 = true;
	private boolean filterContractMthLe12 = true;
	private String filterDevice;
	private String filterProjectCd;
	private boolean filterOsTypeAndroid;
	private boolean filterOsTypeiOS;
	
	private DeviceDetailDTO selectedEyeDevice;
	private int basketTabIndex = 0;
	private BasketTab basketTab;

	public enum BasketTab {
		UPG_MASS,
		RET_MASS_EYE,
		RET_MASS_DEL,
		RET_PT_EYE,
		RET_PT_DEL;
	}
	
	public enum Action {
		SUBMIT, FILTER
	}
	
	public String getSelectedBasketId() {
		return selectedBasketId;
	}

	public void setSelectedBasketId(String selectedBasketId) {
		this.selectedBasketId = selectedBasketId;
	}

	public String getFilterBasketId() {
		return filterBasketId;
	}

	public void setFilterBasketId(String filterBasketId) {
		this.filterBasketId = filterBasketId;
	}

	public Action getFormAction() {
		return formAction;
	}

	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}

	public List<BasketDetailDTO> getHotBasketList() {
		return hotBasketList;
	}

	public void setHotBasketList(List<BasketDetailDTO> hotBasketList) {
		this.hotBasketList = hotBasketList;
	}

	public List<BasketDetailDTO> getRegularBasketList() {
		return regularBasketList;
	}

	public void setRegularBasketList(List<BasketDetailDTO> regularBasketList) {
		this.regularBasketList = regularBasketList;
	}

	public DeviceDetailDTO getSelectedEyeDevice() {
		return selectedEyeDevice;
	}

	public void setSelectedEyeDevice(DeviceDetailDTO selectedEyeDevice) {
		this.selectedEyeDevice = selectedEyeDevice;
	}
	
	public boolean isFilterContractMthGe24() {
		return filterContractMthGe24;
	}

	public void setFilterContractMthGe24(boolean filterContractMthGe24) {
		this.filterContractMthGe24 = filterContractMthGe24;
	}

	public boolean isFilterContractMthEq21() {
		return filterContractMthEq21;
	}

	public void setFilterContractMthEq21(boolean filterContractMthLe21) {
		this.filterContractMthEq21 = filterContractMthLe21;
	}

	public boolean isFilterContractMthEq18() {
		return filterContractMthEq18;
	}

	public void setFilterContractMthEq18(boolean filterContractMthEq18) {
		this.filterContractMthEq18 = filterContractMthEq18;
	}

	public boolean isFilterContractMthEq15() {
		return filterContractMthEq15;
	}

	public void setFilterContractMthEq15(boolean filterContractMthLe15) {
		this.filterContractMthEq15 = filterContractMthLe15;
	}

	public boolean isFilterContractMthLe12() {
		return filterContractMthLe12;
	}

	public void setFilterContractMthLe12(boolean filterContractMthLe12) {
		this.filterContractMthLe12 = filterContractMthLe12;
	}

	public String getFilterDevice() {
		return filterDevice;
	}

	public void setFilterDevice(String filterDevice) {
		this.filterDevice = filterDevice;
	}

	public BasketTab getBasketTab() {
		return basketTab;
	}

	public void setBasketTab(BasketTab basketTab) {
		this.basketTab = basketTab;
	}

	public int getBasketTabIndex() {
		return basketTabIndex;
	}

	public void setBasketTabIndex(int basketTabIndex) {
		this.basketTabIndex = basketTabIndex;
	}

	public String getFilterProjectCd() {
		return filterProjectCd;
	}

	public void setFilterProjectCd(String filterProjectCd) {
		this.filterProjectCd = filterProjectCd;
	}

	public boolean isFilterOsTypeAndroid() {
		return filterOsTypeAndroid;
	}

	public void setFilterOsTypeAndroid(boolean filterOsTypeAndroid) {
		this.filterOsTypeAndroid = filterOsTypeAndroid;
	}

	public boolean isFilterOsTypeiOS() {
		return filterOsTypeiOS;
	}

	public void setFilterOsTypeiOS(boolean filterOsTypeiOS) {
		this.filterOsTypeiOS = filterOsTypeiOS;
	}



}
