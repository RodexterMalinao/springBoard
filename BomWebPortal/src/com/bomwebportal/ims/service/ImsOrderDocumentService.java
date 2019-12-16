package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.ims.dto.ImsAllOrdDocDTO;
//created by Tony
public interface ImsOrderDocumentService {
	public void insertImsAllOrdDocDTO(List<ImsAllOrdDocDTO> imsAllOrdDocDtoList);
	public void insertImsEdfRef(String orderId, String edfRef, String user);
	public boolean checkImsEdfRef(String orderId, String dtlId);
	public void updateImsEdfRef(String orderId, String dtlId, String edfRef, String user);
	public String getImsAllOrderDocFaxSerial(String orderId, String docType);
	public void updateWqFaxSerial(String orderId, String serial, String user);
}
