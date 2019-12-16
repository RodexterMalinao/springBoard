package com.bomwebportal.dao;

import java.sql.Types;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrdEmailReqDTO.DisMode;
import com.bomwebportal.exception.DAOException;

public class OrdEmailReqWriteDAO extends BaseDAO {
	
	/*
	Initial copied on 20140925 from Version 1.1 of
	\Springboard\SBWPR3\BomWebPortal\src\com\bomwebportal\dao\OrdEmailReqWriteDAO.java
	*/		
	
	public int insertOrdEmailReq(OrdEmailReqDTO pDto) throws DAOException {
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
				" , PROFILE_ID" +
				" , ACTV_ID" +
				" , TASK_SEQ" +
				" , LOB" +
				" , STORE_PATH" +
				" , CUST_NO" + 
				" , PARAM_STRING" +
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
				" , :profileId" +
				" , :actvId" +
				" , :taskSeq" +
				" , :lob" +
				" , :storePath" +
				" , :custNo" + 
				" , :paramString" +
				" )";

		if (logger.isInfoEnabled()) {
			logger.info("insertOrdEmailReq() @ OrdEmailReqWriteDAO: " + sql);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", pDto.getOrderId());
			params.addValue("templateId", pDto.getTemplateId());
			params.addValue("requestDate", pDto.getRequestDate(), Types.TIMESTAMP);
			params.addValue("filePathName1", pDto.getFilePathName1());
			params.addValue("filePathName2", pDto.getFilePathName2());
			params.addValue("filePathName3", pDto.getFilePathName3());
			params.addValue("sentDate", pDto.getSentDate(), Types.TIMESTAMP);
			params.addValue("errMsg", pDto.getErrMsg());
			params.addValue("createBy", pDto.getCreateBy());
			params.addValue("lastUpdBy", pDto.getLastUpdBy());
			params.addValue("emailAddr", pDto.getEmailAddr());
			params.addValue("locale", pDto.getLocale().toString());
			params.addValue("seqNo", pDto.getSeqNo());
			params.addValue("method", pDto.getMethod().toString());
			params.addValue("actvId", pDto.getActvId());
			params.addValue("profileId", pDto.getProfileId());
			params.addValue("lob", pDto.getLob());
			params.addValue("taskSeq", pDto.getTaskSeq());
			params.addValue("storePath", pDto.getStorePath());
			params.addValue("custNo", pDto.getCustNo());
			params.addValue("paramString", pDto.getParamString());
			
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in insertOrdEmailReq(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateOrdEmailReq(OrdEmailReqDTO pDto) throws DAOException {
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
			params.addValue("sentDate", pDto.getSentDate(), Types.TIMESTAMP);
			params.addValue("errMsg", pDto.getErrMsg());
			params.addValue("lastUpdBy", pDto.getLastUpdBy());
			params.addValue("rowId", pDto.getRowId());
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getLastRowId(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int insertOrdSMSReq(OrdEmailReqDTO pDto) throws DAOException {
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
				" , PROFILE_ID" +
				" , ACTV_ID" +
				" , TASK_SEQ" +
				" , LOB" +
				" , STORE_PATH" +
				" , CUST_NO" + 
				" , PARAM_STRING" +
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
				" , :profileId" +
				" , :actvId" +
				" , :taskSeq" +
				" , :lob" +
				" , :storePath" +
				" , :custNo" + 
				" , :paramString" +
				" )";

		if (logger.isInfoEnabled()) {
			logger.info("insertOrdSMSReq() @ OrdEmailReqWriteDAO: " + sql);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", pDto.getOrderId());
			params.addValue("templateId", pDto.getTemplateId());
			params.addValue("requestDate", pDto.getRequestDate(), Types.TIMESTAMP);
			params.addValue("filePathName1", pDto.getFilePathName1());
			params.addValue("filePathName2", pDto.getFilePathName2());
			params.addValue("filePathName3", pDto.getFilePathName3());
			params.addValue("sentDate", pDto.getSentDate(), Types.TIMESTAMP);
			params.addValue("errMsg", pDto.getErrMsg());
			params.addValue("createBy", pDto.getCreateBy());
			params.addValue("lastUpdBy", pDto.getLastUpdBy());
			params.addValue("smsno", pDto.getSMSno());
			params.addValue("locale", pDto.getLocale().toString());
			params.addValue("seqNo", pDto.getSeqNo());
			params.addValue("method", DisMode.S.toString());
			params.addValue("actvId", pDto.getActvId());
			params.addValue("profileId", pDto.getProfileId());
			params.addValue("lob", pDto.getLob());
			params.addValue("taskSeq", pDto.getTaskSeq());
			params.addValue("storePath", pDto.getStorePath());
			params.addValue("custNo", pDto.getCustNo());
			params.addValue("paramString", pDto.getParamString());
			
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in insertOrdSMSReq(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateOrdSMSReq(OrdEmailReqDTO pDto) throws DAOException {
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
			params.addValue("sentDate", pDto.getSentDate(), Types.TIMESTAMP);
			params.addValue("errMsg", pDto.getErrMsg());
			params.addValue("lastUpdBy", pDto.getLastUpdBy());
			params.addValue("rowId", pDto.getRowId());
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getLastRowId(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
}
