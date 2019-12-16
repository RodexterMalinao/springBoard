package com.activity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.pccw.util.db.OracleSelectHelper;

public class ActivityDetailDAOImpl extends BaseDAO implements ActivityDetailDAO {

	private String SQL_GET_PENDING_ACTV_BY_ORDER = 
		"select a.actv_id " +
		"from sb_actv a, sb_actv_status s " +
		"where a.actv_id = s.actv_id and s.task_seq = 0 " +
		"and s.status not in ('C', 'L') " +
		"and a.actv_type = ? and a.order_id = ?";
	
	private String SQL_GET_ALL_ACTV_BY_ORDER = 
			"select a.actv_id " +
			"from sb_actv a " +
			"where a.actv_type = ? and a.order_id = ?";
	
	private String SQL_GET_ALL_COMPLETED_ACTV_BY_ORDER = 
			"select a.actv_id " +
			"from sb_actv a, sb_actv_status s " +
			"where a.actv_id = s.actv_id and s.task_seq = 0 " +
			"and s.status in ('L') " +
			"and a.actv_type = ? and a.order_id = ? order by s.last_upd_date desc";
				
	
	private String SQL_GET_ACTV_BY_ASSGN_ID = 
		"	SELECT ACTV_ID FROM SB_ACTV " +
		"	  WHERE ACTV_ID IN ( " +
		"	    SELECT ACTV_ID " +
		"	    FROM SB_ACTV_TASK" +
		"	    WHERE wq_wp_assgn_id = ? )";
	
	private String SQL_GET_RELATED_ASSGN_ID_BY_ASSGN_ID = 
		"SELECT WQ_WP_ASSGN_ID FROM Q_WQ_WP_SEARCH_ID_V WHERE (WQ_ID, WQ_BATCH_ID) IN " +
        "(SELECT WQ_ID, WQ_BATCH_ID FROM Q_WQ_WP_SEARCH_ID_V WHERE WQ_WP_ASSGN_ID = ?) ";
	
	private String SQL_GET_NEXT_RMK_SEQ = 
			" SELECT nvl(max(rmk_seq)+1, 1) FROM SB_ACTV_REMARK " +
			" WHERE ACTV_ID = ? AND TASK_SEQ = ? " +
			" AND STATUS_SEQ = ( SELECT MAX(STATUS_SEQ) " +
			                  " from sb_actv_status_hist " +
			                  " where actv_id = ? and task_seq = ?)";
	
	private String SQL_UPDATE_REMARK_WQ_WP_ASSGN_ID = 
			"update SB_ACTV_REMARK "+
			"set wq_wp_assgn_id = ? "+
			"where ACTV_ID = ? and TASK_SEQ = ? "+ 
			"and wq_wp_assgn_id is null ";
		
	public String[] getPendingActvByOrderId(String pOrderId, String pActvType) throws DAOException {
		
		try {
			return simpleJdbcTemplate.query(SQL_GET_PENDING_ACTV_BY_ORDER, 
				new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("ACTV_ID");
				}}, pActvType, pOrderId).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException("Fail to getPendingActvByOrderId\n" + e.getMessage(), e);
		}
	}
	
	public String[] getAllActvByOrderId(String pOrderId, String pActvType) throws DAOException {
		
		try {
			return simpleJdbcTemplate.query(SQL_GET_ALL_ACTV_BY_ORDER, 
				new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("ACTV_ID");
				}}, pActvType, pOrderId).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException("Fail to getAllActvByOrderId\n" + e.getMessage(), e);
		}
	}

	public String[] getAllCompletedActvByOrderId(String pOrderId, String pActvType) throws DAOException {

		try {
			return simpleJdbcTemplate.query(SQL_GET_ALL_COMPLETED_ACTV_BY_ORDER, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("ACTV_ID");
				}
			}, pActvType, pOrderId).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException("Fail to getAllActvByOrderId\n" + e.getMessage(), e);
		}
	}
	
	public String[] getActvByWqWpAssgnId(String pWqWpAssgnId) throws DAOException {
		
		try {
			return simpleJdbcTemplate.query(SQL_GET_ACTV_BY_ASSGN_ID, 
				new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("ACTV_ID");
				}}, pWqWpAssgnId).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException("Fail to getActvByAssgnId\n" + e.getMessage(), e);
		}
	}
	
    public String[] getRelatedAssgnIdFromSearchSourceByAssgnId(String pWqWpAssgnId) throws DAOException {
		
		try {
			return simpleJdbcTemplate.query(SQL_GET_RELATED_ASSGN_ID_BY_ASSGN_ID, 
				new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("wq_wp_assgn_id");
				}}, pWqWpAssgnId).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException("Fail to getRelatedAssgnIdFromSearchSourceByAssgnId\n" + e.getMessage(), e);
		}
	}
    
    public int getNextRemarkSeq(String pActvId, String pTaskSeq) throws DAOException {
		
		try {
			String result = OracleSelectHelper.getSqlFirstRowColumnString(
					this.jdbcTemplate.getDataSource(), 
					this.SQL_GET_NEXT_RMK_SEQ,
					new Object[] {pActvId, pTaskSeq, pActvId, pTaskSeq});
			if (StringUtils.isNotBlank(result)){
				return Integer.parseInt(result);
			}
			return 1;
		} catch (EmptyResultDataAccessException erdae) {
			return 1;
		} catch (Exception e) {
			throw new DAOException("Fail to getMaxRemarkSeq\n" + e.getMessage(), e);
		}
	}

	public void updateActvTaskWqWpAssgnId(String pActvId, String pTaskSeq, String pWqWpAssgnId) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_REMARK_WQ_WP_ASSGN_ID, pWqWpAssgnId, pActvId, pTaskSeq);
		} catch (Exception e) {
			throw new DAOException("Fail to updateActvTaskWqWpAssgnId\n" + e.getMessage(), e);
		}
	} 
}
