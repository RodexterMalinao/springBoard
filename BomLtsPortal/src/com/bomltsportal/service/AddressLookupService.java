package com.bomltsportal.service;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomltsportal.dto.MarkerDTO;

public interface AddressLookupService {
	
	MarkerDTO[] getMarkerByLevel(String level, String latLower, String latUpper, String lngLower, String lngUpper);
	BuildingMarkerDTO[] searchBuildingByRange(String latLower, String latUpper, String lngLower, String lngUpper);
	BuildingMarkerDTO getBuildingMarkerDetailByKey(String key);
}
