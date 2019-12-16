package com.bomltsportal.dao.email;

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

import com.bomltsportal.dto.email.OrdEmailReqDTO;
import com.bomwebportal.dao.BaseDAO;
import com.bomltsportal.dto.email.OrderDTO.EsigEmailLang;

public class OrdEmailReqDAO extends BaseDAO {
	public int getNextSeq(String orderId, String templateId) {
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOALL @ OrdEmailReqDAO is called");
		}
		String sql = "select nvl(max(SEQ_NO), 0) + 1 from bomweb_ord_email_req where ORDER_ID = :orderId and TEMPLATE_ID = :templateId";
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOALL() @ OrdEmailReqDAO: " + sql);
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("templateId", templateId);
		return this.simpleJdbcTemplate.queryForInt(sql, params);
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALL(String lob) {
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOALL @ OrdEmailReqDAO is called");
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
				"  , oer.rowid ROW_ID" +
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
				logger.info("getOrdEmailReqDTOALL() @ OrdEmailReqDAO: " + sql);
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
				logger.info("getOrdEmailReqDTOALLByOrderId() @ OrdEmailReqDAO: " + sql);
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
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOALLByOnlyOrderId(String orderId) {
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOALLByOnlyOrderId @ OrdEmailReqDAO is called");
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
				"  , oer.rowid ROW_ID" +
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
			if (logger.isInfoEnabled()) {
				logger.info("getOrdEmailReqDTOALLByOnlyOrderId() @ OrdEmailReqDAO: " + sql);
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
			String shopCd, String lob, Date requestDate, String templateId) {
		if (logger.isInfoEnabled()) {
			logger.info("getOrdEmailReqDTOALLBySearch @ OrdEmailReqDAO is called");
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
				"  , oer.rowid ROW_ID" +
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
				"	) "); //Filter IMS non-retail orders 
				
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
	
	public OrdEmailReqDTO getOrdEmailReqDTOByOrderIdAndSeqNo(String orderId, int seqNo, String templateId) {
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
				
				return dto;
			}
			
		};
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
