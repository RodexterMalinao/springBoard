package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;

public class OrdEmailReqDAO extends BaseDAO {
	public int getNextSeq(String orderId, String templateId) {
		if (logger.isDebugEnabled()) {
			logger.debug("getNextSeq @ OrdEmailReqDAO is called");
		}
		String sql = "select nvl(max(SEQ_NO), 0) + 1 from bomweb_ord_email_req where ORDER_ID = :orderId and TEMPLATE_ID = :templateId";
		if (logger.isDebugEnabled()) {
			logger.debug("getNextSeq() @ OrdEmailReqDAO: " + sql);
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("templateId", templateId);
		return this.simpleJdbcTemplate.queryForInt(sql, params);
	}
	
	public int isCareCashFormSent(String orderId) {
		if (logger.isDebugEnabled()) {
			logger.debug("isCareCashFormSent @ OrdEmailReqDAO is called");
		}
		String sql = "select count(1) from bomweb_ord_email_req where order_id =:orderId and template_id = 'RT017' and lob = 'MOB'";
		if (logger.isDebugEnabled()) {
			logger.debug("isCareCashFormSent() @ OrdEmailReqDAO: " + sql);
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		return this.simpleJdbcTemplate.queryForInt(sql, params);
	}
	
	public int getNextSeqIMS(String orderId) {
		if (logger.isDebugEnabled()) {
			logger.debug("getNextSeqIMS @ OrdEmailReqDAO is called");
		}
		String amdStr = ImsConstants.IMS_AMENTMENT_NOTIFICATION + "%";
		String sql = "select nvl(max(SEQ_NO), 0) + 1 from bomweb_ord_email_req where ORDER_ID = :orderId  AND TEMPLATE_ID NOT LIKE :amdStr ";//CELIA IMS 20141121 add amendment notification filter
		if (logger.isDebugEnabled()) {
			logger.debug("getNextSeqIMS() @ OrdEmailReqDAO: " + sql);
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("amdStr", amdStr);
		return this.simpleJdbcTemplate.queryForInt(sql, params);
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALL(String lob) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTOALL @ OrdEmailReqDAO is called");
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
			if (logger.isDebugEnabled()) {
				logger.debug("getOrdEmailReqDTOALL() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("lob", lob);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOALL()");
			}
		}
		return list;
	}
	
	public OrdEmailReqDTO getOrdEmailReqDTO(String rowId) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTO @ OrdEmailReqDAO is called");
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
			if (logger.isDebugEnabled()) {
				logger.debug("getOrdEmailReqDTO() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("rowId", rowId);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTO()");
			}
		}
		return this.isEmpty(list) ? null : list.get(0);
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOrderId(String orderId, String templateId) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTOALLByOrderId @ OrdEmailReqDAO is called");
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
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , nvl(s.CONTACT_PHONE, o.sales_contact_num) contact_phone " +
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
				"  and e.TEMPLATE_ID = nvl(:templateId, e.TEMPLATE_ID) " +
				" order by" +
				"  oer.SEQ_NO, oer.template_id";
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrdEmailReqDTOALLByOrderId() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("templateId", templateId);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOALLByOrderId()");
			}
		}
		return list;
	}
	
	public List<OrdEmailReqDTO> getCCSOrdEmailReqDTOALLByOrderId(String orderId, String templateId,String pLang) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTOALLByOrderId @ OrdEmailReqDAO is called");
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
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , nvl(s.CONTACT_PHONE, o.sales_contact_num) contact_phone " +
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
				"  left join bomweb_email_template e on (oer.TEMPLATE_ID = e.TEMPLATE_ID and o.LOB = e.LOB)" +
				" where" +
				"  oer.ORDER_ID = :orderId" +
				"  and e.TEMPLATE_ID = :templateId " +
				"  and e.LOCALE=:pLang"+
				" order by" +
				"  oer.SEQ_NO";
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrdEmailReqDTOALLByOrderId() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("templateId", templateId);
			params.addValue("pLang", pLang);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOALLByOrderId()");
			}
		}
		return list;
	}
	
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOrderIdIMS(String orderId, String templateId) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTOALLByOrderIdIMS @ OrdEmailReqDAO is called");
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
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , nvl(s.CONTACT_PHONE, o.sales_contact_num) contact_phone" +
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
				"  and oer.template_id = nvl(:templateId, oer.template_id) " +
				"  and oer.template_id not in (select code from w_code_lkup where grp_id = 'IMS_NO_RESEND_TEMPLT')" +
				" order by" +
				"  oer.SEQ_NO,oer.template_id ";//celia ims ds 20141202 add order by template_id
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTOALLByOrderIdIMS() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("templateId", templateId);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOALLByOrderIdIMS()");
			}
		}
		return list;
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOnlyOrderId(String orderId) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTOALLByOnlyOrderId @ OrdEmailReqDAO is called");
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
//				"  and e.TEMPLATE_ID = :templateId " +
				" order by" +
				" oer.TEMPLATE_ID, oer.SEQ_NO";
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrdEmailReqDTOALLByOnlyOrderId() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
//			params.addValue("templateId", templateId);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOALLByOnlyOrderId()");
			}
		}
		return list;
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearch(String orderId,
			String shopCd, String lob, Date requestDate, String templateId,String orderType) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTOALLBySearch @ OrdEmailReqDAO is called");
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
				"  where 1=1 "); 
				
				
					sql.append(" AND not exists (" +
							"	select 1 " +
							"	from BOMWEB_CODE_LKUP sbof " +
							"	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD'" +
							"	and sbof.code_id = o.SHOP_CD" +
							"	) ");//Filter IMS non-retail orders 
					sql.append(" AND o.SHOP_CD in (select distinct s.shop_cd from bomweb_shop s where channel_id='1') " +
				"	"); //only retail orders 
					
					sql.append(" AND oer.template_id not in (select code from w_code_lkup where grp_id = 'IMS_NO_RESEND_TEMPLT') ");// IMS filter no need resend templates
				
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			if (StringUtils.isNotBlank(orderId)) {
				sql.append(" AND oer.ORDER_ID = :orderId");
				params.addValue("orderId", orderId);
			}
			if (StringUtils.isNotBlank(shopCd)) {
				sql.append(" AND o.SHOP_CD = :shopCd");
				params.addValue("shopCd", shopCd);
			}
			if (StringUtils.isNotBlank(lob)) {
				sql.append(" AND o.LOB = :lob");
				params.addValue("lob", lob);
			}
			if (StringUtils.isNotBlank(templateId)) {
				sql.append(" AND e.TEMPLATE_ID = :templateId");
				params.addValue("templateId", templateId);
			}		
			if (StringUtils.isNotBlank(orderType)) {
				if(orderType.equalsIgnoreCase("NTV-A"))
					sql.append("  and o.ORDER_TYPE = 'NTV-A' ");
				else if(orderType.equalsIgnoreCase("NTVAO"))
					sql.append("  and o.ORDER_TYPE = 'NTVAO' ");
				else if(orderType.equalsIgnoreCase("NTVRE"))
					sql.append("  and o.ORDER_TYPE = 'NTVRE' ");
				else if(orderType.equalsIgnoreCase("NTVUS"))
					sql.append("  and o.ORDER_TYPE = 'NTVUS' ");
				else if(orderType.equalsIgnoreCase("PCD-A"))
					sql.append("  and (o.ORDER_TYPE is null or o.ORDER_TYPE = 'PCD-A') ");
			}
			if (requestDate != null) {
				sql.append(" AND trunc(oer.REQUEST_DATE) = trunc(:requestDate)");
				params.addValue("requestDate", requestDate, Types.TIMESTAMP);
			}
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTOALLBySearch() @ OrdEmailReqDAO: " + sql);
			}
			sql.append(" order by oer.ORDER_ID, oer.TEMPLATE_ID, oer.SEQ_NO");
			list = this.simpleJdbcTemplate.query(sql.toString(), getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOALLBySearch()");
			}
		}
	
		return list;
	}
	
	
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOIMSLTSBySearch(String orderId,
			int ChannelId, Date requestDate, String templateId, String team) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTOIMSLTSBySearch @ OrdEmailReqDAO is called");
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
//				"  AND oer.SEQ_NO = 1"  +
				"  AND not exists (" +
				"	select 1 " +
				"	from BOMWEB_CODE_LKUP sbof " +
				"	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD'" +
				"	and sbof.code_id = o.SHOP_CD" +
				"	) " + //Filter IMS non-retail orders 
				" and oer.template_id not in (select code from w_code_lkup where grp_id = 'IMS_NO_RESEND_TEMPLT') " + //filter no need resend template
				" AND nvl(o.SHOP_CD, o.sales_channel) in (select distinct s.shop_cd from bomweb_shop s where channel_id in ('2','3','99') )" +
				"	"); //only call center 
				
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			if (StringUtils.isNotBlank(orderId)) {
				sql.append(" AND oer.ORDER_ID = :orderId");
				params.addValue("orderId", orderId);
			}
			sql.append(" AND (" +
					"	 ( '2' = :ChannelId AND oer.ORDER_ID LIKE 'C%') " +
					" OR ( '3' = :ChannelId AND oer.ORDER_ID LIKE 'P%') " +
					" OR ( '99' = :ChannelId AND oer.ORDER_ID LIKE 'N%') " +
					"	)");
			params.addValue("ChannelId", ChannelId);
			
			if (StringUtils.isNotBlank(templateId)) {
				sql.append(" AND e.TEMPLATE_ID = :templateId");
				params.addValue("templateId", templateId);
			}
			if (requestDate != null) {
				sql.append(" AND trunc(oer.REQUEST_DATE) = trunc(:requestDate)");
				params.addValue("requestDate", requestDate, Types.TIMESTAMP);
			}
			if (StringUtils.isNotBlank(team))
			{
				sql.append(" AND o.SALES_TEAM = :team");
				params.addValue("team", team);
			}
