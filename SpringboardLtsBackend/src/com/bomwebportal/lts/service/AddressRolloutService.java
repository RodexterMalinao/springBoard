package com.bomwebportal.lts.service;

import com.bomwebportal.lts.dto.AddressRolloutDTO;

public interface AddressRolloutService {

	public abstract AddressRolloutDTO getAddressRollout(String serviceBoundary,
			String flat, String floor);

}