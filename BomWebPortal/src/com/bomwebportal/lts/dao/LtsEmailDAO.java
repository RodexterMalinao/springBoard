package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.axis.utils.ArrayUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.OrderSendRequestDTO;
import com.bomwebportal.util.Utility;

public class LtsEmailDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<OrderSendRequestDTO> getCCOrderSendRequest(String orderId, String requestDate, 
			String staffId, String teamCd, String seqNo, String templateId, String[] channelId) throws DAOException {
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sql = new StringBuilder();
			
//			sql.append("SELECT * FROM ( ");
			sql.append(" SELECT boer.order_id, boer.template_id, to_char(boer.request_date, 'dd/MM/yyyy hh24:MI:ss') request_date, ");
			sql.append("        boer.email_addr, boer.create_by, to_char(boer.sent_date, 'dd/MM/yyyy hh24:MI:ss') sent_date, ");
			sql.append("        boer.seq_no, boer.locale, boer.err_msg, boer.method, boer.sms_no, ");
			sql.append("        boer.file_path_name_1, boer.file_path_name_2, boer.file_path_name_3, ");
			sql.append("        bet.template_desc, bet.template_subject, bet.template_content, ");
			sql.append("        bo.dis_mode, bfpr.print_date, bfpr.print_req_status, ");
			sql.append("        bfpr.POSTAL_ADDR_LINE_1, bfpr.POSTAL_ADDR_LINE_2, bfpr.POSTAL_ADDR_LINE_3, ");
			sql.append("        bfpr.POSTAL_ADDR_LINE_4, bfpr.POSTAL_ADDR_LINE_5, bfpr.POSTAL_ADDR_LINE_6 ");
			sql.append(" FROM bomweb_order bo,");
			sql.append("      bomweb_ord_email_req boer,");
			sql.append("      bomweb_email_template bet,");
			sql.append("      BOMWEB_FORM_PRINT_REQ bfpr");
			sql.append(" WHERE bo.order_id = boer.order_id");
			sql.append("  AND boer.template_id = bet.template_id");
			sql.append("  AND boer.locale = bet.locale");
			sql.append("  AND boer.form_print_req_id  = bfpr.PRINT_REQ_ID(+)");
			sql.append("  AND bo.lob = 'LTS'");
			
			if (StringUtils.isNotBlank(seqNo)) {
				sql.append("AND boer.seq_no = :seqNo");
				params.addValue("seqNo", seqNo);
			}
			if (StringUtils.isNotBlank(orderId)) {
				sql.append(" AND boer.order_Id = :orderId");
				params.addValue("orderId", orderId);
			}
			if (StringUtils.isNotBlank(requestDate)) {
				sql.append(" AND trunc(boer.request_date) = trunc(:requestDate)");
				params.addValue("requestDate", Utility.string2Date(requestDate), Types.TIMESTAMP);
			}
			if (StringUtils.isNotBlank(staffId)) {
				sql.append(" AND bo.create_by = :staffId");
				params.addValue("staffId", staffId);
			}
			if (StringUtils.isNotBlank(teamCd)) {
				sql.append(" AND bo.shop_cd = :teamCd");
				params.addValue("teamCd", teamCd);
			}
			if (StringUtils.isNotBlank(templateId)) {
				sql.append(" AND boer.template_id = :templateId");
				params.addValue("templateId", templateId);
			}
			if(channelId != null && !(channelId.length<0)){
				sql.append(" AND BO.CREATE_CHANNEL IN (" +
						" SELECT DISTINCT CHANNEL_CD FROM BOMWEB_SALES_ASSIGNMENT" +
						" WHERE CHANNEL_ID in (:channelId) )");
				params.addValue("channelId", Arrays.asList(channelId)); 
			}
			sql.append(" ORDER BY BOER.REQUEST_DATE DESC, BOER.SEQ_NO DESC ");
//			sql.append(" ) RESULT "); 
//			sql.append(" WHERE ROWNUM <= 50 ");
			
			
			return this.simpleJdbcTemplate.query(sql.toString(), getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(e.getMessage());
		}
		
		
	}
	
	private ParameterizedRowMapper<OrderSendRequestDTO> getRowMapper() {
		return new ParameterizedRowMapper<OrderSendRequestDTO>() {
			public OrderSendRequestDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderSendRequestDTO dto = new OrderSendRequestDTO();
				
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setEmail(rs.getString("EMAIL_ADDR"));
				dto.setErrMsg(rs.getString("ERR_MSG"));
				dto.setFilePathName1(rs.getString("FILE_PATH_NAME_1"));
				dto.setFilePathName2(rs.getString("FILE_PATH_NAME_2"));
				dto.setFilePathName3(rs.getString("FILE_PATH_NAME_3"));
				dto.setLang(rs.getString("LOCALE"));
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setRequestDate(rs.getString("REQUEST_DATE"));
				dto.setSentDate(rs.getString("SENT_DATE"));
				dto.setSeqNo(rs.getString("SEQ_NO"));
				dto.setTemplateId(rs.getString("TEMPLATE_ID"));
				dto.setTemplateDesc(rs.getString("TEMPLATE_DESC"));
				dto.setTemplateSubject(rs.getString("TEMPLATE_SUBJECT"));
				dto.setTemplateContent(rs.getString("TEMPLATE_CONTENT"));
				dto.setSmsNo(rs.getString("SMS_NO"));
				dto.setMethod(rs.getString("METHOD"));
				dto.setDisMode(rs.getString("DIS_MODE"));
				dto.setBillingAddress((StringUtils.isEmpty(rs.getString("POSTAL_ADDR_LINE_1")) ? "" : rs.getString("POSTAL_ADDR_LINE_1")) +
					                  (StringUtils.isEmpty(rs.getString("POSTAL_ADDR_LINE_2")) ? "" : rs.getString("POSTAL_ADDR_LINE_2")) +
						              (StringUtils.isEmpty(rs.getString("POSTAL_ADDR_LINE_3")) ? "" : rs.getString("POSTAL_ADDR_LINE_3")) + 
						              (StringUtils.isEmpty(rs.getString("POSTAL_ADDR_LINE_4")) ? "" : rs.getString("POSTAL_ADDR_LINE_4")) + 
						              (StringUtils.isEmpty(rs.getString("POSTAL_ADDR_LINE_5")) ? "" : rs.getString("POSTAL_ADDR_LINE_5")) +
						              (StringUtils.isEmpty(rs.getString("POSTAL_ADDR_LINE_6")) ? "" : rs.getString("POSTAL_ADDR_LINE_6")));
				dto.setPrintRequestStatus(rs.getString("PRINT_REQ_STATUS"));
				dto.setPrintDate(rs.getString("PRINT_DATE"));
				
				return dto;
			}
			
		};
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOLTS(String orderId, String lob, String... templateIds) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrdEmailReqDTOLTS @ LtsEmailDAO is called");
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
				"  where 1=1 " +
				"  AND sent_date IS NULL " +
				"  AND oer.err_msg IS NULL " +
				"	");
				
		List<OrdEmailReqDTO> list = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			if (StringUtils.isNotBlank(orderId)) {
				sql.append(" AND oer.ORDER_ID = :orderId ");
				params.addValue("orderId", orderId);
			}
			if (StringUtils.isNotBlank(lob)) {
				sql.append(" AND o.LOB = :lob ");
				params.addValue("lob", lob);
			}
			if (templateIds != null && templateIds.length > 0) {
				sql.append(" AND oer.TEMPLATE_ID IN ( :templateIds ) ");
				params.addValue("templateIds", Arrays.asList(templateIds));
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getOrdEmailReqDTOLTS() @ LtsEmailDAO: " + sql);
			}
			sql.append(" order by oer.ORDER_ID, oer.TEMPLATE_ID, oer.SEQ_NO ");
			list = this.simpleJdbcTemplate.query(sql.toString(), getOrdEmailReqRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrdEmailReqDTOLTS()");
			}
		}
	
		return list;
	}
	
	/*Copied from OrdEmailReqDAO.java rev 1.35 by Felix*/
	private ParameterizedRowMapper<OrdEmailReqDTO> getOrdEmailReqRowMapper() {
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
	
	public int getNextSeqNo(String orderId){
		if (logger.isDebugEnabled()) {
			logger.debug("getNextSeqNo @ ltsEmailDAO is called");
		}
		String sql = "select nvl(max(SEQ_NO), 0) + 1 from bomweb_ord_email_req where ORDER_ID = :orderId";
		if (logger.isDebugEnabled()) {
			logger.debug("getNextSeqNo() @ ltsEmailDAO: " + sql);
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		return this.simpleJdbcTemplate.queryForInt(sql, params);
	}
}
