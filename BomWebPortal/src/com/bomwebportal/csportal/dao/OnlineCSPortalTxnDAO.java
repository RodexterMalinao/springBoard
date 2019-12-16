package com.bomwebportal.csportal.dao; 

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class OnlineCSPortalTxnDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
 
	
	public int insertTxn(String orderId,
			String apiTy,
			String sysId,
			String reply,
			String reqObj,
			String lastUpdBy) throws DAOException {
		try {
			String SQL = "INSERT INTO "
					+ " W_ONLINE_CSPORTAL_TXN ("
					+ " ORDER_ID, API_TY, SYS_ID, REPLY, REQ_OBJ, "
					+ " CREATE_DATE, CREATE_BY "
					+ " ) VALUES ( "
					+ " :ORDER_ID, :API_TY, :SYS_ID, :REPLY, :REQ_OBJ, "
					+ " sysdate, :CREATE_BY "
					+ " ) "
					;
			
					
			MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("ORDER_ID", orderId)
				.addValue("API_TY", apiTy)
				.addValue("SYS_ID", sysId)
				.addValue("REPLY", reply)
				.addValue("REQ_OBJ", reqObj)
				.addValue("CREATE_BY", lastUpdBy);
				
			return simpleJdbcTemplate.update(SQL, params);
						
		} catch (Exception e) {
			throw new DAOException("Failed to insert csp txn.", e);
		}
		
	}
	
	public int updateCsPortalInd(String orderId, String csPortalInd,
			String lastUpdateBy) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateCsPortalInd @ CustomerProfileDAO is called");
		}
		String sql = "update BOMWEB_CUSTOMER C\n"
				+ "set C.CS_PORTAL_IND = :V_CS_PORTAL_IND\n"
				+ "   ,C.LAST_UPD_BY   = :V_LAST_UPD_BY\n"
				+ "   ,C.LAST_UPD_DATE = sysdate\n"
				+ "where C.ORDER_ID = :V_ORDER_ID";

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("V_CS_PORTAL_IND", csPortalInd);
			params.addValue("V_LAST_UPD_BY", lastUpdateBy);
			params.addValue("V_ORDER_ID", orderId);

			if (logger.isInfoEnabled()) {
				logger.info("updateCsPortalInd() @ CustomerProfileDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in CustomerProfileDAO(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}

}
