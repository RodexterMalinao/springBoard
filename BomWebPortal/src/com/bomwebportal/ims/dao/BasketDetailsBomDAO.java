package com.bomwebportal.ims.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.ims.dto.ValidateOfferResultDTO;
import com.google.gson.Gson;
import com.pccw.util.db.stringOracleType.OraArrayVarChar2;

public class BasketDetailsBomDAO extends BaseDAO {
	
	private static final String ORACLE_VAR_ARRAY = "OPS$BOM.VARRTYPE_VARCHAR200";
	
    private Gson gson = new Gson();
	
	public ValidateOfferResultDTO validateOffers(String[] inList) throws SQLException{ 
		
		ValidateOfferResultDTO result = new ValidateOfferResultDTO();
		
		int o_result=0;
		String o_result_msg="";
		int gnretval=0;
		int gnerrcode=0;
		String gserrtext="";
		
		Connection conn = null;
		
		try {
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$bom")
			.withCatalogName("pkg_oc_ims_sb_ret")   
			.withProcedureName("valid_offer_acq")
			.declareParameters( 
					new SqlParameter("i_in_prod_list", Types.ARRAY),
					new SqlOutParameter("o_result", Types.INTEGER),
					new SqlOutParameter("o_result_msg", Types.VARCHAR),
					new SqlOutParameter("gnretval", Types.INTEGER), 
					new SqlOutParameter("gnerrcode", Types.INTEGER),
					new SqlOutParameter("gserrtext", Types.VARCHAR)
					);
			
			logger.debug("**************IN/OUT list*************");
			logger.debug("inList : " + Arrays.asList(inList)); 
			
			logger.debug("**************IN/OUT list(end)*************"); 
			
			OraArrayVarChar2 a = new OraArrayVarChar2(ORACLE_VAR_ARRAY, inList);
			
			conn = jdbcCall.getJdbcTemplate().getDataSource().getConnection();
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_in_prod_list",  a.getOracleArray(conn));
			
			Map out = jdbcCall.execute(in); 
			
			o_result = (Integer) out.get("o_result");
			o_result_msg = (String) out.get("o_result_msg");
			gserrtext = (String) out.get("gserrtext"); 
			
			
			result.setO_result(o_result);
			result.setO_result_msg(o_result_msg);
			result.setGserrtext((String)out.get("gserrtext"));
			
			logger.debug("************ validateOffers result *******************");
			logger.debug(new Gson().toJson(result)); 
			logger.debug("************ validateOffers result(end) *******************"); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
			throw new SQLException();
			
		}
		finally{
			if(conn!=null) conn.close(); 
			
		}
		return result;
	}
	
}
