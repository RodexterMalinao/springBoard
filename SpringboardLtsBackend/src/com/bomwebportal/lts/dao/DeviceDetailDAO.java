package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.DeviceDetailDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class DeviceDetailDAO extends BaseDAO{

	
	public DeviceDetailDTO getDevice(String deviceType, String channelId, String locale) throws DAOException {
		
		ParameterizedRowMapper<DeviceDetailDTO> mapper = new ParameterizedRowMapper<DeviceDetailDTO>() {
			public DeviceDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				DeviceDetailDTO deviceDetail = new DeviceDetailDTO();
				deviceDetail.setType(rs.getString("TYPE"));
				deviceDetail.setLocale(rs.getString("LOCALE"));
				deviceDetail.setDesc(rs.getString("DESCRIPTION"));
				deviceDetail.setHtml(rs.getString("HTML"));
				deviceDetail.setImagePath(rs.getString("IMAGE_PATH"));

				return deviceDetail;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT ddl.*")
			.append(" FROM w_device_display_lkup ddl")	   
			.append(" WHERE ddl.type = ?")
			.append(" AND ddl.lob = 'LTS'")
			.append(" AND ddl.locale = ? ");

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getEyeDeviceList() Sql: " + sql.toString());	
			}
			return simpleJdbcTemplate.queryForObject(sql.toString(), mapper, deviceType, locale);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getEyeDeviceList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getEyeDeviceList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<DeviceDetailDTO> getEyeDeviceList(String locale) throws DAOException {
		
		ParameterizedRowMapper<DeviceDetailDTO> mapper = new ParameterizedRowMapper<DeviceDetailDTO>() {
			public DeviceDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				DeviceDetailDTO deviceDetail = new DeviceDetailDTO();
				deviceDetail.setType(rs.getString("TYPE"));
				deviceDetail.setLocale(rs.getString("LOCALE"));
				deviceDetail.setDesc(rs.getString("DESCRIPTION"));
				deviceDetail.setHtml(rs.getString("HTML"));
				deviceDetail.setImagePath(rs.getString("IMAGE_PATH"));

				return deviceDetail;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT ddl.* ")
			.append(" FROM w_display_lkup dl, w_device_display_lkup ddl")	   
			.append(" WHERE dl.id = ? ")
			.append(" AND ddl.lob = 'LTS'")
			.append(" AND dl.type = ddl.type")
			.append(" AND ddl.locale = ? ");
			

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getEyeDeviceList() Sql: " + sql.toString());	
			}
			return simpleJdbcTemplate.query(sql.toString(), mapper, LtsBackendConstant.CHANNEL_ID_SPRINGBOARD_RETAIL, locale);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getEyeDeviceList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getEyeDeviceList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	

}
