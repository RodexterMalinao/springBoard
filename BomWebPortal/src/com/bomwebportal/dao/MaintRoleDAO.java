package com.bomwebportal.dao;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsMaintFuncDTO;

public class MaintRoleDAO extends BaseDAO{

	protected final Log logger = LogFactory.getLog(getClass());
	
//	public List<String> getUsableMaintId(String channelCd, String category) throws DAOException {
//
//		List<String> maintIdList = new ArrayList<String>();
//
//		String SQL = "SELECT DISTINCT maint_id FROM bomweb_maint_role \n"
//			+" WHERE channel_cd = ? \n"
//			+" AND CATEGORY = ? \n"
//			+" AND SYSDATE BETWEEN start_date AND NVL(end_date, SYSDATE)";
//
//		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return rs.getString("maint_id");
//			}
//		};
//		
//		try {
//			logger.debug("getUsableMaintId @ MaintRoleDAO: " + SQL);
//			maintIdList = simpleJdbcTemplate.query(SQL, mapper, channelCd, category);
//		} catch (EmptyResultDataAccessException erdae) {
//			logger.info("EmptyResultDataAccessException");
//			maintIdList = null;
//		} catch (Exception e) {
//			logger.error("Exception caught in getUsableMaintId()", e);
//			throw new DAOException(e.getMessage(), e);
//		}
//
//		return maintIdList;
//
//	}
	
	/**
	 * Using sales' channel code & category to find out which
	 * MOBCCS admin maintenance functions can be used by
	 * this sales.
	 * Used in Welcome page - to display corresponding buttons.
	 * 
	 * @param channelCd
	 * @param category
	 * @return List<MobCcsMaintFuncDTO>
	 * @throws DAOException
	 */
	public List<MobCcsMaintFuncDTO> getUsableMaintFuncInfo(int channelId, String channelCd, String category) throws DAOException {
		final String sql = "SELECT" + 
							"  MAINT.CHANNEL_ID" +
							"  , MAINT.CHANNEL_CD" +
							"  , MAINT.CATEGORY" +
							"  , TITLE.CODE_ID MAINT_ID" +
							"  , TITLE.CODE_DESC FUNC_NAME" +
							"  , URL.CODE_DESC FUNC_HTML" +
							" FROM" +
							"  BOMWEB_CODE_LKUP PREFIX" +
							"  INNER JOIN BOMWEB_CODE_LKUP TITLE ON (PREFIX.CODE_DESC || '_FUNC' = TITLE.CODE_TYPE)" +
							"  INNER JOIN BOMWEB_CODE_LKUP URL ON (TITLE.CODE_TYPE || '_HTML' = URL.CODE_TYPE AND TITLE.CODE_ID = URL.CODE_ID)" +
							"  INNER JOIN BOMWEB_MAINT_ROLE MAINT ON (PREFIX.CODE_ID = MAINT.CHANNEL_CD AND TITLE.CODE_ID = MAINT.MAINT_ID)" +
							" WHERE" +
							"  PREFIX.CODE_TYPE = 'CHANNEL_FLOW'" +
							"  AND PREFIX.CODE_ID = :channelCd" +
							"  AND MAINT.CATEGORY = :category" +
							"  AND MAINT.CHANNEL_ID = :channelId" +
							" ORDER BY" +
							"  TITLE.CODE_ID";

		if (logger.isDebugEnabled()) {
			logger.debug("sql: " + sql);
			logger.debug("channelId: " + channelId + ", channelCd: " + channelCd + ", category: " + category);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("channelCd", channelCd);
			params.addValue("category", category);
			params.addValue("channelId", channelId);

			return simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(MobCcsMaintFuncDTO.class), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException");
			}
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error("Exception caught in getUsableMaintFuncInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}

