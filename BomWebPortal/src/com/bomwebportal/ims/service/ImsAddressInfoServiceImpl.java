package com.bomwebportal.ims.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

import com.bomwebportal.ims.dao.ImsAddressInfo2DAO;
import com.bomwebportal.ims.dao.ImsAddressInfoDAO;
import com.bomwebportal.ims.dao.ImsBomOrderDAO;
import com.bomwebportal.ims.dao.OnetimeChargeDAO;

import com.bomwebportal.ims.dto.ImsBlacklistCustInfoDTO;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;
import com.bomwebportal.ims.dto.SbRemarksDTO;
import com.bomwebportal.ims.dto.UimBlockageDTO;
import com.bomwebportal.ims.dto.ui.AddressInfoUI;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.dto.ui.InstallFeeUI;

@Transactional(readOnly=true)
public class ImsAddressInfoServiceImpl implements ImsAddressInfoService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsAddressInfoDAO imsAddressInfoDao;	
	
	public ImsAddressInfoDAO getImsAddressInfoDao() {
		return imsAddressInfoDao;
	}
	public void setImsAddressInfoDao(ImsAddressInfoDAO imsAddressInfoDao) {
		this.imsAddressInfoDao = imsAddressInfoDao;
	}
	private ImsAddressInfo2DAO imsAddressInfo2Dao;	

	public ImsAddressInfo2DAO getImsAddressInfo2Dao() {
		return imsAddressInfo2Dao;
	}
	public void setImsAddressInfo2Dao(ImsAddressInfo2DAO imsAddressInfo2Dao) {
		this.imsAddressInfo2Dao = imsAddressInfo2Dao;
	}
	
	private OnetimeChargeDAO onetimeChargeDao;
	
	public OnetimeChargeDAO getOnetimeChargeDao() {
		return onetimeChargeDao;
	}

	public void setOnetimeChargeDao(OnetimeChargeDAO onetimeChargeDao) {
		this.onetimeChargeDao = onetimeChargeDao;
	}

	private ImsBomOrderDAO bomDao;
	
	
		
	
	/*@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public ImsServiceSrdDTO getServiceSrd(String isBlAddr, String isBlCust, String isPccwLn, Date appntDateTime, String sbno, String flat, String floor, String isCC, String isPT){
		
		logger.info("getServiceSrd is called");
		ImsServiceSrdDTO addressInfo = new ImsServiceSrdDTO();
		
		String sysId= "";
		if ( "Y".equals(isCC))
		{
			if ( "Y".equals(isPT))
				sysId = "CC_PT";
			else
				sysId = "CC";
		}
		else
		{
			sysId = "RETAIL";
		}
		try{			
			addressInfo = imsAddressInfoDao.getServiceSrd(isBlAddr, isBlCust, isPccwLn, appntDateTime, sbno, flat, floor, sysId);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return addressInfo;
	}*/

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public UimBlockageDTO getFiberBlockage(String sbNum, String floor, String unitNo){
		
		logger.debug("getFiberBlockage is called");
		List<UimBlockageDTO> uimBlockageList = new ArrayList<UimBlockageDTO>();
		
		try{			
			uimBlockageList = imsAddressInfoDao.getFiberBlockage(sbNum, floor, unitNo);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		if (uimBlockageList.size() == 0) {
			return null;
		} else {
			return uimBlockageList.get(0);
		}
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<UimBlockageDTO> getFiberBlockageByFloor(String sbNum, String floor){
		
		logger.debug("getFiberBlockageByFloor is called");
		List<UimBlockageDTO> uimBlockageList = new ArrayList<UimBlockageDTO>();
		
		try{			
			uimBlockageList = imsAddressInfoDao.getFiberBlockageByFloor(sbNum, floor);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return uimBlockageList;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getBlacklistAddr(String sbNum, String floor){
		
		logger.debug("getBlacklistAddr is called");
		
		try{
			return imsAddressInfoDao.getBlacklistAddr(sbNum, floor);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getBlacklistAddrForPH(String sbNum, String floor, String unitNo){
		
		logger.debug("getBlacklistAddr is called");
		
		try{
			return imsAddressInfoDao.getBlacklistAddrForPH(sbNum, floor, unitNo);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<String> getOsOrder(String sbNum, String floor, String unitNo){
		
		logger.debug("getOsOrder is called");
		List<String> osOrderList = new ArrayList<String>();
		
		try{			
			osOrderList = imsAddressInfoDao.getOsOrder(sbNum, floor, unitNo);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return osOrderList;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String isBlacklistCust(String idDocType, String idDocNum){
		
		logger.debug("validBlacklistCust is called");
		
		try{
			return imsAddressInfoDao.isBlacklistCust(idDocType, idDocNum);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsBlacklistCustInfoDTO> getBlacklistCustInfo(String idDocType, String idDocNum){
		
		logger.debug("getBlacklistCustOsAmt is called");
		List<String> acctList = new ArrayList<String>();
		List<ImsBlacklistCustInfoDTO> osAmtList = new ArrayList<ImsBlacklistCustInfoDTO>();
		
		try{
			acctList = imsAddressInfoDao.getBlCustAcctList(idDocType, idDocNum);
			
			for (int i=0; i < acctList.size(); i++) {
				osAmtList.add(imsAddressInfoDao.getBlCustOsAmt(acctList.get(i)));
			}
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
		logger.debug("OS Amt List size()=" + osAmtList.size());
		return osAmtList;
	}

	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public AddressInfoUI getHousTypeOTChrgList(String housingtype, String serbdyno, String floor, String flat)
	{
		//List<ImsInstallationInstallmentPlanDTO> imsInstallationInstallmentPlanList=new ArrayList<ImsInstallationInstallmentPlanDTO>();
		AddressInfoUI AddressInfo = new AddressInfoUI();
		
		try{
			AddressInfo = onetimeChargeDao.addrInfoOnetimeAmount(housingtype, serbdyno, floor, flat);
										
		
//		System.out.println("AddressInfo:" + AddressInfo.getOneTimeFee() + "," + AddressInfo.getoTFeeProdType());

		
		return AddressInfo;
		}catch (Exception e){
			logger.error("Exception caught in getOtAmount()", e);
			throw new AppRuntimeException(e);
		}
		
	}
	
	
	//Gary
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<String> getBrmVasMsg(String sbNum){
		
		List<String> bomDescList = new ArrayList<String>();
		logger.debug("getBrmVasMsg is called");
		try{		
			bomDescList = bomDao.getBrmVasDesc(sbNum);		
		}catch(Exception e){
			logger.error("Exception caught in getBrmVasMsg()",e);
			throw new AppRuntimeException(e);
		}
		for(int i=0;i<bomDescList.size();i++)
			logger.info("getBrmVasMsg"+i+": " + bomDescList.get(i).toString());
		return bomDescList;
	}
	
	public void setBomDao(ImsBomOrderDAO bomDao) {
		this.bomDao = bomDao;
	}
	public ImsBomOrderDAO getBomDao() {
		return bomDao;
	}
	
	public Boolean checkSpecialSBFilterVas(String SB){
		logger.debug("@checkSpecialSBFilterVas");
		
		try {
			return imsAddressInfoDao.checkSpecialSBFilterVas(SB);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	public List<String> getRelatedSBList(String currentSB) {
		logger.debug("getRelatedSBList is called");
		List<String> sbList = null;
		
		try{
			sbList = imsAddressInfoDao.getRelatedSBList(currentSB);
			
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
		return sbList;
	}

	public String getCorrectSBbyFlatFloor(String sb, String floor, String flat){
		logger.debug("getCorrectSBbyFlatFloor is called");
		try{
			return imsAddressInfoDao.getCorrectSBbyFlatFloor(sb,  floor,  flat);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean checkifAddressExactMatchwService(String sbNum, String floor, String unitNo, String sbOrderId){
		boolean check=false;
		logger.debug("checkifAddressExactMatchwService is called");
		
		try{			
			check = imsAddressInfoDao.checkifAddressExactMatchwService(sbNum, floor, unitNo,sbOrderId);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return check;
	}
	
	public String getH264Ind(String sbno){
		logger.debug("getH264Ind");
		String h264Ind = null;
		try{
			h264Ind = imsAddressInfoDao.getH264Ind(sbno);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return h264Ind;
	}
	
	public String getAdsl18mInd(String sbno){
		logger.debug("getAdsl18mInd");
		String adsl18mInd = null;
		try{
			adsl18mInd = imsAddressInfoDao.getAdsl18mInd(sbno);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return adsl18mInd;
	}
	public Boolean isShopCodeValid(String shopCode) {
		try {
			return imsAddressInfo2Dao.isShopCodeValid(shopCode);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		logger.info("isShopCodeValid error, return false");
		return false;
	}
	public String hasPcdBeforeCheckCsl(String sbno, String unit, String floor) throws DAOException{
		logger.debug("hasPcdBeforeCheckCsl");
		String hasPcdBeforeInd = null;
		try{
			hasPcdBeforeInd = imsAddressInfoDao.hasPcdBeforeCheckCsl(sbno, unit, floor);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return hasPcdBeforeInd;
	}
	public String cslMobileNumPCDCheck(String mrt,String orderId) throws DAOException{
		logger.debug("cslMobileNumPCDCheck");
		String hasNonCslPCDInd = null;
		try{
			hasNonCslPCDInd = imsAddressInfo2Dao.cslMobileNumPCDCheck(mrt,orderId);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return hasNonCslPCDInd;
	}
	
	public 	Map<String, String> getCslMobileCustomer(String mrt, String idDocType, String idDocNum){
		logger.debug("getCslMobileCustomer");
		List<Map<String, String>> result = null;
		try {
			result = bomDao.getCslCostomer(mrt, idDocType, idDocNum);
			if(result == null || result.size()==0)
				return null;
			else
				return result.get(0);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	
	public String getCslOfferEnableInd(String user) throws DAOException{
		logger.debug("getCslOfferEnableInd");
		String cslOfferEnableInd = null;
		try{
			cslOfferEnableInd = imsAddressInfo2Dao.getCslOfferEnableInd(user);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return cslOfferEnableInd;
	}
	
	public String getCslPcdMonthCheck() throws DAOException{
		logger.debug("getCslPcdMonthCheck");
		String cslPcdMonth = null;
		try{
			cslPcdMonth = imsAddressInfoDao.getCslPcdMonthCheck();
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return cslPcdMonth;
	}
	public String getCslShopCustInd(String user) throws DAOException{
		logger.debug("CslShopCustCheck");
		String cslShopCustInd = null;
		try{
			cslShopCustInd = imsAddressInfo2Dao.getCslShopCustInd(user);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return cslShopCustInd;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<SbRemarksDTO> getSbRemarks(String serbdyno)
	{
		List<SbRemarksDTO> list = new ArrayList<SbRemarksDTO>();
		
		try{
			list = imsAddressInfoDao.getSbRemarks(serbdyno);

		
		return list;
		}catch (Exception e){
			logger.error("Exception caught in getOtAmount()", e);
			throw new AppRuntimeException(e);
		}
		
	}
	
	public String withDelCheckAddr(String sbno, String unit, String floor) {
		logger.debug("withDelCheckAddr");
		String ltsServiceInd = null;
		try{
			ltsServiceInd = imsAddressInfoDao.withDelCheckAddr(sbno,unit,floor);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return ltsServiceInd;
	}
	
	public String getBypassLtsCheckInd() {
		try{
			String ind="";
			ind = imsAddressInfo2Dao.getBypassLtsCheckInd();
			return ind;
		}catch(DAOException de){
			logger.error("getBypassLtsCheckInd: "+de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String isSupremeHousingType(String housingType) {
		logger.debug("isSupremeHousingType");
		String isSupremeHousingType = "N";
		try{
			isSupremeHousingType = imsAddressInfoDao.isSupremeHousingType(housingType);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return isSupremeHousingType;
	}

	public List<String> getPonSBList(String sbNum) {
		logger.debug("getPonSBList is called");
		List<String> sbList = null;
		
		try{
			sbList = imsAddressInfoDao.getPonSBList(sbNum);
			
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
		return sbList;
	}

}
