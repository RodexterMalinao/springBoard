package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.mob.ccs.dto.ContactDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.sbs.dto.DeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.CcsDeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.CcsDeliveryTimeSlotDTO;

public interface DeliveryService {
	public int insertBomwebDelivery(DeliveryUI dto);
	
	public DeliveryUI getMobCcsDelivery(String orderID);
	
	public List<ContactDTO> findContactDTOList(String orderId);
	
	List<String[]> findTimeSlot(String districtCd, String slotType, Date appDate);
	
	String[] getTimeSlotDesc(String timeSlot);
	
	List<String[]> findUrgentTimeSlot();
	
	String getDateType(String date);
	
	List<String> getAvailableUrgentTimeSlot(String availableTimeSlot);
	
	Date[] getDeliveryDateRange(String payMethod, String itemCode);
	
	//Date[] normalDeliveryDateRange(String orderId, String payMethod, String itemCode, Date appDate);
	DeliveryDateRangeDTO normalDeliveryDateRange(String orderId, String payMethod, String itemCode, Date appDate);
	
	void updateOrderDelivery(DeliveryUI deliveryUI);
	
	void updateContactDto(ContactDTO dto);
	
	void updateBomWebCustomerAddress(DeliveryUI dto);
	/**
	 * Calling store procedure to determine whether input date is working day
	 * @param inDate
	 * @return Y or N. Y is working and N is otherwise
	 */
	String isWorkingDay(Date inDate);
	/**
	 * Calling store procedure to return a Date which is determined by the param nDay.
	 * Sunday is considered as Working Day 
	 * and started from the jobDate
	 * @param jobDate Input Date
	 * @param nDay Number of day range from jobDate
	 * @return
	 */
	Date getNextNDeliveryDay(Date jobDate, int nDay);
	/**
	 * Calling store procedure to return a Date which is determined by the param nDay.
	 * Sunday is NOT considered as Working Day 
	 * and started from the jobDate
	 * @param jobDate Input Date
	 * @param nDay Number of day range from jobDate
	 * @return
	 */
	Date getNextNWorkingDay(Date jobDate, int nDay);
	List<String> getSalesMemoNo(String osOrderId);//Athena 20131111 online sales report
	
	public CcsDeliveryDateRangeDTO getCcsDeliveryDateRange(String orderType, String delMode, String delInd, String hsInd, String hsItemCd, String payMthd, String fsInd, String mnpInd, String orderId, java.util.Date appDate);
	
	public List<CcsDeliveryTimeSlotDTO> getCcsDeliveryTimeslot(String delMode, String delInd, java.util.Date formattedDelDate, String distNo, java.util.Date minDate, String minTimeslot);

}
