package com.bomwebportal.dao;



import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.bomwebportal.exception.DAOException;


public class BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected SimpleJdbcTemplate simpleJdbcTemplate;
	
	protected JdbcTemplate jdbcTemplate;

	protected DataSource dataSource;
	
	public String getUID() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String ref_no = ts.toString();
		ref_no = ref_no.replaceAll("[-:\\.]", "");
		ref_no = ref_no.replaceAll("[\\s]", "-");
		return ref_no;
	}

	public void setDataSource(DataSource pDataSource) {
	    this.simpleJdbcTemplate = new SimpleJdbcTemplate(pDataSource);
	    this.jdbcTemplate = new JdbcTemplate(pDataSource);
	}
	
	public DataSource getDataSource() {
		if (this.jdbcTemplate != null) {
			return this.jdbcTemplate.getDataSource();
		}
		return null;
	}
}
