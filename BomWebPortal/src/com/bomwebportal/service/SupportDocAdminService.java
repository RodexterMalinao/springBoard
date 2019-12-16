package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.AllOrdDocDmsDTO;
import com.bomwebportal.dto.OrdDocAssgnAdminDTO;
import com.bomwebportal.dto.OrdDocAssgnAdminDTO.DmsInd;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrderDTO.CollectMethod;

public interface SupportDocAdminService {
	List<OrdDocAssgnAdminDTO> findDocAssigned(String shopCd, String lob, Date startDate, Date endDate, String orderId, CollectMethod collectMethod, DmsInd dmsInd, int start, int size);
	long countDocAssigned(String shopCd, String lob, Date startDate, Date endDate, String orderId, CollectMethod collectMethod, DmsInd dmsInd);

	
	List<OrdDocAssgnAdminDTO> getRequiredProofTypes(String orderId);
	
	void updateWaiveReason(List<OrdDocAssgnAdminDTO> updateList);
	void updateOrdDocAssgn(OrdDocAssgnDTO dto, boolean overwrite);

	int insertOrdDocDms(AllOrdDocDmsDTO dto);
	int prepareOrdDocDmsForProcessing(String lob, String updatedBy);
	List<AllOrdDocDmsDTO> findAllOrdDocDmsDTO(Date processDate, String status, String orderId, String lob);
	List<AllOrdDocDmsDTO> findOrderForDmsPreprocessing(String lob);
	int updateOrdDocDms(AllOrdDocDmsDTO dto);
	
	int insertOrdDocAssgn(OrdDocAssgnDTO dto);
	
}
