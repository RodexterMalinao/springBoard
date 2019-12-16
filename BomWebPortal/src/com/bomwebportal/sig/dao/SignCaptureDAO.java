package com.bomwebportal.sig.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.sig.dto.SignCaptureDTO;

public class SignCaptureDAO extends BaseDAO {
 
	public int createSignCaptureReq(SignCaptureDTO dto) throws DAOException {
		String sql = "INSERT " +
				"INTO BOMWEB_SIGN_REQ " +
				"  ( " +
				"    req_id, " +
				"    order_id, " +
				"    remark, " +
				"    signature, " +
				"    create_by, " +
				"    create_date, " +
				"    last_upd_by, " +
				"    last_upd_date " +
				"  ) " +
				"  VALUES " +
				"  ( " +
				"    :reqId, " +
				"    :orderId, " +
				"    :remark, " +
				"    :signature, " +
				"    :createBy, " +
				"    sysdate, " +
				"    :lastUpdBy, " +
				"    sysdate " +
				"  )";
		
		try {
			logger.info("Create Parms");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("reqId", dto.getReqId());
			params.addValue("orderId", dto.getOrderId());
			params.addValue("remark", dto.getRemark());
			params.addValue("signature", dto.getSignature());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("createDate", dto.getCreateDate());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("lastUpdDate", dto.getLastUpdDate());


			logger.info("createSignCaptureReq() @ SignCaptureDAO: "
					+ sql);
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in createSignCaptureReq()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public SignCaptureDTO getSignCaptureDTO(String reqId) throws DAOException {
		return getSignCaptureDTO(reqId, null);
	}
	
	public SignCaptureDTO getSignCaptureDTO(String reqId, String orderId) throws DAOException {
		String sql = "SELECT req_id, " +
				"  order_id , " +
				"  remark , " +
				"  signature, " +
				"  create_by, " +
				"  create_date , " +
				"  last_upd_by, " +
				"  last_upd_date " +
				"FROM BOMWEB_SIGN_REQ " +
				"WHERE req_id = :reqId" +
				(StringUtils.isNotBlank(orderId)? (" and order_id = '" + orderId + "'") : "");
		
		try {
			logger.debug("getSignCaptureDTO() @ SignCaptrueDAO: " + sql);
			
			ParameterizedRowMapper<SignCaptureDTO> mapper = new ParameterizedRowMapper<SignCaptureDTO>() {
				public SignCaptureDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					SignCaptureDTO dto = new SignCaptureDTO();
					
					dto.setReqId(rs.getString("req_id"));
					dto.setOrderId(rs.getString("order_id"));
					dto.setRemark(rs.getString("remark"));
					dto.setSignature(rs.getString("signature"));
					dto.setCreateBy(rs.getString("create_by"));
					dto.setLastUpdBy(rs.getString("last_upd_by"));
					
					dto.setCreateDate(rs.getTimestamp("create_date"));
					dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));
					
					return dto;
				}
			};
			
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("reqId", reqId);
			List<SignCaptureDTO> list = this.simpleJdbcTemplate.query(sql,mapper, params);
			
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getSignCaptureDTO()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getSignCaptureDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public int updateSignCaptureReq(SignCaptureDTO dto, int timeout) throws DAOException {
		String sql = "UPDATE BOMWEB_SIGN_REQ " +
				"SET req_id = :reqId, " +
				"  order_id = :orderId, " +
				"  remark = :remark, " +
				"  signature     = :signature, " +
				"  create_by     = :createBy, " +
				"  create_date   = :createDate, " +
				"  last_upd_by   = :lastUpdBy, " +
				"  last_upd_date = sysdate " +
				"WHERE req_id    = :reqId and last_upd_date  + :timeout/(24*60) >= sysdate ";
		
		try {
			logger.debug("updateSignCaptureReq() @ SignCaptureDAO: "
					+ sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("reqId", dto.getReqId());
			params.addValue("orderId", dto.getOrderId());
			params.addValue("remark", dto.getRemark());
			params.addValue("signature", dto.getSignature());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("createDate", dto.getCreateDate());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("timeout", timeout);
			return simpleJdbcTemplate.update(sql,
					params);
		}
		catch (Exception e) {
			logger.error("Exception caught in updateSignCaptureReq()", e);
			throw new DAOException(e.getMessage(), e);
		}		
		
	}
	
	public void deleteSignCaptureReq(String reqId) throws DAOException {
		String sql = "DELETE BOMWEB_SIGN_REQ " +
				"WHERE req_id    = :reqId";
		
		try {
			logger.debug("deleteSignCaptureReq() @ SignCaptrueDAO: "
					+ sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("reqId", reqId);


			simpleJdbcTemplate.update(sql,
					params);
		} catch (Exception e) {
			logger.error("Exception caught in deleteSignCaptureReq()", e);
			throw new DAOException(e.getMessage(), e);
		}		
		
	}
	
}
