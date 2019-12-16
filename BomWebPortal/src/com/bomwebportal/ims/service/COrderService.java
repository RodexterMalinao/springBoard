package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.ims.dto.GetCOrderDTO;
//steven created this java file, plz find steven if u find anything wrong
public interface COrderService {
	public GetCOrderDTO getCOrder(String sub_pcd, String sub_nowtv,String tech,String custNum, String sb,String unit,String floor);
	public List<GetCOrderDTO> getAvailFSAforNowTV(String sb, String unit, String floor, String custNum);
	public String checkMultipleFSAUnderAcct(String acctNo);
	public String getNAcctByFSA(String fsa);
}
