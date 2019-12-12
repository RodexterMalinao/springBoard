package com.pccw.wq.dao;

import java.util.List;

import com.pccw.wq.schema.dto.SelectionItemDTO;

public interface ConstantLkupDAO {
	
	public void setLkupCode(String pLkupCode);

	public String getLkupCode();
		
	List<SelectionItemDTO> getCodeLkup();
		
}
