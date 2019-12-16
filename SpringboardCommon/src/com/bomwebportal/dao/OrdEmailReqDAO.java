package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrdEmailReqDTO.DisMode;
import com.bomwebportal.dto.EmailTemplateDTO.EsigEmailLang;

public class OrdEmailReqDAO extends BaseDAO {
	/*
	Initial copied on 20140925 from Version 1.22 of
	\Springboard\SBWPR3\BomWebPortal\src\com\bomwebportal\dao\OrdEmailReqDAO.java
	*/	
	
	public int getNextSeq(String pOrderId, String pTemplateId, String pLob) {
		
		String sql = null;
		
		if (logger.isInfoEnabled()) {
			logger.info("getNextSeq @ OrdEmailReqDAO is called");
		}
		
		if ("IMS".equalsIgnoreCase(pLob)) {
			sql = "select nvl(max(SEQ_NO), 0) + 1 from bomweb_ord_email_req where ORDER_ID = :orderId";
		} else {
			sql = "select nvl(max(SEQ_NO), 0) + 1 from bomweb_ord_email_req where ORDER_ID = :orderId and TEMPLATE_ID = :templateId";
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("getNextSeq() @ OrdEmailReqDAO: " + sql);
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", pOrderId);
		
		if (!"IMS".equalsIgnoreCase(pLob)) {		
			params.addValue("templateId", pTemplateId);
		}
		
		return this.simpleJdbcTemplate.queryForInt(sql, params);
	}
	
	
/*	// Replaced getNextSeq(String pOrderId, String pTemplateId, String pLob) instead
	public int getNextSeqIMS(String orderId) {
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOALL @ OrdEmailReqDAO is called");
		}
		String sql = "select nvl(max(SEQ_NO), 0) + 1 from bomweb_ord_email_req where ORDER_ID = :orderId";
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOALL() @ OrdEmailReqDAO: " + sql);
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		return this.simpleJdbcTemplate.queryForInt(sql, params);
	}	
*/
	
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOList(String pLob) {
		// previously getOrdEmailReqDTOALL(String pLob)
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOList @ OrdEmailReqDAO is called");
		}
		String sql = "select" +
				"  oer.ORDER_ID" +
				"  , oer.TEMPLATE_ID" +
				"  , e.TEMPLATE_DESC" +
				"  , oer.REQUEST_DATE" +
				"  , oer.FILE_PATH_NAME_1" +
				"  , oer.FILE_PATH_NAME_2" +
				"  , oer.FILE_PATH_NAME_3" +
				"  , oer.SENT_DATE" +
				"  , oer.ERR_MSG" +
				"  , oer.CREATE_BY" +
				"  , oer.CREATE_DATE" +
				"  , oer.LAST_UPD_BY" +
				"  , oer.LAST_UPD_DATE" +
				"  , oer.EMAIL_ADDR" +
				"  , oer.LOCALE" +
				"  , oer.SEQ_NO" +
				"  , oer.STORE_PATH" + 
				"  , oer.CUST_NO" + 
				"  , oer.PARAM_STRING" +
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , s.CONTACT_PHONE" +
				"  , o.SALES_NAME" +
				"  , o.SHOP_CD" +
				"  , o.LOB" +
				"  , oer.METHOD" +
				"  , oer.SMS_NO" +
				"  , o.sales_team" +
				"  , s.BRAND" +
				"  , oer.rowid ROW_ID" +
				"  , o.ORDER_TYPE" +
				" from" +
				"  bomweb_ord_email_req oer" +
				"  left join bomweb_order o on (oer.ORDER_ID = o.ORDER_ID)" +
				"  left join bomweb_contact c on (oer.ORDER_ID = c.ORDER_ID and c.CONTACT_TYPE = 'CC')" +
				"  left join bomweb_shop s on (o.SHOP_CD = s.SHOP_CD)" +
				"  left join bomweb_email_template e on (oer.TEMPLATE_ID = e.TEMPLATE_ID and o.ESIG_EMAIL_LANG = e.LOCALE and o.LOB = e.LOB)" +
				" where" +
				"  o.LOB = :lob" +
				" order by" +
				"  oer.ORDER_ID, oer.TEMPLATE_ID, oer.SEQ_NO";
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTOList() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("lob", pLob);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOList()");
			}
		}
		return list;
	}
	
	
	public OrdEmailReqDTO getOrdEmailReqDTO(String pRowId) {
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTO @ OrdEmailReqDAO is called");
		}
		String sql = "select" +
				"  oer.ORDER_ID" +
				"  , oer.TEMPLATE_ID" +
				"  , e.TEMPLATE_DESC" +
				"  , oer.REQUEST_DATE" +
				"  , oer.FILE_PATH_NAME_1" +
				"  , oer.FILE_PATH_NAME_2" +
				"  , oer.FILE_PATH_NAME_3" +
				"  , oer.SENT_DATE" +
				"  , oer.ERR_MSG" +
				"  , oer.CREATE_BY" +
				"  , oer.CREATE_DATE" +
				"  , oer.LAST_UPD_BY" +
				"  , oer.LAST_UPD_DATE" +
				"  , oer.EMAIL_ADDR" +
				"  , oer.LOCALE" +
				"  , oer.SEQ_NO" +
				"  , oer.STORE_PATH" + 
				"  , oer.CUST_NO" + 
				"  , oer.PARAM_STRING" +
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , s.CONTACT_PHONE" +
				"  , o.SALES_NAME" +
				"  , o.SHOP_CD" +
				"  , o.LOB" +
				"  , o.sales_team" +
				"  , oer.METHOD" +
				"  , oer.SMS_NO" +
				"  , s.BRAND" +
				"  , oer.rowid ROW_ID" +
				"  , o.ORDER_TYPE" +
				" from" +
				"  bomweb_ord_email_req oer" +
				"  left join bomweb_order o on (oer.ORDER_ID = o.ORDER_ID)" +
				"  left join bomweb_contact c on (oer.ORDER_ID = c.ORDER_ID and c.CONTACT_TYPE = 'CC')" +
				"  left join bomweb_shop s on (o.SHOP_CD = s.SHOP_CD)" +
				"  left join bomweb_email_template e on (oer.TEMPLATE_ID = e.TEMPLATE_ID and o.ESIG_EMAIL_LANG = e.LOCALE and o.LOB = e.LOB)" +
				" where" +
				"  oer.rowid = :rowId";
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTO() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("rowId", pRowId);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTO()");
			}
		}
		return this.isEmpty(list) ? null : list.get(0);
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOListByOrderAndTemplateId(String pOrderId, String pTemplateId, String pLob) {
		// Renamed - previous getOrdEmailReqDTOALLByOrderId(String pOrderId, String pTemplateId)
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOListByOrderAndTemplateId @ OrdEmailReqDAO is called");
		}
		String sql = "select" +
				"  oer.ORDER_ID" +
				"  , oer.TEMPLATE_ID" +
				"  , e.TEMPLATE_DESC" +
				"  , oer.REQUEST_DATE" +
				"  , oer.FILE_PATH_NAME_1" +
				"  , oer.FILE_PATH_NAME_2" +
				"  , oer.FILE_PATH_NAME_3" +
				"  , oer.SENT_DATE" +
				"  , oer.ERR_MSG" +
				"  , oer.CREATE_BY" +
				"  , oer.CREATE_DATE" +
				"  , oer.LAST_UPD_BY" +
				"  , oer.LAST_UPD_DATE" +
				"  , oer.EMAIL_ADDR" +
				"  , oer.LOCALE" +
				"  , oer.SEQ_NO" +
				"  , oer.STORE_PATH" + 
				"  , oer.CUST_NO" + 
				"  , oer.PARAM_STRING" +
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , s.CONTACT_PHONE" +
				"  , o.SALES_NAME" +
				"  , o.SHOP_CD" +
				"  , o.LOB" +
				"  , oer.METHOD" +
				"  , oer.SMS_NO" +
				"  , o.sales_team" +
				"  , s.BRAND" +
				"  , oer.rowid ROW_ID" +
				"  , o.ORDER_TYPE" +
				" from" +
				"  bomweb_ord_email_req oer" +
				"  left join bomweb_order o on (oer.ORDER_ID = o.ORDER_ID)" +
				"  left join bomweb_contact c on (oer.ORDER_ID = c.ORDER_ID and c.CONTACT_TYPE = 'CC')" +
				"  left join bomweb_shop s on (o.SHOP_CD = s.SHOP_CD)" +
				"  left join bomweb_email_template e on (oer.TEMPLATE_ID = e.TEMPLATE_ID and o.ESIG_EMAIL_LANG = e.LOCALE and o.LOB = e.LOB)" +
				" where" +
				"  oer.ORDER_ID = :orderId" +
				"  and e.TEMPLATE_ID = :templateId " +
				" order by" +
				"  oer.SEQ_NO";
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTOListByOrderAndTemplateId() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", pOrderId);
			params.addValue("templateId", pTemplateId);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOListByOrderAndTemplateId()");
			}
		}
		return list;
	}
	
