package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class LtsAppointmentDAO extends BaseDAO {
	private static final String SQL = "SELECT HOLIDAY FROM B_HOLIDAY";
	
	public List<String> getHolidayList() throws DAOException{
		
		List<String> list = new ArrayList<String>();
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				String s = rs.getString("HOLIDAY");
				return s.split(" ")[0];
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getHolidayList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
}
