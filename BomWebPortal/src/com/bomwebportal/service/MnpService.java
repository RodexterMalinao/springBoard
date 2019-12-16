package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.CnpDTO;
import com.bomwebportal.dto.MnpDTO;

import bom.mob.schema.javabean.si.xsd.CslNoStatusDTO;
import bom.mob.schema.javabean.si.xsd.GetCurrentDNODTO;

public interface MnpService {
	
	public MnpDTO validateMnpMsisdn(MnpDTO mnpDTO);
	public MnpDTO validateNewMsisdn(MnpDTO mnpDTO);
	public MnpDTO validateMnpMsisdn(String msisdn);
	public String checkBomPendingOrder(String mobileNumber);
	public MnpDTO validateCnmMsindn(String msisdn, String shopCd);
	public List<String> checkReserveInfo(String msisdn, String shopCd);
	public String getReserveInfoFromCNM(String msisdn, String shopCd);
	public boolean isPccw3gPrepaidNumber(String msisdn);
	List<String[]> getMrtNum(String staffId, List<String> grade, Date appDate, String numType);
	List<String[]> getCCSMrtNum(String channelCd, String grade, String numType);
	String[] getMrtNum(String oldMsisdn, String channelCd, String numType);
	public MnpDTO checkCCSPendingOrder(String orderId, String msisdn);
	public String getDno(String msisdn);
	
	public List<CnpDTO> getNewMsisdn(String msisdn, int status, String shopCd, String msisdnlvl, String numType, int returnQty);
	public List<CnpDTO> getNew1C2NMsisdn(String msisdn, int status, String shopCd, String msisdnlvl, String cityCd, String numType, int returnQty);
	public List<CnpDTO> getNewSsMsisdn(String msisdn, int status, String shopCd, String msisdnlvl, String numType, int returnQty);
	public boolean reserveMsisdn(String msisdn, String actionType, String shopCd);
	public GetCurrentDNODTO getCurrDNODTO(String msisdn);
	public CslNoStatusDTO checkNoStatusInCInv(String msisdn);
	public MnpDTO checkCentralNumPoolMsisdnMIP(String msisdn);
	public Integer checkIsWhiteList(String msisdn);
	public Integer getCSLBillPeriod(String msisdn);
	public Integer checkPendingOrder(String msisdn, String orderId);
	public Integer checkPendingMUPOrder(String msisdn, String orderId);
}
