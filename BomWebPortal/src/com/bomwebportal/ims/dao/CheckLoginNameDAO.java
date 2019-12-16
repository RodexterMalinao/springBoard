package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class CheckLoginNameDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean checkLoginName(String loginName) throws DAOException{
		logger.debug("checkLoginName"+"["+loginName+"]");

		try{						
			String exist;
			String SQL="SELECT count(1) FROM NELGNINV WHERE DOMNTYPE IN ('N', 'E') AND netlogid = ? ";
		
			exist = simpleJdbcTemplate.queryForObject(SQL, String.class, loginName.toLowerCase());
		
			if("1".equals(exist)){
				return true;
			}else if("0".equals(exist)){
				return false;
			}
			
			return true;
		}catch (Exception e) {
			logger.error("Exception caught in checkloginName()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}