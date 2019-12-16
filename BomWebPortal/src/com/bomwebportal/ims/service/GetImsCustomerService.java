package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.ims.dto.ui.ImsInstallationUI;

public interface GetImsCustomerService {
	
	public List<ImsInstallationUI> getImsCustomer(String idDocType, String idDocNum);
	public String checkImsCustomer(String idDocType, String idDocNum);
	public String checkImsCustomerNowTV(String idDocType, String idDocNum);
	public String checkImsDataPrivacy(String idDocType, String idDocNum, String bomLob);
}
