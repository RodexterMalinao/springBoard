package com.bomwebportal.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.OrdEmailReqDAO;
import com.bomwebportal.dao.OrdEmailReqWriteDAO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.service.OrdEmailReqService;

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
	public List<OrdEmailReqDTO> getCcsOrdEmailReqDTOALLByOrderId(String orderId, String templateId,String pLang) {
		logger.info("getOrdEmailReqDTOALLByOrderId() called");
		try {
			return this.ordEmailReqDAO.getCCSOrdEmailReqDTOALLByOrderId(orderId, templateId,pLang);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLByOrderId()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOrderIdIMS(String orderId, String templateId) {
		logger.info("getOrdEmailReqDTOALLByOrderId() called");
		List<OrdEmailReqDTO> result = new ArrayList<OrdEmailReqDTO>();
		try {
			result = this.ordEmailReqDAO.getOrdEmailReqDTOALLByOrderIdIMS(orderId, templateId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLByOrderId()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		return this.notificationRecordFilter(result);//celia ims 20141121
	}
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearch(String orderId,
			String shopCd, String lob, Date requestDate, String templateId) {
		return this.getOrdEmailReqDTOALLBySearch(orderId, shopCd, lob, requestDate, templateId,null);
	}
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearch(String orderId,
			String shopCd, String lob, Date requestDate, String templateId,String orderType) {
		logger.info("getOrdEmailReqDTOALLBySearch() called");
		List<OrdEmailReqDTO> result = new ArrayList<OrdEmailReqDTO>();
		try {
			result= this.ordEmailReqDAO.getOrdEmailReqDTOALLBySearch(orderId, shopCd, lob, requestDate, templateId,orderType);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLBySearch()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		return this.notificationRecordFilter(result);//celia ims 20141121
	}
	
	/*public List<OrdEmailReqDTO> getCCSOrdEmailReqDTOALLBySearch(String orderId) {
		logger.info("getOrdEmailReqDTOALLBySearch() called");
		List<OrdEmailReqDTO> result = new ArrayList<OrdEmailReqDTO>();
		try {
			result= this.ordEmailReqDAO.getCCSOrdEmailReqDTOALLBySearch(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLBySearch()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		return this.notificationRecordFilter(result);//celia ims 20141121
	}
	*/

	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOLTSIMSBySearch(String orderId,
			int ChannelId, Date requestDate, String templateId, String team) {
		logger.info("getOrdEmailReqDTOALLBySearch() called");
		logger.info("orderId:" + orderId);
		logger.info("ChannelId:" + ChannelId);
		logger.info("requestDate:" + requestDate);
		logger.info("templateId:" + templateId);
		List<OrdEmailReqDTO> result = new ArrayList<OrdEmailReqDTO>();
		
		try {
			result= this.ordEmailReqDAO.getOrdEmailReqDTOIMSLTSBySearch(orderId, ChannelId, requestDate, templateId, team);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLBySearch()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		return this.notificationRecordFilter(result);//celia ims 20141121
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
	
	public int getNextSeqIMS(String orderId) {
		logger.info("getNextSeq() called");
		try {
			return this.ordEmailReqDAO.getNextSeqIMS(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in getNextSeq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	public int isCareCashFormSent(String orderId) {
		logger.info("isCareCashFormSent() called");
		try {
			return this.ordEmailReqDAO.isCareCashFormSent(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in isCareCashFormSent()", e);
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
	
	public OrdEmailReqDTO getCareCashOrdEmailReqDTOByOrderIdAndSeqNo(String orderId, int seqNo, String templateId,String pLang) {
		logger.info("getOrdEmailReqDTOByOrderIdAndSeqNo() called");
		try {
			return this.ordEmailReqDAO.getCareCashOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId,pLang);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOByOrderIdAndSeqNo()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrdSMSReq(OrdEmailReqDTO dto) {
		logger.info("insertOrdSMSReq() called");
		try {
			return this.ordEmailReqWriteDAO.insertOrdSMSReq(dto);
		} catch (Exception e) {
			logger.error("Exception caught in insertOrdSMSReq()", e);
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

	public int updateOrdSMSReq(OrdEmailReqDTO dto) {
		logger.info("updateOrdSMSReq() called");
		try {
			return this.ordEmailReqWriteDAO.updateOrdSMSReq(dto);
		} catch (Exception e) {
			logger.error("Exception caught in updateOrdSMSReq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOnlyOrderId(String orderId) {
		logger.info("getOrdEmailReqDTOALLByOrderId() called");
		List<OrdEmailReqDTO> result = new ArrayList<OrdEmailReqDTO>();
		try {
			 result = this.ordEmailReqDAO.getOrdEmailReqDTOALLByOnlyOrderId(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLByOrderId()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		return this.notificationRecordFilter(result);//celia ims 20141121
	}
	
	public List<String> getTeamCdsByRole(BomSalesUserDTO salesDto) {
		logger.info("getTeamCdsByRole() called");
		try {
			return this.ordEmailReqDAO.getTeamCdsByRole(salesDto);
		} 	catch (DAOException de)
		{
			logger.info("Exception caught in getTeamCdsByRole()");
			de.printStackTrace();

		}
		return null;
	}
	public List<String> getTeamCdsByRoleandChannelCd(BomSalesUserDTO salesDto) {
		logger.info("getTeamCdsByRole() called");
		try {
			return this.ordEmailReqDAO.getTeamCdsByRoleandChannelCd(salesDto);
		} 	catch (DAOException de)
		{
			logger.info("Exception caught in getTeamCdsByRole()");
			de.printStackTrace();

		}
		return null;
	}
	//celia ims 20141121
	public int getNextAmendNoteSeqNoIMS(String orderId){
		logger.info("getNextSeq() called");
		try {
			return this.ordEmailReqDAO.getNextAmendNoteSeqNoIMS(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in getNextSeq()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	private List<OrdEmailReqDTO> notificationRecordFilter(List<OrdEmailReqDTO> list){
		List<OrdEmailReqDTO> result = new ArrayList<OrdEmailReqDTO>();
		for (OrdEmailReqDTO dto:list){
			if (!dto.getTemplateId().contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){
				result.add(dto);
			}
		}
		return result;
	}
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearchIMS(String orderId,
			Date requestDate,BomSalesUserDTO salesDto){
		return this.getOrdEmailReqDTOALLBySearchIMS(orderId, requestDate,salesDto,null,null);
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearchIMS(String orderId,
			Date requestDate,BomSalesUserDTO salesDto,String teamCd, String orderType) {
		logger.info("getOrdEmailReqDTOALLBySearchIMS() called");
		List<OrdEmailReqDTO> result = new ArrayList<OrdEmailReqDTO>();
		try {
			result = this.ordEmailReqDAO.getOrdEmailReqDTOALLBySearchIMS(orderId, requestDate,salesDto,teamCd,orderType);
		} catch (Exception e) {
			logger.error("Exception caught in getOrdEmailReqDTOALLBySearchIMS()", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		if (salesDto!=null)
			return this.notificationRecordFilter(result);//celia ims 20141121
		else
			return result;
	}
	
	public String[] getLatestEmailSMSPair(String orderId) {
		try {
			return this.ordEmailReqDAO.getLatestEmailSMSPair(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BasketDTO> getOnlinePlanDetails(String orderId, String type, String locale, String vas_category_id) {
		try {
			return this.ordEmailReqDAO.getOnlinePlanDetails(orderId, type, locale, vas_category_id);
		} catch (Exception e) {
			logger.error("Exception caught in getOnlinePlanDetails()", e);
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BasketDTO> getOnlineHSDetails(String orderId, String locale) {
		try {
			return this.ordEmailReqDAO.getOnlineHSDetails(orderId, locale);
		} catch (Exception e) {
			logger.error("Exception caught in getOnlineHSDetails()", e);
			e.printStackTrace();
			return null;
		}
	}
}
