package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;

public interface OrdEmailReqService {
	OrdEmailReqDTO getOrdEmailReqDTO(String rowId);
	List<OrdEmailReqDTO> getOrdEmailReqDTOALL(String lob);
	List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOrderId(String orderId, String templateId);
	List<OrdEmailReqDTO> getCcsOrdEmailReqDTOALLByOrderId(String orderId, String templateId,String pLang);
	List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOrderIdIMS(String orderId , String templateId);
	OrdEmailReqDTO getOrdEmailReqDTOByOrderIdAndSeqNo(String orderId, int seqNo, String templateId);
	OrdEmailReqDTO getCareCashOrdEmailReqDTOByOrderIdAndSeqNo(String orderId, int seqNo, String templateId,String pLang);
	List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearch(String orderId,String shopCd, String lob, Date requestDate, String templateId);
	//List<OrdEmailReqDTO> getCCSOrdEmailReqDTOALLBySearch(String orderId);
	List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearch(String orderId,String shopCd, String lob, Date requestDate, String templateId,String orderType); 
	List<OrdEmailReqDTO> getOrdEmailReqDTOLTSIMSBySearch(String orderId, int channelId, Date requestDate, String templateId, String team);
	/*
	 * For IMS re-send email function, added on 2013-01-28 by Eric Ng
	 */
	List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOnlyOrderId(String orderId);
	
	int getNextAmendNoteSeqNoIMS(String orderId);
	List<String> getTeamCdsByRole(BomSalesUserDTO salesDto);
	List<String> getTeamCdsByRoleandChannelCd(BomSalesUserDTO salesDto);
	List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearchIMS(String orderId,Date requestDate,  BomSalesUserDTO salesUserDto);
	List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearchIMS(String orderId,Date requestDate,  BomSalesUserDTO salesUserDto, String teamCd, String orderType);
	//Celia 201411
	
	int getNextSeq(String orderId, String templateId);
	int getNextSeqIMS(String orderId);
	int isCareCashFormSent(String orderId);
	
	int insertOrdEmailReq(OrdEmailReqDTO dto);
	int updateOrdEmailReq(OrdEmailReqDTO dto);
	int insertOrdSMSReq(OrdEmailReqDTO dto) ;
	int updateOrdSMSReq(OrdEmailReqDTO dto) ;

	//NOWTVSALES
	public String[] getLatestEmailSMSPair(String orderId);
	
	public List<BasketDTO> getOnlinePlanDetails(String orderId, String type, String locale, String vas_category_id);
	public List<BasketDTO> getOnlineHSDetails(String orderId, String string);
	
}
