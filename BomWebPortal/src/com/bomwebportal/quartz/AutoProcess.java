package com.bomwebportal.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.thread.CcsCreateBomOrder;
import com.bomwebportal.thread.CreateBomOrder;
import com.bomwebportal.util.BomWebPortalConstant;

public class AutoProcess extends AutoProcessBase{

	protected final Log logger = LogFactory.getLog(getClass());

	private OrderDAO orderDao;

	public OrderDAO getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	private CreateBomOrder createBomOrder;

	public CreateBomOrder getCreateBomOrder() {
		return createBomOrder;
	}

	public void setCreateBomOrder(CreateBomOrder createBomOrder) {
		this.createBomOrder = createBomOrder;
	}

	private CcsCreateBomOrder ccsCreateBomOrder;
	
	public CcsCreateBomOrder getCcsCreateBomOrder() {
		return ccsCreateBomOrder;
	}

	public void setCcsCreateBomOrder(CcsCreateBomOrder ccsCreateBomOrder) {
		this.ccsCreateBomOrder = ccsCreateBomOrder;
	}

	@Override
	protected void trigger() throws DAOException{
		try {

			logger.info("Get signed-off order ID from BOM");
			String orderId = orderDao.getAutoProcessOrderId();

			logger.info("Signed-off order ID: " + orderId);
			if (orderId != null) {
			
				/* ********************************************* */
				// Modify by Herbert 20120127
				// Separate the Retails & Call Centre Orders
				/* ********************************************* */
				logger.info("Set status of order ID [" + orderId + "] to PROCESS");
				//orderDao.updateBomWebOrderStatus(orderId, BomWebPortalConstant.BWP_ORDER_PROCESS);
				orderDao.updateBomWebOrderStatusToProcess(orderId, BomWebPortalConstant.BWP_ORDER_PROCESS);

				
				/*if (orderId.charAt(0) == 'C' && orderId.charAt(4) == 'M') {
					// ***** Call Centre Orders *****
					logger.info("Create CCS BOM order for order ID [" + orderId + "]");
					ccsCreateBomOrder.processCcsRequest(orderId);
				} else {*/
					// ***** Retails Orders *****
					logger.info("Create Retails BOM order for order ID [" + orderId + "]");
					createBomOrder.processRequest(orderId);
				//}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		
	}
}