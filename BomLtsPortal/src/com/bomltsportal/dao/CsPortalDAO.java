package com.bomltsportal.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomltsportal.dto.CsPortalTxnDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class CsPortalDAO extends BaseDAO {
	
	public void createCsPortalTxn(CsPortalTxnDTO csPortalTxn) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO W_ONLINE_CSPORTAL_TXN ");
		sql.append(" (ORDER_ID, API_TY, SYS_ID, REPLY, REQ_OBJ, CREATE_DATE, CREATE_BY) ");
		sql.append(" VALUES ");
		sql.append(" (:orderId, :apiTy, :sysId, :reply, :reqObj, SYSDATE, :createBy) ");

		try {
			paramSource.addValue("orderId", csPortalTxn.getOrderId());
			paramSource.addValue("apiTy", csPortalTxn.getApiTy());
			paramSource.addValue("sysId", csPortalTxn.getSysId());
			paramSource.addValue("reply", csPortalTxn.getReply());
			paramSource.addValue("reqObj", csPortalTxn.getReqObj());
			paramSource.addValue("createBy", BomLtsConstant.USER_ID);
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in createCsPortalTxn():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

	
}
