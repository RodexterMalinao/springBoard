package com.activity.dao.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.activity.dto.ActivityStatusDTO;
import com.activity.util.ActivityConstants;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class ActivityStatusDAO extends BaseDAO {

	
	private static final String SQL_INITIALIZE_STATUS = 
		"insert into sb_actv_status " +
		"(actv_id, task_seq, status, last_upd_by, last_upd_date) " +
		"values (?,?,?,?,sysdate)";
		
	private static final String SQL_UPDATE_ACTV_STATUS = 
		"update sb_actv_status " +
		"set status = ?, last_upd_by = ?, last_upd_date = sysdate " +
		"where actv_id = ? and task_seq = ?";
		
	private static final String SQL_GET_ACTV_STATUS = 
		"select task_seq, status, null as status_seq, to_char(last_upd_date, 'yyyymmddhh24miss') as status_date " +
		"from sb_actv_status " +
		"where actv_id = ?";
		
	private static final String SQL_GET_STATUS_HIST = 
		"select task_seq, status, status_seq, to_char(last_upd_date, 'yyyymmddhh24miss') as status_date " +
		"from sb_actv_status_hist " +
		"where actv_id = ? and task_seq = ? " +
		"order by status_seq desc";
	
	
	public void insertStatus(String pActvId, String pTaskSeq, String pUser) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_INITIALIZE_STATUS, pActvId, pTaskSeq, ActivityConstants.ACTV_TASK_STATUS_INITIAL, pUser);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateActvStatus(String pActvId, String pTaskSeq, String pStatus, String pUser) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_ACTV_STATUS, pStatus, pUser, pActvId, pTaskSeq);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ActivityStatusDTO[] getLatestStatus(String pActvId) throws DAOException {
		try {
			return (ActivityStatusDTO[])simpleJdbcTemplate.query(SQL_GET_ACTV_STATUS, this.getStatusParmMapper(), pActvId).toArray(new ActivityStatusDTO[0]);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	public ActivityStatusDTO getLatestStatus(String pActvId, String pTaskSeq) throws DAOException {
		try {
			List<ActivityStatusDTO> actvStatusList = simpleJdbcTemplate.query(SQL_GET_ACTV_STATUS + " and task_seq = ?", this.getStatusParmMapper(), pActvId, pTaskSeq);
			if (actvStatusList.isEmpty()) {
				return null;
			}
			return actvStatusList.get(0);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ActivityStatusDTO[] getStatusHistory(String pActvId, String pTaskSeq) throws DAOException {

		try {
			return (ActivityStatusDTO[])simpleJdbcTemplate.query(SQL_GET_STATUS_HIST, this.getStatusParmMapper(), pActvId, pTaskSeq).toArray(new ActivityStatusDTO[0]);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<ActivityStatusDTO> getStatusParmMapper() {
		return new ParameterizedRowMapper<ActivityStatusDTO>() {
			public ActivityStatusDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ActivityStatusDTO status = new ActivityStatusDTO();
				status.setStatus(rs.getString("STATUS"));
				status.setStatusSeq(rs.getString("STATUS_SEQ"));
				status.setStatusDate(rs.getString("STATUS_DATE"));
				return status;
			}
		};
	}
}
