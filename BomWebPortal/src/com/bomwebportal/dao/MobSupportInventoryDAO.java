package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;

/**
 * Retrieve SIM and MRT info from SB Inventory<br>
 * DEV, UAT - XHKARS97_BOSSUAT<br>
 * PRD - XHKARS98_SVC_BOSS1<br> 
 * Related table(s): RES_SIM (SIM), RES_NUMBER (MRT)
 */
public class MobSupportInventoryDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Retrieve SIM status from SB Inventory<br>
	 * DEV, UAT - XHKARS97_BOSSUAT
	 * PRD - XHKARS98_SVC_BOSS1
	 * @param String SIM no. (18-digit, trim in SQL)
	 * @return String SIM status
	 * @throws DAOException
	 */
	public String getSBsimStatus(String sim) throws DAOException{
		
		String sql = "SELECT ICCID, RES_STATUS_ID FROM RES_SIM WHERE ICCID = SUBSTR(:ICCID, 0, 18)";
		
		logger.info("getSBsimStatus is called");
				
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("RES_STATUS_ID");
			}
		};
		
		try {
			logger.debug("getSBsimStatus SQL: " + sql);
			List<String> resultList = simpleJdbcTemplate.query(sql, mapper, sim);
			logger.debug("getSBsimStatus SQL param SIM: " + sim);
			logger.debug("getSBsimStatus SQL result list: " + resultList);
			if (resultList == null || resultList.size() == 0) {
				return null;
			} else {
				return resultList.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getSBsimStatus EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getSBsimStatus Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * Update SIM status to SB Inventory<br>
	 * DEV, UAT - XHKARS97_BOSSUAT
	 * PRD - XHKARS98_SVC_BOSS1
	 * @param String SIM no. (18-digit, trim in SQL)
	 * @return no. of rows updated
	 * @throws DAOException
	 */
	public int updateSBsimStatus(String sim, String newSimStatus, String oldSimStatus) throws DAOException{
		
		String sql = "UPDATE RES_SIM " 
				+"SET RES_STATUS_ID = :NEW_RES_STATUS_ID " 
				+"WHERE ICCID       = SUBSTR(:ICCID, 0, 18) " 
				+"AND RES_STATUS_ID = :OLD_RES_STATUS_ID";
		
		logger.info("updateSBsimStatus is called");
				
		try {
			logger.debug("updateSBsimStatus SQL: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("NEW_RES_STATUS_ID", newSimStatus);
			params.addValue("ICCID", sim);
			params.addValue("OLD_RES_STATUS_ID", oldSimStatus);
			
			logger.debug("updateSBsimStatus SQL param NEW_SIM_STATUS: " + newSimStatus);
			logger.debug("updateSBsimStatus SQL param SIM: " + newSimStatus);
			logger.debug("updateSBsimStatus SQL param OLD_SIM_STATUS: " + oldSimStatus);
			
			int result = simpleJdbcTemplate.update(sql, params);
			
			logger.debug("updateSBsimStatus SQL result: " + result);
			
			return result;
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("updateSBsimStatus EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("updateSBsimStatus Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return 0;
	}
	
	/**
	 * Retrieve MRT status from SB Inventory<br>
	 * DEV, UAT - XHKARS97_BOSSUAT
	 * PRD - XHKARS98_SVC_BOSS1
	 * @param String MRT
	 * @return String MRT status
	 * @throws DAOException
	 */
	public String getSBmrtStatus(String mrt) throws DAOException{
		
		String sql = "SELECT RES_STATUS_ID FROM RES_NUMBER WHERE SRV_NUM = :SRV_NUM";
		
		logger.info("getSBmrtStatus is called");
				
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("RES_STATUS_ID");
			}
		};
		
		try {
			logger.debug("getSBmrtStatus SQL: " + sql);
			logger.debug("getSBmrtStatus SQL param MRT: " + mrt);
			List<String> resultList = simpleJdbcTemplate.query(sql, mapper, mrt);
			logger.debug("getSBmrtStatus SQL result list: " + resultList);
			if (resultList == null || resultList.size() == 0) {
				return null;
			} else {
				return resultList.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getSBmrtStatus EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getSBmrtStatus Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * Update MRT status to SB Inventory<br>
	 * DEV, UAT - XHKARS97_BOSSUAT
	 * PRD - XHKARS98_SVC_BOSS1
	 * @param String MRT
	 * @return no. of rows updated
	 * @throws DAOException
	 */
	public int updateSBmrtStatus(String mrt, String newMrtStatus, String oldMrtStatus) throws DAOException{
		
		String sql = "UPDATE RES_NUMBER " 
				+ "SET RES_STATUS_ID = :NEW_RES_STATUS_ID, " 
				+ "LAST_UPD_BY = 'PSBMPATCH', " 
				+ "LAST_UPD_DATE = SYSDATE " 
				+ "WHERE SRV_NUM = :SRV_NUM " 
				+ "AND RES_STATUS_ID = :OLD_RES_STATUS_ID";
		
		logger.info("updateSBmrtStatus is called");
				
		try {
			logger.debug("updateSBmrtStatus SQL: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("NEW_RES_STATUS_ID", newMrtStatus);
			params.addValue("SRV_NUM", mrt);
			params.addValue("OLD_RES_STATUS_ID", oldMrtStatus);
			
			logger.debug("updateSBmrtStatus SQL param NEW_MRT_STATUS: " + newMrtStatus);
			logger.debug("updateSBmrtStatus SQL param MRT: " + mrt);
			logger.debug("updateSBmrtStatus SQL param OLD_MRT_STATUS: " + oldMrtStatus);
			
			int result = simpleJdbcTemplate.update(sql, params);
			
			logger.debug("updateSBmrtStatus SQL result: " + result);
			
			return result;
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("updateSBmrtStatus EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("updateSBmrtStatus Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return 0;
	}
	
	public int freeSBmrtStatus(String mrt, String oldMrtStatus) throws DAOException{
		
		String sql = "UPDATE RES_NUMBER " 
				+ "SET RES_STATUS_ID = decode(trim(reserve_id), null, 2, '', 2, 18), " 
				+ "LAST_UPD_BY = 'PSBMPATCH', " 
				+ "LAST_UPD_DATE = SYSDATE " 
				+ "WHERE SRV_NUM = :SRV_NUM " 
				+ "AND RES_STATUS_ID = :OLD_RES_STATUS_ID";
		
		logger.info("updateSBmrtStatus is called");
				
		try {
			logger.debug("updateSBmrtStatus SQL: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("SRV_NUM", mrt);
			params.addValue("OLD_RES_STATUS_ID", oldMrtStatus);
			
			logger.debug("updateSBmrtStatus SQL param MRT: " + mrt);
			logger.debug("updateSBmrtStatus SQL param OLD_MRT_STATUS: " + oldMrtStatus);
			
			int result = simpleJdbcTemplate.update(sql, params);
			
			logger.debug("updateSBmrtStatus SQL result: " + result);
			
			return result;
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("updateSBmrtStatus EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("updateSBmrtStatus Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return 0;
	}
}
