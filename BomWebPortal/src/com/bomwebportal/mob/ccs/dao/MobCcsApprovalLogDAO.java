package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.ApprovalLogDTO;
import com.bomwebportal.util.Utility;

public class MobCcsApprovalLogDAO extends BaseDAO {
	private static String getNextAuthorizedIdSQL = "SELECT to_char(BOMWEB_AUTH_ID.nextval, 'FM0000000000') NEXT_VAL FROM dual";

	public String getNextAuthorizedId() throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getNextAuthorizedId() is called");
		}
		
		List<String> itemList = null;
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getNextAuthorizedId() @ ApprovalLogDAO: " + getNextAuthorizedIdSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getNextAuthorizedIdSQL, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("NEXT_VAL");
				}
			});
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getNextAuthorizedId()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getNextAuthorizedId():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}
	
	private static String insertApprovalLogDTOSQL = "INSERT INTO" +
			" bomweb_approval_log" +
			" (" +
			" AUTHORIZED_ID" +
			" , ORDER_ID" +
			" , AUTHORIZED_DATE" +
			" , AUTHORIZED_BY" +
			" , AUTHORIZED_ACTION" +
			" , SHOP_CODE" +
			" , LAST_UPD_BY" +
			" , CREATE_DATE" +
			" , LAST_UPD_DATE" +
			" , REASON_TYPE" +
			" , REASON_CD" +
			" , REMARK" +
			" , ORDER_NATURE" +
			" , BASKET_ID" +
			" , MIN_VAS" +
			" , RP_GROSS_PLAN_FEE" +
			" ) VALUES (" +
			" :authorizedId" +
			" , :orderId" +
			" , sysdate" +
			" , :authorizedBy" +
			" , :authorizedAction" +
			" , :shopCode" +
			" , :lastUpdBy" +
			" , sysdate" +
			" , sysdate" +
			" , :reasonType" +
			" , :reasonCd" +
			" , :remark" +
			" , :orderNature" +
			" , :basketId" +
			" , :minVas" +
			" , :rpGrossPlanFee" +
			" )";
	public int insertApprovalLogDTO(ApprovalLogDTO dto) throws DAOException {
		System.out.println(Utility.toPrettyJson(dto));
		if (logger.isInfoEnabled()) {
			logger.info("insertApprovalLogDTOSQL @ ApprovalLogDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertApprovalLogDTOSQL() @ ApprovalLogDAO: " + insertApprovalLogDTOSQL);
			}
			
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("authorizedId", dto.getAuthorizedId());
			params.addValue("orderId", dto.getOrderId());
			//params.addValue("authorizedDate", dto.getAuthorizedDate());
			params.addValue("authorizedBy", dto.getAuthorizedBy());
			params.addValue("authorizedAction", dto.getAuthorizedAction());
			params.addValue("shopCode", dto.getShopCode());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			
			params.addValue("reasonType", dto.getReasonType());
			params.addValue("reasonCd", dto.getReasonCd());
			params.addValue("remark", dto.getRemark());
			
			params.addValue("orderNature", dto.getOrderNature());
			params.addValue("basketId", dto.getBasketId());
			params.addValue("minVas", dto.getMinVas());
			params.addValue("rpGrossPlanFee", dto.getRpGrossPlanFee());
			
			//params.addValue("createDate", dto.getCreateDate());
			//params.addValue("lastUpdDate", dto.getLastUpdDate());
			return simpleJdbcTemplate.update(insertApprovalLogDTOSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in insertApprovalLogDTOSQL()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	String updateApprovalLogSQL = 
		"update BOMWEB_APPROVAL_LOG AL\n" +
		"   set AL.ORDER_ID      = :orderId,\n" + 
		"       AL.LAST_UPD_BY   = :lastUpdBy,\n" + 
		"       AL.LAST_UPD_DATE = sysdate\n" + 
		" where AL.AUTHORIZED_ID = :authorizedId";

	public int updateApprovalLog(String orderId, String authorizedId, String lastUpdBy) throws DAOException {
		logger.info("updateApprovalLog is called");
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("authorizedId", authorizedId);
			params.addValue("lastUpdBy", lastUpdBy);
			
			return simpleJdbcTemplate.update(updateApprovalLogSQL,params);

		} catch (Exception e) {
			logger.error("Exception caught in updateApprovalLog()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	private static String isApprovalSQL = "select authorized_id from "
			+ "bomweb_approval_log where order_id=? and authorized_action=? and rownum=1";//add and rownum=1 add by wilson 20120523
	
	public boolean isApproval(String orderId, String actionType) throws DAOException{
		logger.debug(" isApproval is called");

    	String id = "";
    	
    	boolean result = false;

    	try {
    	    logger.debug("isApproval() @ ArrayList<String>: " + isApprovalSQL);

    	    id = (String) simpleJdbcTemplate.queryForObject(
    	    		isApprovalSQL, String.class, orderId, actionType);
    	    
    	    if(StringUtils.isNotEmpty(id)){
    	    	result = true;
    	    }
    	} catch (EmptyResultDataAccessException erdae) {
    	    logger.info("EmptyResultDataAccessException in isApproval()");

    	    result = false;
    	} catch (Exception e) {
    	    logger.info("Exception caught in isApproval():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    	return result;
	}
	
	private static String isApprovedBasketSQL = 
			"select basket_id \n" +
			" from BOMWEB_APPROVAL_LOG AL\n" +
			" where AL.ORDER_ID = :orderId\n" + 
			" and AL.BASKET_ID = :basketId" +
			" and AL.AUTHORIZED_ACTION = :authorizedAction";
	
	public boolean isApprovedBasket(String orderId, String basketId, String authorizedAction) throws DAOException {
		logger.debug("isApprovedBasket is called");
		try {
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("basket_id");
				}
			};
			
			MapSqlParameterSource params = new MapSqlParameterSource();			
			params.addValue("orderId", orderId);
			params.addValue("basketId", basketId);
			params.addValue("authorizedAction", authorizedAction);
			
			List<String> result = simpleJdbcTemplate.query(isApprovedBasketSQL,mapper,params);
			if (CollectionUtils.isNotEmpty(result)) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Exception caught in isApprovedBasket()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return false;
	}
}
