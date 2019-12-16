package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.DoaItemDTO;

public class MobCcsDoaItemDAO extends BaseDAO {
	private String getMobCcsDoaItemDTOALLSQL = "SELECT" +
			" ORDER_ID" +
			" , SEQ_NO" +
			" , DOA_ITEM_CODE" +
			" , CREATE_BY" +
			" , CREATE_DATE" +
			" , LAST_UPD_BY" +
			" , LAST_UPD_DATE" +
			" , DOA_ITEM_SERIAL" +
			" FROM bomweb_doa_item" +
			" WHERE ORDER_ID = :orderId" +
			" AND SEQ_NO = :seqNo";
	public List<DoaItemDTO> getDoaItemDTOALL(String orderId, int seqNo) throws DAOException {
		logger.info("getDoaItemDTOALL() is called");
		
		List<DoaItemDTO> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			if (logger.isInfoEnabled()) {
				logger.info("getDoaItemDTOALL() @ MobCcsDoaItemDAO: " + getMobCcsDoaItemDTOALLSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getMobCcsDoaItemDTOALLSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getDoaItemDTOALL()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getDoaItemDTOALL():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	private ParameterizedRowMapper<DoaItemDTO> getRowMapper() {
		return new ParameterizedRowMapper<DoaItemDTO>() {
			public DoaItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				DoaItemDTO dto = new DoaItemDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setSeqNo(rs.getInt("SEQ_NO"));
				dto.setDoaItemCode(rs.getString("DOA_ITEM_CODE"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setDoaItemSerial(rs.getString("DOA_ITEM_SERIAL"));
				return dto;
			}
		};
	}
	
	private String insertDoaItemDTOSQL = "INSERT INTO bomweb_doa_item" +
			"(" +
			" ORDER_ID" +
			" , SEQ_NO" +
			" , DOA_ITEM_CODE" +
			" , CREATE_BY" +
			" , CREATE_DATE" +
			" , LAST_UPD_BY" +
			" , LAST_UPD_DATE" +
			" , DOA_ITEM_SERIAL" +
			") VALUES (" +
			" :orderId" +
			" , :seqNo" +
			" , :doaItemCode" +
			" , :createBy" +
			" , sysdate" +
			" , :lastUpdBy" +
			" , sysdate" +
			" , :doaItemSerial" +
			")";
	public int insertDoaItemDTO(DoaItemDTO dto) throws DAOException {
		logger.info("insertDoaItemDTO() is called");
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("seqNo", dto.getSeqNo());
			params.addValue("doaItemCode", dto.getDoaItemCode());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("doaItemSerial", dto.getDoaItemSerial());
			if (logger.isInfoEnabled()) {
				logger.info("insertDoaItemDTO() @ MobCcsDoaItemDAO: " + insertDoaItemDTOSQL);
			}
			return this.simpleJdbcTemplate.update(insertDoaItemDTOSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in insertDoaItemDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	private String deleteDoaItemBySeqNoSQL = "DELETE FROM bomweb_doa_item" +
			" WHERE ORDER_ID = :orderId" +
			" AND SEQ_NO = :seqNo";
	public int deleteDoaItemBySeqNo(String orderId, int seqNo) throws DAOException {
		logger.info("deleteDoaItemBySeqNo() is called");
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			if (logger.isInfoEnabled()) {
				logger.info("deleteDoaItemBySeqNo() @ MobCcsDoaItemDAO: " + deleteDoaItemBySeqNoSQL);
			}
			return this.simpleJdbcTemplate.update(deleteDoaItemBySeqNoSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in deleteDoaItemBySeqNo():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
