package com.bomwebportal.mob.ccs.service;

import java.sql.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.DeliveryDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsBulkPrintDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsUtilDAO;
import com.bomwebportal.mob.ccs.dto.ContactDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.sbs.dto.DeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.CcsDeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.CcsDeliveryTimeSlotDTO;

@Transactional(readOnly = true)
public class DeliveryServiceImpl implements DeliveryService {

	protected final Log logger = LogFactory.getLog(getClass());

	private DeliveryDAO deliveryDao;

	private MobCcsUtilDAO mobCcsUtilDAO;
	
	private MobCcsBulkPrintDAO mobCcsBulkPrintDAO;
	
	/**
	 * @return the mobCcsUtilDAO
	 */
	public MobCcsUtilDAO getMobCcsUtilDAO() {
		return mobCcsUtilDAO;
	}

	/**
	 * @param mobCcsUtilDAO the mobCcsUtilDAO to set
	 */
	public void setMobCcsUtilDAO(MobCcsUtilDAO mobCcsUtilDAO) {
		this.mobCcsUtilDAO = mobCcsUtilDAO;
	}

	/**
	 * @return the mobCcsBulkPrintDAO
	 */
	public MobCcsBulkPrintDAO getMobCcsBulkPrintDAO() {
		return mobCcsBulkPrintDAO;
	}

	/**
	 * @param mobCcsBulkPrintDAO the mobCcsBulkPrintDAO to set
	 */
	public void setMobCcsBulkPrintDAO(MobCcsBulkPrintDAO mobCcsBulkPrintDAO) {
		this.mobCcsBulkPrintDAO = mobCcsBulkPrintDAO;
	}

	public void setDeliveryDao(DeliveryDAO deliveryDao) {
		this.deliveryDao = deliveryDao;
	}

	public DeliveryDAO getDeliveryDao() {
		return deliveryDao;
	}

