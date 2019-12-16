package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.DoaRequestSelectedCdDTO;

public class MobCcsDoaRequestSelectedCdDAO extends BaseDAO {
	private String getMobCcsDoaRequestSelectedCdDTOALLSQL = "SELECT" +
			" ORDER_ID" +
			" , SEQ_NO" +
			" , CODE_ID" +
			" , CREATE_BY" +
			" , CREATE_DATE" +
			" , LAST_UPD_BY" +
			" , LAST_UPD_DATE" +
			" FROM bomweb_doa_request_selected_cd" +
			" WHERE ORDER_ID = :orderId" +
			" AND SEQ_NO = :seqNo";
	public List<DoaRequestSelectedCdDTO> getDoaRequestSelectedCdDTOALL(String orderId, int seqNo) throws DAOException {
		logger.info("getMobCcsDoaRequestSelectedCdDTOALL() is called");
		
		List<DoaRequestSelectedCdDTO> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			if (logger.isInfoEnabled()) {
				logger.info("getMobCcsDoaRequestSelectedCdDTOALL() @ MobCcsDoaRequestSelectedCdDAO: " + getMobCcsDoaRequestSelectedCdDTOALLSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getMobCcsDoaRequestSelectedCdDTOALLSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsDoaRequestSelectedCdDTOALL()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsDoaRequestSelectedCdDTOALL():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	private ParameterizedRowMapper<DoaRequestSelectedCdDTO> getRowMapper() {
		return new ParameterizedRowMapper<DoaRequestSelectedCdDTO>() {
			public DoaRequestSelectedCdDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				DoaRequestSelectedCdDTO dto = new DoaRequestSelectedCdDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setSeqNo(rs.getInt("SEQ_NO"));
				dto.setCodeId(rs.getString("CODE_ID"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				return dto;
			}
			
		};
	}
	
	private String insertDoaRequestSelectedCdDTOSQL = "INSERT INTO bomweb_doa_request_selected_cd" +
			"(" +
			" ORDER_ID" +
			" , SEQ_NO" +
			" , CODE_ID" +
			" , CREATE_BY" +
			" , CREATE_DATE" +
			" , LAST_UPD_BY" +
			" , LAST_UPD_DATE" +
			") VALUES (" +
			" :orderId" +
			" , :seqNo" +
			" , :codeId" +
			" , :createBy" +
			" , sysdate" +
			" , :lastUpdBy" +
			" , sysdate" +
			")";
	public int insertDoaRequestSelectedCdDTO(DoaRequestSelectedCdDTO dto) throws DAOException {
		logger.info("insertDoaRequestSelectedCdDTO() is called");
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("seqNo", dto.getSeqNo());
			params.addValue("codeId", dto.getCodeId());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			if (logger.isInfoEnabled()) {
				logger.info("insertDoaRequestSelectedCdDTO() @ MobCcsDoaRequestSelectedCdDAO: " + insertDoaRequestSelectedCdDTOSQL);
			}
			return this.simpleJdbcTemplate.update(insertDoaRequestSelectedCdDTOSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in insertDoaRequestSelectedCdDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private String deleteDoaRequestSelectedCdBySeqNoSQL = "DELETE FROM bomweb_doa_request_selected_cd" +
			" WHERE ORDER_ID = :orderId" +
			" AND SEQ_NO = :seqNo";
	public int deleteDoaRequestSelectedCdBySeqNo(String orderId, int seqNo) throws DAOException {
		logger.info("deleteDoaRequestSelectedCdBySeqNo() is called");
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			if (logger.isInfoEnabled()) {
				logger.info("deleteDoaRequestSelectedCdBySeqNo() @ MobCcsDoaRequestSelectedCdDAO: " + deleteDoaRequestSelectedCdBySeqNoSQL);
			}
			return this.simpleJdbcTemplate.update(deleteDoaRequestSelectedCdBySeqNoSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in deleteDoaRequestSelectedCdBySeqNo():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
