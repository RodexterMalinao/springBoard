package com.bomwebportal.ims.service;

import java.util.List;
import java.util.Map;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.RemarkDTO;
import com.bomwebportal.ims.dto.ui.ImsPaymentUI;

public interface GetSourceCodeService {
	public List<Map<String, String>> getSourceCode(String channel) throws DAOException;	

	public List<Map<String, String>> getSourceCode() throws DAOException;
	
	public String getDeflaultAppMethod(String deflaultSourceCode);
	
	public String getDeflaultAppMethodRetry(String deflaultSourceCode);
	
	public List<RemarkDTO> getL2JobCode();
	public void getDirectSalesAppMethod(ImsPaymentUI payment, String srcCd);
	
	public String getRetailAppMethod(String SourceCode);
}
