package com.bomwebportal.ims.service;

import com.bomwebportal.ims.dto.ui.ImsInstallationUI;

public interface SuggestLoginIDService {
	public ImsInstallationUI suggestLoginID(String loginName);
}