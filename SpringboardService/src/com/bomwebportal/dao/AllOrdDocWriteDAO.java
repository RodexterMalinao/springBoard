package com.bomwebportal.dao;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dto.AllOrdDocDTO;
import com.bomwebportal.exception.DAOException;

public class AllOrdDocWriteDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public int insertAllOrderDocDTO(AllOrdDocDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertAllOrderDocDTO @ AllOrdDocDAO is called");
		}
		String sql = "INSERT INTO bomweb_all_ord_doc" +
				" (" +
				" ORDER_ID" +
				" , DOC_TYPE" +
				" , SEQ_NUM" +
				" , FILE_PATH_NAME" +
				" , PROCESS_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" ) VALUES (" +
				" :orderId" +
				" , :docType" +
				" , (SELECT NVL(MAX(SEQ_NUM), 0) + 1 FROM bomweb_all_ord_doc WHERE ORDER_ID = :orderId AND DOC_TYPE = :docType)" +
				" , :filePathName" +
				" , trunc(:processDate)" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" )";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			//params.addValue("docType", dto.getDocType() == null ? null : dto.getDocType().toString());
			params.addValue("docType", dto.getDocTypeMob() == null ? (dto.getDocType() == null ? null : dto.getDocType().toString()) : dto.getDocTypeMob());
			params.addValue("filePathName", dto.getFilePathName());
			params.addValue("processDate", dto.getProcessDate(), Types.DATE);
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			if (logger.isInfoEnabled()) {
				logger.info("insertAllOrderDocDTO() @ AllOrdDocDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in insertAllOrderDocDTO(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	public int updateOutdatedInd(String orderId, String docType,
			boolean isOutdated) throws DAOException {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_ALL_ORD_DOC SET");
		sql.append(" OUTDATED_IND = :outdatedInd");
		sql.append(" WHERE ORDER_ID = :orderId");
		sql.append(" AND DOC_TYPE = :docType");

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("outdatedInd", isOutdated ? "Y" : "NULL");
			params.addValue("orderId", orderId);
			params.addValue("docType", docType);
			return simpleJdbcTemplate.update(sql.toString(), params);
		} catch (Exception e) {
			logger.error("Exception caught in updateOutdatedInd(): ", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	//steven
	public int updateAllOrdDocAssgnDTOOutdatedInd(String order_id, String doc_type) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateAllOrdDocAssgnDTOOutdatedInd @ AllOrdDocAssgnDAO is called");
		}
		String sql = "UPDATE bomweb_all_ord_doc" +
				" SET" +
				" OUTDATED_IND = :outdatedInd" +
				" WHERE ORDER_ID = :orderId" +
				" and DOC_TYPE = :docType";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("outdatedInd", "Y");
			params.addValue("docType", doc_type);
			params.addValue("orderId", order_id);
			if (logger.isInfoEnabled()) {
				logger.info("updateAllOrdDocAssgnDTOOutdatedInd() @ AllOrdDocAssgnDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateAllOrdDocAssgnDTOOutdatedInd(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
}
