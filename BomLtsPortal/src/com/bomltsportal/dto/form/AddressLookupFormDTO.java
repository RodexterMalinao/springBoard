package com.bomltsportal.dto.form;

import java.io.Serializable;

import com.bomltsportal.dto.BuildingMarkerDTO;

public class AddressLookupFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3852105579156529483L;

	private BuildingMarkerDTO buildingMarker;
	private String markerIdx;

	public BuildingMarkerDTO getBuildingMarker() {
		return buildingMarker;
	}

	public void setBuildingMarker(BuildingMarkerDTO buildingMarker) {
		this.buildingMarker = buildingMarker;
	}

	public String getMarkerIdx() {
		return markerIdx;
	}

	public void setMarkerIdx(String markerIdx) {
		this.markerIdx = markerIdx;
	}

}
