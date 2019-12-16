package com.bomltsportal.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class DnPoolDAO extends BaseDAO {
	
	public List<String> getDnListFromPool() throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append(" WHERE SRV_NUM IN ( ");
		sql.append("  SELECT SRV_NUM FROM ( ");
		sql.append("    SELECT SRV_NUM FROM BOMWEB_DN_POOL ");
		sql.append("      WHERE STATUS = 'N'");
		sql.append("      ORDER BY dbms_random.value ) ");
		sql.append("  WHERE ROWNUM <= ( ");
		sql.append("    SELECT CODE_ID FROM BOMWEB_CODE_LKUP ");
		sql.append("    WHERE CODE_TYPE = 'LTS_DN_DISPLAY_COUNT' ))");
		sql.append("  ORDER BY SRV_NUM ");
		sql.append("  FOR UPDATE NOWAIT ");
		
		try {
			return (List<String>) simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(sql.toString(), paramSource, String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getDnListFromPool()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getEyeDeviceList():", e);
			return getDnListFromPool();
		}
	}

	
	public void updateDnStatus(List<String> dnList, String status) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append(" STATUS = :status ,");
		sql.append(" LAST_UPD_BY = 'TLTSOLS' ,");
		sql.append(" LAST_UPD_DATE = SYSDATE");
		sql.append(" WHERE SRV_NUM IN (:dnList) ");

		try {
			paramSource.addValue("status", status);
			paramSource.addValue("dnList", dnList);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updateDnStatus():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

	
	public void lockDnList(List<String> dnList, String status, String sessionId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append(" STATUS = :status ,");
		sql.append(" SESSION_ID = :sessionId ,");
		sql.append(" LAST_UPD_BY = 'TLTSOLS', ");
		sql.append(" LAST_UPD_DATE = SYSDATE");
		sql.append(" WHERE SRV_NUM IN (:dnList) ");

		try {
			paramSource.addValue("status", status);
			paramSource.addValue("sessionId", sessionId);
			paramSource.addValue("dnList", dnList);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updateDnStatus():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void assignDn(String dn) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append(" STATUS = :status ,");
		sql.append(" SESSION_ID = null ,");
		sql.append(" LAST_UPD_BY = 'TLTSOLS' ,");
		sql.append(" LAST_UPD_DATE = SYSDATE");
		
		sql.append(" WHERE SRV_NUM = :srvNum ");

		try {
			paramSource.addValue("status", BomLtsConstant.DN_POOL_STATUS_ASSIGNED);
			paramSource.addValue("srvNum", dn);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in assignDn():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	public void releaseDnStatus(String sessionId) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append(" STATUS = :status ,");
		sql.append(" SESSION_ID = NULL ,");
		sql.append(" LAST_UPD_BY = 'TLTSOLS' ,");
		sql.append(" LAST_UPD_DATE = SYSDATE");
		sql.append(" WHERE SESSION_ID = :sessionId ");

		try {
			paramSource.addValue("status", BomLtsConstant.DN_POOL_STATUS_NORMAL);
			paramSource.addValue("sessionId", sessionId);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in releaseDnStatus():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	
}
