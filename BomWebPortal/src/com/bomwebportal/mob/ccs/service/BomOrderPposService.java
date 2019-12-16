package com.bomwebportal.mob.ccs.service;

import java.util.List;
import java.util.Map;

import com.bomwebportal.mob.ccs.dto.BomOrderPposDTO;

public interface BomOrderPposService {
	/**
	 * Extract records from PPOS which has modified today
	 * @param ccsMode
	 * @return
	 */
	List<BomOrderPposDTO> getBomOrderPpos(String ccsMode);
	/**
	 * update data in bomweb_sb_bom_order_info table
	 * @param dto
	 */
	void updateBomOrderInfo(BomOrderPposDTO dto);
	/**
	 * update data in bomweb_sb_bom_order table
	 * @param dto
	 */
	void updateBomOrder(BomOrderPposDTO dto);
	/**
	 * insert data into bomweb_sb_bom_order_info table
	 * @param dto
	 */
	void insertBomOrderInfo(BomOrderPposDTO dto);
	/**
	 * insert data into bomweb_sb_bom_order
	 * @param dto
	 */
	void insertBomOrder(BomOrderPposDTO dto);
	/**
	 * check whether data with specific ocid exists in bomweb_sb_bom_order_info table
	 * @param ocid
	 * @return
	 */
	boolean isBomOrderPposInfoExist(String ocid);
	/**
	 * check whether data with specific ocid exists in bomweb_sb_bom_order table
	 * @param ocid
	 * @return
	 */
	boolean isBomOrderPposExist(String orderId);
	/**
	 * Extract records from BOM which has modified today with 60 days of time frame
	 * @param ccsMode
	 * @return
	 */
	List<BomOrderPposDTO> getUpdatedBomOrder(String ccsMode);
	/**
	 * get code shop_code and sm_num according to different environment
	 * @param mode
	 * @return
	 */
	String getEnvironmentCcsMode(String mode);
	/**
	 * get a list of BomOrderPposDTO which orderId is set as the key
	 * @param orderIdList
	 * @return
	 */
	Map<String, BomOrderPposDTO> getBomOrderPposMap(List<String> orderIdList);
	/**
	 * get canceling order status from MOB which order status on BOM is cancelled
	 * @param mobOrderSts
	 * @param bomOrderSts
	 * @return
	 */
	List<String> getCrossBomAndMobOrderStatus(String mobOrderSts, String bomOrderSts);
	
	/**
	 * add by wilson 20120910
	 * @return
	 */
	List<BomOrderPposDTO> getRetailShopBomOrderStatus(String todayString);
}
