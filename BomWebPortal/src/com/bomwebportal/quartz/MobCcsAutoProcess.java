package com.bomwebportal.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;
import com.bomwebportal.thread.CcsCreateBomOrder;
import com.bomwebportal.thread.CreateBomOrder;
import com.bomwebportal.util.BomWebPortalConstant;

public class MobCcsAutoProcess {

	protected final Log logger = LogFactory.getLog(getClass());

	private CcsCreateBomOrder ccsCreateBomOrder;
	public CcsCreateBomOrder getCcsCreateBomOrder() {
		return ccsCreateBomOrder;
	}
	public void setCcsCreateBomOrder(CcsCreateBomOrder ccsCreateBomOrder) {
		this.ccsCreateBomOrder = ccsCreateBomOrder;
	}
	
	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}
	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}
	
	private MobCcsOrderDAO mobCcsOrderDao;
	public MobCcsOrderDAO getMobCcsOrderDao() {
		return mobCcsOrderDao;
	}
	public void setMobCcsOrderDao(MobCcsOrderDAO mobCcsOrderDao) {
		this.mobCcsOrderDao = mobCcsOrderDao;
	}

	public void mobCcsAutoUpdateBomweb_orderStatus() throws DAOException {

		logger.info("mobCcsAutoUpdateBomweb_orderStatus() is executed.");
		String appEnv = "";

		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(
					BomWebPortalConstant.APP_ENV);
			
			logger.info("Environment: " + appEnv);
			
			if (!"dev".equalsIgnoreCase(appEnv)) {
				logger.info("Get signed-off order ID from BOM");
				String orderId = mobCcsOrderDao.getAutoProcessOrderId();

				logger.info("Signed-off order ID: " + orderId);
				if (orderId != null) {
				
					logger.info("Set status of order ID [" + orderId + "] to PROCESS");
					mobCcsOrderDao.updateCcsOrderStatus(orderId, BomWebPortalConstant.BWP_MOBCCS_ORDER_PENDING, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_PROCESS, "","","","AutoProcess");
					mobCcsOrderDao.updateCcsOrderCreateStatus(orderId,BomWebPortalConstant.BWP_MOBCCS_ORDER_PENDING,BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_PROCESS,"");
					//mobCcsOrderDao.updateBomWebOrderStatus(orderId, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_PROCESS);
					//mobCcsOrderDao.updateBomWebOrderStatusToProcess(orderId, BomWebPortalConstant.BWP_ORDER_PROCESS);

					// ***** Call Centre Orders *****
					logger.info("Create CCS BOM order for order ID [" + orderId + "]");
					ccsCreateBomOrder.processCcsRequest(orderId);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		// System.out.println("autoUpdateBomweb_orderStatus() is End");
		logger.info("mobCcsAutoUpdateBomweb_orderStatus() is End");
	}
}