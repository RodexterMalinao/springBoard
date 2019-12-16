package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.LtsQuotaDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class LtsQuotaDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String SQL_GET_QUOTA = "SELECT QLL.PROGRAM_CD, QLL.PROGRAM_DESC, QLL.PSEF_CD, QLL.QUOTA, NVL(QLS.QUOTA_USED,0) as QUOTA_USED " +
												"FROM BOMWEB_QUOTA_LTS_LKUP QLL, BOMWEB_QUOTA_LTS_STATUS QLS " + 
												"WHERE QLL.PROGRAM_CD = QLS.PROGRAM_CD (+) " +
												"AND (SYSDATE BETWEEN QLL.EFF_START_DATE AND NVL(QLL.EFF_END_DATE, SYSDATE+1)) "; 
	
	private static final String SQL_HAS_QUOTA_USED = "SELECT COUNT(QLS.QUOTA_USED) " +
													"FROM BOMWEB_QUOTA_LTS_STATUS QLS " +
													"WHERE QLS.PROGRAM_CD = :program_cd ";
	
	private static final String SQL_UPDATE_QUOTA_USED = "UPDATE BOMWEB_QUOTA_LTS_STATUS SET QUOTA_USED = QUOTA_USED + :num_of_quota_used, " +
															"LAST_UPD_BY = 'SB', "+
															"LAST_UPD_DATE = SYSDATE "+
															"WHERE PROGRAM_CD = :program_cd ";
	
	private static final String SQL_INSERT_QUOTA_USED = " INSERT INTO BOMWEB_QUOTA_LTS_STATUS " +
														" (PROGRAM_CD, QUOTA_USED, CREATED_BY, CREATED_DATE, LAST_UPD_BY, LAST_UPD_DATE) " + 
														" VALUES " + 
														" (:program_cd, :num_of_quota_used, 'SB', SYSDATE, 'SB', SYSDATE) ";
	
	
	public List<LtsQuotaDTO> getQuota(String programCd, String psef) throws DAOException{
		
		StringBuilder sb = new StringBuilder(SQL_GET_QUOTA);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		if(StringUtils.isNotBlank(programCd)){ 
			sb.append("   AND QLL.PROGRAM_CD = :program_cd ");
			paramSource.addValue("program_cd", programCd);
		}

		if(StringUtils.isNotBlank(psef)){ 
			sb.append("   AND QLL.PSEF_CD like :like_psef_cd " );
			paramSource.addValue("%like_psef_cd%", "%"+psef+"%");
		}
		
		ParameterizedRowMapper<LtsQuotaDTO> mapper = new ParameterizedRowMapper<LtsQuotaDTO>() {
			public LtsQuotaDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LtsQuotaDTO quota = new LtsQuotaDTO();
				quota.setProgramCd(rs.getString("PROGRAM_CD"));
				quota.setProgramDesc(rs.getString("PROGRAM_DESC"));
				quota.setPsefCd(rs.getString("PSEF_CD"));
				quota.setQuota(rs.getInt("QUOTA"));
				quota.setQuotaUsed(rs.getInt("QUOTA_USED"));
				quota.setCurrentQuotaUsed(1);
				return quota;
			}
		};

		try {
			return simpleJdbcTemplate.query(sb.toString(), mapper, paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("Exception caught in getQuota()\n - sql: " + sb.toString() + "\n - value:" + paramSource.getValues(), erdae);
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getQuota()\n - sql: " + sb.toString() + "\n - value:" + paramSource.getValues(), e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public boolean hasQuotaUsed(String programCd) throws DAOException{
		
		StringBuilder sb = new StringBuilder(SQL_HAS_QUOTA_USED);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		if(StringUtils.isNotBlank(programCd)){ 
			paramSource.addValue("program_cd", programCd);
		}

		try {
			return simpleJdbcTemplate.queryForInt(sb.toString(),paramSource) > 0;
		} catch (Exception e) {
			logger.error("Exception caught in hasQuotaUsed()\n - sql: " + sb.toString() + "\n - value:" + paramSource.getValues(), e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateQuotaUsed(String programCd) throws DAOException {
		updateQuotaUsed(programCd, 1);
	}
	
	public void updateQuotaUsed(String programCd, int num_of_quota_used) throws DAOException{		
		
		StringBuilder sb = new StringBuilder(SQL_UPDATE_QUOTA_USED);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("num_of_quota_used", num_of_quota_used);
		if(StringUtils.isNotBlank(programCd)){ 
			paramSource.addValue("program_cd", programCd);
		}
		
		
		
		try {
			simpleJdbcTemplate.update(sb.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updateQuotaUsed()\n - sql: " + sb.toString() + "\n - value:" + paramSource.getValues(), e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void insertQuotaUsed(String programCd) throws DAOException{	
		insertQuotaUsed(programCd, 1);
	}
	
    public void insertQuotaUsed(String programCd, int num_of_quota_used) throws DAOException{	
    	
		StringBuilder sb = new StringBuilder(SQL_INSERT_QUOTA_USED);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
    	
		if(StringUtils.isNotBlank(programCd)){ 
			paramSource.addValue("program_cd", programCd);
		}
		paramSource.addValue("num_of_quota_used", num_of_quota_used);
		
		try {
			simpleJdbcTemplate.update(sb.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in insertQuotaUsed()\n - sql: " + sb.toString() + "\n - value:" + paramSource.getValues(), e);
			throw new DAOException(e.getMessage(), e);
		}
    }
    
}
