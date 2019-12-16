package com.bomwebportal.lts.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class LtsAcqDnPoolDAO extends BaseDAO {
	
	@SuppressWarnings("unchecked")
	public List<String> searchDnListFromPoolByNoCriteria() throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("WHERE SRV_NUM IN ( ");
		sql.append("SELECT SRV_NUM FROM ( ");
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("WHERE STATUS = 'N' ");
		sql.append("ORDER BY dbms_random.value ) ");
		sql.append("WHERE ROWNUM <= ( ");
		sql.append("SELECT CODE_ID FROM BOMWEB_CODE_LKUP ");
		sql.append("WHERE CODE_TYPE = 'LTS_ACQ_DN_DISPLAY_COUNT')) ");
		sql.append("ORDER BY SRV_NUM ");
		sql.append("FOR UPDATE NOWAIT ");
		
		try {
			return (List<String>) simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(
					sql.toString(), paramSource, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in searchDnListFromPoolByNoCriteria()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in searchDnListFromPoolByNoCriteria():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
		@SuppressWarnings("unchecked")
		public List<String> searchDnListFromPoolByFirst8Digits(String searchingKey) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("WHERE SRV_NUM IN ( ");
		sql.append("SELECT SRV_NUM FROM ( ");
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("WHERE STATUS = 'N' ");
		sql.append("AND SUBSTR(SRV_NUM,5,12) like :searchingKey||'%' ");
		sql.append("ORDER BY dbms_random.value ) ");
		sql.append("WHERE ROWNUM <= ( ");
		sql.append("SELECT CODE_ID FROM BOMWEB_CODE_LKUP ");
		sql.append("WHERE CODE_TYPE = 'LTS_ACQ_DN_DISPLAY_COUNT')) ");
		sql.append("ORDER BY SRV_NUM ");
		sql.append("FOR UPDATE NOWAIT ");
		
		try {
			paramSource.addValue("searchingKey", searchingKey);
			return (List<String>) simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(
					sql.toString(), paramSource, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in searchDnListFromPoolByFirst8Digits()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in searchDnListFromPoolByFirst8Digits():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
		@SuppressWarnings("unchecked")
		public List<String> searchDnListFromPoolByLast3Digits(String searchingKey) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("WHERE SRV_NUM IN ( ");
		sql.append("SELECT SRV_NUM FROM ( ");
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("WHERE STATUS = 'N' ");
		sql.append("AND SRV_NUM like '%'||:searchingKey ");
		sql.append("ORDER BY dbms_random.value ) ");
		sql.append("WHERE ROWNUM <= ( ");
		sql.append("SELECT CODE_ID FROM BOMWEB_CODE_LKUP ");
		sql.append("WHERE CODE_TYPE = 'LTS_ACQ_DN_DISPLAY_COUNT')) ");
		sql.append("ORDER BY SRV_NUM ");
		sql.append("FOR UPDATE NOWAIT ");
		
		try {
			paramSource.addValue("searchingKey", searchingKey);
			return (List<String>) simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(
					sql.toString(), paramSource, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in searchDnListFromPoolByLast3Digits()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in searchDnListFromPoolByLast3Digits():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
		
	@SuppressWarnings("unchecked")
	public List<String> getLockedDnList(String sessionId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("WHERE STATUS = :status ");
		sql.append("AND SESSION_ID = :sessionId ");
		sql.append("ORDER BY SRV_NUM ");
		
		try {
			paramSource.addValue("status", LtsBackendConstant.DN_POOL_STATUS_LOCKED);
			paramSource.addValue("sessionId", sessionId);
			return (List<String>) simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(
					sql.toString(), paramSource, String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getLockedDnList()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getLockedDnList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getReservedDnList(String sessionId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("WHERE STATUS = :status ");
		sql.append("AND SESSION_ID = :sessionId ");
		sql.append("ORDER BY SRV_NUM ");
		
		try {
			paramSource.addValue("status", LtsBackendConstant.DN_POOL_STATUS_RESERVED);
			paramSource.addValue("sessionId", sessionId);
			return (List<String>) simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(
					sql.toString(), paramSource, String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getReserveDnList()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getReserveDnList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getReservedDnList(String sessionId, List<String> dnList) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("WHERE STATUS = :status ");
		sql.append("AND SESSION_ID = :sessionId ");
		if (dnList!=null && dnList.size()>0) {
			sql.append("AND SRV_NUM IN (:dnList) ");
		}
		sql.append("ORDER BY SRV_NUM ");
		
		try {
			paramSource.addValue("status", LtsBackendConstant.DN_POOL_STATUS_RESERVED);
			paramSource.addValue("sessionId", sessionId);			
			if (dnList!=null && dnList.size()>0) {
				paramSource.addValue("dnList", dnList);
			}
			return (List<String>) simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(
					sql.toString(), paramSource, String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getReserveDnList()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getReserveDnList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
    public void updDnStatus(List<String> dnList, String nStatus, String oStatus, String sessionId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append("STATUS = :status, ");
		sql.append("LAST_UPD_BY = 'TLTSACQ', ");
		sql.append("LAST_UPD_DATE = SYSDATE ");
		sql.append("WHERE SESSION_ID = :sessionId ");
		sql.append("AND STATUS = :oStatus ");
		sql.append("AND SRV_NUM IN (:dnList) ");

		try {
			paramSource.addValue("nStatus", nStatus);
			paramSource.addValue("oStatus", oStatus);
			paramSource.addValue("sessionId", sessionId);
			paramSource.addValue("dnList", dnList);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updDnStatus():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updDnStatusToLock(List<String> dnList, String sessionId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append("STATUS = :status, ");
		sql.append("SESSION_ID = :sessionId, ");
		sql.append("LAST_UPD_BY = 'TLTSACQ', ");
		sql.append("LAST_UPD_DATE = SYSDATE ");
		sql.append("WHERE SRV_NUM IN (:dnList) ");

		try {
			paramSource.addValue("status", LtsBackendConstant.DN_POOL_STATUS_LOCKED);
			paramSource.addValue("sessionId", sessionId);
			paramSource.addValue("dnList", dnList);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updDnStatusToLock():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	    
    public void updDnStatusToReserve(List<String> dnList, String oStatus, String sessionId, String staffId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append("STATUS = :status, ");
		sql.append("LAST_UPD_BY = :staffId, ");
		sql.append("LAST_UPD_DATE = SYSDATE ");
		sql.append("WHERE SRV_NUM IN (:dnList) ");
		sql.append("AND SESSION_ID = :sessionId ");
		sql.append("AND STATUS = :oStatus ");

		try {
			paramSource.addValue("status", LtsBackendConstant.DN_POOL_STATUS_RESERVED);
			paramSource.addValue("dnList", dnList);
			paramSource.addValue("sessionId", sessionId);
			paramSource.addValue("oStatus", oStatus);
			paramSource.addValue("staffId", staffId);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updDnStatusToReserve():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public void updDnStatusToConfirm(String srvNum, String sessionId, String staffId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append("STATUS = :status, ");
		sql.append("LAST_UPD_BY = :staffId, ");
		sql.append("LAST_UPD_DATE = SYSDATE ");
		sql.append("WHERE SRV_NUM = :srvNum ");
		sql.append("AND SESSION_ID = :sessionId ");

		try {
			paramSource.addValue("status", LtsBackendConstant.DN_POOL_STATUS_CONFIRMED);
			paramSource.addValue("srvNum", srvNum);
			paramSource.addValue("sessionId", sessionId);
			paramSource.addValue("staffId", staffId);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updDnStatusToConfirm():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updDnStatusToNormal(String oStatus, String sessionId, String staffId) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append("STATUS = :nStatus, ");
		sql.append("SESSION_ID = NULL, ");
		sql.append("LAST_UPD_BY = :staffId, ");
		sql.append("LAST_UPD_DATE = SYSDATE ");
		sql.append("WHERE SESSION_ID = :sessionId ");
		sql.append("AND STATUS = :oStatus ");

		try {
			paramSource.addValue("nStatus", LtsBackendConstant.DN_POOL_STATUS_NORMAL);
			paramSource.addValue("oStatus", oStatus);
			paramSource.addValue("sessionId", sessionId);
			paramSource.addValue("staffId", staffId);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updDnStatusToNormal():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updDnStatusToNormalBySrvNum(List<String> dnList, String oStatus, String sessionId, String staffId) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append("STATUS = :nStatus, ");
		sql.append("SESSION_ID = NULL, ");
		sql.append("LAST_UPD_BY = :staffId, ");
		sql.append("LAST_UPD_DATE = SYSDATE ");
		sql.append("WHERE SESSION_ID = :sessionId ");
		sql.append("AND STATUS = :oStatus ");
		sql.append("AND SRV_NUM IN (:dnList) ");

		try {
			paramSource.addValue("nStatus", LtsBackendConstant.DN_POOL_STATUS_NORMAL);
			paramSource.addValue("oStatus", oStatus);
			paramSource.addValue("sessionId", sessionId);
			paramSource.addValue("dnList", dnList);
			paramSource.addValue("staffId", staffId);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updDnStatusToNormalBySrvNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
		
	public void releaseDnStatusToLockedByReservedDn(List<String> dnList, String sessionId, String staffId) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append("STATUS = :nStatus, ");
		sql.append("LAST_UPD_BY = :staffId, ");
		sql.append("LAST_UPD_DATE = SYSDATE ");
		sql.append("WHERE SESSION_ID = :sessionId ");
		sql.append("AND STATUS = :oStatus ");
		sql.append("AND SRV_NUM IN (:dnList) ");

		try {
			paramSource.addValue("nStatus", LtsBackendConstant.DN_POOL_STATUS_LOCKED);
			paramSource.addValue("sessionId", sessionId);
			paramSource.addValue("oStatus", LtsBackendConstant.DN_POOL_STATUS_RESERVED);
			paramSource.addValue("dnList", dnList);
			paramSource.addValue("staffId", staffId);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in releaseDnStatusToLockedByReservedDn():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void releaseDnStatusToNormalByConfirmedDn(String srvNum) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append("STATUS = :nStatus, ");
		sql.append("SESSION_ID = NULL, ");
		sql.append("LAST_UPD_BY = 'TLTSACQ', ");
		sql.append("LAST_UPD_DATE = SYSDATE ");
		sql.append("WHERE STATUS = :oStatus ");
		sql.append("AND SRV_NUM = :srvNum ");

		try {
			paramSource.addValue("nStatus", LtsBackendConstant.DN_POOL_STATUS_NORMAL);
			paramSource.addValue("oStatus", LtsBackendConstant.DN_POOL_STATUS_CONFIRMED);
			paramSource.addValue("srvNum", srvNum);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in releaseDnStatusToNormalByConfirmedDn():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
		
    public void assignDn(String dn) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append("STATUS = :status, ");
		sql.append("SESSION_ID = null, ");
		sql.append("LAST_UPD_BY = 'TLTSACQ', ");
		sql.append("LAST_UPD_DATE = SYSDATE ");		
		sql.append("WHERE SRV_NUM = :srvNum ");
		
		try {
			paramSource.addValue("status", LtsBackendConstant.DN_POOL_STATUS_ASSIGNED);
			paramSource.addValue("srvNum", dn);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in assignDn():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
    
    
	@SuppressWarnings("unchecked")
	public boolean isDnAssigned(String dn) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("WHERE STATUS = :status ");		
    	sql.append("AND SRV_NUM = :srvNum ");
		sql.append("AND SESSION_ID is null ");
		
		try {
			paramSource.addValue("status", LtsBackendConstant.DN_POOL_STATUS_ASSIGNED);
			paramSource.addValue("srvNum", dn);
			List<String> list = (List<String>) simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(
					sql.toString(), paramSource, String.class);
			return list.isEmpty()? false : true;
		} catch (Exception e) {
			logger.error("Exception caught in isDnAssigned():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
