package com.bomwebportal.mob.dao.bom;



import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


public class BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected SimpleJdbcTemplate simpleJdbcTemplate;
	
	protected JdbcTemplate jdbcTemplate;

	DataSource dataSource;
	
	public String getUID() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String ref_no = ts.toString();
		ref_no = ref_no.replaceAll("[-:\\.]", "");
		ref_no = ref_no.replaceAll("[\\s]", "-");
		return ref_no;
	}

	public void setDataSource(DataSource dataSource) {
	    this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

		
}
