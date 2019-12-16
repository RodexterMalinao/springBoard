package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.NowTVDAO.CursorMapper;
import com.bomwebportal.ims.dto.ui.AddressInfoUI;
import com.bomwebportal.ims.dto.ui.InstallFeeUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.util.BomWebPortalConstant;
import com.google.gson.Gson;



public class OnetimeChargeDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private Gson gson = new Gson();
	
	public InstallFeeUI onetimeAmount(String prodType, String housingType, String serbdyno, String floor, String flat) throws DAOException{		

		try{
			InstallFeeUI InstallFee = new InstallFeeUI();
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("pkg_oc_ims_sb")
				.withProcedureName("get_install_ot_amount_sp_cc");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_prod_type", Types.VARCHAR),
					new SqlParameter("i_housing_type", Types.VARCHAR),
					new SqlParameter("i_serbdyno", Types.VARCHAR),
					new SqlParameter("i_flat", Types.VARCHAR),
					new SqlParameter("i_floor", Types.VARCHAR),
					new SqlOutParameter("o_install_detail_cur", OracleTypes.CURSOR, new InstallationInstallmentPlanMapper()),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_prod_type", prodType);
			inMap.addValue("i_housing_type", housingType);
			inMap.addValue("i_serbdyno", serbdyno);
			inMap.addValue("i_flat", flat);
			inMap.addValue("i_floor", floor);
			SqlParameterSource in = inMap;
		

			logger.info("onetimeAmount input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.info("onetimeAmount output:"+gson.toJson(out));
			int error_code = -1;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");					
						
			if(error_code != 0){				
				InstallFee = null;
			}else{	
				InstallFee.setImsInstallationInstallmentPlanList((List) out.get("o_install_detail_cur"));			
				InstallFee.setErrorCode(error_code);
				InstallFee.setErrorText(error_text);	
				logger.debug("onetimeAmount() output record = " + gson.toJson(InstallFee));
			}

			logger.info("InstallFee:"+gson.toJson(InstallFee));
			return InstallFee;
		}catch (Exception e) {
				logger.error("Exception caught in onetimeAmount()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	public String isValidForWaive(String serbdyno, String prodType, String bandwidth, String housingType, Date appdate) throws DAOException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String appdatestr =  sdf.format(appdate);
			String result="N";
			
			try{
				SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$BOM")
					.withCatalogName("pkg_oc_ims_sb")
					.withProcedureName("get_install_ot_amount_eff_date");
				jdbcCall.setAccessCallParameterMetaData(false);
				jdbcCall.declareParameters(
						new SqlParameter("i_serbdyno", Types.VARCHAR),						
						new SqlParameter("i_prod_type", Types.VARCHAR),
						new SqlParameter("i_bandwidth", Types.VARCHAR),
						new SqlParameter("i_housing_type", Types.VARCHAR),						
						new SqlParameter("i_appdate", Types.VARCHAR),
						new SqlOutParameter("o_waive", Types.VARCHAR),						
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
				MapSqlParameterSource inMap = new MapSqlParameterSource();
				inMap.addValue("i_serbdyno", serbdyno);				
				inMap.addValue("i_prod_type", prodType);
				inMap.addValue("i_bandwidth", bandwidth);				
				inMap.addValue("i_housing_type", housingType);
				inMap.addValue("i_appdate", appdatestr);
				SqlParameterSource in = inMap;
			

				logger.info("isValidForWaive input:"+gson.toJson(in));
				Map out = jdbcCall.execute(in);
				logger.info("isValidForWaive output:"+gson.toJson(out));
				
				result =out.get("o_waive").toString();
				logger.info("result:"+gson.toJson(result));
			}catch (Exception e) {
					logger.error("Exception caught in reserveloginName()", e);
					throw new DAOException(e.getMessage(), e);
			}
			return result;
	}
	
	
	public AddressInfoUI addrInfoOnetimeAmount(String housingType, String serbdyno, String floor, String flat) throws DAOException{

		try{
			AddressInfoUI AddressInfo = new AddressInfoUI();
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("pkg_oc_ims_sb")
				.withProcedureName("get_addr_info_install_ot_cc");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_housing_type", Types.VARCHAR),	
					new SqlParameter("i_serbdyno", Types.VARCHAR),
					new SqlParameter("i_flat", Types.VARCHAR),	
					new SqlParameter("i_floor", Types.VARCHAR),
					new SqlOutParameter("o_install_detail_cur", OracleTypes.CURSOR, new HousTypeOTChrgMapper()),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_housing_type", housingType);
			inMap.addValue("i_serbdyno", serbdyno);
			inMap.addValue("i_flat", flat);
			inMap.addValue("i_floor", floor);
			SqlParameterSource in = inMap;

			logger.info("addrInfoOnetimeAmount input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.info("addrInfoOnetimeAmount output:"+gson.toJson(out));
			
			int error_code = -1;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
						
			String error_text = (String)out.get("gsErrText");			

			
			if(error_code != 0){				
				AddressInfo = null;
			}else{	
				AddressInfo.setHousTypeOTChrgList((List) out.get("o_install_detail_cur"));			
				AddressInfo.setErrorCode(error_code);
				AddressInfo.setErrorText(error_text);	
			}
			logger.info("AddressInfo:"+gson.toJson(AddressInfo));
			
			return AddressInfo;
		}catch (Exception e) {
				logger.error("Exception caught in onetimeAmount()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
	

	
	
}

