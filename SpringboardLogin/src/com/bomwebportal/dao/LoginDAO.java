package com.bomwebportal.dao;

import java.sql.Types;
import com.bomwebportal.util.BomWebPortalConstant;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.DAOException;

public class LoginDAO extends BaseDAO{

	protected final Log logger = LogFactory.getLog(getClass());
	
	// add by Joyce 20111026
	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	
	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}

	// modified by Joyce 20111026
	public boolean validateLogin(BomSalesUserDTO bomSalesUserDTO) throws DAOException {
		logger.info("validateLogin is called");
		
		return (validateUser(bomSalesUserDTO) && validateApp(bomSalesUserDTO));
	}

	// add by Joyce 20111026
	private boolean validateUser(BomSalesUserDTO bomSalesUserDTO)throws DAOException {
		logger.info("validateUser@validateLogin is called");
		
		String username = bomSalesUserDTO.getUsername();
		String password = bomSalesUserDTO.getPassword();
		
		boolean result = false;
		
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$SSM")
            	.withCatalogName("SM_SECURITY")
            	.withProcedureName("VALIDATE_USER");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlOutParameter("ERROR_CODE", Types.INTEGER),
					new SqlOutParameter("ERROR_TEXT", Types.VARCHAR),
					new SqlParameter("P_USER_ID", Types.VARCHAR),
					new SqlParameter("P_PWD", Types.VARCHAR),
					new SqlOutParameter("P_DAYS_REMAIN", Types.INTEGER));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("P_USER_ID", username);
			inMap.addValue("P_PWD", password);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			int error_code = -1;
			int p_days_remain = -1; 
			
			if (((Integer) out.get("ERROR_CODE"))!= null ) {
				error_code = ((Integer) out.get("ERROR_CODE")).intValue();
			}
			
			if (((Integer) out.get("P_DAYS_REMAIN"))!= null ) {
				p_days_remain = ((Integer) out.get("P_DAYS_REMAIN")).intValue();
			}
			
			String error_text = (String)out.get("ERROR_TEXT");
			
			// use ssm error text as error message prompt to users
			bomSalesUserDTO.setErrMsg(error_text);
			
			logger.info("OPS$SSM.SM_SECURITY.validate_user() output error_code = " + error_code);
			logger.info("OPS$SSM.SM_SECURITY.validate_user() output error_text = " + error_text);
			logger.info("OPS$SSM.SM_SECURITY.validate_user() output p_days_remain = " + p_days_remain);
			
			// modified by Joyce 20120113, pass for will expire tmr or active
			if ((error_code == 0) && (p_days_remain >= 0)) {
				result = true;
			}
//			else if ((error_code == 0) && p_days_remain == 0) {
//				bomSalesUserDTO.setErrMsg("Your password expired. Please change your password as soon as possible.");
//			}
			
			logger.info("validateUser result = " + result);
			
			return result;
			
		} catch (Exception e) {
			logger.error("Exception caught in validateUser()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	// add by Joyce 20111026
	private boolean validateApp(BomSalesUserDTO bomSalesUserDTO)throws DAOException {
					
		logger.info("validateApp@validateLogin is called");
		
		String username = bomSalesUserDTO.getUsername();
		
		boolean result2 = false;
		
		try {	
				SimpleJdbcCall jdbcCall2 = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$SSM")
	            	.withCatalogName("SM_SECURITY")
	            	.withProcedureName("VALIDATE_APP");
				jdbcCall2.setAccessCallParameterMetaData(false);
				jdbcCall2.declareParameters(
						new SqlOutParameter("ERROR_CODE", Types.INTEGER),
						new SqlOutParameter("ERROR_TEXT", Types.VARCHAR),
						new SqlParameter("P_USER_ID", Types.VARCHAR),
						new SqlParameter("P_APPL_ID", Types.VARCHAR),
						new SqlParameter("P_ENV_ID", Types.VARCHAR),
						new SqlParameter("P_VERSION", Types.VARCHAR));
				MapSqlParameterSource inMap2 = new MapSqlParameterSource();
				inMap2.addValue("P_USER_ID", username);
				inMap2.addValue("P_APPL_ID", "BOM");
				
				String appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
				
				String env_id = propertyConfigurer.getMergedProperties().getProperty(appEnv + ".ssm_env_id");
				inMap2.addValue("P_ENV_ID", env_id);
				
				String version = propertyConfigurer.getMergedProperties().getProperty(appEnv + ".ssm_version");
				inMap2.addValue("P_VERSION", version);
				
				SqlParameterSource in2 = inMap2;
				
				Map out2 = jdbcCall2.execute(in2);
				int error_code2 = -1;
				String error_text2 = ""; 
				
				if (((Integer) out2.get("ERROR_CODE"))!= null ) {
					error_code2 = ((Integer) out2.get("ERROR_CODE")).intValue();
				}
	
				error_text2 = ((String) out2.get("ERROR_TEXT"));

				// use ssm error text as error message prompt to users
				bomSalesUserDTO.setErrMsg(error_text2);

				logger.info("OPS$SSM.SM_SECURITY.validate_app() output error_code = " + error_code2);
				logger.info("OPS$SSM.SM_SECURITY.validate_app() output error_text = " + error_text2);
				
				if ( (error_code2 == 0) && (error_text2 != null) && !("".equals(error_text2))) {
					result2 = true;
				} else {
					result2 = false;
				}

				logger.info("validateApp result = " + result2);
			
			return result2;
			
		} catch (Exception e) {
			logger.error("Exception caught in validateApp()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}

		
	
	


