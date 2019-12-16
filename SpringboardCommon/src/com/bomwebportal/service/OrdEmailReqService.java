package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.OrdEmailReqDTO;

public interface OrdEmailReqService {
	/*
	Initial copied on 20140925 from Version 1.7 of
	\Springboard\SBWPR3\BomWebPortal\src\com\bomwebportal\service\OrdEmailReqService.java
	*/
	
	OrdEmailReqDTO getOrdEmailReqDTO(String pRowId);
	
	// Renamed - previously getOrdEmailReqDTOALL(String lob)
	List<OrdEmailReqDTO> getOrdEmailReqDTOList(String pLob);
	
	// Renamed - previously getOrdEmailReqDTOALLByOrderId(String orderId, String templateId)
	List<OrdEmailReqDTO> getOrdEmailReqDTOListByOrderAndTemplateId(String pOrderId, String pTemplateId, String pLob);
	
	// Replaced By getOrdEmailReqDTOListByOrderId(String pOrderId, String pLob) instead
	//List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOrderIdIMS(String orderId); 
	
	OrdEmailReqDTO getOrdEmailReqDTOByOrderIdAndSeqNo(String pOrderId, int pSeqNo, String pTemplateId);
	
	// Renamed - previously getOrdEmailReqDTOALLBySearch(String orderId,String shopCd, String lob, Date requestDate, String templateId)
	List<OrdEmailReqDTO> getOrdEmailReqDTOListBySearchCriteria(String pOrderId, String pShopCd, String pLob, Date pRequestDate, String pTemplateId);
	
	// Renamed - previously getOrdEmailReqDTOLTSIMSBySearch(String orderId,	int ChannelId, Date requestDate, String templateId, String team)
	List<OrdEmailReqDTO> getOrdEmailReqDTOListByChannelIdAndTeam(String pOrderId, int pChannelId, String pLob, Date pRequestDate, String pTemplateId, String pTeam);

	// Renamed - previously getOrdEmailReqDTOALLByOnlyOrderId(String orderId)
	List<OrdEmailReqDTO> getOrdEmailReqDTOListByOrderId(String pOrderId, String pLob);
	
	int getNextSeq(String pOrderId, String pTemplateId, String pLob);
	
	//int getNextSeqIMS(String orderId);  // Replaced getNextSeq(String pOrderId, String pTemplateId, String pLob) instead
	
	int insertOrdEmailReq(OrdEmailReqDTO pDto);
	int updateOrdEmailReq(OrdEmailReqDTO pDto);
	int insertOrdSMSReq(OrdEmailReqDTO pDto) ;
	int updateOrdSMSReq(OrdEmailReqDTO pDto) ;
}
