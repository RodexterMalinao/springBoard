package com.bomwebportal.lts.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.CsPortalTxnDTO;

public class CsPortalDAO extends BaseDAO {
	
	public void createCsPortalTxn(CsPortalTxnDTO csPortalTxn, String userId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO W_ONLINE_CSPORTAL_TXN ");
		sql.append(" (ORDER_ID, API_TY, SYS_ID, REPLY, REQ_OBJ, CREATE_DATE, CREATE_BY, CLUB_REPLY, MYHKT_REPLY) ");
		sql.append(" VALUES ");
		sql.append(" (:orderId, :apiTy, :sysId, :reply, :reqObj, SYSDATE, :createBy, :clubRes, :hktRes) ");

		try {
			paramSource.addValue("orderId", csPortalTxn.getOrderId());
			paramSource.addValue("apiTy", csPortalTxn.getApiTy());
			paramSource.addValue("sysId", csPortalTxn.getSysId());
			paramSource.addValue("reply", csPortalTxn.getReply());
			paramSource.addValue("reqObj", csPortalTxn.getReqObj());
			paramSource.addValue("createBy", userId);
			paramSource.addValue("clubRes", csPortalTxn.getClubReply());
			paramSource.addValue("hktRes", csPortalTxn.getMyhktReply());
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in createCsPortalTxn():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

	
}
