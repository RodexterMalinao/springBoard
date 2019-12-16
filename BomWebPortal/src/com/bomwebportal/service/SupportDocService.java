package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocDTO;
import com.bomwebportal.dto.AllOrdDocTempDTO;
import com.bomwebportal.dto.AllOrdDocWaiveDTO;
import com.bomwebportal.dto.SignCaptureAllOrdDocDTO;

public interface SupportDocService {
	@Deprecated List<AllDocDTO> getAllDocDTOByType(String lob, Type type);
	List<AllDocDTO> getAllDocDTOByTypeAndAppDate(String lob, Type type, Date appDate);
	// same as getAllDocDTOByTypeAndAppDate, except limited to known enum DocType records
	List<AllDocDTO> getAllDocDTOKnownByTypeAndAppDate(String lob, Type type, Date appDate);
	List<AllOrdDocTempDTO> getAllOrdDocDTOALL(String orderId);
	List<AllOrdDocAssgnDTO> getAllOrdDocAssgnDTOALL(String orderId);
	List<AllOrdDocAssgnDTO> getInUseAllOrdDocAssgnDTOALL(String orderId);
	List<AllOrdDocWaiveDTO> getAllOrdDocWaiveDTOALL(String lob, DocType docType);
	List<AllOrdDocWaiveDTO> getAllOrdDocWaiveDTOALL(String lob, String docType);
	boolean isSupportDocRequired(String docType, String basketID, String[] vasItemList, String channel);
	
	void insertAllOrdDocAssgnDTOALL(String orderId, List<AllOrdDocAssgnDTO> list);
	void insertImsAllOrdDocAssgnDTOALL(List<AllOrdDocAssgnDTO> list);
	void ims_mark_all_delete(String order_id);
	
	//add by nancy 20131122
	List<String> getMissingDocReasonList();
	List<SignCaptureAllOrdDocDTO> retrieveAllOrdDocDTOList(String orderId, Date appDate, boolean isTemp);
	List<SignCaptureAllOrdDocDTO> getSignCaptureAllOrdDocDTO(String orderId, Date appDate);	
	void insertAllOrderDocTempDTO(AllOrdDocDTO dto);
	void deleteAllOrderDocTempDTO(String orderId);
}