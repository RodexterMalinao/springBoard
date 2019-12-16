package com.bomwebportal.ims.dto;

import java.io.Serializable;
import com.bomwebportal.dto.AddressDTO;

public class ImsCCAddressDTO extends AddressDTO {
	
	private String coverEyeX;
	
	private String coverPe;
	
	private String floorNo;
	private String flatNo;

	public String getCoverEyeX() {
		return coverEyeX;
	}

	public void setCoverEyeX(String coverEyeX) {
		this.coverEyeX = coverEyeX;
	}

	public String getCoverPe() {
		return coverPe;
	}

	public void setCoverPe(String coverPe) {
		this.coverPe = coverPe;
	}

	private String chiBuildingName;
	
	private String chiStreetName;

	public void setChiBuildingName(String chiBuildingName) {
		this.chiBuildingName = chiBuildingName;
	}

	public String getChiBuildingName() {
		return chiBuildingName;
	}

	public void setChiStreetName(String chiStreetName) {
		this.chiStreetName = chiStreetName;
	}

	public String getChiStreetName() {
		return chiStreetName;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getFlatNo() {
		return flatNo;
	}
	
}