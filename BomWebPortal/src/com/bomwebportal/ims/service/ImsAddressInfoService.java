package com.bomwebportal.ims.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsBlacklistCustInfoDTO;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;
import com.bomwebportal.ims.dto.SbRemarksDTO;
import com.bomwebportal.ims.dto.UimBlockageDTO;
import com.bomwebportal.ims.dto.ui.AddressInfoUI;
import com.bomwebportal.ims.dto.ui.InstallFeeUI;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;


public interface ImsAddressInfoService {
	
	//public ImsServiceSrdDTO getServiceSrd(String isBlAddr, String isBlCust, String isPccwLn, Date appntDateTime, String sbno, String flat, String floor, String isCC, String PT);
	public UimBlockageDTO getFiberBlockage(String sbNum, String floor, String unitNo);
	public List<UimBlockageDTO> getFiberBlockageByFloor(String sbNum, String floor);
	public String getBlacklistAddr(String sbNum, String floor);
	public String getBlacklistAddrForPH(String sbNum, String floor, String unitNo);
	public List<String> getOsOrder(String sbNum, String floor, String unitNo);
	public String isBlacklistCust(String idDocType, String idDocNum);
	public List<ImsBlacklistCustInfoDTO> getBlacklistCustInfo(String idDocType, String idDocNum);
	public AddressInfoUI getHousTypeOTChrgList(String housingtype, String serbdyno, String floor, String flat);
	public List<String> getBrmVasMsg(String sbNum);
	public Boolean checkSpecialSBFilterVas(String SB);
	public String getCorrectSBbyFlatFloor(String sb, String floor, String flat);
	public List<String> getRelatedSBList(String currentSB);
	public boolean checkifAddressExactMatchwService(String sbNum, String floor, String unitNo,String sbOrderId);	
	public String getH264Ind(String sbno);
	public String getAdsl18mInd(String sbno);
	public Boolean isShopCodeValid(String shopCode);
	public String hasPcdBeforeCheckCsl(String sbno, String unit, String floor) throws DAOException;
	public String cslMobileNumPCDCheck(String mrt, String orderId) throws DAOException;
	public Map<String, String> getCslMobileCustomer(String mrt, String idDocType, String idDocNum);
	public String getCslOfferEnableInd(String user) throws DAOException;
	public String getCslPcdMonthCheck() throws DAOException;
	public String getCslShopCustInd(String user) throws DAOException;
	public List<SbRemarksDTO> getSbRemarks(String serbdyno);
	public String withDelCheckAddr(String sbno, String unit, String floor);
	public String getBypassLtsCheckInd();
	public String isSupremeHousingType(String housingType);
	public List<String> getPonSBList(String sbNum);
}
