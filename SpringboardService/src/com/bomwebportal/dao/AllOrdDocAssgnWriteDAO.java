package com.bomwebportal.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.MarkDelInd;
import com.bomwebportal.exception.DAOException;

public class AllOrdDocAssgnWriteDAO extends BaseDAO {
	public int insertAllOrdDocAssgnDTO(AllOrdDocAssgnDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertAllOrdDocAssgnDTO @ AllOrdDocAssgnDAO is called");
		}
		String sql = "INSERT INTO bomweb_all_ord_doc_assgn" +
				" (" +
				" ORDER_ID" +
				" , DOC_TYPE" +
				" , WAIVE_REASON" +
				" , WAIVED_BY" +
				" , COLLECTED_IND" +
				" , MARK_DEL_IND" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" ) VALUES (" +
				" :orderId" +
				" , :docType" +
				" , :waiveReason" +
				" , :waivedBy" +
				" , :collectedInd" +
				" , :markDelInd" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" )";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("docType", dto.getDocTypeMob() == null ? (dto.getDocType() == null ? null : dto.getDocType().toString()) : dto.getDocTypeMob());
			params.addValue("waiveReason", dto.getWaiveReason());
			params.addValue("waivedBy", dto.getWaivedBy());
			params.addValue("collectedInd", dto.getCollectedInd() == null ? null : dto.getCollectedInd().toString());
			params.addValue("markDelInd", dto.getMarkDelInd() == null ? null : dto.getMarkDelInd().toString());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			if (logger.isInfoEnabled()) {
				logger.info("insertAllOrdDocAssgnDTO() @ AllOrdDocAssgnDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in insertAllOrdDocAssgnDTO(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int updateAllOrdDocAssgnDTO(AllOrdDocAssgnDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateAllOrdDocAssgnDTO @ AllOrdDocAssgnDAO is called");
		}
		String sql = "UPDATE bomweb_all_ord_doc_assgn" +
				" SET" +
				" WAIVE_REASON = :waiveReason" +
				" , WAIVED_BY = :waivedBy" +
				" , COLLECTED_IND = :collectedInd" +
				" , MARK_DEL_IND = :markDelInd" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = sysdate" +
				" WHERE rowid = :rowId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("waiveReason", dto.getWaiveReason());
			params.addValue("waivedBy", dto.getWaivedBy());
			params.addValue("collectedInd", dto.getCollectedInd() == null ? null : dto.getCollectedInd().toString());
			params.addValue("markDelInd", dto.getMarkDelInd() == null ? null : dto.getMarkDelInd().toString());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("rowId", dto.getRowId());
			if (logger.isInfoEnabled()) {
				logger.info("updateAllOrdDocAssgnDTO() @ AllOrdDocAssgnDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateAllOrdDocAssgnDTO(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int updateMarkDelInd(String rowId, MarkDelInd markDelInd, String lastUpdBy) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateMarkDelInd @ AllOrdDocAssgnDAO is called");
		}
		String sql = "UPDATE bomweb_all_ord_doc_assgn" +
				" SET" +
				" MARK_DEL_IND = :markDelInd" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = sysdate" +
				" WHERE rowid = :rowId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("markDelInd", markDelInd == null ? null : markDelInd.toString());
			params.addValue("lastUpdBy", lastUpdBy);
			params.addValue("rowId", rowId);
			if (logger.isInfoEnabled()) {
				logger.info("updateMarkDelInd() @ AllOrdDocAssgnDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateMarkDelInd(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int ims_mark_all_delete_dao(String orderId) throws DAOException {
		System.out.println("ims_mark_all_delete_dao @ AllOrdDocAssgnWriteDAO is called");
		if (logger.isInfoEnabled()) {
			logger.info("ims_mark_all_delete_dao @ AllOrdDocAssgnDAO is called");
		}
		String sql = "UPDATE bomweb_all_ord_doc_assgn" +
				" SET" +
				" MARK_DEL_IND = :markDelInd" +
				" WHERE ORDER_ID = :orderId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("markDelInd", "Y");
			if (logger.isInfoEnabled()) {
				logger.info("ims_mark_all_delete_dao() @ AllOrdDocAssgnDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in ims_mark_all_delete_dao(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}

}
