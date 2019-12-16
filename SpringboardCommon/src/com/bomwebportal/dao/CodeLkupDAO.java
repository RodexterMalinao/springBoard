package com.bomwebportal.dao;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;

public interface CodeLkupDAO {
	
	public void setLkupCode(String pLkupCode);

	public String getLkupCode();
	
	public String getLkupTable();

	public void setLkupTable(String pLkupTable);
		
	LookupItemDTO []  getCodeLkup() throws DAOException;

	public void setItemKey(String pItemKey);

	public String getItemKey();
	
}
