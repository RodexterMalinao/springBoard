package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllOrdDocWaiveDTO;
import com.bomwebportal.exception.DAOException;

public class AllOrdDocWaiveDAO extends BaseDAO {
	public List<AllOrdDocWaiveDTO> getAllOrdDocWaiveDTOALL(String lob, String docType) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.debug("getAllOrdDocWaiveDTOALL @ AllOrdDocWaiveDAO is called");
		}
		List<AllOrdDocWaiveDTO> list = Collections.emptyList();
		String sql = "SELECT" +
				" DOC_TYPE" +
				" , LOB" +
				" , WAIVE_REASON" +
				" , WAIVE_DESC" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_all_ord_doc_waive" +
				" WHERE LOB = :lob" +
				" AND DOC_TYPE = :docType" +
				" ORDER BY WAIVE_REASON";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("lob", lob);
			params.addValue("docType", docType == null ? null : docType);
			if (logger.isInfoEnabled()) {
				logger.debug("getAllOrdDocWaiveDTOALL() @ AllOrdDocWaiveDAO: " + sql);
			}
			list = simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getAllOrdDocWaiveDTOALL()");
			}
			list = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllOrdDocWaiveDTOALL(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
	
	private ParameterizedRowMapper<AllOrdDocWaiveDTO> getRowMapper() {
		return new ParameterizedRowMapper<AllOrdDocWaiveDTO>() {
			public AllOrdDocWaiveDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllOrdDocWaiveDTO dto = new AllOrdDocWaiveDTO();
				try {
					dto.setDocType(DocType.valueOf(rs.getString("DOC_TYPE")));
				} catch (Exception e) {
					dto.setDocType(DocType.valueOf("M001"));
				}
				dto.setDocTypeMob(rs.getString("DOC_TYPE"));
				dto.setLob(rs.getString("LOB"));
				dto.setWaiveReason(rs.getString("WAIVE_REASON"));
				dto.setWaiveDesc(rs.getString("WAIVE_DESC"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setRowId(rs.getString("ROW_ID"));
				return dto;
			}
		};
	}
}
