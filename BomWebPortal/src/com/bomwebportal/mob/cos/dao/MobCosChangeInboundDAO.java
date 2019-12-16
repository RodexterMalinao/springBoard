package com.bomwebportal.mob.cos.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.cos.dto.MobCosStaffListDTO;
import com.bomwebportal.util.Utility;

public class MobCosChangeInboundDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<String> getTeamListByCenterId(String selectedcenterId, String channelCd) throws DAOException { 
		logger.info("getTeamList() is called");
		List<String> teamList = new ArrayList<String>();
		String SQL = "SELECT distinct team_cd FROM bomweb_sales_assignment where channel_cd=:channelCd and channel_id=2 and team_cd is not null and centre_cd=:centerId";
			
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("team_cd");
			}
		};
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("centerId",selectedcenterId);
			params.addValue("channelCd",channelCd);
			teamList = simpleJdbcTemplate.query(SQL, mapper,params);
				
	
		} catch (EmptyResultDataAccessException erdae) {
			teamList = null;
		}
		catch (Exception e) {
			logger.error("Exception caught in getCenterList()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return teamList;
		
	}
	
	public List<String> getCenterList(String channelCd) throws DAOException { 
		logger.info("getCenterList() is called");
		List<String> centerList = new ArrayList<String>();
		String SQL = "SELECT distinct centre_cd FROM bomweb_sales_assignment where channel_cd=:channelCd and channel_id=2 and centre_cd is not null";
			
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("centre_cd");
			}
		};
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("channelCd",channelCd);
			centerList = simpleJdbcTemplate.query(SQL, mapper,params);
				
	
		} catch (EmptyResultDataAccessException erdae) {
			centerList = null;
		}
		catch (Exception e) {
			logger.error("Exception caught in getCenterList()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return centerList;
		
	}
	
	public List<String> getTeamList(String channelCd) throws DAOException { 
		logger.info("getTeamList() is called");
		List<String> teamList = new ArrayList<String>();
		String SQL = "SELECT distinct team_cd FROM bomweb_sales_assignment where channel_cd=:channelCd and channel_id=2 and team_cd is not null";
			
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("team_cd");
			}
		};
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("channelCd",channelCd);
			teamList = simpleJdbcTemplate.query(SQL, mapper,params);
				
	
		} catch (EmptyResultDataAccessException erdae) {
			teamList = null;
		}
		catch (Exception e) {
			logger.error("Exception caught in getCenterList()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return teamList;
		
	}
	
	public List<MobCosStaffListDTO> getStaffHistoryBySId(String staffid,String channelCd) throws DAOException { 
		logger.info("getStaffHistoryBySId() is called");
		List<MobCosStaffListDTO> staffList = new ArrayList<MobCosStaffListDTO>();

		String SQL =" select distinct a.staff_id, a.channel_cd, a.centre_cd, a.team_cd, nvl(a.inbound_ind,'N') inb_ind, nvl(b.tmp_inbound_ind,'N/A') tmp_inb_ind, b.active_ind, "+
		" to_char(b.create_date,'dd/mm/yyyy HH24:MI:SS') overwrite_date, b.create_by, to_char(b.last_upd_date,'dd/mm/yyyy HH24:MI:SS') last_upd_date, b.last_upd_by "+		
		" from bomweb_sales_assignment a, bomweb_sales_tmp_inb_assgn b "+		
		" where a.channel_cd = :channelCd "+
		" and a.staff_id = b.staff_id "+
		" and a.staff_id = :staffid "+			
		" and to_char(a.start_date, 'yyyymmdd') <= to_char(sysdate, 'yyyymmdd') "+	
		" and nvl(to_char(a.end_date,'yyyymmdd'),'99991231') > to_char(sysdate,'yyyymmdd') "+	
		" order by overwrite_date, active_ind desc ";


		
		ParameterizedRowMapper<MobCosStaffListDTO> mapper = new ParameterizedRowMapper<MobCosStaffListDTO>() {

			public MobCosStaffListDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCosStaffListDTO staffListDTO = new MobCosStaffListDTO();
				staffListDTO.setStaffid(rs.getString("staff_id"));
				staffListDTO.setChannelCd(rs.getString("channel_cd"));
				staffListDTO.setCenterId(rs.getString("centre_cd"));
				staffListDTO.setTeamId(rs.getString("team_cd"));
				staffListDTO.setInBoundindSt4(rs.getString("inb_ind"));
				staffListDTO.setInBoundindOw(rs.getString("tmp_inb_ind"));
				staffListDTO.setActiveInd(rs.getString("active_ind"));
				staffListDTO.setInBoundindOwLastUpdDate(rs.getString("overwrite_date"));
				staffListDTO.setCreateBy(rs.getString("create_by"));
				staffListDTO.setLastUpdDate(rs.getString("last_upd_date"));
				staffListDTO.setUpdBy(rs.getString("last_upd_by"));
				return staffListDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffid",staffid);
			params.addValue("channelCd",channelCd);
			staffList = simpleJdbcTemplate.query(SQL, mapper,params);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getStaffHistoryBySId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return staffList;
	}	

	
	public List<MobCosStaffListDTO> getStaffListBySId(String staffid,String channelCd) throws DAOException { 
		logger.info("getStaffListBySId() is called");
		List<MobCosStaffListDTO> staffList = new ArrayList<MobCosStaffListDTO>();

		String SQL = "select distinct a.staff_id, a.channel_cd, a.centre_cd, a.team_cd, nvl(a.inbound_ind,'N') inb_ind, nvl(b.tmp_inbound_ind,'N/A') tmp_inb_ind, to_char(b.create_date,'dd/mm/yyyy HH24:MI:SS') overwrite_date "+
		" from bomweb_sales_assignment a, bomweb_sales_tmp_inb_assgn b "+
		" where a.channel_cd = :channelCd "+
		" and a.staff_id = :staffid "+
		" and to_char(a.start_date, 'yyyymmdd') <= to_char(sysdate, 'yyyymmdd') "+
		" and nvl(to_char(a.end_date,'yyyymmdd'),'99991231') > to_char(sysdate,'yyyymmdd') "+
		" and (a.staff_id = b.staff_id (+) "+
		" and b.active_ind (+) = 'Y') "+
		" order by inb_ind, tmp_inb_ind, a.staff_id ";

		
		ParameterizedRowMapper<MobCosStaffListDTO> mapper = new ParameterizedRowMapper<MobCosStaffListDTO>() {

			public MobCosStaffListDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCosStaffListDTO staffListDTO = new MobCosStaffListDTO();
				staffListDTO.setStaffid(rs.getString("staff_id"));
				staffListDTO.setChannelCd(rs.getString("channel_cd"));
				staffListDTO.setCenterId(rs.getString("centre_cd"));
				staffListDTO.setTeamId(rs.getString("team_cd"));
				staffListDTO.setInBoundindSt4(rs.getString("inb_ind"));
				staffListDTO.setInBoundindOw(rs.getString("tmp_inb_ind"));
				staffListDTO.setInBoundindOwLastUpdDate(rs.getString("overwrite_date"));
				return staffListDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffid",staffid);
			params.addValue("channelCd",channelCd);
			staffList = simpleJdbcTemplate.query(SQL, mapper,params);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getStaffListBySId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return staffList;
	}	
	
	public List<MobCosStaffListDTO> getStaffList(String channelCd,String centerId,String teamId,String inBoundindSt4,String inBoundindOw) throws DAOException { 
		logger.info("getStaffList() is called");
		List<MobCosStaffListDTO> staffList = new ArrayList<MobCosStaffListDTO>();

		String SQL = "select distinct a.staff_id, a.channel_cd, a.centre_cd, a.team_cd, nvl(a.inbound_ind,'N') inb_ind, nvl(b.tmp_inbound_ind,'N/A') tmp_inb_ind, to_char(b.create_date,'dd/mm/yyyy') overwrite_date "+
		" from bomweb_sales_assignment a, bomweb_sales_tmp_inb_assgn b "+
		" where a.channel_cd = :channelCd "+
		" and a.centre_cd = :centerId "+
		" and a.team_cd = :teamId "+
		" and to_char(a.start_date, 'yyyymmdd') <= to_char(sysdate, 'yyyymmdd') "+
		" and nvl(to_char(a.end_date,'yyyymmdd'),'99991231') > to_char(sysdate,'yyyymmdd') "+
		" and (a.staff_id = b.staff_id (+) "+
		" and b.active_ind (+) = 'Y') ";
		if ("Y".equalsIgnoreCase(inBoundindSt4)){
			SQL=SQL+" and nvl(a.inbound_ind,'N') = 'Y' ";
		}else if ("N".equalsIgnoreCase(inBoundindSt4)){
			SQL=SQL+" and nvl(a.inbound_ind,'N') = 'N' ";
		}

		if ("Y".equalsIgnoreCase(inBoundindOw)){
			SQL=SQL+" and nvl(b.tmp_inbound_ind,'N/A') = 'Y' ";
		}else if ("N".equalsIgnoreCase(inBoundindOw)){
			SQL=SQL+" and nvl(b.tmp_inbound_ind,'N/A') = 'N' ";
		}else if ("NA".equalsIgnoreCase(inBoundindOw)){
			SQL=SQL+" and nvl(b.tmp_inbound_ind,'N/A') = 'N/A' ";
		}
		
		SQL=SQL+" order by inb_ind, tmp_inb_ind, a.staff_id ";


		
		ParameterizedRowMapper<MobCosStaffListDTO> mapper = new ParameterizedRowMapper<MobCosStaffListDTO>() {

			public MobCosStaffListDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCosStaffListDTO staffListDTO = new MobCosStaffListDTO();
				staffListDTO.setStaffid(rs.getString("staff_id"));
				staffListDTO.setChannelCd(rs.getString("channel_cd"));
				staffListDTO.setCenterId(rs.getString("centre_cd"));
				staffListDTO.setTeamId(rs.getString("team_cd"));
				staffListDTO.setInBoundindSt4(rs.getString("inb_ind"));
				staffListDTO.setInBoundindOw(rs.getString("tmp_inb_ind"));
				staffListDTO.setInBoundindOwLastUpdDate(rs.getString("overwrite_date"));

				return staffListDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("channelCd",channelCd);
			params.addValue("centerId",centerId);
			params.addValue("teamId",teamId);
			staffList = simpleJdbcTemplate.query(SQL, mapper,params);			
		} catch (EmptyResultDataAccessException erdae) {
			staffList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getStaffList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return staffList;
	}

	public List<MobCosStaffListDTO> findInBoundindOw(String staffid) throws DAOException { 
		logger.info("findInBoundindOw() is called");
		List<MobCosStaffListDTO> inboundList = new ArrayList<MobCosStaffListDTO>();

		String SQL = "SELECT staff_id,tmp_inbound_ind,last_upd_date FROM bomweb_sales_tmp_inb_assgn where staff_id=:staffid";	
		
		ParameterizedRowMapper<MobCosStaffListDTO> mapper = new ParameterizedRowMapper<MobCosStaffListDTO>() {

			public MobCosStaffListDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCosStaffListDTO staffListDTO = new MobCosStaffListDTO();
				staffListDTO.setStaffid(rs.getString("staff_id"));
				staffListDTO.setInBoundindOw(rs.getString("tmp_inbound_ind"));
				staffListDTO.setInBoundindOwLastUpdDate(Utility.date2String(rs.getTimestamp("last_upd_date"),"dd/MM/yyyy"));
				return staffListDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffid",staffid);


			inboundList = simpleJdbcTemplate.query(SQL, mapper,params);			
		} catch (EmptyResultDataAccessException erdae) {
			inboundList = null;
		} catch (Exception e) {
			logger.error("Exception caught in findInBoundindOw()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return inboundList;
	}
	public int insertInbAssign(MobCosStaffListDTO dto) throws DAOException {
		logger.info("insertInbAssign() is called");

		// define SQL string
		String SQL = "insert into bomweb_sales_tmp_inb_assgn\n"
				+ " (staff_id,tmp_inbound_ind,active_ind,create_by,create_date,last_upd_by,last_upd_date)\n"
				+ "values\n"
				+ "  (:staffId,:inBoundindOw,:activeInd,:createBy,sysdate,:lastUpdBy,sysdate)";

		// insert to table
		try {			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", dto.getStaffid());
			
			if ("Y".equalsIgnoreCase(dto.getInBoundindSt4())){
				params.addValue("inBoundindOw", "N");
			}else{
				params.addValue("inBoundindOw", "Y");
			}
			params.addValue("activeInd", "Y");
			params.addValue("createBy", dto.getUpdBy());
			params.addValue("lastUpdBy", dto.getUpdBy());
			return simpleJdbcTemplate.update(SQL, params);
			
		} catch (Exception e) {
			logger.error("Exception caught in insertInbAssign()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	public int updateOverwriteEnd(MobCosStaffListDTO dto) throws DAOException {
		String SQL = "update bomweb_sales_tmp_inb_assgn "
			+ " set active_ind='N', last_upd_by=:lastUpdBy, last_upd_date= sysdate "
			+ " where active_ind='Y' and staff_id=:staffId ";


		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", dto.getStaffid());
			params.addValue("lastUpdBy", dto.getUpdBy());
			return simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateOverwriteEnd(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	public boolean isAllowAccess(String staffId) throws DAOException {
		
	String sql = "SELECT a.staff_id FROM bomweb_sales_profile a,bomweb_sales_assignment b "
			+ "where a.staff_id=b.staff_id and category='SALES MANAGER' and channel_id=2 "
			+" AND to_char(A.start_date, 'yyyymmdd') <= to_char(SYSDATE, 'yyyymmdd') " 
			+" AND nvl(to_char(A.end_date,'yyyymmdd'),'99991231') > to_char(SYSDATE,'yyyymmdd') "
			+" AND to_char(b.start_date, 'yyyymmdd') <= to_char(SYSDATE, 'yyyymmdd') " 
			+" AND nvl(to_char(b.end_date,'yyyymmdd'),'99991231') > to_char(SYSDATE,'yyyymmdd') AND A.staff_id=:staffId ";
	
	
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("staffId", staffId);
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int row) throws SQLException {
			return rs.getString("staff_id");
		}
	};
	try {
		List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
		if (result == null || result.isEmpty()) {
			return false;
		} else {
			return true;
		}
		
	} catch (EmptyResultDataAccessException erdae) {
		System.out.println("not allow "+staffId);
		return false;
	} catch (Exception e) {
		logger.error("isAllowAccess Exception: " + e, e);
		throw new DAOException(e.getMessage(), e);
	}	
	
}

}
