package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDTO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDetailDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class AmendLtsDAO extends BaseDAO {
	
	private static final String SQL_GET_NEXT_BATCH_SEQ = 
		"select nvl(max(batch_seq), -1) + 1 " +
		"from bomweb_order_lts_amend " +
		"where order_id = ? ";// +
	    //"and dtl_id = ? ";
	
	private static final String SQL_GET_NEXT_ITEM_SEQ = 
		"select nvl(max(item_seq), -1) + 1 " +
		"from bomweb_order_lts_amend_dtl " +
		"where order_id = ? " +
	    "and dtl_id = ? " +
	    "and batch_seq = ? ";
	
	private static final String SQL_UPDATE_APPROVAL_STATUS = 
		"update bomweb_order_lts_amend " +
		"set approval_status = ?, " +
		"last_upd_by = ?, " + 
		"last_upd_date = sysdate " +
		"where order_id = ? " +
	    "and approval_satus is null ";
	
	private static final String SQL_RETRIEVE_APPROVED_AMEND =
			"SELECT ORDER_ID, DTL_ID, BATCH_SEQ, STAFF_NUM, SALES_CD, " +
			" SALES_CHANNEL, SHOP_CD, SALES_CONTACT_NUM, APP_NAME, " +
			" APP_CONTACT_NUM, APP_RELATION_CD, REQUIRE_APPROVAL, " +
			" APPROVAL_STATUS, BATCH_UPDATE_STATUS, TO_CHAR(CREATE_DATE, 'DD/MM/YYYY') AS CREATE_DATE, " +
			" EMAIL_ADDR, SMS_NO " +
			"FROM BOMWEB_ORDER_LTS_AMEND " +
			"WHERE order_id = :orderId " +
			"AND dtl_id = TO_NUMBER(:dtlId) " +
			"AND batch_seq =  " +
			"	(SELECT MAX(batch_seq) FROM BOMWEB_ORDER_LTS_AMEND " +
			"	WHERE order_id = :orderId " +
			"	AND ((require_approval = 'Y' AND approval_status = 'A') " +
			"	     OR NVL(require_approval, 'N') = 'N') " +
			"	AND  BATCH_UPDATE_STATUS IS NULL) " +
			"AND ROWNUM = 1 ";
	
	private static final String SQL_RETRIEVE_APPROVED_AMEND_DTL = 
			"SELECT order_id, dtl_id, batch_seq, item_seq, item_sub_seq, amend_cat, " +
			" amend_sub_cat, amend_item, amend_value " +
			"FROM BOMWEB_ORDER_LTS_AMEND_DTL " +
			"WHERE order_id = :orderId " +
			"AND DTL_ID = TO_NUMBER(:dtlId) " +
			"AND batch_seq =  " +
			"	(SELECT MAX(batch_seq) FROM BOMWEB_ORDER_LTS_AMEND " +
			"	WHERE order_id = :orderId " +
			"	AND ((require_approval = 'Y' AND approval_status = 'A') " +
			"	     OR NVL(require_approval, 'N') = 'N') " +
			"	AND  BATCH_UPDATE_STATUS IS NULL) ";

	private static final String SQL_UPDATE_AMEND_BATCH_UPDATE_STATUS = 
		"update bomweb_order_lts_amend " +
		"set batch_update_status = ?, " +
		"last_upd_by = ?, " + 
		"last_upd_date = sysdate " +
		"where order_id = ? " +
	    "and dtl_id = ? " +
	    "and batch_seq = ? ";

	private static final String SQL_UPDATE_ITEM_UPDATE_STATUS = 
		"update bomweb_order_lts_amend_dtl " +
		"set item_update_status = :status, " +
		"last_upd_by = :user, " + 
		"last_upd_date = sysdate " +
		"where order_id = :orderId " +
	    "and dtl_id = :dtlId " +
	    "and batch_seq = :batchSeq " +
	    "and amend_cat IN (:amendCat)";

	private static final String SQL_UPDATE_AMEND_ERROR_MSG = 
		"update bomweb_order_lts_amend " +
		"set error_msg = SUBSTR(SUBSTR(:errorMsg, 1, 1000) || CHR(10) || TO_CLOB(error_msg), 1, 4000), " +
		"last_upd_by = :user, " + 
		"last_upd_date = sysdate " +
		"where order_id = :orderId " +
	    "and batch_seq = :batchSeq ";
	
	private static final String SQL_INSERT_EMAIL_REQ = 
			" INSERT INTO bomweb_ord_email_req " +
			" (ORDER_ID, TEMPLATE_ID, REQUEST_DATE, EMAIL_ADDR, SMS_NO, LOCALE, LOB, " +
			" SEQ_NO, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE, METHOD, form_print_req_ID) " + 
			" VALUES " + 
			" (:orderId, :templateId, TO_DATE(:reqDate, 'DD/MM/YYYY'), :emailAddr, :smsNo, :locale, :lob, " +
			" :seqNo, :username, SYSDATE, :username, SYSDATE, :method, :formPrintReqId) ";

    private final String SQL_GET_EMAIL_TEMPLATE_SUBJECT_CONTENT = 
    		" SELECT TEMPLATE_DESC, TEMPLATE_SUBJECT, TEMPLATE_CONTENT " +
    		" FROM bomweb_email_template " +
    		" WHERE LOB = 'LTS' " +
    		" AND template_id = :templateId " +
    		" AND locale = :locale";
    	
    public void insertEmailReq(String orderId, String templateId, String reqDate, String emailAddr, String smsNo,
    		String locale, int seqNo, String username, String method, String formPrintReqId) throws DAOException {	
    	MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("templateId", templateId);
		params.addValue("reqDate", reqDate);
		params.addValue("emailAddr", emailAddr);
		params.addValue("smsNo", smsNo);
		params.addValue("locale", locale);
		params.addValue("lob", LtsBackendConstant.LOB_LTS);
		params.addValue("seqNo", seqNo);
		params.addValue("username", username);
		params.addValue("method", method);
		params.addValue("formPrintReqId", formPrintReqId);
		
		try {
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(SQL_INSERT_EMAIL_REQ, params);
	    } catch (Exception e) {
			logger.error("Exception caught in insertEmailReq():", e);
			throw new DAOException(e.getMessage(), e);
		}
    }
    
    public int getNextSeq(String orderId, String templateId) {
		if (logger.isInfoEnabled()) {
			logger.info("getNextSeq @ AmendLtsDAO is called");
		}
		String sql = "select nvl(max(SEQ_NO), 0) + 1 from bomweb_ord_email_req where ORDER_ID = :orderId ";
		if (logger.isInfoEnabled()) {
			logger.info("getNextSeq() @ AmendLtsDAO: " + sql);
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("templateId", templateId);
		return this.simpleJdbcTemplate.queryForInt(sql, params);
	}
    
    public void updateItemUpdateStatus(String orderId, String pUser, String status, String dtlId, String batchSeq, List<String> amendCat) throws DAOException {		
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("status", status);
			paramSource.addValue("user", pUser);
			paramSource.addValue("orderId", orderId);
			paramSource.addValue("dtlId", dtlId);
			paramSource.addValue("batchSeq", batchSeq);
			paramSource.addValue("amendCat", amendCat);
			
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(SQL_UPDATE_ITEM_UPDATE_STATUS, paramSource);
			
//			simpleJdbcTemplate.update(SQL_UPDATE_ITEM_UPDATE_STATUS, status, pUser, orderId, dtlId, batchSeq);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in updateApprovalStatus - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public void updateBatchUpdateStatus(String orderId, String pUser, String status, String dtlId, String batchSeq) throws DAOException {		
		try {			
			simpleJdbcTemplate.update(SQL_UPDATE_AMEND_BATCH_UPDATE_STATUS, status, pUser, orderId, dtlId, batchSeq);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in updateBatchUpdateStatus - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
    public void updateErrorMsg(String orderId, String pUser, String errorMsg, String batchSeq, String dtlId) throws DAOException {		
  		try {			
  			StringBuilder sqlSB = new StringBuilder(SQL_UPDATE_AMEND_ERROR_MSG);
  			

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("user", pUser);
			paramSource.addValue("orderId", orderId);
			paramSource.addValue("batchSeq", batchSeq);
			paramSource.addValue("errorMsg", errorMsg);
  			
  			if(StringUtils.isNotBlank(dtlId)){
  				sqlSB.append(" AND DTL_ID = :dtlId ");
  				paramSource.addValue("dtlId", dtlId);
  			}
  			
  			simpleJdbcTemplate.update(sqlSB.toString(), paramSource);
  		} catch (EmptyResultDataAccessException erdae) {
  			throw new AppRuntimeException(erdae);
  		} catch (Exception e) {
  			logger.error("Error in updateErrorMsg - ", e);
  			throw new DAOException(e.getMessage(), e);
  		}
  	}
    
	public OrderLtsAmendDTO retrieveApprovedAmendLog(String orderId, String dtlId) throws DAOException {
		
		ParameterizedRowMapper<OrderLtsAmendDTO> mapper = new ParameterizedRowMapper<OrderLtsAmendDTO>(){
			public OrderLtsAmendDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderLtsAmendDTO result = new OrderLtsAmendDTO();
				result.setOrderId((String) rs.getString("ORDER_ID"));
				result.setAppContactNum((String) rs.getString("APP_CONTACT_NUM"));
				result.setAppName((String) rs.getString("APP_NAME"));
				result.setAppRelationCd((String) rs.getString("APP_RELATION_CD"));
				result.setApprovalStatus((String) rs.getString("APPROVAL_STATUS"));
				result.setRequireApproval((String) rs.getString("REQUIRE_APPROVAL"));
				result.setSalesCd((String) rs.getString("SALES_CD"));
				result.setSalesChannel((String) rs.getString("SALES_CHANNEL"));
				result.setSalesContactNum((String) rs.getString("SALES_CONTACT_NUM"));
				result.setShopCd((String) rs.getString("SHOP_CD"));
				result.setStaffNum((String) rs.getString("STAFF_NUM"));
				result.setDtlId((String) rs.getString("DTL_ID"));
				result.setBatchSeq((String) rs.getString("BATCH_SEQ"));
				result.setCreateDate((String) rs.getString("CREATE_DATE"));
				result.setEmailAddr((String) rs.getString("EMAIL_ADDR"));
				result.setSmsNo((String) rs.getString("SMS_NO"));
				return result;
			}
		};

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("dtlId", dtlId);
		
		try{
	    	return simpleJdbcTemplate.queryForObject(SQL_RETRIEVE_APPROVED_AMEND, mapper, params);
	    } catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in retrieveApprovedAmendLog - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	

	public List<OrderLtsAmendDetailDTO> retrieveApprovedAmendDetailLog(String orderId, String dtlId) throws DAOException {
		
		ParameterizedRowMapper<OrderLtsAmendDetailDTO> mapper = new ParameterizedRowMapper<OrderLtsAmendDetailDTO>(){
			public OrderLtsAmendDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderLtsAmendDetailDTO result = new OrderLtsAmendDetailDTO();
				result.setOrderId((String) rs.getString("ORDER_ID"));
				result.setAmendCat((String) rs.getString("AMEND_CAT"));
				result.setAmendItem((String) rs.getString("AMEND_ITEM"));
				result.setAmendSubCat((String) rs.getString("AMEND_SUB_CAT"));
				result.setAmendValue((String) rs.getString("AMEND_VALUE"));;
				result.setItemSeq((String) rs.getString("ITEM_SEQ"));
				result.setItemSubSeq((String) rs.getString("ITEM_SUB_SEQ"));
				return result;
			}
		};

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("dtlId", dtlId);
		
		try{
	    	return simpleJdbcTemplate.query(SQL_RETRIEVE_APPROVED_AMEND_DTL, mapper, params);
	    } catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in retrieveApprovedAmendDetailLog - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
    public String getNextBatchSeq(String orderId) throws DAOException {
		
	    try{
	    	return simpleJdbcTemplate.queryForObject(SQL_GET_NEXT_BATCH_SEQ, String.class, orderId);//, dtlId);
	    } catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getNextBatchSeq - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public String getNextItemSeq(String orderId, String dtlId, String batchSeq) throws DAOException {
		
	    try{
	    	return simpleJdbcTemplate.queryForObject(SQL_GET_NEXT_ITEM_SEQ, String.class, orderId, dtlId, batchSeq);
	    } catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getNextItemSeq - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

    public void updateApprovalStatus(String orderId, String pUser, String status) throws DAOException {		
		try {			
			simpleJdbcTemplate.update(SQL_UPDATE_APPROVAL_STATUS, status, pUser, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in updateApprovalStatus - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public String[] getEmailTemplate(String templateId, String locale) throws DAOException{
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>(){
			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String desc = (String) rs.getString("TEMPLATE_DESC");
				String subject = (String) rs.getString("TEMPLATE_SUBJECT");
				String content = (String) rs.getString("TEMPLATE_CONTENT");
				return new String[]{desc, subject, content};
				
			}
		};

    	MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("templateId", templateId);
		params.addValue("locale", locale);
		
		try{
	    	return simpleJdbcTemplate.queryForObject(SQL_GET_EMAIL_TEMPLATE_SUBJECT_CONTENT, mapper, params);
	    } catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in retrieveApprovedAmendDetailLog - ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		
    }
    
}
