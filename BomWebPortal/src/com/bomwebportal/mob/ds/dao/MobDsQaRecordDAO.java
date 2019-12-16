package com.bomwebportal.mob.ds.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ds.dto.MobDsQaRecordDTO;
import com.bomwebportal.util.BomWebPortalConstant;

public class MobDsQaRecordDAO extends BaseDAO {
	
	private static String getQARecordSQL = 
			"select order_id, seq_no, staff_id, must_q, qa_option, remark, create_date " +
			"from bomweb_qa " +
			"where order_id = ? " +
			"order by seq_no desc, create_date desc";
	
	public List<MobDsQaRecordDTO> getQARecord(String orderId) throws DAOException {
		logger.debug("getQARecord @ MobDsQaRecordDAO is called");
		List<MobDsQaRecordDTO> recordList = new ArrayList<MobDsQaRecordDTO>();

		ParameterizedRowMapper<MobDsQaRecordDTO> mapper = new ParameterizedRowMapper<MobDsQaRecordDTO>() {
		    public MobDsQaRecordDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {

		    MobDsQaRecordDTO tempDto = new MobDsQaRecordDTO();
			tempDto.setOrderId(rs.getString("order_id"));
			tempDto.setSeqNo(rs.getInt("seq_no"));
			tempDto.setStaffId(rs.getString("staff_id"));
			tempDto.setMustQ(rs.getString("must_q"));
			tempDto.setQaOption((rs.getInt("qa_option")));
			tempDto.setRemark(rs.getString("remark"));
			tempDto.setDateTime(rs.getTimestamp("create_date"));
			
			return tempDto;
		    }
		};

		try {
		    logger.debug("getQARecord() @ MobDsQaRecordDAO: "
			    + getQARecordSQL);

		    recordList = simpleJdbcTemplate.query(getQARecordSQL,
			    mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getQARecord()");

		    recordList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getQARecord():", e);

		    throw new DAOException(e.getMessage(), e);
		}
		return recordList;
	}
	
	private static String insertQARecordSQL = 
			"insert into bomweb_qa (order_id, seq_no, staff_id, must_q, qa_option, remark, " +
			"create_by, create_date, last_upd_by, last_upd_date) " +
			"values(:orderId, :seqNo, :username, :mustQ, :qaOption, :remark, " +
			":username, sysdate, :username, sysdate)";
	
	public int insertQARecord(String orderId, int seqNo,String mustQ, 
			int qaOption, String remark, String username) throws DAOException {

		logger.debug("insertQARecord @ MobDsQaRecordDAO is called");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("orderId", orderId);
		params.addValue("seqNo", seqNo);
		params.addValue("mustQ", mustQ);
		params.addValue("qaOption", qaOption);
		params.addValue("remark", remark);
		params.addValue("username", username);
		
		try {
		    logger.debug("insertQARecord() @ MobDsQaRecordDAO: "
			    + insertQARecordSQL);
		    
 		return simpleJdbcTemplate.update(insertQARecordSQL, params);
		} catch (Exception e) {
		    logger.info("Exception caught in insertQARecord():", e);

		    throw new DAOException(e.getMessage(), e);
		}
	}

	private static String updateOrderApproveIndSQL = 
			"UPDATE bomweb_order SET order_app_ind = 'Y', " +
			"last_update_date = sysdate, " +
			"last_update_by = ? " +
			"WHERE order_id = ?";
	
	public int updateOrderApproveInd(String orderId, String username) throws DAOException {

		logger.debug("updateOrderApproveInd @ MobDsQaRecordDAO is called");
		
		try {
		    logger.debug("updateOrderApproveInd() @ MobDsQaRecordDAO: "
			    + updateOrderApproveIndSQL);

		    return simpleJdbcTemplate.update(updateOrderApproveIndSQL, username, orderId);
		} catch (Exception e) {
		    logger.info("Exception caught in updateOrderApproveInd():", e);

		    throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static String isSupervisorApprovedSQL = 
			"SELECT super_app_ind FROM bomweb_order " +
			"WHERE order_id = ? ";
	
	public boolean isSupervisorApproved(String orderId) throws DAOException {

		logger.debug("isSupervisorApproved() @ MobDsQaRecordDAO is called");
		
		List<String> resultList = new ArrayList<String>();
		
		try {
		    logger.debug("isSupervisorApproved() @ MobDsQaRecordDAO: "
			    + isSupervisorApprovedSQL);

		    ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			    public String mapRow(ResultSet rs, int rowNum)
				    throws SQLException {

			    String result = rs.getString("super_app_ind");
				
				return result;
				
			    }
			};
		    
			resultList = simpleJdbcTemplate.query(isSupervisorApprovedSQL, mapper, orderId);
			
			if (resultList == null || resultList.isEmpty()) {
				return false;
			}
			
		    return "Y".equalsIgnoreCase(resultList.get(0));
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in isSupervisorApproved()");

		    resultList = null;
		    
		    return false;
		} catch (Exception e) {
		    logger.info("Exception caught in isSupervisorApproved():", e);

		    throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static String updateQaOrderStatusSQL = 
			"UPDATE bomweb_order SET order_status = prev_order_status, " +
			"prev_order_status = null, " +
			"last_update_date = sysdate, " +
			"last_update_by = ? " +
			"WHERE order_id = ?";
	
	public int updateQaOrderStatus(String orderId, String status, String username) throws DAOException {

		logger.debug("updateQaOrderStatus @ MobDsQaRecordDAO is called");
		
		try {
		    logger.debug("updateQaOrderStatus() @ MobDsQaRecordDAO: "
			    + updateQaOrderStatusSQL);

		    return simpleJdbcTemplate.update(updateQaOrderStatusSQL, username, orderId);
		} catch (Exception e) {
		    logger.info("Exception caught in updateQaOrderStatus():", e);

		    throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static String setMustQStatusSQL = 
			"UPDATE bomweb_order SET order_status = '" + BomWebPortalConstant.BWP_ORDER_QAPROCESS + "', " +
			"prev_order_status = order_status, " +
			"last_update_date = sysdate, " +
			"last_update_by = ? " +
			"WHERE order_id = ?";
	
	public int setMustQStatus(String orderId, String username) throws DAOException {

		logger.debug("setMustQStatus @ MobDsQaRecordDAO is called");
		
		try {
		    logger.debug("setMustQStatus() @ MobDsQaRecordDAO: "
			    + setMustQStatusSQL);

		    return simpleJdbcTemplate.update(setMustQStatusSQL, username, orderId);
		} catch (Exception e) {
		    logger.info("Exception caught in setMustQStatus():", e);

		    throw new DAOException(e.getMessage(), e);
		}
	}
}
