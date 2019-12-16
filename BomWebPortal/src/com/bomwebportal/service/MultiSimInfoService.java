package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.BomMupInfoDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.SimDTO;
import com.bomwebportal.wsclient.exception.WsClientException;

import bom.mob.schema.javabean.si.springboard.xsd.SubInfoDTO;

public interface MultiSimInfoService {
	List<MultiSimInfoDTO> getMultiSimInfoDTO(String orderId, String locale, String appDate, String channelId, String channelCd, String mipBrand, String mipSimType);
	void orderHistoryProcess(String orderId);
	void updateBomWebMultiSimMNP(MultiSimInfoDTO msi, String salesId);
	void insertSbOrderAmendDTO(MultiSimInfoDTO msi, String salesId);
	String validateSBPendingOrder(String mrt, String orderId);
	List<String> getDBSecondaryMRTs(String orderId);
	public List<BomMupInfoDTO> getBomMupInfoDTOList(String srvNum);
	//public MultiSimInfoMemberDTO getMupMemberSubscriber(String msisdn,BomSalesUserDTO salesUser) throws WsClientException;
	String getBomServiceId (String msisdn);
	String getItemCodeLkup(String itemCd);
	public String getBomPendingOrderListString(String serviceId, String channelId);
	public String getAutoInd(MultiSimInfoMemberDTO msim);
	public String getCosInd(MultiSimInfoMemberDTO msim);
	public String getToo1Ind(MultiSimInfoMemberDTO msim, CustomerProfileDTO customerProfileDTO);
	public String getCmnInd(MultiSimInfoMemberDTO msim);
	public String getBrmInd(MultiSimInfoMemberDTO msim, CustomerProfileDTO customerProfileDTO);
	public String getCsimInd(MultiSimInfoMemberDTO msim);
	public SimDTO getBrmChgSimInfo(String memberOrderId);
	public boolean checkOneNumber(String serviceId);
}
