package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dao.StockInDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;
import com.bomwebportal.mob.ds.dao.MobDsStockDAO;

@Transactional(readOnly=true)
public class StockUpdateServiceImpl implements StockUpdateService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockDAO stockdao;
	private StockInDAO stockIndao;
	private MobDsStockDAO dsStockdao;

	public void setStockdao(StockDAO stockdao) {
		this.stockdao = stockdao;
	}
	public StockDAO getStockdao() {
		return stockdao;
	}
	
	public void setStockIndao(StockInDAO stockIndao) {
		this.stockIndao = stockIndao;
	}
	public StockInDAO getStockIndao() {
		return stockIndao;
	}
	
	public MobDsStockDAO getDsStockdao() {
		return dsStockdao;
	}
	public void setDsStockdao(MobDsStockDAO dsStockdao) {
		this.dsStockdao = dsStockdao;
	}
	
	public List<StockUpdateDTO> getStockUpdateDTObyImei(String serialNumber) {
		try {
			logger.info("getStockUpdateDTObyImei() is called in StockUpdateServiceImpl");
			return stockdao.getStockUpdateDTObyImei(serialNumber);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockUpdateDTObyImei()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<StockUpdateDTO> getStockUpdateDTObyImeiReal(String serialNumber) {
		try {
			logger.info("getStockUpdateDTObyImeiReal() is called in StockUpdateServiceImpl");
			return stockdao.getStockUpdateDTObyImeiReal(serialNumber);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockUpdateDTObyImeiReal()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<StockUpdateDTO> getDSStockUpdateDTObyImei(String serialNumber) {
		try {
			logger.info("getDSStockUpdateDTObyImei() is called in StockUpdateServiceImpl");
			return dsStockdao.getDSStockUpdateDTObyImei(serialNumber);
		} catch (DAOException de) {
			logger.error("Exception caught in getDSStockUpdateDTObyImei()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String updateStockInventory(StockUpdateDTO dto, String username) {
		try {
			logger.info("updateStockInventory() is called in StockUpdateServiceImpl");
			return stockIndao.updateStockInventory(dto, username);
		} catch (DAOException de) {
			logger.error("Exception caught in updateStockInventory()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int updateDSStockInventory(StockUpdateDTO dto, String originalStatus, String username) {
		try {
			logger.info("updateDSStockInventory() is called in StockUpdateServiceImpl");
			return dsStockdao.updateDSStockInventory(dto, originalStatus, username);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDSStockInventory()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<CodeLkupDTO> getStockUpdateStatusList() {
		try {
			logger.info("getStockUpdateStatusList() is called in StockUpdateServiceImpl");
			return stockIndao.getStockUpdateStatusList();
		} catch (DAOException de) {
			logger.error("Exception caught in getStockUpdateStatusList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int updateDSStockInventoryValidation(String status, StockUpdateDTO dto, StockUpdateDTO originalDTO) {
		try {
			logger.info("updateDSStockInventoryValidation() is called in StockUpdateServiceImpl");
			if ("RSS".equalsIgnoreCase(status)){
				if (dsStockdao.validateEffEndDate(dto.getEventCode()) && dsStockdao.validateEffEndDate(originalDTO.getEventCode())) {
					return 1;
				}
			} else if ("SOS".equalsIgnoreCase(status)) {
				if (dsStockdao.getStaffAssignTeam(dto.getStaffId()) == dsStockdao.getStaffAssignTeam(originalDTO.getStaffId())) {
					return 1;
				}
			}
		} catch (DAOException de) {
			logger.error("Exception caught in updateDSStockInventoryValidation()", de);
			throw new AppRuntimeException(de);
		}
		return -1;
	}
	
	public boolean validateEffEndDate(String eventCode) {
		try {
			logger.info("validateEffEndDate() is called in StockUpdateServiceImpl");
			return dsStockdao.validateEffEndDate(eventCode);
		} catch (DAOException de) {
			logger.error("Exception caught in validateEffEndDate()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean validateUpdateStaff(String channelCode, String newStaffTeamCode, String userTeamCode, String newStaffID, String originalStaffID) {
		logger.info("validateUpdateStaff() is called in StockUpdateServiceImpl");
		String[] newStaff = this.getStaffAssignTeam(newStaffID);
		String[] originalStaff = this.getStaffAssignTeam(originalStaffID);
		//validate staff id and team
		if (newStaff != null && originalStaff != null) {
			if (newStaffTeamCode.equalsIgnoreCase(newStaff[1])) {
				if ("MDV".equalsIgnoreCase(channelCode)) {
					//1. stock belong to superviser's team
					//2. transfer to another team under the same store
					if (userTeamCode.equalsIgnoreCase(originalStaff[1]) && newStaff[0].equalsIgnoreCase(originalStaff[0])) {
						return true;
					}
				} else {
					//SIS, WHT
					// transfer to another team under the same store
					if (newStaff[0].equalsIgnoreCase(originalStaff[0])) {
						return true;
					}
				}
			}
		} else if (originalStaff == null) {
			
		}
		return false;
	}
	
	public String[] getStaffAssignTeam(String staffID) {
		try {
			logger.info("getStaffAssignTeam() is called in StockUpdateServiceImpl");
			return dsStockdao.getStaffAssignTeam(staffID);
		} catch (DAOException de) {
			logger.error("Exception caught in getStaffAssignTeam()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean validateStaff(String staffID, String storeCode, String teamCode) {
		try {
			logger.info("validateStaff() is called in StockUpdateServiceImpl");
			return dsStockdao.validateStaff(staffID, storeCode, teamCode);
		} catch (DAOException de) {
			logger.error("Exception caught in validateStaff()", de);
			throw new AppRuntimeException(de);
		}
	}
	
}

