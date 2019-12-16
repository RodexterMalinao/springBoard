package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;


public class AddressIndex2DAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());

	public String validIPAddress(String ipAddress) throws DAOException{
		
		logger.info("validIPAddress is called");

		int cnt = 0;
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT COUNT(*) \n");
		SQL.append(" FROM   W_CODE_LKUP \n");
		SQL.append(" WHERE  grp_id = 'SB_TRUST_IP_ADDR' \n");
		SQL.append(" AND    code = ? \n");

		try {
			logger.info("validIPAddress() @ AddressIndex2DAO: " + SQL);
			cnt = simpleJdbcTemplate.queryForInt(SQL.toString(), new Object[]{ipAddress});
			logger.debug("Count for validIP address = " + cnt);

		} catch (Exception e) {
			logger.info("Exception caught in validIPAddress():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return (cnt==0?"N":"Y");
				
	}
	
}
