package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsOrderFalloutDTO;

public class MobCcsOrderFalloutDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	public int insertOrderFalloutDTO(MobCcsOrderFalloutDTO dto) throws DAOException {
		logger.info("insertOrderFalloutDTO is called");
		String sql = "INSERT INTO bomweb_order_fallout" +
				" (" +
				" ORDER_ID" +
				" , INCIDENT_ID" +
				" , REPORT_BY" +
				" , OCCURANCE_DATE" +
				" , FALLOUT_CAT" +
				" , REASON_CODE" +
				" , RESOLVE_CODE" +
				" , RESOLVE_DATE" +
				" , RESOLVE_BY" +
				" , SERIAL_IND" +
				" , VISIT_IND" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , REMARK" +
				" ) VALUES (" +
				" :orderId" +
				" , :incidentId" +
				" , :reportBy" +
				" , :occuranceDate" +
				" , :falloutCat" +
				" , :reasonCode" +
				" , :resolveCode" +
				" , :resolveDate" +
				" , :resolveBy" +
				" , :serialInd" +
				" , :visitInd" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" , :remark" +
				" )";
		try {
		    logger.info("insertOrderFalloutDTO() @ MobCcsOrderFalloutDAO: " + sql);
		    
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			// incidentId is not used
			params.addValue("incidentId", dto.getIncidentId());
			params.addValue("reportBy", dto.getReportBy());
			params.addValue("occuranceDate", dto.getOccuranceDate(), Types.TIMESTAMP);
			params.addValue("falloutCat", dto.getFalloutCat());
			params.addValue("reasonCode", dto.getReasonCode());
			params.addValue("resolveCode", dto.getResolveCode());
			params.addValue("resolveDate", dto.getResolveDate(), Types.DATE);
			params.addValue("resolveBy", dto.getResolveBy());
			params.addValue("serialInd", dto.getSerialInd());
			params.addValue("visitInd", dto.getVisitInd());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("remark", dto.getRemark());
			
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertOrderFalloutDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public MobCcsOrderFalloutDTO getOrderFalloutByCat(String orderId, String falloutCat)
			throws DAOException {
		logger.info("getOrderFalloutByCat is called");

		String SQL = "select f.order_id, f.occurance_date, f.fallout_cat, f.reason_code "
				+ "from BOMWEB_ORDER_FALLOUT f JOIN BOMWEB_ORDER BO "
				+ "on f.order_id = bo.order_id and f.reason_code = bo.reason_cd "
				+ "where f.order_id = ? "
				+ "and f.fallout_cat = ? "
				+ "and bo.order_status = '99' "
				+ "and bo.check_point = '999' "
				+ "and f.occurance_date = ( "
				+ "  select max(occurance_date) from BOMWEB_ORDER_FALLOUT "
				+ "  where order_id = f.order_id) ";

		ParameterizedRowMapper<MobCcsOrderFalloutDTO> mapper = new ParameterizedRowMapper<MobCcsOrderFalloutDTO>() {
			public MobCcsOrderFalloutDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCcsOrderFalloutDTO dto = new MobCcsOrderFalloutDTO();
				dto.setOrderId(rs.getString("order_id"));
				dto.setOccuranceDate(rs.getDate("occurance_date"));
				dto.setFalloutCat(rs.getString("fallout_cat"));
				dto.setReasonCode(rs.getString("reason_code"));
				return dto;
			}
		};

		try {
			List<MobCcsOrderFalloutDTO> result = simpleJdbcTemplate.query(SQL, mapper, orderId, falloutCat);
			if (CollectionUtils.isEmpty(result)) {
				return null;
			}
			return result.get(0);
		} catch (Exception e) {
			logger.error("Exception caught in getOrderFalloutByCat()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
