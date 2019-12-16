package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class OrderStatusDAO extends BaseDAO {

	private static final String SQL_INITIALIZE_STATUS = 
		"insert into bomweb_order_latest_status " +
		"(order_id, dtl_id, last_hist_date, sb_status, last_upd_by, last_upd_date) " + 
		"values (?,?,sysdate,?,?,sysdate)"; 
	
	private static final String SQL_UPDATE_SB_STATUS = 
		"update BOMWEB_ORDER_LATEST_STATUS " +
		"set last_hist_date = sysdate, sb_status = ?, rea_cd = ?, last_upd_by = ?, wq_type = ?, last_upd_date = sysdate " +
		"where order_id = ? and dtl_id = ?";
	
	private static final String SQL_GET_SB_STATUS = 
		"select dtl_id, sb_status, rea_cd, bom_status, wq_type, to_char(bom_issue_date, 'dd/mm/yyyy') as bom_issue_date, to_char(last_upd_date, 'dd/mm/yyyy') as status_date, last_upd_by, legacy_status " +
		"from bomweb_order_latest_status " +
		"where order_id = ?";
	
	private static final String SQL_GET_STATUS_HIST = 
			"select dtl_id, sb_status, rea_cd, bom_status, wq_type, to_char(bom_issue_date, 'dd/mm/yyyy hh24:mi') as bom_issue_date, to_char(last_upd_date, 'dd/mm/yyyy hh24:mi') as status_date, last_upd_by, legacy_status " +
			"from bomweb_order_status_hist " +
			"where order_id = ? and dtl_id = ? " + 
			"order by status_date desc";
	
	private static final String SQL_GET_NUM_OF_REA_CD = 
			"SELECT COUNT(*) " +
			"FROM BOMWEB_ORDER_STATUS_HIST " +
			"WHERE ORDER_ID = :orderId " +
			"AND SB_STATUS = :statusCd " +
			"AND REA_CD IS NOT NULL ";
	
	private static final String SQL_GET_SIGNOFF_DATE = "select to_char(SIGN_OFF_DATE, 'dd/mm/yyyy hh24:mi') as SIGN_OFF_DATE from BOMWEB_ORDER where ORDER_ID = ?";
	
	private static final String SQL_GET_PREPAYMENT_SETTLE_DATE = "select to_char(LAST_UPD_DATE, 'dd/mm/yyyy hh24:mi') as PREPAY_SETTLE_DATE from BOMWEB_ORDER_LTS_PREPAY where PAYMENT_STATUS = 'S' and ORDER_ID = ?";
	
	public int getNumOfReasonCd(String orderId, String dtlId, String statusCd) throws DAOException{
		
		StringBuilder sb = new StringBuilder(SQL_GET_NUM_OF_REA_CD);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("orderId", orderId);
		paramSource.addValue("statusCd", statusCd);
		if(StringUtils.isNotBlank(dtlId)){ 
			sb.append("   AND DTL_ID= :dtlId ");
			paramSource.addValue("dtlId", dtlId);
		}

		try {
			return simpleJdbcTemplate.queryForInt(sb.toString(),paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in getNumOfReasonCd()\n - sql: " + sb.toString() + "\n - value:" + paramSource.getValues(), e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void insertStatus(String pOrderId, String pDtlId, String pUser) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_INITIALIZE_STATUS, pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_INITIAL, pUser);
		} catch (Exception e) {
			logger.error("Error in initializeStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateSbStatus(String pOrderId, String pDtlId, String pSbStatus, String pReaCd, String pUser, String pWqType) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_SB_STATUS, pSbStatus, pReaCd, pUser, pWqType, pOrderId, pDtlId);
		} catch (Exception e) {
			logger.error("Error in updateSbStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public OrderStatusDTO[] getStatus(String pOrderId) throws DAOException {

		try {
			return (OrderStatusDTO[])simpleJdbcTemplate.query(SQL_GET_SB_STATUS, this.getStatusParaMapper(), pOrderId).toArray(new OrderStatusDTO[0]);
		} catch (Exception e) {
			logger.error("Error in getStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public OrderStatusDTO[] getStatusHistory(String pOrderId, String pDtlId) throws DAOException {

		try {
			return (OrderStatusDTO[])simpleJdbcTemplate.query(SQL_GET_STATUS_HIST, this.getStatusParaMapper(), pOrderId, pDtlId).toArray(new OrderStatusDTO[0]);
		} catch (Exception e) {
			logger.error("Error in getStatusHistory()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<OrderStatusDTO> getStatusParaMapper() {
		return new ParameterizedRowMapper<OrderStatusDTO>() {
			public OrderStatusDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderStatusDTO status = new OrderStatusDTO();
				status.setBomIssueDate(rs.getString("BOM_ISSUE_DATE"));
				status.setBomStatus(rs.getString("BOM_STATUS"));
				status.setDtlId(rs.getString("DTL_ID"));
				status.setReasonCd(rs.getString("REA_CD"));
				status.setSbStatus(rs.getString("SB_STATUS"));
				status.setWqType(rs.getString("WQ_TYPE"));
				status.setStatusDate(rs.getString("STATUS_DATE"));
				status.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				status.setLegacyStatus(rs.getString("LEGACY_STATUS"));
				return status;
			}
		};
	}
	
	public String getSignoffDate(String pOrderId){
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_SIGNOFF_DATE, String.class, pOrderId);
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getPrepaymentSettledDate(String pOrderId){
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_PREPAYMENT_SETTLE_DATE, String.class, pOrderId);
		} catch (Exception e) {
			return null;
		}
	}
}
