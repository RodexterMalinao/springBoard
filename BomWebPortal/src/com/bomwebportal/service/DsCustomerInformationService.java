package com.bomwebportal.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.openuri.www.ServiceLineDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.AddressDTO;
import com.bomwebportal.dto.CustomerOrderHistoryDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public interface DsCustomerInformationService {
	
	public List<CustomerOrderHistoryDTO> getCustomerOrderHistoryDTOALL(String idDocNum, String serviceNum, String idDocType, String loginId, String serviceType);
	
	public List<CustomerOrderHistoryDTO> getCustomerOrderHistoryDTOALL_retail_only(String idDocNum, String serviceNum, String idDocType, String loginId, String serviceType);
	
	public String getStreetCatgDescFromStCatgCd(String stCatgCd);
	
	public String getSectDescFromSectCd(String sectCd);
	
	public String getDistDscFromDistrNum(String distrNum);
	
	public String getAreaDescFromDistrNum(String distrNum);
	
	public String getAreaCdFromDistrNum(String distrNum);
	
}
