package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.AuthorizeDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;

public class AuthorizeDAO extends BaseDAO{
	
	public List<String> getAttr(String html) throws DAOException {
		
		String sql = "select distinct attribute from bomweb_function "
				 + "where html = :html ";
		
		MapSqlParameterSource mapSql = new MapSqlParameterSource();
		mapSql.addValue("html", html);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("attribute");
			}
		};
		
		try {
			logger.debug("getAttr() :"+  sql);
			
			return simpleJdbcTemplate.query(sql, mapper, mapSql);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getAttr()");
		} catch (Exception e) {
			logger.info("Exception caught in getAttr():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	public List<AuthorizeDTO> getRoleLkup(String staffId, String category, String channelId) throws DAOException {
		
		String sql = "select s.staff_id users, f.html, f.attribute from  "
				 + "bomweb_staff_function_assgn s "
				 + "left join  "
				 + "bomweb_function f "
				 + "on f.function_cd = s.function_cd "
				 + "where staff_id = :staffId "
				 + "and sysdate between s.start_date and nvl(s.end_date, sysdate) "
				 + "union "
				 + "select r.category, f.html, f.attribute from  "
				 + "bomweb_role_function_assgn r "
				 + "left join  "
				 + "bomweb_function f "
				 + "on f.function_cd = r.function_cd "
				 + "where category = :category "
				 + "and sysdate between r.start_date and nvl(r.end_date, sysdate) "
				 + "and r.channel_cd = :channelId ";
		
		MapSqlParameterSource mapSql = new MapSqlParameterSource();
		mapSql.addValue("staffId", staffId);
		mapSql.addValue("channelId", channelId);
		mapSql.addValue("category", category);
		
		ParameterizedRowMapper<AuthorizeDTO> mapper = new ParameterizedRowMapper<AuthorizeDTO>() {
			public AuthorizeDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AuthorizeDTO dto = new AuthorizeDTO();
				dto.setUsers(rs.getString("users"));
				dto.setHtml(rs.getString("html"));
				dto.setAttribute(rs.getString("attribute"));
				return dto;
			}
		};
		
		try {
			logger.debug("getRoleLkup() :"+  sql);
			
			return simpleJdbcTemplate.query(sql, mapper, mapSql);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getRoleLkup()");
		} catch (Exception e) {
			logger.info("Exception caught in getRoleLkup():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	
	private static final String getAuthorizeRightSQL = 
			"SELECT DECODE(COUNT(*),0,'N','Y') auth_right " +
					"FROM bomweb_auth_right " +
					"WHERE authorized_action=:action " +
					"AND channel_id         = :channelId " +
					"AND category          IN (:sbCategory, :bomCategory) " +
					"AND sysdate BETWEEN start_date AND NVL(end_date,sysdate)";
	
	public String getAuthorizeRight(String authorizedAction,String channelId, String sbCategory ,String bomCategory) throws DAOException {
		
		try {
		
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("action", authorizedAction);
			params.addValue("channelId", channelId);
			params.addValue("sbCategory", sbCategory);
			params.addValue("bomCategory", bomCategory);
			String authRight = (String) simpleJdbcTemplate.queryForObject(getAuthorizeRightSQL,String.class,params);

			if (logger.isDebugEnabled()) {
				logger.debug("SQL: " + getAuthorizeRightSQL);
				logger.debug("authRight: " + authRight);
			}
			return authRight;

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		}
		catch (Exception e) {
			logger.error("Exception caught in getAuthorizeRight() @ AuthorizeDAO", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	private static final String getAuthorizeCategoryTextSQL = 
			"SELECT REPLACE(LISTAGG(category, ' / ') WITHIN GROUP ( " +
					"ORDER BY category),'_',' ') AS category " +
					"FROM bomweb_auth_right " +
					"WHERE authorized_action = :action " +
					"AND channel_id          = :channelId " +
					"GROUP BY authorized_action, " +
					"  channel_id";
	public String getAuthorizeCategoryText(String authorizedAction,String channelId) throws DAOException {
		
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("action", authorizedAction);
			params.addValue("channelId", channelId);
		
			String authRight = (String) simpleJdbcTemplate.queryForObject(getAuthorizeCategoryTextSQL,String.class,params);

			if (logger.isDebugEnabled()) {
				logger.debug("SQL: " + getAuthorizeCategoryTextSQL);
				logger.debug("AuthorizeCategoryText: " + authRight);
			}
			return authRight;

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		}
		catch (Exception e) {
			logger.error("Exception caught in getAuthorizeCategoryText() @ AuthorizeDAO", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	private static final String getSalesInfoDTOByIDSQL = 
			"SELECT bsa.channel_id, " +
					"  bsa.staff_id, " +
					"  bsa.channel_cd, " +
					"  bsp.category SB_CATEGORY " +
					"FROM bomweb_sales_assignment bsa, " +
					"  bomweb_sales_profile bsp " +
					"WHERE bsa.staff_id=:staffId " +
					"AND bsa.staff_id  =bsp.staff_id " +
					"AND sysdate BETWEEN bsa.start_date AND NVL(bsa.end_date,sysdate) " +
					"AND sysdate BETWEEN bsp.start_date AND NVL(bsp.end_date,sysdate)";
	
	public List<SalesInfoDTO> getSalesInfoDTOByID(String staffId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getSalesInfoDTOByID() is called");
		}
		List<SalesInfoDTO> itemList = Collections.emptyList();
		
		ParameterizedRowMapper<SalesInfoDTO> mapper = new ParameterizedRowMapper<SalesInfoDTO>() {
			
			public SalesInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				SalesInfoDTO temp = new SalesInfoDTO();
				temp.setCategory(rs.getString("SB_CATEGORY"));
				temp.setChannelId(rs.getString("CHANNEL_ID"));
				temp.setChannelCd(rs.getString("CHANNEL_CD"));
				temp.setStaffId(rs.getString("STAFF_ID"));
				return temp;
			}
		};
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getSalesInfoDTOByID() @ AuthorizeDAO: " + getSalesInfoDTOByIDSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", staffId);		
			itemList = simpleJdbcTemplate.query(getSalesInfoDTOByIDSQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getSalesInfoDTOByID()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getSalesInfoDTOByID():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		
		return itemList;
	}
	
	private static String updateAdditionalApprovalLogSQL = "update BOMWEB_APPROVAL_LOG AL\n" +
			"   set AL.ORDER_ID = :orderId,\n" + 
			"       AL.ORDER_NATURE = :orderNature,\n" + 
			"       AL.RP_GROSS_PLAN_FEE = :rpGrossPlanFee,\n" + 
			"       AL.BASKET_ID = :basketId,\n" + 
			"       AL.MIN_VAS = :minVas\n" + 
			" where AL.AUTHORIZED_ID = :authorizedId";
	
	
	public int updateAdditionalApprovalLog(String authorizedId, String orderId, String orderNature, String rpGrossPlanFee, String basketId, double minVas) throws DAOException {
		logger.info("updateAdditionalCosApprovalLog is called");
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();			
			params.addValue("authorizedId", authorizedId);
			params.addValue("orderId", orderId);
			params.addValue("orderNature", orderNature);
			params.addValue("rpGrossPlanFee", rpGrossPlanFee);
			params.addValue("basketId", basketId);
			params.addValue("minVas", minVas);
			
			return simpleJdbcTemplate.update(updateAdditionalApprovalLogSQL,params);

		} catch (Exception e) {
			logger.error("Exception caught in updateAdditionalApprovalLog()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	

	private static String updatelApprovalLogOrderIdSQL = "update BOMWEB_APPROVAL_LOG AL\n" +
			"   set AL.ORDER_ID = :orderId\n" + 

			" where AL.AUTHORIZED_ID = :authorizedId";
	
	
	public int updateApprovalLogOrderId(String authorizedId, String orderId) throws DAOException {
		logger.info("updateApprovalLogOrderId is called");
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();			
			params.addValue("authorizedId", authorizedId);
			params.addValue("orderId", orderId);
		
			
			return simpleJdbcTemplate.update(updatelApprovalLogOrderIdSQL,params);

		} catch (Exception e) {
			logger.error("Exception caught in updateApprovalLogOrderId()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
}
