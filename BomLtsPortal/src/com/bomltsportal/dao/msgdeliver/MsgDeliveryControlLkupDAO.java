package com.bomltsportal.dao.msgdeliver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class MsgDeliveryControlLkupDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	public String getSendSMSorNot() throws DAOException {
		
		String SQL = "select DESCRIPTION  \n"
			+" from w_code_lkup \n"
			+" where grp_id = 'BOM_LTS_PORTAL_PARM'" 
			+" and code = ?";
		String SmsOrNot;
		try {
			logger.debug("getSendSMSorNot @ ConstantLkupDAO: \n" + SQL);
			SmsOrNot = simpleJdbcTemplate.queryForObject(SQL, String.class, "CONF_SMS");;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			SmsOrNot = null;
			return SmsOrNot;
		} catch (Exception e) {
			logger.error("Exception caught in getSendSMSorNot()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return SmsOrNot;	
	}
	
	public String getSendEmailOrNot() throws DAOException {
		String SQL = "select DESCRIPTION  \n"
			+" from w_code_lkup \n"
			+" where grp_id = 'BOM_LTS_PORTAL_PARM'" 
			+" and code = ?";

		String EmailOrNot;
		try {
			logger.debug("getSendEmailOrNot @ ConstantLkupDAO: \n" + SQL);
			EmailOrNot = simpleJdbcTemplate.queryForObject(SQL, String.class, "CONF_EMAIL");;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			EmailOrNot = null;
			return EmailOrNot;
		} catch (Exception e) {
			logger.error("Exception caught in getSendEmailOrNot()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return EmailOrNot;	
	}
}
