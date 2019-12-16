package com.bomwebportal.mob.cos.service;

import java.util.List;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.cos.dto.MobCosStaffListDTO;
import com.bomwebportal.mob.ds.dto.MobDsFalloutLogDTO;

public interface MobCosChangeInboundService {
	List<String> getCenterList(String channelCd);
	List<String> getTeamList(String channelCd);
	List<String> getTeamListByCenterId(String selectedcenterId, String channelCd);
	List<MobCosStaffListDTO> getStaffListBySId(String staffId,String channelCd);
	List<MobCosStaffListDTO> getStaffHistoryBySId(String staffId,String channelCd);
	List<MobCosStaffListDTO> getStaffList(String channelCd,String centerCd,String teamCd,String inBoundindSt4,String inBoundindOw);
	List<MobCosStaffListDTO> findInBoundindOw(String staffid);
	int insertInbAssign(MobCosStaffListDTO dto) throws DAOException;
	int updateOverwriteEnd(MobCosStaffListDTO dto) throws DAOException;
	boolean isAllowAccess(String staffId);
}