/*	// Replaced By getOrdEmailReqDTOListByOrderId(String pOrderId, String pLob) instead
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOrderIdIMS(String pOrderId) {
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOALLByOrderId @ OrdEmailReqDAO is called");
		}
		String sql = "select" +
				"  oer.ORDER_ID" +
				"  , oer.TEMPLATE_ID" +
				"  , e.TEMPLATE_DESC" +
				"  , oer.REQUEST_DATE" +
				"  , oer.FILE_PATH_NAME_1" +
				"  , oer.FILE_PATH_NAME_2" +
				"  , oer.FILE_PATH_NAME_3" +
				"  , oer.SENT_DATE" +
				"  , oer.ERR_MSG" +
				"  , oer.CREATE_BY" +
				"  , oer.CREATE_DATE" +
				"  , oer.LAST_UPD_BY" +
				"  , oer.LAST_UPD_DATE" +
				"  , oer.EMAIL_ADDR" +
				"  , oer.LOCALE" +
				"  , oer.SEQ_NO" +
				"  , oer.STORE_PATH" + "  , oer.CUST_NO"
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , s.CONTACT_PHONE" +
				"  , o.SALES_NAME" +
				"  , o.SHOP_CD" +
				"  , o.LOB" +
				"  , oer.METHOD" +
				"  , oer.SMS_NO" +
				"  , o.sales_team" +
				"  , s.BRAND" +
				"  , oer.rowid ROW_ID" +
				"  , o.ORDER_TYPE" +
				" from" +
				"  bomweb_ord_email_req oer" +
				"  left join bomweb_order o on (oer.ORDER_ID = o.ORDER_ID)" +
				"  left join bomweb_contact c on (oer.ORDER_ID = c.ORDER_ID and c.CONTACT_TYPE = 'CC')" +
				"  left join bomweb_shop s on (o.SHOP_CD = s.SHOP_CD)" +
				"  left join bomweb_email_template e on (oer.TEMPLATE_ID = e.TEMPLATE_ID and o.ESIG_EMAIL_LANG = e.LOCALE and o.LOB = e.LOB)" +
				" where" +
				"  oer.ORDER_ID = :orderId" +
				" order by" +
				"  oer.SEQ_NO";
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTOALLByOrderId() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", pOrderId);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOALLByOrderId()");
			}
		}
		return list;
	}
*/
	
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOListByOrderId(String pOrderId, String pLob) {
		// Renamed - previously getOrdEmailReqDTOALLByOnlyOrderId(String pOrderId)
		String sql = "select" +
				"  oer.ORDER_ID" +
				"  , oer.TEMPLATE_ID" +
				"  , e.TEMPLATE_DESC" +
				"  , oer.REQUEST_DATE" +
				"  , oer.FILE_PATH_NAME_1" +
				"  , oer.FILE_PATH_NAME_2" +
				"  , oer.FILE_PATH_NAME_3" +
				"  , oer.SENT_DATE" +
				"  , oer.ERR_MSG" +
				"  , oer.CREATE_BY" +
				"  , oer.CREATE_DATE" +
				"  , oer.LAST_UPD_BY" +
				"  , oer.LAST_UPD_DATE" +
				"  , oer.EMAIL_ADDR" +
				"  , oer.LOCALE" +
				"  , oer.SEQ_NO" +
				"  , oer.STORE_PATH" + 
				"  , oer.CUST_NO" + 
				"  , oer.PARAM_STRING" +
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , s.CONTACT_PHONE" +
				"  , o.SALES_NAME" +
				"  , o.SHOP_CD" +
				"  , o.LOB" +
				"  , oer.METHOD" +
				"  , oer.SMS_NO" +
				"  , o.sales_team" +
				"  , s.BRAND" +
				"  , oer.rowid ROW_ID" +
				"  , o.ORDER_TYPE" +
				" from" +
				"  bomweb_ord_email_req oer" +
				"  left join bomweb_order o on (oer.ORDER_ID = o.ORDER_ID)" +
				"  left join bomweb_contact c on (oer.ORDER_ID = c.ORDER_ID and c.CONTACT_TYPE = 'CC')" +
				"  left join bomweb_shop s on (o.SHOP_CD = s.SHOP_CD)" +
				"  left join bomweb_email_template e on (oer.TEMPLATE_ID = e.TEMPLATE_ID and o.ESIG_EMAIL_LANG = e.LOCALE and o.LOB = e.LOB)" +
				" where" +
				"  oer.ORDER_ID = :orderId";
		
		String sqlOrderBy = null;

		
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOListByOrderId @ OrdEmailReqDAO is called");
		}
		
		if ("IMS".equalsIgnoreCase(pLob)) {
			sqlOrderBy = " order by oer.SEQ_NO";
		} else {
			sqlOrderBy = " order by oer.TEMPLATE_ID, oer.SEQ_NO";		
		}
		
		sql = sql + sqlOrderBy;
		List<OrdEmailReqDTO> list = Collections.emptyList();
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTOListByOrderId() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", pOrderId);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOListByOrderId()");
			}
		}
		return list;
	}
	
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOListBySearchCriteria(String pOrderId,
			String pShopCd, String pLob, Date pRequestDate, String pTemplateId) {
		// Renamed - previous getOrdEmailReqDTOALLBySearch(String pOrderId,	String pShopCd, String pLob, Date pRequestDate, String pTemplateId)
		
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOListBySearchCriteria @ OrdEmailReqDAO is called");
		}
		StringBuilder sql = new StringBuilder("select" +
				"  oer.ORDER_ID" +
				"  , oer.TEMPLATE_ID" +
				"  , e.TEMPLATE_DESC" +
				"  , oer.REQUEST_DATE" +
				"  , oer.FILE_PATH_NAME_1" +
				"  , oer.FILE_PATH_NAME_2" +
				"  , oer.FILE_PATH_NAME_3" +
				"  , oer.SENT_DATE" +
				"  , oer.ERR_MSG" +
				"  , oer.CREATE_BY" +
				"  , oer.CREATE_DATE" +
				"  , oer.LAST_UPD_BY" +
				"  , oer.LAST_UPD_DATE" +
				"  , oer.EMAIL_ADDR" +
				"  , oer.LOCALE" +
				"  , oer.SEQ_NO" +
				"  , oer.STORE_PATH" + 
				"  , oer.CUST_NO" + "  , oer.PARAM_STRING" +
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , s.CONTACT_PHONE" +
				"  , o.SALES_NAME" +
				"  , o.SHOP_CD" +
				"  , o.LOB" +
				"  , oer.METHOD" +
				"  , oer.SMS_NO" +
				"  , o.sales_team" +
				"  , s.BRAND" +
				"  , oer.rowid ROW_ID" +
				"  , o.ORDER_TYPE" +
				" from" +
				"  bomweb_ord_email_req oer" +
				"  left join bomweb_order o on (oer.ORDER_ID = o.ORDER_ID)" +
				"  left join bomweb_contact c on (oer.ORDER_ID = c.ORDER_ID and c.CONTACT_TYPE = 'CC')" +
				"  left join bomweb_shop s on (o.SHOP_CD = s.SHOP_CD)" +
				"  left join bomweb_email_template e on (oer.TEMPLATE_ID = e.TEMPLATE_ID and o.ESIG_EMAIL_LANG = e.LOCALE and o.LOB = e.LOB)" +
				" where" +
				"  1=1"  +
				"  AND not exists (" +
				"	select 1 " +
				"	from BOMWEB_CODE_LKUP sbof " +
				"	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD'" +
				"	and sbof.code_id = o.SHOP_CD" +
				"	) " + //Filter IMS non-retail orders 
				" AND o.SHOP_CD in (select distinct s.shop_cd from bomweb_shop s where channel_id='1')" +
				"	"); //only retail orders 
				
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			if (StringUtils.isNotBlank(pOrderId)) {
				sql.append(" AND oer.ORDER_ID = :orderId");
				params.addValue("orderId", pOrderId);
			}
			if (StringUtils.isNotBlank(pShopCd)) {
				sql.append(" AND o.SHOP_CD = :shopCd");
				params.addValue("shopCd", pShopCd);
			}
			if (StringUtils.isNotBlank(pLob)) {
				sql.append(" AND o.LOB = :lob");
				params.addValue("lob", pLob);
			}
			if (StringUtils.isNotBlank(pTemplateId)) {
				sql.append(" AND e.TEMPLATE_ID = :templateId");
				params.addValue("templateId", pTemplateId);
			}
			if (pRequestDate != null) {
				sql.append(" AND trunc(oer.REQUEST_DATE) = trunc(:requestDate)");
				params.addValue("requestDate", pRequestDate, Types.TIMESTAMP);
			}
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTOListBySearchCriteria() @ OrdEmailReqDAO: " + sql);
			}
			sql.append(" order by oer.ORDER_ID, oer.TEMPLATE_ID, oer.SEQ_NO");
			list = this.simpleJdbcTemplate.query(sql.toString(), getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOListBySearchCriteria()");
			}
		}
		return list;
	}
	
	
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOListByChannelIdAndTeam(String pOorderId,
			int pChannelId, String pLob, Date pRequestDate, String pTemplateId, String pTeam) {
		// Renamed - previous getOrdEmailReqDTOIMSLTSBySearch(String pOorderId, int pChannelId, Date pRequestDate, String pTemplateId, String pTeam)
		
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOListByChannelIdAndTeam @ OrdEmailReqDAO is called");
		}
		StringBuilder sql = new StringBuilder(
				" select * from (" +
				" select " +
				"  oer.ORDER_ID" +
				"  , oer.TEMPLATE_ID" +
				"  , e.TEMPLATE_DESC" +
				"  , oer.REQUEST_DATE" +
				"  , oer.FILE_PATH_NAME_1" +
				"  , oer.FILE_PATH_NAME_2" +
				"  , oer.FILE_PATH_NAME_3" +
				"  , oer.SENT_DATE" +
				"  , oer.ERR_MSG" +
				"  , oer.CREATE_BY" +
				"  , oer.CREATE_DATE" +
				"  , oer.LAST_UPD_BY" +
				"  , oer.LAST_UPD_DATE" +
				"  , oer.EMAIL_ADDR" +
				"  , oer.LOCALE" +
				"  , oer.SEQ_NO" +
				"  , oer.STORE_PATH" + 
				"  , oer.CUST_NO" + "  , oer.PARAM_STRING" +
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , s.CONTACT_PHONE" +
				"  , o.SALES_NAME" +
				"  , o.SHOP_CD" +
				"  , o.LOB" +
				"  , oer.rowid ROW_ID" +
				"  , oer.METHOD" +
				"  , oer.SMS_NO" +
				"  , s.BRAND" +
				"  , o.sales_team" +
				"  , o.ORDER_TYPE" +
				" from" +
				"  bomweb_ord_email_req oer" +
				"  left join bomweb_order o on (oer.ORDER_ID = o.ORDER_ID)" +
				"  left join bomweb_contact c on (oer.ORDER_ID = c.ORDER_ID and c.CONTACT_TYPE = 'CC')" +
				"  left join bomweb_shop s on (o.SHOP_CD = s.SHOP_CD)" +
				"  left join bomweb_email_template e on (oer.TEMPLATE_ID = e.TEMPLATE_ID and o.ESIG_EMAIL_LANG = e.LOCALE and o.LOB = e.LOB)" +
				" where" +
				"  1=1 " +
				"  AND oer.SEQ_NO = 1"  +
				"  AND not exists (" +
				"	select 1 " +
				"	from BOMWEB_CODE_LKUP sbof " +
				"	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD'" +
				"	and sbof.code_id = o.SHOP_CD" +
				"	) " + //Filter IMS non-retail orders 
				" AND nvl(o.SHOP_CD, o.sales_channel) in (select distinct s.shop_cd from bomweb_shop s where channel_id in ('2','3') )" +
				"	"); //only call center 
				
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			if (StringUtils.isNotBlank(pOorderId)) {
				sql.append(" AND oer.ORDER_ID = :orderId");
				params.addValue("orderId", pOorderId);
			}
			sql.append(" AND (" +
					"	 ( '2' = :ChannelId AND oer.ORDER_ID LIKE 'C%') " +
					" OR ( '3' = :ChannelId AND oer.ORDER_ID LIKE 'P%') " +
					"	)");
			params.addValue("ChannelId", pChannelId);
			
			if (StringUtils.isNotBlank(pTemplateId)) {
				sql.append(" AND e.TEMPLATE_ID = :templateId");
				params.addValue("templateId", pTemplateId);
			}
			if (pRequestDate != null) {
				sql.append(" AND trunc(oer.REQUEST_DATE) = trunc(:requestDate)");
				params.addValue("requestDate", pRequestDate, Types.TIMESTAMP);
			}
			if (StringUtils.isNotBlank(pTeam))
			{
				sql.append(" AND o.SALES_TEAM = :team");
				params.addValue("team", pTeam);
			}
			
