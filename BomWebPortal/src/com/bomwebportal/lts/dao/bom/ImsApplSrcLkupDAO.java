package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class ImsApplSrcLkupDAO extends BaseDAO {

	public List<String> getImsApplSource(String applMthd) throws DAOException{
		//String SQL = "SELECT * FROM B_APPLN_SRC s WHERE s.applcode= ? AND s.obsolete = 'N'";
		String SQL = "SELECT l.BOM_DESC AS code, l.BOM_CODE AS DESCRIPTION " +
						"FROM B_LOOKUP l, B_APPLN_SRC s " + 
						"WHERE l.BOM_STATUS= 'A' AND l.BOM_GRP_ID = 'SOURCE' AND l.BOM_CODE = s.SRCCODE " + 
						"AND s.applcode = ? " +
						"ORDER BY code "; 
		ParameterizedRowMapper<String> mapper = 
				new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				String sourceCode = rs.getString("CODE");
				return sourceCode;
			}
		};
		try{
			return simpleJdbcTemplate.query(SQL, mapper, applMthd);
		} catch (Exception e) {
			logger.error("Exception caught in getImsApplSource()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

}
