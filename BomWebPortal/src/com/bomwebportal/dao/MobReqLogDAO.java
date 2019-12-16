package com.bomwebportal.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.dto.MobReqLogDTO;


public class MobReqLogDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static String insertMobReqLogSQL = "INSERT INTO" +
			" BOMWEB_MOB_REQ_LOG " +
			" ( " +
			" TYPE, " +
			" ID, " +
			" SEQ_NO, " +
			" ACTION, " +
			" CREATE_BY, " +
			" CREATE_DATE, " +
			" LAST_UPD_BY, " +
			" LAST_UPD_DATE " +
			" ) VALUES (" +
			" :type " +
			" , :requestId" +
			" , (select nvl(max(seq_no),0)  from bomweb_mob_req_log where id = :requestId and type = :type) +1" +
			" , :action" +
			" , :createBy" +
			" , sysdate" +
			" , :lastUpdBy" +
			" , sysdate" +
			" )";
	
	
	public int insertMobReqLog(MobReqLogDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertMobReqLog @ MobReqLogDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info(insertMobReqLogSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("type", dto.getType());
			params.addValue("requestId", dto.getRequestId());
			//params.addValue("seqNo", dto.getSeqNo());
			params.addValue("action", dto.getAction());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			return simpleJdbcTemplate.update(insertMobReqLogSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in insertMobReqLog()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
}
