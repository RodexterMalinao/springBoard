package com.bomwebportal.mob.ccs.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;

public class MobCcsMaintParmLkupDAO extends BaseDAO {
	public List<MaintParmLkupDTO> getMaintParmLkupDTO(String channelCd, String lob, String functionCd, String parmType) throws DAOException {
		String sql = "select" +
				" CHANNEL_CD" +
				" , LOB" +
				" , FUNCTION_CD" +
				" , PARM_TYPE" +
				" , PARM_VALUE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" from" +
				"  BOMWEB_MAINT_PARM_LKUP" +
				" where" +
				"  CHANNEL_CD = :channelCd" +
				"  and LOB = :lob" +
				"  and FUNCTION_CD = :functionCd" +
				"  and PARM_TYPE = :parmType";
		
		if ("STOCK QUANTITY ENQUIRY".equalsIgnoreCase(functionCd)) {
			sql += " order by decode(PARM_VALUE, 'RET', 1, 'SBO', 2, 'SBS', 3, 'CCS', 4, 5) ";
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("sql: " + sql);
			logger.debug("channelCd: " + channelCd + ", lob: " + lob + ", functionCd: " + functionCd + ", parmType: " + parmType);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("channelCd", channelCd);
			params.addValue("lob", lob);
			params.addValue("functionCd", functionCd);
			params.addValue("parmType", parmType);
			return this.simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(MaintParmLkupDTO.class), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMaintParmLkupDTO()");
			return Collections.emptyList();
		} catch (Exception e) {
			logger.info("Exception caught in getMaintParmLkupDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
