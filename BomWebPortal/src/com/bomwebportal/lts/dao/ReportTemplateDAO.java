package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;

public class ReportTemplateDAO extends BaseDAO {
	
	
	public List<LookupItemDTO> getReportTemplate(String reportName, String attribute)
		throws DAOException{
		
		ParameterizedRowMapper<LookupItemDTO> mapper = new ParameterizedRowMapper<LookupItemDTO>() {
			public LookupItemDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LookupItemDTO rptTemplate = new LookupItemDTO();
				rptTemplate.setItemKey(rs.getString("LANGUAGE"));
				rptTemplate.setItemValue(rs.getString("CONTENTS"));
				return rptTemplate;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT LANGUAGE, CONTENTS ")
			.append(" FROM BOMWEB_RPT_TEMPLATE")	   
			.append(" WHERE RPT_NAME = ?")
			.append(" AND ATTRIBUTE = ? ");
			

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getReportTemplate() Sql: " + sql.toString());	
			}
			
			return simpleJdbcTemplate.query(sql.toString(), mapper, reportName, attribute);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getReportTemplate()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getReportTemplate():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

}
