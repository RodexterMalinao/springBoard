package com.bomwebportal.dao;

import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.AccessRoleDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;

public class AccessRoleDAO extends CodeLkupDAOImpl {
	
	private static final String SQL_GET_ACCESS_ROLE = 
			"select category, channel_cd, channel_id, func_id, property, access_right " + 
			"from slv_maint_role " + 
			"where eff_start_date <= sysdate and nvl(eff_end_date, to_date('99991231','yyyymmdd')) > sysdate";

	
	
	public LookupItemDTO[] getCodeLkup() throws DAOException {
		
		final Map<String,LookupItemDTO> accessRoleMap = new TreeMap<String,LookupItemDTO>();
		
		ParameterizedRowMapper<Object> mapper = new ParameterizedRowMapper<Object>() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String channelId = rs.getString("CHANNEL_ID");
				String channelCd = rs.getString("CHANNEL_CD");
				String category = rs.getString("CATEGORY");
				String funcId = rs.getString("FUNC_ID");
				String property = rs.getString("PROPERTY");
				String accessRight = rs.getString("ACCESS_RIGHT");
				LookupItemDTO lookup = null;
				AccessRoleDTO role = null;
				String key = buildKey(channelId, channelCd, category, funcId);
				
				if (accessRoleMap.containsKey(key)) {
					role = (AccessRoleDTO)accessRoleMap.get(key).getItemValue();
				} else {
					role = new AccessRoleDTO();
					role.setCategory(category);
					role.setChannelCd(channelCd);
					role.setChannelId(channelId);
					role.setFuncId(funcId);
					lookup = new LookupItemDTO();
					lookup.setItemKey(key);
					lookup.setItemValue(role);
					accessRoleMap.put(key, lookup);
				}
				if (StringUtils.equals("ALL", property)) {
					role.setAccessRight(accessRight);
				} else {
					role.setPropertyAccessRole(property, accessRight);
				}
				return null;
			}
		};

		try {
			this.simpleJdbcTemplate.query(SQL_GET_ACCESS_ROLE, mapper);
			return accessRoleMap.values().toArray(new LookupItemDTO[accessRoleMap.size()]);
		} catch (Exception e) {
			logger.error("Error in getStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public static String buildKey(String pChannelId, String pChannelCd, String pCategory, String pFuncId) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(pChannelId);
		sb.append("|");
		sb.append(pChannelCd);
		sb.append("|");
		sb.append(pCategory);
		sb.append("|");
		sb.append(pFuncId);
		return sb.toString();
	}
}
