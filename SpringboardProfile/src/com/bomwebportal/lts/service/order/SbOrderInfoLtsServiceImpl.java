package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.BomOrderInfoDAO;
import com.bomwebportal.lts.dao.order.SbOrderInfoLtsDAO;
import com.bomwebportal.lts.dto.ImsBomPCDOrderDetailsDTO;
import com.bomwebportal.lts.dto.ImsPendingOrderDTO;
import com.bomwebportal.lts.dto.ImsSbOrderDetailDTO;
import com.bomwebportal.lts.dto.PcdOrderCheckAttrDTO;
import com.bomwebportal.lts.dto.order.LtsPendingSbOrderDTO;

public class SbOrderInfoLtsServiceImpl implements SbOrderInfoLtsService {
	
	private final Log logger = LogFactory.getLog(getClass());

	private SbOrderInfoLtsDAO sbOrderInfoLtsDao = null;

	private BomOrderInfoDAO bomOrderInfoDao = null;
	

	public String getSbLtsLatestPendingOrderBySrvNum(String pSrvNum, String pSrvType) {
		
		try {
			return this.sbOrderInfoLtsDao.getSbLtsLatestPendingOrderBySrvNum(pSrvNum, pSrvType);
		} catch (DAOException e) {
			logger.error("Fail to getSbLtsLatestPendingOrderBySrvNum.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to getSbLtsLatestPendingOrderBySrvNum.", e);
		}
	}
	

	public List<ImsPendingOrderDTO> getSbImsLatestPendingOrderBySrvNum(String pSrvNum) {
		
		try {
			logger.error("In getSbImsLatestPendingOrderBySrvNum." );
			List<ImsPendingOrderDTO> rtnList = new ArrayList<ImsPendingOrderDTO>();
			List<ImsPendingOrderDTO> rtnList1 = new ArrayList<ImsPendingOrderDTO>();
			List<ImsPendingOrderDTO> rtnList2 = new ArrayList<ImsPendingOrderDTO>();
			
			rtnList = null;
			
			rtnList1 = this.sbOrderInfoLtsDao.getSbImsLatestPendingOrderBySrvNum(pSrvNum);
			
			rtnList2 = this.bomOrderInfoDao.getBomImsLatestPendingOrderBySrvNum(pSrvNum);
			
			if (rtnList1 != null){
				rtnList = rtnList1;
			}
			
			if (rtnList2 != null){
				if (rtnList != null){
					rtnList.addAll(rtnList2);
				}else{
					rtnList = rtnList2;
				}
			}
			
			return rtnList;
			
		} catch (DAOException e) {
			logger.error("Fail to getSbImsLatestPendingOrderBySrvNum.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to getSbImsLatestPendingOrderBySrvNum.", e);
		}
	}

	@Override
	public List<LtsPendingSbOrderDTO> getPendingSbOrderList(String pSrvNum,
			String pSrvType) {

		try {
			List<LtsPendingSbOrderDTO> ltsPendingSbOrderList = this.sbOrderInfoLtsDao.getPendingSbOrderList(StringUtils.equals("TEL", pSrvType) ? StringUtils.leftPad(pSrvNum, 12, "0") : pSrvNum, pSrvType);
			
			if (StringUtils.equalsIgnoreCase("IMS", pSrvType)) {
				removeTimeSlotForImsNoFieldVisit(ltsPendingSbOrderList);	
			}
			
			return ltsPendingSbOrderList;
		} catch (DAOException e) {
			logger.error("Fail to getPendingSbOrderList.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to getPendingSbOrderList.", e);
		}
	}
	
	@Override
	public ImsSbOrderDetailDTO getPcdSbOrder(String orderId) {
		try {
			logger.error("In getSbImsLatestPendingOrderBySrvNum." );
			ImsSbOrderDetailDTO  result = new ImsSbOrderDetailDTO ();
			ImsBomPCDOrderDetailsDTO bomAmendDetail = new ImsBomPCDOrderDetailsDTO();
			
			result = null;
			
			result = this.sbOrderInfoLtsDao.getPcdSbOrder(orderId);
			
			bomAmendDetail = this.bomOrderInfoDao.getBomPcdSbOrderAmendDetail(orderId, "GetTime");
			
			if(bomAmendDetail!=null){
				if(bomAmendDetail.getApplicationStartDate()!=null)
					result.setAppntStartTime(bomAmendDetail.getApplicationStartDate());
				if(bomAmendDetail.getApplicationEndDate()!=null)
					result.setAppntEndTime(bomAmendDetail.getApplicationEndDate());
			}
			return result;
			
		} catch (DAOException e) {
			logger.error("Fail to getPcdSbOrder.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to getPcdSbOrder.", e);
		}
	}

