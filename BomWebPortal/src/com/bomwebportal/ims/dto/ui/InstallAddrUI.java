package com.bomwebportal.ims.dto.ui;

import java.util.List;

import com.bomwebportal.ims.dto.BandwidthDTO;
import com.bomwebportal.ims.dto.CustAddrDTO;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ServiceDetailDTO;

public class InstallAddrUI extends CustAddrDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8224114645621698178L;
	private String ActionInd;
	private String quickSearch;
	private String quickSearchSB;//Tony
	private List<ServiceDetailDTO> serviceDetailList;
	private List<HousingTypeDTO> housingTypeList;
	private List<BandwidthDTO> bandwidthList;
	
	private Boolean sbFilterVas;
	private String coverEyeX;
	private String coverPe;
	
	private String sbFlat;
	private String sbFloor ;
	
	private String hasVDSL;
	private String hasADSL6;
	private String hasADSL8;
	private String hasADSL18;
	private String ntvCoverage;
	
	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}
	
	public String getQuickSearch() {
		return quickSearch;
	}

	public void setQuickSearch(String quickSearch) {
		this.quickSearch = quickSearch;
	}

	public List<ServiceDetailDTO> getServiceDetailList() {
		return serviceDetailList;
	}

	public void setServiceDetailList(List<ServiceDetailDTO> serviceDetailList) {
		this.serviceDetailList = serviceDetailList;
	}

	public List<HousingTypeDTO> getHousingTypeList() {
		return housingTypeList;
	}

	public void setHousingTypeList(List<HousingTypeDTO> housingTypeList) {
		this.housingTypeList = housingTypeList;
	}

	public List<BandwidthDTO> getBandwidthList() {
		return bandwidthList;
	}

	public void setBandwidthList(List<BandwidthDTO> bandwidthList) {
		this.bandwidthList = bandwidthList;
	}

	public void setQuickSearchSB(String quickSearchSB) {
		this.quickSearchSB = quickSearchSB;
	}

	public String getQuickSearchSB() {
		return quickSearchSB;
	}
	
	public void setSbFilterVas(Boolean sbFilterVas) {
		this.sbFilterVas = sbFilterVas;
	}
	public Boolean getSbFilterVas() {
		return sbFilterVas;
	}

	public void setCoverEyeX(String coverEyeX) {
		this.coverEyeX = coverEyeX;
	}

	public String getCoverEyeX() {
		return coverEyeX;
	}

	public void setCoverPe(String coverPe) {
		this.coverPe = coverPe;
	}

	public String getCoverPe() {
		return coverPe;
	}

	public void setSbFlat(String sbFlat) {
		this.sbFlat = sbFlat;
	}

	public String getSbFlat() {
		return sbFlat;
	}

	public void setSbFloor(String sbFloor) {
		this.sbFloor = sbFloor;
	}

	public String getSbFloor() {
		return sbFloor;
	}

	public void setHasVDSL(String hasVDSL) {
		this.hasVDSL = hasVDSL;
	}

	public String getHasVDSL() {
		return hasVDSL;
	}

	public void setHasADSL8(String hasADSL8) {
		this.hasADSL8 = hasADSL8;
	}

	public String getHasADSL8() {
		return hasADSL8;
	}

	public void setHasADSL18(String hasADSL18) {
		this.hasADSL18 = hasADSL18;
	}

	public String getHasADSL18() {
		return hasADSL18;
	}

	public void setNtvCoverage(String ntvCoverage) {
		this.ntvCoverage = ntvCoverage;
	}

	public String getNtvCoverage() {
		return ntvCoverage;
	}

	public void setHasADSL6(String hasADSL6) {
		this.hasADSL6 = hasADSL6;
	}

	public String getHasADSL6() {
		return hasADSL6;
	}

}