//			sql.append(
//					" AND o.LOB in ('IMS','LTS')" +
//					" ORDER BY oer.ORDER_ID, oer.TEMPLATE_ID, oer.SEQ_NO" +
//					" ) where rownum <= 50");
			
			sql.append(
					" AND o.LOB in ('IMS')" +
					" ORDER BY oer.ORDER_ID, oer.TEMPLATE_ID, oer.SEQ_NO" +
					" ) where rownum <= 50");
			
			
			if (logger.isDebugEnabled()) {
				logger.debug("getOrdEmailReqDTOIMSLTSBySearch() @ OrdEmailReqDAO: " + sql);
			}
			list = this.simpleJdbcTemplate.query(sql.toString(), getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOIMSLTSBySearch()");
			}
		}
		return list;
	}
		
	
	public OrdEmailReqDTO getOrdEmailReqDTOByOrderIdAndSeqNo(String orderId, int seqNo, String templateId) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTOByOrderIdAndSeqNo @ OrdEmailReqDAO is called");
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
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , nvl(s.CONTACT_PHONE, o.sales_contact_num) CONTACT_PHONE" +
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
				"  and oer.seq_no = :seqNo" +
				"  and e.TEMPLATE_ID = :templateId";
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrdEmailReqDTOByOrderIdAndSeqNo() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			params.addValue("templateId", templateId);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOByOrderIdAndSeqNo()");
			}
		}
		return this.isEmpty(list) ? null : list.get(0);
	}
	
	public OrdEmailReqDTO getCareCashOrdEmailReqDTOByOrderIdAndSeqNo(String orderId, int seqNo, String templateId,String pLang) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCareCashOrdEmailReqDTOByOrderIdAndSeqNo @ OrdEmailReqDAO is called");
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
				"  , o.ESIG_EMAIL_LANG" +
				"  , o.ESIG_EMAIL_ADDR" +
				"  , c.TITLE" +
				"  , c.CONTACT_NAME" +
				"  , o.MSISDN" +
				"  , o.APP_DATE" +
				"  , nvl(s.CONTACT_PHONE, o.sales_contact_num) CONTACT_PHONE" +
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
				"  left join bomweb_email_template e on (oer.TEMPLATE_ID = e.TEMPLATE_ID and o.LOB = e.LOB)" +
				" where" +
				"  oer.ORDER_ID = :orderId" +
				"  and oer.seq_no = :seqNo" +
				"  and e.TEMPLATE_ID = :templateId" +
				"  and e.locale = :pLang";
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getCareCashOrdEmailReqDTOByOrderIdAndSeqNo() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			params.addValue("templateId", templateId);
			params.addValue("pLang",pLang);
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getCareCashOrdEmailReqDTOByOrderIdAndSeqNo()");
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
	
	
	
	//Celia ims ds 20141125
	public int getNextAmendNoteSeqNoIMS(String orderId){
		if (logger.isDebugEnabled()) {
			logger.debug("getNextAmendNoteSeqNoIMS @ OrdEmailReqDAO is called");
		}
		String amdStr = ImsConstants.IMS_AMENTMENT_NOTIFICATION + "%";
		String sql = "select nvl(max(SEQ_NO), 0) + 1 from bomweb_ord_email_req where ORDER_ID = :orderId  AND TEMPLATE_ID LIKE :amdStr ";//CELIA IMS 20141121 add amendment notification filter
		if (logger.isDebugEnabled()) {
			logger.debug("getNextAmendNoteSeqNoIMS() @ OrdEmailReqDAO: " + sql);
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("amdStr", amdStr);
		return this.simpleJdbcTemplate.queryForInt(sql, params);
	}
	
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLBySearchIMS(String orderId,
	Date requestDate,BomSalesUserDTO salesDto,String teamCd,String orderType) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTOALLBySearchIMS @ OrdEmailReqDAO is called");
		}
		StringBuilder sql = new StringBuilder("select * from (" +
				"  SELECT oer.ORDER_ID" +
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
				"  where 1=1 " +
				"  and oer.template_id not in (select code from w_code_lkup where grp_id = 'IMS_NO_RESEND_TEMPLT') ");

	
				
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			if (StringUtils.isNotBlank(orderId)) {
				sql.append(" AND oer.ORDER_ID = :orderId ");
				params.addValue("orderId", orderId);
			}		
			if (requestDate != null) {
				sql.append(" AND trunc(oer.REQUEST_DATE) = trunc(:requestDate) ");
				params.addValue("requestDate", requestDate, Types.TIMESTAMP);
			}
			if(StringUtils.isNotBlank(teamCd)){
				sql.append(" AND o.sales_team = :teamCd ");
				params.addValue("teamCd", teamCd);
			}
			if (StringUtils.isNotBlank(orderType)) {
				if(orderType.equalsIgnoreCase("PCD-A")){
					sql.append(" and (o.ORDER_TYPE is null or o.ORDER_TYPE = 'PCD-A') ");
				}else if(orderType.equalsIgnoreCase("NTV-A")){
					sql.append(" and o.ORDER_TYPE = 'NTV-A' ");
				}else if(orderType.equalsIgnoreCase("NTVAO"))
					sql.append("  and o.ORDER_TYPE = 'NTVAO' ");
				else if(orderType.equalsIgnoreCase("NTVRE"))
					sql.append("  and o.ORDER_TYPE = 'NTVRE' ");
				else if(orderType.equalsIgnoreCase("NTVUS"))
					sql.append("  and o.ORDER_TYPE = 'NTVUS' ");
			}
			if (salesDto == null)
				sql.append(this.getAMNTRequestListSQL());
			else{
				sql.append(this.getOrderByRoleSQL(salesDto));			
			sql.append(
					" AND o.LOB in ('IMS')" +
					" order by oer.ORDER_ID, oer.SEQ_NO, oer.TEMPLATE_ID " +
					" ) where rownum <= 50");}
			list = this.simpleJdbcTemplate.query(sql.toString(), getRowMapper(), params);
			logger.info("getOrdEmailReqDTOALLBySearchIMS     "+sql);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOALLBySearchIMS()");
			}
		}
	
		return list;
	}
	private Object getAMNTRequestListSQL() {
		// TODO Auto-generated method stub
		String amdStr = ImsConstants.IMS_AMENTMENT_NOTIFICATION + "%";
		String sql =  "  AND oer.TEMPLATE_ID LIKE '" + amdStr + "'    AND SENT_DATE IS NULL  " +
			 "  AND oer.seq_no in (select max(seq_no) from bomweb_ord_email_req where order_id in oer.order_id and trunc(request_date) < trunc(sysdate) and template_id like '" + amdStr + "' ) "
				+"	 AND o.LOB in ('IMS') " +
					" order by oer.ORDER_ID, oer.SEQ_NO, oer.TEMPLATE_ID) ";

		return sql;
	}

	private String getOrderByRoleSQL(BomSalesUserDTO salesDto) {
		// TODO Auto-generated method stub		
		String sql;
		if (salesDto.getChannelCd().equalsIgnoreCase("QCC")){
			sql = " and o.sales_channel in (select DISTINCT channel_cd from bomweb_sales_assignment where channel_id in (" + salesDto.getChannelId()+ ") AND START_DATE < sysdate and (sysdate < end_date or end_date is null) 	 ) ";
		}else if (salesDto.getChannelCd().equalsIgnoreCase("VQA")){
			sql = " and o.sales_channel in (select DISTINCT channel_cd from bomweb_sales_assignment where channel_id in (" + salesDto.getChannelId()+ " ,99 ) AND START_DATE < sysdate and (sysdate < end_date or end_date is null) 	 ) ";
		}else{
	    List<String> role= new ArrayList<String>();
		try {
			role = (ArrayList<String>)this.checkRole(salesDto.getUsername());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(role.size()==0 ){
			 sql = " AND o.Create_by = '"+salesDto.getUsername()+"' ";
		 }else if (role.contains("SUPPORT")){
			 sql = " and o.sales_channel in (select DISTINCT channel_cd from bomweb_sales_assignment where channel_id in (" + salesDto.getChannelId()+ ") AND START_DATE < sysdate and (sysdate < end_date or end_date is null) 	 ) ";
		 }else if (role.contains("SALES MANAGER")){
			 sql = " and o.sales_channel in (SELECT DISTINCT channel_cd FROM BOMWEB_SALES_ASSIGNMENT WHERE STAFF_ID = '"+salesDto.getUsername()+"' AND START_DATE < sysdate and (sysdate < end_date or end_date is null) 	) ";
		 }else if (role.contains("MANAGER")){
			 sql = " and o.sales_team in (SELECT DISTINCT team_cd FROM BOMWEB_SALES_ASSIGNMENT WHERE STAFF_ID = '"+salesDto.getUsername()+"' AND START_DATE < sysdate and (sysdate < end_date or end_date is null) 	) ";
		 }else if (role.contains("FRONTLINE")){
			 sql = " AND o.Create_by = '"+salesDto.getUsername()+"' ";
		 }else
			 sql = " and o.sales_channel in (select DISTINCT channel_cd from bomweb_sales_assignment where channel_id in ( " + salesDto.getChannelId()+ ") AND START_DATE < sysdate and (sysdate < end_date or end_date is null) 	) ";
		 
		}
		return sql;
	}

	public List<String> checkRole(String userId) throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"	SELECT CATEGORY FROM BOMWEB_SALES_PROFILE where staff_id = :userId  AND START_DATE < sysdate and (sysdate < end_date or end_date is null) 		" );
		
		params.addValue("userId", userId);

		
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("CATEGORY");
			return tempResult;
		}
	};
	try {
		logger.debug("userId @ checkRole: " + userId);

		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in checkRole():", e);
		throw new DAOException(e.getMessage(), e);
	}

		return result;
	}

	public List<String> getTeamCdsByRole(BomSalesUserDTO salesDto) throws DAOException {
		logger.debug("getTeamCdsByRole: " );
		List<String> role= new ArrayList<String>();
		List<String> teamCds= new ArrayList<String>();
		try {
			role = (ArrayList<String>)this.checkRole(salesDto.getUsername());
		} catch (DAOException e) {

			e.printStackTrace();
		}
		StringBuilder sql = new StringBuilder( "SELECT DISTINCT TEAM_CD  FROM BOMWEB_SALES_ASSIGNMENT WHERE  ");

		if(role.size()==0 ){
			sql.append("  STAFF_ID = '"+salesDto.getUsername()+"'  AND START_DATE < sysdate  		 ");
		}else if (role.contains("SUPPORT")){
			sql.append( "  CHANNEL_ID in "+salesDto.getChannelId()+" 	 ");
		}else if (role.contains("SALES MANAGER")){
			sql.append("  channel_cd in ( select channel_cd FROM BOMWEB_SALES_ASSIGNMENT WHERE STAFF_ID = '"+salesDto.getUsername()+"'  AND START_DATE < sysdate and (sysdate < end_date or end_date is null) 		 ) 	");
		}else if (role.contains("MANAGER")){
			sql.append("  STAFF_ID = '"+salesDto.getUsername()+"' 	 ");
		}else if (role.contains("FRONTLINE")){
			sql.append("  STAFF_ID = '"+salesDto.getUsername()+"' 	");
		}else{
			sql.append( "  CHANNEL_ID in "+salesDto.getChannelId()+" 	 ");
		}

		sql.append(" AND nvl(END_DATE,SYSDATE) >= SYSDATE AND (START_DATE < sysdate or start_date is null ) ");


		logger.info("getTeamCdsByRole:   "+sql);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = "";
				tempResult=(String) rs.getString("TEAM_CD");
				return tempResult;
			}
		};
		try {
			logger.debug("userId @ getTeamCdsByRole: " + salesDto.getUsername()  +  "      "+salesDto.getChannelId());
			logger.debug("getTeamCdsByRole : " + sql);
			teamCds = simpleJdbcTemplate.query(sql.toString(), mapper);

		} catch (EmptyResultDataAccessException e) {
			logger.debug("EmptyResultDataAccessException");
			teamCds.add("");
		} catch (Exception e) {
			logger.debug("Exception caught in getTeamCdsByRole():", e);
			throw new DAOException(e.getMessage(), e);
		}

		return teamCds;
	}
	public List<String> getTeamCdsByRoleandChannelCd(BomSalesUserDTO salesDto) throws DAOException {
	   	logger.debug("getTeamCdsByRole: " );

    	List<String> teamCds= new ArrayList<String>();

    	StringBuilder sql = new StringBuilder( "SELECT DISTINCT TEAM_CD  FROM BOMWEB_SALES_ASSIGNMENT WHERE  ");
    	if (salesDto.getChannelCd().equalsIgnoreCase("QCC")||salesDto.getChannelCd().equalsIgnoreCase("VQA")){

    		sql.append( "  CHANNEL_ID in "+salesDto.getChannelId()+" 	 ");
    	}else{

    		List<String> role= new ArrayList<String>();
    		try {
    			role = (ArrayList<String>)this.checkRole(salesDto.getUsername());
    		} catch (DAOException e) {

    			e.printStackTrace();
    		}


    		if(role.size()==0 ){
    			sql.append("  STAFF_ID = '"+salesDto.getUsername()+"'  AND START_DATE < sysdate  		 ");
    		}else if (role.contains("SUPPORT")){
    			sql.append( "  CHANNEL_ID in "+salesDto.getChannelId()+" 	 ");
    		}else if (role.contains("SALES MANAGER")){
    			sql.append("  channel_cd in ( select channel_cd FROM BOMWEB_SALES_ASSIGNMENT WHERE STAFF_ID = '"+salesDto.getUsername()+"'  AND START_DATE < sysdate and (sysdate < end_date or end_date is null) 		 ) 	");
    		}else if (role.contains("MANAGER")){
    			sql.append("  STAFF_ID = '"+salesDto.getUsername()+"' 	 ");
    		}else if (role.contains("FRONTLINE")){
    			sql.append("  STAFF_ID = '"+salesDto.getUsername()+"' 	");
    		}else{
    			sql.append( "  CHANNEL_ID in "+salesDto.getChannelId()+" 	 ");
    		}
    	}
    	sql.append(" AND nvl(END_DATE,SYSDATE) >= SYSDATE AND (START_DATE < sysdate or start_date is null ) ");

			
		logger.info("getTeamCdsByRole:   "+sql);
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String tempResult = "";
					tempResult=(String) rs.getString("TEAM_CD");
					return tempResult;
				}
			};
			try {
				logger.debug("userId @ getTeamCdsByRole: " + salesDto.getUsername()  +  "      "+salesDto.getChannelId());
				logger.debug("getTeamCdsByRole : " + sql);
				teamCds = simpleJdbcTemplate.query(sql.toString(), mapper);

			} catch (EmptyResultDataAccessException e) {
				logger.debug("EmptyResultDataAccessException");
				teamCds.add("");
			} catch (Exception e) {
				logger.debug("Exception caught in getTeamCdsByRole():", e);
				throw new DAOException(e.getMessage(), e);
			}
		
				return teamCds;
	}
	
	public String[] getLatestEmailSMSPair(String orderId) {
		logger.debug("@ getLatestEmailSMSPair" );
		logger.info("orderId: " + orderId);
		List<OrdEmailReqDTO> list = new ArrayList<OrdEmailReqDTO>();
		String[] pair = {"", ""};
		
		String sql = "select order_id, template_id, email_addr, seq_no, sms_no from bomweb_ord_email_req where template_id like 'R%' and order_id  = :orderId order by seq_no";
		
		ParameterizedRowMapper<OrdEmailReqDTO> mapper = new ParameterizedRowMapper<OrdEmailReqDTO>() {
			public OrdEmailReqDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrdEmailReqDTO dto = new OrdEmailReqDTO();
				dto.setOrderId(rs.getString("order_id"));
				dto.setTemplateId(rs.getString("template_id"));
				dto.setEmailAddr(rs.getString("email_addr"));
				dto.setSeqNo(rs.getInt("seq_no"));
				dto.setSMSno(rs.getString("sms_no"));
				return dto;
			}
		};
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			list = this.simpleJdbcTemplate.query(sql, mapper, params);
			
			for (OrdEmailReqDTO o: list) {
				if (!StringUtils.isEmpty(o.getEmailAddr())) {
					pair[0] = o.getEmailAddr();
				}
				if (!StringUtils.isEmpty(o.getSMSno())) {
					pair[1] = o.getSMSno();
				}
			}
		} catch (EmptyResultDataAccessException ee) {
			ee.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return pair;
	}
	
	public List<BasketDTO> getOnlinePlanDetails(String orderId, String type, String locale, String vas_category_id){
		logger.debug("@ getOnlinePlanDetails" );
		logger.info("orderId: " + orderId);		
		String sql = "select item_desc, NVL(recurrent_amt, 0) recurrent_amt, NVL(upfront_amt, 0) upfront_amt from W_ITEM_ATTRIBUTE \n" +
				"where item_id in (select id from bomweb_subscribed_item where order_id=:orderId and type=:type) \n" +
				"and locale=:locale ";
		if (vas_category_id==null){
			sql += "and vas_category_id is null";
		} else {
			sql += "and vas_category_id in (select code_id from BOMWEB_CODE_LKUP where CODE_TYPE = :vas_category_id)";
		}
		
		ParameterizedRowMapper<BasketDTO> mapper = new ParameterizedRowMapper<BasketDTO>() {
			public BasketDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketDTO dto = new BasketDTO();
				dto.setDescription(rs.getString("item_desc"));
				dto.setRecurrentAmt(rs.getString("recurrent_amt"));
				dto.setUpfrontAmt(rs.getString("upfront_amt"));
				return dto;
			}
		};
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("type", type);
			params.addValue("locale", locale);
			params.addValue("vas_category_id", vas_category_id);
			
			return this.simpleJdbcTemplate.query(sql, mapper, params);
		} catch (EmptyResultDataAccessException ee) {
			ee.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BasketDTO> getOnlineHSDetails(String orderId, String locale){
		logger.debug("@ getOnlineHSDetails" );
		logger.info("orderId: " + orderId);		
		String sql = "select brand, model, color, (nvl(upfront_amt, 0)-nvl(rebate_price,0)) effective_amt, NVL(upfront_amt, 0) upfront_amt from W_BASKET_ATTRIBUTE \n" +
				"where basket_id in (select distinct basket_id from bomweb_subscribed_item where order_id=:orderId and basket_id is not null) \n" +
				"and locale=:locale and model is not null";
		
		ParameterizedRowMapper<BasketDTO> mapper = new ParameterizedRowMapper<BasketDTO>() {
			public BasketDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketDTO dto = new BasketDTO();
				dto.setBrandDesc(rs.getString("brand"));
				dto.setModelDesc(rs.getString("model"));
				dto.setColorDesc(rs.getString("color"));
				dto.setRecurrentAmt(rs.getString("effective_amt"));
				dto.setUpfrontAmt(rs.getString("upfront_amt"));
				return dto;
			}
		};
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("locale", locale);
			return this.simpleJdbcTemplate.query(sql, mapper, params); 
		} catch (EmptyResultDataAccessException ee) {
			ee.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
