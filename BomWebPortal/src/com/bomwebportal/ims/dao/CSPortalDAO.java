package com.bomwebportal.ims.dao;


import com.bomwebportal.exception.DAOException;

public class CSPortalDAO extends BaseDAO{
	
	public void insertCsPortalLog(String orderId, String apiTy, String SysId, String reply, String reqObj) throws DAOException{
		
		logger.debug("insertCsPortalLog");
		
		String SQL = 	" insert into w_online_csportal_txn " +
						"(order_id, api_ty, sys_id, reply, req_obj,  " +
						"create_date) values " +
						"(?, ?, ?, ?, ?,  " +
						"sysdate ) ";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
					orderId, apiTy, SysId, reply, reqObj
					);
			
		}catch(Exception e){
			logger.error("Exception caught in insertCsPortalLog()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

}
