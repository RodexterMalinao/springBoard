package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class ImsServiceSrdDTO implements Serializable {

	private String isBlacklistAddr;
	private String isBlacklistCust;
	private String isPccwFixedLine;
	private Date appntDateTime;
	private String serviceBoundary;
	private String isAllowed;
	private String is2NBuilding;
	private String rfsStatus;
	private Date rfsDate;
	private String rfsIsFuture;
	private String rfsIsNotAvailable;
	private String rejectCd;
	private String rejectDesc;
	private List<ServiceDetailDTO> serviceDetailList = new ArrayList<ServiceDetailDTO>();
	private List<HousingTypeDTO> housingTypeList;
	private List<BandwidthDTO> bandwidthList;
	private int fieldPermitDay;
	private List<HousingTypeDescDTO> housingTypeDescList;	
	private String OtChrgType;
	private String submitAllowed;
		

	public String getSubmitAllowed() {
		if(submitAllowed == null)
			submitAllowed = "";
		return submitAllowed;
	}

	public void setSubmitAllowed(String submitAllowed) {
		this.submitAllowed = submitAllowed;
	}

	public String getIsBlacklistAddr() {
		return isBlacklistAddr;
	}

	public void setIsBlacklistAddr(String isBlacklistAddr) {
		this.isBlacklistAddr = isBlacklistAddr;
	}

	public String getIsBlacklistCust() {
		return isBlacklistCust;
	}

	public void setIsBlacklistCust(String isBlacklistCust) {
		this.isBlacklistCust = isBlacklistCust;
	}

	public String getIsPccwFixedLine() {
		return isPccwFixedLine;
	}

	public void setIsPccwFixedLine(String isPccwFixedLine) {
		this.isPccwFixedLine = isPccwFixedLine;
	}

	public Date getAppntDateTime() {
		return appntDateTime;
	}

	public void setAppntDateTime(Date appntDateTime) {
		this.appntDateTime = appntDateTime;
	}

	public String getServiceBoundary() {
		return serviceBoundary;
	}

	public void setServiceBoundary(String serviceBoundary) {
		this.serviceBoundary = serviceBoundary;
	}

	public String getIsAllowed() {
		return isAllowed;
	}

	public void setIsAllowed(String isAllowed) {
		this.isAllowed = isAllowed;
	}

	public String getIs2NBuilding() {
		return is2NBuilding;
	}

	public void setIs2NBuilding(String is2NBuilding) {
		this.is2NBuilding = is2NBuilding;
	}

	public String getRfsStatus() {
		return rfsStatus;
	}

	public void setRfsStatus(String rfsStatus) {
		this.rfsStatus = rfsStatus;
	}

	public Date getRfsDate() {
		return rfsDate;
	}

	public void setRfsDate(Date rfsDate) {
		this.rfsDate = rfsDate;
	}

	public String getRfsIsFuture() {
		return rfsIsFuture;
	}

	public void setRfsIsFuture(String rfsIsFuture) {
		this.rfsIsFuture = rfsIsFuture;
	}

	public String getRfsIsNotAvailable() {
		return rfsIsNotAvailable;
	}

	public void setRfsIsNotAvailable(String rfsIsNotAvailable) {
		this.rfsIsNotAvailable = rfsIsNotAvailable;
	}

	public String getRejectCd() {
		return rejectCd;
	}

	public void setRejectCd(String rejectCd) {
		this.rejectCd = rejectCd;
	}

	public String getRejectDesc() {
		return rejectDesc;
	}

	public void setRejectDesc(String rejectDesc) {
		this.rejectDesc = rejectDesc;
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

	public int getFieldPermitDay() {
		return fieldPermitDay;
	}

	public void setFieldPermitDay(int fieldPermitDay) {
		this.fieldPermitDay = fieldPermitDay;
	}

	public List<HousingTypeDescDTO> getHousingTypeDescList() {
		return housingTypeDescList;
	}

	public void setHousingTypeDescList(List<HousingTypeDescDTO> housingTypeDescList) {
		this.housingTypeDescList = housingTypeDescList;
	}

	@Override
	public String toString() {
		return "ImsServiceSrdDTO [isBlacklistAddr=" + isBlacklistAddr
				+ ", isBlacklistCust=" + isBlacklistCust + ", isPccwFixedLine="
				+ isPccwFixedLine + ", appntDateTime=" + appntDateTime
				+ ", serviceBoundary=" + serviceBoundary + ", isAllowed="
				+ isAllowed + ", is2NBuilding=" + is2NBuilding + ", rfsStatus="
				+ rfsStatus + ", rfsDate=" + rfsDate + ", rfsIsFuture="
				+ rfsIsFuture + ", rfsIsNotAvailable=" + rfsIsNotAvailable
				+ ", rejectCd=" + rejectCd + ", rejectDesc=" + rejectDesc
				+ ", serviceDetailList=" + serviceDetailList
				+ ", housingTypeList=" + housingTypeList + ", bandwidthList="
				+ bandwidthList + ", fieldPermitDay=" + fieldPermitDay
				+ ", housingTypeDescList=" + housingTypeDescList + "]";
	}

	public void setOtChrgType(String otChrgType) {
		OtChrgType = otChrgType;
	}

	public String getOtChrgType() {
		return OtChrgType;
	}
	
	public boolean hasBandwidth(String bw){
		
		for (BandwidthDTO b : bandwidthList){
			if (StringUtils.equals(b.getBandwidth(), bw))
				return true;
		}
		
		return false;
	}
	
	public boolean containBandwidth(List<String> bw) {
		for (BandwidthDTO b : bandwidthList) {
			if (!StringUtils.isEmpty(b.getBandwidth()) && bw.contains(b.getBandwidth())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasBandwidthGreater(double bw) {
		for (BandwidthDTO b : bandwidthList) {
			double o;
			try {
				o = Double.parseDouble(b.getBandwidth());
				if (o >= bw) {
					return true;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public String getNtvCoverage(){
		Integer ponC = 0;
		Integer vdslC = 0;
		Integer adslC = 0;
		Integer Coverage = 0;
		submitAllowed = "Y";
		String ntvCoverage = "";
		for(ServiceDetailDTO dto: serviceDetailList){
			if("PON".equals(dto.getTechnology())&&"Y".equals(dto.getTechnologySupported())&&"N".equals(dto.getIsDeadCase())){
				ponC =("Y".equals(dto.getHasSPHDTV()))?3:(("Y".equals(dto.getHasHDTV()))?2:(("Y".equals(dto.getHasSDTV()))?1:0));			
				submitAllowed = "Y";
			}else if ("VDSL".equals(dto.getTechnology())&&"Y".equals(dto.getTechnologySupported())&&"N".equals(dto.getIsDeadCase())){
				vdslC =("Y".equals(dto.getHasSPHDTV()))?3:(("Y".equals(dto.getHasHDTV()))?2:(("Y".equals(dto.getHasSDTV()))?1:0));				
				submitAllowed = "Y";
			}else if ("ADSL".equals(dto.getTechnology())&&"Y".equals(dto.getTechnologySupported())&&"N".equals(dto.getIsDeadCase())){
				adslC =("Y".equals(dto.getHasSPHDTV()))?3:(("Y".equals(dto.getHasHDTV()))?2:(("Y".equals(dto.getHasSDTV()))?1:0));				
				submitAllowed = "Y";
			}else if("Vectoring".equals(dto.getTechnology())&&"Y".equals(dto.getTechnologySupported())&&"N".equals(dto.getIsDeadCase()))
				submitAllowed = "N";
		}
		int[] myArray = new int[]{ponC, vdslC, adslC};
		Arrays.sort(myArray);
		Coverage = myArray[myArray.length - 1];
		ntvCoverage = Coverage==3?"SD~HD~SHD":(Coverage==2?"SD~HD":(Coverage==1?"SD":"NONE"));
		if(ntvCoverage.equalsIgnoreCase("NONE")) submitAllowed = "N";

		return ntvCoverage;
		
	}
	
	
}