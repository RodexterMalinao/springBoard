package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class OrderDetailDAO extends BaseDAO {
	
	private static final String SQL_GET_ORDER_PREFIX = 
			"select ord_prefix " +
			"from bomweb_order_prefix_v " +
			"where staff_id = ?";
	
	private static final String SQL_GET_SHOP_SEQ = 
			"select lpad(eye_seq_no, 8, '0') " +
			"from bomweb_shop " +
			"where shop_cd = ? " +
			"for update";
	
	private static final String SQL_GET_SHOP_BOC = 
			"select boc " +
			"from bomweb_shop " +
			"where shop_cd = ?";
	
	private static final String SQL_GET_CALL_CENTRE_ORD_SEQ =
			"select lpad(LTS_CALL_CENTRE_ORD_SEQ.nextval, 8, '0') " +
			"from dual";
	
	private static final String SQL_GET_PREMIER_TEAM_ORD_SEQ =
			"select lpad(LTS_PREMIER_TEAM_ORD_SEQ.nextval, 8, '0') " +
			"from dual";
	
	private static final String SQL_GET_DIRECT_SALES_ORD_SEQ =
		"select lpad(LTS_DIRECT_SALES_ORD_SEQ.nextval, 8, '0') " +
		"from dual";
	
	private static final String SQL_INCREMENT_EYE_ORDER_SEQ = 
			"update bomweb_shop " +
			"set eye_seq_no = eye_seq_no + 1, last_upd_date = sysdate, last_upd_by = ? " +
			"where shop_cd = ?";
	
	private static final String SQL_UPDATE_SIGNOFF_DATE =
			"update bomweb_order " + 
			"set sign_off_date = sysdate " +
			"where order_id = ?";
	
	private static final String SQL_UPDATE_TERM_DATE =
			"update bomweb_payment " + 
			"set term_date = (select sign_off_date from bomweb_order where order_id = ?) " +
			"where order_id = ? " +
			"and term_cd is not null " +
			"and (pay_mtd_type = 'A' or pay_mtd_type = 'C')";
	
	private static final String SQL_GET_SIGNOFF_DATE = 
			"select to_char(sign_off_date, 'dd/mm/yyyy hh:mm') " + 
			"from bomweb_order " +
			"where order_id = ?";
	
	private static final String SQL_GET_MAX_DOC_SEQ = 
			"select nvl(max(seq_num), 0) " +
			"from bomweb_all_ord_doc " +
			"where order_id = ? " +
			"and doc_type = ?";
	
	private static final String SQL_CLEAR_CUST_NOT_MATCH_IND = 
			"update bomweb_order_service_lts " +
			"set cust_name_not_match = null " +
			"where order_id = ?";
	
	private static final String SQL_GET_USER_BOC = 
			"select boc from bomweb_sales_assignment " +
			"where staff_id = ? and start_date <= sysdate and nvl(end_date, to_date('31129999', 'ddmmyyyy')) >= sysdate";
	
	private static final String SQL_GET_ORDER_TYPE = 
			"select order_type from bomweb_order " +
			"where order_id = ?";
	
	private static final String SQL_UPDATE_MUST_QC_IND =
			"update bomweb_order_lts " + 
			"set must_qc = ?, last_upd_date = sysdate, last_upd_by = ? " +
			"where order_id = ? ";
	
	private static final String SQL_UPDATE_WAIVE_QC_STATUS =
			"update bomweb_order_lts " + 
			"set waive_qc_status = ?, last_upd_date = sysdate, last_upd_by = ? " +
			"where order_id = ? ";
		
	private static final String SQL_GET_WAIVE_QC_STATUS =
			"select waive_qc_status from bomweb_order_lts " +
			"where order_id = ? ";
	
	private static final String SQL_GET_WAIVE_QC_CODE =
		"select waive_qc_cd from bomweb_order_lts " +
		"where order_id = ? ";
	
	public String getOrderType(String pOrderId) throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_ORDER_TYPE, String.class, pOrderId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			throw new DAOException(e.getMessage() +  "Fail to getOrderType.", e);
		}
	}
	
	public String orderIdPrefix(String pUser) throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_ORDER_PREFIX, String.class, pUser);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			throw new DAOException(e.getMessage() + " User ID " + pUser + " not found.  Fail to orderIdPrefix.", e);
		}
	}
	
	public String getShopNextOrderSeq(String pShopCd, String pUser) throws DAOException {
				
		try {
			String shopSeq = simpleJdbcTemplate.queryForObject(SQL_GET_SHOP_SEQ, String.class, pShopCd);
			simpleJdbcTemplate.update(SQL_INCREMENT_EYE_ORDER_SEQ, pUser, pShopCd);
			return shopSeq;
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			throw new DAOException(e.getMessage() + " Shop cd " + pShopCd + " not found.  Fail to getShopSeq.", e);
		}
	}
	
	public String getUserBoc(String pUserId) throws DAOException {
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_USER_BOC, String.class, pUserId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			throw new DAOException(e.getMessage() + " User ID " + pUserId + " not found.  Fail to getUserBoc.", e);
		}
	}
	
	public String getShopBoc(String pShopCd) throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_SHOP_BOC, String.class, pShopCd);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			throw new DAOException(e.getMessage() + " Shop cd " + pShopCd + " not found.  Fail to getShopBoc.", e);
		}
	}
	
	public String getCallCentreOrderSeq() throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_CALL_CENTRE_ORD_SEQ, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			throw new DAOException(e.getMessage() +  " Fail to getCallCentreOrderSeq.", e);
		}
	}
	
	public String getPremierTeamOrderSeq() throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_PREMIER_TEAM_ORD_SEQ, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			throw new DAOException(e.getMessage() +  " Fail to getPremierTeamOrderSeq.", e);
		}
	}
	
    public String getDirectSalesOrderSeq() throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_DIRECT_SALES_ORD_SEQ, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			throw new DAOException(e.getMessage() +  " Fail to getDirectSalesOrderSeq.", e);
		}
	}
	
	public String updateSignoffDate(String pOrderId) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_SIGNOFF_DATE, pOrderId);
			return simpleJdbcTemplate.queryForObject(SQL_GET_SIGNOFF_DATE, String.class, pOrderId);
		} catch (Exception e) {
			logger.error("Error in updateSignoffDate()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
    public String updateTermDate(String pOrderId) throws DAOException {
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_TERM_DATE, pOrderId, pOrderId);
			return getTermDate(pOrderId);
		} catch (Exception e) {
			logger.error("Error in updateTermDate()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
	public String getTermDate(String pOrderId)
			throws DAOException{
		List<String> list = new ArrayList<String>();
		String SQL = "select distinct to_char(term_date, 'dd/mm/yyyy hh:mi') as TERM_DATE " + 
				     "from bomweb_payment " +
				     "where order_id = '" + pOrderId + "' " +
				     "and term_cd is not null " +
				     "and (pay_mtd_type = 'A' or pay_mtd_type = 'C')";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String termDate = rs.getString("TERM_DATE");
				return termDate;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
			if(list.size() > 0){
				return list.get(0);
			}else{
				return null;
			}

		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getTermDate()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int getMaxDocSeq(String pOrderId, String pDocType) throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForInt(SQL_GET_MAX_DOC_SEQ, pOrderId, pDocType);
		} catch (Exception e) {
			logger.error("Error in getMaxDocSeq()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void clearCustNotMatchInd(String pOrderId) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_CLEAR_CUST_NOT_MATCH_IND, pOrderId);
		} catch (Exception e) {
			logger.error("Error in clearCustNotMatchInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	// temp generate reminder msg
	private static final String SQL_GET_2C_PLAN = 
			"select id " +
			"from w_item " +
			"where description like '%Plan 2C%'";
	
	private static Set<String> plan2cSet = null; 
	
	public Set<String> getPlan2C() {
		
		if (plan2cSet != null) {
			return plan2cSet;
		}
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("ID");
			}
		};
		try {
			List<String> plan2cList = this.simpleJdbcTemplate.query(SQL_GET_2C_PLAN, mapper);
			
			plan2cSet = new HashSet<String>(plan2cList);
		} catch (Exception e) {
			logger.error("Error in getPlan2C()", e);
		}
		return plan2cSet;
	}
	
    public void updateMustQCInd(String pOrderId, String mustQcInd, String pUserId) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_MUST_QC_IND, mustQcInd, pUserId, pOrderId);
		} catch (Exception e) {
			logger.error("Error in updateMustQCInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public String getWaiveQcStatus(String pOrderId) throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_WAIVE_QC_STATUS, String.class, pOrderId);
		} catch (Exception e) {
			logger.error("Error in getWaiveQcStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public void updateWaiveQcStatus(String pOrderId, String waiveQcStatus, String pUserId) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_WAIVE_QC_STATUS, waiveQcStatus, pUserId, pOrderId);
		} catch (Exception e) {
			logger.error("Error in updateWaiveQcStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public String getWaiveQcCode(String pOrderId) throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_WAIVE_QC_CODE, String.class, pOrderId);
		} catch (Exception e) {
			logger.error("Error in getWaiveQcCode()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

}
