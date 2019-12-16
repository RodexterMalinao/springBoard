package com.bomwebportal.mob.ccs.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsDoaItemDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsDoaRequestDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsDoaRequestSelectedCdDAO;
import com.bomwebportal.mob.ccs.dto.DoaItemDTO;
import com.bomwebportal.mob.ccs.dto.DoaRequestDTO;
import com.bomwebportal.mob.ccs.dto.DoaRequestSelectedCdDTO;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;
import com.bomwebportal.service.OrderService;

@Transactional(readOnly=true)
public class MobCcsDoaRequestServiceImpl implements MobCcsDoaRequestService {
	private static final String ORDER_STATUS_COMPLETED = "02";
	private static final String ORDER_STATUS_FALLOUT = "99";
	
	private static final String CHECK_POINT_FALLOUT = "999";
	
	private static final String REASON_CD_DOA_SAVED = "N000";
	private static final String REASON_CD_DOA_AFTER_DELIVERY = "N001";
	//private static final String REASON_CD_DOA_COMPLETED = "02";
	private static final String REASON_CD_DOA_REJECTED = "03";

	private static final String DOA_TYPE_ONLINE = "DOA";
	
	protected final Log logger = LogFactory.getLog(getClass());

	private OrderService orderService;
	private MobCcsOrderRemarkService mobCcsOrderRemarkService;
	
	private MobCcsDoaRequestDAO mobCcsDoaRequestDAO;
	private MobCcsDoaRequestSelectedCdDAO mobCcsDoaRequestSelectedCdDAO;
	private MobCcsDoaItemDAO mobCcsDoaItemDAO;
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MobCcsOrderRemarkService getMobCcsOrderRemarkService() {
		return mobCcsOrderRemarkService;
	}

	public void setMobCcsOrderRemarkService(
			MobCcsOrderRemarkService mobCcsOrderRemarkService) {
		this.mobCcsOrderRemarkService = mobCcsOrderRemarkService;
	}

	public MobCcsDoaRequestDAO getMobCcsDoaRequestDAO() {
		return mobCcsDoaRequestDAO;
	}

	public void setMobCcsDoaRequestDAO(MobCcsDoaRequestDAO mobCcsDoaRequestDAO) {
		this.mobCcsDoaRequestDAO = mobCcsDoaRequestDAO;
	}

	public MobCcsDoaRequestSelectedCdDAO getMobCcsDoaRequestSelectedCdDAO() {
		return mobCcsDoaRequestSelectedCdDAO;
	}

	public void setMobCcsDoaRequestSelectedCdDAO(
			MobCcsDoaRequestSelectedCdDAO mobCcsDoaRequestSelectedCdDAO) {
		this.mobCcsDoaRequestSelectedCdDAO = mobCcsDoaRequestSelectedCdDAO;
	}

	public MobCcsDoaItemDAO getMobCcsDoaItemDAO() {
		return mobCcsDoaItemDAO;
	}

	public void setMobCcsDoaItemDAO(MobCcsDoaItemDAO mobCcsDoaItemDAO) {
		this.mobCcsDoaItemDAO = mobCcsDoaItemDAO;
	}

