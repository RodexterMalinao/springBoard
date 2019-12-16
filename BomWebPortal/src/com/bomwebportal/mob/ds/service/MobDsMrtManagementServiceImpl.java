package com.bomwebportal.mob.ds.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.service.MobCcsMaintParmLkupService;
import com.bomwebportal.mob.ccs.service.MrtInventoryService.FunctionCd;
import com.bomwebportal.mob.ccs.service.MrtInventoryService.ParmType;
import com.bomwebportal.mob.ds.dao.MobDsMrtManagementDAO;
import com.bomwebportal.mob.ds.dto.MobDsMrtManagementDTO;

public class MobDsMrtManagementServiceImpl implements MobDsMrtManagementService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobDsMrtManagementDAO mobDsMrtManagementDAO;
	private MobCcsMaintParmLkupService mobCcsMaintParmLkupService;
	
	public MobDsMrtManagementDAO getMobDsMrtManagementDAO() {
		return mobDsMrtManagementDAO;
	}
	public void setMobDsMrtManagementDAO(MobDsMrtManagementDAO mobDsMrtManagementDAO) {
		this.mobDsMrtManagementDAO = mobDsMrtManagementDAO;
	}
	public MobCcsMaintParmLkupService getMobCcsMaintParmLkupService() {
		return mobCcsMaintParmLkupService;
	}
	public void setMobCcsMaintParmLkupService(
			MobCcsMaintParmLkupService mobCcsMaintParmLkupService) {
		this.mobCcsMaintParmLkupService = mobCcsMaintParmLkupService;
	}
	
	public List<MobDsMrtManagementDTO> getMrtSummaryList(MobDsMrtManagementDTO searchDto, String staffId, String category, String channelCd) {
		try {
			List<MobDsMrtManagementDTO> requestServiceNums = mobDsMrtManagementDAO.getMrtSummaryList(searchDto, staffId, category, channelCd);
			return requestServiceNums;
		} catch (DAOException e) {
			logger.error("Exception caught in getMrtSummaryList()", e);
			return null;
		}
	}
	
	public String getSalesCentreCd(String staffId, Date appDate){
		try {
			return mobDsMrtManagementDAO.getSalesCentreCd(staffId, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in getSalesCentreCd()", e);
			return null;
		}
	}
	
	public String getSalesTeamCd(String staffId, Date appDate) {
		try {
			return mobDsMrtManagementDAO.getSalesTeamCd(staffId, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in getSalesTeamCd()", e);
			return null;
		}
	}
	
	public String getSalesChannelCd(String staffId, Date appDate) {
		try {
			return mobDsMrtManagementDAO.getSalesChannelCd(staffId, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in getSalesChannelCd()", e);
			return null;
		}
	}
	
	public String getStoreChannelCd(String storeCd) {
		try {
			return mobDsMrtManagementDAO.getStoreChannelCd(storeCd);
		} catch (DAOException e) {
			logger.error("Exception caught in getStoreChannelCd()", e);
			return null;
		}
	}
	
	public boolean isValidMrtStore(List<String> msisdn, String teamCode) {
		try {
			return mobDsMrtManagementDAO.isValidMrtStore(msisdn, teamCode);
		} catch (DAOException e) {
			logger.error("Exception caught in getStoreChannelCd()", e);
			return false;
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public int updateMrtInventory(MobDsMrtManagementDTO dto, String updateStaffId) {
		try {
			return mobDsMrtManagementDAO.updateMrtInventory(dto, updateStaffId);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
			logger.error("Exception caught in updateMrtInventory()", e);
		}
		return -1;
	}
	
	public List<MaintParmLkupDTO> getMsisdnlvlList() {
		return this.mobCcsMaintParmLkupService.getMaintParmLkupDTO("CFM", "MOB", FunctionCd.MRT_INVENTORY.getValue(), ParmType.GRADE.toString());
	}
	
	public boolean allowUpdateMrtStatus(List<String> msisdn) {
		try {
			return mobDsMrtManagementDAO.allowUpdateMrtStatus(msisdn);
		} catch (DAOException e) {
			logger.error("Exception caught in allowUpdateMrtStatus()", e);
			return false;
		}
	}
	
	public List<String> getMdvTeamList() {
		try {
			return mobDsMrtManagementDAO.getMdvTeamList();
		} catch (DAOException e) {
			logger.error("Exception caught in getMdvTeamList()", e);
			return null;
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
    public int deleteDsReuseMrtInventory(String msisdn) {
		try {
			logger.info("deleteDsReuseMrtInventory() is called in MobDsMrtManagementServiceImpl");
			return mobDsMrtManagementDAO.deleteDsReuseMrtInventory(msisdn);
	
		} catch (DAOException de) {
			logger.error("Exception caught in deleteDsReuseMrtInventory()", de);
			throw new AppRuntimeException(de);
		}
    }
}
