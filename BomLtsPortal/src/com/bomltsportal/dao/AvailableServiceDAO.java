package com.bomltsportal.dao;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class AvailableServiceDAO extends BaseDAO {

	public boolean IsServiceInMaintenance() throws DAOException{
		
		logger.debug("IsServiceInMaintenance");
		
		String sql = "select count(1) from w_online_sales_maint where (lob = 'LTS' OR lob = 'OLC') and start_date <= sysdate and end_date >= sysdate";
		int count = -1;
	
		try {
			count = Integer.parseInt((String) simpleJdbcTemplate.queryForObject(sql,String.class));
		}
		 catch (Exception e) {
				logger.error("Exception caught in IsServiceInMaintenance()", e);
				throw new DAOException(e.getMessage(), e);
		}
		
		return count >= 1;
		
	}
	
	public String getMaintenanceDetail(String lang){
		StringBuilder sql = new StringBuilder();
		if(StringUtils.equals(lang, BomLtsConstant.LOCALE_CHI)){
			sql.append(" SELECT MSG_DISPLAY_CH ");
		}else{
			sql.append(" SELECT MSG_DISPLAY_EN ");
		}
		sql.append(" FROM w_online_sales_maint ");
		sql.append(" WHERE (LOB = 'LTS' OR LOB = 'OLC') ");
		sql.append(" AND start_date <= SYSDATE  ");
		sql.append(" AND end_date >= SYSDATE  ");
		
		return simpleJdbcTemplate.queryForObject(sql.toString(), String.class);	
		
	}
	
}
