package com.bomwebportal.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.MaintRoleDAO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ImsDSQCDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;
import com.bomwebportal.mob.ccs.dto.MobCcsMaintFuncDTO;
import com.bomwebportal.mob.ds.dao.MobDsOrderDAO;

@Transactional(readOnly=true)
public class WelcomeServiceImpl implements WelcomeService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private MaintRoleDAO maintRoleDao;
	private MobCcsOrderDAO mobCcsOrderDao;
	private MobDsOrderDAO mobDsOrderDao;
	private ImsDSQCDAO imsDSQCDao;
	
	public MaintRoleDAO getMaintRoleDao() {
		return maintRoleDao;
	}

	public void setMaintRoleDao(MaintRoleDAO maintRoleDao) {
		this.maintRoleDao = maintRoleDao;
	}
	
	public MobCcsOrderDAO getMobCcsOrderDao() {
		return mobCcsOrderDao;
	}

	public void setMobCcsOrderDao(MobCcsOrderDAO mobCcsOrderDao) {
		this.mobCcsOrderDao = mobCcsOrderDao;
	}

	public MobDsOrderDAO getMobDsOrderDao(){
		return mobDsOrderDao;
	}
	
	public void setMobDsOrderDao(MobDsOrderDAO mobDsOrderDao) {
		this.mobDsOrderDao = mobDsOrderDao;
	}
	
	public ImsDSQCDAO getImsDSQCDao() {
		return imsDSQCDao;
	}

	public void setImsDSQCDao(ImsDSQCDAO imsDSQCDao) {
		this.imsDSQCDao = imsDSQCDao;
	}
	
//	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
//	public List<String> getUsableMaintId(String channelCd, String category){
//		try{
//			logger.info("getUsableMaintId() is called");
//			return maintRoleDao.getUsableMaintId(channelCd, category);
//		}catch (DAOException de) {
//			logger.error("Exception caught in getUsableMaintId()", de);
//			throw new AppRuntimeException(de);
//		}
//		
//	}
	
	public String getOrderEnquiryCount (String startDate, String endDate, 
			String orderId, String orderStatus, String variousDateType, String channel, 
			String staffId, String orderType, String category, String areaCd, 
			String shopCd, String group){
		try{
			logger.info("getOrderEnquiryCount() is called");
			return mobCcsOrderDao.getOrderEnquiryCount(startDate, endDate, orderId, orderStatus, variousDateType, channel, staffId, orderType, category, areaCd, shopCd, group);
		}catch (DAOException de) {
			logger.error("Exception caught in getOrderEnquiryCount()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	public String getDsOrderReviewCount (String staffId, String channelId, String channelCd, String category){
		try{
			logger.info("getOrderReviewCount() is called");
			return mobDsOrderDao.getDsOrderReviewCount(staffId, channelId, channelCd, category);
		}catch (DAOException de) {
			logger.error("Exception caught in getDsOrderReviewCount()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	public List<MobCcsMaintFuncDTO> getUsableMaintFuncInfo(int channelId, String channelCd, String category) {
		try{
			logger.info("getUsableMaintFuncInfo() is called");
			return maintRoleDao.getUsableMaintFuncInfo(channelId, channelCd, category);
		}catch (DAOException de) {
			logger.error("Exception caught in getUsableMaintFuncInfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public BomSalesUserDTO getDSinfo(BomSalesUserDTO user){
		try{
			return imsDSQCDao.getDSinfo(user);
		}catch (DAOException de) {
			logger.error("Exception caught in getDSinfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	

}
