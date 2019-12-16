package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.OrdEmailReqDAO;
import com.bomwebportal.dao.OrdEmailReqWriteDAO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.service.OrdEmailReqService;

@Transactional(readOnly = true)
public class OrdEmailReqServiceImpl implements OrdEmailReqService {
	
	/*
	Initial copied on 20140925 from Version 1.7 of
	\Springboard\SBWPR3\BomWebPortal\src\com\bomwebportal\service\OrdEmailReqServiceImpl.java
	*/
	
	
	protected final Log logger = LogFactory.getLog(getClass());

	private OrdEmailReqDAO ordEmailReqDAO;
	private OrdEmailReqWriteDAO ordEmailReqWriteDAO;

	public OrdEmailReqDAO getOrdEmailReqDAO() {
		return ordEmailReqDAO;
	}

	public void setOrdEmailReqDAO(OrdEmailReqDAO pOrdEmailReqDAO) {
		this.ordEmailReqDAO = pOrdEmailReqDAO;
	}

	public OrdEmailReqWriteDAO getOrdEmailReqWriteDAO() {
		return ordEmailReqWriteDAO;
	}

	public void setOrdEmailReqWriteDAO(OrdEmailReqWriteDAO pOrdEmailReqWriteDAO) {
		this.ordEmailReqWriteDAO = pOrdEmailReqWriteDAO;
	}

	public List<OrdEmailReqDTO> getOrdEmailReqDTOList(String pLob) {
		// Renamed - previously getOrdEmailReqDTOALL(String lob)
		logger.info("getOrdEmailReqDTOList() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOList(pLob);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOList()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}

	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOListByOrderAndTemplateId(String pOrderId, String pTemplateId, String pLob) {
		// Renamed - previously getOrdEmailReqDTOALLByOrderId(String orderId, String templateId)
		logger.info("getOrdEmailReqDTOALLByOrderId() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOListByOrderAndTemplateId(pOrderId, pTemplateId, pLob);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLByOrderId()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
/*	// Replaced By getOrdEmailReqDTOListByOrderId(String pOrderId, String pLob) instead
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOrderIdIMS(String orderId) {
		logger.info("getOrdEmailReqDTOALLByOrderId() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOALLByOrderIdIMS(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLByOrderId()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
*/
	
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOListBySearchCriteria(String pOrderId,
			String pShopCd, String pLob, Date pRequestDate, String pTemplateId) {		
		// Renamed - previously getOrdEmailReqDTOALLBySearch(String orderId,String shopCd, String lob, Date requestDate, String templateId)
		
		logger.info("getOrdEmailReqDTOListBySearchCriteria() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOListBySearchCriteria(pOrderId, pShopCd, pLob, pRequestDate, pTemplateId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOListBySearchCriteria()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOListByChannelIdAndTeam(String pOrderId,
			int pChannelId, String pLob, Date pRequestDate, String pTemplateId, String pTeam) {
		// Renamed - previously getOrdEmailReqDTOLTSIMSBySearch(String orderId,	int ChannelId, Date requestDate, String templateId, String team)
		
		logger.info("getOrdEmailReqDTOALLBySearch() called");
		logger.info("orderId:" + pOrderId);
		logger.info("pLob:" + pLob);
		logger.info("ChannelId:" + pChannelId);
		logger.info("requestDate:" + pRequestDate);
		logger.info("templateId:" + pTemplateId);
		logger.info("team:" + pTeam);
		
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOListByChannelIdAndTeam(pOrderId, pChannelId, pLob, pRequestDate, pTemplateId, pTeam);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOListByChannelIdAndTeam()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	
	public OrdEmailReqDTO getOrdEmailReqDTO(String pRowId) {
		logger.info("getOrdEmailReqDTO() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTO(pRowId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTO()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	public int getNextSeq(String pOrderId, String pTemplateId, String pLob) {
		logger.info("getNextSeq() called");
		try {
			return this.ordEmailReqDAO.getNextSeq(pOrderId, pTemplateId, pLob);
		} catch (Exception e) {
			logger.error("Exception caught in getNextSeq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
/*	// Replaced getNextSeq(String pOrderId, String pTemplateId, String pLob) instead
	public int getNextSeqIMS(String orderId) {
		logger.info("getNextSeq() called");
		try {
			return this.ordEmailReqDAO.getNextSeqIMS(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in getNextSeq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
*/
	
	public OrdEmailReqDTO getOrdEmailReqDTOByOrderIdAndSeqNo(String pOrderId, int pSeqNo, String pTemplateId) {
		logger.info("getOrdEmailReqDTOByOrderIdAndSeqNo() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOByOrderIdAndSeqNo(pOrderId, pSeqNo, pTemplateId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOByOrderIdAndSeqNo()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrdSMSReq(OrdEmailReqDTO pDto) {
		logger.info("insertOrdSMSReq() called");
		try {
			return this.ordEmailReqWriteDAO.insertOrdSMSReq(pDto);
		} catch (Exception e) {
			logger.error("Exception caught in insertOrdSMSReq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrdEmailReq(OrdEmailReqDTO pDto) {
		logger.info("insertOrdEmailReq() called");
		try {
			return this.ordEmailReqWriteDAO.insertOrdEmailReq(pDto);
		} catch (Exception e) {
			logger.error("Exception caught in insertOrdEmailReq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateOrdEmailReq(OrdEmailReqDTO pDto) {
		logger.info("updateOrdEmailReq() called");
		try {
			return this.ordEmailReqWriteDAO.updateOrdEmailReq(pDto);
		} catch (Exception e) {
			logger.error("Exception caught in updateOrdEmailReq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}

	public int updateOrdSMSReq(OrdEmailReqDTO pDto) {
		logger.info("updateOrdSMSReq() called");
		try {
			return this.ordEmailReqWriteDAO.updateOrdSMSReq(pDto);
		} catch (Exception e) {
			logger.error("Exception caught in updateOrdSMSReq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}

	public List<OrdEmailReqDTO> getOrdEmailReqDTOListByOrderId(String pOrderId, String pLob) {
		// Renamed - previously getOrdEmailReqDTOALLByOnlyOrderId(String orderId)
		logger.info("getOrdEmailReqDTOALLByOrderId() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOListByOrderId(pOrderId, pLob);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLByOrderId()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
}
