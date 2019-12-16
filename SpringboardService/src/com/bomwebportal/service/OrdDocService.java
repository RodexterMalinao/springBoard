package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.AllOrdDocDTO;
import com.bomwebportal.dto.DocTypeDTO;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.OrdDocDTO.VerifyInd;

public interface OrdDocService {
	enum AuditLogActionCd {
		AD01	// WAIVE REASON UPDATE
		, AD02	// SUPPORT IMAGE DOWNLOAD
		, AD03	// SUPPORT DOC VERIFY
		;
	}
	
	public DocTypeDTO getDocType(String docType, String lob);
	public DocTypeDTO getDocTypeForOrder(String docType, String orderId);
	public List<OrdDocDTO> getOrdDoc(String orderId);
	public List<OrdDocDTO> getImsOrdDoc(String orderId);
	public List<OrdDocAssgnDTO> getRequiredDoc(String orderId);
	public int getLastSeqNum(String orderId, String docType);
	public int getImsNumberOfCollectedDoc(String orderId, String docType);//steven 20121025
	public void insertOrdDoc(OrdDocDTO ordDoc);
	void insertAllOrderDocDTO(AllOrdDocDTO dto); // add by Joyce 20120816
	void updateAllOrderDocDTOOutdatedInd(String Order_id, String doc_type); // add by Steven 20121024
	List<OrdDocDTO> getOrdDoc(String orderId, String docType);
	OrdDocDTO getOrdDoc(String orderId, String docType, int seqNum);
	
	int updateVerifyInd(String orderId, String docType, int seqNum, VerifyInd verifyInd, String username);
	int insertAuditLog(String orderId, String docType, Integer seqNum, AuditLogActionCd actionCd, String username);
}
