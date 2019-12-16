package com.bomwebportal.lts.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class LostModemDAO extends BaseDAO {
	
	private static final String SQL_INSERT_L2_JOB = 
			"INSERT INTO BOMWEB_ORDER_L2_JOB " +
			" (ORDER_ID, DTL_ID, L2_CD, FT_IND, L2_OTY, ACT_IND) " +
			"VALUES " +
			" (?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_GET_L2_JOB = 
			" select nvl(b.description, a.l2_cd) l2_job  " +
			" from bomweb_order_l2_job a, w_code_lkup b "+
			" where order_id = ? "  +
			" and b.GRP_ID (+) = 'EYE_IMS_L2_JOB' " + 
			" and b.code (+) = a.L2_CD "; 
	
	
	private static final String SQL_GET_LOST_MODEM_APPROVER = 
			" SELECT DISTINCT B.CREATE_BY AS STAFF_ID, C.STAFF_NAME AS STAFF_NAME " +
			" FROM Q_WQ_WP_SEARCH_ID_V A , Q_WQ_WP_ASSGN_STATUS_LOG B, BOMWEB_SALES_PROFILE C " +
			" WHERE A.WQ_WP_ASSGN_ID = B.WQ_WP_ASSGN_ID " +
			" AND B.CREATE_BY = C.STAFF_ID " +
			" AND WQ_NATURE_ID in ('133', '134') " + 
			" AND A.WQ_SUBTYPE = 'APPROVAL' " + 
			" AND A.TYPE_OF_SRV = 'IMS' " +
			" AND B.STATUS_CD = '050' " +
			" AND (C.END_DATE IS NULL OR C.END_DATE > SYSDATE) " + 
			" AND A.SB_ID = ? ";
	
	public List<String[]> getLostModemApproverName(String pOrderId) throws DAOException {
		List<String[]> approverList = null;
		
		try {
			approverList = this.simpleJdbcTemplate.query(SQL_GET_LOST_MODEM_APPROVER, this.getWqApproverMapper(), pOrderId);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getLostModemApproverName() OrderId: " + pOrderId, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return approverList;
	}

	private ParameterizedRowMapper<String[]> getWqApproverMapper() {
		return new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new String[]{rs.getString("STAFF_ID"), rs.getString("STAFF_NAME")};
			}
		};
	}
	
	public void insertL2Cd(String pOrderId, String pDtlId, String pL2Cd, String pFTInd, String pL2Qty, String pActInd) throws DAOException {
		try {
			this.simpleJdbcTemplate.update(SQL_INSERT_L2_JOB, pOrderId, pDtlId, pL2Cd, pFTInd, pL2Qty, pActInd);
		}catch (Exception e) {
			logger.error("Error in insertL2Cd() OrderId: " + pOrderId, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> getL2Cd(String pOrderId) throws DAOException {
		List<String> statusList = null;
		
		try {
			statusList = this.simpleJdbcTemplate.query(SQL_GET_L2_JOB, this.getStatusMapper(), pOrderId);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getL2Cd() OrderId: " + pOrderId, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return statusList;
	}

	private ParameterizedRowMapper<String> getStatusMapper() {
		return new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("L2_job");
			}
		};
	}
}
