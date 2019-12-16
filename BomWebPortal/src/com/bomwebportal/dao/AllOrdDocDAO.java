package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllOrdDocDTO;
import com.bomwebportal.dto.AllOrdDocTempDTO;
import com.bomwebportal.dto.SignCaptureAllOrdDocDTO;
import com.bomwebportal.exception.DAOException;

public class AllOrdDocDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<AllOrdDocTempDTO> getAllOrdDocDTOALL(String orderId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getAllOrdDocDTOALL @ AllOrdDocDAO is called");
		}
		List<AllOrdDocTempDTO> list = Collections.emptyList();
		String sql = "SELECT" +
				" ORDER_ID" +
				" , DOC_TYPE" +
				" , SEQ_NUM" +
				" , FILE_PATH_NAME" +
				" , PROCESS_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_all_ord_doc" +
				" WHERE ORDER_ID = :orderId" +
				" ORDER BY DOC_TYPE, SEQ_NUM";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("getAllOrdDocDTOALL() @ AllOrdDocDAO: " + sql);
			}
			list = simpleJdbcTemplate.query(sql, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getAllOrdDocDTOALL()");
			}
			list = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllOrdDocDTOALL(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
	
	public List<AllOrdDocTempDTO> getAllOrdDocDTOALL(String orderId, DocType docType) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getAllOrdDocDTOALL @ AllOrdDocDAO is called");
		}
		List<AllOrdDocTempDTO> list = Collections.emptyList();
		String sql = "SELECT" +
				" ORDER_ID" +
				" , DOC_TYPE" +
				" , SEQ_NUM" +
				" , FILE_PATH_NAME" +
				" , PROCESS_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_all_ord_doc" +
				" WHERE ORDER_ID = :orderId" +
				" AND DOC_TYPE = :docType" +
				" ORDER BY SEQ_NUM";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("docType", docType == null ? null : docType.toString());
			if (logger.isInfoEnabled()) {
				logger.info("getAllOrdDocDTOALL() @ AllOrdDocDAO: " + sql);
			}
			list = simpleJdbcTemplate.query(sql, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getAllOrdDocDTOALL()");
			}
			list = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllOrdDocDTOALL(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
	
	public AllOrdDocTempDTO getAllOrdDocDTO(String rowId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getAllOrdDocDTO @ AllOrdDocDAO is called");
		}
		List<AllOrdDocTempDTO> list = Collections.emptyList();
		String sql = "SELECT" +
				" ORDER_ID" +
				" , DOC_TYPE" +
				" , SEQ_NUM" +
				" , FILE_PATH_NAME" +
				" , PROCESS_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_all_ord_doc" +
				" WHERE rowid = :rowId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("rowId", rowId);
			if (logger.isInfoEnabled()) {
				logger.info("getAllOrdDocDTO() @ AllOrdDocDAO: " + sql);
			}
			list = simpleJdbcTemplate.query(sql, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getAllOrdDocDTO()");
			}
			list = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllOrdDocDTO(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	private ParameterizedRowMapper<AllOrdDocTempDTO> getRowMapper() {
		return new ParameterizedRowMapper<AllOrdDocTempDTO>() {
			public AllOrdDocTempDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllOrdDocTempDTO dto = new AllOrdDocTempDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setDocType(rs.getString("DOC_TYPE"));
				dto.setFilePathName(rs.getString("FILE_PATH_NAME"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				return dto;
			}
		};
	}
	
	public void deleteAllOrderDocTempDTO(String orderId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("deleteAllOrderDocTempDTO @ AllOrdDocDAO is called");
		}
		String sql = "delete from bomweb_all_ord_doc_temp where order_id = :orderId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("[" + orderId + "] deleteAllOrderDocTempDTO() @ AllOrdDocDAO: " + sql);
			}
			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("[" + orderId + "] Exception caught in deleteAllOrderDocTempDTO(): " + e.getStackTrace());
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param dto
	 * @return
	 * @throws DAOException
	 */
	public void insertAllOrderDocTempDTO(AllOrdDocDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertAllOrderDocTempDTO @ AllOrdDocDAO is called");
		}
		String sql = "INSERT INTO bomweb_all_ord_doc_temp" +
				" (" +
				" ORDER_ID" +
				" , DOC_TYPE" +
				" , FILE_PATH_NAME" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" ) VALUES (" +
				" :orderId" +
				" , :docType" +
				" , :filePathName" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" )";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("docType", dto.getDocTypeMob());
			params.addValue("filePathName", dto.getFilePathName());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			if (logger.isInfoEnabled()) {
				logger.info("[" + dto.getOrderId() + "] insertAllOrderDocTempDTO() @ AllOrdDocDAO: " + sql);
				logger.info("[" + dto.getOrderId() + "] DocType = " + dto.getDocTypeMob());
				logger.info("[" + dto.getOrderId() + "] FilePathName = " + dto.getFilePathName());
			}
			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("[" + dto.getOrderId() + "] Exception caught in insertAllOrderDocTempDTO(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<SignCaptureAllOrdDocDTO> getSignCaptureAllOrdDocDTORowMapper() {
		return new ParameterizedRowMapper<SignCaptureAllOrdDocDTO>() {
			public SignCaptureAllOrdDocDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				SignCaptureAllOrdDocDTO dto = new SignCaptureAllOrdDocDTO();
				if (rs == null) {
					return null;
				}
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setUrl(rs.getString("FILE_PATH_NAME"));
				dto.setDocName(rs.getString("DOC_NAME"));
				dto.setDocType(rs.getString("DOC_TYPE"));
				return dto;
			}
		};
	}
	
	public List<SignCaptureAllOrdDocDTO> getSignCaptureAllOrdDocDTO(String orderId, Date appDate) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getSignCaptureAllOrdDocDTO @ AllOrdDocDAO is called");
		}
		List<SignCaptureAllOrdDocDTO> list = Collections.emptyList();
		String sql = "SELECT" +
				" doctemp.order_id," +
				" doctemp.file_path_name," +
				" doc.doc_name," +
				" doctemp.doc_type" +
				" FROM" +
				" (select order_id, file_path_name, doc_type from bomweb_all_ord_doc_temp where order_id = :orderId) doctemp" +
				" LEFT JOIN bomweb_all_doc doc" +
				" ON doc.lob = 'MOB'" +
				" and doc.type = 'O'" +
				" and trunc(:appDate) between trunc(nvl(doc.start_date, sysdate)) and trunc(nvl(doc.end_date, sysdate))" +
				" and doctemp.doc_type = doc.doc_type" +
				" order by doc.doc_type";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("appDate", appDate);
			if (logger.isInfoEnabled()) {
				logger.info("getSignCaptureAllOrdDocDTO() @ AllOrdDocDAO: " + sql);
			}
			list = simpleJdbcTemplate.query(sql, this.getSignCaptureAllOrdDocDTORowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getSignCaptureAllOrdDocDTO()");
			}
			list = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getSignCaptureAllOrdDocDTO(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
}