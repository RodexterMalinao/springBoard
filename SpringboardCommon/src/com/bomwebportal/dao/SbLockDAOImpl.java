package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.LockDTO;
import com.bomwebportal.exception.DAOException;
import com.pccw.util.db.OracleSelectHelper;

public class SbLockDAOImpl extends BaseDAO implements SbLockDAO  {

	private static final String SQL_INSERT_LOCK = 
			"insert into sb_lock " +
			"(id, type, create_by, create_date, last_upd_by, last_upd_date) " +
			"values (?, ?, ?, sysdate, ?, sysdate)";
		
	private static final String SQL_LOCK = 
		"select id, type, lock_by, session_id, to_char(last_upd_date, 'dd/mm/yyyy') lock_date " +
		"from sb_lock " +
		"where id = ? and type = ? " + 
		"for update nowait";
		
	private static final String SQL_UPDATE_LOCKER = 
		"update sb_lock " +
		"set session_id = ?, lock_by = ?, last_upd_by = ?, last_upd_date = sysdate " +
		"where id = ? and type = ?";
		
	private static final String SQL_RELEASE_LOCK_BY_SESSION = 
		"update sb_lock " +
		"set session_id = null, lock_by = null, last_upd_by = ?, last_upd_date = sysdate " +
		"where session_id = ?";
	
	private static final String SQL_RELEASE_LOCK_BY_LOCKER = 
			"update sb_lock " +
			"set session_id = null, lock_by = null, last_upd_by = ?, last_upd_date = sysdate " +
			"where lock_by = ?";
	
	
	private static final String SQL_GER_LOCKER_INFO = 
			" select staff_name from bomweb_sales_lkup_v where staff_id = ? or org_staff_id = ? ";
		
	// NOT TO CALL DIRECTLY
	public void insertLock(String pId, String pType, String pUserId) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_INSERT_LOCK, pId, pType, pUserId, pUserId);
		} catch (Exception e) {
			throw new DAOException("Fail to insertLock\n" + e.getMessage(), e);
		}
	}
		
	// NOT TO CALL DIRECTLY
	public LockDTO getLockBy(String pId, String pType, String pUserId) throws DAOException {
			
		ParameterizedRowMapper<LockDTO> mapper = new ParameterizedRowMapper<LockDTO>() {
			public LockDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				LockDTO lock = new LockDTO();
				lock.setId(rs.getString("ID"));
				lock.setLockBy(rs.getString("LOCK_BY"));
				lock.setLockDate(rs.getString("LOCK_DATE"));
				lock.setSessionId(rs.getString("SESSION_ID"));
				lock.setType(rs.getString("TYPE"));
				return lock;
			}
		};
		try {
			return simpleJdbcTemplate.query(SQL_LOCK, mapper, pId, pType).get(0);
		} catch (Exception e) {
			throw new DAOException("Fail to getLockBy\n" + e.getMessage(), e);
		}
	}

	// NOT TO CALL DIRECTLY
	public void updateLocker(String pId, String pType, String pSessionId, String pLockBy, String pUserId) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_LOCKER, pSessionId, pLockBy, pUserId, pId, pType);
		} catch (Exception e) {
			throw new DAOException("Fail to updateLocker\n" + e.getMessage(), e);
		}
	}
	
	// NOT TO CALL DIRECTLY
	public void releaseLockBySession(String pSessionId, String pUserId) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_RELEASE_LOCK_BY_SESSION, pUserId, pSessionId);
		} catch (Exception e) {
			throw new DAOException("Fail to releaseLockBySession\n" + e.getMessage(), e);
		}
	}
	
	// NOT TO CALL DIRECTLY
	public void releaseLockByLocker(String pLocker, String pUserId) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_RELEASE_LOCK_BY_LOCKER, pUserId, pLocker);
		} catch (Exception e) {
			throw new DAOException("Fail to releaseLockByLocker\n" + e.getMessage(), e);
		}
	}
	
	public String getLockerInfo(String pLocker) throws Exception{
		return OracleSelectHelper.getSqlFirstRowColumnString(this.getDataSource(), SQL_GER_LOCKER_INFO, new Object[] {pLocker,pLocker});
	}
}