package com.bomwebportal.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderHistoryDAO;
import com.bomwebportal.thread.CcsCreateBomOrderHistory;
import com.bomwebportal.util.BomWebPortalConstant;

public class MobCcsHistoryAutoProcess extends AutoProcessBase{

	protected final Log logger = LogFactory.getLog(getClass());

	private CcsCreateBomOrderHistory ccsCreateBomOrderHistory;
	public CcsCreateBomOrderHistory getCcsCreateBomOrderHistory() {
		return ccsCreateBomOrderHistory;
	}
	public void setCcsCreateBomOrderHistory(
			CcsCreateBomOrderHistory ccsCreateBomOrderHistory) {
		this.ccsCreateBomOrderHistory = ccsCreateBomOrderHistory;
	}
	
	private MobCcsOrderHistoryDAO mobCcsOrderHistoryDAO;
	public MobCcsOrderHistoryDAO getMobCcsOrderHistoryDAO() {
		return mobCcsOrderHistoryDAO;
	}
	public void setMobCcsOrderHistoryDAO(MobCcsOrderHistoryDAO mobCcsOrderHistoryDAO) {
		this.mobCcsOrderHistoryDAO = mobCcsOrderHistoryDAO;
	}
	
	private MobCcsOrderDAO mobCcsOrderDao;
	public MobCcsOrderDAO getMobCcsOrderDao() {
		return mobCcsOrderDao;
	}
	public void setMobCcsOrderDao(MobCcsOrderDAO mobCcsOrderDao) {
		this.mobCcsOrderDao = mobCcsOrderDao;
	}

	@Override
	protected void trigger() throws DAOException {
		
		logger.info("mobCcsHistoryAutoUpdateBomweb_orderStatus() is executed.");
		logger.info("Get signed-off order ID & History Seq from BOM");
		
		String orderId = mobCcsOrderHistoryDAO.getAutoProcessHistOrderId();
		
		logger.info("Set status of order ID [" + orderId + "] to PROCESS");
		mobCcsOrderDao.updateCcsOrderStatus(orderId, BomWebPortalConstant.BWP_MOBCCS_ORDER_CANCELLING, BomWebPortalConstant.BWP_MOBCCS_HIST_CHECK_POINT_PROCESS, "","","","AutoProcess");
		
		//System.out.println("Hist orderId:" + orderId);
		String seqNo = mobCcsOrderHistoryDAO.getAutoProcessHistOrderSeqNo(orderId);
		
		if (orderId != null && seqNo != null) {
			logger.info("Signed-off order ID: " + orderId + ", seq_no:" + seqNo);

			// ***** Call Centre Orders *****
			logger.info("Create CCS History BOM order for order ID [" + orderId + "]");
			ccsCreateBomOrderHistory.processCcsRequest(orderId,seqNo);
		}
		
		logger.info("mobCcsAutoUpdateBomweb_orderStatus() is End");
	}
}