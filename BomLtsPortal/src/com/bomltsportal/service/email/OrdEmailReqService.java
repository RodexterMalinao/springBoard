package com.bomltsportal.service.email;

import java.util.Date;
import java.util.List;

import com.bomltsportal.dto.email.OrdEmailReqDTO;

public interface OrdEmailReqService {
	OrdEmailReqDTO getOrdEmailReqDTO(String rowId);
	List<OrdEmailReqDTO> getOrdEmailReqDTOALL(String lob);
	List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOrderId(String orderId, String templateId);
	OrdEmailReqDTO getOrdEmailReqDTOByOrderIdAndSeqNo(String orderId, int seqNo, String templateId);
	List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearch(String orderId, String shopCd, String lob, Date requestDate, String templateId);
	/*
	 * For IMS re-send email function, added on 2013-01-28 by Eric Ng
	 */
	List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOnlyOrderId(String orderId);
	
	int getNextSeq(String orderId, String templateId);
	
	int insertOrdEmailReq(OrdEmailReqDTO dto);
	int updateOrdEmailReq(OrdEmailReqDTO dto);
}
