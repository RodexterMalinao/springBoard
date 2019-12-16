package com.bomwebportal.service;

import com.bomwebportal.dto.OrderDTO;


public interface ReleaseLockService {
	enum LockResult {
		LOCK_FREE("No one lock this order.")
		, LOCKED_BY_YOURSELF("Order is Locked by yourself.") 
		, LOCKED_BY_OTHER_USER("Order is Locked by another user.") 
		;
		
		LockResult(String message) {
			this.message = message;
		}
		public String getMessage() {
			return message;
		}
		private String message;
	}
	
	/**
	 * Retrieve OrderDTO by its orderID
	 * @param orderId Order ID
	 * @return OrderDTO object
	 */
	public OrderDTO getOrderLockInfo(String orderId);
	/**
	 * Release or lock specific order
	 * @param orderId Order ID
	 * @param lockInd Locking indicator. "Y" to lock it else to release it
	 * @param lastUpdateBy User ID
	 * @return the numbers of rows affected by the update
	 */
	public int updateOrderLockInd(String orderId, String lockInd, String lastUpdateBy);
	
	/**
	 * Release or lock based on PL/SQL Order Recall Check procedure
	 * @param orderId Order Id
	 * @param staffId User ID
	 * @return
	 */
	public String[] checkAuthority(String orderId, String staffId);
	public LockResult getOrderLockInfo(String orderId, String staffId);
}
