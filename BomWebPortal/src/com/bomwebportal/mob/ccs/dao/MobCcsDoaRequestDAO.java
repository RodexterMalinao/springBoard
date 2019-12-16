package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.DoaRequestDTO;
import com.bomwebportal.util.BomWebPortalConstant;

public class MobCcsDoaRequestDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public int autoDoaDeassign(String orderId) throws DAOException {
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("pkg_stock_assign")
					.withProcedureName("auto_doa_deassign");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_order_id", Types.VARCHAR)
										, new SqlOutParameter("gnRetVal", Types.INTEGER)
										, new SqlOutParameter("gnErrCode", Types.INTEGER)
										, new SqlOutParameter("gsErrText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			
			int gnRetVal = BomWebPortalConstant.ERRCODE_FAIL;
			int gnErrCode = BomWebPortalConstant.ERRCODE_FAIL;
			if (((Integer) out.get("gnRetVal")) != null) {
				gnRetVal = ((Integer) out.get("gnRetVal")).intValue();
			}
			if (((Integer) out.get("gnErrCode")) != null) {
				gnErrCode = ((Integer) out.get("gnErrCode")).intValue();
			}
			String gsErrText = (String) out.get("gsErrText");

			if (logger.isInfoEnabled()) {
				logger.info("pkg_stock_assign.auto_doa_deassign() output gnRetVal = " + gnRetVal);
				logger.info("pkg_stock_assign.auto_doa_deassign() output gnErrCode = " + gnErrCode);
				logger.info("pkg_stock_assign.auto_doa_deassign() output gsErrText = " + gsErrText);
			}

			return gnRetVal;
		} catch (Exception e) {
			logger.error("Exception caught in autoDoaDeassign()", e);
			throw new DAOException(e.getMessage(), e);
		}
    }

	public DoaRequestDTO getInitDoaRequestDTO(String orderId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getInitDoaRequestDTO is called");
		}
		final String getInitDoaRequestDTO = "SELECT" +
				" o.ORDER_ID" +
				" , 0 SEQ_NO" +
				" , null DOA_TYPE" +
				" , null STATUS" +
				" , c.TITLE || ' ' || c.LAST_NAME || ' ' || c.FIRST_NAME CONTACT_NAME" +
				" , o.MSISDN" +
				" , d.ACTUAL_DELIVERY_DATE DELIVERY_DATE" +
				" , sysdate REQUEST_DATE" +
				" , null REMARKS" +
				" , null AUTHORIZED_ID" +
				" , null MKT_SERIAL_ID" +
				" , null CREATE_BY" +
				" , null CREATE_DATE" +
				" , null LAST_UPD_BY" +
				" , null LAST_UPD_DATE" +
				" , null ROW_ID" +
				" FROM bomweb_order o" +
				" LEFT JOIN bomweb_delivery d ON (o.ORDER_ID = d.ORDER_ID)" +
				" LEFT JOIN bomweb_customer c ON (o.ORDER_ID = c.ORDER_ID)" +
				" WHERE o.ORDER_ID = :orderId" +
				" AND o.LOB = 'MOB'";
		List<DoaRequestDTO> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getInitDoaRequestDTO() @ MobCcsDoaRequestDAO: " + getInitDoaRequestDTO);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			itemList = simpleJdbcTemplate.query(getInitDoaRequestDTO, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getInitDoaRequestDTO()");
			}
			itemList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getInitDoaRequestDTO():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}

	public List<DoaRequestDTO> getDoaRequestDTOALLByOrderId(String orderId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getDoaRequestDTOALLByOrderId is called");
		}
		final String getDoaRequestDTOALLByOrderIdSQL = "SELECT" +
				" ORDER_ID" +
				" , SEQ_NO" +
				" , DOA_TYPE" +
				" , STATUS" +
				" , CONTACT_NAME" +
				" , MSISDN" +
				" , DELIVERY_DATE" +
				" , REQUEST_DATE" +
				" , REMARKS" +
				" , AUTHORIZED_ID" +
				" , MKT_SERIAL_ID" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_doa_request dr" +
				" WHERE ORDER_ID = :orderId" +
				" ORDER BY SEQ_NO";
		List<DoaRequestDTO> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getDoaRequestDTOALLByOrderId() @ MobCcsDoaRequestDAO: " + getDoaRequestDTOALLByOrderIdSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			itemList = simpleJdbcTemplate.query(getDoaRequestDTOALLByOrderIdSQL, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getDoaRequestDTOALLByOrderId()");
			}
			itemList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getDoaRequestDTOALLByOrderId():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public List<DoaRequestDTO> getDoaRequestDTOALLByOrderIdAndDoaType(String orderId, String doaType) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getDoaRequestDTOALLByOrderIdAndDoaType is called");
		}
		final String sql = "SELECT" +
				" ORDER_ID" +
				" , SEQ_NO" +
				" , DOA_TYPE" +
				" , STATUS" +
				" , CONTACT_NAME" +
				" , MSISDN" +
				" , DELIVERY_DATE" +
				" , REQUEST_DATE" +
				" , REMARKS" +
				" , AUTHORIZED_ID" +
				" , MKT_SERIAL_ID" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_doa_request dr" +
				" WHERE ORDER_ID = :orderId" +
				" AND DOA_TYPE = :doaType" +
				" ORDER BY SEQ_NO";
		List<DoaRequestDTO> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getDoaRequestDTOALLByOrderIdAndDoaType() @ MobCcsDoaRequestDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("doaType", doaType);
			itemList = simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getDoaRequestDTOALLByOrderIdAndDoaType()");
			}
			itemList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getDoaRequestDTOALLByOrderIdAndDoaType():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public DoaRequestDTO getDoaRequestDTO(String rowId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getDoaRequestDTO is called");
		}
		final String getDoaRequestDTOSQL = "SELECT" +
				" ORDER_ID" +
				" , SEQ_NO" +
				" , DOA_TYPE" +
				" , STATUS" +
				" , CONTACT_NAME" +
				" , MSISDN" +
				" , DELIVERY_DATE" +
				" , REQUEST_DATE" +
				" , REMARKS" +
				" , AUTHORIZED_ID" +
				" , MKT_SERIAL_ID" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_doa_request dr" +
				" WHERE rowid = :rowId";
		List<DoaRequestDTO> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getDoaRequestDTO() @ MobCcsDoaRequestDAO: " + getDoaRequestDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("rowId", rowId);
			itemList = simpleJdbcTemplate.query(getDoaRequestDTOSQL, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getDoaRequestDTO()");
			}
			itemList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getDoaRequestDTO():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}

	public DoaRequestDTO getDoaRequestDTOByOrderIdAndSeqNo(String orderId, int seqNo) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getDoaRequestDTOByOrderIdAndSeqNo is called");
		}
		final String getDoaRequestDTOByOrderIdAndSeqNoSQL = "SELECT" +
				" ORDER_ID" +
				" , SEQ_NO" +
				" , DOA_TYPE" +
				" , STATUS" +
				" , CONTACT_NAME" +
				" , MSISDN" +
				" , DELIVERY_DATE" +
				" , REQUEST_DATE" +
				" , REMARKS" +
				" , AUTHORIZED_ID" +
				" , MKT_SERIAL_ID" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_doa_request dr" +
				" WHERE ORDER_ID = :orderId" +
				" AND SEQ_NO = :seqNo";
		
		List<DoaRequestDTO> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getDoaRequestDTOByOrderIdAndSeqNo() @ MobCcsDoaRequestDAO: " + getDoaRequestDTOByOrderIdAndSeqNoSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			itemList = simpleJdbcTemplate.query(getDoaRequestDTOByOrderIdAndSeqNoSQL, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getDoaRequestDTOByOrderIdAndSeqNo()");
			}
			itemList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getDoaRequestDTOByOrderIdAndSeqNo():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}
	
	public int getLastSeqNo(String orderId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getLastSeqNo is called");
		}
		final String sql = "SELECT NVL(MAX(SEQ_NO), 0)" +
				" FROM bomweb_doa_request" +
				" WHERE ORDER_ID = :orderId";
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getLastSeqNo() @ MobCcsDoaRequestDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			return simpleJdbcTemplate.queryForInt(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getLastSeqNo():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<DoaRequestDTO> getRowMapper() {
		return new ParameterizedRowMapper<DoaRequestDTO>() {
			public DoaRequestDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				DoaRequestDTO dto = new DoaRequestDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setSeqNo(rs.getInt("SEQ_NO"));
				dto.setDoaType(rs.getString("DOA_TYPE"));
				dto.setStatus(rs.getString("STATUS"));
				dto.setContactName(rs.getString("CONTACT_NAME"));
				dto.setMsisdn(rs.getString("MSISDN"));
				dto.setDeliveryDate(rs.getDate("DELIVERY_DATE"));
				// require time part in REQUEST_DATE
				dto.setRequestDate(rs.getTimestamp("REQUEST_DATE"));
				dto.setRemarks(rs.getString("REMARKS"));
				dto.setAuthorizedId(rs.getString("AUTHORIZED_ID"));
				dto.setMktSerialId(rs.getString("MKT_SERIAL_ID"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setRowId(rs.getString("ROW_ID"));
				return dto;
			}
		};
	}

	public int insertDoaRequestDTO(DoaRequestDTO dto) throws DAOException {
		logger.info("insertDoaRequestDTO() is called");
		final String insertDoaRequestDTOSQL = "INSERT INTO bomweb_doa_request" +
				"(" +
				" ORDER_ID" +
				" , SEQ_NO" +
				" , DOA_TYPE" +
				" , STATUS" +
				" , CONTACT_NAME" +
				" , MSISDN" +
				" , DELIVERY_DATE" +
				" , REQUEST_DATE" +
				" , REMARKS" +
				" , AUTHORIZED_ID" +
				" , MKT_SERIAL_ID" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				") VALUES (" +
				" :orderId" +
				" , :seqNo" +
				" , :doaType" +
				" , :status" +
				" , :contactName" +
				" , :msisdn" +
				" , :deliveryDate" +
				" , :requestDate" +
				" , :remarks" +
				" , :authorizedId" +
				" , :mktSerialId" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				")";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("seqNo", dto.getSeqNo());
			params.addValue("doaType", dto.getDoaType());
			params.addValue("status", dto.getStatus());
			params.addValue("contactName", dto.getContactName());
			params.addValue("msisdn", dto.getMsisdn());
			params.addValue("deliveryDate", dto.getDeliveryDate());
			params.addValue("requestDate", dto.getRequestDate());
			params.addValue("remarks", dto.getRemarks());
			params.addValue("authorizedId", dto.getAuthorizedId());
			params.addValue("mktSerialId", dto.getMktSerialId());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			if (logger.isInfoEnabled()) {
				logger.info("insertDoaRequestDTO() @ MobCcsDoaRequestDAO: " + insertDoaRequestDTOSQL);
			}
			return this.simpleJdbcTemplate.update(insertDoaRequestDTOSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in insertDoaRequestDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int updateDoaRequestDTO(DoaRequestDTO dto) throws DAOException {
		logger.info("updateDoaRequestDTO() is called");
		final String updateDoaRequestDTOSQL = "UPDATE bomweb_doa_request SET" +
				" STATUS = :status" +
				" , REMARKS = :remarks" +
				" , AUTHORIZED_ID = :authorizedId" +
				" , MKT_SERIAL_ID = :mktSerialId" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = sysdate" +
				" WHERE rowid = :rowId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("status", dto.getStatus());
			params.addValue("remarks", dto.getRemarks());
			params.addValue("authorizedId", dto.getAuthorizedId());
			params.addValue("mktSerialId", dto.getMktSerialId());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("rowId", dto.getRowId());
			if (logger.isInfoEnabled()) {
				logger.info("updateDoaRequestDTO() @ MobCcsDoaRequestDAO: " + updateDoaRequestDTOSQL);
			}
			return this.simpleJdbcTemplate.update(updateDoaRequestDTOSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in updateDoaRequestDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int updateDoaRequestStatus(String rowId, String status) throws DAOException {
		logger.info("updateDoaRequestStatus() is called");
		final String updateDoaRequestStatusSQL = "UPDATE bomweb_doa_request SET" +
				" status = :status" +
				" WHERE rowid = :rowId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("status", status);
			params.addValue("rowId", rowId);
			if (logger.isInfoEnabled()) {
				logger.info("updateDoaRequestStatus() @ MobCcsDoaRequestDAO: " + updateDoaRequestStatusSQL);
			}
			return this.simpleJdbcTemplate.update(updateDoaRequestStatusSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in updateDoaRequestStatus():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int updateDoaRequestStatusFromOrder(String orderId, int seqNo) throws DAOException {
		logger.info("updateDoaRequestStatusFromOrder() is called");
		final String updateDoaRequestStatusFromOrderSQL = "UPDATE bomweb_doa_request SET" +
				" status = (SELECT reason_cd FROM bomweb_order WHERE order_id = :orderId)" +
				" WHERE order_id = :orderId" +
				" AND seq_no = :seqNo";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			if (logger.isInfoEnabled()) {
				logger.info("updateDoaRequestStatusFromOrder() @ MobCcsDoaRequestDAO: " + updateDoaRequestStatusFromOrderSQL);
			}
			return this.simpleJdbcTemplate.update(updateDoaRequestStatusFromOrderSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in updateDoaRequestStatusFromOrder():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int deleteDoaRequestBySeqNo(String orderId, int seqNo) throws DAOException {
		logger.info("deleteDoaRequestBySeqNo() is called");
		final String deleteDoaRequestBySeqNoSQL = "DELETE FROM bomweb_doa_request " +
				" WHERE ORDER_ID = :orderId" +
				" AND SEQ_NO = :seqNo";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			if (logger.isInfoEnabled()) {
				logger.info("deleteDoaRequestBySeqNo() @ MobCcsDoaRequestDAO: " + deleteDoaRequestBySeqNoSQL);
			}
			return this.simpleJdbcTemplate.update(deleteDoaRequestBySeqNoSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in deleteDoaRequestBySeqNo():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public Date getActualDeliveryDate(String orderId) throws DAOException {
		logger.info("getActualDeliveryDate() is called");
		List<Date> itemList = null;
		final String getActualDeliveryDateSQL = "SELECT" +
				" trunc(ACTUAL_DELIVERY_DATE) ACTUAL_DELIVERY_DATE" +
				" FROM bomweb_delivery" +
				" WHERE ORDER_ID = :orderId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("getActualDeliveryDate() @ MobCcsDoaRequestSelectedCdDAO: " + getActualDeliveryDateSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getActualDeliveryDateSQL, new ParameterizedRowMapper<Date>() {
				public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getDate("ACTUAL_DELIVERY_DATE");
				}
			}, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getActualDeliveryDate()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getActualDeliveryDate():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}
	
	public boolean isMktSerialIdExists(String mktSerialId) throws DAOException {
		logger.info("isApprovalSerialExists() is called");
		int count = 0;
		final String isApprovalSerialExistsSQL = "SELECT" +
				" count(*)" +
				" FROM bomweb_doa_request" +
				" WHERE MKT_SERIAL_ID = :mktSerialId";
		if (logger.isDebugEnabled()) {
			logger.debug("isApprovalSerialExists() @ MobCcsDoaRequestSelectedCdDAO: " + isApprovalSerialExistsSQL);
			logger.debug("mktSerialId: " + mktSerialId);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("mktSerialId", mktSerialId);
			count = this.simpleJdbcTemplate.queryForInt(isApprovalSerialExistsSQL, params);
			if (logger.isDebugEnabled()) {
				logger.debug("count: " + count);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in isApprovalSerialExists()");
		} catch (Exception e) {
			logger.info("Exception caught in isApprovalSerialExists():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return count > 0;
	}
	
	
	public int autoDoaDeassignSt(String orderId) throws DAOException {
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("pkg_stock_assign_st")
					.withProcedureName("auto_doa_deassign_st");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_order_id", Types.VARCHAR)
										, new SqlOutParameter("gnRetVal", Types.INTEGER)
										, new SqlOutParameter("gnErrCode", Types.INTEGER)
										, new SqlOutParameter("gsErrText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			
			int gnRetVal = BomWebPortalConstant.ERRCODE_FAIL;
			int gnErrCode = BomWebPortalConstant.ERRCODE_FAIL;
			if (((Integer) out.get("gnRetVal")) != null) {
				gnRetVal = ((Integer) out.get("gnRetVal")).intValue();
			}
			if (((Integer) out.get("gnErrCode")) != null) {
				gnErrCode = ((Integer) out.get("gnErrCode")).intValue();
			}
			String gsErrText = (String) out.get("gsErrText");

			if (logger.isInfoEnabled()) {
				logger.info("pkg_stock_assign_st.auto_doa_deassign_st() output gnRetVal = " + gnRetVal);
				logger.info("pkg_stock_assign_st.auto_doa_deassign_st() output gnErrCode = " + gnErrCode);
				logger.info("pkg_stock_assign_st.auto_doa_deassign_st() output gsErrText = " + gsErrText);
			}

			return gnRetVal;
		} catch (Exception e) {
			logger.error("Exception caught in autoDoaDeassignSt()", e);
			throw new DAOException(e.getMessage(), e);
		}
    }
	
	public String[] getRetDoaRequestDefectInd(String orderId, int seqNo) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getRetDoaRequestDefectInd is called");
		}
		final String getRetDoaRequestDefectIndSQL = "SELECT" +
				" req.HS_DEFECT_IND" +
				" , req.AC_DEFECT_IND" +
				" FROM bomweb_doa_request req" +
				" WHERE req.ORDER_ID = :orderId" +
				" AND req.seq_no = :seqNo"
				;
		List<String[]> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getInitDoaRequestDTO() @ MobCcsDoaRequestDAO: " + getRetDoaRequestDefectIndSQL);
			}
			
			ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
				public String[] mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					String[] result = new String[2];
					result[0] = rs.getString("HS_DEFECT_IND");
					result[1] = rs.getString("AC_DEFECT_IND");
					return result;
				}
			};
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			
			itemList = simpleJdbcTemplate.query(getRetDoaRequestDefectIndSQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getInitDoaRequestDTO()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getInitDoaRequestDTO():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? new String[] {"N", "N"} : itemList.get(0);
	}
	
	private String getDoaRequestHandsetDefectALLSQL = "SELECT" +
			" cd.CODE_ID" +
			" FROM bomweb_doa_request_selected_cd cd JOIN bomweb_code_lkup lkup" +
			" ON cd.code_id = lkup.code_id" +
			" WHERE ORDER_ID = :orderId" +
			" AND SEQ_NO = :seqNo" +
			" AND CODE_TYPE = :type" +
			" ORDER BY cd.CODE_ID";
	public List<String> getDoaRequestDefectALL(String orderId, int seqNo, String type) throws DAOException {
		logger.info("getDoaRequestHandsetDefectALL() is called");
		
		List<String> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			params.addValue("type", type);
			
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("CODE_ID");
				}
			};
			if (logger.isInfoEnabled()) {
				logger.info("getDoaRequestHandsetDefectALL() @ MobCcsDoaRequestSelectedCdDAO: " + getDoaRequestHandsetDefectALLSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getDoaRequestHandsetDefectALLSQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getDoaRequestHandsetDefectALL()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getDoaRequestHandsetDefectALL():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private String getDoaRequestAcDefectItemsSQL = 
			" SELECT cd.CODE_ID, lkup.CODE_DESC, item.AC_DEFECT_OTHERS" +
			" FROM bomweb_doa_request_selected_cd cd " +
			" JOIN bomweb_code_lkup lkup" +
			" ON cd.code_id = lkup.code_id" +
			" LEFT JOIN bomweb_doa_item item" +
			" ON cd.order_id = item.order_id" +
			" AND cd.seq_no = item.seq_no" +
			" AND cd.code_id = 'AD05'" +
			" WHERE cd.ORDER_ID = :orderId" +
			" AND cd.SEQ_NO = :seqNo" +
			" AND lkup.CODE_TYPE = :type" +
			" ORDER BY cd.CODE_ID";
	public List<String> getDoaRequestAcDefectItems(String orderId, int seqNo, String type) throws DAOException {
		logger.info("getDoaRequestAcDefectItems() is called");
		
		List<String> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			params.addValue("type", type);
			
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					String codeId = rs.getString("CODE_ID");
					if ("AD05".equalsIgnoreCase(codeId)) {
						return rs.getString("CODE_DESC") + " ("+rs.getString("AC_DEFECT_OTHERS")+")";
					}
					return rs.getString("CODE_DESC");
				}
			};
			if (logger.isInfoEnabled()) {
				logger.info("getDoaRequestAcDefectItems() @ MobCcsDoaRequestSelectedCdDAO: " + getDoaRequestAcDefectItemsSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getDoaRequestAcDefectItemsSQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getDoaRequestAcDefectItems()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getDoaRequestAcDefectItems():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
}
