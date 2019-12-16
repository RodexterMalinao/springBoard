package com.bomwebportal.mob.ccs.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;

public interface MrtInventoryService {
	enum FunctionCd {
		MRT_INVENTORY("MRT INVENTORY")
		, MRT_CONTROL("MRT CONTROL")
		;
		FunctionCd(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		private String value;
	}
	
	enum ParmType {
		  NUM_TYPE
		, SERVICE_TYPE
		, CHANNEL
		, GRADE
		, CITY
		;
	}
	
    MrtInventoryDTO getMrtInventoryDTO(String rowId);
    MrtInventoryDTO getMrtInventoryDTOByOrderId(String orderId, List<String> channelCds);
    List<MrtInventoryDTO> getMrtInventoryDTOALL(String msisdn);
    List<MrtInventoryDTO> getMrtInventoryDTOALL(String msisdn, List<String> channelCds);
    List<MrtInventoryDTO> getMrtInventoryDTOBySearch(MrtInventoryDTO dto, List<String> channelCds);
    int insertMrtInventory(MrtInventoryDTO dto);
    int updateMrtInventory(MrtInventoryDTO dto);
    int deleteMrtInventory(MrtInventoryDTO dto);
    int updateMrtInventoryStatus(MrtInventoryDTO dto);
    String getStsFmMRT(String msisdn);
    
    List<MaintParmLkupDTO> getMaintParmValue(String channelCd, FunctionCd functionCd, ParmType parmType);
    List<MaintParmLkupDTO> getParmControl(String channelCd, String controlType, FunctionCd functionCd, FunctionCd controlFunctionCd, ParmType parmType);
    JSONObject getMrtControlJson(String channelCd);
}
