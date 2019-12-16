package com.bomltsportal.dao;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.BomWebPortalConstant;

public class CeksDAO extends BaseDAO{

	protected final Log logger = LogFactory.getLog(getClass());

	public String getCeksFeeDesc(String locale) throws DAOException {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DESCRIPTION FROM ");
		sql.append(" W_DISPLAY_LKUP");
		sql.append(" WHERE type = :type");
		sql.append(" AND locale = :locale");
		
		try {
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("type", BomLtsConstant.CEKS_FEE_DESC);
			param.addValue("locale", locale);
			return simpleJdbcTemplate.queryForObject(sql.toString(), String.class, param);	
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public String getCeksTitle(String srvType, String locale) throws DAOException {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DESCRIPTION FROM ");
		sql.append(" W_DISPLAY_LKUP");
		sql.append(" WHERE type = :type");
		sql.append(" AND locale = :locale");
		
		try {
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("type", StringUtils.equals(srvType, BomLtsConstant.SERVICE_TYPE_DEL) ? BomLtsConstant.CEKS_TITLE_DEL : BomLtsConstant.CEKS_TITLE_EYE);
			param.addValue("locale", locale);
			return simpleJdbcTemplate.queryForObject(sql.toString(), String.class, param);	
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public String initialCeksAccess(String username, String appId) throws DAOException {
		String contextId = null;

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("BOMWEB_CEKS_PKG")
            	.withProcedureName("INIT_CEKS_ACCESS");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("I_APP_ID", Types.VARCHAR),
					new SqlParameter("I_USER_ID", Types.VARCHAR),
					new SqlOutParameter("O_CONTEXT_ID", Types.VARCHAR),
					new SqlOutParameter("RET_VAL", Types.INTEGER),
					new SqlOutParameter("ERROR_CODE", Types.INTEGER),
					new SqlOutParameter("ERROR_TEXT", Types.VARCHAR));
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("I_APP_ID", appId);
			inMap.addValue("I_USER_ID", username);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("ERROR_CODE"))!= null ) {
				error_code = ((Integer) out.get("ERROR_CODE")).intValue();
			}
			
			if (((Integer) out.get("RET_VAL"))!= null ) {
				ret_val = ((Integer) out.get("RET_VAL")).intValue();
			}
			
			String error_text = (String)out.get("ERROR_TEXT");
			contextId = (String)out.get("O_CONTEXT_ID");
			
			logger.info("BOMWEB_CEKS_PKG.INIT_CEKS_ACCESS() output ret_val = " + ret_val);
			logger.info("BOMWEB_CEKS_PKG.INIT_CEKS_ACCESS() output error_code = " + error_code);
			logger.info("BOMWEB_CEKS_PKG.INIT_CEKS_ACCESS() output error_text = " + error_text);
			logger.info("BOMWEB_CEKS_PKG.INIT_CEKS_ACCESS() output contextId = " + contextId);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				contextId = null;
			}
			
			logger.info("initialCeksAccess contextId = " + contextId);
			
			return contextId;
			
		} catch (Exception e) {
			logger.error("Exception caught in initialCeksAccess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void insertCeksTempRecord(String order_id, String app_id, String reference_no, String pay_amount) throws DAOException
	{
		logger.info("insertCeksTempRecord is called");
		String SQL = " INSERT INTO w_online_payment_txn " +
				" (order_id, app_id, status, reference_no, ret_code, pay_amount, " +
				"card_pan, exp_date, create_by, create_date, last_upd_by, last_upd_date " +
				") " +
				"VALUES (?, ?, null, ?, null, ?, " +
				"null, null, 'SBOLTS', sysdate , null, null " +
				") ";
		try{
			simpleJdbcTemplate.update(SQL, 
					order_id,
					app_id,
					reference_no,
					pay_amount);
			
		}catch(Exception e) {
			logger.error("Exception caught in insertCeksTempRecord()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void updateCeksTempRecord(String order_id, String reference_no, String ret_code, String status, String card_pan, String ret_parm, String exp_date) throws DAOException
	{
		logger.info("updateCeksTempRecord is called");
		String SQL = " UPDATE w_online_payment_txn " +
				" SET order_id = ?, " +
				" status = ?, " +
				" reference_no = ?, " +
				" ret_code = ?, " +
				" card_pan = ?, " +
				" ret_parm = ?, " +
				" exp_date = ?, " +
				" last_upd_by = 'SBOLTS', " +
				" last_upd_date = sysdate " +
				"WHERE order_id = ? " +
				"AND reference_no = ? ";
		
		try {
			simpleJdbcTemplate.update(SQL, 
					order_id,
					status,
					reference_no,
					ret_code,
					card_pan,
					ret_parm,
					exp_date,
					order_id,
					reference_no);
		}catch(Exception e) {
			logger.error("Exception caught in updateCeksTempRecord()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> getCeksTempRecord(String order_id, String status, String ret_code) throws DAOException{
		
		logger.info("checkCeksTempRecordExist is called");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT reference_no FROM w_online_payment_txn ");
		sql.append(" WHERE order_id = :order_id ");
		if(status != null){
			sql.append(" AND status = :status ");
		}
		if(ret_code != null){
			sql.append(" AND ret_code = :ret_code ");
		}
		
		try {
			paramSource.addValue("order_id", order_id);
			paramSource.addValue("status", status);
			paramSource.addValue("ret_code", ret_code);
			List<String> resultList = simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(sql.toString(), paramSource, String.class);
			return resultList;

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in checkCeksTempRecordExist()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in checkCeksTempRecordExist():", e);
			return null;
		}
	}
}
