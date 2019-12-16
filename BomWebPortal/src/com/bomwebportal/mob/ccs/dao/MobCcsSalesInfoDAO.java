package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsSalesInfoDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String getSalesInfoDTOSQL = "SELECT" +
			" bsa.STAFF_ID" +
			" , bsp.CATEGORY" +
			" , bsa.TEAM_CD" +
			" , bsa.CENTRE_CD" +
			" , bsa.CHANNEL_CD" +
			" FROM bomweb_sales_assignment bsa" +
			" INNER JOIN bomweb_sales_profile bsp ON (bsa.STAFF_ID = bsp.STAFF_ID)" +
			" WHERE bsa.CHANNEL_CD = :channelCd" +
			" AND bsp.CATEGORY = :category" +
			" AND TRUNC(sysdate) BETWEEN bsa.START_DATE AND nvl(bsa.END_DATE, sysdate)" +
			" AND TRUNC(sysdate) BETWEEN bsp.START_DATE AND nvl(bsp.END_DATE, sysdate)" +
			" ORDER BY bsa.STAFF_ID";
			
	public List<SalesInfoDTO> getSalesInfoDTO(String channelCd, String category) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getSalesInfoDTO() is called");
		}
		List<SalesInfoDTO> itemList = Collections.emptyList();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getSalesInfoDTO() @ SalesInfoDAO: " + getSalesInfoDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("channelCd", channelCd);
			params.addValue("category", category);
			itemList = simpleJdbcTemplate.query(getSalesInfoDTOSQL, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getSalesInfoDTO()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getSalesInfoDTO():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<SalesInfoDTO> getRowMapper() {
		return new ParameterizedRowMapper<SalesInfoDTO>() {
			public SalesInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				SalesInfoDTO dto = new SalesInfoDTO();
				dto.setStaffId(rs.getString("STAFF_ID"));
				dto.setCategory(rs.getString("CATEGORY"));
				dto.setTeamCd(rs.getString("TEAM_CD"));
				dto.setCentreCd(rs.getString("CENTRE_CD"));
				dto.setChannelCd(rs.getString("CHANNEL_CD"));
				return dto;
			}
		};
	}
	
	private static final String getSalesInfoDTOByIDSQL = 
			"SELECT channel_id, " 
					+"  channel_cd, " 
					+"  centre_cd, " 
					+"  team_cd, " 
					+"  staff_id " 
					+"FROM bomweb_sales_assignment " 
					+"WHERE staff_id = :staffId "
					+"AND TRUNC(to_date(:appDate, 'DD/MM/YYYY')) " 
					+"between TRUNC(START_DATE) and TRUNC(NVL(END_DATE, sysdate)) ";
			
	public List<SalesInfoDTO> getSalesInfoDTOByID(String staffId, String appDate) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getSalesInfoDTOByID() is called");
		}
		List<SalesInfoDTO> itemList = Collections.emptyList();
		
		ParameterizedRowMapper<SalesInfoDTO> mapper = new ParameterizedRowMapper<SalesInfoDTO>() {
			
			public SalesInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				SalesInfoDTO temp = new SalesInfoDTO();
				temp.setChannelId(rs.getString("CHANNEL_ID"));
				temp.setChannelCd(rs.getString("CHANNEL_CD"));
				temp.setCentreCd(rs.getString("CENTRE_CD"));
				temp.setTeamCd(rs.getString("TEAM_CD"));
				temp.setStaffId(rs.getString("STAFF_ID"));
				return temp;
			}
		};
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getSalesInfoDTOByID() @ MobCcsSalesInfoDAO: " + getSalesInfoDTOByIDSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", staffId);
			if (StringUtils.isBlank(appDate)) {
				appDate = Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT);
			}
			params.addValue("appDate", appDate);
			itemList = simpleJdbcTemplate.query(getSalesInfoDTOByIDSQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getSalesInfoDTOByID()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getSalesInfoDTOByID():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		
		return itemList;
	}
}
