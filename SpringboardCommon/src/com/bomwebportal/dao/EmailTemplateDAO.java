package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.EmailTemplateDTO.PdfPwdInd;
import com.bomwebportal.dto.EmailTemplateDTO.EsigEmailLang;

public class EmailTemplateDAO extends BaseDAO {

	/*
	Initial copied on 20140925 from Version 1.1 of
	\Springboard\SBWPR3\BomWebPortal\src\com\bomwebportal\dao\EmailTemplateDAO.java
	*/	
	
	public EmailTemplateDTO getEmailTemplateDTO(String pTemplateId, String pLob, EsigEmailLang pLocale) {
		String sql = "select" +
				"  TEMPLATE_ID" +
				"  , LOB" +
				"  , TEMPLATE_SEQ" +
				"  , TEMPLATE_DESC" +
				"  , TEMPLATE_SUBJECT" +
				"  , TEMPLATE_CONTENT" +
				"  , CREATE_BY" +
				"  , CREATE_DATE" +
				"  , LAST_UPD_BY" +
				"  , LAST_UPD_DATE" +
				"  , LOCALE" +
				"  , PDF_PWD_IND" +
				" from" +
				"  bomweb_email_template" +
				" where" +
				"  TEMPLATE_ID = :templateId" +
				"  and LOB = :lob" +
				"  and LOCALE = :locale";
		List<EmailTemplateDTO> list = Collections.emptyList();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getEmailTemplateDTOReady() @ OrdEmailReqDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("templateId", pTemplateId);
			params.addValue("lob", pLob);
			params.addValue("locale", pLocale.toString());
			list = this.simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getAllDocDTOByType()");
			}
		}
		return isEmpty(list) ? null : list.get(0);
	}
	
	private ParameterizedRowMapper<EmailTemplateDTO> getRowMapper() {
		return new ParameterizedRowMapper<EmailTemplateDTO>() {
			public EmailTemplateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmailTemplateDTO dto = new EmailTemplateDTO();
				
				dto.setTemplateId(rs.getString("TEMPLATE_ID"));
				dto.setLob(rs.getString("LOB"));
				dto.setTemplateSeq(rs.getString("TEMPLATE_SEQ"));
				dto.setTemplateDesc(rs.getString("TEMPLATE_DESC"));
				dto.setTemplateSubject(rs.getString("TEMPLATE_SUBJECT"));
				dto.setTemplateContent(rs.getString("TEMPLATE_CONTENT"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setLocale(EsigEmailLang.valueOf(rs.getString("LOCALE")));
				String pdfPwdInd = rs.getString("PDF_PWD_IND");
				if (StringUtils.isNotBlank(pdfPwdInd)) {
					dto.setPdfPwdInd(PdfPwdInd.valueOf(pdfPwdInd));
				}
				
				return dto;
			}
			
		};
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
