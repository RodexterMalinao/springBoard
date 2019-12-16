package com.bomltsportal.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class ConnectionCheckDAO {

	private DataSource bomWebPortalDS_DataSource;
	private DataSource bomDS_DataSource;
	private DataSource uamsDS_DataSource;
	
	private SimpleJdbcTemplate bomWebPortalDS_SimpleJdbcTemplate;
	private SimpleJdbcTemplate bomDS_SimpleJdbcTemplate;
	private SimpleJdbcTemplate uamsDS_SimpleJdbcTemplate;
	
	private String sql = "select '1' from dual";
	
	public void setUamsDS_DataSource(DataSource uamsDS_DataSource) {
		this.uamsDS_SimpleJdbcTemplate = new SimpleJdbcTemplate(uamsDS_DataSource);
		this.uamsDS_DataSource = uamsDS_DataSource;
	}
	
	
	public void setBomDS_DataSource(DataSource bomDS_DataSource) {
		this.bomDS_SimpleJdbcTemplate = new SimpleJdbcTemplate(bomDS_DataSource);
		this.bomDS_DataSource = bomDS_DataSource;
	}

	public void setBomWebPortalDS_DataSource(DataSource bomWebPortalDS_DataSource) {
		this.bomWebPortalDS_SimpleJdbcTemplate = new SimpleJdbcTemplate(bomWebPortalDS_DataSource);
		this.bomWebPortalDS_DataSource = bomWebPortalDS_DataSource;
	}

	public void QueryBomWebPortalDS() throws SQLException{
		String i = (String) this.bomWebPortalDS_SimpleJdbcTemplate.queryForObject(this.sql,String.class);
	}
	
	public void QueryBomDS() throws SQLException{
		String i = (String) this.bomDS_SimpleJdbcTemplate.queryForObject(this.sql,String.class);	
	}
	
	public void QueryUamsDS() throws SQLException{
		String i = (String) this.uamsDS_SimpleJdbcTemplate.queryForObject(this.sql,String.class);		
	}
}
	