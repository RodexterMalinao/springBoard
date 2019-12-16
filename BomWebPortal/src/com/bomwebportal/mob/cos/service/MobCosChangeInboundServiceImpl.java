package com.bomwebportal.mob.cos.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.cos.dao.MobCosChangeInboundDAO;
import com.bomwebportal.mob.cos.dto.MobCosStaffListDTO;
import com.bomwebportal.mob.ds.dao.MobDsFalloutLogDAO;
import com.bomwebportal.mob.ds.dto.MobDsFalloutLogDTO;
import com.bomwebportal.util.Utility;

@Transactional(readOnly=true)
public class MobCosChangeInboundServiceImpl implements MobCosChangeInboundService {

	protected final Log logger = LogFactory.getLog(getClass());
	private MobCosChangeInboundDAO mobCosChangeInboundDAO;
	
	public MobCosChangeInboundDAO getMobCosChangeInboundDAO() {
		return mobCosChangeInboundDAO;
	}

	public void setMobCosChangeInboundDAO(
			MobCosChangeInboundDAO mobCosChangeInboundDAO) {
		this.mobCosChangeInboundDAO = mobCosChangeInboundDAO;
	}

	public List<String> getTeamListByCenterId(String selectedcenterId, String channelCd) {
		try {
			return mobCosChangeInboundDAO.getTeamListByCenterId(selectedcenterId,channelCd);
		} catch (DAOException e) {
			logger.error("Exception caught in getTeamList(String channelCd)", e);
		}
		return null;
	}	
	public  List<String> getCenterList(String channelCd) {
		try {
			return mobCosChangeInboundDAO.getCenterList(channelCd);
		} catch (DAOException e) {
			logger.error("Exception caught in getCenterList(String channelCd)", e);
		}
		return null;
	}
	public  List<String> getTeamList(String channelCd) {
		try {
			return mobCosChangeInboundDAO.getTeamList(channelCd);
		} catch (DAOException e) {
			logger.error("Exception caught in getTeamList(String channelCd)", e);
		}
		return null;
	}
	public List<MobCosStaffListDTO> getStaffList(String channelCd,String centerId,String teamId,String inBoundindSt4,String inBoundindOw) {
		try {
			List<MobCosStaffListDTO> staffList= new ArrayList<MobCosStaffListDTO>();
			staffList=mobCosChangeInboundDAO.getStaffList(channelCd, centerId, teamId,inBoundindSt4,inBoundindOw);
			for(MobCosStaffListDTO item: staffList) {
				if(findRequiredAction(item.getInBoundindSt4(),item.getInBoundindOw())!=null){
					item.setAction(findRequiredAction(item.getInBoundindSt4(),item.getInBoundindOw()));
				}
				
			}
			return staffList;
		} catch (DAOException e) {
			logger.error("Exception caught in getStaffList", e);
		}
		return null;
	}
	
	public List<MobCosStaffListDTO> getStaffHistoryBySId(String staffId,String channelCd) {
		try {
			List<MobCosStaffListDTO> staffList= new ArrayList<MobCosStaffListDTO>();
			staffList=mobCosChangeInboundDAO.getStaffHistoryBySId(staffId,channelCd);
			return staffList;
		} catch (DAOException e) {
			logger.error("Exception caught in getStaffHistoryBySId", e);
		}
		return null;
	}
	
		
	public List<MobCosStaffListDTO> getStaffListBySId(String staffId,String channelCd) {
		try {
			List<MobCosStaffListDTO> staffList= new ArrayList<MobCosStaffListDTO>();
			staffList=mobCosChangeInboundDAO.getStaffListBySId(staffId,channelCd);
			for(MobCosStaffListDTO item: staffList) {
				List<MobCosStaffListDTO> inboundOwList= new ArrayList<MobCosStaffListDTO>();
				inboundOwList=findInBoundindOw(item.getStaffid());

				if (inboundOwList!=null &&!inboundOwList.isEmpty()){
					item.setInBoundindOw(inboundOwList.get(0).getInBoundindOw());
					item.setInBoundindOwLastUpdDate(inboundOwList.get(0).getInBoundindOwLastUpdDate());
					
				}else{
					item.setInBoundindOw("N/A");
				}
				if(findRequiredAction(item.getInBoundindSt4(),item.getInBoundindOw())!=null){
					item.setAction(findRequiredAction(item.getInBoundindSt4(),item.getInBoundindOw()));
				}
				
			}
			return staffList;
		} catch (DAOException e) {
			logger.error("Exception caught in getStaffList(String channelCd)", e);
		}
		return null;
	}
	
	public List<MobCosStaffListDTO> findInBoundindOw(String staffid) {
		try {
			return mobCosChangeInboundDAO.findInBoundindOw(staffid);
		} catch (DAOException e) {
			logger.error("Exception caught in findInBoundindOw(String staffid)", e);
		}
		return null;
	}
	public String findRequiredAction(String inBoundindSt4,String inBoundindOw) {
		try {
			String action=null;
			if ("Y".equalsIgnoreCase(inBoundindSt4) && "N/A".equalsIgnoreCase(inBoundindOw)){
				action="Overwrite to outbound";
			}else if ("N".equalsIgnoreCase(inBoundindSt4) && "N/A".equalsIgnoreCase(inBoundindOw)){
				action="Overwrite to inbound";
			}else if (!"N/A".equalsIgnoreCase(inBoundindOw)){
				action="Mark overwrite end";
			}
			return action;
		} catch (Exception e) {
			logger.error("Exception caught in findRequiredAction(String inBoundindSt4,String inBoundindOw)", e);
		}
		return null;
	}

	public int insertInbAssign(MobCosStaffListDTO dto) throws DAOException{
		logger.info("insertInbAssign() is called in MobCosChangeInboundServiceImpl");
		return mobCosChangeInboundDAO.insertInbAssign(dto);
		
	}
	public int updateOverwriteEnd(MobCosStaffListDTO dto) throws DAOException{
		logger.info("updateOverwriteEnd() is called in MobCosChangeInboundServiceImpl");
		return mobCosChangeInboundDAO.updateOverwriteEnd(dto);
	}
	public boolean isAllowAccess(String staffId){
		try {

			logger.info("isAllowAccess() is called in MobCosChangeInboundServiceImpl");
			return mobCosChangeInboundDAO.isAllowAccess(staffId);
		} catch (DAOException de) {
			logger.error("Exception caught in isAllowAccess(MobCosStaffListDTO dto)", de);
			throw new AppRuntimeException(de);
		}
	}
}