//			sql.append(
//					" AND o.LOB in ('IMS')" +
//					" ORDER BY oer.ORDER_ID, oer.TEMPLATE_ID, oer.SEQ_NO" +
//					" ) where rownum <= 50");

			if (StringUtils.isNotBlank(pLob)) {
				sql.append(" AND o.LOB = :lob");
				params.addValue("lob", pLob);
			}
			
			sql.append(
					" ORDER BY oer.ORDER_ID, oer.TEMPLATE_ID, oer.SEQ_NO" +
					" ) where rownum <= 50");
						
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTOListByChannelIdAndTeam() @ OrdEmailReqDAO: " + sql);
			}
			list = this.simpleJdbcTemplate.query(sql.toString(), getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOListByChannelIdAndTeam()");
			}
		}
		return list;
	}

	
	
	public OrdEmailReqDTO getOrdEmailReqDTOByOrderIdAndSeqNo(String pOrderId, int pSeqNo, String pTemplateId) {
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOByOrderIdAndSeqNo @ OrdEmailReqDAO is called");
		}
		String sql = "select" +
				"  oer.ORDER_ID" +
				"  , oer.TEMPLATE_ID" +
				"  , e.TEMPLATE_DESC" +
				"  , oer.REQUEST_DATE" +
				"  , oer.FILE_PATH_NAME_1" +
				"  , oer.FILE_PATH_NAME_2" +
				"  , oer.FILE_PATH_NAME_3" +
				"  , oer.SENT_DATE" +
				"  , oer.ERR_MSG" +
				"  , oer.CREATE_BY" +
				"  , oer.CREATE_DATE" +
				"  , oer.LAST_UPD_BY" +
				"  , oer.LAST_UPD_DATE" +
				"  , oer.EMAIL_ADDR" +
				"  , oer.LOCALE" +
				"  , oer.SEQ_NO" +
				"  , oer.STORE_PATH" + 
				"  , oer.CUST_NO" + 
				"  , oer.PARAM_STRING" +
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , nvl(s.CONTACT_PHONE, o.sales_contact_num) contact_phone" +
				"  , o.SALES_NAME" +
				"  , o.SHOP_CD" +
				"  , oer.LOB" +
				"  , oer.METHOD" +
				"  , oer.SMS_NO" +
				"  , o.sales_team" +
				"  , s.BRAND" +
				"  , oer.rowid ROW_ID" +
				"  , o.ORDER_TYPE" +
				" from" +
				"  bomweb_ord_email_req oer" +
				"  left join bomweb_order o on (oer.ORDER_ID = o.ORDER_ID)" +
				"  left join bomweb_contact c on (oer.ORDER_ID = c.ORDER_ID and c.CONTACT_TYPE = 'CC')" +
				"  left join bomweb_shop s on (o.SHOP_CD = s.SHOP_CD)" +
				"  left join bomweb_email_template e on (oer.TEMPLATE_ID = e.TEMPLATE_ID and o.ESIG_EMAIL_LANG = e.LOCALE and o.LOB = e.LOB)" +
				" where" +
				"  oer.ORDER_ID = :orderId" +
				"  and oer.seq_no = :seqNo" +
				"  and oer.TEMPLATE_ID = :templateId";
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTOByOrderIdAndSeqNo() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", pOrderId);
			params.addValue("seqNo", pSeqNo);
			params.addValue("templateId", pTemplateId);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOByOrderIdAndSeqNo()");
			}
		}
		return this.isEmpty(list) ? null : list.get(0);
	}
	
	
	private ParameterizedRowMapper<OrdEmailReqDTO> getRowMapper() {
		return new ParameterizedRowMapper<OrdEmailReqDTO>() {
			public OrdEmailReqDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrdEmailReqDTO dto = new OrdEmailReqDTO();
				
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setTemplateId(rs.getString("TEMPLATE_ID"));
				dto.setEmailType(rs.getString("TEMPLATE_DESC"));
				dto.setRequestDate(rs.getTimestamp("REQUEST_DATE"));
				dto.setFilePathName1(rs.getString("FILE_PATH_NAME_1"));
				dto.setFilePathName2(rs.getString("FILE_PATH_NAME_2"));
				dto.setFilePathName3(rs.getString("FILE_PATH_NAME_3"));
				dto.setSentDate(rs.getTimestamp("SENT_DATE"));
				dto.setErrMsg(rs.getString("ERR_MSG"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setEmailAddr(rs.getString("EMAIL_ADDR"));
				dto.setLocale(EsigEmailLang.valueOf(rs.getString("LOCALE")));
				dto.setSeqNo(rs.getInt("SEQ_NO"));
				dto.setRowId(rs.getString("ROW_ID"));
				dto.setOrderType(rs.getString("ORDER_TYPE"));
				dto.setParamString(rs.getString("PARAM_STRING"));
				
				String esigEmailLang = rs.getString("ESIG_EMAIL_LANG");
				if (StringUtils.isNotBlank(esigEmailLang)) {
					dto.setEsigEmailLang(EsigEmailLang.valueOf(rs.getString("ESIG_EMAIL_LANG")));
				}
				dto.setEsigEmailAddr(rs.getString("ESIG_EMAIL_ADDR"));
				dto.setMsisdn(rs.getString("MSISDN"));
				dto.setAppDate(rs.getTimestamp("APP_DATE"));
				dto.setSalesName(rs.getString("SALES_NAME"));
				dto.setShopCd(rs.getString("SHOP_CD"));
				dto.setLob(rs.getString("LOB"));
				
				dto.setTitle(rs.getString("TITLE"));
				dto.setContactName(rs.getString("CONTACT_NAME"));
				
				dto.setContactPhone(rs.getString("CONTACT_PHONE"));
				dto.setSMSno(rs.getString("SMS_NO"));
				
				dto.setStorePath(rs.getString("STORE_PATH"));
				dto.setCustNo(rs.getString("CUST_NO"));
				
				// stupid way!!
//				for (DisMode d:DisMode.values())
//				{
//					if ( d.toString().equals(rs.getString("method")))
//						dto.setMethod(d);
//				}
				if ( rs.getString("method") != null && !"".equals(rs.getString("method")) )
				dto.setMethod(DisMode.valueOf(rs.getString("method")));
				dto.setSalesTeam(rs.getString("SALES_TEAM"));
				
				dto.setBrand(rs.getString("BRAND"));
				
				return dto;
			}
			
		};
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