	public DoaRequestDTO getInitDoaRequestDTO(String orderId) {
		try {
			logger.info("getInitDoaRequestDTO() is called in MobCcsDoaRequestServiceImpl");
			return mobCcsDoaRequestDAO.getInitDoaRequestDTO(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getInitDoaRequestDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<DoaRequestDTO> getDoaRequestDTOALL(String orderId) {
		try {
			logger.info("getDoaRequestDTOALLByOrderId() is called in MobCcsDoaRequestServiceImpl");
			return mobCcsDoaRequestDAO.getDoaRequestDTOALLByOrderId(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getDoaRequestDTOALLByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}

	public DoaRequestDTO getDoaRequestDTO(String rowId) {
		try {
			logger.info("getDoaRequestDTO() is called in MobCcsDoaRequestServiceImpl");
			return mobCcsDoaRequestDAO.getDoaRequestDTO(rowId);
		} catch (DAOException de) {
			logger.error("Exception caught in getDoaRequestDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	public DoaRequestDTO getDoaRequestDTOByOrderIdAndSeqNo(String orderId, int seqNo) {
		try {
			logger.info("getDoaRequestDTOByOrderIdAndSeqNo() is called in MobCcsDoaRequestServiceImpl");
			return mobCcsDoaRequestDAO.getDoaRequestDTOByOrderIdAndSeqNo(orderId, seqNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getDoaRequestDTOByOrderIdAndSeqNo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int getLastSeqNo(String orderId) {
		try {
			logger.info("getLastSeqNo() is called in MobCcsDoaRequestServiceImpl");
			return this.mobCcsDoaRequestDAO.getLastSeqNo(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getLastSeqNo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean isInitNewDoaRequest(OrderDTO orderDto) {
		try {
			if (ORDER_STATUS_COMPLETED.equals(orderDto.getOrderStatus())) {
				return true;
			}
			List<DoaRequestDTO> doaRequests = getDoaRequestDTOALL(orderDto.getOrderId());
			if (this.isEmpty(doaRequests)) {
				return true;
			}
			
			List<DoaRequestDTO> list = this.mobCcsDoaRequestDAO.getDoaRequestDTOALLByOrderIdAndDoaType(orderDto.getOrderId(), DOA_TYPE_ONLINE);
			if (this.isEmpty(list)) {
				return true;
			}
			DoaRequestDTO dto = list.get(list.size() - 1);
			return REASON_CD_DOA_REJECTED.equals(dto.getStatus());
		} catch (DAOException de) {
			logger.error("Exception caught in isInitNewDoaRequest()", de);
			throw new AppRuntimeException(de);
		}
	}

	public boolean isUpdateDoaRequest(OrderDTO orderDto) {
		if (this.isInitNewDoaRequest(orderDto)) {
			return false;
		}
		if (ORDER_STATUS_FALLOUT.equals(orderDto.getOrderStatus()) 
				&& CHECK_POINT_FALLOUT.equals(orderDto.getCheckPoint()) 
				&& REASON_CD_DOA_SAVED.equals(orderDto.getReasonCd())) {
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertDoaRequestDTO(DoaRequestDTO dto) {
		try {
			logger.info("insertDoaRequestDTO() is called in MobCcsDoaRequestServiceImpl");
			return this.mobCcsDoaRequestDAO.insertDoaRequestDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertDoaRequestDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateDoaRequestDTO(DoaRequestDTO dto) {
		try {
			logger.info("updateDoaRequestDTO() is called in MobCcsDoaRequestServiceImpl");
			return this.mobCcsDoaRequestDAO.updateDoaRequestDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDoaRequestDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateDoaRequestStatus(String rowId, String status) {
		try {
			logger.info("updateDoaRequestStatus() is called in MobCcsDoaRequestServiceImpl");
			return this.mobCcsDoaRequestDAO.updateDoaRequestStatus(rowId, status);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDoaRequestStatus()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<DoaRequestSelectedCdDTO> getDoaRequestSelectedCdDTOALL(String orderId, int seqNo) {
		try {
			logger.info("getDoaRequestSelectedCdDTOALL() is called in MobCcsDoaRequestServiceImpl");
			return this.mobCcsDoaRequestSelectedCdDAO.getDoaRequestSelectedCdDTOALL(orderId, seqNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getDoaRequestSelectedCdDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertDoaRequestSelectedCdDTOList(List<DoaRequestSelectedCdDTO> list) {
		try {
			logger.info("insertDoaRequestSelectedCdDTO() is called in MobCcsDoaRequestServiceImpl");
			for (DoaRequestSelectedCdDTO dto : list) {
				this.mobCcsDoaRequestSelectedCdDAO.insertDoaRequestSelectedCdDTO(dto);
			}
		} catch (DAOException de) {
			logger.error("Exception caught in insertDoaRequestSelectedCdDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int deleteDoaRequestSelectedCdBySeqNo(String orderId, int seqNo) {
		try {
			logger.info("deleteDoaRequestSelectedCdBySeqNo() is called in MobCcsDoaRequestServiceImpl");
			return this.mobCcsDoaRequestSelectedCdDAO.deleteDoaRequestSelectedCdBySeqNo(orderId, seqNo);
		} catch (DAOException de) {
			logger.error("Exception caught in deleteDoaRequestSelectedCdBySeqNo()", de);
			throw new AppRuntimeException(de);
		}
	}

	public boolean approveByManager(String orderId) {
		/*
		 * UM Approve - delivery date < 14 days
		 * MKT Approve - DOA 2+ times / delivery date > 14 days
		 */
		try {
			logger.info("requireApproveByManager() is called in MobCcsDoaRequestServiceImpl, orderId: " + orderId);

			Date deliveryDate = this.mobCcsDoaRequestDAO.getActualDeliveryDate(orderId);
			if (logger.isDebugEnabled()) {
				logger.debug("actualDeliveryDate: " + deliveryDate);
			}
			final int nDays = 14;
			Calendar lastApproveDate = Calendar.getInstance();
			lastApproveDate.setTime(deliveryDate);
			lastApproveDate.add(Calendar.DAY_OF_MONTH, nDays);
			lastApproveDate.set(Calendar.HOUR_OF_DAY, 0);
			lastApproveDate.set(Calendar.MINUTE, 0);
			lastApproveDate.set(Calendar.SECOND, 0);
			lastApproveDate.set(Calendar.MILLISECOND, 0);
			
			Calendar now = Calendar.getInstance();
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			now.set(Calendar.MILLISECOND, 0);
			
			if (!now.before(lastApproveDate)) {
				// > 14 days
				if (logger.isDebugEnabled()) {
					logger.debug("now: " + now + " before lastApproveDate: " + lastApproveDate);
				}
				return false;
			}
			// < 14 days
			List<DoaRequestDTO> doaRequests = this.getDoaRequestDTOALL(orderId);
			if (this.isEmpty(doaRequests)) {
				if (logger.isDebugEnabled()) {
					logger.debug("doaRequests is empty");
				}
				return true;
			}
			// DOA + status within N001 - N006(now check only not N000 / REASON_CD_DOA_SAVED)
			int doaRequestCount = 0;
			List<DoaRequestDTO> list = this.mobCcsDoaRequestDAO.getDoaRequestDTOALLByOrderIdAndDoaType(orderId, DOA_TYPE_ONLINE);
			if (!this.isEmpty(list)) {
				for (DoaRequestDTO dto : list) {
					if (StringUtils.isNotBlank(dto.getStatus())) {
						if (dto.getStatus().startsWith("N") && !REASON_CD_DOA_SAVED.equals(dto.getStatus())) {
							doaRequestCount++;
						}
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("doaRequestCount: " + doaRequestCount);
			}
			return doaRequestCount == 0;
		} catch (DAOException de) {
			logger.error("Exception caught in requireApproveByManager()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<DoaItemDTO> getDoaItemDTOList(String orderId, int seqNo) {
		try {
			logger.info("getDoaItemDTOList() is called in MobCcsDoaRequestServiceImpl");
			return this.mobCcsDoaItemDAO.getDoaItemDTOALL(orderId, seqNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getDoaItemDTOList()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertDoaItemDTOList(List<DoaItemDTO> list) {
		try {
			logger.info("insertDoaItemDTOList() is called in MobCcsDoaRequestServiceImpl");
			for (DoaItemDTO dto : list) {
				this.mobCcsDoaItemDAO.insertDoaItemDTO(dto);
			}
		} catch (DAOException de) {
			logger.error("Exception caught in insertDoaItemDTOList()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int deleteDoaItemBySeqNo(String orderId, int seqNo) {
		try {
			logger.info("deleteDoaItemBySeqNo() is called in MobCcsDoaRequestServiceImpl");
			return this.mobCcsDoaItemDAO.deleteDoaItemBySeqNo(orderId, seqNo);
		} catch (DAOException de) {
			logger.error("Exception caught in deleteDoaItemBySeqNo()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateDoa(DoaRequestDTO dto
			, List<DoaRequestSelectedCdDTO> selectedCds
			, List<DoaItemDTO> doaItems
			, OrderRemarkDTO orderRemark
			, boolean saveAsDraft, String username) {
		try {
			updateOrderStatus(dto.getOrderId(), saveAsDraft, username);
			updateDoaRequestDTO(dto);
			insertSelectedCds(dto.getOrderId(), dto.getSeqNo(), selectedCds);
			insertDoaItem(dto.getOrderId(), dto.getSeqNo(), doaItems);
			if (!saveAsDraft) {
				autoDoaDeassign(dto.getOrderId());
				this.mobCcsDoaRequestDAO.updateDoaRequestStatusFromOrder(dto.getOrderId(), dto.getSeqNo());
				mobCcsOrderRemarkService.insertOrderRemarkDTO(orderRemark);
			}
		} catch (DAOException de) {
			logger.error("Exception caught in updateDoa()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertDoa(DoaRequestDTO dto
			, List<DoaRequestSelectedCdDTO> selectedCds
			, List<DoaItemDTO> doaItems
			, OrderRemarkDTO orderRemark
			, boolean saveAsDraft, String username) {
		try {
			updateOrderStatus(dto.getOrderId(), saveAsDraft, username);
			insertDoaRequestDTO(dto);
			insertSelectedCds(dto.getOrderId(), dto.getSeqNo(), selectedCds);
			insertDoaItem(dto.getOrderId(), dto.getSeqNo(), doaItems);
			if (!saveAsDraft) {
				autoDoaDeassign(dto.getOrderId());
				this.mobCcsDoaRequestDAO.updateDoaRequestStatusFromOrder(dto.getOrderId(), dto.getSeqNo());
				mobCcsOrderRemarkService.insertOrderRemarkDTO(orderRemark);
			}
		} catch (DAOException de) {
			logger.error("Exception caught in insertDoa()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	private void updateOrderStatus(String orderId, boolean saveAsDraft, String username) {
		this.orderService.updateCcsOrderStatus(orderId, ORDER_STATUS_FALLOUT, CHECK_POINT_FALLOUT, saveAsDraft ? REASON_CD_DOA_SAVED : REASON_CD_DOA_AFTER_DELIVERY, null, null, username);
	}
	
	private void insertSelectedCds(String orderId, int seqNo, List<DoaRequestSelectedCdDTO> selectedCds) {
		deleteDoaRequestSelectedCdBySeqNo(orderId, seqNo);
		insertDoaRequestSelectedCdDTOList(selectedCds);
	}
	
	private void insertDoaItem(String orderId, int seqNo, List<DoaItemDTO> doaItems) {
		deleteDoaItemBySeqNo(orderId, seqNo);
		insertDoaItemDTOList(doaItems);
	}
	
	private void autoDoaDeassign(String orderId) throws DAOException {
		int gnRetVal = -1;
		if (StringUtils.startsWith(orderId, "CSBSM")) {
			gnRetVal = this.mobCcsDoaRequestDAO.autoDoaDeassignSt(orderId);
		} else {
			gnRetVal = this.mobCcsDoaRequestDAO.autoDoaDeassign(orderId);
		}
		if (gnRetVal < 0) {
			logger.error("Error in DOA stock change, orderId: " + orderId);
			throw new AppRuntimeException("Error in DOA stock change");
		}
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	public boolean isMktSerialIdExists(String mktSerialId) {
		if (StringUtils.isBlank(mktSerialId)) {
			return false;
		}
		try {
			return this.mobCcsDoaRequestDAO.isMktSerialIdExists(mktSerialId);
		} catch (DAOException de) {
			logger.error("Exception caught in isMktSerialIdExists()", de);
			throw new AppRuntimeException(de);
		}
	}

	public boolean isMktSerialIdPatternValid(String mktSerialId) {
		if (StringUtils.isBlank(mktSerialId)) {
			return false;
		}
		// check for pattern
		if (!mktSerialId.matches("^DOA[0-9]{8}[0-9]{3}[A-Z]$")) {
			return false;
		}
		// check date value
		String approvalSerialDate = mktSerialId.substring(3, 11);
		try {
			(new SimpleDateFormat("yyyyMMdd", Locale.US)).parse(approvalSerialDate);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public String[] getRetDoaRequestDefectInd(String orderId) {
		try {
			logger.info("getRetDoaRequestDefectInd() is called in MobCcsDoaRequestServiceImpl");
			int seqNo = this.getLastSeqNo(orderId);
			return mobCcsDoaRequestDAO.getRetDoaRequestDefectInd(orderId, seqNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getRetDoaRequestDefectInd()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<String> getDoaRequestSelectedHsDefectAll(String orderId, int seqNo) {
		try {
			logger.info("getDoaRequestSelectedHandsetDefectAll() is called in MobCcsDoaRequestServiceImpl");
			return mobCcsDoaRequestDAO.getDoaRequestDefectALL(orderId, seqNo, "HS_DEFECT");
		} catch (DAOException de) {
			logger.error("Exception caught in getDoaRequestSelectedHandsetDefectAll()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<String> getDoaRequestSelectedAcDefectAll(String orderId, int seqNo) {
		try {
			logger.info("getDoaRequestSelectedAcDefectAll() is called in MobCcsDoaRequestServiceImpl");
			return mobCcsDoaRequestDAO.getDoaRequestDefectALL(orderId, seqNo, "AC_DEFECT");
		} catch (DAOException de) {
			logger.error("Exception caught in getDoaRequestSelectedAcDefectAll()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<String> getDoaRequestAcDefectItems(String orderId, int seqNo) {
		try {
			logger.info("getDoaRequestAcDefectItems() is called in MobCcsDoaRequestServiceImpl");
			return mobCcsDoaRequestDAO.getDoaRequestAcDefectItems(orderId, seqNo, "AC_DEFECT");
		} catch (DAOException de) {
			logger.error("Exception caught in getDoaRequestAcDefectItems()", de);
			throw new AppRuntimeException(de);
		}
	}
}