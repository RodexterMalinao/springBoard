package com.bomwebportal.sbs.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class OnlineApiTxnDAO extends BaseDAO {
	
	
	public int insertTxn(String orderId,
			String apiType,
			String sysId,
			String resultCode,
			String resultDesc,
			String reqObj,
			String lastUpdBy) throws DAOException {
		try {
			String SQL = "INSERT INTO "
					+ " W_ONLINE_API_TXN ("
					+ " ORDER_ID, API_TYPE, SYS_ID, "
					+ " RESULT_CODE, RESULT_DESC, "
					+ " REQ_OBJ, "
					+ " CREATE_DATE, CREATE_BY, "
					+ " LAST_UPD_DATE, LAST_UPD_BY "
					+ " ) VALUES ( "
					+ " :orderId, :apiType, :sysId, :resultCode, :resultDesc, "
					+ " :reqObj, "
					+ " sysdate, :createBy, sysdate, :createBy "
					+ " ) "
					;
			
					
			MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("orderId", orderId)
				.addValue("apiType", apiType)
				.addValue("sysId", sysId)
				.addValue("resultCode", resultCode)
				.addValue("resultDesc", resultDesc)
				.addValue("reqObj", reqObj)
				.addValue("createBy", lastUpdBy);
				
			return simpleJdbcTemplate.update(SQL, params);
						
		} catch (Exception e) {
			throw new DAOException("Failed to insert api txn.", e);
		}
		
	}
	

	
}