	// @Transactional(readOnly=false, propagation=Propagation.REQUIRED,
	// rollbackFor=AppRuntimeException.class)
	/*
	 * public MrtInventoryDTO getMrtInventoryDTO(String rowId){ try {
	 * logger.info("getMrtInventoryDTO() is called in MrtInventoryServiceImpl");
	 * return mrtInventoryDao.getMrtInventoryDTO(rowId);
	 * 
	 * } catch (DAOException de) {
	 * logger.error("Exception caught in getMrtInventoryDTO()", de); throw new
	 * AppRuntimeException(de); } }
	 * 
	 * @Transactional(readOnly=false, propagation=Propagation.REQUIRED,
	 * rollbackFor=AppRuntimeException.class) public MrtInventoryDTO
	 * getMrtInventoryDTOByOrderId(String orderId){ try { logger.info(
	 * "getMrtInventoryDTOByOrderId() is called in MrtInventoryServiceImpl");
	 * return mrtInventoryDao.getMrtInventoryDTOByOrderId(orderId);
	 * 
	 * } catch (DAOException de) {
	 * logger.error("Exception caught in getMrtInventoryDTOByOrderId()", de);
	 * throw new AppRuntimeException(de); } }
	 * 
	 * @Transactional(readOnly=false, propagation=Propagation.REQUIRED,
	 * rollbackFor=AppRuntimeException.class) public List<MrtInventoryDTO>
	 * getMrtInventoryDTOALL(String msisdn){ try {
	 * logger.info("getMrtInventoryDTOALL() is called in MrtInventoryServiceImpl"
	 * ); return mrtInventoryDao.getMrtInventoryDTOALL(msisdn);
	 * 
	 * } catch (DAOException de) {
	 * logger.error("Exception caught in getMrtInventoryDTOALL()", de); throw
	 * new AppRuntimeException(de); } }
	 * 
	 * @Transactional(readOnly=false, propagation=Propagation.REQUIRED,
	 * rollbackFor=AppRuntimeException.class) public List<MrtInventoryDTO>
	 * getMrtInventoryDTOBySearch(MrtInventoryDTO dto) { try { logger.info(
	 * "getMrtInventoryDTOBySearch() is called in MrtInventoryServiceImpl");
	 * return mrtInventoryDao.getMrtInventoryDTOBySearch(dto);
	 * 
	 * } catch (DAOException de) {
	 * logger.error("Exception caught in getMrtInventoryDTOBySearch()", de);
	 * throw new AppRuntimeException(de); } }
	 */

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertBomwebDelivery(DeliveryUI dto) {
		try {
			logger.info("insertBomwebDelivery() is called in DeliveryServiceImpl");
			deliveryDao.insertBomWebCustomerAddress(dto);
			
			deliveryDao.insertBomWebContact(dto.getPrimaryContact());
			if(dto.isRecieveByThirdPartyInd()){
				deliveryDao.insertBomWebContact(dto.getThirdPartyContact());
			}
			return deliveryDao.insertBomwebDelivery(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in insertBomwebDelivery()", de);
			throw new AppRuntimeException(de);
		}
	}

	public DeliveryUI getMobCcsDelivery(String orderID) {

		DeliveryUI deliveryUI = null;

		try {
			logger.info("getMobCcsDelivery(String orderID) is called");
			deliveryUI = deliveryDao.getMobCcsDelivery(orderID);
			if (deliveryUI != null) {
				List<ContactDTO> contactDTOList = findContactDTOList(orderID);
				if (contactDTOList != null && !contactDTOList.isEmpty()) {
					for (ContactDTO dto : contactDTOList) {
						if (dto.getContactType() != null) {
							if (dto.getContactType().equalsIgnoreCase("3C")) {
								deliveryUI.setThirdPartyContact(dto);
							} else if (dto.getContactType().equalsIgnoreCase("DC")) {
								deliveryUI.setPrimaryContact(dto);
							}
						}
					}
				}
			}
		} catch (DAOException e) {
			logger.error("Exception caught in getMobCcsDelivery(String orderID)", e);
		}
		return deliveryUI;
	}

	public List<ContactDTO> findContactDTOList(String orderId) {
		try {
			return deliveryDao.findContactDTOList(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in findContactDTOList(String orderId)", e);
		}
		return null;
	}

	public List<String[]> findTimeSlot(String districtCd, String slotType, java.util.Date appDate) {
		try {
			return deliveryDao.findTimeSlot(districtCd, slotType, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in findTimeSlot(String disctrictCd)", e);
		}
		return null;
	}

	public String[] getTimeSlotDesc(String timeSlot) {
		
		try {
			return deliveryDao.getTimeSlotDesc(timeSlot);
		} catch (DAOException e) {
			logger.error("Exception caught in getTimeSlotDesc(String timeSlot)", e);
		}
		
		
		return null;
	}

	public List<String[]> findUrgentTimeSlot() {
		
		try {
			return deliveryDao.findUrgentTimeSlot();
		} catch (DAOException e) {
			logger.error("Exception caught in findUrgentTimeSlot()", e);
		}
		
		return null;
	}

	public String getDateType(String date) {
		
		try {
			return deliveryDao.getDateType(date);
		} catch (DAOException e) {
			logger.error("Exception caught in getDateType(String date)", e);
		}
		
		return null;
	}

	public List<String> getAvailableUrgentTimeSlot(String availableTimeSlot) {
		
		try {
			return deliveryDao.getAvailableUrgentTimeSlot(availableTimeSlot);
		} catch (DAOException e) {
			logger.error("Exception caught in getAvailableUrgentTimeSlot()", e);
		}
		
		return null;
	}

	public Date[] getDeliveryDateRange(String payMethod, String itemCode) {
		
		try {
			return deliveryDao.getDeliveryDateRange(payMethod, itemCode);
		} catch (DAOException e) {
			logger.error("Exception caught in getDeliveryDateRange()", e);
		}
		
		return null;
	}

	/*public Date[] normalDeliveryDateRange(String orderId, String payMethod, String itemCode, java.util.Date appDate) {
		
		try {
			return deliveryDao.normalDeliveryDateRange(orderId, payMethod, itemCode, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in normalDeliveryDateRange()", e);
		}
		
		return null;
	}*/
	public DeliveryDateRangeDTO normalDeliveryDateRange(String orderId, String payMethod, String itemCode, java.util.Date appDate) {
		
		try {
			return deliveryDao.normalDeliveryDateRange(orderId, payMethod, itemCode, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in normalDeliveryDateRange()", e);
		}
		
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateOrderDelivery(DeliveryUI deliveryUI) {
		try{
			deliveryDao.updateOrderDelivery(deliveryUI);
		} catch (DAOException e) {
			logger.error("Exception caught in updateOrderDeliveryDateTime()", e);
		}
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateContactDto(ContactDTO dto) {
		try{
			deliveryDao. updateContactDto(dto);
		} catch (DAOException e) {
			logger.error("Exception caught in updateContactDto()", e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBomWebCustomerAddress(DeliveryUI dto) {
		try{
			deliveryDao. updateBomWebCustomerAddress(dto);
		} catch (DAOException e) {
			logger.error("Exception caught in updateBomWebCustomerAddress()", e);
		}
	}

	public String isWorkingDay(java.util.Date inDate) {
		try{
			return mobCcsUtilDAO.isWorkingDay(inDate);
		} catch (DAOException e) {
			logger.error("Exception caught in isWorkingDay()", e);
		}
		return null;
	}

	public java.util.Date getNextNDeliveryDay(java.util.Date jobDate, int nDay) {
		try{
			return mobCcsUtilDAO.getNextNDeliveryDay(jobDate, nDay);
		} catch (DAOException e) {
			logger.error("Exception caught in getNextNDeliveryDay()", e);
		}
		return null;
	}

	public java.util.Date getNextNWorkingDay(java.util.Date jobDate, int nDay) {
		try{
			return mobCcsBulkPrintDAO.getNextNWorkingDay(jobDate, nDay);
		} catch (DAOException e) {
			logger.error("Exception caught in getNextNWorkingDay()", e);
		}
		return null;
	}

	/*
	 * @Transactional(readOnly=false, propagation=Propagation.REQUIRED,
	 * rollbackFor=AppRuntimeException.class) public int
	 * updateMrtInventory(MrtInventoryDTO dto){ try {
	 * logger.info("updateMrtInventory() is called in MrtInventoryServiceImpl");
	 * return mrtInventoryDao.updateMrtInventory(dto);
	 * 
	 * } catch (DAOException de) {
	 * logger.error("Exception caught in updateMrtInventory()", de); throw new
	 * AppRuntimeException(de); } }
	 * 
	 * @Transactional(readOnly=false, propagation=Propagation.REQUIRED,
	 * rollbackFor=AppRuntimeException.class) public int
	 * deleteMrtInventory(MrtInventoryDTO dto){ try {
	 * logger.info("deleteMrtInventory() is called in MrtInventoryServiceImpl");
	 * return mrtInventoryDao.deleteMrtInventory(dto);
	 * 
	 * } catch (DAOException de) {
	 * logger.error("Exception caught in deleteMrtInventory()", de); throw new
	 * AppRuntimeException(de); } }
	 */
	public List<String> getSalesMemoNo(String osOrderId) {
		//Athena 20131111 online sales report
		try{
			return deliveryDao.getSalesMemoNo(osOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in getSalesMemoNo()", e);
		}
		return null;
	}
	
	public CcsDeliveryDateRangeDTO getCcsDeliveryDateRange(String orderType, String delMode, String delInd, String hsInd, String hsItemCd, String payMthd, String fsInd, String mnpInd, String orderId, java.util.Date appDate) {
		try {
			return deliveryDao.getCcsDeliveryDateRange(orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in getCcsDeliveryDateRange()", e);
		}
		return null;
	}
	
	public List<CcsDeliveryTimeSlotDTO> getCcsDeliveryTimeslot(String delMode, String delInd, java.util.Date formattedDelDate, String distNo, java.util.Date minDate, String minTimeslot) {
		try {
			return deliveryDao.getCcsDeliveryTimeslot(delMode, delInd, formattedDelDate, distNo, minDate, minTimeslot);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
}
