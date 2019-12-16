package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.NelgninvDTO;

public class ReleaseLoginIDDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public int releaseLoginID(String loginName, String idNo, String idType) throws DAOException{
		logger.info("releaseLoginID is called");
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$IMS")
				.withCatalogName("pkg_bossweb")
				.withProcedureName("releaseLoginID");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_domntype", Types.VARCHAR),
					new SqlParameter("i_netlogid", Types.VARCHAR),
					new SqlParameter("i_iddoctype", Types.VARCHAR),
					new SqlParameter("i_iddocnb", Types.VARCHAR),
					new SqlParameter("i_serordnb", Types.INTEGER),
					new SqlOutParameter("o_retVal", Types.INTEGER),
					new SqlOutParameter("o_errorCode", Types.INTEGER),
					new SqlOutParameter("o_errorText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_domntype", 'N');
			inMap.addValue("i_netlogid", loginName);
			inMap.addValue("i_iddoctype", idType);
			inMap.addValue("i_iddocnb", idNo);
			inMap.addValue("i_serordnb", -99999999);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			int error_code = -1;
			
			if (((Integer) out.get("o_errorCode"))!= null ) {
				error_code = ((Integer) out.get("o_errorCode")).intValue();
			}
						
			String error_text = (String)out.get("o_errorText");			

			logger.info("OPS$IMS.pkg_bossweb.ReleaseLoginID() output error_code = " + error_code);
			logger.info("OPS$IMS.pkg_bossweb.ReleaseLoginID() output error_text = " + error_text);
			
			if(error_code < 0){
				return error_code;
			}
			
			return 0;
		}catch (Exception e) {
				logger.error("Exception caught in reserveloginName()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<NelgninvDTO> getUselessLoginId() throws DAOException{
		logger.info("getUselessLoginId");
		
		String SQL = "select " +
				" a.NETLOGID, " +
				" a.IDDOCNB, " +
				" a.IDDOCTYP " +
				" from nelgninv a " +
				" where a.serordnb = -99999999 " +
				" and a.status = 'R' " +
				" and a.iddoctyp in ('HKID', 'PASS') " +
				" and a.DOMNTYPE = 'N'" +
				" and trunc(stsupdt) <= trunc(sysdate - (" +
				" 			select to_number(bom_desc) from b_lookup " +
				"			where bom_grp_id = 'SBIMS_LOGINID' and bom_code = 'house_keep_day' and BOM_STATUS = 'A' ))" +
				" and to_char(sysdate, 'hh24') >= (Select BOM_DESC from b_lookup where bom_grp_id = 'SBIMS_LOGINID' and bom_code = 'house_keep_hour_start' and BOM_STATUS = 'A' ) " +
				" and to_char(sysdate, 'hh24') < (Select BOM_DESC from b_lookup where bom_grp_id = 'SBIMS_LOGINID' and bom_code = 'house_keep_hour_end' and BOM_STATUS = 'A' )";	
		
		ParameterizedRowMapper<NelgninvDTO> mapper = new ParameterizedRowMapper<NelgninvDTO>() {

		public NelgninvDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
				NelgninvDTO dto = new NelgninvDTO();
				dto.setIDDOCNB(rs.getString("IDDOCNB"));
				dto.setIDDOCTY(rs.getString("IDDOCTYP"));
				dto.setNETLOGID(rs.getString("NETLOGID"));

				return dto;
			}
		};
		
		try {
			logger.debug("getUselessLoginId : " + SQL.toString());
			List<NelgninvDTO> loginid = simpleJdbcTemplate.query(SQL, mapper);
			
			return loginid;
		
		} catch (Exception e) {
			logger.error("Exception caught in getUselessLoginId()", e);
	
			throw new DAOException(e.getMessage(), e);
		}		
		
	}
}
