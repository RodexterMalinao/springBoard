package com.bomltsportal.service;

import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;

public interface CustomerDetailService {

	public abstract CustomerDetailProfileLtsDTO getLtsCustomerDetailByDocId(
			String pDocId, String pDocType);

}