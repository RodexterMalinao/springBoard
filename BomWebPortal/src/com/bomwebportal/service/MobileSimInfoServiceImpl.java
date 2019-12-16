package com.bomwebportal.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.dao.MobileSimInfoDAO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.SalesmanDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.wsclient.BulkNewActClient;
import com.bomwebportal.wsclient.HwInvClient;
import com.pccw.bom.mob.schemas.ResultDTO;

@Transactional(readOnly = true)
public class MobileSimInfoServiceImpl implements MobileSimInfoService {

	protected final Log logger = LogFactory.getLog(getClass());

	private HwInvClient hwInvClient;

	private BulkNewActClient bulkNewActClient;

	private MobileSimInfoDAO mobileSimInfoDao;

	public HwInvClient getHwInvClient() {
		return hwInvClient;
	}

	public void setHwInvClient(HwInvClient hwInvClient) {
		this.hwInvClient = hwInvClient;
	}

	public BulkNewActClient getBulkNewActClient() {
		return bulkNewActClient;
	}

	public void setBulkNewActClient(BulkNewActClient bulkNewActClient) {
		this.bulkNewActClient = bulkNewActClient;
	}

	public void setMobileSimInfoDao(MobileSimInfoDAO mobileSimInfoDAO) {
		this.mobileSimInfoDao = mobileSimInfoDAO;
	}

	public MobileSimInfoDAO getMobileSimInfoDao() {
		return mobileSimInfoDao;
	}

