package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.bomwebportal.dto.CNMRTSupportDocDTO;
import com.bomwebportal.exception.DAOException;

public class CNMRTSUpportDocUploadDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());

	public List<CNMRTSupportDocDTO> getJobList() throws DAOException{
		logger.info("getJobList @ SchedulerDAO is called");
		List<CNMRTSupportDocDTO> resultList = new ArrayList<CNMRTSupportDocDTO> ();
		String sql = "select "
				+ "(SYSDATE - UPLOAD_DATE) * 24 AS DURATION, "
				+ "MRT_CN, "
				+ "IMAGE_TYPE, "
				+ "SEQ_NO, "
				+ "STATUS, "
				+ "UPLOAD_DATE "
				+ "from CNMRT_SUPPORT_DOC "
				+ "WHERE STATUS = 'SCANNING'";
		MapSqlParameterSource params = new MapSqlParameterSource();
		ParameterizedRowMapper<CNMRTSupportDocDTO> mapper = new ParameterizedRowMapper<CNMRTSupportDocDTO>() {
			public CNMRTSupportDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				CNMRTSupportDocDTO temp = new CNMRTSupportDocDTO();
				temp.setMrtCn(rs.getString("MRT_CN"));
				temp.setImageType(rs.getString("IMAGE_TYPE"));
				temp.setSeqNo(rs.getInt("SEQ_NO"));
				temp.setStatus(rs.getString("STATUS"));
				temp.setUploadDate(rs.getDate("UPLOAD_DATE"));
				temp.setDuration(rs.getDouble("DURATION"));
				return temp;
			}
		};
		try {
			resultList = simpleJdbcTemplate.query(sql, mapper, params);
			return resultList;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getJobList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateJob(CNMRTSupportDocDTO dto) throws DAOException {
		System.out.println("updateJob @ SchedulerDAO is called");
		return 1;
//		String sql = "UPDATE "
//				+ "CNMRT_SUPPORT_DOC "
//				+ "SET STATUS = :status "
//				+ "WHERE MRT_CN = :mrtCn";
//		MapSqlParameterSource params = new MapSqlParameterSource();
//		params.addValue("status", dto.getStatus());
//		params.addValue("mrtCn", dto.getMrtCn());
//		try {
//			System.out.println("success");
//			return simpleJdbcTemplate.update(sql, params);
//		} catch (Exception e) {
//			logger.error("Exception caught in updateJob()", e);
//			throw new DAOException(e.getMessage(), e);
//		}
	}
	
	public int deleteJob(CNMRTSupportDocDTO dto) throws DAOException {
		logger.info("deleteJob @ SchedulerDAO is called");
		String sql = "DELETE FROM "
				+ "CNMRT_SUPPORT_DOC "
				+ "WHERE MRT_CN = :mrtCn";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mrtCn", dto.getMrtCn());
		try {
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in deleteJob()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getSessionId(String staffId) throws DAOException {
		logger.info("getSessionId @ SchedulerDAO is called");
		List<CNMRTSupportDocDTO> resultList = new ArrayList<CNMRTSupportDocDTO> ();
		String sql = "SELECT "
				+ "SESSION_ID "
				+ "from BOMWEB_SALES_PROFILE "
				+ "WHERE STAFF_ID = :staffId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		ParameterizedRowMapper<CNMRTSupportDocDTO> mapper = new ParameterizedRowMapper<CNMRTSupportDocDTO>() {
			public CNMRTSupportDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				CNMRTSupportDocDTO temp = new CNMRTSupportDocDTO();
				temp.setSessionId(rs.getString("SESSION_ID"));
				return temp;
			}
		};
		try {
			resultList = simpleJdbcTemplate.query(sql, mapper, params);
			return resultList.get(0).getSessionId();
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getSessionId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}


}
