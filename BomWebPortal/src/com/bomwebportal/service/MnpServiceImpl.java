package com.bomwebportal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.NewMSISDNAnonType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.MnpDAO;
import com.bomwebportal.dao.MnpNumDAO;
import com.bomwebportal.dto.CnpDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.exception.WebServiceException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;
import com.bomwebportal.mob.dao.bom.BomOrderDAO;
import com.bomwebportal.mob.ds.dao.MobDsMrtManagementDAO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.wsclient.CnmClient;
import com.bomwebportal.wsclient.MvnoWSClient;
import com.bomwebportal.wsclient.exception.WsClientException;
import com.hkt.aip.oadapi.OneADService;
import com.hkt.aip.oadapi.data.GetDNORequest;
import com.hkt.aip.oadapi.data.GetDNOResponse;
import com.bomwebportal.util.Utility;

import bom.mob.schema.javabean.si.xsd.CslNoStatusDTO;
import bom.mob.schema.javabean.si.xsd.GetCurrentDNODTO;

@Transactional(readOnly=true)
public class MnpServiceImpl implements MnpService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private CnmClient cnmClient;
	private MnpDAO mnpDAO;
	private MnpNumDAO mnpNumDAO;
	private BomOrderDAO bomOrderDAO;
	private MobDsMrtManagementDAO mobDsMrtManagementDAO;
	private MobCcsOrderDAO mobCcsOrderDAO;
	private MvnoWSClient mvnoWSClient;
	private OneADService oneADService;
	
	public CnmClient getCnmClient() {
		return cnmClient;
	}
	
	public void setCnmClient(CnmClient cnmClient) {
		this.cnmClient = cnmClient;
	}
	

	public MnpDAO getMnpDAO() {
		return mnpDAO;
	}

	public void setMnpDAO(MnpDAO mnpDAO) {
		this.mnpDAO = mnpDAO;
	}

	public MnpNumDAO getMnpNumDAO() {
		return mnpNumDAO;
	}

	public void setMnpNumDAO(MnpNumDAO mnpNumDAO) {
		this.mnpNumDAO = mnpNumDAO;
	}
	
	public MobDsMrtManagementDAO getMobDsMrtManagementDAO() {
		return mobDsMrtManagementDAO;
	}

	public void setMobDsMrtManagementDAO(MobDsMrtManagementDAO mobDsMrtManagementDAO) {
		this.mobDsMrtManagementDAO = mobDsMrtManagementDAO;
	}
	
	public MobCcsOrderDAO getMobCcsOrderDAO() {
		return mobCcsOrderDAO;
	}

	public void setMobCcsOrderDAO(MobCcsOrderDAO mobCcsOrderDAO) {
		this.mobCcsOrderDAO = mobCcsOrderDAO;
	}

	public MvnoWSClient getMvnoWSClient() {
		return mvnoWSClient;
	}

	public void setMvnoWSClient(MvnoWSClient mvnoWSClient) {
		this.mvnoWSClient = mvnoWSClient;
	}

	public OneADService getOneADService() {
		return oneADService;
	}
	
	public void setOneADService(OneADService oneADService) {
		this.oneADService = oneADService;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public MnpDTO validateMnpMsisdn(MnpDTO mnpDTO){

		MnpDTO result = new MnpDTO();
		
		try{
			logger.info("MnpServiceImpl validateMnpMsisdn() is called");
			mnpDTO.setMsisdn(mnpDTO.getMnpMsisdn());
			logger.info("MnpDTO MnpMsisdn = " + mnpDTO.getMnpMsisdn());
			
			result = mnpDAO.checkAdMsisdn(mnpDTO);
			
			if (result != null) {
				logger.info("MnpServiceImpl mnpDAO.checkAdMsisdn() output MnpDTO Dno = " + result.getDno());
			}
			
			logger.info("MnpServiceImpl mnpDAO.checkAdMsisdn() result = " + result);
				
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in validateMnpMsisdn()", e);
			throw new AppRuntimeException(e);
		}
	}
	

	
	public MnpDTO validateNewMsisdn(MnpDTO mnpDTO){
		
		String numType = "B";	//Dennis MIP3
		/*List<CodeLkupDTO> cdLkupList = LookupTableBean.getInstance().getCodeLkupList().get("MRT_NUM_TYPE");
			
		if (cdLkupList != null&& !cdLkupList.isEmpty()) {						
			numType = (cdLkupList.get(0).getCodeId());	
		} */
		
		//boolean result = false;
		MnpDTO outMnpDTO = new MnpDTO();
		try{
			logger.info("MnpServiceImpl validateNewMsisdn() is called");
			
			mnpDTO.setMsisdn(mnpDTO.getNewMsisdn());
			
			logger.info("validateNewMsisdn() input MnpDTO ShopCd = " + mnpDTO.getShopCd());
			logger.info("validateNewMsisdn() input MnpDTO NumType = " + mnpDTO.getNumType());
			
			//outMnpDTO = cnmClient.checkMsisdn(mnpDTO.getNewMsisdn(), mnpDTO.getShopCd());  //change from 20150203
			if (StringUtils.isNotEmpty(mnpDTO.getNewMsisdn())){
			outMnpDTO = cnmClient.checkCentralNumPoolMsisdn(mnpDTO.getNewMsisdn(), mnpDTO.getShopCd(), numType);
			}
			
			/*
			if (outMnpDTO != null) {
				logger.info("MnpServiceImpl cnmClient.checkMsisdn() output result = " + outMnpDTO.getCnmStatus());
				
				if (BomWebPortalConstant.CNM_STATUS_NORMAL == outMnpDTO.getCnmStatus()){
					result = true;
				} 
			} 
			
			return result;
			*/
		} catch (Exception e) {
			logger.error("Exception caught in validateNewMsisdn()", e);
			throw new AppRuntimeException(e);
		}
		return outMnpDTO;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public MnpDTO validateMnpMsisdn(String msisdn){

		MnpDTO result = new MnpDTO();
		
		try{
			logger.info("MnpServiceImpl validateMnpMsisdn() is called");
			//mnpDTO.setMsisdn(mnpDTO.getMnpMsisdn());
			logger.info("msisdn = " + msisdn);
			
			result = mnpDAO.checkAdMsisdn(msisdn);
			
			if (result != null) {
				logger.info("MnpServiceImpl mnpDAO.checkAdMsisdn() output MnpDTO Dno = " + result.getDno());
			}
			
			logger.info("MnpServiceImpl mnpDAO.checkAdMsisdn() result = " + result);
				
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in validateMnpMsisdn()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public String checkBomPendingOrder(String mobileNumber) {
		
		try {
			return bomOrderDAO.checkBomPendingOrder(mobileNumber);
		} catch (DAOException e) {
			logger.error("Exception caught in checkBomPendingOrder()", e);
		}
		
		return null;
	}

	public BomOrderDAO getBomOrderDAO() {
		return bomOrderDAO;
	}

	public void setBomOrderDAO(BomOrderDAO bomOrderDAO) {
		this.bomOrderDAO = bomOrderDAO;
	}
	
	public MnpDTO validateCnmMsindn(String msisdn, String shopCd){
		MnpDTO outMnpDTO = new MnpDTO();
		try{
			logger.info("MnpServiceImpl validateCnmMsindn() is called");
			outMnpDTO = cnmClient.checkMsisdn(msisdn, shopCd);
			
		} catch (Exception e) {
			logger.error("Exception caught in validateCnmMsindn()", e);
			throw new AppRuntimeException(e);
		}
		return outMnpDTO;
	}
	
	public boolean isPccw3gPrepaidNumber(String msisdn){
		
		MnpDTO outMnpDTO = new MnpDTO();
		try{
			logger.info("MnpServiceImpl validateCnmMsindn() is called");
			outMnpDTO = cnmClient.checkMsisdn(msisdn, "3GPP");
			
		} catch (Exception e) {
			logger.error("Exception caught in validateCnmMsindn()", e);
			throw new AppRuntimeException(e);
		}
		
		if (!StringUtils.isEmpty(outMnpDTO.getMsisdn())){
			return true;
		}else{
			return false;
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getMrtNum(String staffId, List<String> grade, Date appDate, String numType) {
    	try {
    		logger.info("getMrtNum() is called in MnpServiceImpl");
    		String channelCd = mobDsMrtManagementDAO.getSalesChannelCd(staffId, appDate);
    		String centreCd = mobDsMrtManagementDAO.getSalesCentreCd(staffId, appDate);
    		String shopCd = mobDsMrtManagementDAO.getSalesTeamCd(staffId, appDate);
    		return mnpNumDAO.getMrtNum(staffId, channelCd, centreCd, shopCd, grade, numType);
    	} catch (DAOException de) {
    		logger.error("Exception caught in getMrtNum", de);
    		throw new AppRuntimeException(de);
    	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getCCSMrtNum(String channelCd, String grade, String numType) {
    	try {
    		logger.info("getCCSMrtNum() is called in MnpServiceImpl");
    		return mnpNumDAO.getCCSMrtNum(channelCd, grade, numType);

    	} catch (DAOException de) {
    		logger.error("Exception caught in getCCSMrtNum", de);
    		throw new AppRuntimeException(de);
    	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public String[] getMrtNum(String oldMsisdn, String channelCd, String numType) {
    	try {
    		logger.info("getMrtNum() is called in MnpServiceImpl");
    		return mnpNumDAO.getMrtNum(oldMsisdn, channelCd, numType);

    	} catch (DAOException de) {
    		logger.error("Exception caught in getMrtNum", de);
    		throw new AppRuntimeException(de);
    	}
    }
    
    
    public MnpDTO checkCCSPendingOrder(String orderId, String msisdn){
		
    	MnpDTO result = new MnpDTO();
		
		try{
			logger.info("MnpServiceImpl checkCCSPendingOrder() is called");			
			result = mobCcsOrderDAO.checkCCSPendingOrder(orderId, msisdn);
				
			if (result == null){
				logger.info("MnpServiceImpl checkCCSPendingOrder() result = " + result);
			}
			if (result != null) {
				logger.info("MnpServiceImpl checkCCSPendingOrder() result != null ");
				logger.info("MnpServiceImpl checkCCSPendingOrder() orderId = " + result.getOrderId());
			}
				
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in checkCCSPendingOrder()", e);
			throw new AppRuntimeException(e);
		}
	}
    
    public List<String> checkReserveInfo(String msisdn, String shopCd){
		String cnmReserveId = this.getReserveInfoFromCNM(msisdn, shopCd);
		String sbReserveId = "";
		List<String> resultList = new ArrayList <String> ();
		
		try{
			logger.info("MnpServiceImpl validateAgnMsindn() is called");
			sbReserveId = mobDsMrtManagementDAO.getAssignResId(msisdn, shopCd);
		} catch (Exception e) {
			logger.error("Exception caught in validateCnmMsindn()", e);
			throw new AppRuntimeException(e);
		}
		if(cnmReserveId != null && sbReserveId != null) {
			if(sbReserveId.equals(cnmReserveId)){
				resultList.add("Y");
				resultList.add(sbReserveId);
				return resultList;
			} 
		} 
		
		resultList.add("N");
		resultList.add("");
		return resultList;
	}
    
    public String getReserveInfoFromCNM(String msisdn, String shopCd) {
		MnpDTO result = new MnpDTO();
		try{
			logger.info("MnpServiceImpl getReserveInfoFromCNM() is called");
			result = cnmClient.checkMsisdn(msisdn, shopCd);
			
			if(result != null) {
				return result.getReserveId();
			}
		} catch (Exception e) {
			logger.error("Exception caught in getReserveInfoFromCNM()", e);
			throw new AppRuntimeException(e);
		}
		return null;
	}
    
    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getDno(String msisdn){

		MnpDTO result = new MnpDTO();
		String returnString="ERROR";
		try{
			logger.info("MnpServiceImpl validateMnpMsisdn() is called");
			//mnpDTO.setMsisdn(mnpDTO.getMnpMsisdn());
			logger.info("msisdn = " + msisdn);
			
			result = mnpDAO.checkAdMsisdn(msisdn);
			
			if (result != null) {
				logger.info("MnpServiceImpl mnpDAO.checkAdMsisdn() output MnpDTO Dno = " + result.getDno());
			}
			
			logger.info("MnpServiceImpl mnpDAO.checkAdMsisdn() result = " + result);
				
			if (result != null && !"".equals(result.getDno().trim())) {
				if (BomWebPortalConstant.DN_STR_PCCW3G.equals(result
						.getDno())
						|| BomWebPortalConstant.DN_STR_ERR
								.equals(result.getDno())) {
					returnString="ERROR";
				} else {
					
						returnString=result.getDno();
					
				}
			} else {
				returnString="ERROR";
			}
			return returnString;
		} catch (Exception e) {
			logger.error("Exception caught in validateMnpMsisdn()", e);
			throw new AppRuntimeException(e);
		}
	}
    

	public List<CnpDTO> getNewMsisdn(String msisdn, int status, String shopCd, String msisdnlvl, String numType, int returnQty) {
		
		List<CnpDTO> result = null;
		try{
			logger.info("MnpServiceImpl getNewMsisdn() is called");
			NewMSISDNAnonType[] output = cnmClient.getNewMsisdn(msisdn,status, shopCd, msisdnlvl, numType, returnQty);
			
			if (output != null && output.length > 0) {
				result = new ArrayList<CnpDTO>();
				for (int i = 0 ; i < output.length ;i++){
					CnpDTO temp = new CnpDTO();
					temp.setMsisdn(output[i].getMsisdn());
					temp.setNumType(output[i].getNumType());
					temp.setCity(output[i].getCity());
					temp.setCustomerName(output[i].getCustomerName());
					temp.setDepartmentCode(output[i].getDepartmentCode());
					temp.setLevel(output[i].getLevel());
					temp.setParentDepartmentCode(output[i].getParentDepartmentCode());
					temp.setPool(output[i].getPool());
					temp.setPortInIndicator(output[i].getPortInIndicator());
					temp.setRegion(output[i].getRegion());
					temp.setReservationID(output[i].getReserveID());
					temp.setSalesmanID(output[i].getSalesmanID());
					temp.setStatus(output[i].getStatus());
					temp.setType(output[i].getType());
					result.add(temp);
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in getNewMsisdn()", e);
			throw new AppRuntimeException(e);
		}
		
	}
	
public List<CnpDTO> getNew1C2NMsisdn(String msisdn, int status, String shopCd, String msisdnlvl, String cityCd, String numType, int returnQty) {
		
		List<CnpDTO> result = null;
		try{
			logger.info("MnpServiceImpl getNew1C2NMsisdn() is called");
			NewMSISDNAnonType[] output = cnmClient.getNew1C2NMsisdn(msisdn,status, shopCd, msisdnlvl, cityCd, numType, returnQty);
			
			if (output != null && output.length > 0) {
				result = new ArrayList<CnpDTO>();
				for (int i = 0 ; i < output.length ;i++){
					CnpDTO temp = new CnpDTO();
					temp.setMsisdn(output[i].getMsisdn());
					temp.setNumType(output[i].getNumType());
					temp.setCity(output[i].getCity());
					temp.setCustomerName(output[i].getCustomerName());
					temp.setDepartmentCode(output[i].getDepartmentCode());
					temp.setLevel(output[i].getLevel());
					temp.setParentDepartmentCode(output[i].getParentDepartmentCode());
					temp.setPool(output[i].getPool());
					temp.setPortInIndicator(output[i].getPortInIndicator());
					temp.setRegion(output[i].getRegion());
					temp.setReservationID(output[i].getReserveID());
					temp.setSalesmanID(output[i].getSalesmanID());
					temp.setStatus(output[i].getStatus());
					temp.setType(output[i].getType());
					result.add(temp);
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in getNew1C2NMsisdn()", e);
			throw new AppRuntimeException(e);
		}
		
	}

public List<CnpDTO> getNewSsMsisdn(String msisdn, int status, String shopCd, String msisdnlvl, String numType, int returnQty) {
	
	List<CnpDTO> result = null;
	try{
		logger.info("MnpServiceImpl getNewSsMsisdn() is called");
		NewMSISDNAnonType[] output = cnmClient.getNewSsMsisdn(msisdn,status, shopCd, msisdnlvl, numType, returnQty);
		
		if (output != null && output.length > 0) {
			result = new ArrayList<CnpDTO>();
			for (int i = 0 ; i < output.length ;i++){
				CnpDTO temp = new CnpDTO();
				temp.setMsisdn(output[i].getMsisdn());
				temp.setNumType(output[i].getNumType());
				temp.setCity(output[i].getCity());
				temp.setCustomerName(output[i].getCustomerName());
				temp.setDepartmentCode(output[i].getDepartmentCode());
				temp.setLevel(output[i].getLevel());
				temp.setParentDepartmentCode(output[i].getParentDepartmentCode());
				temp.setPool(output[i].getPool());
				temp.setPortInIndicator(output[i].getPortInIndicator());
				temp.setRegion(output[i].getRegion());
				temp.setReservationID(output[i].getReserveID());
				temp.setSalesmanID(output[i].getSalesmanID());
				temp.setStatus(output[i].getStatus());
				temp.setType(output[i].getType());
				result.add(temp);
			}
		}
		return result;
	} catch (Exception e) {
		logger.error("Exception caught in getNewSsMsisdn()", e);
		throw new AppRuntimeException(e);
	}
	
}
	
	
	public boolean reserveMsisdn(String msisdn, String actionType, String shopCd){
	
		boolean isReserveCnmSuccess = false;
		
		try {
			isReserveCnmSuccess = cnmClient.reserveCentralNumPoolMsisdn(msisdn, actionType, shopCd);
			return isReserveCnmSuccess;
		}  catch (Exception e) {
			logger.error("Exception caught in reserveMsisdn()", e);
			throw new AppRuntimeException(e);
		}
	}

	
	public GetCurrentDNODTO getCurrDNODTO(String msisdn) {
		
		logger.info("getCurrDNODTO() is called");
		
		GetCurrentDNODTO output = new GetCurrentDNODTO();
		
		GetDNORequest req = new GetDNORequest();
		
		req.setMsisdn(msisdn);
		
		GetDNOResponse resp = oneADService.getDNO(req);
		
		logger.info(Utility.toPrettyJson(req));
		
		if ("0".equals(resp.getRetCode())) {
			
			if (StringUtils.isNotEmpty(resp.getActDno())) {
				output.setActDNO(resp.getActDno());
			}
			
			if (StringUtils.isNotEmpty(resp.getOdno())) {
				output.setDirectoryNo(resp.getOdno());
			}
			
			if (StringUtils.isNotEmpty(resp.getDno())) {
				output.setDno(resp.getDno());
			}
			
			logger.info(Utility.toPrettyJson(resp));
			
		} else {
			
			logger.info("Exception caught in getCurrDNODTO()");
			
			throw new AppRuntimeException("Error Code: " + resp.getRetCode() + " / Error Message: " + resp.getRetDesc());
			
		}

		return output;
		
	}
	
	public  CslNoStatusDTO checkNoStatusInCInv(String msisdn){
		
		CslNoStatusDTO output = new CslNoStatusDTO();
		
		
		try{
			logger.info("MnpServiceImpl checkNoStatusInCInv() is called");
			output =  mvnoWSClient.checkNoStatusInCInv(msisdn);
			
		} catch (WsClientException wse){
			throw new AppRuntimeException(wse.getErrMessage()+"("+wse.getErrCode()+")");
		} catch (Exception e) {
		
			logger.error("Exception caught in checkNoStatusInCInv()", e);
			throw new AppRuntimeException(e.getMessage());
		}
		return output;
		
	}
	
	
	public MnpDTO checkCentralNumPoolMsisdnMIP(String msisdn){
		
		MnpDTO outMnpDTO = null;
		try{
			logger.info("MnpServiceImpl checkCentralNumPoolMsisdnMIP() is called");

			outMnpDTO = cnmClient.checkCentralNumPoolMsisdnMIP(msisdn);
			if (!StringUtils.equals(msisdn, outMnpDTO.getMsisdn())){
				return null;
			}
			
		} catch (WebServiceException wse){
			throw new AppRuntimeException("Error Msg: "+wse.getMessage());
		}	catch (Exception e) {
			logger.error("Exception caught in checkCentralNumPoolMsisdnMIP()", e);
			throw new AppRuntimeException(e.getMessage());
		}
		return outMnpDTO;
	}
	
	public Integer checkIsWhiteList(String msisdn) {
		logger.info("checkIsWhiteList() called");
		try {
			return this.mnpNumDAO.checkIsWhiteList(msisdn);
		} catch (Exception e) {
			logger.error("Exception caught in checkIsWhiteList()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	public Integer getCSLBillPeriod(String msisdn){
		try{
			logger.info("MnpServiceImpl getCSLBillPeriod() is called");

			return this.mnpNumDAO.getCSLBillPeriod(msisdn);
			
		} catch (Exception e) {
			logger.error("Exception caught in checkCentralNumPoolMsisdnMIP()", e);
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	public Integer checkPendingOrder(String msisdn, String orderId){
		try{
			logger.info("MnpServiceImpl checkPendingOrder() is called");

			return this.mnpNumDAO.checkPendingOrder(msisdn, orderId);
			
		} catch (Exception e) {
			logger.error("Exception caught in checkPendingOrder()", e);
			throw new AppRuntimeException(e.getMessage());
		}
	}

	public Integer checkPendingMUPOrder(String msisdn, String orderId){
		try{
			logger.info("MnpServiceImpl checkPendingOrder() is called");

			return this.mnpNumDAO.checkPendingMUPOrder(msisdn, orderId);
			
		} catch (Exception e) {
			logger.error("Exception caught in checkPendingOrder()", e);
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
}
