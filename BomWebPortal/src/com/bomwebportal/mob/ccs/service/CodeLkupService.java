package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;

public interface CodeLkupService {

	// public CodeLkupDTO getCodeLkupDTO(String codeId);
	public List<CodeLkupDTO> getCodeLkupDTOALL(String codeType);
	public List<CodeLkupDTO> getCodeLkupDTOALLDesc(String codeType);
	// public int insertCodeLkup(CodeLkupDTO dto);
	// public int updateCodeLkup(CodeLkupDTO dto);
	// public int deleteCodeLkup(CodeLkupDTO dto);
	public String getCodeTypeById(List<String> codeTypes, String codeId);
	String getCodeDesc(String codeType, String codeId);
	public  List<CodeLkupDTO> getPayMethodCodeLkup();
	public List<CodeLkupDTO> findActiveReasonCodeLkupByType(String type, Date appDate);
	public Map<String,String> findReasonCodeTypeAsMap(String type, Date appDate);
	public Map<String,String> findAttbTypeAsMap( String locale);
	public List<CodeLkupDTO> findCodeLkupByType(String type);
}
