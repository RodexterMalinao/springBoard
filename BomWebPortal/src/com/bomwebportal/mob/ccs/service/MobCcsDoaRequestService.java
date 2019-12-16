package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.DoaItemDTO;
import com.bomwebportal.mob.ccs.dto.DoaRequestDTO;
import com.bomwebportal.mob.ccs.dto.DoaRequestSelectedCdDTO;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;

public interface MobCcsDoaRequestService {
	DoaRequestDTO getInitDoaRequestDTO(String orderId);
	
	List<DoaRequestDTO> getDoaRequestDTOALL(String orderId);
	DoaRequestDTO getDoaRequestDTO(String rowId);
	DoaRequestDTO getDoaRequestDTOByOrderIdAndSeqNo(String orderId, int seqNo);
	int getLastSeqNo(String orderId);
	
	int insertDoaRequestDTO(DoaRequestDTO dto);
	int updateDoaRequestDTO(DoaRequestDTO dto);
	int updateDoaRequestStatus(String rowId, String status);
	//int deleteDoaRequestBySeqNo(String orderId, int seqNo);
	
	List<DoaRequestSelectedCdDTO> getDoaRequestSelectedCdDTOALL(String orderId, int seqNo);
	void insertDoaRequestSelectedCdDTOList(List<DoaRequestSelectedCdDTO> list);
	int deleteDoaRequestSelectedCdBySeqNo(String orderId, int seqNo);
	
	boolean approveByManager(String orderId);
	
	List<DoaItemDTO> getDoaItemDTOList(String orderId, int seqNo);
	void insertDoaItemDTOList(List<DoaItemDTO> list);
	int deleteDoaItemBySeqNo(String orderId, int seqNo);
	
	void updateDoa(DoaRequestDTO dto
			, List<DoaRequestSelectedCdDTO> selectedCds
			, List<DoaItemDTO> doaItems
			, OrderRemarkDTO orderRemark
			, boolean saveAsDraft, String username);
	void insertDoa(DoaRequestDTO dto
			, List<DoaRequestSelectedCdDTO> selectedCds
			, List<DoaItemDTO> doaItems
			, OrderRemarkDTO orderRemark
			, boolean saveAsDraft, String username);
	
	boolean isInitNewDoaRequest(OrderDTO orderDto);
	boolean isUpdateDoaRequest(OrderDTO orderDto);
	
	boolean isMktSerialIdExists(String mktSerialId);
	boolean isMktSerialIdPatternValid(String mktSerialId);
	
	public String[] getRetDoaRequestDefectInd(String orderId);
	public List<String> getDoaRequestSelectedHsDefectAll(String orderId, int seqNo);
	public List<String> getDoaRequestSelectedAcDefectAll(String orderId, int seqNo);
	public List<String> getDoaRequestAcDefectItems(String orderId, int seqNo);
}
