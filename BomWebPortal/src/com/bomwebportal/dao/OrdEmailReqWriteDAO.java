package com.bomwebportal.dao;

import java.sql.Types;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.exception.DAOException;

public class OrdEmailReqWriteDAO extends BaseDAO {
	public int insertOrdEmailReq(OrdEmailReqDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertOrdEmailReq @ OrdEmailReqWriteDAO is called");
		}
		String sql = "insert into" +
				"  bomweb_ord_email_req" +
				" (" +
				" ORDER_ID" +
				" , TEMPLATE_ID" +
				" , REQUEST_DATE" +
				" , FILE_PATH_NAME_1" +
				" , FILE_PATH_NAME_2" +
				" , FILE_PATH_NAME_3" +
				" , SENT_DATE" +
				" , ERR_MSG" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , EMAIL_ADDR" +
				" , LOCALE" +
				" , SEQ_NO" +
				" , METHOD" +
				" , LOB" +
				" , SMS_NO" +
				" , PARAM_STRING" +
				" , FORM_PRINT_REQ_ID" +
				" ) VALUES (" +
				" :orderId" +
				" , :templateId" +
				" , :requestDate" +
				" , :filePathName1" +
				" , :filePathName2" +
				" , :filePathName3" +
				" , :sentDate" +
				" , :errMsg" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" , :emailAddr" +
				" , :locale" +
				" , :seqNo" +
				" , :method" +
				" , :lob" +
				" , :smsNo" +
				" , :paramString" +
				" , :formPrintReqId" +
				" )";

		if (logger.isInfoEnabled()) {
			logger.info("insertOrdEmailReq() @ OrdEmailReqWriteDAO: " + sql);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("templateId", dto.getTemplateId());
			params.addValue("requestDate", dto.getRequestDate(), Types.TIMESTAMP);
			params.addValue("filePathName1", dto.getFilePathName1());
			params.addValue("filePathName2", dto.getFilePathName2());
			params.addValue("filePathName3", dto.getFilePathName3());
			params.addValue("sentDate", dto.getSentDate(), Types.TIMESTAMP);
			params.addValue("errMsg", dto.getErrMsg());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("emailAddr", dto.getEmailAddr());
			params.addValue("locale", dto.getLocale().toString());
			params.addValue("seqNo", dto.getSeqNo());
			params.addValue("lob", dto.getLob());
			params.addValue("smsNo", dto.getSMSno());
			params.addValue("method", dto.getMethod().toString());
			params.addValue("paramString", dto.getParamString());
			params.addValue("formPrintReqId", dto.getPrintReqId());
			
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in insertOrdEmailReq(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateOrdEmailReq(OrdEmailReqDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateOrdEmailReq @ OrdEmailReqWriteDAO is called");
		}
		String sql = "update" +
				"  bomweb_ord_email_req" +
				" set" +
				"  SENT_DATE = :sentDate" +
				"  , ERR_MSG = :errMsg" +
				"  , LAST_UPD_BY = :lastUpdBy" +
				"  , LAST_UPD_DATE = sysdate" +
				" where" +
				"  rowid = :rowId";

		if (logger.isInfoEnabled()) {
			logger.info("updateOrdEmailReq() @ OrdEmailReqWriteDAO: " + sql);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("sentDate", dto.getSentDate(), Types.TIMESTAMP);
			params.addValue("errMsg", dto.getErrMsg());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("rowId", dto.getRowId());
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getLastRowId(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int insertOrdSMSReq(OrdEmailReqDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertOrdSMSReq @ OrdEmailReqWriteDAO is called");
		}
		String sql = "insert into" +
				"  bomweb_ord_email_req" +
				" (" +
				" ORDER_ID" +
				" , TEMPLATE_ID" +
				" , REQUEST_DATE" +
				" , FILE_PATH_NAME_1" +
				" , FILE_PATH_NAME_2" +
				" , FILE_PATH_NAME_3" +
				" , SENT_DATE" +
				" , ERR_MSG" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , SMS_NO" +
				" , LOCALE" +
				" , SEQ_NO" +
				" , METHOD" +
				" ) VALUES (" +
				" :orderId" +
				" , :templateId" +
				" , :requestDate" +
				" , :filePathName1" +
				" , :filePathName2" +
				" , :filePathName3" +
				" , :sentDate" +
				" , :errMsg" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" , :smsno" +
				" , :locale" +
				" , :seqNo" +
				" , :method" +
				" )";

		if (logger.isInfoEnabled()) {
			logger.info("insertOrdSMSReq() @ OrdEmailReqWriteDAO: " + sql);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("templateId", dto.getTemplateId());
			params.addValue("requestDate", dto.getRequestDate(), Types.TIMESTAMP);
			params.addValue("filePathName1", dto.getFilePathName1());
			params.addValue("filePathName2", dto.getFilePathName2());
			params.addValue("filePathName3", dto.getFilePathName3());
			params.addValue("sentDate", dto.getSentDate(), Types.TIMESTAMP);
			params.addValue("errMsg", dto.getErrMsg());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("smsno", dto.getSMSno());
			params.addValue("locale", dto.getLocale().toString());
			params.addValue("seqNo", dto.getSeqNo());
			params.addValue("method", DisMode.S.toString());
			
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in insertOrdSMSReq(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateOrdSMSReq(OrdEmailReqDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateOrdSMSReq @ OrdEmailReqWriteDAO is called");
		}
		String sql = "update" +
				"  bomweb_ord_email_req" +
				" set" +
				"  SENT_DATE = :sentDate" +
				"  , ERR_MSG = :errMsg" +
				"  , LAST_UPD_BY = :lastUpdBy" +
				"  , LAST_UPD_DATE = sysdate" +
				" where" +
				"  rowid = :rowId";

		if (logger.isInfoEnabled()) {
			logger.info("updateOrdSMSReq() @ OrdEmailReqWriteDAO: " + sql);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("sentDate", dto.getSentDate(), Types.TIMESTAMP);
			params.addValue("errMsg", dto.getErrMsg());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("rowId", dto.getRowId());
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getLastRowId(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
}
