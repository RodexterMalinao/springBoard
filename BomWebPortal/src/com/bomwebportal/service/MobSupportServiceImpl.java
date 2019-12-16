package com.bomwebportal.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dao.MobSupportDAO;
import com.bomwebportal.dao.MobSupportInventoryDAO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobSupportDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.dao.bom.MobSupportBomDAO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.util.WsConstants;
import com.bomwebportal.wsclient.CnmClient;
import com.bomwebportal.wsclient.HwInvClient;

public class MobSupportServiceImpl implements MobSupportService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobSupportDAO mobSupportDao;
	private MobSupportInventoryDAO mobSupportInventoryDao;
	private MobSupportBomDAO mobSupportBomDao;
	
	private static String INITIAL_ORDER_STATUS = "INITIAL";
	private static String SIGNOFF_ORDER_STATUS = "SIGNOFF";
	private CnmClient cnmClient;
	private HwInvClient hwInvClient;
	private OrderService orderService;
	
	public CnmClient getCnmClient() {
		return cnmClient;
	}
	public void setCnmClient(CnmClient cnmClient) {
		this.cnmClient = cnmClient;
	}
	public HwInvClient getHwInvClient() {
		return hwInvClient;
	}
	public void setHwInvClient(HwInvClient hwInvClient) {
		this.hwInvClient = hwInvClient;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public OrderService getOrderService() {
		return orderService;
	}
	public MobSupportBomDAO getMobSupportBomDao() {
		return mobSupportBomDao;
	}
	public void setMobSupportBomDao(MobSupportBomDAO mobSupportBomDao) {
		this.mobSupportBomDao = mobSupportBomDao;
	}
	public MobSupportDAO getMobSupportDao() {
		return mobSupportDao;
	}
	public void setMobSupportDao(MobSupportDAO mobSupportDao) {
		this.mobSupportDao = mobSupportDao;
	}

	public MobSupportInventoryDAO getMobSupportInventoryDao() {
		return mobSupportInventoryDao;
	}
	public void setMobSupportInventoryDao(MobSupportInventoryDAO mobSupportInventoryDao) {
		this.mobSupportInventoryDao = mobSupportInventoryDao;
	}
	
	public List<String[]> getAlertOrders() {
		try {
			return mobSupportDao.getAlertOrders();
		} catch (DAOException de) {
			logger.error("Exception caught in getAlertOrders()", de);
			throw new AppRuntimeException(de);
		}
	}

	public MobSupportDTO getSBOrderBasicInfo(String orderId) {
		try {
			return mobSupportDao.getSBOrderBasicInfo(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getSBOrderBasicInfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public MobSupportDTO getSBMUPOrderBasicInfo(String orderId) {
		try {
			return mobSupportDao.getSBMUPOrderBasicInfo(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getSBMUPOrderBasicInfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSBorderStatus(String orderId, String newOrderStatus, String oldOrderStatus) {
		try {
			return mobSupportDao.updateSBorderStatus(orderId, newOrderStatus, oldOrderStatus);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSBorderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateMemOrderStatus(String orderId, String newCheckPoint, String oldCheckPoint) {
		try {
			return mobSupportDao.updateMemberOrderStatus(orderId, newCheckPoint, oldCheckPoint);
		} catch (DAOException de) {
			logger.error("Exception caught in updateMemOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSBCcsOrderStatus(String orderId, String newOrderStatus, String newCheckPoint, String newReasonCd, 
			String oldOrderStatus, String oldCheckPoint) {
		try {
			return mobSupportDao.updateSBCcsOrderStatus(orderId, newOrderStatus, newCheckPoint, newReasonCd, oldOrderStatus, oldCheckPoint);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSBorderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSBorderAppDate(String orderId, Date appDate) {
		try {
			return mobSupportDao.updateSBorderAppDate(orderId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSBorderAppDate()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	private int updateSbErrMsg(String orderId, String errMsg) {
		try {
			return mobSupportDao.updateSbErrMsg(orderId, errMsg);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSbErrMsg()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	/*public String getSBsimStatus(String sim) {
		try {
			return mobSupportInventoryDao.getSBsimStatus(sim);
		} catch (DAOException de) {
			logger.error("Exception caught in getSBsimStatus()", de);
			throw new AppRuntimeException(de);
		}
	}*/
	
	/*@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSBsimStatus(String sim, String newSimStatus, String oldSimStatus) {
		try {
			return mobSupportInventoryDao.updateSBsimStatus(sim, newSimStatus, oldSimStatus);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSBsimStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	*/
	public String getWSsimStatus(String sim){
		try{
			logger.info("MobSupportServiceImpl getWSsimStatus() is called");
			logger.debug("Iccid = " + sim);		
			MobileSimInfoDTO outMobileSimInfoDTO= hwInvClient.checkIccid(sim);
			String simStatus = String.valueOf(outMobileSimInfoDTO.getHwInvStatus());
			return simStatus;
		} catch (Exception e) {
			logger.error("Exception caught in getWSsimStatus()", e);
			throw new AppRuntimeException(e);
		}
	}
	public boolean updateWSsimStatus(String sim, String newSimStatus, String oldSimStatus, String orderId, String channelId){
		String[] iccid = new String[1];
		iccid[0] = sim;
		int oSimStatus = Integer.parseInt(oldSimStatus);
		int nSimStatus = Integer.parseInt(newSimStatus);
		logger.info("oSimStatus = " + oldSimStatus);
		logger.info("nSimStatus = " + newSimStatus);
		if(oSimStatus == nSimStatus) {
			return true;
		}
		boolean isHwCallSuccess = false;
		try{
			isHwCallSuccess = hwInvClient.updateSIM("", "", oSimStatus, nSimStatus, iccid, WsConstants.OPER_CODE);
			if (!isHwCallSuccess){				
				if(!"2".equals(channelId)) {
					throw new WsOrderException(WsConstants.ERR_STATUS_HW_INV_FAILURE, WsConstants.ERR_MSG_HW_INV_FAILURE);
				} else {
					throw new WsCcsOrderException(WsConstants.ERR_STATUS_HW_INV_FAILURE, WsConstants.ERR_MSG_HW_INV_FAILURE,BomWebPortalConstant.BWP_MOBCCS_BOM_SIM_FAIL);
				}
			}
			return isHwCallSuccess;
		} catch (WsOrderException wsOrderException){
			logger.error(wsOrderException.getMessage());
			orderService.updateOrderStatus(orderId, BomWebPortalConstant.BWP_ORDER_FAILED, wsOrderException.getErrCode(), wsOrderException.getMessage(), "MOB Support");
		} catch (WsCcsOrderException wsCcsOrderException){
			logger.error(orderId + ":" + wsCcsOrderException.getReasonCd() + ":" +wsCcsOrderException.getMessage());
			orderService.updateCcsCreateOrderStatus(orderId, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, wsCcsOrderException.getReasonCd(), wsCcsOrderException.getErrCode(), wsCcsOrderException.getMessage(), "MOB Support");
		} catch (RemoteException ex){
			logger.error(ex.getMessage());
		} catch (Exception ex){
			logger.error(ex.getMessage());
		}
		return isHwCallSuccess;
	}
	
	public boolean updateMemWSsimStatus(String sim, String newSimStatus, String oldSimStatus, String orderId, String channelId){
		String[] iccid = new String[1];
		iccid[0] = sim;
		int oSimStatus = Integer.parseInt(oldSimStatus);
		int nSimStatus = Integer.parseInt(newSimStatus);
		logger.info("oSimStatus = " + oldSimStatus);
		logger.info("nSimStatus = " + newSimStatus);
		if(oSimStatus == nSimStatus) {
			return true;
		}
		boolean isHwCallSuccess = false;
		try{
			isHwCallSuccess = hwInvClient.updateSIM("", "", oSimStatus, nSimStatus, iccid, WsConstants.OPER_CODE);
			if (!isHwCallSuccess){				
				if(!"2".equals(channelId)) {
					throw new WsOrderException(WsConstants.ERR_STATUS_HW_INV_FAILURE, WsConstants.ERR_MSG_HW_INV_FAILURE);
				} else {
					throw new WsCcsOrderException(WsConstants.ERR_STATUS_HW_INV_FAILURE, WsConstants.ERR_MSG_HW_INV_FAILURE,BomWebPortalConstant.BWP_MOBCCS_BOM_SIM_FAIL);
				}
			}
			return isHwCallSuccess;
		} catch (WsOrderException wsOrderException){
			logger.error(wsOrderException.getMessage());
			orderService.updateMemOrder(orderId, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, wsOrderException.getErrCode(), wsOrderException.getMessage(), "MOB Support");
		} catch (WsCcsOrderException wsCcsOrderException){
			logger.error(orderId + ":" + wsCcsOrderException.getReasonCd() + ":" +wsCcsOrderException.getMessage());
			orderService.updateMemOrder(orderId, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, wsCcsOrderException.getErrCode(), wsCcsOrderException.getMessage(), "MOB Support");
		} catch (RemoteException ex){
			logger.error(ex.getMessage());
		} catch (Exception ex){
			logger.error(ex.getMessage());
		}
		return isHwCallSuccess;
	}
	
	
	/*public String getSBmrtStatus(String mrt) {
		try {
			return mobSupportInventoryDao.getSBmrtStatus(mrt);
		} catch (DAOException de) {
			logger.error("Exception caught in getSBmrtStatus()", de);
			throw new AppRuntimeException(de);
		}
	}*/
	/*
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSBmrtStatus(String mrt, String newMrtStatus, String oldMrtStatus) {
		try {
			return mobSupportInventoryDao.updateSBmrtStatus(mrt, newMrtStatus, oldMrtStatus);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSBmrtStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	public int freeSBmrtStatus(String mrt, String oldMrtStatus) {
		try {
			return mobSupportInventoryDao.freeSBmrtStatus(mrt, oldMrtStatus);
		} catch (DAOException de) {
			logger.error("Exception caught in freeSBmrtStatus()", de);
			throw new AppRuntimeException(de);
		}
	}*/
	public String getWSmrtStatus(String mrt, String shopCd) {
		try{
			logger.info("MobSupportServiceImpl getWSmrtStatus() is called");
			logger.debug("Mrt = " + mrt);		
			logger.debug("shopCd = " + shopCd);	
			MnpDTO outMnpDto = cnmClient.checkMsisdn(mrt, shopCd);
			String mrtStatus = String.valueOf(outMnpDto.getCnmStatus());
			logger.info("mrtStatus = " + mrtStatus);
			return mrtStatus;
		} catch (Exception e) {
			logger.error("Exception caught in getWSmrtStatus()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public String getCentralNumPoolWSmrtStatus(String mrt, String shopCd) {
		try{
			logger.info("MobSupportServiceImpl getWSmrtStatus() is called");
			logger.debug("Mrt = " + mrt);		
			String numType = "B";	
			/*List<CodeLkupDTO> cdLkupList = LookupTableBean.getInstance().getCodeLkupList().get("MRT_NUM_TYPE");
			if (cdLkupList != null&& !cdLkupList.isEmpty()) {						
				numType = (cdLkupList.get(0).getCodeId());	
			} */
			MnpDTO outMnpDto = cnmClient.checkCentralNumPoolMsisdn(mrt, shopCd, numType);
			String mrtStatus = String.valueOf(outMnpDto.getCnmStatus());
			logger.info("mrtStatus = " + mrtStatus);
			return mrtStatus;
		} catch (Exception e) {
			logger.error("Exception caught in getWSmrtStatus()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public boolean updateWSmrtStatus(String mrt, String newMrtStatus, String oldMrtStatus, String reserveId, String orderId, String channelId, boolean freeFlg){
		int oMrtStatus, nMrtStatus;
		oMrtStatus = Integer.parseInt(oldMrtStatus);
		if(!freeFlg) {
			nMrtStatus = Integer.parseInt(newMrtStatus);
		} else {
			if(reserveId == null || "".equals(reserveId.trim())){
				nMrtStatus = 2;
			} else {
				nMrtStatus = 18;
			}
		}
		logger.info("oMrtStatus = " + oMrtStatus);
		logger.info("nMrtStatus = " + nMrtStatus);
		if(nMrtStatus == oMrtStatus) {
			return true;
		}
		boolean isCnmCallSuccess = false;
		try {
			isCnmCallSuccess = cnmClient.updateMSISDN(mrt, oMrtStatus, nMrtStatus, WsConstants.CNM_TYPE, reserveId, WsConstants.INV_MOB_NO_TYP_CD_3G, null);
			if (!isCnmCallSuccess){
				if(!"2".equals(channelId)) {
					orderService.updateOrderStatus(orderId, BomWebPortalConstant.BWP_ORDER_FAILED, WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE, "MOB Support");
					throw new WsOrderException(WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE);
				} else {
					throw new WsCcsOrderException(WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE,BomWebPortalConstant.BWP_MOBCCS_BOM_MRT_FAIL);
				}
			}
			return isCnmCallSuccess;
		} catch (WsOrderException wsOrderException){
			logger.error(wsOrderException.getMessage());
			orderService.updateOrderStatus(orderId, BomWebPortalConstant.BWP_ORDER_FAILED, wsOrderException.getErrCode(), wsOrderException.getMessage(), "MOB Support");
		} catch (WsCcsOrderException wsCcsOrderException){
			logger.error(orderId + ":                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               " + wsCcsOrderException.getReasonCd() + ":" +wsCcsOrderException.getMessage());
			orderService.updateCcsOrderStatus(orderId, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, wsCcsOrderException.getReasonCd(), wsCcsOrderException.getErrCode(), wsCcsOrderException.getMessage(), "MOB Support");
		} catch (RemoteException ex){
			logger.error(ex.getMessage());
		} catch (Exception ex){
			logger.error(ex.getMessage());
		}
		return isCnmCallSuccess;
	}
	
	public boolean updateMemWSmrtStatus(String mrt, String newMrtStatus, String oldMrtStatus, String reserveId, String orderId, String channelId, boolean freeFlg){
		int oMrtStatus, nMrtStatus;
		oMrtStatus = Integer.parseInt(oldMrtStatus);
		if(!freeFlg) {
			nMrtStatus = Integer.parseInt(newMrtStatus);
		} else {
			if(reserveId == null || "".equals(reserveId.trim())){
				nMrtStatus = 2;
			} else {
				nMrtStatus = 18;
			}
		}
		logger.info("oMrtStatus = " + oMrtStatus);
		logger.info("nMrtStatus = " + nMrtStatus);
		if(nMrtStatus == oMrtStatus) {
			return true;
		}
		boolean isCnmCallSuccess = false;
		try {
			isCnmCallSuccess = cnmClient.updateMSISDN(mrt, oMrtStatus, nMrtStatus, WsConstants.CNM_TYPE, reserveId, WsConstants.INV_MOB_NO_TYP_CD_3G, null);
			if (!isCnmCallSuccess){
				if(!"2".equals(channelId)) {
					throw new WsOrderException(WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE);
				} else {
					throw new WsCcsOrderException(WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE,BomWebPortalConstant.BWP_MOBCCS_BOM_MRT_FAIL);
				}
			}
			return isCnmCallSuccess;
		} catch (WsOrderException wsOrderException){
			logger.error(wsOrderException.getMessage());
			orderService.updateMemOrder(orderId, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, wsOrderException.getErrCode(), wsOrderException.getMessage(), "MOB Support");
		} catch (WsCcsOrderException wsCcsOrderException){
			logger.error(orderId + ":" + wsCcsOrderException.getReasonCd() + ":" +wsCcsOrderException.getMessage());
			orderService.updateMemOrder(orderId, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, wsCcsOrderException.getErrCode(), wsCcsOrderException.getMessage(), "MOB Support");
		} catch (RemoteException ex){
			logger.error(ex.getMessage());
		} catch (Exception ex){
			logger.error(ex.getMessage());
		}
		return isCnmCallSuccess;
	}
	
	public String getBomSimStatus(String sim) {
		try {
			return mobSupportBomDao.getBomSimStatus(sim);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomSimStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	public String getBomMrtStatus(String mrt) {
		try {
			return mobSupportBomDao.getBomMrtStatus(mrt);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomMrtStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	public String getOcid(String orderId, String checkPt, String reqStatus) {
		try {
			return mobSupportBomDao.getOcid(orderId, checkPt, reqStatus);
		} catch (DAOException de) {
			logger.error("Exception caught in getOcid()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<String> executeUpdate(MobSupportDTO inDto) {
		List<String> failReason = new ArrayList<String>();
		int row = 1;
		
		row = updateSBorderStatus(inDto.getOrderId(), inDto.getnOrderStatus(), inDto.getoOrderStatus());
		if (row > 1 || row < 0) {
			failReason.add("SB Order Status update failed \n");
		}
		
		if (INITIAL_ORDER_STATUS.equalsIgnoreCase(inDto.getnOrderStatus())) {
			if (!Utility.date2String(inDto.getnAppDate(), "dd/MM/yyyy").equalsIgnoreCase(inDto.getoAppDate())) {
				row = updateSBorderAppDate(inDto.getOrderId(), inDto.getnAppDate());
				if (row > 1 || row < 0) {
					failReason.add("SB Order initialize failed \n");
				}
			}
		} else if ("REJECTED".equalsIgnoreCase(inDto.getnOrderStatus()) && inDto.getOrderId().startsWith("D")) {
			row = updateSbErrMsg(inDto.getOrderId(), inDto.getnErrMsg());
			if (row > 1 || row < 0) {
				failReason.add("SB Error Msg update failed \n");
			}
		}
		
		boolean isHWInvSuccess = updateWSsimStatus(inDto.getSim(), inDto.getnSimStatus(), inDto.getoSimStatus(), 
				inDto.getOrderId(), inDto.getChannelId());
		if (!isHWInvSuccess) {
			failReason.add("SB Sim status update failed \n");
		}
		/*row = updateSBsimStatus(inDto.getSim(), inDto.getnSimStatus(), inDto.getoSimStatus());
		if (row > 1 || row < 0) {
			failReason.add("SB Sim status update failed \n");
		}*/
		if ("N".equalsIgnoreCase(inDto.getMnpInd())) {
			boolean isCNMSuccess = updateWSmrtStatus(inDto.getMrt(), inDto.getnMrtStatus(), inDto.getoMrtStatus(), 
					inDto.getReserveId(), inDto.getOrderId(), inDto.getChannelId(), false);
			if(!isCNMSuccess) {
				failReason.add("SB Mrt status update failed \n");
			}
			/* original method
			row = updateSBmrtStatus(inDto.getMrt(), inDto.getnMrtStatus(), inDto.getoMrtStatus());
			if (row > 1 || row < 0) {
				failReason.add("SB Mrt status update failed \n");
			}
			*/
		}
		return failReason;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<String> executeSIMUpdate(MobSupportDTO inDto) {
		List<String> failReason = new ArrayList<String>();
		int row = 1;
		boolean isHWInvSuccess = updateWSsimStatus(inDto.getSim(), inDto.getnSimStatus(), inDto.getoSimStatus(), inDto.getOrderId(), inDto.getChannelId());
		if (!isHWInvSuccess) {
			failReason.add("SB Sim status update failed \n");
		}
		return failReason;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<String> executeMRTUpdate(MobSupportDTO inDto) {
		List<String> failReason = new ArrayList<String>();
		int row = 1;
		if ("N".equalsIgnoreCase(inDto.getMnpInd()) || ("MUPS01".equalsIgnoreCase(inDto.getMnpInd()) || "MUPS02".equalsIgnoreCase(inDto.getMnpInd()) || "MUPS03".equalsIgnoreCase(inDto.getMnpInd()) || "MUPS04".equalsIgnoreCase(inDto.getMnpInd()) || "MUPS05".equalsIgnoreCase(inDto.getMnpInd()))) {
			boolean isCNMSuccess = updateWSmrtStatus(inDto.getMrt(), inDto.getnMrtStatus(), inDto.getoMrtStatus(), inDto.getReserveId(), inDto.getOrderId(), inDto.getChannelId(), false);
			if(!isCNMSuccess) {
				failReason.add("SB Mrt status update failed \n");
			}
		} else {
			failReason.add("Not allowed to update \n");
		}
		return failReason;
	}
	
	public List<String> executeMemUpdate(MobSupportDTO inDto) {
		List<String> failReason = new ArrayList<String>();
		int row = 1;
		String nCheckPoint = "";
		if (inDto.getoOrderStatus().equals("79")) {
			if ("449".equals(inDto.getoCheckPoint()) || "999".equals(inDto.getoCheckPoint())) {
				nCheckPoint = "400";
			}
		} 
		row = updateMemOrderStatus(inDto.getOrderId(), nCheckPoint, inDto.getoCheckPoint());
		if (row > 1 || row < 0) {
			failReason.add("SB Member Order Status update failed \n");
		}
		boolean isHWInvSuccess = updateMemWSsimStatus(inDto.getSim(), "2", inDto.getoSimStatus(), 
				inDto.getOrderId(), inDto.getChannelId());
		if (!isHWInvSuccess) {
			failReason.add("SB Member Sim status update failed \n");
		}
		
		if ("N".equalsIgnoreCase(inDto.getMnpInd()) || "A".equalsIgnoreCase(inDto.getMnpInd())) {
			String newSts = "18";
			if (inDto.getReserveId() != null && !"".equals(inDto.getReserveId())){
				newSts = null;
			}
			boolean isCNMSuccess = updateMemWSmrtStatus(inDto.getMrt(), newSts, inDto.getoMrtStatus(), 
					null, inDto.getOrderId(), inDto.getChannelId(), true);
			if (!isCNMSuccess) {
				failReason.add("SB Member Mrt status update failed \n");
			}
		}
		
		return failReason;
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<String> executeCcsUpdate(MobSupportDTO inDto) {
		List<String> failReason = new ArrayList<String>();
		int row = 1;
		String nOrderStatus = "";
		String nCheckPoint = "";
		String nReasonCd = "";
		if (inDto.getoOrderStatus().equals("01")) {
			if (inDto.getoCheckPoint().equals("449") || inDto.getoCheckPoint().equals("499")) {
				nOrderStatus = "01";
				nCheckPoint = "400";
			}
		} else if (inDto.getoOrderStatus().equals("03")) {
			if (inDto.getoCheckPoint().equals("C449") || inDto.getoCheckPoint().equals("C499")) {
				nOrderStatus = "03";
				nCheckPoint = "C400";
			}
		} else if (inDto.getoOrderStatus().equals("99")) {
			if (inDto.getoReasonCd().startsWith("D")) {
				nOrderStatus = "01";
				nCheckPoint = "400";
			} else if (inDto.getoReasonCd().startsWith("L")) {
				nOrderStatus = "03";
				nCheckPoint = "C400";
			}
		}
		
		if (StringUtils.isBlank(nOrderStatus) || StringUtils.isBlank(nCheckPoint)) {
			failReason.add("SB Order Status update failed (new order_status or check_point is null) \n");
		}
		
		row = updateSBCcsOrderStatus(inDto.getOrderId(), nOrderStatus, nCheckPoint, nReasonCd, 
				inDto.getoOrderStatus(), inDto.getoCheckPoint());
		if (row > 1 || row < 0) {
			failReason.add("SB Order Status update failed \n");
		}
		boolean isHWInvSuccess = updateWSsimStatus(inDto.getSim(), "2", inDto.getoSimStatus(), 
				inDto.getOrderId(), inDto.getChannelId());
		if (!isHWInvSuccess) {
			failReason.add("SB Sim status update failed \n");
		}
		/*row = updateSBsimStatus(inDto.getSim(), "2", inDto.getoSimStatus());
		if (row > 1 || row < 0) {
			failReason.add("SB Sim status update failed \n");
		}*/
		if ("N".equalsIgnoreCase(inDto.getMnpInd()) || "A".equalsIgnoreCase(inDto.getMnpInd())) {
			String newSts = "18";
			if (inDto.getReserveId() != null && !"".equals(inDto.getReserveId())){
				newSts = null;
			}
			boolean isCNMSuccess = updateWSmrtStatus(inDto.getMrt(), newSts, inDto.getoMrtStatus(), 
					inDto.getReserveId(), inDto.getOrderId(), inDto.getChannelId(), true);
			if (!isCNMSuccess) {
				failReason.add("SB Mrt status update failed \n");
			}
			/*row = freeSBmrtStatus(inDto.getMrt(), inDto.getoMrtStatus());
			if (row > 1 || row < 0) {
				failReason.add("SB Mrt status update failed \n");
			}*/
		}
		
		return failReason;
	}
	
	public String getSbOcid(String orderId) {
		try {
			return mobSupportDao.getSbOcid(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getSbOcid()", de);
			throw new AppRuntimeException(de);
		}
	}
	public List<String[]> getConflictOrder(String orderId, String simNumber,
			String mrtNumber) {
		try {
			return mobSupportDao.getConflictOrder(orderId, simNumber, mrtNumber);
		} catch (DAOException de) {
			logger.error("Exception caught in getSbOcid()", de);
			throw new AppRuntimeException(de);
		}
	}
	public List<String> updateCcsReasonCd(String orderId) {
		List<String> failReason = new ArrayList<String>();
		int row = 1;
		try {
			row = mobSupportDao.updateCcsReasonCd(orderId);
			if (row > 1 || row < 0) {
				failReason.add("SB Order ReasonCd update failed \n");
			}
			return failReason;
		} catch (DAOException de) {
			logger.error("Exception caught in updateCcsReasonCd()", de);
			throw new AppRuntimeException(de);
		}
	}
	private class WsOrderException extends Exception{
		private String errCode = null;
		
		public WsOrderException(String pErrCode, String pErrMsg){
			super(pErrMsg);
			errCode = pErrCode;
		}
		public String getErrCode(){
			return errCode;
		}
		
	}
	private class WsCcsOrderException extends Exception{
		private String errCode = null;
		private String reasonCd = null;
		
		public WsCcsOrderException(String pErrCode, String pErrMsg, String pReasonCd){
			super(pErrMsg);
			errCode = pErrCode;
			reasonCd = pReasonCd;
		}
		public String getErrCode(){
			return errCode;
		}
		public String getReasonCd(){
			return reasonCd;
		}
	}
}
