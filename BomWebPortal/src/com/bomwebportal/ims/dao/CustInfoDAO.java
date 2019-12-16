package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.CustInfoDTO;

public class CustInfoDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<CustInfoDTO> getCustInfo(String iddocno) throws DAOException{
		
		logger.info("getCustInfo is called");
		
		try {												
			
			String SQL = "	SELECT CUSTNB, TITLE, CUSTLANM, CUSTFINM " +
				        "	FROM CUSTOMER " +
						"	WHERE IDDOCTYP = 'PASS' " +
						"	AND IDDOCNB = ? " +
						"	AND ROWNUM = 1 ";
					
			
			ParameterizedRowMapper<CustInfoDTO> mapper = new ParameterizedRowMapper<CustInfoDTO>() {
				public CustInfoDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
					CustInfoDTO cust = new CustInfoDTO();
					
					cust.setCustNo(rs.getString("CUSTNB"));
					cust.setTitle(rs.getString("TITLE"));
					cust.setFirstName(rs.getString("CUSTFINM"));
					cust.setLastName(rs.getString("CUSTLANM"));
					
					return cust;
				}
			};						
			
			return simpleJdbcTemplate.query(SQL, mapper, iddocno);
			
		}catch (Exception e) {
			logger.error("Exception caught in validateLogin()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

}
