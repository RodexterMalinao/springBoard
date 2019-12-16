package com.bomltsportal.service.email;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomltsportal.dao.email.OrdEmailReqDAO;
import com.bomltsportal.dao.email.OrdEmailReqWriteDAO;
import com.bomltsportal.dto.email.OrdEmailReqDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomltsportal.service.email.OrdEmailReqService;

@Transactional(readOnly = true)
public class OrdEmailReqServiceImpl implements OrdEmailReqService {
	protected final Log logger = LogFactory.getLog(getClass());

	private OrdEmailReqDAO ordEmailReqDAO;
	private OrdEmailReqWriteDAO ordEmailReqWriteDAO;

	public OrdEmailReqDAO getOrdEmailReqDAO() {
		return ordEmailReqDAO;
	}

	public void setOrdEmailReqDAO(OrdEmailReqDAO ordEmailReqDAO) {
		this.ordEmailReqDAO = ordEmailReqDAO;
	}

	public OrdEmailReqWriteDAO getOrdEmailReqWriteDAO() {
		return ordEmailReqWriteDAO;
	}

	public void setOrdEmailReqWriteDAO(OrdEmailReqWriteDAO ordEmailReqWriteDAO) {
		this.ordEmailReqWriteDAO = ordEmailReqWriteDAO;
	}

	public List<OrdEmailReqDTO> getOrdEmailReqDTOALL(String lob) {
		logger.info("getOrdEmailReqDTOALL() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOALL(lob);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALL()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}

	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOrderId(String orderId, String templateId) {
		logger.info("getOrdEmailReqDTOALLByOrderId() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOALLByOrderId(orderId, templateId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLByOrderId()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}

	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearch(String orderId,
			String shopCd, String lob, Date requestDate, String templateId) {
		logger.info("getOrdEmailReqDTOALLBySearch() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOALLBySearch(orderId, shopCd, lob, requestDate, templateId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLBySearch()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	public OrdEmailReqDTO getOrdEmailReqDTO(String rowId) {
		logger.info("getOrdEmailReqDTO() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTO(rowId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTO()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	public int getNextSeq(String orderId, String templateId) {
		logger.info("getNextSeq() called");
		try {
			return this.ordEmailReqDAO.getNextSeq(orderId, templateId);
		} catch (Exception e) {
			logger.error("Exception caught in getNextSeq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	public OrdEmailReqDTO getOrdEmailReqDTOByOrderIdAndSeqNo(String orderId, int seqNo, String templateId) {
		logger.info("getOrdEmailReqDTOByOrderIdAndSeqNo() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOByOrderIdAndSeqNo()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrdEmailReq(OrdEmailReqDTO dto) {
		logger.info("insertOrdEmailReq() called");
		try {
			return this.ordEmailReqWriteDAO.insertOrdEmailReq(dto);
		} catch (Exception e) {
			logger.error("Exception caught in insertOrdEmailReq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateOrdEmailReq(OrdEmailReqDTO dto) {
		logger.info("updateOrdEmailReq() called");
		try {
			return this.ordEmailReqWriteDAO.updateOrdEmailReq(dto);
		} catch (Exception e) {
			logger.error("Exception caught in updateOrdEmailReq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}

	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOnlyOrderId(String orderId) {
		logger.info("getOrdEmailReqDTOALLByOrderId() called");
		try {
			return this.ordEmailReqDAO.getOrdEmailReqDTOALLByOnlyOrderId(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLByOrderId()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
}
