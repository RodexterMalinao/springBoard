package com.bomwebportal.ims.dao;



import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
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
		this.dataSource = dataSource;
	    this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
		
	public String getOrderId(){
		String orderId;
		String SQL="SELECT TO_CHAR (SYSDATE, 'YYMMDD')|| TRIM (TO_CHAR (bomweb_seq_no.NEXTVAL, '0999999')) AS next_bomweb_seq_no FROM DUAL ";

		try {
			//queryForObject("select surname from t_actor where id = ?", new Object[]{new Long(1212)}, String.class);
			orderId = (String)	simpleJdbcTemplate.queryForObject(SQL,  String.class);
			//orderId ="20100113000000001";
		} catch (EmptyResultDataAccessException erdae) {
			//logger.info("EmptyResultDataAccessException");
			orderId = null;
		} 
		logger.info("[BasedDAO] Get Order ID: " + orderId);
		return orderId;
	}
	
	protected String getConnectionPoolInfo(){
		 
		  String rtnStr="";
		  try {		   
		   BasicDataSource bds = (BasicDataSource) dataSource;
		   logger.info("calling getConnectionPoolInfo:");
		   rtnStr= "NumActive: " + bds.getNumActive() + ","
		          + "NumIdle: " + bds.getNumIdle();
		   logger.info(rtnStr);		 		   		 
		 
		  } catch (Exception ex) {
		   logger.info(ex.toString());
		   logger.info("call getConnectionPoolInfo() failed:" + ex);
		  }
		  return rtnStr;
		 }
		
}