	public String getBomWebSimItemId(String basketId, String posItemCd) {
		try {
			logger.info("getBomWebSimItemId() is called in MobileSimInfoService");
			return mobileSimInfoDao.getBomWebSimItemId(basketId, posItemCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomWebSimItemId()", de);
			throw new AppRuntimeException(de);
		}
	}

	public MobileSimInfoDTO validateSim(MobileSimInfoDTO mobileSimInfoDTO) {

		// boolean result = false;
		try {
			logger.info("MobileSimInfoServiceImpl validateSim() is called");
			logger.debug("MobileSimInfoDTO Iccid = "
					+ mobileSimInfoDTO.getIccid());

			MobileSimInfoDTO outMobileSimInfoDTO = hwInvClient
					.checkIccid(mobileSimInfoDTO);
			/*
			 * if (outMobileSimInfoDTO != null) { logger.info(
			 * "MobileSimInfoServiceImpl  hwInvClient.checkIccid() output MobileSimInfoDTO iccid = "
			 * + outMobileSimInfoDTO.getIccid());
			 * logger.info("outMobileSimInfoDTO.getItemCd(): " +
			 * outMobileSimInfoDTO.getItemCd());
			 * 
			 * if
			 * (mobileSimInfoDTO.getIccid().equals(outMobileSimInfoDTO.getIccid
			 * ())){ result = true; } }
			 * 
			 * logger.debug("MobileSimInfoServiceImpl validateSim() result = " +
			 * result);
			 */
			return outMobileSimInfoDTO;
		} catch (Exception e) {
			logger.error("Exception caught in validateSim()", e);
			throw new AppRuntimeException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public boolean validateSalesCd(MobileSimInfoDTO mobileSimInfoDTO) {

		boolean result = false;

		try {
			logger.info("MobileSimInfoServiceImpl validateSalesCd() is called");
			logger.debug("MobileSimInfoDTO SalesCd = "
					+ mobileSimInfoDTO.getSalesCd());

			ResultDTO resultDTO = bulkNewActClient
					.checkBomLogin(mobileSimInfoDTO.getSalesCd());

			if (resultDTO != null) {
				logger.info("MobileSimInfoServiceImpl  bulkNewActClient.checkBomLogin() output ResultDTO Valid = "
						+ resultDTO.getValid());

				if (BomWebPortalConstant.ERRCODE_STR_SUCCESS.equals(resultDTO
						.getErrCode())) {
					result = resultDTO.getValid();
				}
			}

			logger.debug("MobileSimInfoServiceImpl validateSalesCd() result = "
					+ result);

			return result;
			// return true;
		} catch (Exception e) {
			logger.error("Exception caught in validateSalesCd()", e);
			throw new AppRuntimeException(e);
		}
	}

	// add by wilson 20110503
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<String[]> getBomWebItemCdList(String basketId, String appDate) {
		try {
			logger.info("getBomWebItemCdList() is called in MobileSimInfoService");
			return mobileSimInfoDao.getBomWebItemCdList(basketId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomWebItemCdList()", de);
			throw new AppRuntimeException(de);
		}

	}

	// add by Eliot 20110622
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public SalesmanDTO getSalesman(String salesType, String salesCd) {

		SalesmanDTO salesmanDTO = new SalesmanDTO();

		try {
			logger.info("MobileSimInfoServiceImpl getSalesman() is called");
			logger.debug("MobileSimInfoDTO SalesCd = " + salesCd);
			logger.debug("MobileSimInfoDTO SalesType = " + salesType);

			salesmanDTO = bulkNewActClient.getSalesman(salesType, salesCd);

			return salesmanDTO;
		} catch (Exception e) {
			logger.error("Exception caught in getSaleName()", e);
			throw new AppRuntimeException(e);
		}
	}

	public List<String[]> getSimPrice(String itemId, Date appDate) {
		try {
			logger.info("getSimPrice() is called in MobileSimInfoService");
			return mobileSimInfoDao.getSimPrice(itemId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getSimPrice()", de);
			throw new AppRuntimeException(de);
		}
	}

	public String getMockSimItemId(String basketId, String simType,
			String stockPool, Date appDate) {
		try {
			logger.info("getMockSimItemId() is called in MobileSimInfoService");
			return mobileSimInfoDao.getMockSimItemId(basketId, simType,
					stockPool, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getMockSimItemId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getPendingOrderExistWithIccid(String iccid) {

		try {
			logger.info("getPendingOrderExistWithIccid() is called in MobileSimInfoService");
			String bomWebOrderId = mobileSimInfoDao.getPendingOrderExistWithIccidFromBomwebSim(iccid);
			if (StringUtils.isBlank(bomWebOrderId)) {
				bomWebOrderId = mobileSimInfoDao.getPendingOrderExistWithIccidFromBomwebOrdMobMember(iccid);
			}
			return bomWebOrderId;

		} catch (DAOException de) {
			logger.error("Exception caught in getPendingOrderExistWithIccid()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getPendingOrderExistWithIccidOrderId(String iccid, String orderId) {

		try {
			logger.info("getPendingOrderExistWithIccidOrderId() is called in MobileSimInfoService");
			String bomWebOrderId = mobileSimInfoDao.getPendingOrderExistWithIccidOrderIdFromBomwebSim(iccid, orderId);
			if (StringUtils.isBlank(bomWebOrderId)) {
				bomWebOrderId = mobileSimInfoDao.getPendingOrderExistWithIccidOrderIdFromBomwebOrdMobMember(iccid, orderId);
			}
			return bomWebOrderId;

		} catch (DAOException de) {
			logger.error("Exception caught in getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public Map<String,String> getDSLocationList() {
		try {
			logger.info("getSimPrice() is called in getDSLocationList");
			List<String[]> locationList =  mobileSimInfoDao.getDSLocationList();
			Map<String,String> locationMap = new HashMap<String,String>();
			for (String[] s: locationList) {
				locationMap.put(s[0], s[1] + " - " + s[2]);
			}
			return sortByValue(locationMap);
		} catch (DAOException de) {
			logger.error("Exception caught in getDSLocationList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	 public <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
	 {
	     List<Map.Entry<K, V>> list =
	         new LinkedList<Map.Entry<K, V>>( map.entrySet() );
	     Collections.sort( list, new Comparator<Map.Entry<K, V>>()
	     {
	         public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
	         {
	             return (o1.getValue()).compareTo( o2.getValue() );
	         }
	     } );
	
	     Map<K, V> result = new LinkedHashMap<K, V>();
	     for (Map.Entry<K, V> entry : list)
	     {
	         result.put( entry.getKey(), entry.getValue() );
	     }
     return result;
 }

}
