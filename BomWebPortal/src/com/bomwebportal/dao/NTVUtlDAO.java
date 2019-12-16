package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.NtvUtilDTO;
import com.bomwebportal.exception.DAOException;

public class NTVUtlDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	private String GetNtvUtilCodeSQL() {
		return "select code, description from w_code_lkup where GRP_ID = 'SB_IMS_NTVUTL'";
	}
	private String GetRptUtilCodeSQL() {
		return "select code, description from w_code_lkup where GRP_ID = 'SB_IMS_NTVUTL_RPT'";

	}
	
	public String[][] GetNtvUtilCode()throws DAOException
	{
		List<NtvUtilDTO> List = new ArrayList<NtvUtilDTO>();
		ParameterizedRowMapper<NtvUtilDTO> mapper = new ParameterizedRowMapper<NtvUtilDTO>() {
		    public NtvUtilDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	NtvUtilDTO dto = new NtvUtilDTO();
		    	dto.setCode(rs.getString("code"));
		    	dto.setDescription(rs.getString("description"));
		        return dto;
		    }
		};
		try {
			List = simpleJdbcTemplate.query(GetNtvUtilCodeSQL(), mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			List = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNtvUtilCode@NTVUtlDAO():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		if(List != null && List.size()>1)
		{
			String[][] str = new String[List.size()][2];
			
			for(int i = 0; i < List.size(); ++i)
			{
				str[i][0] = List.get(i).getCode();
				str[i][1] = List.get(i).getDescription();
			}
			
			return str;
		}
		return null;
	}
	
	public String[][] GetRptUtilCode()throws DAOException
	{
		List<NtvUtilDTO> List = new ArrayList<NtvUtilDTO>();
		ParameterizedRowMapper<NtvUtilDTO> mapper = new ParameterizedRowMapper<NtvUtilDTO>() {
		    public NtvUtilDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	NtvUtilDTO dto = new NtvUtilDTO();
		    	dto.setCode(rs.getString("code"));
		    	dto.setDescription(rs.getString("description"));
		        return dto;
		    }
		};
		try {
			List = simpleJdbcTemplate.query(GetRptUtilCodeSQL(), mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			List = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getRptUtilCode@ReportUtlDAO():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		if(List != null && List.size()>1)
		{
			String[][] str = new String[List.size()][2];
			
			for(int i = 0; i < List.size(); ++i)
			{
				str[i][0] = List.get(i).getCode();
				str[i][1] = List.get(i).getDescription();
			}
			
			return str;
		}
		return null;
	}
}