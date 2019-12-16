package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.SupportDocAdminDAO;
import com.bomwebportal.dto.AllOrdDocDmsDTO;
import com.bomwebportal.dto.OrdDocAssgnAdminDTO;
import com.bomwebportal.dto.OrdDocAssgnAdminDTO.DmsInd;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.service.OrdDocService.AuditLogActionCd;

public class SupportDocAdminServiceImpl implements SupportDocAdminService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private SupportDocAdminDAO supportDocAdminDao;
	private OrdDocService ordDocService;
	
	public SupportDocAdminDAO getSupportDocAdminDao() {
		return supportDocAdminDao;
	}

	public void setSupportDocAdminDao(SupportDocAdminDAO supportDocAdminDao) {
		this.supportDocAdminDao = supportDocAdminDao;
	}

	public OrdDocService getOrdDocService() {
		return ordDocService;
	}

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}

	// first page start = 1
	public List<OrdDocAssgnAdminDTO> findDocAssigned(String shopCd, String lob
			, Date startDate, Date endDate
			, String orderId
			, CollectMethod collectMethod, DmsInd dmsInd
			, int start, int size) {
		try {
			int end = start+size-1;
			return supportDocAdminDao.findDocAssigned(shopCd, lob, startDate, endDate, orderId, collectMethod, dmsInd, start, end);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}

	public long countDocAssigned(String shopCd, String lob
			, Date startDate, Date endDate
			, String orderId
			, CollectMethod collectMethod, DmsInd dmsInd) {
		try {
			return supportDocAdminDao.countDocAssigned(shopCd, lob, startDate, endDate, orderId, collectMethod, dmsInd);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}


	public List<OrdDocAssgnAdminDTO> getRequiredProofTypes(String orderId) {
		try {
			return supportDocAdminDao.getRequiredProofTypes(orderId);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}


	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateWaiveReason(List<OrdDocAssgnAdminDTO> updateList) {
		if (this.isEmpty(updateList)) {
			if (logger.isDebugEnabled()) {
				logger.debug("updateList is empty");
			}
			return;
		}
		try {
			for (OrdDocAssgnAdminDTO dto : updateList) {
				if (logger.isInfoEnabled()) {
					logger.info("orderId: " + dto.getOrderId() + ", docType: " + dto.getDocType() + ", waiveReason: " + dto.getWaiveReason() + ", lastUpdBy: " + dto.getLastUpdBy());
				}
				this.ordDocService.insertAuditLog(dto.getOrderId(), dto.getDocType(), null, AuditLogActionCd.AD01, dto.getLastUpdBy());
				supportDocAdminDao.updateWaiveReason(dto.getOrderId(), dto.getDocType(), dto.getWaiveReason(), dto.getLastUpdBy());
			}
		} catch (Exception e) {
			logger.warn("Exception in updateWaiveReason", e);
			throw new AppRuntimeException(e);
		}
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateOrdDocAssgn(OrdDocAssgnDTO dto, boolean overwrite) {
		try {
			if (!overwrite) {
				int r = supportDocAdminDao.markDeleteDocAssgn(dto.getOrderId(), dto.getDocType(), dto.getLastUpdBy());
				if (r > 0)
					r = supportDocAdminDao.insertOrdDocAssgn(dto);
			} else {
				supportDocAdminDao.updateOrdDocAssgn(dto);
			}
			//supportDocAdminDao.updateOrdDocAssgn(dto);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrdDocDms(AllOrdDocDmsDTO dto) {
		try {
			return supportDocAdminDao.insertOrdDocDms(dto);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int prepareOrdDocDmsForProcessing(String lob, String updatedBy) {
		try {
			return supportDocAdminDao.prepareOrdDocDmsForProcessing(lob, updatedBy);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}



	public List<AllOrdDocDmsDTO> findAllOrdDocDmsDTO(Date processDate,
			String status, String orderId, String lob) {
		try {
			return supportDocAdminDao.findAllOrdDocDmsDTO(processDate, status, orderId, lob);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}



	public List<AllOrdDocDmsDTO> findOrderForDmsPreprocessing(String lob) {
		try {
			return supportDocAdminDao.findOrderForDmsPreprocessing(lob);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}


	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateOrdDocDms(AllOrdDocDmsDTO dto) {
		try {
			return supportDocAdminDao.updateOrdDocDms(dto);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrdDocAssgn(OrdDocAssgnDTO dto) {
		try {
			return supportDocAdminDao.insertOrdDocAssgn(dto);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	

}
