package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.LtsOrderSearchDTO;

public class LtsOrderSearchDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	
	public List<LtsOrderSearchDTO> searchLtsOnlineOrder(String orderId,
			String idDocType, String idDocNum, String srvNum, String email) throws DAOException{
		
		ParameterizedRowMapper<LtsOrderSearchDTO> mapper = getMapper();

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct");
		sql.append("   BO.LOB,");
		sql.append("   BO.ORDER_ID,");
		sql.append("   BO.OCID,");
		sql.append("   BC.ID_DOC_TYPE,");
		sql.append("   BC.ID_DOC_NUM,");
		sql.append("   BC.LAST_NAME || ' ' || BC.FIRST_NAME CUST_NAME,");
		sql.append("   BOLS.SB_STATUS ORDER_STATUS,");
		sql.append("   BOLS.REA_CD STATUS_REA_CD,");
		sql.append("   BO.APP_DATE,");
		sql.append("   BOS.SRV_NUM,");
		sql.append("   BO.ESIG_EMAIL_ADDR EMAIL,");
		sql.append("   BO.ERR_MSG,");
		sql.append("   BOER.TEMPLATE_ID EMAIL_TEMPLATE_ID");
		sql.append(" from ");
		sql.append("   BOMWEB_ORDER               BO,");
		sql.append("   BOMWEB_CUSTOMER            BC,");
		sql.append("   BOMWEB_ORDER_SERVICE       BOS,");
		sql.append("   BOMWEB_ORDER_LATEST_STATUS BOLS,");
		sql.append("   BOMWEB_ACCT                BA,");
		sql.append("   BOMWEB_ORD_EMAIL_REQ       BOER,");
		sql.append("   BOMWEB_SERVICE_ACCT_ASSGN  BSAA");
		sql.append(" where BO.LOB = 'LTS'");
		sql.append(" and BO.SHOP_CD = 'SBO'");
		sql.append(" and BO.ORDER_ID = BOS.ORDER_ID");
		sql.append(" and BO.ORDER_ID = BOLS.ORDER_ID");
		sql.append(" and BOS.DTL_ID = BOLS.DTL_ID");
		sql.append(" and BOS.TYPE_OF_SRV = 'TEL'");
		sql.append(" and BO.ORDER_ID = BA.ORDER_ID");
//		sql.append(" and BOS.ACCT_NO = BA.ACCT_NO");
		sql.append(" and BC.ORDER_ID = BA.ORDER_ID");
		sql.append(" and BC.CUST_NO = BA.CUST_NO");
		sql.append(" and BO.ORDER_ID = BA.ORDER_ID");
		sql.append(" and BO.ORDER_ID = BOER.ORDER_ID (+) ");
		sql.append(" and BOS.ORDER_ID = BSAA.ORDER_ID ");
		sql.append(" and BOS.DTL_ID = BSAA.DTL_ID ");
		sql.append(" and BSAA.CHRG_TYPE = 'R' ");
		sql.append(" AND ((bsaa.action = 'I' OR bsaa.action = ' ') OR (BO.ORDER_TYPE = 'SBD' AND bsaa.action = 'O')) ");
		sql.append(" AND bsaa.acct_no = ba.acct_no ");
		
		try {
			
			if (StringUtils.isNotBlank(orderId)) {
				sql.append(" and BO.ORDER_ID = :orderId");
				params.addValue("orderId", orderId.trim());
			}
			
			if (StringUtils.isNotBlank(idDocType) && StringUtils.isNotBlank(idDocNum)) {
				sql.append(" and BC.ID_DOC_TYPE = :idDocType");
				sql.append(" and BC.ID_DOC_NUM = :idDocNum");
				params.addValue("idDocType", idDocType.trim());
				params.addValue("idDocNum", idDocNum.trim());
			}
			
			if (StringUtils.isNotBlank(srvNum)) {
				sql.append(" and BOS.SRV_NUM = :srvNum");
				params.addValue("srvNum", srvNum.trim());
			}
			
			if (StringUtils.isNotBlank(email)) {
				sql.append(" and BO.ESIG_EMAIL_ADDR = :email");
				params.addValue("email", email.trim());
			}
			
			return simpleJdbcTemplate.query(sql.toString(), mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("searchLtsOnlineOrder EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in searchLtsOnlineOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		
	}
	
	public List<LtsOrderSearchDTO> searchLtsCcOrder(String orderId,
			String idDocType, String idDocNum, String srvNum, String email, String[] channelId, 
			String staffNum, String salesTeam, String[] salesCenter, String salesChannel, String orgStaffId,
			String startDate, String endDate, String status, String srdStartDate, String srdEndDate ) throws DAOException{
		
		ParameterizedRowMapper<LtsOrderSearchDTO> mapper = getCcOrderMapper();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT ");
		sql.append("  BOLSV.LOB,   ");
		sql.append("  BOLSV.ORDER_ID,   ");
		sql.append("  BOLSV.OCID,   ");
		sql.append("  BOLSV.ID_DOC_TYPE,   ");
		sql.append("  BOLSV.ID_DOC_NUM,   ");
		sql.append("  BOLSV.CUST_NAME,   ");
		sql.append("  BOLSV.SB_STATUS ORDER_STATUS,   ");
		sql.append("  BOLSV.REA_CD STATUS_REA_CD, ");
		sql.append("  BOLSV.APP_DATE,   ");
		sql.append("  BOLSV.SRV_NUM,   ");
		sql.append("  BOLSV.NEW_SRV_NUM,");
		sql.append("  BOLSV.ESIG_EMAIL_ADDR EMAIL,   ");
		sql.append("  BOLSV.ERR_MSG,   ");
		sql.append("  BOLSV.STAFF_NUM,   ");
		sql.append("  BOLSV.SHOP_CD,   ");
		sql.append("  BOLSV.CENTRE_CD,   ");
		sql.append("  BOLSV.CREATE_CHANNEL,   ");
		sql.append("  BOLSV.CREATE_BY   ");
		//sql.append("  '0' AS EMAIL_TEMPLATE_ID ");
		sql.append("FROM ");
		sql.append(" BOMWEB_ORDER_LTS_SEARCH_V  BOLSV "); 
		
		try {
			List<String> criteriaSql = new ArrayList<String>();
			
			if (StringUtils.isNotBlank(orderId)) {
				criteriaSql.add(" BOLSV.ORDER_ID = :orderId ");
				params.addValue("orderId", orderId);
			}
			
			if (StringUtils.isNotBlank(idDocType) && StringUtils.isNotBlank(idDocNum)) {
				criteriaSql.add(" BOLSV.ID_DOC_TYPE = :idDocType");
				criteriaSql.add(" BOLSV.ID_DOC_NUM = :idDocNum");
				params.addValue("idDocType", idDocType);
				params.addValue("idDocNum", idDocNum);
			}
			
			if (StringUtils.isNotBlank(srvNum)) {
				criteriaSql.add(" (BOLSV.SRV_NUM = :srvNum OR BOLSV.NEW_SRV_NUM = :srvNum)");
				params.addValue("srvNum", srvNum);
			}
			
			if (StringUtils.isNotBlank(email)) {
				criteriaSql.add(" UPPER(BOLSV.ESIG_EMAIL_ADDR) = UPPER(:email)");
				params.addValue("email", email);
			}
			
			if (StringUtils.isNotBlank(staffNum) && StringUtils.isNotBlank(orgStaffId)) {
				criteriaSql.add(" ((BOLSV.STAFF_NUM = :orgStaffId OR BOLSV.STAFF_NUM = :staffNum) " +
						"OR (BOLSV.CREATE_BY = :orgStaffId OR BOLSV.CREATE_BY = :staffNum))");
				params.addValue("orgStaffId", orgStaffId);
				params.addValue("staffNum", staffNum);
			}else if(StringUtils.isNotBlank(staffNum)){
				criteriaSql.add(" (BOLSV.STAFF_NUM = :staffNum OR BOLSV.CREATE_BY = :staffNum)");
				params.addValue("staffNum", staffNum);
			}
			
			if (StringUtils.isNotBlank(salesTeam)) {
				criteriaSql.add(" BOLSV.SHOP_CD = :salesTeam");
				params.addValue("salesTeam", salesTeam);
			}
			
			if (StringUtils.isNotBlank(salesChannel)) {
				criteriaSql.add(" BOLSV.CREATE_CHANNEL = :salesChannel");
				params.addValue("salesChannel", salesChannel);
			}
			
			if(StringUtils.isNotBlank(startDate)){
				criteriaSql.add(" BOLSV.APP_DATE >= TO_DATE(:startDate, 'DD/MM/YYYY')");
				params.addValue("startDate", startDate);
			}
			
			if(StringUtils.isNotBlank(endDate)){
				criteriaSql.add(" BOLSV.APP_DATE < TO_DATE(:endDate, 'DD/MM/YYYY')+1");
				params.addValue("endDate", endDate);
			}
			
			if(StringUtils.isNotBlank(status)){
				criteriaSql.add(" BOLSV.SB_STATUS = :status");
				params.addValue("status", status); 
			}
			
			if(channelId != null && channelId.length>0){
				criteriaSql.add(" BOLSV.CREATE_CHANNEL IN (" +
						" SELECT DISTINCT CHANNEL_CD FROM BOMWEB_SALES_ASSIGNMENT" +
						" WHERE CHANNEL_ID in (:channelId) )");
				params.addValue("channelId", Arrays.asList(channelId)); 
			}
			
			if(salesCenter != null && salesCenter.length>0){
				criteriaSql.add(" BOLSV.CENTRE_CD IN (:salesCenter)");
				params.addValue("salesCenter", Arrays.asList(salesCenter)); 
			}
			
			if(StringUtils.isNotBlank(srdStartDate)){
				criteriaSql.add(" BOLSV.SRD_START_TIME >= TO_DATE(:srdStartDate, 'DD/MM/YYYY') ");
				params.addValue("srdStartDate", srdStartDate); 
			}

			if(StringUtils.isNotBlank(srdEndDate)){
				criteriaSql.add(" BOLSV.SRD_START_TIME < TO_DATE(:srdEndDate, 'DD/MM/YYYY')+1 ");
				params.addValue("srdEndDate", srdEndDate); 
			}
			
			if(criteriaSql.size() > 0){
				sql.append(" WHERE ");
				sql.append(StringUtils.join(criteriaSql.toArray(new String[criteriaSql.size()]), " AND "));
			}
			
			sql.append(" ORDER BY BOLSV.APP_DATE DESC"); 
			
			return simpleJdbcTemplate.query(sql.toString(), mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("searchLtsOnlineOrder EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in searchLtsOnlineOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		
	}
	
	private ParameterizedRowMapper<LtsOrderSearchDTO> getMapper(){
		return new ParameterizedRowMapper<LtsOrderSearchDTO>() {

			public LtsOrderSearchDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LtsOrderSearchDTO result = new LtsOrderSearchDTO();
				result.setApplicationDate(rs.getTimestamp("APP_DATE"));
				result.setCustName(rs.getString("CUST_NAME"));
				result.setErrorMsg(rs.getString("ERR_MSG"));
				result.setLob(rs.getString("LOB"));
				result.setOcId(rs.getString("OCID"));
				result.setOrderId(rs.getString("ORDER_ID"));
				result.setSrvNum(rs.getString("SRV_NUM"));
				result.setStatus(rs.getString("ORDER_STATUS"));
				result.setStatusReaCd(rs.getString("STATUS_REA_CD"));
				result.setContactEmail(rs.getString("EMAIL"));
				result.setIdDocType(rs.getString("ID_DOC_TYPE"));
				result.setIdDocNum(rs.getString("ID_DOC_NUM"));
				result.setEmailTemplateId(rs.getString("EMAIL_TEMPLATE_ID"));
				return result;
			}
		};
	}
	
	private ParameterizedRowMapper<LtsOrderSearchDTO> getCcOrderMapper(){
		return new ParameterizedRowMapper<LtsOrderSearchDTO>() {

			public LtsOrderSearchDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LtsOrderSearchDTO result = new LtsOrderSearchDTO();
				result.setApplicationDate(rs.getTimestamp("APP_DATE"));
				result.setCustName(rs.getString("CUST_NAME"));
				result.setErrorMsg(rs.getString("ERR_MSG"));
				result.setLob(rs.getString("LOB"));
				result.setOcId(rs.getString("OCID"));
				result.setOrderId(rs.getString("ORDER_ID"));
				result.setSrvNum(rs.getString("SRV_NUM"));
				result.setStatus(rs.getString("ORDER_STATUS"));
				result.setStatusReaCd(rs.getString("STATUS_REA_CD"));
				result.setContactEmail(rs.getString("EMAIL"));
				result.setIdDocType(rs.getString("ID_DOC_TYPE"));
				result.setIdDocNum(rs.getString("ID_DOC_NUM"));
				result.setStaffNum(rs.getString("STAFF_NUM"));
				result.setShopCd(rs.getString("SHOP_CD"));
				result.setCreateChannel(rs.getString("CREATE_CHANNEL"));
				result.setCenterCd(rs.getString("CENTRE_CD"));
				result.setCreateBy(rs.getString("CREATE_BY"));
				return result;
			}
		};
	}
	
	public List<String> getDsSalesCenter(String staffId, String channelId) throws DAOException{
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT CENTRE_CD FROM BOMWEB_SALES_CENTRE ");
		sb.append(" WHERE STAFF_ID = :staffId ");
		sb.append(" AND (START_DATE < SYSDATE OR START_DATE IS NULL) ");
		sb.append(" AND (END_DATE > SYSDATE OR END_DATE IS NULL) ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		
		if(StringUtils.isNotBlank(channelId)){
			sb.append(" AND CHANNEL_ID = :channelId ");
			params.addValue("channelId", channelId);
		}
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("CENTRE_CD");
			}
		};
		
		try{
			return simpleJdbcTemplate.query(sb.toString(), mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getSalesCenter()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
