package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.CollectedInd;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.MarkDelInd;
import com.bomwebportal.exception.DAOException;

public class AllOrdDocAssgnDAO extends BaseDAO {
	public List<AllOrdDocAssgnDTO> getAllOrdDocAssgnDTOALL(String orderId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.debug("getAllOrdDocAssgnDTOALL @ AllOrdDocAssgnDAO is called");
		}
		List<AllOrdDocAssgnDTO> list = Collections.emptyList();
		String sql = "SELECT" +
				" a.ORDER_ID" +
				" , a.DOC_TYPE" +
				" , d.DOC_NAME" +
				" , a.WAIVE_REASON" +
				" , a.WAIVED_BY" +
				" , a.COLLECTED_IND" +
				" , a.MARK_DEL_IND" +
				" , a.CREATE_BY" +
				" , a.CREATE_DATE" +
				" , a.LAST_UPD_BY" +
				" , a.LAST_UPD_DATE" +
				" , a.rowid ROW_ID" +
				" FROM bomweb_all_ord_doc_assgn a" +
				" LEFT JOIN bomweb_order o ON (a.ORDER_ID = o.ORDER_ID)" +
				" LEFT JOIN bomweb_all_doc d ON (a.DOC_TYPE = d.DOC_TYPE AND d.LOB = o.LOB)" +
				" WHERE a.ORDER_ID = :orderId" +
				" ORDER BY a.DOC_TYPE";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.debug("getAllOrdDocAssgnDTOALL() @ AllOrdDocAssgnDAO: " + sql);
			}
			list = simpleJdbcTemplate.query(sql, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getAllOrdDocAssgnDTOALL()");
			}
			list = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllOrdDocAssgnDTOALL(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
	
	public List<AllOrdDocAssgnDTO> getInUseAllOrdDocAssgnDTOALL(String orderId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.debug("getAllInUseOrdDocAssgnDTOALL @ AllOrdDocAssgnDAO is called");
		}
		List<AllOrdDocAssgnDTO> list = Collections.emptyList();
		String sql = "SELECT" +
				" a.ORDER_ID" +
				" , a.DOC_TYPE" +
				" , d.DOC_NAME" +
				" , a.WAIVE_REASON" +
				" , a.WAIVED_BY" +
				" , a.COLLECTED_IND" +
				" , a.MARK_DEL_IND" +
				" , a.CREATE_BY" +
				" , a.CREATE_DATE" +
				" , a.LAST_UPD_BY" +
				" , a.LAST_UPD_DATE" +
				" , a.rowid ROW_ID" +
				" FROM bomweb_all_ord_doc_assgn a" +
				" LEFT JOIN bomweb_order o ON (a.ORDER_ID = o.ORDER_ID)" +
				" LEFT JOIN bomweb_all_doc d ON (a.DOC_TYPE = d.DOC_TYPE AND d.LOB = o.LOB)" +
				" WHERE a.ORDER_ID = :orderId" +
				" AND NVL(a.MARK_DEL_IND, :defaultMarkDelInd) <> :markDelInd" +
				" ORDER BY a.DOC_TYPE";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("defaultMarkDelInd", MarkDelInd.N.toString());
			params.addValue("markDelInd", MarkDelInd.Y.toString());
			if (logger.isInfoEnabled()) {
				logger.debug("getAllInUseOrdDocAssgnDTOALL() @ AllOrdDocAssgnDAO: " + sql);
			}
			list = simpleJdbcTemplate.query(sql, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getAllInUseOrdDocAssgnDTOALL()");
			}
			list = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllOrdDocAssgnDTOALL(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
	
	public AllOrdDocAssgnDTO getInUseAllOrdDocAssgnDTO(String orderId, String docType) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.debug("getInUseAllOrdDocAssgnDTO @ AllOrdDocAssgnDAO is called");
		}
		List<AllOrdDocAssgnDTO> list = Collections.emptyList();
		String sql = "SELECT" +
				" a.ORDER_ID" +
				" , a.DOC_TYPE" +
				" , d.DOC_NAME" +
				" , a.WAIVE_REASON" +
				" , a.WAIVED_BY" +
				" , a.COLLECTED_IND" +
				" , a.MARK_DEL_IND" +
				" , a.CREATE_BY" +
				" , a.CREATE_DATE" +
				" , a.LAST_UPD_BY" +
				" , a.LAST_UPD_DATE" +
				" , a.rowid ROW_ID" +
				" FROM bomweb_all_ord_doc_assgn a" +
				" LEFT JOIN bomweb_order o ON (a.ORDER_ID = o.ORDER_ID)" +
				" LEFT JOIN bomweb_all_doc d ON (a.DOC_TYPE = d.DOC_TYPE AND d.LOB = o.LOB)" +
				" WHERE a.ORDER_ID = :orderId" +
				" AND a.DOC_TYPE = :docType" +
				" AND NVL(a.MARK_DEL_IND, :defaultMarkDelInd) <> :markDelInd" +
				" ORDER BY a.DOC_TYPE";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("docType", docType);
			params.addValue("defaultMarkDelInd", MarkDelInd.N.toString());
			params.addValue("markDelInd", MarkDelInd.Y.toString());
			if (logger.isInfoEnabled()) {
				logger.debug("getInUseAllOrdDocAssgnDTO() @ AllOrdDocAssgnDAO: " + sql);
			}
			list = simpleJdbcTemplate.query(sql, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getInUseAllOrdDocAssgnDTO()");
			}
			list = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllOrdDocAssgnDTOALL(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return this.isEmpty(list) ? null : list.get(0);
	}
	
	public AllOrdDocAssgnDTO getImsInUseAllOrdDocAssgnDTO(String orderId, DocType docType) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.debug("getInUseAllOrdDocAssgnDTO @ AllOrdDocAssgnDAO is called");
		}
		List<AllOrdDocAssgnDTO> list = Collections.emptyList();
		String sql = "SELECT" +
				" a.ORDER_ID" +
				" , a.DOC_TYPE" +
				" , d.DOC_NAME" +
				" , a.WAIVE_REASON" +
				" , a.WAIVED_BY" +
				" , a.COLLECTED_IND" +
				" , a.MARK_DEL_IND" +
				" , a.CREATE_BY" +
				" , a.CREATE_DATE" +
				" , a.LAST_UPD_BY" +
				" , a.LAST_UPD_DATE" +
				" , a.rowid ROW_ID" +
				" FROM bomweb_all_ord_doc_assgn a" +
				" LEFT JOIN bomweb_all_doc d ON (a.DOC_TYPE = d.DOC_TYPE)" +
				" WHERE a.ORDER_ID = :orderId" +
				" AND a.DOC_TYPE = :docType" +
				" ORDER BY a.DOC_TYPE";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("docType", docType.toString());
			if (logger.isInfoEnabled()) {
				logger.debug("getInUseAllOrdDocAssgnDTO() @ AllOrdDocAssgnDAO: " + sql);
			}
			list = simpleJdbcTemplate.query(sql, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getInUseAllOrdDocAssgnDTO()");
			}
			list = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllOrdDocAssgnDTOALL(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return this.isEmpty(list) ? null : list.get(0);
	}
	
	private ParameterizedRowMapper<AllOrdDocAssgnDTO> getRowMapper() {
		return new ParameterizedRowMapper<AllOrdDocAssgnDTO>() {
			public AllOrdDocAssgnDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllOrdDocAssgnDTO dto = new AllOrdDocAssgnDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				try {
					dto.setDocType(DocType.valueOf(rs.getString("DOC_TYPE")));
				} catch (Exception e) {
					dto.setDocType(DocType.valueOf("M001"));
				}
				dto.setDocTypeMob(rs.getString("DOC_TYPE"));
				dto.setDocName(rs.getString("DOC_NAME"));
				dto.setWaiveReason(rs.getString("WAIVE_REASON"));
				dto.setWaivedBy(rs.getString("WAIVED_BY"));
				String collectedInd = rs.getString("COLLECTED_IND");
				if (collectedInd instanceof String) {
					dto.setCollectedInd(CollectedInd.valueOf(collectedInd));
				}
				String markDelInd = rs.getString("MARK_DEL_IND");
				if (markDelInd instanceof String) {
					dto.setMarkDelInd(MarkDelInd.valueOf(markDelInd));
				}
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setRowId(rs.getString("ROW_ID"));
				return dto;
			}
		};
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
