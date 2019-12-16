package com.bomltsportal.service;

import com.bomwebportal.lts.dto.AddressRolloutDTO;

public interface OnlineAddressRolloutService {

	public abstract boolean isBlacklistListLtsAddress(String pFlat,
			String pFloor, String pServiceBoundary);

	public abstract String[] getConsumerEyeSrvBdryByBuildCoordinate(String pFlat, String pFloor, String pBuildxy, String pSrvType);

	public abstract AddressRolloutDTO getAddressRollout(String[] srvBrdys, String flat, String floor, String serviceTypeInd);
}