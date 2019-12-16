package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class LtsAddressSrvNumDAO extends BaseDAO {
	public List<String> getLtsAddressSrvNum(String i_serbdyno, String i_flat, String i_floor) throws DAOException{
		//String SQL = "SELECT * FROM B_APPLN_SRC s WHERE s.applcode= ? AND s.obsolete = 'N'";
		String SQL = 	" 	select srv_num from b_service where install_addr in(                                 "+
   			            "     select addr_id from b_address_dtl                                                  "+
			            "     where srvbdry_num = :srvbdry_num                                                   "+
			            "       and ((unit_num = :unit_num and floor_num = :floor_num)                           "+ //BOM 7A 7 SB 7A 7
			            "            or (unit_num = :floor_num||:unit_num and floor_num = :floor_num)            "+ //SB flat A floor 7
			            "            or (unit_num = :floor_num||:unit_num and floor_num is null)                 "+
			            "            or (floor_num || unit_num = :unit_num and floor_num = :floor_num)           "+
			            "            ))                                                                           "+
			            "       and (eff_end_date is null or eff_end_date > sysdate)                            ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("srvbdry_num", i_serbdyno);
		params.addValue("unit_num", i_flat);
		params.addValue("floor_num", i_floor);
		ParameterizedRowMapper<String> mapper = 
		new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				return rs.getString("srv_num");
			}
		};
		try{
			return simpleJdbcTemplate.query(SQL, mapper, params );
		} catch (Exception e) { 
			logger.error("Exception caught in getLtsAddressSrvNum()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
