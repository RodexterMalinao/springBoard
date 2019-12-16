package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bomwebportal.dto.IssueBankDTO;


public interface PaymentService {
	
	public List<IssueBankDTO> getIssueBankList();
	public String initialCeksAccess(String username, HttpServletRequest request);
	public String getSalesChannelCd(String staffId, Date appDate);
}