	public List<PcdOrderCheckAttrDTO> getPcdSbOrderDetails(String orderId){
		try {			
			return this.sbOrderInfoLtsDao.getPcdSbOrderDetails(orderId);
			
		} catch (DAOException e) {
			logger.error("Fail to getPcdSbOrderDetails.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to getPcdSbOrderDetails.", e);
		}
	}
	
	public String getPcdActivationDate(String orderId, String preInstallCheck, String pcdBundleFreeType) {

		try {
			String pcdActivationDate = null;
			
			if(StringUtils.isNotBlank(orderId) 
					&& "Y".equals(preInstallCheck)){		
				
					pcdActivationDate = this.sbOrderInfoLtsDao.getPcdActivationDate(orderId);
	
			}
			return pcdActivationDate;
			
		} catch (DAOException e) {
			logger.error("Fail to getPcdActivationDate.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to getPcdActivationDate.", e);
		}
	}
	
	public String getPcdSbOrderHasDel(String orderId, String pcdBundleFreeType){
		try {
			//logger.error("In getPcdSbOrderHasDel." );
			
			String resultStr = this.sbOrderInfoLtsDao.getPcdSbOrderHasDel(orderId, pcdBundleFreeType);
			
			return resultStr;
			
		} catch (DAOException e) {
			logger.error("Fail to getPcdSbOrderHasDel.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to getPcdSbOrderHasDel.", e);
		}
	}
	
	public String checkAnyActiveServiceWithinXMonths(String srvbdry_num, String floor_num, String unit_num, String prevSerTermMth){
		try {			
			String resultStr = this.bomOrderInfoDao.checkAnyActiveServiceWithinXMonths(srvbdry_num, floor_num, unit_num, prevSerTermMth);
			
			return resultStr;
			
		} catch (DAOException e) {
			logger.error("Fail to checkAnyActiveServiceWithinXMonths.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to checkAnyActiveServiceWithinXMonths.", e);
		}
	}
	
	private void removeTimeSlotForImsNoFieldVisit(List<LtsPendingSbOrderDTO> ltsPendingSbOrderList) {
		if (ltsPendingSbOrderList == null || ltsPendingSbOrderList.isEmpty()) {
			return;
		}
		
		for (LtsPendingSbOrderDTO ltsPendingSbOrder : ltsPendingSbOrderList) {
			if (!StringUtils.equalsIgnoreCase("Y", ltsPendingSbOrder.getFieldVisitInd())) {
				if (StringUtils.isNotBlank(ltsPendingSbOrder.getAppntStartTime())) {
					ltsPendingSbOrder.setAppntStartTime(StringUtils.split(ltsPendingSbOrder.getAppntStartTime(), " ")[0]);	
				}
				if (StringUtils.isNotBlank(ltsPendingSbOrder.getAppntEndTime())) {
					ltsPendingSbOrder.setAppntEndTime(StringUtils.split(ltsPendingSbOrder.getAppntEndTime(), " ")[0]);	
				}
			}
		}
	}
	
	public SbOrderInfoLtsDAO getSbOrderInfoLtsDao() {
		return sbOrderInfoLtsDao;
	}

	public void setSbOrderInfoLtsDao(SbOrderInfoLtsDAO sbOrderInfoLtsDao) {
		this.sbOrderInfoLtsDao = sbOrderInfoLtsDao;
	}


	public void setBomOrderInfoDao(BomOrderInfoDAO bomOrderInfoDao) {
		this.bomOrderInfoDao = bomOrderInfoDao;
	}


	public BomOrderInfoDAO getBomOrderInfoDao() {
		return bomOrderInfoDao;
	}


	
}
